/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wellsoft.pt.common.component.jqgrid.JqGridQueryController;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.exception.WellException;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dytable.service.DynamicFormConfigService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.utils.encode.JsonBinder;

/**
 * Description: 动态表单定义Controller类
 *  
 * @author jiangmb
 * @date 2012-10-30
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2012-10-31.1	jiangmb		2012-10-31		Create
 * 2014-5-8.1	hunt		2014-5-8		定义JSON化
 * </pre>
 *
 */
@Controller
@RequestMapping("/dyform")
public class DyFormDefinitionController extends JqGridQueryController {
	private Logger logger = LoggerFactory.getLogger(DyFormDefinitionController.class);

	@Autowired
	private FormDefinitionService formDefinitionService;

	@Autowired
	private DyFormDefinitionService dyFormDefinitionService;

	@Autowired
	private DynamicFormConfigService dynamicFormConfigService;

	@RequestMapping("/demo/openFormDefinition")
	public String addFormDefinition(@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "flag", required = false) String flag, Model model) {
		model.addAttribute("flag", flag);
		model.addAttribute("uuid", uuid);
		return forward("/dyform/dyform_definition2");
	}

	/**
	 * 保存表单信息
	 * 
	 * @param rootTableInfo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save_form_by_html", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody String formDefinitionJson) throws Exception {

		DyFormDefinition formDefinition = JsonBinder.buildNormalBinder().fromJson(formDefinitionJson,
				DyFormDefinition.class);
		formDefinition.setUuid(null);
		//String defintionJson = formDefinition.getDefinitionJson();

		if (this.dyFormDefinitionService.isFormExistByFormOuterId(formDefinition.getOuterId())) {

			throw new WellException("table outerId[" + formDefinition.getOuterId() + "] has exist in ");
		}

		if (this.dyFormDefinitionService.isFormExistByFormTblName(formDefinition.getName())) {
			throw new WellException("tableName[" + formDefinition.getName() + "] has exist  ");
		}

		//保存表单数据表定义信息并生成表单数据表
		this.dyFormDefinitionService.createFormDefinitionAndFormTable(formDefinition);

		return "1";
	}

	/**
	 * 更新表单定义信息
	 * 
	 * @param rootTableInfo
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update_form_by_html", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String update(@RequestBody DyFormDefinition formDefinition) throws IOException {

		//更新表单数据表定义信息及表单数据表
		this.dyFormDefinitionService.createFormDefinitionAndFormTable(formDefinition);

		return "1";
	}

	/**
	 * 获取表单定义
	 * @param fieldDefinitionService 
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/getFormDefinition", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String findFormDefinition(@RequestParam("formUuid") String formUuid) {
		DyFormDefinition formDef = this.dyFormDefinitionService.findDyFormDefinitionByFormUuid(formUuid);
		return formDef.getDefinitionJson();
	}

	/**
	 * 编辑表单定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editable/form/definition/list")
	public String ediableFormDefinitionList(Model model) {
		model.addAttribute("flag", 1);
		return forward("/dyform/dyform_definition");
	}

	@RequestMapping(value = "/list/tree")
	public @ResponseBody
	JqGridQueryData listAsTreeJson(@RequestParam(value = "flag") String flag, HttpServletRequest request,
			JqGridQueryInfo jqGridQueryInfo) {
		QueryInfo queryInfo = buildQueryInfo(jqGridQueryInfo, request);
		QueryData queryData = dyFormDefinitionService.getForPageAsTree(jqGridQueryInfo, queryInfo, flag);
		JqGridQueryData jqGridQueryData = convertToJqGridQueryData(queryData);

		System.out.println(JsonBinder.buildNormalBinder().toJson(jqGridQueryData));
		//System.out.println(Jso);
		return jqGridQueryData;
	}

	@RequestMapping("/demo")
	public String open(@RequestParam("formUuid") String formUid,
			@RequestParam(value = "dataUuid", required = false) String dataUid, Model model) {
		model.addAttribute("formUuid", formUid);
		model.addAttribute("dataUuid", dataUid);
		return forward("/dyform/dyform_demo");
	}

}
