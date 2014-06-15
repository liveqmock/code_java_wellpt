<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Format Form</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/ligerUI/js/ligerui.min.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/ligerUI/js/plugins/ligerTree.js"
	type="text/javascript"></script>
<script src="${ctx}/resources/ligerUI/js/plugins/ligerComboBox.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
	});
</script>
</head>
<body>
	<div style="padding: 3px 2px; border-bottom: 1px solid #ccc">岗位维护</div>
	<form:form id="form" commandName="position" method="post"
		action="${ctx}/org/position/${action}" cssClass="cleanform">
		<c:if test="${not empty position.parentUuid}">
			<form:hidden path="parentUuid"></form:hidden>
			<form:hidden path="parentName"></form:hidden>
		</c:if>
		<table>
			<tr>
				<td><label>部门名</label></td>
				<td><form:input path="name"></form:input></td>
				<td><form:errors path="name" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label>编码</label></td>
				<td><form:input path="code"></form:input></td>
				<td><form:errors path="code" cssClass="error" /></td>
			</tr>
			<tr>
				<td><label>备注</label></td>
				<td><form:textarea path="remark"></form:textarea></td>
				<td><form:errors path="remark" cssClass="error" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" id="getval" value="取值"></input></td>
				<td><input type="submit" value="保存"></input></td>
			</tr>
		</table>
	</form:form>
</body>
</html>