package com.wellsoft.pt.dyform.support;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumDySysVariable;
import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName;
import com.wellsoft.pt.dyform.support.enums.EnumValidateCode;
import com.wellsoft.pt.repository.entity.LogicFileInfo;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;

public class DyFormDataUtils {
	private DyFormDefinitionJSON dUtils;
	private Map<String, Object> formDataColMap = null;
	private MongoFileService mongoFileService = (MongoFileService) ApplicationContextHolder.getBean("mongoFileService");
	DyFormApiFacade dyFormApiFacade = (DyFormApiFacade) ApplicationContextHolder.getBean("dyFormApiFacade");

	public DyFormDataUtils(String formUuid, Map<String, Object> formDataColMap) {
		this.formDataColMap = formDataColMap;
		this.dUtils = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
	}

	public DyFormDataUtils(String formUuid, Map<String, Object> formDataColMap, DyFormDefinitionJSON dUtils) {
		this.formDataColMap = formDataColMap;
		if (dUtils == null) {
			this.dUtils = this.dyFormApiFacade.getFormDefinition(formUuid).getJsonHandler();
		} else {
			this.dUtils = dUtils;
		}

	}

	private void processFileField(String fieldName) {
		Object fileInfoObjs = getValueFromMap(fieldName, this.formDataColMap);

		//formDataColMap.put(fieldName, null);

		/*if (fileInfoObjs instanceof List) {
			if (((List) (fileInfoObjs)).size() == 0) {
				formDataColMap.put(fieldName, null);
			 
			}
		}*/

		String dataUuid = (String) getValueFromMap("uuid", this.formDataColMap);
		List<String> fileIds = null;
		if (fileInfoObjs == null) {
			fileIds = new ArrayList<String>();
		} else {
			fileIds = getFileIds(fileInfoObjs);
		}

		fileOperate(dataUuid, fieldName, fileIds);
		///formDataColMap.put(fieldName, null);

	}

	/**
	 * 处理从formdata中由于大小写问题取不到值
	 * 
	 * @param fieldName
	 * @param formDatas
	 * @return
	 */
	public static Object getValueFromMap(String fieldName, Map<String, Object> formDatas) {
		Object value = formDatas.get(fieldName);
		if (value == null) {//可能大小写问题造成找不到对应的字段值
			value = formDatas.get(fieldName.toLowerCase());
			if (value == null) {
				value = formDatas.get(fieldName.toUpperCase());
			}
		}
		return value;
	}

	private static List<Object> getFiles(Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			return null;
		}
		return (List<Object>) value;
	}

	/**
	 * 获取从前台统一上传控件上传过来的文件ID列表
	 * @return
	 */
	public static List<String> getFileIds(Object value) {
		List<Object> files = getFiles(value);//获取从前台传送过来的文件列表信息 
		if (files == null) {
			files = new ArrayList<Object>();
		}

		List<String> fileIDs = new ArrayList<String>();
		for (Object obj : files) {
			if (obj instanceof LogicFileInfo) {
				LogicFileInfo file = (LogicFileInfo) obj;
				fileIDs.add(file.getFileID());
			} else {
				Map<String, String> fileInfo = (Map<String, String>) obj;
				fileIDs.add(fileInfo.get("fileID"));
			}
		}
		return fileIDs;
	}

	private void fileOperate(String folderID, String fieldName, List<String> fileIDListFromPage) {
		if (fieldName.equals("DJFJ")) {
			System.out.println();
		}
		List<String> newFileIDList = new ArrayList<String>(); //该列表中的fileID都是要push到数据库里面的
		boolean folderExist = mongoFileService.isFolderExist(folderID);

		if (!folderExist) {//新增
			newFileIDList.addAll(fileIDListFromPage);
		} else {//更新 
			List<MongoFileEntity> dbFiles = mongoFileService.getFilesFromFolder(folderID, fieldName);//取出存放在数据库里面的文件
			if (dbFiles == null) {
				dbFiles = new ArrayList<MongoFileEntity>();
			}
			Iterator<MongoFileEntity> it1 = dbFiles.iterator();
			while (it1.hasNext()) {
				MongoFileEntity dbFile = it1.next();
				String dbFileID = dbFile.getId();
				boolean isExist = false;
				Iterator<String> it2 = fileIDListFromPage.iterator();
				while (it2.hasNext()) {
					String fileId = it2.next();
					if (dbFileID.equals(fileId)) {//如果该文件在数据库中已存在,则不用进行再处理
						it2.remove();
						isExist = true;
						break;
					}
				}

				if (!isExist) {//如果从页面传进来的文件没有该文件，则表示该文件是要被删除的
					mongoFileService.popFileFromFolder(folderID, dbFileID);
				}
			}
			newFileIDList.addAll(fileIDListFromPage);

		}

		//将要保存到数据库的文件push到数据 库中
		if (newFileIDList.size() > 0) {
			mongoFileService.pushFilesToFolder(folderID, newFileIDList, fieldName);
		}
	}

	private void parseDateField(String fieldName) throws ParseException {
		String datePattern = dUtils.getDateTimePatternByFieldName(fieldName);
		Object value = getValueFromMap(fieldName, this.formDataColMap);
		formDataColMap.put(
				fieldName,
				convertData2DbType(dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType),
						value, null, datePattern));
	}

	private void parseNumberField(String fieldName) throws ParseException {
		Object value = getValueFromMap(fieldName, this.formDataColMap);
		String defaultValue = dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.defaultValue);
		/*if (defaultValue == null || defaultValue.trim().length() == 0) {
			defaultValue = "0";
		}*/
		this.formDataColMap.put(
				fieldName,
				convertData2DbType(dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType),
						value, defaultValue));
	}

	private void parseStringField(String fieldName) throws ParseException {
		Object value = getValueFromMap(fieldName, this.formDataColMap);
		this.formDataColMap.put(
				fieldName,
				convertData2DbType(dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType),
						value, null));
	}

	public void doProcessValueCreateBySystem(String fieldName) {
		if (this.dUtils.isInputModeEqDate(fieldName)) {
			formDataColMap.put(fieldName, new Date());
		} else if (this.dUtils.isInputModeEqText(fieldName)) {
			//String valueObj = (String) formDataColMap.get(fieldName);
			String value = this.replaceSystemVariable(fieldName);
			formDataColMap.put(fieldName, value);
		}
	}

	private final String substitution = "S_U_B_S_T_I_T_U_T_I_O_N";

	private String replaceSystemVariable(String fieldName) {
		String defaultValue = dUtils.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.defaultValue);
		Pattern p = Pattern.compile(".*(\\{[^\\}]+\\}).*");
		Matcher matcher = p.matcher(defaultValue);
		Map<String, String> notMatchMap = new HashMap<String, String>();
		int index = 0;
		while (matcher.find()) {
			String datePattern = null;
			for (int i = 0; i < matcher.groupCount(); i++) {
				String pattern = matcher.group(i + 1);
				EnumDySysVariable systeVar = EnumDySysVariable.key2EnumObj(pattern);
				if (systeVar == null) {//非系统定义的表达式
					String substitution = this.substitution + index;
					notMatchMap.put(substitution, pattern);
					defaultValue = defaultValue.replace(pattern, substitution);
					index++;
				} else {
					defaultValue = defaultValue.replace(systeVar.getKey(), systeVar.getValue());
				}
			}
			matcher = p.matcher(defaultValue);
		}
		if (!notMatchMap.isEmpty()) {
			Iterator<String> it = notMatchMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				defaultValue = defaultValue.replace(key, notMatchMap.get(key));
			}
		}
		return defaultValue;
	}

	public void doProcessValueCreateByUser(String fieldName) throws ParseException {
		if (dUtils.isInputModeEqAttach(fieldName)) {//附件字段
			this.processFileField(fieldName);//处理文件字段 
		} else if (dUtils.isInputModeEqDate(fieldName)) {//日期字段
			this.parseDateField(fieldName);
		} else if (dUtils.isInputModeEqText(fieldName)) {//文本字段 
			this.parseStringField(fieldName);
		} else if (dUtils.isInputModeEqNumber(fieldName)) {
			this.parseNumberField(fieldName);
		}
	}

	/** 
	 * 数据验证
	 */
	public EnumValidateCode validate(String fieldName) {
		Object valueObj = getValueFromMap(fieldName, this.formDataColMap);
		if (dUtils.isInputModeEqNumber(fieldName)) {
			String value = (String) valueObj;
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException e) {
				throw new NumberFormatException(fieldName + "=" + value);
			}
		} else if (dUtils.isDbDataTypeEqDate(fieldName)) {
			if (!this.isDate((String) valueObj)) {
				return EnumValidateCode.INVALID_FORMAT_DATE;
			}
		}
		/*	if (!StringUtils.isNumeric(value)) {
				throw new NumberFormatException(fieldName + "=" + value);
			}*/
		return EnumValidateCode.SUCESS;
	}

	/**
	 *设置指定字段的值 
	 * @param fieldName
	 * @param data
	 */
	public void setValue(String fieldName, Object data) {
		this.formDataColMap.put(fieldName, data);
	}

	public static Object convertData2DbType(String dbDataType, Object data, Object... paramters) throws ParseException {

		if (data == null) {//如果值为空则返回默认值
			if (paramters != null && paramters.length > 0) {
				data = paramters[0];//如果值为空则使用默认值 
			} else {
				return null;
			}
		}

		if (data instanceof String && !DyFormConfig.DbDataTypeUtils.isDbDataTypeEqString(dbDataType)
				&& ((String) data).trim().length() == 0) {//对于空字符串的处理
			/*if (DyFormConfig.DbDataTypeUtils.isDbDataTypeAsNumber(dbDataType)) {
				if (paramters != null && paramters.length > 0) {
					data = paramters[0];
					if()
				} else {
					return null;
				}
			} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeAsNumber(dbDataType)) {
				return null;
			} else {
				return null;
			}*/
			return null;

		}

		if (DyFormConfig.DbDataTypeUtils.isDbDataTypeEqDate(dbDataType)) {
			return convertData2Date(data, paramters);
		} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeEqFloat(dbDataType)) {
			return convertData2Float(data);
		} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeEqDouble(dbDataType)) {
			return convertData2Double(data);
		} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeEqInt(dbDataType)) {
			return convertData2Int(data);
		} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeEqLong(dbDataType)) {
			return convertData2Long(data);
		} else if (DyFormConfig.DbDataTypeUtils.isDbDataTypeAsString(dbDataType)) {
			return convertData2String(data);
		} else {

		}
		return data;
	}

	private static Object convertData2String(Object data) {
		String datePattern = "yyyy-MM-dd HH:mm:ss";
		if (data instanceof BigDecimal) {
			return ((BigDecimal) data).toString();
		} else if (data instanceof String) {
			return data;
		} else if (data instanceof Integer) {
			return Integer.toString((Integer) data);
		} else if (data instanceof Long) {
			return Long.toString((Long) data);
		} else if (data instanceof Float) {
			return Float.toString((Float) data);
		} else if (data instanceof Date || data instanceof java.sql.Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			sdf.format(data);
		}
		return data.toString();

	}

	private static Object convertData2Long(Object data) {
		if (data instanceof BigDecimal) {
			return ((BigDecimal) data).intValue();
		} else if (data instanceof String) {
			return Long.parseLong((String) data);
		} else if (data instanceof Integer) {
			return Long.parseLong(Integer.toString((Integer) data));
		} else if (data instanceof Long) {
			return data;
		} else if (data instanceof Double) {
			return (long) ((Double) data).doubleValue();
		} else if (data instanceof Float) {
			return (long) ((Float) data).floatValue();
		}
		return data;

	}

	private static Object convertData2Int(Object data) {
		if (data instanceof BigDecimal) {
			return ((BigDecimal) data).intValue();
		} else if (data instanceof String) {
			return Integer.parseInt((String) data);
		} else if (data instanceof Integer) {
			return data;
		} else if (data instanceof Long) {
			return Integer.parseInt(Long.toString((Long) data));
		} else if (data instanceof Double) {
			return (int) ((Double) data).doubleValue();
		} else if (data instanceof Float) {
			return (int) ((Float) data).floatValue();
		}
		return data;

	}

	public static void main(String[] args) {
		Double d = 2.3;
		System.out.println((int) d.doubleValue());
	}

	private static Object convertData2Float(Object data) {
		if (data instanceof BigDecimal) {
			return ((BigDecimal) data).floatValue();
		} else if (data instanceof String) {
			return Float.parseFloat((String) data);
		} else if (data instanceof Float) {
			return data;
		} else if (data instanceof Integer) {
			return Float.parseFloat(Integer.toString((Integer) data));
		} else if (data instanceof Long) {
			return Float.parseFloat(Long.toString((Long) data));
		} else if (data instanceof Double) {
			return (float) ((Double) data).doubleValue();
		}
		return data;

	}

	private static Object convertData2Double(Object data) {
		if (data instanceof BigDecimal) {
			return ((BigDecimal) data).doubleValue();
		} else if (data instanceof String) {
			return Double.parseDouble((String) data);
		} else if (data instanceof Double) {
			return data;
		} else if (data instanceof Integer) {
			return Double.parseDouble(Integer.toString((Integer) data));
		} else if (data instanceof Long) {
			return Double.parseDouble(Long.toString((Long) data));
		} else if (data instanceof Float) {
			return Double.parseDouble(Float.toString((Float) data));
		}
		return data;
	}

	private static Object convertData2Date(Object data, Object... parameters) throws ParseException {
		String pattern1 = "yyyy-MM-dd HH:mm:ss";
		String pattern2 = "yyyy-MM-dd HH:mm";
		String pattern3 = "yyyy-MM-dd HH";
		String pattern4 = "yyyy-MM-dd";
		String pattern5 = "HH:mm:ss";
		String pattern6 = "HH:mm";
		String pattern7 = "HH";

		if (data instanceof Date || data instanceof java.sql.Date) {
			return data;
		} else if (data instanceof String) {
			SimpleDateFormat sdf = new SimpleDateFormat();
			String pattern = "";
			if (parameters == null || parameters.length < 2) {
				pattern = pattern1;
			} else {
				pattern = (String) parameters[1];
			}

			try {
				sdf.applyPattern(pattern);
				Date date = sdf.parse((String) data);
				return date;
			} catch (ParseException e) {
				try {
					sdf.applyPattern(pattern1);
					Date date = sdf.parse((String) data);
					return date;
				} catch (ParseException e1) {
					try {
						sdf.applyPattern(pattern2);
						Date date = sdf.parse((String) data);
						return date;
					} catch (ParseException e2) {
						try {
							sdf.applyPattern(pattern3);
							Date date = sdf.parse((String) data);
							return date;
						} catch (ParseException e3) {
							try {
								sdf.applyPattern(pattern4);
								Date date = sdf.parse((String) data);
								return date;
							} catch (ParseException e4) {
								throw e4;
							}
						}
					}

				}
			}
		} else {
			return data;
		}
	}

	public void setValueByApplyTo(String applyTo, Object data) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName != null)
			this.setValue(fieldName, data);
	}

	/**
	 * 获取指定的字段的值
	 * 
	 * @param fieldName
	 * @return
	 */
	public Object getValue(String fieldName) {
		Object v = getValueFromMap(fieldName, this.formDataColMap);
		return v;
	}

	/**
	 * 获取指定的字段的值
	 * 
	 * @param fieldName
	 * @return
	 */
	public List<String> getValueOfFileIds(String fieldName) {
		Object fileInfoObjs = getValueFromMap(fieldName, this.formDataColMap);
		formDataColMap.put(fieldName, null);

		/*if (fileInfoObjs instanceof List) {
			if (((List) (fileInfoObjs)).size() == 0) {
				formDataColMap.put(fieldName, null);
			 
			}
		}*/

		String dataUuid = (String) getValueFromMap("uuid", this.formDataColMap);
		List<String> fileIds = null;
		if (fileInfoObjs == null) {
			fileIds = new ArrayList<String>();
		} else {
			fileIds = getFileIds(fileInfoObjs);
		}

		return fileIds;
	}

	/**
	 * 获取指定的字段的值 
	 * @param fieldName
	 * @return
	 */
	public Object getValueByApplyTo(String applyTo) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return null;
		}

		return this.getValue(fieldName);
	}

	/**
	 *设置指定字段的值
	 * 
	 * @param fieldName
	 * @param data  data的key为真实值, value为显示值
	 * @param append true:表示在原来的值后面追加新值，false:直接覆盖原来的值
	 */
	public void setValueMap(String fieldName, Map<String, Object> data, boolean append) {
		try {
			if (append) {//追加的方式
				String value = (String) getValueFromMap(fieldName, this.formDataColMap);
				JSONObject jsonObject = new JSONObject();
				if (value != null) {
					jsonObject = new JSONObject(value);
				}

				if (data != null) {
					Iterator<String> it = data.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						Object vObj = data.get(key);
						if (vObj == null) {
							jsonObject.put(key, "");
						} else {
							jsonObject.put(key, vObj);
						}
					}
				}
				this.formDataColMap.put(fieldName, jsonObject.toString());

			} else {//覆盖的方式
				//this.setValue(fieldName, data);
				JSONObject jsonObject = new JSONObject();
				if (data != null) {
					Iterator<String> it = data.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						Object vObj = data.get(key);
						if (vObj == null) {
							jsonObject.put(key, "");
						} else {
							jsonObject.put(key, vObj);
						}

					}
				}
				this.formDataColMap.put(fieldName, jsonObject.toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setValueMap(String fieldName, String jsonValue) {
		this.formDataColMap.put(fieldName, jsonValue);
	}

	/**
	 *设置指定字段的值
	 * 
	 * @param applyTo
	 * @param data  data的key为真实值, value为显示值
	 * @param append true:表示在原来的值后面追加新值，false:直接覆盖原来的值
	 */
	public void setValueMapByApplyTo(String applyTo, Map<String, Object> data, boolean append) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return;
		}
		this.setValueMap(fieldName, data, append);
	}

	/**
	 *设置指定字段的值
	 * 
	 * @param applyTo
	 * @param data  data的key为真实值, value为显示值
	 * @param append true:表示在原来的值后面追加新值，false:直接覆盖原来的值
	 */
	public void setValueMapByApplyTo(String applyTo, String data) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return;
		}
		this.setValueMap(fieldName, data);
	}

	/**
	 * 获取真实值显示值
	 * @param fieldName
	 * @return
	 */
	public Map<String, Object> getValueMap(String fieldName) {
		if (!dUtils.isValueAsMap(fieldName)) {
			//throw new WellException("value of field[" + fieldName + "] is not a map, please invoke getValue");
			return null;
		}
		String v = (String) getValueFromMap(fieldName, this.formDataColMap);
		if (v == null || v.length() == 0 || v.replaceAll(" ", "").length() < 5) {
			return null;
		}
		try {
			return new ObjectMapper().readValue(v, HashMap.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取真实值显示值
	 * @param fieldName
	 * @return
	 */
	public Map<String, Object> getValueMapByApplyTo(String applyTo) {
		String fieldName = this.getdUtils().getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return null;
		}
		return this.getValueMap(fieldName);
	}

	/**
	 * 返回指定的某真实值对应的真实值
	 * @param fieldName 字段名 
	 * @return
	 */
	public String getRealValue(String fieldName) {
		Map<String, Object> valueMap = this.getValueMap(fieldName);
		if (valueMap == null) {
			return null;
		}
		String realValues = "";
		for (String realValue : valueMap.keySet()) {
			if (realValues.length() == 0) {
				realValues = realValue;
			} else {
				realValues += ";" + realValue;
			}
		}
		return realValues;
	}

	/** 
	 * @param fieldName 字段名
	 * @param applyTo 应用于
	 * @return
	 */
	public String getRealValueByApplyTo(String applyTo) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return null;
		}

		return this.getRealValue(fieldName);
	}

	/** 
	 * @param fieldName 字段名 
	 * @return
	 */
	public String getDisplayValue(String fieldName) {
		Map<String, Object> valueMap = this.getValueMap(fieldName);
		if (valueMap == null) {
			return null;
		}

		String displayValues = "";
		for (String realValue : valueMap.keySet()) {
			if (displayValues.length() == 0) {
				displayValues = (String) valueMap.get(realValue);
			} else {
				displayValues += ";" + (String) valueMap.get(realValue);
			}
		}
		return displayValues;

	}

	/** 
	 * @param applyTo  
	 * @return
	 */
	public String getDisplayValueByApplyTo(String applyTo) {
		String fieldName = this.dUtils.getFieldNameByApplyTo(applyTo);
		if (fieldName == null) {
			return null;
		}
		return this.getDisplayValue(fieldName);
	}

	public DyFormDefinitionJSON getdUtils() {
		return dUtils;
	}

	/**======================================================================
	 * 功能：判断字符串是否为日期格式
	 * @param str
	 * @return
	 */
	public boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((/d{2}(([02468][048])|([13579][26]))[/-///s]?((((0?[13578])|(1[02]))[/-///s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/-///s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/-///s]?((0?[1-9])|([1-2][0-9])))))|(/d{2}(([02468][1235679])|([13579][01345789]))[/-///s]?((((0?[13578])|(1[02]))[/-///s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[/-///s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[/-///s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(/s(((0?[0-9])|([1-2][0-3]))/:([0-5]?[0-9])((/s)|(/:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public Map<String, Object> getDisplayValues() {
		Map<String, Object> displayValues = new HashMap<String, Object>();
		List<String> fieldNames = this.getdUtils().getFieldNamesOfMainform();
		for (String fieldName : fieldNames) {
			if (this.dUtils.isValueAsMap(fieldName)) {
				displayValues.put(fieldName, this.getDisplayValue(fieldName));
			} else {
				displayValues.put(fieldName, this.getValue(fieldName));
			}
		}
		return displayValues;
	}

	public ValidateMsg validate() {
		ValidateMsg msg = new ValidateMsg();
		return msg;
	}

}
