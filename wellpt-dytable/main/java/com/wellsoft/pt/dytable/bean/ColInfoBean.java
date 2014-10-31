/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.Serializable;
import java.util.List;

import com.wellsoft.pt.common.bean.LabelValueBean;

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
public class ColInfoBean implements Serializable {
	//字段uuid
	private String uuid;
	//数据来源的uuid
	private String uuid2;
	//字段英文名
	private String colEnName;
	//字段中文名
	private String colCnName;
	//表头名称
	private String thCnName;
	//字段类型
	private String dataType;
	//字段长度
	private Integer dataLength;
	//浮点数的计算结果保留位数
	private Integer floatSet;
	//树形调用的serviceName
	private String serviceName;
	//树形调用的methodName
	private String methodName;
	//树形调用的data
	private String data;
	//可编辑流水号定义
	private String designatedId;
	//可编辑流水号定义
	private String designatedType;
	//可编辑流水号定义
	private String isOverride;
	//可编辑流水号定义：是否保存进数据库
	private String isSaveDb;
	//不可编辑流水号定义：指定的流水号ID
	private String unEditDesignatedId;
	//不可编辑流水号定义：是否保存进数据库
	private String unEditIsSaveDb;
	//附件上传的样式
	private String uploadFileType;
	//附件上传是否正文
	private String isGetZhengWen;
	//字段展示形式
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
	//数据输入模式
	private String inputMode;
	//textarea行
	private Integer rows;
	//textarea列
	private Integer cols;
	//是否可编辑
	private boolean isEdit = true;
	//是否隐藏
	private boolean isHidden = false;
	//供选项(select,radio,checkbox)
	private List<LabelValueBean> options;
	//校验规则
	private List<CheckRuleBean> checkRules;
	//radio,checkbox,select供选项来源 1.常量 2.字典
	private Integer optionDataSource;
	//checkbox、radio 控制隐藏字段
	private String ctrlField;

	private Integer fieldOrder;
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
	//关联数据显示方式
	private String relationDataShowType;
	//关联数据的展示方式
	private String relationDataShowMethod;

	private String relationDataTextTwo;

	private String relationDataValueTwo;

	private String relationDataDefiantion;
	//从表字段是否隐藏
	private String subColHidden;
	//从表字段是否可编辑
	private String subColEdit;
	//从表字段的宽度
	private String fieldWidth;

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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getColEnName() {
		return colEnName;
	}

	public void setColEnName(String colEnName) {
		this.colEnName = colEnName;
	}

	public String getColCnName() {
		return colCnName;
	}

	public void setColCnName(String colCnName) {
		this.colCnName = colCnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
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

	public String getInputMode() {
		return inputMode;
	}

	public void setInputMode(String inputMode) {
		this.inputMode = inputMode;
	}

	public List<LabelValueBean> getOptions() {
		return options;
	}

	public void setOptions(List<LabelValueBean> options) {
		this.options = options;
	}

	public List<CheckRuleBean> getCheckRules() {
		return checkRules;
	}

	public void setCheckRules(List<CheckRuleBean> checkRules) {
		this.checkRules = checkRules;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public Integer getOptionDataSource() {
		return optionDataSource;
	}

	public void setOptionDataSource(Integer optionDataSource) {
		this.optionDataSource = optionDataSource;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getThCnName() {
		return thCnName;
	}

	public void setThCnName(String thCnName) {
		this.thCnName = thCnName;
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

	public void setRelationDataSql(String relationDataSql) {
		this.relationDataSql = relationDataSql;
	}

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

	public String getRelationDataDefiantion() {
		return relationDataDefiantion;
	}

	public void setRelationDataDefiantion(String relationDataDefiantion) {
		this.relationDataDefiantion = relationDataDefiantion;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

}
