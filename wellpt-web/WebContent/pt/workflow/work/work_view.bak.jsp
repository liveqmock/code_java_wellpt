<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Work View</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/easyui/themes/icon.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#save").click(function(){
			$("#form").attr("action", "new");
			$("#form").submit();
			return false;
		});
		$("#submit").click(function(){
			$("#form").attr("action", "submit");
			$("#form").submit();
			return false;
		});
		$("#rollback").click(function(){
			$("#form").attr("action", "rollback");
			$("#form").submit();
			return false;
		});
		$("#cancel").click(function(){
			$("#form").attr("action", "cancel");
			$("#form").submit();
			return false;
		});
		$("#transfer").click(function(){
			$("#form").attr("action", "transfer");
			$("#form").submit();
			return false;
		});
		$("#counterSign").click(function(){
			$("#form").attr("action", "counterSign");
			$("#form").submit();
			return false;
		});
		$("#attention").click(function(){
			$("#form").attr("action", "attention");
			$("#form").submit();
			return false;
		});
		$("#webPrint").click(function(){
			window.print();
		});
		$("#print").click(function(){
			$("#form").attr("action", "print");
			$("#form").submit();
			return false;
		});
	});
</script>
</head>
<body>
<div id="toolbar">
	<a href="#" id="save" class="easyui-linkbutton">保存</a>
	<a href="#" id="submit" class="easyui-linkbutton">提交</a>
<!-- 	<a href="#" id="rollback" class="easyui-linkbutton">退回</a> -->
<!-- 	<a href="#" id="cancel" class="easyui-linkbutton">撤回</a> -->
<!-- 	<a href="#" id="transfer" class="easyui-linkbutton">转办</a> -->
<!-- 	<a href="#" id="counterSign" class="easyui-linkbutton">会签</a> -->
<!-- 	<a href="#" id="attention" class="easyui-linkbutton">关注</a> -->
<!-- 	<a href="#" id="webPrint" class="easyui-linkbutton">打印页面</a> -->
<!-- 	<a href="#" id="print" class="easyui-linkbutton">套打</a> -->
</div>
<div style="padding:3px 2px;border-bottom:1px solid #ccc">${workBean.title}</div>  
<form:form id="form" commandName="workBean" action="new" method="post" cssClass="cleanform">
	<form:hidden path="taskUuid"></form:hidden>
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
</body>
</html>