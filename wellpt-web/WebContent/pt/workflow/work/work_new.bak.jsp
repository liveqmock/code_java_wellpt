<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
</style>

</head>
<body>
<div id="toolbar">
	<button id="btn_save" class="btn">保存</button>
	<button id="btn_submit" class="btn">提交</button>
</div>
<div style="padding:3px 2px;border-bottom:1px solid #ccc"><h3>${workBean.title}</h3></div>
<form:form id="form" commandName="workBean" action="new" method="post" cssClass="cleanform">
	<form:hidden path="flowInstUuid"></form:hidden>
	<form:hidden path="flowDefUuid"></form:hidden>
    <table>
    	 <tr>
			<td><label>申请人</label></td>
        	<td><form:input path="applicant"></form:input></td>
        	<td><form:errors path="applicant" cssClass="error" /></td>
        </tr>  
        <tr>  
			<td><label>开始时间</label></td>
        	<td><form:input path="fromTime" cssClass="easyui-datebox"></form:input></td>
        	<td><form:errors path="fromTime" cssClass="error" /></td>
        </tr>  
        <tr>
			<td><label>结束时间</label></td>
        	<td><form:input path="toTime" cssClass="easyui-datebox"></form:input></td>
        	<td><form:errors path="toTime" cssClass="error" /></td>
        </tr>  
        <tr>  
			<td><label>原因</label></td>
			<td><form:textarea path="reason"></form:textarea></td>
        	<td><form:errors path="reason" cssClass="error" /></td>
        </tr>
    </table>  
</form:form>

<!-- Project -->
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/workflow/work/work_new.js"></script>
</body>
</html>