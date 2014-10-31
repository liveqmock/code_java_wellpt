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

import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.dao.maindata.FicoViewDao;
import com.wellsoft.pt.ldx.dao.maindata.MmViewDao;
import com.wellsoft.pt.ldx.dao.maindata.PlanViewDao;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.model.mainData.Mmview;
import com.wellsoft.pt.ldx.model.mainData.PlanView;
import com.wellsoft.pt.ldx.model.mainData.Ppview;
import com.wellsoft.pt.ldx.service.maindata.MainDataPpService;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.service.UserService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataPpServiceImpl extends BaseServiceImpl implements
		MainDataPpService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private PlanViewDao planViewDao;

	@Autowired
	private MmViewDao mmViewDao;

	@Autowired
	private FicoViewDao ficoViewDao;

	@Autowired
	private UserService userService;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Ppview getObject(String uuid) {
		return this.dao.get(Ppview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map savePpview(Ppview ppview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(ppview.getUuid())) {
			Ppview object = this.dao.get(Ppview.class, ppview.getUuid());
			object.setWl(ppview.getWl());
			object.setShortdesc(ppview.getShortdesc());
			object.setFactory(ppview.getFactory());
			object.setScstore(ppview.getScstore());
			object.setWbstore(ppview.getWbstore());
			object.setPpmrp2cglx(ppview.getPpmrp2cglx());
			object.setPpmrp1srz(ppview.getPpmrp1srz());
			object.setPpbstma(ppview.getPpbstma());
			object.setPpgzjhbzyc(ppview.getPpgzjhbzyc());
			object.setPpgzjhcswj(ppview.getPpgzjhcswj());
			object.setPpgzjhgdyc(ppview.getPpgzjhgdyc());
			object.setPpgzjhscgly(ppview.getPpgzjhscgly());
			object.setPpmrp1control(ppview.getPpmrp1control());
			object.setPpmrp1group(ppview.getPpmrp1group());
			object.setPpmrp1pldx(ppview.getPpmrp1pldx());
			object.setPpmrp1type(ppview.getPpmrp1type());
			object.setPpmrp2aqkc(ppview.getPpmrp2aqkc());
			object.setPpmrp2bjm(ppview.getPpmrp2bjm());
			object.setPpmrp2fc(ppview.getPpmrp2fc());
			object.setPpmrp2jhjhsj(ppview.getPpmrp2jhjhsj());
			object.setPpmrp2shclsj(ppview.getPpmrp2shclsj());
			object.setPpmrp2tscgl(ppview.getPpmrp2tscgl());
			object.setPpmrp2zzscsj(ppview.getPpmrp2zzscsj());
			object.setPpmrp3clz(ppview.getPpmrp3clz());
			object.setPpmrp3kyxjc(ppview.getPpmrp3kyxjc());
			object.setPpmrp3nxxhqj(ppview.getPpmrp3nxxhqj());
			object.setPpmrp3xhms(ppview.getPpmrp3xhms());
			object.setPpmrp3xqxhqj(ppview.getPpmrp3xqxhqj());
			object.setPpmrp4bjfp(ppview.getPpmrp4bjfp());
			object.setPpmrp4dljz(ppview.getPpmrp4dljz());
			object.setModifier(SpringSecurityUtils.getCurrentUserId());
			object.setModifyTime(new Date());
			object.setStatus("1");
			this.dao.save(object);
			relationUpdate(object);
		} else {
			ppview.setCreator(SpringSecurityUtils.getCurrentUserId());
			ppview.setCreateTime(new Date());
			ppview.setModifier(SpringSecurityUtils.getCurrentUserId());
			ppview.setModifyTime(new Date());
			ppview.setStatus("1");
			this.dao.save(ppview);
			relationUpdate(ppview);
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Ppview object = this.dao.get(Ppview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Ppview> bgObjects = new HashMap<String, Ppview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Ppview object = this.dao.get(Ppview.class, as[i]);
			bgObjects.put(String.valueOf(i), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Ppview objectView = bgObjects.get(key);
			List<PlanView> planviews = planViewDao.find(
					"from PlanView where wl=? and factory=?",
					objectView.getWl(), objectView.getFactory());
			if (null != planviews && planviews.size() > 0) {
				hashmap.put(objectView.getUuid(), objectView.getWl() + ","
						+ objectView.getFactory() + ",对应的计划价格视图未维护，不能传递SAP");
				continue;
			}
			List<Mmview> mmviews = mmViewDao.find(
					"from Mmview where wl=? and factory=?", objectView.getWl(),
					objectView.getFactory());
			if (null != mmviews && mmviews.size() > 0) {
				hashmap.put(objectView.getUuid(), objectView.getWl() + ","
						+ objectView.getFactory() + ",对应的mm视图未维护，不能传递SAP");
				continue;
			}
			jObject.element("ID", key);
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("PLANT", objectView.getFactory());
			jObject.element("MRP_TYPE", objectView.getPpmrp1type());
			jObject.element("MRP_CTRLER", objectView.getPpmrp1control());
			jObject.element("LOTSIZEKEY", objectView.getPpmrp1pldx());
			jObject.element("MRP_GROUP", objectView.getPpmrp1group());
			jObject.element("PROC_TYPE", objectView.getPpmrp2cglx());
			jObject.element("SPPROCTYPE", objectView.getPpmrp2tscgl());
			jObject.element("BACKFLUSH", objectView.getPpmrp2fc());
			jObject.element("ISS_ST_LOC", objectView.getScstore());
			jObject.element("SLOC_EXPRC", objectView.getWbstore());
			jObject.element("INHSEPRODT", objectView.getPpmrp2zzscsj());
			jObject.element("PLND_DELRY", objectView.getPpmrp2jhjhsj());
			jObject.element("GR_PR_TIME", objectView.getPpmrp2shclsj());
			jObject.element("SM_KEY", objectView.getPpmrp2bjm());
			jObject.element("SAFETY_STK", objectView.getPpmrp2aqkc());
			jObject.element("PLAN_STRGP", objectView.getPpmrp3clz());
			jObject.element("CONSUMMODE", objectView.getPpmrp3xhms());
			jObject.element("FWD_CONS", objectView.getPpmrp3xqxhqj());
			jObject.element("BWD_CONS", objectView.getPpmrp3nxxhqj());
			jObject.element("AVAILCHECK", objectView.getPpmrp3kyxjc());
			jObject.element("COMP_SCRAP", objectView.getPpmrp4bjfp());
			jObject.element("DEP_REQ_ID", objectView.getPpmrp4dljz());
			jObject.element("PRODPROF", objectView.getPpgzjhcswj());
			jObject.element("PRODUCTION_SCHEDULER", objectView.getPpgzjhscgly());
			jObject.element("ROUND_VAL", objectView.getPpmrp1srz());
			jObject.element("UNDER_TOL", objectView.getPpgzjhbzyc());
			jObject.element("OVER_TOL", objectView.getPpgzjhgdyc());
			jObject.element("BSTMA", objectView.getPpbstma());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_PP_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_PP_VIEW", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("pp");
				log.setWl(bgObjects.get(returnObject.get("ID")).getWl());
				log.setFactory(bgObjects.get(returnObject.get("ID"))
						.getFactory());
				log.setContent(JSONObject.fromObject(
						bgObjects.get(returnObject.get("ID"))).toString());
				this.dao.save(log);
				this.dao.delete(bgObjects.get(returnObject.get("ID")));
			} else {
				hashmap.put(returnObject.get("ID"), returnObject.get("MESSAGE"));
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map findObjectFromSap(String wl, String factory) {
		HashMap hashmap = new HashMap();
		SAPRfcJson util = new SAPRfcJson("");
		util.setField("PI_MATNR", wl);
		util.setField("PI_WERKS", factory);
		util.setField("PI_VTYPE", "PP");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0005_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject result = rfcjson.getStructure("PO_MESS");
		if (null != result.get("TYPE")
				&& "E".equals(result.get("TYPE").toString())) {
			hashmap.put("type", "E");
			hashmap.put("message", result.get("MESSAGE"));
		} else {
			hashmap.put("type", "S");
			JSONObject o = rfcjson.getRecord("PT_PP");
			JSONArray jObs = o.getJSONArray("row");
			if (jObs.size() > 0) {
				JSONObject object = (JSONObject) jObs.get(0);
				hashmap.put("shortdesc", object2String(object.get("MAKTX")));
				hashmap.put("ppmrp2cglx", object2String(object.get("BESKZ")));
				hashmap.put("scstore", object2String(object.get("LGPRO")));
				hashmap.put("wbstore", object2String(object.get("LGFSB")));
				hashmap.put("ppmrp1srz", object2String(object.get("BSTRF")));
				hashmap.put("ppbstma", object2String(object.get("BSTMA")));
				hashmap.put("ppgzjhbzyc", object2String(object.get("UNETO")));
				hashmap.put("ppgzjhcswj", object2String(object.get("SFCPF")));
				hashmap.put("ppgzjhgdyc", object2String(object.get("UEETO")));
				hashmap.put("ppgzjhscgly", object2String(object.get("FEVOR")));
				hashmap.put("ppmrp1control", object2String(object.get("DISPO")));
				hashmap.put("ppmrp1group", object2String(object.get("DISGR")));
				hashmap.put("ppmrp1pldx", object2String(object.get("DISLS")));
				hashmap.put("ppmrp1type", object2String(object.get("DISMM")));
				hashmap.put("ppmrp2aqkc", object2String(object.get("EISBE")));
				hashmap.put("ppmrp2bjm", object2String(object.get("FHORI")));
				hashmap.put("ppmrp2fc", object2String(object.get("RGEKZ")));
				hashmap.put("ppmrp2jhjhsj", object2String(object.get("PLIFZ")));
				hashmap.put("ppmrp2shclsj", object2String(object.get("WEBAZ")));
				hashmap.put("ppmrp2tscgl", object2String(object.get("SOBSL")));
				hashmap.put("ppmrp2zzscsj", object2String(object.get("DZEIT")));
				hashmap.put("ppmrp3clz", object2String(object.get("STRGR")));
				hashmap.put("ppmrp3kyxjc", object2String(object.get("MTVFP")));
				hashmap.put("ppmrp3nxxhqj", object2String(object.get("VINT1")));
				hashmap.put("ppmrp3xhms", object2String(object.get("VRMOD")));
				hashmap.put("ppmrp3xqxhqj", object2String(object.get("VINT2")));
				hashmap.put("ppmrp4bjfp", object2String(object.get("KAUSF")));
				hashmap.put("ppmrp4dljz", object2String(object.get("SBDKZ")));
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(Ppview objectView) {
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.element("MATNR",
				StringUtils.leftPad(objectView.getWl(), 18, '0'));
		jObjects.add(jObject);
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_MAT", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0003_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_MARA");
		JSONArray jObs = o.getJSONArray("row");
		if (jObs.size() > 0) {
			JSONObject object = (JSONObject) jObs.get(0);
			List<FmFile> fmFiles = fmFileDao
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText1=? order by title,reservedText1,reservedText2,reservedNumber1 desc",
							"0b25c7a2-02c4-449d-abd3-b72edbe01cd5",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText2())
						|| object.get("SPART").toString().trim()
								.equals(fmFile.getReservedText2().trim())) {
					DyFormData dyFormData = dyFormApiFacade.getDyFormData(
							fmFile.getDynamicFormId(),
							fmFile.getDynamicDataId());
					objectView.setPpmrp1type(object2String(dyFormData
							.getFieldValue("ppMrp1Type")));
					objectView.setPpmrp1control(object2String(dyFormData
							.getFieldValue("ppMrp1Control")));
					objectView.setPpmrp1pldx(object2String(dyFormData
							.getFieldValue("ppMrp1Pldx")));
					objectView.setPpmrp1group(object2String(dyFormData
							.getFieldValue("ppMrp1Group")));
					objectView.setPpmrp2cglx(object2String(dyFormData
							.getFieldValue("ppMrp2Cglx")));
					objectView.setPpmrp2tscgl(object2String(dyFormData
							.getFieldValue("ppMrp2Tscgl")));
					objectView.setPpmrp2fc(object2String(dyFormData
							.getFieldValue("ppMrp2Fc")));
					objectView.setScstore(object2String(dyFormData
							.getFieldValue("scStoreAddr")));
					objectView.setWbstore(object2String(dyFormData
							.getFieldValue("wbcgStoreAddr")));
					objectView.setPpmrp2zzscsj(object2String(dyFormData
							.getFieldValue("ppMrp2Zzscsj")));
					objectView.setPpmrp2shclsj(object2String(dyFormData
							.getFieldValue("ppMrp2Shclsj")));
					objectView.setPpmrp2bjm(object2String(dyFormData
							.getFieldValue("ppMrp2Bjm")));
					objectView.setPpmrp2aqkc(object2String(dyFormData
							.getFieldValue("ppMrp2Aqkc")));
					objectView.setPpmrp3clz(object2String(dyFormData
							.getFieldValue("ppMrp3Clz")));
					objectView.setPpmrp3xhms(object2String(dyFormData
							.getFieldValue("ppMrp3Xhms")));
					objectView.setPpmrp3xqxhqj(object2String(dyFormData
							.getFieldValue("ppMrp3Xqxhqj")));
					objectView.setPpmrp3nxxhqj(object2String(dyFormData
							.getFieldValue("ppMrp3Nxxhqj")));
					objectView.setPpmrp3kyxjc(object2String(dyFormData
							.getFieldValue("ppMrp3Kyxjc")));
					objectView.setPpmrp4bjfp(object2String(dyFormData
							.getFieldValue("ppMrp4Bjfp")));
					objectView.setPpmrp4dljz(object2String(dyFormData
							.getFieldValue("ppMrp4Dljz")));
					objectView.setPpgzjhcswj(object2String(dyFormData
							.getFieldValue("ppGzjhCswj")));
					objectView.setPpgzjhscgly(object2String(dyFormData
							.getFieldValue("ppGzjhScgly")));
					objectView.setPpgzjhbzyc(object2String(dyFormData
							.getFieldValue("ppGzjhBzyc")));
					objectView.setPpgzjhgdyc(object2String(dyFormData
							.getFieldValue("ppGzjhGdyc")));
					objectView.setPpbstma(object2String(dyFormData
							.getFieldValue("ppBstma")));
					break;
				}
			}
		}
		objectView.setStatus("1");
		this.dao.save(objectView);
		relationUpdate(objectView);
	}

	@SuppressWarnings("deprecation")
	private void relationUpdate(Ppview objectView) {
		List<PlanView> planViews = planViewDao.find(
				"from PlanView where wl=? and factory=?", objectView.getWl(),
				objectView.getFactory());
		for (PlanView planView : planViews) {
			planView.setPpmrp2cglx(objectView.getPpmrp2cglx());
			this.dao.save(planView);
		}
		List<Ficoview> ficoViews = ficoViewDao.find(
				"from Ficoview where wl=? and factory=?", objectView.getWl(),
				objectView.getFactory());
		for (Ficoview ficoView : ficoViews) {
			ficoView.setPpmrp2cglx(objectView.getPpmrp2cglx());
			this.dao.save(ficoView);
		}

	}

	private String object2String(Object object) {
		String s = "";
		if (null != object)
			s = object.toString();
		return s;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> findOwnFactory() {
		List<String> factorys = new ArrayList<String>();
		List<FmFile> fmFiles = fmFileDao
				.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText2=? and reservedText7 like '%"
						+ SpringSecurityUtils.getCurrentUserId() + "%'",
						"960df70d-d718-4770-a139-a92ba6c648b1", "PP");
		for (FmFile fmFile : fmFiles) {
			String[] strArray = fmFile.getReservedText5().split(",");
			for (int i = 0; i < strArray.length; i++) {
				String str = strArray[i];
				if (StringUtils.isNotEmpty(str)) {
					factorys.add(str);
				}
			}
		}
		return factorys;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> findWlgcOwnFactory() {
		List<String> factorys = new ArrayList<String>();
		List<FmFile> fmFiles = fmFileDao
				.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText2=? and reservedText7 like '%"
						+ SpringSecurityUtils.getCurrentUserId() + "%'",
						"960df70d-d718-4770-a139-a92ba6c648b1", "WLGC");
		for (FmFile fmFile : fmFiles) {
			String[] strArray = fmFile.getReservedText5().split(",");
			for (int i = 0; i < strArray.length; i++) {
				String str = strArray[i];
				if (StringUtils.isNotEmpty(str)) {
					factorys.add(str);
				}
			}
		}
		return factorys;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveWlgcStatus(String wl, String factory, String mmsta) {
		HashMap hashmap = new HashMap();
		SAPRfcJson util = new SAPRfcJson("");
		if (StringUtils.isNotEmpty(wl)) {
			util.setField("PI_MATNR", wl);
		}
		if (StringUtils.isNotEmpty(factory)) {
			util.setField("PI_WERKS", factory);
		}
		if (StringUtils.isNotEmpty(mmsta)) {
			util.setField("PI_MMSTA", mmsta);
		}
		User u = userService.getById(SpringSecurityUtils.getCurrentUserId());
		util.setField("PI_PERNR", u.getEmployeeNumber());
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZMMI0027",
				util.getRfcJson(), 1, -1, null);
		if ("E".equals(rfcjson.getFieldValue("PO_TYPE"))) {
			hashmap.put("type", "E");
			hashmap.put("message", rfcjson.getFieldValue("PO_MESS"));
		} else {
			hashmap.put("type", "S");
		}
		return hashmap;
	}

}
