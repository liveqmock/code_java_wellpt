/*
 * @(#)2013-4-19 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.repository.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.wellsoft.pt.core.resource.Config;
import com.wellsoft.pt.repository.entity.FileEntity;
import com.wellsoft.pt.repository.support.convert.DocConverter;
import com.wellsoft.pt.repository.support.convert.JacobPDFConverter;
import com.wellsoft.pt.repository.support.convert.PDFConverter;
import com.wellsoft.pt.repository.support.convert.SWFConverter;
import com.wellsoft.pt.repository.support.convert.SWFToolsSWFConverter;

/**
 * Description: 文件上传转换成swf的工具类
 *  
 * @author jackCheng
 * @date 2013-4-17
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-4-17.1	jackCheng		2013-4-17		Create
 * </pre>
 *
 */
public class FileUploadHandler implements Runnable {
	Logger logger = Logger.getLogger(FileUploadHandler.class);
	/**待转换的文档集合*/
	private List<java.io.File> docFiles;
	private InputStream fileStream;
	private String fileName;
	private PDFConverter pdfConverter = new JacobPDFConverter();
	private SWFConverter swfConverter = new SWFToolsSWFConverter();
	private DocConverter converter = new DocConverter(pdfConverter, swfConverter);
	public final static String tempPath = Config.APP_DATA_DIR + "/pdf2swf";

	static {
		//初始化确认文件夹是否存在，不存在则创建该文件夹
		File checkFolderExist = new File(tempPath);
		if (!checkFolderExist.exists()) {
			checkFolderExist.mkdirs();
			checkFolderExist = null;
		}
	}

	public void getPdfFile() {
		FileEntity fileEntity = new FileEntity();
		if (null == docFiles || docFiles.size() == 0) {
			if (fileStream != null) {
				String downLoadPath = tempPath + File.separator + fileName;
				File file = new File(downLoadPath);
				try {
					OutputStream os1 = new FileOutputStream(file);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];

					while ((bytesRead = fileStream.read(buffer, 0, 8192)) != -1) {
						os1.write(buffer, 0, bytesRead);
					}
					os1.close();
					fileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				converter.convertPdf(file.getAbsolutePath(), fileName);
			}
		}
	}

	public FileEntity getSwfFile() {
		FileEntity fileEntity = new FileEntity();
		if (null == docFiles || docFiles.size() == 0) {
			if (fileStream != null) {
				String downLoadPath = tempPath + File.separator + fileName;
				File file = new File(downLoadPath);
				try {
					OutputStream os1 = new FileOutputStream(file);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];

					while ((bytesRead = fileStream.read(buffer, 0, 8192)) != -1) {
						os1.write(buffer, 0, bytesRead);
					}
					os1.close();
					fileStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
				fileEntity = converter.convert(file.getAbsolutePath(), fileName);
			}
		}
		return fileEntity;
		//		for (java.io.File file : docFiles) {
		//			String filePath = file.getAbsolutePath();
		//			converter.convert(filePath);
		//		}
		//		docFiles.clear();
	}

	public FileUploadHandler() {
	}

	public FileUploadHandler(List<java.io.File> docFiles) {
		this.docFiles = docFiles;
	}

	public FileUploadHandler(InputStream fileStream, String fileName) {
		this.fileStream = fileStream;
		this.fileName = fileName;
	}

	public FileEntity test(InputStream fileStream, String fileName) {
		this.fileStream = fileStream;
		this.fileName = fileName;
		return getSwfFile();
	}

	public void test2(InputStream fileStream, String fileName) {
		this.fileStream = fileStream;
		this.fileName = fileName;
		getPdfFile();
	}

	/** 
	 * 如何描述该方法
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

	}

}
