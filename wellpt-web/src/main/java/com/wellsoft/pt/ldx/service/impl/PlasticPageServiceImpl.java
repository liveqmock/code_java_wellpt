package com.wellsoft.pt.ldx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.star.uno.RuntimeException;
import com.wellsoft.pt.basicdata.sap.service.SAPRfcService;
import com.wellsoft.pt.basicdata.sap.util.SAPRfcJson;
import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.file.bean.FmFileBean;
import com.wellsoft.pt.file.common.FileManagerConstant;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.file.service.FileManagerService;
import com.wellsoft.pt.ldx.service.PlasticPageService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class PlasticPageServiceImpl extends BaseServiceImpl implements
		PlasticPageService {

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private SAPRfcService saprfcservice;

	@Autowired
	private FileManagerService fileManagerService;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Map generateFile(String customer) {
		HashMap hashmap = new HashMap();
		List<FmFile> fmFiles = fmFileDao
				.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText1=? ",
						"7ac92079-f292-4695-9073-0ddb81067cf3", customer);
		String dynamicTempalteId = "dbd6033f-e530-4e71-85ee-c7417fcad19d";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		if (null != fmFiles && fmFiles.size() > 0) {
			ob.put("code",
					"P"
							+ customer
							+ StringUtils.leftPad(
									String.valueOf(fmFiles.size() + 1), 4, '0')
							+ "V1.0");
			ob.put("customercode", customer);
			ob.put("version", "V1.0");
			ob.put("sequenceno", StringUtils.leftPad(
					String.valueOf(fmFiles.size() + 1), 4, '0'));
			hashmap.put("code", ob.get("code"));
		} else {
			ob.put("code", "P" + customer + "0001V1.0");
			ob.put("customercode", customer);
			ob.put("version", "V1.0");
			ob.put("sequenceno", "0001");
			hashmap.put("code", ob.get("code"));
		}
		resultMapList.add(ob);
		formDatas.put(dynamicTempalteId, resultMapList);
		String dataId = dyFormApiFacade.saveFormData(dynamicTempalteId,
				formDatas, null, null);
		String title = (String) dyFormApiFacade.getDyFormData(
				dynamicTempalteId, dataId).getFieldValueByMappingName(
				"File_title");
		String editFileUser = (String) dyFormApiFacade.getDyFormData(
				dynamicTempalteId, dataId).getFieldValueByMappingName(
				"File_editFile");
		String readFileUser = (String) dyFormApiFacade.getDyFormData(
				dynamicTempalteId, dataId).getFieldValueByMappingName(
				"File_readFile");
		FmFileBean fileBean = new FmFileBean();
		fileBean.setFolderId("7ac92079-f292-4695-9073-0ddb81067cf3");
		fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
		fileBean.setEditFile(editFileUser);
		fileBean.setReadFile(readFileUser);
		fileBean.setTitle(title);
		fileBean.setReservedText1(ob.get("customercode").toString());
		fileBean.setReservedText2(ob.get("sequenceno").toString());
		fileBean.setReservedText3(ob.get("version").toString());
		fileBean.setDynamicDataId(dataId);
		fileBean.setDynamicFormId(dynamicTempalteId);
		fileManagerService.saveFile(fileBean);
		transferToSap("", fileBean.getTitle());
		return hashmap;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Map upgradeFile(String s) {
		HashMap hashmap = new HashMap();
		FmFileBean fileBean = new FmFileBean();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			String as1[] = as[i].split(",");
			FmFile fmFile = fmFileDao.get(as1[0]);
			String oldCode = fmFile.getTitle();
			DyFormData dyFormData = dyFormApiFacade.getDyFormData(
					fmFile.getDynamicFormId(), fmFile.getDynamicDataId());
			Map formDatas = dyFormData.getFormDatas();
			Map<String, List<Map<String, Object>>> listMap = new HashMap<String, List<Map<String, Object>>>();

			String version = fmFile.getReservedText3().replaceAll("V", "");
			Double fversion = Double.valueOf(version) + 0.1;
			version = "V" + fversion;
			formDatas.put("version",
					version.substring(0, version.indexOf(".") + 2));
			fmFile.setReservedText3(formDatas.get("version").toString());
			formDatas.put("code",
					"P" + fmFile.getReservedText1() + fmFile.getReservedText2()
							+ formDatas.get("version"));
			formDatas.put("uuid", fmFile.getDynamicDataId());
			fmFile.setTitle(formDatas.get("code").toString());
			fmFile.setShowTitles(formDatas.get("code").toString());
			fmFile.setModifier(SpringSecurityUtils.getCurrentUserId());
			fmFile.setModifyTime(new Date());
			hashmap.put("code", formDatas.get("code").toString());
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			resultMapList.add(formDatas);
			listMap.put(fmFile.getDynamicFormId(), resultMapList);
			String dataId = dyFormApiFacade.saveFormData(
					fmFile.getDynamicFormId(), listMap, null, null);
			String newCode = fmFile.getTitle();
			transferToSap(oldCode, newCode);
			// BeanUtils.copyProperties(fmFile, fileBean);
			// fileBean.setFolderId(fmFile.getFmFolder().getUuid());
			// fileManagerService.saveFile(fileBean);
			// fileManagerService.saveFmFile(version, dyformdata);
		}
		return hashmap;
	}

	private void transferToSap(String oldCode, String newCode) {
		SAPRfcJson util = new SAPRfcJson("");
		util.setField("PI_BMID1", newCode);
		if (StringUtils.isNotEmpty(oldCode)) {
			util.setField("PI_BMID2", oldCode);
		}
		SAPRfcJson rfcjson = saprfcservice.RFC("sapConnectConfig", "ZOA_BMID",
				util.getRfcJson(), 1, -1, null);
		if (null == rfcjson) {
			throw new RuntimeException("传递SAP失败");
		}
		JSONObject jObject = rfcjson.getStructure("PT_RETURN");
		if ("E".equals(jObject.get("TYPE").toString())) {
			throw new RuntimeException("传递SAP失败");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<FmFile> getFiles(String uuid) {
		return fmFileDao.find("from FmFile where status=2 and fmFolder.uuid=?",
				uuid);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Map searchFiles(String uuid) {
		HashMap hashmap = new HashMap();
		List<FmFile> files = fmFileDao.find(
				"from FmFile where status=2 and fmFolder.uuid=?", uuid);
		hashmap.put("files", files);
		return hashmap;
	}

}
