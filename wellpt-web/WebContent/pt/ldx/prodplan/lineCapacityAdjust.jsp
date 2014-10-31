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
<title>产能调整</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>产能调整</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">确定</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr>
					<td class="title" colspan="2">产能调整</td>
				</tr>
				<tr class="field">
					<td width="110px;">生管</td>
					<td><input type="text" name="zsg" id="zsg"/></td>
				</tr>
				<tr class="field">
					<td width="110px;">日期</td>
					<td><input type="text" name="zrq" id="zrq" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" class="Wdate" /></td>
				</tr>
				<tr class="field">
					<td width="110px;">系数</td>
					<td><input type="text" name="zxs" id="zxs" value="1.2"/></td>
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
			service : "productionLineCapacityService.adjustLineCapacity",
			data : [  $("#zsg").val(), $("#zrq").val(), $("#zxs").val() ],
			async : false,
			success : function(result) {
				if(result.data.res=="success"){
					window.opener.reloadFileParentWindow();
					window.close();
				}else{
					oAlert2(result.data.err);
				}
			}
		});
	});
});
</script>
</html>