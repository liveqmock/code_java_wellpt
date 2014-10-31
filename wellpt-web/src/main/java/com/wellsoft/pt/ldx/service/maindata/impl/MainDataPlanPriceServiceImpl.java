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
import com.wellsoft.pt.ldx.dao.maindata.PpViewDao;
import com.wellsoft.pt.ldx.model.mainData.Ficoview;
import com.wellsoft.pt.ldx.model.mainData.PlanView;
import com.wellsoft.pt.ldx.model.mainData.Ppview;
import com.wellsoft.pt.ldx.service.maindata.MainDataPlanPriceService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataPlanPriceServiceImpl extends BaseServiceImpl implements
		MainDataPlanPriceService {

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private PpViewDao ppViewDao;

	@Autowired
	private FicoViewDao ficoViewDao;

	@Override
	public List<QueryItem> queryQueryItemData(String hql,
			Map<String, Object> queryParams, PagingInfo pagingInfo) {
		return this.dao.query(hql, queryParams, QueryItem.class, pagingInfo);
	}

	@Override
	public PlanView getObject(String uuid) {
		return this.dao.get(PlanView.class, uuid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map savePlanView(PlanView planView) {
		HashMap hashmap = new HashMap();
		if (StringUtils.isNotEmpty(planView.getUuid())) {
			PlanView object = this.dao.get(PlanView.class, planView.getUuid());
			object.setWl(planView.getWl());
			object.setFactory(planView.getFactory());
			object.setScstore(planView.getScstore());
			object.setWbstore(planView.getWbstore());
			object.setPricejhjgrq(planView.getPricejhjgrq());
			object.setPpmrp2cglx(planView.getPpmrp2cglx());
			object.setPricejhjg(planView.getPricejhjg());
			object.setFicokj1jgdw(planView.getFicokj1jgdw());
			object.setPpmrp1srz(planView.getPpmrp1srz());
			object.setShortdesc(planView.getShortdesc());
			object.setModifier(SpringSecurityUtils.getCurrentUserId());
			object.setModifyTime(new Date());
			object.setStatus("1");
			this.dao.save(object);
		} else {
			planView.setCreator(SpringSecurityUtils.getCurrentUserId());
			planView.setCreateTime(new Date());
			planView.setModifier(SpringSecurityUtils.getCurrentUserId());
			planView.setModifyTime(new Date());
			planView.setStatus("1");
			this.dao.save(planView);
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map deleteObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			PlanView object = this.dao.get(PlanView.class, as[i]);
			this.dao.delete(object);
		}
		hashmap.put("state", "1");
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public Map transferObjects(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			PlanView object = this.dao.get(PlanView.class, as[i]);
			List<Ppview> ppViews = ppViewDao.find(
					"from Ppview where wl=? and factory=?", object.getWl(),
					object.getFactory());
			for (Ppview ppview : ppViews) {
				ppview.setPpmrp1srz(object.getPpmrp1srz());
				this.dao.save(ppview);
			}
			List<Ficoview> ficoViews = ficoViewDao.find(
					"from Ficoview where wl=? and factory=?", object.getWl(),
					object.getFactory());
			for (Ficoview ficoview : ficoViews) {
				ficoview.setFicokj1jgdw(object.getFicokj1jgdw());
				ficoview.setPricejhjg(object.getPricejhjg());
				ficoview.setPricejhjgrq(object.getPricejhjgrq());
				this.dao.save(ficoview);
			}
			this.dao.delete(object);
		}
		return hashmap;
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void updateFromDefaultParam(PlanView objectView) {
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
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText3=? order by title,reservedText3,reservedText4 desc",
							"4da18fa1-75a6-4214-a4b8-71856fd62da9",
							object.get("MATNR").toString().substring(0, 5),
							objectView.getFactory());
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText4())
						|| object.get("SPART").toString().trim()
								.equals(fmFile.getReservedText4().trim())) {
					DyFormData dyFormData = dyFormApiFacade.getDyFormData(
							fmFile.getDynamicFormId(),
							fmFile.getDynamicDataId());
					objectView.setPricejhjg(object2String(dyFormData
							.getFieldValue("priceJhjg")));
					objectView.setPpmrp1srz(object2String(dyFormData
							.getFieldValue("ppMrp1Srz")));
					objectView.setPricejhjgrq(object2String(dyFormData
							.getFieldValue("priceJhjgrq")));
					objectView.setFicokj1jgdw(object2String(dyFormData
							.getFieldValue("ficoKj1Jgdw")));
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
						"960df70d-d718-4770-a139-a92ba6c648b1", "PLAN");
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
