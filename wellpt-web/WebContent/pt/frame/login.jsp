<%@ page contentType="text/html;charset=UTF-8"%>
<%@   page   session= "true"   %>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>
<%@ page import="com.wellsoft.pt.utils.security.SpringSecurityUtils"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>OA系统登录页</title>


<link href="${ctx}/css/themes/default/easyui.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/css/themes/icon.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/js/easyui/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="${ctx}/js/easyui//locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="${ctx}/js/validate/jquery.validate.js"
	type="text/javascript"></script>
<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		//$("#loginForm").validate();
	});
</script>
</head>
<body>

	<div id="login-window" title="OA-用户登录" class="easyui-window"
		style="width: 500px; height: 250px;" closable="false"
		minimizable="false" maximizable="false" collapsible="false">
		<div style="padding: 20px 20px 40px 80px;">
			<div class="yui-b">
				<%
	if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
%>
				<div class="error">登录失败，请重试.</div>
				<%
	}
%>
				
				<form id="loginForm" action="${ctx}/j_spring_security_check"
					method="post" style="margin-top: 1em">

					<table>
                        <tr>
							<td align="right"><b>租户名：</b></td>

							<td><input type='text' id='tenant' name='tenant' 
								style="width: 200px;" class="required" /></td>
						</tr>
						<tr>
							<td align="right"><b>用户名：</b></td>

							<td><input type='text' id='j_username' name='j_username'
								style="width: 200px;" class="required" /></td>
						</tr>
						<tr>
							<td align="right"><b>密码：</b></td>

							<td><input type='password' id='j_password' name='j_password'
								style="width: 200px;" class="required" /></td>
						</tr>
						<tr>
							<td colspan='2' align="right"><input type="checkbox"
								name="_spring_security_remember_me" />两周内记住我 <input value="登录"
								type="submit" class="button" /> <input type="reset" value="重置"
								class="button" /></td>
						</tr>
					</table>
				</form>
				
			</div>
		</div>
	</div>

</body>
</html>




