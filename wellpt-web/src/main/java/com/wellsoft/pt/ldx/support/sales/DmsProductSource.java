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
public class DmsProductSource extends AbstractDataSourceProvider {

	@Autowired
	private IDmsService dmsService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("substr(matnr,9)","matnr","产品编码",ViewColumnType.STRING));
		viewColumns.add(generateCol("stext","stext","产品短描述",ViewColumnType.STRING));
		viewColumns.add(generateCol("DECODE(status,0,'未发布','已发布')","status","状态",ViewColumnType.STRING));
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
		return "经销商商品管理";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " product where " + whereHql;
		List<QueryItem> results = dmsService.queryForItemBySql(viewColumns,
				sql, queryParams, orderBy, pagingInfo, false);
		return results;
	}

}
