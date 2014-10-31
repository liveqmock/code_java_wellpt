package com.wellsoft.pt.ldx.service.sales;

import java.util.Map;

import com.wellsoft.pt.ldx.model.sales.Bulletin;
import com.wellsoft.pt.ldx.service.IDmsService;

public interface DmsBulletinService extends IDmsService {

	public Bulletin getBulletin(String objectUuid);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);
	
	@SuppressWarnings("rawtypes")
	public Map saveBulletin(Bulletin object);

}
