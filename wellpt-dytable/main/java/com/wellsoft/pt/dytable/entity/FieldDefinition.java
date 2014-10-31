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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.wellsoft.pt.core.annotation.UnCloneable;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.dytable.support.FormUtils;

/**
 * 
* @ClassName: FieldDefinition
* @Description: field实体对应表单中的每一个字段，系统设定的表将通过配置文件一次导入
* @author lilin
 */
@Entity
@Table(name = "dytable_field_definition")
@DynamicUpdate
@DynamicInsert
public class FieldDefinition extends IdEntity {
	private static final long serialVersionUID = 7399630217195009421L;
	//字段定义
	private String fieldName;
	//对应的表名
	private String entityName;
	//描述名称
	private String descname;
	//字段类型
	private String type;
	//是否索引
	private boolean indexed;
	//是否界面表格显示
	private boolean showed;
	//是否排序
	private boolean sorted;
	//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
	private Integer sysType;
	//字段长度
	private Integer length;
	//浮点数的计算结果保留位数
	private Integer floatSet;
	//树形下拉框调用的服务层
	private String serviceName;
	//树形下拉框调用的服务层的方法
	private String methodName;
	//树形下拉框调用的服务层的方法的请求参数
	private String data;
	//可编辑流水号定义:指定的流水号ID
	private String designatedId;
	//可编辑流水号定义:指定的流水号分类
	private String designatedType;
	//可编辑流水号定义:是否覆盖指针
	private String isOverride;
	//可编辑流水号定义：是否保存进数据库
	private String isSaveDb;
	//不可编辑流水号定义：指定的流水号ID
	private String unEditDesignatedId;
	//不可编辑流水号定义：是否保存进数据库
	private String unEditIsSaveDb;
	//附件上传样式
	private String uploadFileType;
	//附件上传是否正文
	private String isGetZhengWen;
	//字段展现形式
	private String dataShow;
	//应用于
	private String applyTo;
	//字段的默认值
	private String defaultValue;
	//字段的大小
	private String fontSize;
	//字段的颜色
	private String fontColor;
	//只读状态下设置跳转的url
	private String setUrlOnlyRead;
	//js函数定义
	private String jsFunction;
	//输入方式
	private String inputType;
	//radio,checkbox,select供选项来源 1.常量 2.字典
	private Integer optionDataSource;
	//checkbox、radio 控制隐藏字段
	private String ctrlField;
	//textarea行
	private Integer textareaRow;
	//textarea列
	private Integer textareaCol;

	//宽度
	private String fontWidth;
	//高度
	private String fontHight;
	//对齐方式
	private String textAlign;
	//是否隐藏
	private String isHide;
	//默认值类型
	private String defaultValueWay;
	//输入样式
	private String inputDataType;
	//关联数据类型
	private String relationDataTwoSql;
	//关联数据源显示值
	private String relationDataText;
	//关联数据源隐藏值
	private String relationDataValue;
	//关联数据约束条件
	private String relationDataSql;
	//关联数据的展示方式
	private String relationDataShowMethod;
	//关联数据显示方式
	private String relationDataShowType;

	private String relationDataTextTwo;

	private String relationDataValueTwo;

	private Clob relationDataDefiantion;
	@UnCloneable
	private FormDefinition formDefinition;

	@UnCloneable
	private Set<FieldOption> fieldOptions = new HashSet<FieldOption>(0);

	@UnCloneable
	private Set<FieldCheckRule> fieldCheckRules = new HashSet<FieldCheckRule>(0);

	public FieldDefinition() {
	}

	/**
	 * @return the unEditDesignatedId
	 */
	public String getUnEditDesignatedId() {
		return unEditDesignatedId;
	}

	/**
	 * @param unEditDesignatedId 要设置的unEditDesignatedId
	 */
	public void setUnEditDesignatedId(String unEditDesignatedId) {
		this.unEditDesignatedId = unEditDesignatedId;
	}

	/**
	 * @return the unEditIsSaveDb
	 */
	public String getUnEditIsSaveDb() {
		return unEditIsSaveDb;
	}

	/**
	 * @param unEditIsSaveDb 要设置的unEditIsSaveDb
	 */
	public void setUnEditIsSaveDb(String unEditIsSaveDb) {
		this.unEditIsSaveDb = unEditIsSaveDb;
	}

	/**
	 * @return the isSaveDb
	 */
	public String getIsSaveDb() {
		return isSaveDb;
	}

	/**
	 * @param isSaveDb 要设置的isSaveDb
	 */
	public void setIsSaveDb(String isSaveDb) {
		this.isSaveDb = isSaveDb;
	}

	/**
	 * @return the setUrlOnlyRead
	 */
	public String getSetUrlOnlyRead() {
		return setUrlOnlyRead;
	}

	/**
	 * @param setUrlOnlyRead 要设置的setUrlOnlyRead
	 */
	public void setSetUrlOnlyRead(String setUrlOnlyRead) {
		this.setUrlOnlyRead = setUrlOnlyRead;
	}

	/**
	 * @return the relationDataShowMethod
	 */
	public String getRelationDataShowMethod() {
		return relationDataShowMethod;
	}

	/**
	 * @param relationDataShowMethod 要设置的relationDataShowMethod
	 */
	public void setRelationDataShowMethod(String relationDataShowMethod) {
		this.relationDataShowMethod = relationDataShowMethod;
	}

	/**
	 * @return the ctrlField
	 */
	public String getCtrlField() {
		return ctrlField;
	}

	/**
	 * @param ctrlField 要设置的ctrlField
	 */
	public void setCtrlField(String ctrlField) {
		this.ctrlField = ctrlField;
	}

	/**
	 * @return the floatSet
	 */
	public Integer getFloatSet() {
		return floatSet;
	}

	/**
	 * @param floatSet 要设置的floatSet
	 */
	public void setFloatSet(Integer floatSet) {
		this.floatSet = floatSet;
	}

	/**
	 * @return the jsFunction
	 */
	public String getJsFunction() {
		return jsFunction;
	}

	/**
	 * @param jsFunction 要设置的jsFunction
	 */
	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	/**
	 * @return the fontSize
	 */
	public String getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize 要设置的fontSize
	 */
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor 要设置的fontColor
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue 要设置的defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the designatedId
	 */
	public String getDesignatedId() {
		return designatedId;
	}

	/**
	 * @param designatedId 要设置的designatedId
	 */
	public void setDesignatedId(String designatedId) {
		this.designatedId = designatedId;
	}

	/**
	 * @return the designatedType
	 */
	public String getDesignatedType() {
		return designatedType;
	}

	/**
	 * @param designatedType 要设置的designatedType
	 */
	public void setDesignatedType(String designatedType) {
		this.designatedType = designatedType;
	}

	/**
	 * @return the isOverride
	 */
	public String getIsOverride() {
		return isOverride;
	}

	/**
	 * @param isOverride 要设置的isOverride
	 */
	public void setIsOverride(String isOverride) {
		this.isOverride = isOverride;
	}

	@Column(name = "fieldname", length = 50)
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * @return the isGetZhengWen
	 */
	public String getIsGetZhengWen() {
		return isGetZhengWen;
	}

	/**
	 * @param isGetZhengWen 要设置的isGetZhengWen
	 */
	public void setIsGetZhengWen(String isGetZhengWen) {
		this.isGetZhengWen = isGetZhengWen;
	}

	/**
	 * @return the uploadFileType
	 */
	public String getUploadFileType() {
		return uploadFileType;
	}

	/**
	 * @param uploadFileType 要设置的uploadFileType
	 */
	public void setUploadFileType(String uploadFileType) {
		this.uploadFileType = uploadFileType;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName 要设置的serviceName
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName 要设置的methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data 要设置的data
	 */
	public void setData(String data) {
		this.data = data;
	}

	public void setFieldName(String fieldname) {
		this.fieldName = fieldname;
	}

	@Column(name = "descname", length = 50)
	public String getDescname() {
		return this.descname;
	}

	public void setDescname(String descname) {
		this.descname = descname;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * @return the dataShow
	 */
	public String getDataShow() {
		return dataShow;
	}

	/**
	 * @param dataShow 要设置的dataShow
	 */
	public void setDataShow(String dataShow) {
		this.dataShow = dataShow;
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

	@Column(length = 50)
	public String getInputType() {
		return inputType;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public boolean isShowed() {
		return showed;
	}

	public void setShowed(boolean showed) {
		this.showed = showed;
	}

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Integer getOptionDataSource() {
		return optionDataSource;
	}

	public void setOptionDataSource(Integer optionDataSource) {
		this.optionDataSource = optionDataSource;
	}

	public Integer getTextareaRow() {
		return textareaRow;
	}

	public void setTextareaRow(Integer textareaRow) {
		this.textareaRow = textareaRow;
	}

	public Integer getTextareaCol() {
		return textareaCol;
	}

	public void setTextareaCol(Integer textareaCol) {
		this.textareaCol = textareaCol;
	}

	/**
	 * @return the formDefinition
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_uuid")
	public FormDefinition getFormDefinition() {
		return formDefinition;
	}

	/**
	 * @param formDefinition 要设置的formDefinition
	 */
	public void setFormDefinition(FormDefinition formDefinition) {
		this.formDefinition = formDefinition;
	}

	/**
	 * @return the fieldOptions
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldDefinition")
	@Cascade({ CascadeType.ALL })
	@OrderBy("value asc")
	public Set<FieldOption> getFieldOptions() {
		return fieldOptions;
	}

	/**
	 * @param fieldOptions 要设置的fieldOptions
	 */
	public void setFieldOptions(Set<FieldOption> fieldOptions) {
		this.fieldOptions = fieldOptions;
	}

	/**
	 * @return the fieldCheckRules
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldDefinition")
	@Cascade({ CascadeType.ALL })
	@OrderBy("value asc")
	public Set<FieldCheckRule> getFieldCheckRules() {
		return fieldCheckRules;
	}

	/**
	 * @param fieldCheckRules 要设置的fieldCheckRules
	 */
	public void setFieldCheckRules(Set<FieldCheckRule> fieldCheckRules) {
		this.fieldCheckRules = fieldCheckRules;
	}

	public String getFontWidth() {
		return fontWidth;
	}

	public void setFontWidth(String fontWidth) {
		this.fontWidth = fontWidth;
	}

	public String getFontHight() {
		return fontHight;
	}

	public void setFontHight(String fontHight) {
		this.fontHight = fontHight;
	}

	public String getIsHide() {
		return isHide;
	}

	public void setIsHide(String isHide) {
		this.isHide = isHide;
	}

	public String getDefaultValueWay() {
		return defaultValueWay;
	}

	public void setDefaultValueWay(String defaultValueWay) {
		this.defaultValueWay = defaultValueWay;
	}

	public String getInputDataType() {
		return inputDataType;
	}

	public void setInputDataType(String inputDataType) {
		this.inputDataType = inputDataType;
	}

	public String getRelationDataTwoSql() {
		return relationDataTwoSql;
	}

	public void setRelationDataTwoSql(String relationDataTwoSql) {
		this.relationDataTwoSql = relationDataTwoSql;
	}

	public String getRelationDataText() {
		return relationDataText;
	}

	public void setRelationDataText(String relationDataText) {
		this.relationDataText = relationDataText;
	}

	public String getRelationDataValue() {
		return relationDataValue;
	}

	public void setRelationDataValue(String relationDataValue) {
		this.relationDataValue = relationDataValue;
	}

	public String getRelationDataSql() {
		return relationDataSql;
	}

	public void setRelationDataSQL(String relationDataSql) {
		this.relationDataSql = relationDataSql;
	}

	@Column(length = 2000)
	public String getRelationDataShowType() {
		return relationDataShowType;
	}

	public void setRelationDataShowType(String relationDataShowType) {
		this.relationDataShowType = relationDataShowType;
	}

	public String getRelationDataTextTwo() {
		return relationDataTextTwo;
	}

	public void setRelationDataTextTwo(String relationDataTextTwo) {
		this.relationDataTextTwo = relationDataTextTwo;
	}

	public String getRelationDataValueTwo() {
		return relationDataValueTwo;
	}

	public void setRelationDataValueTwo(String relationDataValueTwo) {
		this.relationDataValueTwo = relationDataValueTwo;
	}

	public Clob getRelationDataDefiantion() {
		return relationDataDefiantion;
	}

	public void setRelationDataDefiantion(Clob relationDataDefiantion) {
		this.relationDataDefiantion = relationDataDefiantion;
	}

	public void setRelationDataSql(String relationDataSql) {
		this.relationDataSql = relationDataSql;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

	@Transient
	public boolean isAttach() {
		return FormUtils.isAttach(this.inputType, this.fieldName);
	}

}