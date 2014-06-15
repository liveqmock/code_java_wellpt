<%@page import="org.apache.commons.lang.exception.ExceptionUtils"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>

<%
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ExceptionUtils.getStackTrace(exception), exception);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统忙</title>
</head>

<body>
	<div>
		<h1>系统忙，请稍候再试!</h1>
	</div>
<!-- 	<div> -->
<%-- 		<a href="<c:url value="/"/>" target="_top">返回首页</a> --%>
<!-- 	</div> -->
<!-- 	<p> -->
<!-- 		<strong>错误信息</strong><br /> -->
<%-- 		<% --%>
<!-- // 			StringWriter sw = new StringWriter(); -->
<!-- // 			PrintWriter pw = new PrintWriter(sw); -->
<!-- // 			exception.printStackTrace(pw); -->
<!-- // 			out.print(sw.toString()); -->
<%-- 		%> --%>
<!-- 	</p> -->
</body>
</html>
