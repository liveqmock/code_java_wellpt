<%@page import="com.wellsoft.pt.core.context.ApplicationContextHolder"%>
<%@page import="com.wellsoft.pt.cms.facade.CmsApiFacade"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>首页</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.workflowComboTree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
	src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
</head>
<body>
	<!-- 	<label for="apply_to">应用于</label> -->
	<!-- 	<input id="apply_to" type="text" /> -->
	<!-- 	<div id="my_dialog">我的主页</div> -->
	<input id="my_home" />
	<input id="my_home2" value="TIMER_TEST_1" />
	<!-- 	<iframe id="dialog" width="100%" style="display: none"></iframe> -->
	<%
		CmsApiFacade cmsApiFacade = ApplicationContextHolder
				.getBean(CmsApiFacade.class);
		String homePage = cmsApiFacade.getIndexCmsPage();
	%>
	<jsp:forward page="<%=homePage%>"></jsp:forward>
</body>
<script type="text/javascript">
	$("#my_home").workflowComboTree({
		labelField : "my_home",
		valueField : "my_home2"
	});
</script>
</html>