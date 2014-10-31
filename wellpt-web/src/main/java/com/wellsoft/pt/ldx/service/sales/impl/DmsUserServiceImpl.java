package com.wellsoft.pt.ldx.service.sales.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.star.uno.RuntimeException;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.ldx.model.sales.SellUser;
import com.wellsoft.pt.ldx.service.impl.DmsServiceImpl;
import com.wellsoft.pt.ldx.service.sales.DmsUserService;

@Service
@Transactional
public class DmsUserServiceImpl extends DmsServiceImpl implements
		DmsUserService {

	@Autowired
	public Md5PasswordEncoder passwordEncoder;

	@Autowired
	private SAPRfcService saprfcservice;

	@Override
	public SellUser getUser(String objectUuid) {
		SellUser object = this.dmsDao.get(SellUser.class, objectUuid);
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			SellUser object = this.dmsDao.get(SellUser.class, as[i]);
			object.setStatus(0);
			this.dmsDao.save(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveUser(SellUser object) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(object.getId())) {
			SellUser user = this.dmsDao.get(SellUser.class, object.getId());
			user.setZterm(object.getZterm());
			user.setKdgrp(object.getKdgrp());
			user.setKonda(object.getKonda());
			user.setBzirk(object.getBzirk());
			this.dmsDao.save(user);
			String doType = "1";
			transferSap(hashmap, user, doType);
		} else {
			object.setId(null);
			object.setCreatedate(new Date());
			object.setStatus(1);
			object.setDiscountrate(BigDecimal.ONE);
			object.setPwd(passwordEncoder.encodePassword(object.getPwd(), null));
			this.dmsDao.save(object);
			String doType = "0";
			transferSap(hashmap, object, doType);
		}

		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void transferSap(HashMap hashmap, SellUser user, String doType) {
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.element("KUNNR", user.getCustomernum());
		jObject.element("ANRED", "0003");
		jObject.element("NAME1", user.getCompanyname());
		jObject.element("WAERS", "CNY");
		jObject.element("LAND1", "CN");
		jObject.element("STREET", user.getCompanyaddr());
		jObject.element("FAX_NUMBER", user.getFax());
		jObject.element("ZNAME", user.getRealname());
		jObject.element("TEL_NUMBER", user.getTel());
		jObject.element("MOB_NUMBER", user.getMobile());
		jObject.element("SMTP_ADDR", user.getEmail());
		jObject.element("AKONT", "1122020100");
		jObject.element("VSBED", "01");
		jObject.element("VWERK", "5201");
		jObject.element("ZTERM", user.getZterm());
		jObject.element("KTGRD", "Z3");
		jObject.element("TAXKD", "1");
		jObject.element("BUKRS", "5200");
		jObject.element("VKORG", "5200");
		jObject.element("VTWEG", "30");
		jObject.element("SPART", "00");
		jObject.element("ZDO", doType);
		jObject.element("KDGRP", user.getKdgrp());
		jObject.element("KONDA", user.getKonda());
		jObject.element("BZIRK", user.getBzirk());
		jObject.element("KTOKD", "Z002");
		jObject.element("KALKS", "2");
		jObjects.add(jObject);
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_INPUT", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZSDI0011",
				util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN");
		JSONArray jObs = o.getJSONArray("row");
		for (Object ob : jObs) {
			JSONObject returnObject = (JSONObject) ob;
			if ("S".equals(returnObject.get("TYPE"))) {
				hashmap.put("type", "S");
			} else {
				hashmap.put("type", "E");
				hashmap.put("type", returnObject.get("MESSAGE").toString());
				throw new RuntimeException(returnObject.get("MESSAGE")
						.toString());
			}
		}
	}
}
