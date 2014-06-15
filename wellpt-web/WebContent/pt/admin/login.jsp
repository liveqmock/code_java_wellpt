<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/pt/common/taglibs.jsp"%>
<head>
<title>租户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<style type="text/css">
body {
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
.login  a:link, a:visited {
    color: #434343;
    text-decoration: none;
}

.login  .content_home {
    position: relative;
    width: 100%;
	height:650px;
}
.login  .content_home .content_home_left{
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
    background: url("${ctx}/resources/pt/images/login/footer.png") no-repeat scroll 35px 10px #EAECED;
    border-top: 2px solid #CACFD2;
    color: #63686A;
    height: 78px;
    margin-top: 15px;
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
    margin-top: 110px;
    width: 500px;
}
.content_home_left_form{
	margin-left: auto;
    margin-right: auto;
    margin-top: 110px;
    width: 450px;
}
.content_home_right_form{
	margin-top: 110px;
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
    width: 300px;
}
.content_home_right_div  .content_home_right_input {
    height: 30px;
    margin-left: 5px;
    width: 180px;
}
.content_home_right_div  span {
     display: block;
    float: left;
    margin-left: 10px;
    width: 50px;
}
.content_home_right_div input{
	border:none;
	height: 35px;
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
}
.content_home_right_other div{
	float: left;
    margin-left: 30px;
    margin-top: 20px;
}
</style>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />
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
	<h1><a href="#">威尔软件</a></h1>
    <h2>协同办公系统</h2>
</div>
<div class="content_home">
	   <div class="content_home_left">
	   		<div class="content_home_left_img"><img  src="${ctx}/resources/pt/images/login/login_left.png"/></div>
	   </div>
	   <div class="content_home_right">
	   		<div class="content_home_right_form">
				<form action="${ctx}/passport/user/login/security_check" method="post"
					class="form-horizontal">
				<div class="control-group">
					<div class="controls">
						<input type="hidden" id="tenant" name="tenant" value="${tenant}"
							placeholder="tentent" />
					</div>
				</div>
				<div class="content_home_right_div content_home_right_username">
					<span>用户名</span><input id="j_username" name="j_username" type="text" class="content_home_right_input"/><input type="checkbox"/>&nbsp;记住
				</div>
				<div class="content_home_right_div content_home_right_password">
					<span>密码</span><input id="j_password" name="j_password" type="password" class="content_home_right_input"/><input type="checkbox"/>&nbsp;记住
				</div>
				<div class="control-group">
					<!-- 显示登录失败原因 -->
					<c:if test="${not empty param.error}">
						<label class="control-label">登录失败：</label>
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</c:if>
				</div>
				<c:if test="${requiredVerifyCode == true}">
					<div class="control-group">
						<label for="j_verify_code" class="control-label">验证码</label>
						<div class="controls">
							<input type="text" id="j_verify_code" name="j_verify_code"
								value="" placeholder="verify code" />
							<div>
								<img id="virify_code_image" style="cursor: pointer;"
									src="${ctx}/security/verifycode/image?t=<%=new Date().getTime()%>"
									alt="验证码" width="64" height="24">
							</div>
						</div>
					</div>
				</c:if>
				<div class="content_home_right_div content_home_right_submit">
					<input type="submit" calss="content_home_right_submit_button"/>
				</div>
				<div class="content_home_right_other">
					<div class="content_home_right_other_sc"><a href="javascript:void(0)" onclick="SetHome(this,window.location)"><img src="${ctx}/resources/pt/images/login/login_sc.png"/>&nbsp;&nbsp;加入收藏</a></div>
					<div class="content_home_right_other_sy"><a href="javascript:void(0)" onclick="shoucang(document.title,window.location)"><img src="${ctx}/resources/pt/images/login/login_sy.png"/>&nbsp;&nbsp;设为首页</a></div>
<!-- 					<div class="content_home_right_other_sy"><input type="checkbox"/>&nbsp;&nbsp;隐藏菜单</div> -->
				</div>
				</form>
			</div>
	   </div>
</div>

<div class="footer"><a href="#">使用帮助</a>|<a href="#">联系管理员</a></div>
</div>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script src="${ctx}/resources/timers/jquery.timers.js"></script>
	<script type="text/javascript">
		$("#_spring_security_remember_me").attr("checked", "checked");

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
		$(document).ready(function() {
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
</body>
</html>