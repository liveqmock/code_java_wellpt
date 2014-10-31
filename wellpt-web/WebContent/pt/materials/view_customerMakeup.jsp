<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户验货标准解读跟踪清单信息补录</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title" style="">
				<h2>客户验货标准解读跟踪清单信息补录</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<input type="hidden" id='formUuid' value='${formUuid}'> <input
			type="hidden" id='dataUuid' value='${dataUuid}'> 
		<input type="hidden" id='jdsx' value='${jdsx}'>
		<input type="hidden" id='xh' value='${xh}'>
		<input type="hidden" id='khz' value='${khz}'>
		<input type="hidden" id='ksrq' value='${ksrq}'>
		<input type="hidden" id='wcrq' value='${wcrq}'>
		<input type="hidden" id='jdry' value='${jdry}'>
		<form action="" id="customerMakeupForm" class="dyform"></form>
	</div>
</body>

<%@ include file="/pt/dyform/dyform_js.jsp"%>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/materials/view_customerMakeup.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
</html>