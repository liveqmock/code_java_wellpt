package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mainData.Sdview;

public interface MainDataSdService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public Sdview getObject(String uuid);

	@SuppressWarnings("rawtypes")
	public Map saveSdview(Sdview objectview);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map transferObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map findObjectFromSap(String wl, String factory, String xszz,
			String fxqd);

	public void updateFromDefaultParam(Sdview objectView);

	public List<String> findOwnFactory();

}
