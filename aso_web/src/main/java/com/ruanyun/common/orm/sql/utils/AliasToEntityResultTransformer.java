package com.ruanyun.common.orm.sql.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

public class AliasToEntityResultTransformer<T> extends AliasedTupleSubsetResultTransformer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Class<T> clz;
	
	public AliasToEntityResultTransformer(Class<T> clz) {
		this.clz=clz;
	}

	public boolean isTransformedValueATupleElement(String[] aliases,
			int tupleLength) {
		return false;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		try {
			T t=clz.newInstance();
			Method method=null;
			Method[] methods=clz.getMethods();
			for ( int i=0; i<tuple.length; i++ ) {
				String alias = aliases[i];
				if ( alias!=null ) {
					method=getMethod(alias,methods);
					if(method !=null){
						Object value=tuple[i];
						if(value !=null){
							method.invoke(t, value);
						}
					}
				}
			}
			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Method getMethod(String methodName,Method methods[]){
		String name="";
		methodName=methodName.replaceAll("_", "").toUpperCase();
		for(int i=0;i<methods.length;i++){
			name=methods[i].getName().toUpperCase();
			if(name.startsWith("SET") && name.substring(3).equals(methodName))
				return methods[i];
		}
		return null;
	}

}
