<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<title>数据显示页</title>
<style>
td     { font-family: 宋体; font-size: 9pt;height:1%;width:auto;align:left;}
td.p10{
height:1%;width:auto;font-family: 宋体; font-size: 9pt;
}
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
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
	padding: 10px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
	line-height:10px;
	
}
</style>
</head>
<body>
<a href="../pt/mps/mps.jsp" >返回</a>
<center>
<h1>查询到的数据显示如下</h1>
<%String[][] s1=(String[][])request.getAttribute("value1"); %>
<table  class="gridtable">
<%for(int i=0;i<s1.length;i++){%>
<tr>
<% 
if(i==0){
%>
<td colspan="57"  ><div ><%="海德信分厂整灯MPS  Sept (02版）"%></div></td>	
<% 
}
else{
for(int j=0;j<s1[i].length;j++){
%>
<td class="p10">
<div style=""><%=s1[i][j] %></div>
</td>
<%} }%>
</tr>
<%} %>
</table>
</center>
</body>
</html>