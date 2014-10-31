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
<title>QM视图维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>QM视图维护</h2>
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
						<td class="title" colspan="2">QM视图维护</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料ID</td>
						<td><input name="wl" id="wl" value="${objectView.wl}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料描述</td>
						<td><input name="shortdesc" id="shortdesc"
							value="${objectView.shortdesc}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">工厂</td>
						<td><select name="factory" id="factory">
								<option value=""></option>
								<c:forEach var="factory" items="${factorys}">
									<option value="${factory[0]}"
										<c:if test="${!empty objectView.factory && factory[0] eq objectView.factory}">selected</c:if>>${factory[1]}(${factory[0]})</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">批次管理</td>
						<td><select name="qmpcgl" id="qmpcgl"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmpcgl && 'X' eq objectView.qmpcgl}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:01</td>
						<td><select name="qmjylx01" id="qmjylx01"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylx01 && 'X' eq objectView.qmjylx01}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:03</td>
						<td><select name="qmjylx03" id="qmjylx03"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylx03 && 'X' eq objectView.qmjylx03}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:10</td>
						<td><select name="qmjylx10" id="qmjylx10"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylx10 && 'X' eq objectView.qmjylx10}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:89</td>
						<td><select name="qmjylx89" id="qmjylx89"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylx89 && 'X' eq objectView.qmjylx89}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX01</td>
						<td><select name="qmjylxldx01" id="qmjylxldx01"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx01 && 'X' eq objectView.qmjylxldx01}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX02</td>
						<td><select name="qmjylxldx02" id="qmjylxldx02"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx02 && 'X' eq objectView.qmjylxldx02}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX03</td>
						<td><select name="qmjylxldx03" id="qmjylxldx03"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx03 && 'X' eq objectView.qmjylxldx03}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX04</td>
						<td><select name="qmjylxldx04" id="qmjylxldx04"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx04 && 'X' eq objectView.qmjylxldx04}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX05</td>
						<td><select name="qmjylxldx05" id="qmjylxldx05"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx05 && 'X' eq objectView.qmjylxldx05}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">检验类型:LDX06</td>
						<td><select name="qmjylxldx06" id="qmjylxldx06"><option
									value=""></option>
								<option value="X"
									<c:if test="${!empty objectView.qmjylxldx06 && 'X' eq objectView.qmjylxldx06}">selected</c:if>>X</option>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">平均检验期</td>
						<td><input name="qmpjjyq" id="qmpjjyq"
							value="${objectView.qmpjjyq}">
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
	function loadBean(wl, factory) {
		if ('' != wl.trim() && '' != factory.trim())
			JDS.call({
				service : "mainDataQmService.findObjectFromSap",
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
				"qmjylx01" : null,
				"qmjylx03" : null,
				"qmjylx10" : null,
				"qmjylx89" : null,
				"qmjylxldx01" : null,
				"qmjylxldx02" : null,
				"qmjylxldx03" : null,
				"qmjylxldx04" : null,
				"qmjylxldx05" : null,
				"qmjylxldx06" : null,
				"qmpcgl" : null,
				"qmpjjyq" : null
			};
			$("#wlgcform").form2json(bean);
			JDS.call({
				service : "mainDataQmService.saveQmview",
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