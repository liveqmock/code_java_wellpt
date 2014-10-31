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
<title>商品编辑</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>商品编辑</h2>
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
						<td class="title" colspan="2">商品编辑</td>
					</tr>
					<tr class="field">
						<td width="110px;">产品编码</td>
						<td><input name="matnr1" id="matnr1" value="${entity.matnr}"
							disabled="disabled"> <input name="matnr" id="matnr"
							value="${entity.matnr}" hidden="true">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">短描述</td>
						<td><input name="stext" id="stext" value="${entity.stext}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">长描述</td>
						<td><input name="maktx" id="maktx" value="${entity.maktx}"
							disabled="disabled">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">商品名称</td>
						<td><input name="name" id="name" value="${entity.name}"
							class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">商品描述</td>
						<td><input name="remark" id="remark" value="${entity.remark}"
							class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">包装单位</td>
						<td><input name="unit" id="unit" value="${entity.unit}"
							class="required">
						</td>
					</tr>
					<tr class="field">
						<td width="110px;">包装单位数量</td>
						<td><input name="unitcount" id="unitcount" value="${entity.unitcount}"
							class="required digits">
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
				"matnr" : null,
				"name" : null,
				"remark" : null,
				"unit" : null,
				"unitcount" : null
			};
			if ($("#wlgcform").validate(Theme.validationRules).form()) {
				$("#wlgcform").form2json(bean);
				JDS.call({
					service : "dmsProductService.saveProduct",
					data : [ bean ],
					async : false,
					success : function(result) {
						window.opener.reloadFileParentWindow();
						window.close();
					}
				});
			}
		});
	});
</script>
</html>