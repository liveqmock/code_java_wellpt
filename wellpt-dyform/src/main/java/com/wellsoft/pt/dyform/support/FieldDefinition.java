package com.wellsoft.pt.dyform.support;

import org.json.JSONException;

import com.wellsoft.pt.dyform.support.DyFormConfig.EnumFieldPropertyName;

public class FieldDefinition {
	private DyFormDefinitionJSON dyJson = null;
	private String fieldName = null;

	/*private String name; //字段名
	private String oldName; //旧的字段名
	private String realDisplay; //真实值与显示值的字段名，一个JSON对象;real为真实值的字段名，display为显示值的字段名
	private String showType; //编辑模式
	private String applyTo; //
	private String displayName; //
	private String dbDataType; //
	private String indexed; //
	private String showed; //
	private String sorted; //
	private String sysType; //
	private String length; //
	private String defaultValue; //
	private String valueCreateMethod; //
	private String onlyreadUrl; //
	private String inputMode; //

	private String textAlign; //
	private String ctlWidth; //
	private String ctlHight; //
	private String fontSize; //
	private String fontColor; // 
	private String fieldCheckRules; //
	private String contentFormat;*/

	public FieldDefinition(String fieldName, DyFormDefinitionJSON dyJson) {
		this.dyJson = dyJson;
		this.fieldName = fieldName;
	}

	public String getName() {
		return this.getFieldName();
	}

	public void setName(String name) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.name, name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getShowType() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
	}

	public void setShowType(String showType) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.showType, showType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getApplyTo() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.applyTo);
	}

	public void setApplyTo(String applyTo) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.applyTo, applyTo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getDisplayName() {
		if (this.dyJson == null) {
			return this.getName();
		} else {
			return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.displayName);
		}

	}

	public void setDisplayName(String displayName) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.displayName, displayName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getDbDataType() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.dbDataType);
	}

	public void setDbDataType(String dbDataType) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.dbDataType, dbDataType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getIndexed() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.indexed);

	}

	public void setIndexed(String indexed) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.indexed, indexed);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getShowed() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showed);
	}

	public void setShowed(String showed) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.showed, showed);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getSorted() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.sorted);
	}

	public void setSorted(String sorted) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.sorted, sorted);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getSysType() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.showType);
	}

	public void setSysType(String sysType) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.sysType, sysType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getLength() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.length);
	}

	public void setLength(String length) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.length, length);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getDefaultValue() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.defaultValue);
	}

	public void setDefaultValue(String defaultValue) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.defaultValue, defaultValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getValueCreateMethod() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.valueCreateMethod);
	}

	public void setValueCreateMethod(String valueCreateMethod) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.valueCreateMethod, valueCreateMethod);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getOnlyreadUrl() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.onlyreadUrl);
	}

	public void setOnlyreadUrl(String onlyreadUrl) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.onlyreadUrl, onlyreadUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getInputMode() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.inputMode);
	}

	public void setInputMode(String inputMode) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.inputMode, inputMode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getTextAlign() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.textAlign);
	}

	public void setTextAlign(String textAlign) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.textAlign, textAlign);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getCtlWidth() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.ctlWidth);
	}

	public void setCtlWidth(String ctlWidth) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.ctlWidth, ctlWidth);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getCtlHight() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.ctlHight);
	}

	public void setCtlHight(String ctlHight) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.ctlHight, ctlHight);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getFontSize() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.fontSize);
	}

	public void setFontSize(String fontSize) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.fontSize, fontSize);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public String getFontColor() {
		if (this.dyJson == null) {
			return null;
		}
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.fontColor);
	}

	public void setFontColor(String fontColor) {
		if (this.dyJson == null) {
			return;
		}
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.fontColor, fontColor);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*
		public String getFieldCheckRules() {
			return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.fieldCheckRules);
		}

		public void setFieldCheckRules(String fieldCheckRules) {
			try {
				this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.fieldCheckRules, fieldCheckRules);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}*/

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/*public String getContentFormat() {
		return this.dyJson.getFieldPropertyOfStringType(fieldName, EnumFieldPropertyName.contentFormat);
	}

	public void setContentFormat(String contentFormat) {
		try {
			this.dyJson.addFieldProperty(fieldName, EnumFieldPropertyName.contentFormat, contentFormat);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}*/

}
