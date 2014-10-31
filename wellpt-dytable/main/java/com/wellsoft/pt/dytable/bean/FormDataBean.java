/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

/**
 * Description: 表单数据对象表级别
 *  
 * @author jiangmb
 * @date 2012-11-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-13.1	jiangmb		2012-11-13		Create
 * </pre>
 *
 */
public class FormDataBean {
	//用于主表uuid
	private String formDefinitionUuid;
	//表名
	private String tableName;
	//显示的表名称
	private String descTableName;
	//表类型(主：1	从：2)
	private String tableType;

	//表单数据修改时间（用于新旧比较接口）
	private String modifyTime;

	//数据uuid
	private String dataUuid;
	
	 

	//表单记录列表
	private List<FormDataRecordBean> recordList = new ArrayList<FormDataRecordBean>();

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormDataBean [formDefinitionUuid=" + formDefinitionUuid + ", tableName=" + tableName + ", tableType="
				+ tableType + ", modifyTime=" + modifyTime + ", dataUuid=" + dataUuid + ", recordList=" + recordList
				+ "]";
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
	 * @return the modifyTime
	 */
	public String getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime 要设置的modifyTime
	 */
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the dataUuid
	 */
	public String getDataUuid() {
		return dataUuid;
	}

	/**
	 * @param dataUuid 要设置的dataUuid
	 */
	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	public String getFormDefinitionUuid() {
		return formDefinitionUuid;
	}

	public void setFormDefinitionUuid(String formDefinitionUuid) {
		this.formDefinitionUuid = formDefinitionUuid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public List<FormDataRecordBean> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<FormDataRecordBean> recordList) {
		this.recordList = recordList;
	}
	
	
	public boolean isMainForm(){
		if(tableType != null && tableType.trim().equals("1")){
			return true;
		}else{
			return false;
		}
	}
}
