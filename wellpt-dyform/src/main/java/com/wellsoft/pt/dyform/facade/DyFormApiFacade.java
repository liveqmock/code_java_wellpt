/*
 * @(#)2013-1-28 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.facade;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.criterion.Criterion;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fr.fs.base.entity.User;
import com.wellsoft.pt.core.service.AbstractApiFacade;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.entity.BeanCopyUtils;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dyform.support.DataSignature;
import com.wellsoft.pt.dyform.support.DyFormConfig;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.DyFormDataUtils;
import com.wellsoft.pt.dyform.support.DyFormDefinitionJSON;
import com.wellsoft.pt.dyform.support.DyformBlock;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.dyform.support.SubformDefinition;
import com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField;
import com.wellsoft.pt.dytable.exception.SaveDataException;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.integration.support.StreamingData;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

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
public class DyFormApiFacade extends AbstractApiFacade {
	private Logger logger = LoggerFactory.getLogger(DyFormApiFacade.class);

	@Autowired
	DyFormDataService dyFormDataService;

	@Autowired
	DyFormDefinitionService dyFormDefinitionService;

	@Autowired
	MongoFileService mongoFileService;

	@Autowired
	OrgApiFacade orgApiFacade;

	/**
	 * 检查数据是否已存在<br/>
	 
	 * 检查指定的字段的值是否存在于指定的表中
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue 
	 * @return
	 * @throws Exception 
	 */
	public boolean queryFormDataExists(String tblName, String fieldName, String fieldValue) throws Exception {
		return dyFormDataService.queryFormDataExists(tblName, fieldName, fieldValue);
	}

	/**
	 * 检查数据是否已存在<br/>
	 * 检查指定的字段的值除了指定的uuid的记录外，还有没有存在于其他记录中<br/>
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @param uuid
	 * @return
	 * @throws Exception 
	 */
	public boolean queryFormDataExists(String tblName, String fieldName, String fieldValue, String uuid)
			throws Exception {
		return dyFormDataService.queryFormDataExists(uuid, tblName, fieldName, fieldValue);
	}

	/**
	 * @param mainformUuid 主表的表定义uuid
	 * @param formDatas 表单数据列表
	 * @param deletedFormDatas  被删除 的表单数据
	 * @param signature 
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 */
	public String saveFormData(String mainformUuid/*主表表单定义uuid*/,
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas,
			Map<String/*表单定义id*/, List<String>/*表单数据id*/> deletedFormDatas, DataSignature signature) {
		try {
			return dyFormDataService.saveFormData(mainformUuid, formDatas, deletedFormDatas, signature);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SaveDataException();
		}

	}

	public String saveFormData(DyFormData formData) {
		//formData.initFormDefintion();
		String dataUuid = this.saveFormData(formData.getFormUuid(), formData.getFormDatas(),
				formData.getDeletedFormDatas(), formData.getSignature());
		return dataUuid;

	}

	/**
	 * 获取主表数据
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public Map<String /*表单字段名*/, Object/*表单字段值*/> getFormDataOfMainform(String formUuid, String dataUuid) {
		return dyFormDataService.getFormDataOfMainform(formUuid, dataUuid);
	}

	/**
	 * 获取表单数据(包括主表、从表)
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 * @throws JSONException 
	 */
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
		DyFormDefinitionJSON dUtils;

		dUtils = this.getFormDefinition(formUuid).getJsonHandler();

		for (String formUuidOfSubform : dUtils.getFormUuidsOfSubform()) {
			QueryData qd = this.getFormDataOfParentNodeByPage(formUuidOfSubform, formUuid, dataUuid, null);
			if (qd == null || qd.getDataList() == null) {
				continue;
			}
			List formDataOfSubform = qd.getDataList();
			//this.getFormDataOfSubform(formUuid, formUuidOfSubform, dataUuid);

			if (formDataOfSubform == null) {
				continue;
			}
			formDatas.put(formUuidOfSubform, formDataOfSubform);
		}
		return formDatas;

	}

	/**
	 * 
	 * 获取动态表单数据
	 * 
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	public DyFormData getDyFormData(String formUuid, String dataUuid) {
		long time1 = System.currentTimeMillis();
		Map<String, List<Map<String, Object>>> formData = this.getFormData(formUuid, dataUuid);
		DyFormData dyFormData = new DyFormData();
		dyFormData.setLoadDefaultFormData(true);//加载默认值
		dyFormData.setLoadDictionary(true);//加载数字字段
		dyFormData.setLoadSubformDefinition(true);//加载从表定义
		dyFormData.setFormDatas(formData);
		dyFormData.setFormUuid(formUuid);
		long time2 = System.currentTimeMillis();
		logger.info("load FormDefinition and Data spent " + (time2 - time1) / 1000.0 + "s");
		return dyFormData;
	}

	/**
	 * 创建uuid
	 * @return
	 */
	public static String createUuid() {
		return DynamicUtils.getRandomUUID();
	}

	/**
	 * 获取表单定义
	 * @param formUuid 表单定义uuid
	 * @return
	 */
	public DyFormDefinition getFormDefinition(String formUuid) {
		DyFormDefinition dydf = this.dyFormDefinitionService.findDyFormDefinitionByFormUuid(formUuid);
		if (dydf == null) {
			return null;
		}
		DyFormDefinition df = null;
		try {
			BeanCopyUtils utils = new BeanCopyUtils();
			df = new DyFormDefinition();
			utils.copyProperties(df, dydf);
			if (df.getJsonHandler() == null) {
				df.setJsonHandler(new DyFormDefinitionJSON(df.getDefinitionJson()));
			}
			df.getJsonHandler().createOldName4AllFields();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return df;
	}

	/**
	 * 根据表单定义ID获取表单定义
	 * @param formDefId 表单定义ID
	 * @return
	 */
	public DyFormDefinition getFormDefinitionById(String outerId) {
		return this.dyFormDefinitionService.findDyFormDefinitionByOuterId(outerId);
	}

	/**
	 *  获取表单的定义
	 * @param isMaxVersion  <br>
	 * true 获取所有最新版本的表单定义基本信息<br>
	 * false 获取所有的表单定义基本信息
	 * 					   
	 * @return
	 */
	public List<DyFormDefinition> getAllFormDefinitions(boolean isMaxVersion) {
		if (isMaxVersion) {
			return this.dyFormDefinitionService.getMaxVersionList();
		} else {
			return this.dyFormDefinitionService.getAllFormDefintions();
		}

	}

	/**
	 * 根据指定的表单表名和版本号查找对应的字段定义信息列表
	 * 
	 * @param tblName
	 * @param version
	 * @return
	 */
	public List<FieldDefinition> getFieldDefinitions(String tblName, String version) {
		return this.getFormDefinition(tblName, version).getFieldDefintions();
	}

	/**
	 * 根据指定的表单表名和版本号查找表单定义
	 * @param tblName
	 * @param version
	 * @return
	 */
	public DyFormDefinition getFormDefinition(String tblName, String version) {
		return this.dyFormDefinitionService.getFormDefinition(tblName, version);
	}

	/**
	 * 拷贝,并将拷贝完的副本保存到目的表单中
	 * @param srcFormUuid
	 * @param srcDataUuid
	 * @param destFormUuid
	 * @return 拷贝失败时返回 null,成功时返回数据uuid
	 */
	public String copyAndSaveFormDataOfMainform(String srcFormUuid, String srcDataUuid, String destFormUuid) {
		Map<String, Object> destData = new HashMap<String, Object>();
		Map<String, Object> srcData = this.getFormDataOfMainform(srcFormUuid, srcDataUuid);
		DyFormDefinitionJSON destDJson = this.getFormDefinition(destFormUuid).getJsonHandler();
		List<String> destFieldNames = destDJson.getFieldNamesOfMainform();
		for (String destFieldName : destFieldNames) {
			if (destDJson.isInputModeEqAttach(destFieldName)) {//附件
				List<LogicFileInfo> fileEntities = mongoFileService.getNonioFilesFromFolder(srcDataUuid, destFieldName);
				destData.put(destFieldName, fileEntities);
			} else {
				destData.put(destFieldName, srcData.get(destFieldName));
			}
		}
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDataList = new ArrayList<Map<String, Object>>();
		formDataList.add(destData);
		formDatas.put(destFormUuid, formDataList);

		try {
			return this.saveFormData(destFormUuid, formDatas, null, null);
		} catch (SaveDataException e) {
			throw e;
		}

	}

	/**
	 * 动态表单主表数据复制
	 * 
	 * @param sourceFormUuid	源表单定义UUID
	 * @param sourceDataUuid	源表单数据UUID
	 * @param targetFormUuid	目标表单定义UUID
	 * @return	返回复制后的表单数据
	 */
	public String copyFormData(String sourceFormUuid, String sourceDataUuid, String targetFormUuid) {
		return this.copyAndSaveFormDataOfMainform(sourceFormUuid, sourceDataUuid, targetFormUuid);
	}

	/**
	 * 动态表单从表数据复制  
	 * @param formUuidOfMainform
	 * @param dataUuidOfMainform
	 * @param destFormUuid
	 * @param destDataUuid
	 * @param formUuidOfSubform
	 * @param whereSql
	 * @param values
	 * @return
	 */
	public List<String> copySubFormData(String formUuidOfMainform, String dataUuidOfMainform, String destFormUuid,
			String destDataUuid, String formUuidOfSubform, String whereSql, Map<String, Object> values) {
		List<String> uuids = new ArrayList<String>();
		try {
			List<Map<String, Object>> list = this.dyFormDataService.getFormDataOfSubform(formUuidOfSubform,
					dataUuidOfMainform, whereSql, values);

			for (int i = 0; i < list.size(); i++) {
				list.get(i).put(EnumRelationTblSystemField.mainform_data_uuid.name(), destDataUuid);
				list.get(i).put(EnumRelationTblSystemField.mainform_form_uuid.name(), destFormUuid);
				//list.get(i).put(EnumRelationTblSystemField.sort_order.name(),(String) list.get(i).get("sort_order"));
				String srcSubFormDataUuid = (String) list.get(i).get("uuid");
				list.get(i).remove("uuid");

				String uuid = this.dyFormDataService.saveFormData(formUuidOfSubform, list.get(i));
				uuids.add(uuid);

				//复制文件

				for (String fieldName : this.getFormDefinition(formUuidOfMainform).getJsonHandler()
						.getFieldNamesOfSubform(formUuidOfSubform)) {
					if (this.getFormDefinition(formUuidOfSubform).getJsonHandler().isInputModeEqAttach(fieldName)) {
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

		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return uuids;
	}

	/**
	 * 动态表单数据复制
	 * 
	 * @param dyFormData	动态表单数据
	 * @param targetFormUuid	目标表单定义UUID
	 * @return	返回复制后的表单数据
	 */
	public String copyFormData(DyFormData dyFormData, String targetFormUuid) {
		Map<String, Object> destData = new HashMap<String, Object>();
		DyFormDefinitionJSON destDJson = this.getFormDefinition(targetFormUuid).getJsonHandler();
		List<String> destFieldNames = destDJson.getFieldNamesOfMainform();
		DyFormDataUtils dyformdataUtils = dyFormData.getFormDataUtils(dyFormData.getFormUuid(), null);
		for (String destFieldName : destFieldNames) {
			/*if (destDJson.isInputModeEqAttach(destFieldName)) {//附件
				List<LogicFileInfo> fileEntities = mongoFileService.getNonioFilesFromFolder(dyFormData.getFormUuid(),
						destFieldName);
				destData.put(destFieldName, fileEntities);
			} else {
				
			}*/
			destData.put(destFieldName, dyformdataUtils.getValue(destFieldName));
		}
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDataList = new ArrayList<Map<String, Object>>();
		formDataList.add(destData);
		formDatas.put(targetFormUuid, formDataList);

		try {
			return this.saveFormData(targetFormUuid, formDatas, null, null);
		} catch (SaveDataException e) {
			throw e;
		}
	}

	/**
	 * 获取默认值
	 * @param formUuid
	 * @return
	 * @throws JSONException
	 */
	public Map<String, Object> getDefaultFormData(String formUuid) throws JSONException {
		return dyFormDataService.getDefaultFormData(formUuid);
	}

	/**
	 * 分页查询从表父节点记录
	 * @param formUuid 从表的表单uuid
	 * @param dataUuidOfMainform
	 * @param pagingInfo
	 * @return
	 */
	public QueryData getFormDataOfParentNodeByPage(String formUuidOfSubform, String formUuidOfMainform,
			String dataUuidOfMainform, PagingInfo pagingInfo) {
		return this.dyFormDataService.getFormDataOfParentNodeByPage(formUuidOfSubform, formUuidOfMainform,
				dataUuidOfMainform, pagingInfo);
	}

	public List<Map<String, Object>> getFormDataOfChildNode4ParentNode(String formUuidOfSubform,
			String formUuidOfMainform, String dataUuidOfMainform, String dataUuidOfParentNode) {
		return this.dyFormDataService.getFormDataOfChildNode4ParentNode(formUuidOfSubform, formUuidOfMainform,
				dataUuidOfMainform, dataUuidOfParentNode);
	}

	/**
	 * 
	 * @param formUuid
	 * @param conditions 
	 * @param pagingInfo 
	 * 			如果该值为null ，返回所有数据，当结果大于1000时将自动分页，pageSize=1000 
	 * @return
	 */
	public QueryData queryFormDataOfMainform(String formUuid, Criterion conditions, PagingInfo pagingInfo) {

		return this.dyFormDataService.queryFormDataOfMainform(formUuid, conditions, pagingInfo);

	}

	/**
	 * 根据formUuid获取表名
	 * @param formUuid
	 * @return
	 */
	public String getTblNameByFormUuid(String formUuid) {
		return this.getFormDefinition(formUuid).getName();
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

		return this.dyFormDataService.query(formUuid, projection, selection, selectionArgs, groupBy, having, orderBy,
				firstResult, maxResults);
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
	public List<QueryItem> query(boolean distinct, String formUuid, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults) {

		return this.dyFormDataService.query(this.getTblNameByFormUuid(formUuid), distinct, projection, selection,
				selectionArgs, groupBy, having, orderBy, firstResult, maxResults);
	}

	public List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults) {

		return this.dyFormDataService.query(tableName, distinct, projection, selection, selectionArgs, groupBy, having,
				orderBy, firstResult, maxResults);

	}

	public List<QueryItem> query2(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults) {
		return this.dyFormDataService.query2(formUuid, projection, selection, selectionArgs, groupBy, having, orderBy,
				firstResult, maxResults);
	}

	/**
	 * 根据表名，获取对应的所有版本的定义
	 * @param name
	 * @return
	 */
	public List<DyFormDefinition> getFormDefinitionsByTblName(String tblName) {

		return this.dyFormDefinitionService.getFormDefinitionsByTblName(tblName);
	}

	/**
	 * 将从表及从表对应的数据关系表组成一个视图subformdata的sql语句
	 * @param formUuidOfSubform
	 * @return
	 */
	public String getSqlOfSubformView(String formUuidOfSubform) {
		DyFormDefinition dy = this.getFormDefinition(formUuidOfSubform);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("WITH " + DyFormConfig.VIEWNAME_OF_SUBFORM + " AS (")
				.append(" select t2.*, t1.uuid uuid_sb, t1.creator creator_sb, t1.create_time create_time_sb, t1.modifier modifier_sb, t1.modify_time modify_time_sb, t1.rec_ver rec_ver_sb, ")
				.append("  t1.data_uuid, t1.mainform_data_uuid, t1.mainform_form_uuid, t1.sort_order, t1.parent_uuid from ")
				.append(dy.getRelationTbl()).append(" t1 left join ").append(dy.getName())
				.append(" t2 on t1.data_uuid = t2.uuid where t2.uuid is not null )");
		return sqlBuffer.toString();

	}

	public DataSignature getDigestValue(String signedContent) {
		return this.dyFormDataService.getDigestValue(signedContent);
	}

	/**
	 * 根据定义uuid创建一笔新的表单数据,且未持久化
	 * @param formUuid
	 * @return
	 */
	public DyFormData createDyformData(String formUuid) {
		DyFormData dyFormdata = null;
		try {
			Map<String, Object> formData = this.getDefaultFormData(formUuid);
			formData.put("uuid", this.createUuid());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(formData);
			Map<String, List<Map<String, Object>>> formdatas = new HashMap<String, List<Map<String, Object>>>();
			formdatas.put(formUuid, list);
			dyFormdata = new DyFormData(formUuid, formdatas);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
		return dyFormdata;
	}

	public long queryTotalCountOfFormDataOfMainform(String tblName, Criterion conditions) {
		return this.dyFormDataService.queryTotalCountOfFormDataOfMainform(tblName, conditions);
	}

	public long queryTotalCountOfFormDataOfMainform(String tblName, String conditions) {
		return this.dyFormDataService.queryTotalCountOfFormDataOfMainform(tblName, conditions);
	}

	public String getPrintTemplateCode(String formUuid) {
		return this.getFormDefinition(formUuid).getPrintTemplateId();
	}

	public QueryItem getFormKeyValuePair(String moduleId, String formUuids) {
		return this.dyFormDefinitionService.getFormKeyValuePair(moduleId, formUuids);
	}

	/**
	 * 获取所有的字段定义
	 * 
	 * @param formUuid
	 * @return
	 */
	public List<FieldDefinition> getFieldDefinitions(String formUuid) {
		return this.getFormDefinition(formUuid).getFieldDefintions();
	}

	/**
	 * 获取指定的表单下面的从表的定义
	 * 
	 * @param formUuid
	 * @return
	 */
	public List<SubformDefinition> getSubformDefinitions(String formUuid) {
		DyFormDefinition df = this.getFormDefinition(formUuid);
		return df.getSubformDefinitions();
	}

	/**
	 * 根据指定的表名获取其对应的最高版本的定义
	 * @param tableName
	 * @return
	 */
	public DyFormDefinition getFormDefinitionOfMaxVersionByTblName(String tableName) {
		return this.dyFormDefinitionService.getFormDefinitionOfMaxVersionByTblName(tableName);
	}

	/**
	 * 通过对外暴露的id获得对应的表单定义uuid
	 * @param id
	 * @return
	 */
	public String getFormUuidById(String id) {
		DyFormDefinition df = this.getFormDefinitionById(id);
		return df.getUuid();
	}

	public Object getEntity(Class<User> clazz, String value) {
		// TODO Auto-generated method stub
		return this.dyFormDataService.getEntity(clazz, value);
	}

	/**
	 * 获取真实值
	 * 
	 * @param value
	 * @return
	 */
	public static String getRealValue(String jsonValue) {
		if (jsonValue == null || jsonValue.trim().length() == 0) {
			return null;
		}
		JSONObject json;
		try {
			json = new JSONObject(jsonValue);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		if (json.keySet().size() == 0) {
			return null;
		}
		Iterator<String> it = json.keys();
		String realvalues = null;
		while (it.hasNext()) {
			String realValue = it.next();
			if (realvalues == null) {
				realvalues = realValue;
			} else {
				realvalues = realvalues + ";" + realValue;
			}
		}

		return realvalues;
	}

	public static String getDisplayValue(String jsonValue) {
		if (jsonValue == null || jsonValue.trim().length() == 0) {
			return null;
		}
		JSONObject json;
		try {
			json = new JSONObject(jsonValue);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		if (json.keySet().size() == 0) {
			return null;
		}
		Iterator<String> it = json.keys();
		String displayvalues = null;
		while (it.hasNext()) {
			String realvalue = it.next();
			String displayvalue;
			try {
				displayvalue = json.getString(realvalue);
			} catch (JSONException e) {
				return null;
			}
			if (displayvalues == null) {
				displayvalues = displayvalue;
			} else {
				displayvalues = displayvalues + ";" + displayvalue;
			}
		}

		return displayvalues;
	}

	public String getFormIdByFormUuid(String formUuid) {
		return this.getFormDefinition(formUuid).getOuterId();
	}

	/*public static void main(String[] args) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("a", "is");
		json.put("b", "no");

		System.out.println(DyFormApiFacade.getDisplayValue(json.toString()));
		System.out.println(DyFormApiFacade.getRealValue(json.toString()));
	}*/

	/**
	 * 
	 * 验证表单数据是否满足约束条件
	 * 
	 * @param formUuid
	 * @param dataList
	 * @return 
	 */
	//Map<Integer/*第几行*/, List<Map<String/*列名*/, String/*不满足的约束*/>>>
	public Map<Integer, List<Map<String, String>>> validateFormdates(String formUuid, List<Map<String, Object>> dataList) {

		return dyFormDataService.validateFormdates(formUuid, dataList);
	}

	/**
	 * 
	 * 将表单定义转换为xml标准串
	 * 
	 * @param formUuid
	 * @return
	 */
	public String formDefinationToXml(String formUuid) {
		return dyFormDataService.formDefinationToXml(formUuid);
	}

	/**
	 * 
	 * 将表单数据装换为xml及file列表
	 * map_.put("files", files);
	 * map_.put("xml", xml);
	 * @param formUuid
	 * @param dyFormData
	 * @return
	 */
	public Map<String, Object> formDataToXmlData(String formUuid, DyFormData dyFormData) {
		return dyFormDataService.formDataToXmlData(formUuid, dyFormData);
	}

	/**
	 *
	 * 将xml数据转换为DyFormData
	 * 
	 * @param xml
	 * @param streamingDatas
	 * @return
	 */
	public DyFormData xmlDataToFormData(String formUuid, String xml, List<StreamingData> streamingDatas) {
		return dyFormDataService.xmlDataToFormData(formUuid, xml, streamingDatas);
	}

	public static void main(String[] args) {
		System.out.println((Integer) null);
	}

	/**
	 * 删除表单定义及表单结构
	 * @param formUuid
	 */
	public void dropForm(String formUuid) {
		this.dyFormDefinitionService.dropForm(formUuid);
	}

	/**
	 * 判断数据表中指定的formUuid有多少数据
	 * 
	 * @param formUuid
	 * @return
	 */
	public long countByFormUuid(String formUuid) {

		return this.dyFormDataService.countByFormUuid(formUuid);
	}

	public long countDataInForm(String tblName) {

		return this.dyFormDataService.countDataInForm(tblName);
	}

	/**
	 * 获取所有用到model显示单据的表单定义
	 * @param outerId
	 * @return
	 */
	public List<DyFormDefinition> getFormDefinitionByModelId(String modelId) {

		return this.dyFormDefinitionService.getFormDefinitionByModelId(modelId);
	}

	/**
	 * 判断显示单据是否被使用
	 * 
	 * @param modelId
	 * @return
	 */
	public boolean isUsedOfDisplayModel(String modelId) {
		List<DyFormDefinition> dfs = this.getFormDefinitionByModelId(modelId);
		if (dfs == null || dfs.size() == 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 取得多个form的定义
	 * @param formUuids
	 * @return
	 */
	public List<DyFormDefinition> getFormDefinition(List<String> formUuids) {
		List<DyFormDefinition> list = new ArrayList<DyFormDefinition>();
		if (formUuids == null || formUuids.size() == 0) {
			return list;
		}
		for (String formUuid : formUuids) {
			DyFormDefinition dyf = this.getFormDefinition(formUuid);
			if (dyf != null) {
				list.add(dyf);
			}

		}

		return list;
	}

	/**
	 * 取得多个form的定义
	 * @param formUuids
	 * @return
	 */
	public List<String> getFormDefinitionJSON(List<String> formUuids) {
		List<DyFormDefinition> formDefs = this.getFormDefinition(formUuids);
		List<String> jsons = new ArrayList<String>();
		if (formDefs != null)
			for (DyFormDefinition dfd : formDefs) {
				jsons.add(dfd.getDefinitionJson());
			}

		return jsons;
	}

	public static String getCurrentUserMainJobName() {
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		HashMap<String, Object> majorJo = userDetail.getMajorJob();
		if (majorJo == null) {
			return "unknown";
		}
		return (String) majorJo.get("fullJobName");
	}

	public static List<String> getCurrentUserSecondaryJobs() {
		UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
		List<HashMap<String, Object>> otherJobs = userDetail.getOtherJobs();
		List<String> jobs = new ArrayList<String>();
		if (otherJobs == null) {
			return jobs;
		}

		for (HashMap<String, Object> job : otherJobs) {
			jobs.add((String) job.get("fullJobName"));
		}
		return jobs;
	}

	public static List<String> getCurrentUserSJobs() {
		List<String> jobs = new ArrayList<String>();
		jobs.add(getCurrentUserMainJobName());//主职位放在第一个位置
		jobs.addAll(getCurrentUserSecondaryJobs());
		return jobs;
	}

	/**
	 * 获取区块
	 * 
	 * @param formUuid
	 * @return
	 */
	public List<DyformBlock> getBlocksByformUuid(String formUuid) {
		DyFormData dyformdata = new DyFormData(formUuid);
		return dyformdata.getBlocks();
	}

	/**
	 * 获取区块
	 * 
	 * @param formUuid
	 * @return
	 */
	public List<DyformBlock> getBlocksByFormId(String formId) {
		String formUuid = this.getFormUuidById(formId);
		return this.getBlocksByformUuid(formUuid);
	}
}
