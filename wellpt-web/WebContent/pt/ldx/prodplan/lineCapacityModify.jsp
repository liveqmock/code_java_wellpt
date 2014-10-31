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
<title>标准产能修改</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>标准产能</h2>
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
				<td class="title" colspan="2">标准产能</td>
			</tr>
			<tr class="field">
				<td width="110px;">工厂</td>
				<td><input name="dwerks" type="text" value="${dwerks}" id="dwerks"  readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">部门</td>
				<td><input name="department" type="text" value="${department}" id="department" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">课别</td>
				<td><input name="clazz" type="text" value="${clazz}" id="clazz" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">线号</td>
				<td><input name="zxh" type="text" value="${zxh}" id="zxh" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">生管</td>
				<td><input name="zsg" type="text" value="${zsg}" id="zsg" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">日期</td>
				<td><input name="zrq" type="text" value="${zrq}" id="zrq" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">工序别</td>
				<td><input name="ltxa1" type="text" value="${ltxa1}" id="ltxa1" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">标准产能A</td>
				<td><input name="gamng1" type="text" value="${gamng1}" id="gamng1" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">标准产能B</td>
				<td><input name="gamng2" type="text" value="${gamng2}" id="gamng2" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">标准产能C</td>
				<td><input name="gamng3" type="text" value="${gamng3}" id="gamng3" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">线别报表显示</td>
				<td><input name="zxbbbxs" type="text" value="${zxbbbxs}" id="zxbbbxs" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
			</tr>
			<tr class="field">
				<td width="110px;">产线ID</td>
				<td><input type="text" name="zcxdm" id="zcxdm" value="${zcxdm}" /></td>
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
			service : "productionLineCapacityService.updateLineCapacity",
			data : [ $("#dwerks").val(), $("#department").val(), $("#clazz").val(),
						$("#zxh").val(), $("#zsg").val(), $("#zrq").val(),
						$("#ltxa1").val(), $("#gamng1").val(), $("#gamng2").val(),
						$("#gamng3").val(), $("#zxbbbxs").val(),$("#zcxdm").val()],
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