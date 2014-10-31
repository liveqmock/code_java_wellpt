/*
 * @(#)2013-1-28 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.core.service.AbstractApiFacade;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.RootFormDataBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.entity.FieldDefinition;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.service.FieldDefinitionService;
import com.wellsoft.pt.dytable.service.FormDataService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.dytable.support.FormDataInfo;
import com.wellsoft.pt.utils.bean.BeanUtils;

/**
 * Description: 如何描述该类
 * 
 * @author zhulh
 * @date 2013-1-28
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-1-28.1	zhulh		2013-1-28		Create
 * </pre>
 * 
 */
@Component
public class DytableApiFacade extends AbstractApiFacade {
	@Autowired
	private FormDefinitionService formDefinitionService;

	@Autowired
	private FieldDefinitionService fieldDefinitionService;

	@Autowired
	private FormDataService formDataService;

	/**
	 * 
	 * 根据表单的ID获取表单的uuid
	 * 
	 * @param Id
	 * @return
	 */
	public String getFormUuidById(String Id) {
		return formDataService.getFormUuidById(Id);
	}

	/**
	 * 
	 * 保存或者更新表单数据
	 * 
	 * @param formUuid
	 * @param data
	 * @return
	 */
	public String saveFormData(String formUuid, Map<String, Object> data) {
		return formDataService.saveFormData(formUuid, data);
	}

	/**
	 * 按实体名获取所有版本的表单定义信息列表
	 * 
	 * @param entityName
	 * @return
	 */
	public List<FormDefinition> getForms(boolean isAll) {
		return formDefinitionService.getForms(isAll);
	}

	/**
	 * 按实体名获取所有版本的表单定义信息列表
	 * 
	 * @param entityName
	 * @return
	 */
	public List<FormDefinition> getForms(String entityName) {
		return formDefinitionService.getForms(entityName);
	}

	/**
	 * 按实体名获取最新版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @return
	 */
	public FormDefinition getForm(String entityName) {
		return formDefinitionService.getForm(entityName);
	}

	/**
	 * 根据实体和版本号获取对应版本的表单定义信息对象
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	public FormDefinition getForm(String entityName, String version) {
		FormDefinition formDefinition = formDefinitionService.getForm(entityName, version);
		if (formDefinition == null) {
			return null;
		}
		FormDefinition formDef = new FormDefinition();
		BeanUtils.copyProperties(formDefinition, formDef);
		return formDef;
	}

	/**
	 * 根据表单UUID查找对应的表单定义信息对象
	 * 
	 * @param uid
	 * @return
	 */
	public FormDefinition getFormByUUID(String uuid) {
		return formDefinitionService.getFormByUUID(uuid);
	}

	/**
	 * 获取表单对应的字段信息列表
	 * 
	 * @param uuid
	 * @return
	 */
	public List<FieldDefinition> getFieldByForm(String uuid) {
		return formDefinitionService.getFieldByForm(uuid);
	}

	/**
	 * 根据指定的实体名和版本号查找对应的字段定义信息列表
	 * 
	 * @param entityName
	 * @param version
	 * @return
	 */
	public List<FieldDefinition> getField(String entityName, String version) {
		return fieldDefinitionService.getField(entityName, version);
	}

	/**
	 * 获取表单数据（真实数据）
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public FormAndDataBean getFormData(String formUuid, String dataUuid) {
		return formDataService.getFormData(formUuid, dataUuid, null);
	}

	/**
	 * 
	 * 获取表单显示数据
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public FormAndDataBean getFormShowData(String formUuid, String dataUuid) {
		return formDataService.getFormShowData(formUuid, dataUuid, null);
	}

	/**
	 * 保存表单数据
	 * 
	 * @param formData
	 * @return
	 */
	public String saveFormData(RootFormDataBean formData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = formDataService.save(formData);
		return (String) map.get("uuid");
	}

	/**
	 * 保存表单数据
	 * 
	 * @param formData
	 * @return
	 */
	public Map compareData(RootFormDataBean formData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = formDataService.save(formData);
		return map;
	}

	/**
	 * 根据字段名，获取表单字段的值
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param fieldName
	 * @return getFieldValueByMappingName
	 * @see
	 */
	@Deprecated
	public Object getFormFieldValue(String formUuid, String dataUuid, String fieldName) {
		return formDataService.getFormFieldValue(formUuid, dataUuid, fieldName);
	}

	/**
	 * 根据映射名，获取表单字段的值
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValueByMappingName(String formUuid, String dataUuid, String mappingName) {
		return formDataService.getFieldValueByMappingName(formUuid, dataUuid, mappingName);
	}

	/**
	 * 根据主表的formUuid和dataUuid取到从表的dataUuid和formUuid列表的方法
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public List<FormDataInfo> getSubFormDataInfo(String formUuid, String dataUuid) {
		return formDataService.getSubFormDataInfo(formUuid, dataUuid);
	}

	/**
	 * 动态表单数据查询
	 * 
	 * @param formUuid
	 *            表单uuid
	 * @param projection
	 *            查询的列名，为空查询所有列
	 * @param selection
	 *            查询where条件语句
	 * @param selectionArgs
	 *            查询where条件语句参数
	 * @param groupBy
	 *            分组语句
	 * @param having
	 *            分组条件语句
	 * @param orderBy
	 *            排序
	 * @param firstResult
	 *            首条记录索引号
	 * @param maxResults
	 *            最大记录集
	 * @return List<QueryItem> 查询结果列表
	 */
	public List<QueryItem> query(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		return formDataService.query(formUuid, projection, selection, selectionArgs, groupBy, having, orderBy,
				firstResult, maxResults);
	}

	public List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults) {
		return formDataService.query(tableName, distinct, projection, selection, selectionArgs, groupBy, having,
				orderBy, firstResult, maxResults);
	}

	/**
	 * 
	 * 根据传入的表名获取表数据的总记录数
	 * 
	 * @param tableName
	 * @return
	 */
	public Integer queryTotal(String tableName) {
		return formDataService.queryTotal(tableName);
	}

	/**
	 * 通过传入的formUuid，dataUuid,mappingName 查找并返回tableName,fieldName,fieldValue属性
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @param mappingName
	 * @return
	 */
	public Object getFieldInfosByMappingName(String formUuid, String dataUuid, String mappingName) {
		return formDataService.getFieldInfosByMappingName(formUuid, dataUuid, mappingName);
	}

	/**
	 *动态表单从表数据复制 
	 * 
	 * @param sourceFormUuid
	 * @param sourceDataUuid
	 * @param targetFormUuid
	 * @param targetDataUuid
	 * @param subFormUuid
	 * @param tempList
	 */
	public List<String> copySubFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid,
			String targetDataUuid, String subFormUuid, String whereSql, Map<String, Object> values) {
		return formDataService.copySubFormData(sourceFormUuid, sourceDataUuid, targetFormUuid, targetDataUuid,
				subFormUuid, whereSql, values);
	}

	/**
	 * 动态表单数据复制
	 * 
	 * @param sourceFormUuid
	 *            源表单定义UUID
	 * @param sourceDataUuid
	 *            源表单数据UUID
	 * @param targetFormUuid
	 *            目标表单定义UUID
	 * @return 返回复制后的表单数据
	 */
	public String copyFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid) {
		return formDataService.copyFormData(sourceFormUuid, sourceDataUuid, targetFormUuid);
	}

	/**
	 * 动态表单数据复制，返回新的数据UUID
	 * 
	 * @param formAndDataBean
	 * @param targetFormUuid
	 * @return
	 */
	public String copyFormData(RootFormDataBean formAndDataBean, String targetFormUuid) {
		return formDataService.copyFormData(formAndDataBean, targetFormUuid);
	}

	/**
	 * 
	 * 根据表单uuid获取表单对应的打印模板code
	 * 
	 * @param uuid
	 * @return
	 */
	public String getPrintTemplateCode(String uuid) {
		RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(uuid, true, false);
		return rootTableInfo.getTableInfo().getPrintTemplate();
	}

	/**
	 * 
	 * 获得从表某个字段的值
	 * 
	 * @param formUid
	 * @param dataUid
	 * @param fieldName
	 * @return
	 */
	public String getSubFormFieldValue(String formUid, String dataUid, String fieldName) {
		return formDataService.getSubFormFieldValue(formUid, dataUid, fieldName);
	}

	/**
	 * 
	 * 验证表单数据是否满足验证
	 * 
	 * @param formId 表单id
	 * @param datas	表单数据集合
	 * @return Map key为datas的编号（从1累加），value为ArrayList<Map1>不满足的说明（Map1,key为字段名，value为说明）
	 */
	public Map<Integer, List<Map<String, String>>> verificationDytableData(String formId, List<Map> datas) {
		return formDefinitionService.verificationDytableData(formId, datas);
	}

}
