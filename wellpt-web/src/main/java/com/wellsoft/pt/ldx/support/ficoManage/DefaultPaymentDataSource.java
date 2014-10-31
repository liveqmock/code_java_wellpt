package com.wellsoft.pt.ldx.support.ficoManage;

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
 * Description: 付款条件维护
 * 
 * @author qiulb
 * @date 2014-8-11
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-11.1	qiulb		2014-8-1		Create
 * 2014-9-16.2  qiulb       2014-9-16       Change  
 * </pre>
 * 
 */
@Component
public class DefaultPaymentDataSource extends AbstractDataSourceProvider {
	@Autowired
	protected ISapService sapService;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.app.xzsp.biz.AbstractXZSPViewDataSource#getAllViewColumns()
	 */
	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns(){
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("zterm","zterm","付款条件码",null));
		viewColumns.add(generateCol("bukrs","bukrs","公司代码",null));
		viewColumns.add(generateCol("kunnr","kunnr","客户编码",null));
		viewColumns.add(generateCol("zvtext","zvtext","付款说明",null));
		viewColumns.add(generateCol("zearea","zearea","出口区域",null));
		viewColumns.add(generateCol("zkdgrp","zkdgrp","客户组",null));
		viewColumns.add(generateCol("zdeadline","zdeadline","期限",null));
		viewColumns.add(generateCol("waers","waers","货币码",null));		
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("zterm".equals(cond.getSearchField())){
				sql.append(" and zterm ='"+cond.getSearchValue()+"'");
			}
			if("bukrs".equals(cond.getSearchField())){
				sql.append(" and bukrs = '"+StringUtils.addLeftZero(cond.getSearchValue(),10)+"'");
			}
			if("kunnr".equals(cond.getSearchField())){
				sql.append(" kunnr = '"+cond.getSearchValue()+"'");
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
	public DataSourceColumn generateCol(String attributeName,String columnAlias,String columnName,ViewColumnType viewcolumntype){
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
		return "付款条件维护";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection,
	 *      java.lang.String, java.util.Map, java.lang.String,
	 *      com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> viewColumns,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zfmt0008 where "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(viewColumns, sql,
				queryParams, orderBy, pagingInfo, "", false);

		return items;

	}

}
