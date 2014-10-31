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
import com.wellsoft.pt.basicdata.view.support.CondSelectAskInfoNew;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;

@Component
public class WlDataSource extends AbstractDataSourceProvider {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private ISapService sapService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("substr(a.matnr,9)", "matnr", "物料ID",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("b.maktx", "maktx", "物料描述",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("c.matkl", "matkl", "物料组",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("c.wgbez", "wgbez", "物料组描述",
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
		return "物料ID";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		// queryParams.put("wl", "1010100003,1010100002");
		String sql = " mara a  inner join makt b on b.mandt = a.mandt and b.matnr = a.matnr  inner join t023t c on c.mandt = a.mandt and c.spras = '1' and c.matkl = a.matkl where substr(a.matnr,9,1) in ('2','3') and a.lvorm = ' ' and "
				+ whereHql;
		List<QueryItem> results = sapService.queryForItemBySql(viewColumns,
				sql, queryParams, orderBy, pagingInfo, "a.mandt", false);
		return results;

	}

	@Override
	public String getWhereSql(DyViewQueryInfoNew dyviewqueryinfonew) {
		List<CondSelectAskInfoNew> whereSqlList = dyviewqueryinfonew
				.getCondSelectList();
		String str = "1=1";
		for (CondSelectAskInfoNew condSelectAskInfoNew : whereSqlList) {
			if ("matnr".equals(condSelectAskInfoNew.getSearchField())
					&& StringUtils.isNotEmpty(condSelectAskInfoNew
							.getSearchValue())) {
				str += " and substr(a." + condSelectAskInfoNew.getSearchField()
						+ ",9) like '%" + condSelectAskInfoNew.getSearchValue()
						+ "%'";
			} else if ("maktx".equals(condSelectAskInfoNew.getSearchField())
					&& StringUtils.isNotEmpty(condSelectAskInfoNew
							.getSearchValue())) {
				str += " and b." + condSelectAskInfoNew.getSearchField()
						+ " like '%" + condSelectAskInfoNew.getSearchValue()
						+ "%'";
			} else if (("matkl".equals(condSelectAskInfoNew.getSearchField()) || "wgbez"
					.equals(condSelectAskInfoNew.getSearchField()))
					&& StringUtils.isNotEmpty(condSelectAskInfoNew
							.getSearchValue())) {
				str += " and c." + condSelectAskInfoNew.getSearchField()
						+ " like '%" + condSelectAskInfoNew.getSearchValue()
						+ "%'";
			}
		}
		return str;
	}

}
