/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dytable.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.Configuration;
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
import org.springframework.web.multipart.MultipartFile;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.common.bean.LabelValueBean;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryController;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.dytable.bean.FormAndDataBean;
import com.wellsoft.pt.dytable.bean.RootTableInfoBean;
import com.wellsoft.pt.dytable.bean.TreeNodeBean;
import com.wellsoft.pt.dytable.entity.FormDefinition;
import com.wellsoft.pt.dytable.facade.DytableApiFacade;
import com.wellsoft.pt.dytable.service.DynamicFormConfigService;
import com.wellsoft.pt.dytable.service.FieldDefinitionService;
import com.wellsoft.pt.dytable.service.FormDefinitionService;
import com.wellsoft.pt.dytable.support.HibernateUtil;
import com.wellsoft.pt.dytable.support.TableConfig;
import com.wellsoft.pt.dytable.utils.DynamicUtils;
import com.wellsoft.pt.utils.web.FileDownloadUtils;

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
@RequestMapping("/dytable")
public class FormDefinitionController extends JqGridQueryController {
	private Logger logger = LoggerFactory.getLogger(FormDefinitionController.class);
	@Autowired
	private FormDefinitionService formDefinitionService;
	@Autowired
	private FieldDefinitionService fieldDefinitionService;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;
	@Autowired
	private DynamicFormConfigService dynamicFormConfigService;
	@Autowired
	private DytableApiFacade dytableApiFacade;

	@RequestMapping("/demo")
	public String open(@RequestParam("formUid") String formUid,
			@RequestParam(value = "dataUid", required = false) String dataUid, Model model) {
		model.addAttribute("formUuid", formUid);
		model.addAttribute("dataUuid", dataUid);
		return forward("/dytable/form_demo");
	}

	@RequestMapping("/grid/demo")
	public String grid() {

		return forward("/dytable/grid_demo");
	}

	@RequestMapping("/demo/get/data")
	@ResponseBody
	public FormAndDataBean getData(@RequestParam("formUuid") String formUuid,
			@RequestParam(value = "dataUuid", required = false) String dataUuid) {
		return dytableApiFacade.getFormData(formUuid, dataUuid);
	}

	@RequestMapping("/demo/openFormDefinition")
	public String openFormDefinition(@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "flag", required = false) String flag, Model model) {
		model.addAttribute("flag", flag);
		model.addAttribute("uuid", uuid);
		return forward("/dytable/form_definition2");
	}

	/**
	 * 编辑表单定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editable/form/definition/list")
	public String ediableFormDefinitionList(Model model) {
		model.addAttribute("flag", 1);
		return forward("/dytable/form_definition");
	}

	/**
	 * 显示表单定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "/display/form/definition/list")
	public String displayFormDefinitionList(Model model) {
		model.addAttribute("flag", 2);
		return forward("/dytable/form_definition");
	}

	//	/**
	//	 * 动态表单定义入口地址
	//	 * 
	//	 * @return
	//	 */
	//	@RequestMapping(value = "/form_definition_index", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//	public String formDefinitionIndex(@RequestParam("flag") String flag, Model model) {
	//		model.addAttribute("flag", flag);
	//		return forward("/dytable/form_definition");
	//	}

	/**
	 * 动态表单定义入口地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "/form_definition_index", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String formDefinitionIndex(@RequestParam("flag") String flag, Model model) {
		model.addAttribute("flag", flag);
		return forward("/dytable/form_definition");
	}

	/**
	 * 获取动态表单定义下拉列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/form_all_list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<LabelValueBean> findFormAllList() {
		List<FormDefinition> formList = formDefinitionService.getAllTopDynamicFormList();
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (null != formList && formList.size() > 0) {
			for (FormDefinition form : formList) {
				list.add(new LabelValueBean(form.getUuid(), form.getDescname() + "(" + form.getVersion() + ")"));
			}
		}
		return list;
	}

	/**
	 * 表单对应字段列表
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/field_list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<LabelValueBean> findFiledList(@RequestParam("tableId") String tableId) {

		return fieldDefinitionService.getFieldLavelValueBean(tableId);
	}

	/**
	 * 表单对应字段列表
	 * 
	 * @param tableId
	 * @return
	 */
	@RequestMapping(value = "/field_list2", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<LabelValueBean> findFieldNameAndValueList(@RequestParam("tableId") String tableId) {

		return fieldDefinitionService.getFieldNameAndValueLavelValueBean(tableId);
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
	public @ResponseBody
	String save(@RequestBody RootTableInfoBean rootTableInfo, HttpServletRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			//return "1";
		}
		String tempHtmlPath = "";
		rootTableInfo.getTableInfo().getTempHtmlPath();
		if (StringUtils.isNotEmpty(tempHtmlPath)) {
			String htmlFullPath = request.getSession().getServletContext().getRealPath("") + tempHtmlPath;
			if (logger.isDebugEnabled()) {
				logger.debug("添加模板html文件完整路径:" + htmlFullPath);
			}
			String htmlBodyContent = DynamicUtils.getHtmlBodyContent(htmlFullPath);
			rootTableInfo.getTableInfo().setHtmlBodyContent(htmlBodyContent);
		}
		formDefinitionService.saveFormDefinition(rootTableInfo);

		// 根据配置信息创建表
		String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("用户配置后生成相应的hbm文件内容(新建):" + hbmCfgXml);
		}
		Configuration config = new Configuration();
		config.addXML(hbmCfgXml);
		dynamicFormConfigService.addNewConfig(config);
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.addTable();

		if (logger.isDebugEnabled()) {
			logger.debug("table's uuid is " + rootTableInfo.getTableInfo().getUuid());
		}
		return "1";
	}

	/**
	 * 获取表单定义信息对象
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/pre_edit_form", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	RootTableInfoBean preEditForm(@RequestParam("uuid") String uuid) {
		if (logger.isDebugEnabled()) {
			logger.debug("将要编辑的表单uuid:" + uuid);
		}

		RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(uuid, true, false);
		return rootTableInfo;
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
	String update(@RequestBody RootTableInfoBean rootTableInfo, HttpServletRequest request) throws IOException {
		String tempHtmlPath = rootTableInfo.getTableInfo().getTempHtmlPath();
		if (logger.isDebugEnabled()) {
			//return "1";
		}
		if (StringUtils.isNotEmpty(tempHtmlPath)) {
			String htmlFullPath = request.getSession().getServletContext().getRealPath("") + tempHtmlPath;
			if (logger.isDebugEnabled()) {
				logger.debug("修改模板html文件完整路径:" + htmlFullPath);
			}
			String htmlBodyContent = DynamicUtils.getHtmlBodyContent(htmlFullPath);
			rootTableInfo.getTableInfo().setHtmlBodyContent(htmlBodyContent);
		}
		formDefinitionService.updateupdFormDefinition(rootTableInfo);

		// 根据配置信息修改表结构
		String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("用户配置后生成相应的hbm文件内容(修改):" + hbmCfgXml);
		}
		Configuration config = new Configuration();
		config.addXML(hbmCfgXml);
		dynamicFormConfigService.addNewConfig(config);
		TableConfig tableconfig = new TableConfig(config);
		tableconfig.updateTable();

		return "1";
	}

	/**
	 * 根据传入的字典类型获取字典的属性
	 * 如何描述该方法
	 * 
	 * @param type
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getData_dictionary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<DataDictionary> getDataDictionary(@RequestBody String type, HttpServletRequest request) throws IOException {
		List<DataDictionary> datas = basicDataApiFacade.getDataDictionariesByType(type);
		return datas;
	}

	/**
	 * 删除表单定义信息
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete_form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String delete(@RequestParam("uuid") String uuid) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("将要删除表单的uuid为:" + uuid);
		}
		RootTableInfoBean rootTableInfo = formDefinitionService.getRootTableInfoByUuid(uuid, false, false);

		boolean deleteTableStruct = formDefinitionService.deleteFormDefinition(uuid);
		// 删除表结构(没有其它版本在使用此表)
		if (deleteTableStruct) {
			String hbmCfgXml = HibernateUtil.getHbmCfgXml(rootTableInfo);
			if (logger.isDebugEnabled()) {
				logger.debug("用户配置后生成相应的hbm文件内容(删除):" + hbmCfgXml);
			}
			Configuration config = new Configuration();
			config.addXML(hbmCfgXml);
			dynamicFormConfigService.addNewConfig(config);
			TableConfig tableconfig = new TableConfig(config);
			tableconfig.deleteTable();
		}
		return "1";
	}

	/**
	 * 上传模板文件
	 * 
	 * @param uploadFile
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload_html_file", method = RequestMethod.POST)
	public void uploadHtml(@RequestParam(value = "uploadFile") MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int result = 1;
		String htmlPath = "";
		String fileName = "";
		String fullPath = "";
		if (uploadFile.isEmpty()) {
			result = 0;
			logger.info("文件未上传");
		} else {
			logger.info("文件名称:" + uploadFile.getName());
			logger.info("文件大小:" + uploadFile.getSize());
			logger.info("文件类型:" + uploadFile.getContentType());
			logger.info("文件原名:" + uploadFile.getOriginalFilename());

			fileName = uploadFile.getOriginalFilename();
			String webPath = "/pt/upload/" + System.currentTimeMillis() + "/";
			htmlPath = request.getContextPath() + webPath + "/" + fileName;
			String realPath = request.getSession().getServletContext().getRealPath(webPath);
			logger.info("存储路径:" + realPath);
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(realPath, fileName));

			fullPath = webPath + "/" + fileName;
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("{'result':'" + result + "','htmlPath':'" + htmlPath + "','fileName':'" + fileName + "','filePath':'"
				+ fullPath + "'}");
	}

	/**
	 * 
	 * 下载模板文件
	 * @throws FileNotFoundException 
	 *
	 */
	@RequestMapping(value = "/downloadTemp")
	@ResponseBody
	public void downloadTemp(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

		String path = "/resources/form/表单样例模板.html";
		String spath = request.getServletContext().getRealPath(path);
		File file = new File(spath);
		InputStream inputStream = new FileInputStream(file);
		String fileName = "表单样例模板.html";
		//		InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
		FileDownloadUtils.download(request, response, inputStream, fileName);
	}

	/**
	 * 获取用户模板html内容
	 * 
	 * @param tempHtmlPath
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_html_body", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map<String, String> getHtmlBody(@RequestParam("tempHtmlPath") String tempHtmlPath, HttpServletRequest request) {
		String htmlPath = request.getSession().getServletContext().getRealPath("") + tempHtmlPath;
		String str = DynamicUtils.getHtmlBodyContent(htmlPath);
		Map<String, String> map = new HashMap<String, String>();
		map.put("htmlContent", str);
		return map;
	}

	/**
	 * 获取表单定义树
	 * 
	 * @param id
	 * @param selectedId
	 * @return
	 */
	@RequestMapping(value = "/get_full_tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<TreeNodeBean> getFullTree(@RequestParam(value = "id", required = false) String id,
			@RequestParam("selectedId") String selectedId) {
		return formDefinitionService.getFullTree(id, selectedId);
	}

	@RequestMapping(value = "/list/tree")
	public @ResponseBody
	JqGridQueryData listAsTreeJson(@RequestParam(value = "flag") String flag, HttpServletRequest request,
			JqGridQueryInfo jqGridQueryInfo) {
		QueryInfo queryInfo = buildQueryInfo(jqGridQueryInfo, request);
		QueryData queryData = formDefinitionService.getForPageAsTree(jqGridQueryInfo, queryInfo, flag);
		return convertToJqGridQueryData(queryData);
	}

	@RequestMapping(value = "/list/getSerialNumberIdList")
	public @ResponseBody
	List<String> getSerialNumberIdList() {
		return basicDataApiFacade.getSerialNumberIdList();
	}

	@RequestMapping(value = "/list/getSerialNumberTypeList")
	public @ResponseBody
	List<String> getSerialNumberTypeList() {
		return basicDataApiFacade.getSerialNumberTypeList();
	}

	@RequestMapping(value = "/test")
	@ResponseBody
	public Map<Integer, List<Map<String, String>>> test() {
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("project_id", "001001");
		list.add(map);
		return formDefinitionService.verificationDytableData("acebfc9e-af6a-479e-bc01-9029ac0c2e10", list);
	}
}
