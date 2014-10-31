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
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.OrderManageService;
@Component
public class orderStatusOmDataSource extends AbstractDataSourceProvider{
	@Autowired
	private OrderManageService orderManageService;
	
	public Collection<ViewColumn> getAllViewColumns() {
		Collection<ViewColumn> viewColumns = new ArrayList<ViewColumn>();
		
		ViewColumn wcch = new ViewColumn();
		wcch.setAttributeName("sum(case when zddzt='已完全出货' then 1 else 0 end)");
		wcch.setColumnAlias("sum(casewhenzddzt='已完全出货'then1else0end)");
		wcch.setColumnName("已完全出货");
		wcch.setColumnType(ViewColumnType.STRING);
		viewColumns.add(wcch);
		
		ViewColumn wsch = new ViewColumn();
		wsch.setAttributeName("sum(case when zddzt='尾数待出货' then 1 else 0 end)");
		wsch.setColumnAlias("sum(casewhenzddzt='尾数待出货'then1else0end)");
		wsch.setColumnName("尾数待出货");
		wsch.setColumnType(ViewColumnType.STRING);
		viewColumns.add(wsch);
		
		ViewColumn dchd = new ViewColumn();
		dchd.setAttributeName("sum(case when zddzt='待出货-delay' then 1 else 0 end)");
		dchd.setColumnAlias("sum(casewhenzddzt='待出货-delay'then1else0end)");
		dchd.setColumnName("待出货_delay");
		dchd.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dchd);
		
		ViewColumn dchyj = new ViewColumn();
		dchyj.setAttributeName("sum(case when zddzt='待出货-预警' then 1 else 0 end)");
		dchyj.setColumnAlias("sum(casewhenzddzt='待出货-预警'then1else0end)");
		dchyj.setColumnName("待出货_预警");
		dchyj.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dchyj);
		
		ViewColumn dch = new ViewColumn();
		dch.setAttributeName("sum(case when zddzt='待出货' then 1 else 0 end)");
		dch.setColumnAlias("sum(casewhen zddzt='待出货'then1else0end)");
		dch.setColumnName("待出货");
		dch.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dch);
		
		ViewColumn ddcd = new ViewColumn();
		ddcd.setAttributeName("sum(case when zddzt='待订舱-delay' then 1 else 0 end)");
		ddcd.setColumnAlias("sum(casewhenzddzt='待订舱-delay'then1else0end)");
		ddcd.setColumnName("待订舱_delay");
		ddcd.setColumnType(ViewColumnType.STRING);
		viewColumns.add(ddcd);
		
		ViewColumn ddcyj = new ViewColumn();
		ddcyj.setAttributeName("sum(case when zddzt='待订舱-预警' then 1 else 0 end)");
		ddcyj.setColumnAlias("sum(casewhenzddzt='待订舱-预警'then1else0end)");
		ddcyj.setColumnName("待订舱_预警");
		ddcyj.setColumnType(ViewColumnType.STRING);
		viewColumns.add(ddcyj);
		
		ViewColumn ddc = new ViewColumn();
		ddc.setAttributeName("sum(case when zddzt='待订舱' then 1 else 0 end)");
		ddc.setColumnAlias("sum(casewhenzddzt='待订舱'then1else0end)");
		ddc.setColumnName("待订舱");
		ddc.setColumnType(ViewColumnType.STRING);
		viewColumns.add(ddc);
		
		ViewColumn dbzd = new ViewColumn();
		dbzd.setAttributeName("sum(case when zddzt='待包装-delay' then 1 else 0 end)");
		dbzd.setColumnAlias("sum(casewhenzddzt='待包装-delay'then1else0end)");
		dbzd.setColumnName("待包装_delay");
		dbzd.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dbzd);
		
		ViewColumn dbzyj = new ViewColumn();
		dbzyj.setAttributeName("sum(case when zddzt='待包装-预警' then 1 else 0 end)");
		dbzyj.setColumnAlias("sum(casewhenzddzt='待包装-预警'then1else0end)");
		dbzyj.setColumnName("待包装_预警");
		dbzyj.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dbzyj);
		
		ViewColumn dbz = new ViewColumn();
		dbz.setAttributeName("sum(case when zddzt='待包装' then 1 else 0 end)");
		dbz.setColumnAlias("sum(casewhenzddzt='待包装'then1else0end)");
		dbz.setColumnName("待包装");
		dbz.setColumnType(ViewColumnType.STRING);
		viewColumns.add(dbz);
		
		ViewColumn sortl = new ViewColumn();
		sortl.setAttributeName("sortl");
		sortl.setColumnAlias("sortl");
		sortl.setColumnName("客户");
		sortl.setColumnType(ViewColumnType.STRING);
		viewColumns.add(sortl);
		return viewColumns;
	}

	@Override
	public String getModuleId() {
		// TODO Auto-generated method stub
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "订单状态汇总";
	}

	
	public List<QueryItem> query(Collection<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		List<QueryItem> list = orderManageService.orderStatusOm(dataSourceColumn, whereHql, pagingInfo);
		return list;
	}

	@Override
	public Collection<DataSourceColumn> getAllDataSourceColumns() {
		Collection<DataSourceColumn> viewColumns = new ArrayList<DataSourceColumn>();
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		viewColumns.add(generateCol("a.vbeln", "vbeln", "订单号", null));
		
		return viewColumns;
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> arg0, String arg1,
			Map<String, Object> arg2, String arg3, PagingInfo arg4) {
		// TODO Auto-generated method stub
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

}
