package com.wellsoft.pt.ldx.service.sales;

import java.util.Map;

import com.wellsoft.pt.ldx.model.sales.DmsProduct;
import com.wellsoft.pt.ldx.service.IDmsService;

public interface DmsProductService extends IDmsService {

	public DmsProduct getProduct(String matnr);
	
	@SuppressWarnings("rawtypes")
	public Map saveProduct(DmsProduct object);
	
	@SuppressWarnings("rawtypes")
	public Map cancelProduct(String s);

}
