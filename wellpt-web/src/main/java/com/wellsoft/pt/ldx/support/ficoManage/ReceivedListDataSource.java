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
 * Description: 到款资料列表
 * 
 * @author Heshi
 * @date 2014-8-28
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-8-28.1	Heshi		2014-8-28		Create
 * </pre>
 * 
 */
@Component
public class ReceivedListDataSource extends AbstractDataSourceProvider {
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
		viewColumns.add(generateCol("b.ename","ename","业务员",null));
		viewColumns.add(generateCol("a.bukrs","bukrs","公司代码",null));
		viewColumns.add(generateCol("a.kunnr","kunnr","客户编号",null));
		viewColumns.add(generateCol("a.sortl","sortl","客户简称",null));
		viewColumns.add(generateCol("a.zbelnr","zbelnr","凭证编号(流水号)",null));
		viewColumns.add(generateCol("d.bstkd","bstkd","合同号",null));
		viewColumns.add(generateCol("a.zdoip","zdoip","国际收支申报单号",null));
		viewColumns.add(generateCol("a.bldat","bldat","收款日期",null));
		viewColumns.add(generateCol("a.zcamount","zcamount","收款金额",null));
		viewColumns.add(generateCol("a.waers","waers","收款币别",null));
		viewColumns.add(generateCol("a.sgtxt","sgtxt","业务对象（摘要）",null));
		viewColumns.add(generateCol("a.hkont","hkont","银行类科目",null));
		viewColumns.add(generateCol("a.zdrs","zdrs","预收状态",null));
		viewColumns.add(generateCol("a.zcirs","zcirs","流转状态",null));
		viewColumns.add(generateCol("nvl(c.usrid,' ')","email","Email",null));
		viewColumns.add(generateCol("a.zremind","zremind","提醒",null));
		viewColumns.add(generateCol("a.zetime","zetime","最早通知时间",null));
		viewColumns.add(generateCol("a.zrdate","zrdate","回复时间",null));
		return viewColumns;
	}
	
	@Override
	public String getWhereSql(DyViewQueryInfoNew args) {
		StringBuffer sql = new StringBuffer();
		for(CondSelectAskInfoNew cond:args.getCondSelectList()){
			if(StringUtils.isBlank(cond.getSearchValue()))
				continue;
			if("b.ename".equals(cond.getSearchField())){
				sql.append(" and b.ename like '%"+cond.getSearchValue()+"%'");
			}
			if("a.zbelnr".equals(cond.getSearchField())){
				sql.append(" and a.zbelnr = '"+cond.getSearchValue()+"'");
			}
			if("a.bukrs".equals(cond.getSearchField())){
				sql.append(" and a.bukrs = '"+cond.getSearchValue()+"'");
			}
			if("a.kunnr".equals(cond.getSearchField())){
				sql.append(" and a.kunnr like '%"+cond.getSearchValue()+"'");
			}
			if("a.sortl".equals(cond.getSearchField())){
				sql.append(" and a.sortl like '%"+cond.getSearchValue()+"%'");
			}
			if("a.zdrs".equals(cond.getSearchField())){
				sql.append(" and a.zdrs = '"+cond.getSearchValue()+"'");
			}
			if("a.zcirs".equals(cond.getSearchField())){
				sql.append(" and a.zcirs = '"+cond.getSearchValue()+"'");
			}
		}
		return sql.toString().replaceFirst("and","");
	}
	
	private boolean isFicoAdmin(){
		return hasRole("ROLE_ADMIN");
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
	public String getModuleId() {
		return "receiveAcctList";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "到款资料";
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
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection,
	 *      java.lang.String, java.util.Map, java.lang.String,
	 *      com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Set<DataSourceColumn> dataSourceColumn, String whereHql, Map<String, Object> queryParams, String orderBy,
			PagingInfo pagingInfo) {
		String sql = " zfmt0003 a"
			+ " left join (select distinct pernr,ename from pa0001)b on a.zsname = b.pernr "
			+ " left join (select pernr,max(usrid) as usrid from pa0105 where usrty='MAIL' group by pernr) c on a.zsname = c.pernr "
			+ " left join (select zbelnr,max(bstkd) as bstkd from zfmt0004 where zrbl='A' and vbeln=' ' and zcamount<>zwoamt group by zbelnr) d on a.zbelnr = d.zbelnr "
			+ " where "
			+ whereHql;
		if(!isFicoAdmin()){
			sql+=" and a.zsname ='"+getUserCode()+"'";
		}
		List<QueryItem> items = sapService.queryItemBySql(dataSourceColumn, sql,
				queryParams, orderBy, pagingInfo, "a.mandt", false);
		return items;

	}

}
