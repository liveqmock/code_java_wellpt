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
 * Description: 保额维护
 * 
 * @author qiulb
 * @date 2014-8-6
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-6.1	qiulb		2014-8-6		Create
 * 2014-9-16.1  qiulb       2014-9-19       Change
 * </pre>
 * 
 */
@Component
public class DefaultBaoeDataSource extends AbstractDataSourceProvider {
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
		viewColumns.add(generateCol("a.kunnr","kunnr","客户编号",null));
		viewColumns.add(generateCol("b.sortl","sortl","客户简称",null));
		viewColumns.add(generateCol("a.zbemt","zbemt","保额",null));
		viewColumns.add(generateCol("a.waers","waers","币种",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("a.kunnr".equals(cond.getSearchField())){
				sql.append(" and a.kunnr like'%"+cond.getSearchValue()+"'");
			}
			if("b.sortl".equals(cond.getSearchField())){
				sql.append(" and b.sortl like '%"+cond.getSearchValue()+"%'");
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
		return "保额维护";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection,
	 *      java.lang.String, java.util.Map, java.lang.String,
	 *      com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn,
			String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zfmt0016_be a left join kna1 b on a.kunnr = b.kunnr where a.mandt = b.mandt and "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(dataSourceColumn, sql,
				queryParams, orderBy, pagingInfo, "a.mandt", false);
		for (QueryItem item : items) {
			String kunnr = item.getString("kunnr");
			item.put("kunnr", StringUtils.removeLeftZero(kunnr));
		}
		return items;

	}
}
