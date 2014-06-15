<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>System Log</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	padding: 10px;
	height: 100%;
}
</style>
</head>
<body>
	<div id="container" class="tabs">
		<form>
			<table style="width: 100%; height: 100%;">
				<tr>
					<td style="width: 65px;" class="align-top"><label>系统日志</label></td>
					<td><textarea id="system_log" rows="30" class="full-width"></textarea></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/timers/jquery.timers.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/log/system_log.js"></script>
</body>
</html>