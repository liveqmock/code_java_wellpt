/*
 * @(#)2013-2-25 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-25.1	zhulh		2013-2-25		Create
 * </pre>
 * 
 */
@Entity
@Table(name = "dytable_form_relation")
@DynamicUpdate
@DynamicInsert
public class FormRelation extends IdEntity {

	private static final long serialVersionUID = 7430369185953655245L;
	// 主表
	private String mainFormUuid;
	// 子表
	private String subFormUuid;
	// html模板中从表table标签的id属性值
	private String id;
	// 编辑模式1.行内编辑 2.弹出窗口编辑
	private String editMode;
	// 是否隐藏操作按钮 1 隐藏 2 不隐藏
	private String hideButtons;

	//显示的标题
	private String tableTitle;
	//从表表单展开或者折叠
	private String tableOpen;

	//从表分组展示的标题
	private String groupShowTitle;
	//从表是否进行分组展示
	private String isGroupShowTitle;

	//从表映射关系的(主表字段对应选中的从表的某一字段,允许添加多个)
	private String subrRelationDataDefiantion;
	//从表映射关系的对应的表
	private String subformApplyTableId;

	// 字段关系
	@UnCloneable
	private Set<FieldRelation> fieldRelations = new HashSet<FieldRelation>(0);

	/**
	 * @return the groupShowTitle
	 */
	public String getGroupShowTitle() {
		return groupShowTitle;
	}

	/**
	 * @param groupShowTitle 要设置的groupShowTitle
	 */
	public void setGroupShowTitle(String groupShowTitle) {
		this.groupShowTitle = groupShowTitle;
	}

	/**
	 * @return the isGroupShowTitle
	 */
	public String getIsGroupShowTitle() {
		return isGroupShowTitle;
	}

	/**
	 * @param isGroupShowTitle 要设置的isGroupShowTitle
	 */
	public void setIsGroupShowTitle(String isGroupShowTitle) {
		this.isGroupShowTitle = isGroupShowTitle;
	}

	/**
	 * @return the subrRelationDataDefiantion
	 */
	public String getSubrRelationDataDefiantion() {
		return subrRelationDataDefiantion;
	}

	/**
	 * @param subrRelationDataDefiantion 要设置的subrRelationDataDefiantion
	 */
	public void setSubrRelationDataDefiantion(String subrRelationDataDefiantion) {
		this.subrRelationDataDefiantion = subrRelationDataDefiantion;
	}

	/**
	 * @return the subformApplyTableId
	 */
	public String getSubformApplyTableId() {
		return subformApplyTableId;
	}

	/**
	 * @param subformApplyTableId 要设置的subformApplyTableId
	 */
	public void setSubformApplyTableId(String subformApplyTableId) {
		this.subformApplyTableId = subformApplyTableId;
	}

	/**
	 * @return the tableTitle
	 */
	public String getTableTitle() {
		return tableTitle;
	}

	/**
	 * @param tableTitle 要设置的tableTitle
	 */
	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	/**
	 * @return the tableOpen
	 */
	public String getTableOpen() {
		return tableOpen;
	}

	/**
	 * @param tableOpen 要设置的tableOpen
	 */
	public void setTableOpen(String tableOpen) {
		this.tableOpen = tableOpen;
	}

	/**
	 * @return the mainFormUuid
	 */
	public String getMainFormUuid() {
		return mainFormUuid;
	}

	/**
	 * @param mainFormUuid
	 *            要设置的mainFormUuid
	 */
	public void setMainFormUuid(String mainFormUuid) {
		this.mainFormUuid = mainFormUuid;
	}

	/**
	 * @return the subFormUuid
	 */
	public String getSubFormUuid() {
		return subFormUuid;
	}

	/**
	 * @param subFormUuid
	 *            要设置的subFormUuid
	 */
	public void setSubFormUuid(String subFormUuid) {
		this.subFormUuid = subFormUuid;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the editMode
	 */
	public String getEditMode() {
		return editMode;
	}

	/**
	 * @param editMode
	 *            要设置的editMode
	 */
	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}

	public String getHideButtons() {
		return hideButtons;
	}

	public void setHideButtons(String hideButtons) {
		this.hideButtons = hideButtons;
	}

	/**
	 * @return the fieldRelations
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formRelation")
	@Cascade({ CascadeType.ALL })
	@OrderBy("fieldOrder asc")
	public Set<FieldRelation> getFieldRelations() {
		return fieldRelations;
	}

	/**
	 * @param fieldRelations
	 *            要设置的fieldRelations
	 */
	public void setFieldRelations(Set<FieldRelation> fieldRelations) {
		this.fieldRelations = fieldRelations;
	}

}
