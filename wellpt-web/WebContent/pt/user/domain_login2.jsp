<%@page import="com.wellsoft.pt.security.core.userdetails.UserDetailsServiceProvider"%>
<%@page import="com.wellsoft.pt.core.resource.Config"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>LCP平台登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<%@ include file="/pt/common/taglibs.jsp"%>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
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

.login {
    height: 768px;
    width: 1360px;
    margin-top: 54px;
    position: relative;
    background: url("${ctx}/resources/pt/images/login/ldx_login_page.png");
}
.login .header {
	color: white;
	height: 230px;
}

.set_home_page{
	float:right;
	padding: 10px;
}

.join_store{
	float:right;
	padding: 10px;
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
    color: white;
    text-decoration: none;
}

.login .content_home {
background: url("${ctx}/resources/pt/images/login/ldx_transparent.png") no-repeat scroll 0 0 transparent;
    
/* 	filter:alpha(Opacity=50);-moz-opacity:0.5;opacity: 0.5; */
	height: 230px;
    margin-bottom: 0;
    margin-left: auto;
    margin-right: auto;
    padding: 10px;
    width: 700px;
}
.login  .content_home .content_home_left{
/* 	filter:alpha(Opacity=100);-moz-opacity:1;opacity: 1; */
	 background: none repeat scroll 0 0 #ffffff;
    float: left;
    height: 230px;
    width: 50%;
}
.login  .content_home .content_home_right{
	background-color: #f0f0f0;
    float: left;
    height: 230px;
    width: 50%;
}
.login  .footer {
	bottom: 20px;
    clear: both;
    color: white;
    position: absolute;
    text-align: center;
    width: 100%;
}

.login  .footer a:link, .footer a:visited {
/*     color: #63686A; */
/*     margin: 0 10px; */
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
	padding: 20px;
}
.content_home_right_div{
	background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #8E9293;
    color: #8E9293;
    font-size: 16px;
   height: 40px;
    line-height: 40px;
    margin-top: 15px;	
    width: 300px;
}
.content_home_right_div  .content_home_right_input {
     border: medium none;
    height: 20px;
    margin-bottom: 5px;
    width: 180px;
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
	margin-top: 4px;
	height: 25px;
}
.content_home_right_submit{
	border:none;
	background:none;
	margin-left:0;
}
.content_home_right_submit input{
	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -117px transparent;
    height: 38px;
    margin-left: 0;
    width: 148px;
	cursor: pointer;
	border: medium none;
    border-radius: 0 0 0 0;
    color: #FFFFFF;
    font-size: 15px;
    padding-left: 13px;
    font-family: microsoft yahei;
}
.content_home_right_submit input.content_home_cert_submit {
	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -163px -114px transparent;
	float: right;
	padding-top: 1px;
}

.content_home_right_submit_button:hover {
	background:#007bb4;
}

#login_by_cert:hover {
	background:#007bb4;
}

/* .content_home_right_submit input :hover { */
/* 	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -163px transparent; */
/* } */

/* .content_home_right_submit input.content_home_cert_submit:hover{ */
/* 	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -163px transparent; */
/* } */
.content_home_right_other {
    border-top: 1px solid #F4F6F7;
    margin-top: 40px;
    width: 300px;
    font-size: 12px;
}

.content_home_right_other_first {
	 border-top: 1px solid #DDDDDD;
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
 width: 340px;
 color: red;
    text-align: left;
}
.content_home_right_other_yc input{
	width: 10px;
	margin: 0;
	padding: 0;
}
.content_home_right_div .username{
	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -15px transparent;
    float: left;
    height: 36px;
    width: 40px;
}

.content_home_right_div .password{
	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -61px transparent;
    float: left;
    height: 36px;
    width: 40px;
}

.content_home_right_username .password{
	background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll 0 0 transparent;
}

/* .content_home_right_username:hover{ */
/* 	border-color: #FF7200; */
/* } */
/* .content_home_right_password:hover{ */
/* 	border-color: #FF7200; */
/* } */
/* .content_home_right_submit input:hover{ */
/* 	 background: url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -15px transparent; */
/* } */
/* .login a:hover{ */
/* 	filter:alpha(Opacity=40);-moz-opacity:0.5;opacity: 0.5; */
/* 	border:1px solid  */
/* } */
.login .header .error_message{
	
}
.join_store:hover {
	background: #fffff;
	filter:alpha(Opacity=40);-moz-opacity:0.5;opacity: 0.4;
}

.set_home_page:hover {
	background: #fffff;
	filter:alpha(Opacity=40);-moz-opacity:0.5;opacity: 0.4;
}

.slide_dot2 {
    float: left;
    margin: 5px 10px;
    width: 90px;
}


.slide_dot2 .dot_list {
    height: 18px;
    width: 18px;
}

.autoSkip {
	margin: 174px auto 0;
    width: 100px;
}

.autoSkip .autoSkipSpan {
	background:url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -34px -220px transparent;
	width:25px;
	 height: 20px;
	color:white;
	display: block;
	float: left;
	cursor: pointer;
}

.autoSkip .live {
	background:url("${ctx}/resources/pt/images/login/ldx_login.png") repeat scroll -17px -220px transparent;
	width:25px;
	 height: 20px;
	color:white;
	display: block;
	float: left;
	cursor: pointer;
}
</style>
</head>
<body onload="document.getElementById('j_username').focus()">
<%
	fjca.FJCAApps ca = new fjca.FJCAApps();
	//社保4000
	fjca.FJCAApps.setErrorBase(4000);
	//Windows 验证服务器IP
	//ca.setServerURL("192.168.11.209:7000");
	fjca.FJCAApps.setServerURL(Config.getValue(UserDetailsServiceProvider.KEY_FJCA_SERVER_URL));
	
	// 产生随机数,正式的应用，随机数要在服务端产生传给客户端，登陆成功后清空。
	String strRandom = ca.FJCA_GenRandom2();
	session.setAttribute("RANDOM",strRandom);
%>
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
//计算浏览器的高度
// 
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
<div class="login" id="login">
<script type="text/javascript">
//使div上下居中
var o = document.getElementById("login");
o.style.marginTop = parseInt((window.screen.availHeight-768)/4) + "px";
</script>
<div class="header">
<!-- 	<div class="set_home_page"> -->
<%-- 		<a href="javascript:void(0)" onclick="SetHome(this,window.location)"><img src="${ctx}/resources/pt/images/login/ldx_home_page.png"/>&nbsp;&nbsp;设为首页</a> --%>
<!-- 	</div> -->
<!-- 	<div class="join_store"> -->
<%-- 		<a href="javascript:void(0)" onclick="shoucang(document.title,window.location)"><img src="${ctx}/resources/pt/images/login/ldx_store_up.png"/>&nbsp;&nbsp;加入收藏</a> --%>
<!-- 	</div> -->
</div>
<div class="content_home">
	   <div class="content_home_left">
	   		<div class="content_home_right_form">
				<form action="${ctx}/passport/user/login/security_check" method="post" id="login" name="login"
					class="form-horizontal">
				<div class="control-group">
					<div class="controls">
						<input type="hidden" id="tenant" name="tenant" value="${tenant}" />
						<input type="hidden" id="tenantId" name="tenantId" value="${tenantId}" />
					</div>
				</div>
				<div class="content_home_right_div content_home_right_username">
					<div class="username"></div><input id="j_username" name="j_username" type="text" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_username" class="checkbox" type="checkbox" tabindex="-1"/>
				</div>
				<div class="content_home_right_div content_home_right_password">
					<div class="password"></div><input id="j_password" name="j_password" type="password" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_password" class="checkbox" type="checkbox" tabindex="-1"/>
				</div>
				<input type="hidden" name="loginType" id="loginType" />
				<input type="hidden" name="textCert" id="textCert" value=""/>
				<input type="hidden" name="textOriginData" id="textOriginData" value="<%=strRandom%>"/>
				<input type="hidden" name="textSignData" id="textSignData" value=""/>
				<input type="hidden" name="idNumber" id="idNumber" />
				<input type="hidden" name="certType" id="certType" />
				<div class="control-group error_message">
					<!-- 显示登录失败原因 -->
					<c:if test="${not empty param.error}">
						<label class="control-label">登录失败：<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></label>
						
					</c:if>
				</div>
				<c:if test="${requiredVerifyCode == true}">
					<div class="control-group content_home_right_div">
						<span for="j_verify_code">验证码</span>
						<div class="controls">
							<input type="text" id="j_verify_code" name="j_verify_code"
								value="" placeholder="verify code" class="content_home_right_input"/>
							<div>
								<img id="virify_code_image" style="cursor: pointer;"
									src="${ctx}/security/verifycode/image?t=<%=new Date().getTime()%>"
									alt="验证码" width="64" height="24">
							</div>
						</div>
					</div>
				</c:if>
				<div class="content_home_right_div content_home_right_submit">
					<input type="submit" class="content_home_right_submit_button" value="用户登录"/>
<!-- 					<input id="login_by_cert" type="button" class="content_home_cert_submit" value="证书登录"/> -->
				</div>
<!-- 				<div class="content_home_right_other content_home_right_other_first"> -->
<%-- 					<div class="content_home_right_other_sc"><a href="javascript:void(0)" onclick="SetHome(this,window.location)"><img src="${ctx}/resources/pt/images/login/login_sc.png"/>&nbsp;&nbsp;设为首页</a></div> --%>
<%-- 					<div class="content_home_right_other_sy"><a href="javascript:void(0)" onclick="shoucang(document.title,window.location)"><img src="${ctx}/resources/pt/images/login/login_sy.png"/>&nbsp;&nbsp;加入收藏</a></div> --%>
<!-- <!-- 					<div class="content_home_right_other_yc"><input class="checkbox" type="checkbox"/>&nbsp;&nbsp;隐藏菜单</div> -->
<!-- 				</div> -->
				</form>
			</div>
	   </div>
	   <div class="content_home_right">
	   </div>
</div>

<!-- <div id="slide_dot3" class="slide_dot2 clearfix"> -->
<!-- 	<span class="dot_list "></span> -->
<!-- 	<span class="dot_list "></span> -->
<!-- 	<span class="dot_list current"></span> -->
<!-- 	<span class="dot_list "></span> -->
<!-- </div> -->

<div class="autoSkip">
	<span class="autoSkipSpan live"></span>
	<span class="autoSkipSpan"></span>
	<span class="autoSkipSpan"></span>
	<span class="autoSkipSpan"></span>
</div>
<div class="footer">
	技术支持电话：0592-1234567 CA服务电话：15457545415	
</div>
	<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx }/ReportServer?op=emb&resource=finereport.js"></script>      
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.js"></script>
	<script type="text/javascript" src="${ctx}/resources/cookie/jquery.cookie.js"></script> 
	<script type="text/javascript" src="${ctx}/resources/pt/js/security/cert_login.js"></script> 
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
// 		        var username =FR.cjkEncode(document.getElementById("j_username").value);    
// 		        var password =FR.cjkEncode(document.getElementById("j_password").value); 
//                  var url="${ctx}/ReportServer?op=fs_load&cmd=sso";	
// 				 jQuery.ajax({  
// 					 url:url,
// 					 dataType:"jsonp",
// 					 data:{"username":username,"password":password},  
// 					 jsonp:"callback",  
// 					 timeout:50000,  
// 					 success:function(data) { 
// 							if (data.indexOf("success")>=0) { 
// // 								 alert("登录成功");
// 								  document.getElementById("login").submit(); 
// 							} 
// 					 },  
// 					 error:function(){
// // 						 alert("登录失败");
// 						 doSubmit();  
// 					 } 				 			
// 					});
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
		// 登录验证码
		$("#virify_code_image").click(
				function(e) {
					var url = "${ctx}/security/verifycode/image?t="
							+ new Date().getTime();
					$(this).attr("src", url);
				});
	</script>
	<!-- 短信验证码 -->
	<c:if test="${requiredSmsVerifyCode == true}">
		<!-- Modal -->
		<div id="sms_verify_code_modal" class="modal hide fade">
			<div class="modal-header">
				<button id="modal_close" type="button" class="close"
					data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>短信验证</h3>
			</div>
			<div class="modal-body">
				<p>
				<div class="control-group">
					<div class="controls">
						<button id="btn_send_sms_verify_code" type="button">发送短信验证码</button>
						<span id="timeout_msg"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="j_sms_verify_code" class="control-label">短信验证码</label>
					<div class="controls">
						<input type="text" id="j_sms_verify_code" name="j_sms_verify_code"
							value="" placeholder="sms verify code" />
					</div>
					<span id="error_msg_sms_verify_code"></span>
				</div>
				</p>
			</div>
			<div class="modal-footer">
				<a id="modal_cancel" href="#" class="btn">取消</a> <a id="modal_ok"
					href="#" class="btn btn-primary">确定</a>
			</div>
		</div>
		<script type="text/javascript">
		$().ready(function() {
			$("#sms_verify_code_modal").modal({
				show : true,
				keyboard : false
			});
			// 关闭短信验证码窗口，注销登录
			$("#sms_verify_code_modal").on("hidden", function() {
				location.href = "${ctx}/security_logout";
			});
			// 发送短信验证码
			$("#btn_send_sms_verify_code").click(function(e) {
				$.ajax({
					type : "POST",
					url : "${ctx}/security/verifycode/sms",
					data : {},
					success : function(success, statusText, jqXHR) {
						$("#j_sms_verify_code").val(success.verifyCode);
						timeout = success.timeout;
						$('#timeout_msg').html(timeout);
						$("#btn_send_sms_verify_code").attr("disabled", "disabled");
						$("body").everyTime("1s", "sms", function(counter) {
							$('#timeout_msg').html(timeout - counter);
							if (counter >= timeout) {
								smsTimeout();
							}
						}, timeout);
					},
					error : function(jqXHR, statusText, error) {
						$("#error_msg_sms_verify_code").html("请输入正确的短信验证码!");
					}
				});
			});
			// 短信验证码超时处理
			function smsTimeout() {
				$.ajax({
					url : "${ctx}/security/verifycode/sms/timeout",
					success : function(success, statusText, jqXHR) {
						$("#btn_send_sms_verify_code").removeAttr("disabled");
					}
				});
			}
			// 关闭短信验证码窗口
			$("#modal_close, #modal_cancel").click(function(e) {
				$("#sms_verify_code_modal").modal("hide");
			});
			// 验证输入的短信验证码
			$("#modal_ok").click(function(e) {
				$.ajax({
					type : "POST",
					url : "${ctx}/passport/user/login/check/smsverifycode",
					data : {
						j_sms_verify_code : $("#j_sms_verify_code").val()
					},
					success : function(success, statusText, jqXHR) {
						location.href = "${ctx}/passport/user/login/success";
					},
					error : function(jqXHR, statusText, error) {
						$("#error_msg_sms_verify_code").html("请输入正确的短信验证码!");
					}
				});
			});
		});
		</script>
	</c:if>
	<script type="text/javascript">
	$(function(){
		if(location.href.indexOf("as_dev")>-1){
			$(".header h1").css("width","230px");
			$(".header h1").css("background","url('${ctx}/resources/pt/img/zzffzx-logo3.png') no-repeat scroll 0 6px transparent");
			$(".header h2").css("width","138px");
			$(".header h2").html("行政审批云平台");
			$(".login .footer").css("background","url('${ctx}/resources/pt/img/zzffzx-logo2.png') no-repeat scroll 35px 10px #EAECED");
		}
	});
	$('body').everyTime('5s',function(){
		var index = $('.autoSkipSpan.live').index();
		$(".autoSkipSpan").attr("class","autoSkipSpan");
		if(index == 3){
			$(".autoSkipSpan").eq(0).attr("class","autoSkipSpan live");
			$(".login").css("background","url('${ctx}/resources/pt/images/login/ldx_login_page.png')");
		}
		else{
			$(".autoSkipSpan").eq(index+1).attr("class","autoSkipSpan live");
			$(".login").css("background","url('${ctx}/resources/pt/images/login/ldx_login_page2.png')");
		}
	});
	
	</script>
	<div style="display:none;">
<%-- 	<object id="fjcaWs" name="SBFjCAEnAndSign" classid="CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709" codebase="${ctx}/resources/pt/js/security/SBFjCAEnAndSign.ocx"  > --%>
<!-- 	</object> -->
<%--     <object id="fjcaControl" classid="clsid:414C56EC-7370-48F1-9FB4-AF4A40526463" codebase="${ctx}/resources/pt/js/security/fjcaControl.ocx"> --%>
<!--     </object> -->
	</div>
</div> 
	
</body>
</html>
