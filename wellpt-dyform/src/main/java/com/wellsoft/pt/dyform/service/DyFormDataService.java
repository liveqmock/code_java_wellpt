/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.criterion.Criterion;
import org.json.JSONException;

import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.support.DataSignature;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.integration.support.StreamingData;

/**
 * Description: 动态表单定义service类
 *  
 * @author jiangmb
 * @date 2012-12-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-12-20.1	hunt		2012-12-20		Create
 * </pre>
 *
 */
public interface DyFormDataService {

	/**
	 * 检查指定的字段是否已存在 
	 * 
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws Exception 
	 */
	boolean queryFormDataExists(String tblName, String fieldName, String fieldValue) throws Exception;

	/**
	 * 检查指定的字段是否存在于uuid指定的记录外的其他记录中
	 * 
	 * @param uuid
	 * @param tblName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws Exception 
	 */
	boolean queryFormDataExists(String uuid, String tblName, String fieldName, String fieldValue) throws Exception;

	String saveFormData(String mainformUuid,
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas,
			Map<String, List<String>> deletedFormDatas, DataSignature signature);

	/**
	 * 获取主表的数据
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	Map<String/*字段名*/, Object/*字段值*/> getFormDataOfMainform(String formUuid, String dataUuid);

	Map<String, Object> getDefaultFormData(String formUuid) throws JSONException;

	/**
	 * 分页查询从表的父节点记录
	 * 
	 * @param formUuidOfSubform
	 * @param formUuidOfMainform
	 * @param dataUuidOfMainform
	 * @param pagingInfo 
	 * @return
	 */
	QueryData getFormDataOfParentNodeByPage(String formUuidOfSubform, String formUuidOfMainform,
			String dataUuidOfMainform, PagingInfo pagingInfo);

	/**
	 * 获取指定的dataUUid下一层的子节点 
	 * 
	 * @param formUuidOfSubform
	 * @param formUuidOfMainform
	 * @param dataUuidOfMainform
	 * @param dataUuidOfParentNode
	 * @return
	 */
	List<Map<String, Object>> getFormDataOfChildNode4ParentNode(String formUuidOfSubform, String formUuidOfMainform,
			String dataUuidOfMainform, String dataUuidOfParentNode);

	/**
	 * 复杂搜索
	 * @param formUuid
	 * @param conditions
	 * @param pagingInfo 如果该值为null ，返回所有数据，当结果大于1000时将自动分页，pageSize=1000 
	 * @return 
	 */
	QueryData queryFormDataOfMainform(String formUuid, Criterion conditions, PagingInfo pagingInfo);

	public long queryTotalCountOfFormDataOfMainform(String tblName, Criterion conditions);

	/**
	 * 获取指定字符串的签名摘要
	 * @param formData
	 * @return
	 */
	public DataSignature getDigestValue(String signedContent);

	/**
	 * 获取指定字段的表单的数据
	 * @param tblName
	 * @param dataUuid
	 * @param fieldNames
	 * @return
	 */
	public Map<String, Object> getFormDataOfMainform(String tblName, String dataUuid, List<String> fieldNames);

	List<Map<String, Object>> getFormDataOfSubform(String formUuidOfSubform, String dataUuidOfMainform,
			String whereSql, Map<String, Object> values);

	public String saveFormData(String formUuid, Map<String, Object> formDataColMap) throws JSONException,
			JsonParseException, JsonMappingException, IOException, ParseException;

	List<QueryItem> query(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults);

	List<QueryItem> query(String tableName, boolean distinct, String[] projection, String selection,
			Map<String, Object> selectionArgs, String groupBy, String having, String orderBy, int firstResult,
			int maxResults);

	public List<QueryItem> query2(String formUuid, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, int firstResult, int maxResults);

	List<Map<String, Object>> getFormDataOfMainform(String formUuid);

	Object getEntity(Class clazz, String value);

	void update(Object obj);

	void executeSql(String string);

	public Map<Integer, List<Map<String, String>>> validateFormdates(String formUuid, List<Map<String, Object>> dataList);

	public String formDefinationToXml(String formUuid);

	public Map<String, Object> formDataToXmlData(String formUuid, DyFormData dyFormData);

	public DyFormData xmlDataToFormData(String formUuid, String xml, List<StreamingData> streamingDatas);

	/**
	 *判断数据表中指定的formUuid有多少数据
	 * 
	 * @param formUuid
	 * @return
	 */
	long countByFormUuid(String formUuid);

	long countDataInForm(String tblName);

	long queryTotalCountOfFormDataOfMainform(String tblName, String conditions);

}
