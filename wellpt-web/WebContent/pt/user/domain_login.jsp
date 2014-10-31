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
<link rel="stylesheet"
	href="${ctx}/resources/pt/css/login/login.css" />
</head> 
<body onload="document.getElementById('j_username').focus()">
<div class="w">
    <div id="logo"><img src="${ctx}/resources/pt/css/login/logo-2013-l.png" alt="立达信中文网站" width="170" height="60"><b></b></div>
</div>
<form action="${ctx}/passport/user/login/security_check" method="post" id="login" name="login">
    <div class=" w1" id="entry">
        <div class="extra-en">[English]</div>
        <div class="mc " id="bgDiv">
            <div id="entry-bg" style="width: 511px; height: 455px; background-image: url(${ctx}/resources/pt/css/login/i/53a247c5Ne727c5df.png); position: absolute; left: -44px; top: -44px; background-position: 0px 0px; background-repeat: no-repeat no-repeat;">
                           </div>
            <div class="form ">
                <div class="item fore1">
                    <span>用户</span>
                    <div class="item-ifo">
                        <input type="text" id="j_username" name="j_username" class="text" tabindex="1" autocomplete="off" sta="0">
                        <div class="i-name ico"></div>
                        <label id="loginname_error" class="focus"></label>
                    </div>
                </div>
                                <div class="item fore2">
                    <span>密码</span>
                    <div class="item-ifo">
                        <input type="password" id="j_password" name="j_password" class="text" tabindex="2" autocomplete="off">
                        <input type="hidden" name="loginpwd" id="loginpwd" value="" class="hide">
                        <div class="i-pass ico"></div>
                        <c:if test="${not empty param.error}">
                        <label id="loginpwd_error" class="error" style="display: inline;">登录失败：<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></label>
						</c:if>
                    </div>
                </div>
                <input type="hidden" id="tenant" name="tenant" value="${tenant}" />
				<input type="hidden" id="tenantId" name="tenantId" value="${tenantId}" />
                <input type="hidden" name="loginType" id="loginType" />
				<input type="hidden" name="textCert" id="textCert" value=""/>
				<input type="hidden" name="textSignData" id="textSignData" value=""/>
				<input type="hidden" name="idNumber" id="idNumber" />
				<input type="hidden" name="certType" id="certType" />
                <div id="autoentry" class="item fore4">
                    <div class="item-ifo">
                        <input type="checkbox" class="checkbox" id="remember_username">
                        <label class="mar">记住用户</label>
                                                <div id="ctrlDiv" style="float:left;">
                            <input type="checkbox" id="remember_password" class="checkbox">
                            <label id="jdsafe" class="mar">记住密码</label>
                        </div>
                                                <label><a class="" href="#" onclick="javascript:alert('忘记密码');">忘记密码?</a></label>
                        <div class="clr"></div>
                    </div>
                </div>
                <div class="item login-btn2013">
                    <input type="submit" class="btn-img btn-entry" id="loginsubmit" value="登录" tabindex="8">
                </div>
            </div>
                <div class="coagent">
               <label class="ftx24">
                        友情链接：
                        <span class="clr"></span>
                    <span class="btns qq"><s></s> <a target="_blank" href="http://www.leedarson.com.cn/">中文网站</a></span>
                    <span class="btns qq"><s></s> <a target="_blank" href="http://www.leedarson.com/">英文网站</a></span>
                        <dl class="btns more-slide">
                            <dt><b>其它</b></dt>
                            <dd>
								<a target="_blank" href="http://www.leedarson.com.cn/">中文网站</a>		   
								<a target="_blank" href="http://www.leedarson.com/">英文网站</a>
								<br>
								<a target="_blank" href="http://www.leedarson.com.cn/">中文网站</a>
								<a target="_blank" href="http://www.leedarson.com/">英文网站</a>
                            </dd>
                        </dl>
                        <a style="display:none" id="kx001_btn_login"></a>
                    </label>
                </div>
        </div>
    </div>
</form>
<div class="w1">
    <div id="mb-bg" class="mb"></div>
</div>
<div class="w">
    <div id="footer-2013">
        <div class="links></div>
        <div class="copyright">技术支持电话：0592-1234567 CA服务电话：15457545415</div>
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
</body></html>