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
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-30.1	jiangmb		2012-10-30		Create
 * </pre>
 *
 */
@SuppressWarnings("serial")
public class RootTableInfoBean implements Serializable {
	//主表信息对象
	private TableInfoBean tableInfo = new TableInfoBean();
	//子表集合
	private List<SubTableInfoBean> subTables = new ArrayList<SubTableInfoBean>();

	public TableInfoBean getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfoBean tableInfo) {
		this.tableInfo = tableInfo;
	}

	public List<SubTableInfoBean> getSubTables() {
		return subTables;
	}

	public void setSubTables(List<SubTableInfoBean> subTables) {
		this.subTables = subTables;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (tableInfo != null) {
			sb.append("主表名:").append(tableInfo.getTableName()).append(";中文名:").append(tableInfo.getTableDesc())
					.append(";从表个数:").append(subTables == null ? 0 : subTables.size());
		}

		return sb.toString();
	}

	public String getFieldName(String fieldMappingName) {
		return this.tableInfo.getFieldName(fieldMappingName);
	}

	public String getFieldType(String fieldName) {
		return this.tableInfo.getFieldType(fieldName);
	}
}
