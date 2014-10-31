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
 * Description: 生产计划平台标准产能管理
 * 
 * @author heshi
 * @date 2014-8-4
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-4.1	heshi		2014-8-4		Create
 * </pre>
 * 
 */
@Component
public class ProdLineCapacityDataSource extends AbstractDataSourceProvider {

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
		viewColumns.add(generateCol("dwerks","dwerks","工厂",null));
		viewColumns.add(generateCol("department","department","部门",null));
		viewColumns.add(generateCol("class","clazz","课别",null));
		viewColumns.add(generateCol("zxh","zxh","线号",null));
		viewColumns.add(generateCol("zsg","zsg","生管",null));
		viewColumns.add(generateCol("zrq","zrq","日期",null));
		viewColumns.add(generateCol("ltxa1","ltxa1","工序别",null));
		viewColumns.add(generateCol("gamng1","gamng1","标准产能A",ViewColumnType.INTEGER));
		viewColumns.add(generateCol("gamng2","gamng2","标准产能B",ViewColumnType.INTEGER));
		viewColumns.add(generateCol("gamng3","gamng3","标准产能C",ViewColumnType.INTEGER));
		viewColumns.add(generateCol("zxbbbxs","zxbbbxs","线别报表显示",ViewColumnType.STRING));
		viewColumns.add(generateCol("zcxdm","zcxdm","产线ID",ViewColumnType.STRING));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue())&&"TEXT".equals(cond.getSearchFieldTypeId()))
				continue;
			if("dwerks".equals(cond.getSearchField())){
				sql.append(" and dwerks = '"+cond.getSearchValue()+"'");
			}
			if("department".equals(cond.getSearchField())){
				sql.append(" and department like '%"+cond.getSearchValue()+"%'");
			}
			if("class".equals(cond.getSearchField())){
				sql.append(" and class like '%"+cond.getSearchValue()+"%'");
			}
			if("zxh".equals(cond.getSearchField())){
				sql.append(" and zxh like '%"+cond.getSearchValue()+"%'");
			}
			if("ltxa1".equals(cond.getSearchField())){
				sql.append(" and ltxa1 = '"+cond.getSearchValue()+"'");
			}
			if("zsg".equals(cond.getSearchField())){
				sql.append(" and zsg = '"+cond.getSearchValue().toUpperCase()+"'");
			}
			if("zcxdm".equals(cond.getSearchField())){
				sql.append(" and zcxdm = '"+cond.getSearchValue()+"'");
			}
			//生产日期查询条件
			if("zrq".equals(cond.getSearchField())&&"DATE".equals(cond.getSearchFieldTypeId())
					&&(StringUtils.isNotBlank(cond.getBeginTime())||StringUtils.isNotBlank(cond.getEndTime()))){
				String begin = StringUtils.nullToString(cond.getBeginTime()).replaceAll("-","");
				String end = StringUtils.nullToString(cond.getEndTime()).replaceAll("-","");
				begin = StringUtils.isBlank(begin)?end:begin;
				end = StringUtils.isBlank(end)?begin:end;
				sql.append(" and zrq >= '"+begin+"' and zrq<='"+end+"'");
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
		return "标准产能管理";
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
		String sql = " zppt0034 where "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(viewColumns, sql,
				queryParams, orderBy, pagingInfo, "mandt", false);
		return items;

	}
}
