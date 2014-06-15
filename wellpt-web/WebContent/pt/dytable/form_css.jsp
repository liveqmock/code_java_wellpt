<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.wellsoft.pt.utils.security.SpringSecurityUtils"%>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<link rel="stylesheet" type="text/css" 
		href="${ctx}/resources/theme/css/wellnewoa.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/calendar.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/fileupload.css" />
<style>
/* Component containers
----------------------------------*/
.ui-widget { font-family: "Microsoft YaHei"; font-size: 12px; }
.ui-widget .ui-widget { font-size: 11px; }
.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button { font-family:"Microsoft YaHei"; font-size: 12px; }
<!--
/* 组织选择框 */
.ui-dialog {
	padding:0;
}
.ui-widget-header{
	background: url("${ctx}/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
	border-bottom-right-radius: 0;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl {
	border-bottom-left-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr {
	border-top-right-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl {
	border-top-left-radius: 0;
}
.ui-resizable-handle.ui-resizable-s {
	background: url("${ctx}/resources/theme/images/v1_panel.png") repeat-x scroll 0 -10px transparent;
}
.ui-resizable-handle.ui-resizable-e {
	background: url("${ctx}/resources/theme/images/v1_bd.png") repeat-y scroll 100% 0 transparent;
	right: -2px;
} 
-->
</style>
<script type="text/javascript">
	var port = window.location.port;
	var host = window.location.host.replace(":" + port, "");
	document.write('<OBJECT ID="MSOffice" WIDTH=0 HEIGHT=0 CLASSID="clsid:12FCCF9A-24C5-4556-AD8B-ACFAE87E7C4A" codebase="${ctx}/resources/pt/js/dytable/WebFun.ocx#version=1.0.1.3">');
	document.write('<PARAM NAME="protocol" VALUE="http">');
	document.write('<PARAM NAME="username" VALUE="<%=SpringSecurityUtils.getCurrentUserName()%>">');
	document.write('<PARAM NAME="domain" VALUE="'+host+'">');

	document.write('<PARAM NAME="port" VALUE="'+port+'">');
	document.write('<PARAM NAME="cgi" VALUE="${ctx}/fileUpload">');
	document.write('<PARAM NAME="downloadPath" VALUE="c:\\temp\\BodyTemp">');
	document.write('<PARAM NAME="DelTempPath" VALUE=true>');
	document.write('</OBJECT>');
</script>