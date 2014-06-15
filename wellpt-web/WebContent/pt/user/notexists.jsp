<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>租户登录</title>
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
			<div class="span9 offset3">
				<h3>你当前登录的账号非该单位的用户，请注销后再登录!</h3>
			</div>
		</div>
	</div>
	<!-- /container -->
	<script src="${ctx}/resources/jquery/1.8/jquery.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>