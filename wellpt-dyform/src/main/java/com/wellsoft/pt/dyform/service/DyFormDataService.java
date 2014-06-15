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
import org.json.JSONException;

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
			Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas)
			throws JsonParseException, JsonMappingException, JSONException, IOException, ParseException;

	Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> getFormData(String formUuid,
			String dataUuid) throws JSONException;

	/**
	 * 获取主表的数据
	 * @param formUuid
	 * @param dataUuid
	 * @return
	 */
	Map<String/*字段名*/, Object/*字段值*/> getFormDataOfMainform(String formUuid, String dataUuid);

	List<Map<String, Object>> getFormDataOfSubform(String formUuidOfMainform, String formUuidOfSubform,
			String dataUuidOfMainform);

	Map<String, Object> getDefaultFormData(String formUuid) throws JSONException;

}
