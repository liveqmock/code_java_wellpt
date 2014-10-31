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
<title>计划价格维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>计划价格维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="uuid" id="uuid" value="${planView.uuid}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">计划价格维护</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料ID</td>
						<td><input name="wl" id="wl" value="${planView.wl}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料描述</td>
						<td><input name="shortdesc" id="shortdesc"
							value="${planView.shortdesc}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">工厂</td>
						<td><select name="factory" id="factory">
								<option value=""></option>
								<c:forEach var="factory" items="${factorys}">
									<option value="${factory[0]}"
										<c:if test="${!empty planView.factory && factory[0] eq planView.factory}">selected</c:if>>${factory[1]}(${factory[0]})</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">采购类型</td>
						<td><select name="ppmrp2cglx" id="ppmrp2cglx">
								<option value=""></option>
								<option value="F"
									<c:if test="${!empty planView.ppmrp2cglx && 'F' eq planView.ppmrp2cglx}">selected</c:if>>F-外购</option>
								<option value="E"
									<c:if test="${!empty planView.ppmrp2cglx && 'E' eq planView.ppmrp2cglx}">selected</c:if>>E-自制</option>
								<option value="X"
									<c:if test="${!empty planView.ppmrp2cglx && 'X' eq planView.ppmrp2cglx}">selected</c:if>>X-两种采购类型</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">计划价格1日期</td>
						<td><input name="pricejhjgrq" id="pricejhjgrq"
							value="${planView.pricejhjgrq}" class="Wdate" onclick="WdatePicker({dateFmt:'yyyyMMdd'});">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">计划价格1</td>
						<td><input name="pricejhjg" id="pricejhjg"
							value="${planView.pricejhjg}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">价格单位</td>
						<td><input name="ficokj1jgdw" id="ficokj1jgdw"
							value="${planView.ficokj1jgdw}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">舍入值</td>
						<td><input name="ppmrp1srz" id="ppmrp1srz"
							value="${planView.ppmrp1srz}">
						</td>
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
				"wl" : null,
				"shortdesc" : null,
				"factory" : null,
				"ppmrp2cglx" : null,
				"scstore" : null,
				"wbstore" : null,
				"pricejhjgrq" : null,
				"pricejhjg" : null,
				"ficokj1jgdw" : null,
				"ppmrp1srz" : null

			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataPlanPriceService.savePlanView",
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