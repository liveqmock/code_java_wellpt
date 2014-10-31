<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>Bo用户设置</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>Bo用户设置</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="uuid" id="uuid" value="${objectView.uuid}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">Bo用户设置</td>
					</tr>
					<tr class="field">
						<td width="110px;">用户</td>
						<td><input name="userName" id="userName" value="${objectView.userName}" readonly="true">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">部门</td>
						<td><input name="departmentName" id="departmentName"
							value="${objectView.departmentName}" readonly="true"></td>
					</tr>
					<tr class="field">
						<td width="110px;">Bo用户</td>
						<td><input name="boUser" id="boUser"
							value="${objectView.boUser}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">Bo密码</td>
						<td><input name="boPwd" id="boPwd"
							value="${objectView.boPwd}"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
		$("#form_close").click(function() {
			window.close();
		});
		$("#form_save").click(function() {
			var bean = {
				"uuid" : null,
				"userName" : null,
				"departmentName" : null,
				"boUser" : null,
				"boPwd" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "boUserService.saveBoview",
				data : [ bean ],
				async : false,
				success : function(result) {
					window.opener.reloadFileParentWindow();
					window.close();
				}
			});
		});
	});
</script>
</html>