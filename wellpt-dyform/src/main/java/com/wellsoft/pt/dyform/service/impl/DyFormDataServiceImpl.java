/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wellsoft.pt.dyform.dao.DyFormDataDao;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.support.DyFormDataUtils;
import com.wellsoft.pt.dyform.support.DyFormDefinitionUtils;
import com.wellsoft.pt.dyform.support.enums.EnumFormDataStatus;
import com.wellsoft.pt.repository.dao.DbTableDao;
import com.wellsoft.pt.repository.service.MongoFileService;

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
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas)
			throws JsonParseException, JsonMappingException, JSONException, IOException, ParseException {
		Assert.notNull(formDatas, "表单数据为null");
		Assert.notEmpty(formDatas, "表单数据为空");
		Transaction transaction = this.dyFormDataDao.getSession().getTransaction();
		try {
			Iterator<String> formUuidIt = formDatas.keySet().iterator();

			DyFormDefinition mainformDefinition = this.dyFormApiFacade.getFormDefinitionByFormUuid(mainformUuid);

			//获取主表的数据id
			Map<String, Object> formDataOfMainform = formDatas.get(mainformUuid).get(0);
			Object dataUuidOfMainform = formDataOfMainform.get("uuid");
			if (dataUuidOfMainform == null || ((String) dataUuidOfMainform).trim().length() == 0) {
				dataUuidOfMainform = DyFormApiFacade.createUuid();
			}

			//保存数据
			while (formUuidIt.hasNext()) {
				String formUuid = formUuidIt.next();
				if (mainformUuid.equals(formUuid)) {//主表数据
					this.doProcessFormData(formUuid, formDataOfMainform);
				} else {//从表数据
					List<Map<String, Object>> formDatasInOneForm = formDatas.get(formUuid);
					for (Map<String, Object> map : formDatasInOneForm) {
						//主表的数据uuid,这里的key本想用formUuid,但是oracle的字段名称最多只能32位
						map.put(mainformDefinition.getName().toLowerCase(), dataUuidOfMainform);
						this.doProcessFormData(formUuid, map);
					}
				}
			}
			//transaction.commit();
			return (String) dataUuidOfMainform;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			transaction.rollback();
		}

		return null;
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
	public void doProcessFormData(String formUuid, Map<String, Object> formDataColMap) throws JSONException,
			JsonParseException, JsonMappingException, IOException, ParseException {
		boolean isNew = true;//如果表单数据已存在于表单中则为false, 如果不存在于表单中则为true
		String dataUuid = (String) formDataColMap.get("uuid");
		if (dataUuid == null || dataUuid.trim().length() == 0) {
			dataUuid = DyFormApiFacade.createUuid();
			formDataColMap.put("uuid", dataUuid);
		}

		//根据定义来设置特殊字段
		DyFormDefinition formDefinition = this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuid);

		DyFormDefinitionUtils dUtils = new DyFormDefinitionUtils(formDefinition);
		DyFormDataUtils dataUtils = new DyFormDataUtils(dUtils, mongoFileService, formDataColMap);

		List<String> fieldNames = dUtils.getFieldNamesOfMainform();//表单的各字段名称

		Map<String, Object> dbFormDataColMap = this.getFormDataOfMainform(dUtils.getTblNameOfMainform(), dataUuid,
				fieldNames);

		if (dbFormDataColMap == null || dbFormDataColMap.size() == 0) {
			isNew = true;
		} else {
			isNew = false;
		}

		if (isNew) {//新数据
			Iterator<String> it = fieldNames.iterator();
			while (it.hasNext()) {
				String fieldName = it.next();
				if (dUtils.isValueCreateBySystemWhenSave(fieldName)) {//在保存时由系统产生 
					dataUtils.doProcessValueCreateBySystem(fieldName);
				} else/* if (dUtils.isValueCreateByUser(fieldName))*/{//从前台插入
					dataUtils.doProcessValueCreateByUser(fieldName);
				}
				dataUtils.validate(fieldName);//验证数据 
			}
			formDataColMap.put("status", EnumFormDataStatus.DYFORM_DATA_STATUS_DEFAULT.getValue());
			formDataColMap.put("form_uuid", formUuid);

			this.dyFormDataDao.save(formDefinition, formDataColMap);

		} else {
			Iterator<String> it = formDataColMap.keySet().iterator();
			while (it.hasNext()) {

				String fieldName = it.next();

				if (dUtils.isValueCreateBySystemWhenSave(fieldName)) {//在保存时由系统产生 
					dataUtils.doProcessValueCreateBySystem(fieldName);
				} else /*if (dUtils.isValueCreateByUser(fieldName)) */{//从前台插入
					dataUtils.doProcessValueCreateByUser(fieldName);
				}
				dataUtils.validate(fieldName);//验证数据

			}
			this.dyFormDataDao.update(formDefinition, formDataColMap);
		}
	}

	@Override
	public Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> getFormData(String formUuid,
			String dataUuid) {
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		Map<String, Object> formDataOfMainform = this.getFormDataOfMainform(formUuid, dataUuid);
		if (formDataOfMainform == null) {
			return null;
		}
		List<Map<String /*表单字段值*/, Object/*表单字段值*/>> list = new ArrayList<Map<String, Object>>();
		list.add(formDataOfMainform);
		formDatas.put(formUuid, list);
		DyFormDefinitionUtils dUtils;
		try {
			dUtils = new DyFormDefinitionUtils(this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuid));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		for (String formUuidOfSubform : dUtils.getFormUuidsOfSubform()) {
			List<Map<String, Object>> formDataOfSubform = this.getFormDataOfSubform(formUuid, formUuidOfSubform,
					dataUuid);
			if (formDataOfSubform == null) {
				continue;
			}
			formDatas.put(formUuidOfSubform, formDataOfSubform);
		}
		return formDatas;
	}

	private Map<String, Object> getFormDataOfMainform(String tblName, String dataUuid, List<String> fieldNames) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select uuid,creator,create_time,modify_time,sort_order, form_uuid");
		for (Object fieldNameObj : fieldNames) {
			String fieldName = (String) fieldNameObj;
			sqlBuffer.append(", ").append(fieldName);
		}
		sqlBuffer.append(" from ");
		sqlBuffer.append(tblName);
		sqlBuffer.append(" where ");
		sqlBuffer.append(" uuid = '");
		sqlBuffer.append(dataUuid);
		sqlBuffer.append("'");
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
		DyFormDefinitionUtils dUtils = null;
		try {
			dUtils = new DyFormDefinitionUtils(this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuid));
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		Map<String, Object> formDataOfMainform = this.getFormDataOfMainform(dUtils.getTblNameOfMainform(), dataUuid,
				dUtils.getFieldNamesOfMainform());
		if (formDataOfMainform == null) {
			return null;
		}
		//转换时间格式

		formatValueOfFormData(formDataOfMainform, dUtils);

		return formDataOfMainform;
	}

	/**
	 * 格式化数据格式
	 * @param formDataOfMainform
	 * @param dUtils
	 */
	@SuppressWarnings("static-method")
	private void formatValueOfFormData(Map<String, Object> formDataOfMainform, DyFormDefinitionUtils dUtils) {
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
			}

			formDataOfMainform.put(fieldName, value);
		}

	}

	private List<Map<String, Object>> getFormDataOfSubform(String tblNameOfSubform, List<String> fieldNamesOfSubform,
			String tblNameOfMainform, String dataUuidOfMainform) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select uuid,creator,create_time,modify_time,sort_order, form_uuid");
		for (Object fieldNameObj : fieldNamesOfSubform) {
			String fieldName = (String) fieldNameObj;
			sqlBuffer.append(", ").append(fieldName);
		}
		sqlBuffer.append(" from ");
		sqlBuffer.append(tblNameOfSubform);
		sqlBuffer.append(" where ");

		sqlBuffer.append(tblNameOfMainform);
		sqlBuffer.append(" = '");
		sqlBuffer.append(dataUuidOfMainform);
		sqlBuffer.append("'");
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
	public List<Map<String, Object>> getFormDataOfSubform(String formUuidOfMainform, String formUuidOfSubform,
			String dataUuidOfMainform) {
		DyFormDefinitionUtils dUtils = null;
		try {
			dUtils = new DyFormDefinitionUtils(this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuidOfMainform));

			List<Map<String, Object>> list = this
					.getFormDataOfSubform(dUtils.getTblNameOfSubform(formUuidOfSubform),
							dUtils.getFieldNamesOfSubform(formUuidOfSubform), dUtils.getTblNameOfMainform(),
							dataUuidOfMainform);
			if (list == null || list.size() == 0) {
				return null;
			}
			DyFormDefinitionUtils dUtils2 = new DyFormDefinitionUtils(
					this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuidOfSubform));
			for (Map<String, Object> record : list) {
				formatValueOfFormData(record, dUtils2);
			}
			return list;
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public Map<String, Object> getDefaultFormData(String formUuid) throws JSONException {
		DyFormDefinitionUtils dUtils = new DyFormDefinitionUtils(
				this.dyFormApiFacade.getFormDefinitionByFormUuid(formUuid));
		Map<String, Object> formData = new HashMap<String, Object>();
		DyFormDataUtils dataUtils = new DyFormDataUtils(dUtils, mongoFileService, formData);
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
}
