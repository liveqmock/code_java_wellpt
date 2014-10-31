package com.wellsoft.pt.ldx.support.sales;

import java.math.BigDecimal;
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
import com.wellsoft.pt.ldx.service.IDmsService;

@Component
public class DmsOrderSource extends AbstractDataSourceProvider {

	@Autowired
	private IDmsService dmsService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("a.id","id","ID",ViewColumnType.STRING));
		viewColumns.add(generateCol("a.ddbh","ddbh","订单编号",ViewColumnType.STRING));
		viewColumns.add(generateCol("b.companyname","companyname","公司名称",ViewColumnType.STRING));
		viewColumns.add(generateCol("b.realname","realname","联系人",ViewColumnType.STRING));
		viewColumns.add(generateCol("b.mobile","mobile","联系人电话",ViewColumnType.STRING));
		viewColumns.add(generateCol("to_char(a.createdate,'yyyy-mm-dd')","createdate","下单时间",ViewColumnType.DATE));
		viewColumns.add(generateCol("a.status","status","审核状态",ViewColumnType.STRING));
		return viewColumns;
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
		return "经销商订单管理";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String querySql = " sellorder a inner join users b on a.customer=b.id where "
				+ whereHql;
		List<QueryItem> items = dmsService.queryForItemBySql(viewColumns,
				querySql, queryParams, orderBy, pagingInfo, false);
		for (QueryItem queryItem : items) {
			if ((new BigDecimal(0)).compareTo((BigDecimal) queryItem
					.get("status")) == 0) {
				queryItem.put("status", "未审核");
				continue;
			}
			if ((new BigDecimal(1)).compareTo((BigDecimal) queryItem
					.get("status")) == 0) {
				queryItem.put("status", "审核通过");
				continue;
			}
			if ((new BigDecimal(2)).compareTo((BigDecimal) queryItem
					.get("status")) == 0) {
				queryItem.put("status", "审核不通过");
				continue;
			}
			if ((new BigDecimal(3)).compareTo((BigDecimal) queryItem
					.get("status")) == 0) {
				queryItem.put("status", "取消");
				continue;
			}
		}
		return items;
	}

}
