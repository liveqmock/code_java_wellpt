/*
 * @(#)2012-11-26 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 如何描述该类
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
@Entity
@Table(name = "dytable_field_check_rule")
@DynamicUpdate
@DynamicInsert
public class FieldCheckRule extends IdEntity {
	private static final long serialVersionUID = -1028615478800405458L;

	//校验规则值 1.不允许为空 2.必须是URL 3.必须是邮箱地址 4.必须是身份证 5.要求唯一
	private String value;
	//上面值对应的中文说明
	private String label;
	//5.要求唯一 这个校验规则需求提供校验规则语句
	private String checkRule;

	@UnCloneable
	private FieldDefinition fieldDefinition;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCheckRule() {
		return checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "field_definition_uid")
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	public void setFieldDefinition(FieldDefinition fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}
}
