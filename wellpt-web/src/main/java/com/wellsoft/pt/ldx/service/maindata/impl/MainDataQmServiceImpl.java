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
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.model.mainData.Qmview;
import com.wellsoft.pt.ldx.service.maindata.MainDataQmService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataQmServiceImpl extends BaseServiceImpl implements
		MainDataQmService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FmFileDao fmFileDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Qmview getObject(String uuid) {
		return this.dao.get(Qmview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveQmview(Qmview objectview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(objectview.getUuid())) {
			Qmview object = this.dao.get(Qmview.class, objectview.getUuid());
			object.setWl(objectview.getWl());
			object.setShortdesc(objectview.getShortdesc());
			object.setFactory(objectview.getFactory());
			object.setQmpcgl(objectview.getQmpcgl());
			object.setQmjylx01(objectview.getQmjylx01());
			object.setQmjylx03(objectview.getQmjylx03());
			object.setQmjylx10(objectview.getQmjylx10());
			object.setQmjylx89(objectview.getQmjylx89());
			object.setQmjylxldx01(objectview.getQmjylxldx01());
			object.setQmjylxldx02(objectview.getQmjylxldx02());
			object.setQmjylxldx03(objectview.getQmjylxldx03());
			object.setQmjylxldx04(objectview.getQmjylxldx04());
			object.setQmjylxldx05(objectview.getQmjylxldx05());
			object.setQmjylxldx06(objectview.getQmjylxldx06());
			object.setQmpjjyq(objectview.getQmpjjyq());
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
			Qmview object = this.dao.get(Qmview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Qmview> bgObjects = new HashMap<String, Qmview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Qmview object = this.dao.get(Qmview.class, as[i]);
			bgObjects.put(String.valueOf(i), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Qmview objectView = bgObjects.get(key);
			jObject.element("ID", key);
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("PLANT", objectView.getFactory());
			jObject.element("BATCH_MGMT", objectView.getQmpcgl());
			jObject.element("ART1", objectView.getQmjylx01());
			jObject.element("ART2", objectView.getQmjylx03());
			jObject.element("ART3", objectView.getQmjylx10());
			jObject.element("ART4", objectView.getQmjylx89());
			jObject.element("ART5", objectView.getQmjylxldx01());
			jObject.element("ART6", objectView.getQmjylxldx04());
			jObject.element("ART7", objectView.getQmjylxldx02());
			jObject.element("ART8", objectView.getQmjylxldx05());
			jObject.element("ART9", objectView.getQmjylxldx03());
			jObject.element("ART10", objectView.getQmjylxldx06());
			jObject.element("MPDAU", objectView.getQmpjjyq());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_QM_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_QM_VIEW", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("qm");
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
		util.setField("PI_VTYPE", "QM");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0005_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject result = rfcjson.getStructure("PO_MESS");
		if (null != result.get("TYPE")
				&& "E".equals(result.get("TYPE").toString())) {
			hashmap.put("type", "E");
			hashmap.put("message", result.get("MESSAGE"));
		} else {
			hashmap.put("type", "S");
			JSONObject o = rfcjson.getRecord("PT_QM");
			JSONArray jObs = o.getJSONArray("row");
			if (jObs.size() > 0) {
				JSONObject object = (JSONObject) jObs.get(0);
				hashmap.put("shortdesc", object2String(object.get("MAKTX")));
				hashmap.put("qmjylx01", object2String(object.get("ART1")));
				hashmap.put("qmjylx03", object2String(object.get("ART2")));
				hashmap.put("qmjylx10", object2String(object.get("ART3")));
				hashmap.put("qmjylx89", object2String(object.get("ART4")));
				hashmap.put("qmjylxldx01", object2String(object.get("ART5")));
				hashmap.put("qmjylxldx02", object2String(object.get("ART6")));
				hashmap.put("qmjylxldx03", object2String(object.get("ART7")));
				hashmap.put("qmjylxldx04", object2String(object.get("ART8")));
				hashmap.put("qmjylxldx05", object2String(object.get("ART9")));
				hashmap.put("qmjylxldx06", object2String(object.get("ART10")));
				hashmap.put("qmpcgl", object2String(object.get("XCHPF")));
				hashmap.put("qmpjjyq", object2String(object.get("MPDAU")));
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(Qmview objectView) {
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
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText1=? order by title,reservedText1,reservedText2 desc",
							"c858520c-c9ea-44da-bb5c-87ae65cce850",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText2())
						|| object.get("SPART").toString().trim()
								.equals(fmFile.getReservedText2().trim())) {
					DyFormData dyFormData = dyFormApiFacade.getDyFormData(
							fmFile.getDynamicFormId(),
							fmFile.getDynamicDataId());
					objectView.setQmpcgl(object2String(dyFormData
							.getFieldValue("qmPcgl")));
					objectView.setQmjylx01(object2String(dyFormData
							.getFieldValue("qmJylx01")));
					objectView.setQmjylx03(object2String(dyFormData
							.getFieldValue("qmJylx03")));
					objectView.setQmjylx10(object2String(dyFormData
							.getFieldValue("qmJylx10")));
					objectView.setQmjylx89(object2String(dyFormData
							.getFieldValue("qmJylx89")));
					objectView.setQmjylxldx01(object2String(dyFormData
							.getFieldValue("qmJylxLdx01")));
					objectView.setQmjylxldx02(object2String(dyFormData
							.getFieldValue("qmJylxLdx02")));
					objectView.setQmjylxldx03(object2String(dyFormData
							.getFieldValue("qmJylxLdx03")));
					objectView.setQmjylxldx04(object2String(dyFormData
							.getFieldValue("qmJylxLdx04")));
					objectView.setQmjylxldx05(object2String(dyFormData
							.getFieldValue("qmJylxLdx05")));
					objectView.setQmjylxldx06(object2String(dyFormData
							.getFieldValue("qmJylxLdx06")));
					objectView.setQmpjjyq(object2String(dyFormData
							.getFieldValue("qmPjjyq")));
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
						"960df70d-d718-4770-a139-a92ba6c648b1", "QM");
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
