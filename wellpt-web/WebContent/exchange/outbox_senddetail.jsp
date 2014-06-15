<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${viewForm.title}</title>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>	
<%@ include file="/pt/dytable/form_css.jsp"%>	
<link href="${ctx}/resources/exchange/css/exchange.css" rel="stylesheet" />
</head>
<body>
<div class="form_header">
	<div class="form_title"><h2>${viewForm.title}</h2></div>
	<div id="toolbar" class="form_toolbar">
		<div class="form_operate">
			<button id="btn_close" type="button" onclick="window.opener=null;window.close();" class="btn btn-primary"><spring:message code="exchange.btn.close" /></button>
			<button id="btn_save" type="button" class="btn btn-primary"><spring:message code="exchange.btn.save" /></button>
		</div>
	</div>
</div>
<div class="form_content"></div>
<form:form id="draft_form" commandName="viewForm" cssClass="cleanform">
	<form:hidden path="formUUID" id="formUUID"></form:hidden>
	<form:hidden path="dataUUID" id="dataUUID"></form:hidden>
	<form:hidden path="title"></form:hidden>
	<form:hidden path="documentUUID" id="documentUUID"></form:hidden>
	<input type="hidden" id="otype" name="otype" value="${otype}" />
</form:form>
<div class="dytable_form">
<div class="post-sign">	
<div class="post-detail">
	<table width="100%">
		<tr class="title">
			<td class="Label" colspan="4">基本信息</td>
		</tr>
		<tr class="odd">
			<td class="label">发送意见</td>
			<td class="value" colspan=3><textarea name="dy_opinion" id="dy_opinion"></textarea></td>
		</tr>
		<tr>
			<td class="label" valign="top">选择对象</td>
			<td class="value" colspan=3><ul id="sender_tree" class='ztree'></ul></td>
		</tr>
	</table>
</div>
</div>
</div>

<%@ include file="/pt/dytable/form_js.jsp"%>
<%@ include file="/exchange/cm_option.jsp"%>
<script type="text/javascript" src="${ctx}/resources/exchange/js/common_v1.js"></script>
<script type="text/javascript" src="${ctx}/resources/exchange/js/outbox_v1.js"></script>
<script type="text/javascript">
$(function() {
	var hasSendee = 1;
	if($('#otype').val() == "FINISH") hasSendee = 0;
	OutboxUI.InitSendTree($('#documentUUID').val(), hasSendee);
});
</script>
</body>
</html>