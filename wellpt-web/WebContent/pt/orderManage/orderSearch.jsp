<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>订单明细查询</title>
</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>订单明细查询</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr>
					<td>待评审-预警(${dspyj })</td>
					<td>待评审-delay(${dspd })</td>
					<td>待AE确认转正-预警(${dzzyj })</td>
					<td>待AE确认转正-delay(${dzzd })</td>
					<td>不及时签发(${bjsqf })</td>
				</tr>
				<tr>
					<td>及时签发(${jsqf }) </td>
					<td>不需评审(${bxps })</td>
					<td>待包装(${dbz })</td>
					<td>待包装-预警(${dbzyj })</td>
					<td>待包装-delay(${dbzd })</td>
				</tr>
				<tr>
					<td>待出货(${dch })</td>
					<td>待出货-预警(${dchyj })</td>
					<td>待出货-delay(${dchd })</td>
					<td>尾数待出货(${wsdch })</td>
					<td>待订舱(${ddc })</td>
				</tr>
				<tr>
					<td>待订舱-预警(${ddcyj })</td>
					<td>待订舱-delay(${ddcd })</td>
					<td>已完全出货(${ywqch })</td>
					<td>退货处理(${thcl })</td>
					<td>退货完成(${thwc })</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td>交货逾期(${jhyq })</td>
					<td>交货及时(${jhjs })</td>
					<td>验货合格(${yhhg })</td>
					<td>验货不合格(${yhbhg })</td>
				</tr>
			</table>
			
			<table>
				<tr>
					<td>待计划(${djh })</td>
					<td>已计划(${yjh })</td>
					<td>在制(${zz })</td>
					<td>已完成(${ywc })</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
$(document).ready(function() {
	
	$("#form_close").click(function(){
		window.close();
	});

});
</script>
</html>