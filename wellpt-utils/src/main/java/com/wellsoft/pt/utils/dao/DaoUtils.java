/*
 * @(#)2013-1-29 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.dao;

import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2013-1-29
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-29.1	zhulh		2013-1-29		Create
 * </pre>
 * 
 */
public class DaoUtils {

	/**
	 * 实例化对象
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object instance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isUnique(HibernateDao<?, ?> dao, IdEntity entity, String... checkFields) {
		BeanWrapper ebw = new BeanWrapperImpl(entity);
		// 生成查询的entity,获得对象
		IdEntity queryModel = (IdEntity) DaoUtils.instance(entity.getClass());
		for (String checkField : checkFields) {
			Object checkFieldValue = ebw.getPropertyValue(checkField);
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(queryModel);
			bw.setPropertyValue(checkField, checkFieldValue);
		}
		List<IdEntity> queryResult = dao.findByExample(queryModel);
		// 查询判断返回值个数，两个及两个以上直接返回false
		if (queryResult.size() > 1) {
			return false;
		}
		return true;
	}

	public static boolean isExists(HibernateDao<?, ?> dao, IdEntity entity, String... checkFields) {
		BeanWrapper ebw = new BeanWrapperImpl(entity);
		// 生成查询的entity,获得对象
		IdEntity queryModel = (IdEntity) DaoUtils.instance(entity.getClass());
		for (String checkField : checkFields) {
			Object checkFieldValue = ebw.getPropertyValue(checkField);
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(queryModel);
			bw.setPropertyValue(checkField, checkFieldValue);
		}
		List<IdEntity> queryResult = dao.findByExample(queryModel);
		// 查询判断返回值个数，两个及两个以上直接返回false
		if (queryResult.size() >= 1) {
			return true;
		}
		return false;
	}
}
