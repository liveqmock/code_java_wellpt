<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<script>
		var height = $(".tab-content").css("height");
	</script>
	<iframe src="${url}" frameborder="no" style="width:100%;height:100%;border: medium none;"></iframe>
</body>
</html>