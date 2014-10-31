package com.wellsoft.pt.basicdata.view.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.wellsoft.pt.basicdata.view.bean.ViewAndDataNewBean;
import com.wellsoft.pt.basicdata.view.bean.ViewDefinitionNewBean;
import com.wellsoft.pt.basicdata.view.entity.ColumnCssDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.ColumnDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.CustomButtonNew;
import com.wellsoft.pt.basicdata.view.entity.FieldSelect;
import com.wellsoft.pt.basicdata.view.entity.PageDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.SelectDefinitionNew;
import com.wellsoft.pt.basicdata.view.entity.ViewDefinitionNew;
import com.wellsoft.pt.basicdata.view.service.ViewDefinitionNewService;
import com.wellsoft.pt.basicdata.view.support.DyViewQueryInfoNew;
import com.wellsoft.pt.core.service.AbstractApiFacade;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.dyform.facade.DyFormApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2013-5-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-5-20.1	Administrator		2013-5-20		Create
 * </pre>
 *
 */
@Component
public class ViewApiFacade extends AbstractApiFacade {
	@Autowired
	private ViewDefinitionNewService viewDefinitionNewService;

	@Autowired
	private DyFormApiFacade dyFormApiFacade;

	/**
	 * 
	 * 根据视图uuid获得所有的列定义
	 * 
	 * @param viewUuid
	 * @return
	 */
	public Set<ColumnDefinitionNew> getColumnDefinitions(String viewUuid) {
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		Set<ColumnDefinitionNew> columnDefinitionNews = viewDefinitionNewBean.getColumnDefinitionNews();
		return columnDefinitionNews;
	}

	public long getViewDataCount(@RequestParam("viewUuid") String viewUuid, PagingInfo page,
			@RequestParam(value = "count", required = false) String count) {

		DyViewQueryInfoNew dyViewQueryInfoNew = new DyViewQueryInfoNew();
		/************************获取视图的定义bean开始***********************/
		ViewAndDataNewBean viewAndDataNewBean = new ViewAndDataNewBean();
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		if (viewUuid != null && viewUuid != "") {
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
		String roleType = "";
		//获取视图的角色权限信息
		//		String roleType = viewDefinitionNewBean.getRoleType();
		//获取视图的角色类型
		//		String roleValue = viewDefinitionNewBean.getRoleValue();
		String roleValue = "";
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
		}
		if (selectDefinitionNew.getForFieldSelect() == true) {
		}
		//获取视图自定义按钮
		Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
		StringBuilder buttonTemplate = new StringBuilder();
		StringBuilder buttonTemplate2 = new StringBuilder();
		String buttonGroup = "";
		String buttonGroup2 = "";
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
			page.setCurrentPage(-1);
		}
		//点击事件跳转的url
		String lineType = viewDefinitionNewBean.getLineType();
		long queryItemCount = 0;
		//获取列标题模板
		Map<String, ColumnDefinitionNew> columnFields = new LinkedHashMap<String, ColumnDefinitionNew>();
		boolean showTitle = viewDefinitionNewBean.getShowTitle();
		String templateAll = "";
		//根据视图的条件获取数据源的数据
		dyViewQueryInfoNew.setPageInfo(page);
		List<QueryItem> queryItems = new ArrayList<QueryItem>();
		String whereSql = "";
		queryItems = viewDefinitionNewService.getViewData(defaultCondition, whereSql, tableDefinitionId,
				columnDefinitionNews, pageDefinitionNew, dyViewQueryInfoNew, rowIdKey);
		queryItemCount = dyViewQueryInfoNew.getPageInfo().getTotalCount();
		page.setTotalCount(queryItemCount);
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
		return queryItemCount;

	}

	/**
	 * 
	 * 获得视图按钮
	 * 
	 * @param moudleId
	 * @return
	 */
	public List<Map<String, Object>> getViewButtons(String viewUuid, HttpServletRequest request) {
		List<Map<String, Object>> list_ = new ArrayList<Map<String, Object>>();
		ViewDefinitionNewBean viewDefinitionNewBean = new ViewDefinitionNewBean();
		viewDefinitionNewBean = viewDefinitionNewService.getBeanByUuid(viewUuid);
		Set<CustomButtonNew> viewbutton = viewDefinitionNewBean.getCustomButtonNews();
		for (CustomButtonNew cbb : viewbutton) {
			Map<String, Object> map_ = new HashMap<String, Object>();
			String buttonName = cbb.getName();
			String buttonCode = cbb.getCode();
			String buttonFunction = cbb.getJsContent();
			map_.put("name", buttonName);
			map_.put("code", buttonCode);
			buttonFunction = buttonFunction.replace("${ctx}", request.getContextPath());
			map_.put("function", buttonFunction);
			list_.add(map_);
		}
		return list_;
	}

	/**
	 * 
	 * 通过字段名称key及对应的值查询视图数据（当key为null默认uuid列）
	 * 
	 * @param val
	 * @param key
	 * @return
	 */
	public List<QueryItem> getViewDataByKey(String val, String key) {
		return viewDefinitionNewService.getViewDataByKey(val, key);
	}

	/**
	 * 
	 * 根据视图的ID获取视图的uuid
	 * 
	 * @param viewId
	 * @return
	 */
	public String getViewUuid(String viewId) {
		ViewDefinitionNew viewDefinitionNew = viewDefinitionNewService.getByViewId(viewId);
		return viewDefinitionNew.getUuid();
	}
}
