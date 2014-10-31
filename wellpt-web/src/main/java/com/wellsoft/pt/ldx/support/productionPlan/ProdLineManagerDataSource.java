/*
 * @(#)2014-3-4 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.ldx.support.productionPlan;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.provider.AbstractDataSourceProvider;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.view.support.CondSelectAskInfoNew;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;

/**
 * 
 * Description: 生产计划平台用户管理
 * 
 * @author heshi
 * @date 2014-8-1
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-1.1	heshi		2014-8-1		Create
 * </pre>
 * 
 */
@Component
public class ProdLineManagerDataSource extends AbstractDataSourceProvider {

	@Autowired
	protected ISapService sapService;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.app.xzsp.biz.AbstractXZSPViewDataSource#getAllViewColumns()
	 */
	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("zsg","zsg","生管",null));
		viewColumns.add(generateCol("zoauser","zoauser","OA用户",null));
		viewColumns.add(generateCol("zkz","zkz","课长",null));
		viewColumns.add(generateCol("verid","verid","生产版本",null));
		viewColumns.add(generateCol("matkl","matkl","物料组",null));
		viewColumns.add(generateCol("atwrt","atwrt","特性值",null));
		viewColumns.add(generateCol("dwerks","dwerks","工厂",null));
		viewColumns.add(generateCol("zzz","zzz","组长",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("zsg".equals(cond.getSearchField())){
				sql.append(" and zsg = '"+cond.getSearchValue()+"'");
			}
		}
		return sql.toString().replaceFirst("and","");
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "生产用户资料";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection,
	 *      java.lang.String, java.util.Map, java.lang.String,
	 *      com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns, String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zppt0035 where "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(viewColumns, sql,
				queryParams, orderBy, pagingInfo, "mandt", false);
		return items;

	}
}
