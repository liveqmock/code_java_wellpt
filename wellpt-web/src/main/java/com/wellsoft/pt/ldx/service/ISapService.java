package com.wellsoft.pt.ldx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.org.entity.User;

public interface ISapService extends BaseService {

	/**
	 * 
	 * 执行sql语句
	 * 
	 * @param sql
	 */
	public void execSql(String sql);
	
	/**
	 * 
	 * 根据HQL语句查询列表
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findByHql(String hql);

	/**
	 * 
	 * sql语句查询
	 * 
	 * @param sql
	 * @return 结果列表
	 */
	public List<Object> findListBySql(String sql);

	/**
	 * 
	 * 根据用户ID获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public User getCurrentUser(String userId);
	
	/**
	 * 
	 * 通过sql分页查询数据列表
	 * 
	 * @param viewColumns
	 * @param querySql
	 * @param queryParams
	 * @param orderby
	 * @param pagingInfo
	 * @param clientString
	 * @return
	 */
	public List<QueryItem> queryForItemBySql(
			Set<DataSourceColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, String clientString, Boolean isDistinct);

	public List<QueryItem> queryQueryItemData(String string,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public List<QueryItem> queryItemBySql(Set<DataSourceColumn> dataSourceColumn, String sql,
			Map<String, Object> queryParams, String orderBy, PagingInfo pagingInfo, String string, boolean b);
	
	/**
	 * 
	 * 分页查询
	 * 
	 * @param sql
	 * @param pagingInfo
	 * @return
	 */
	public List<Object> findPageListBySql(String sql,PagingInfo pagingInfo);

}
