<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Resource List</title>

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
	<div class="ui-layout-center">
		<div>
			<form action="" id="resource_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">按钮</a></li>
						<li><a href="#tabs-3">方法</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" /> <input type="hidden"
						id="type" name="type" value="MENU" /> <input type="hidden"
						id="parentName" name="parentName" /> <input type="hidden"
						id="parentUuid" name="parentUuid" />
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
								<td><label>URL</label></td>
								<td><input id="url" name="url" type="text"
									class="full-width"></td>
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
						<div class="btn-group">
							<button id="btn_add_btn" type="button" class="btn">新增按钮</button>
							<button id="btn_del_btn" type="button" class="btn">删除按钮</button>
						</div>
						<table id="btn_list"></table>
						<div id="btn_pager"></div>
					</div>
					<div id="tabs-3">
						<div class="btn-group">
							<button id="btn_add_method" type="button" class="btn">新增方法</button>
							<button id="btn_del_method" type="button" class="btn">删除方法</button>
						</div>
						<table id="method_list"></table>
						<div id="method_pager"></div>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form>
		</div>
	</div>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<button id="btn_add" type="button" class="btn">新增</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<ul id="tree" class="ztree"></ul>
		</div>
	</div>
	<div id="dlg_cell"></div>

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
		src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/security/resource.js"></script>
</body>
</html>