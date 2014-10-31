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
import com.wellsoft.pt.ldx.dao.maindata.PpViewDao;
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.model.mainData.Mmview;
import com.wellsoft.pt.ldx.model.mainData.Ppview;
import com.wellsoft.pt.ldx.service.maindata.MainDataMmService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataMmServiceImpl extends BaseServiceImpl implements
		MainDataMmService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private PpViewDao ppViewDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Mmview getObject(String uuid) {
		return this.dao.get(Mmview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveMmview(Mmview objectview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(objectview.getUuid())) {
			Mmview object = this.dao.get(Mmview.class, objectview.getUuid());
			object.setWl(objectview.getWl());
			object.setShortdesc(objectview.getShortdesc());
			object.setFactory(objectview.getFactory());
			object.setMmcgz(objectview.getMmcgz());
			object.setMmhyqd(objectview.getMmhyqd());
			object.setMmpegl(objectview.getMmddwb());
			object.setMmddwb(objectview.getMmddwb());
			object.setPpmrp2jhjhsj(objectview.getPpmrp2jhjhsj());
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
			Mmview object = this.dao.get(Mmview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Mmview> bgObjects = new HashMap<String, Mmview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Mmview object = this.dao.get(Mmview.class, as[i]);
			bgObjects.put(String.valueOf(i), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Mmview objectView = bgObjects.get(key);
			jObject.element("ID", key);
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("PLANT", objectView.getFactory());
			jObject.element("PUR_GROUP", objectView.getMmcgz());
			jObject.element("SOURCELIST", objectView.getMmhyqd());
			jObject.element("QUOTAUSAGE", objectView.getMmpegl());
			jObject.element("ZPTEXT", objectView.getMmddwb());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_MM_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_MM_VIEW", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("mm");
				log.setWl(bgObjects.get(returnObject.get("ID")).getWl());
				log.setFactory(bgObjects.get(returnObject.get("ID"))
						.getFactory());
				log.setContent(JSONObject.fromObject(
						bgObjects.get(returnObject.get("ID"))).toString());
				this.dao.save(log);
				List<Ppview> ppViews = ppViewDao.find(
						"from Ppview where wl=? and factory=?",
						bgObjects.get(returnObject.get("ID")).getWl(),
						bgObjects.get(returnObject.get("ID")).getFactory());
				for (Ppview ppview : ppViews) {
					ppview.setPpmrp2jhjhsj(bgObjects
							.get(returnObject.get("ID")).getPpmrp2jhjhsj());
					this.dao.save(ppview);
				}
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
		util.setField("PI_VTYPE", "MM");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0005_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject result = rfcjson.getStructure("PO_MESS");
		if (null != result.get("TYPE")
				&& "E".equals(result.get("TYPE").toString())) {
			hashmap.put("type", "E");
			hashmap.put("message", result.get("MESSAGE"));
		} else {
			hashmap.put("type", "S");
			JSONObject o = rfcjson.getRecord("PT_MM");
			JSONArray jObs = o.getJSONArray("row");
			if (jObs.size() > 0) {
				JSONObject object = (JSONObject) jObs.get(0);
				hashmap.put("shortdesc", object2String(object.get("MAKTX")));
				hashmap.put("mmcgz", object2String(object.get("EKGRP")));
				hashmap.put("mmddwb", object2String(object.get("ZPTEXT")));
				hashmap.put("mmhyqd", object2String(object.get("KORDB")));
				hashmap.put("mmpegl", object2String(object.get("USEQU")));
				hashmap.put("ppmrp2jhjhsj", object2String(object.get("PLIFZ")));
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(Mmview objectView) {
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
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText1=? order by title,reservedText1 desc",
							"59bc6064-c015-460e-9452-a1219ff3af2f",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				DyFormData dyFormData = dyFormApiFacade.getDyFormData(
						fmFile.getDynamicFormId(), fmFile.getDynamicDataId());
				objectView.setMmcgz(object2String(dyFormData
						.getFieldValue("mmCgz")));
				objectView.setMmhyqd(object2String(dyFormData
						.getFieldValue("mmHyqd")));
				objectView.setMmpegl(object2String(dyFormData
						.getFieldValue("mmPegl")));
				objectView.setMmddwb(object2String(dyFormData
						.getFieldValue("mmDdwb")));
				objectView.setPpmrp2jhjhsj(object2String(dyFormData
						.getFieldValue("ppMrp2Jhjhsj")));
				break;
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
						"960df70d-d718-4770-a139-a92ba6c648b1", "MM");
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
