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
<title>可用库存修改</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>可用库存</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr>
					<td class="title" colspan="2">可用库存</td>
				</tr>
				<tr class="field">
					<td width="110px;">工厂</td>
					<td><input name="dwerks" type="text" value="${dwerks}" id="dwerks" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">库存地址</td>
					<td><input name="lgort" type="text" value="${lgort}" id="lgort" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">删除标识符</td>
					<td><input name="lvorm" type="text" value="${lvorm}" id="lvorm" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
			</table>
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
		JDS.call({
			service : "productionStockService.updateStocks",
			data : [ $("#dwerks").val(),$("#lgort").val(),$("#lvorm").val()],
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