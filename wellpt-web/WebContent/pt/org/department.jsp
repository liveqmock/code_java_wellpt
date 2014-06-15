<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Department List</title>

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
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_department" name="query_department" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button id="btn_add" type="button" class="btn">新 增</button>
				<!-- <button id="btn_del_all" type="button" class="btn">删除</button> -->
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form action="" id="dept_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">角色信息</a></li>
						<li><a href="#tabs-3">角色树</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" /> <input type="hidden"
						id="id" name="id" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 65px;"><label for="name">名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="shortName">简称</label></td>
								<td><input id="shortName" name="shortName" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="code">编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="parentName">上级部门</label></td>
								<td><input type="text" id="parentName" name="parentName"
									class="full-width" /> <input type="hidden" id="parentId"
									name="parentId" class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="principalLeaderNames">部门负责人</label></td>
								<td><input type="text" id="principalLeaderNames"
									name="principalLeaderNames" class="full-width" /> <input
									type="hidden" id="principalLeaderIds" name="principalLeaderIds" /></td>
							</tr>
							<tr>
								<td><label for="branchedLeaderNames">分管领导</label></td>
								<td><input type="text" id="branchedLeaderNames"
									name="branchedLeaderNames" class="full-width" /> <input
									type="hidden" id="branchedLeaderIds" name="branchedLeaderIds" /></td>
							</tr>
							<tr>
								<td><label for="managerNames">管理员</label></td>
								<td><input type="text" id="managerNames"
									name="managerNames" class="full-width" /> <input type="hidden"
									id="managerIds" name="managerIds" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label for="remark">备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="commonUnitId">挂接单位</label></td>
								<td><select id="commonUnitId" name="commonUnitId" class="full-width">
										<option></option>										
									</select></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><input id="isVisible" name="isVisible" type="checkbox" 
						><label for="isVisible">允许此部门在集团通讯录中显示</label></td>
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
								style="height: 300px; width: 200px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					<div id="tabs-3">
						<div>
							<ul id="department_role_nested_role_tree" class="ztree">
							</ul>
						</div>
					</div>
				</div>
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
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/department.js"></script>
</body>
</html>