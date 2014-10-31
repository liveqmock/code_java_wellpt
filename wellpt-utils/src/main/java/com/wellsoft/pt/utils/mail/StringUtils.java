package com.wellsoft.pt.utils.mail;

public class StringUtils {

	public static String nullToEmpty(String string) {
		return string == null ? "" : string;
	}

	public static String emptyToNull(String string) {
		if (string == null) {
			return string;
		}
		return "".equals(string) || "".equals(string.trim()) ? null : string;
	}

	@Deprecated
	public String toGBK(String strvalue) {
		try {
			if (strvalue == null) { // 当变量strvalue为null时
				return ""; //返回空的字符串
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "GBK");//将字符串转换为GBK编码
				return strvalue; //返回转换后的输入变量
			}
		} catch (Exception e) {
			return "";
		}
	}

	@Deprecated
	public String toUTF8(String strvalue) {
		try {
			if (strvalue == null) { //当变量strvalue为null时
				return ""; //返回空的字符串
			} else {
				strvalue = new String(strvalue.getBytes("ISO-8859-1"), "UTF-8");//将字符串转换为UTF8编码
				return strvalue; //返回转换后的输入变量strvalue
			}
		} catch (Exception e) {
			return "";
		}
	}

	//对输入的字符串进行一次编码转换，防止SQL注入
	public String StringtoSql(String str) {
		if (str == null) { //当变量str为null时
			return ""; //返回空的字符串
		} else {
			try {
				str = str.trim().replace('\'', (char) 32); //将'号转换化为空格
			} catch (Exception e) {
				return "";
			}
		}
		return str;
	}
}
