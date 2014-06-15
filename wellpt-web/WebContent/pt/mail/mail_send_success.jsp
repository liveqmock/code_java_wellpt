<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>mail_send_success</title>
<link href="${ctx}/resources/pt/css/mail_send_success.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2><spring:message code="mail.label.sendSuccess"/></h2>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<button id="btn_close" onclick="window.opener=null;window.close();" type="button"><spring:message code="mail.btn.close"/></button>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</body>
</html>