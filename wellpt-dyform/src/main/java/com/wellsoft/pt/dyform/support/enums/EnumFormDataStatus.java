package com.wellsoft.pt.dyform.support.enums;

public enum EnumFormDataStatus {
	DYFORM_DATA_STATUS_DEL("1", "已删除"), DYFORM_DATA_STATUS_DEFAULT("2", "默认值");
	private String value = "";
	private String remark;

	private EnumFormDataStatus(String value, String remark) {
		this.value = value;
		this.remark = remark;
	}

	public String getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public EnumFormDataStatus value2EnumObj(String value) {
		EnumFormDataStatus enumObj = null;
		for (EnumFormDataStatus status : EnumFormDataStatus.values()) {
			if (status.getValue().equals(value)) {
				enumObj = status;
			}
		}

		return enumObj;
	}
}
