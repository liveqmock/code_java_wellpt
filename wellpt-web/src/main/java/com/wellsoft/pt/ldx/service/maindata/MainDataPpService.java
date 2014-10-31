package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mainData.Ppview;

public interface MainDataPpService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public Ppview getObject(String uuid);

	@SuppressWarnings("rawtypes")
	public Map savePpview(Ppview ppview);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map transferObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map findObjectFromSap(String wl, String factory);

	public void updateFromDefaultParam(Ppview objectView);

	public List<String> findOwnFactory();

	public List<String> findWlgcOwnFactory();

	@SuppressWarnings("rawtypes")
	public Map saveWlgcStatus(String wl, String factory, String mmsta);

}
