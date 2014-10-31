package com.wellsoft.pt.ldx.service.maindata.impl;

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

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.ldx.dao.maindata.WlGcDao;
import com.wellsoft.pt.ldx.model.mainData.Baoguanview;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mainData.Mmview;
import com.wellsoft.pt.ldx.model.mainData.PlanView;
import com.wellsoft.pt.ldx.model.mainData.Ppview;
import com.wellsoft.pt.ldx.model.mainData.Qmview;
import com.wellsoft.pt.ldx.model.mainData.Sdview;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlGcService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class MainDataWlGcServiceImpl extends SapServiceImpl implements
		MainDataWlGcService {

	@Autowired
	private SAPDbConfig sapConnectConfig;

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private WlGcDao wlGcDao;

	@SuppressWarnings("unchecked")
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			WlGc object = this.dao.get(WlGc.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@Override
	public WlGc getObject(String uuid) {
		return this.dao.get(WlGc.class, uuid);
	}

	@Override
	public List<Object> findStores(String client, String factory) {
		String sql = "select lgort,lgobe from t001l where mandt='" + client
				+ "' and werks='" + factory + "'";
		return this.findListBySql(sql);
	}

	@Override
	public List<Object> findFactorys(String client) {
		String sql = "select werks,name1 from t001w where werks!='0001' and mandt='"
				+ client + "' order by werks asc";
		return this.findListBySql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getStores(String factory) {
		HashMap hashmap = new HashMap();
		List<Object> objects = new ArrayList<Object>();
		String[] o = { "", "" };
		objects.add(o);
		if (StringUtils.isNotEmpty(factory)) {
			String sql = "select lgort,lgobe from t001l where mandt='"
					+ sapConnectConfig.getClient() + "' and werks='" + factory
					+ "'";
			objects.addAll(this.findListBySql(sql));
		}
		hashmap.put("stores", objects);
		return hashmap;
	}

	@Override
	public Map saveWlgc(WlGc wlgc) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(wlgc.getUuid())) {
			WlGc object = this.dao.get(WlGc.class, wlgc.getUuid());
			object.setWl(wlgc.getWl());
			object.setFactory(wlgc.getFactory());
			object.setScstore(wlgc.getScstore());
			object.setWbstore(wlgc.getWbstore());
			object.setShortdesc(wlgc.getShortdesc());
			object.setModifier(SpringSecurityUtils.getCurrentUserId());
			object.setModifyTime(new Date());
			this.dao.save(object);
		} else {
			wlgc.setCreator(SpringSecurityUtils.getCurrentUserId());
			wlgc.setCreateTime(new Date());
			wlgc.setModifier(SpringSecurityUtils.getCurrentUserId());
			wlgc.setModifyTime(new Date());
			this.dao.save(wlgc);
		}
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		Map<String, String> uuidMap = new HashMap<String, String>();
		for (int i = 0; i < as.length; i++) {
			WlGc object = this.dao.get(WlGc.class, as[i]);
			uuidMap.put(object.getWl() + "-" + object.getFactory(),
					object.getUuid());
			JSONObject jObject = new JSONObject();
			jObject.element("MATNR",
					StringUtils.leftPad(object.getWl(), 18, '0'));
			jObject.element("WERKS", object.getFactory());
			if (StringUtils.isNotEmpty(object.getScstore())) {
				jObject.element("LGORT", object.getScstore());
			} else {
				if (StringUtils.isNotEmpty(object.getWbstore())) {
					jObject.element("LGORT", object.getWbstore());
				}
			}
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_WK_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_WERKS_VIEW", util.getRfcJson(), 1, -1, null);
		List<String[]> errStrs = new ArrayList<String[]>();
		JSONObject jObject = rfcjson.getRecord("PT_RETURN");
		JSONArray jObs = jObject.getJSONArray("row");
		List<WlGc> viewObjects = new ArrayList<WlGc>();
		for (int i = 0; i < jObs.size(); i++) {
			if ("P".equals(((JSONObject) jObs.get(i)).get("TYPE").toString())) {
				String[] os = new String[1];
				os[0] = ((JSONObject) jObs.get(i)).get("MESSAGE").toString();
				errStrs.add(os);
			}
			if ("R".equals(((JSONObject) jObs.get(i)).get("TYPE").toString())) {
				String[] messes = ((JSONObject) jObs.get(i)).get("MESSAGE")
						.toString().split("-");
				List<WlGc> objects = wlGcDao.find(
						"from WlGc where wl=? and factory=?", messes[0].trim(),
						messes[1].trim());
				viewObjects.add(objects.get(0));
				for (WlGc wlGc : objects) {
					wlGcDao.delete(wlGc);
				}
			}
		}
		createViews(viewObjects);
		hashmap.put("errStrs", errStrs);
		return hashmap;
	}

	private void createViews(List<WlGc> viewObjects) {
		for (WlGc wlGc : viewObjects) {
			createPlanView(wlGc);
			createPpView(wlGc);
			createFicoView(wlGc);
			createSdView(wlGc);
			createQmView(wlGc);
			createMmView(wlGc);
		}
		createBaoGuanView(viewObjects);
	}

	private void createBaoGuanView(List<WlGc> viewObjects) {
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (WlGc wlgc : viewObjects) {
			JSONObject jObject = new JSONObject();
			jObject.element("MATNR", StringUtils.leftPad(wlgc.getWl(), 18, '0'));
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_MAT", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0004_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_MARA");
		JSONArray jObs = o.getJSONArray("row");
		for (Object ob : jObs) {
			JSONObject object = (JSONObject) ob;
			if (!"X".equals(object.get("ZBG_CN").toString())
					&& !"X".equals(object.get("ZBG_EN").toString())) {
				Baoguanview bg = new Baoguanview();
				bg.setWl(object.get("MATNR").toString());
				bg.setShortdesc(object.get("MAKTX").toString());
				bg.setCreator(SpringSecurityUtils.getCurrentUserId());
				bg.setCreateTime(new Date());
				bg.setModifier(SpringSecurityUtils.getCurrentUserId());
				bg.setModifyTime(new Date());
				this.dao.save(bg);
			}
		}
	}

	private void createMmView(WlGc wlGc) {
		Mmview object = new Mmview();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}

	private void createQmView(WlGc wlGc) {
		Qmview object = new Qmview();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}

	private void createSdView(WlGc wlGc) {
		Sdview object = new Sdview();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}

	private void createFicoView(WlGc wlGc) {
		Ficoview object = new Ficoview();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}

	private void createPlanView(WlGc wlGc) {
		PlanView object = new PlanView();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setScstore(wlGc.getScstore());
		object.setWbstore(wlGc.getWbstore());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}

	private void createPpView(WlGc wlGc) {
		Ppview object = new Ppview();
		object.setWl(wlGc.getWl());
		object.setShortdesc(wlGc.getShortdesc());
		object.setFactory(wlGc.getFactory());
		object.setScstore(wlGc.getScstore());
		object.setWbstore(wlGc.getWbstore());
		object.setCreator(SpringSecurityUtils.getCurrentUserId());
		object.setCreateTime(new Date());
		object.setModifier(SpringSecurityUtils.getCurrentUserId());
		object.setModifyTime(new Date());
		this.dao.save(object);
	}
}
