package com.wellsoft.pt.ldx.service.sales;

import java.util.Map;

import com.wellsoft.pt.ldx.model.sales.Sellorder;
import com.wellsoft.pt.ldx.service.IDmsService;

public interface DmsOrderService extends IDmsService {

	public Sellorder getSellOrder(String objectUuid);

	@SuppressWarnings("rawtypes")
	public Map auditPass(Sellorder object);

	@SuppressWarnings("rawtypes")
	public Map auditNoPass(Sellorder object);
	
	@SuppressWarnings("rawtypes")
	public Map saveObject(Sellorder object);

}
