/*
 * @(#)2014-1-13 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.ca.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.ca.service.FJCAAppsService;
import com.wellsoft.pt.security.core.userdetails.UserDetails;
import com.wellsoft.pt.utils.ca.FJCAUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

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
@Service
@Transactional
public class FJCAAppsServiceImpl implements FJCAAppsService {

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.ca.service.FJCAAppsService#checkCurrentCertificate(java.lang.String)
	 */
	@Override
	public void checkCurrentCertificate(String textCert) {
		UserDetails userDetails = SpringSecurityUtils.getCurrentUser();
		Object originData = userDetails.getExtraData(FJCAUtils.KEY_LOGIN_TOKEN_ORIGIN_DATA);
		Object signData = userDetails.getExtraData(FJCAUtils.KEY_LOGIN_TOKEN_SIGN_DATA);
		String textOriginData = originData == null ? "" : originData.toString();
		String textSignData = signData == null ? "" : signData.toString();

		if (StringUtils.isBlank(textOriginData) || StringUtils.isBlank(textSignData)) {
			throw new RuntimeException("请先用证书登录系统!");
		}

		int retCode = FJCAUtils.verify(textOriginData, textSignData, textCert);
		if (retCode != 0) {
			throw new RuntimeException("证书验证错误，请用证书重新登录!");
		}
	}

}
