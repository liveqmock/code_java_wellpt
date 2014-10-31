package com.wellsoft.pt.ldx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.file.bean.FmFileBean;
import com.wellsoft.pt.file.common.FileManagerConstant;
import com.wellsoft.pt.file.dao.FmFileDao;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.file.service.FileManagerService;
import com.wellsoft.pt.ldx.service.PackagePageService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class PackagePageServiceImpl extends BaseServiceImpl implements
		PackagePageService {

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FileManagerService fileManagerService;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Map generateFile(String customer, String material) {
		HashMap hashmap = new HashMap();
		List<FmFile> fmFiles = fmFileDao
				.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText1=? and reservedText2=?",
						"aa997fa5-8425-4caa-84bf-cd631721e741", customer,
						material);
		String dynamicTempalteId = "c2d1a1b0-fec8-4b7e-b039-22c5e18dd997";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		if (null != fmFiles && fmFiles.size() > 0) {
			ob.put("code",
					"B"
							+ customer
							+ material
							+ StringUtils.leftPad(
									String.valueOf(fmFiles.size() + 1), 4, '0')
							+ "V1.0");
			ob.put("customercode", customer);
			ob.put("materialcode", material);
			ob.put("version", "V1.0");
			ob.put("secuenceno", StringUtils.leftPad(
					String.valueOf(fmFiles.size() + 1), 4, '0'));
			hashmap.put("code", ob.get("code"));
		} else {
			ob.put("code", "B" + customer + material + "0001V1.0");
			ob.put("customercode", customer);
			ob.put("materialcode", material);
			ob.put("version", "V1.0");
			ob.put("secuenceno", "0001");
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
		fileBean.setFolderId("aa997fa5-8425-4caa-84bf-cd631721e741");
		fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
		fileBean.setEditFile(editFileUser);
		fileBean.setReadFile(readFileUser);
		fileBean.setTitle(title);
		fileBean.setReservedText1(ob.get("customercode").toString());
		fileBean.setReservedText2(ob.get("materialcode").toString());
		fileBean.setReservedText3(ob.get("secuenceno").toString());
		fileBean.setReservedText4(ob.get("version").toString());
		fileBean.setDynamicDataId(dataId);
		fileBean.setDynamicFormId(dynamicTempalteId);
		fileManagerService.saveFile(fileBean);
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
			DyFormData dyFormData = dyFormApiFacade.getDyFormData(
					fmFile.getDynamicFormId(), fmFile.getDynamicDataId());
			Map formDatas = dyFormData.getFormDatas();
			Map<String, List<Map<String, Object>>> listMap = new HashMap<String, List<Map<String, Object>>>();

			String version = fmFile.getReservedText4().replaceAll("V", "");
			Double fversion = Double.valueOf(version) + 0.1;
			version = "V" + fversion;
			formDatas.put("version",
					version.substring(0, version.indexOf(".") + 2));
			fmFile.setReservedText4(formDatas.get("version").toString());
			formDatas.put(
					"code",
					"B" + fmFile.getReservedText1() + fmFile.getReservedText2()
							+ fmFile.getReservedText3()
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
			// BeanUtils.copyProperties(fmFile, fileBean);
			// fileBean.setFolderId(fmFile.getFmFolder().getUuid());
			// fileManagerService.saveFile(fileBean);
			// fileManagerService.saveFmFile(version, dyformdata);
		}
		return hashmap;
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
