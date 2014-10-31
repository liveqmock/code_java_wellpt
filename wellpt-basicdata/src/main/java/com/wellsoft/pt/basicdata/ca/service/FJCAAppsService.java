/*
 * @(#)2014-1-13 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.ca.service;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2014-1-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-1-13.1	zhulh		2014-1-13		Create
 * </pre>
 *
 */
public interface FJCAAppsService {

	public static final String KEY_ENABLE = "fjca.enable";

	public static final String KEY_SERVER_URL = "fjca.server.url";

	public static final String ENABLE = "true";

	/**
	 * 检验当前用户是否以该证书登录
	 * 
	 * @param textCert
	 */
	void checkCurrentCertificate(String textCert);
}
