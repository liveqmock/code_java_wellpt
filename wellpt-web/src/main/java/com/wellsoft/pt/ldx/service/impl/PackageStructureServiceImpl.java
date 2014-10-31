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
import com.wellsoft.pt.ldx.service.PackageStructureService;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class PackageStructureServiceImpl extends BaseServiceImpl implements
		PackageStructureService {

	@Autowired
	private FmFileDao fmFileDao;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FileManagerService fileManagerService;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Map generateFile(String s) {
		HashMap hashmap = new HashMap();
		String as[] = s.split(";");
		for (int i = 0; i < as.length; i++) {
			String as1[] = as[i].split(",");
			FmFile fmFile = fmFileDao.get(as1[0]);
			String code = fmFile.getTitle();
			List<FmFile> fmFiles = fmFileDao
					.find("from FmFile where status=2 and fmFolder.uuid=? and reservedText1=?",
							"f35ffa2e-93b5-42e6-b45d-16eb7ffac1ca", code);
			String dynamicTempalteId = "c4d37e12-6e0e-481c-a22a-a49c1cb8df86";
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			if (null != fmFiles && fmFiles.size() > 0) {
				ob.put("coding",
						"LDS_"
								+ code
								+ "_"
								+ StringUtils.leftPad(
										String.valueOf(fmFiles.size() + 1), 3,
										'0') + "_V1.0");
				ob.put("pscoding", code);
				ob.put("seno", StringUtils.leftPad(
						String.valueOf(fmFiles.size() + 1), 3, '0'));
				ob.put("version", "V1.0");
				hashmap.put(
						"code",
						"LDS_"
								+ code
								+ "_"
								+ StringUtils.leftPad(
										String.valueOf(fmFiles.size() + 1), 3,
										'0') + "_V1.0");
			} else {
				ob.put("coding", "LDS_" + code + "_001_V1.0");
				ob.put("pscoding", code);
				ob.put("seno", "001");
				ob.put("version", "V1.0");
				hashmap.put("code", "LDS_" + code + "_001_V1.0");
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
			fileBean.setFolderId("f35ffa2e-93b5-42e6-b45d-16eb7ffac1ca");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText1(ob.get("pscoding").toString());
			fileBean.setReservedText2(ob.get("seno").toString());
			fileBean.setReservedText3(ob.get("version").toString());
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
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
			String version = fmFile.getReservedText3().replaceAll("V", "");
			Double fversion = Double.valueOf(version) + 0.1;
			version = "V" + fversion;
			formDatas.put("version",
					version.substring(0, version.indexOf(".") + 2));
			fmFile.setReservedText3(formDatas.get("version").toString());
			formDatas.put(
					"coding",
					"LDS_" + fmFile.getReservedText1() + "_"
							+ fmFile.getReservedText2() + "_"
							+ formDatas.get("version"));
			formDatas.put("uuid", fmFile.getDynamicDataId());
			fmFile.setTitle(formDatas.get("coding").toString());
			fmFile.setShowTitles(formDatas.get("coding").toString());
			fmFile.setModifier(SpringSecurityUtils.getCurrentUserId());
			fmFile.setModifyTime(new Date());
			hashmap.put("code", formDatas.get("coding").toString());
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			resultMapList.add(formDatas);
			listMap.put(fmFile.getDynamicFormId(), resultMapList);
			String dataId = dyFormApiFacade.saveFormData(
					fmFile.getDynamicFormId(), listMap, null, null);
//			BeanUtils.copyProperties(fmFile, fileBean);
//			fileBean.setFolderId(fmFile.getFmFolder().getUuid());
//			fileManagerService.saveFile(fileBean);
//			fileManagerService.saveFmFile(version, dyformdata);
		}
		return hashmap;
	}

}
