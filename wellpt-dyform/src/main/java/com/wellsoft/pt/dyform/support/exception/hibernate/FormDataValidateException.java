package com.wellsoft.pt.dyform.support.exception.hibernate;

import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.web.json.JsonDataException;

public class FormDataValidateException extends JsonDataException {
	private String msg = "";

	public FormDataValidateException() {
	}

	public FormDataValidateException(String msg) {
		this.msg = msg;
	}

	@Override
	public Object getData() {
		return this.msg;
	}

	@Override
	public JsonDataErrorCode getErrorCode() {
		return JsonDataErrorCode.FormDataValidateException;
	}

}
