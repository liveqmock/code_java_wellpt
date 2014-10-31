/*
 * @(#)2014-7-31 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceDefinition;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.org.entity.User;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-7-31
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-7-31.1	wubin		2014-7-31		Create
 * </pre>
 *
 */
@Repository
public class DataSourceDefinitionDao extends HibernateDao<DataSourceDefinition, String> {

	public DataSourceDefinition getById(String id) {
		return findUniqueBy("dataSourceId", id);
	}

	public DataSourceDefinition getByJqGridId(String id) {
		return findUniqueBy("id", id);
	}

	public DataSourceDefinition getByUuid(String uuid) {
		return findUniqueBy("uuid", uuid);
	}

	public List<QueryItem> getByHql(String hql) {
		List<User> users = this.find("from User t  where 1=1", new HashMap<String, Object>());
		return null;
	}
}
