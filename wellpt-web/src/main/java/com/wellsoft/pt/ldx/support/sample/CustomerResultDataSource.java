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
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;

@Component
public class CustomerResultDataSource extends AbstractDataSourceProvider{
	@Autowired
	protected SampleTrackService sampleTrackService;
	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "客户反馈结果";
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
		viewColumns.add(generateCol("applyuser", "applyuser", "申请人", null));
		viewColumns.add(generateCol("applydept", "applydept", "申请部门", null));
		viewColumns.add(generateCol("customerid", "customerid", "客户代码", null));
		viewColumns.add(generateCol("productid", "productid", "产品ID", null));
		viewColumns.add(generateCol("productname", "productname", "产品名称", null));
		viewColumns.add(generateCol("completedate", "completedate", "完工日期", null));
		viewColumns.add(generateCol("sampleaim", "sampleaim", "送样目的", null));
		viewColumns.add(generateCol("sampledestination", "sampledestination", "实际送样目的地", null));
		viewColumns.add(generateCol("sampledate", "sampledate", "送样时间", null));
		viewColumns.add(generateCol("expressagenum", "expressagenum", "快递单号", null));
		viewColumns.add(generateCol("customerresult", "customerresult", "客户反馈结果", null));
		viewColumns.add(generateCol("qcexceptreply", "qcexceptreply", "责任部门反馈（品质异常）", null));
		viewColumns.add(generateCol("prodexceptreply", "prodexceptreply", "责任部门反馈（交期异常）", null));
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
		String hql = " USERFORM_SAMPLE_TRACK  where  sampleOrderStatus < 4 and "+whereHql;
		List<QueryItem> list = sampleTrackService.findData(dataSourceColumn, hql, pagingInfo);
		return list;
	}

}
