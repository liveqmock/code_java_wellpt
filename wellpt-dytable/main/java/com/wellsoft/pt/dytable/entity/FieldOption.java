/*
 * @(#)2012-11-22 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.entity;

import javax.persistence.Column;
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
 * Description: 字段选择项(select,radio,checkbox)
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
@Entity
@Table(name = "dytable_field_option")
@DynamicUpdate
@DynamicInsert
public class FieldOption extends IdEntity {
	private static final long serialVersionUID = 8983139104306002551L;

	//供选项值
	private String value;
	//供选项说明
	private String text;

	@UnCloneable
	private FieldDefinition fieldDefinition;

	@Column(length = 50)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(length = 100)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
