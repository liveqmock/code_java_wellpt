/*
 * @(#)2013-12-26 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.bean;

import com.wellsoft.pt.dytable.entity.FormDefinition;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-12-26
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-12-26.1	wubin		2013-12-26		Create
 * </pre>
 *
 */
public class FormDefinitionBean extends FormDefinition {

	/** 如何描述serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String htmlBody;

	/**
	 * @return the htmlBody
	 */
	public String getHtmlBody() {
		return htmlBody;
	}

	/**
	 * @param htmlBody 要设置的htmlBody
	 */
	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

}
