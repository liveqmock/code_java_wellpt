<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>商事统计报表统一访问jsp</title>
<style type="text/css">

</style>
</head>
<body>
	<div class="viewContent">
		<c:choose>
			<c:when test="${ bypagesize eq 'false'}">
				<iframe id="reportFrame" width="100%" height="482px" frameborder="0" src="${ctx }/ReportServer?reportlet=${reportletPath }&__bypagesize__=false"></iframe>
			</c:when>
			<c:when test="${ bypagesize eq '' || bypagesize eq 'true'}">
				<iframe id="reportFrame" width="100%" height="482px" frameborder="0" src="${ctx }/ReportServer?reportlet=${reportletPath }"></iframe>
			</c:when>
		</c:choose>
	</div>
</body>
</html>