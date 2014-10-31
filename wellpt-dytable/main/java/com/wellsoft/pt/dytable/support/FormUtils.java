/*
 * @(#)2013-12-12 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.support;

import java.util.Map;

import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-12-12
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-12-12.1	zhulh		2013-12-12		Create
 * </pre>
 *
 */
public class FormUtils {

	/**
	 * 创建key不区分大小写的Map
	 * 
	 * @return
	 */
	public static <V extends Object> Map<String, V> createColumnMap() {
		return new LinkedCaseInsensitiveMap<V>();
	}

	/**
	 * 创建key不区分大小写的Map
	 * 
	 * @param initialCapacity
	 * @return
	 */
	public static <V extends Object> Map<String, V> createColumnMap(int initialCapacity) {
		return new LinkedCaseInsensitiveMap<V>(initialCapacity);
	}

	/**
	 * 判断某输入类型是不是附件类型 
	 * @param inputType
	 * @param fieldName
	 * @return
	 */
	public static boolean isAttach(String inputType, String fieldName) {
		if (DytableConfig.isAttach(inputType) //正文,signature_,expand_field字段的inputMode也是附件类型所以这里要剔除
				&& (!"signature_".equalsIgnoreCase(fieldName) && !"expand_field".equalsIgnoreCase(fieldName))) {
			return true;
		} else {
			return false;
		}

	}
}
