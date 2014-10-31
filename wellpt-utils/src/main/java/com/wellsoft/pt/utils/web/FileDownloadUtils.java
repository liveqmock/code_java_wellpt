/*
 * @(#)2013-4-11 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.utils.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-4-11
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-11.1	zhulh		2013-4-11		Create
 * </pre>
 *
 */
public class FileDownloadUtils {

	public static void download(HttpServletRequest request, HttpServletResponse response, InputStream is,
			String filename) {
		if (StringUtils.isBlank(filename)) {
			throw new RuntimeException("Filename should not be empty.");
		}
		String filenamePart = getFilenamePart(request, filename);
		response.reset();
		// response.setCharacterEncoding(Encoding.UTF8.getValue());
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;" + filenamePart);
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			int len = -1;
			byte[] b = new byte[102400];
			while ((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				IOUtils.write(ExceptionUtils.getStackTrace(e), os);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
			if (os != null) {
				IOUtils.closeQuietly(os);
			}
		}
	}

	/**
	 * Content-Disposition值可以有以下几种编码格式
	 * 1、直接urlencode
	 * Content-Disposition: attachment; filename="struts2.0%E4%B8%AD%E6%96%87%E6%95%99%E7%A8%8B.chm"
	 * 2、Base64编码
	 * Content-Disposition: attachment; filename="=?UTF8?B?c3RydXRzMi4w5Lit5paH5pWZ56iLLmNobQ==?="
	 * 3、RFC2231规定的标准
	 * Content-Disposition: attachment; filename*=UTF-8''%E5%9B%9E%E6%89%A7.msg
	 * 4、直接ISO编码的文件名
	 * Content-Disposition: attachment;filename="测试.txt"
	 * 
	 * 1、IE浏览器，采用URLEncoder编码  
	 * 2、Opera浏览器，采用filename*方式  
	 * 3、Safari浏览器，采用ISO编码的中文输出  
	 * 4、Chrome浏览器，采用Base64编码或ISO编码的中文输出  
	 * 5、FireFox浏览器，采用Base64或filename*或ISO编码的中文输出  
	 * 
	 * @param request
	 * @param filename
	 * @return
	 */
	private static String getFilenamePart(HttpServletRequest request, String filename) {
		String filenamePart = null;
		try {
			String userAgent = request.getHeader("user-agent");
			userAgent = userAgent == null ? "" : userAgent.toLowerCase();
			if (StringUtils.isBlank(userAgent)) {
				filenamePart = "filename*=UTF-8''" + filename;
			} else if (userAgent.indexOf("msie") != -1) {// MSIE 10.0
				String namePart = filename;
				int lastIndex = filename.lastIndexOf(".");
				if (lastIndex != -1) {
					namePart = filename.substring(0, lastIndex);
				}
				filenamePart = "filename=" + URLEncoder.encode(namePart, "UTF-8");
				if (lastIndex != -1) {
					filenamePart += filename.substring(lastIndex);
				}
			} else if (userAgent.indexOf("chrome") != -1) {// Chrome
				filenamePart = "filename=" + MimeUtility.encodeText(filename, "UTF-8", "B");
			} else if (userAgent.indexOf("firefox") != -1) {// Firefox
				filenamePart = "filename=" + MimeUtility.encodeText(filename, "UTF-8", "B");
				// outputFileName = MimeUtility.encodeText(filename, "UTF-8", "B");
			} else if (userAgent.indexOf("opera") != -1) {// Opera
				filenamePart = "filename=" + filename;
			} else if (userAgent.indexOf("safari") != -1) {// Safari
				filenamePart = "filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			filenamePart = "filename*=UTF-8''" + filename;
		}
		return filenamePart;
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, File inputFile,
			String filename) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inputFile);
			download(request, response, fis, filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}
		}
	}

}
