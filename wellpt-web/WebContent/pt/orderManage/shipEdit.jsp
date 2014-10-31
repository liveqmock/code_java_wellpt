<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出货信息维护</title>
</head>
<body>
    <%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>出货信息维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="save_close" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
		<table>
			<tr>
				<td align="right">交货单号：</td>
				<td><input type="text" name="vbeln" id="vbeln" value="${vbeln }" readonly="true"></td>
			</tr>
			<tr>
				<td align="right">订单号：</td>
				<td><input type="text" name="vgbel" id="vgbel" value="${vgbel }" readonly="true"></td>
			</tr>
			<tr>
				<td align="right">装柜编号：</td>
				<td><input type="text" name="zzjbh" id="zzjbh" value="${zzjbh }" readonly="true"></td>
			</tr>
			<tr>
				<td align="right">客人确认出货：</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">信用证船期：</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">信用证到期日：</td>
				<td></td>
			</tr>
			<tr>
				<td align="right">运输方式：</td>
				<td><input type="text" id="zysfs" value="${zysfs }"></td>
			</tr>
			<tr>
				<td align="right">异常责任部门：</td>
				<td>
				<select id="zzrbm" name="zzrbm">
					<option value=" "> </option>
					<option value="LED-光源应用开发部">LED-光源应用开发部</option>
					<option value="LED-智能系统开发部">LED-智能系统开发部</option>
					<option value="LED-灯具应用开发部">LED-灯具应用开发部</option>
					<option value="CFL产品线/品技部">CFL产品线/品技部</option>
					<option value="客户">客户</option>
					<option value="销售部">销售部</option>
					<option value="包装课">包装课</option>
					<option value="船务课">船务课</option>
					<option value="LED产品线/制造一部">LED产品线/制造一部</option>
					<option value="LED产品线/制造三部">LED产品线/制造三部</option>
					<option value="LED产品线/制造四部">LED产品线/制造四部</option>
					<option value="LED产品线/制造五部">LED产品线/制造五部</option>
					<option value="LED产品线/注塑部">LED产品线/注塑部</option>
					<option value="LED产品线/电感部">LED产品线/电感部</option>
					<option value="LED产品线/包材厂">LED产品线/包材厂</option>
					<option value="CFL产品线/联恺">CFL产品线/联恺</option>
					<option value="CFL产品线/制造三部">CFL产品线/制造三部</option>
					<option value="LED PMC">LED PMC</option>
					<option value="CFL PMC">CFL PMC</option>
					<option value="采购部">采购部</option>
					<option value="仓储部">仓储部</option>
					<option value="品保部">品保部</option>
					<option value="供应商管理部">供应商管理部</option>
					<option value="技转部">技转部</option>
					<option value="系统">系统</option>
					<option value="项目管理部">项目管理部</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">出货异常大类：</td>
				<td>
				<select id="zchyc" name="zchyc">
					<option value=" "> </option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">出货异常备注：</td>
				<td><textarea id="zycbz" rows="4" cols="50">${zycbz }</textarea></td>
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