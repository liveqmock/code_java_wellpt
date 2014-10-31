<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Group List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
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
					<input id="query_group" name="query_group" />
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
			<form action="" id="group_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">角色信息</a></li>
						<li><a href="#tabs-3">角色树</a></li>
	<!-- 					<li><a href="#tabs-4">权限信息</a></li>
						<li><a href="#tabs-5">权限树</a></li> -->
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" /> <input
							type="hidden" id="id" name="id" />
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
								<td class="align-top"><label>成员</label></td>
								<td><textarea id="memberNames" name="memberNames"
										class="full-width"></textarea> <input id="memberIds"
									name="memberIds" type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label>使用范围</label></td>
								<td><textarea id="rangeNames" name="rangeNames"
										class="full-width"></textarea> <input id="rangeIds"
									name="rangeIds" type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>分类</label></td>
								<td><input id=category name="category" type="text"
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
							<ul id="role_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 50px; float: left">
							<div>
								<label>已选角色</label>
							</div>
							<select id="selected_role" multiple="multiple"
								style="height: 350px; width: 200px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div id="tabs-3">
						<div>
							<ul id="group_role_nested_role_tree" class="ztree">
							</ul>
						</div>
					</div>
					
					<div id="tabs-4" style="display: none">
						<div style="float: left">
							<ul id="privilege_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 100px; float: left">
							<div>
								<label>已选权限</label>
							</div>
							<select id="selected_privilege" multiple="multiple"
								style="height: 200px; width: 250px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					
					<div id="tabs-5" style="display: none">
						<div>
							<ul id="group_privilege_tree" class="ztree">
							</ul>
						</div>
					</div>
					
				</div>
			</form>
			<div class="btn-group btn-group-bottom">
				<button id="btn_save" type="button" class="btn">保存</button>
				<button id="btn_del" type="button" class="btn">删除</button>
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
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/group.js"></script>
</body>
</html>