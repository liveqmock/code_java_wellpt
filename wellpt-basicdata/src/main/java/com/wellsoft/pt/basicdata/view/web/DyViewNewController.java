/*
 * @(#)2013-3-13 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.view.web;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wellsoft.pt.basicdata.datadict.entity.DataDictionary;
import com.wellsoft.pt.basicdata.datasource.entity.DataSourceColumn;
import com.wellsoft.pt.basicdata.datasource.facade.DataSourceApiFacade;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.basicdata.systemtable.entity.SystemTableAttribute;
import com.wellsoft.pt.basicdata.view.bean.ViewAndDataNewBean;
import com.wellsoft.pt.basicdata.view.bean.ViewDefinitionNewBean;
import com.wellsoft.pt.basicdata.view.entity.ColumnCssDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.ColumnDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.ConditionTypeNew;
import com.wellsoft.pt.basicdata.view.entity.CustomButtonNew;
import com.wellsoft.pt.basicdata.view.entity.FieldSelect;
import com.wellsoft.pt.basicdata.view.entity.PageDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.SelectDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.ViewDefinitionNew;
import com.wellsoft.pt.basicdata.view.provider.ViewColumnNew;
import com.wellsoft.pt.basicdata.view.provider.ViewDataSourceNew;
import com.wellsoft.pt.basicdata.view.service.GetViewDataNewService;
import com.wellsoft.pt.basicdata.view.service.ViewDefinitionNewService;
import com.wellsoft.pt.basicdata.view.support.CondSelectAskInfoNew;
import com.wellsoft.pt.basicdata.view.support.DateUtil;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.basicdata.view.support.DyviewConfigNew;
import com.wellsoft.pt.common.component.jqgrid.JqGridQueryData;
import com.wellsoft.pt.common.marker.service.ReadMarkerService;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.template.TemplateEngine;
import com.wellsoft.pt.core.template.TemplateEngineFactory;
import com.wellsoft.pt.core.web.BaseController;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.dyform.support.FieldDefinition;
import com.wellsoft.pt.security.audit.entity.Resource;
import com.wellsoft.pt.security.facade.SecurityApiFacade;
import com.wellsoft.pt.utils.reflection.ConvertUtils;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 视图自定义的控制器
 * 
 * @author wubin
 * @date 2013-3-13
 * @version 1.0
 * 
 *          <pre>
 * 修改记录:
 * 修改后版本		修改人		修改日期			修改内容
 * 2013-3-13.1	Administrator		2013-3-13		Create
 * </pre>
 * 
 */
@Controller
@RequestMapping("/basicdata/view")
public class DyViewNewController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(DyViewNewController.class);

	@Autowired
	private DyFormApiFacade dyFormApiFacade;
	@Autowired
	private ViewDefinitionNewService viewDefinitionNewService;
	@Autowired
	private GetViewDataNewService getViewDataNewService;
	@Autowired
	private SecurityApiFacade securityApiFacade;
	@Autowired
	private BasicDataApiFacade basicDataApiFacade;
	@Autowired
	private ReadMarkerService readMarkerService;
	@Autowired(required = false)
	private Map<String, ViewDataSourceNew> viewDataSourceNewMap;
	@Autowired
	private DataSourceApiFacade dataSourceApiFacade;

	/**
	 * 
	 * 视图自定义入口地址 
	 * @return
	 */
	@RequestMapping("")
	public String viewDefinitionIndex(Model model) {
		//视图自定的编号(存放在数据字典中)
		String code = "003006";
		List<Resource> resources = securityApiFacade.getDynamicButtonResourcesByCode(code);
		model.addAttribute("resources", resources);
		return forward("/basicdata/view/view_definition_new");
	}

	@RequestMapping(value = "/column_definition_list", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	List<FieldDefinition> getColumnList(@RequestParam("formUuid") String formUuid) {
		List<FieldDefinition> fieldDefinitions = dyFormApiFacade.getFormDefinition(formUuid).getFieldDefintions();
		return fieldDefinitions;
	}

	@RequestMapping(value = "/get_select_column", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<DataSourceColumn> getSelectColumn(@RequestParam("formUuid") String formUuid) {
		return dataSourceApiFacade.getDataSourceFieldsById(formUuid);
	}

	@RequestMapping(value = "/get_select_column2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<SystemTableAttribute> getSelectColumn2(@RequestParam("formUuid") String formUuid) {
		return getViewDataNewService.getSystemTableColumns(formUuid);
	}

	@RequestMapping(value = "/get_select_column3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ViewColumnNew> getSelectColumn3(@RequestParam("formUuid") String formUuid) {
		return getViewDataNewService.getViewColumns(formUuid);
	}

	@RequestMapping(value = "/getDataDictionaryByCode")
	@ResponseBody
	public List<DataDictionary> getDataDictionaryByCode(@RequestParam(value = "code", required = false) String code) {
		List<DataDictionary> ddList = basicDataApiFacade.getDataDictionariesByType(code);
		return ddList;
	}

	/**
	 * 
	 * 获取弹出框的中的数据
	 * 
	 * @param formUuid
	 * @param fieldName
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSelectData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JqGridQueryData getSelectData(@RequestParam("tableType") String tableType,
			@RequestParam("formUuid") String formUuid, @RequestParam("fieldName") String fieldName,
			@RequestParam("page") int currentPage, @RequestParam("rows") int pageSize,
			@RequestParam("defaultCondition") String defaultCondition) throws Exception {
		PagingInfo page = new PagingInfo();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		JqGridQueryData data = new JqGridQueryData();
		if (tableType.equals("1")) {
			List<QueryItem> queryItems = viewDefinitionNewService.getSelectData(formUuid, fieldName, page);
			data.setDataList(queryItems);
			data.setCurrentPage(page.getCurrentPage());
			data.setTotalPages((long) Math.ceil(queryItems.size() / page.getPageSize()));
		} else if (tableType.equals("2")) {
			List<Map<String, Object>> queryItems = viewDefinitionNewService.getSelectData2(formUuid, fieldName, page);
			data.setDataList(queryItems);
			data.setCurrentPage(page.getCurrentPage());
			data.setTotalPages((long) Math.ceil(queryItems.size() / page.getPageSize()));
		} else if (tableType.equals("3")) {
			List<ViewColumnNew> viewColumnNews = new ArrayList<ViewColumnNew>();
			Map<String, ViewColumnNew> map = ConvertUtils.convertElementToMap(viewDataSourceNewMap.get(formUuid)
					.getAllViewColumns(), "columnName");
			ViewColumnNew v = map.get(fieldName);
			viewColumnNews.add(v);
			List<QueryItem> data_ = viewDataSourceNewMap.get(formUuid).query(viewColumnNews, defaultCondition,
					new HashMap(), "", page);
			List<QueryItem> newdata = new ArrayList<QueryItem>();
			for (QueryItem q : data_) {
				QueryItem newq = new QueryItem();
				newq.put("value", q.get(v.getColumnAlias()));
				newdata.add(newq);
			}
			data.setDataList(newdata);
			data.setCurrentPage(page.getCurrentPage());
			data.setTotalPages((long) Math.ceil(newdata.size() / page.getPageSize()));
		}
		return data;
	}

	@RequestMapping(value = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViewDataByViewId(@PathVariable(value = "id") String id, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		ViewDefinitionNew viewDefinitionNew = this.viewDefinitionNewService.getByViewId(id);
		redirectAttributes.addAttribute("viewUuid", viewDefinitionNew.getUuid());
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String paramName = e.nextElement();
			redirectAttributes.addAttribute(paramName, request.getParameter(paramName));
		}
		return redirect("/basicdata/view/view_show");
	}

	/**
	 * 
	 * 视图的控制层调用入口
	 * 
	 * @param model
	 * @param viewUuid
	 * @param dyViewQueryInfo
	 * @param expandParams
	 * @param openBy
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view_show", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViewData(Model model, @RequestParam(value = "viewUuid", required = false) String viewUuid,
			@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "viewName", required = false) String viewName,
			@RequestParam(value = "openBy", required = false) String openBy,
			@RequestParam(value = "relationDataDefiantion", required = false) String relationDataDefiantion,
			PagingInfo page, HttpServletRequest request) throws Exception {
		String whereSql = request.getParameter("whereSql");
		DyViewQueryInfoNew dyViewQueryInfoNew = new DyViewQueryInfoNew();
		/************************获取视图的定义bean开始***********************/
		ViewAndDataNewBean viewAndDataNewBean = new ViewAndDataNewBean();
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		if (viewUuid != null && viewUuid != "") {
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		} else {
			ViewDefinitionNew viewDefinitionNew = viewDefinitionNewService.getByViewId(viewName);
			viewUuid = viewDefinitionNew.getUuid();
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		}
		/************************获取视图的定义信息结束***********************/
		//		int datascope = viewDefinitionNewBean.getDataScope();
		int datascope = 0;
		//数据源的ID
		String tableDefinitionId = viewDefinitionNewBean.getTableDefinitionId();
		String tableName = "";
		//启用已读未读属性
		String readKey = viewDefinitionNewBean.getReadKey();
		if (readKey == null || (readKey != null && readKey.equals(""))) {
			readKey = "uuid";
		}
		//复选框的key
		String rowIdKey = StringUtils.isBlank(viewDefinitionNewBean.getCheckKey()) ? "uuid" : viewDefinitionNewBean
				.getCheckKey();
		//获取视图的默认搜索条件
		String defaultCondition = viewDefinitionNewBean.getDefaultCondition();
		if (defaultCondition == null) {
			defaultCondition = "";
		}
		/*******************************解析视图的默认搜索条件中特定的变量********************************/
		while (defaultCondition.contains("  ")) {
			defaultCondition = defaultCondition.replaceAll("  ", " ");
		}
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserName}",
				SpringSecurityUtils.getCurrentUserName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentLoginName}",
				SpringSecurityUtils.getCurrentLoginName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserId}",
				SpringSecurityUtils.getCurrentUserId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentId}",
				SpringSecurityUtils.getCurrentUserDepartmentId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentName}",
				SpringSecurityUtils.getCurrentUserDepartmentName());
		defaultCondition = defaultCondition.replace("=${nowDate}", " between :preDate and :nextDate").replace(
				"= ${nowDate}", " between :preDate and :nextDate");
		//解析默认条件里定义的参数，来源url链接
		String parmStr = "";
		String htmlParmStr = "";
		Map<String, String[]> pMap = request.getParameterMap();
		for (String pkey : pMap.keySet()) {
			String pvalue = pMap.get(pkey)[0];
			if (defaultCondition != null) {
				defaultCondition = defaultCondition.replace("${" + pkey + "}", pvalue);
			}
			if (!pkey.equals("viewUuid") && !pkey.equals("currentPage")) {
				parmStr += "&" + pkey + "=" + pvalue;
				htmlParmStr += "<input type='hidden' id='view_param_" + pkey + "' value='" + pvalue + "' />";
			}
		}
		/*******************************解析视图的默认搜索条件中特定的变量结束********************************/
		//获取视图的分页信息
		PageDefinitionNew pageDefinitionNew = new PageDefinitionNew();
		if (0 == (page.getCurrentPage())) {
			page.setCurrentPage(1);
		}

		//??
		if (count == null) {
			pageDefinitionNew = viewDefinitionNewBean.getPageDefinitionNews();
		} else {
			pageDefinitionNew.setIsPaging(false);
			pageDefinitionNew.setPageNum(Integer.valueOf(count));
		}

		Set<FieldSelect> fieldSelects = new HashSet<FieldSelect>();
		//视图是否分页
		viewDefinitionNewBean.setPageAble(viewDefinitionNewBean.getPageDefinitionNews().getIsPaging());
		//获取视图的行样式信息
		Set<ColumnCssDefinitionNew> columnCssDefinitionNews = viewDefinitionNewBean.getColumnCssDefinitionNew();
		//获取视图的查询定义信息
		SelectDefinitionNew selectDefinitionNew = viewDefinitionNewBean.getSelectDefinitionNews();
		//获取视图的自定义按钮信息
		Set<CustomButtonNew> buttonBeans = viewDefinitionNewBean.getCustomButtonNews();
		//获取有权限的自定义按钮
		getViewDataNewService.setCustomButtonRights(viewDefinitionNewBean, buttonBeans);
		//查询模板(按条件查询)
		StringBuilder selectTemplate = new StringBuilder();
		//按字段查询模版
		StringBuilder fieldSelect = new StringBuilder();
		//按条件查询模板
		StringBuilder condSelect = new StringBuilder();
		//按关键字查询模板
		StringBuilder keySelect = new StringBuilder();
		selectTemplate.append("<table class='view_search' style='width:100%;'>");
		if (selectDefinitionNew.getForCondition() == true) {
			Set<ConditionTypeNew> conditionTypeNews = selectDefinitionNew.getConditionTypeNew();
			model.addAttribute("conditionTypes", conditionTypeNews);
			int flag = 0;
			//按条件查询的前台页面展示
			for (ConditionTypeNew conditionTypeNew : conditionTypeNews) {
				String ShowName = conditionTypeNew.getName(); //备选项的显示值
				String ShowValue = conditionTypeNew.getConditionValue();//备选项的真实值
				String ConditionName = conditionTypeNew.getConditionName();//条件名称
				String appointColumn = conditionTypeNew.getAppointColumn();//条件对应的列
				String appointColumnType = conditionTypeNew.getAppointColumnType();
				if (datascope == DyviewConfigNew.DYVIEW_DATASCOPE_DYTABLE) {
					if ("1".equals(appointColumnType)) {
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left'>" + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						String[] ShowNames = ShowName.split(";");
						String[] ShowValues = ShowValue.split(";");
						for (int i = 0; i < ShowNames.length; i++) {
							condSelect.append("<td class='view_search_right'><div class='cond_class' id='" + i + "_"
									+ conditionTypeNew.getUuid() + "_cond" + "' value='" + ShowValues[i]
									+ "' appointColumn='" + appointColumn + "' appointColumnType = '"
									+ appointColumnType + "'><a>" + ShowNames[i] + "</a></div></td>");
						}

						condSelect.append("</tr>");
					} else if ("2".equals(appointColumnType) || "DATE".equals(appointColumnType)) {
						DateUtil dateUtil = new DateUtil();
						String today = dateUtil.getPreDate(0);
						String yesterday = dateUtil.getPreDate(-1);
						//获取上周星期一的日期
						String lastWeekFirstDay = dateUtil.getPreviousMonday();
						//获取上周星期日的日期
						String lastWeekSunday = dateUtil.getSunday();
						//获取上个月的第一天
						String lastMonthFirstDay = dateUtil.getLastMonthFirstDay();
						//获取上个月的最后一天
						String lastMonthLastDay = dateUtil.getLastMonthLastDay();
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left' " + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						condSelect.append("<td class='view_search_right'><div id='allDay'><a>不限</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='today' today='" + today
								+ "' appointColumn='" + appointColumn + "' ><a>今天</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='yesterday' yesterday='" + yesterday
								+ "' appointColumn='" + appointColumn + "'><a>昨天</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='lastWeek' lastWeekFirstDay='"
								+ lastWeekFirstDay + "' lastWeekSunday='" + lastWeekSunday + "' appointColumn='"
								+ appointColumn + "'><a>上星期</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='lastMonth' lastMonthFirstDay='"
								+ lastMonthFirstDay + "' lastMonthLastDay='" + lastMonthLastDay + "' appointColumn='"
								+ appointColumn + "'><a>上个月</a></div></td>");
						condSelect
								.append("<td  class='view_search_right'><div id='chooseDate'><a>选择日期</a></div><div id='dateInput' style='display:none'><input type='text' id='datepicker' appointColumn='"
										+ appointColumn + "'></div></td></tr>");
					} else if ("3".equals(appointColumnType)) {
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left'>" + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						condSelect.append("<td class='view_search_right'><div><a>不限</a></div></td>");
						condSelect
								.append("<td class='view_search_right'><div><input type='text' id='' name=''/></div></td>");
						condSelect.append("<td class='view_search_right'><div>'--'</div></td>");
						condSelect
								.append("<td class='view_search_right'><div><input type='text' id='' name='' appointColumn='"
										+ appointColumn + "'/></div></td></tr>");
					}
				} else if (datascope == DyviewConfigNew.DYVIEW_DATASCOPE_ENTITY
						|| datascope == DyviewConfigNew.DYVIEW_DATASCOPE_MOUDLE) {
					if ("STRING".equals(appointColumnType)) {
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left'>" + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						String[] ShowNames = ShowName.split(";");
						String[] ShowValues = ShowValue.split(";");
						for (int i = 0; i < ShowNames.length; i++) {
							condSelect.append("<td class='view_search_right'><div class='cond_class' id='" + i + "_"
									+ conditionTypeNew.getUuid() + "_cond" + "' value='" + ShowValues[i]
									+ "' appointColumn='" + appointColumn + "' appointColumnType = '"
									+ appointColumnType + "'><a>" + ShowNames[i] + "</a></div></td>");
						}

						condSelect.append("</tr>");
					} else if ("DATE".equals(appointColumnType)) {
						DateUtil dateUtil = new DateUtil();
						String today = dateUtil.getPreDate(0);
						String yesterday = dateUtil.getPreDate(-1);
						//获取上周星期一的日期
						String lastWeekFirstDay = dateUtil.getPreviousMonday();
						//获取上周星期日的日期
						String lastWeekSunday = dateUtil.getSunday();
						//获取上个月的第一天
						String lastMonthFirstDay = dateUtil.getLastMonthFirstDay();
						//获取上个月的最后一天
						String lastMonthLastDay = dateUtil.getLastMonthLastDay();
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left'>" + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						condSelect.append("<td class='view_search_right'><div id='allDay'><a>不限</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='today' today='" + today
								+ "' appointColumn='" + appointColumn + "' ><a>今天</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='yesterday' yesterday='" + yesterday
								+ "' appointColumn='" + appointColumn + "'><a>昨天</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='lastWeek' lastWeekFirstDay='"
								+ lastWeekFirstDay + "' lastWeekSunday='" + lastWeekSunday + "' appointColumn='"
								+ appointColumn + "'><a>上星期</a></div></td>");
						condSelect.append("<td class='view_search_right'><div id='lastMonth' lastMonthFirstDay='"
								+ lastMonthFirstDay + "' lastMonthLastDay='" + lastMonthLastDay + "' appointColumn='"
								+ appointColumn + "'><a>上个月</a></div></td>");
						condSelect
								.append("<td class='view_search_right'><div id='chooseDate'><a>选择日期</a></div><div id='dateInput' style='display:none'><input type='text' id='datepicker' appointColumn='"
										+ appointColumn + "'></div></td></tr>");
					} else {
						if (flag % 2 == 1) {
							condSelect.append("<tr class='view_seach_tr_odd'>");
						} else {
							condSelect.append("<tr class='view_seach_tr_even'>");
						}
						condSelect.append("<td class='view_search_left'>" + ConditionName
								+ "&nbsp;&nbsp;&nbsp;&nbsp;|</td>");
						String[] ShowNames = ShowName.split(";");
						String[] ShowValues = ShowValue.split(";");
						for (int i = 0; i < ShowNames.length; i++) {
							condSelect.append("<td class='view_search_right'><div class='cond_class' id='" + i + "_"
									+ conditionTypeNew.getUuid() + "_cond" + "' value='" + ShowValues[i]
									+ "' appointColumn='" + appointColumn + "' appointColumnType = '"
									+ appointColumnType + "'><a>" + ShowNames[i] + "</a></div></td>");
						}
					}
				}
				flag++;
			}
			condSelect.append("</tr>");
		}
		StringBuilder buttonStr = new StringBuilder();
		if (selectDefinitionNew.getForFieldSelect() == true) {
			fieldSelects = selectDefinitionNew.getFieldSelects();
			int i = 0;
			for (FieldSelect fs : fieldSelects) {
				//字段的展示名
				String titleName = fs.getShowName();
				//字段名
				String fieldName = fs.getField();
				String selectTypeId = fs.getSelectTypeId();
				String dataSourceId = fs.getDataSourceId();
				String selectNameColumn = fs.getSelectNameColumn();
				String selectValueColumn = fs.getSelectValueColumn();
				String optionDataSource = fs.getOptionDataSource();
				String optdata = fs.getOptdata();
				if (StringUtils.isNotBlank(optdata)) {
					optdata = URLEncoder.encode(optdata, "utf-8");
					fs.setOptdata(optdata);
				}
				if (i % 2 != 0) {
					fieldSelect.append("<td class='fieldSelectTitleTd' width='10%'>").append(titleName)
							.append(":</td>");
					if (fs.getSelectTypeId().equals("TEXT")) {
						if (fs.getIsArea() == true) {
							fieldSelect
									.append("<td class='fieldSelectTd'><div class='fieldSelectDiv'>")
									.append("<input class='inputClass' type='text' id='" + fieldName
											+ "_first' selectTypeId='" + selectTypeId + "' isArea='true' fieldName='"
											+ fieldName + "'/>")
									.append("至<input class='inputClass' type='text' id='" + fieldName
											+ "_last' selectTypeId='" + selectTypeId + "' isArea='true' fieldName='"
											+ fieldName + "'/></div></td>");
						} else if (fs.getIsExact() == true) {
							String exactValue = fs.getExactValue();
							fieldSelect.append("<td class='fieldSelectTd'>").append(
									"<input class='inputClass' type='text' id='" + fieldName + "' exactValue='"
											+ exactValue + "' selectTypeId='" + selectTypeId
											+ "' isExact='true'/></td>");
						} else if (fs.getIsLike()) {
							fieldSelect.append("<td class='fieldSelectTd'>").append(
									"<input class='inputClass' type='text' id='" + fieldName + "' isLike='true' id='"
											+ fieldName + "' selectTypeId='" + selectTypeId + "'/></td>");
						}
					} else if (fs.getSelectTypeId().equals("DATE")) {
						String contentFormat = fs.getContentFormat();
						fieldSelect.append(
								"<td class='fieldSelectTd'><div class='fieldSelectDiv'><input class='inputClass' type='hidden' name='"
										+ fieldName + "_begin' id='" + fieldName + "_begin' contentFormat='"
										+ contentFormat + "' selectTypeId='" + selectTypeId + "' searchField='"
										+ fieldName + "'/>").append(
								"至<input class='inputClass' type='hidden' name='" + fieldName + "_end' id='"
										+ fieldName + "_end' contentFormat='" + contentFormat + "' selectTypeId='"
										+ selectTypeId + "'/></div></td>");

					} else if (fs.getSelectTypeId().equals("ORG")) {
						String orgInputMode = fs.getOrgInputMode();
						fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
								+ fieldName + "' name='" + fieldName + "' orgInputMode='" + orgInputMode
								+ "' selectTypeId='" + selectTypeId + "'/></td>");
					} else if (fs.getSelectTypeId().equals("SELECT")) {
						if (fs.getOptionDataSource().equals("2")) {
							String dictCode = fs.getDictCode();
						}
						fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
								+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
								+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
								+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
								+ optionDataSource + "' optdata='" + optdata + "'></td>");
					} else if (fs.getSelectTypeId().equals("RADIO")) {
						fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
								+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
								+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
								+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
								+ optionDataSource + "' optdata='" + optdata + "'></td>");
					} else if (fs.getSelectTypeId().equals("CHECKBOX")) {
						fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
								+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
								+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
								+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
								+ optionDataSource + "' optdata='" + optdata + "'></td>");
					} else if (fs.getSelectTypeId().equals("DIAlOG")) {
						fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
								+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId + "'></td>");
					}
				} else {
					if (i % 4 == 0) {
						fieldSelect.append("<tr class='fieldSelectTr'><td width='10%' class='fieldSelectTitleTd'>")
								.append(titleName).append(":</td>");
						if (fs.getSelectTypeId().equals("TEXT")) {
							if (fs.getIsArea() == true) {
								fieldSelect
										.append("<td class='fieldSelectTd'><div class='fieldSelectDiv'>")
										.append("<input class='inputClass' isArea='true' type='text' id='" + fieldName
												+ "_first' selectTypeId='" + selectTypeId + "' fieldName='" + fieldName
												+ "'/>")
										.append("至<input class='inputClass' isArea='true' type='text' id='" + fieldName
												+ "_last' selectTypeId='" + selectTypeId + "' fieldName='" + fieldName
												+ "'/></div></td>");
							} else if (fs.getIsExact() == true) {
								String exactValue = fs.getExactValue();
								fieldSelect.append("<td class='fieldSelectTd'>").append(
										"<input class='inputClass' type='text' isExact='true' id='" + fieldName
												+ "' exactValue='" + exactValue + "' selectTypeId='" + selectTypeId
												+ "'/></td>");
							} else if (fs.getIsLike()) {
								fieldSelect.append("<td class='fieldSelectTd'>").append(
										"<input class='inputClass' type='text' isLike='true' id='" + fieldName
												+ "' selectTypeId='" + selectTypeId + "'/></td>");
							}
						} else if (fs.getSelectTypeId().equals("DATE")) {
							String contentFormat = fs.getContentFormat();
							fieldSelect.append(
									"<td class='fieldSelectTd'><div class='fieldSelectDiv'><input class='inputClass' type='hidden' name='"
											+ fieldName + "_begin' id='" + fieldName + "_begin' contentFormat='"
											+ contentFormat + "' selectTypeId='" + selectTypeId + "' searchField='"
											+ fieldName + "'/>").append(
									"至<input class='inputClass' type='hidden' name='" + fieldName + "_end' id='"
											+ fieldName + "_end' contentFormat='" + contentFormat + "' selectTypeId='"
											+ selectTypeId + "'/></div></td>");

						} else if (fs.getSelectTypeId().equals("ORG")) {
							String orgInputMode = fs.getOrgInputMode();
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' orgInputMode='" + orgInputMode
									+ "' selectTypeId='" + selectTypeId + "'/></td>");
						} else if (fs.getSelectTypeId().equals("SELECT")) {
							if (fs.getOptionDataSource().equals("2")) {
								String dictCode = fs.getDictCode();
							}
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("RADIO")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("CHECKBOX")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("DIAlOG")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "'></td>");
						}
					} else {

						fieldSelect.append("<tr class='fieldSelectTr'><td width='10%' class='fieldSelectTitleTd'>")
								.append(titleName).append(":</td>");
						if (fs.getSelectTypeId().equals("TEXT")) {
							if (fs.getIsArea() == true) {
								fieldSelect
										.append("<td class='fieldSelectTd'><div class='fieldSelectDiv'>")
										.append("<input class='inputClass' isArea='true' type='text' id='" + fieldName
												+ "_first' selectTypeId='" + selectTypeId + "' fieldName='" + fieldName
												+ "'/>")
										.append("至<input class='inputClass' type='text' id='" + fieldName
												+ "_last' selectTypeId='" + selectTypeId
												+ "' isArea='true' fieldName='" + fieldName + "'/></div></td>");
							} else if (fs.getIsExact() == true) {
								String exactValue = fs.getExactValue();
								fieldSelect.append("<td class='fieldSelectTd'>").append(
										"<input class='inputClass' type='text' isExact='true' id='" + fieldName
												+ "' exactValue='" + exactValue + "' selectTypeId='" + selectTypeId
												+ "'/></td>");
							} else if (fs.getIsLike()) {
								fieldSelect.append("<td class='fieldSelectTd'>").append(
										"<input class='inputClass' type='text' isLike='true' id='" + fieldName
												+ "' selectTypeId='" + selectTypeId + "'/></td>");
							}
						} else if (fs.getSelectTypeId().equals("DATE")) {
							String contentFormat = fs.getContentFormat();
							fieldSelect.append(
									"<td class='fieldSelectTd'><div class='fieldSelectDiv'><input class='inputClass' type='hidden' name='"
											+ fieldName + "_begin' id='" + fieldName + "_begin' contentFormat='"
											+ contentFormat + "' selectTypeId='" + selectTypeId + "' searchField='"
											+ fieldName + "'/>").append(
									"至<input class='inputClass' type='hidden' name='" + fieldName + "_end' id='"
											+ fieldName + "_end' contentFormat='" + contentFormat + "' selectTypeId='"
											+ selectTypeId + "'/></div></td>");

						} else if (fs.getSelectTypeId().equals("ORG")) {
							String orgInputMode = fs.getOrgInputMode();
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' orgInputMode='" + orgInputMode
									+ "' selectTypeId='" + selectTypeId + "'/></td>");
						} else if (fs.getSelectTypeId().equals("SELECT")) {
							if (fs.getOptionDataSource().equals("2")) {
								String dictCode = fs.getDictCode();
							}
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("RADIO")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("CHECKBOX")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "' dataSourceId='" + dataSourceId + "' selectNameColumn='" + selectNameColumn
									+ "'  selectValueColumn='" + selectValueColumn + "' optionDataSource='"
									+ optionDataSource + "' optdata='" + optdata + "'></td>");
						} else if (fs.getSelectTypeId().equals("DIAlOG")) {
							fieldSelect.append("<td class='fieldSelectTd'><input class='inputClass' type='hidden' id='"
									+ fieldName + "' name='" + fieldName + "' selectTypeId='" + selectTypeId
									+ "'></td>");
						}

					}
				}
				i++;
			}
			if (i == fieldSelects.size()) {
				if (fieldSelects.size() % 2 != 0) {
					fieldSelect.append("<td></td><td></td>");
				}
				fieldSelect.append("</tr>");
			}
			buttonStr.append("<button id='fieldSelect' type='button' style='display:none;'>查询</button>");
		}
		//获取视图自定义按钮
		Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
		StringBuilder buttonTemplate = new StringBuilder();
		StringBuilder buttonTemplate2 = new StringBuilder();
		String buttonGroup = "";
		String buttonGroup2 = "";
		if (viewbutton.size() > 0 || selectDefinitionNew.getForKeySelect() == true) {
			if (viewDefinitionNewBean.getButtonPlace() != null && viewDefinitionNewBean.getButtonPlace() == true) {
				buttonTemplate2.append("<div class='view_tool_bottom'>");
				if (viewbutton.size() > 0) {
					buttonTemplate2.append("<div class='customButton customButton_top'>");
					for (CustomButtonNew cbb : viewbutton) {
						//加按钮权限SecurityApiFacade.isGranted
						if (SecurityApiFacade.isGranted(cbb.getCode())) {
							if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
									&& buttonGroup2.indexOf(cbb.getButtonGroup()) < 0) {
								buttonGroup2 += "," + cbb.getButtonGroup();
							}
							String place = cbb.getPlace();
							if (place != null && place.indexOf("头部") > -1 && cbb.getButtonGroup() == null) {
								String buttonFunction = cbb.getJsContent();
								String buttonName = cbb.getName();
								String buttonCode = cbb.getCode();
								if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
									buttonFunction = "";
								}
								buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
								buttonTemplate2.append("<button place=\"place_top\" type=\"button\" value=\""
										+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName
										+ "</button>");
							}
						}
					}
					buttonTemplate2.append("</div>");
				}

				buttonGroup2 = buttonGroup2.replaceFirst(",", "");
				String[] buttonGroupArray2 = buttonGroup2.split(",");
				if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
					buttonTemplate2.append("<div class='customButton_group'>");
				}
				for (int j = 0; j < buttonGroupArray2.length; j++) {
					if (!"".equals(buttonGroupArray2[j])) {
						buttonTemplate2.append("<div class='customButton_group_item'>");
						buttonTemplate2
								.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
										+ buttonGroupArray2[j] + "</div><div class='select_icon'></div></div>");
						buttonTemplate2.append("<div class='customButton_group_buttons_bottom'>");
						for (CustomButtonNew cbb2 : viewbutton) {
							if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
									&& cbb2.getButtonGroup().equals(buttonGroupArray2[j])) {
								String buttonFunction = cbb2.getJsContent();
								String buttonName = cbb2.getName();
								String buttonCode = cbb2.getCode();
								buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
								buttonTemplate2
										.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
												+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName);
								buttonTemplate2.append("</div>");
							}
						}
						buttonTemplate2.append("</div>");
						buttonTemplate2.append("</div>");
					}
				}
				if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
					buttonTemplate2.append("</div>");
				}
			}

			buttonTemplate.append("<div class='view_tool2'>");
			if (viewbutton.size() > 0) {
				int i = 0;
				for (CustomButtonNew cbb : viewbutton) {
					//加按钮权限SecurityApiFacade.isGranted
					if (SecurityApiFacade.isGranted(cbb.getCode()) && cbb.getPlace() != null
							&& cbb.getPlace().indexOf("头部") > -1) {
						if (i == 0) {
							buttonTemplate.append("<div class='customButton customButton_top'>");
							i++;
						}
						if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
								&& buttonGroup.indexOf(cbb.getButtonGroup()) < 0) {
							buttonGroup += "," + cbb.getButtonGroup();
						}
						String place = cbb.getPlace();
						if (place != null && place.indexOf("头部") > -1 && cbb.getButtonGroup() == null) {
							String buttonFunction = cbb.getJsContent();
							String buttonName = cbb.getName();
							String buttonCode = cbb.getCode();
							if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
								buttonFunction = "";
							}
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate.append("<button place=\"place_top\" type=\"button\" value=\"" + buttonCode
									+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
						}
					}

				}
				if (i == 1) {
					buttonTemplate.append("</div>");
				}
			}

			buttonGroup = buttonGroup.replaceFirst(",", "");
			String[] buttonGroupArray = buttonGroup.split(",");
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("<div class='customButton_group'>");
			}
			for (int j = 0; j < buttonGroupArray.length; j++) {
				if (!"".equals(buttonGroupArray[j])) {
					buttonTemplate.append("<div class='customButton_group_item'>");
					buttonTemplate
							.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
									+ buttonGroupArray[j] + "</div><div class='select_icon'></div></div>");
					buttonTemplate.append("<div class='customButton_group_buttons'>");
					for (CustomButtonNew cbb2 : viewbutton) {
						if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
								&& cbb2.getButtonGroup().equals(buttonGroupArray[j])) {
							String buttonFunction = cbb2.getJsContent();
							String buttonName = cbb2.getName();
							String buttonCode = cbb2.getCode();
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate
									.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
											+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName);
							buttonTemplate.append("</div>");
						}
					}
					buttonTemplate.append("</div>");
					buttonTemplate.append("</div>");
				}
			}
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("</div>");
			}
			if (StringUtils.isNotBlank(fieldSelect.toString())) {
				buttonTemplate
						.append("<div class='view_keyword_div'><input type='text' value='关键字搜索' onblur=\"if(this.value =='') this.value = '关键字搜索'\" onfocus=\"if(this.value == '关键字搜索') this.value = ''\" name='keyWord' id='keyWord' autocomplete='off'/>");
				if (StringUtils.isNotBlank(buttonStr.toString())) {
					buttonTemplate.append(buttonStr.toString());
				}
				buttonTemplate.append("<button id='keySelect' type='button'>查询</button>");
				buttonTemplate.append("<button id='showButton' type='button'>↑</button></div>");
			} else {
				buttonTemplate
						.append("<div class='view_keyword_div'><input type='text' value='关键字搜索' onblur=\"if(this.value =='') this.value = '关键字搜索'\" onfocus=\"if(this.value == '关键字搜索') this.value = ''\" name='keyWord' id='keyWord' autocomplete='off'/>");
				if (StringUtils.isNotBlank(buttonStr.toString())) {
					buttonTemplate.append(buttonStr.toString());
				}
				buttonTemplate.append("<button id='keySelect' type='button'>查询</button></div>");
			}
			buttonTemplate.append("</div>");
		}
		selectTemplate.append(fieldSelect.toString());
		selectTemplate.append(condSelect.toString());
		selectTemplate.append(keySelect.toString());
		selectTemplate.append("</table>");
		//获取视图下所有的列字段的数据
		Set<ColumnDefinitionNew> columnDefinitionNews = new HashSet<ColumnDefinitionNew>();
		columnDefinitionNews = viewDefinitionNewBean.getColumnDefinitionNews();
		//获取表的总记录数
		Long totalCount = 0L;
		Map queryParams = new HashMap();
		//		totalCount = dataSourceApiFacade.countForView(tableDefinitionId, defaultCondition, queryParams);
		//如果分页存在,设置分页信息
		if (pageDefinitionNew.getIsPaging() == true) {
			if (pageDefinitionNew.getPageNum() == null) {
				pageDefinitionNew.setPageNum(10);
			}
			page.setTotalCount(totalCount);
			page.setPageSize(pageDefinitionNew.getPageNum());
		} else {
			page.setPageSize(Integer.valueOf(count));
			page.setCurrentPage(-1);
		}
		//点击事件跳转的url
		String lineType = viewDefinitionNewBean.getLineType();
		long queryItemCount = 0;
		//获取列标题模板
		Map<String, ColumnDefinitionNew> columnFields = new LinkedHashMap<String, ColumnDefinitionNew>();
		boolean showTitle = viewDefinitionNewBean.getShowTitle();
		String titleSource = getTitleSource(showTitle, viewDefinitionNewBean.getShowCheckBox() == null ? false
				: viewDefinitionNewBean.getShowCheckBox(), columnDefinitionNews, columnFields, datascope);
		String templateAll = "";
		//根据视图的条件获取数据源的数据
		dyViewQueryInfoNew.setPageInfo(page);
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		queryItems = viewDefinitionNewService.getViewData(defaultCondition, whereSql, tableDefinitionId,
				columnDefinitionNews, pageDefinitionNew, dyViewQueryInfoNew, rowIdKey);
		queryItemCount = dyViewQueryInfoNew.getPageInfo().getTotalCount();
		page.setTotalCount(queryItemCount);
		if (viewDefinitionNewBean.getIsRead() != null && viewDefinitionNewBean.getIsRead()) {
			readMarkerService.markList(queryItems, SpringSecurityUtils.getCurrentUserId(), readKey, "readFlag");
		}
		templateAll = getColumnTemplate(viewDefinitionNewBean, columnDefinitionNews, queryItems, columnFields, request);
		viewAndDataNewBean.setData(queryItems);

		viewAndDataNewBean.setViewDefinitionBean(viewDefinitionNewBean);
		List srcList = new ArrayList();
		if (viewDefinitionNewBean.getJsSrc() == null) {
			viewDefinitionNewBean.setJsSrc("");
		}
		String[] srcTemp = viewDefinitionNewBean.getJsSrc().split(",");
		for (int j = 0; j < srcTemp.length; j++) {
			Map srcMap = new HashMap();
			if (!srcTemp[j].equals("")) {
				srcMap.put("src", srcTemp[j]);
				srcList.add(srcMap);
			}
		}
		model.addAttribute("fieldSelects", fieldSelects);
		model.addAttribute("mark", "viewShow");
		model.addAttribute("selectTemplate", selectTemplate);
		model.addAttribute("condSelect", condSelect);
		model.addAttribute("keySelect", keySelect);
		model.addAttribute("page", page);
		model.addAttribute("pageDefinition", pageDefinitionNew);
		model.addAttribute("titleSource", titleSource);
		model.addAttribute("buttonTemplate", buttonTemplate);
		model.addAttribute("buttonTemplate2", buttonTemplate2);
		model.addAttribute("template", templateAll);
		model.addAttribute("openBy", openBy);
		model.addAttribute("columnDefinitions", columnDefinitionNews);
		model.addAttribute("viewDefinitionBean", viewDefinitionNewBean);
		model.addAttribute("selectDefinition", selectDefinitionNew);
		model.addAttribute("viewAndDataBean", viewAndDataNewBean);
		if (queryItemCount == -1) {
			queryItemCount = 0;
		}
		model.addAttribute("queryItemCount", queryItemCount);
		model.addAttribute("srcList", srcList);
		model.addAttribute("parmStr", parmStr);
		model.addAttribute("htmlParmStr", htmlParmStr);
		return forward("/basicdata/view/view_explain_new");
	}

	/**
	 * 
	 * 视图的控制层调用入口(直接预览后的分页控制层入口)
	 * 
	 * @param model
	 * @param viewUuid
	 * @param dyViewQueryInfoNew
	 * @param expandParams
	 * @param openBy
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view_show_forpage", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViewDataForPage(Model model, @RequestBody DyViewQueryInfoNew dyViewQueryInfoNew,
			HttpServletRequest request) throws Exception {
		String whereSql = request.getParameter("whereSql");
		if (dyViewQueryInfoNew == null) {
			dyViewQueryInfoNew = new DyViewQueryInfoNew();
		}
		Map<String, String> expandParams = new HashMap<String, String>();
		if (dyViewQueryInfoNew.getExpandParams() != null) {
			expandParams = dyViewQueryInfoNew.getExpandParams();
		}
		String viewUuid = "";
		if (dyViewQueryInfoNew.getViewUuid() != null) {
			viewUuid = dyViewQueryInfoNew.getViewUuid();
		}

		//视图名称
		String viewName = dyViewQueryInfoNew.getViewName();
		String orderTitle = "";
		String orderbyArr = "";

		//视图的条数，由前台传递过来，传递的格式是必须放在组装的map里面，且key为count
		String count = expandParams.get("count");
		//关联数据所用的参数，由前台传递过来，传递的格式是必须放在组装的map里面，且key为count
		String relationDataDefiantion = expandParams.get("relationDataDefiantion");
		/************************获取视图的定义bean开始***********************/
		ViewAndDataNewBean viewAndDataNewBean = new ViewAndDataNewBean();
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		if (viewUuid != null && viewUuid != "") {
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		} else {
			ViewDefinitionNew viewDefinitionNew = viewDefinitionNewService.getByViewId(viewName);
			viewUuid = viewDefinitionNew.getUuid();
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		}
		/************************获取视图的定义信息结束***********************/
		int datascope = 0;
		//数据源的ID
		String tableDefinitionId = viewDefinitionNewBean.getTableDefinitionId();
		String tableUuid = "";
		String tableName = "";
		//启用已读未读属性
		String readKey = viewDefinitionNewBean.getReadKey();
		if (readKey == null || (readKey != null && readKey.equals(""))) {
			readKey = "uuid";
		}
		//复选框的key
		String rowIdKey = StringUtils.isBlank(viewDefinitionNewBean.getCheckKey()) ? "uuid" : viewDefinitionNewBean
				.getCheckKey();
		//获取视图的默认搜索条件
		String defaultCondition = viewDefinitionNewBean.getDefaultCondition();
		if (defaultCondition == null) {
			defaultCondition = "";
		}
		/*******************************解析视图的默认搜索条件中特定的变量********************************/
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserName}",
				SpringSecurityUtils.getCurrentUserName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentLoginName}",
				SpringSecurityUtils.getCurrentLoginName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserId}",
				SpringSecurityUtils.getCurrentUserId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentId}",
				SpringSecurityUtils.getCurrentUserDepartmentId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentName}",
				SpringSecurityUtils.getCurrentUserDepartmentName());
		defaultCondition = StringUtils.replace(defaultCondition, "${nowDate}", "date(t.create_time) = :nowDate");
		//解析默认条件里定义的参数，来源url链接
		String parmStr = "";
		String htmlParmStr = "";
		Map<String, String> pMap = dyViewQueryInfoNew.getExpandParams();
		if (pMap != null) {
			for (String pkey : pMap.keySet()) {
				String pvalue = pMap.get(pkey);
				if (defaultCondition != null) {
					defaultCondition = defaultCondition.replace("${" + pkey + "}", pvalue);
				}
				if (!pkey.equals("viewUuid") && !pkey.equals("currentPage")) {
					parmStr += "&" + pkey + "=" + pvalue;
					htmlParmStr += "<input type='hidden' id='view_param_" + pkey + "' value='" + pvalue + "' />";
				}
			}
		}
		if (!StringUtils.isBlank(dyViewQueryInfoNew.getOpenBy())) {
			parmStr += "&openBy=" + dyViewQueryInfoNew.getOpenBy();
		}
		/*******************************解析视图的默认搜索条件中特定的变量结束********************************/
		//获取视图的分页信息
		PageDefinitionNew pageDefinitionNew = new PageDefinitionNew();
		PagingInfo page = new PagingInfo();
		if (dyViewQueryInfoNew.getPageInfo() != null) {
			page = dyViewQueryInfoNew.getPageInfo();
		} else {
			if (0 == (page.getCurrentPage())) {
				page.setCurrentPage(1);
			}
		}

		//??
		if (count == null) {
			pageDefinitionNew = viewDefinitionNewBean.getPageDefinitionNews();
		} else {
			pageDefinitionNew.setIsPaging(false);
		}
		//视图是否分页
		viewDefinitionNewBean.setPageAble(viewDefinitionNewBean.getPageDefinitionNews().getIsPaging());
		//获取视图的行样式信息
		Set<ColumnCssDefinitionNew> columnCssDefinitionNews = viewDefinitionNewBean.getColumnCssDefinitionNew();
		//获取视图的查询定义信息
		SelectDefinitionNew selectDefinitionNew = viewDefinitionNewBean.getSelectDefinitionNews();
		//获取视图的自定义按钮信息
		Set<CustomButtonNew> buttonBeans = viewDefinitionNewBean.getCustomButtonNews();
		//获取视图的角色权限信息
		//		String roleType = viewDefinitionNewBean.getRoleType();
		String roleType = "";
		//获取视图的角色类型
		String roleValue = "";
		//		String roleValue = viewDefinitionNewBean.getRoleValue();
		//获取有权限的自定义按钮
		getViewDataNewService.setCustomButtonRights(viewDefinitionNewBean, buttonBeans);
		//获取视图下所有的列字段的数据
		Set<ColumnDefinitionNew> columnDefinitionNews = new HashSet<ColumnDefinitionNew>();
		//		if (relationDataDefiantion != null && !relationDataDefiantion.equals("")) {//关联数据——指定字段显示
		//			Set<ColumnDefinitionNew> columnDefinitionsTemp = viewDefinitionBean.getColumnDefinitions();
		//			for (ColumnDefinitionNew columnDefinition : columnDefinitionsTemp) {
		//				if (columnDefinition.getColumnAliase() != null
		//						&& relationDataDefiantion.indexOf(columnDefinition.getColumnAliase()) > -1) {
		//					columnDefinitions.add(columnDefinition);
		//				} else if (columnDefinition.getFieldName() != null
		//						&& relationDataDefiantion.indexOf(columnDefinition.getFieldName()) > -1) {
		//					columnDefinitions.add(columnDefinition);
		//				}
		//			}
		//		} else {//关联数据——配置的字段全部显示
		columnDefinitionNews = viewDefinitionNewBean.getColumnDefinitionNews();
		//		}

		//获取表的总记录数
		Long totalCount = -1L;
		Map queryParams = new HashMap();
		totalCount = dataSourceApiFacade.countForView(tableDefinitionId, defaultCondition, queryParams);
		if (totalCount != -1L) {
			dyViewQueryInfoNew.getPageInfo().setTotalCount(totalCount);
		}
		//获取视图自定义按钮
		Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
		//点击事件跳转的url
		String lineType = viewDefinitionNewBean.getLineType();
		StringBuilder buttonTemplate = new StringBuilder();
		StringBuilder buttonTemplate2 = new StringBuilder();
		String buttonGroup = "";
		String buttonGroup2 = "";
		if (viewbutton.size() > 0 || selectDefinitionNew.getForKeySelect() == true) {
			buttonTemplate.append("<div class='view_tool2'>");
			if (viewbutton.size() > 0) {
				if (viewDefinitionNewBean.getButtonPlace() != null && viewDefinitionNewBean.getButtonPlace() == true) {
					buttonTemplate2.append("<div class='view_tool_bottom'>");
					if (viewbutton.size() > 0) {
						int i = 0;
						for (CustomButtonNew cbb : viewbutton) {
							//加按钮权限SecurityApiFacade.isGranted
							if (SecurityApiFacade.isGranted(cbb.getCode()) && cbb.getPlace() != null
									&& cbb.getPlace().indexOf("头部") > -1) {
								if (i == 0) {
									buttonTemplate2.append("<div class='customButton customButton_top'>");
									i++;
								}
								if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
										&& buttonGroup2.indexOf(cbb.getButtonGroup()) < 0) {
									buttonGroup2 += "," + cbb.getButtonGroup();
								}
								String place = cbb.getPlace();
								if (place != null && place.indexOf("头部") > -1 && cbb.getButtonGroup() == null) {
									String buttonFunction = cbb.getJsContent();
									String buttonName = cbb.getName();
									String buttonCode = cbb.getCode();
									if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
										buttonFunction = "";
									}
									buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
									buttonTemplate2.append("<button place=\"place_top\" type=\"button\" value=\""
											+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName
											+ "</button>");
								}
							}
						}
						if (i == 1) {
							buttonTemplate2.append("</div>");
						}
					}

					buttonGroup2 = buttonGroup2.replaceFirst(",", "");
					String[] buttonGroupArray2 = buttonGroup2.split(",");
					if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
						buttonTemplate2.append("<div class='customButton_group'>");
					}
					for (int j = 0; j < buttonGroupArray2.length; j++) {
						if (!"".equals(buttonGroupArray2[j])) {
							buttonTemplate2.append("<div class='customButton_group_item'>");
							buttonTemplate2
									.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
											+ buttonGroupArray2[j] + "</div><div class='select_icon'></div></div>");
							buttonTemplate2.append("<div class='customButton_group_buttons_bottom'>");
							for (CustomButtonNew cbb2 : viewbutton) {
								if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
										&& cbb2.getButtonGroup().equals(buttonGroupArray2[j])) {
									String buttonFunction = cbb2.getJsContent();
									String buttonName = cbb2.getName();
									String buttonCode = cbb2.getCode();
									buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
									buttonTemplate2
											.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
													+ buttonCode
													+ "\"  onclick=\""
													+ buttonFunction
													+ "\">"
													+ buttonName);
									buttonTemplate2.append("</div>");
								}
							}
							buttonTemplate2.append("</div>");
							buttonTemplate2.append("</div>");
						}
					}
					if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
						buttonTemplate2.append("</div>");
					}
				}

				int j = 0;
				for (CustomButtonNew cbb : viewbutton) {
					//加按钮权限SecurityApiFacade.isGranted
					if (SecurityApiFacade.isGranted(cbb.getCode()) && cbb.getPlace() != null
							&& cbb.getPlace().indexOf("头部") > -1) {
						if (j == 0) {
							buttonTemplate.append("<div class='customButton customButton_top'>");
							j++;
						}
						if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
								&& buttonGroup.indexOf(cbb.getButtonGroup()) < 0) {
							buttonGroup += "," + cbb.getButtonGroup();
						}
						String place = cbb.getPlace();
						if (place != null && place.indexOf("头部") > -1) {
							String buttonFunction = cbb.getJsContent();
							String buttonName = cbb.getName();
							String buttonCode = cbb.getCode();
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate.append("<button place=\"place_top\" type=\"button\" value=\"" + buttonCode
									+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
						} else if (place != null && place.indexOf("头尾部") > -1 && cbb.getButtonGroup() == null) {
							String buttonFunction = cbb.getJsContent();
							String buttonName = cbb.getName();
							String buttonCode = cbb.getCode();
							if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
								buttonFunction = "";
							}
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate.append("<button place=\"place_top\" type=\"button\" value=\"" + buttonCode
									+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
							buttonTemplate2.append("<button place=\"place_top\" type=\"button\" value=\"" + buttonCode
									+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
						}
					}
				}
				if (j == 1) {
					buttonTemplate.append("</div>");
				}
			}

			buttonGroup = buttonGroup.replaceFirst(",", "");
			String[] buttonGroupArray = buttonGroup.split(",");
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("<div class='customButton_group'>");
			}
			for (int j = 0; j < buttonGroupArray.length; j++) {
				if (!"".equals(buttonGroupArray[j])) {
					buttonTemplate.append("<div class='customButton_group_item'>");
					buttonTemplate
							.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
									+ buttonGroupArray[j] + "</div><div class='select_icon'></div></div>");
					buttonTemplate.append("<div class='customButton_group_buttons'>");
					for (CustomButtonNew cbb2 : viewbutton) {
						if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
								&& cbb2.getButtonGroup().equals(buttonGroupArray[j])) {
							String buttonFunction = cbb2.getJsContent();
							String buttonName = cbb2.getName();
							String buttonCode = cbb2.getCode();
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate
									.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
											+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName);
							buttonTemplate.append("</div>");
						}
					}
					buttonTemplate.append("</div>");
					buttonTemplate.append("</div>");
				}
			}
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("</div>");
			}

			//			if (selectDefinitionNew.getForTimeSolt() != null && selectDefinitionNew.getForTimeSolt() == true
			//					&& !StringUtils.isBlank(selectDefinitionNew.getSearchField())) {
			//				buttonTemplate.append("<div class='view_timeSolt_div'>");
			//				buttonTemplate.append("<select class='searchField'>");
			//				String[] searchFieldArray = selectDefinitionNew.getSearchField().split(";");
			//				for (int ij = 0; ij < searchFieldArray.length; ij++) {
			//					buttonTemplate.append("<option value='" + searchFieldArray[ij].split(":")[1] + "'>"
			//							+ searchFieldArray[ij].split(":")[0] + "</option>");
			//				}
			//				buttonTemplate.append("</select>");
			//				buttonTemplate
			//						.append("<input type='text' value='开始时间' name='beginTime' id='beginTime' autocomplete='off' class='Wdate'/>");
			//				buttonTemplate.append("<span class='toLine'>--</span>");
			//				buttonTemplate
			//						.append("<input type='text' value='结束时间' name='endTime' id='endTime' autocomplete='off' class='Wdate'/>");
			//				buttonTemplate.append("</div>");
			//			}
			if (selectDefinitionNew.getForKeySelect() == true) {
				buttonTemplate
						.append("<div class='view_keyword_div'><input type='text' value='关键字搜索' onblur=\"if(this.value =='') this.value = '关键字搜索'\" onfocus=\"if(this.value == '关键字搜索') this.value = ''\" name='keyWord' id='keyWord' autocomplete='off'/><button id='keySelect' type='button'>查询</button></div>");
			}

			buttonTemplate.append("</div>");
		}
		long queryItemCount = 0;
		//获取列标题模板
		Map<String, ColumnDefinitionNew> columnFields = new LinkedHashMap<String, ColumnDefinitionNew>();
		boolean showTitle = viewDefinitionNewBean.getShowTitle();
		String titleSource = getTitleSource(showTitle, viewDefinitionNewBean.getShowCheckBox() == null ? false
				: viewDefinitionNewBean.getShowCheckBox(), columnDefinitionNews, columnFields, datascope);
		String templateAll = "";
		//根据视图的条件获取数据源的数据
		dyViewQueryInfoNew.setPageInfo(page);
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		queryItems = viewDefinitionNewService.getViewData(defaultCondition, whereSql, tableDefinitionId,
				columnDefinitionNews, pageDefinitionNew, dyViewQueryInfoNew, rowIdKey);
		queryItemCount = dyViewQueryInfoNew.getPageInfo().getTotalCount();
		page.setTotalCount(queryItemCount);
		if (viewDefinitionNewBean.getIsRead() != null && viewDefinitionNewBean.getIsRead()) {
			readMarkerService.markList(queryItems, SpringSecurityUtils.getCurrentUserId(), readKey, "readFlag");
		}
		templateAll = getColumnTemplate(viewDefinitionNewBean, columnDefinitionNews, queryItems, columnFields, request);
		viewAndDataNewBean.setData(queryItems);
		viewAndDataNewBean.setViewDefinitionBean(viewDefinitionNewBean);
		List srcList = new ArrayList();
		if (viewDefinitionNewBean.getJsSrc() == null) {
			viewDefinitionNewBean.setJsSrc("");
		}
		String[] srcTemp = viewDefinitionNewBean.getJsSrc().split(",");
		for (int j = 0; j < srcTemp.length; j++) {
			Map srcMap = new HashMap();
			if (!srcTemp[j].equals("")) {
				srcMap.put("src", srcTemp[j]);
				srcList.add(srcMap);
			}
		}
		model.addAttribute("orderbyArr", orderbyArr);
		model.addAttribute("title", orderTitle);
		model.addAttribute("mark", "viewShow");
		model.addAttribute("page", page);
		model.addAttribute("pageDefinition", pageDefinitionNew);
		model.addAttribute("titleSource", titleSource);
		//		model.addAttribute("buttonTemplate", buttonTemplate);
		//		model.addAttribute("buttonTemplate2", buttonTemplate2);
		model.addAttribute("template", templateAll);
		model.addAttribute("columnDefinitions", columnDefinitionNews);
		model.addAttribute("viewDefinitionBean", viewDefinitionNewBean);
		model.addAttribute("viewAndDataBean", viewAndDataNewBean);
		model.addAttribute("queryItemCount", queryItemCount);
		model.addAttribute("srcList", srcList);
		model.addAttribute("parmStr", parmStr);
		model.addAttribute("htmlParmStr", htmlParmStr);
		return forward("/basicdata/view/view_explain_new");
	}

	/**
	 * 
	 * 视图的控制层调用入口(进来后做了点击排序、关键字查询等操作后的)
	 * 
	 * @param model
	 * @param viewUuid
	 * @param dyViewQueryInfoNew
	 * @param expandParams
	 * @param openBy
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view_show_param", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViewDataByParam(Model model, @RequestBody DyViewQueryInfoNew dyViewQueryInfoNew,
			HttpServletRequest request) throws Exception {
		String whereSql = request.getParameter("whereSql");
		if (dyViewQueryInfoNew == null) {
			dyViewQueryInfoNew = new DyViewQueryInfoNew();
		}
		Map<String, String> expandParams = new HashMap<String, String>();
		if (dyViewQueryInfoNew.getExpandParams() != null) {
			expandParams = dyViewQueryInfoNew.getExpandParams();
		}
		String viewUuid = "";
		if (dyViewQueryInfoNew.getViewUuid() != null) {
			viewUuid = dyViewQueryInfoNew.getViewUuid();
		}

		//视图名称
		String viewName = dyViewQueryInfoNew.getViewName();
		String orderTitle = "";
		String orderbyArr = "";
		//		if (dyViewQueryInfoNew.getCondSelect() != null) {
		//			if (StringUtils.isNotEmpty(dyViewQueryInfoNew.getCondSelect().getOrderTitle())) {
		//				orderTitle = dyViewQueryInfoNew.getCondSelect().getOrderTitle();
		//			}
		//			if (StringUtils.isNotEmpty(dyViewQueryInfoNew.getCondSelect().getOrderbyArr())) {
		//				orderbyArr = dyViewQueryInfoNew.getCondSelect().getOrderbyArr();
		//			}
		//		}

		//视图的条数，由前台传递过来，传递的格式是必须放在组装的map里面，且key为count
		String count = expandParams.get("count");
		//关联数据所用的参数，由前台传递过来，传递的格式是必须放在组装的map里面，且key为count
		String relationDataDefiantion = expandParams.get("relationDataDefiantion");
		/************************获取视图的定义bean开始***********************/
		ViewAndDataNewBean viewAndDataNewBean = new ViewAndDataNewBean();
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		if (viewUuid != null && viewUuid != "") {
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		} else {
			ViewDefinitionNew viewDefinitionNew = viewDefinitionNewService.getByViewId(viewName);
			viewUuid = viewDefinitionNew.getUuid();
			viewAndDataNewBean.setViewUuid(viewUuid);
			viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		}
		/************************获取视图的定义信息结束***********************/
		int datascope = 0;
		//数据源的ID
		String tableDefinitionId = viewDefinitionNewBean.getTableDefinitionId();
		//启用已读未读属性
		String readKey = viewDefinitionNewBean.getReadKey();
		if (readKey == null || (readKey != null && readKey.equals(""))) {
			readKey = "uuid";
		}
		//复选框的key
		String rowIdKey = StringUtils.isBlank(viewDefinitionNewBean.getCheckKey()) ? "uuid" : viewDefinitionNewBean
				.getCheckKey();
		//获取视图的默认搜索条件
		String defaultCondition = viewDefinitionNewBean.getDefaultCondition();
		if (defaultCondition == null) {
			defaultCondition = "";
		}
		/*******************************解析视图的默认搜索条件中特定的变量********************************/
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserName}",
				SpringSecurityUtils.getCurrentUserName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentLoginName}",
				SpringSecurityUtils.getCurrentLoginName());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserId}",
				SpringSecurityUtils.getCurrentUserId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentId}",
				SpringSecurityUtils.getCurrentUserDepartmentId());
		defaultCondition = StringUtils.replace(defaultCondition, "${currentUserDepartmentName}",
				SpringSecurityUtils.getCurrentUserDepartmentName());
		defaultCondition = StringUtils.replace(defaultCondition, "${nowDate}", "date(t.create_time) = :nowDate");
		//解析默认条件里定义的参数，来源url链接
		String parmStr = "";
		String htmlParmStr = "";
		Map<String, String> pMap = dyViewQueryInfoNew.getExpandParams();
		if (pMap != null) {
			for (String pkey : pMap.keySet()) {
				String pvalue = pMap.get(pkey);
				if (defaultCondition != null) {
					defaultCondition = defaultCondition.replace("${" + pkey + "}", pvalue);
				}
				if (!pkey.equals("viewUuid") && !pkey.equals("currentPage")) {
					parmStr += "&" + pkey + "=" + pvalue;
					htmlParmStr += "<input type='hidden' id='view_param_" + pkey + "' value='" + pvalue + "' />";
				}
			}
		}
		if (!StringUtils.isBlank(dyViewQueryInfoNew.getOpenBy())) {
			parmStr += "&openBy=" + dyViewQueryInfoNew.getOpenBy();
		}

		/*******************************解析视图的默认搜索条件中特定的变量结束********************************/
		//获取视图的分页信息
		PageDefinitionNew pageDefinitionNew = new PageDefinitionNew();
		PagingInfo page = new PagingInfo();
		if (dyViewQueryInfoNew.getPageInfo() != null) {
			page = dyViewQueryInfoNew.getPageInfo();
		} else {
			if (0 == (page.getCurrentPage())) {
				page.setCurrentPage(1);
			}
		}
		Set<FieldSelect> fieldSelects = new HashSet<FieldSelect>();
		//??
		if (count == null) {
			pageDefinitionNew = viewDefinitionNewBean.getPageDefinitionNews();
		} else {
			pageDefinitionNew.setIsPaging(false);
		}
		//视图是否分页
		viewDefinitionNewBean.setPageAble(viewDefinitionNewBean.getPageDefinitionNews().getIsPaging());
		//获取视图的行样式信息
		Set<ColumnCssDefinitionNew> columnCssDefinitionNews = viewDefinitionNewBean.getColumnCssDefinitionNew();
		//获取视图的查询定义信息
		SelectDefinitionNew selectDefinitionNew = viewDefinitionNewBean.getSelectDefinitionNews();
		//获取视图的自定义按钮信息
		Set<CustomButtonNew> buttonBeans = viewDefinitionNewBean.getCustomButtonNews();
		//获取视图的角色权限信息
		//		String roleType = viewDefinitionNewBean.getRoleType();
		String roleType = "";
		//获取视图的角色类型
		//		String roleValue = viewDefinitionNewBean.getRoleValue();
		String roleValue = "";
		//获取有权限的自定义按钮
		getViewDataNewService.setCustomButtonRights(viewDefinitionNewBean, buttonBeans);
		//获取视图下所有的列字段的数据
		Set<ColumnDefinitionNew> columnDefinitionNews = new HashSet<ColumnDefinitionNew>();
		columnDefinitionNews = viewDefinitionNewBean.getColumnDefinitionNews();

		//获取表的总记录数
		Long totalCount = 0L;
		Map queryParams = new HashMap();
		//		totalCount = dataSourceApiFacade.countForSelect(tableDefinitionId, defaultCondition, dyViewQueryInfoNew,
		//				queryParams, columnDefinitionNews);
		//如果分页存在,设置分页信息
		if (pageDefinitionNew.getIsPaging() == true) {
			if (pageDefinitionNew.getPageNum() == null) {
				pageDefinitionNew.setPageNum(10);
			}
			page.setTotalCount(totalCount);
			page.setPageSize(pageDefinitionNew.getPageNum());
		} else {
			page.setCurrentPage(-1);
		}
		//获取视图自定义按钮
		Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
		//点击事件跳转的url
		String lineType = viewDefinitionNewBean.getLineType();
		StringBuilder buttonTemplate = new StringBuilder();
		StringBuilder buttonTemplate2 = new StringBuilder();
		String buttonGroup = "";
		String buttonGroup2 = "";
		if (viewbutton.size() > 0 || selectDefinitionNew.getForKeySelect() == true) {
			buttonTemplate.append("<div class='view_tool2'>");
			if (viewbutton.size() > 0) {
				if (viewDefinitionNewBean.getButtonPlace() != null && viewDefinitionNewBean.getButtonPlace() == true) {
					buttonTemplate2.append("<div class='view_tool_bottom'>");
					if (viewbutton.size() > 0) {
						int i = 0;
						for (CustomButtonNew cbb : viewbutton) {
							//加按钮权限SecurityApiFacade.isGranted
							if (SecurityApiFacade.isGranted(cbb.getCode()) && cbb.getPlace() != null
									&& cbb.getPlace().indexOf("头部") > -1) {
								if (i == 0) {
									buttonTemplate2.append("<div class='customButton customButton_top'>");
									i++;
								}
								if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
										&& buttonGroup2.indexOf(cbb.getButtonGroup()) < 0) {
									buttonGroup2 += "," + cbb.getButtonGroup();
								}
								String place = cbb.getPlace();
								if (place != null && place.indexOf("头部") > -1 && cbb.getButtonGroup() == null) {
									String buttonFunction = cbb.getJsContent();
									String buttonName = cbb.getName();
									String buttonCode = cbb.getCode();
									if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
										buttonFunction = "";
									}
									buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
									buttonTemplate2.append("<button place=\"place_top\" type=\"button\" value=\""
											+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName
											+ "</button>");
								}
							}
						}
						if (i == 1) {
							buttonTemplate2.append("</div>");
						}
					}

					buttonGroup2 = buttonGroup2.replaceFirst(",", "");
					String[] buttonGroupArray2 = buttonGroup2.split(",");
					if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
						buttonTemplate2.append("<div class='customButton_group'>");
					}
					for (int j = 0; j < buttonGroupArray2.length; j++) {
						if (!"".equals(buttonGroupArray2[j])) {
							buttonTemplate2.append("<div class='customButton_group_item'>");
							buttonTemplate2
									.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
											+ buttonGroupArray2[j] + "</div><div class='select_icon'></div></div>");
							buttonTemplate2.append("<div class='customButton_group_buttons_bottom'>");
							for (CustomButtonNew cbb2 : viewbutton) {
								if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
										&& cbb2.getButtonGroup().equals(buttonGroupArray2[j])) {
									String buttonFunction = cbb2.getJsContent();
									String buttonName = cbb2.getName();
									String buttonCode = cbb2.getCode();
									buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
									buttonTemplate2
											.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
													+ buttonCode
													+ "\"  onclick=\""
													+ buttonFunction
													+ "\">"
													+ buttonName);
									buttonTemplate2.append("</div>");
								}
							}
							buttonTemplate2.append("</div>");
							buttonTemplate2.append("</div>");
						}
					}
					if (!"".equals(buttonGroup2) && buttonGroupArray2.length > 0) {
						buttonTemplate2.append("</div>");
					}
				}

				int j = 0;
				for (CustomButtonNew cbb : viewbutton) {
					//加按钮权限SecurityApiFacade.isGranted
					if (SecurityApiFacade.isGranted(cbb.getCode()) && cbb.getPlace() != null
							&& cbb.getPlace().indexOf("头部") > -1) {
						if (j == 0) {
							buttonTemplate.append("<div class='customButton customButton_top'>");
							j++;
						}
						if (cbb.getButtonGroup() != null && !"".equals(cbb.getButtonGroup())
								&& buttonGroup.indexOf(cbb.getButtonGroup()) < 0) {
							buttonGroup += "," + cbb.getButtonGroup();
						}
						String place = cbb.getPlace();
						if (place != null && place.indexOf("头部") > -1 && cbb.getButtonGroup() == null) {
							String buttonFunction = cbb.getJsContent();
							String buttonName = cbb.getName();
							String buttonCode = cbb.getCode();
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate.append("<button place=\"place_top\" type=\"button\" value=\"" + buttonCode
									+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
						}
					}
				}
				if (j == 1) {
					buttonTemplate.append("</div>");
				}
			}

			buttonGroup = buttonGroup.replaceFirst(",", "");
			String[] buttonGroupArray = buttonGroup.split(",");
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("<div class='customButton_group'>");
			}
			for (int j = 0; j < buttonGroupArray.length; j++) {
				if (!"".equals(buttonGroupArray[j])) {
					buttonTemplate.append("<div class='customButton_group_item'>");
					buttonTemplate
							.append("<div class='customButton_group_name'><div class='customButton_group_name_text'>"
									+ buttonGroupArray[j] + "</div><div class='select_icon'></div></div>");
					buttonTemplate.append("<div class='customButton_group_buttons'>");
					for (CustomButtonNew cbb2 : viewbutton) {
						if (cbb2.getButtonGroup() != null && !"".equals(cbb2.getButtonGroup())
								&& cbb2.getButtonGroup().equals(buttonGroupArray[j])) {
							String buttonFunction = cbb2.getJsContent();
							String buttonName = cbb2.getName();
							String buttonCode = cbb2.getCode();
							buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
							buttonTemplate
									.append("<div class=\"customButton_group_button\" place=\"place_top\" value=\""
											+ buttonCode + "\"  onclick=\"" + buttonFunction + "\">" + buttonName);
							buttonTemplate.append("</div>");
						}
					}
					buttonTemplate.append("</div>");
					buttonTemplate.append("</div>");
				}
			}
			if (!"".equals(buttonGroup) && buttonGroupArray.length > 0) {
				buttonTemplate.append("</div>");
			}
			if (selectDefinitionNew.getForKeySelect() == true) {
				List<CondSelectAskInfoNew> condSelectList = dyViewQueryInfoNew.getCondSelectList();
				StringBuilder selectKeyTextAll = new StringBuilder();
				for (int l = 0; l < condSelectList.size(); l++) {
					List<Map<String, String>> keyWords = condSelectList.get(l).getKeyWords();
					if (keyWords != null && keyWords.size() != 0) {
						for (int j = 0; j < keyWords.size(); j++) {
							Map<String, String> keyWord = keyWords.get(j);
							for (String key : keyWord.keySet()) {
								selectKeyTextAll.append(keyWord.get("all"));
							}
						}
					}
				}
				if (StringUtils.isNotEmpty(selectKeyTextAll.toString())) {
					buttonTemplate
							.append("<div class='view_keyword_div'><input type='text' value='"
									+ selectKeyTextAll.toString()
									+ "' onblur=\"if(this.value =='') this.value = '关键字搜索'\" onfocus=\"if(this.value == '关键字搜索') this.value = ''\" name='keyWord' id='keyWord' autocomplete='off'/><button id='keySelect' type='button'>查询</button></div>");
				} else {
					buttonTemplate
							.append("<div class='view_keyword_div'><input type='text' value='关键字搜索' onblur=\"if(this.value =='') this.value = '关键字搜索'\" onfocus=\"if(this.value == '关键字搜索') this.value = ''\" name='keyWord' id='keyWord' autocomplete='off'/><button id='keySelect' type='button'>查询</button></div>");
				}
			}

			buttonTemplate.append("</div>");
		}
		long queryItemCount = 0;
		//获取列标题模板
		Map<String, ColumnDefinitionNew> columnFields = new LinkedHashMap<String, ColumnDefinitionNew>();
		boolean showTitle = viewDefinitionNewBean.getShowTitle();
		String titleSource = getTitleSource(showTitle, viewDefinitionNewBean.getShowCheckBox() == null ? false
				: viewDefinitionNewBean.getShowCheckBox(), columnDefinitionNews, columnFields, datascope);
		String templateAll = "";
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		queryItems = viewDefinitionNewService.getViewData(defaultCondition, whereSql, tableDefinitionId,
				columnDefinitionNews, pageDefinitionNew, dyViewQueryInfoNew, rowIdKey);
		//		queryItemCount = queryItems.size();
		queryItemCount = dyViewQueryInfoNew.getPageInfo().getTotalCount();
		page.setTotalCount(queryItemCount);
		templateAll = getColumnTemplate(viewDefinitionNewBean, columnDefinitionNews, queryItems, columnFields, request);
		viewAndDataNewBean.setData(queryItems);
		if (viewDefinitionNewBean.getIsRead() != null && viewDefinitionNewBean.getIsRead()) {
			readMarkerService.markList(queryItems, SpringSecurityUtils.getCurrentUserId(), readKey, "readFlag");
		}
		viewAndDataNewBean.setViewDefinitionBean(viewDefinitionNewBean);
		List srcList = new ArrayList();
		if (viewDefinitionNewBean.getJsSrc() == null) {
			viewDefinitionNewBean.setJsSrc("");
		}
		String[] srcTemp = viewDefinitionNewBean.getJsSrc().split(",");
		for (int j = 0; j < srcTemp.length; j++) {
			Map srcMap = new HashMap();
			if (!srcTemp[j].equals("")) {
				srcMap.put("src", srcTemp[j]);
				srcList.add(srcMap);
			}
		}
		model.addAttribute("loadJs", "false");
		model.addAttribute("orderbyArr", orderbyArr);
		model.addAttribute("title", orderTitle);
		model.addAttribute("mark", "viewSelect");
		//		model.addAttribute("selectTemplate", selectTemplate);
		//		model.addAttribute("condSelect", condSelect);
		//		model.addAttribute("keySelect", keySelect);
		if (page.getTotalCount() == 0) {
			page.setCurrentPage(0);
		}
		model.addAttribute("page", page);
		model.addAttribute("pageDefinition", pageDefinitionNew);
		model.addAttribute("titleSource", titleSource);
		//		model.addAttribute("buttonTemplate", buttonTemplate);
		//		model.addAttribute("buttonTemplate2", buttonTemplate2);
		model.addAttribute("template", templateAll);
		model.addAttribute("columnDefinitions", columnDefinitionNews);
		model.addAttribute("viewDefinitionBean", viewDefinitionNewBean);
		model.addAttribute("viewAndDataBean", viewAndDataNewBean);
		model.addAttribute("queryItemCount", queryItemCount);
		model.addAttribute("srcList", srcList);
		model.addAttribute("parmStr", parmStr);
		//		model.addAttribute("htmlParmStr", htmlParmStr);
		return forward("/basicdata/view/view_explain_new");
	}

	/**
	 * 
	 * 获取行模板
	 * 
	 */
	public String getColumnTemplate(ViewDefinitionNewBean viewDefinitionNewBean,
			Set<ColumnDefinitionNew> columnDefinitionNews, List<QueryItem> queryItems,
			Map<String, ColumnDefinitionNew> columnFields, HttpServletRequest request) throws Exception {
		String lineType = viewDefinitionNewBean.getLineType();
		StringBuilder template = new StringBuilder();
		String cssValue = "";
		Map<String, Object> root = new HashMap<String, Object>();
		for (int i = 0; i < queryItems.size(); i++) {
			//是否已读
			String isread = "";
			if (viewDefinitionNewBean.getIsRead() != null && viewDefinitionNewBean.getIsRead()) {
				boolean isflag = queryItems.get(i).get("readFlag") == null ? false : (Boolean) queryItems.get(i).get(
						"readFlag");
				if (isflag) {
					isread = "readed";
				} else {
					isread = "noread";
				}
			}
			//获取行模板
			String keyName = "list" + i;
			root.put(keyName, queryItems.get(i));
			//			String jsonStr = queryItems.get(i).toString();
			JSONObject jsonStr = new JSONObject();
			QueryItem item = queryItems.get(i);
			Iterator<String> it = item.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object value = item.get(key);
				jsonStr.put(key, value);
			}

			/**获取视图自定义按钮开始**/
			Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
			StringBuilder buttonTemplate1 = new StringBuilder();
			StringBuilder buttonTemplate2 = new StringBuilder();
			if (viewbutton.size() > 0) {
				buttonTemplate1.append("<div class='customButton1'>");
				buttonTemplate2.append("<div class='customButton2'>");
			}
			for (CustomButtonNew cbb : viewbutton) {
				//加按钮权限SecurityApiFacade.isGranted
				if (SecurityApiFacade.isGranted(cbb.getCode())) {
					String place = cbb.getPlace();
					String buttonName = cbb.getName();
					String buttonCode = cbb.getCode();
					String buttonFunction = cbb.getJsContent();
					//按钮事件的传参处理
					Pattern p1 = Pattern.compile("\\{.*?\\}");
					if (!(!StringUtils.isBlank(buttonFunction) && !"null".equals(buttonFunction))) {
						buttonFunction = "";
					}
					Matcher m1 = p1.matcher(buttonFunction);
					while (m1.find()) {
						String afild = m1.group().replace("{", "").replace("}", "");
						for (String key : columnFields.keySet()) {
							ColumnDefinitionNew columnDefinitionNew = columnFields.get(key);
							if (afild.equals(columnDefinitionNew.getTitleName())) {
								String fieldName = columnDefinitionNew.getColumnAliase();
								String fieldNameTurn = QueryItem.getKey(fieldName);
								String replaceData = "${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!}";
								buttonFunction = buttonFunction.replace("${" + afild + "}", replaceData);
							}
						}
					}
					buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
					if (place != null && place.indexOf("第一行") > -1) {
						buttonTemplate1.append("<button place=\"place_1stline\" type=\"button\" value=\"" + buttonCode
								+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
					} else if (place != null && place.indexOf("第二行") > -1) {
						buttonTemplate2.append("<button place=\"place_2ndline\" type=\"button\" value=\"" + buttonCode
								+ "\"  onclick=\"" + buttonFunction + "\">" + buttonName + "</button>");
					}
				}
			}
			if (viewbutton.size() > 0) {
				buttonTemplate1.append("</div>");
				buttonTemplate2.append("</div>");
			}
			/**获取视图自定义按钮开始结束**/

			String url = viewDefinitionNewBean.getUrl();
			if (url == null) {
				url = "";
			}
			//获取视图的行样式信息
			Set<ColumnCssDefinitionNew> columnCssDefinitionNews = viewDefinitionNewBean.getColumnCssDefinitionNew();
			for (ColumnCssDefinitionNew columnCssDefinitionNew : columnCssDefinitionNews) {
				String viewColumn = columnCssDefinitionNew.getViewColumn();//列
				String columnCondition = columnCssDefinitionNew.getColumnCondition();//列条件
				String columnConditionValue = columnCssDefinitionNew.getColumnConditionValue();//列条件值
				String fontColor = columnCssDefinitionNew.getFontColor();//字体颜色
				String fontWide = columnCssDefinitionNew.getFontWide();//字体是否加粗
				Iterator<?> objkey = jsonStr.keys();
				while (objkey.hasNext()) {// 遍历JSONObject     
					String json_key = (String) objkey.next().toString();
					Object value = jsonStr.get(json_key);
					String json_value = value != null ? value.toString() : "";

					if (json_key.equals(viewColumn)) {
						String condition = DyviewConfigNew.getDyviewColumncssCondtion().get(columnCondition);
						if (condition.equals("包含")) {
							if (json_value.contains(columnConditionValue)) {
								if (fontWide.equals("1")) {
									cssValue = "color:" + fontColor + ";" + " font-weight: bold;";
								} else {
									cssValue = "color:" + fontColor + ";";
								}
							}
						} else if (condition.equals("不包含")) {
							if (!json_value.contains(columnConditionValue)) {
								if (fontWide.equals("1")) {
									cssValue = "color:" + fontColor + ";" + " font-weight: bold;";
								} else {
									cssValue = "color:" + fontColor + ";";
								}
							}
						} else {
							String expression = "'" + json_value + "' " + condition + " '" + columnConditionValue + "'";
							boolean result = (Boolean) Ognl.getValue(expression, null);
							if (result == true) {
								if ("1".equals(fontWide)) {
									cssValue = "color:" + fontColor + ";" + " font-weight: bold;";
								} else {
									cssValue = "color:" + fontColor + ";";
								}
							}
						}
					}
				}
			}
			String NewUrl = "";
			String tr1 = "";
			String tr2 = "";
			NewUrl = url;
			Pattern p1 = Pattern.compile("\\{.*?\\}");
			Matcher m1 = p1.matcher(url);
			int flag = 0;
			while (m1.find()) {
				String afild = m1.group().replace("{", "").replace("}", "");
				for (String key : columnFields.keySet()) {
					ColumnDefinitionNew columnDefinitionNew = columnFields.get(key);
					if (columnDefinitionNew.getShowLine() != null && columnDefinitionNew.getShowLine().equals("第二行")) {
						flag = 1;
					}
					if (afild.equals(columnDefinitionNew.getTitleName())) {
						String fieldName = "";
						if (columnDefinitionNew.getColumnAliase() == null) {
							fieldName = columnDefinitionNew.getFieldName();
						} else {
							fieldName = columnDefinitionNew.getColumnAliase();
						}
						String fieldNameTurn = QueryItem.getKey(fieldName);
						String replaceData = "${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!}";
						NewUrl = NewUrl.replace("${" + afild + "}", replaceData);
					}
				}
			}

			String xyz = jsonStr.toString();
			xyz = URLEncoder.encode(xyz, "utf-8");
			if (i % 2 == 1) {
				if (NewUrl != null && !NewUrl.equals("")) {
					if (i == 0) {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='dataTr odd first tr_bg1 "
								+ isread + "' src='" + getRequestPath(request) + NewUrl.toString() + "'>";
						cssValue = "";
					} else {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='dataTr odd  tr_bg1 " + isread
								+ "' src='" + getRequestPath(request) + NewUrl.toString() + "'>";
						cssValue = "";
					}
				} else if (NewUrl.indexOf("http://") != -1) {
					if (i == 0) {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='dataTr odd first tr_bg1 "
								+ isread + "' src='" + NewUrl.toString() + "'>";
						cssValue = "";
					} else {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='dataTr odd  tr_bg1 " + isread
								+ "' src='" + NewUrl.toString() + "'>";
						cssValue = "";
					}
				} else {
					if (i == 0) {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='odd dataTr first  tr_bg1 "
								+ isread + "'>";
						cssValue = "";
					} else {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='odd  dataTr tr_bg1 " + isread
								+ "'>";
						cssValue = "";
					}
				}
				if (flag == 1) {
					tr2 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='even  tr_bg1 " + isread + "'>";
					cssValue = "";
				}
			} else {
				if (NewUrl != null && !NewUrl.equals("")) {
					if (i == 0) {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='tr_bg2 dataTr odd first "
								+ isread + "' src='" + getRequestPath(request) + NewUrl.toString() + "'>";
						cssValue = "";
					} else {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='tr_bg2 dataTr odd " + isread
								+ "' src='" + getRequestPath(request) + NewUrl.toString() + "'>";
						cssValue = "";
					}
				} else {
					if (i == 0) {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='tr_bg2 dataTr odd first "
								+ isread + "'>";
						cssValue = "";
					} else {
						tr1 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='tr_bg2 dataTr odd " + isread
								+ "'>";
						cssValue = "";
					}
				}
				if (flag == 1) {
					tr2 = "<tr jsonStr='" + xyz + "' style='" + cssValue + "' class='tr_bg2 even tr_bg2even " + isread
							+ "'>";
					cssValue = "";
				}
			}
			StringBuilder source1 = new StringBuilder();
			StringBuilder source2 = new StringBuilder();
			//是否显示复选框
			if (viewDefinitionNewBean.getShowCheckBox() != null && viewDefinitionNewBean.getShowCheckBox()) {
				String getCheckKey = viewDefinitionNewBean.getCheckKey();
				if (getCheckKey == null || (getCheckKey != null && getCheckKey.equals(""))) {
					getCheckKey = "uuid";
				}
				source1.append("<td width='15px'><input type='checkbox' class='checkeds' ");
				for (String key : columnFields.keySet()) {
					String replaceData = "";
					ColumnDefinitionNew columnDefinitionNew = columnFields.get(key);
					if (StringUtils.isNotBlank(columnDefinitionNew.getFieldName())
							&& columnDefinitionNew.getFieldName().equals(getCheckKey)) {
						String fieldName = columnDefinitionNew.getColumnAliase();
						String fieldNameTurn = QueryItem.getKey(fieldName);
						replaceData = "${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!}";
						source1.append(" value='" + replaceData + "'");
					}
				}
				source1.append("/></td>");
			}
			for (String key : columnFields.keySet()) {

				ColumnDefinitionNew columnDefinitionNew = columnFields.get(key);
				if (columnDefinitionNew.getShowLine() == null || columnDefinitionNew.getShowLine().equals("")
						|| columnDefinitionNew.getShowLine().equals("第一行")) {
					if (columnDefinitionNew.getHidden() == false) {
						if (columnDefinitionNew.getValueType().equals("1")) {
							//基础列值设置
							String columnValue = columnDefinitionNew.getValue();
							String fieldNameTurn = "";
							if (columnValue.equals(columnDefinitionNew.getTitleName())) {
								String fieldName = "";
								if (columnDefinitionNew.getEntityName() != null) {
									fieldName = columnDefinitionNew.getColumnAliase();
								} else if (columnDefinitionNew.getFieldName().indexOf(".") != -1) {
									fieldName = columnDefinitionNew.getColumnAliase();
								} else if (StringUtils.isNotBlank(columnDefinitionNew.getColumnAliase())) {
									fieldName = columnDefinitionNew.getColumnAliase();
								} else {
									fieldName = columnDefinitionNew.getFieldName();
								}
								fieldNameTurn = QueryItem.getKey(fieldName);
								//								if (datascope == DyviewConfigNew.DYVIEW_DATASCOPE_DYTABLE) {
								//									fieldName = columnDefinitionNew.getFieldName();
								//									String[] fieldNames = fieldName.split("_");
								//									fieldName = "";
								//									for (int n = 0; n < fieldNames.length; n++) {
								//										if (n == 0) {
								//											fieldName += fieldNames[n];
								//										} else {
								//											char fieldNameOld = fieldNames[n].charAt(0);
								//											char fieldNameNew = (fieldNameOld + "").toUpperCase().charAt(0);
								//											String newField = fieldNames[n].replace(fieldNameOld, fieldNameNew);
								//											fieldName += newField;
								//										}
								//									}
								//									fieldNameTurn = fieldName;
								//								} else if (datascope == DyviewConfigNew.DYVIEW_DATASCOPE_MOUDLE) {
								//									fieldName = columnDefinitionNew.getFieldName();
								//									fieldNameTurn = QueryItem.getKey(fieldName);
								//								} else if (datascope == DyviewConfigNew.DYVIEW_DATASCOPE_ENTITY) {
								//									fieldName = columnDefinitionNew.getColumnAliase();
								//									fieldNameTurn = QueryItem.getKey(fieldName);
								//								}

								source1.append("<td field='" + fieldNameTurn + "' title=\"${" + keyName + "['"
										+ fieldNameTurn + "']!}\" width=" + columnDefinitionNew.getWidth() + ">");
								source1.append("${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!" + "}</td>");
							} else {
								source1.append("<td field='" + key + "' width=" + columnDefinitionNew.getWidth() + ">");
								source1.append("${" + keyName + "[" + "'" + key + "'" + "]!" + "}</td>");
							}
						} else if (columnDefinitionNew.getValueType().equals("2")) {
							//高级列值设置
							String columnAliase = QueryItem.getKey(columnDefinitionNew.getColumnAliase());
							source1.append("<td field='" + columnAliase + "' width=" + columnDefinitionNew.getWidth()
									+ ">");
							String columnValue = columnDefinitionNew.getValue();
							Pattern p = Pattern.compile("\\{.*?\\}");
							Matcher m = p.matcher(columnValue);
							while (m.find()) {
								String result = m.group().replace("{", "").replace("}", "");
								for (String key1 : columnFields.keySet()) {
									ColumnDefinitionNew columnDefinition1 = columnFields.get(key1);
									if (result.equals(columnDefinition1.getTitleName())) {
										String fieldName = "";
										if (columnDefinition1.getEntityName() != null) {
											fieldName = columnDefinition1.getColumnAliase();
										} else if (columnDefinitionNew.getFieldName().indexOf(".") != -1) {
											fieldName = columnDefinitionNew.getColumnAliase();
										} else if (StringUtils.isNotBlank(columnDefinitionNew.getColumnAliase())) {
											fieldName = columnDefinitionNew.getColumnAliase();
										} else {
											fieldName = columnDefinition1.getFieldName();
										}
										String fieldNameTurn = QueryItem.getKey(fieldName);
										String replaceData = "${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!}";
										columnValue = columnValue.replace("${" + result + "}", replaceData);

									}
								}
							}
							source1.append(columnValue);
							source1.append("</td>");
						}
					}
				} else {
					if (columnDefinitionNew.getHidden() == false) {
						if (columnDefinitionNew.getValueType().equals("1")) {
							//基础列值设置
							String columnValue = columnDefinitionNew.getValue();
							if (columnValue.equals(columnDefinitionNew.getTitleName())) {
								String fieldName = "";
								if (columnDefinitionNew.getEntityName() != null) {
									fieldName = columnDefinitionNew.getColumnAliase();
								} else if (StringUtils.isNotBlank(columnDefinitionNew.getColumnAliase())) {
									fieldName = columnDefinitionNew.getColumnAliase();
								} else {
									fieldName = columnDefinitionNew.getFieldName();
								}
								String fieldNameTurn = QueryItem.getKey(fieldName);
								source2.append("<td field='" + fieldNameTurn + "' title=\"${" + keyName + "['"
										+ fieldNameTurn + "']!}\" width=" + columnDefinitionNew.getWidth() + ">");
								source2.append("${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!" + "}</td>");
							}
						} else if (columnDefinitionNew.getValueType().equals("2")) {
							//高级列值设置
							String columnAliase = QueryItem.getKey(columnDefinitionNew.getColumnAliase());
							source2.append("<td field='" + columnAliase + "' width=" + columnDefinitionNew.getWidth()
									+ ">");
							String columnValue = columnDefinitionNew.getValue();
							Pattern p = Pattern.compile("\\{.*?\\}");
							Matcher m = p.matcher(columnValue);
							while (m.find()) {
								String result = m.group().replace("{", "").replace("}", "");
								for (String key1 : columnFields.keySet()) {
									ColumnDefinitionNew columnDefinition1 = columnFields.get(key1);
									if (result.equals(columnDefinition1.getTitleName())) {
										String fieldName = "";
										if (columnDefinitionNew.getEntityName() != null) {
											fieldName = columnDefinitionNew.getColumnAliase();
										} else if (StringUtils.isNotBlank(columnDefinitionNew.getColumnAliase())) {
											fieldName = columnDefinitionNew.getColumnAliase();
										} else {
											fieldName = columnDefinitionNew.getFieldName();
										}
										String fieldNameTurn = QueryItem.getKey(fieldName);
										String replaceData = "${" + keyName + "[" + "'" + fieldNameTurn + "'" + "]!}";
										columnValue = columnValue.replace("${" + result + "}", replaceData);

									}
								}
							}
							source2.append(columnValue);
							source2.append("</td>");
						}
					}
				}
			}
			//没有行按钮
			if (buttonTemplate1.toString().indexOf("place_1stline") < 0
					&& buttonTemplate2.toString().indexOf("place_2ndline") < 0) {
				tr1 += source1.toString() + "</tr>";
				//第二行有内容
				if (!tr2.equals("")) {
					//有复选框时
					if (tr1.indexOf("checkbox") > -1) {
						tr2 += "<td width='15px'></td>" + source2.toString() + "</tr>";
					} else {
						tr2 += source2.toString() + "</tr>";
					}
				}
			}
			//有第一行按钮，无第二行按钮
			else if (buttonTemplate1.toString().indexOf("place_1stline") > -1
					&& buttonTemplate2.toString().indexOf("place_2ndline") < 0) {
				tr1 += source1.toString() + "<td class='tr_td_button' style='text-align: right;'>"
						+ buttonTemplate1.toString() + "</td>" + "</tr>";
				//第二行有内容
				if (!tr2.equals("")) {
					//有复选框时
					if (tr1.indexOf("checkbox") > -1) {
						tr2 += "<td width='15px'></td>" + source2.toString() + "</tr>";
					} else {
						tr2 += source2.toString() + "</tr>";
					}
				}
			}
			//有第二行按钮，无第一行按钮
			else if (buttonTemplate1.toString().indexOf("place_1stline") < 0
					&& buttonTemplate2.toString().indexOf("place_2ndline") > -1) {
				tr1 += source1.toString() + "</tr>";
				if (!tr2.equals("")) {
					//有复选框时
					if (tr1.indexOf("checkbox") > -1) {
						tr2 += "<td width='15px'></td>" + source2.toString();
					} else {
						tr2 += source2.toString();
					}
				}
				if (tr2.equals("")) {
					tr2 = "<tr class='tr_bg2 even tr_bg2even '>";
				}
				tr2 += "<td class='tr_td_button' style='text-align: right;'>" + buttonTemplate2.toString() + "</td>";
				tr2 += "</tr>";
			}
			//两行按钮都有
			else if (buttonTemplate1.toString().indexOf("place_1stline") > -1
					&& buttonTemplate2.toString().indexOf("place_2ndline") > -1) {
				tr1 += source1.toString() + "<td class='tr_td_button' style='text-align: right;'>"
						+ buttonTemplate1.toString() + "</td>" + "</tr>";
				if (!tr2.equals("")) {
					//有复选框时
					if (tr1.indexOf("checkbox") > -1) {
						tr2 += "<td width='15px'></td>" + source2.toString();
					} else {
						tr2 += source2.toString();
					}
				}
				if (tr2.equals("")) {
					tr2 = "<tr class='tr_bg2 even tr_bg2even '>";
				}
				tr2 += "<td class='tr_td_button' style='text-align: right;'>" + buttonTemplate2.toString() + "</td>";
				tr2 += "</tr>";
			}
			template.append(tr1 + tr2);
		}
		template.append("<tr class='dataTr' style='height: 2px;display: block;'></tr>");
		TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
		String templateAll = templateEngine.process(template.toString(), root);
		return templateAll;
	}

	/**
	 * 获取路径
	 * 
	 * @param request
	 * @return
	 */
	private static String getRequestPath(HttpServletRequest request) {
		String ctx = request.getContextPath();
		return "/".equals(ctx) ? "" : ctx;
	}

	/**
	 * 
	 * 获取列标题模板
	 * 
	 * @return
	 */
	public String getTitleSource(@RequestParam("showTitle") boolean showTitle,
			@RequestParam("showCheckBox") boolean showCheckBox, Set<ColumnDefinitionNew> columnDefinitionNews,
			Map<String, ColumnDefinitionNew> columnFields, @RequestParam("datascope") int datascope) {
		StringBuilder titleSource = new StringBuilder();
		String fieldName = null;
		titleSource.append("<tr class='thead_tr'>");
		//是否显示复选框
		if (showCheckBox) {
			titleSource.append("<td width='15px' class='checks_td'><input type='checkbox' class='checkall'/></td>");
		}
		int j = 0;
		for (ColumnDefinitionNew columnDefinitionNew : columnDefinitionNews) {
			if (columnDefinitionNew.getHidden() == false
					&& !(columnDefinitionNew.getShowLine() != null && columnDefinitionNew.getShowLine().equals("第二行"))) {
				j++;
			}
		}
		int i = 1;
		for (ColumnDefinitionNew columnDefinitionNew : columnDefinitionNews) {
			String titleName;
			if (columnDefinitionNew.getOtherName() != null && !columnDefinitionNew.getOtherName().equals("")) {
				titleName = columnDefinitionNew.getOtherName();
			} else {
				titleName = columnDefinitionNew.getTitleName();
			}
			if (columnDefinitionNew.getEntityName() != null) {
				fieldName = columnDefinitionNew.getColumnAliase();
			} else {
				fieldName = columnDefinitionNew.getFieldName();
			}

			boolean hidden = columnDefinitionNew.getHidden();
			boolean sortAble = columnDefinitionNew.getSortAble();
			columnFields.put(fieldName, columnDefinitionNew);
			if (hidden == false) {
				if (showTitle == true) {
					if (columnDefinitionNew.getShowLine() == null
							|| !(columnDefinitionNew.getShowLine() != null && columnDefinitionNew.getShowLine().equals(
									"第二行"))) {
						if (sortAble == true) {
							if (i == j) {
								titleSource.append("<td class='sortAble last' orderby='asc' width='"
										+ columnDefinitionNew.getWidth() + "'>" + titleName + "</td>");
							} else {
								titleSource.append("<td class='sortAble' orderby='asc' width='"
										+ columnDefinitionNew.getWidth() + "'>" + titleName + "</td>");
							}
						} else {
							if (i == j) {
								titleSource.append("<td class='last' width='" + columnDefinitionNew.getWidth() + "'>"
										+ titleName + "</td>");
							} else {
								titleSource.append("<td width='" + columnDefinitionNew.getWidth() + "'>" + titleName
										+ "</td>");
							}
						}
						i++;
					}
				}
			}
		}
		titleSource.append("</tr>");
		return titleSource.toString();
	}

	@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<QueryItem> test(@RequestParam("viewUuid") String viewUuid) {
		return viewDefinitionNewService.getViewDataByKey(viewUuid, "ID");
	}

}