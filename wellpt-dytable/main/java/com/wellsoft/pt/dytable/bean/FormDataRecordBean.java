/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import java.io.*;
import java.util.*;

/**
 * Description: 表单数据行级别
 * 
 * @author jiangmb
 * @date 2012-11-13
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-13.1	jiangmb		2012-11-13		Create
 * </pre>
 * 
 */
public class FormDataRecordBean implements Serializable {
	// 记录uuid
	private String uuid;
	private String sortorder;
	// 记录中的字段值列表
	private List<FormDataColValBean> colValList = new ArrayList<FormDataColValBean>();

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<FormDataColValBean> getColValList() {
		return colValList;
	}

	public void setColValList(List<FormDataColValBean> colValList) {
		this.colValList = colValList;
	}
}
