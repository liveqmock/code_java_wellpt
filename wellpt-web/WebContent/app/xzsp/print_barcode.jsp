<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<body onload="window.print();">
	<div style="text-align: center; margin-top: 100px">
		<img alt="${barcode}" src="${ctx}/common/generator/barcode/${barcode}">
	</div>
</body>
</html>