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
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.OrderManageService;

@Component
public class orderSearchDataSource extends AbstractDataSourceProvider{
	@Autowired
	private OrderManageService orderManageService;

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
		// TODO Auto-generated method stub
		return "订单明细查询";
	}

	@Override
	public Collection<DataSourceColumn> getAllDataSourceColumns() {
		Collection<DataSourceColumn> viewColumns = new ArrayList<DataSourceColumn>();
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("sortl", "sortl", "客户编码", null));
		viewColumns.add(generateCol("werks", "werks", "加工厂", null));
		viewColumns.add(generateCol("bstkd", "bstkd", "合同号", null));
		viewColumns.add(generateCol("kwmeng", "kwmeng", "合同数量", null));
		viewColumns.add(generateCol("edatu", "edatu", "订单交期", null));
		viewColumns.add(generateCol("zddzt", "zddzt", "订单状态", null));
		return viewColumns;
	}
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		List<QueryItem> list = orderManageService.getOrderListbyNativePage(dataSourceColumn,whereHql,pagingInfo);
		return list;
	}

}
