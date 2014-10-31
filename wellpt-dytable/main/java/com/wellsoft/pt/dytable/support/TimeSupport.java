/*
 * @(#)2013-8-1 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-8-1
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-8-1.1	wubin		2013-8-1		Create
 * </pre>
 *
 */
public class TimeSupport {

	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List lDate = new ArrayList();
		lDate.add(dBegin);

		Calendar cal = Calendar.getInstance();
		//使用给定的 Date 设置此 Calendar 的时间 
		cal.setTime(dBegin);

		boolean bContinue = true;

		while (bContinue) {
			//根据日历的规则，为给定的日历字段添加或减去指定的时间量 
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后 
			if (dEnd.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		if (!dBegin.equals(dEnd)) {

			lDate.add(dEnd);
		}

		return lDate;
	}

	//	//计算占用的时间块
	//	public static String getTimes(String begintime, String endtime) {
	//		String[] beginTimes = begintime.split(":");
	//		String[] endTimes = endtime.split(":");
	//		String time1 = beginTimes[1];
	//		String time2 = endTimes[1];
	//
	//		int a = 0;
	//		int b = 0;
	//		if ("30".equals(time1)) {
	//			a = Integer.valueOf(beginTimes[0]) * 2 + 1;
	//		} else if ("00".equals(time1)) {
	//			a = Integer.valueOf(beginTimes[0]) * 2;
	//		}
	//
	//		if ("30".equals(time2)) {
	//			b = Integer.valueOf(endTimes[0]) * 2 + 1;
	//		} else if ("00".equals(time2)) {
	//			b = Integer.valueOf(endTimes[0]) * 2;
	//		}
	//		StringBuffer strs = new StringBuffer();
	//		int c = b - a;
	//		for (int i = 0; i < c; i++) {
	//			strs.append("," + String.valueOf(a + i));
	//		}
	//
	//		return strs.toString().replaceFirst(",", "");
	//	}
	//计算占用的时间块
	public static String getTimes(String begintime, String endtime) {
		String[] beginTimes = begintime.split(":");
		String[] endTimes = endtime.split(":");
		String time1 = beginTimes[1];
		String time2 = endTimes[1];

		int a = 0;
		int b = 0;
		if (Integer.valueOf(time1) >= 30) {
			a = Integer.valueOf(beginTimes[0]) * 2 + 2;
		} else {
			a = Integer.valueOf(beginTimes[0]) * 2 + 1;
		}

		if (Integer.valueOf(time2) >= 30) {
			b = Integer.valueOf(endTimes[0]) * 2 + 2;
		} else {
			b = Integer.valueOf(endTimes[0]) * 2 + 1;
		}
		StringBuffer strs = new StringBuffer();
		int c = b - a;
		for (int i = 0; i < c; i++) {
			strs.append("," + String.valueOf(a + i));
		}

		return strs.toString().replaceFirst(",", "");
	}

	//计算占用的时间块
	public static String getTime2s(String begintime, String endtime) {
		String[] beginTimes = begintime.split(":");
		String[] endTimes = endtime.split(":");
		String time1 = beginTimes[1];
		String time2 = endTimes[1];

		int a = 0;
		int b = 0;
		int A = 0;
		int B = 0;

		if (Integer.valueOf(time1) == 00) {
			a = Integer.valueOf(beginTimes[0]) * 2 + 1;
			A = Integer.valueOf(30);
		} else if (Integer.valueOf(time1) == 30) {
			a = Integer.valueOf(beginTimes[0]) * 2 + 2;
			A = Integer.valueOf(30);
		} else {
			a = Integer.valueOf(beginTimes[0]) * 2 + 1;
			A = 30 - Integer.valueOf(time1);
		}

		if (Integer.valueOf(time2) == 00) {
			b = Integer.valueOf(endTimes[0]) * 2 + 1;
			B = Integer.valueOf(30);
		} else if (Integer.valueOf(time2) == 30) {
			b = Integer.valueOf(endTimes[0]) * 2 + 2;
			B = Integer.valueOf(30);
		} else {
			b = Integer.valueOf(endTimes[0]) * 2 + 1;
			B = 30 - Integer.valueOf(time2);
		}

		StringBuffer strs = new StringBuffer();
		int c = b - a;
		for (int i = 0; i < c; i++) {
			if (i == 0) {
				strs.append("," + String.valueOf(a + i) + "_" + A);
			} else if (i < (c - 1) && i > 0) {
				strs.append("," + String.valueOf(a + i) + "_" + "30");
			} else if (i == (c - 1)) {
				strs.append("," + String.valueOf(a + i) + "_" + B);
			}

		}

		return strs.toString().replaceFirst(",", "");
	}

	public static void main(String[] args) throws Exception {
		String a = "18:30";
		String b = "21:00";
		String c = TimeSupport.getTime2s(a, b);
		//		String d = TimeSupport.getTime2s(a, b);
		System.out.println(c);
		//		System.out.println(c);
	}
}
