/* --------------------------------------------------------
 * Copyright (c) Express Scripts, Inc.  All rights reserved.
 * --------------------------------------------------------
 */
package com.wellsoft.pt.ldx.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

/**
 * The Class DateUtils.
 */
public final class DateUtils {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(DateUtils.class);

	/** The Constant MILLISECONDS_OF_ONE_DAY. */
	private static final int MILLISECONDS_OF_ONE_DAY = 1000 * 60 * 60 * 24;
	private static final int MILLISECONDS_OF_ONE_HOUR = 1000 * 60 * 60;

	/** The en locale. */
	private static Locale enLocale = new Locale("en", "US");

	/**
	 * Instantiates a new date utils.
	 */
	private DateUtils() {
	}

	/**
	 * Format date.
	 * 
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the string
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, enLocale);
		return sdf.format(date);
	}

	/**
	 * Format date time.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String formatDateTime(Date date) {
		return (formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * Format date time.
	 * 
	 * @return the string
	 */
	public static String formatDateTime() {
		return (formatDate(now(), "MM-dd-yyyy HH:mm:ss"));
	}

	/**
	 * Format date.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String formatDate(Date date) {
		return (formatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * Format date.
	 * 
	 * @return the string
	 */
	public static String formatDate() {
		return (formatDate(now(), "yyyy-MM-dd"));
	}

	/**
	 * Format time.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String formatTime(Date date) {
		return (formatDate(date, "HH:mm:ss"));
	}

	/**
	 * Format time no colon.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String formatTimeNoColon(Date date) {
		return (formatDate(date, "HHmmssSS"));
	}

	/**
	 * Format time.
	 * 
	 * @return the string
	 */
	public static String formatTime() {
		return (formatDate(now(), "HH:mm:ss"));
	}

	/**
	 * Format full time.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the string
	 */
	public static String formatFullTime(Date date) {
		return (formatDate(date, "HH:mm:ss SS a"));
	}

	/**
	 * Now.
	 * 
	 * @return the date
	 */
	public static Date now() {
		return (new Date());
	}

	/**
	 * Parses the datetime.
	 * 
	 * @param datetime
	 *            the datetime
	 * 
	 * @return the date
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return parseDate(datetime, "MM-dd-yyyy HH:mm:ss", true);

	}

	/**
	 * Specify whether or not date parsing is to be lenient. With lenient
	 * parsing, the parser may use heuristics to interpret inputs that do not
	 * precisely match this object's format. With strict parsing, inputs must
	 * match this object's format. when the date is 2008-13-12 and
	 * isLenient=false then throws parase exception when the date is 2008-13-12
	 * and isLenient=true then return 2009-01-12.
	 * 
	 * @param date
	 *            date
	 * @param isLenient
	 *            isLenient
	 * 
	 * @return Date
	 * 
	 * @throws ParseException
	 *             ParseException
	 */
	public static Date parseDate(String date, boolean isLenient)
			throws ParseException {
		return parseDate(date, "yyyy-MM-dd", isLenient);
	}

	/**
	 * Format date.
	 * 
	 * @param o
	 *            the o
	 * 
	 * @return the string
	 */
	public static String formatDate(Object o) {
		if (o == null) {
			return "";
		}
		if (o.getClass() == String.class) {
			return formatDate((String) o);
		} else if (o.getClass() == Date.class) {
			return formatDate((Date) o);
		} else if (o.getClass() == Timestamp.class) {
			return formatDate(new Date(((Timestamp) o).getTime()));
		} else {
			return o.toString();
		}
	}

	/**
	 * Format date time.
	 * 
	 * @param o
	 *            the o
	 * 
	 * @return the string
	 */
	public static String formatDateTime(Object o) {
		if ((o == null) || (o.toString().trim().length() == 0)) {
			return "";
		}
		if (o.getClass() == String.class) {
			return formatDateTime((String) o);
		} else if (o.getClass() == Date.class) {
			return formatDateTime((Date) o);
		} else if (o.getClass() == Timestamp.class) {
			return formatDateTime(new Date(((Timestamp) o).getTime()));
		} else {
			return o.toString();
		}
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date getDate() throws ParseException {
		return parseDate(formatDate(), false);
	}

	/**
	 * Parses the date.
	 * 
	 * @param dateString
	 *            the date string
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the date
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseDate(String dateString, String pattern)
			throws ParseException {
		return parseDate(dateString, pattern, true);
	}

	public static Date parseDateSimply(String dateString, String pattern) {
		try {
			return parseDate(dateString, pattern, true);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Date diff.
	 * 
	 * @param startDay
	 *            the start day
	 * @param endDay
	 *            the end day
	 * 
	 * @return the int
	 */
	public static int dateDiff(Date startDay, Date endDay) {
		if (null == startDay || null == endDay)
			return 0;
		if (startDay.after(endDay)) {
			Date temp = startDay;
			startDay = endDay;
			endDay = temp;
		}
		long sl = startDay.getTime();
		long el = endDay.getTime();
		long ei = el - sl;
		return (int) (ei / (MILLISECONDS_OF_ONE_DAY));
	}

	/**
	 * Adds the date.
	 * 
	 * @param date
	 *            the date
	 * @param amount
	 *            the amount
	 * 
	 * @return the date
	 */
	public static Date addDate(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, amount); // calendar.DATE
		return calendar.getTime();
	}

	/**
	 * Adds the month.
	 * 
	 * @param date
	 *            the date
	 * @param amount
	 *            the amount
	 * 
	 * @return the date
	 */
	public static Date addMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, amount); // calendar.MONTH
		return calendar.getTime();
	}

	/**
	 * Adds the year.
	 * 
	 * @param date
	 *            the date
	 * @param amount
	 *            the amount
	 * 
	 * @return the date
	 */
	public static Date addYear(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, amount); // calendar.YEAR
		return calendar.getTime();
	}

	public static Integer getMonthOfCurrentTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * Gets the first day of current month.
	 * 
	 * @return the first day of current month
	 */
	public static Date getFirstDayOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * Gets the next day of current month.
	 * 
	 * @return the next day of current month
	 */
	public static Date getNextDayOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * Gets the before day of current day.
	 * 
	 * @return the before day of current day
	 */
	public static Date getBeforeDayOfCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * Gets the first day of current year.
	 * 
	 * @return the first day of current year
	 */
	public static Date getFirstDayOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Gets the last day of current year.
	 * 
	 * @return the last day of current year
	 */
	public static Date getLastDayOfCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.YEAR, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 判断是否周星期一
	 * 
	 * @return boolean
	 */
	public static boolean isFirseDayOfCurrentWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		Date firstDate = calendar.getTime();
		return sdf.format(date).equals(sdf.format(firstDate));
	}

	// 1998-12-01
	/**
	 * Gets the date for noc.
	 * 
	 * @return the date for noc
	 */
	public static Date getDateForNOC() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 1998);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * Gets the current date of last year.
	 * 
	 * @return the current date of last year
	 */
	public static Date getCurrentDateOfLastYear() {
		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * Gets the biggest date.
	 * 
	 * @return the biggest date
	 */
	public static Date getBiggestDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(9999, 11, 31);
		return calendar.getTime();
	}

	/**
	 * Parses the date.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the date
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseDate(String date) throws ParseException {
		return parseDate(date, false);
	}

	/**
	 * Before today.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return true, if successful
	 */
	public static boolean beforeToday(Date date) {
		Date currentDate = null;
		try {
			currentDate = DateUtils.parseDate(formatDate(new Date()));
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return date.before(currentDate);
	}

	/**
	 * After today.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return true, if successful
	 */
	public static boolean afterToday(Date date) {
		Date currentDate = null;
		try {
			currentDate = DateUtils.parseDate(formatDate(new Date()));
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return date.after(currentDate);
	}

	/**
	 * Parses the date.
	 * 
	 * @param dateString
	 *            the date string
	 * @param pattern
	 *            the pattern
	 * @param isLenient
	 *            the is lenient
	 * 
	 * @return the date
	 * 
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parseDate(String dateString, String pattern,
			boolean isLenient) throws ParseException {
		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, enLocale);
		formatter.setLenient(false);
		Date resultDate = null;
		resultDate = formatter.parse(dateString);
		return resultDate;

	}

	/**
	 * Gets the current time stamp.
	 * 
	 * @return the current time stamp
	 */
	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp((new Date()).getTime());
	}

	/**
	 * Gets the day.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return the day
	 */
	public static Date getDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String getJulianDay(Calendar calendar) {
		String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		String dayOfYear = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
		String date = StringUtils.leftPad(dayOfYear, 3, "0");
		String dateStr = year + date;
		return dateStr;
	}

	public static String getCurJulianDay() {
		return DateUtils.getJulianDay(Calendar.getInstance());
	}

	public static String getCurYear() {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}

	public static Date parseDateByJulian(String julianDate) {
		String year = StringUtils.substring(julianDate, 0, 2);
		String julian = StringUtils.substring(julianDate, 2);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt("20".concat(year)));
		calendar.set(Calendar.DAY_OF_YEAR, Integer.parseInt(julian));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * @param nowDate
	 * @return Function Desc:
	 */
	public static Date getBeginTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.HOUR_OF_DAY, -8);
		return calendar.getTime();
	}

	public static Date getChineseTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		return calendar.getTime();
	}

	/**
	 * @param nowDate
	 * @return Function Desc:
	 */
	public static Date getEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(Calendar.HOUR_OF_DAY, -8);
		return calendar.getTime();
	}

	/**
	 * 获得星期值
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 根据日期字符串获取日期所在当年中的第几周
	 * 
	 * @param date
	 *            ("yyyy-MM-dd")
	 * @return
	 */
	public static String getWeekOfYear(String date) {
		String[] tempYear = date.trim().split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(tempYear[0]));
		cal.set(Calendar.MONTH, (Integer.valueOf(tempYear[1]) - 1));
		cal.set(Calendar.DATE, Integer.valueOf(tempYear[2]));
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return String.valueOf(week);
	}

	/**
	 * 判断日期是否为月末一天
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1); // calendar.DATE
		return calendar.getTime().getDate() == 1;
	}

	public static Double hourDiff(Date startDay, Date endDay) {
		if (startDay.after(endDay)) {
			Date temp = startDay;
			startDay = endDay;
			endDay = temp;
		}
		long sl = startDay.getTime();
		long el = endDay.getTime();
		long ei = el - sl;
		BigDecimal b = new BigDecimal((double) ei / (MILLISECONDS_OF_ONE_HOUR));
		BigDecimal one = new BigDecimal(1);
		return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static void addDateToJson(JSONObject jObject, Date date, String key) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			jObject.element(key, sdf.format(date));
		} else {
			jObject.element(key, "");
		}
	}
}
