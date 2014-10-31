package com.wellsoft.pt.ldx.service.sales;

import java.util.Map;

import com.wellsoft.pt.ldx.model.sales.SellUser;
import com.wellsoft.pt.ldx.service.IDmsService;

public interface DmsUserService extends IDmsService {

	public SellUser getUser(String objectUuid);

	@SuppressWarnings("rawtypes")
	public Map deleteObjects(String s);
	
	@SuppressWarnings("rawtypes")
	public Map saveUser(SellUser object);

}
