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
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.service.ISapService;
import com.wellsoft.pt.ldx.util.StringUtils;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 发票跟踪数据源
 * 
 * @author Heshi
 * @date 2014-8-27
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-27.1	Heshi		2014-8-27		Create
 * </pre>
 * 
 */
@Component
public class BillTrackDataSource extends AbstractDataSourceProvider {
	@Autowired
	protected ISapService sapService;
	@Autowired
	private SecurityApiFacade securityApiFacade;
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.app.xzsp.biz.AbstractXZSPViewDataSource#getAllViewColumns()
	 */
	@Override
	public Set<DataSourceColumn> getAllDataSourceColumns() {
		Set<DataSourceColumn> viewColumns = new HashSet<DataSourceColumn>();
		viewColumns.add(generateCol("t15.zbelnr","zbelnr","凭证编号(流水号)",null));
		viewColumns.add(generateCol("t15.vbeln","vbeln","外向交货单",null));
		viewColumns.add(generateCol("pa.ename","ar","AR",null));
		viewColumns.add(generateCol("t3.bukrs","bukrs","公司代码",null));
		viewColumns.add(generateCol("t3.kunnr","kunnr","客户编号",null));
		viewColumns.add(generateCol("t3.sortl","sortl","客户简称",null));
		viewColumns.add(generateCol("t3.bldat","bldat","收款日期",null));
		viewColumns.add(generateCol("t15.zndate","zndate","通知日期",null));
		viewColumns.add(generateCol("t15.fksta","fksta","已开发票",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("t15.zbelnr".equals(cond.getSearchField())){
				sql.append(" and t15.zbelnr ='"+cond.getSearchValue()+"'");
			}
			if("t15.vbeln".equals(cond.getSearchField())){
				sql.append(" and t15.vbeln = '"+StringUtils.addLeftZero(cond.getSearchValue(),10)+"'");
			}
			if("t15.fksta".equals(cond.getSearchField())){
				sql.append(" t15.fksta = '"+cond.getSearchValue()+"'");
			}
		}
		return sql.toString().replaceFirst("and","");
	}
	
	private boolean isFicoAdmin(){
		return hasRole("ROLE_ADMIN");
	}
	
	private String getUserCode(){
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		return userDetail.getEmployeeNumber(); 
	}
	
	private boolean hasRole(String roleName){
		String userId = ((UserDetails)SpringSecurityUtils.getAuthentication().getPrincipal()).getUserId();
		return securityApiFacade.hasRole(userId,roleName);
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
		return "SapBillTrack";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "发票跟踪";
	}

	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn, String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zfmt0015 t15 left join zfmt0003 t3 on t15.zbelnr = t3.zbelnr"
			+ " left join zfmt0007 t7 on t3.bukrs = t7.bukrs and t3.kunnr = t7.kunnr"
			+ " left join (select distinct mandt,pernr,ename from pa0001) pa on pa.pernr = t7.zrname and pa.mandt=t7.mandt"
			+ " where "+whereHql;
		if(!isFicoAdmin()){
			sql+=" and t7.zrname='"+getUserCode()+"'";
		}
		List<QueryItem> items = sapService.queryItemBySql(dataSourceColumn, sql,
				queryParams, orderBy, pagingInfo, "t15.mandt", false);
		return items;
	}
}
