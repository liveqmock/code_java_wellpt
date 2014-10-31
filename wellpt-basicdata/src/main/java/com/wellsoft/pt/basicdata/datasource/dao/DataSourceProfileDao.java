/*
 * @(#)2014-7-31 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.datasource.dao;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceProfile;
import com.wellsoft.pt.core.dao.hibernate.HibernateDao;

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
public class DataSourceProfileDao extends HibernateDao<DataSourceProfile, String> {

	public DataSourceProfile getById(String id) {
		return findUniqueBy("id", id);
	}

	public DataSourceProfile getByUuid(String uuid) {
		return findUniqueBy("uuid", uuid);
	}

	public DataSourceProfile getByProId(String id) {
		return findUniqueBy("dataSourceProfileId", id);
	}
}
