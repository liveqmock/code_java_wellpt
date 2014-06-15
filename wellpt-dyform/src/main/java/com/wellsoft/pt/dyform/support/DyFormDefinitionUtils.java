package com.wellsoft.pt.dyform.support;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.support.DyFormConfig.DyDateFomat;
import com.wellsoft.pt.dyform.support.DyFormConfig.ValueCreateMethod;

import edu.emory.mathcs.backport.java.util.Arrays;

public class DyFormDefinitionUtils {
	private final DyFormDefinition dyFormDefinition;
	private final JSONObject formDefinitionJSONObject;
	private final JSONObject fieldDefinitionJSONObjects;
	private final JSONObject subformDefinitionJSONObjects;

	public DyFormDefinitionUtils(final DyFormDefinition dyFormDefinition) throws JSONException {
		this.dyFormDefinition = dyFormDefinition;
		this.formDefinitionJSONObject = new JSONObject(dyFormDefinition.getDefinitionJson());
		this.fieldDefinitionJSONObjects = this.formDefinitionJSONObject.getJSONObject("fields");
		this.subformDefinitionJSONObjects = this.formDefinitionJSONObject.getJSONObject("subforms");
	}

	/**
	 * 获取主表表名
	 * @return
	 */
	public String getTblNameOfMainform() {
		return this.dyFormDefinition.getName();
	}

	/**
	 * 获取字段的某属性的值
	 * @param fieldName
	 * @param propertyName
	 * @return
	 */
	public JSONObject getValueOfJSONObject4FieldProperty(String fieldName, String propertyName) {
		JSONObject fieldDefinitionJSONObject;
		JSONObject propertyDefinitionJsonObject;
		try {
			fieldDefinitionJSONObject = this.fieldDefinitionJSONObjects.getJSONObject(fieldName);
			propertyDefinitionJsonObject = fieldDefinitionJSONObject.getJSONObject(propertyName);
		} catch (JSONException e) {
			return null;
		}
		return propertyDefinitionJsonObject;
	}

	/**
	 * 获取字段的某属性的值
	 * @param fieldName
	 * @param propertyName
	 * @return
	 */
	public Object getValueOfObject4FieldProperty(String fieldName, String propertyName) {
		JSONObject fieldDefinitionJSONObject;
		try {
			fieldDefinitionJSONObject = this.fieldDefinitionJSONObjects.getJSONObject(fieldName);
			return fieldDefinitionJSONObject.get(propertyName);
		} catch (JSONException e) {
			return null;
		}
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
			if (fn.equals(fieldName)) {
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
		String inputType = (String) this.getValueOfObject4FieldProperty(fieldName, "inputMode");
		if (DyFormConfig.INPUTMODE_ACCESSORY1.equals(inputType) || DyFormConfig.INPUTMODE_ACCESSORY2.equals(inputType)
				|| DyFormConfig.INPUTMODE_ACCESSORY3.equals(inputType)
				|| DyFormConfig.INPUTMODE_TEXTBODY.equals(inputType)) {
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
		String inputMode = (String) this.getValueOfObject4FieldProperty(fieldName, "inputMode");
		if (DyFormConfig.INPUTMODE_DATE.equals(inputMode)) {
			return true;
		}
		return false;
	}

	public boolean isInputModeEqNumber(String fieldName) {
		String inputMode = (String) this.getValueOfObject4FieldProperty(fieldName, "inputMode");
		if (DyFormConfig.INPUTMODE_NUMBER.equals(inputMode)) {
			return true;
		}
		return false;
	}

	public boolean isInputModeEqText(String fieldName) {
		String inputMode = (String) this.getValueOfObject4FieldProperty(fieldName, "inputMode");
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
		String contentFormat = (String) this.getValueOfObject4FieldProperty(fieldName, "contentFormat");

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
		} else if (DyDateFomat.yearMonthDate.equals(contentFormat)) {
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

		///String yearMonthDate = "1";//当前日期(2000-01-01)
		//String yearMonthDateCn = "2";//当前日期(2000年1月1日)
		//String yearCn = "3";//当前日期(2000年)
		//String yearMonthCn = "4";//当前日期(2000年1月)
		//String monthDateCn = "5";//当前日期(1月1日)
		//String weekCn = "6";//当前日期(星期一)
		//String year = "7";//当前年份(2000)
		//String timeHour = "8";//当前时间(12)
		//String timeMin = "9";//当前时间(12:00)
		//String TimeSec = "10";//当前时间(12:00:00)
		//String dateTimeHour = "11";//日期到时 当前日期时间(2000-01-01 12)
		//String dateTimeMin = "12";//日期到分 当前日期时间(2000-01-01 12:00)
		//String dateTimeSec = "13";//日期到秒 当前日期时间(2000-01-01 12:00:00)
	}

	/**
	 * 值为系统分配 ,这些值是在保存时向系统请求分配 
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateBySystemWhenSave(String fieldName) {
		String valueCreateMethod = (String) this.getValueOfObject4FieldProperty(fieldName, "valueCreateMethod");
		return ValueCreateMethod.creatOperation.equals(valueCreateMethod);
	}

	/**
	 * 值为系统分配,这些值是在展示一张新表单时才向系统请求分配 
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateBySystemWhenShowNewForm(String fieldName) {
		String valueCreateMethod = (String) this.getValueOfObject4FieldProperty(fieldName, "valueCreateMethod");
		return ValueCreateMethod.showOperation.equals(valueCreateMethod);
	}

	/**
	 * 值为用户输入
	 * @param fieldName
	 * @return
	 */
	public boolean isValueCreateByUser(String fieldName) {
		String valueCreateMethod = (String) this.getValueOfObject4FieldProperty(fieldName, "valueCreateMethod");
		return ValueCreateMethod.userImport.equals(valueCreateMethod);
	}

	/**
	 * 字段的对应的数据库类型是不是长整型
	 * @param fieldName
	 * @return
	 */
	public boolean isDbDataTypeEqLong(String fieldName) {
		String dbDataType = (String) this.getValueOfObject4FieldProperty(fieldName, "dbDataType");
		if (DyFormConfig.DbDataType._long.equals(dbDataType)) {
			return true;
		}
		return false;
	}

	public List<String> getFormUuidsOfSubform() {
		return Arrays.asList(this.subformDefinitionJSONObjects.keySet().toArray());
	}

	public String getTblNameOfSubform(String formUuidOfSubform) throws JSONException {
		return this.subformDefinitionJSONObjects.getJSONObject(formUuidOfSubform).getString("name");
	}

	public List<String> getFieldNamesOfSubform(String formUuidOfSubform) throws JSONException {
		return Arrays.asList(this.subformDefinitionJSONObjects.getJSONObject(formUuidOfSubform).getJSONObject("fields")
				.keySet().toArray());
	}

	public JSONObject getJSONObject4FieldDefinition() {
		return this.fieldDefinitionJSONObjects;
	}

	public JSONObject getJSONObject4FormDefinition() {
		return this.formDefinitionJSONObject;
	}

	public boolean isDbDataTypeEqInt(String fieldName) {
		String dbDataType = (String) this.getValueOfObject4FieldProperty(fieldName, "dbDataType");
		if (DyFormConfig.DbDataType._int.equals(dbDataType)) {
			return true;
		}
		return false;
	}

	public boolean isDbDataTypeEqFloat(String fieldName) {
		String dbDataType = (String) this.getValueOfObject4FieldProperty(fieldName, "dbDataType");
		if (DyFormConfig.DbDataType._float.equals(dbDataType)) {
			return true;
		}
		return false;
	}

}
