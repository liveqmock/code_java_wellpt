<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Role List</title>

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
	<div class="pane ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_role" name="query_role" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button id="btn_add" type="button" class="btn">新 增</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="pane ui-layout-center">
		<div>
			<form action="" id="role_form">
				<div id="role_basic" class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">角色/权限</a></li>
						<li><a href="#tabs-3">角色树</a></li>
						<li><a href="#tabs-4">人员/部门/群组</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table>
							<tr>
								<td style="width: 65px;"><label>名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>ID</label></td>
								<td><input id="id" name="id" type="text"
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
								<td class="align-top"><label>备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-2">
						<div style="float: left">
							<ul id="role_privilege_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 100px; float: left">
							<div>
								<label>已选角色/权限</label>
							</div>
							<select id="selected_role_privilege" multiple="multiple"
								style="height: 200px; width: 250px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div id="tabs-3">
						<div>
							<ul id="role_nested_role_tree" class="ztree">
							</ul>
						</div>
					</div>
					<div id="tabs-4">
						<table id="role_owner_list"></table>
						<div id="role_owner_list"></div>
					</div>
				</div>
				<!-- 				<div id="role_privilege" class="tabs" style="height: 100%"> -->
				<!-- 					<ul> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form>
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
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/security/role.js"></script>
</body>
</html>