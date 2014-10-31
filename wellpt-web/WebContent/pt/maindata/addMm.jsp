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
<title>MM视图维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>MM视图维护</h2>
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
						<td class="title" colspan="2">MM视图维护</td>
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
						<td width="110px;">采购组</td>
						<td><input name="mmcgz" id="mmcgz"
							value="${objectView.mmcgz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">货源清单</td>
						<td><input name="mmhyqd" id="mmhyqd"
							value="${objectView.mmhyqd}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">配额管理</td>
						<td><input name="mmpegl" id="mmpegl"
							value="${objectView.mmpegl}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">采购订单文本</td>
						<td><input name="mmddwb" id="mmddwb"
							value="${objectView.mmddwb}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">计划交货时间</td>
						<td><input name="ppmrp2jhjhsj" id="ppmrp2jhjhsj"
							value="${objectView.ppmrp2jhjhsj}"></td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	function loadBean(wl, factory) {
		if ('' != wl.trim() && '' != factory.trim())
			JDS.call({
				service : "mainDataMmService.findObjectFromSap",
				data : [ wl.trim(), factory.trim() ],
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
				"mmcgz" : null,
				"mmddwb" : null,
				"mmhyqd" : null,
				"mmpegl" : null,
				"ppmrp2jhjhsj" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataMmService.saveMmview",
				data : [ bean ],
				async : false,
				success : function(result) {
					window.opener.reloadFileParentWindow();
					window.close();
				}
			});
		});
		$("#factory").change(function(e) {
			var val = $(this).val();
			var wl = $("#wl").val();
			loadBean(wl, val);
		});
		$("#wl").change(function(e) {
			var wl = $(this).val();
			var factory = $("#factory").val();
			loadBean(wl, factory);
		});
	});
</script>
</html>