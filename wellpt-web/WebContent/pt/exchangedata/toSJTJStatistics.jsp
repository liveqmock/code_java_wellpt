<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>数据统计</title>
<style type="text/css">

</style>
</head>
<body>
	<div class="viewContent">
		<iframe id="reportFrame" width="100%" height="482px" frameborder="0" src="${ctx }/ReportServer?reportlet=statistics/数据统计.cpt&__bypagesize__=false"></iframe>
	</div>
</body>
</html>