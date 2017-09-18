/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-25
 */
package com.ruanyun.web.service.background;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.AdverInferfaceDao;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TAdverInferface;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-1-25
 */
@Service
public class AdverInferfaceService extends BaseServiceImpl<TAdverInferface>{
	@Autowired
	private AdverInferfaceDao adverInferfaceDao;
	
	/**
	 * 功能描述:获取分页
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public Page<TAdverInferface> queryPage(Page<TAdverInferface> page,
			TAdverInferface info) {
		return adverInferfaceDao.pageSql(page, info);
	}
	
	/**
	 * 功能描述: 添加接口参数
	 *
	 * @author yangliu  2016年1月26日 下午5:58:14
	 * 
	 * @param parameterTypes 参数对应的数据库字典的配置
	 * @param parameterNames 客户填写的配置
	 * @param inferfaceRequestType 口类型1--客户方回调我们 2-我们调用客户方
	 * @param adverNum 广告编号
	 * @param inferfaceType   参数类型 1--广告的 2--渠道
	 * @return
	 */
	public Integer saveConfigure(String [] parameterTypes,String [] parameterNames,Integer inferfaceRequestType,String adverNum,String inferfaceType) {
		super.delete(TAdverInferface.class, new String[]{"inferfaceRequestType","inferfaceType","adverNum"}, new Object[]{inferfaceRequestType,inferfaceType,adverNum});
			int length=parameterTypes.length;
		for(int i=0;i<length;i++){
				if(EmptyUtils.isNotEmpty(parameterNames[i])){
					TAdverInferface inferface = new TAdverInferface();
					inferface.setAdverNum(adverNum);
					inferface.setInferfaceRequestType(inferfaceRequestType);
					inferface.setInferfaceType(inferfaceType);
					inferface.setParameterName(parameterNames[i]);
					inferface.setParameterType(parameterTypes[i]);
					save(inferface);
				}
		}
		return 1;
	}
	
	/**
	 * 
	 * 功能描述:增加参数
	 * @param parameterType  原有参数名
	 * @param parameterName  对应的参数名
	 * @param info
	 *@author feiyang
	 *@date 2016-1-26
	 */
	public void addAdverInferface(String parameterType, String parameterName,TAdverInferface info){
		if (EmptyUtils.isNotEmpty(parameterName)) {			
			TAdverInferface newInfo=new TAdverInferface();	
			newInfo.setParameterType(parameterType);
			newInfo.setParameterName(parameterName);
			newInfo.setAdverNum(info.getAdverNum());
			newInfo.setInferfaceRequestType(info.getInferfaceRequestType());
			newInfo.setInferfaceType(info.getInferfaceType());
			save(newInfo);
		}
	}
	
	/**
	 * 功能描述: 根据条件获取列表
	 *
	 * @author yangliu  2016年1月26日 下午9:08:14
	 * 
	 * @param adverNum 广告或者渠道编号
	 * @param inferfaceRequestType 接口类型1--客户方回调我们 2-我们调用客户方
	 * @param inferfaceType 1--广告的 2--渠道
	 * @return
	 */
	public List<TAdverInferface> getAdverList(String adverNum,Integer inferfaceRequestType,String inferfaceType){
		return super.getAllByCondition(TAdverInferface.class, new String[]{"inferfaceRequestType","inferfaceType","adverNum"}, new Object[]{inferfaceRequestType,inferfaceType,adverNum});
	}
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年1月26日 下午4:23:37
	 * 
	 * @param adverNum 广告编号
	 * @param inferfaceRequestType   接口类型1--客户方回调我们 2-我们调用客户方
	 * @param inferfaceType 1--广告的 2--渠道
	 * @return
	 */
	public List<TAdverInferface> getAdverINferfaceAll(String adverNum,Integer inferfaceRequestType,String inferfaceType){
		List<TDictionary> listDictionary=PublicCache.getItemList(Constants.PARAMETER_TYPE);
		List<TAdverInferface> adverInferfaceList=getAdverList(adverNum, inferfaceRequestType, inferfaceType);
		if(EmptyUtils.isEmpty(adverInferfaceList)){
			adverInferfaceList=new ArrayList<TAdverInferface>();
			TAdverInferface adverInferface=null;
			for(TDictionary dic : listDictionary){
				adverInferface=new TAdverInferface();
				adverInferface.setParameterType(dic.getItemCode());
				adverInferface.setParameterTypeName(dic.getItemName());
				adverInferfaceList.add(adverInferface);
			}
		}else if(listDictionary.size()>adverInferfaceList.size()){  //字典表大于我们配置的信息
			TAdverInferface adverInferface1=null;
			for(TDictionary dic : listDictionary){
				boolean isTrue=true;
				for(TAdverInferface adverInferface : adverInferfaceList){
					if(dic.getItemCode().equals(adverInferface.getParameterType())){
						isTrue=false;
					}
				}
				if(isTrue){
					adverInferface1=new TAdverInferface();
					adverInferface1.setParameterType(dic.getItemCode());
					adverInferface1.setParameterTypeName(dic.getItemName());
					adverInferfaceList.add(adverInferface1);
				}
			}
		}
		return adverInferfaceList;
	}
	
	/**
	 * 
	 * 功能描述:修改参数
	 * @param adverInferface
	 * @return
	 *@author feiyang
	 *@date 2016-1-26
	 */
	public int updateInferface(TAdverInferface adverInferface){
		if (EmptyUtils.isNotEmpty(adverInferface.getInferaceId())) {
			TAdverInferface oldAdverInferface=get(TAdverInferface.class, adverInferface.getInferaceId());
			BeanUtils.copyProperties(adverInferface, oldAdverInferface, new String[]{});
			update(oldAdverInferface);
		}
		return 1;
	}
	
	/**
	 * 功能描述: 获取 接口对象 key 为我们对应的类型 value 为接口参数配置对象
	 *
	 * @author yangliu  2016年1月26日 下午9:11:53
	 * 
	 * @param adverNum 广告或者渠道编号
	 * @param inferfaceRequestType 接口类型1--客户方回调我们 2-我们调用客户方
	 * @param inferfaceType 1--广告的 2--渠道
	 * @return
	 */
	public Map<String,TAdverInferface> getMapAdverInferfaceByAdverNum(String adverNum,Integer inferfaceRequestType,String inferfaceType){
		List<TAdverInferface> adverInferfaceList=getAdverList(adverNum, inferfaceRequestType, inferfaceType);
		Map<String,TAdverInferface> adverInferfaceMap = new HashMap<String, TAdverInferface>();
		if(EmptyUtils.isNotEmpty(adverInferfaceList)){
			for(TAdverInferface adver : adverInferfaceList){
				adverInferfaceMap.put(adver.getParameterType(), adver);
			}
		}
		return adverInferfaceMap;
	}
	
	
	
}
