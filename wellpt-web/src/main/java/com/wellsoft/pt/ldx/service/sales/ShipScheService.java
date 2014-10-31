package com.wellsoft.pt.ldx.service.sales;

import java.util.Map;

import com.wellsoft.pt.ldx.model.sales.ShipScheManage;
import com.wellsoft.pt.ldx.service.ISapService;

public interface ShipScheService extends ISapService {

	@SuppressWarnings("rawtypes")
	public Map saveShipSche(ShipScheManage object);

	@SuppressWarnings("rawtypes")
	public Map updateObjects(String s);

	@SuppressWarnings("rawtypes")
	public Map findShipSche(String vbeln);

}
