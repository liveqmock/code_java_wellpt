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
<title>用户资料修改</title>

</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>用户资料</h2>
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
					<td class="title" colspan="2">用户资料</td>
				</tr>
				<tr class="field">
					<td width="110px;">生管</td>
					<td><input name="zsg" type="text" value="${zsg}" id="zsg" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">OA用户</td>
					<td><input name="zoauser" type="text" value="${zoauser}" id="zoauser" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">课长</td>
					<td><input name="zkz" type="text" value="${zkz}" id="zkz" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">生产版本</td>
					<td><input name="verid" type="text" value="${verid}" id="verid" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">物料组</td>
					<td><input name="matkl" type="text" value="${matkl}" id="matkl" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">特性值</td>
					<td><input name="atwrt" type="text" value="${atwrt}" id="atwrt" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">工厂</td>
					<td><input name="dwerks" type="text" value="${dwerks}" id="dwerks" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
				</tr>
				<tr class="field">
					<td width="110px;">组长</td>
					<td><input name="zzz" type="text" value="${zzz}" id="zzz" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
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
			service : "productionLineManagerService.updateLineManager",
			data : [ $("#zsg").val(), $("#zoauser").val(), $("#zkz").val(),
						$("#verid").val(), $("#matkl").val(), $("#atwrt").val(),
						$("#dwerks").val(), $("#zzz").val() ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					window.opener.reloadFileParentWindow();
					window.close();
				}else{
					oAlert2(result.data.msg);
				}
			}
		});
	});
});
</script>
</html>