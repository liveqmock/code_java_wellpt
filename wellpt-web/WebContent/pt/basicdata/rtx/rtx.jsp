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
		
			<form action="" id="rtx_form" method="post" class="rtx_form" enctype='multipart/form-data' >
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">RTX设置</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table class="stab">
							<tr>
								<td><label>是否启用RTX</label></td>
								<td><input id="isEnable_enable" name="isEnable"
									type="checkbox" value="" /><label for="isEnable_enable">是</label>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>RTX服务器IP</label></td>
								<td><input id="rtxServerIp" name="rtxServerIp" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>SDK服务器端口</label></td>
								<td><input id="rtxServerPort" name="rtxServerPort" type="text" class="inputxt" /></td>
							</tr>
							<!-- <tr>
								<td><label>SDK服务器IP</label></td>
								<td><input id="sdkServerIp" name="sdkServerIp" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>SDK服务器端口</label></td>
								<td><input id="sdkServerPort" name="sdkServerPort" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>RTX应用服务器IP</label></td>
								<td><input id="rtxApplicationServerIp" name="rtxApplicationServerIp" type="text" class="inputxt" /></td>
							</tr>
							<tr>
								<td><label>RTX应用服务器端口</label></td>
								<td><input id="rtxApplicationServerPort" name="rtxApplicationServerPort" type="text" class="inputxt" /></td>
							</tr>
							<tr> -->
								<td><label>系统消息发送方式</label></td>
								<td>
									<input id="messageSendWay_online" name="messageSendWay" type="radio" value="ON_LINE"/><label
									for="messageSendWay_online">在线消息</label>
								</td>
							</tr>
							<tr>
								<td><label>是否启用用户简称</label></td>
								<td><input id="isEnableAbbreviation_enable" name="isEnableAbbreviation"
									type="checkbox" value="" /><label for="isEnableAbbreviation_enable">是</label>
								</td>
							</tr>
							<tr>
								<td><label>同步操作</label></td>
								<td><input id="synchronizationOperations_clear_all" name="synchronizationOperation" type="radio" value="CLEAR_ALL"/><label
									for="synchronizationOperations_clear_all">清除RTX中的全部组织及用户</label> <input id="synchronizationOperations_only_add_new"
									name="synchronizationOperation" type="radio" value="ONLY_ADD_NEW" /><label for="synchronizationOperations_only_add_new">仅增加新OA用户</label>
									<input id="synchronizationOptions_remove_no_exist" name="synchronizationOperation" type="radio" value="REMOVE_NO_EXIST" /><label for="synchronizationOptions_remove_no_exist">删除OA中不存在的RTX部门和人员</label>
								</td>
							</tr>
							<tr>
								<td><label>RTX客户端下载地址</label></td>
								<td><input id="rtxClientDownloadAddress" name="rtxClientDownloadAddress" type="text" class="inputxt" /></td>
							</tr>
						</table>
					</div>
				</div>
				
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<button id="btn_synchronized_to_rtx" type="button" class="btn">同步组织到RTX</button>
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
		src="${ctx}/resources/pt/js/basicdata/rtx/rtx.js"></script>
</body>
</html>