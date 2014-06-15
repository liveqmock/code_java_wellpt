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
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
</head>
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_tenant_normal" name="query_tenant_normal" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button id="btn_add" type="button" class="btn">新 增</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form:form id="tenant_form" commandName="tenant">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<form:hidden path="id" />
					<form:hidden path="jdbcDatabaseName" />
					<form:hidden path="jdbcUsername" />
					<form:hidden path="jdbcPassword" />
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
								<td><label>管理员账号</label></td>
								<td><input id="account" name="account" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>密码</label></td>
								<td><input id="password" name="password" type="password"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>邮件地址</label></td>
								<td><input id="email" name="email" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>数据库类型</label></td>
								<td><form:select path="databaseConfigUuid"
										items="${databaseConfigs}" itemValue="uuid" itemLabel="name"
										cssClass="full-width"></form:select></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label>备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><input id="status" name="status" type="checkbox"
									value="0"><label for="enabled">启用</label></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
					<button id="btn_check_connecton_status" type="button" class="btn">连接性测试</button>
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
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/superadmin/tenant_normal.js"></script>
</body>
</html>