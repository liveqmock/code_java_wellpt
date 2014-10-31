/*
 * @(#)2014-3-4 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.ldx.support.maindata;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.maindata.MainDataMmService;

@Component
public class MmViewDataSource extends AbstractDataSourceProvider {

	@Autowired
	private MainDataMmService mmService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns
				.add(generateCol("uuid", "uuid", "主键", ViewColumnType.STRING));
		viewColumns.add(generateCol("wl", "wl", "物料", ViewColumnType.STRING));
		viewColumns.add(generateCol("shortdesc", "shortdesc", "物料描述",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("factory", "factory", "工厂",
				ViewColumnType.STRING));
		return viewColumns;
	}

	private DataSourceColumn generateCol(String attributeName,
			String columnAlias, String columnName, ViewColumnType viewcolumntype) {
		DataSourceColumn column = new DataSourceColumn();
		column.setFieldName(attributeName);
		column.setColumnAliase(columnAlias);
		column.setColumnName(columnName);
		column.setColumnDataType(viewcolumntype == null ? ViewColumnType.STRING
				.name() : viewcolumntype.name());
		return column;
	}

	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "MM视图维护";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		orderBy = "order by wl asc,factory asc";
		List<String> factorys = mmService.findOwnFactory();
		if (null != factorys && factorys.size() > 0) {
			whereHql += " and factory in (:factory)";
			queryParams.put("factory", factorys);
		}
		List<QueryItem> items = mmService.queryQueryItemData(
				"from Mmview where " + whereHql + " " + orderBy, queryParams,
				pagingInfo);
		return items;

	}
}
