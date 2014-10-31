package com.wellsoft.pt.ldx.support.sample;

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
import com.wellsoft.pt.basicdata.view.provider.ViewColumnNew;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;

@Component
public class prodResultDataSource extends AbstractDataSourceProvider{
	@Autowired
	protected SampleTrackService sampleTrackService;
	
	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "样品课反馈结果";
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
		viewColumns.add(generateCol("lineitemid", "lineitemid", "行项目", null));
		viewColumns.add(generateCol("sampleorderid", "sampleorderid", "样品单号", null));
		viewColumns.add(generateCol("productid", "productid", "产品ID", null));
		viewColumns.add(generateCol("productname", "productname", "样品名称", null));
		viewColumns.add(generateCol("samplepredictddate", "samplepredictddate", "样品预计交期", null));
		viewColumns.add(generateCol("sampledeliveryperiod", "sampledeliveryperiod", "样品交货绝期", null));
		viewColumns.add(generateCol("completedate", "completedate", "完工日期", null));
		viewColumns.add(generateCol("planstart", "planstart", "排程开始", null));
		viewColumns.add(generateCol("planend", "planend", "排程完工", null));
		viewColumns.add(generateCol("prodcosttime", "prodcosttime", "生产时间", null));
		viewColumns.add(generateCol("prodstatus", "prodstatus", "生产状态", null));
		viewColumns.add(generateCol("prodstatusmemo", "prodstatusmemo", "生产状态备注说明", null));
		viewColumns.add(generateCol("ledsamplememo", "ledsamplememo", "领样说明", null));
		viewColumns.add(generateCol("unitprice", "unitprice", "单价(RMB)", null));
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
		String hql = " USERFORM_SAMPLE_TRACK  where "+whereHql;
		List<QueryItem> list = sampleTrackService.findData(dataSourceColumn, hql, pagingInfo);
		return list;
	}


}
