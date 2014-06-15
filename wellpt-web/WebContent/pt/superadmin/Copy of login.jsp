<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>超级管理员登录</title>
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
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span4 offset4">
				<form action="${ctx}/superadmin/login/security_check" method="post"
					class="form-horizontal">
					<fieldset>
						<legend>租户管理员登录</legend>
						<div class="control-group">
							<!-- 显示登录失败原因 -->
							<c:if test="${not empty param.error}">
								<label class="control-label">登录失败：</label>
								<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
							</c:if>
						</div>
						<div class="control-group">
							<label for="j_username" class="control-label">用户名</label>
							<div class="controls">
								<input type="text" id="j_username" name="j_username"
									value="admin" placeholder="username" />
							</div>
						</div>
						<div class="control-group">
							<label for="j_password" class="control-label">密码</label>
							<div class="controls">
								<input type="password" id="j_password" name="j_password"
									value="0" placeholder="password" />
							</div>
						</div>
						<div class="control-group">
							<label for="_spring_security_remember_me" class="control-label">两周内记住我</label>
							<div class="controls">
								<input type="checkbox" id="_spring_security_remember_me"
									name="_spring_security_remember_me" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="btn btn-primary">登录</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="span4">
				<div class="row-fluid">
					<div class="span8 offset4">
						<a href="${ctx}/index">返回首页</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
</body>
</html>