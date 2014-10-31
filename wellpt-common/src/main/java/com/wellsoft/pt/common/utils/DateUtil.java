/*
 * @(#)2014-3-5 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2014-3-5
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-3-5.1	Administrator		2014-3-5		Create
 * </pre>
 *
 */
public class DateUtil {
	/** 
	* 对于Calendar获得的月、日、时、分、秒、如果是个位的话，在前面补一个零
	* @param sourceStr 
	* @return 
	*/
	public static String getFormatDate(int sourceDate) {
		String date = String.valueOf(sourceDate);
		if (date.length() < 2) {
			date = "0" + date;
		}
		return date;
	}

	/** 
	 * 获取年份
	 * @return 
	 */
	public static String getYear() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));//获取年份  
		return year;
	}

	/** 
	 * 获取月份
	 * @return 
	 */
	public static String getMonth() {
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);//获取月份  
		return month;
	}

	/** 
	* 获取指定日期N天后的时间
	* @return 
	*/
	public static Date getSpecifyDate(Date date, int monunt) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		c.add(Calendar.DATE, monunt);//N天后的日期
		Date finalDate = new Date(c.getTimeInMillis());
		return finalDate;
	}

	/**  
	 * 日期格式化,格式自己指定
	 * @param date  
	 * @return  
	 */
	public static String getFormatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
