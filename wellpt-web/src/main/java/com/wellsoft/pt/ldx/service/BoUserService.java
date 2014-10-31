package com.wellsoft.pt.ldx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.org.entity.User;
public interface BoUserService extends BaseService{
	public void execSql(String sql);
	public List<QueryItem> queryForItemBySql(
			Set<DataSourceColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, Boolean isDistinct);
	public List<Object> findListBySql(String sql);
	@SuppressWarnings("rawtypes")
	public Map saveBoview(User objectview);
}
