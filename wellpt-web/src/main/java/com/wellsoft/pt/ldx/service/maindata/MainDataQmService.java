package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mainData.Qmview;

public interface MainDataQmService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public Qmview getObject(String uuid);

	@SuppressWarnings("rawtypes")
	public Map saveQmview(Qmview objectview);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map transferObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map findObjectFromSap(String wl, String factory);

	public void updateFromDefaultParam(Qmview objectView);

	public List<String> findOwnFactory();

}
