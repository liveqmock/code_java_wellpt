package com.wellsoft.pt.ldx.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.BoUserService;

@Component
public class BoUserDataSource extends AbstractDataSourceProvider{
	@Autowired
	private BoUserService boUserService;

	/**
	 * 
	 * 生成视图显示列
	 * 
	 * @param attributeName
	 * @param columnAlias
	 * @param columnName
	 * @param viewcolumntype
	 * @return
	 */
	private DataSourceColumn generateCol(String attributeName,String columnAlias,String columnName,ViewColumnType viewcolumntype){
		DataSourceColumn column = new DataSourceColumn();
		column.setFieldName(attributeName);
		column.setColumnAliase(columnAlias);
		column.setColumnName(columnName);
		column.setColumnDataType(viewcolumntype==null?ViewColumnType.STRING.name():viewcolumntype.name());
		return column;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		
		return null;
	}

	@Override
	public String getModuleId() {
		// TODO Auto-generated method stub
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "Bo报表用户设置";
	}

	@Override
	public Collection<DataSourceColumn> getAllDataSourceColumns() {
		Collection<DataSourceColumn> viewColumns = new ArrayList<DataSourceColumn>();
		viewColumns.add(generateCol("uuid", "uuid", "ID", null));
		viewColumns.add(generateCol("department_name", "department_name", "部门", null));
		viewColumns.add(generateCol("user_name", "user_name", "用户", null));
		viewColumns.add(generateCol("bouser", "bouser", "BO用户", null));
		viewColumns.add(generateCol("bopwd", "bopwd", "BO密码", null));
		return viewColumns;
	}
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String querySql = " org_user where enabled=1 and " + whereHql;
		List<QueryItem> list = boUserService.queryForItemBySql(dataSourceColumn, querySql, queryParams, orderBy, pagingInfo, false);
		return list;
	}

}
