/*
 * @(#)2012-11-22 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dytable.entity.FieldOption;

/**
 * Description: 字段选择项(select,radio,checkbox)操作类
 *  
 * @author jiangmb
 * @date 2012-11-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-22.1	jiangmb		2012-11-22		Create
 * </pre>
 *
 */
@Repository
public class FieldOptionDao extends HibernateDao<FieldOption, String> {
	private final static String DELETE_BY_FIELD_UID = "delete FieldOption a where a.fieldDefinition.uuid = ?";
	private final static String QUERY_BY_FIELD_UID = "from FieldOption where field_definition_uid = ? order by value";

	/**
	 * 通过字段uuid删除对应的此字段的供选列表
	 * 
	 * @param uid
	 */
	public void deleteByFieldUid(String uid) {
		this.batchExecute(DELETE_BY_FIELD_UID, uid);
	}

	/**
	 * 通过字段定义uuid查找此字段对应的供选列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<FieldOption> getListByFieldUid(String uid) {
		return this.find(QUERY_BY_FIELD_UID, uid);
	}
}
