<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>报表设计</title>
</head>
<body>
	<iframe id="reportFrame" width="1250" height="850" src="${ctx }/ReportServer?op=fs"></iframe>
</body>
</html>