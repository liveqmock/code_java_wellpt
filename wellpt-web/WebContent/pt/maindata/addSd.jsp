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
<title>SD视图维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>SD视图维护</h2>
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
						<td class="title" colspan="2">SD视图维护</td>
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
						<td width="110px;">工厂</td>
						<td><select name="factory" id="factory">
								<option value=""></option>
								<c:forEach var="factory" items="${factorys}">
									<option value="${factory[0]}"
										<c:if test="${!empty objectView.factory && factory[0] eq objectView.factory}">selected</c:if>>${factory[1]}(${factory[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">销售组织</td>
						<td><input name="sdxszz" id="sdxszz"
							value="${objectView.sdxszz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">分销渠道</td>
						<td><input name="sdfxqd" id="sdfxqd"
							value="${objectView.sdfxqd}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">项目类别组</td>
						<td><input name="sdxmlbz" id="sdxmlbz"
							value="${objectView.sdxmlbz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">税分类</td>
						<td><input name="sdsfl" id="sdsfl"
							value="${objectView.sdsfl}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">科目设置组</td>
						<td><input name="sdkmszz" id="sdkmszz"
							value="${objectView.sdkmszz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">可用性检查</td>
						<td><input name="sdkyxjc" id="sdkyxjc"
							value="${objectView.sdkyxjc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">运输组</td>
						<td><input name="sdysz" id="sdysz"
							value="${objectView.sdysz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">装载组</td>
						<td><input name="sdzzz" id="sdzzz"
							value="${objectView.sdzzz}"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	function loadBean() {
		var wl = $("#wl").val();
		var factory = $("#factory").val();
		var sdxszz = $("#sdxszz").val();
		var sdfxqd = $("#sdfxqd").val();
		if ('' != wl.trim() && '' != factory.trim())
			JDS.call({
				service : "mainDataSdService.findObjectFromSap",
				data : [ wl.trim(), factory.trim(), sdxszz.trim(),
						sdfxqd.trim() ],
				async : false,
				success : function(result) {
					var bean = result.data;
					if ("E" == bean.type) {
						alert(bean.message);
					} else {
						$("#wlgcform").json2form(bean);
					}
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
				"factory" : null,
				"sdfxqd" : null,
				"sdkmszz" : null,
				"sdkyxjc" : null,
				"sdsfl" : null,
				"sdxmlbz" : null,
				"sdxszz" : null,
				"sdysz" : null,
				"sdzzz" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataSdService.saveSdview",
				data : [ bean ],
				async : false,
				success : function(result) {
					window.opener.reloadFileParentWindow();
					window.close();
				}
			});
		});
		$("#factory").change(function(e) {
			loadBean();
		});
		$("#wl").change(function(e) {
			loadBean();
		});
		$("#sdxszz").change(function(e) {
			loadBean();
		});
		$("#sdfxqd").change(function(e) {
			loadBean();
		});
	});
</script>
</html>