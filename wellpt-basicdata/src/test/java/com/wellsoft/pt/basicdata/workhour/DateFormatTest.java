/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.workhour;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.wellsoft.pt.basicdata.workhour.support.WorkHourUtils;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2012-11-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-13.1	zhulh		2012-11-13		Create
 * </pre>
 *
 */
public class DateFormatTest extends TestCase {

	@Test
	public void testDateFormat() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2012-11-13");
		Calendar calendar = Calendar.getInstance();
		System.out.println(date);
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));

		calendar.setTime(date);
		String code = WorkHourUtils.DAY_OF_WEEKS[calendar.get(Calendar.DAY_OF_WEEK) - 1];
		System.out.println(code);
	}

	public void testCompare() {
		Calendar calendar1 = Calendar.getInstance();
		Date date1 = new Date();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		Date date2 = new Date();
		calendar2.setTime(date1);
		System.out.println(date1.compareTo(date2));
		calendar1.clear(Calendar.HOUR_OF_DAY);
		calendar1.clear(Calendar.MINUTE);
		calendar1.clear(Calendar.SECOND);
		calendar1.clear(Calendar.MILLISECOND);
		calendar2.clear(Calendar.HOUR_OF_DAY);
		calendar2.clear(Calendar.MINUTE);
		calendar2.clear(Calendar.SECOND);
		calendar2.clear(Calendar.MILLISECOND);
		System.out.println(calendar1.getTime());
		System.out.println(calendar2.getTime());
		System.out.println(calendar1.getTime().compareTo(calendar2.getTime()));
	}
}
