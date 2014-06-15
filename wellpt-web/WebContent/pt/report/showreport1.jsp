<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>威尔 - 统一业务应用与开发平台</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<!-- Bootstrap -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />


</head>
<body>
	<iframe id="reportFrame" width="1024" height="600" frameborder="0" src="${ctx }/ReportServer?reportlet=/demo/chart/staticchart.cpt"></iframe>
</body>
</html>