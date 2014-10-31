package com.wellsoft.pt.ldx.support.develope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;

@Component
public class RoutingBomDataSource extends AbstractDataSourceProvider {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private ISapService sapService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		setSearchColumn(viewColumns);
		viewColumns.add(generateCol("routing", "routing", "工艺路线",
				ViewColumnType.STRING));
		viewColumns
				.add(generateCol("bom", "bom", "BOM", ViewColumnType.STRING));
		return viewColumns;
	}

	private void setSearchColumn(Set<DataSourceColumn> viewColumns) {
		viewColumns.add(generateCol("a.matnr", "matnr", "物料ID",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("b.maktx", "maktx", "物料短描述",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("a.ersda", "ersda", "物料创建时间",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("c.werks", "werks", "工厂",
				ViewColumnType.STRING));
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
		return "工艺路线查询";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		// queryParams.put("wl", "1010100003,1010100002");
		Set<DataSourceColumn> vcs = new HashSet<DataSourceColumn>();
		setSearchColumn(vcs);
		String sql = " mara a inner join makt b on a.matnr=b.matnr and b.spras='1' and a.mandt = b.mandt inner join marc c on a.matnr=c.matnr and a.mandt = c.mandt where "
				+ whereHql;
		List<QueryItem> results = sapService.queryForItemBySql(vcs, sql,
				queryParams, orderBy, pagingInfo, "a.mandt", false);
		if (null != results && results.size() > 0) {
			SAPRfcJson util = new SAPRfcJson("");
			String jsonData = getRecordJsonString(results);
			util.setRecord("PT_OUT", jsonData);
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
					"ZI0006_LCP", util.getRfcJson(), 1, -1, null);
			JSONObject jObject = rfcjson.getRecord("PT_OUT");
			JSONArray jObjects = jObject.getJSONArray("row");
			Map<String, String[]> vas = new HashMap<String, String[]>();
			for (Object object : jObjects) {
				JSONObject o = (JSONObject) object;
				String[] arr = new String[2];
				arr[0] = o.getString("ZBOM");
				arr[1] = o.getString("ZROT");
				vas.put(o.getString("MATNR") + o.getString("WERKS"), arr);
			}
			for (QueryItem result : results) {
				String[] arr = vas.get(result.getString("matnr")
						+ result.getString("werks"));
				if ("Y".equals(arr[0])) {
					result.put("bom", "存在");
				} else {
					result.put("bom", "不存在");
				}
				if ("Y".equals(arr[1])) {
					result.put("routing", "存在");
				} else {
					result.put("routing", "不存在");
				}

			}

		}

		return results;

	}

	private String getRecordJsonString(List<QueryItem> results) {
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (QueryItem result : results) {
			result.put("matnr", result.get("matnr").toString().substring(8));
			JSONObject object = new JSONObject();
			object.element("MATNR", result.get("matnr"));
			object.element("WERKS", result.get("werks"));
			jObjects.add(object);
		}
		rowObject.element("row", jObjects);
		return rowObject.toString();
	}
}
