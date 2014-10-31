/*
 * @(#)2014-4-2 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.reflection;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.wellsoft.pt.core.context.ApplicationContextHolder;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2014-4-2
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-4-2.1	zhulh		2014-4-2		Create
 * </pre>
 *
 */
public class ServiceInvokeUtils {

	public static Object invoke(String service, final Class<?>[] parameterTypes, final Object... args) {
		try {
			String[] services = StringUtils.split(service, ".");
			String serviceName = services[0];
			String methodName = services[1];
			Object serviceObject = ApplicationContextHolder.getBean(serviceName);
			Method method = serviceObject.getClass().getMethod(methodName, parameterTypes);
			return method.invoke(serviceObject, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
