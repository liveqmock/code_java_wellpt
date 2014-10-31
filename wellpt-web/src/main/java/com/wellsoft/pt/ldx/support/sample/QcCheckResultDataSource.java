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
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.sampleTrack.SampleTrackService;
import com.wellsoft.pt.ldx.util.StringUtils;

@Component
public class QcCheckResultDataSource extends AbstractDataSourceProvider{

	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	@Override
	public String getModuleName() {
		return "品保检验结果";
	}
	@Autowired
	protected SampleTrackService sampleTrackService;

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
		viewColumns.add(generateCol("qcname", "qcname", "检验人员", null));
		viewColumns.add(generateCol("qcsecondresult", "qcsecondresult", "二次判定结果", null));
		viewColumns.add(generateCol("qccheckresult", "qccheckresult", "品保判定结果", null));
		viewColumns.add(generateCol("qccosttime", "qccosttime", "品质检验时间", null));
		viewColumns.add(generateCol("qcfinishdate", "qcfinishdate", "检验完成时间", null));
		viewColumns.add(generateCol("qccheckdate", "qccheckdate", "检验开始时间", null));
		viewColumns.add(generateCol("qcexceptcause", "qcexceptcause", "品质异常原因", null));
		viewColumns.add(generateCol("qccheckmemo", "qccheckmemo", "品保检验描述", null));
		viewColumns.add(generateCol("qccheckitem", "qccheckitem", "品保检验项目", null));
		viewColumns.add(generateCol("testappnum", "testappnum", "测试委托单", null));
		viewColumns.add(generateCol("productname", "productname", "产品名称", null));
		viewColumns.add(generateCol("productid", "productid", "产品ID", null));
		viewColumns.add(generateCol("sampleorderid", "sampleorderid", "样品单号", null));
		viewColumns.add(generateCol("lineitemid", "lineitemid", "行项目", null));
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
		String sql = " USERFORM_SAMPLE_TRACK  where "+whereHql;
		List<QueryItem> list = sampleTrackService.findData(dataSourceColumn, sql, pagingInfo);
		return list;
	}

}
