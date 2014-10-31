package com.wellsoft.pt.ldx.support.maindata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
public class WlProgressDataSource extends AbstractDataSourceProvider {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private ISapService sapService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		setSearchColumn(viewColumns);
		viewColumns.add(generateCol("bom","bom","BOM",ViewColumnType.STRING));
		viewColumns.add(generateCol("mrp","mrp","MRP*",ViewColumnType.STRING));
		viewColumns.add(generateCol("plan","plan","工作计划",ViewColumnType.STRING));
		viewColumns.add(generateCol("mm","mm","MM",ViewColumnType.STRING));
		viewColumns.add(generateCol("sd","sd","SD",ViewColumnType.STRING));
		viewColumns.add(generateCol("qm","qm","QM",ViewColumnType.STRING));
		viewColumns.add(generateCol("fico","fico","FICO*",ViewColumnType.STRING));
		viewColumns.add(generateCol("cost","cost","滚算成本*",ViewColumnType.STRING));
		viewColumns.add(generateCol("routing","routing","工艺路线",ViewColumnType.STRING));
		viewColumns.add(generateCol("delstatus","delstatus","删除状态",ViewColumnType.STRING));
		viewColumns.add(generateCol("crm","crm","CRM存在",ViewColumnType.STRING));
		viewColumns.add(generateCol("crmactive","crmactive","CRM启用",ViewColumnType.STRING));
		viewColumns.add(generateCol("crmonline","crmonline","CRM上线",ViewColumnType.STRING));
		return viewColumns;
	}

	private void setSearchColumn(Set<DataSourceColumn> viewColumns) {
		viewColumns.add(generateCol("a.matnr","matnr","物料ID",ViewColumnType.STRING));
		viewColumns.add(generateCol("b.maktx","maktx","物料短描述",ViewColumnType.STRING));
		viewColumns.add(generateCol("a.ersda","ersda","物料创建时间",ViewColumnType.STRING));
		viewColumns.add(generateCol("c.werks","werks","工厂",ViewColumnType.STRING));
	}
	
	private DataSourceColumn generateCol(String attributeName,String columnAlias,String columnName,ViewColumnType viewcolumntype){
		DataSourceColumn column = new DataSourceColumn();
		column.setFieldName(attributeName);
		column.setColumnAliase(columnAlias);
		column.setColumnName(columnName);
		column.setColumnDataType(viewcolumntype==null?ViewColumnType.STRING.name():viewcolumntype.name());
		return column;
	}

	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "物料数据进度查询";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		// queryParams.put("wl", "1010100003,1010100002");
		Set<DataSourceColumn> vcs = new HashSet<DataSourceColumn>();
		setSearchColumn(vcs);
		String sql = " mara a inner join makt b on a.matnr=b.matnr and b.spras='1' and a.mandt = b.mandt inner join marc c on a.matnr=c.matnr and a.mandt = c.mandt where a.matnr!='000000000000000000' and "
				+ whereHql;
		List<QueryItem> results = sapService.queryForItemBySql(vcs, sql,
				queryParams, orderBy, pagingInfo, "a.mandt", false);
		if (null != results && results.size() > 0) {
			setSapData(results);
			setCrmData(results);
		}

		return results;

	}

	private void setCrmData(List<QueryItem> results) {
		Set<String> ids = new HashSet<String>();
		Set<String> ids_other = new HashSet<String>();
		for (QueryItem item : results) {
			if (item.getString("matnr").startsWith("3000")) {
				ids.add(item.getString("matnr"));
			} else {
				ids_other.add(item.getString("matnr"));
			}
		}
		Map<String, String[]> vas = new HashMap<String, String[]>();
		if (!ids.isEmpty()) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url = "jdbc:mysql://192.168.0.186:3306/crm";
				Connection conn = DriverManager.getConnection(url, "read",
						"leedarson@110");
				PreparedStatement psStatement = conn
						.prepareStatement("select active,status,package_id from crm_packages where package_id in ('"
								+ StringUtils.join(ids, "','") + "')");
				ResultSet rSet = psStatement.executeQuery();
				while (rSet.next()) {
					String[] arr = new String[3];
					String isCrm = "";
					String isCrmActive = "";
					String isCrmOnline = "";
					if ("1".equals(rSet.getObject("active").toString())) {
						isCrm = "√";
						if ("1".equals(rSet.getObject("status").toString())) {
							isCrmActive = "√";
							isCrmOnline = "√";
						}
					}
					arr[0] = isCrm;
					arr[1] = isCrmActive;
					arr[2] = isCrmOnline;
					vas.put(rSet.getObject("package_id").toString(), arr);
				}
				rSet.close();
				psStatement.close();
				conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		if (!ids_other.isEmpty()) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url = "jdbc:mysql://192.168.0.186:3306/crm";
				Connection conn = DriverManager.getConnection(url, "read",
						"leedarson@110");
				PreparedStatement psStatement = conn
						.prepareStatement("select active,isactive_pm,status,product_id from crm_product where product_id in ('"
								+ StringUtils.join(ids_other, "','") + "')");
				ResultSet rSet = psStatement.executeQuery();
				while (rSet.next()) {
					String[] arr = new String[3];
					String isCrm = "";
					String isCrmActive = "";
					String isCrmOnline = "";
					if ("1".equals(rSet.getObject("active").toString())) {
						isCrm = "√";
						if ("1".equals(rSet.getObject("status").toString())) {
							isCrmActive = "√";
							isCrmOnline = "√";
						}
					}
					if ("1".equals(rSet.getObject("status").toString())) {
						isCrm = "√";
						if ("1".equals(rSet.getObject("active").toString())) {
							isCrmActive = "√";
						}
						if ("1".equals(rSet.getObject("isactive_pm").toString())) {
							isCrmOnline = "√";
						}
					}
					arr[0] = isCrm;
					arr[1] = isCrmActive;
					arr[2] = isCrmOnline;
					vas.put(rSet.getObject("product_id").toString(), arr);
				}
				rSet.close();
				psStatement.close();
				conn.close();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		for (QueryItem result : results) {
			if(vas.containsKey(result.getString("matnr"))){
				String[] arr = vas.get(result.getString("matnr"));
				result.put("crm", arr[0]);
				result.put("crmactive", arr[1]);
				result.put("crmonline", arr[1]);
			}
		}
	}

	private void setSapData(List<QueryItem> results) {
		SAPRfcJson util = new SAPRfcJson("");
		String jsonData = getRecordJsonString(results);
		util.setRecord("PT_OUT", jsonData);
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0007_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject jObject = rfcjson.getRecord("PT_OUT");
		JSONArray jObjects = jObject.getJSONArray("row");
		Map<String, String[]> vas = new HashMap<String, String[]>();
		for (Object object : jObjects) {
			JSONObject o = (JSONObject) object;
			String[] arr = new String[10];
			arr[0] = o.getString("ZBOM");
			arr[1] = o.getString("ZMRP");
			arr[2] = o.getString("ZPLAN");
			arr[3] = o.getString("ZMM");
			arr[4] = o.getString("ZSD");
			arr[5] = o.getString("ZQM");
			arr[6] = o.getString("ZFC");
			arr[7] = o.getString("ZCB");
			arr[8] = o.getString("ZGY");
			arr[9] = o.getString("LVORM");
			vas.put(StringUtils.leftPad(o.getString("MATNR"), 10, '0') + o.getString("WERKS"), arr);
		}
		for (QueryItem result : results) {
			String[] arr = vas.get(result.getString("matnr")
					+ result.getString("werks"));
			String[] keys = { "bom", "mrp", "plan", "mm", "sd", "qm", "fico",
					"cost", "routing", "delstatus" };
			for (int i = 0; i < keys.length; i++) {
				if ("Y".equals(arr[i])) {
					result.put(keys[i], "√");
				} else {
					result.put(keys[i], "");
				}
			}
			result.put("crm", "");
			result.put("crmactive", "");
			result.put("crmonline", "");
		}
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
