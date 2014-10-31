/*
 * @(#)2012-11-14 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.ca.service.FJCAAppsService;
import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.dyview.facade.DyViewApiFacade;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.common.component.tree.TreeNode;
import com.wellsoft.pt.core.entity.IdEntity;
import com.wellsoft.pt.core.exception.WellException;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.dytable.bean.ColInfoBean;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.FormDataBean;
import com.wellsoft.pt.dytable.bean.FormDataColValBean;
import com.wellsoft.pt.dytable.bean.FormDataRecordBean;
import com.wellsoft.pt.dytable.bean.RootFormDataBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.bean.SubTableInfoBean;
import com.wellsoft.pt.dytable.bean.TableInfoBean;
import com.wellsoft.pt.dytable.dao.FieldOptionDao;
import com.wellsoft.pt.dytable.dao.FormDataDao;
import com.wellsoft.pt.dytable.dao.FormDefinitionDao;
import com.wellsoft.pt.dytable.dao.FormRelationDao;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FieldOption;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.entity.FormRelation;
import com.wellsoft.pt.dytable.exception.SaveDataException;
import com.wellsoft.pt.dytable.service.FieldDefinitionService;
import com.wellsoft.pt.dytable.service.FormDataService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormDataInfo;
import com.wellsoft.pt.dytable.support.FormDataSignature;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.unit.entity.CommonUnit;
import com.wellsoft.pt.unit.facade.UnitApiFacade;
import com.wellsoft.pt.utils.ca.FJCAUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 如何描述该类
 * 
 * @author jiangmb
 * @date 2012-11-14
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-14.1	jiangmb		2012-11-14		Create
 * </pre>
 * 
 */
@Service
@Transactional
public class FormDataServiceImpl implements FormDataService {
	Logger logger = Logger.getLogger(FormDataServiceImpl.class);
	@Autowired
	private FormDefinitionDao formDefinitionDao;
	@Autowired
	private FormDefinitionService formDefinitionService;
	@Autowired
	private FormDataDao formDataDao;
	@Autowired
	private FormRelationDao formRelationDao;

	@Autowired
	private MongoFileService mongoFileService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private FJCAAppsService fjcaAppsService;

	@Autowired
	private FieldDefinitionService fieldDefinitionService;

	@Autowired
	private DyViewApiFacade dyViewApiFacade;

	@Autowired
	private FieldOptionDao fieldOptionDao;

	@Autowired
	private BasicDataApiFacade basicDataApiFacade;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@Autowired
	private UnitApiFacade unitApiFacade;

	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	/**
	 * 保存表单数字签名
	 * 
	 * @param formData
	 * @return	返回签名在JCR中的结点名
	 * @throws Exception 
	 */
	@Override
	public String saveSignature(RootFormDataBean formData) {
		if (!formData.isEnableSignature()) {
			return null;
		}

		// 保存表单数字签名
		FormDataSignature signature = formData.getSignature();
		if (signature == null) {
			return null;
		}

		if (FormDataSignature.STATUS_FAILURE == signature.getStatus()) {
			throw new RuntimeException(signature.getRemark());
		}

		if (FormDataSignature.STATUS_SUCCESS == signature.getStatus()) {
			// 当前用户证书登录验证
			if (signature.getCertificate() != null) {
				fjcaAppsService.checkCurrentCertificate(signature.getCertificate());

				// 签名验证
				int retCode = FJCAUtils.verify(signature.getDigestValue(), signature.getSignatureValue(),
						signature.getCertificate());
				if (retCode != 0) {
					throw new RuntimeException("表单数据数字签名验证失败!");
				}
			}
			//将由前台页面js生成的表单json保存成一个文件,且同时保存该文件对应的签名
			MongoFileEntity file = mongoFileService.saveFile("formData.json",
					new StringInputStream(signature.getFormData(), DytableConfig.CHARSET), signature.getDigestValue(),
					signature.getDigestAlgorithm(), signature.getSignatureValue(), signature.getCertificate());

			if (file == null) {//文件保存失败
				throw new RuntimeException("签名信息保存失败");
			} else {//文件保存成功
				return file.getId();
			}

		}
		return null;
	}

	/**
	 * 保存动态表单数据，返回表单数据uuid(包括新建与修改)
	 * 
	 * @param formData
	 * @return
	 */
	public synchronized Map<String, Object> save(RootFormDataBean formData) {
		Map<String, Object> map = new HashMap<String, Object>();

		boolean flag = false;

		List<FormDataBean> formDatas = formData.getFormDatas();

		FormDataBean mainFormDataBean = getMainDataBean(formDatas);//一个表单由一个主表的数据和多个从表数据据,在这里取出其中的主表

		//一个表单可能同时被多个用户操作，可能出现一个用户在操作一个旧表单的过程中,该表单已被其他用户操作过了
		ConflictStatus status = isOpConflictBtwMultiUser(mainFormDataBean);

		if (status.isConflict) {//表示该表单被其他用户操作过了，则抛出异常，由上层进行操作 
			throwSaveDataException(formDatas, status.columnsData, null);
			return null;
		}

		/*
		 * 表单没有问题，继续处理
		 */

		String mainFormDataUUID = mainFormDataBean.getDataUuid();
		boolean isNew = status.isNew;//用于判断本次操作是新增还是更新
		if (mainFormDataUUID == null && isNew) {
			mainFormDataUUID = mongoFileService.createFolderID();
		}

		for (int i = 0; i < formDatas.size(); i++) {
			FormDataBean formDataBean = formDatas.get(i);
			List<FormDataRecordBean> recordList = formDataBean.getRecordList();
			for (int j = 0; j < recordList.size(); j++) {
				FormDataRecordBean recordBean = recordList.get(j);
				List<FormDataColValBean> colValList = recordBean.getColValList();
				String dataUUID = recordBean.getUuid();//获取表单数据id

				if (isNew && formDataBean.isMainForm() && dataUUID == null) {
					dataUUID = mainFormDataUUID;
					recordBean.setUuid(mainFormDataUUID);
				} else {
					if (dataUUID == null) {
						dataUUID = mongoFileService.createFolderID();
					}
				}

				for (int k = 0; k < colValList.size(); k++) {
					FormDataColValBean col = colValList.get(k);
					//附件 处理 
					if (col.isAttach()) {
						List<String> fileIDs = col.getFileIds();
						String fieldName = col.getColEnName();//列名
						/* if(fileIDs != null && fileIDs.size() > 0){
							 mongoFileService.pushFilesToFolder(dataUUID, col.getFileIds(), fieldName); 
						 } */
						fileOperate(dataUUID, fieldName, fileIDs, isNew);
						col.setValue(null);
					}
				}
			}

		}

		if (formData.isEnableSignature()) {
			try {
				//保存签名
				String signatureFileID = this.saveSignature(formData);
				//将表单数据与表单签名关联起来
				if (!isNew) {
					//将原来旧的签名删除掉
					FormDataSignature signature = this.getSignatureFile(mainFormDataUUID);
					if (signature != null) {
						String dbSignatureFileID = signature.getFileID();
						mongoFileService.popFileFromFolder(mainFormDataUUID, dbSignatureFileID);
					}
				}
				mongoFileService.pushFileToFolder(mainFormDataUUID, signatureFileID,
						DytableConfig.DYTABLE_SIGNATURE_NODE_NAME_SUFFIX);
			} catch (Exception e) {
				throw new WellException("保存表单签名异常,请先是否用证书方式登录");
			}
		}

		//保存表单数据
		formDataDao.saveFormData(formDatas, status.isNew);

		map.put("uuid", mainFormDataUUID);

		map.put("flag", true);

		return map;
	}

	private void fileOperate(String folderID, String fieldName, List<String> fileIDListFromPage, boolean isNew) {

		List<String> newFileIDList = new ArrayList<String>(); //该列表中的fileID都是要push到数据库里面的

		if (isNew) {//新增
			newFileIDList.addAll(fileIDListFromPage);
		} else {//更新
			List<MongoFileEntity> dbFiles = mongoFileService.getFilesFromFolder(folderID, fieldName);//取出存放在数据库里面的文件
			if (dbFiles == null) {
				newFileIDList.addAll(fileIDListFromPage);
			} else {
				Iterator<MongoFileEntity> it1 = dbFiles.iterator();
				while (it1.hasNext()) {
					MongoFileEntity dbFile = it1.next();
					String dbFileID = dbFile.getId();
					boolean isExist = false;
					Iterator<String> it2 = fileIDListFromPage.iterator();
					while (it2.hasNext()) {
						String fileId = it2.next();
						if (dbFileID.equals(fileId)) {//如果该文件在数据库中已存在,则不用进行再处理
							it2.remove();
							isExist = true;
							break;
						}
					}

					if (!isExist) {//如果从页面传进来的文件没有该文件，则表示该文件是要被删除的
						mongoFileService.popFileFromFolder(folderID, dbFileID);
					}
				}
				newFileIDList.addAll(fileIDListFromPage);
			}

		}

		//将要保存到数据库的文件push到数据 库中
		if (newFileIDList.size() > 0) {
			mongoFileService.pushFilesToFolder(folderID, newFileIDList, fieldName);
		}

	}

	/**
	 * @param dataUUID
	 * @param fieldName
	 * @param fileIDListFromPage 从页面传进来的文件列表
	 * @param file2swf
	 * @param isNew
	private void fileOperate(String folderID, String fieldName,
			List<String> fileIDListFromPage, boolean file2swf, boolean isNew) {
		
		List<String> newFileIDList = new ArrayList<String>(); //该列表中的fileID都是要push到数据库里面的
		
		if(isNew){//新增
			newFileIDList.addAll(fileIDListFromPage);
		}else{//更新
			List<MongoFileEntity>  dbFiles = mongoFileService.getFilesFromFolder(folderID, fieldName);//取出存放在数据库里面的文件
			if(dbFiles == null){
				newFileIDList.addAll(fileIDListFromPage);
			}else{
				Iterator<MongoFileEntity> it1 = dbFiles.iterator();
				while(it1.hasNext()){
					MongoFileEntity dbFile = it1.next();
					String dbFileID = dbFile.getId();
					boolean isExist = false;
					Iterator<String> it2 = fileIDListFromPage.iterator();
					while(it2.hasNext()){
						String fileId = it2.next();
						if(dbFileID.equals(fileId)){//如果该文件在数据库中已存在,则不用进行再处理
							it2.remove();
							isExist = true;
							break;
						}
					}
					
					if(!isExist){//如果从页面传进来的文件没有该文件，则表示该文件是要被删除的
						mongoFileService.popFileFromFolder(folderID, dbFileID);
					}
				}
				newFileIDList.addAll(fileIDListFromPage); 
			}
			
		
		}
		
		//将要保存到数据库的文件push到数据 库中
		if(newFileIDList.size() > 0){
			mongoFileService.pushFilesToFolder(folderID, newFileIDList, fieldName);
		}
		
		
		//重新从数据库中取出文件,再产生其相应的副本
		//List<MongoFileEntity>  dbFiles = mongoFileService.getFilesFromFolder(folderID, fieldName);
		 
		for(String fileID: newFileIDList){//为新文件产生副本
			MongoFileEntity dbFile = mongoFileService.getFile(fileID);
			String fileName = dbFile.getFileName();
			if (fileName.toLowerCase().indexOf(".doc") > -1
					|| fileName.toLowerCase().indexOf(".docx") > -1
					|| fileName.toLowerCase().indexOf(".ppt") > -1
					|| fileName.toLowerCase().indexOf(".pptx") > -1
					|| fileName.toLowerCase().indexOf(".xls") > -1
					|| fileName.toLowerCase().indexOf(".xlsx") > -1
					|| fileName.toLowerCase().indexOf(".txt") > -1) {
				
				//产生swf文件副本
				mongoFileService.createReplicaOfSWF(dbFile.getInputstream(), dbFile.getId());
				 
			}
		}
		 
		
		
		
		 
		
	}
	*/

	public FormDataSignature getSignatureFile(String folderID) throws IOException {
		List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(folderID,
				DytableConfig.DYTABLE_SIGNATURE_NODE_NAME_SUFFIX);
		if (files != null && files.size() > 0) {
			MongoFileEntity signatureFile = files.get(0);
			LogicFileInfo fileInfo = signatureFile.getLogicFileInfo();
			FormDataSignature signature = new FormDataSignature();
			signature.setCertificate(fileInfo.getCertificate());
			signature.setDigestAlgorithm(fileInfo.getDigestAlgorithm());
			signature.setDigestValue(fileInfo.getDigestValue());
			signature.setSignatureValue(fileInfo.getSignatureValue());
			InputStream is = signatureFile.getInputstream();
			byte[] buffer = new byte[1024 * 20];
			int size = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((size = is.read(buffer)) != -1) {
				baos.write(buffer, 0, size);
			}
			String formData = baos.toString(DytableConfig.CHARSET);
			signature.setFormData(formData);
			signature.setFileID(fileInfo.getUuid());
			return signature;
		}
		return null;
	}

	/**
	 * 一个表单可能同时被多个用户操作，可能出现一个用户在操作一个旧表单的过程中,该表单已被其他用户操作过了
	 * @param mainFormDataBean
	 * @return
	 */
	private ConflictStatus isOpConflictBtwMultiUser(FormDataBean mainFormDataBean) {
		ConflictStatus status = new ConflictStatus();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> aa = new HashMap<String, Object>();

		String dataUuid = "";
		String modifyTime2 = null;

		dataUuid = mainFormDataBean.getDataUuid();
		if (dataUuid == null || dataUuid.trim().length() == 0) {//表示这是一个新表单,所以不会出现多个用户操作的情况
			return status;
		} else {//旧表单,通过判断修改时间来确认该表单是否在用户操作的过程中被其他用户修改过了
			String formUuid = mainFormDataBean.getFormDefinitionUuid();
			//标示（用于判断获取表单数据的时候是否清空临时节点下的文件）
			String temp = "save";
			FormAndDataBean form = getFormData(formUuid, dataUuid, temp);
			if (form == null) {//表示新表单
				return status;
			}
			String tableName = mainFormDataBean.getTableName();
			data = form.getData();
			aa = (Map<String, Object>) data.get(tableName);
			if (aa != null) {
				status.isNew = false;//数据库面有保存了，说明是旧数据
				modifyTime2 = aa.get("modify_time").toString();
				modifyTime2 = modifyTime2.split("\\.")[0];
			}
			String modifyTime = mainFormDataBean.getModifyTime();
			if (modifyTime != null && modifyTime2 != null) {
				if (!modifyTime.equals(modifyTime2)) {
					status.isConflict = true;
					status.columnsData = aa;
					return status;
				}
			}
		}

		return status;
	}

	private FormDataBean getMainDataBean(List<FormDataBean> formDatas) {
		for (FormDataBean formDataBean : formDatas) {
			if (formDataBean.getTableType() != null && "1".equals(formDataBean.getTableType())) {
				return formDataBean;
			}
		}
		return null;
	}

	/**
	 * 抛出保存失败异常
	 * @param formDatas
	 * @param serviceData
	 * @param msg
	 */
	private void throwSaveDataException(List<FormDataBean> formDatas, Map<String, Object> serviceData, String msg) {
		Map mapData = new HashMap();
		mapData.put("localData", formDatas);
		if (serviceData != null)
			mapData.put("serviceData", serviceData);
		if (msg != null)
			mapData.put("msg", msg);
		throw new SaveDataException(mapData);

	}

	/**
	 * 删除表单数据记录
	 * @param formUid
	 * @param dataUid
	 */
	public void delete(String formUid, String dataUid) {
		FormDefinition form = formDefinitionDao.findUniqueBy("uuid", formUid);
		// List<MainSubTableRelation> subList =
		// relationDao.getListByMainTableId(formUid);
		FormRelation example = new FormRelation();
		example.setMainFormUuid(formUid);
		List<FormRelation> subList = formRelationDao.findByExample(example);
		String[] dataUids = { dataUid };
		formDataDao.delete(form, subList, dataUids);
	}

	/**
	 * 删除表单数据记录(批量)
	 * 
	 * @param formUid
	 * @param dataUid
	 */
	public void delete(String formUid, String[] dataUids) {
		FormDefinition form = formDefinitionDao.findUniqueBy("uuid", formUid);
		// List<MainSubTableRelation> subList =
		// relationDao.getListByMainTableId(formUid);
		FormRelation example = new FormRelation();
		example.setMainFormUuid(formUid);
		List<FormRelation> subList = formRelationDao.findByExample(example);
		formDataDao.delete(form, subList, dataUids);
	}

	/**
	 * 根据主表uid获取对应的数据列表
	 * 
	 * @param tableType
	 *            1主表 2从表
	 * @param tableName
	 * @param dataUid
	 * @return
	 */
	public List<Map> getFormDataList(String tableType, String tableName, String dataUid) {
		return formDataDao.getFormDataList(tableType, tableName, dataUid, null);
	}

	/* 获取表单信息对象,此对象未添加字段控制信息（获取显示数据）
	 * 
	 * @param formUid
	 *            表单定义uuid
	 * @param dataUid
	 *            表单数据uuid
	 * @return
	 */
	public FormAndDataBean getFormShowData(String formUid, String dataUid, String temp) {
		FormAndDataBean form = new FormAndDataBean();
		Map data2 = new HashMap();
		form.setFormUuid(formUid);
		form.setDataUuid(dataUid);

		Map res = new HashMap();
		RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(formUid, true, true);
		String tableName = rootTableInfo.getTableInfo().getTableName();
		String tableType = "1";
		String bodyContent = rootTableInfo.getTableInfo().getHtmlBodyContent();
		Map data = new HashMap();
		Map mainTableData = new HashMap();
		if (StringUtils.isNotEmpty(dataUid)) {

			// 主表
			mainTableData = formDataDao
					.getFormDataList("1", rootTableInfo.getTableInfo().getTableName(), dataUid, temp).get(0);
			data.put(rootTableInfo.getTableInfo().getTableName(), mainTableData);
			//主表
			TableInfoBean tableInfoBean = rootTableInfo.getTableInfo();
			List<ColInfoBean> fields = tableInfoBean.getFields();
			Map<String, Object> zData = (Map<String, Object>) data.get(tableName);
			Map<String, Object> zData2 = new HashMap<String, Object>();
			if (zData != null) {
				for (String key : zData.keySet()) {
					for (ColInfoBean colInfoBean : fields) {
						if (colInfoBean.getColEnName().toUpperCase().equals(key.toUpperCase())) {
							String realVal = zData.get(key) == null ? "" : zData.get(key).toString();
							String showVal = "";
							if (!StringUtils.isBlank(realVal)) {
								if (DytableConfig.INPUTMODE_CHECKBOX.equals(colInfoBean.getInputMode())
										|| DytableConfig.INPUTMODE_RADIO.equals(colInfoBean.getInputMode())
										|| DytableConfig.INPUTMODE_SELECTMUTILFASE.equals(colInfoBean.getInputMode())) {
									if (colInfoBean.getOptionDataSource() == 1) {
										List<FieldOption> foList = fieldOptionDao.getListByFieldUid(colInfoBean
												.getUuid());
										String[] optionValArray = realVal.split(",");
										for (int ii = 0; ii < optionValArray.length; ii++) {
											for (FieldOption fo : foList) {
												if (!StringUtils.isBlank(optionValArray[ii])
														&& fo.getValue().equals(optionValArray[ii])) {
													showVal = "," + fo.getText();
												}
											}
										}
									} else {
										List<FieldOption> foList = fieldOptionDao.getListByFieldUid(colInfoBean
												.getUuid());
										List<DataDictionary> ddList = basicDataApiFacade
												.getDataDictionariesByType(foList.get(0).getValue());
										String[] optionValArray = realVal.split(",");
										for (int ii = 0; ii < optionValArray.length; ii++) {
											for (DataDictionary dd : ddList) {
												if (!StringUtils.isBlank(optionValArray[ii])
														&& dd.getCode().equals(optionValArray[ii])) {
													showVal = "," + dd.getName();
												}
											}
										}
									}
									showVal = showVal.replaceFirst(",", "");
								} else if (DytableConfig.INPUTMODE_TREESELECT.equals(colInfoBean.getInputMode())) {//树形下拉框
									//									if (colInfoBean.getServiceName().indexOf("${data}") < 0) {
									//										String[] tempArray = colInfoBean.getServiceName().split("\\.");
									//										String servicename = tempArray[0];//服务名
									//										String method = tempArray[1].split("\\(")[0]; //方法名
									//										String datas_ = tempArray[1].split("\\(")[1].replace(")", ""); //数据集合
									//										String[] datas = datas_.split(",");
									//										String data_ = datas[0].replaceAll("'", "");
									//										Class<? extends Object> serviceName = ApplicationContextHolder.getBean(
									//												servicename).getClass();
									//										try {
									//											Method serviceMethod = serviceName.getMethod(method, String.class,
									//													String.class);
									//											List<TreeNode> treeNodes = (List<TreeNode>) serviceMethod.invoke(
									//													serviceName, data_);
									//											for (TreeNode treeNode : treeNodes) {
									//												if (treeNode.getData().equals(realVal)) {
									//													showVal = treeNode.getName();
									//												}
									//											}
									//										} catch (SecurityException e) {
									//											// TODO Auto-generated catch block
									//											e.printStackTrace();
									//										} catch (NoSuchMethodException e) {
									//											// TODO Auto-generated catch block
									//											e.printStackTrace();
									//										} catch (IllegalArgumentException e) {
									//											// TODO Auto-generated catch block
									//											e.printStackTrace();
									//										} catch (IllegalAccessException e) {
									//											// TODO Auto-generated catch block
									//											e.printStackTrace();
									//										} catch (InvocationTargetException e) {
									//											// TODO Auto-generated catch block
									//											e.printStackTrace();
									//										}
									//									}
								} else if (DytableConfig.INPUTMODE_ORCHOOSE1.equals(colInfoBean.getInputMode())) {//组织选择框（人员）
									String[] tempChoose = realVal.split(",");
									for (int o = 0; o < tempChoose.length; o++) {
										if (!StringUtils.isBlank(tempChoose[o])) {
											User user = orgApiFacade.getUserById(tempChoose[o]);
											if (user != null) {
												showVal = "," + user.getUserName();
											}
										}
									}
									showVal = showVal.replaceFirst(",", "");
								} else if (DytableConfig.INPUTMODE_ORCHOOSE2.equals(colInfoBean.getInputMode())) {//组织选择框（部门）
									String[] tempChoose = realVal.split(",");
									for (int o = 0; o < tempChoose.length; o++) {
										if (!StringUtils.isBlank(tempChoose[o])) {
											Department department = orgApiFacade.getDepartmentById(tempChoose[o]);
											if (department != null) {
												showVal = "," + department.getName();
											}
										}
									}
									showVal = showVal.replaceFirst(",", "");
								} else if (DytableConfig.INPUTMODE_ORCHOOSE3.equals(colInfoBean.getInputMode())) {//组织选择框（部门+人员）
									String[] tempChoose = realVal.split(",");
									for (int o = 0; o < tempChoose.length; o++) {
										if (!StringUtils.isBlank(tempChoose[o])) {
											if (tempChoose[o].substring(0, 1).equals("U")) {
												User user = orgApiFacade.getUserById(tempChoose[o]);
												if (user != null) {
													showVal = "," + user.getUserName();
												}
											} else if (tempChoose[o].substring(0, 1).equals("D")) {
												Department department = orgApiFacade.getDepartmentById(tempChoose[o]);
												if (department != null) {
													showVal = "," + department.getName();
												}
											}
										}
									}
									showVal = showVal.replaceFirst(",", "");
								} else if (DytableConfig.INPUTMODE_ORCHOOSE4.equals(colInfoBean.getInputMode())) {//组织选择框 (单位通讯录)
									String[] tempChoose = realVal.split(",");
									for (int o = 0; o < tempChoose.length; o++) {
										if (!StringUtils.isBlank(tempChoose[o])) {
											CommonUnit commonUnit = unitApiFacade.getCommonUnitById(tempChoose[o]);
											if (commonUnit != null) {
												showVal = "," + commonUnit.getName();
											}
										}
									}
									showVal = showVal.replaceFirst(",", "");
								}
							}
							if (StringUtils.isBlank(showVal)) {
								showVal = realVal;
							}
							zData2.put(key, showVal);
						}
					}
					if (key.toUpperCase().equals("ID")) {
						zData2.put("id", zData.get(key));
					} else if (key.toUpperCase().equals("CREATE_TIME")) {
						zData2.put("create_time", zData.get(key));
					} else if (key.toUpperCase().equals("MODIFY_TIME")) {
						zData2.put("modify_time", zData.get(key));
					} else if (key.toUpperCase().equals("CREATOR")) {
						zData2.put("creator", zData.get(key));
					} else if (key.toUpperCase().indexOf("FILELIST") != -1) {
						zData2.put(key.toLowerCase(), zData.get(key));
					} else if (key.toUpperCase().equals("BODYFILE")) {
						zData2.put("bodyFile", zData.get(key));
					}
				}
			}
			data2.put(tableName, zData2);
			// 从表
			List<SubTableInfoBean> subList = rootTableInfo.getSubTables();
			for (SubTableInfoBean subTab : subList) {
				String subTabName = subTab.getTableName();
				List<ColInfoBean> subFields = subTab.getFields();
				List<Map> subZDatas = formDataDao.getFormDataList("2", subTabName, dataUid, temp);
				List<Map> subZDatas1 = new ArrayList<Map>();
				if (subZDatas != null && subZDatas.size() > 0) {
					for (Map<String, Object> subZData : subZDatas) {
						if (subZData != null) {
							Map<String, Object> subZData2 = new HashMap<String, Object>();
							for (String key : subZData.keySet()) {
								for (ColInfoBean colInfoBean : subFields) {
									if (colInfoBean.getColEnName().toUpperCase().equals(key.toUpperCase())) {
										subZData2.put(colInfoBean.getColEnName(), subZData.get(key));
									}
								}
								if (key.toUpperCase().equals("ID")) {
									subZData2.put("id", subZData.get(key));
								} else if (key.toUpperCase().equals("CREATE_TIME")) {
									subZData2.put("create_time", subZData.get(key));
								} else if (key.toUpperCase().equals("MODIFY_TIME")) {
									subZData2.put("modify_time", subZData.get(key));
								} else if (key.toUpperCase().equals("CREATOR")) {
									subZData2.put("creator", subZData.get(key));
								} else if (key.toUpperCase().indexOf("FILELIST") != -1) {
									subZData2.put(key.toLowerCase(), subZData.get(key));
								} else if (key.toUpperCase().equals("BODYFILE")) {
									subZData2.put("bodyFile", subZData.get(key));
								}
							}
							subZDatas1.add(subZData2);
						}
					}
				}
				data2.put(subTabName, subZDatas1);
			}
		}
		rootTableInfo.getTableInfo().setHtmlBodyContent(bodyContent);
		/*************************宏的解析*******************************/
		//时间格式化
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy年");//设置日期格式
		SimpleDateFormat df4 = new SimpleDateFormat("yyyy年MM月");//设置日期格式
		SimpleDateFormat df5 = new SimpleDateFormat("MM月dd日");//设置日期格式
		SimpleDateFormat df6 = new SimpleDateFormat("yyyy");//设置日期格式
		SimpleDateFormat df7 = new SimpleDateFormat("HH:mm");//设置日期格式
		SimpleDateFormat df8 = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		SimpleDateFormat df9 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		SimpleDateFormat df10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//获得星期几
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		Map<Integer, String> moneyMap = new HashMap<Integer, String>();
		moneyMap.put(1, "一");
		moneyMap.put(2, "二");
		moneyMap.put(3, "三");
		moneyMap.put(4, "四");
		moneyMap.put(5, "五");
		moneyMap.put(6, "六");
		moneyMap.put(7, "七");
		String week = "星期" + moneyMap.get(cal.get(Calendar.DAY_OF_WEEK)).toString();
		if (data.size() == 0) {//数据为空时，解析后修改默认值
			for (ColInfoBean colInfoBean : rootTableInfo.getTableInfo().getFields()) {
				String tempStr = colInfoBean.getDefaultValue();
				if (tempStr != null && !tempStr.equals("")) {
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000-01-01)}", df1.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月1日)}", df2.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年)}", df3.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月)}", df4.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(1月1日)}", df5.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(星期一)}", "" + week);
					tempStr = StringUtils.replace(tempStr, "{当前年份(2000)}", df6.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前时间(12:00)}", df7.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前时间(12:00:00)}", df8.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00)}", df9.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00:00)}", df10.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前用户ID}", SpringSecurityUtils.getCurrentUserId());
					tempStr = StringUtils.replace(tempStr, "{当前用户姓名}", SpringSecurityUtils.getCurrentUserName());
					tempStr = StringUtils.replace(tempStr, "{当前用户部门(长名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentPath());
					tempStr = StringUtils.replace(tempStr, "{当前用户部门(短名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentName());
					tempStr = StringUtils.replace(tempStr, "{创建人ID}", SpringSecurityUtils.getCurrentUserId());
					tempStr = StringUtils.replace(tempStr, "{创建人姓名}", SpringSecurityUtils.getCurrentUserName());
					tempStr = StringUtils.replace(tempStr, "{创建人部门(长名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentPath());
					tempStr = StringUtils.replace(tempStr, "{创建人部门(短名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentName());
				}
				colInfoBean.setDefaultValue(tempStr);
			}
		} else {//数据不为空时，解析后修改数据
			List<ColInfoBean> colInfoBeanList = new ArrayList<ColInfoBean>();
			for (ColInfoBean colInfoBean : rootTableInfo.getTableInfo().getFields()) {
				if (colInfoBean.getDefaultValueWay() != null && colInfoBean.getDefaultValueWay().equals("4")
						&& colInfoBean.getDefaultValue() != null && !colInfoBean.getDefaultValue().equals("")) {
					String tempStr = colInfoBean.getDefaultValue();
					if (tempStr != null && !tempStr.equals("")) {
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000-01-01)}", df1.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月1日)}", df2.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年)}", df3.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月)}", df4.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(1月1日)}", df5.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(星期一)}", "" + week);
						tempStr = StringUtils.replace(tempStr, "{当前年份(2000)}", df6.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前时间(12:00)}", df7.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前时间(12:00:00)}", df8.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00)}", df9.format(new Date()));
						tempStr = StringUtils
								.replace(tempStr, "{当前日期时间(2000-01-01 12:00:00)}", df10.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前用户ID}", SpringSecurityUtils.getCurrentUserId());
						tempStr = StringUtils.replace(tempStr, "{当前用户姓名}", SpringSecurityUtils.getCurrentUserName());
						tempStr = StringUtils.replace(tempStr, "{当前用户部门(长名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentPath());
						tempStr = StringUtils.replace(tempStr, "{当前用户部门(短名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentName());
						tempStr = StringUtils.replace(tempStr, "{创建人ID}", SpringSecurityUtils.getCurrentUserId());
						tempStr = StringUtils.replace(tempStr, "{创建人姓名}", SpringSecurityUtils.getCurrentUserName());
						tempStr = StringUtils.replace(tempStr, "{创建人部门(长名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentPath());
						tempStr = StringUtils.replace(tempStr, "{创建人部门(短名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentName());
					}
					mainTableData.put(colInfoBean.getColEnName(), tempStr);
				}
			}
			data.put(rootTableInfo.getTableInfo().getTableName(), mainTableData);
		}
		form.setForm(rootTableInfo);
		form.setData(data2);
		//		form.setUnDownFile(true);

		// 表单数据启用数字签名
		if ("2".equals(rootTableInfo.getTableInfo().getFormSign())) {
			/*try {
				// 获取签名数据
				String moduleName = DytableConfig.DYTABLE_JCR_MODLE_NAME;
				Object nodeName = form.getFieldValueByMappingName("signature_");
				if (nodeName != null && StringUtils.isNotBlank(nodeName.toString())) {
					InputStream is = fileService.downFile(moduleName, nodeName.toString(), nodeName.toString());
					if (is != null) {
						FormDataSignature signature = objectMapper.readValue(is, FormDataSignature.class);
						form.setSignature(signature);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			try {
				FormDataSignature signature = this.getSignatureFile(dataUid);
				form.setSignature(signature);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return form;
	}

	/**
	 * 获取表单信息对象,此对象未添加字段控制信息
	 * 
	 * @param formUid
	 *            表单定义uuid
	 * @param dataUid
	 *            表单数据uuid
	 * @return
	 */
	public FormAndDataBean getFormData(String formUid, String dataUid, String temp) {
		FormAndDataBean form = new FormAndDataBean();
		Map data2 = new HashMap();
		form.setFormUuid(formUid);
		form.setDataUuid(dataUid);

		Map res = new HashMap();
		RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(formUid, true, true);
		String tableName = rootTableInfo.getTableInfo().getTableName();
		String tableType = "1";
		String bodyContent = rootTableInfo.getTableInfo().getHtmlBodyContent();
		Map data = new HashMap();
		Map mainTableData = new HashMap();
		if (StringUtils.isNotEmpty(dataUid)) {

			// 主表
			List<Map> mainFormDatas = formDataDao.getFormDataList("1", rootTableInfo.getTableInfo().getTableName(),
					dataUid, temp);
			if (mainFormDatas == null || mainFormDatas.size() == 0) {
				return null;
			}
			mainTableData = mainFormDatas.get(0);
			data.put(rootTableInfo.getTableInfo().getTableName(), mainTableData);
			//主表
			TableInfoBean tableInfoBean = rootTableInfo.getTableInfo();
			List<ColInfoBean> fields = tableInfoBean.getFields();
			Map<String, Object> zData = (Map<String, Object>) data.get(tableName);
			Map<String, Object> zData2 = new HashMap<String, Object>();
			if (zData != null) {
				for (String key : zData.keySet()) {
					for (ColInfoBean colInfoBean : fields) {
						if (colInfoBean.getColEnName().toUpperCase().equals(key.toUpperCase())) {
							zData2.put(colInfoBean.getColEnName(), zData.get(key));
						}
					}
					if (key.toUpperCase().equals("ID")) {
						zData2.put("id", zData.get(key));
					} else if (key.toUpperCase().equals("CREATE_TIME")) {
						zData2.put("create_time", zData.get(key));
					} else if (key.toUpperCase().equals("MODIFY_TIME")) {
						zData2.put("modify_time", zData.get(key));
					} else if (key.toUpperCase().equals("CREATOR")) {
						zData2.put("creator", zData.get(key));
					} else if (key.toUpperCase().indexOf("FILELIST") != -1) {
						zData2.put(key.toLowerCase(), zData.get(key));
					} else if (key.toUpperCase().equals("BODYFILE")) {
						zData2.put("bodyFile", zData.get(key));
					} else if (key.toUpperCase().equals("SORT_ORDER")) {
						zData2.put("sort_order", zData.get(key));
					}
				}
			}
			data2.put(tableName, zData2);
			// 从表
			List<SubTableInfoBean> subList = rootTableInfo.getSubTables();
			for (SubTableInfoBean subTab : subList) {
				String subTabName = subTab.getTableName();
				List<ColInfoBean> subFields = subTab.getFields();
				List<Map> subZDatas = formDataDao.getFormDataList("2", subTabName, dataUid, temp);
				List<Map> subZDatas1 = new ArrayList<Map>();
				if (subZDatas != null && subZDatas.size() > 0) {
					for (Map<String, Object> subZData : subZDatas) {
						if (subZData != null) {
							Map<String, Object> subZData2 = new HashMap<String, Object>();
							for (String key : subZData.keySet()) {
								for (ColInfoBean colInfoBean : subFields) {
									if (colInfoBean.getColEnName().toUpperCase().equals(key.toUpperCase())) {
										subZData2.put(colInfoBean.getColEnName(), subZData.get(key));
									}
								}
								if (key.toUpperCase().equals("ID")) {
									subZData2.put("id", subZData.get(key));
								} else if (key.toUpperCase().equals("CREATE_TIME")) {
									subZData2.put("create_time", subZData.get(key));
								} else if (key.toUpperCase().equals("MODIFY_TIME")) {
									subZData2.put("modify_time", subZData.get(key));
								} else if (key.toUpperCase().equals("CREATOR")) {
									subZData2.put("creator", subZData.get(key));
								} else if (key.toUpperCase().indexOf("FILELIST") != -1) {
									subZData2.put(key.toLowerCase(), subZData.get(key));
								} else if (key.toUpperCase().equals("BODYFILE")) {
									subZData2.put("bodyFile", subZData.get(key));
								} else if (key.toUpperCase().equals("SORT_ORDER")) {
									subZData2.put("sort_order", subZData.get(key));
								}
							}
							subZDatas1.add(subZData2);
						}
					}
				}
				data2.put(subTabName, subZDatas1);
			}
		}
		rootTableInfo.getTableInfo().setHtmlBodyContent(bodyContent);
		/*************************宏的解析*******************************/
		//时间格式化
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy年");//设置日期格式
		SimpleDateFormat df4 = new SimpleDateFormat("yyyy年MM月");//设置日期格式
		SimpleDateFormat df5 = new SimpleDateFormat("MM月dd日");//设置日期格式
		SimpleDateFormat df6 = new SimpleDateFormat("yyyy");//设置日期格式
		SimpleDateFormat df7 = new SimpleDateFormat("HH:mm");//设置日期格式
		SimpleDateFormat df8 = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		SimpleDateFormat df9 = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		SimpleDateFormat df10 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//获得星期几
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		Map<Integer, String> moneyMap = new HashMap<Integer, String>();
		moneyMap.put(1, "一");
		moneyMap.put(2, "二");
		moneyMap.put(3, "三");
		moneyMap.put(4, "四");
		moneyMap.put(5, "五");
		moneyMap.put(6, "六");
		moneyMap.put(7, "七");
		String week = "星期" + moneyMap.get(cal.get(Calendar.DAY_OF_WEEK)).toString();
		if (data.size() == 0) {//数据为空时，解析后修改默认值
			for (ColInfoBean colInfoBean : rootTableInfo.getTableInfo().getFields()) {
				String tempStr = colInfoBean.getDefaultValue();
				if (tempStr != null && !tempStr.equals("")) {
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000-01-01)}", df1.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月1日)}", df2.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年)}", df3.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月)}", df4.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(1月1日)}", df5.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期(星期一)}", "" + week);
					tempStr = StringUtils.replace(tempStr, "{当前年份(2000)}", df6.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前时间(12:00)}", df7.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前时间(12:00:00)}", df8.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00)}", df9.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00:00)}", df10.format(new Date()));
					tempStr = StringUtils.replace(tempStr, "{当前用户ID}", SpringSecurityUtils.getCurrentUserId());
					tempStr = StringUtils.replace(tempStr, "{当前用户姓名}", SpringSecurityUtils.getCurrentUserName());
					tempStr = StringUtils.replace(tempStr, "{当前用户部门(长名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentPath());
					tempStr = StringUtils.replace(tempStr, "{当前用户部门(短名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentName());
					tempStr = StringUtils.replace(tempStr, "{创建人ID}", SpringSecurityUtils.getCurrentUserId());
					tempStr = StringUtils.replace(tempStr, "{创建人姓名}", SpringSecurityUtils.getCurrentUserName());
					tempStr = StringUtils.replace(tempStr, "{创建人部门(长名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentPath());
					tempStr = StringUtils.replace(tempStr, "{创建人部门(短名称)}",
							SpringSecurityUtils.getCurrentUserDepartmentName());
				}
				colInfoBean.setDefaultValue(tempStr);
			}
		} else {//数据不为空时，解析后修改数据
			List<ColInfoBean> colInfoBeanList = new ArrayList<ColInfoBean>();
			for (ColInfoBean colInfoBean : rootTableInfo.getTableInfo().getFields()) {
				if (colInfoBean.getDefaultValueWay() != null && colInfoBean.getDefaultValueWay().equals("4")
						&& colInfoBean.getDefaultValue() != null && !colInfoBean.getDefaultValue().equals("")) {
					String tempStr = colInfoBean.getDefaultValue();
					if (tempStr != null && !tempStr.equals("")) {
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000-01-01)}", df1.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月1日)}", df2.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年)}", df3.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(2000年1月)}", df4.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(1月1日)}", df5.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期(星期一)}", "" + week);
						tempStr = StringUtils.replace(tempStr, "{当前年份(2000)}", df6.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前时间(12:00)}", df7.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前时间(12:00:00)}", df8.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前日期时间(2000-01-01 12:00)}", df9.format(new Date()));
						tempStr = StringUtils
								.replace(tempStr, "{当前日期时间(2000-01-01 12:00:00)}", df10.format(new Date()));
						tempStr = StringUtils.replace(tempStr, "{当前用户ID}", SpringSecurityUtils.getCurrentUserId());
						tempStr = StringUtils.replace(tempStr, "{当前用户姓名}", SpringSecurityUtils.getCurrentUserName());
						tempStr = StringUtils.replace(tempStr, "{当前用户部门(长名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentPath());
						tempStr = StringUtils.replace(tempStr, "{当前用户部门(短名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentName());
						tempStr = StringUtils.replace(tempStr, "{创建人ID}", SpringSecurityUtils.getCurrentUserId());
						tempStr = StringUtils.replace(tempStr, "{创建人姓名}", SpringSecurityUtils.getCurrentUserName());
						tempStr = StringUtils.replace(tempStr, "{创建人部门(长名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentPath());
						tempStr = StringUtils.replace(tempStr, "{创建人部门(短名称)}",
								SpringSecurityUtils.getCurrentUserDepartmentName());
					}
					((Map) data2.get(tableName)).put(colInfoBean.getColEnName(), tempStr);
					//					mainTableData.put(colInfoBean.getColEnName(), tempStr);
				}
			}
			//			data.put(rootTableInfo.getTableInfo().getTableName(), mainTableData);
		}
		form.setForm(rootTableInfo);
		form.setData(data2);
		//		form.setUnDownFile(true);

		// 表单数据启用数字签名
		if ("2".equals(rootTableInfo.getTableInfo().getFormSign())) {
			/*try {
				// 获取签名数据
				String moduleName = DytableConfig.DYTABLE_JCR_MODLE_NAME;
				Object nodeName = form.getFieldValueByMappingName("signature_");
				if (nodeName != null && StringUtils.isNotBlank(nodeName.toString())) {
					InputStream is = fileService.downFile(moduleName, nodeName.toString(), nodeName.toString());
					if (is != null) {
						FormDataSignature signature = objectMapper.readValue(is, FormDataSignature.class);
						form.setSignature(signature);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			try {
				FormDataSignature signature = this.getSignatureFile(dataUid);
				form.setSignature(signature);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

		}
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wellsoft.pt.dytable.service.FormDataService#getFormFieldValue(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Object getFormFieldValue(String formUid, String dataUid, String fieldName) {
		Object value = null;
		try {
			FormDefinition formDefinition = formDefinitionDao.get(formUid);
			String queryString = "SELECT " + fieldName + " FROM " + formDefinition.getName() + " WHERE UUID = :uuid";
			value = formDefinitionDao.getSession().createSQLQuery(queryString).setParameter("uuid", dataUid)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wellsoft.pt.dytable.service.FormDataService#getFormFieldValue(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getSubFormFieldValue(String formUid, String dataUid, String fieldName) {
		String result = "";
		Object value = null;
		List<FormDataInfo> subForm = this.getSubFormDataInfo(formUid, dataUid);
		String fieldName_ = "";
		for (FormDataInfo formDataInfo : subForm) {
			FormDefinition formDefinition = formDefinitionService.getFormByUUID(formDataInfo.getFormUuid());
			Set<FieldDefinition> fieldDefinitions = formDefinition.getFieldDefinitions();
			for (FieldDefinition fieldDefinition : fieldDefinitions) {
				if (fieldDefinition.getDescname() != null && fieldDefinition.getDescname().equals(fieldName)) {
					//传入的fieldname为中文显示值
					fieldName_ = fieldDefinition.getFieldName();
				} else {//传入的fieldName为字段值
					fieldName_ = fieldName;
				}
			}
			try {
				String queryString = "SELECT " + fieldName_ + " FROM " + formDefinition.getName()
						+ " WHERE UUID = :uuid";
				value = formDefinitionDao.getSession().createSQLQuery(queryString)
						.setParameter("uuid", formDataInfo.getDataUuid()).uniqueResult();
				result += value.toString() + ";";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getFieldValueByMappingName(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public Object getFieldValueByMappingName(String formUuid, String dataUuid, String mappingName) {
		Object value = null;
		try {
			RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(formUuid, false, false);
			String fieldName = rootTableInfo.getFieldName(mappingName);
			if (StringUtils.isNotBlank(fieldName)) {
				FormDefinition formDefinition = formDefinitionDao.get(formUuid);
				String queryString = "SELECT " + fieldName + " FROM " + formDefinition.getName()
						+ " WHERE UUID = :uuid";
				value = formDefinitionDao.getSession().createSQLQuery(queryString)
						.setParameter(IdEntity.UUID, dataUuid).uniqueResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 
	 * 通过传入的formUuid，dataUuid,mappingName 查找并返回tableName,fieldName,fieldValue属性
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param mappingName
	 * @return
	 */
	public Object getFieldInfosByMappingName(String formUuid, String dataUuid, String mappingName) {
		Map<String, Object> info = new HashMap<String, Object>();
		Object value = null;
		try {
			RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(formUuid, false, false);
			String fieldName = rootTableInfo.getFieldName(mappingName);
			if (StringUtils.isNotBlank(fieldName)) {
				FormDefinition formDefinition = formDefinitionDao.get(formUuid);
				String queryString = "SELECT " + fieldName + " FROM " + formDefinition.getName()
						+ " WHERE UUID = :uuid";
				value = formDefinitionDao.getSession().createSQLQuery(queryString)
						.setParameter(IdEntity.UUID, dataUuid).uniqueResult();
			}
			info.put("tableName", rootTableInfo.getTableInfo().getTableName());
			info.put("fieldName", fieldName);
			info.put("fieldValue", value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getSubFormDataInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<FormDataInfo> getSubFormDataInfo(String formUuid, String dataUuid) {
		List<FormDataInfo> formDataInfos = new ArrayList<FormDataInfo>();
		FormAndDataBean form = getFormData(formUuid, dataUuid, null);
		List<SubTableInfoBean> subTableInfoBeans = form.getForm().getSubTables();
		for (SubTableInfoBean subTableInfoBean : subTableInfoBeans) {
			Object value = form.getData().get(subTableInfoBean.getTableName());
			if (value instanceof List) {
				List list = (List) value;
				for (int index = 0; index < list.size(); index++) {
					Object listData = list.get(index);
					if (listData instanceof Map) {
						Map map = (Map) listData;
						FormDataInfo formDataInfo = new FormDataInfo();
						formDataInfo.setFormName(subTableInfoBean.getTableName());
						formDataInfo.setFormUuid(subTableInfoBean.getTableId());
						formDataInfo.setDataUuid(map.get("id") == null ? "" : map.get("id").toString());
						formDataInfos.add(formDataInfo);
					}
				}
			}
		}
		return formDataInfos;
	}

	public List<QueryItem> query(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		FormDefinition formDefinition = formDefinitionDao.get(formUuid);
		String table = formDefinition.getName();
		String queryString = buildQueryString(false, table, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = formDefinitionDao.getSession().createSQLQuery(queryString);
		List<QueryItem> queryItems = sqlQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return queryItems;
	}

	public List<QueryItem> query2(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		FormDefinition formDefinition = formDefinitionDao.get(formUuid);
		String table = formDefinition.getName();
		String queryString = buildQueryString(false, table, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = formDefinitionDao.getSession().createSQLQuery(queryString);
		List<QueryItem> queryItems = sqlQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return queryItems;
	}

	public Integer queryTotal(String tableName) {
		String countString = "select count(*) from " + tableName;
		SQLQuery countQuery = formDefinitionDao.getSession().createSQLQuery(countString);
		Object count = countQuery.uniqueResult();
		return Integer.valueOf(count.toString());
	}

	public List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults) {
		String queryString = buildQueryString(distinct, tableName, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = formDefinitionDao.getSession().createSQLQuery(queryString);
		sqlQuery.setProperties(selectionArgs);
		List<QueryItem> queryItems = sqlQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return queryItems;
	}

	/**
	 * Description how to use this method
	 * 
	 * @param b
	 * @param table
	 * @param columns
	 * @param where
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 */
	private String buildQueryString(boolean distinct, String tables, String[] columns, String where, String groupBy,
			String having, String orderBy) {
		if (StringUtils.isBlank(groupBy) && !StringUtils.isBlank(having)) {
			throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
		}

		StringBuilder query = new StringBuilder(120);
		query.append("SELECT ");
		if (distinct) {
			query.append("DISTINCT ");
		}
		if (columns != null && columns.length != 0) {
			appendColumns(query, columns);
		} else {
			query.append("* ");
		}
		query.append("FROM ");
		query.append(tables);
		appendClause(query, " WHERE ", where);
		appendClause(query, " GROUP BY ", groupBy);
		appendClause(query, " HAVING ", having);
		appendClause(query, " ORDER BY ", orderBy);

		return query.toString();
	}

	/**
	 * Add the names that are non-null in columns to s, separating them with
	 * commas.
	 * 
	 * @param sb
	 * @param columns
	 */
	public static void appendColumns(StringBuilder sb, String[] columns) {
		int n = columns.length;

		for (int i = 0; i < n; i++) {
			String column = columns[i];

			if (column != null) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(column);
			}
		}
		sb.append(' ');
	}

	/**
	 * @param sb
	 * @param name
	 * @param clause
	 */
	private static void appendClause(StringBuilder sb, String name, String clause) {
		if (!StringUtils.isBlank(clause)) {
			sb.append(name);
			sb.append(clause);
		}
	}

	/** 
	 * 动态表单从表数据复制
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#copySubFormData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public List<String> copySubFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid,
			String targetDataUuid, String subFormUuid, String whereSql, Map<String, Object> values) {
		List<String> list2 = new ArrayList<String>();
		//从表的字段信息
		FormDefinition subFormDefinition = formDefinitionService.getFormByUUID(subFormUuid);
		//获得从表的表名
		String subTableName = subFormDefinition.getName();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = formDataDao.getSubFormDataList(subTableName, sourceFormUuid, sourceDataUuid, whereSql, values);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("parent_id", targetDataUuid);
			String srcSubFormDataUuid = (String) list.get(i).get("uuid");
			list.get(i).remove("uuid");

			String uuid = saveFormData(subFormUuid, list.get(i));
			list2.add(uuid);

			//复制文件
			Set<FieldDefinition> fieldDefinitions = subFormDefinition.getFieldDefinitions();
			for (FieldDefinition fd : fieldDefinitions) {
				if (fd.isAttach()) {
					String fieldName = fd.getFieldName();
					List<LogicFileInfo> files = this.mongoFileService.getNonioFilesFromFolder(srcSubFormDataUuid,
							fieldName);
					List<String> fileIDs = new ArrayList<String>();
					for (LogicFileInfo file : files) {
						fileIDs.add(file.getFileID());
					}
					if (fileIDs.size() > 0) {
						mongoFileService.pushFilesToFolder(uuid, fileIDs, fieldName);
					}

				}
			}

		}

		return list2;
	}

	/**
	 * 动态表单数据复制
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.dytable.service.FormDataService#copyFormData(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public String copyFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid) {
		FormAndDataBean form = getFormData(sourceFormUuid, sourceDataUuid, null);
		// 原表的字段信息
		List<ColInfoBean> colInfoBeans = form.getForm().getTableInfo().getFields();
		// 新表的字段信息
		FormDefinition targetForm = formDefinitionDao.get(targetFormUuid);
		Set<FieldDefinition> targetFormFields = targetForm.getFieldDefinitions();
		RootFormDataBean targetFormData = new RootFormDataBean();
		List<FormDataBean> formDatas = new ArrayList<FormDataBean>();
		List<FormDataColValBean> colValList = new ArrayList<FormDataColValBean>();
		List<FormDataRecordBean> recordList = new ArrayList<FormDataRecordBean>();
		FormDataBean dataBean = new FormDataBean();

		FormDataRecordBean dataRecordBean = new FormDataRecordBean();
		dataBean.setFormDefinitionUuid(targetFormUuid);
		dataBean.setTableName(targetForm.getName());
		dataBean.setTableType("1");
		for (Iterator<FieldDefinition> iter = targetFormFields.iterator(); iter.hasNext();) {
			FieldDefinition fdt = iter.next();
			for (int i = 0; i < colInfoBeans.size(); i++) {
				if (colInfoBeans.get(i).getColEnName().equals(fdt.getFieldName())) {
					Map formData = form.getData();
					Map mainForm = (Map) formData.get(form.getFormName(sourceFormUuid));
					System.out.println(mainForm);
					String a = fdt.getFieldName();
					System.out.println(a);
					String b = fdt.getDescname();
					System.out.println(b);
					String dataType = (String) mainForm.get(fdt.getType());
					Object fieldValue = null;
					if (fdt.isAttach()) {//拷贝附件类型的字段
						List<LogicFileInfo> fileEntities = mongoFileService.getNonioFilesFromFolder(sourceDataUuid, a);
						fieldValue = fileEntities;
					} else if ("20".equals(fdt.getInputType())) {
						if (mainForm.get(fdt.getFieldName()) != null
								&& StringUtils.isNotBlank((String) mainForm.get(fdt.getFieldName()))) {
							String value = (String) mainForm.get(fdt.getFieldName());
							String[] values = value.split(",");
							for (int j = 0; j < values.length; j++) {
								fieldValue = values[1];
							}
						}
					} else {
						fieldValue = (String) mainForm.get(fdt.getFieldName());
					}
					FormDataColValBean colvalBean = new FormDataColValBean();
					colvalBean.setColEnName(fdt.getFieldName());
					colvalBean.setDataType(fdt.getType());
					colvalBean.setValue(fieldValue);
					colvalBean.setInputMode(fdt.getInputType());
					colValList.add(colvalBean);
				}
			}
		}
		dataRecordBean.setColValList(colValList);
		recordList.add(dataRecordBean);
		dataBean.setRecordList(recordList);
		formDatas.add(dataBean);
		targetFormData.setFormDatas(formDatas);
		Map<String, Object> map = new HashMap<String, Object>();
		map = save(targetFormData);
		String uuid = (String) map.get("uuid");
		return uuid;
	}

	/**
	 * 
	 * 动态表单数据复制
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.wellsoft.pt.dytable.service.FormDataService#copyFormData(com.wellsoft.pt.dytable.bean.RootFormDataBean,
	 *      java.lang.String)
	 */
	@Override
	public String copyFormData(RootFormDataBean formAndDataBean, String targetFormUuid) {
		// 获取原表的信息
		List<FormDataBean> formDatas = formAndDataBean.getFormDatas();
		FormDefinition targetForm = formDefinitionDao.get(targetFormUuid);
		Set<FieldDefinition> targetFormFields = targetForm.getFieldDefinitions();
		RootFormDataBean targetFormData = new RootFormDataBean();

		List<FormDataColValBean> colValList = new ArrayList<FormDataColValBean>();
		FormDataRecordBean dataRecordBean = new FormDataRecordBean();
		FormDataBean dataBean = new FormDataBean();

		dataBean.setFormDefinitionUuid(targetFormUuid);
		dataBean.setTableName(targetForm.getName());
		dataBean.setTableType("1");
		List<FormDataRecordBean> recordList = new ArrayList<FormDataRecordBean>();
		List<FormDataBean> targetFormDatas = new ArrayList<FormDataBean>();
		for (int i = 0; i < formDatas.size(); i++) {
			FormDataBean formData = formDatas.get(i);
			String formDefinitionUuid = formData.getFormDefinitionUuid();
			List<FieldDefinition> fieldDefinitions = fieldDefinitionService.getFieldByForm(formDefinitionUuid);
			if ("1".equals(formDatas.get(i).getTableType())) {// 为主表
				for (Iterator<FieldDefinition> iter = targetFormFields.iterator(); iter.hasNext();) {
					FieldDefinition fdt = iter.next();
					for (int j = 0; j < fieldDefinitions.size(); j++) {
						if (fieldDefinitions.get(j).getFieldName().equals(fdt.getFieldName())) {
							Map<String, Object> mainForm = new HashMap<String, Object>();
							for (int l = 0; l < formDatas.get(i).getRecordList().size(); l++) {
								List<FormDataColValBean> a = formDatas.get(i).getRecordList().get(l).getColValList();
								for (int k = 0; k < a.size(); k++) {
									String value = null;
									try {
										value = a.get(k).getValue();
									} catch (Exception e) {

									}
									mainForm.put(a.get(k).getColEnName(), value);
								}
							}
							Object fieldValue = null;
							String fieldName = fdt.getFieldName();
							if (fdt.isAttach()) {//拷贝附件类型的字段
								List<LogicFileInfo> fileEntities = mongoFileService.getNonioFilesFromFolder(
										formData.getDataUuid(), fieldName);
								fieldValue = fileEntities;
							} else if ("20".equals(fdt.getInputType())) {
								String value = (String) mainForm.get(fdt.getFieldName());
								String[] values = value.split(",");
								for (int k = 0; k < values.length; k++) {
									fieldValue = values[1];
								}
							} else {
								fieldValue = (String) mainForm.get(fdt.getFieldName());
							}
							FormDataColValBean colvalBean = new FormDataColValBean();
							colvalBean.setColEnName(fdt.getFieldName());
							colvalBean.setDataType(fdt.getType());
							colvalBean.setValue(fieldValue);
							colvalBean.setInputMode(fdt.getInputType());
							colValList.add(colvalBean);

						}
					}
				}
			}
		}
		dataRecordBean.setColValList(colValList);
		recordList.add(dataRecordBean);
		dataBean.setRecordList(recordList);
		targetFormDatas.add(dataBean);
		targetFormData.setFormDatas(targetFormDatas);
		Map<String, Object> map = new HashMap<String, Object>();
		map = save(targetFormData);
		String uuid = (String) map.get("uuid");
		return uuid;
	}

	/** 
	 * 保存或者更新表单数据
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#saveFormData(java.lang.String, java.util.Map)
	 */
	@Override
	public String saveFormData(String formUuid, Map<String, Object> data) {
		String dataUuid = formDataDao.saveFormData(formUuid, data);
		return dataUuid;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getDigestValue(java.lang.String)
	 */
	@Override
	public FormDataSignature getDigestValue(String formData) {
		String digestValue = null;
		String digestAlgorithm = "MD5";
		try {

			MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
			Provider provider = md.getProvider();
			System.out.println(provider.getInfo());
			md.update(formData.getBytes("UTF8"));
			byte[] digestData = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digestData) {
				String s = Integer.toHexString((0x000000ff & b) | 0xffffff00).substring(6);
				sb.append(s);
			}
			digestValue = sb.toString();
			// System.out.println(digestValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("表单数据无法生成消息摘要!");
		}
		FormDataSignature signature = new FormDataSignature();
		signature.setDigestValue(digestValue);
		signature.setDigestAlgorithm(digestAlgorithm);
		return signature;
	}

	/** 
	 * 获取指定实例的字段
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getShiliZiduan()
	 */
	@Override
	public List<TreeNode> getShiliZiduan(String s, String s1) {
		TreeNode treenode = new TreeNode();
		List<TreeNode> list1 = treenode.getChildren();
		QueryItem queryItem = new QueryItem();
		queryItem.put("人事部门", "人事部门|U0000000039");
		queryItem.put("政策法规处", "政策法规处|U0000000041");
		queryItem.put("业务处室A", "业务处室A|U0000000043");
		queryItem.put("业务处室B", "业务处室B|U0000000045");
		queryItem.put("业务处室C", "业务处室C|U0000000047");
		for (String key : queryItem.keySet()) {
			TreeNode treenode1 = new TreeNode();
			treenode1.setName(key);
			treenode1.setData(queryItem.get(key));
			list1.add(treenode1);
		}
		return list1;
	}

	/**
	 * 
	 * 获得时间占用资源的树形(动态表单数据)
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	@Override
	public List<TreeNode> getFormDataAsTree(String s, String s1) {
		TreeNode treenode = new TreeNode();
		List<TreeNode> list1 = treenode.getChildren();
		List<QueryItem> queryItems = query(s1, null, "", null, "", "", "", 0, 0);
		for (QueryItem queryItem : queryItems) {
			TreeNode treenode1 = new TreeNode();
			if (queryItem.get("driverName") != null) {
				treenode1.setName(String.valueOf(queryItem.get("driverName")));
				treenode1.setChecked(false);
				treenode1.setData(String.valueOf(queryItem.get("driverName")));
			} else if (queryItem.get("resourceName") != null) {
				treenode1.setName(String.valueOf(queryItem.get("resourceName")));
				treenode1.setChecked(false);
				treenode1.setData(String.valueOf(queryItem.get("resourceName")));
			} else if (queryItem.get("vehicleName") != null) {
				treenode1.setName(String.valueOf(queryItem.get("vehicleName")));
				treenode1.setChecked(false);
				treenode1.setData(String.valueOf(queryItem.get("vehicleName")));
			}
			list1.add(treenode1);
		}
		return list1;
	}

	@Override
	public Map<String, String> getShiliZiduanLabel(String s, String s1) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		if (s != null) {
			String[] s2 = s1.split("|");
			if (s1.equals("人事部门|U0000000039")) {
				hashmap.put("label", "人事部门");
			} else if (s1.equals("政策法规处|U0000000041")) {
				hashmap.put("label", "政策法规处");
			} else if (s1.equals("业务处室A|U0000000043")) {
				hashmap.put("label", "业务处室A");
			} else if (s1.equals("业务处室B|U0000000045")) {
				hashmap.put("label", "业务处室B");
			} else if (s1.equals("业务处室C|U0000000047")) {
				hashmap.put("label", "业务处室C");
			}
		} else {
			hashmap.put("label", s1);
		}
		return hashmap;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getViewDataAsTree(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getViewDataAsTree(String s, String s1) {
		TreeNode treenode = new TreeNode();
		List<TreeNode> list1 = treenode.getChildren();
		String uuid = s1;
		List<QueryItem> queryItems = dyViewApiFacade.getViewDataByKey(uuid, null);
		for (QueryItem queryItem : queryItems) {
			TreeNode treenode1 = new TreeNode();
			treenode1.setName(String.valueOf(queryItem.get("title")));
			treenode1.setData(queryItem);
			list1.add(treenode1);
		}
		return list1;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getViewDataAsTree2(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TreeNode> getViewDataAsTree2(String s, String s1) {
		TreeNode treenode = new TreeNode();
		List<TreeNode> list1 = treenode.getChildren();
		String uuid = s1;
		List<QueryItem> queryItems = dyViewApiFacade.getViewDataByKey(uuid, null);
		for (QueryItem queryItem : queryItems) {
			TreeNode treenode1 = new TreeNode();
			treenode1.setData(queryItem);
			list1.add(treenode1);
		}
		return list1;
	}

	/** 
	 * 根据表单的Id获得表单的Uuid
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getFormUuidById(java.lang.String)
	 */
	@Override
	public String getFormUuidById(String Id) {
		FormDefinition formDefinition = formDefinitionDao.findUniqueBy("id", Id);
		return formDefinition.getUuid();
	}

	/** 
	 * 解析表单定义配置为xml的方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getXml(java.lang.String, java.lang.String)
	 */
	@Override
	public String getXml(String xmlStr, String xslStr) {
		StringInputStream xmlStream = new StringInputStream(xmlStr);
		StringInputStream xslStream = new StringInputStream(xslStr);
		Source source = new StreamSource(xmlStream);
		Source xsl = new StreamSource(xslStream);
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = factory.newTransformer(xsl);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		System.out.println(writer.toString());
		return writer.toString();
	}

	/** 
	 * 根据传入的表单id 跟参数获取数据列表
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getSubFormData(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map> getSubFormData(String formUuid, String temp) {
		FormDefinition formDefinition = formDefinitionService.getFormByUUID(formUuid);
		//获取表名
		String tableName = formDefinition.getName();
		List<Map> list = new ArrayList<Map>();
		StringBuffer sb = new StringBuffer();
		sb.append("PARENT_ID = " + "'" + temp + "'");
		list = formDataDao.getFormDataBySql(tableName, sb.toString());
		return list;
	}

	public class ConflictStatus {
		public boolean isNew = true;
		public boolean isConflict = false;//默认不冲突
		public Map<String, Object> columnsData = new HashMap<String, Object>();//如果冲突了，则该字段用于保存在数据库中的数据
	}

}
