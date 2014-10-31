<%@page import="com.wellsoft.pt.security.core.userdetails.UserDetailsServiceProvider"%>
<%@page import="com.wellsoft.pt.core.resource.Config"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>登录页</title>
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

.login .content_home {
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
    background:#EAECED;
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
	margin-top: 4px;
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
.content_home_right_submit input.content_home_cert_submit {
	background: url("${ctx}/resources/pt/images/login/login_cert_button.png") repeat scroll 0 0 transparent;
	float: right;
}
.content_home_right_submit input.content_home_cert_submit:hover{
	background: url("${ctx}/resources/pt/images/login/login_cert_button.png") repeat scroll 0 0 transparent;
}
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
.error_message{
	height: 10px;
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
</div>
<div class="content_home">
	   <div class="content_home_left">
	   		<div class="content_home_left_img"><img  src="${ctx}/resources/pt/images/login/login_left_ssgl.png"/></div>
	   		<div style=" margin-left: auto;
    margin-right: auto;
    margin-top: 10px;
    width: 407px;">
	   		<div><span>CA证书登录：</span><span style="margin-left: 190px;">用户帮助：</span></div>
	   		<div style="width:500px;"><span>1、请先下载并安装<a style="cursor: pointer;color: #F7572D;text-decoration: underline;" href="${ctx}/resfacade/share/cadriver?filename=cadriver.zip">政务CA安全引擎功能</a></span><span style="margin-left: 50px;"><a style="cursor: pointer;color: #F7572D;text-decoration: underline;" href="${ctx}/resfacade/share/cadriver?filename=厦门市行政审批（商事登记）信息管理平台使用手册v2.0.doc">商改使用手册下载(v2.0更新于2014.08.11)</a></span></div>
	   		<div style="width:500px;">
	   			<span>
	   			2、插入CA的USB，点击证书登录即可
	   			</span>
	   			<span style="margin-left: 58px;"><a style="cursor: pointer;color: #F7572D;text-decoration: underline;" href="${ctx}/resfacade/share/cadriver?filename=市行政审批（建设项目）信息管理平台用户操作手册V1.0.doc">建设项目操作手册(v1.0更新于2014.08.07)</a></span>
	   		</div>
	   </div>
	   </div>
	   <div class="content_home_right">
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
					<span>用户名</span><input id="j_username" name="j_username" type="text" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_username" class="checkbox" type="checkbox" tabindex="-1"/>
				</div>
				<div class="content_home_right_div content_home_right_password">
					<span>密码</span><input id="j_password" name="j_password" type="password" value="" class="content_home_right_input"/><div>&nbsp;记住</div><input id="remember_password" class="checkbox" type="checkbox" tabindex="-1"/>
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
					<input type="submit" class="content_home_right_submit_button" value=""/>
					<input id="login_by_cert" type="button" class="content_home_cert_submit" />
				</div>
				<div class="content_home_right_other content_home_right_other_first">
					<div class="content_home_right_other_sc"><a href="javascript:void(0)" onclick="SetHome(this,window.location)"><img src="${ctx}/resources/pt/images/login/login_sc.png"/>&nbsp;&nbsp;设为首页</a></div>
					<div class="content_home_right_other_sy"><a href="javascript:void(0)" onclick="shoucang(document.title,window.location)"><img src="${ctx}/resources/pt/images/login/login_sy.png"/>&nbsp;&nbsp;加入收藏</a></div>
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
			<div class="span4 offset3"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;width: 600px;">
				技术支持电话:(0592)7703191,(0592)7703192,CA服务电话 :(0592)2031201  邮箱:spgl@xmas.gov.cn
			</div>
			<div class="span4 offset4"
				style="height: 30px; line-height: 30px; text-align: center; vertical-align: middle;">
				(建议使用IE8.0以上版本浏览器)
			</div>
		</div>

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
	</script>
	<div style="display:none;">
	<object id="fjcaWs" name="SBFjCAEnAndSign" classid="CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709" codebase="${ctx}/resources/pt/js/security/SBFjCAEnAndSign.ocx"  >
	</object>
    <object id="fjcaControl" classid="clsid:414C56EC-7370-48F1-9FB4-AF4A40526463" codebase="${ctx}/resources/pt/js/security/fjcaControl.ocx">
    </object>
	</div>
</body>
</html>
