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
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.dao.maindata.FicoViewDao;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.model.mainData.Sdview;
import com.wellsoft.pt.ldx.service.maindata.MainDataSdService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataSdServiceImpl extends BaseServiceImpl implements
		MainDataSdService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private FicoViewDao ficoViewDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Sdview getObject(String uuid) {
		return this.dao.get(Sdview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveSdview(Sdview objectview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(objectview.getUuid())) {
			Sdview object = this.dao.get(Sdview.class, objectview.getUuid());
			object.setWl(objectview.getWl());
			object.setShortdesc(objectview.getShortdesc());
			object.setFactory(objectview.getFactory());
			object.setSdxszz(objectview.getSdxszz());
			object.setSdfxqd(objectview.getSdfxqd());
			object.setSdxmlbz(objectview.getSdxmlbz());
			object.setSdsfl(objectview.getSdsfl());
			object.setSdkmszz(objectview.getSdkmszz());
			object.setSdkyxjc(objectview.getSdkyxjc());
			object.setSdysz(objectview.getSdysz());
			object.setSdzzz(objectview.getSdzzz());
			object.setModifier(SpringSecurityUtils.getCurrentUserId());
			object.setModifyTime(new Date());
			object.setStatus("1");
			this.dao.save(object);
		} else {
			objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
			objectview.setCreateTime(new Date());
			objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
			objectview.setModifyTime(new Date());
			objectview.setStatus("1");
			this.dao.save(objectview);
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Sdview object = this.dao.get(Sdview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Sdview> bgObjects = new HashMap<String, Sdview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Sdview object = this.dao.get(Sdview.class, as[i]);
			bgObjects.put(String.valueOf(i), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Sdview objectView = bgObjects.get(key);
			List<Ficoview> ficoviews = ficoViewDao.find(
					"from Ficoview where wl=? and factory=?",
					objectView.getWl(), objectView.getFactory());
			if (null != ficoviews && ficoviews.size() > 0) {
				hashmap.put(objectView.getUuid(), objectView.getWl() + ","
						+ objectView.getFactory() + ",对应的FICO视图未维护，不能传递SAP");
				continue;
			}
			jObject.element("ID", key);
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("PLANT", objectView.getFactory());
			jObject.element("SALES_ORG", objectView.getSdxszz());
			jObject.element("DISTR_CHAN", objectView.getSdfxqd());
			jObject.element("ITEM_CAT", objectView.getSdxmlbz());
			jObject.element("TAXCLASS_1", objectView.getSdsfl());
			jObject.element("ACCT_ASSGT", objectView.getSdkmszz());
			jObject.element("AVAILCHECK", objectView.getSdkyxjc());
			jObject.element("TRANS_GRP", objectView.getSdysz());
			jObject.element("LOADINGGRP", objectView.getSdzzz());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_SD_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_SD_VIEW", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("sd");
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
	public Map findObjectFromSap(String wl, String factory, String xszz,
			String fxqd) {
		HashMap hashmap = new HashMap();
		SAPRfcJson util = new SAPRfcJson("");
		util.setField("PI_MATNR", wl);
		util.setField("PI_WERKS", factory);
		util.setField("PI_VKORG", xszz);
		util.setField("PI_VTWEG", fxqd);
		util.setField("PI_VTYPE", "SD");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0005_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject result = rfcjson.getStructure("PO_MESS");
		if (null != result.get("TYPE")
				&& "E".equals(result.get("TYPE").toString())) {
			hashmap.put("type", "E");
			hashmap.put("message", result.get("MESSAGE"));
		} else {
			hashmap.put("type", "S");
			JSONObject o = rfcjson.getRecord("PT_SD");
			JSONArray jObs = o.getJSONArray("row");
			if (jObs.size() > 0) {
				JSONObject object = (JSONObject) jObs.get(0);
				hashmap.put("shortdesc", object2String(object.get("MAKTX")));
				hashmap.put("sdkmszz", object2String(object.get("KTGRM")));
				hashmap.put("sdkyxjc", object2String(object.get("MTVFP")));
				hashmap.put("sdsfl", object2String(object.get("TAXMN")));
				hashmap.put("sdxmlbz", object2String(object.get("MTPOS")));
				hashmap.put("sdysz", object2String(object.get("TRAGR")));
				hashmap.put("sdzzz", object2String(object.get("LADGR")));
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(Sdview objectView) {
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
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText1=? order by title,reservedText1,reservedText6 desc",
							"08d1a20d-8147-4ef1-b923-ea7083e9bd7f",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText6())
						|| object.get("MAKTX").toString().trim()
								.contains(fmFile.getReservedText6().trim())) {
					objectView.setSdxszz(fmFile.getReservedText2());
					objectView.setSdfxqd(fmFile.getReservedText3());
					break;
				}
			}
		}
		objectView.setStatus("1");
		this.dao.save(objectView);
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
						"960df70d-d718-4770-a139-a92ba6c648b1", "SD");
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
}
