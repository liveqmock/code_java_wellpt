/*
 * @(#)2014-3-4 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.demo.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Component;

import com.wellsoft.pt.basicdata.dyview.provider.AbstractViewDataSource;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumn;
import com.wellsoft.pt.basicdata.dyview.provider.ViewColumnType;
import com.wellsoft.pt.basicdata.facade.BasicDataApiFacade;
import com.wellsoft.pt.bpm.engine.entity.TaskInstance;
import com.wellsoft.pt.common.enums.ModuleID;
import com.wellsoft.pt.common.enums.Separator;
import com.wellsoft.pt.core.support.PagingInfo;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.security.acl.service.AclService;
import com.wellsoft.pt.security.acl.support.AclPermission;
import com.wellsoft.pt.security.acl.support.QueryInfo;
import com.wellsoft.pt.unit.facade.UnitApiFacade;
import com.wellsoft.pt.utils.security.SpringSecurityUtils;

/**
 * 
 * Description: 公文督办
 *  
 * @author wangbx
 * @date 2014-4-15
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-4-15.1	wangbx		2014-4-15		Create
 * </pre>
 *
 */
@Component
public class WorkFlowDoBanViewDataSource extends AbstractViewDataSource {

	@Autowired
	protected AclService aclService;

	@Autowired
	protected BasicDataApiFacade basicDataApiFacade;

	@Autowired
	private UnitApiFacade unitApiFacade;

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.app.xzsp.biz.AbstractXZSPViewDataSource#getAllViewColumns()
	 */
	@Override
	public Collection<ViewColumn> getAllViewColumns() {
		Collection<ViewColumn> viewColumns = new ArrayList<ViewColumn>();

		// 环节UUID
		ViewColumn taskInstUuid = new ViewColumn();
		taskInstUuid.setAttributeName("uuid");
		taskInstUuid.setColumnAlias("taskInstanceUuid");
		taskInstUuid.setColumnName("环节.UUID");
		taskInstUuid.setColumnType(ViewColumnType.STRING);
		viewColumns.add(taskInstUuid);
		// 流程UUID
		ViewColumn flowInstUuid = new ViewColumn();
		flowInstUuid.setAttributeName("flowInstance.uuid");
		flowInstUuid.setColumnAlias("flowInstanceUuid");
		flowInstUuid.setColumnName("流程.UUID");
		flowInstUuid.setColumnType(ViewColumnType.STRING);
		viewColumns.add(flowInstUuid);
		// 流程创建时间
		ViewColumn flowInstCreateTime = new ViewColumn();
		flowInstCreateTime.setAttributeName("flowInstance.createTime");
		flowInstCreateTime.setColumnAlias("createTime");
		flowInstCreateTime.setColumnName("流程.创建时间");
		flowInstCreateTime.setColumnType(ViewColumnType.DATE);
		viewColumns.add(flowInstCreateTime);
		// 流程标题
		ViewColumn title = new ViewColumn();
		title.setAttributeName("flowInstance.title");
		title.setColumnAlias("title");
		title.setColumnName("流程.标题");
		title.setColumnType(ViewColumnType.STRING);
		viewColumns.add(title);
		// 环节名称
		ViewColumn taskName = new ViewColumn();
		taskName.setAttributeName("name");
		taskName.setColumnAlias("taskName");
		taskName.setColumnName("环节.名称");
		taskName.setColumnType(ViewColumnType.STRING);
		viewColumns.add(taskName);
		// 前办理人
		ViewColumn assignee = new ViewColumn();
		assignee.setAttributeName("assignee");
		assignee.setColumnAlias("assignee");
		assignee.setColumnName("环节.办理人");
		assignee.setColumnType(ViewColumnType.STRING);
		viewColumns.add(assignee);
		// 受理时间
		ViewColumn startTime = new ViewColumn();
		startTime.setAttributeName("startTime");
		startTime.setColumnAlias("startTime");
		startTime.setColumnName("环节.开始时间");
		startTime.setColumnType(ViewColumnType.DATE);
		viewColumns.add(startTime);
		// 到期时间
		ViewColumn dueTime = new ViewColumn();
		dueTime.setAttributeName("flowInstance.dueTime");
		dueTime.setColumnAlias("dueTime");
		dueTime.setColumnName("到期时间");
		dueTime.setColumnType(ViewColumnType.DATE);
		viewColumns.add(dueTime);
		//预警时间
		ViewColumn alarmTime = new ViewColumn();
		alarmTime.setAttributeName("flowInstance.alarmTime");
		alarmTime.setColumnAlias("alarmTime");
		alarmTime.setColumnName("预警时间");
		alarmTime.setColumnType(ViewColumnType.DATE);
		viewColumns.add(alarmTime);
		// 流程名称
		ViewColumn flowDefName = new ViewColumn();
		flowDefName.setAttributeName("flowDefinition.name");
		flowDefName.setColumnAlias("flowDefName");
		flowDefName.setColumnName("流程.名称");
		flowDefName.setColumnType(ViewColumnType.STRING);
		viewColumns.add(flowDefName);

		return viewColumns;
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleId()
	 */
	@Override
	public String getModuleId() {
		return ModuleID.WORKFLOW.getValue();
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "公文督办";
	}

	/** 
	 * (non-Javadoc)
	 * @see com.wellsoft.pt.basicdata.dyview.provider.ViewDataSource#query(java.util.Collection, java.lang.String, java.util.Map, java.lang.String, com.wellsoft.pt.core.support.PagingInfo)
	 */
	@Override
	public List<QueryItem> query(Collection<ViewColumn> viewColumns, String whereHql, Map<String, Object> queryParams,
			String orderBy, PagingInfo pagingInfo) {
		Iterator<ViewColumn> it = null;
		if (viewColumns.isEmpty()) {
			it = getAllViewColumns().iterator();
		} else {
			it = viewColumns.iterator();
		}
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			ViewColumn viewColumn = it.next();
			sb.append(Separator.COMMA.getValue());
			if (viewColumn.getAttributeName().indexOf(".") > -1) {
				sb.append(viewColumn.getAttributeName());
			} else {
				sb.append("o." + viewColumn.getAttributeName());
			}
			sb.append(" as ");
			sb.append(viewColumn.getColumnAlias());
		}

		QueryInfo<TaskInstance> aclQueryInfo = new QueryInfo<TaskInstance>();
		aclQueryInfo.getPage().setPageNo(pagingInfo.getCurrentPage());
		aclQueryInfo.getPage().setPageSize(pagingInfo.getPageSize());
		aclQueryInfo.setSelectionHql(sb.toString().replaceFirst(Separator.COMMA.getValue(), ""));
		String viewType = "";
		int typeIndex = whereHql.indexOf("and type");
		if (whereHql.indexOf("and type") > -1) {
			viewType = whereHql.substring(typeIndex, typeIndex + 17);
			whereHql = whereHql.substring(0, typeIndex) + whereHql.substring(typeIndex + 17);
		}
		aclQueryInfo.setWhereHql(whereHql);
		for (String key : queryParams.keySet()) {
			aclQueryInfo.addQueryParams(key, queryParams.get(key));
		}
		if (StringUtils.isNotBlank(orderBy)) {
			String[] orderBys = orderBy.split(Separator.COMMA.getValue());
			for (String string : orderBys) {
				aclQueryInfo.addOrderby(string);
			}
		}
		String userId = SpringSecurityUtils.getCurrentUserId();
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(AclPermission.SUPERVISE);
		List<String> sids = new ArrayList<String>();
		sids.add(userId);
		List<QueryItem> items = aclService.queryForItem(TaskInstance.class, aclQueryInfo, permissions, sids);
		pagingInfo.setTotalCount(aclQueryInfo.getPage().getTotalCount());
		// 1催办、2到期、3逾期
		Calendar currentCalendar = Calendar.getInstance();
		Date currentTime = currentCalendar.getTime();
		//Date minTime = DateUtils.getMinTimeCalendar(currentCalendar).getTime();
		//Date maxTime = DateUtils.getMaxTimeCalendar(currentCalendar).getTime();

		List<QueryItem> resultitems = new ArrayList<QueryItem>();
		if (!viewType.equals("")) {
			String[] vts = viewType.split("=");
			for (QueryItem queryItem : items) {
				Object dueTimeObject = queryItem.get(QueryItem.getKey("dueTime")); // 到期时间
				Object alarmTimeObject = queryItem.get(QueryItem.getKey("alarmTime")); //预警时间
				if (dueTimeObject instanceof Date && alarmTimeObject instanceof Date) {
					Date dueTime = (Date) dueTimeObject;
					Date alarmTime = (Date) alarmTimeObject;
					if (currentTime.compareTo(dueTime) < 0 && currentTime.compareTo(alarmTime) >= 0
							&& vts[1].equals("yj")) {
						resultitems.add(queryItem); // 预警
					} else if (currentTime.compareTo(dueTime) > 0 && vts[1].equals("yq")) {
						resultitems.add(queryItem); // 逾期
					} else {
						resultitems.add(queryItem);
					}
				}
			}
		} else {
			resultitems = items;
		}
		pagingInfo.setTotalCount(resultitems.size());
		return resultitems;

	}
}
