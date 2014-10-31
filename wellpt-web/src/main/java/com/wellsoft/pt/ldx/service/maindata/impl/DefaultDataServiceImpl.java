package com.wellsoft.pt.ldx.service.maindata.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.core.service.impl.BaseServiceImpl;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.file.bean.FmFileBean;
import com.wellsoft.pt.file.common.FileManagerConstant;
import com.wellsoft.pt.file.service.FileManagerService;
import com.wellsoft.pt.ldx.service.maindata.DefaultDataService;
import com.wellsoft.pt.ldx.util.DateUtils;

@Service
@Transactional
public class DefaultDataServiceImpl extends BaseServiceImpl implements
		DefaultDataService {

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private FileManagerService fileManagerService;

	public DefaultDataServiceImpl() {
	}

	@Override
	public void importFico(Sheet sh) {
		String dynamicTempalteId = "46eb4606-c4f2-472c-9c7e-2329191c71e0";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][9];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("ficoKj1Jgkz", rowarr[i][3]);
			ob.put("ficoKj1Pgl", rowarr[i][4]);
			ob.put("ficoKj1Jgqd", rowarr[i][5]);
			ob.put("ficoCb1Wcbgs", rowarr[i][6]);
			ob.put("ficoCb1Ysz", rowarr[i][7]);
			ob.put("sdKmszz", rowarr[i][8]);
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
			fileBean.setFolderId("2b76e98f-d45b-4075-a6cb-df23a7e7108b");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText4((String) ob.get("wlzName"));
			fileBean.setReservedText1((String) ob.get("gcCode"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importSd(Sheet sh) {
		String dynamicTempalteId = "03140bdf-9add-42bd-b8de-9c03345e6db6";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][7];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("describle", rowarr[i][3]);
			ob.put("sdXszz", rowarr[i][5]);
			ob.put("sdFxqd", rowarr[i][6]);
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
			fileBean.setFolderId("08d1a20d-8147-4ef1-b923-ea7083e9bd7f");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText4((String) ob.get("wlzName"));
			fileBean.setReservedText1((String) ob.get("gcCode"));
			fileBean.setReservedText6((String) ob.get("describle"));
			fileBean.setReservedText2((String) ob.get("sdXszz"));
			fileBean.setReservedText3((String) ob.get("sdFxqd"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importMm(Sheet sh) {
		String dynamicTempalteId = "6e77acd6-58ac-4430-9e10-97f0bbde0a46";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][9];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("mmWhbs", rowarr[i][3]);
			ob.put("mmCgz", rowarr[i][4]);
			ob.put("mmHyqd", rowarr[i][5]);
			ob.put("mmPegl", rowarr[i][6]);
			ob.put("mmDdwb", rowarr[i][7]);
			ob.put("ppMrp2Jhjhsj", rowarr[i][8]);
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
			fileBean.setFolderId("59bc6064-c015-460e-9452-a1219ff3af2f");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText4((String) ob.get("wlzName"));
			fileBean.setReservedText1((String) ob.get("gcCode"));
			fileBean.setReservedText2((String) ob.get("mmCgz"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importBaoguan(Sheet sh) {
		String dynamicTempalteId = "522eca9c-b61e-4606-8f0c-893aec69c67e";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][6];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("describle", rowarr[i][2]);
			ob.put("sdBgzwm", rowarr[i][4]);
			ob.put("sdBgywm", rowarr[i][5]);
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
			fileBean.setFolderId("1ff8c678-27bf-431f-b493-f0ca25fdfca2");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText1((String) ob.get("wlzName"));
			fileBean.setReservedText2((String) ob.get("describle"));
			fileBean.setReservedText3((String) ob.get("sdBgzwm"));
			fileBean.setReservedText4((String) ob.get("sdBgywm"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importFactory(Sheet sh) {
		String dynamicTempalteId = "ce6efb30-5e32-492d-a026-ad6f4d66f791";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][9];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("classify", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("productGroup", rowarr[i][2]);
			ob.put("productDecs", rowarr[i][3]);
			ob.put("describle", rowarr[i][4]);
			ob.put("factoryCode", rowarr[i][5]);
			ob.put("scstore", rowarr[i][6]);
			ob.put("wbstore", rowarr[i][7]);
			ob.put("sno", rowarr[i][8]);
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
			fileBean.setFolderId("e117d0eb-dbdc-4628-9dcd-57030911b4f6");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText2((String) ob.get("productGroup"));
			fileBean.setReservedText1((String) ob.get("productDecs"));
			fileBean.setReservedText6((String) ob.get("describle"));
			fileBean.setReservedText3((String) ob.get("factoryCode"));
			fileBean.setReservedText4((String) ob.get("scstore"));
			fileBean.setReservedText5((String) ob.get("wbstore"));
			fileBean.setReservedText7((String) ob.get("sno"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importPp(Sheet sh) {
		String dynamicTempalteId = "86700b3a-ed62-47c5-85b3-01f279ba14e6";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][31];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("cpCode", rowarr[i][3]);
			ob.put("sno", rowarr[i][4]);
			ob.put("ppMrp1Type", rowarr[i][6]);
			ob.put("ppMrp1Control", rowarr[i][7]);
			ob.put("ppMrp1Pldx", rowarr[i][8]);
			ob.put("ppMrp1Group", rowarr[i][9]);
			ob.put("ppMrp2Cglx", rowarr[i][10]);
			ob.put("ppMrp2Tscgl", rowarr[i][11]);
			ob.put("ppMrp2Fc", rowarr[i][12]);
			ob.put("scStoreAddr", rowarr[i][13]);
			ob.put("wbcgStoreAddr", rowarr[i][14]);
			ob.put("ppMrp2Zzscsj", rowarr[i][15]);
			ob.put("ppMrp2Shclsj", rowarr[i][16]);
			ob.put("ppMrp2Bjm", rowarr[i][17]);
			ob.put("ppMrp2Aqkc", rowarr[i][18]);
			ob.put("ppMrp3Clz", rowarr[i][19]);
			ob.put("ppMrp3Xhms", rowarr[i][20]);
			ob.put("ppMrp3Xqxhqj", rowarr[i][21]);
			ob.put("ppMrp3Nxxhqj", rowarr[i][22]);
			ob.put("ppMrp3Kyxjc", rowarr[i][23]);
			ob.put("ppMrp4Bjfp", rowarr[i][24]);
			ob.put("ppMrp4Dljz", rowarr[i][25]);
			ob.put("ppGzjhCswj", rowarr[i][26]);
			ob.put("ppGzjhScgly", rowarr[i][27]);
			ob.put("ppGzjhBzyc", rowarr[i][28]);
			ob.put("ppGzjhGdyc", rowarr[i][29]);
			ob.put("ppBstma", rowarr[i][30]);
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
			fileBean.setFolderId("0b25c7a2-02c4-449d-abd3-b72edbe01cd5");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText4((String) ob.get("wlzName"));
			fileBean.setReservedText1((String) ob.get("gcCode"));
			fileBean.setReservedText2((String) ob.get("cpCode"));
			if (StringUtils.isNotEmpty((String) ob.get("sno"))) {
				fileBean.setReservedNumber1((Integer) ob.get("sno"));
			}
			fileBean.setReservedText3((String) ob.get("ppMrp1Control"));
			fileBean.setReservedText5((String) ob.get("ppMrp1Pldx"));
			fileBean.setReservedText6((String) ob.get("ppMrp1Group"));
			fileBean.setReservedText8((String) ob.get("ppMrp2Cglx"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importPlan(Sheet sh) {
		String dynamicTempalteId = "b52b04a3-06f7-4731-8140-09baf6120760";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][9];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("cpCode", rowarr[i][3]);
			ob.put("priceWhbs", rowarr[i][4]);
			ob.put("priceJhjg", rowarr[i][5]);
			ob.put("ppMrp1Srz", rowarr[i][6]);
			ob.put("priceJhjgrq", rowarr[i][7]);
			ob.put("ficoKj1Jgdw", rowarr[i][8]);
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
			fileBean.setFolderId("4da18fa1-75a6-4214-a4b8-71856fd62da9");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText2((String) ob.get("wlzName"));
			fileBean.setReservedText3((String) ob.get("gcCode"));
			fileBean.setReservedText4((String) ob.get("cpCode"));
			fileBean.setReservedText5((String) ob.get("priceJhjg"));
			fileBean.setReservedText6((String) ob.get("ppMrp1Srz"));
			fileBean.setReservedText7((String) ob.get("priceJhjgrq"));
			fileBean.setReservedText8((String) ob.get("ficoKj1Jgdw"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	@Override
	public void importQm(Sheet sh) {
		String dynamicTempalteId = "a35f4132-ceb4-4286-b73a-3b2956f603f0";
		int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
		String[][] rowarr = new String[rows - 1][17];
		for (int rowno = 0; rowno < rows - 1; rowno++) {
			Row row = sh.getRow(rowno + 1);
			int cols = row.getLastCellNum() - row.getFirstCellNum();
			for (int j = 0; j < cols; j++) {
				Cell cell = row.getCell(j);
				String col = "";
				col = getCellValue(cell, false, false);
				rowarr[rowno][j] = col;
			}
		}
		for (int i = 0; i < rows - 1; i++) {
			Map<String, List<Map<String, Object>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
			Map<String, Object> ob = new HashMap<String, Object>();
			ob.put("wlzCode", rowarr[i][0]);
			ob.put("wlzName", rowarr[i][1]);
			ob.put("gcCode", rowarr[i][2]);
			ob.put("cpCode", rowarr[i][3]);
			ob.put("qmPcgl", rowarr[i][5]);
			ob.put("qmJylx01", rowarr[i][6]);
			ob.put("qmJylx03", rowarr[i][7]);
			ob.put("qmJylx10", rowarr[i][8]);
			ob.put("qmJylx89", rowarr[i][9]);
			ob.put("qmJylxLdx01", rowarr[i][10]);
			ob.put("qmJylxLdx02", rowarr[i][11]);
			ob.put("qmJylxLdx03", rowarr[i][12]);
			ob.put("qmJylxLdx04", rowarr[i][13]);
			ob.put("qmJylxLdx05", rowarr[i][14]);
			ob.put("qmJylxLdx06", rowarr[i][15]);
			ob.put("qmPjjyq", rowarr[i][16]);
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
			fileBean.setFolderId("c858520c-c9ea-44da-bb5c-87ae65cce850");
			fileBean.setStatus(FileManagerConstant.FILE_STATUS_NORMAL);
			fileBean.setEditFile(editFileUser);
			fileBean.setReadFile(readFileUser);
			fileBean.setTitle(title);
			fileBean.setReservedText4((String) ob.get("wlzName"));
			fileBean.setReservedText1((String) ob.get("gcCode"));
			fileBean.setReservedText2((String) ob.get("cpCode"));
			fileBean.setReservedText3((String) ob.get("qmPcgl"));
			fileBean.setReservedText6((String) ob.get("qmPjjyq"));
			fileBean.setDynamicDataId(dataId);
			fileBean.setDynamicFormId(dynamicTempalteId);
			fileManagerService.saveFile(fileBean);
		}
	}

	private String getCellValue(Cell cell, boolean isUpperCase, boolean isdate) {
		String returnValue = "";

		if (null != cell) {

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字或日期
				if (isdate) {
					Date date = cell.getDateCellValue();
					returnValue = DateUtils.formatDate(date, "yyyyMMdd");
				} else {
					Double doubleValue = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("0");
					String str = df.format(doubleValue);
					if (str.endsWith(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					returnValue = str;
				}
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				if (isUpperCase)
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim().toUpperCase();
				else
					returnValue = cell.getStringCellValue() == null ? "" : cell
							.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				returnValue = "";
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				returnValue = "";
				break;
			default:
				returnValue = "";
				break;
			}
		}
		return returnValue;
	}
}
