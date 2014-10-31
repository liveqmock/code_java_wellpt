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
 * Description: 客户对应表维护
 * 
 * @author Heshi
 * @date 2014-8-25
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-25.1	Heshi		2014-8-25		Create
 * </pre>
 * 
 */
@Component
public class CustomerTableDataSource extends AbstractDataSourceProvider {
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
		viewColumns.add(generateCol("a.bukrs","bukrs","公司代码",null));
		viewColumns.add(generateCol("a.kunnr","kunnr","客户编码",null));
		viewColumns.add(generateCol("a.sortl","sortl","客户简称",null));
		viewColumns.add(generateCol("t1.ename","rsm","RSM",null));
		viewColumns.add(generateCol("t2.ename","om","OM",null));
		viewColumns.add(generateCol("t3.ename","aa","AA",null));
		viewColumns.add(generateCol("t4.ename","ae","AE",null));
		viewColumns.add(generateCol("t5.ename","zr","应收人员",null));
		viewColumns.add(generateCol("a.zday","zday","放宽期限",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("a.bukrs".equals(cond.getSearchField())){
				sql.append(" and a.bukrs ='"+cond.getSearchValue()+"'");
			}
			if("a.kunnr".equals(cond.getSearchField())){
				sql.append(" and a.kunnr = '"+StringUtils.addLeftZero(cond.getSearchValue(),10)+"'");
			}
			if("a.sortl".equals(cond.getSearchField())){
				sql.append(" a.sortl like '%"+cond.getSearchValue()+"%'");
			}
			if("t1.ename".equals(cond.getSearchField())){
				sql.append(" t1.ename like '%"+cond.getSearchValue()+"%'");
			}
			if("t2.ename".equals(cond.getSearchField())){
				sql.append(" t2.ename like '%"+cond.getSearchValue()+"%'");
			}
			if("t3.ename".equals(cond.getSearchField())){
				sql.append(" t3.ename like '%"+cond.getSearchValue()+"%'");
			}
			if("t4.ename".equals(cond.getSearchField())){
				sql.append(" t4.ename like '%"+cond.getSearchValue()+"%'");
			}
			if("t5.ename".equals(cond.getSearchField())){
				sql.append(" t5.ename like '%"+cond.getSearchValue()+"%'");
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
		return "客户对应表维护";
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
		String sql = "  zfmt0007 a " 
				+ " left join (select distinct mandt,pernr,ename from pa0001) t1 on a.mandt=t1.mandt and a.zrsm=t1.pernr"
				+ " left join (select distinct mandt,pernr,ename from pa0001) t2 on a.mandt=t2.mandt and a.zom=t2.pernr"
				+ " left join (select distinct mandt,pernr,ename from pa0001) t3 on a.mandt=t3.mandt and a.zaa=t3.pernr"
				+ " left join (select distinct mandt,pernr,ename from pa0001) t4 on a.mandt=t4.mandt and a.zae=t4.pernr"
				+ " left join (select distinct mandt,pernr,ename from pa0001) t5 on a.mandt=t5.mandt and a.zrname=t5.pernr"
				+ " where "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(viewColumns, sql,
				queryParams, orderBy, pagingInfo, "a.mandt", false);
		return items;

	}

}
