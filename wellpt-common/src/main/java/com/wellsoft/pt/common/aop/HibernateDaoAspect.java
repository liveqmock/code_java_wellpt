/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.aop;

import java.util.Calendar;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

import com.wellsoft.pt.core.entity.BaseEntity;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.security.support.IgnoreLoginUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-2-8
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-8.1	zhulh		2013-2-8		Create
 * </pre>
 *
 */
@Aspect
public class HibernateDaoAspect implements Ordered {
	/**
	 * Before *SimpleHibernateDao.save
	 * 
	 * @param entity
	 */
	@Before("execution(* com.wellsoft.pt.core.dao.hibernate.SimpleHibernateDao.save(..)) && args(entity)")
	public void beforeSaveModel(JoinPoint jp, BaseEntity entity) {
		if (StringUtils.isBlank(entity.getUuid())) {
			Calendar calendar = Calendar.getInstance();
			UserDetails user = SpringSecurityUtils.getCurrentUser();
			// 如果当前处于忽略登录中，则使用忽略登录的用户信息
			if (IgnoreLoginUtils.isIgnoreLogin()) {
				user = IgnoreLoginUtils.getUserDetails();
			}
			entity.setCreateTime(calendar.getTime());
			entity.setModifyTime(calendar.getTime());
			entity.setCreator(user.getUserId());
			entity.setModifier(user.getUserId());
		} else {
			Calendar calendar = Calendar.getInstance();
			// 如果当前处于忽略登录中，则使用忽略登录的用户信息
			UserDetails user = SpringSecurityUtils.getCurrentUser();
			if (IgnoreLoginUtils.isIgnoreLogin()) {
				user = IgnoreLoginUtils.getUserDetails();
			}
			entity.setModifyTime(calendar.getTime());
			entity.setModifier(user.getUserId());
		}
	}

	/**
	 * Before *SimpleHibernateDao.saveAll
	 * 
	 * @param entity
	 */
	@Before("execution(* com.wellsoft.pt.core.dao.hibernate.SimpleHibernateDao.saveAll(..)) && args(entities)")
	public void beforeSaveAllModels(JoinPoint jp, Collection<? extends BaseEntity> entities) {
		for (BaseEntity entity : entities) {
			if (StringUtils.isBlank(entity.getUuid())) {
				Calendar calendar = Calendar.getInstance();
				UserDetails user = SpringSecurityUtils.getCurrentUser();
				// 如果当前处于忽略登录中，则使用忽略登录的用户信息
				if (IgnoreLoginUtils.isIgnoreLogin()) {
					user = IgnoreLoginUtils.getUserDetails();
				}
				entity.setCreateTime(calendar.getTime());
				entity.setModifyTime(calendar.getTime());
				entity.setCreator(user.getUserId());
				entity.setModifier(user.getUserId());
			} else {
				Calendar calendar = Calendar.getInstance();
				UserDetails user = SpringSecurityUtils.getCurrentUser();
				// 如果当前处于忽略登录中，则使用忽略登录的用户信息
				if (IgnoreLoginUtils.isIgnoreLogin()) {
					user = IgnoreLoginUtils.getUserDetails();
				}
				entity.setModifyTime(calendar.getTime());
				entity.setModifier(user.getUserId());
			}
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
