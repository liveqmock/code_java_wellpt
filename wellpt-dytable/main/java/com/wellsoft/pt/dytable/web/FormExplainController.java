/*
 * @(#)2012-11-2 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.RootFormDataBean;
import com.wellsoft.pt.dytable.service.FormDataService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;

/**
 * Description: 动态表单解释Controller类
 * 
 * @author jiangmb
 * @date 2012-11-2
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-11-2.1	jiangmb		2012-11-2		Create
 * </pre>
 * 
 */
@Controller
@RequestMapping(value = "/dytable")
public class FormExplainController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(FormExplainController.class);
	@Autowired
	private FormDefinitionService formDefinitionService;
	@Autowired
	private FormDataService formDataService;

	private PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	/**
	 * 动态表单解释入口
	 * 
	 * @param formUid
	 * @param dataUid
	 * @return
	 */
	@RequestMapping("/form_explain_index")
	public String formExplainIndex(@RequestParam("formUid") String formUid,
			@RequestParam(value = "dataUid", required = false) String dataUid, Model model) {
		model.addAttribute("formUid", formUid);
		model.addAttribute("dataUid", dataUid);
		return forward("/dytable/form_explain");
	}

	/**
	 * 展示查看的预览页面
	 * 
	 * @param formUid
	 * @param dataUid
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/file_show")
	public String fileShow(@RequestParam("modulename") String modulename, @RequestParam("nodename") String nodename,
			@RequestParam("fileName") String fileName, Model model) throws UnsupportedEncodingException {
		//		String fileName2 = new String(fileName);//
		//		String[] fileName3 = fileName2.split("\\.");
		String fileNameMd5 = passwordEncoder.encodePassword(fileName + modulename, null);
		//		for (int i = 0; i < fileName3.length; i++) {
		model.addAttribute("fileName", fileNameMd5);
		//		}
		model.addAttribute("modulename", modulename);
		model.addAttribute("nodename", nodename);
		return forward("/dytable/file_view");
	}

	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取表单定义信息与主表单数据
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/pre_explain_form", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	FormAndDataBean preEditForm(@RequestParam("formUid") String formUid,
			@RequestParam(value = "dataUid", required = false) String dataUid) {
		if (logger.isDebugEnabled()) {
			logger.debug("将要解释的表单formUid:" + formUid + ";主表数据dataUid:" + dataUid);
		}
		FormAndDataBean form = formDataService.getFormData(formUid, dataUid, null);
		Object value = form.getFieldValueByMappingName("File_ZRZ");
		System.out.println("File_ZRZ value: " + value);

		String newDataUuid = formDataService.copyFormData(formUid, dataUid, formUid);
		FormAndDataBean newForm = formDataService.getFormData(formUid, newDataUuid, null);
		Object newValue = newForm.getFieldValueByMappingName("File_ZRZ");
		System.out.println("File_ZRZ value: " + newValue);
		//		Object value2 = formDataService.getFieldValueByMappingName(formUid, dataUid, "Plan_ZRZ");
		//		System.out.println(value2);
		//		Object value3 = formDataService.getFieldInfosByMappingName(formUid, dataUid, "Plan_ZRZ");
		//		System.out.println(value3);
		//
		//		formDataService.getSubFormDataInfo(formUid, dataUid);
		//
		//		String tableName1 = "main_table";
		//		List<String> hidden1 = new ArrayList<String>();
		//		// hidden1.add("username");
		//
		//		List<String> readonly1 = new ArrayList<String>();
		//		readonly1.add("insert_time");
		//
		//		List<String> requierd1 = new ArrayList<String>();
		//		requierd1.add("insert_time");
		//
		//		form.setFieldHiddens(tableName1, hidden1);
		//		form.setFieldOnlyReads(tableName1, readonly1);
		//		form.setFieldRequireds(tableName1, requierd1);

		return form;
	}

	/**
	 * 保存表单数据
	 * 
	 * @param rootFormData
	 * @return
	 */
	@RequestMapping(value = "/save_form_data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String saveFormData(@RequestBody RootFormDataBean rootFormData) {
		String uuid = (String) formDataService.save(rootFormData).get("uuid");
		logger.debug(uuid);
		return "1";
	}

	/**
	 * 获取单个表数据
	 * 
	 * @param tableType
	 * @param tableName
	 * @param dataUid
	 * @return
	 */
	@RequestMapping(value = "/get_data_list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List getDataList(@RequestParam("tableType") String tableType, @RequestParam("tableName") String tableName,
			@RequestParam("dataUid") String dataUid) {
		return formDataService.getFormDataList(tableType, tableName, dataUid);
	}

	/**
	 * 删除表单数据
	 * 
	 * @param formUid
	 *            表单定义uuid
	 * @param dataUid
	 *            主表数据uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String deleteFormData(@RequestParam("formUid") String formUid, @RequestParam("dataUid") String dataUid) {
		formDataService.delete(formUid, dataUid);
		return "1";
	}
}