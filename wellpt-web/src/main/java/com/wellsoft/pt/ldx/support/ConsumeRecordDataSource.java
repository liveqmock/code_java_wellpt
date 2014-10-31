package com.wellsoft.pt.ldx.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.util.DateUtils;

@Component
public class ConsumeRecordDataSource extends AbstractDataSourceProvider {

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("recId", "recId", "RecID",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("computer", "computer", "采集设备",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("xfPosDay", "xfPosDay", "刷卡日期",
				ViewColumnType.DATE));
		viewColumns.add(generateCol("mealTypeName", "mealTypeName", "消费时段",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("empNo", "empNo", "工号",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("dptName", "dptName", "部门",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("empName", "empName", "姓名",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("xfDevSerialNo", "xfDevSerialNo", "设备记录号",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("cardID", "cardID", "卡流水号",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("xfPosMoney", "xfPosMoney", "消费金额",
				ViewColumnType.STRING));
		viewColumns.add(generateCol("xfCardMoney", "xfCardMoney", "消费余额",
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
		return "员工餐厅消费";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		if (queryParams.containsKey("startDate")) {
			queryParams.put("from", queryParams.get("startDate"));
		} else {
			queryParams.put("from", DateUtils.getBeforeDayOfCurrentDay());
		}
		if (queryParams.containsKey("endDate")) {
			queryParams.put("to", queryParams.get("endDate"));
		} else {
			queryParams.put("to", new Date());
		}
		List<QueryItem> items = new ArrayList<QueryItem>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
					.newInstance();
			String url = "jdbc:sqlserver://192.168.0.24:1433;databasename=AIO20110418163744";
			Connection conn = DriverManager.getConnection(url, "sa", "mysql");
			PreparedStatement psStatement = conn
					.prepareStatement("{call dbo.uprpt_XFDataSumByDateDeptNew(?,?)}");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			psStatement.setString(1, df.format(queryParams.get("from")));
			psStatement.setString(2, df.format(queryParams.get("to")));
			ResultSet rSet = psStatement.executeQuery();
			while (rSet.next()) {
				QueryItem item = new QueryItem();
				item.put("recId", rSet.getString(1));
				item.put("computer", rSet.getString(2));
				item.put("xfPosDay", rSet.getTimestamp(3));
				item.put("mealTypeName", rSet.getString(4));
				item.put("empNo", rSet.getString(5));
				item.put("dptName", rSet.getString(6));
				item.put("empName", rSet.getString(7));
				item.put("xfDevSerialNo", rSet.getString(8));
				item.put("cardID", rSet.getString(9));
				item.put("xfPosMoney", rSet.getFloat(10));
				item.put("xfCardMoney", rSet.getFloat(11));
				items.add(item);
			}
			rSet.close();
			psStatement.close();
			conn.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		pagingInfo.setTotalCount(items.size());
		List<QueryItem> datas = new ArrayList<QueryItem>();
		for (int i = pagingInfo.getFirst(); i < pagingInfo.getFirst()
				+ pagingInfo.getPageSize(); i++) {
			if (i < pagingInfo.getTotalCount()) {
				datas.add(items.get(i));
			}
		}
		return datas;

	}

}
