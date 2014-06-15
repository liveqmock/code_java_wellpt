<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Exchange</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/exchange/css/exchange.css" rel="stylesheet" />
<link href="${ctx}/resources/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
</head>

<body>
<div class="dytable_form">
<div class="post-sign">	
<div class="post-detail">
	<form class="form-horizontal" id="config_form">
	<!-- 表类型(tableType):1主表,2从表 -->
	<table width="100%" tableType="1" id="001" editType="1">
		<tr class="title">
	    	<td class="Label" colspan="4">基本信息</td>
	  	</tr>
		<tr class="odd">
			<td class="label"><spring:message code="exchange.label.config.moduleName" /></td>
			<td class="value"><input type="text" id="moduleName" /></td>			
		</tr>
		<tr>
			<td class="label"><spring:message code="exchange.label.config.moduleManagerName" /></td>
			<td class="value" colspan="3">
			<input id="moduleManagerName" name="moduleManagerName" type="text" /> 
			<input id="moduleManager" name="moduleManager" type="hidden" /></td>
		</tr>
		<tr class="odd">
			<td class="label"><spring:message code="exchange.label.config.moduleSupervisorName" /></td>
			<td class="value" colspan="3">
			<input id="moduleSupervisorName" name="moduleSupervisorName" type="text" /> 
			<input id="moduleSupervisor" name="moduleSupervisor" type="hidden" /></td>
		</tr>
		<tr>
			<td class="label"><spring:message code="exchange.label.config.dyTableName" /></td>
			<td class="value">
				<input type="text" id="formName" /> 
				<span class="help-inline"><spring:message code="exchange.label.config.dyTableName.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"><spring:message code="exchange.label.config.dyTableVersion" /></td>
			<td class="value">
				<input type="text" id="formVersion" /> 
				<span class="help-inline"><spring:message code="exchange.label.config.dyTableVersion.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="exchange.label.config.messageTemplate" /></td>
			<td class="value" colspan="3">
			<input type="text" id="messageTemplate" />
			</td>
		</tr>
		<tr class="odd">
			<td class="label"><spring:message code="exchange.label.config.draftLeaveDays" /></td>
			<td class="value" colspan="3">
			<input type="text" id="draftLeaveDays" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.draftLeaveDays.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"><spring:message code="exchange.label.config.trashLeaveDays" /></td>
			<td class="value" colspan="3">
			<input type="text" id="trashLeaveDays" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.draftLeaveDays.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="showTraceUser" />
			<span class="help-inline"><spring:message code="exchange.label.config.showTraceUser.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="showTraceAll" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.showTraceAll.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="showLogs" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.showLogs.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="attachmentForSign" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.attachmentForSign.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="attachmentForOpinion" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.attachmentForOpinion.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="allowLimitTime" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.allowLimitTime.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="forceSignature" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.forceSignature.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="feedbackIsUnread" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.feedbackIsUnread.help" /></span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="repeatIsUnread" />
			<span class="help-inline"><spring:message code="exchange.label.config.repeatIsUnread.help" /></span>
			</td>
		</tr>
		<tr class="odd">
			<td class="label"></td>
			<td class="value" colspan="3">
			<input type="checkbox" id="forceSignature" /> 
			<span class="help-inline"><spring:message code="exchange.label.config.forceSignature.help" /></span>
			</td>
		</tr>
		<tr class="title">
			<td colspan="4" align="center">
				<button id="btn_save" type="button" class="btn btn-primary"><spring:message code="exchange.btn.save" /></button>
			</td>
		</tr>		
	</table>
	</form>
</div>
</div>
</div>		
	
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"> </script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/exchange/js/common_v1.js"></script>
	<script type="text/javascript" src="${ctx}/resources/exchange/js/setting.js"></script>
</body>
</html>