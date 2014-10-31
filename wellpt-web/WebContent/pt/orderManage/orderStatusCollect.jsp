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
<title>订单行项目状态汇总</title>
</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>订单行项目状态汇总</h2>
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
		<td algin="right">订单号：</td>
		<td id="vbeln">${vbeln }</td>
		</tr>
		<tr>
		<td algin="right">订单类型：</td>
		<td id="bezei">${bezei }</td>
		</tr>
		<tr>
		<td algin="right">发货状态：</td>
		<td id="lfgsk">${lfgsk }</td>
		</tr>
		<tr>
		<td algin="right">销售部门：</td>
		<td id=VKBUR>${VKBUR }</td>
		</tr>
		<tr>
		<td algin="right">合同号：</td>
		<td id="bstkd">${bstkd }</td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td algin="right">
		<select name="fhdcx" id="fhdcx" style="width: 200px">
		<option value=" ">查询发货单</option>
		<c:forEach var="vbelns" items="${vbelnList}">
		<option value="${vbelns}">${vbelns}</option>
		</c:forEach>
		</select>
		</td>
		</tr>
		<tr>
		<td algin="right">AE：</td>
		<td id="AE">${AE }</td>
		</tr>
		<tr>
		<td algin="right">客户：</td>
		<td id="sortlTemp">${sortlTemp }</td>
		</tr>
		<tr>
		<td algin="right">币种：</td>
		<td id="waerk">${waerk }</td>
		</tr>
		<tr>
		<td algin="right">OM：</td>
		<td id="OM">${OM }</td>
		</tr>
		<tr>
		<td algin="right">新单/翻单：</td>
		<td id="abrvw">${abrvw }</td>
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