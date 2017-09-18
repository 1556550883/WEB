package com.ruanyun.common.orm.sql.utils;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.ToListResultTransformer;

public class TransformersUtils{
	
	private TransformersUtils() {}
	
	/**
	 * Each row of results is a <tt>Map</tt> from alias to values/entities
	 */
	public static final AliasToEntityMapResultTransformer ALIAS_TO_ENTITY_MAP =
			AliasToEntityMapResultTransformer.INSTANCE;

	/**
	 * Each row of results is a <tt>List</tt> 
	 */
	public static final ToListResultTransformer TO_LIST = ToListResultTransformer.INSTANCE;
	
	/**
	 * Creates a resulttransformer that will inject aliased values into 
	 * instances of Class via property methods or fields.
	 * @param <T>
	 */
	public static <T> ResultTransformer aliasToBean(Class<T> target) {
		return new AliasToEntityResultTransformer<T>(target);
	}
	
}
