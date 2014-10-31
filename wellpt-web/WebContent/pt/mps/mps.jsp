<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>整灯业务查询报表</title>
<style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	width:100%;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
table.head td{
padding: 12px;
}
</style>
<script type="text/javascript" >
</script>
</head>
<body>
<div id="head">
<h2>查询条件</h2>
<form action="../../mps/mps"  method="post" />
<table class="head">
<tr>
<td>销售订单号<input type="text"  name="xs" /></td>
<td>计划订单号<input type="text" name="jh" /></td>
<td>生产订单号<input type="text" name="sc" /></td>
<td>SAP产品ID<input type="text"  name="sd" /></td>
</tr>
<tr>
<td colspan="3"><input type="submit"  /></td>
</tr>
</table>
</form>
</div>
</body>
</html>