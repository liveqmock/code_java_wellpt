package com.wellsoft.pt.ldx.service.maindata;

import java.util.List;
import java.util.Map;

import com.wellsoft.pt.core.service.BaseService;
import com.wellsoft.pt.ldx.model.mainData.GcMessage;

public interface MainDataWlService extends BaseService {

	public List<GcMessage> findFactorys(String wlid);
	
	@SuppressWarnings("rawtypes")
	public Map saveWlgc(String s);
	
	@SuppressWarnings("rawtypes")
	public Map saveDefaultWlgc(String s);

}
