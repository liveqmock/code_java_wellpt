package com.wellsoft.pt.dyform.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubFormFieldShow;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubformFieldPropertyName;
import com.wellsoft.pt.dyform.support.enums.EnumValidateCode;
import com.wellsoft.pt.utils.json.JsonUtils;

public class DyFormData {
	private String formUuid;
	//private Map<String, Object> mainformData;
	private Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
	private Map<String/*表单定义id*/, List<String>/*表单数据id*/> deletedFormDatas;
	private DataSignature signature;
	DyFormApiFacade dyFormApiFacade = (DyFormApiFacade) ApplicationContextHolder.getBean("dyFormApiFacade");
	private String formDefinition = null;
	private DyFormDefinitionJSON dUtils = null;
	private Map<String, DyFormDefinition> formDefinitionMap = new HashMap<String, DyFormDefinition>();
	private boolean loadSubformDefinition = false;
	private boolean loadDefaultFormData = false;
	private boolean loadDictionary = false;

	public DyFormData() {
		//initFormDefintion(this.formUuid); 
	}

	/**
	 * 创建一个有主、从数据的表单,表单你默认值
	 *
	 * @param formUuid
	 * @param formData
	 */
	public DyFormData(String formUuid, Map<String, List<Map<String, Object>>> formData) {
		this.setFormDatas(formData);

		this.initFormDefintion(formUuid);
	}

	/** 
	 * @param formUuid
	 * @param formData
	 */
	public DyFormData(String formUuid) {
		this.initFormDefintion(formUuid);
		this.setDataUuid(this.dyFormApiFacade.createUuid());
	}

	public void initFormDefintion() {
		this.initFormDefintion(formUuid);
	}

	/*public DyFormData(String jsonData) throws JSONException, JsonParseException, JsonMappingException, IOException {
		JSONObject jsonObject = new JSONObject(jsonData);
		JSONObject mainformData = jsonObject.getJSONObject("mainformData");//主表数据
		JSONObject subformData = jsonObject.getJSONObject("subformData");//从表数据
		JSONObject deletedFormDataOfSubform = jsonObject.getJSONObject("deletedFormDataOfSubform");//被删除的从表数据
		JSONObject signatureJSON;
		try {
			signatureJSON = jsonObject.getJSONObject("signature");//被删除的从表数据
			this.signature = JsonBinder.buildNormalBinder().fromJson(signatureJSON.toString(), DataSignature.class);
		} catch (Exception e) {
		}

		this.formDatas = new HashMap<String, List<Map<String, Object>>>();

		//解析主表
		String formUuid = (String) mainformData.keySet().toArray()[0];//主表定义uuid 
		JSONObject formData = mainformData.getJSONObject(formUuid);
		Map<String, Object> formColDataMap = new ObjectMapper().readValue(formData.toString(), HashMap.class);//列数据Map
		List<Map<String 表单字段值, Object表单字段值>> formColDataMapOfMainform = new ArrayList<Map<String, Object>>();
		formColDataMapOfMainform.add(formColDataMap);
		formDatas.put(formUuid, formColDataMapOfMainform);

		//解析从表
		Iterator<String> it = subformData.keys();
		while (it.hasNext()) {
			String formUuidtmp = it.next();
			JSONArray formDataArray = subformData.getJSONArray(formUuidtmp);
			List<Map<String 表单字段值, Object表单字段值>> formColDataMapOfSubform = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < formDataArray.length(); i++) {
				formData = (JSONObject) formDataArray.get(i);
				formColDataMap = new ObjectMapper().readValue(formData.toString(), HashMap.class);//列数据Map
				formColDataMapOfSubform.add(formColDataMap);
			}
			formDatas.put(formUuidtmp, formColDataMapOfSubform);
		}

		//解析被删除的从表记录
		Iterator<String> it2 = deletedFormDataOfSubform.keys();
		this.deletedFormDatas = new HashMap<String, List<String>>();
		while (it2.hasNext()) {
			String formUuidtmp = it2.next();
			JSONArray formDataArray = deletedFormDataOfSubform.getJSONArray(formUuidtmp);
			List<String> dataUuids = new ArrayList<String>();
			for (int i = 0; i < formDataArray.length(); i++) {
				Object obj = formDataArray.get(i);
				dataUuids.add((String) obj);
			}
			deletedFormDatas.put(formUuidtmp, dataUuids);
		}
		this.initFormDefintion(formUuid);
	}*/
	/**
	 *  
	 * 
	 * @param formUuid 
	 */
	private void initFormDefintion(String formUuid) {

		this.formUuid = formUuid;
		DyFormDefinition dyformDefinition = this.dyFormApiFacade.getFormDefinition(this.formUuid);
		this.formDefinition = dyformDefinition.getDefinitionJson();
		this.formDefinitionMap.put(this.formUuid, dyformDefinition);
		this.dUtils = dyformDefinition.getJsonHandler();
		if (this.loadDefaultFormData) {
			this.dUtils.loadDefaultFormData();//获取主表的默认值
		}

		if (this.loadDictionary) {
			long time1 = System.currentTimeMillis();
			this.dUtils.loadFieldDictionary();//加载字段对应的数据字典
			long time2 = System.currentTimeMillis();
			System.out.println("===>m loading FieldDictionary spent " + (time2 - time1) / 1000.0 + "s");
		}

		this.dUtils.loadFieldJobDictionary();//为职位控件添加option

		if (!this.loadSubformDefinition) {
			return;
		}
		List<SubformDefinition> subformConfigs = dyformDefinition.getSubformDefinitions();
		if (subformConfigs != null) {//获取从表的定义

			for (SubformDefinition config : subformConfigs) {//遍历从表的配置
				String subformFormUuid = config.getFormUuid();
				DyFormDefinition df = this.dyFormApiFacade.getFormDefinition(subformFormUuid);
				if (df == null) {
					continue;
				}
				//获取从表的默认值
				if (this.loadDefaultFormData) {
					df.getJsonHandler().loadDefaultFormData();
				}

				if (this.loadDictionary) {
					long time3 = System.currentTimeMillis();
					df.getJsonHandler().loadFieldDictionary();

					long time4 = System.currentTimeMillis();
					System.out.println("===>s loading FieldDictionary spent " + (time4 - time3) / 1000.0 + "s");
				}

				df.getJsonHandler().loadFieldJobDictionary();//为职位控件添加option
				this.formDefinitionMap.put(subformFormUuid, df);
				this.addSubformDefinition(df);

			}

		}

	}

	private void addSubformDefinition(DyFormDefinition df) {
		String formUuid = df.getUuid();
		Object jobj = this.dUtils.getFormProperty("subformDefinitions");
		List<String> subformDefinitions = null;
		if (jobj == null) {
			subformDefinitions = new ArrayList<String>();
		} else {
			subformDefinitions = (List<String>) jobj;
		}
		Iterator<String> it = subformDefinitions.iterator();
		while (it.hasNext()) {
			String json = it.next();
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(json);
				String uuid = jsonObj.getString("uuid");
				if (df.getUuid().equals(uuid)) {
					it.remove();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		subformDefinitions.add(df.getDefinitionJson());
		this.dUtils.addFormProperty("subformDefinitions", subformDefinitions);
	}

	public void addSubformData(DyFormData dyformData) {
		List<Map<String, Object>> formdatas = this.getFormDatas(dyformData.getFormUuid());
		if (formdatas == null) {
			formdatas = new ArrayList<Map<String, Object>>();
		}
		formdatas.add(dyformData.getFormDataByFormUuidAndDataUuid(dyformData.getFormUuid(), dyformData.getDataUuid()));
		this.formDatas.put(dyformData.getFormUuid(), formdatas);
	}

	public String getFormUuid() {
		return formUuid;
	}

	public Map<String, List<Map<String, Object>>> getFormDatas() {
		return formDatas;
	}

	public List<Map<String, Object>> getFormDatas(String formUuid) {
		return this.formDatas.get(formUuid);
	}

	/**
	 *  通过formUuid及dataUuid编辑添加从表数据
	 * @param formId
	 * @param dataUuid
	 * @return
	 */
	public DyFormData getDyFormData(String formUuid, String dataUuid) {
		DyFormData dyformdata = new DyFormData(formUuid);
		//dyformdata.setDataUuid(dataUuid);
		Map<String, Object> data = this.getFormDataByFormUuidAndDataUuid(formUuid, dataUuid);//获取formUuid、dataUuid对应的数据
		if (data == null) {//没找到formUuid、dataUuid对应的数据
			dyformdata.setDataUuid(dataUuid);
			data = dyformdata.getFormDataByFormUuidAndDataUuid(formUuid, dataUuid);
			this.addSubformData(dyformdata);
		}

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		datas.add(data);
		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> formDatas = new HashMap<String, List<Map<String, Object>>>();
		formDatas.put(formUuid, datas);
		dyformdata.setFormDatas(formDatas);
		return dyformdata;
	}

	/**
	 *  通过formId及dataUuid编辑添加从表数据
	 * @param formId
	 * @param dataUuid
	 * @return
	 */
	public DyFormData getDyFormDataByFormId(String formId, String dataUuid) {
		String formUuid = this.dyFormApiFacade.getFormUuidById(formId);
		return this.getDyFormData(formUuid, dataUuid);
	}

	public List<Map<String, Object>> getFormDatasById(String id) {
		List<Map<String, Object>> list = this.formDatas.get(this.dyFormApiFacade.getFormDefinitionById(id).getUuid());
		return list == null ? new ArrayList<Map<String, Object>>(0) : list;
	}

	public Map<String, Object> getFormDataByIdAndDataUuid(String id, String dataUuid) {
		List<Map<String, Object>> formdatas = this.getFormDatasById(id);
		for (Map<String, Object> formdata : formdatas) {
			if (dataUuid.equals(formdata.get("uuid"))) {
				return formdata;
			}
		}
		return null;
	}

	public Map<String, Object> getFormDataByFormUuidAndDataUuid(String formUuid, String dataUuid) {
		List<Map<String, Object>> formdatas = this.getFormDatas(formUuid);
		if (formdatas == null) {
			return null;
		}
		for (Map<String, Object> formdata : formdatas) {
			if (dataUuid.equals(formdata.get("uuid"))) {
				return formdata;
			}
		}
		return null;
	}

	public void setFormDatas(Map<String, List<Map<String, Object>>> formDatas) {
		if (formDatas != null) {
			this.formDatas = formDatas;
		}

	}

	public DataSignature getSignature() {
		return signature;
	}

	public void setSignature(DataSignature signature) {
		this.signature = signature;
	}

	public Map<String, List<String>> getDeletedFormDatas() {
		return deletedFormDatas;
	}

	public void setDeletedFormDatas(Map<String, List<String>> deletedFormDatas) {
		this.deletedFormDatas = deletedFormDatas;
	}

	/**
	 * 
	 * @param formUuid
	 * @param dataUuid 对于主表该参数可放置为null
	 * @return
	 */
	public DyFormDataUtils getFormDataUtils(String formUuid, String dataUuid) {
		if (this.formDefinition == null) {
			this.initFormDefintion();
		}

		DyFormDefinitionJSON dUtils = this.formDefinitionMap.get(formUuid).getJsonHandler();
		List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDatasOfFormUuid = this.getFormDatas().get(formUuid);
		if (formDatasOfFormUuid == null) {//找不到formUuid,dataUuid对应的数据
			if (this.formUuid.equals(formUuid)) {//如果是主表，则生成一条数据
				Map<String, Object> formDataColMap = new HashMap<String, Object>();
				formDatasOfFormUuid = new ArrayList<Map<String, Object>>();
				formDatasOfFormUuid.add(formDataColMap);
				this.getFormDatas().put(formUuid, formDatasOfFormUuid);
				return new DyFormDataUtils(formUuid, formDatasOfFormUuid.get(0), dUtils);
			}
			return null;
		}

		if (this.formUuid.equals(formUuid)) {//主表
			return new DyFormDataUtils(formUuid, formDatasOfFormUuid.get(0), dUtils);
		}

		if (dataUuid == null) {
			return null;
		}
		for (Map<String /*表单字段名*/, Object/*表单字段值*/> formData : formDatasOfFormUuid) {
			if (dataUuid.equals(((String) formData.get("uuid")))) {
				return new DyFormDataUtils(formUuid, formData, dUtils);
			}
		}
		return null;
	}

	public boolean isMainform(String formUuid) {
		if (this.formUuid.equals(formUuid)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置表单字段为必填项
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setRequiredFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {
			dydef.getJsonHandler().setRequired(fieldName, true);
			/*if (isMainform(formUuid)) {
				dydef.getJsonHandler().setRequired(fieldName, true);
			} else {
				dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.hidden,
						EnumSubFormFieldShow.hide.getValue());
			}*/
		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段为隐藏项
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setHiddenFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}

		for (String fieldName : fields) {
			if (isMainform(formUuid)) {
				dUtils.setShowTypeAsHide(fieldName, true);
			} else {
				dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.hidden,
						EnumSubFormFieldShow.hide.getValue());
			}

		}

		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段为显示
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setShowFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {
			if (isMainform(formUuid)) {
				dydef.getJsonHandler().setShowTypeAsHide(fieldName, false);
			} else {
				/*dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.hidden,
						EnumSubFormFieldShow.hide.getValue());*/
			}

		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段显示为文本
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setLabelFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {

			if (isMainform(formUuid)) {
				dydef.getJsonHandler().setShowTypeAsLabel(fieldName);
			} else {
				dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.editable,
						"0");
			}
		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段为只读
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setReadonlyFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {

			if (isMainform(formUuid)) {
				dydef.getJsonHandler().setShowTypeAsReadOnly(fieldName);
			} else {
				dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.editable,
						"0");
			}
		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段为可编辑
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setEditableFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {

			if (isMainform(formUuid)) {
				dydef.getJsonHandler().setShowTypeAsEditable(fieldName);
			} else {
				dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.editable,
						"1");
			}
		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 设置表单字段为不可用
	 * 
	 * @param formUuid	表单定义UUID
	 * @param fields	字段名列表
	 */
	public void setDisabledFields(String formUuid, List<String> fields) {
		DyFormDefinition dydef = this.formDefinitionMap.get(formUuid);
		if (dydef == null) {
			throw new RuntimeException("subform formUuid:" + formUuid + " not in form:" + this.formUuid);
		}
		for (String fieldName : fields) {
			if (isMainform(formUuid)) {
				dydef.getJsonHandler().setShowTypeAsDisable(fieldName);
			} else {
				/*dUtils.addSubformFieldPropertyOfStringType(formUuid, fieldName, EnumSubformFieldPropertyName.hidden,
						EnumSubFormFieldShow.hide.getValue());*/
			}
		}
		if (!isMainform(formUuid))
			this.addSubformDefinition(dydef);
	}

	/**
	 * 判断是否有指定的字段映射名
	 * 
	 * @param fieldMappingName
	 * @return
	 */
	public boolean hasFieldMappingName(String fieldMappingName) {
		return this.dUtils.hasFieldNameByAppyTo(fieldMappingName);
	}

	/**
	 * 通过传入的映射名来获取字段显示值
	 * 
	 * @param fieldMappingName
	 * @return
	 */
	public Object getFieldDisplayValueByMappingName(String fieldMappingName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getDisplayValueByApplyTo(fieldMappingName);
	}

	/**
	 * 通过传入的映射名来获取字段值
	 * 
	 * @param fieldMappingName
	 * @return
	 */
	public Object getFieldValueByMappingName(String fieldMappingName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getValueByApplyTo(fieldMappingName);
	}

	/**
	 * 根据映射名设置对应的字段值
	 * 
	 * @param mappingName
	 * @param value
	 */
	public void setFieldMapValueByMappingName(String mappingName, String value) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		//utils.setValueByApplyTo(mappingName, value);

		utils.setValueMapByApplyTo(mappingName, value);

	}

	/**
	 * 通过字段名获取字段显示值
	 * 
	 * @param fieldName
	 * @return
	 */
	public Object getFieldDisplayValue(String fieldName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getDisplayValue(fieldName);
	}

	/**
	 * 通过字段名获取字段真实值
	 * 
	 * @param fieldName
	 * @return
	 */
	public Object getFieldRealValue(String fieldName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getRealValue(fieldName);
	}

	/**
	 * 通过字段名获取字段值
	 * 
	 * @param fieldName
	 * @return
	 */
	public Object getFieldValue(String fieldName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getValue(fieldName);
	}

	public List<String> getValueOfFileIds(String fieldName) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		return utils.getValueOfFileIds(fieldName);
	}

	public List<String> getValueOfFileIds4Subform(String fieldName, String formUuidx, String dataUuid) {
		DyFormDataUtils utils = this.getFormDataUtils(formUuidx, dataUuid);
		return utils.getValueOfFileIds(fieldName);
	}

	/**
	 * 设置字指定定义的值
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void setFieldValue(String fieldName, Object value) {
		/*if (this.getDataUuid() == null) {
			return;
		}*/
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		if (this.dUtils.isValueAsMap(fieldName)) {
			Map<String, Object> d = new HashMap<String, Object>();
			try {
				String valueObj = (String) DyFormDataUtils.convertData2DbType(DyFormConfig.DbDataType._string, value,
						null);
				if (valueObj == null || valueObj.trim().length() == 0) {
					utils.setValueMap(fieldName, d, false);
					return;
				} else {
					if (valueObj.trim().startsWith("{") && valueObj.trim().endsWith("}")) {
						try {
							JSONObject jsonValue = new JSONObject(valueObj);
							Iterator<String> it = jsonValue.keys();
							while (it.hasNext()) {
								String key = it.next();
								d.put(key, jsonValue.get(key));
							}
						} catch (JSONException e) {

						}
					} else {
						d.put(valueObj, valueObj);
						utils.setValueMap(fieldName, d, false);
					}
				}
				//d.put(fieldName, d);
			} catch (ParseException e) {
			}
			utils.setValueMap(fieldName, d, false);
		} else {
			utils.setValue(fieldName, value);
		}

	}

	/**
	 * 设置值为json的字段
	 * 
	 * @param fieldName
	 * @param value
	 */
	public void setFieldMapValue(String fieldName, String value) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		if (!this.dUtils.isValueAsMap(fieldName)) {
			utils.setValue(fieldName, value);
		} else {
			utils.setValueMap(fieldName, value);

		}
	}

	/**
	 * 根据映射名设置对应的字段值
	 * 
	 * @param mappingName
	 * @param value
	 */
	public void setFieldValueByMappingName(String mappingName, Object value) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		utils.setValueByApplyTo(mappingName, value);

	}

	public String getDataUuid() {
		Map<String /*表单字段名*/, Object/*表单字段值*/> formDatasOfMainform = this.getFormDataOfMainform();
		if (formDatasOfMainform == null) {
			return null;
		}
		return (String) formDatasOfMainform.get("uuid");
	}

	public Map<String /*表单字段名*/, Object/*表单字段值*/> getFormDataOfMainform() {
		if (this.getFormDatas() == null) {
			return null;
		}
		if (this.getFormDatas().get(this.formUuid) == null || this.getFormDatas().get(this.formUuid).size() == 0) {
			return null;
		}
		return this.getFormDatas().get(this.formUuid).get(0);
	}

	/**
	 * 获取所有的字段的显示值,formUuid为key
	 * @return
	 */
	public Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> getDisplayValues() {

		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> displayFormDatas = new HashMap<String, List<Map<String, Object>>>();
		if (formDatas == null) {
			return null;
		}
		for (String formUuid : formDatas.keySet()) {

			List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDataOneForm = this.formDatas.get(formUuid);
			List<Map<String /*表单字段名*/, Object/*表单字段值*/>> displayFormDataOneForm = new ArrayList<Map<String, Object>>();
			for (Map<String /*表单字段名*/, Object/*表单字段值*/> formData : formDataOneForm) {
				DyFormDataUtils utils = new DyFormDataUtils(formUuid, formData, this.formDefinitionMap.get(formUuid)
						.getJsonHandler());
				displayFormDataOneForm.add(utils.getDisplayValues());
			}
			displayFormDatas.put(formUuid, displayFormDataOneForm);
		}
		return displayFormDatas;
	}

	/**
	 * 获取所有的字段的显示值,formId为key
	 * @return
	 */
	public Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> getDisplayValuesKeyAsFormId() {

		Map<String/*表单定义uuid*/, List<Map<String /*表单字段名*/, Object/*表单字段值*/>>> displayFormDatas = new HashMap<String, List<Map<String, Object>>>();
		if (formDatas == null) {
			return null;
		}
		for (String formUuid : formDatas.keySet()) {
			List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDataOneForm = this.formDatas.get(formUuid);
			List<Map<String /*表单字段名*/, Object/*表单字段值*/>> displayFormDataOneForm = new ArrayList<Map<String, Object>>();
			for (Map<String /*表单字段名*/, Object/*表单字段值*/> formData : formDataOneForm) {
				DyFormDataUtils utils = new DyFormDataUtils(formUuid, formData, this.formDefinitionMap.get(formUuid)
						.getJsonHandler());
				displayFormDataOneForm.add(utils.getDisplayValues());
			}

			displayFormDatas.put(this.formDefinitionMap.get(formUuid).getOuterId(), displayFormDataOneForm);
		}
		return displayFormDatas;
	}

	public String getFormDefinition() {
		return this.dUtils.toString();
	}

	public void setDataUuid(String dataUUid) {
		DyFormDataUtils utils = this.getFormDataUtils(this.formUuid, null);
		utils.setValue("uuid", dataUUid);
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		try {

			json.put("formDefinition", this.formDefinition);
			json.put("formUuid", this.getFormUuid());
			json.put("dataUuid", this.getDataUuid());
			json.put("formDatas", this.formDatas);
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return json.toString();
	}

	public ValidateMsg validateFormData() {
		ValidateMsg vMsg = new ValidateMsg();
		if (this.formDatas == null) {//没有数据则直接验证通过
			vMsg.code = EnumValidateCode.NULL;

			return vMsg;
		} else if (this.formDatas.size() == 0) {
			vMsg.code = EnumValidateCode.EMPTY;
			return vMsg;
		}

		Iterator<String> it = this.formDatas.keySet().iterator();
		while (it.hasNext()) {
			String formUuid = it.next();
			List<Map<String /*表单字段名*/, Object/*表单字段值*/>> formDataList = this.formDatas.get(formUuid);
			for (Map<String, Object> formData : formDataList) {
				vMsg = this.validateFormData(formUuid, formData);
			}
			//DyFormDataUtils dataUtils = new DyFormDataUtils(formUuid, this.formDatas.get);
		}
		return vMsg;
	}

	public Map<Integer, List<Map<String, String>>> validateFormdate() {
		return null;
	}

	public ValidateMsg validateFormData(String formUuid, Map<String /*表单字段名*/, Object/*表单字段值*/> formData) {
		DyFormDataUtils dataUtils = new DyFormDataUtils(formUuid, formData);
		return dataUtils.validate();
	}

	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
		this.initFormDefintion();
	}

	public void setLoadSubformDefinition(boolean loadSubformDefinition) {
		this.loadSubformDefinition = loadSubformDefinition;
	}

	public void setLoadDefaultFormData(boolean loadDefaultFormData) {
		this.loadDefaultFormData = loadDefaultFormData;
	}

	public void setLoadDictionary(boolean loadDictionary) {
		this.loadDictionary = loadDictionary;
	}

	public void showBlock(String blockCode) {
		this.dUtils.setBlockHide(blockCode, false);
	}

	public void hideBlock(String blockCode) {
		this.dUtils.setBlockHide(blockCode, true);
	}

	public List<DyformBlock> getBlocks() {
		JSONObject json = this.dUtils.getBlockDefinitionJSONObjects();
		if (json == null) {
			return null;
		}
		List<DyformBlock> blocks = new ArrayList<DyformBlock>();
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			String blockCode = it.next();
			try {
				DyformBlock block = JsonUtils.json2Object(json.getJSONObject(blockCode).toString(), DyformBlock.class);
				blocks.add(block);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		return blocks;
	}

	/**
	 * 隐藏从表的按钮
	 */
	public void hideSubformOperateBtn(final String formUuid) {
		this.dUtils.hideSubformOperateBtn(formUuid);
	}

	/**
	 * 显示从表的按钮
	 * @param formUuid
	 */
	public void showSubformOperateBtn(final String formUuid) {
		this.dUtils.showSubformOperateBtn(formUuid);
	}

}
