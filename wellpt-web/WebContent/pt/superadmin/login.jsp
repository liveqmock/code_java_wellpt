<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/pt/common/taglibs.jsp"%>
<head>
<title>管理员登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />
<style type="text/css">
body {
	background: none repeat scroll 0 0 #EAECED;
    color: #434343;
    font: 12px/1.5 arial;
}
*{
	margin:0;
	padding:0;
}
.login .header {
    background: none repeat scroll 0 0 #30619D;
    border-bottom: 1px solid #1E4574;
    color: #FFFFFF;
    height: 86px;
    position: relative;
    z-index: 2;
}
.login .header h1 {
    background: url("${ctx}/resources/pt/images/login/logo.png") no-repeat scroll 0 1px transparent;
    display: inline;
    float: left;
    height: 50px;
    margin: 20px 15px 0;
    overflow: hidden;
    text-indent: -999px;
    width: 145px;
}
.login .header h2 {
    border-left: 1px solid #FFFFFF;
    color: #FFFFFF;
    float: left;
    font-family: "Microsoft YaHei";
    font-size: 16px;
    font-weight: normal;
    height: 27px;
    line-height: 27px;
    margin-top: 32px;
    text-align: center;
    width: 220px;
}
.login  a:link, a:visited {
    color: #434343;
    text-decoration: none;
}

.login  .content_home {
    position: relative;
    width: 100%;
	height:400px;
}
.login  .content_home .content_home_left{
	background: none repeat scroll 0 0 #FFFFFF;
	width:55%;
	height:100%;
	float:left;
}
.login  .content_home .content_home_right{
	width:45%;
	height:100%;
	background: url("${ctx}/resources/pt/images/login/login_right_bg.png") no-repeat;
	float:left;
}
.login  .footer {
/*     background: url("${ctx}/resources/pt/images/login/footer.png") no-repeat scroll 35px 10px #EAECED; */
    background: #EAECED;
    border-top: 2px solid #CACFD2;
    color: #63686A;
    height: 89px;
    padding: 20px 20px 0;
    text-align: right;
	clear: both;
}

.login  .footer a:link, .footer a:visited {
    color: #63686A;
    margin: 0 10px;
}
.content_home_left_img {
    margin-left: auto;
    margin-right: auto;
    margin-top: 30px;
    width: 500px;
}
.content_home_left_form{
	margin-left: auto;
    margin-right: auto;
    margin-top: 110px;
    width: 450px;
}
.content_home_right_form{
	margin-top: 50px;
	margin-left: 100px;
}
.content_home_right_div{
	background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #8E9293;
    color: #8E9293;
    font-size: 16px;
   height: 40px;
    line-height: 40px;
    margin-top: 15px;	
    width: 340px;
}
.content_home_right_div  .content_home_right_input {
     border: medium none;
    height: 20px;
    margin-bottom: 5px;
    width: 200px;
}

.content_home_right_div  span {
     display: block;
    float: left;
    margin-left: 10px;
    width: 50px;
}
.content_home_right_div div{
	 display: block;
    float: right;
    font-size: 12px;
     margin-right: 7px;
     height: 30px;		
     line-height: 32px;
}
.content_home_right_div input{
	border:none;
	height: 35px;
}
.content_home_right_div .checkbox{
	float: right;
	height: 25px;
}
.content_home_right_submit{
	border:none;
	background:none;
	margin-left:0;
}
.content_home_right_submit input{
	 background: url("${ctx}/resources/pt/images/login/login_button.png") repeat scroll 0 0 transparent;
    height: 47px;
    margin-left: 0;
    width: 148px;
	cursor: pointer;
}

.content_home_right_other {
    border-top: 1px solid #DDDDDD;
    margin-top: 40px;
    width: 300px;
    font-size: 12px;
}
.content_home_right_other div{
	float: left;
    margin-left: 30px;
    margin-top: 20px;
    width: 70px;
}
.content_home_right_div.content_home_right_submit > input {
    width: 148px;
    text-indent: -999;
}
.content_home_right_other_sy{

}

.form-horizontal .controls {
    margin-left: 0;
}
.form-horizontal .controls div{
	float: right;
    margin-right: 10px;
}
.form-horizontal .controls  #j_verify_code{
	width: 160px;
}
.form-horizontal .control-label {
 width: 250px;
}
.content_home_right_other_yc input{
	width: 10px;
	margin: 0;
	padding: 0;
}
.content_home_right_username:hover{
	border-color: #FF7200;
}
.content_home_right_password:hover{
	border-color: #FF7200;
}
.content_home_right_submit input:hover{
	 background: url("${ctx}/resources/pt/images/login/login_button2.png") repeat scroll 0 0 transparent;
}
.login a:hover{
	color: #FF7200;
}
</style>
</head>
<body>
<script type="text/javascript">
//设置为主页 
function SetHome(obj,vrl){ 
	try{ 
		obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl); 
	} 
	catch(e){ 
	if(window.netscape) { 
		try { 
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
		} 
		catch (e) { 
			alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。"); 
		} 
		var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch); 
		prefs.setCharPref('browser.startup.homepage',vrl); 
	}else{ 
		alert("您的浏览器不支持，请按照下面步骤操作：1.打开浏览器设置。2.点击设置网页。3.输入："+vrl+"点击确定。"); 
	} 
	} 
} 
	// 加入收藏 兼容360和IE6 
function shoucang(sTitle,sURL) 
	{ 
	try 
	{ 
		window.external.addFavorite(sURL, sTitle); 
	} 
	catch (e) 
	{ 
	try 
	{ 
		window.sidebar.addPanel(sTitle, sURL, ""); 
	} 
	catch (e) 
	{ 
		alert("加入收藏失败，请使用Ctrl+D进行添加"); 
	} 
	} 
} 
</script>
<div class="login">
<div class="header">
<!-- 	<h1><a href="#">威尔软件</a></h1> -->
<!--     <h2>统一业务应用与开发云平台</h2> -->
<h1 style="width: 230px;background:url('${ctx}/resources/pt/img/zzffzx-logo4.png') no-repeat 0 1px;height: 60px;cursor: auto;margin: 20px 100px 0;" >&nbsp;</h1>
<h2  style="width: 312px;border-left: medium none;margin-left: -270px;font-size: 18px;margin-top: 28px;">
<!--     <div style="text-align: left;letter-spacing: 15px;">厦门市审批服务云</div> -->
     <div style="text-align: left;font-size: 24px;">厦门市行政审批信息管理平台</div>
    </h2>
</div>
<div class="content_home">
	   <div class="content_home_left">
	   		<div class="content_home_left_img"><img  src="${ctx}/resources/pt/images/login/login_left_ssgl.png"/></div>
	   </div>
	   <div class="content_home_right">
	   		<div class="content_home_right_form">
				<form action="${ctx}/superadmin/login/security_check" method="post"
					class="form-horizontal">
				<div class="control-group">
					<div class="controls">
						<input type="hidden" id="tenant" name="tenant" value="${tenant}"
							placeholder="tentent" />
					</div>
				</div>
				<div class="content_home_right_div content_home_right_username">
					<span>用户名</span><input id="j_username" name="j_username" type="text" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_username" class="checkbox" type="checkbox" tabindex="-1"/>
				</div>
				<div class="content_home_right_div content_home_right_password">
					<span>密码</span><input id="j_password" name="j_password" type="password" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_password" class="checkbox" type="checkbox" tabindex="-1"/>
				</div>
				<div class="control-group">
					<!-- 显示登录失败原因 -->
					<c:if test="${not empty param.error}">
						<label class="control-label">登录失败：<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></label>
						
					</c:if>
				</div>
				<div class="content_home_right_div content_home_right_submit">
					<input type="submit" calss="content_home_right_submit_button" value=""/>
				</div>
				<div class="content_home_right_other">
					<div class="content_home_right_other_sc"><a href="javascript:void(0)" onclick="SetHome(this,window.location)"><img src="${ctx}/resources/pt/images/login/login_sc.png"/>&nbsp;&nbsp;加入收藏</a></div>
					<div class="content_home_right_other_sy"><a href="javascript:void(0)" onclick="shoucang(document.title,window.location)"><img src="${ctx}/resources/pt/images/login/login_sy.png"/>&nbsp;&nbsp;设为首页</a></div>
<!-- 					<div class="content_home_right_other_yc"><input class="checkbox" type="checkbox"/>&nbsp;&nbsp;隐藏菜单</div> -->
				</div>
				</form>
			</div>
	   </div>
</div>

<div class="footer">
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				Copyright &copy; <a>厦门市行政服务中心管理委员会</a>
			</div>
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;width: 400px;">
				技术支持电话:(0592)7703191,(0592)7703192  邮箱:spgl@xmas.gov.cn
			</div>
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				(建议使用IE8.0以上版本浏览器)
			</div>
			
</div>

</div>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/resources/cookie/jquery.cookie.js"></script> 
	<script type="text/javascript">
	$().ready(function(){
		$("input[type=submit]").click(function(){
			if($("#remember_username").attr("checked") === "checked") {
				$.cookie("cookie.checked.username", $("#remember_username").attr("checked"), { expires: 7, path: '/' });
				$.cookie("cookie.remember.username", $("#j_username").val(), { expires: 7, path: '/' });
			}else{
				$.cookie("cookie.checked.username", "unchecked", { expires: 7, path: '/' });
				$.cookie("cookie.remember.username", "", { expires: 7, path: '/' });
			}
			if($("#remember_password").attr("checked") === "checked") {
				$.cookie("cookie.checked.password", $("#remember_password").attr("checked"), { expires: 7, path: '/' });
				$.cookie("cookie.remember.password", $("#j_password").val(), { expires: 7, path: '/' });
			}else{
				$.cookie("cookie.checked.password", "unchecked", { expires: 7, path: '/' });
				$.cookie("cookie.remember.password", "", { expires: 7, path: '/' });
			}
		});
		// $("#_spring_security_remember_me").attr("checked", "checked");
		if($.cookie("cookie.checked.username") === "checked") {
			$("#remember_username").attr("checked", "checked");
			$("#j_username").val($.cookie("cookie.remember.username"));
		}
		if($.cookie("cookie.checked.password") === "checked") {
			$("#remember_password").attr("checked", "checked");
			$("#j_password").val($.cookie("cookie.remember.password"));
		}
	});
	</script>
</body>
</html>