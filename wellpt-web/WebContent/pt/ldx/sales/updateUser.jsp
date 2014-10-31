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
<title>用户编辑</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>用户编辑</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="id" id="id" value="${entity.id}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title" colspan="2">用户编辑</td>
					</tr>
					<c:if test="${!isEdit}">
						<tr class="field">
							<td width="110px;">账户名</td>
							<td><input name="regname" id="regname"
								value="${entity.regname}" class="required"></td>
						</tr>
						<tr class="field">
							<td width="110px;">密码</td>
							<td><input name="pwd" id="pwd" value="${entity.pwd}"
								class="required"></td>
						</tr>
					</c:if>
					<tr class="field">
						<td width="110px;">客户编号</td>
						<td><input name="customernum" id="customernum"
							value="${entity.customernum}" class="required"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">公司名称</td>
						<td><input name="companyname" id="companyname"
							value="${entity.companyname}" class="required"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">公司地址</td>
						<td><input name="companyaddr" id="companyaddr"
							value="${entity.companyaddr}" class="required"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">传真</td>
						<td><input name="fax" id="fax" value="${entity.fax}"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">联系人姓名</td>
						<td><input name="realname" id="realname"
							value="${entity.realname}" class="required"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">固定电话</td>
						<td><input name="tel" id="tel" value="${entity.tel}"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">手机</td>
						<td><input name="mobile" id="mobile" value="${entity.mobile}"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">联系人邮箱</td>
						<td><input name="email" id="email" value="${entity.email}"
							<c:if test="${isEdit}">disabled="disabled"</c:if>></td>
					</tr>
					<tr class="field">
						<td width="110px;">付款条件</td>
						<td><select name="zterm" id="zterm">
								<c:forEach var="object" items="${zterms}">
									<option value="${object[0]}"
										<c:if test="${!empty entity.zterm && object[0] eq entity.zterm}">selected</c:if>>${object[1]}(${object[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">客户组</td>
						<td><select name="kdgrp" id="kdgrp">
								<c:forEach var="object" items="${kdgrps}">
									<option value="${object[0]}"
										<c:if test="${!empty entity.kdgrp && object[0] eq entity.kdgrp}">selected</c:if>>${object[1]}(${object[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">价格组</td>
						<td><select name="konda" id="konda">
								<c:forEach var="object" items="${kondas}">
									<option value="${object[0]}"
										<c:if test="${!empty entity.konda && object[0] eq entity.konda}">selected</c:if>>${object[1]}(${object[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr class="field">
						<td width="110px;">销售地区</td>
						<td><select name="bzirk" id="bzirk">
								<c:forEach var="object" items="${bzirks}">
									<option value="${object[0]}"
										<c:if test="${!empty entity.bzirk && object[0] eq entity.bzirk}">selected</c:if>>${object[1]}(${object[0]})</option>
								</c:forEach>
						</select></td>
					</tr>
				</tbody>
			</table>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	function openUser(lname, lid) {
		$.unit.open({
			title : 'test',
			labelField : lname,
			valueField : lid,
			selectType : 4
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
				"id" : null,
				"regname" : null,
				"pwd" : null,
				"customernum" : null,
				"companyname" : null,
				"companyaddr" : null,
				"fax" : null,
				"realname" : null,
				"tel" : null,
				"mobile" : null,
				"email" : null,
				"zterm" : null,
				"kdgrp" : null,
				"konda" : null,
				"bzirk" : null
			};
			if ($("#wlgcform").validate(Theme.validationRules).form()) {
				$("#wlgcform").form2json(bean);
				JDS.call({
					service : "dmsUserService.saveUser",
					data : [ bean ],
					async : false,
					success : function(result) {
						var b = result.data;
						if ("E" == b.type) {
							alert(bean.message);
						} else {
							window.opener.reloadFileParentWindow();
							window.close();
						}
					}
				});
			}
		});
	});
</script>
</html>