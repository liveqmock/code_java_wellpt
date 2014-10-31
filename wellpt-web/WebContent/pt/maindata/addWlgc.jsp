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
<title>分配工厂</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>分配工厂</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="uuid" id="uuid" value="${wlgc.uuid}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">分配工厂</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料ID</td>
						<td><input name="wl" id="wl" value="${wlgc.wl}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">物料描述</td>
						<td><input name="shortdesc" id="shortdesc" value="${wlgc.shortdesc}">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">工厂</td>
						<td><select name="factory" id="factory">
								<option value=""></option>
								<c:forEach var="factory" items="${factorys}">
									<option value="${factory[0]}"
										<c:if test="${!empty wlgc.factory && factory[0] eq wlgc.factory}">selected</c:if>>${factory[1]}(${factory[0]})</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">生产仓储地点</td>
						<td><select name="scstore" id="scstore">
								<option value=""></option>
								<c:forEach var="story" items="${stores}">
									<option value="${story[0]}"
										<c:if test="${!empty wlgc.scstore && story[0] eq wlgc.scstore}">selected</c:if>>${story[1]}(${story[0]})</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">外部采购仓储地点</td>
						<td><select name="wbstore" id="wbstore">
								<option value=""></option>
								<c:forEach var="story" items="${stores}">
									<option value="${story[0]}"
										<c:if test="${!empty wlgc.wbstore && story[0] eq wlgc.wbstore}">selected</c:if>>${story[1]}(${story[0]})</option>
								</c:forEach>
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
				$("#form_save").click(function() {
					var bean = {
						"uuid" : null,
						"wl" : null,
						"shortdesc" : null,
						"factory" : null,
						"scstore" : null,
						"wbstore" : null
					};
					$("#wlgcform").form2json(bean);
					JDS.call({
						service : "mainDataWlGcService.saveWlgc",
						data : [ bean ],
						async : false,
						success : function(result) {
							window.opener.reloadFileParentWindow();
							window.close();
						}
					});
				});
				$("#factory").change(
						function(e) {
							var selScStore = $("#scstore");
							var selWbStore = $("#wbstore");
							selScStore.empty();
							selWbStore.empty();
							var val = $(this).val();
							var bean = null;
							JDS.call({
								service : "mainDataWlGcService.getStores",
								data : [ val ],
								async : false,
								success : function(result) {
									bean = result.data.stores;
								}
							});
							for ( var i = 0; i < bean.length; i++) {
								var object = bean[i];
								var value = object[0];
								var text = object[1];
								selScStore.append("<option value='"+value+"'>"
										+ text + "</option>");
								selWbStore.append("<option value='"+value+"'>"
										+ text + "</option>");
							}
						});
			});
</script>
</html>