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
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService;
import com.wellsoft.pt.bpm.engine.entity.FlowDefinition;
import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDataService;
import com.wellsoft.pt.dyform.support.DictMap;
import com.wellsoft.pt.dyform.support.DyFormData;
import com.wellsoft.pt.dyform.support.DyFormDefinitionJSON;
import com.wellsoft.pt.file.entity.FmFile;
import com.wellsoft.pt.org.entity.Department;
import com.wellsoft.pt.org.entity.Group;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.unit.entity.CommonUnit;
import com.wellsoft.pt.utils.encode.JsonBinder;

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
	@Autowired
	DyFormDataService dyFormDataService;

	@Autowired
	MongoFileService mongoFileService;
	@Autowired
	DataDictionaryService dataDictionaryService;

	/**
	 * 检查数据是否存在 
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/validate/exists", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> checkExists(@RequestBody String jsonData) {
		JSONObject jsonObject;
		String checkType = null;
		String uuid = null;
		String fieldName = null;
		String fieldValue = null;

		try {
			jsonObject = new JSONObject(jsonData);
			checkType = (String) jsonObject.get("tblName");
			fieldName = (String) jsonObject.get("fieldName");
			fieldValue = (String) jsonObject.get("fieldValue");
			uuid = (String) jsonObject.get("uuid");
		} catch (JSONException e1) {
		}

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

		DyFormData dyFormData = JsonBinder.buildNormalBinder().fromJson(jsonData, DyFormData.class);

		//dyFormData.setFieldValue("selectest", "DYBTN_WF_ADMIN");
		//dyFormData.setFieldValue("selectest", 1);
		//dyFormData.setFieldValue("test3", "{'2':'机关事业单位'}");
		//dyFormData.setFieldValue("test3", "2");
		return getSucessfulResultMsg(this.dyFormApiFacade.saveFormData(dyFormData));
	}

	@RequestMapping(value = "/getFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormData(@RequestParam("formUuid") String formUuid,
			@RequestParam("dataUuid") String dataUuid) {
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();

		formDatas = dyFormApiFacade.getFormData(formUuid, dataUuid);

		return getSucessfulResultMsg(formDatas);
	}

	@RequestMapping(value = "/editFormData", method = RequestMethod.GET)
	public ResponseEntity<ResultMessage> editFormData(@RequestParam("formUuid") String formUuid,
			@RequestParam("dataUuid") String dataUuid) {

		DyFormData dyformdata = this.dyFormApiFacade.getDyFormData(formUuid, dataUuid);

		DyFormData subformdata = dyformdata.getDyFormData("bfa0c271-0412-4c89-ba61-884d5c6f3fa6",
				"24cdc0ae-8bd8-43f5-9e47-b920f7038566");

		//subformdata.setFieldValue("radio", "1");

		this.dyFormApiFacade.saveFormData(dyformdata);

		return getSucessfulResultMsg(dyformdata);
	}

	@RequestMapping(value = "/getFormDefinitionData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormDefinitionData(@RequestParam("formUuid") String formUuid,
			@RequestParam("dataUuid") String dataUuid) {
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(formUuid, dataUuid);

		//List<DyformBlock> blocks = dyformdata.getBlocks();
		//dyformdata.hideBlock("uf_test_01");
		//dyformdata.setFieldValue("XMDBH", "a");
		//List<String> fields = new ArrayList<String>();
		//fields.add("rq");
		//fields.add("zy");
		//List<String> fields2 = new ArrayList<String>();
		//fields2.add("zy1");
		///dyformdata.setRequiredFields("35112ada-91a2-4ddc-922e-5ac047e595bf", fields2);
		//dyformdata.setReadonlyFields("fd7731bb-fb9d-4689-9fdd-618c888cfea7", fields);

		//List<String> fields3 = new ArrayList<String>();
		//fields3.add("zy");
		//dyformdata.setRequiredFields("fd7731bb-fb9d-4689-9fdd-618c888cfea7", fields3);
		return getSucessfulResultMsg(dyformdata);
	}

	@RequestMapping(value = "/getFormDefinitionDataByFormId", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormDefinitionDataByFormId(@RequestParam("formId") String formId,
			@RequestParam("dataUuid") String dataUuid) {
		String formUuid = this.dyFormApiFacade.getFormUuidById(formId);
		DyFormData dyformdata = dyFormApiFacade.getDyFormData(formUuid, dataUuid);
		//List<String> fieldNames = new ArrayList<String>();
		//fieldNames.add("DFDSF");
		//dyformdata.setHiddenFields(formUuid, fieldNames);
		//dyformdata.setReadonlyFields(formUuid, fieldNames);
		//dyformdata.setShowFields(formUuid, fieldNames);
		return getSucessfulResultMsg(dyformdata);
	}

	@RequestMapping(value = "/getFieldValueByMappingName", method = RequestMethod.GET)
	public ResponseEntity<ResultMessage> getFormDefinitionData(@RequestParam("formUuid") String formUuid,
			@RequestParam("dataUuid") String dataUuid, @RequestParam("mappingName") String mappingName) {
		DyFormData formData = dyFormApiFacade.getDyFormData(formUuid, dataUuid);
		return getSucessfulResultMsg(formData.getFieldValueByMappingName(mappingName));
	}

	@RequestMapping(value = "/getFormDataOfChildNode4ParentNode", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormDataOfChildNode4ParentNode(
			@RequestParam("formUuidOfSubform") String formUuidOfSubform,
			@RequestParam("formUuidOfMainform") String formUuidOfMainform,
			@RequestParam("dataUuidOfMainform") String dataUuidOfMainform,
			@RequestParam("dataUuidOfParentNode") String dataUuidOfParentNode) {
		List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDatas = null;

		formDatas = dyFormApiFacade.getFormDataOfChildNode4ParentNode(formUuidOfSubform, formUuidOfMainform,
				dataUuidOfMainform, dataUuidOfParentNode);

		return getSucessfulResultMsg(formDatas);
	}

	/*@RequestMapping(value = "/getMainFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getMainFormData(String formUuid, String dataUuid) {
		Map<String 表单字段名, Object表单字段值> formDatas = new HashMap<String, Object>();
		formDatas = dyFormApiFacade.getFormDataOfMainform(formUuid, dataUuid);
		return getSucessfulResultMsg(formDatas);
	}*/

	@RequestMapping(value = "/getDefaultFormData", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getDefaultFormData(@RequestParam("formUuid") String formUuid) {
		Map<String, Object> formData = new HashMap<String, Object>();
		try {
			formData = dyFormApiFacade.getDefaultFormData(formUuid);
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionFomatException);
		}
		return getSucessfulResultMsg(formData);
	}

	@RequestMapping(value = "/getFormDataOfParentNode", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getFormDataOfParentNode(String formUuidOfSubform, String formUuidOfMainform,
			String dataUuidOfMainform, int pageSize, int currentPageNo) {
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setAutoCount(true);
		pagingInfo.setCurrentPage(currentPageNo);
		pagingInfo.setPageSize(pageSize);

		return getSucessfulResultMsg(this.dyFormApiFacade.getFormDataOfParentNodeByPage(formUuidOfSubform,
				formUuidOfMainform, dataUuidOfMainform, pagingInfo));

	}

	@RequestMapping(value = "/getDigestValue", method = RequestMethod.POST)
	public ResponseEntity<ResultMessage> getDigestValue(@RequestBody String signedContent) {
		return getSucessfulResultMsg(this.dyFormApiFacade.getDigestValue(signedContent));
	}

	@RequestMapping("/demo")
	public String open(@RequestParam(value = "formDefinition") String formDefinition, Model model) {
		model.addAttribute("formDefinition", formDefinition);
		return forward("/dyform/dyform_demo");
	}

	@RequestMapping("/synMatters")
	public String synMatters(String formUuid, String isAttach) throws JSONException, JsonParseException,
			JsonMappingException, IOException, ParseException {
		DyFormDefinition df = dyFormApiFacade.getFormDefinition(formUuid);
		DyFormDefinitionJSON dUtils = df.getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			String uuid = (String) record.get("uuid");
			/*	if (!"e692149b36eb4e20b74b3f559a3c3820".equals(record.get("uuid"))) {
					continue;
				}*/
			for (String fieldName : dUtils.getFieldNamesOfMainform()) {
				if (fieldName.equalsIgnoreCase("CKDJRY")) {
					System.out.println("");
				}

				/*	if (dUtils.isInputModeEqAttach(fieldName) && isAttach != null && isAttach.equals("1")) {//附件
						String purpose = "";
						if (fieldName.equalsIgnoreCase("SQBGMB")) {
							purpose = "apply_table_template";
						} else if (fieldName.equalsIgnoreCase("SQBGFB")) {
							purpose = "apply_table_fanben";
						} else if (fieldName.equalsIgnoreCase("ZNJHSB")) {
							purpose = "guide_cream";
						} else {////未知的附件字段
							continue;
						}

						List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(uuid, purpose);
						if (files.size() > 0) {
							System.out.println();
						}
						for (int index = 0; files != null && index < files.size(); index++) {
							MongoFileEntity file = files.get(index);
							MongoFileEntity fileCopy = mongoFileService.saveFile(file.getFileName(), file.getInputstream());
							mongoFileService.pushFileToFolder(uuid, fileCopy.getFileID(), fieldName);

						}
						continue;
					}*/

				if (dUtils.isValueAsMap(fieldName) && !fieldName.equals("uuid")) {
					String value = (String) record.get(fieldName);

					if (value == null || value.trim().length() == 0 || value.indexOf("{") != -1) {
						continue;
					}

					if (value.toLowerCase().startsWith("u")) {
						String[] values = value.split(";");
						JSONObject json = new JSONObject();
						for (String v : values) {
							if (v.trim().length() == 0) {
								continue;
							}
							User obj = (User) this.dyFormDataService.getEntity(User.class, v);
							if (obj == null) {
								json.put(v, v);
								continue;
							}
							String userName = obj.getUserName();
							json.put(v, userName);
						}
						record.put(fieldName, json.toString());

					} else if (value.toLowerCase().startsWith("d")) {
						String[] values = value.split(";");
						JSONObject json = new JSONObject();
						for (String v : values) {
							if (v.trim().length() == 0) {
								continue;
							}
							Department obj = (Department) this.dyFormDataService.getEntity(Department.class, v);
							if (obj == null) {
								json.put(v, v);
								continue;
							}
							String userName = obj.getName();
							json.put(v, userName);

						}

						record.put(fieldName, json.toString());
					} else if (value.toLowerCase().startsWith("g")) {
						String[] values = value.split(";");
						JSONObject json = new JSONObject();
						for (String v : values) {
							if (v.trim().length() == 0) {

								continue;
							}
							Group obj = (Group) this.dyFormDataService.getEntity(Group.class, v);
							if (obj == null) {
								json.put(v, v);
								continue;
							}
							String userName = obj.getName();
							json.put(v, userName);
						}

						record.put(fieldName, json.toString());
					} else {
						String[] values = value.split(";");

						JSONObject json = new JSONObject();
						for (String v : values) {
							if (v.trim().length() == 0) {
								continue;
							}
							CommonUnit obj = (CommonUnit) this.dyFormDataService.getEntity(CommonUnit.class, v);
							if (obj == null) {
								FlowDefinition fd = (FlowDefinition) this.dyFormDataService.getEntity(
										FlowDefinition.class, value);
								if (fd == null) {
									json.put(v, v);
								} else {
									String userName = fd.getName();
									json.put(v, userName);
								}

								//json.put(v, v);
								continue;
							}
							String userName = obj.getName();
							json.put(v, userName);
						}
						record.put(fieldName, json.toString());
					}
				} else {

				}
			}
			System.out.println(record.toString());
			this.dyFormDataService.saveFormData(formUuid, record);
		}
		System.out.println("完成");
		return null;
	}

	@RequestMapping("/xmlx_")
	public String xmlx_() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinition df = dyFormApiFacade.getFormDefinition(formUuid);
		DyFormDefinitionJSON dUtils = df.getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			String uuid = (String) record.get("uuid");
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			updateInfo.put("uuid", uuid);

			for (String fieldName : record.keySet()) {
				if (!fieldName.equalsIgnoreCase("SPJGCLLX_")) {
					continue;
				}

				String value = (String) record.get(fieldName);
				String realField = null;
				String displayField = null;
				//fieldName = fieldName.toLowerCase();
				String valueObject = (String) record.get(fieldName);

				if (valueObject == null || valueObject.trim().length() == 0) {
					continue;
				}

				JSONObject jsonObject = new JSONObject(valueObject);
				if (jsonObject.keySet().size() == 0) {//没值
					continue;
				}
				JSONObject toObject = new JSONObject();
				Iterator<String> it = jsonObject.keys();
				while (it.hasNext()) {
					String v = it.next();
					QueryItem item = this.dataDictionaryService.getKeyValuePair("APPROVAL_RESULT_TYPE", v);
					String label = (String) item.get("label");
					if (label == null) {
						toObject.put(v, v);
						logger.error("--->xmlx_[" + v + "]--->indb-->no mapping");
						continue;
					} else {
						toObject.put(v, label);
					}

					if (realField == null) {
						realField = v;
					} else {
						realField = realField + ";" + v;
					}

					if (displayField == null) {
						displayField = label;
					} else {
						displayField = displayField + ";" + label;
					}
				}

				updateInfo.put(fieldName, toObject.toString());
				String realFieldName = dUtils.getAssistedFieldNameRealValue(fieldName);
				String displayFieldName = dUtils.getAssistedFieldNameDisplayValue(fieldName);
				if (realFieldName != null && realFieldName.length() > 0) {
					updateInfo.put(realFieldName, realField);
				}
				if (displayFieldName != null && displayFieldName.length() > 0) {
					updateInfo.put(displayFieldName, displayField);
				}
			}

			if (updateInfo.size() > 1) {
				logger.info("--->org---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->org---->不更新:" + updateInfo.toString());
			}
		}
		System.out.println("完成");
		return null;
	}

	@RequestMapping("/org")
	public String org() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String fieldNames = "GLSPLCMC,SSBMDWMC,CKDJRY,SLHZRY,SQSLJGMC";
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinitionJSON dJson = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);

			for (String fieldName : fieldNames.split(",")) {
				String realField = null;
				//fieldName = fieldName.toLowerCase();
				String valueObject = (String) record.get(fieldName);

				if (valueObject == null || valueObject.trim().length() == 0) {
					continue;
				}

				JSONObject jsonObject = new JSONObject(valueObject);
				if (jsonObject.keySet().size() == 0) {//没值
					continue;
				}
				JSONObject toObject = new JSONObject();
				Iterator<String> it = jsonObject.keys();
				while (it.hasNext()) {
					String v = it.next();
					if (v.toLowerCase().startsWith("u")) {
						if (v.trim().length() == 0) {
							continue;
						}
						User obj = (User) this.dyFormDataService.getEntity(User.class, v);
						if (obj == null) {
							toObject.put(v, v);
							continue;
						}
						String userName = obj.getUserName();

						toObject.put(v, userName);

						if (realField == null) {
							realField = v;
						} else {
							realField = realField + ";" + v;
						}
					} else if (v.toLowerCase().startsWith("d")) {
						if (v.trim().length() == 0) {
							continue;
						}
						Department obj = (Department) this.dyFormDataService.getEntity(Department.class, v);
						if (obj == null) {
							toObject.put(v, v);
							continue;
						}
						String userName = obj.getName();
						if (realField == null) {
							realField = v;
						} else {
							realField = realField + ";" + v;
						}
						toObject.put(v, userName);
					} else if (v.toLowerCase().startsWith("g")) {
						if (v.trim().length() == 0) {
							continue;
						}
						User obj = (User) this.dyFormDataService.getEntity(User.class, v);
						if (obj == null) {
							toObject.put(v, v);
							continue;
						}
						String userName = obj.getUserName();

						toObject.put(v, userName);
						if (realField == null) {
							realField = v;
						} else {
							realField = realField + ";" + v;
						}

					} else {

						CommonUnit obj = (CommonUnit) this.dyFormDataService.getEntity(CommonUnit.class, v);
						if (obj == null) {
							FlowDefinition fd = (FlowDefinition) this.dyFormDataService.getEntity(FlowDefinition.class,
									v);
							if (fd == null) {
								toObject.put(v, v);
							} else {
								String userName = fd.getName();
								toObject.put(v, userName);
								if (realField == null) {
									realField = v;
								} else {
									realField = realField + ";" + v;
								}
							}

						} else {
							String userName = obj.getName();
							toObject.put(v, userName);
							if (realField == null) {
								realField = v;
							} else {
								realField = realField + ";" + v;
							}
						}

					}
				}
				updateInfo.put(fieldName, toObject.toString());
				String realFieldName = dJson.getAssistedFieldNameRealValue(fieldName);
				if (realFieldName != null && realFieldName.length() > 0) {
					updateInfo.put(realFieldName, realField);
				}
			}

			if (updateInfo.size() > 1) {
				logger.info("--->org---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->org---->不更新:" + updateInfo.toString());
			}
		}
		System.out.println("--->org---->同步完成");
		return null;

	}

	/**
	 * 文件库字段同步
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/fmFile")
	public String fmFile() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String fieldNames = "SSFSXMC,QZSXMC,BHCLSXMC";
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);

			for (String fieldName : fieldNames.split(",")) {
				//fieldName = fieldName.toLowerCase();
				String value = (String) record.get(fieldName);
				if (value == null || value.trim().length() == 0) {//没值
					continue;
				}
				JSONObject jsonObject = new JSONObject(value);
				if (jsonObject.keySet().size() == 0) {//没值
					continue;
				}
				JSONObject toObject = new JSONObject();
				Iterator<String> it = jsonObject.keys();
				while (it.hasNext()) {
					String real = it.next();
					FmFile obj = (FmFile) this.dyFormDataService.getEntity(FmFile.class, real);
					if (obj == null) {
						logger.error("--->fmFile---->fieldName:[" + fieldName + "]uuid[" + real + "] 在文件库中不存在");
						continue;
					}

					if (fieldName.equalsIgnoreCase("SSFSXMC")) {
						//SSFSXBH 
						String res = (String) updateInfo.get("SSFSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("SSFSXBH", res);
					} else if (fieldName.equalsIgnoreCase("QZSXMC")) {
						String res = (String) updateInfo.get("QZSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("QZSXBH", res);

					} else if (fieldName.equalsIgnoreCase("BHCLSXMC")) {
						String res = (String) updateInfo.get("BHCLSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("BHCLSXBH", res);
					}

					toObject.put(real, obj.getTitle());

				}
				updateInfo.put(fieldName, toObject.toString());
			}

			if (updateInfo.size() > 1) {
				logger.info("--->fmFile---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->fmFile---->不更新:" + updateInfo.toString());
			}

		}
		System.out.println("--->fmFile---->同步完成");
		return null;
	}

	/**
	 * 材料从表同步
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/defStuff_CLLXMC")
	public String defStuff_CLLXMC() throws JSONException, JsonParseException, JsonMappingException, IOException,
			ParseException {
		String fieldName = "CLLXMC";
		String codeType = "XZSP_MATERIAL_NAME";
		DictMap map = new DictMap();
		Map<String, String> oldNew = DictMap.XZSP_MATERIAL_NAMECode;
		String formUuid = "d16326db-6b49-402a-8e35-3b45bba8fb90";
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);

			//fieldName = fieldName.toLowerCase();
			String value = (String) record.get(fieldName);
			if (value == null || value.trim().length() == 0) {//没值
				continue;
			}
			JSONObject jsonObject = new JSONObject(value);
			if (jsonObject.keySet().size() == 0) {//没值
				continue;
			}
			JSONObject toObject = new JSONObject();
			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				String oldCode = it.next();
				String newCode = oldNew.get(oldCode);
				if (newCode == null) {
					logger.error("--->defStuff_CLLXMC[" + oldCode + "]--->dictMap---->no mapping ");
					toObject.put(oldCode, jsonObject.get(oldCode));
					continue;
				}
				QueryItem item = this.dataDictionaryService.getKeyValuePair(codeType, oldCode);
				String label = (String) item.get("label");
				if (label == null) {
					toObject.put(oldCode, jsonObject.get(oldCode));
					logger.error("--->defStuff_CLLXMC[" + oldCode + "]--->indb-->no mapping");
					continue;
				} else {
					toObject.put(newCode, label);
				}
			}
			updateInfo.put(fieldName, toObject.toString());

			if (updateInfo.size() > 1) {
				logger.info("--->defStuff_CLLXMC---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->defStuff_CLLXMC---->不更新:" + updateInfo.toString());
			}

		}
		System.out.println("--->defStuff_CLLXMC---->同步完成");
		return null;
	}

	/**
	 *  SPJGCLLYMC
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/SPJGCLLYMC")
	public String SPJGCLLYMC() throws JSONException, JsonParseException, JsonMappingException, IOException,
			ParseException {
		String fieldName = "SPJGCLLYMC";
		String codeType = "XZSP_MATERIAL_NAME";
		DictMap map = new DictMap();
		Map<String, String> oldNew = DictMap.XZSP_MATERIAL_NAMECode;
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinitionJSON dJson = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);
			String realField = null;

			//fieldName = fieldName.toLowerCase();
			String value = (String) record.get(fieldName);
			if (value == null || value.trim().length() == 0) {//没值
				continue;
			}
			JSONObject jsonObject = new JSONObject(value);
			if (jsonObject.keySet().size() == 0) {//没值
				continue;
			}
			JSONObject toObject = new JSONObject();

			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				String oldCode = it.next();
				String newCode = oldNew.get(oldCode);
				Iterator<String> ix = oldNew.values().iterator();

				while (ix.hasNext()) {
					if (oldCode.equals(ix.next())) {
						if (realField == null) {
							realField = oldCode;
						} else {
							realField = realField + ";" + oldCode;
						}
						break;
					}
				}
				if (newCode == null) {
					logger.error("--->SPJGCLLYMC[" + oldCode + "]--->dictMap---->no mapping ");
					toObject.put(oldCode, jsonObject.get(oldCode));
					continue;
				}
				QueryItem item = this.dataDictionaryService.getKeyValuePair(codeType, oldCode);
				String label = (String) item.get("label");
				if (label == null) {
					toObject.put(oldCode, jsonObject.get(oldCode));
					logger.error("--->SPJGCLLYMC[" + oldCode + "]--->indb-->no mapping");
					continue;
				} else {
					toObject.put(newCode, label);

				}
			}
			updateInfo.put(fieldName, toObject.toString());
			String realFieldName = dJson.getAssistedFieldNameRealValue(fieldName);
			if (realFieldName != null && realFieldName.length() > 0) {
				updateInfo.put(realFieldName, realField);
			}
			if (updateInfo.size() > 1) {
				logger.info("--->SPJGCLLYMC---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->SPJGCLLYMC---->不更新:" + updateInfo.toString());
			}

		}
		System.out.println("--->SPJGCLLYMC---->同步完成");
		return null;
	}

	/**
	 * SSJDMC
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/SSJDMC")
	public String SSJDMC() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String fieldName = "SSJDMC";
		String codeType = "XZSP_PROJECT_PROCESS_JSXM";

		DictMap map = new DictMap();
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinitionJSON dJson = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);

		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String realField = null;
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);

			//fieldName = fieldName.toLowerCase();
			String value = (String) record.get(fieldName);
			if (value == null || value.trim().length() == 0) {//没值
				continue;
			}
			JSONObject jsonObject = new JSONObject(value);
			if (jsonObject.keySet().size() == 0) {//没值
				continue;
			}
			JSONObject toObject = new JSONObject();
			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				String code = it.next();
				QueryItem item = this.dataDictionaryService.getKeyValuePair(codeType, code);
				String label = (String) item.get("label");
				if (label == null) {
					toObject.put(code, jsonObject.get(code));
					logger.error("--->SSJDMC[" + code + "]--->indb-->no mapping");
					continue;
				} else {
					toObject.put(code, label);
					if (realField == null) {
						realField = code;
					} else {
						realField = realField + ";" + code;
					}
				}
			}
			updateInfo.put(fieldName, toObject.toString());
			String realFieldName = dJson.getAssistedFieldNameRealValue(fieldName);
			if (realFieldName != null && realFieldName.length() > 0) {
				updateInfo.put(realFieldName, realField);
			}

			if (updateInfo.size() > 1) {
				logger.info("--->SSJDMC---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->SSJDMC---->不更新:" + updateInfo.toString());
			}

		}
		System.out.println("--->SSJDMC---->同步完成");
		return null;
	}

	/**
	 *  SXLX
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/SXLX")
	public String SXLX() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String fieldName = "SXLX";
		String codeType = "XZSP_MATTERS_TYPE";
		DictMap map = new DictMap();
		Map<String, String> oldNew = DictMap.XZSP_MATTERS_TYPECode;
		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinitionJSON dJson = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);

			//fieldName = fieldName.toLowerCase();
			String value = (String) record.get(fieldName);
			if (value == null || value.trim().length() == 0) {//没值
				continue;
			}
			JSONObject jsonObject = new JSONObject(value);
			if (jsonObject.keySet().size() == 0) {//没值
				continue;
			}
			JSONObject toObject = new JSONObject();

			Iterator<String> it = jsonObject.keys();
			while (it.hasNext()) {
				String oldCode = it.next();
				String newCode = oldNew.get(oldCode);
				if (newCode == null) {
					logger.error("--->SXLX[" + oldCode + "]--->dictMap---->no mapping ");
					toObject.put(oldCode, jsonObject.get(oldCode));
					continue;
				}
				QueryItem item = this.dataDictionaryService.getKeyValuePair(codeType, oldCode);
				String label = (String) item.get("label");
				if (label == null) {
					toObject.put(oldCode, jsonObject.get(oldCode));
					logger.error("--->SXLX[" + oldCode + "]--->indb-->no mapping");
					continue;
				} else {
					toObject.put(newCode, label);
				}
			}
			updateInfo.put(fieldName, toObject.toString());

			if (updateInfo.size() > 1) {
				logger.info("--->SXLX---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->SXLX---->不更新:" + updateInfo.toString());
			}

		}
		System.out.println("--->SXLX---->同步完成");
		return null;
	}

	@RequestMapping("/old2New")
	public String old2New() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		String codeType = "XZSP_MATERIAL_NAME";
		DictMap map = new DictMap();
		Map<String, String> oldNew = DictMap.XZSP_MATERIAL_NAMECode;
		Iterator<String> it = oldNew.keySet().iterator();
		while (it.hasNext()) {
			String old = it.next();
			String newv = oldNew.get(old);
			List<DataDictionary> list = this.dataDictionaryService.getDataDictionaries(codeType, old);
			for (int index = 0; list != null && index < list.size(); index++) {
				DataDictionary d = list.get(0);
				//d.setCode(newv);
				this.dyFormDataService.executeSql("update cd_data_dict set code = '" + newv + "' where uuid = '"
						+ d.getUuid() + "'");
			}
		}
		logger.info("完成.....");
		return null;
	}

	@RequestMapping("/realDisplay")
	public String realDisplay() throws JSONException, JsonParseException, JsonMappingException, IOException,
			ParseException {
		String fieldNames = "SSFSX,SXLX_,XMLX_,BJLX_,BJSX_,MXYH_,SSBMDW,CKDJRY_,SLHZRY_,SQSLJG,SPJGCLLX_,SPJGCLLY,QZSX,BHCLSX,GLSPLC,SSJD,XSGCTLB_,QY_,WWXS_";

		String formUuid = "7d65b1c0-3d7f-4223-87dd-60ee036013bb";
		DyFormDefinitionJSON dJson = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		List<Map<String, Object>> lsit = this.dyFormDataService.getFormDataOfMainform(formUuid);
		String[] fieldNameArray = fieldNames.split(",");
		for (Map<String, Object> record : lsit) {
			Map<String, Object> updateInfo = new HashMap<String, Object>();
			String uuid = (String) record.get("uuid");
			updateInfo.put("uuid", uuid);
			for (String fieldName : fieldNameArray) {
				if (dJson.isValueAsMap(fieldName)) {
					String realFieldName = dJson.getAssistedFieldNameRealValue(fieldName);
					String displayFieldName = dJson.getAssistedFieldNameDisplayValue(fieldName);

					/*
					if (fieldName.equalsIgnoreCase("SSFSXMC")) {
						//SSFSXBH 
						String res = (String) updateInfo.get("SSFSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("SSFSXBH", res);
					} else if (fieldName.equalsIgnoreCase("QZSXMC")) {
						String res = (String) updateInfo.get("QZSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("QZSXBH", res);

					} else if (fieldName.equalsIgnoreCase("BHCLSXMC")) {
						String res = (String) updateInfo.get("BHCLSXBH");
						if (res == null) {
							res = obj.getReservedText2();
						} else {
							res = obj.getReservedText2() + ";" + res;
						}
						updateInfo.put("BHCLSXBH", res);
					}
					*/

					//fieldName = fieldName.toLowerCase();
					String value = (String) record.get(fieldName);
					if ("BHCLSX".equals(fieldName) && value != null && value.length() > 0) {
						System.out.println();
					}
					if (value == null || value.trim().length() == 0) {//没值
						continue;
					}
					JSONObject jsonObject = new JSONObject(value);
					if (jsonObject.keySet().size() == 0) {//没值
						continue;
					}

					Iterator<String> it = jsonObject.keys();
					String realValue = null;
					String displayValue = null;
					while (it.hasNext()) {
						String vm = it.next();
						if (vm == null || vm.equals("null")) {
							continue;
						}
						if (realValue == null) {
							realValue = vm;
						} else {
							realValue = realValue + ";" + vm;
						}
						String dis = jsonObject.getString(vm);
						if (displayValue == null) {
							displayValue = dis;
						} else {
							displayValue = displayValue + ";" + dis;
						}

					}

					if (realFieldName != null && realFieldName.trim().length() > 0) {
						updateInfo.put(realFieldName, realValue);
					}

					if (displayFieldName != null && displayFieldName.trim().length() > 0) {
						updateInfo.put(displayFieldName, displayValue);
					}

				}

			}

			if (updateInfo.size() > 1) {
				logger.info("--->realDisplay---->更新的内容:" + updateInfo.toString());
				this.dyFormDataService.saveFormData(formUuid, updateInfo);
			} else {
				logger.info("--->realDisplay---->不更新:" + updateInfo.toString());
			}
		}
		logger.info("--->realDisplay---->更新完成");
		return null;
	}

}
