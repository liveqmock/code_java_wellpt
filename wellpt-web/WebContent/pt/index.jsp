<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>公共主页</title>
<STYLE type="text/css">

.message_module {
    margin: 100px auto 0;
    width: 600px;
    font-family: "Microsoft YaHei";
}
.message_module_title {
    font-size: 25px;
    text-align: center;
}
.message_module_user{
	float: right;
    margin-right: 100px;
    margin-top: 50px;
    text-align: center;
}
.message_module_operate {
    float: left;
    margin-left: 100px;
    margin-top: 50px;
    text-align: center;
}
.message_module_operate a {
    background: url("${ctx}/resources/pt/images/login/login_button_null.png") repeat scroll 0 0 transparent;
    color: #FFFFFF;
    cursor: pointer;
    display: block;
    font-size: 20px;
    height: 40px;
    margin: 15px auto;
    padding-top: 8px;
    text-decoration: none;
    width: 148px;
}
.message_module_operate a:hover {
	background: url("${ctx}/resources/pt/images/login/login_button2_null.png") repeat scroll 0 0 transparent;
}
.message_module_user_title{
 	font-size: 20px;
}
.message_module_user_item a{
 	background: url("${ctx}/resources/pt/images/login/login_button_null.png") repeat scroll 0 0 transparent;
    color: #FFFFFF;
    cursor: pointer;
    display: block;
    font-size: 20px;
    height: 40px;
    margin: 15px auto;
    padding-top: 8px;
    text-decoration: none;
    width: 148px;
}
.message_module_user_item a:hover{
	background: url("${ctx}/resources/pt/images/login/login_button2_null.png") repeat scroll 0 0 transparent;
}
</STYLE>
</head>
<body>
<div class="message_module">
	<div class="message_module_title">统一业务应用云平台</div>
	<div class="message_module_operate">
		<div class="message_module_user_title">管理</div>
		<a href="${ctx}/superadmin/login">租户管理</a> <a
			href="${ctx}/security/tenant/register">注册租户</a>
	</div>
	<div class="message_module_user">
		<div class="message_module_user_title">租户登录</div>
		<c:forEach var="tenant" items="${tenants}">
			<div class="message_module_user_item">
				<a href="${ctx}/tenant/${tenant.account}">${tenant.name}</a>
			</div>
		</c:forEach>
	</div>
</div>
</body>
</html>