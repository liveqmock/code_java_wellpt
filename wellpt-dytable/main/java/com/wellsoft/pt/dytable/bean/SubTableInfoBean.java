/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 自定义表单配置信息，前端json数据对应后端类
 * 
 * @author jiangmb
 * @date 2012-10-30
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-30.1	jiangmb		2012-10-30		Create
 * </pre>
 * 
 */
@SuppressWarnings("serial")
public class SubTableInfoBean implements Serializable {
	// 表关系UUID
	private String formRelationUuid;
	// html模板中从表table标签的id属性值
	private String id;
	//从表应用于
	private String applyTo;
	// 子表uuid
	private String tableId;
	// 子表表名
	private String tableName;
	// 子表显示名
	private String descTableName;
	//显示的标题
	private String tableTitle;
	//从表分组展示的标题
	private String groupShowTitle;
	//从表是否进行分组展示
	private String isGroupShowTitle;
	//从表表单展开或者折叠
	private String tableOpen;

	//从表映射关系的(主表字段对应选中的从表的某一字段,允许添加多个)
	private String subrRelationDataDefiantion;
	//从表映射关系的对应的表
	private String subformApplyTableId;
	// 编辑类型
	private String editType;
	// 是否隐藏操作按钮 1 隐藏 2不隐藏
	private String hideButtons;
	// html body内容
	private String htmlBodyContent;
	// html表到子表字段映射列表
	private List<ColInfoBean> fields = new ArrayList<ColInfoBean>(0);

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
	 * @return the descTableName
	 */
	public String getDescTableName() {
		return descTableName;
	}

	/**
	 * @param descTableName 要设置的descTableName
	 */
	public void setDescTableName(String descTableName) {
		this.descTableName = descTableName;
	}

	/**
	 * @return the applyTo
	 */
	public String getApplyTo() {
		return applyTo;
	}

	/**
	 * @param applyTo 要设置的applyTo
	 */
	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	/**
	 * @return the formRelationUuid
	 */
	public String getFormRelationUuid() {
		return formRelationUuid;
	}

	/**
	 * @param formRelationUuid
	 *            要设置的formRelationUuid
	 */
	public void setFormRelationUuid(String formRelationUuid) {
		this.formRelationUuid = formRelationUuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getHideButtons() {
		return hideButtons;
	}

	public void setHideButtons(String hideButtons) {
		this.hideButtons = hideButtons;
	}

	public List<ColInfoBean> getFields() {
		return fields;
	}

	public void setFields(List<ColInfoBean> fields) {
		this.fields = fields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getHtmlBodyContent() {
		return htmlBodyContent;
	}

	public void setHtmlBodyContent(String htmlBodyContent) {
		this.htmlBodyContent = htmlBodyContent;
	}
}
