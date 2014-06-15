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
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
</head>
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_database_config" name="query_database_config" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button type="button" class="btn" id="btn_add">新 增</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form:form id="database_config_form" commandName="databaseConfig">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 65px;"><label>名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>数据库类型</label></td>
								<td><form:select path="type" items="${databaseTypes}"
										cssClass="input-xlarge focused full-width"></form:select></td>
								<td></td>
							</tr>
							<tr>
								<td><label>地址</label></td>
								<td><input id="host" name="host" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>端口</label></td>
								<td><input id="port" name="port" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>数据库</label></td>
								<td><input id="databaseName" name="databaseName"
									type="text" class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>登录名</label></td>
								<td><input id="loginName" name="loginName" type="text"
									class="full-width"></input></td>
								<td></td>
							</tr>
							<tr>
								<td><label>密码</label></td>
								<td><input id="password" name="password" type="password"
									class="full-width"></input></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			</form:form>
			<div class="btn-group btn-group-bottom">
				<button id="btn_save" type="button" class="btn">保存</button>
				<button id="btn_del" type="button" class="btn">删除</button>
				<button id="btn_check_connecton_status" type="button" class="btn">连接性测试</button>
			</div>
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
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/superadmin/database_config.js"></script>
</body>
</html>