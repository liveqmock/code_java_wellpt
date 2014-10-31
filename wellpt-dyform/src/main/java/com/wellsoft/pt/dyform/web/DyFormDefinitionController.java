/*
 * @(#)2012-10-30 V1.0
 * 
 * Copyright 2012 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.HibernateException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceDefinition;
import com.wellsoft.pt.basicdata.datasource.facade.DataSourceApiFacade;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryController;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryInfo;
import com.wellsoft.pt.common.enums.JsonDataErrorCode;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryData;
import com.wellsoft.pt.core.support.QueryInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.web.FaultMessage;
import com.wellsoft.pt.core.web.ResultMessage;
import com.wellsoft.pt.dyform.entity.DyFormDefinition;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.service.DyFormDefinitionService;
import com.wellsoft.pt.dyform.support.exception.hibernate.HibernateDataExistException;
import com.wellsoft.pt.dyform.support.exception.hibernate.HibernateDdlException;
import com.wellsoft.pt.org.entity.User;
import com.wellsoft.pt.org.facade.OrgApiFacade;
import com.wellsoft.pt.org.service.UserService;
import com.wellsoft.pt.utils.encode.JsonBinder;
import com.wellsoft.pt.utils.json.JsonUtils;

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
	private DyFormDefinitionService dyFormDefinitionService;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	@Autowired
	private OrgApiFacade orgApiFacade;

	@RequestMapping("/demo/openFormDefinition")
	public String addFormDefinition(@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "flag", required = false) String flag, Model model) {
		model.addAttribute("flag", flag);
		model.addAttribute("uuid", uuid);
		return forward("/dyform/dyform_definition2");
	}

	@RequestMapping("/demo/exportAsHtml")
	public String exportAsHtml(@RequestParam(value = "uuid", required = true) String uuid, Model model) {

		return null;
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
	public ResponseEntity<ResultMessage> save(@RequestParam(value = "formDefinition") String formDefinitionJson)
			throws Exception {
		DyFormDefinition formDefinition = JsonUtils.json2Object(formDefinitionJson, DyFormDefinition.class);

		formDefinition.setUuid(null);
		//String defintionJson = formDefinition.getDefinitionJson();

		if (this.dyFormDefinitionService.isFormExistByFormOuterId(formDefinition.getOuterId())) {
			return getFaultResultMsg("table outerId[" + formDefinition.getOuterId() + "] has exist in ",
					JsonDataErrorCode.FormDefinitionSaveException);

		}

		if (this.dyFormDefinitionService.isFormExistByFormTblName(formDefinition.getName())) {
			return getFaultResultMsg("tableName[" + formDefinition.getName() + "] has exist  ",
					JsonDataErrorCode.FormDefinitionUpdateException);

		}

		try {
			//保存表单数据表定义信息并生成表单数据表
			this.dyFormDefinitionService.createFormDefinitionAndFormTable(formDefinition);
		} catch (HibernateDdlException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		}

		return getSucessfulResultMsg(formDefinition.getUuid());
	}

	/**
	 * 更新表单定义信息
	 * 
	 * @param rootTableInfo
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/update_form_by_html", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<ResultMessage> update(@RequestParam(value = "formDefinition") String formDefinition,
			@RequestParam(value = "deletedFieldNames") String deletedFieldNames) {

		//更新表单数据表定义信息及表单数据表
		DyFormDefinition dy = null;
		try {
			dy = JsonUtils.json2Object(formDefinition, DyFormDefinition.class);
			this.dyFormDefinitionService.updateFormDefinitionAndFormTable(dy,
					JsonBinder.buildNormalBinder().fromJson(deletedFieldNames, List.class));
		} catch (HibernateDdlException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		}

		return getSucessfulResultMsg(dy.getUuid());
	}

	/**
	 * 删除表单定义
	 * 
	 * @param rootTableInfo
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete_form", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<ResultMessage> delete(@RequestParam(value = "formUuid") String formUuid) {
		try {
			this.dyFormApiFacade.dropForm(formUuid);
		} catch (HibernateDataExistException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getFaultResultMsg(e.getMessage(), JsonDataErrorCode.FormDefinitionUpdateException);
		}

		return getSucessfulResultMsg("删除成功");
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
		DyFormDefinition formDef = //this.dyFormDefinitionService.findDyFormDefinitionByFormUuid(formUuid);
		this.dyFormApiFacade.getFormDefinition(formUuid);
		return formDef.getDefinitionJson();
	}

	@RequestMapping(value = "/getFormDefinitions", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String findFormDefinitions(@RequestParam("formUuids") String formUuidsJSON,
			@RequestParam("formUuid") String formUuid) {

		JSONObject datas = new JSONObject();

		List<String> formUuids = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(formUuidsJSON);
			for (int i = 0; i < array.length(); i++) {
				formUuids.add(array.getString(i));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		List<DyFormDefinition> formDefs = this.dyFormApiFacade.getFormDefinition(formUuids);
		List<String> jsons = new ArrayList<String>();
		for (DyFormDefinition dfd : formDefs) {
			jsons.add(dfd.getDefinitionJson());
		}

		try {
			datas.put("definition", jsons);
			Map<String, Object> defaultFormData = this.dyFormApiFacade.getDefaultFormData(formUuid);
			datas.put("formdata", defaultFormData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//JSONArray array = new JSONArray(jsons);

		return datas.toString();
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

	@SuppressWarnings("static-method")
	protected ResponseEntity<ResultMessage> getSucessfulResultMsg(Object data) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setSuccess(true);
		resultMessage.setData(data);
		return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.OK);
	}

	@SuppressWarnings("static-method")
	protected ResponseEntity<ResultMessage> getFaultResultMsg(Object data, JsonDataErrorCode jsonDataErrorCode) {
		FaultMessage resultMessage = new FaultMessage();
		resultMessage.setSuccess(false);
		resultMessage.setData(data);
		resultMessage.setErrorCode(jsonDataErrorCode.toString());
		return new ResponseEntity<ResultMessage>(resultMessage, HttpStatus.EXPECTATION_FAILED);
	}

	/**
	 * 上传设计模板
	 * @param request
	 * @param fileUpload
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadDesignTemplate", method = RequestMethod.POST)
	public void uploadDesignTemplate(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws IOException {
		ResultMessage resultMessage = new ResultMessage();
		MultipartFile multifile = multipartRequest.getFile(multipartRequest.getFileNames().next());
		String filename = multifile.getOriginalFilename();
		InputStream multifileIS = multifile.getInputStream();
		String contentType = multifile.getContentType();
		long size = multifile.getSize();
		IOUtils.closeQuietly(multifileIS);
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
		OutputStream os = response.getOutputStream();
		IOUtils.copyLarge(multifileIS, os);
		os.flush();
		IOUtils.closeQuietly(os);
	}

	/**
	 * 获取所有的数据源
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getDataSources", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String getDataSources() throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
		JSONArray array = new JSONArray();
		List<DataSourceDefinition> dsds = dataSourceApiFacade.getAll();
		array.put(dsds);
		return array.toString();
	}

	/**
	 * 获取指定的数据源的所有列定义
	 * 
	 * @return
	 * @throws JSONException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getDataSourceColumns", method = RequestMethod.POST)
	public @ResponseBody
	String getDataSourceColumns(@RequestParam("dsId") String dsId) throws JSONException, JsonParseException,
			JsonMappingException, IOException, ParseException {
		JSONArray array = new JSONArray();
		List<DataSourceColumn> columns = this.dataSourceApiFacade.getDataSourceShowTitleNameFieldsById(dsId);
		array.put(columns);
		return array.toString();
	}

	@RequestMapping(value = "/autoComplete", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String autoComplete(@RequestParam("searchText") String searchText, @RequestParam("fields") String fields,
			@RequestParam("dataSourceId") String dataSourceId, @RequestParam("contraint") String contraint,
			@RequestParam("pageSize") String pageSize) throws JSONException, JsonParseException, JsonMappingException,
			IOException, ParseException {
		if (pageSize == null || pageSize.trim().length() == 0 || pageSize.trim().equals("undefined")) {
			pageSize = "300";
		}
		List<String> fieldList = JsonBinder.buildNormalBinder().fromJson(fields, List.class);
		Set<DataSourceColumn> set = new HashSet<DataSourceColumn>();
		Collection<DataSourceColumn> DataSourceColumns = new ArrayList<DataSourceColumn>();
		Map<String, Object> map = new HashMap<String, Object>();

		StringBuffer whereSql = new StringBuffer();

		JSONObject contraintJ = new JSONObject(contraint);
		Iterator<String> it = contraintJ.keys();
		boolean isContraint = false;//是否有加约束条件
		whereSql.append("( 1=1 ");
		Object[] obj = dataSourceApiFacade.custom(dataSourceId, new String[] {});
		boolean sapDs = false;
		if (obj != null && obj.length > 0 && ((String) obj[0]).equalsIgnoreCase("sap")) {//sap接口
			sapDs = true;
		}
		while (it.hasNext()) {
			String fieldName = it.next();
			String value = contraintJ.getString(fieldName);
			if (value != null && value.trim().length() > 0) {
				whereSql.append(" and ").append(fieldName).append(" = :").append(fieldName);
				if (sapDs) {//sap接口
					map.put(fieldName + "___MUST", value);
				} else {
					map.put(fieldName, value);
				}

				DataSourceColumn fieldC = new DataSourceColumn();
				fieldC.setFieldName(fieldName);
				fieldC.setColumnAliase(fieldName);
				fieldC.setColumnName(fieldName);
				//fieldC.setColumnDataType(ViewColumnType.STRING.name());
				DataSourceColumns.add(fieldC);
			}

		}
		if (isContraint) {
			whereSql.append(") and (1 = 2 ");
		} else {
			whereSql.append(") and (1 = 2 ");
		}

		String orderField = "";
		for (String fieldName : fieldList) {

			DataSourceColumn fieldC = new DataSourceColumn();
			fieldC.setFieldName(fieldName);
			fieldC.setColumnAliase(fieldName);
			fieldC.setColumnName(fieldName);
			//fieldC.setColumnDataType(ViewColumnType.STRING.name());
			DataSourceColumns.add(fieldC);
			if (searchText == null || searchText.trim().length() == 0) {
				whereSql.append(" or 1 = 1 ");
			} else {
				whereSql.append(" or ").append(fieldName).append(" like '%'||:").append(fieldName).append("||'%'  ");
				map.put(fieldName, searchText);
			}

			orderField = fieldName;
		}
		whereSql.append(")");
		//DataSourceDefinition d = null;
		set.addAll(DataSourceColumns);
		JSONArray array = new JSONArray();//test_jiekou
		PagingInfo pagingInfo = new PagingInfo();
		pagingInfo.setPageSize(Integer.parseInt(pageSize));
		List<QueryItem> items = dataSourceApiFacade.query(dataSourceId, whereSql.toString(), map, "  " + orderField
				+ " asc", pagingInfo);

		int i = 0;
		for (QueryItem item : items) {
			if (i > Integer.parseInt(pageSize)) {//超出分页的条数时直接舍弃
				break;
			}
			JSONObject json = new JSONObject();
			for (String fieldName : fieldList) {
				String fieldname = QueryItem.getKey(fieldName);
				Object val = item.get(fieldname);
				if (val == null) {
					val = "";
				}
				json.put(fieldName, val);
			}
			System.out.println(json.toString());
			array.put(json);
			i++;
		}

		return array.toString();
	}

	@RequestMapping(value = "/loadOrg", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	String loadOrg(@RequestParam("userId") String userId) throws JSONException, JsonParseException,
			JsonMappingException, IOException, ParseException {
		JSONObject jsonobj = new JSONObject();
		String userOrgInfo = userService.getUserOrgInfo(userId);
		/*if (userOrgInfo == null) {
			userOrgInfo = "{}";
		} else {
			Department dept = orgApiFacade.getDepartmentByUserId(userId);
			jsonobj.put("mainDepartmentId", dept.getId());
			jsonobj.put("mainDepartmentName", dept.getName());
		}

		Set<DepartmentUserJob> departmentUserJobs = user.getDepartmentUsers();
		for (DepartmentUserJob du : departmentUserJobs) {
			if (du.getIsMajor()) {
				//du.getDepartment().getde
				jsonobj.put("mainDepartmentId", du.getDepartment().getId());
				jsonobj.put("mainDepartmentName", du.getDepartment().getName());
			}
		}

		Set<UserJob> jobs = user.getUserJobs();
		for (UserJob du : jobs) {
			if (du.getIsMajor()) {
				//du.getDepartment().getde
				jsonobj.put("mainJobId", du.getJob().getId());
				jsonobj.put("mainJobName", du.getJob().getName());
			}
		}

		jsonobj.put("employeeNumber", user.getEmployeeNumber());
		jsonobj.put("user", new JSONObject(user).toString());*/
		return userOrgInfo;
	}

	@Autowired
	DataSourceApiFacade dataSourceApiFacade;
	@Autowired
	UserService userService;

	public static void main(String[] args) throws JSONException {
		User user = new User();
		JSONObject o = new JSONObject(user);
		System.out.println(o.toString());
	}
}
