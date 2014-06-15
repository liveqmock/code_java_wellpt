<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/js/common/jquery.commercialRegister.css" />
</head>
<body>
	${unids}${result}
</body>
</html>