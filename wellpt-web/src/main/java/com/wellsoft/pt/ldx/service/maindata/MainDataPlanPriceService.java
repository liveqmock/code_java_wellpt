package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.ldx.model.mainData.PlanView;

public interface MainDataPlanPriceService extends BaseService {

	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo);

	public PlanView getObject(String uuid);

	@SuppressWarnings("rawtypes")
	public Map savePlanView(PlanView planView);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map transferObjects(String s);

	public void updateFromDefaultParam(PlanView planView);

	public List<String> findOwnFactory();

}
