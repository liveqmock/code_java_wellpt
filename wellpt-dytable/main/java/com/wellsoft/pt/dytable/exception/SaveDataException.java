/*
 * @(#)2013-5-17 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.exception;

import java.util.Map;

import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.web.json.JsonDataException;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-5-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-5-17.1	wubin		2013-5-17		Create
 * </pre>
 *
 */
public class SaveDataException extends JsonDataException {

	private Map variables;

	private String errorCode = "SaveDataException";

	public SaveDataException() {

	}

	public SaveDataException(Map variables) {
		this.variables = variables;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.core.web.json.JsonDataException#getErrorCode()
	 */
	@Override
	public JsonDataErrorCode getErrorCode() {
		return JsonDataErrorCode.SaveData;
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.core.web.json.JsonDataException#getData()
	 */
	@Override
	public Object getData() {
		return variables;
	}

}
