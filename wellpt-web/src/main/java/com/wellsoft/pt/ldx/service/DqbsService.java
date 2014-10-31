package com.wellsoft.pt.ldx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;

public interface DqbsService extends BaseService {

	
	public List<QueryItem> queryItemsData(String hql,
			Map<String, Object> queryParams);
	
	public List<QueryItem> queryForItemBySql(
			Set<DataSourceColumn> viewColumns, String querySql,
			Map<String, Object> queryParams, String orderby,
			PagingInfo pagingInfo, Boolean isDistinct);
	
	
}
