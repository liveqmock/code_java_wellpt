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
	<!-- 		<div class="pane ui-layout-north"> -->
	<!-- 			<div class="btn-group"> -->
	<!-- 				<button type="button" class="btn" id="btn_add">新 增</button> -->
	<!-- 				<button type="button" class="btn" id="btn_del1">删 除</button> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_flowDefinition" name="query_flowDefinition" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<div style="height: 2.6em;"></div>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form action="" id="flow_develop_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 65px;"><label for="name">名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" readonly="readonly" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="id">ID</label></td>
								<td><input id="id" name="id" type="text" class="full-width"
									readonly="readonly" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="code">编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" readonly="readonly" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label for="customJsUrl">自定义JS文件</label></td>
								<td><textarea id="customJsUrl" name="customJsUrl" rows="5"
										class="full-width"></textarea>
									二次开发的JavaScript脚本文件，多个以分号隔开。如"/resources/app/js/pm/pm_workflow.js"</td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
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
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/workflow/develop.js"></script>
</body>
</html>