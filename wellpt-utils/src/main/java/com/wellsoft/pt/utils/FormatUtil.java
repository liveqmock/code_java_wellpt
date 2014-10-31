package com.wellsoft.pt.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormatUtil {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(FormatUtil.class);

	/**
	 * Detect if the current browser is a mobile one
	 */
	public static final boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent").toLowerCase();
		return userAgent.contains("android") || userAgent.contains("iphone");
	}

	/**
	 * Format the document size for human readers
	 */
	public static String formatSize(long size) {
		DecimalFormat df = new DecimalFormat("#0.0");
		String str;

		if (size / 1024 < 1) {
			str = size + " B";
		} else if (size / 1048576 < 1) {
			str = df.format(size / 1024.0) + " KB";
		} else if (size / 1073741824 < 1) {
			str = df.format(size / 1048576.0) + " MB";
		} else if (size / 1099511627776L < 1) {
			str = df.format(size / 1073741824.0) + " GB";
		} else if (size / 1125899906842624L < 1) {
			str = df.format(size / 1099511627776.0) + " TB";
		} else {
			str = "BIG";
		}

		return str;
	}

	/**
	 * Format time for human readers
	 */
	public static String formatTime(long time) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String str = df.format(time);
		return str;
	}

	public static String formatDate(long time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(time);
		return str;
	}

	public static String formatFullDate(long time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(time);
		return str;
	}

	/**
	 * Format time interval for humans 
	 */
	public static String formatSeconds(long time) {
		long hours, minutes, seconds, mseconds;
		mseconds = time % 1000;
		time = time / 1000;
		hours = time / 3600;
		time = time - (hours * 3600);
		minutes = time / 60;
		time = time - (minutes * 60);
		seconds = time;
		return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds) + "."
				+ (mseconds < 10 ? "00" + mseconds : (mseconds < 100 ? "0" + mseconds : mseconds));
	}

	/**
	 * Format calendar date
	 */
	public static String formatDate(Calendar cal) {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(cal.getTime());
	}

	/**
	 * Format string array
	 */
	public static String formatArray(String[] values) {
		if (values != null) {
			if (values.length == 1) {
				return values[0];
			} else {
				return ArrayUtils.toString(values);
			}
		} else {
			return "NULL";
		}
	}

	/**
	 * Format object
	 */
	public static String formatObject(Object value) {
		if (value != null) {
			if (value instanceof Object[]) {
				return ArrayUtils.toString(value);
			} else {
				return value.toString();
			}
		} else {
			return "NULL";
		}
	}

	/**
	 * Escape html tags 
	 */
	public static String escapeHtml(String str) {
		return str.replace("<", "&lt;").replace(">", "&gt;");
	}

}
