/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.utils;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class DynamicUtils {
	public final static String HBM_XML_PATH = "formxml";
	public final static String FORM_XML_PATH = "formcontentxml";

	/**
	 * 获取32位随机数，方便与hibernate的uuid.hex合并
	 * 
	 * @return
	 */
	public static String getRandomUUID(){
		String uuid = java.util.UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	public static String getHtmlBodyContent(String fullPath){
		String str = "";
		Parser parser;
		try {
			parser = new Parser(fullPath);
			NodeFilter filterBody=new TagNameFilter("body");
			NodeList parse = parser.parse(filterBody);
			str = parse.toHtml();
			str = str.replace("<body>", "");
			str = str.replace("</body>", "");
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args){
		System.out.println(java.util.UUID.randomUUID().toString().replaceAll("-", ""));
		System.out.println(System.currentTimeMillis());
	}
}
