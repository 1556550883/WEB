package com.ruanyun.common.annotation.method;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ruanyun.common.annotation.bind.FormList;
import com.ruanyun.common.utils.EmptyUtils;
public class FormListMethodArgumentResolver implements HandlerMethodArgumentResolver {
	/**
	 * 重写到父类
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		 String name = parameter.getParameterAnnotation(FormList.class).value();
	        
	        Object target = (mavContainer.containsAttribute(name)) ?
	                mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, webRequest);
	        
	        WebDataBinder binder = binderFactory.createBinder(webRequest, target, name);
	        target = binder.getTarget();
	        if (target != null) {
	            bindRequestParameters(mavContainer, binderFactory, binder, webRequest, parameter);
	            
	            if (binder.getBindingResult().hasErrors()) {
	                    throw new BindException(binder.getBindingResult());
	            }
	        }
	        
	        target = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType());
	        mavContainer.addAttribute(name, target);
	        
	        return target;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		  if (parameter.hasParameterAnnotation(FormList.class)) {
			  Class<?> parameterType = parameter.getParameterType();
			  if(Collection.class.isAssignableFrom(parameterType)){
				 return true;
			  }
	        }
	        return false;
	}
	
	/**
	 * 
	 * @param attributeName
	 * @param parameter
	 * @param binderFactory
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Object createAttribute(String attributeName, MethodParameter parameter,
            WebDataBinderFactory binderFactory,  NativeWebRequest request) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        if(parameterType.isArray() || List.class.isAssignableFrom(parameterType)) {
            return ArrayList.class.newInstance();
        }
        if(Set.class.isAssignableFrom(parameterType)) {
            return HashSet.class.newInstance();
        }
        return BeanUtils.instantiateClass(parameter.getParameterType());
    }
	
	/**
	 * 绑定值到请求总
	 * @param mavContainer
	 * @param binderFactory
	 * @param binder
	 * @param request
	 * @param parameter
	 * @throws Exception
	 */
	 @SuppressWarnings("unchecked")
	protected void bindRequestParameters(
	            ModelAndViewContainer mavContainer,
	            WebDataBinderFactory binderFactory,
	            WebDataBinder binder, 
	            NativeWebRequest request, 
	            MethodParameter parameter) throws Exception {
		 String prefixName = parameter.getParameterAnnotation(FormList.class).value();
	        Class<?> targetType = binder.getTarget().getClass();
	        if(Collection.class.isAssignableFrom(targetType)) {//bind collection
	            
	            Type type = parameter.getGenericParameterType();
	            Class<?> componentType = Object.class;
	            
	            Collection<Object> target = (Collection<Object>) binder.getTarget();

	            if(type instanceof ParameterizedType) {
	                componentType = (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
	            }
	            
	            if(parameter.getParameterType().isArray()) {
	                componentType = parameter.getParameterType().getComponentType();
	            }
	            
	            List<Map<String,Object>> list=resetMap(request,componentType,prefixName);
	            for(Map<String, Object> map : list){
	            	  Object component = BeanUtils.instantiate(componentType);
	                    WebDataBinder componentBinder = binderFactory.createBinder(request, component, null);
	                    component = componentBinder.getTarget();
	                    if (component != null) {
	                    	MutablePropertyValues pvs = new MutablePropertyValues(map);
	                        componentBinder.bind(pvs);
	                        if (componentBinder.getBindingResult().hasErrors()) {
	                                throw new BindException(componentBinder.getBindingResult());
	                        }
	                        target.add(component);
	            }
	            }
	        }else {
	            ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
	            servletBinder.bind((HttpServletRequest) request.getNativeRequest());
	        }
	    }
	 
	 /**
	  * 把参数放入map中
	  * @param request 请求
	  * @param clz 类型
	  * @return
	  */
	 protected List<Map<String, Object>> resetMap(NativeWebRequest request,
				Class<?> clz,String prefixName) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<String> fileList = getFile(clz);
			if (EmptyUtils.isNotEmpty(fileList)) {

				Map<String, String[]> params = request.getParameterMap();
				Map<String, Object> map = null;
				Set<String> keys = params.keySet();
				for (String key : keys) {
					if(fileList.contains(key) || fileList.contains(key.replace(prefixName+".",""))){
						String [] values=params.get(key);
						for(int j=0;j<values.length;j++){
							if(list.size()>j){
								map=list.get(j);
							}else{
								map=new HashMap<String, Object>();
								list.add(map);
							}
							map.put(key, values[j]);
						}
					}
				}
			}
			return list;
		}
	
	 /**
	  * 获取类型的所有属性
	  * @param componentType
	  * @return
	  */
	 public List<String> getFile(Class<?> componentType){
    	 Field[] fields=componentType.getDeclaredFields();
    	 
    	if(EmptyUtils.isNotEmpty(fields)){
    		List<String> list = new ArrayList<String>();
    		for(Field file : fields){
    			list.add(file.getName());
    		}
    		return list;
    	}
    	return null;
    }
}

