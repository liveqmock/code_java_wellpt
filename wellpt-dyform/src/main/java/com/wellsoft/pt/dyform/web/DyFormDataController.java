/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;

/**
 * Description: 动态表单数据Controller类
 *  
 * @author jiangmb
 * @date 2012-10-30
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-31.1	jiangmb		2012-10-31		Create
 * 2014-5-8.1	hunt		2014-5-8		定义JSON化
 * </pre>
 *
 */
@Controller
@RequestMapping("/dyformdata")
public class DyFormDataController extends BaseFormDataController {
	private Logger logger = LoggerFactory.getLogger(DyFormDataController.class);

	@Autowired
	DyFormApiFacade dyFormApiFacade;

	/**
	 * 检查数据是否存在 
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/validate/exists", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> checkExists(@RequestBody String jsonData) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);
		String checkType = (String) jsonObject.get("tblName");
		String uuid = (String) jsonObject.get("uuid");
		String fieldName = (String) jsonObject.get("fieldName");
		String fieldValue = (String) jsonObject.get("fieldValue");
		boolean isExist = false;
		if (StringUtils.isBlank(uuid)) {
			try {
				isExist = dyFormApiFacade.queryFormDataExists(checkType, fieldName, fieldValue);
			} catch (Exception e) {
				return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.ParameterException);
			}
		} else {
			try {
				isExist = dyFormApiFacade.queryFormDataExists(checkType, fieldName, fieldValue, uuid);
			} catch (Exception e) {
				return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.ParameterException);
			}
		}
		return getSucessfulResultMsg(new Boolean(isExist));
	}

	/**
	 * 保存表单数据
	 * @param jsonData
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/saveFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> saveFormData(@RequestBody String jsonData) throws JSONException,
			JsonParseException, JsonMappingException, IOException, ParseException {

		JSONObject jsonObject = new JSONObject(jsonData);
		JSONObject mainformData = jsonObject.getJSONObject("mainformData");//主表数据
		JSONObject subformData = jsonObject.getJSONObject("subformData");//从表数据
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段值*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();

		//解析主表
		String mainformUuid = (String) mainformData.keySet().toArray()[0];//主表定义uuid 
		JSONObject formData = mainformData.getJSONObject(mainformUuid);
		Map<String, Object> formColDataMap = new ObjectMapper().readValue(formData.toString(), HashMap.class);//列数据Map
		List<Map<String /*表单字段值*/, Object/*表单字段值*/>> formColDataMapOfMainform = new ArrayList<Map<String, Object>>();
		formColDataMapOfMainform.add(formColDataMap);
		formDatas.put(mainformUuid, formColDataMapOfMainform);

		//解析从表
		Iterator<String> it = subformData.keys();
		while (it.hasNext()) {
			String formUuid = it.next();
			JSONArray formDataArray = subformData.getJSONArray(formUuid);
			List<Map<String /*表单字段值*/, Object/*表单字段值*/>> formColDataMapOfSubform = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < formDataArray.length(); i++) {
				formData = (JSONObject) formDataArray.get(i);
				formColDataMap = new ObjectMapper().readValue(formData.toString(), HashMap.class);//列数据Map
				formColDataMapOfSubform.add(formColDataMap);
			}
			formDatas.put(formUuid, formColDataMapOfSubform);
		}

		String dataUuid = dyFormApiFacade.saveFormData(mainformUuid, formDatas);
		return getSucessfulResultMsg(dataUuid);
	}

	@RequestMapping(value = "/getFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormData(String formUuid, String dataUuid) {
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		try {
			formDatas = dyFormApiFacade.getFormData(formUuid, dataUuid);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionFomatException);
		}
		return getSucessfulResultMsg(formDatas);
	}

	/*@RequestMapping(value = "/getMainFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getMainFormData(String formUuid, String dataUuid) {
		Map<String 表单字段名, Object表单字段值> formDatas = new HashMap<String, Object>();
		formDatas = dyFormApiFacade.getFormDataOfMainform(formUuid, dataUuid);
		return getSucessfulResultMsg(formDatas);
	}*/

	@RequestMapping(value = "/getDefaultFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getDefaultFormData(String formUuid) {
		Map<String, Object> formData = new HashMap<String, Object>();
		try {
			formData = dyFormApiFacade.getDefaultFormData(formUuid);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionFomatException);
		}
		return getSucessfulResultMsg(formData);
	}

	@RequestMapping(value = "/getParentNodesOfSubform", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getParentNodesOfSubformByPage(String formUuidOfSubform,
			String formUuidOfMainform, String dataUuidOfMainform, int pageSize, int currentPageNo) {
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setAutoCount(true);
		pagingInfo.setCurrentPage(currentPageNo);
		pagingInfo.setPageSize(pageSize);

		return getSucessfulResultMsg(this.dyFormApiFacade.getFormDataOfParentNodeByPage(formUuidOfSubform,
				formUuidOfMainform, dataUuidOfMainform, pagingInfo));

	}
}
