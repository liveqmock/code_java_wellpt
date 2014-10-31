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
 * Description: 未收汇手工资料
 * 
 * @author Heshi
 * @date 2014-8-26
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-26.1	Heshi		2014-8-26		Create
 * </pre>
 * 
 */
@Component
public class NonReceiveDataSource extends AbstractDataSourceProvider {
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
		viewColumns.add(generateCol("vbeln","vbeln","外向交货单",null));
		viewColumns.add(generateCol("vbelv","vbelv","报关单号",null));
		viewColumns.add(generateCol("lfimg","lfimg","报关数量",null));
		viewColumns.add(generateCol("zeamt","zeamt","报关金额",null));
		viewColumns.add(generateCol("zddate","zddate","申报日期",null));
		viewColumns.add(generateCol("zclfimg","zclfimg","折算数量",null));
		viewColumns.add(generateCol("zcost","zcost","报关海运费",null));
		viewColumns.add(generateCol("zmodel","zmodel","柜型",null));
		viewColumns.add(generateCol("zterm","zterm","付款条件代码",null));
		viewColumns.add(generateCol("zvtext","zvtext","扣款类型",null));
		viewColumns.add(generateCol("zcamt","zcamt","扣款金额",null));
		viewColumns.add(generateCol("znote","znote","备注",null));
		viewColumns.add(generateCol("kunnr","kunnr","客户编号",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("vbeln".equals(cond.getSearchField())){
				sql.append(" and vbeln = '"+StringUtils.addLeftZero(cond.getSearchValue(),10)+"'");
			}
			if("vbelv".equals(cond.getSearchField())){
				sql.append(" and vbelv like '%"+cond.getSearchValue()+"%'");
			}
			if("kunnr".equals(cond.getSearchField())){
				sql.append(" and kunnr like '%"+cond.getSearchValue()+"'");
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
		return "未收汇手工资料";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection,
	 *      java.lang.String, java.util.Map, java.lang.String,
	 *      com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn, String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = "  zfmt0017 where "+whereHql;
		List<QueryItem> items = sapService.queryItemBySql(dataSourceColumn, sql,
				queryParams, orderBy, pagingInfo, "mandt", false);
		return items;

	}

}
