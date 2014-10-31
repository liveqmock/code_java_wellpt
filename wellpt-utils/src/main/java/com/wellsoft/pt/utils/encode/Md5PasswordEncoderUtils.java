/*
 * @(#)2013-11-19 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.encode;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-11-19
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-11-19.1	zhulh		2013-11-19		Create
 * </pre>
 *
 */
public class Md5PasswordEncoderUtils {
	private static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	public synchronized static String encodePassword(String rawPass, Object salt) {
		return passwordEncoder.encodePassword(rawPass, salt);
	}

	public synchronized static boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return passwordEncoder.isPasswordValid(encPass, rawPass, salt);
	}
}
