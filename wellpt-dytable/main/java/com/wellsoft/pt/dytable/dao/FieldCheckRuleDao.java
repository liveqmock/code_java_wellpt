/*
 * @(#)2012-11-26 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wellsoft.pt.core.dao.hibernate.HibernateDao;
import com.wellsoft.pt.dytable.entity.FieldCheckRule;

/**
 * Description: 字段校验规则操作DAO
 *  
 * @author jiangmb
 * @date 2012-11-26
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-26.1	jiangmb		2012-11-26		Create
 * </pre>
 *
 */
@Repository
public class FieldCheckRuleDao extends HibernateDao<FieldCheckRule, String> {
	private final static String DELETE_BY_FIELD_UID = "delete FieldCheckRule a where a.fieldDefinition.uuid = ?";
	private final static String QUERY_BY_FIELD_UID = "from FieldCheckRule where field_definition_uid = ?";
	
	/**
	 * 通过字段定义uuid删除对应的校验规则
	 * 
	 * @param uid
	 */
	public void deleteByFieldUid(String uid){
		this.batchExecute(DELETE_BY_FIELD_UID, uid);
	}
	/**
	 * 通过字段定义uuid获取对应的校验规则列表
	 * 
	 * @param uid
	 * @return
	 */
	public List<FieldCheckRule> getListByFieldUid(String uid){
		return this.find(QUERY_BY_FIELD_UID, uid);
	}
}
