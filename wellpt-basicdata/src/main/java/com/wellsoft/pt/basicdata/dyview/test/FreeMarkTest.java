/*
 * @(#)2013-3-20 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.dyview.test;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import com.wellsoft.pt.basicdata.dyview.bean.ViewDefinitionBean;
import com.wellsoft.pt.basicdata.dyview.entity.ColumnDefinition;
import com.wellsoft.pt.basicdata.dyview.service.ViewDefinitionService;
import com.wellsoft.pt.bpm.engine.entity.FlowInstance;
import com.wellsoft.pt.core.support.QueryItem;
import com.wellsoft.pt.core.template.TemplateEngine;
import com.wellsoft.pt.core.template.TemplateEngineFactory;
import com.wellsoft.pt.core.test.spring.SpringTxTestCase;
import com.wellsoft.pt.security.acl.service.AclService;
import com.wellsoft.pt.security.acl.support.AclPermission;

/**
 * Description: 如何描述该类
 *  
 * @author wubin
 * @date 2013-3-20
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-3-20.1	wubin		2013-3-20		Create
 * </pre>
 *
 */
@ContextConfiguration(locations = { "/applicationContext-core.xml" })
public class FreeMarkTest extends SpringTxTestCase {

	@Autowired
	private ViewDefinitionService viewDefinitionService;

	@Autowired
	private AclService aclService;

	@Test
	@Rollback(false)
	public void testTT() throws Exception {

		//			fail("Not yet implemented");
		String viewUuid = "ceef55ce-09c2-4095-9251-9361a1aab33f";
		ViewDefinitionBean viewDefinitionBean = new ViewDefinitionBean();
		viewDefinitionBean = viewDefinitionService.getBeanByUuid(viewUuid);
		String tableName = viewDefinitionBean.getTableDefinitionName();
		String formUuid = viewDefinitionBean.getFormuuid();
		String defaultCondition = viewDefinitionBean.getDefaultCondition();
		Set<ColumnDefinition> columnDefinitions = viewDefinitionBean.getColumnDefinitions();

		//获取该视图定义下的所有列数据
		List<QueryItem> queryItems = viewDefinitionService.getColumnData(defaultCondition, tableName,
				columnDefinitions, null, null);

		//		Map<String, Object> queryItem = new HashMap<String, Object>();
		//		queryItem.put("a", 1);
		//		queryItem.put("b", 2);
		//		QueryItem ite = new QueryItem();
		StringBuilder source = new StringBuilder();
		source.append("<#list list as item><#list item?keys as key>" + "<tr>");
		source.append("<td>");
		source.append("a:${item['username']!}");
		source.append("b:${item['insertTime']!}");
		source.append("</td>");
		source.append("</tr></#list></#list>");

		TemplateEngine templateEngine = TemplateEngineFactory.getDefaultTemplateEngine();
		String template = templateEngine.process(source.toString(), queryItems);
		System.out.println(template);
	}

	@Test
	@Rollback(false)
	public void testFlt() throws Exception {
		String a = "<table><tr><td>${插入时间}</td><td>${督办人}${aaa}${人生啊}</td></tr></table>";
		String b = "动态表单新需求汇总.doc";
		String[] c = b.split("/./");
		System.out.println(c);
		//		Pattern p = Pattern.compile(".+?\\{(.+?)\\}");
		//		Matcher m = p.matcher(a);
		//		while (m.find()) {
		//			String result = m.group(1);
		//			System.out.println(result);
		//			String replaceData = "{list0['insertTime']!}";
		//			String NewcolumnValue = a.replaceAll("\\{(.+?)\\}", replaceData);
		//			System.out.println(NewcolumnValue);
		//		}

	}

	@Test
	@Rollback(false)
	public void testQuery() throws Exception {
		com.wellsoft.pt.security.acl.support.QueryInfo aclQueryInfo = new com.wellsoft.pt.security.acl.support.QueryInfo();
		String selectionHql = "o.uuid as flowInstUuid,o.title as title,o.createTime as createTime,o.currentTaskInstance.uuid as taskUuid,o.currentTaskInstance.name as taskName,o.currentTaskInstance.assignee as previousAssignee,o.currentTaskInstance.startTime as arrivalTime,o.currentTaskInstance.endTime as dueTime,o.flowDefinition.name as flowName";
		aclQueryInfo.setSelectionHql(selectionHql);
		aclQueryInfo.setWhereHql("o.currentTaskInstance.name like :taskName or o.title like :title");
		aclQueryInfo.addQueryParams("taskName", "%环节%");
		aclQueryInfo.addQueryParams("title", "%环节%");
		//		aclQueryInfo.addOrderby("createTime", "desc");
		//aclQueryInfo.addOrderby(orderBy);
		//查询待办的任务
		List<QueryItem> queryItems = aclService.queryForItem(FlowInstance.class, aclQueryInfo, AclPermission.TODO,
				"112");
		System.out.println("1111");
	}

	public static void main(String[] args) {
		String b = "动态表单新需求汇总.doc";
		String[] c = b.split("\\.");
		System.out.println(c);
	}

}
