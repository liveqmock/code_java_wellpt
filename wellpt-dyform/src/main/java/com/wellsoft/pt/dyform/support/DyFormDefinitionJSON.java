package com.wellsoft.pt.dyform.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.datadict.service.DataDictionaryService;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormConfig.DyDateFomat;
import com.wellsoft.pt.dyform.support.DyFormConfig.DyFieldSysType;
import com.wellsoft.pt.dyform.support.DyFormConfig.DyShowType;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumDyCheckRule;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName_fieldCheckRules;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFormPropertyName;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumHideSubFormOperateBtn;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSignature;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubformFieldPropertyName;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumSubformPropertyName;
import com.wellsoft.pt.dyform.support.DyFormConfig.ValueCreateMethod;
import com.wellsoft.pt.dyform.support.enums.EnumRelationTblSystemField;
import com.wellsoft.pt.dyform.support.enums.EnumSystemField;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

import edu.emory.mathcs.backport.java.util.Arrays;

public class DyFormDefinitionJSON {

	private JSONObject formDefinitionJSONObject;
	private JSONObject fieldDefinitionJSONObjects;
	private JSONObject subformDefinitionJSONObjects;
	private JSONObject blockDefinitionJSONObjects = null;//区域
	private Map<String/*applyTo*/, String/*fieldName*/> applyTo2Field = new HashMap<String, String>();
	private Map<String/*inputMode*/, String/*fieldName*/> inputMode2Field = new HashMap<String, String>();

	public DyFormDefinitionJSON(String defintionJson) throws JSONException {
		this.formDefinitionJSONObject = new JSONObject(defintionJson);
		this.fieldDefinitionJSONObjects = this.getFormPropertyOfJSONType(EnumFormPropertyName.fields);
		this.subformDefinitionJSONObjects = this.getFormPropertyOfJSONType(EnumFormPropertyName.subforms);
		this.blockDefinitionJSONObjects = this.formDefinitionJSONObject.isNull(EnumFormPropertyName.blocks.name()) == true ? new JSONObject()
				: this.getFormPropertyOfJSONType(EnumFormPropertyName.blocks);
	}

	/**
	 * 获取字符串类型的表单属性
	 * @param formPropertyName
	 * @return
	 */
	public String getFormPropertyOfStringType(EnumFormPropertyName formPropertyName) {
		try {
			return this.formDefinitionJSONObject.getString(formPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取JSON类型的表单属性
	 * @param formPropertyName
	 * @return
	 */
	public JSONObject getFormPropertyOfJSONType(EnumFormPropertyName formPropertyName) {
		try {
			if (this.formDefinitionJSONObject.isNull(formPropertyName.name())) {
				return null;
			}
			return this.formDefinitionJSONObject.getJSONObject(formPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取JSON类型的表单属性
	 * @param formPropertyName
	 * @return
	 */
	public Object getFormProperty(String formPropertyName) {
		try {
			if (this.formDefinitionJSONObject.isNull(formPropertyName)) {
				return null;
			}
			return this.formDefinitionJSONObject.get(formPropertyName);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取指定字段的字段定义
	 * 
	 * @param fieldName
	 * @return
	 */
	public JSONObject getFieldDefinition(String fieldName) {
		try {
			Iterator<String> it = fieldDefinitionJSONObjects.keys();
			while (it.hasNext()) {
				String filedNametmp = it.next();
				if (filedNametmp.equalsIgnoreCase(fieldName)) {
					fieldName = filedNametmp;
				}
			}
			if (!this.fieldDefinitionJSONObjects.isNull(fieldName)) {
				return this.fieldDefinitionJSONObjects.getJSONObject(fieldName);
			} else if (!this.fieldDefinitionJSONObjects.isNull(fieldName.toLowerCase())) {
				return this.fieldDefinitionJSONObjects.getJSONObject(fieldName.toLowerCase());
			} else if (!this.fieldDefinitionJSONObjects.isNull(fieldName.toUpperCase())) {
				return this.fieldDefinitionJSONObjects.getJSONObject(fieldName.toUpperCase());
			} else {
				return null;
			}
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public JSONObject getSubformDefinition(String formUuidOfSubform) {
		return this.getSubformJSONObject(formUuidOfSubform);
	}

	/**
	 * 根据指定的字段及属性获取字符串类型的属性值
	 * @param fieldName
	 * @param fieldPropertyName
	 * @return
	 */
	public String getFieldPropertyOfStringType(String fieldName, EnumFieldPropertyName fieldPropertyName) {
		JSONObject fieldDefinitionJSONObject;
		try {
			fieldDefinitionJSONObject = this.getFieldDefinition(fieldName);
			if (fieldDefinitionJSONObject == null) {
				return null;
			}
			Object obj = fieldDefinitionJSONObject.get(fieldPropertyName.name());
			if (obj == null) {
				return null;
			}
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Long) {
				return Long.toString(((Long) obj).longValue());
			} else if (obj instanceof Integer) {
				return Integer.toString(((Integer) obj).intValue());
			} else if (obj instanceof Double) {
				return Double.toString(((Double) obj).doubleValue());
			} else if (obj instanceof Float) {
				return Float.toString(((Float) obj).floatValue());
			} else {
				return (String) obj;
			}
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}

	}

	/**
	 * 根据指定的字段及属性获取字符串类型的属性值
	 * @param fieldName
	 * @param fieldPropertyName
	 * @return
	 */
	public JSONObject getFieldPropertyOfJSONType(String fieldName, EnumFieldPropertyName fieldPropertyName) {
		JSONObject fieldDefinitionJSONObject;
		try {
			fieldDefinitionJSONObject = this.getFieldDefinition(fieldName);
			if (fieldDefinitionJSONObject == null)
				return null;
			return fieldDefinitionJSONObject.getJSONObject(fieldPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据指定的字段及属性获取JSON数组类型的属性值
	 * 
	 * @param fieldName
	 * @param fieldPropertyName
	 * @return
	 */
	public JSONArray getFieldPropertyOfJSONArrayType(String fieldName, EnumFieldPropertyName fieldPropertyName) {
		JSONObject fieldDefinitionJSONObject;
		try {
			fieldDefinitionJSONObject = this.getFieldDefinition(fieldName);
			return fieldDefinitionJSONObject.getJSONArray(fieldPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public JSONObject getSubformJSONObject(String formUuid) {
		try {
			return this.subformDefinitionJSONObjects.getJSONObject(formUuid);
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 *根据指定的从表及从表属性名获取字段串类型的属性值 
	 * @param formUuid
	 * @param subformPropertyName
	 * @return
	 */
	public String getSubformPropertyOfStringType(String formUuid, EnumSubformPropertyName subformPropertyName) {
		JSONObject subformDefinitionJSONObject;
		try {
			subformDefinitionJSONObject = this.getSubformJSONObject(formUuid);
			return subformDefinitionJSONObject.getString(subformPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 *根据指定的从表及从表属性名获取JSON类型的属性值 
	 * @param formUuid
	 * @param subformPropertyName
	 * @return
	 */
	public JSONObject getSubformPropertyOfJSONType(String formUuid, EnumSubformPropertyName subformPropertyName) {
		JSONObject subformDefinitionJSONObject;
		try {
			subformDefinitionJSONObject = this.getSubformJSONObject(formUuid);
			return subformDefinitionJSONObject.getJSONObject(subformPropertyName.name());
		} catch (JSONException e) {
			//e.printStackTrace();
			return null;
		}
	}

	public void addSubformFieldPropertyOfStringType(String formUuidOfSubform, String fieldName,
			EnumSubformFieldPropertyName fieldPropertyName, String subformPropertyValue) {
		try {
			this.getSubformPropertyOfJSONType(formUuidOfSubform, EnumSubformPropertyName.fields)
					.getJSONObject(fieldName).put(fieldPropertyName.name(), subformPropertyValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getSubformFieldPropertyOfStringType(String formUuid, String fieldName,
			EnumSubformFieldPropertyName propertyName) {

		try {

			return this.getSubformPropertyOfJSONType(formUuid, EnumSubformPropertyName.fields).getJSONObject(fieldName)
					.getString(propertyName.name());
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * 为定义添加字段
	 * @param fieldName
	 * @param obj
	 */
	public void addField(String fieldName, JSONObject obj) {
		try {
			this.fieldDefinitionJSONObjects.put(fieldName, obj);
		} catch (JSONException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 为表单添加属性
	 * @param formPropertyName
	 * @param obj
	 */
	public void addFormProperty(EnumFormPropertyName formPropertyName, Object obj) {
		try {
			this.formDefinitionJSONObject.put(formPropertyName.name(), obj);
		} catch (JSONException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 为表单添加属性,该接口最好不要使用,最好使用addFormProperty(EnumFormPropertyName formPropertyName, Object obj)
	 * @param formPropertyName
	 * @param obj
	 */
	public void addFormProperty(String formPropertyName, Object obj) {
		try {
			this.formDefinitionJSONObject.put(formPropertyName, obj);
		} catch (JSONException e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改字段的属性
	 * @param fieldName
	 * @param propertyName
	 * @param propertyValue
	 * @throws JSONException
	 */
	public void addFieldProperty(String fieldName, EnumFieldPropertyName propertyName, Object propertyValue)
			throws JSONException {
		/*JSONObject field = this.getFieldDefinition(fieldName);
		field.put(propertyName.name(), propertyValue);*/
		this.addFieldProperty(fieldName, propertyName.name(), propertyValue);
	}

	/**
	 * 添加或者修改字段的属性
	 * @param fieldName
	 * @param propertyName
	 * @param propertyValue
	 * @throws JSONException
	 */
	public void addFieldProperty(String fieldName, String propertyName, Object propertyValue) throws JSONException {
		JSONObject field = this.getFieldDefinition(fieldName);
		field.put(propertyName, propertyValue);
	}

	public void addSubformProperty(String formUuidOfSubform, EnumSubformPropertyName subformPropertyName,
			String subformPropertyValue) throws JSONException {
		JSONObject subform = this.getSubformDefinition(formUuidOfSubform);
		subform.put(subformPropertyName.name(), subformPropertyValue);
	}

	/**
	 * 获取主表表名
	 * @return
	 */
	public String getTblNameOfMainform() {
		return this.getFormPropertyOfStringType(EnumFormPropertyName.name);
	}

	/**
	 * 获取数据表对应的关系表
	 * @return
	 */
	public String getTblNameOfRelationTbl() {
		return this.getFormPropertyOfStringType(EnumFormPropertyName.relationTbl);
	}

	/**
	 * 获取主表的所有字段名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFieldNamesOfMainform() {
		return Arrays.asList(this.fieldDefinitionJSONObjects.keySet().toArray());
	}

	/**
	 * 判断定义中是否存在fieldName指定的字段
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean isFieldInDefinition(String fieldName) {
		List<String> fieldNames = this.getFieldNamesOfMainform();
		for (String fn : fieldNames) {
			if (fn.equalsIgnoreCase(fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定的从表是否存在于当前的定义中
	 * @param subformUuid
	 * @return
	 */
	public boolean isSubformInDefinition(String subformUuid) {
		List<String> formUuids = this.getFormUuidsOfSubform();
		for (String fn : formUuids) {
			if (fn.equalsIgnoreCase(subformUuid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某字段是不是附件
	 * @param fieldName
	 * @return
	 */
	public boolean isInputModeEqAttach(String fieldName) {
		String inputMode = this.getPropertyValueOfInputMode(fieldName);
		if (DyFormConfig.INPUTMODE_ACCESSORY1.equals(inputMode) || DyFormConfig.INPUTMODE_ACCESSORY2.equals(inputMode)
				|| DyFormConfig.INPUTMODE_ACCESSORY3.equals(inputMode)
				|| DyFormConfig.INPUTMODE_ACCESSORYIMG.equals(inputMode)
				|| DyFormConfig.INPUTMODE_ACCESSORY.equals(inputMode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某字段是不是日期
	 * @param fieldName
	 * @return
	 */
	public boolean isInputModeEqDate(String fieldName) {
		String inputMode = this.getPropertyValueOfInputMode(fieldName);
		if (DyFormConfig.INPUTMODE_DATE.equals(inputMode)) {
			return true;
		}
		return false;
	}

	/**
	 * 判定字段是不是数字类型
	 * @param fieldName
	 * @return
	 */
	public boolean isInputModeEqNumber(String fieldName) {
		String inputMode = this.getPropertyValueOfInputMode(fieldName);
		if (DyFormConfig.INPUTMODE_NUMBER.equals(inputMode)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字段是不是文本类型
	 * @param fieldName
	 * @return
	 */
	public boolean isInputModeEqText(String fieldName) {
		String inputMode = this.getPropertyValueOfInputMode(fieldName);
		if (DyFormConfig.INPUTMODE_Text.equals(inputMode)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取时间字段对应的时间格式
	 * @param fieldName
	 * @return
	 */
	public String getDateTimePatternByFieldName(String fieldName) {
		if (!this.isInputModeEqDate(fieldName)) {
			throw new RuntimeException("fieldName[" + fieldName + "] is not date type");
		}
		String contentFormat = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.contentFormat);

		return getDateTimePatternByContentFormat(contentFormat);
	}

	/**
	 * 根据数据库字段类型获取时间格式
	 * 
	 * @param dbDataType
	 * @return
	 */
	public static String getDateTimePatternByContentFormat(String contentFormat) {
		String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
		if (DyDateFomat.yearMonthDate.equals(contentFormat)) {
			dateTimePattern = "yyyy-MM-dd";
		} else if (DyDateFomat.yearMonthDateCn.equals(contentFormat)) {
			dateTimePattern = "yyyy年MM月dd日";
		} else if (DyDateFomat.yearCn.equals(contentFormat)) {
			dateTimePattern = "yyyy年";
		} else if (DyDateFomat.yearMonthCn.equals(contentFormat)) {
			dateTimePattern = "yyyy年MM月";
		} else if (DyDateFomat.monthDateCn.equals(contentFormat)) {
			dateTimePattern = "MM月dd日";
		} else if (DyDateFomat.year.equals(contentFormat)) {
			dateTimePattern = "yyyy";
		} else if (DyDateFomat.timeHour.equals(contentFormat)) {
			dateTimePattern = "HH";
		} else if (DyDateFomat.timeMin.equals(contentFormat)) {
			dateTimePattern = "HH:mm";
		} else if (DyDateFomat.timeSec.equals(contentFormat)) {
			dateTimePattern = "HH:mm:ss";
		} else if (DyDateFomat.dateTimeHour.equals(contentFormat)) {
			dateTimePattern = "yyyy-MM-dd HH";
		} else if (DyDateFomat.dateTimeMin.equals(contentFormat)) {
			dateTimePattern = "yyyy-MM-dd HH:mm";
		} else if (DyDateFomat.dateTimeSec.equals(contentFormat)) {
			dateTimePattern = "yyyy-MM-dd HH:mm:ss";
		}
		return dateTimePattern;
	}

	/**
	 * 判断值是否为创建时计算
	 * 值为系统分配 ,这些值是在保存时向系统请求分配 
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateBySystemWhenSave(String fieldName) {
		String valueCreateMethod = this
				.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.valueCreateMethod);
		return ValueCreateMethod.creatOperation.equals(valueCreateMethod);
	}

	/**
	 * 判断值是否为显示时计算
	 * 值为系统分配,这些值是在展示一张新表单时才向系统请求分配 
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateBySystemWhenShowNewForm(String fieldName) {
		String valueCreateMethod = this
				.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.valueCreateMethod);
		return ValueCreateMethod.showOperation.equals(valueCreateMethod);
	}

	/**
	 * 值为用户输入
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateByUser(String fieldName) {
		String valueCreateMethod = this
				.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.valueCreateMethod);
		return ValueCreateMethod.userImport.equals(valueCreateMethod);
	}

	/**
	 * 获取所有从表的定义uuid
	 * @return
	 */
	public List<String> getFormUuidsOfSubform() {
		return Arrays.asList(this.subformDefinitionJSONObjects.keySet().toArray());
	}

	/**
	 * 获取指定的从表的表名
	 * @param formUuidOfSubform
	 * @return
	 * @throws JSONException
	 */
	public String getTblNameOfSubform(String formUuidOfSubform) throws JSONException {
		return this.subformDefinitionJSONObjects.getJSONObject(formUuidOfSubform).getString(
				EnumSubformPropertyName.name.name());
	}

	/**
	 * 获取指定的从表被引用的字段名
	 * @param formUuidOfSubform
	 * @return
	 * @throws JSONException
	 */
	public List<String> getFieldNamesOfSubform(String formUuidOfSubform) throws JSONException {
		return Arrays.asList(this.subformDefinitionJSONObjects.getJSONObject(formUuidOfSubform)
				.getJSONObject(EnumSubformPropertyName.fields.name()).keySet().toArray());
	}

	public JSONObject getFieldDefinitions() {
		return this.fieldDefinitionJSONObjects;
	}

	public JSONObject getFormDefinition() {
		return this.formDefinitionJSONObject;
	}

	public JSONObject getSubformDefinitions() {
		return this.subformDefinitionJSONObjects;
	}

	@Override
	public String toString() {
		return this.formDefinitionJSONObject.toString();
	}

	/**
	 * 字段的对应的数据库类型是不是长整型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqLong(String fieldName) {
		String dbDataType = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqLong(dbDataType);

	}

	/**
	 * 判断字段数据库类型是否为int类型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqInt(String fieldName) {
		String dbDataType = this.getPropertyValueOfDbDataType(fieldName);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqInt(dbDataType);
	}

	/**
	 * 判断字段数据库类型是否为float类型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqFloat(String fieldName) {
		String dbDataType = this.getPropertyValueOfDbDataType(fieldName);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqFloat(dbDataType);
	}

	/**
	 * 判断字段数据库类型是否为float类型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqDouble(String fieldName) {
		String dbDataType = this.getPropertyValueOfDbDataType(fieldName);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqDouble(dbDataType);
	}

	/**
	 * 判断字段数据库类型是否为date类型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqDate(String fieldName) {
		String dbDataType = this.getPropertyValueOfDbDataType(fieldName);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqDate(dbDataType);
	}

	/**
	 * 判断字段数据库类型是否为date类型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqClob(String fieldName) {
		String dbDataType = this.getPropertyValueOfDbDataType(fieldName);
		return DyFormConfig.DbDataTypeUtils.isDbDataTypeEqClob(dbDataType);
	}

	/**
	 * 字段是否存在于数据表中
	 * @param fieldName
	 * @return
	 */
	public boolean isTblField(String fieldName) {
		return isFieldInDefinition(fieldName) || isSysTypeAsSystem(fieldName) || isRelationTblField(fieldName);
	}

	/**
	 * 在数据关系表中是否存在指定的字段
	 * @param fieldName
	 * @return
	 */
	public static boolean isRelationTblField(String fieldName) {
		return EnumRelationTblSystemField.value2EnumObj(fieldName) != null;
	}

	/**
	 * 在定义中是否存在指定的字段
	 * @param fieldName
	 * @return
	 */
	public boolean isFieldInDefinitionJson(String fieldName) {
		return this.isFieldInDefinition(fieldName);
	}

	/**
	 * 是不是系统字段 
	 * @param fieldName
	 * @return
	 */
	public static boolean isSysTypeAsSystem(String fieldName) {
		return EnumSystemField.value2EnumObj(fieldName) != null;
	}

	/**
	 * 字段是否只读
	 * @param fieldName
	 * @return
	 */
	public boolean isShowTypeAsReadOnly(String fieldName) {
		String propertyValue = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
		return DyShowType.readonly.equals(propertyValue);
	}

	/**
	 * 判断字段是否为可编辑
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean isShowTypeAsEditable(String fieldName) {
		String propertyValue = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
		return DyShowType.edit.equals(propertyValue);
	}

	/**
	 * 判断字段是否为隐藏
	 * @param fieldName
	 * @return
	 */
	public boolean isShowTypeAsHide(String fieldName) {
		String propertyValue = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
		return DyShowType.hide.equals(propertyValue);
	}

	/**
	 * 判断字段是不是显示为标签
	 * @param fieldName
	 * @return
	 */
	public boolean isShowTypeAsLabel(String fieldName) {
		String propertyValue = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
		return DyShowType.showAsLabel.equals(propertyValue);
	}

	private final String assistedShowType = "assistedShowType";

	private void setShowType(String fieldName, String showType) {
		if (this.isShowTypeAsHide(fieldName) && !DyShowType.hide.equals(showType)) {//字段隐藏时设置其他属性
			JSONObject field = this.getFieldDefinition(fieldName);
			if (field == null) {
				return;
			}
			try {
				field.put(assistedShowType, showType);
			} catch (JSONException e) {
			}
		} else {
			try {
				this.addFieldProperty(fieldName, EnumFieldPropertyName.showType, showType);
			} catch (JSONException e) {
				//e.printStackTrace();
			}
		}

	}

	/**
	 * 设置字段为只读
	 * 
	 * @param fieldName
	 */
	public void setShowTypeAsReadOnly(String fieldName) {
		this.setShowType(fieldName, DyShowType.readonly);

	}

	/**
	 * 设置字段为可编辑
	 * 
	 * @param fieldName
	 */
	public void setShowTypeAsEditable(String fieldName) {
		this.setShowType(fieldName, DyShowType.edit);
	}

	/**
	 * 如何描述该方法
	 * 
	 * @param fieldName
	 * @param isHide true为隐藏; false展示
	 */
	public void setShowTypeAsHide(String fieldName, boolean isHide) {
		if (isHide) {
			JSONObject field = this.getFieldDefinition(fieldName);
			if (field == null) {
				return;
			}
			try {
				field.put(assistedShowType,
						this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType));
			} catch (JSONException e) {
			}
			this.setShowType(fieldName, DyShowType.hide);

		} else {//展示 
			JSONObject field = this.getFieldDefinition(fieldName);
			if (field == null) {
				return;
			}
			try {
				if (!field.isNull(assistedShowType)) {
					String showType = (String) field.get(assistedShowType);
					//this.setShowType(fieldName, showType);
					this.addFieldProperty(fieldName, EnumFieldPropertyName.showType, showType);
				} else {
					if (this.isShowTypeAsHide(fieldName)) {
						this.setShowType(fieldName, DyShowType.edit);
					}

				}

			} catch (JSONException e) {
			}

		}

	}

	/**
	 * 设置字段为隐藏
	 * 
	 * @param fieldName
	 */
	public void setShowTypeAsDisable(String fieldName) {
		this.setShowType(fieldName, DyShowType.disabled);
	}

	/**
	 * 设置字段显示为标签
	 * @param fieldName
	 */
	public void setShowTypeAsLabel(String fieldName) {
		this.setShowType(fieldName, DyShowType.showAsLabel);
	}

	/**
	 * 
	 * 判断字段是不是必输
	 * @param fieldName
	 * @return
	 */
	public boolean isRequired(String fieldName) {
		JSONArray rules = this.getFieldPropertyOfJSONArrayType(fieldName, EnumFieldPropertyName.fieldCheckRules);
		if (rules == null || rules.length() == 0) {
			return false;
		}
		for (int i = 0; i < rules.length(); i++) {
			try {
				JSONObject rule = rules.getJSONObject(i);
				String value = (String) rule.get(EnumFieldPropertyName_fieldCheckRules.value.name());
				if (EnumDyCheckRule.notNull.getRuleKey().equals(value)) {
					return true;
				}
			} catch (JSONException e) {
				////e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 设置必输的检验字段 
	 * @param fieldName
	 */
	public void setRequired(String fieldName, boolean required) {
		JSONArray rules = this.getFieldPropertyOfJSONArrayType(fieldName, EnumFieldPropertyName.fieldCheckRules);
		if (rules == null) {
			rules = new JSONArray();
			try {
				this.addFieldProperty(fieldName, EnumFieldPropertyName.fieldCheckRules, rules);
			} catch (JSONException e) {
				////e.printStackTrace();
			}
		}
		boolean isReqired = false;
		int index = 0;
		for (index = 0; index < rules.length(); index++) {
			JSONObject rule;
			try {
				rule = rules.getJSONObject(index);
				if (EnumDyCheckRule.notNull.getRuleKey().equals(
						rule.getString(EnumFieldPropertyName_fieldCheckRules.value.name()))) {
					isReqired = true;
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

		if (required) {
			if (isReqired) {
				return;
			} else {
				Map<String, String> rule = new HashMap<String, String>();
				rule.put(EnumFieldPropertyName_fieldCheckRules.value.name(), EnumDyCheckRule.notNull.getRuleKey());
				rule.put(EnumFieldPropertyName_fieldCheckRules.label.name(), EnumDyCheckRule.notNull.getRuleLabel());
				rules.put(rule);
			}
			return;
		} else {
			rules.remove(index);
		}

	}

	/**
	 * 启动签名与否
	 * 
	 * @param enable
	 */
	public void setSignature(boolean enable) {
		this.addFormProperty(EnumFormPropertyName.enableSignature, enable ? EnumSignature.enable.getValue()
				: EnumSignature.disable.getValue());
	}

	/**
	 * 获取控件类型
	 * @param fieldName
	 * @return
	 */
	public String getPropertyValueOfInputMode(String fieldName) {
		return this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.inputMode);
	}

	/**
	 * 获取数据类型
	 * @param fieldName
	 * @return
	 */
	public String getPropertyValueOfDbDataType(String fieldName) {
		return this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType);
	}

	/**
	 * 获取值的计算方式
	 * @param fieldName
	 * @return
	 */
	public String getPropertyValueOfValueCreateMethod(String fieldName) {
		return this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.valueCreateMethod);
	}

	/**
	 * 判断字段值是不是key_value的形式
	 * @param fieldName
	 * @return
	 */
	public boolean isValueAsMap(String fieldName) {
		String inputType = this.getPropertyValueOfInputMode(fieldName);
		if (DyFormConfig.INPUTMODE_ORGSELECT.equals(inputType)
				|| DyFormConfig.INPUTMODE_ORGSELECTSTAFF.equals(inputType)
				|| DyFormConfig.INPUTMODE_ORGSELECTDEPARTMENT.equals(inputType)
				|| DyFormConfig.INPUTMODE_ORGSELECTSTADEP.equals(inputType)
				|| DyFormConfig.INPUTMODE_ORGSELECTADDRESS.equals(inputType)
				|| DyFormConfig.INPUTMODE_TREESELECT.equals(inputType)
				|| DyFormConfig.INPUTMODE_RADIO.equals(inputType) || DyFormConfig.INPUTMODE_CHECKBOX.equals(inputType)
				|| DyFormConfig.INPUTMODE_SELECTMUTILFASE.equals(inputType)
				|| DyFormConfig.INPUTMODE_JOB.equals(inputType)) {
			return true;
		}
		return false;
	}

	/**
	 * 为定义中的所有字段添加oldName属性且值为name对应的值
	 * 将字段的oldName设置为name
	 * @throws JSONException 
	 */
	public void createOldName4AllFields() throws JSONException {
		Iterator<String> it = this.getFieldNamesOfMainform().iterator();
		while (it.hasNext()) {
			String fieldName = it.next();
			this.addFieldProperty(fieldName, EnumFieldPropertyName.oldName,
					this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.name));
		}
	}

	/**
	 * 获取指定字段用于保存真实值的真实字段
	 * @param fieldName
	 * @return
	 */
	public String getAssistedFieldNameRealValue(String fieldName) {
		JSONObject jo = this.getFieldPropertyOfJSONType(fieldName, EnumFieldPropertyName.realDisplay);
		if (jo == null) {
			return null;
		}

		try {
			return jo.getString("real");
		} catch (JSONException e) {
			return null;
		}

	}

	/**
	 * 获取指定字段用于保存显示值的显示字段
	 * @param fieldName
	 * @return
	 */
	public String getAssistedFieldNameDisplayValue(String fieldName) {
		JSONObject jo = this.getFieldPropertyOfJSONType(fieldName, EnumFieldPropertyName.realDisplay);
		if (jo == null) {
			return null;
		}

		try {
			return jo.getString("display");
		} catch (JSONException e) {
			return null;
		}
	}

	/**
	 * 删除字段
	 * @param fieldName
	 */
	public void removeFieldByFieldName(String fieldName) {
		this.fieldDefinitionJSONObjects.remove(fieldName);
	}

	/**
	 * 批量删除字段
	 * 
	 * @param fieldNames
	 */
	public void removeFields(List<String> fieldNames) {
		for (String fieldName : fieldNames) {
			this.removeFieldByFieldName(fieldName);
		}
	}

	/**
	 * 删除指定的从表
	 * 
	 * @param formUuid
	 */
	public void removeSubform(String formUuid) {
		this.subformDefinitionJSONObjects.remove(formUuid);
	}

	/**
	 * 批量删除从表
	 * 
	 * @param formUuids
	 */
	public void removeSubforms(List<String> formUuids) {
		for (String formUuid : formUuids) {
			this.removeSubform(formUuid);
		}
	}

	/**
	 * 获取数据表的系统字段
	 * 
	 * @return
	 */
	public static List<String> getSysFieldNames() {
		List<String> fieldNames = new ArrayList<String>();
		for (EnumSystemField sysField : EnumSystemField.values()) {
			fieldNames.add(sysField.name());
		}
		return fieldNames;
	}

	/**
	 * 用户自定义字段
	 * 
	 * @param fieldName
	 * @return
	 */
	public boolean isSysTypeAsCustom(String fieldName) {
		String sysType = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.sysType);
		return sysType == null ? false : DyFieldSysType.custom == Integer.parseInt(this.getFieldPropertyOfStringType(
				fieldName, EnumFieldPropertyName.sysType));
	}

	/**
	 * 根据指定的applyTo获取对应的字段名
	 * 
	 * @param applyTo
	 * @return
	 */
	public String getFieldNameByApplyTo(String applyTo) {
		if (applyTo2Field == null || applyTo2Field.size() == 0) {
			applyTo2Field = new HashMap<String, String>();
			for (String fieldName : this.getFieldNamesOfMainform()) {
				String applyTotmp = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.applyTo);
				applyTo2Field.put(fieldName.toLowerCase(), fieldName);
				if (StringUtils.isNotBlank(applyTotmp)) {
					String[] tos = StringUtils.split(applyTotmp, Separator.SEMICOLON.getValue());
					for (String to : tos) {
						applyTo2Field.put(to.toLowerCase(), fieldName);
					}
				}
			}
		}

		return applyTo2Field.get(applyTo.toLowerCase());
	}

	public boolean hasFieldNameByAppyTo(String applyTo) {
		return this.getFieldNameByApplyTo(applyTo) == null ? false : true;
	}

	/**
	 * 
	 * 根据指定的applyTo获取对应的字段名的控件类型
	 * @param applyTo
	 * @return
	 */
	public String getFieldType(String applyTo) {
		if (inputMode2Field == null || inputMode2Field.size() == 0) {
			inputMode2Field = new HashMap<String, String>();

			for (String fieldName : this.getFieldNamesOfMainform()) {
				String inputMode = this.getPropertyValueOfInputMode(fieldName);
				String applyTotmp = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.applyTo);
				inputMode2Field.put(fieldName.toLowerCase(), inputMode);
				if (StringUtils.isNotBlank(inputMode)) {
					String[] tos = StringUtils.split(applyTotmp, Separator.COMMA.getValue());
					for (String to : tos) {
						inputMode2Field.put(fieldName.toLowerCase(), inputMode);
					}
				}
			}
		}

		return inputMode2Field.get(applyTo.toLowerCase());
	}

	public void loadDefaultFormData() {
		DyFormApiFacade dyFormApiFacade = (DyFormApiFacade) ApplicationContextHolder.getBean("dyFormApiFacade");
		final String DEFAULTFORMDATA = "defaultFormData";
		Map<String, Object> defaultFormData;
		try {
			defaultFormData = dyFormApiFacade.getDefaultFormData(this
					.getFormPropertyOfStringType(EnumFormPropertyName.uuid));
			if (defaultFormData != null)
				this.addFormProperty(DEFAULTFORMDATA, defaultFormData);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public String getInputMode(String fieldName) {
		return this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.inputMode);
	}

	public void loadFieldDictionary() {
		JSONObject fieldConfigs = this.getFieldDefinitions();
		if (fieldConfigs == null) {
			return;
		}

		DataDictionaryService dataDictionaryService = (DataDictionaryService) ApplicationContextHolder
				.getBean("dataDictionaryService");
		for (Object key : fieldConfigs.keySet()) {
			String fieldName = (String) key;
			String inputMode = this.getInputMode((String) key);
			if (!(DyFormConfig.INPUTMODE_SELECTMUTILFASE.equals(inputMode)
					|| DyFormConfig.INPUTMODE_CHECKBOX.equals(inputMode) || DyFormConfig.INPUTMODE_RADIO
						.equals(inputMode))) {
				continue;
			}

			String optionDataSource = this.getFieldPropertyOfStringType(fieldName,
					EnumFieldPropertyName.optionDataSource);
			if (DyFormConfig.DyDataSourceType.dataDictionary.equals(optionDataSource)) {//从数据字段取值,这里取得数据字段树 
				String dictCode = this.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dictCode);
				if (dictCode != null && dictCode.indexOf(":") != -1) {
					dictCode = dictCode.split(":")[0];
				}
				List<DataDictionary> dicts = new ArrayList<DataDictionary>();
				if (dictCode != null && dictCode.length() > 0) {
					List<DataDictionary> dictsx = dataDictionaryService.getDataDictionariesByType(dictCode);
					if (dictsx != null && dictsx.size() > 0) {
						dicts.addAll(dictsx);
					}
				}

				this.setFieldDictionaryOptionSet(fieldName, dicts);

			}

		}

	}

	/**
	 * 添加数据字典集合
	 * 
	 */
	public void setFieldDictionaryOptionSet(String fieldName, List<DataDictionary> dicts) {

		try {
			this.addFieldProperty(fieldName, "optionSet", dicts);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加职位的数据字典
	 */
	public void loadFieldJobDictionary() {
		JSONObject fieldConfigs = this.getFieldDefinitions();
		if (fieldConfigs == null) {
			return;
		}

		for (Object key : fieldConfigs.keySet()) {
			String fieldName = (String) key;
			String inputMode = this.getInputMode((String) key);
			if (!(DyFormConfig.INPUTMODE_JOB.equals(inputMode))) {
				continue;
			}

			UserDetails userDetail = SpringSecurityUtils.getCurrentUser();
			HashMap<String, Object> majorJo = userDetail.getMajorJob();
			List<HashMap<String, Object>> otherJobs = userDetail.getOtherJobs();
			List<HashMap<String, Object>> jobs = new ArrayList<HashMap<String, Object>>();
			if (majorJo != null) {
				jobs.add(majorJo);
			}

			if (otherJobs != null) {
				jobs.addAll(otherJobs);
			}

			List<DataDictionary> dicts = new ArrayList<DataDictionary>();
			for (HashMap<String, Object> job : jobs) {
				DataDictionary dict = new DataDictionary();
				dict.setCode((String) job.get("id"));
				dict.setName((String) job.get("fullJobName"));
				dicts.add(dict);
			}

			this.setFieldDictionaryOptionSet(fieldName, dicts);

		}

	}

	public void setBlockHide(String blockCode, boolean isHide) {
		if (blockCode == null || blockCode.trim().length() == 0) {
			return;
		}
		if (this.blockDefinitionJSONObjects.isNull(blockCode)) {
			return;
		}
		try {
			this.blockDefinitionJSONObjects.getJSONObject(blockCode).put("hide", isHide);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getBlockDefinitionJSONObjects() {
		return blockDefinitionJSONObjects;
	}

	public void hideSubformOperateBtn(final String formUuid) {
		try {
			this.addSubformProperty(formUuid, EnumSubformPropertyName.hideButtons,
					EnumHideSubFormOperateBtn.hide.getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void showSubformOperateBtn(String formUuid) {
		try {
			this.addSubformProperty(formUuid, EnumSubformPropertyName.hideButtons,
					EnumHideSubFormOperateBtn.show.getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
