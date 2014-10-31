/*
 * @(#)2013-3-22 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * Description: 查询定义类
 *  
 * @author wubin
 * @date 2013-3-22
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-22.1	wubin		2013-3-22		Create
 * </pre>
 *
 */
@Entity
@Table(name = "view_select_definition")
@DynamicUpdate
@DynamicInsert
public class SelectDefinitionNew extends IdEntity {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 788898119388427742L;

	private Boolean forCondition; //是否按条件查询 

	private Boolean forKeySelect; //是否按关键字查询

	private Boolean forFieldSelect;//是否按字段查询

	@UnCloneable
	private Set<FieldSelect> fieldSelects; //要查询的字段
	@UnCloneable
	private Set<ConditionTypeNew> conditionTypeNew; //查询的条件选项

	@UnCloneable
	private ViewDefinitionNew viewDefinitionNew; // 所属的视图

	/**
	 * @return the forFieldSelect
	 */
	public Boolean getForFieldSelect() {
		return forFieldSelect;
	}

	/**
	 * @param forFieldSelect 要设置的forFieldSelect
	 */
	public void setForFieldSelect(Boolean forFieldSelect) {
		this.forFieldSelect = forFieldSelect;
	}

	/**
	 * @return the fieldSelects
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selectDefinitionNew")
	@Cascade({ CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	public Set<FieldSelect> getFieldSelects() {
		return fieldSelects;
	}

	/**
	 * @param fieldSelects 要设置的fieldSelects
	 */
	public void setFieldSelects(Set<FieldSelect> fieldSelects) {
		this.fieldSelects = fieldSelects;
	}

	/**
	 * @return the conditionTypeNew
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selectDefinitionNew")
	@Cascade({ CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@OrderBy("sortOrder asc")
	public Set<ConditionTypeNew> getConditionTypeNew() {
		return conditionTypeNew;
	}

	/**
	 * @param conditionTypeNew 要设置的conditionTypeNew
	 */
	public void setConditionTypeNew(Set<ConditionTypeNew> conditionTypeNew) {
		this.conditionTypeNew = conditionTypeNew;
	}

	/**
	 * @return the forCondition
	 */
	public Boolean getForCondition() {
		return forCondition;
	}

	/**
	 * @param forCondition 要设置的forCondition
	 */
	public void setForCondition(Boolean forCondition) {
		this.forCondition = forCondition;
	}

	/**
	 * @return the forKeySelect
	 */
	public Boolean getForKeySelect() {
		return forKeySelect;
	}

	/**
	 * @param forKeySelect 要设置的forKeySelect
	 */
	public void setForKeySelect(Boolean forKeySelect) {
		this.forKeySelect = forKeySelect;
	}

	/**
	 * @return the viewDefinitionNew
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "view_def_uuid", nullable = false)
	public ViewDefinitionNew getViewDefinitionNew() {
		return viewDefinitionNew;
	}

	/**
	 * @param viewDefinitionNew 要设置的viewDefinitionNew
	 */
	public void setViewDefinitionNew(ViewDefinitionNew viewDefinitionNew) {
		this.viewDefinitionNew = viewDefinitionNew;
	}

}
