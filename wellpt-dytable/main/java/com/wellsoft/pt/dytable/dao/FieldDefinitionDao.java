/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dytable.entity.FieldDefinition;

/**
 * Description: 字段定义操作DAO
 *  
 * @author jiangmb
 * @date 2012-12-21
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-21.1	jiangmb		2012-12-21		Create
 * </pre>
 *
 */
@Repository
public class FieldDefinitionDao extends HibernateDao<FieldDefinition, String> {
	private static final String QUERY_FIELD_BY_FORMID = "select f from FieldDefinition f  where f.entityName=?";
	private static final String DELETE_FIELD_BY_FORMID = "delete f from FieldDefinition f  where f.sysType=?";
	private static final String QUERY_LIST_BY_TABLE_ID = "from FieldDefinition field_definition where field_definition.formDefinition.uuid = ?";
	private static final String QUERY_LIST_BY_NAME_VERSION = "from FieldDefinition a where a.formDefinition.uuid in (select b.uuid from FormDefinition b where b.name = ? and b.version = ?)";

	/**
	 * 根据表名查找此表对应的字段列表
	 * 
	 * @param entityName
	 * @return
	 */
	public List<FieldDefinition> getFiledByEntityName(String entityName) {
		return this.find(QUERY_FIELD_BY_FORMID, entityName);
	}

	/**
	 * 删除指定表名的全部字段信息
	 * 
	 * @param entityName
	 */
	public void deleteAllByEntityName(String entityName) {
		this.batchExecute(DELETE_FIELD_BY_FORMID, entityName);
	}

	/**
	 * 通过表uuid查找此表对应的字段列表
	 * 
	 * @param tableId
	 * @return
	 */
	public List<FieldDefinition> getFieldListByTableId(String tableId) {
		return this.find(QUERY_LIST_BY_TABLE_ID, tableId);
	}

	/**
	 * 通过表名查找此表对指定版本的字段列表信息
	 * 
	 * @param formName
	 * @param version
	 * @return
	 */
	public List<FieldDefinition> getListByForm(String formName, String version) {
		return this.find(QUERY_LIST_BY_NAME_VERSION, formName, version);
	}
}
