/*
 * @(#)2014-6-13 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.excelexporttemplate.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import bitronix.tm.utils.ExceptionUtils;

import com.wellsoft.pt.basicdata.excelexporttemplate.entity.ExcelExportDefinition;
import com.wellsoft.pt.basicdata.excelexporttemplate.service.ExcelExportRuleService;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.repository.entity.mongo.file.MongoFileEntity;
import com.wellsoft.pt.repository.service.MongoFileService;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2014-6-13
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-6-13.1	wubin		2014-6-13		Create
 * </pre>
 *
 */
@Controller
@RequestMapping("/basicdata/excelexportrule")
public class ExcelExportController extends BaseController {

	@Autowired
	private MongoFileService mongoFileService;
	@Autowired
	private ExcelExportRuleService excelExportRuleService;

	/**
	 * 
	 * 跳转到管理界面的excel导出规则界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	public String excelExport() {
		return forward("/basicdata/excelexportrule/excelexportrule");
	}

	/**
	 * 
	 * 上传excel导出模版
	 * 
	 * @param upload
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	@ResponseBody
	public void uploadExcel(@RequestParam(value = "upload") MultipartFile upload, HttpServletResponse response)
			throws IOException {
		String uuid = UUID.randomUUID().toString();
		//		上传处理
		mongoFileService.popAllFilesFromFolder(uuid);
		MongoFileEntity file = mongoFileService.saveFile(uuid, upload.getInputStream());
		mongoFileService.pushFileToFolder(uuid, file.getId(), "attach");

		response.setContentType(MediaType.TEXT_HTML_VALUE);
		response.getWriter().write(uuid);
		response.getWriter().flush();
		response.getWriter().close();
	}

	//读取处理
	@RequestMapping(value = "/downloadImage")
	@ResponseBody
	public void downloadImage(@RequestParam("uuid") String uuid, HttpServletRequest request,
			HttpServletResponse response) {
		ExcelExportDefinition excelExportDefinition = excelExportRuleService.getExcelExportDefinition(uuid);
		if (excelExportDefinition.getFileUuid() != null && !"".equals(excelExportDefinition.getFileUuid())) {
			List<MongoFileEntity> files = mongoFileService.getFilesFromFolder(excelExportDefinition.getFileUuid(),
					"attach");
			if (files == null || files.size() == 0) {

			}
			MongoFileEntity mongoFileEntity = files.get(0);
			InputStream inputStream = mongoFileEntity.getInputstream();
			String fileName = excelExportDefinition.getName();
			FileDownloadUtils.download(request, response, inputStream, fileName + ".xls");
		} else {
			ServletOutputStream os;
			try {
				os = response.getOutputStream();
				os.print("<script type='text/javascript'>alert('该规则未上传模版');</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/export")
	@ResponseBody
	public void exportData(@RequestParam(value = "viewUuid") String viewUuid,
			@RequestParam(value = "dataArray", required = false) String[] dataArray, HttpServletRequest request,
			HttpServletResponse response) {
		File file = excelExportRuleService.generateExcelFile(viewUuid, dataArray);
		try {
			FileDownloadUtils.download(request, response, new FileInputStream(file), file.getName());
			if (file.exists()) {
				file.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			FileDownloadUtils.download(request, response, new ByteArrayInputStream(ExceptionUtils.getStackTrace(e)
					.getBytes()), "error.txt");
		}
	}

}