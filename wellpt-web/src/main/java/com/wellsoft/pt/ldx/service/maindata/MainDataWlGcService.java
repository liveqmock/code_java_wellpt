package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.mainData.WlGc;

@SuppressWarnings("rawtypes")
public interface MainDataWlGcService extends BaseService {

	public Map deleteObjects(String s);

	public WlGc getObject(String uuid);

	public List<Object> findStores(String client, String factory);

	public List<Object> findFactorys(String client);
	
	public Map getStores(String factory);
	
	public Map saveWlgc(WlGc wlgc);
	
	public Map transferObjects(String s);

}
