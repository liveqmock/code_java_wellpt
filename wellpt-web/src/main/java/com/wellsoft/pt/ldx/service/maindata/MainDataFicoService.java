package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;

public interface MainDataFicoService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public Ficoview getObject(String uuid);

	@SuppressWarnings("rawtypes")
	public Map saveFicoview(Ficoview objectview);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map transferObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map findObjectFromSap(String wl, String factory);

	public void updateFromDefaultParam(Ficoview objectView);

	public List<String> findOwnFactory();

}
