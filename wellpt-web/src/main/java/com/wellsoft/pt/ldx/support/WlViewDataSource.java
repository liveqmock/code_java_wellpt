/*
 * @(#)2014-3-4 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.ldx.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.star.uno.RuntimeException;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.basicdata.view.support.CondSelectAskInfoNew;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.WlGcService;

@Component
public class WlViewDataSource extends AbstractDataSourceProvider {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private WlGcService wlGcService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("wl", "wl", "物料ID", ViewColumnType.STRING));
		viewColumns.add(generateCol("shortdesc", "shortdesc", "物料短描述",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("longdesc", "longdesc", "物料长描述",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("spart", "spart", "产品组",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("desc", "desc", "物料备注",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("createdate", "createdate", "日期",
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
		return "物料视图";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		SAPRfcJson util = new SAPRfcJson("");
		if (whereHql.contains("@")) {
			String[] arrayObject = whereHql.split("@");
			queryParams.put("wl", arrayObject[1].trim().replace(")", ""));
		}
		if (!queryParams.isEmpty()) {
			String jsonData = getRecordJsonString(queryParams);
			util.setRecord("PT_MAT", jsonData);
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0002_LCP", util.getRfcJson(), 1, -1, null);
		if (null == rfcjson) {
			throw new RuntimeException("传递SAP失败");
		}
		JSONObject jObject = rfcjson.getRecord("PT_MARA");
		JSONArray jObjects = jObject.getJSONArray("row");
		List<QueryItem> items = new ArrayList<QueryItem>();
		List<QueryItem> wlgcs = wlGcService.queryItemsData("from WlGc", null);
		Set<String> wls = new HashSet<String>();
		for (QueryItem queryItem : wlgcs) {
			wls.add(queryItem.getString("wl").trim());
		}
		JSONArray os = new JSONArray();
		for (int i = 0; i < jObjects.size(); i++) {
			if (!wls.contains(((JSONObject) jObjects.get(i)).get("MATNR")
					.toString().trim().substring(8))) {
				os.add(jObjects.get(i));
			}
		}
		pagingInfo.setTotalCount(os.size());
		for (int i = pagingInfo.getFirst(); i < pagingInfo.getFirst()
				+ pagingInfo.getPageSize(); i++) {
			if (i < pagingInfo.getTotalCount()) {
				JSONObject o = (JSONObject) os.get(i);
				QueryItem item = new QueryItem();
				item.put("wl", o.get("MATNR").toString().substring(8));
				item.put("shortdesc", o.get("MAKTX"));
				item.put("longdesc", o.get("ZMAKTX"));
				item.put("spart", o.get("SPART"));
				item.put("desc", o.get("ZREMARK"));
				item.put("createdate", o.get("ERSDA"));
				items.add(item);
			}
		}
		return items;

	}

	@Override
	public String getWhereSql(DyViewQueryInfoNew dyviewqueryinfonew) {
		List<CondSelectAskInfoNew> whereSqlList = dyviewqueryinfonew
				.getCondSelectList();
		String str = "";
		for (CondSelectAskInfoNew object : whereSqlList) {
			str += object.getSearchField() + "@" + object.getSearchValue();
		}
		return str;
	}

	private String getRecordJsonString(Map<String, Object> queryParams) {
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : queryParams.keySet()) {
			String[] arrayString = queryParams.get(key).toString().split(",");
			for (String str : arrayString) {
				JSONObject object = new JSONObject();
				object.element("MATNR", StringUtils.leftPad(str, 18, '0'));
				jObjects.add(object);
			}
		}
		rowObject.element("row", jObjects);
		return rowObject.toString();
	}
}
