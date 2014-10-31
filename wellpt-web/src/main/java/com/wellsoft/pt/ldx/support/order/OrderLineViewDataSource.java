package com.wellsoft.pt.ldx.support.order;

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
import com.wellsoft.pt.ldx.service.OrderManageService;

@Component
public class OrderLineViewDataSource extends AbstractDataSourceProvider{
	
	@Autowired
	private OrderManageService orderManageService;

	@Override
	public String getModuleId() {
		// TODO Auto-generated method stub
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "订单行项目";
	}

	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		return null;
	}
	
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
	public Collection<DataSourceColumn> getAllDataSourceColumns() {
		Collection<DataSourceColumn> viewColumns = new ArrayList<DataSourceColumn>();
		viewColumns.add(generateCol("vbeln", "vbeln", "SAP订单号", null));
		viewColumns.add(generateCol("posnr", "posnr", "行项目号", null));
		viewColumns.add(generateCol("mandt", "mandt", "物料", null));
		viewColumns.add(generateCol("maktx", "maktx", "物料描述", null));
		viewColumns.add(generateCol("kwmeng", "kwmeng", "数量", null));
		return viewColumns;
	}


	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		// TODO Auto-generated method stub
		if(whereHql.indexOf("and")==-1)whereHql = "a.vbeln=''";
		String querySql = " zsdt0049 a where "+whereHql;
		List<QueryItem> list = orderManageService.orderLineItemDetail(dataSourceColumn,querySql,pagingInfo);
		return list;
	}

}
