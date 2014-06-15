<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Tenant List</title>

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
</head>
<body>

	<div class="ui-layout-west">
		<div class="btn-group btn-group-top">
			<input name="currentUserId" id="currentUserId" type="hidden"
				value="${userId }" />
			<div class="query-fields">
				<input id="query_value" name="query_value" />
				<button id="btn_query" type="button" class="btn">查询</button>
			</div>
		</div>
		<div style="height: 23px;"></div>
		<table id="list"></table>
		<div id="pager"></div>
	</div>

	<div class="ui-layout-center">
		<div>
			<form:form id="business_manage_form" commandName="tenant">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">设置业务人</a></li>
					</ul>
					<input type="hidden" id="businessTypeUuid" name="businessTypeUuid" />
					<div id="tabs-1">
						<table>
							<privilege:access ifGranted="B001007002">
								<tr id="row_business_manager">
									<td style="width: 65px;"><label>业务负责人</label></td>
									<td><input id="businessManagerUserName"
										name="businessManagerUserName" type="text" class="full-width" />
										<input id="businessManagerUserId" name="businessManagerUserId"
										type="hidden" class="full-width" /></td>
									<td></td>
								</tr>
							</privilege:access>
							<tr id="row_business_sender" style="display: none;">
								<td style="width: 65px;"><label>业务发送人</label></td>
								<td><input id="businessSenderName"
									name="businessSenderName" type="text" class="full-width" /> <input
									id="businessSenderId" name="businessSenderId" type="hidden"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr id="row_business_receiver" style="display: none;">
								<td style="width: 65px;"><label>业务接收人</label></td>
								<td><input id="businessReceiverName"
									name="businessReceiverName" type="text" class="full-width" />
									<input id="businessReceiverId" name="businessReceiverId"
									type="hidden" class="full-width" /></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
				</div>
			</form:form>
		</div>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validate/js/jquery.validate.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/business_manage.js"></script>
</body>
</html>