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
import com.wellsoft.pt.basicdata.dyview.provider.AbstractViewDataSource;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.OrderManageService;

@Component
public class ProductionSearchDataSource extends AbstractDataSourceProvider{

	@Autowired
	private OrderManageService orderManageService;
	
	public Collection<ViewColumn> getAllViewColumns() {
		Collection<ViewColumn> viewColumns = new ArrayList<ViewColumn>();
		ViewColumn vbeln = new ViewColumn();
		vbeln.setAttributeName("a.vbeln");
		vbeln.setColumnAlias("vbeln");
		vbeln.setColumnName("销售订单");
		vbeln.setColumnType(ViewColumnType.STRING);
		viewColumns.add(vbeln);

		ViewColumn posnr = new ViewColumn();
		posnr.setAttributeName("a.posnr");
		posnr.setColumnAlias("posnr");
		posnr.setColumnName("项目号");
		posnr.setColumnType(ViewColumnType.STRING);
		viewColumns.add(posnr);
		
		ViewColumn sortl = new ViewColumn();
		sortl.setAttributeName("b.sortl");
		sortl.setColumnAlias("sortl");
		sortl.setColumnName("客户");
		sortl.setColumnType(ViewColumnType.STRING);
		viewColumns.add(sortl);
		
		ViewColumn zdate = new ViewColumn();
		zdate.setAttributeName("c.zdate");
		zdate.setColumnAlias("zdate");
		zdate.setColumnName("签发时间");
		zdate.setColumnType(ViewColumnType.STRING);
		viewColumns.add(zdate);
		
		ViewColumn matnr = new ViewColumn();
		matnr.setAttributeName("substr(a.matnr,9)");
		matnr.setColumnAlias("matnr");
		matnr.setColumnName("物料ID");
		matnr.setColumnType(ViewColumnType.STRING);
		viewColumns.add(matnr);
		
		ViewColumn maktx = new ViewColumn();
		maktx.setAttributeName("d.maktx");
		maktx.setColumnAlias("maktx");
		maktx.setColumnName("物料描述");
		maktx.setColumnType(ViewColumnType.STRING);
		viewColumns.add(maktx);
		
		ViewColumn zdats = new ViewColumn();
		zdats.setAttributeName("e.zdats");
		zdats.setColumnAlias("zdats");
		zdats.setColumnName("最终交期");
		zdats.setColumnType(ViewColumnType.STRING);
		viewColumns.add(zdats);
		
		ViewColumn edatu = new ViewColumn();
		edatu.setAttributeName("g.edatu");
		edatu.setColumnAlias("edatu");
		edatu.setColumnName("参考交期");
		edatu.setColumnType(ViewColumnType.STRING);
		viewColumns.add(edatu);
		
		ViewColumn psttr = new ViewColumn();
		psttr.setAttributeName("h.psttr");
		psttr.setColumnAlias("psttr");
		psttr.setColumnName("计划订单");
		psttr.setColumnType(ViewColumnType.STRING);
		viewColumns.add(psttr);
		
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
		return "生产进度";
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
		viewColumns.add(generateCol("a.vbeln", "vbeln", "销售订单", null));
		viewColumns.add(generateCol("a.posnr", "posnr", "项目号", null));
		viewColumns.add(generateCol("b.sortl", "sortl", "客户", null));
		viewColumns.add(generateCol("c.zdate", "zdate", "签发时间", null));
		viewColumns.add(generateCol("substr(a.matnr,9)", "matnr", "物料ID", null));
		viewColumns.add(generateCol("d.maktx", "maktx", "物料描述", null));
		viewColumns.add(generateCol("e.zdats", "zdats", "最终交期", null));
		viewColumns.add(generateCol("g.edatu", "edatu", "参考交期", null));
		viewColumns.add(generateCol("h.psttr", "psttr", "计划订单", null));
		return viewColumns;
	}
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		
		return null;
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		
		List productionSearch = orderManageService.productionSearch(dataSourceColumn,whereHql,pagingInfo);
		List<QueryItem> list = productionSearch;
		return list;
	}

}
