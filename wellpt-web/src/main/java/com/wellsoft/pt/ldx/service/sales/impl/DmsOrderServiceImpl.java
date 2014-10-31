package com.wellsoft.pt.ldx.service.sales.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.star.uno.RuntimeException;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.ldx.model.sales.Sellorder;
import com.wellsoft.pt.ldx.model.sales.Sellorderline;
import com.wellsoft.pt.ldx.service.impl.DmsServiceImpl;
import com.wellsoft.pt.ldx.service.sales.DmsOrderService;
import com.wellsoft.pt.ldx.util.DateUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class DmsOrderServiceImpl extends DmsServiceImpl implements
		DmsOrderService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Override
	public Sellorder getSellOrder(String objectUuid) {
		Sellorder object = this.dmsDao.get(Sellorder.class,
				Integer.valueOf(objectUuid));
		return object;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map auditPass(Sellorder object) {
		HashMap hashmap = new HashMap();
		Integer status = 1;
		updateObject(object, status, hashmap);
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map auditNoPass(Sellorder object) {
		HashMap hashmap = new HashMap();
		Integer status = 2;
		updateObject(object, status, hashmap);
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveObject(Sellorder object) {
		HashMap hashmap = new HashMap();
		updateObject(object, null, hashmap);
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateObject(Sellorder object, Integer status, HashMap hashmap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Sellorder order = this.dmsDao.get(Sellorder.class, object.getId());
		List<Sellorderline> orderlines = new ArrayList<Sellorderline>();
		for (Sellorderline line : object.getOrderlines()) {
			if (null != line) {
				Sellorderline orderline = this.dmsDao.get(Sellorderline.class,
						line.getId());
				orderline.setDj(line.getDj());
				orderline.setJgdw(line.getJgdw());
				if (StringUtils.isNotEmpty(line.getJhrq1())) {
					try {
						orderline.setJhrq(sdf.parse(line.getJhrq1()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					orderline.setJhrq(null);
				}
				orderline.setXmlx(line.getXmlx());
				orderlines.add(orderline);
			}
		}
		order.setDdlx(object.getDdlx());
		order.setSszz(object.getSszz());
		order.setFxqd(object.getFxqd());
		order.setCpz(object.getCpz());
		order.setSoudf(object.getSoudf());
		order.setSongdf(object.getSongdf());
		order.setFkf(object.getFkf());
		if (StringUtils.isNotEmpty(object.getJhrq1())) {
			try {
				order.setJhrq(sdf.parse(object.getJhrq1()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			order.setJhrq(null);
		}
		order.setHblx(object.getHblx());
		order.setKhz(object.getKhz());
		order.setFktj(object.getFktj());
		order.setBstkd(object.getBstkd());
		order.setRemark(object.getRemark());
		if (null != status) {
			order.setStatus(status);
			order.setAuditname(SpringSecurityUtils.getCurrentUserName());
			order.setAuditdate(new Date());
		}
		order.setOrderlines(orderlines);
		this.dmsDao.save(order);
		this.dmsDao
				.getSession()
				.createSQLQuery(
						"delete from sellorderline where orderid is null")
				.executeUpdate();
		if (1 == order.getStatus()) {
			SAPRfcJson util = new SAPRfcJson("");
			setHeadTable(util, order);
			setItemTable(util, order);
			SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
					"ZSDI0013", util.getRfcJson(), 1, -1, null);
			JSONObject o = rfcjson.getRecord("PT_RETURN");
			JSONArray jObs = o.getJSONArray("row");
			for (Object ob : jObs) {
				JSONObject returnObject = (JSONObject) ob;
				if ("S".equals(returnObject.get("TYPE"))) {
					order.setDdbh(returnObject.get("VBELN").toString());
					hashmap.put("type", "S");
				} else {
					hashmap.put("type", "E");
					hashmap.put("type", returnObject.get("MESSAGE").toString());
					throw new RuntimeException(returnObject.get("MESSAGE")
							.toString());
				}
			}
		} else {
			hashmap.put("type", "S");
		}
	}

	private void setHeadTable(SAPRfcJson util, Sellorder order) {
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.element("ZNUM", order.getId());
		jObject.element("BSTKD", order.getBstkd());
		jObject.element("VBELN", order.getDdbh());
		jObject.element("AUART", order.getDdlx());
		jObject.element("VKORG", order.getSszz());
		jObject.element("VTWEG", order.getFxqd());
		jObject.element("SPART", order.getCpz());
		jObject.element("KUNNR", order.getSoudf());
		jObject.element("KUNWE", order.getSongdf());
		jObject.element("KUNRG", order.getFkf());
		DateUtils.addDateToJson(jObject, order.getJhrq(), "VDATU");
		jObject.element("WAERK", order.getHblx());
		jObject.element("KVGR3", order.getKhz());
		jObject.element("ZTERM", order.getFktj());
		jObjects.add(jObject);
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_HEAD", rowObject.toString());
		}
	}

	private void setItemTable(SAPRfcJson util, Sellorder order) {
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		JSONObject jObject = new JSONObject();
		for (Sellorderline line : order.getOrderlines()) {
			jObject.element("ZNUM", order.getId());
			jObject.element("MATNR", line.getProduct().getMatnr());
			jObject.element("KWMENG", line.getSl());
			jObject.element("KBETR", line.getDj());
			jObject.element("KPEIN", line.getJgdw());
			DateUtils.addDateToJson(jObject, line.getJhrq(), "ETDAT");
			jObject.element("PSTYV", line.getXmlx());
			jObject.element("WAERK", line.getHblx());
			jObject.element("KSCHL", line.getTjlx());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_ITEM", rowObject.toString());
		}
	}
}
