﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JS版组织结构图</title>

<script type="text/javascript" src="${ctx}/resources/pt/js/control2/organization.js"></script>

<style type="text/css">
.OrgBox{
	font-size:12px;
	padding:5px 5px 5px 5px;
	clear:left;
	float:left;
	text-align:center;
	position:absolute;
	background-image:url(${ctx}/resources/pt/js/control2/org.jpg);
	width:70px;
	height:106px;
}
.OrgBox img{
	width:60px;
	height:70px;
}
.OrgBox div{
	color:#FFA500;
	font-weight:800;
}
</style>
<script>
// var box = document.getElementById('OrgBox');
 //box.onclick
function CookieGroup(){
	alert(123)	
}


</script>
</head>
<body>
<script language="javascript">
var a=new OrgNode()
a.customParam.EmpName="张明"
a.customParam.department="总经办"
a.customParam.EmpPhoto="http://www.on-cn.cn/tempimg/org1.jpg"
var b=new OrgNode()
b.customParam.EmpName="陈黎明"
b.customParam.department="运营部"
b.customParam.EmpPhoto="http://www.on-cn.cn/tempimg/org2.jpg"
var c=new OrgNode()
c.customParam.EmpPhoto="http://www.on-cn.cn/tempimg/org1.jpg"
var d=new OrgNode()
d.customParam.EmpPhoto="http://www.on-cn.cn/tempimg/org1.jpg"
var e=new OrgNode()
e.customParam.EmpName="张三"
e.customParam.department="开发部"
var e1=new OrgNode()
e1.customParam.EmpName="李四"
e1.customParam.department="开发部1"
var f=new OrgNode()
var g=new OrgNode()
var h=new OrgNode()
var k1=new OrgNode()
var x=new OrgNode()
var y=new OrgNode()
var z=new OrgNode()
var z1=new OrgNode()
var z11=new OrgNode()
var z12=new OrgNode()
var z13=new OrgNode()
z1.Nodes.Add(z11);
z1.Nodes.Add(z12);
z1.Nodes.Add(z13);
var z2=new OrgNode()
z2.Text="三-2"
z2.Description="zxxxcz"
var y1=new OrgNode()
y1.Text="三员工一-1"
y1.Description="zxxxcz"
var y2=new OrgNode()
y2.Text="三员工一-2"
y2.Description="zxxxcz"
var y21=new OrgNode()
y21.Text="三员工一-2-1"
y21.Description="zxxxcz"
var y22=new OrgNode()
y22.Text="三员工一-2-2"
y22.Description="zxxxcz"
y2.Nodes.Add(y21);
y2.Nodes.Add(y22);
y.Nodes.Add(y1);
y.Nodes.Add(y2);
z.Nodes.Add(z1);
z.Nodes.Add(z2);
x.Nodes.Add(y);
x.Nodes.Add(z);
g.Nodes.Add(h);
g.Nodes.Add(k1);
a.Nodes.Add(b);
a.Nodes.Add(x);
a.Nodes.Add(c);
b.Nodes.Add(g);
c.Nodes.Add(d);
c.Nodes.Add(e);
c.Nodes.Add(e1);
c.Nodes.Add(f);
var OrgShows=new OrgShow(a);
OrgShows.Top=50;
OrgShows.Left=50;
OrgShows.IntervalWidth=10;
OrgShows.IntervalHeight=20;
//OrgShows.ShowType=2;
//OrgShows.BoxHeight=100;
OrgShows.BoxTemplet="<div id=\"{Id}\" class=\"OrgBox\" onclick=\"CookieGroup()\"><img src=\"{EmpPhoto}\" /><span>{EmpName}</span><div>{department}</div></div>"
OrgShows.Run()
</script>
</body>
</html>