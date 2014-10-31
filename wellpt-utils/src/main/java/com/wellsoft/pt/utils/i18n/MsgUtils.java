/** 
* @Project:welloa 
* @Description: TODO(用一句话描述该文件做什么)
* @author: Administrator
* @version: V1.0   
* @date: 2012-10-17 
* @Copyright (c) 威尔公司-版权所有
*/

package com.wellsoft.pt.utils.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.wellsoft.pt.core.context.ApplicationContextHolder;

/**
* @ClassName: MsgUtils
* @Description: 获取国际化信息工具类
* @author Administrator
* @date 2012-10-17 
* @version : 1.0
*/

public class MsgUtils {

	/**
	* @Title: getMessage
	* @Description: 获取国际化信息
	* @param @param code
	* @param @param args
	* @param @param defaultMessage
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/

	public static String getMessage(String code, Object[] args, String defaultMessage) {
		return ApplicationContextHolder.getBean(MessageSource.class).getMessage(code, args, defaultMessage,
				LocaleContextHolder.getLocale());
	}

	/**
	* @Title: getMessage
	* @Description: 获取国际化信息
	* @param @param code
	* @param @param args
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/
	public static String getMessage(String code, Object... args) {
		return ApplicationContextHolder.getBean(MessageSource.class).getMessage(code, args,
				LocaleContextHolder.getLocale());
	}

}
