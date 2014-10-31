/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wellsoft.pt.common.enums.Separator;

/**
 * Description: 自定义表单配置信息，前端json数据对应后端类
 *  
 * @author jiangmb
 * @date 2012-10-30
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-30.1	jiangmb		2012-10-30		Create
 * </pre>
 *
 */
@SuppressWarnings("serial")
public class TableInfoBean implements Serializable {
	//表信息uuid
	private String uuid;
	//表名
	private String tableName;
	//表id
	private String id;
	//表单编号
	private String tableNum;
	//表单显示形式 、 两种 一种是可编辑 一种是直接显示
	private String formDisplay;
	//显示单据的名称
	private String showTableModel;
	//显示单据对应的表单uuid
	private String showTableModelId;
	//应用于
	private String applyTo;
	//打印模板
	private String printTemplate;
	//打印模板的名称
	private String printTemplateName;
	//表中文名
	private String tableDesc;
	//模块ID
	private String moduleId;
	//模块ID
	private String moduleName;
	//当前版本
	private String version;
	//是否升级1.是 0.否
	private String isUp;
	//html存储路径
	private String htmlPath;
	//html body内容
	private String htmlBodyContent;
	//上传html 的路径
	private String tempHtmlPath;
	//是否启用表单签名
	private String formSign;
	//字段信息列表
	private List<ColInfoBean> fields = new ArrayList<ColInfoBean>(0);

	private Map<String, String> fieldNameMappingMap;

	private Map<String, String> fieldTypeMappingMap;

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 要设置的id
	 */
	public void setId(String id) {
		this.id = id;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

	public List<ColInfoBean> getFields() {
		return fields;
	}

	public void setFields(List<ColInfoBean> fields) {
		this.fields = fields;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getHtmlBodyContent() {
		return htmlBodyContent;
	}

	public void setHtmlBodyContent(String htmlBodyContent) {
		this.htmlBodyContent = htmlBodyContent;
	}

	public String getTempHtmlPath() {
		return tempHtmlPath;
	}

	public void setTempHtmlPath(String tempHtmlPath) {
		this.tempHtmlPath = tempHtmlPath;
	}

	public String getFieldName(String fieldMappingName) {
		if (fieldNameMappingMap == null) {
			fieldNameMappingMap = new HashMap<String, String>();
			for (ColInfoBean field : fields) {
				String fieldName = field.getColEnName();
				fieldNameMappingMap.put(fieldName, fieldName);
				if (StringUtils.isNotBlank(field.getApplyTo())) {
					String[] tos = StringUtils.split(field.getApplyTo(), Separator.SEMICOLON.getValue());
					for (String to : tos) {
						fieldNameMappingMap.put(to, fieldName);
					}
				}
			}
		}

		return fieldNameMappingMap.get(fieldMappingName);
	}

	public String getFieldType(String fieldName) {
		if (fieldTypeMappingMap == null) {
			fieldTypeMappingMap = new HashMap<String, String>();
			for (ColInfoBean field : fields) {
				String fieldname = field.getColEnName();
				String fieldType = field.getInputDataType();
				fieldTypeMappingMap.put(fieldname, fieldType);
				if (StringUtils.isNotBlank(field.getApplyTo())) {
					String[] tos = StringUtils.split(field.getApplyTo(), Separator.COMMA.getValue());
					for (String to : tos) {
						fieldTypeMappingMap.put(to, fieldType);
					}
				}
			}
		}

		return fieldTypeMappingMap.get(fieldName);
	}
}
