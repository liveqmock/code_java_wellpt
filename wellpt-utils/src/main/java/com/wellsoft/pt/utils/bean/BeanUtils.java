/*
 * @(#)BeanUtils.java 2012-10-16 1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 对象属性复制工具类
 * 
 * @author zhulh
 * @date 2012-10-16
 * 
 *       <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-16.1	zhulh		2012-10-16		Create
 * 2012-11-16.1	zhulh		2012-11-16		改为spring的BeanUtils
 * </pre>
 * 
 */
public class BeanUtils {
	private static HashMap<String, Set<String>> ignorePropertyMap = new HashMap<String, Set<String>>();

	public static void copyProperties(Object source, Object target) {
		org.springframework.beans.BeanUtils.copyProperties(source, target, getIgnoreProperties(target));
	}

	public static void copyProperties(Object source, Object target, Class<?> editable) {
		org.springframework.beans.BeanUtils.copyProperties(source, target, editable);
	}

	public static void copyProperties(Object source, Object target, String[] ignoreProperties) {
		org.springframework.beans.BeanUtils.copyProperties(source, target,
				getIgnoreProperties(target, ignoreProperties));
	}

	@SuppressWarnings("unchecked")
	public static <ENTITY extends IdEntity, COLLECTION extends Collection<ENTITY>> COLLECTION convertCollection(
			COLLECTION sources, Class<ENTITY> targetClass) {
		COLLECTION targets = null;
		try {
			if (Set.class.isAssignableFrom(sources.getClass())) {
				targets = (COLLECTION) new LinkedHashSet<ENTITY>();
			} else {
				targets = (COLLECTION) new ArrayList<ENTITY>();
			}
			Iterator<ENTITY> it = sources.iterator();
			while (it.hasNext()) {
				ENTITY target = targetClass.newInstance();
				BeanUtils.copyProperties(it.next(), target);
				targets.add(target);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return targets;
	}

	private static String[] getIgnoreProperties(Object target, String... ignoreProperties) {
		String key = target.getClass().getCanonicalName();
		if (!ignorePropertyMap.containsKey(key)) {
			setIgnoreProperties(target);
		}
		Set<String> propertySet = ignorePropertyMap.get(key);
		if (propertySet == null) {
			return ignoreProperties;
		}

		for (String ignore : ignoreProperties) {
			propertySet.add(ignore);
		}
		return propertySet.toArray(new String[0]);
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param target
	 */
	private static void setIgnoreProperties(Object target) {
		Class<?> cls = target.getClass();
		while (IdEntity.class.isAssignableFrom(cls)) {
			String key = target.getClass().getCanonicalName();
			Set<String> ignoreProperties = null;
			if (!ignorePropertyMap.containsKey(target.getClass().getCanonicalName())) {
				ignorePropertyMap.put(key, new HashSet<String>());
			}
			ignoreProperties = ignorePropertyMap.get(key);
			ignoreProperties.add(IdEntity.CREATOR);
			ignoreProperties.add(IdEntity.CREATE_TIME);
			ignoreProperties.add(IdEntity.REC_VER);

			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				UnCloneable unCloneable = field.getAnnotation(UnCloneable.class);
				if (unCloneable != null) {
					ignoreProperties = ignorePropertyMap.get(key);
					ignoreProperties.add(field.getName());
				}
			}

			cls = cls.getSuperclass();
		}
	}
}
