/*
 * @(#)2013-2-25 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
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
 * @author zhulh
 * @date 2013-2-25
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-25.1	zhulh		2013-2-25		Create
 * </pre>
 *
 */
@Entity
@Table(name = "dytable_field_relation")
@DynamicUpdate
@DynamicInsert
public class FieldRelation extends IdEntity {

	private static final long serialVersionUID = -3901445338976991685L;
	//html表头名称
	private String headerName;
	//字段信息
	private String fieldUuid;
	//字段顺序
	private Integer fieldOrder;

	//数据来源表的字段的fieldUuid
	private String uuid2;

	private String subColHidden;

	private String subColEdit;

	private String fieldWidth;

	//表关系
	@UnCloneable
	private FormRelation formRelation;

	/**
	 * @return the subColEdit
	 */
	public String getSubColEdit() {
		return subColEdit;
	}

	/**
	 * @param subColEdit 要设置的subColEdit
	 */
	public void setSubColEdit(String subColEdit) {
		this.subColEdit = subColEdit;
	}

	/**
	 * @return the fieldWidth
	 */
	public String getFieldWidth() {
		return fieldWidth;
	}

	/**
	 * @param fieldWidth 要设置的fieldWidth
	 */
	public void setFieldWidth(String fieldWidth) {
		this.fieldWidth = fieldWidth;
	}

	/**
	 * @return the uuid2
	 */
	public String getUuid2() {
		return uuid2;
	}

	/**
	 * @param uuid2 要设置的uuid2
	 */
	public void setUuid2(String uuid2) {
		this.uuid2 = uuid2;
	}

	/**
	 * @return the subColHidden
	 */
	public String getSubColHidden() {
		return subColHidden;
	}

	/**
	 * @param subColHidden 要设置的subColHidden
	 */
	public void setSubColHidden(String subColHidden) {
		this.subColHidden = subColHidden;
	}

	/**
	 * @return the headerName
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * @param headerName 要设置的headerName
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/**
	 * @return the fieldUuid
	 */
	public String getFieldUuid() {
		return fieldUuid;
	}

	/**
	 * @param fieldUuid 要设置的fieldUuid
	 */
	public void setFieldUuid(String fieldUuid) {
		this.fieldUuid = fieldUuid;
	}

	/**
	 * @return the fieldOrder
	 */
	public Integer getFieldOrder() {
		return fieldOrder;
	}

	/**
	 * @param fieldOrder 要设置的fieldOrder
	 */
	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	/**
	 * @return the formRelation
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_relation_uuid")
	public FormRelation getFormRelation() {
		return formRelation;
	}

	/**
	 * @param formRelation 要设置的formRelation
	 */
	public void setFormRelation(FormRelation formRelation) {
		this.formRelation = formRelation;
	}

}
