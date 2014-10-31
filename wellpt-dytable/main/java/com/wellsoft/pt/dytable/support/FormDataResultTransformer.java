/*
 * @(#)2013-2-28 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-2-28
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-28.1	zhulh		2013-2-28		Create
 * </pre>
 *
 */
public class FormDataResultTransformer extends AliasedTupleSubsetResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4195705427730178919L;

	public static final FormDataResultTransformer INSTANCE = new FormDataResultTransformer();

	private FormDataResultTransformer() {
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				result.put(StringUtils.lowerCase(alias), tuple[i]);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.transform.TupleSubsetResultTransformer#
	 * isTransformedValueATupleElement(java.lang.String[], int)
	 */
	@Override
	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	private Object readResolve() {
		return INSTANCE;
	}
}
