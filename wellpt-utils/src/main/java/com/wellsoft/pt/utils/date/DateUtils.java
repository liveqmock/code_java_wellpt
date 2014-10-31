/*
 * @(#)2012-11-13 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.datetime.DateFormatter;

/**
 * Description: 日期格式化/转换工具类
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
public class DateUtils {
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final DateFormatter fullDateTimeFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss.SSS");
	public static final DateFormatter dateTimeFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
	public static final DateFormatter dateTimeMinFormatter = new DateFormatter("yyyy-MM-dd HH:mm");
	public static final DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd");
	public static final DateFormatter monthFormatter = new DateFormatter("yyyy-MM");

	public static final Pattern fullDateTimePattern = Pattern
			.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}");
	public static final Pattern dateTimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
	public static final Pattern dateTimeMinPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
	public static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	public static final Pattern monthPattern = Pattern.compile("\\d{4}-\\d{2}");

	/**
	 * 字符串转化为日期
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source) throws ParseException {
		if (fullDateTimePattern.matcher(source).find()) {
			return fullDateTimeFormatter.parse(source, Locale.getDefault());
		}
		if (dateTimePattern.matcher(source).find()) {
			return dateTimeFormatter.parse(source, Locale.getDefault());
		}
		if (dateTimeMinPattern.matcher(source).find()) {
			return dateTimeMinFormatter.parse(source, Locale.getDefault());
		}
		if (datePattern.matcher(source).find()) {
			return dateFormatter.parse(source, Locale.getDefault());
		}
		if (monthPattern.matcher(source).find()) {
			return monthFormatter.parse(source, Locale.getDefault());
		}
		return dateTimeFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Parse the Date using pattern "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseFullDateTime(String source) throws ParseException {
		return fullDateTimeFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Format the Date using pattern "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFullDateTime(Date date) {
		return fullDateTimeFormatter.print(date, Locale.getDefault());
	}

	/**
	 * Parse the Date using pattern "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String source) throws ParseException {
		return dateTimeFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Format the Date using pattern "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return dateTimeFormatter.print(date, Locale.getDefault());
	}

	/**
	 * Parse the Date using pattern "yyyy-MM-dd HH:mm"
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTimeMin(String source) throws ParseException {
		return dateTimeMinFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Format the Date using pattern "yyyy-MM-dd HH:mm"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeMin(Date date) {
		return dateTimeMinFormatter.print(date, Locale.getDefault());
	}

	/**
	 * Parse the Date using pattern "yyyy-MM-dd"
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String source) throws ParseException {
		return dateFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Format the Date using pattern "yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormatter.print(date, Locale.getDefault());
	}

	/**
	 * Parse the Date using pattern "yyyy-MM"
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseMonth(String source) throws ParseException {
		return monthFormatter.parse(source, Locale.getDefault());
	}

	/**
	 * Format the Date using pattern "yyyy-MM"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatMonth(Date date) {
		return monthFormatter.print(date, Locale.getDefault());
	}

	public static Integer hourToSecond(final Double hour) {
		return Double.valueOf(hour * 3600).intValue();
	}

	public static Double millisecondToHour(Long millisecond) {
		return (millisecond) / (3600 * 1000 * 1d);
	}

	public static Double millisecondToMinute(Long millisecond) {
		return (millisecond) / (60 * 1000 * 1d);
	}

	public static Double calculateAsHour(final Date from, final Date to) {
		long totalTime = from.getTime() - to.getTime();
		return millisecondToHour(totalTime);
	}

	public static Double calculateAsMinute(final Date from, final Date to) {
		long totalTime = from.getTime() - to.getTime();
		return millisecondToMinute(totalTime);
	}

	/**
	 * 判断两个日期是否同一天，若是返回true，否则返回false
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Boolean isSameDate(Date date1, Date date2) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		String tempStr1 = simpleDateFormat.format(date1);
		String tempStr2 = simpleDateFormat.format(date2);
		return tempStr1.equals(tempStr2);
	}

	/**
	 * 计算开始时间(fromTime)、结束时间(toTime)在某一时间区段[fromRange, toRange]中的有效值(取并集)
	 * 
	 * @param fromTime
	 * @param toTime
	 * @param fromTime2
	 * @param toTime2
	 * @param totalTime2
	 * @return
	 */
	public static long getUnionTime(Date fromTime, Date toTime, Date fromRange, Date toRange) {
		long totalTime = Long.valueOf(0);
		//开始时间、结束时间不在时间区段中，返回0
		if ((fromTime.after(toRange)) || (toTime.before(fromRange))) {

		} else {//计算有效工作时间
			totalTime = toRange.getTime() - fromRange.getTime();
			long fromOffset = fromTime.getTime() - fromRange.getTime();
			long toOffset = toTime.getTime() - toRange.getTime();
			//减去已经开始的有效时间
			if (fromOffset > 0) {
				totalTime = totalTime - fromOffset;
			}
			//减去没有到达的有效时间
			if (toOffset < 0) {
				totalTime = totalTime - (-toOffset);
			}
		}
		return totalTime;
	}

	/**
	 * 根据日期获取日历
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 根据日期获取日历
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getMaxTimeCalendar(Calendar source) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source.get(Calendar.DAY_OF_MONTH), 23, 59,
				59);
		return calendar;
	}

	/**
	 * 根据日期获取日历
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getMinTimeCalendar(Calendar source) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar;
	}
}
