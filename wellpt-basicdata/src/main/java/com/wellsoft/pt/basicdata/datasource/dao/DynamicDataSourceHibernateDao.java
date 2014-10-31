/*
 * @(#)2014-8-7 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-8-7
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-7.1	wubin		2014-8-7		Create
 * </pre>
 *
 */
@Component
public class DynamicDataSourceHibernateDao {
	/**
	 * 通过单例类configuration获取的sessionfactory
	 */
	private SessionFactory sessionFactory;

	/**
	 * 
	* @Title: getSessionFactory
	* @Description: 获取sessionfactory
	* @param @return    设定文件
	* @return SessionFactory    返回类型
	* @throws
	 */
	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = ApplicationContextHolder.getBean(Config.TENANT_SESSION_FACTORY_BEAN_NAME,
					SessionFactory.class);
		}
		return sessionFactory;
	}

	/**
	 * 
	* @Title: setSessionFactory
	* @Description:设置sessionfactory
	* @param @param sessionFactory    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	* @Title: getSession
	* @Description: h
	* @param @return    设定文件
	* @return Session    返回类型
	* @throws
	 */
	public Session getSession() {
		sessionFactory = getSessionFactory();
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * 
	* @Title: flush
	* @Description: Flush当前Session.
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void flush() {
		getSession().flush();
	}

	/**
	 * 
	 * 获得所有的系统表
	 * 
	 * @return
	 */
	public List<Map> getAllDataBaseTables() {
		List tableList = null;
		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			tableList = s.createSQLQuery(
					"select t.OBJECT_NAME,t.OBJECT_ID  from user_objects t where object_type='TABLE'").list();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableList;
	}

	/**
	 * 
	 * 获得所有的系统表
	 * 
	 * @return
	 */
	public List<Map> getAllDataBaseViews() {
		List viewList = null;
		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			viewList = s.createSQLQuery(
					"select t.OBJECT_NAME,t.OBJECT_ID  from user_objects t where object_type='VIEW'").list();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewList;
	}

	/**
	 * 
	 * 获得一张系统表的所有字段
	 * 
	 * @param tableName
	 * @return
	 */
	public List<Map> getColumnsByTables(String tableName) {
		List columnList = null;
		try {
			Session s = getSession();
			Transaction t = s.beginTransaction();
			columnList = s
					.createSQLQuery(
							"select t.COLUMN_NAME,t.DATA_TYPE from user_tab_columns t where t.TABLE_NAME = '"
									+ tableName + "'").list();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnList;
	}
}
