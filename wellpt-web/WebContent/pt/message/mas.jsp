<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
</style>

</head>
<body>
	<div id="container">
		<div class="pane ui-layout-center">
		
			<form action="" id="mas_form" method="post" class="mas_form" enctype='multipart/form-data' >
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">MAS设置</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid"/>
						<table class="stab">
							<tr>
								<td><label>移动代理服务器IP</label></td>
								<td><input id="imIp" name="imIp" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>接口登录名</label></td>
								<td><input id="loginName" name="loginName" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>接口登录密码</label></td>
								<td><input id="loginPassword" name="loginPassword" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>接口编码</label></td>
								<td><input id="apiCode" name="apiCode" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>数据库名称</label></td>
								<td><input id="dbName" name="dbName" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>重发时限（/天）</label></td>
								<td><input id="sendLimit" name="sendLimit" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>是否启用MAS</label></td>
								<td><input id="isOpen" name="isOpen"
									type="checkbox" value="" /><label for="isEnable_enable">是</label>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
			</form>
		</div>
	</div>


	<!-- Project -->
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/message/mas.js"></script>
</body>
</html>