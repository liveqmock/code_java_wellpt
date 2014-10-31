package com.wellsoft.pt.ldx.service.sales.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.ldx.model.sales.DmsProduct;
import com.wellsoft.pt.ldx.service.impl.DmsServiceImpl;
import com.wellsoft.pt.ldx.service.sales.DmsProductService;

@Service
@Transactional
public class DmsProductServiceImpl extends DmsServiceImpl implements
		DmsProductService {

	@Override
	public DmsProduct getProduct(String matnr) {
		DmsProduct product = this.dmsDao.get(DmsProduct.class,
				StringUtils.leftPad(matnr, 18, '0'));
		return product;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map saveProduct(DmsProduct object) {
		HashMap hashmap = new HashMap();
		DmsProduct product = this.dmsDao.get(DmsProduct.class,
				StringUtils.leftPad(object.getMatnr(), 18, '0'));
		product.setStatus(1);
		product.setName(object.getName());
		product.setRemark(object.getRemark());
		product.setUnit(object.getUnit());
		product.setUnitcount(object.getUnitcount());
		this.dmsDao.save(product);
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map cancelProduct(String s) {
		HashMap hashmap = new HashMap();
		String[] arr = s.split(";");
		for (String matnr : arr) {
			DmsProduct product = this.dmsDao.get(DmsProduct.class,
					StringUtils.leftPad(matnr, 18, '0'));
			product.setStatus(0);
			this.dmsDao.save(product);
		}
		hashmap.put("state", "0");
		return hashmap;
	}
}
