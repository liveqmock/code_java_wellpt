<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生产状态</title>
</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>生产状态</h2>
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
		<td align="right">待计划：</td>
		<td id="djh">${djh }</td>
		</tr>
		<tr>
		<td align="right">已计划：</td>
		<td id="yjh">${yjh }</td>
		</tr>
		<tr>
		<td align="right">在制：</td>
		<td id="zzhi">${zzhi }</td>
		</tr>
		<tr>
		<td align="right">已完成：</td>
		<td id="ywc">${ywc }</td>
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