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
import com.wellsoft.pt.ldx.dao.maindata.PlanViewDao;
import com.wellsoft.pt.ldx.dao.maindata.SdViewDao;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mainData.MainDataLog;
import com.wellsoft.pt.ldx.model.mainData.PlanView;
import com.wellsoft.pt.ldx.model.mainData.Sdview;
import com.wellsoft.pt.ldx.service.maindata.MainDataFicoService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataFicoServiceImpl extends BaseServiceImpl implements
		MainDataFicoService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private SdViewDao sdViewDao;

	@Autowired
	private PlanViewDao planViewDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public Ficoview getObject(String uuid) {
		return this.dao.get(Ficoview.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveFicoview(Ficoview objectview) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(objectview.getUuid())) {
			Ficoview object = this.dao
					.get(Ficoview.class, objectview.getUuid());
			object.setWl(objectview.getWl());
			object.setShortdesc(objectview.getShortdesc());
			object.setFactory(objectview.getFactory());
			object.setPpmrp2cglx(objectview.getPpmrp2cglx());
			object.setPricejhjg(objectview.getPricejhjg());
			object.setPricejhjgrq(objectview.getPricejhjgrq());
			object.setFicokj1jgkz(objectview.getFicokj1jgkz());
			object.setFicokj1pgl(objectview.getFicokj1pgl());
			object.setFicokj1jgqd(objectview.getFicokj1jgqd());
			object.setFicokj1zqdwjg(objectview.getFicokj1zqdwjg());
			object.setFicokj1jgdw(objectview.getFicokj1jgdw());
			object.setFicokj1bzjg(objectview.getFicokj1bzjg());
			object.setFicocb1cbhspl(objectview.getFicocb1cbhspl());
			object.setFicocb1qs(objectview.getFicocb1qs());
			object.setFicocb1wlly(objectview.getFicocb1wlly());
			object.setFicocb1wcbgs(objectview.getFicocb1wcbgs());
			object.setFicocb1ysz(objectview.getFicocb1ysz());
			object.setFicocb1lrzx(objectview.getFicocb1lrzx());
			object.setSdkmszz(objectview.getSdkmszz());
			object.setModifier(SpringSecurityUtils.getCurrentUserId());
			object.setModifyTime(new Date());
			object.setStatus("1");
			this.dao.save(object);
			relationUpdate(object);
		} else {
			objectview.setCreator(SpringSecurityUtils.getCurrentUserId());
			objectview.setCreateTime(new Date());
			objectview.setModifier(SpringSecurityUtils.getCurrentUserId());
			objectview.setModifyTime(new Date());
			objectview.setStatus("1");
			this.dao.save(objectview);
			relationUpdate(objectview);
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Ficoview object = this.dao.get(Ficoview.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		Map<String, Ficoview> bgObjects = new HashMap<String, Ficoview>();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			Ficoview object = this.dao.get(Ficoview.class, as[i]);
			bgObjects.put(String.valueOf(i), object);
		}
		SAPRfcJson util = new SAPRfcJson("");
		JSONObject rowObject = new JSONObject();
		JSONArray jObjects = new JSONArray();
		for (String key : bgObjects.keySet()) {
			JSONObject jObject = new JSONObject();
			Ficoview objectView = bgObjects.get(key);
			List<PlanView> planviews = planViewDao.find(
					"from PlanView where wl=? and factory=?",
					objectView.getWl(), objectView.getFactory());
			if (null != planviews && planviews.size() > 0) {
				hashmap.put(objectView.getUuid(), objectView.getWl() + ","
						+ objectView.getFactory() + ",对应的计划价格视图未维护，不能传递SAP");
				continue;
			}
			jObject.element("ID", key);
			jObject.element("MATERIAL", objectView.getWl());
			jObject.element("PLANT", objectView.getFactory());
			jObject.element("PRICE_CTRL", objectView.getFicokj1jgkz());
			jObject.element("VAL_CLASS", objectView.getFicokj1pgl());
			jObject.element("ML_SETTLE", objectView.getFicokj1jgqd());
			jObject.element("MOVING_PR", objectView.getFicokj1zqdwjg());
			jObject.element("PRICE_UNIT", objectView.getFicokj1jgdw());
			jObject.element("STD_PRICE", objectView.getFicokj1bzjg());
			jObject.element("LOT_SIZE", objectView.getFicocb1cbhspl());
			jObject.element("QTY_STRUCT", objectView.getFicocb1qs());
			jObject.element("ORIG_MAT", objectView.getFicocb1wlly());
			jObject.element("NO_COSTING", objectView.getFicocb1wcbgs());
			jObject.element("ORIG_GROUP", objectView.getFicocb1ysz());
			jObject.element("PROFIT_CTR", objectView.getFicocb1lrzx());
			jObject.element("PLNDPRICE1", objectView.getPricejhjg());
			jObject.element("PLNDPRDATE1", objectView.getPricejhjgrq());
			jObjects.add(jObject);
		}
		if (jObjects.size() > 0) {
			rowObject.element("row", jObjects);
			util.setRecord("PT_FC_VIEW", rowObject.toString());
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZOA_MATERIAL_FC_VIEW", util.getRfcJson(), 1, -1, null);
		JSONObject o = rfcjson.getRecord("PT_RETURN2");
		JSONArray jObs = o.getJSONArray("row");
		for (Object object : jObs) {
			JSONObject returnObject = (JSONObject) object;
			if ("S".equals(returnObject.get("TYPE"))) {
				MainDataLog log = new MainDataLog();
				log.setCreateTime(new Date());
				log.setCreator(SpringSecurityUtils.getCurrentUserId());
				log.setType("fico");
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
		util.setField("PI_VTYPE", "FC");
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig",
				"ZI0005_LCP", util.getRfcJson(), 1, -1, null);
		JSONObject result = rfcjson.getStructure("PO_MESS");
		if (null != result.get("TYPE")
				&& "E".equals(result.get("TYPE").toString())) {
			hashmap.put("type", "E");
			hashmap.put("message", result.get("MESSAGE"));
		} else {
			hashmap.put("type", "S");
			JSONObject o = rfcjson.getRecord("PT_FC");
			JSONArray jObs = o.getJSONArray("row");
			if (jObs.size() > 0) {
				JSONObject object = (JSONObject) jObs.get(0);
				hashmap.put("shortdesc", object2String(object.get("MAKTX")));
				hashmap.put("ppmrp2cglx", object2String(object.get("BESKZ")));
				hashmap.put("ficocb1cbhspl", object2String(object.get("LOSGR")));
				hashmap.put("ficocb1lrzx", object2String(object.get("PRCTR")));
				hashmap.put("ficocb1qs", object2String(object.get("EKALR")));
				hashmap.put("ficocb1wcbgs", object2String(object.get("NCOST")));
				hashmap.put("ficocb1wlly", object2String(object.get("HKMAT")));
				hashmap.put("ficocb1ysz", object2String(object.get("HRKFT")));
				hashmap.put("ficokj1bzjg", object2String(object.get("STPRS")));
				hashmap.put("ficokj1jgdw", object2String(object.get("PEINH")));
				hashmap.put("ficokj1jgkz", object2String(object.get("VPRSV")));
				hashmap.put("ficokj1jgqd", object2String(object.get("MLAST")));
				hashmap.put("ficokj1pgl", object2String(object.get("BKLAS")));
				hashmap.put("ficokj1zqdwjg", object2String(object.get("VERPR")));
				hashmap.put("pricejhjg", object2String(object.get("ZPLP1")));
				hashmap.put("pricejhjgrq", object2String(object.get("ZPLD1")));
				hashmap.put("sdkmszz", object2String(object.get("KTGRM")));
			}
		}

		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(Ficoview objectView) {
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
							"2b76e98f-d45b-4075-a6cb-df23a7e7108b",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				DyFormData dyFormData = dyFormApiFacade.getDyFormData(
						fmFile.getDynamicFormId(), fmFile.getDynamicDataId());
				objectView.setFicokj1jgkz(object2String(dyFormData
						.getFieldValue("ficoKj1Jgkz")));
				objectView.setFicokj1pgl(object2String(dyFormData
						.getFieldValue("ficoKj1Pgl")));
				objectView.setFicokj1jgqd(object2String(dyFormData
						.getFieldValue("ficoKj1Jgqd")));
				objectView.setFicocb1wcbgs(object2String(dyFormData
						.getFieldValue("ficoCb1Wcbgs")));
				objectView.setFicocb1ysz(object2String(dyFormData
						.getFieldValue("ficoCb1Ysz")));
				objectView.setSdkmszz(object2String(dyFormData
						.getFieldValue("sdKmszz")));
				break;
			}
		}
		objectView.setStatus("1");
		this.dao.save(objectView);
		relationUpdate(objectView);
	}

	@SuppressWarnings("deprecation")
	private void relationUpdate(Ficoview objectView) {
		List<Sdview> sdViews = sdViewDao.find(
				"from Sdview where wl=? and factory=?", objectView.getWl(),
				objectView.getFactory());
		for (Sdview sdView : sdViews) {
			sdView.setSdkmszz(objectView.getSdkmszz());
			this.dao.save(sdView);
		}

	}

	private String object2String(Object object) {
		String s = "";
		if (null != object)
			s = object.toString();
		return s;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public List<String> findOwnFactory() {
		List<String> factorys = new ArrayList<String>();
		List<FmFile> fmFiles = fmFileDao
				.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText2=? and reservedText7 like '%"
						+ SpringSecurityUtils.getCurrentUserId() + "%'",
						"960df70d-d718-4770-a139-a92ba6c648b1", "FICO");
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
