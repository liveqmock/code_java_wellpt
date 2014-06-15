<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}
.tabs-1_td{
	width: 170px;
}
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
</style>

</head>
<body>
	<div id="container">
		<div class="pane ui-layout-center">
		
			
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1"><spring:message code="mail.label.mailSetC"/></a></li>
						</ul>
						<div id="tabs-1">
							<input type="hidden" id="uuid" name="uuid" />
							<form:form id="form" commandName="mailConfig" method="post" action="${ctx}/mail/mailconfig_save.action" cssClass="cleanform">
								<form:hidden path="uuid"></form:hidden>
									<table>
								    	 <tr>
											<td class="tabs-1_td"><label><spring:message code="mail.label.address"/></label></td>
								        	<td><form:input path="address"></form:input></td>
								        	<td><form:errors path="address" cssClass="error" /></td>
								        </tr>  
								        
								        <tr>  
								        	<td class="tabs-1_td"><label><spring:message code="mail.label.pop3"/></label></td>
								        	<td><form:input path="pop3"></form:input></td>
								        	<td><form:errors path="pop3" cssClass="error" /></td>
								        </tr>
								        <tr>  
								        	<td class="tabs-1_td"><label><spring:message code="mail.label.pop3port"/></label></td>
								        	<td><form:input path="pop3Port"></form:input></td>
								        	<td><form:errors path="pop3Port" cssClass="error" /></td>
								        </tr>  
								        <tr>  
											<td class="tabs-1_td"><label><spring:message code="mail.label.smtp"/></label></td>
								        	<td><form:input path="smtp"></form:input></td>
								        	<td><form:errors path="smtp" cssClass="error" /></td>
								        </tr>  
								        <tr>  
											<td class="tabs-1_td"><label><spring:message code="mail.label.smtpport"/></label></td>
								        	<td><form:input path="smtpPort"></form:input></td>
								        	<td><form:errors path="smtpPort" cssClass="error" /></td>
								        </tr>  
								        
								        <tr>  
											<td class="tabs-1_td"><label><spring:message code="mail.label.keepOnServer"/></label></td>
								        	<td><input type="checkbox" id="keepOnServer" name="keepOnServer" checked="${keepOnServer}" /></td>
								        </tr>  
								        
								    </table>  
										    
								</div>
							</div>
							
							<div class="btn-group btn-group-bottom">
								<input type="submit" value="<spring:message code="mail.btn.save"/>"></input>
							</div>
						</form:form>
		</div>
	</div>


	<!-- Project -->
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/mail/mail_config.js"></script>
	<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</body>
</html>