package com.wellsoft.pt.ldx.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.informix.util.stringUtil;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
@SuppressWarnings("rawtypes")
public interface OrderManageService extends BaseService{
	public List getListForSQL(String sql);
	public List orderLineItemDetail(Collection<DataSourceColumn> viewColumns,
			String whereHql,PagingInfo pagingInfo);
	public Map updateOrderLine(String vbeln,String posnr,String zhfkr,String zyhrq,String zyhjg,String zyhbz
			,String zchsl,String zchrq,String zyqbm,String zycdl,String zyqbz,String omchjh);
	public List getListByPage(String sql,PagingInfo pagingInfo);
	public List productionSearch(Collection<DataSourceColumn> viewColumns,
			String whereHql,PagingInfo pagingInfo);
	public List shipManage(Collection<DataSourceColumn> viewColumns,
			String whereHql,PagingInfo pagingInfo);
	public List orderStatusOm(Collection<DataSourceColumn> viewColumns,
			String whereHql,PagingInfo pagingInfo);
	public List getOrderListbyNativePage(Collection<DataSourceColumn> viewColumns,
			String whereHql, PagingInfo pagingInfo);
}
