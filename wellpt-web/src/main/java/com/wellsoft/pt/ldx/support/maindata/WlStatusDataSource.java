package com.wellsoft.pt.ldx.support.maindata;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.service.maindata.MainDataPpService;

@Component
public class WlStatusDataSource extends AbstractDataSourceProvider {

	@Autowired
	protected ISapService sapService;

	@Autowired
	private MainDataPpService ppService;

	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("matnr","matnr","物料",ViewColumnType.STRING));
		viewColumns.add(generateCol("werks","werks","工厂",ViewColumnType.STRING));
		viewColumns.add(generateCol("mmsta","mmsta","状态值",ViewColumnType.STRING));
		viewColumns.add(generateCol("pernr","pernr","修改人工号",ViewColumnType.STRING));
		viewColumns.add(generateCol("zdate","zdate","修改日期",ViewColumnType.STRING));
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
		return "物料工厂状态修改";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " ZMMT0023 where " + whereHql + " ";
		List<String> factorys = ppService.findWlgcOwnFactory();
		if (null != factorys && factorys.size() > 0) {
			String str = StringUtils.join(factorys, "','");
			str = str.replaceAll("\\*", " ");
			sql += " and werks in ('" + str + "')";
		}
		List<QueryItem> items = sapService.queryForItemBySql(viewColumns, sql,
				queryParams, orderBy, pagingInfo, "mandt", false);
		return items;

	}
}
