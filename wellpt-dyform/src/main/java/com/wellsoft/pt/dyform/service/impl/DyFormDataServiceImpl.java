/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.Provider;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wellsoft.pt.basicdata.ca.service.FJCAAppsService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.support.QueryItemResultTransformer;
import com.wellsoft.pt.dyform.dao.DyFormDataDao;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.support.DataSignature;
import com.wellsoft.pt.dyform.support.DyFormConfig;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFormPropertyName;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.DyFormDataUtils;
import com.wellsoft.pt.dyform.support.DyFormDefinitionJSON;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.dyform.support.SubformDefinition;
import com.wellsoft.pt.dyform.support.SubformFieldDefinition;
import com.wellsoft.pt.dyform.support.ValidateMsg;
import com.wellsoft.pt.dyform.support.enums.EnumFormDataStatus;
import com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField;
import com.wellsoft.pt.dyform.support.enums.EnumSystemField;
import com.wellsoft.pt.dyform.support.enums.EnumValidateCode;
import com.wellsoft.pt.dyform.support.exception.hibernate.FormDataValidateException;
import com.wellsoft.pt.dytable.support.DytableConfig;
import com.wellsoft.pt.dytable.support.FormDataResultTransformer;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.integration.support.StreamingData;
import com.wellsoft.pt.repository.dao.DbTableDao;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.unit.dao.CommonUnitDao;
import com.wellsoft.pt.unit.entity.CommonUnit;
import com.wellsoft.pt.utils.ca.FJCAUtils;

/**
 * @ClassName: FormDefinitionService
 * @Description: 动态表单数据业务处理
 * @author lilin
 */
@Service
@Transactional
public class DyFormDataServiceImpl implements DyFormDataService {
	private Logger logger = LoggerFactory.getLogger(DyFormDataServiceImpl.class);

	@Autowired
	DyFormDataDao dyFormDataDao;
	@Autowired
	DyFormApiFacade dyFormApiFacade;

	@Autowired
	DbTableDao dbTableDao;

	@Autowired
	MongoFileService mongoFileService;

	@Autowired
	FJCAAppsService fjcaAppsService;

	@Autowired
	private CommonUnitDao commonUnitDao;

	@Override
	public boolean queryFormDataExists(String tblName, String fieldName, String fieldValue) throws Exception {
		List<Map<String, Object>> list = dyFormDataDao.queryFormDataList(tblName, fieldName, fieldValue, 1, 1);
		if (list == null || list.size() == 0) {//数据在表单中不存在
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean queryFormDataExists(String uuid, String tblName, String fieldName, String fieldValue)
			throws Exception {
		List<Map<String, Object>> list = dyFormDataDao.queryFormDataList(uuid, tblName, fieldName, fieldValue, 1, 1);
		if (list == null || list.size() == 0) {//数据在表单中不存在
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String saveFormData(String mainformUuid,
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas,
			Map<String, List<String>> deletedFormDatas, DataSignature signature) {
		long time1 = System.currentTimeMillis();
		Assert.notNull(formDatas, "表单数据为null");
		Assert.notEmpty(formDatas, "表单数据为空");
		Transaction transaction = this.dyFormDataDao.getSession().getTransaction();
		try {
			Iterator<String> formUuidIt = formDatas.keySet().iterator();

			DyFormDefinition mainformDefinition = this.getDefinition(mainformUuid);

			//获取主表的数据id
			Map<String, Object> formDataOfMainform = formDatas.get(mainformUuid).get(0);
			Object dataUuidOfMainform = formDataOfMainform.get("uuid");
			if (dataUuidOfMainform == null || ((String) dataUuidOfMainform).trim().length() == 0) {
				dataUuidOfMainform = DyFormApiFacade.createUuid();
				formDataOfMainform.put("uuid", dataUuidOfMainform);
			}

			//保存数据
			while (formUuidIt.hasNext()) {
				String formUuid = formUuidIt.next();
				if (mainformUuid.equals(formUuid)) {//主表数据
					this.saveFormData(formUuid, formDataOfMainform);
				} else {//从表数据
					List<Map<String, Object>> formDatasInOneForm = formDatas.get(formUuid);
					for (Map<String, Object> map : formDatasInOneForm) {
						if (map == null) {
							continue;
						}
						map.put(EnumRelationTblSystemField.mainform_data_uuid.name(), dataUuidOfMainform);
						map.put(EnumRelationTblSystemField.mainform_form_uuid.name(), mainformDefinition.getUuid());
						map.put(EnumRelationTblSystemField.sort_order.name(), map.get("seqNO"));//设置前台的排序
						this.saveFormData(formUuid, map);
					}
				}
			}

			//保存签名
			this.saveSignature((String) dataUuidOfMainform, signature);

			//被解除关系的从表
			if (deletedFormDatas == null || deletedFormDatas.size() == 0) {
				return (String) dataUuidOfMainform;
			}
			formUuidIt = deletedFormDatas.keySet().iterator();
			while (formUuidIt.hasNext()) {
				String formUuid = formUuidIt.next();

				//解除关系
				this.doProcessDeletedFormData(formUuid, deletedFormDatas.get(formUuid), (String) dataUuidOfMainform);
			}

			return (String) dataUuidOfMainform;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			transaction.rollback();
		}

		return null;
	}

	public void saveSignature(String dataUuid, DataSignature signature) {
		if (signature == null) {
			return;
		}

		if (DataSignature.STATUS_FAILURE == signature.getStatus()) {
			throw new RuntimeException(signature.getRemark());
		}

		if (DataSignature.STATUS_SUCCESS == signature.getStatus()) {
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
			String signedFormData = signature.getSignedData();
			if (signedFormData == null) {
				signedFormData = "";
			}
			//将由前台页面js生成的表单json保存成一个文件,且同时保存该文件对应的签名
			MongoFileEntity file = mongoFileService.saveFile("formData.json", new StringInputStream(signedFormData,
					DytableConfig.CHARSET), signature.getDigestValue(), signature.getDigestAlgorithm(), signature
					.getSignatureValue(), signature.getCertificate());
			if (file == null) {//文件保存失败
				throw new RuntimeException("签名信息保存失败");
			} else {//文件保存成功
				mongoFileService.pushFileToFolder(dataUuid, file.getFileID(), EnumSystemField.signature_.name());
			}

		}
	}

	private void doProcessDeletedFormData(String formUuid, List<String> dataUuids, String dataUuidOfMainform) {
		DyFormDefinition formDefinition = this.getDefinition(formUuid);
		this.dyFormDataDao.deleteSubformFormMainform(formDefinition.getRelationTbl(), dataUuids, dataUuidOfMainform);
	}

	/** 
	 * 根据定义保存数据
	 * @param formUuid  数据保存的目的表的定义uuid 
	 * @param mainformUuid 主表的定义uuid,如果该值为null则表示要处理的数据是主表的数据，如果非null表示要处理的数据是从表的数据
	 * @param formDataOfMainform
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public String saveFormData(String formUuid, Map<String, Object> formDataColMap) throws JSONException,
			ParseException {
		boolean isNew = true;//如果表单数据已存在于表单中则为false, 如果不存在于表单中则为true
		String dataUuid = (String) formDataColMap.get(EnumSystemField.uuid.name());
		if (dataUuid == null || dataUuid.trim().length() == 0) {
			dataUuid = DyFormApiFacade.createUuid();
			formDataColMap.put(EnumSystemField.uuid.name(), dataUuid);
		}

		//根据定义来设置特殊字段
		DyFormDefinition formDefinition = this.getDefinition(formUuid);

		DyFormDefinitionJSON dUtils = formDefinition.getJsonHandler();
		DyFormDataUtils dataUtils = new DyFormDataUtils(formUuid, formDataColMap, dUtils);

		List<String> fieldNames = dUtils.getFieldNamesOfMainform();//表单的各字段名称

		Map<String, Object> dbFormDataColMap = this.getFormDataOfMainform(dUtils.getTblNameOfMainform(), dataUuid,
				fieldNames);

		if (dbFormDataColMap == null || dbFormDataColMap.size() == 0) {
			isNew = true;
		} else {
			isNew = false;
		}

		//验证数据格式的正确性
		ValidateMsg vMsg = dataUtils.validate();
		if (vMsg.code != EnumValidateCode.SUCESS) {
			throw new FormDataValidateException(vMsg.msg);
		}

		if (isNew) {//新数据
			Iterator<String> it = fieldNames.iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				logger.debug("processed fieldName:" + fieldName);
				if (!dUtils.isTblField(fieldName)) {//非表单的字段
					it.remove();
				}
				if (dUtils.isValueCreateBySystemWhenSave(fieldName)) {//在保存时由系统产生 
					dataUtils.doProcessValueCreateBySystem(fieldName);
				} else/* if (dUtils.isValueCreateByUser(fieldName))*/{//从前台插入 
					dataUtils.doProcessValueCreateByUser(fieldName);
				}
				//dataUtils.validate(fieldName);//验证数据 
			}

			formDataColMap.put(EnumSystemField.status.name(), EnumFormDataStatus.DYFORM_DATA_STATUS_DEFAULT.getValue());
			formDataColMap.put(EnumSystemField.form_uuid.name(), formUuid);

			this.dyFormDataDao.save(formDefinition, formDataColMap);

		} else {
			Iterator<String> it = formDataColMap.keySet().iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				logger.debug("processed fieldName:" + fieldName);
				if (!dUtils.isTblField(fieldName)) {//非表单的字段
					it.remove();
				}
				if (!dUtils.isFieldInDefinition(fieldName)) {
					continue;
				}
				if (dUtils.isValueCreateBySystemWhenSave(fieldName)) {//在保存时由系统产生 
					dataUtils.doProcessValueCreateBySystem(fieldName);
				} else /*if (dUtils.isValueCreateByUser(fieldName)) */{//从前台插入

					dataUtils.doProcessValueCreateByUser(fieldName);
				}
			}

			this.dyFormDataDao.update(formDefinition, formDataColMap);
		}
		return dataUuid;
	}

	public Map<String, Object> getFormDataOfMainform(String tblName, String dataUuid, List<String> fieldNames) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select uuid,creator,create_time,modify_time, form_uuid");
		for (Object fieldNameObj : fieldNames) {
			String fieldName = (String) fieldNameObj;
			sqlBuffer.append(", ").append(fieldName);
		}
		sqlBuffer.append(" from ");
		sqlBuffer.append(tblName);
		if (dataUuid != null) {
			sqlBuffer.append(" where ");
			sqlBuffer.append(" uuid = '");
			sqlBuffer.append(dataUuid);
			sqlBuffer.append("'");
		}

		try {
			List<Map<String, Object>> list = dbTableDao.query(sqlBuffer.toString());
			if (list == null || list.size() == 0) {
				return null;
			}
			for (Map<String, Object> record : list) {
				for (String fieldName : fieldNames) {
					Object value = record.get(fieldName.toLowerCase());
					record.remove(fieldName.toLowerCase());
					record.put(fieldName, value);
				}
			}
			return list.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Map<String, Object> getFormDataOfMainform(String formUuid, String dataUuid) {
		DyFormDefinitionJSON dUtils = null;

		dUtils = this.getDefinition(formUuid).getJsonHandler();

		Map<String, Object> formDataOfMainform = this.getFormDataOfMainform(dUtils.getTblNameOfMainform(), dataUuid,
				dUtils.getFieldNamesOfMainform());
		if (formDataOfMainform == null) {
			return null;
		}

		List<LogicFileInfo> allfiles = this.mongoFileService.getNonioFilesFromFolder(dataUuid, null);
		if (allfiles != null && allfiles.size() > 0) {
			for (String fieldName : dUtils.getFieldNamesOfMainform()) {
				if (dUtils.isInputModeEqAttach(fieldName)) {//附件

					List<LogicFileInfo> files = this.getFiles(allfiles, fieldName);
					formDataOfMainform.put(fieldName, files);
				}
			}
		}

		//转换时间格式 
		formatValueOfFormData(formDataOfMainform, dUtils);

		return formDataOfMainform;
	}

	private List<LogicFileInfo> getFiles(List<LogicFileInfo> allfiles, String fieldName) {
		List<LogicFileInfo> lfiles = new ArrayList<LogicFileInfo>();
		if (allfiles == null) {
			return new ArrayList<LogicFileInfo>();
		}
		for (LogicFileInfo file : allfiles) {
			String purpose = file.getPurpose();
			if (purpose == null) {
				continue;
			}
			if (purpose.toLowerCase().equals(fieldName.toLowerCase())) {
				lfiles.add(file);
			}
		}
		return lfiles;
	}

	/**
	 * 格式化数据格式
	 * @param formDataOfMainform
	 * @param dUtils
	 */
	@SuppressWarnings("static-method")
	private void formatValueOfFormData(Map<String, Object> formDataOfMainform, DyFormDefinitionJSON dUtils) {
		//DyFormDataUtils dataUtils = new DyFormDataUtils(dUtils);
		Iterator<String> it = dUtils.getFieldNamesOfMainform().iterator();
		SimpleDateFormat sdf = new SimpleDateFormat();
		while (it.hasNext()) {
			String fieldName = it.next();
			Object value = formDataOfMainform.get(fieldName);
			if (value == null) {
				continue;
			}

			if (dUtils.isInputModeEqDate(fieldName)) {
				String datePattern = dUtils.getDateTimePatternByFieldName(fieldName);
				sdf.applyPattern(datePattern);
				value = sdf.format((Date) value);
			} else if (dUtils.isDbDataTypeEqClob(fieldName)) {
				if (value != null) {
					value = ClobToString((Clob) value);
				}
			}

			formDataOfMainform.put(fieldName, value);
		}

	}

	public String ClobToString(Clob clob) {

		String reString = "";

		Reader is = null;
		BufferedReader br = null;
		try {
			is = clob.getCharacterStream();
			br = new BufferedReader(is);

			char[] buffer = new char[1024];
			int size = -1;
			StringBuffer sb = new StringBuffer("");

			while ((size = br.read(buffer)) != -1) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(new String(buffer, 0, size));
			}
			reString = sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return reString;

	}

	@Override
	public Map<String, Object> getDefaultFormData(String formUuid) throws JSONException {
		DyFormDefinitionJSON dUtils = this.getDefinition(formUuid).getJsonHandler();
		Map<String, Object> formData = new HashMap<String, Object>();
		DyFormDataUtils dataUtils = new DyFormDataUtils(formUuid, formData);
		List<String> fieldNames = dUtils.getFieldNamesOfMainform();//表单的各字段名称
		for (String fieldName : fieldNames) {
			if (dUtils.isValueCreateBySystemWhenShowNewForm(fieldName)) {
				dataUtils.doProcessValueCreateBySystem(fieldName);
			}/* else if (dUtils.isInputModeEqNumber(fieldName)) {
				formData.put(key, value);
				}*/
		}

		formatValueOfFormData(formData, dUtils);

		return formData;
	}

	private DyFormDefinition getDefinition(String formUuid) {
		return this.dyFormApiFacade.getFormDefinition(formUuid);
	}

	@Override
	public QueryData getFormDataOfParentNodeByPage(String formUuidOfSubform, String formUuidOfMainform,
			String dataUuidOfMainform, PagingInfo pagingInfo) {

		DyFormDefinition formDefinitionOfMainform = this.getDefinition(formUuidOfMainform);

		DyFormDefinitionJSON dUtils = null;
		List<String> fieldNames = new ArrayList<String>();

		String relationTblNameOfSubform = null;

		dUtils = formDefinitionOfMainform.getJsonHandler();
		List<String> fieldNamesOfSubform = null;
		try {
			fieldNamesOfSubform = dUtils.getFieldNamesOfSubform(formUuidOfSubform);
			fieldNames.addAll(fieldNamesOfSubform);
			List<String> sysFieldNames = DyFormDefinitionJSON.getSysFieldNames();
			fieldNames.addAll(sysFieldNames);
		} catch (JSONException e2) {
			e2.printStackTrace();
			return null;
		}

		DyFormDefinition subformdyd = this.getDefinition(formUuidOfSubform);
		relationTblNameOfSubform = subformdyd.getRelationTbl();

		//记录总条数
		StringBuffer totalSqlBuffer = new StringBuffer();
		totalSqlBuffer.append("select count(uuid) c from ");
		totalSqlBuffer.append(relationTblNameOfSubform);
		totalSqlBuffer.append(" where parent_uuid is null and ");
		totalSqlBuffer.append(" MAINFORM_DATA_UUID = '");
		totalSqlBuffer.append(dataUuidOfMainform);
		totalSqlBuffer.append("'");
		long totalCount = 0;
		try {
			String totalCountstr = dbTableDao.query(totalSqlBuffer.toString()).get(0).get("c").toString();
			totalCount = Long.parseLong(totalCountstr);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		if (pagingInfo == null) {//没有传入翻页信息
			int DEFAULTPAGESIZE = 1000;
			pagingInfo = new PagingInfo();
			if (totalCount > DEFAULTPAGESIZE) {
				pagingInfo.setPageSize(DEFAULTPAGESIZE);
			} else {
				pagingInfo.setCurrentPage(1);
				pagingInfo.setPageSize((int) totalCount);
			}
		}
		pagingInfo.setTotalCount(totalCount);
		pagingInfo.setAutoCount(true);

		//分页查询
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(this.dyFormApiFacade.getSqlOfSubformView(formUuidOfSubform));
		String VIEWNAME_OF_SUBFORM = DyFormConfig.VIEWNAME_OF_SUBFORM;
		sqlBuffer.append(" select 1 ");
		for (Object fieldNameObj : fieldNames) {
			String fieldName = (String) fieldNameObj;
			sqlBuffer.append(", ").append(fieldName);
		}
		sqlBuffer.append(" from ");
		sqlBuffer.append(VIEWNAME_OF_SUBFORM);
		sqlBuffer.append(" where rowid in(select rid from (select rownum rn,rid from ");
		sqlBuffer.append(" (select rowid rid    ");
		sqlBuffer.append("  from    ");

		sqlBuffer.append(VIEWNAME_OF_SUBFORM);

		sqlBuffer.append(" where parent_uuid is null and ");

		sqlBuffer.append("MAINFORM_DATA_UUID = '");
		sqlBuffer.append(dataUuidOfMainform);
		sqlBuffer.append("'");

		sqlBuffer.append(" order by to_number(sort_order)  ");
		sqlBuffer.append(" asc ");
		sqlBuffer.append(") where rownum<=  ");
		sqlBuffer.append(pagingInfo.getFirst() + pagingInfo.getPageSize() + 1);
		sqlBuffer.append(") where rn >  ");

		sqlBuffer.append(pagingInfo.getFirst());
		sqlBuffer.append(") order by to_number(sort_order) ");
		sqlBuffer.append(" asc ");

		try {
			List<Map<String, Object>> list = dbTableDao.query(sqlBuffer.toString());
			for (Map<String, Object> record : list) {
				for (String fieldName : fieldNames) {
					Object value = record.get(fieldName.toLowerCase());
					record.remove(fieldName.toLowerCase());
					record.put(fieldName, value);
				}
			}

			//格式化
			if (list == null || list.size() == 0) {
				return null;
			}

			for (Map<String, Object> record : list) {
				formatValueOfFormData(record, subformdyd.getJsonHandler());

				List<LogicFileInfo> allfiles = this.mongoFileService.getNonioFilesFromFolder(
						(String) record.get(EnumSystemField.uuid.name()), null);
				if (allfiles != null && allfiles.size() > 0) {
					for (String fieldName : subformdyd.getJsonHandler().getFieldNamesOfMainform()) {
						if (subformdyd.getJsonHandler().isInputModeEqAttach(fieldName)) {//附件

							List<LogicFileInfo> files = this.getFiles(allfiles, fieldName);
							record.put(fieldName, files);
						}
					}
				}

				/*	
					for (String fieldName : fieldNamesOfSubform) {
						if (subformdyd.getJsonHandler().isInputModeEqAttach(fieldName)) {//附件
							List<LogicFileInfo> files = this.mongoFileService.getNonioFilesFromFolder(
									(String) record.get(EnumSystemField.uuid.name()), fieldName);
							record.put(fieldName, files);
						}
					}*/

			}
			QueryData queryData = new QueryData();
			queryData.setDataList(list);
			queryData.setPagingInfo(pagingInfo);
			return queryData;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public List<Map<String, Object>> getFormDataOfChildNode4ParentNode(String formUuidOfSubform,
			String formUuidOfMainform, String dataUuidOfMainform, String dataUuidOfParentNode) {
		try {
			DyFormDefinitionJSON dUtils = this.getDefinition(formUuidOfMainform).getJsonHandler();
			List<String> fieldNames = new ArrayList<String>();
			List<String> fieldNamesOfSubform = dUtils.getFieldNamesOfSubform(formUuidOfSubform);
			fieldNames.addAll(fieldNamesOfSubform);
			List<String> sysFieldNames = DyFormDefinitionJSON.getSysFieldNames();
			fieldNames.addAll(sysFieldNames);
			/*for (String fieldName : fieldNamesOfSubform) {
				sysFieldNames.add(fieldName);
			}*/

			fieldNames.add(EnumRelationTblSystemField.mainform_data_uuid.name());
			fieldNames.add(EnumRelationTblSystemField.parent_uuid.name());

			/*Iterator<String> it = fieldNames.iterator();
			while (it.hasNext()) {//删除掉uuid字段，在从表里面有这个字段，在关系表中也有这个字段
				String fieldName = it.next();
				if (fieldName.equalsIgnoreCase("uuid")) {
					it.remove();
				}
			}*/

			List<Map<String, Object>> list = getFormDataOfChildNode4ParentNode(formUuidOfSubform, fieldNames,
					dataUuidOfMainform, dataUuidOfParentNode);
			if (list == null || list.size() == 0) {
				return null;
			}
			DyFormDefinitionJSON dUtils2 = this.getDefinition(formUuidOfSubform).getJsonHandler();
			for (Map<String, Object> record : list) {
				formatValueOfFormData(record, dUtils2);
				for (String fieldName : fieldNamesOfSubform) {
					if (dUtils2.isInputModeEqAttach(fieldName)) {//附件
						List<LogicFileInfo> files = this.mongoFileService.getNonioFilesFromFolder(
								(String) record.get(EnumSystemField.uuid.name()), fieldName);
						record.put(fieldName, files);
					}
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	private List<Map<String, Object>> getFormDataOfChildNode4ParentNode(String formUuidOfSubform,
			List<String> fieldNamesOfSubform, String dataUuidOfMainform, String dataUuidOfParentNode) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(this.dyFormApiFacade.getSqlOfSubformView(formUuidOfSubform));

		sqlBuffer.append(" select 1 ");
		for (String fieldNameObj : fieldNamesOfSubform) {
			String fieldName = fieldNameObj;
			sqlBuffer.append(", ").append(fieldName);
		}
		sqlBuffer.append(" from   ");
		sqlBuffer.append(DyFormConfig.VIEWNAME_OF_SUBFORM);
		sqlBuffer.append(" where parent_uuid = '");
		sqlBuffer.append(dataUuidOfParentNode);
		sqlBuffer.append("'");
		sqlBuffer.append(" and MAINFORM_DATA_UUID = '");
		sqlBuffer.append(dataUuidOfMainform);
		sqlBuffer.append("' order by to_number(sort_order) ");
		sqlBuffer.append(" asc ");
		try {
			List<Map<String, Object>> list = dbTableDao.query(sqlBuffer.toString());
			for (Map<String, Object> record : list) {
				for (String fieldName : fieldNamesOfSubform) {
					Object value = record.get(fieldName.toLowerCase());
					record.remove(fieldName.toLowerCase());
					record.put(fieldName, value);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public QueryData queryFormDataOfMainform(String formUuid, Criterion conditions, PagingInfo pagingInfo) {
		QueryData queryData = new QueryData();
		try {
			DyFormDefinitionJSON dUtils = this.getDefinition(formUuid).getJsonHandler();
			String tblName = dUtils.getTblNameOfMainform();
			long totalCount = this.queryTotalCountOfFormDataOfMainform(tblName, conditions);
			if (pagingInfo == null) {//没有传入翻页信息
				int DEFAULTPAGESIZE = 1000;
				pagingInfo = new PagingInfo();
				if (totalCount > DEFAULTPAGESIZE) {
					pagingInfo.setPageSize(DEFAULTPAGESIZE);
				} else {
					pagingInfo.setCurrentPage(1);
					pagingInfo.setPageSize((int) totalCount);
				}
			}
			pagingInfo.setTotalCount(totalCount);
			pagingInfo.setAutoCount(true);
			queryData.setPagingInfo(pagingInfo);
			List<Map<String, Object>> datas = this.queryFormDataOfMainformByLimit(tblName, conditions,
					pagingInfo.getFirst(), pagingInfo.getPageSize());
			for (Map<String, Object> record : datas) {
				for (String fieldName : dUtils.getFieldNamesOfMainform()) {
					Object value = record.get(fieldName.toLowerCase());
					record.remove(fieldName.toLowerCase());
					record.put(fieldName, value);
				}
			}
			queryData.setDataList(datas);
			return queryData;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Map<String, Object>> queryFormDataOfMainformByLimit(String tblName, Criterion conditions, int first,
			int pageSize) throws Exception {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * ");
		sqlBuffer.append(" from ");
		sqlBuffer.append(tblName);
		sqlBuffer.append(" where rowid in(select rid from (select rownum rn,rid from ");
		sqlBuffer.append(" (select rowid rid ");
		sqlBuffer.append("  from    ");
		sqlBuffer.append(tblName);

		sqlBuffer.append(" where 1=1  and (");

		sqlBuffer.append(conditions.toString());
		sqlBuffer.append("  )    ");
		sqlBuffer.append(" order by  modify_time asc ");
		sqlBuffer.append(") where rownum <=  ");
		sqlBuffer.append(first + pageSize);
		sqlBuffer.append(") where rn >  ");

		sqlBuffer.append(first);
		sqlBuffer.append(") order by modify_time asc ");

		return this.dbTableDao.query(sqlBuffer.toString());
	}

	/**
	 * 根据搜索条件,查询符合条件的表单数据条数
	 * @param tblName
	 * @param conditions
	 * @return
	 */
	@Override
	public long queryTotalCountOfFormDataOfMainform(String tblName, Criterion conditions) {
		StringBuffer totalSqlBuffer = new StringBuffer();
		totalSqlBuffer.append("select count(uuid) c from ");
		totalSqlBuffer.append(tblName);
		totalSqlBuffer.append(" where 1=1 ");
		if (conditions != null) {
			totalSqlBuffer.append(" and (");
			totalSqlBuffer.append(conditions.toString());
			totalSqlBuffer.append(" )");
		}
		long totalCount = 0;
		try {
			String totalCountstr = dbTableDao.query(totalSqlBuffer.toString()).get(0).get("c").toString();
			totalCount = Long.parseLong(totalCountstr);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		return totalCount;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.dytable.service.FormDataService#getDigestValue(java.lang.String)
	 */
	@Override
	public DataSignature getDigestValue(String signedContent) {
		DataSignature signature = new DataSignature();
		String digestValue = null;
		String digestAlgorithm = "MD5";
		try {
			MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
			Provider provider = md.getProvider();
			System.out.println(provider.getInfo());
			md.update(signedContent.getBytes("UTF8"));
			byte[] digestData = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digestData) {
				String s = Integer.toHexString((0x000000ff & b) | 0xffffff00).substring(6);
				sb.append(s);
			}
			digestValue = sb.toString();
			signature.setDigestAlgorithm(digestAlgorithm);
			signature.setDigestValue(digestValue);
			signature.setSignedData(signedContent);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("表单数据无法生成消息摘要!");
		}
		return signature;
	}

	@Override
	public List<Map<String, Object>> getFormDataOfSubform(String formUuidOfSubform, String dataUuidOfMainform,
			String whereSql, Map<String, Object> values) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Session session = this.dbTableDao.getSession();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" ")
				.append(this.dyFormApiFacade.getSqlOfSubformView(formUuidOfSubform))
				.append("select *\n from " + DyFormConfig.VIEWNAME_OF_SUBFORM + "  where "
						+ EnumRelationTblSystemField.mainform_data_uuid.name() + " = '").append(dataUuidOfMainform)
				.append("'").append(" and ").append(whereSql);
		SQLQuery sqlquery = session.createSQLQuery(sqlBuffer.toString());
		sqlquery.setProperties(values);
		list = sqlquery.setResultTransformer(FormDataResultTransformer.INSTANCE).list();
		return list;
	}

	@Override
	public List<QueryItem> query(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		DyFormDefinition formDefinition = this.getDefinition(formUuid);
		String table = formDefinition.getName();
		String queryString = buildQueryString(false, table, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = this.dbTableDao.getSession().createSQLQuery(queryString);
		List<QueryItem> queryItems = sqlQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return queryItems;
	}

	public List<QueryItem> query2(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		DyFormDefinition formDefinition = this.getDefinition(formUuid);
		String table = formDefinition.getName();
		String queryString = buildQueryString(false, table, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = this.dbTableDao.getSession().createSQLQuery(queryString);
		List<QueryItem> queryItems = sqlQuery.setFirstResult(firstResult).setMaxResults(maxResults)
				.setResultTransformer(QueryItemResultTransformer.INSTANCE).list();
		return queryItems;
	}

	@Override
	public List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults) {
		String queryString = buildQueryString(distinct, tableName, projection, selection, groupBy, having, orderBy);
		SQLQuery sqlQuery = this.dbTableDao.getSession().createSQLQuery(queryString);
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

	@Override
	public List<Map<String, Object>> getFormDataOfMainform(String formUuid) {
		DyFormDefinition dy = this.getDefinition(formUuid);
		List<String> fieldNames = dy.getJsonHandler().getFieldNamesOfMainform();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select uuid ");
		for (String fieldName : fieldNames) {
			//if (dy.getJsonHandler().isValueAsMap(fieldName)) {
			sqlBuffer.append(", ").append(fieldName);
			//}

		}
		sqlBuffer.append(" from ");
		sqlBuffer.append(dy.getJsonHandler().getTblNameOfMainform());

		try {
			List<Map<String, Object>> list = dbTableDao.query(sqlBuffer.toString());
			if (list == null || list.size() == 0) {
				return null;
			}
			for (Map<String, Object> record : list) {
				for (String fieldName : fieldNames) {
					Object value = record.get(fieldName.toLowerCase());
					record.remove(fieldName.toLowerCase());
					record.put(fieldName, value);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public Object getEntity(Class clazz, String value) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uuid", value);
		String hql = "select o from " + clazz.getName() + " o where o.id = :uuid";
		if (clazz == FmFile.class) {
			hql = "select o from " + clazz.getName() + " o where o.uuid = :uuid";
		}

		if (clazz == CommonUnit.class) {
			return this.commonUnitDao.findUnique(hql, params);
		} else {
			return this.dyFormDataDao.findUnique(hql, params);
		}
	}

	@Override
	public void update(Object obj) {
		this.dyFormDataDao.save(obj);

	}

	@Override
	public void executeSql(String sql) {
		try {
			Session session = this.dyFormDataDao.getSessionFactory().getCurrentSession();

			SQLQuery sqlquery = session.createSQLQuery(sql.toString());
			sqlquery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Map<Integer, List<Map<String, String>>> validateFormdates(String formUuid, List<Map<String, Object>> dataList) {
		// TODO Auto-generated method stub
		Map<Integer, List<Map<String, String>>> map = new HashMap<Integer, List<Map<String, String>>>();
		return map;
	}

	@Override
	public String formDefinationToXml(String formUuid) {
		// TODO Auto-generated method stub
		DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formUuid);
		List<FieldDefinition> fieldDefintions = dyFormDefinition.getFieldDefintions();
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("item");
		//生成主表结构
		for (FieldDefinition fieldDefinition : fieldDefintions) {
			root.addComment(fieldDefinition.getDisplayName());
			Element fieldElement = root.addElement(fieldDefinition.getName());
			if (DyFormConfig.InputModeUtils.isInputModeEqAttach(fieldDefinition.getInputMode())) {//附件
				fieldElement.addAttribute("isAttachment", "1");
			}
		}
		//生成从表结构
		List<SubformDefinition> subformDefinitions = dyFormDefinition.getSubformDefinitions();
		for (SubformDefinition subformDefinition : subformDefinitions) {
			root.addComment(subformDefinition.getDisplayName());
			Element listElement = root.addElement(subformDefinition.getName());
			listElement.addAttribute("isList", "1");
			Element itemElement = listElement.addElement("item");
			List<SubformFieldDefinition> subformFieldDefinitions = subformDefinition.getSubformFieldDefinitions();
			for (SubformFieldDefinition subformFieldDefinition : subformFieldDefinitions) {
				itemElement.addComment(subformFieldDefinition.getDisplayName());
				itemElement.addElement(subformFieldDefinition.getName());
				if (DyFormConfig.InputModeUtils.isInputModeEqAttach(subformFieldDefinition.getInputMode())) {//附件
					itemElement.addAttribute("isAttachment", "1");
				}
			}
		}
		OutputFormat formate = OutputFormat.createPrettyPrint();
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, formate);
		try {
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}

	@Override
	public Map<String, Object> formDataToXmlData(String formUuid, DyFormData formData) {
		// TODO Auto-generated method stub
		/***************生成xml*********************/
		Map<String, Object> map_ = new HashMap<String, Object>();
		DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formUuid);
		List<FieldDefinition> fieldDefinitions = dyFormDefinition.getFieldDefintions();
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("item");
		//生成主表结构
		List<MongoFileEntity> files = new ArrayList<MongoFileEntity>();
		for (FieldDefinition fieldDefinition : fieldDefinitions) {
			Element fieldElement = root.addElement(fieldDefinition.getName());
			if (DyFormConfig.InputModeUtils.isInputModeEqAttach(fieldDefinition.getInputMode())) {//附件
				String fileName = "";
				fieldElement.addAttribute("isAttachment", "1");
				for (String value_ : formData.getValueOfFileIds(fieldDefinition.getName())) {
					MongoFileEntity mongoFileEntity = mongoFileService.getFile(value_);
					files.add(mongoFileEntity);
					fileName += ";" + mongoFileEntity.getFileName();
				}
				fieldElement.addText(fileName.replaceFirst(";", ""));
			} else {
				if (formData.getFieldValue(fieldDefinition.getName()) != null) {
					fieldElement.addText(formData.getFieldValue(fieldDefinition.getName()).toString());
				}
			}
			fieldElement.addComment(fieldDefinition.getDisplayName());
		}
		//生成从表结构,附件未处理
		List<SubformDefinition> subformDefinitions = dyFormDefinition.getSubformDefinitions();
		for (SubformDefinition subformDefinition : subformDefinitions) {
			Element listElement = root.addElement(subformDefinition.getName());
			listElement.addAttribute("isList", "1");
			listElement.addComment(subformDefinition.getDisplayName());
			List<Map<String, Object>> subFormDatas = formData.getFormDatas(subformDefinition.getFormUuid());
			for (Map<String, Object> m : subFormDatas) {
				Element itemElement = listElement.addElement("item");
				List<SubformFieldDefinition> subformFieldDefinitions = subformDefinition.getSubformFieldDefinitions();
				for (SubformFieldDefinition subformFieldDefinition : subformFieldDefinitions) {
					if (DyFormConfig.InputModeUtils.isInputModeEqAttach(subformFieldDefinition.getInputMode())) {//附件
						itemElement.addAttribute("isAttachment", "1");
						String fileName = "";
						for (String value_ : formData.getValueOfFileIds4Subform(subformFieldDefinition.getName(),
								subformDefinition.getFormUuid(), m.get(EnumFormPropertyName.uuid.name()).toString())) {
							MongoFileEntity mongoFileEntity = mongoFileService.getFile(value_);
							files.add(mongoFileEntity);
							fileName += ";" + mongoFileEntity.getFileName();
						}
						itemElement.addElement(subformFieldDefinition.getName())
								.addText(fileName.replaceFirst(";", ""));
					} else {
						if (m.get(subformFieldDefinition.getName()) != null) {
							itemElement.addElement(subformFieldDefinition.getName()).addText(
									m.get(subformFieldDefinition.getName()).toString());
						}
					}
					itemElement.addComment(subformFieldDefinition.getDisplayName());
				}
			}
		}
		map_.put("files", files);
		map_.put("xml", document.asXML());
		return map_;

	}

	@Override
	public DyFormData xmlDataToFormData(String formUuid, String xml, List<StreamingData> streamingDatas) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			DyFormDefinition dyFormDefinition = dyFormApiFacade.getFormDefinition(formUuid);
			Document document = DocumentHelper.parseText(xml);
			Map<String, List<Map<String, Object>>> data = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> mainList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map_ = new HashMap<String, Object>();
			//获得根节点
			Element root = document.getRootElement();
			// 从XML的根结点开始遍历
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element elementOneLevel = (Element) i.next();
				String isAttachment = elementOneLevel.attributeValue("isAttachment");
				String isList = elementOneLevel.attributeValue("isList");
				if (isList != null && isList.equals("1")) {//从表
					String subFormName = elementOneLevel.getName();//当前节点从表名
					List<SubformDefinition> subformDefinitions = dyFormDefinition.getSubformDefinitions();
					for (SubformDefinition subformDefinition : subformDefinitions) {
						if (subformDefinition.getName().equals(subFormName)) {
							String subFormUuid = subformDefinition.getFormUuid();
							List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();//从表数据
							//该种表单的行数据遍历
							List subLineNodes = elementOneLevel.elements("item");
							for (Iterator it = subLineNodes.iterator(); it.hasNext();) {
								Map<String, Object> subMap = new HashMap<String, Object>();
								//一行数据的列遍历
								List subFieldNodes = ((Element) it.next()).elements();
								for (Iterator it2 = subFieldNodes.iterator(); it2.hasNext();) {
									Element elm2 = (Element) it2.next();
									subMap.put(elm2.getName(), elm2.getTextTrim());
								}
								subList.add(subMap);
							}
							data.put(subFormUuid, subList);
						}
					}
				} else {//主表
					String key_ = elementOneLevel.getName();
					String value_ = elementOneLevel.getTextTrim();
					if (dyFormDefinition.getJsonHandler().isValueAsMap(key_) && value_.indexOf("{\"") < 0) {
						String jsonStr = "{\"" + value_ + "\":\"" + value_ + "\"}";
						map_.put(key_, jsonStr);
					} else {
						map_.put(key_, value_);
					}
					//附件
					if (isAttachment != null && (isAttachment.equals("1") || isAttachment.equals("2"))) {
						//上传附件或//正文
						String value_2 = ";" + value_ + ";";
						List<Map<String, Object>> attachmentList = new ArrayList<Map<String, Object>>();
						for (StreamingData streamingData : streamingDatas) {
							if (value_2.indexOf(";" + streamingData.getFileName() + ";") > -1) {
								Map<String, Object> attachment = new HashMap<String, Object>();
								attachment.put("isNew", true);
								attachment.put("fileName", streamingData.getFileName());
								try {
									MongoFileEntity mongoFileEntity = mongoFileService.saveFile(streamingData
											.getFileName(), streamingData.getDataHandler().getInputStream());
									attachment.put("fileID", mongoFileEntity.getFileID());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								attachmentList.add(attachment);
							}
						}
						map_.put(key_, attachmentList);
					}
				}
			}
			mainList.add(map_);
			data.put(dyFormDefinition.getUuid(), mainList);
			DyFormData dyFormData = new DyFormData(dyFormDefinition.getUuid(), data);
			return dyFormData;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public long countByFormUuid(String formUuid) {
		DyFormDefinition dy = this.getDefinition(formUuid);
		SQLQuery sqlquery = this.dyFormDataDao.getSession().createSQLQuery(
				"select count(uuid) c from " + dy.getName() + " where form_uuid ='" + formUuid + "' ");

		return ((BigDecimal) sqlquery.list().get(0)).longValue();
	}

	@Override
	public long countDataInForm(String tblName) {

		SQLQuery sqlquery = this.dyFormDataDao.getSession()
				.createSQLQuery("select count(uuid) c from " + tblName + " ");

		return ((BigDecimal) sqlquery.list().get(0)).longValue();

	}

	/**
	 * 根据搜索条件,查询符合条件的表单数据条数
	 * @param tblName
	 * @param conditions
	 * @return
	 */
	@Override
	public long queryTotalCountOfFormDataOfMainform(String tblName, String conditions) {
		StringBuffer totalSqlBuffer = new StringBuffer();
		totalSqlBuffer.append("select count(uuid) c from ");
		totalSqlBuffer.append(tblName);
		totalSqlBuffer.append(" o where 1=1 ");
		if (conditions != null) {
			totalSqlBuffer.append(" and (");
			totalSqlBuffer.append(conditions);
			totalSqlBuffer.append(" )");
		}
		long totalCount = 0;
		try {
			String totalCountstr = dbTableDao.query(totalSqlBuffer.toString()).get(0).get("c").toString();
			totalCount = Long.parseLong(totalCountstr);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		return totalCount;
	}
}
