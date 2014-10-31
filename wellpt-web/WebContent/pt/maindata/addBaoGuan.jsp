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
<title>报关中英文维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>报关中英文维护</h2>
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
						<td class="title" colspan="2">报关中英文维护</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料ID</td>
						<td><input name="wl" id="wl" value="${objectView.wl}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料描述</td>
						<td><input name="shortdesc" id="shortdesc"
							value="${objectView.shortdesc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">报关中文名</td>
						<td><input name="cnname" id="cnname"
							value="${objectView.cnname}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">报关英文名</td>
						<td><input name="enname" id="enname"
							value="${objectView.enname}"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	function loadBean(wl) {
		if ('' != wl.trim())
			JDS.call({
				service : "mainDataBaoGuanService.findObjectFromSap",
				data : [ wl ],
				async : false,
				success : function(result) {
					var bean = result.data;
					$("#wlgcform").json2form(bean);
				}
			});
	}
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
				"wl" : null,
				"shortdesc" : null,
				"cnname" : null,
				"enname" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataBaoGuanService.saveBaoGuanview",
				data : [ bean ],
				async : false,
				success : function(result) {
					window.opener.reloadFileParentWindow();
					window.close();
				}
			});
		});
		$("#wl").change(function(e) {
			var wl = $(this).val();
			loadBean(wl);
		});
	});
</script>
</html>