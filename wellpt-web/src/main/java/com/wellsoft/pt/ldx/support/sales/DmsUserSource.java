package com.wellsoft.pt.ldx.support.sales;

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
public class DmsUserSource extends AbstractDataSourceProvider {

	@Autowired
	private IDmsService dmsService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("id","id","ID",ViewColumnType.STRING));
		viewColumns.add(generateCol("companyname","companyname","公司名称",ViewColumnType.STRING));
		viewColumns.add(generateCol("customernum","customernum","客户编号",ViewColumnType.STRING));
		viewColumns.add(generateCol("regname","regname","账户名",ViewColumnType.STRING));
		viewColumns.add(generateCol("realname","realname","联系人",ViewColumnType.STRING));
		viewColumns.add(generateCol("mobile","mobile","联系人电话",ViewColumnType.STRING));
		viewColumns.add(generateCol("DECODE(status,1,'正在合作','取消合作')","status","状态",ViewColumnType.STRING));
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
		return "经销商用户管理";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String querySql = " users where " + whereHql;
		List<QueryItem> items = dmsService.queryForItemBySql(viewColumns,
				querySql, queryParams, orderBy, pagingInfo, false);
		return items;
	}

}
