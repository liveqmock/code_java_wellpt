<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Format Form</title>
<%@ include file="/pt/common/meta.jsp"%>

<link href="${ctx}/resources/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />

</head>
<body>
	<div id="toptoolbar"></div>
	<form:form id="form" commandName="position" method="post"
		action="${ctx}/org/position/${action}" cssClass="cleanform">
		<c:if test="${not empty position.uuid}">
			<form:hidden path="uuid"></form:hidden>
		</c:if>
		<c:if test="${not empty position.parentUuid}">
			<form:hidden path="parentUuid"></form:hidden>
			<form:hidden path="parentName"></form:hidden>
		</c:if>
		<table>
			<tr>
				<td><label>名称</label></td>
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
				<td><input type="submit" value="保存"></input></td>
			</tr>
		</table>
	</form:form>

	<script src="${ctx}/resources/easyui/jquery-1.8.0.min.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/ligerUI/js/ligerui.min.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$("#toptoolbar").ligerToolBar({
				items : [ {
					id : 'add',
					text : '增加',
					click : itemclick,
					icon : 'add'
				}, {
					line : true
				}, {
					id : 'delete',
					text : '删除',
					click : itemclick
				} ]
			});
			function itemclick(item) {
				switch (item.id) {
				case "add":
					$(location)
							.attr('href',
									'${ctx}/org/position/create?parentUuid=${position.uuid}');
					break;
				case "delete":
					break;
				}
			}
		});
	</script>
</body>
</html>