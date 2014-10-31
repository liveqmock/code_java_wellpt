package com.wellsoft.pt.ldx.service.maindata.impl;

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
import com.wellsoft.pt.ldx.model.mainData.Baoguanview;
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.service.maindata.MainDataBaoGuanService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataBaoGuanServiceImpl extends BaseServiceImpl implements
		MainDataBaoGuanService {

	@Autowired
	private SAPRfcService saprfcservice;
	@Autowired
	private FmFileDao fmFileDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Baoguanview getObject(String uuid) {
		return this.dao.get(Baoguanview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveBaoGuanview(Baoguanview objectview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(objectview.getUuid())) {
			Baoguanview object = this.dao.get(Baoguanview.class,
					objectview.getUuid());
			object.setWl(objectview.getWl());
			object.setShortdesc(objectview.getShortdesc());
			object.setCnname(objectview.getCnname());
			object.setEnname(objectview.getEnname());
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
			Baoguanview object = this.dao.get(Baoguanview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Baoguanview> bgObjects = new HashMap<String, Baoguanview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Baoguanview object = this.dao.get(Baoguanview.class, as[i]);
			bgObjects.put(object.getWl(), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String wlid : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Baoguanview objectView = bgObjects.get(wlid);
			jObject.element("ID", objectView.getWl());
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("ZMVKE_CN", objectView.getCnname());
			jObject.element("ZMVKE_EN", objectView.getEnname());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_LT", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_LT", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("baoguan");
				log.setWl(bgObjects.get(returnObject.get("ID")).getWl());
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
	public Map findObjectFromSap(String wl) {
		HashMap hashmap = new HashMap();
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.element("MATNR", StringUtils.leftPad(wl, 18, '0'));
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
			hashmap.put("shortdesc", object2String(object.get("MAKTX")));
			hashmap.put("cnname", object2String(object.get("ZCNTXT")));
			hashmap.put("enname", object2String(object.get("ZENTXT")));
		}
		return hashmap;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateFromDefaultParam(Baoguanview objectView) {
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
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? order by title,reservedText2 desc",
							"1ff8c678-27bf-431f-b493-f0ca25fdfca2",
							object.get("MATNR").toString().substring(0, 5));
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText2())
						|| object.get("MAKTX").toString()
								.contains(fmFile.getReservedText2())) {
					objectView.setCnname(fmFile.getReservedText3());
					objectView.setEnname(fmFile.getReservedText4());
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

}
