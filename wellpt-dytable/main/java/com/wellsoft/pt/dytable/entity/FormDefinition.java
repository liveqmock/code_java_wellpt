/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.entity;

import java.sql.Clob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;

/**
 * 
* @ClassName: FormDefinition
* @Description: FormDefinition对应着一个动态表单
* @author lilin
 */
@Entity
@Table(name = "dytable_form_definition")
@DynamicUpdate
@DynamicInsert
public class FormDefinition extends IdEntity {
	//表名
	private String name;
	//显示名称
	private String descname;
	//表单属性id
	private String id;
	//表单编号
	private String tableNum;
	//表单显示形式 ： 两种 一种是可编辑展示、 一种是直接展示文本
	private String formDisplay;
	//显示单据的名称
	private String showTableModel;
	//显示单据对应的表单uuid
	private String showTableModelId;
	// 应用于
	private String applyTo;
	//打印模板的ID
	private String printTemplate;
	//打印模板的名称
	private String printTemplateName;
	//文件名 
	private String fileName;
	//描述
	private String desp;
	//html路径 用于存放html在内容管理系统中的路径
	private String htmlPath;
	//html body内容
	@UnCloneable
	private Clob htmlBodyContent;
	//版本 ,形式：1.0
	private String version;
	//模块ID
	private String moduleId;
	//模块名
	private String moduleName;
	//是否启用表单签名
	private String formSign;

	@UnCloneable
	private Set<FieldDefinition> fieldDefinitions = new HashSet<FieldDefinition>(0);

	/** default constructor */
	public FormDefinition() {
	}

	/**
	 * @return the formSign
	 */
	public String getFormSign() {
		return formSign;
	}

	/**
	 * @param formSign 要设置的formSign
	 */
	public void setFormSign(String formSign) {
		this.formSign = formSign;
	}

	/**
	 * @return the showTableModel
	 */
	public String getShowTableModel() {
		return showTableModel;
	}

	/**
	 * @param showTableModel 要设置的showTableModel
	 */
	public void setShowTableModel(String showTableModel) {
		this.showTableModel = showTableModel;
	}

	/**
	 * @return the showTableModelId
	 */
	public String getShowTableModelId() {
		return showTableModelId;
	}

	/**
	 * @param showTableModelId 要设置的showTableModelId
	 */
	public void setShowTableModelId(String showTableModelId) {
		this.showTableModelId = showTableModelId;
	}

	/**
	 * @return the formDisplay
	 */
	public String getFormDisplay() {
		return formDisplay;
	}

	/**
	 * @param formDisplay 要设置的formDisplay
	 */
	public void setFormDisplay(String formDisplay) {
		this.formDisplay = formDisplay;
	}

	/**
	 * @return the tableNum
	 */
	public String getTableNum() {
		return tableNum;
	}

	/**
	 * @param tableNum 要设置的tableNum
	 */
	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName 要设置的moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "descname", length = 100)
	public String getDescname() {
		return this.descname;
	}

	public void setDescname(String descname) {
		this.descname = descname;
	}

	@Column(name = "desp", length = 500)
	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	/**
	 * @return the fieldDefinitions
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formDefinition")
	@Cascade(CascadeType.ALL)
	public Set<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	/**
	 * @param fieldDefinitions 要设置的fieldDefinitions
	 */
	public void setFieldDefinitions(Set<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public Clob getHtmlBodyContent() {
		return htmlBodyContent;
	}

	public void setHtmlBodyContent(Clob htmlBodyContent) {
		this.htmlBodyContent = htmlBodyContent;
	}

	@Column(name = "version", length = 50)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the printTemplate
	 */
	public String getPrintTemplate() {
		return printTemplate;
	}

	/**
	 * @param printTemplate 要设置的printTemplate
	 */
	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}

	/**
	 * @return the printTemplateName
	 */
	public String getPrintTemplateName() {
		return printTemplateName;
	}

	/**
	 * @param printTemplateName 要设置的printTemplateName
	 */
	public void setPrintTemplateName(String printTemplateName) {
		this.printTemplateName = printTemplateName;
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

}