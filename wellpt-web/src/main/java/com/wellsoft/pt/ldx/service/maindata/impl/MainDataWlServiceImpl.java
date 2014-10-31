package com.wellsoft.pt.ldx.service.maindata.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.ldx.model.mainData.GcMessage;
import com.wellsoft.pt.ldx.model.mainData.WlGc;
import com.wellsoft.pt.ldx.service.impl.SapServiceImpl;
import com.wellsoft.pt.ldx.service.maindata.MainDataWlService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
public class MainDataWlServiceImpl extends SapServiceImpl implements
		MainDataWlService {

	@Autowired
	private SAPDbConfig sapConnectConfig;

	@Autowired
	private FmFileDao fmFileDao;

	@Override
	public List<GcMessage> findFactorys(String wlid) {
		String sql = "select werks,name1 from t001w where werks!='0001' and mandt='"
				+ sapConnectConfig.getClient()
				+ "' and werks not in (select werks from marc where matnr ='"
				+ StringUtils.leftPad(wlid, 18, '0')
				+ "' and mandt='"
				+ sapConnectConfig.getClient() + "') order by werks asc";
		List<Object> objects = this.findListBySql(sql);
		List<GcMessage> gcMessages = new ArrayList<GcMessage>();
		for (Object object : objects) {
			if (object instanceof Object[]) {
				GcMessage gcMessage = new GcMessage();
				gcMessage.setFactory(((Object[]) object)[0].toString());
				gcMessage.setName(((Object[]) object)[1].toString());
				List<String[]> stores = new ArrayList<String[]>();
				List<Object> os = this
						.findListBySql("select lgort,lgobe from t001l where mandt='"
								+ sapConnectConfig.getClient()
								+ "' and werks='"
								+ ((Object[]) object)[0].toString() + "'");
				for (Object o : os) {
					if (o instanceof Object[]) {
						String[] store = new String[2];
						store[0] = ((Object[]) o)[0].toString();
						store[1] = ((Object[]) o)[1].toString();
						stores.add(store);
					}
				}
				gcMessage.setStores(stores);
				gcMessages.add(gcMessage);
			}
		}
		return gcMessages;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map saveWlgc(String s) {
		HashMap hashmap = new HashMap();
		JSONObject object = JSONObject.fromObject(s);
		String str = object.get("wlids").toString();
		JSONArray wlids = JSONArray.fromObject(str);
		JSONArray objectArr = object.getJSONArray("items");
		for (Object o : objectArr) {
			JSONObject ob = (JSONObject) o;
			for (int i = 0; i < wlids.size(); i++) {
				WlGc wlgc = new WlGc();
				wlgc.setWl(((JSONObject) wlids.get(i)).getString("wl"));
				wlgc.setFactory(ob.getString("factory"));
				wlgc.setScstore(ob.getString("scstore"));
				wlgc.setWbstore(ob.getString("wbstore"));
				wlgc.setShortdesc(((JSONObject) wlids.get(i))
						.getString("shortdesc"));
				wlgc.setCreator(SpringSecurityUtils.getCurrentUserId());
				wlgc.setCreateTime(new Date());
				wlgc.setModifier(SpringSecurityUtils.getCurrentUserId());
				wlgc.setModifyTime(new Date());
				this.dao.save(wlgc);
			}
		}
		return hashmap;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public Map saveDefaultWlgc(String s) {
		HashMap hashmap = new HashMap();
		JSONArray objectArr = JSONArray.fromObject(s);
		for (Object o : objectArr) {
			JSONObject object = (JSONObject) o;
			List<FmFile> fmFiles = fmFileDao
					.find("from FmFile where status=2 and fmFolder.uuid=? and title=? and reservedText2=? order by title,reservedText2,reservedText3,reservedText7 desc",
							"e117d0eb-dbdc-4628-9dcd-57030911b4f6",
							object.get("wl").toString().substring(0, 5), object
									.get("spart").toString());
			Set<String> factorys = new HashSet<String>();
			for (FmFile fmFile : fmFiles) {
				if (StringUtils.isEmpty(fmFile.getReservedText1())) {
					fmFile.setReservedText1("");
				}
				if (StringUtils.isEmpty(fmFile.getReservedText6())) {
					fmFile.setReservedText6("");
				}
				if (!factorys.contains(fmFile.getReservedText3())) {
					if (object.get("longdesc").toString()
							.contains(fmFile.getReservedText1())
							&& object.get("desc").toString()
									.contains(fmFile.getReservedText6())) {
						WlGc wlgc = new WlGc();
						wlgc.setWl(object.get("wl").toString());
						wlgc.setShortdesc(object.getString("shortdesc")
								.toString());
						wlgc.setFactory(fmFile.getReservedText3());
						wlgc.setScstore(fmFile.getReservedText4());
						wlgc.setWbstore(fmFile.getReservedText5());
						wlgc.setCreator(SpringSecurityUtils.getCurrentUserId());
						wlgc.setCreateTime(new Date());
						wlgc.setModifier(SpringSecurityUtils.getCurrentUserId());
						wlgc.setModifyTime(new Date());
						this.dao.save(wlgc);
						factorys.add(fmFile.getReservedText3());
					}
				}
			}
		}
		return hashmap;
	}

}
