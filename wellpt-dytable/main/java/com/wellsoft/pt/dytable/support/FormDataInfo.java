/*
 * @(#)2013-2-20 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.support;

import java.io.Serializable;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-2-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-20.1	zhulh		2013-2-20		Create
 * </pre>
 *
 */
public class FormDataInfo implements Serializable {

	private static final long serialVersionUID = -6336889143723449878L;

	private String formName;

	private String formUuid;

	private String dataUuid;

	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName 要设置的formName
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the formUuid
	 */
	public String getFormUuid() {
		return formUuid;
	}

	/**
	 * @param formUuid 要设置的formUuid
	 */
	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}

	/**
	 * @return the dataUuid
	 */
	public String getDataUuid() {
		return dataUuid;
	}

	/**
	 * @param dataUuid 要设置的dataUuid
	 */
	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

}
