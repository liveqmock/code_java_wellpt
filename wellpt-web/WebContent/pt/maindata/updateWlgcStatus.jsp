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
<title>物料工厂状态修改</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>物料工厂状态修改</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">物料工厂状态修改</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料ID</td>
						<td><input name="wl" id="wl" value=""></td>
					</tr>
					<tr class="field">
						<td width="110px;">工厂</td>
						<td><select name="factory" id="factory">
								<c:forEach var="factory" items="${factorys}">
									<option value="${factory[0]}">
										<c:if test="${!empty factory[0] && !(' ' eq factory[0])}">${factory[1]}(${factory[0]})</c:if>
									</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">状态值</td>
						<td><select name="mmsta" id="mmsta">
								<option value=""></option>
								<option value="00">00:冻结状态</option>
								<option value="01">01:新导入</option>
								<option value="03">03:认证过程</option>
								<option value="05">05:待试产</option>
								<option value="09">09:认证合格</option>
								<option value="10">10:允许销售订单和物料需求</option>
								<option value="99">99:不需成本估算</option>
						</select>
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
	$(document).ready(
			function() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
				$("#form_close").click(function() {
					window.close();
				});
				$("#form_save").click(
						function() {
							JDS.call({
								service : "mainDataPpService.saveWlgcStatus",
								data : [ $("#wl").val(), $("#factory").val(),
										$("#mmsta").val() ],
								async : false,
								success : function(result) {
									var bean = result.data;
									if ("E" == bean.type) {
										alert(bean.message);
									} else {
										window.opener.reloadFileParentWindow();
										window.close();
									}
								}
							});
						});
			});
</script>
</html>