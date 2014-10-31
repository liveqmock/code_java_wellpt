package com.wellsoft.pt.ldx.service;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;

public interface WlGcService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);
	
	public List<QueryItem> queryItemsData(String hql,
			Map<String, Object> queryParams);

	public List<String> findOwnFactory();
	
}
