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
<title>FICO视图维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>FICO视图维护</h2>
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
						<td class="title" colspan="2">FICO视图维护</td>
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
						<td width="110px;">采购类型</td>
						<td><select name="ppmrp2cglx" id="ppmrp2cglx">
								<option value=""></option>
								<option value="F"
									<c:if test="${!empty objectView.ppmrp2cglx && 'F' eq objectView.ppmrp2cglx}">selected</c:if>>F-外购</option>
								<option value="E"
									<c:if test="${!empty objectView.ppmrp2cglx && 'E' eq objectView.ppmrp2cglx}">selected</c:if>>E-自制</option>
								<option value="X"
									<c:if test="${!empty objectView.ppmrp2cglx && 'X' eq objectView.ppmrp2cglx}">selected</c:if>>X-两种采购类型</option>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">计划价格1</td>
						<td><input name="pricejhjg" id="pricejhjg"
							value="${objectView.pricejhjg}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">计划价格1日期</td>
						<td><input name="pricejhjgrq" id="pricejhjgrq"
							value="${objectView.pricejhjgrq}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">价格控制</td>
						<td><input name="ficokj1jgkz" id="ficokj1jgkz"
							value="${objectView.ficokj1jgkz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">评估类</td>
						<td><input name="ficokj1pgl" id="ficokj1pgl"
							value="${objectView.ficokj1pgl}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">价格确定</td>
						<td><input name="ficokj1jgqd" id="ficokj1jgqd"
							value="${objectView.ficokj1jgqd}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">周期单位价格</td>
						<td><input name="ficokj1zqdwjg" id="ficokj1zqdwjg"
							value="${objectView.ficokj1zqdwjg}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">价格单位</td>
						<td><input name="ficokj1jgdw" id="ficokj1jgdw"
							value="${objectView.ficokj1jgdw}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">标准价格</td>
						<td><input name="ficokj1bzjg" id="ficokj1bzjg"
							value="${objectView.ficokj1bzjg}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">成本核算批量</td>
						<td><input name="ficocb1cbhspl" id="ficocb1cbhspl"
							value="${objectView.ficocb1cbhspl}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">QS的成本核算</td>
						<td><input name="ficocb1qs" id="ficocb1qs"
							value="${objectView.ficocb1qs}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">物料来源</td>
						<td><input name="ficocb1wlly" id="ficocb1wlly"
							value="${objectView.ficocb1wlly}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">无成本估算</td>
						<td><input name="ficocb1wcbgs" id="ficocb1wcbgs"
							value="${objectView.ficocb1wcbgs}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">原始组</td>
						<td><input name="ficocb1ysz" id="ficocb1ysz"
							value="${objectView.ficocb1ysz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">利润中心</td>
						<td><input name="ficocb1lrzx" id="ficocb1lrzx"
							value="${objectView.ficocb1lrzx}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">科目设置组</td>
						<td><input name="sdkmszz" id="sdkmszz"
							value="${objectView.sdkmszz}"></td>
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
				service : "mainDataFicoService.findObjectFromSap",
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
				"ppmrp2cglx" : null,
				"ficocb1cbhspl" : null,
				"ficocb1lrzx" : null,
				"ficocb1qs" : null,
				"ficocb1wcbgs" : null,
				"ficocb1wlly" : null,
				"ficocb1ysz" : null,
				"ficokj1bzjg" : null,
				"ficokj1jgdw" : null,
				"ficokj1jgkz" : null,
				"ficokj1jgqd" : null,
				"ficokj1pgl" : null,
				"ficokj1zqdwjg" : null,
				"pricejhjg" : null,
				"pricejhjgrq" : null,
				"sdkmszz" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataFicoService.saveFicoview",
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