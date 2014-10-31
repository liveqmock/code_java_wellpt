package com.wellsoft.pt.ldx.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;

public interface ISrmService extends BaseService {

	/**
	 * 
	 * 执行sql语句
	 * 
	 * @param sql
	 */
	public void execSql(String sql);

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
			Collection<ViewColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, Boolean isDistinct);

	public List<QueryItem> queryQueryItemData(String string,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

}
