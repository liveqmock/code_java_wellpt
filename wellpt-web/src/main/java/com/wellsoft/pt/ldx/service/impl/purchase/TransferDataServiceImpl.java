package com.wellsoft.pt.ldx.service.impl.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.file.bean.FmFileBean;
import com.wellsoft.pt.file.common.FileManagerConstant;
import com.wellsoft.pt.file.service.FileManagerService;
import com.wellsoft.pt.ldx.service.purchase.TransferDataService;

@Service
@Transactional
public class TransferDataServiceImpl extends BaseServiceImpl implements
		TransferDataService {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FileManagerService fileManagerService;

	public TransferDataServiceImpl() {
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData1(Map mapList) {
		String dynamicTempalteId = "96f5dc56-ec51-48eb-8f84-dc87a6c68b1e";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("code", mapList.get("code"));
		ob.put("name", mapList.get("name"));
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
		fileBean.setFolderId("bc2a898e-0a7e-428e-b6d9-a8502da039d7");
		fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
		fileBean.setEditFile(editFileUser);
		fileBean.setReadFile(readFileUser);
		fileBean.setTitle(title);
		fileBean.setReservedText1((String) ob.get("name"));
		fileBean.setDynamicDataId(dataId);
		fileBean.setDynamicFormId(dynamicTempalteId);
		fileManagerService.saveFile(fileBean);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData2(Map mapList) {
		String dynamicTempalteId = "c4d37e12-6e0e-481c-a22a-a49c1cb8df86";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("coding", mapList.get("coding"));
		ob.put("pscoding", mapList.get("pstructcode"));
		ob.put("seno", mapList.get("secuenceno"));
		ob.put("version", mapList.get("version"));
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

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData3(Map mapList) {
		String dynamicTempalteId = "6e5dcebf-cd4a-46f5-b30f-0bd26bf80d59";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("code", mapList.get("code"));
		ob.put("name", mapList.get("name"));
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
		fileBean.setFolderId("cdedc58a-752f-4dad-b2e1-2b9e690afa4b");
		fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
		fileBean.setEditFile(editFileUser);
		fileBean.setReadFile(readFileUser);
		fileBean.setTitle(title);
		fileBean.setReservedText1((String) ob.get("name"));
		fileBean.setDynamicDataId(dataId);
		fileBean.setDynamicFormId(dynamicTempalteId);
		fileManagerService.saveFile(fileBean);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData4(Map mapList) {
		String dynamicTempalteId = "1b88984d-c4f2-48a3-b145-e07d8dd9f7f3";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("code", mapList.get("code"));
		ob.put("name", mapList.get("name"));
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
		fileBean.setFolderId("02346d95-b793-46d1-82bd-c3677cf431b8");
		fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
		fileBean.setEditFile(editFileUser);
		fileBean.setReadFile(readFileUser);
		fileBean.setTitle(title);
		fileBean.setReservedText1((String) ob.get("name"));
		fileBean.setDynamicDataId(dataId);
		fileBean.setDynamicFormId(dynamicTempalteId);
		fileManagerService.saveFile(fileBean);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData5(Map mapList) {
		String dynamicTempalteId = "c2d1a1b0-fec8-4b7e-b039-22c5e18dd997";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("code", mapList.get("coding"));
		ob.put("customercode", mapList.get("baccountcode"));
		ob.put("materialcode", mapList.get("bmaterialscode"));
		ob.put("version", mapList.get("secuenceno"));
		ob.put("secuenceno", mapList.get("version"));
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
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void transferData6(Map mapList) {
		String dynamicTempalteId = "dbd6033f-e530-4e71-85ee-c7417fcad19d";
		Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> ob = new HashMap<String, Object>();
		ob.put("code", mapList.get("coding"));
		ob.put("customercode", mapList.get("baccountcode"));
		ob.put("version", mapList.get("bmaterialscode"));
		ob.put("sequenceno", mapList.get("secuenceno"));
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
	}
}
