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
<title>PP视图维护</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>PP视图维护</h2>
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
						<td class="title" colspan="2">PP视图维护</td>
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
						<td width="110px;">生产仓储地点</td>
						<td><select name="scstore" id="scstore">
								<option value=""></option>
								<c:forEach var="story" items="${stores}">
									<option value="${story[0]}"
										<c:if test="${!empty objectView.scstore && story[0] eq objectView.scstore}">selected</c:if>>${story[1]}(${story[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">外部采购仓储地点</td>
						<td><select name="wbstore" id="wbstore">
								<option value=""></option>
								<c:forEach var="story" items="${stores}">
									<option value="${story[0]}"
										<c:if test="${!empty objectView.wbstore && story[0] eq objectView.wbstore}">selected</c:if>>${story[1]}(${story[0]})</option>
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
						<td width="110px;">舍入值</td>
						<td><input name="ppmrp1srz" id="ppmrp1srz"
							value="${objectView.ppmrp1srz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">MRP类型</td>
						<td><input name="ppmrp1type" id="ppmrp1type"
							value="${objectView.ppmrp1type}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">MRP控制者</td>
						<td><input name="ppmrp1control" id="ppmrp1control"
							value="${objectView.ppmrp1control}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">批量大小</td>
						<td><input name="ppmrp1pldx" id="ppmrp1pldx"
							value="${objectView.ppmrp1pldx}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">MRP组</td>
						<td><input name="ppmrp1group" id="ppmrp1group"
							value="${objectView.ppmrp1group}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">特殊采购类</td>
						<td><input name="ppmrp2tscgl" id="ppmrp2tscgl"
							value="${objectView.ppmrp2tscgl}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">反冲</td>
						<td><input name="ppmrp2fc" id="ppmrp2fc"
							value="${objectView.ppmrp2fc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">自制生产时间</td>
						<td><input name="ppmrp2zzscsj" id="ppmrp2zzscsj"
							value="${objectView.ppmrp2zzscsj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">计划交货时间</td>
						<td><input name="ppmrp2jhjhsj" id="ppmrp2jhjhsj"
							value="${objectView.ppmrp2jhjhsj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">收货处理时间</td>
						<td><input name="ppmrp2shclsj" id="ppmrp2shclsj"
							value="${objectView.ppmrp2shclsj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">计划边际码</td>
						<td><input name="ppmrp2bjm" id="ppmrp2bjm"
							value="${objectView.ppmrp2bjm}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">安全库存</td>
						<td><input name="ppmrp2aqkc" id="ppmrp2aqkc"
							value="${objectView.ppmrp2aqkc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">策略组</td>
						<td><input name="ppmrp3clz" id="ppmrp3clz"
							value="${objectView.ppmrp3clz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">消耗模式</td>
						<td><input name="ppmrp3xhms" id="ppmrp3xhms"
							value="${objectView.ppmrp3xhms}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">向前消耗期间</td>
						<td><input name="ppmrp3xqxhqj" id="ppmrp3xqxhqj"
							value="${objectView.ppmrp3xqxhqj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">逆向消耗期间</td>
						<td><input name="ppmrp3nxxhqj" id="ppmrp3nxxhqj"
							value="${objectView.ppmrp3nxxhqj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">可用性检查</td>
						<td><input name="ppmrp3kyxjc" id="ppmrp3kyxjc"
							value="${objectView.ppmrp3kyxjc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">部件废品%</td>
						<td><input name="ppmrp4bjfp" id="ppmrp4bjfp"
							value="${objectView.ppmrp4bjfp}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">独立/集中</td>
						<td><input name="ppmrp4dljz" id="ppmrp4dljz"
							value="${objectView.ppmrp4dljz}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">生产计划参数文件</td>
						<td><input name="ppgzjhcswj" id="ppgzjhcswj"
							value="${objectView.ppgzjhcswj}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">生产管理员</td>
						<td><input name="ppgzjhscgly" id="ppgzjhscgly"
							value="${objectView.ppgzjhscgly}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">不足交货允差</td>
						<td><input name="ppgzjhbzyc" id="ppgzjhbzyc"
							value="${objectView.ppgzjhbzyc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">过度交货允差</td>
						<td><input name="ppgzjhgdyc" id="ppgzjhgdyc"
							value="${objectView.ppgzjhgdyc}"></td>
					</tr>
					<tr class="field">
						<td width="110px;">最大批量大小</td>
						<td><input name="ppbstma" id="ppbstma"
							value="${objectView.ppbstma}"></td>
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
				service : "mainDataPpService.findObjectFromSap",
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
						"ppmrp2cglx" : null,
						"scstore" : null,
						"wbstore" : null,
						"ppmrp1srz" : null,
						"ppbstma" : null,
						"ppgzjhbzyc" : null,
						"ppgzjhcswj" : null,
						"ppgzjhgdyc" : null,
						"ppgzjhscgly" : null,
						"ppmrp1control" : null,
						"ppmrp1group" : null,
						"ppmrp1pldx" : null,
						"ppmrp1type" : null,
						"ppmrp2aqkc" : null,
						"ppmrp2bjm" : null,
						"ppmrp2fc" : null,
						"ppmrp2jhjhsj" : null,
						"ppmrp2shclsj" : null,
						"ppmrp2tscgl" : null,
						"ppmrp2zzscsj" : null,
						"ppmrp3clz" : null,
						"ppmrp3kyxjc" : null,
						"ppmrp3nxxhqj" : null,
						"ppmrp3xhms" : null,
						"ppmrp3xqxhqj" : null,
						"ppmrp4bjfp" : null,
						"ppmrp4dljz" : null
					};
					$("#wlgcform").form2json(bean);
					JDS.call({
						service : "mainDataPpService.savePpview",
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
							var wl = $("#wl").val();
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