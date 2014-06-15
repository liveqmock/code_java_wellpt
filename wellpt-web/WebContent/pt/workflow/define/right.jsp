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
</style>

</head>
<body>
	<div id="container">
		<div class="pane ui-layout-center">
			<table id="list"></table>
			<div id="pager"></div>
		</div>
		<div class="pane ui-layout-east">
			<form action="" id="right_form">
				<div class="btn-group">
					<button id="btn_add" type="button" class="btn">新 增</button>
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" /> <input type="hidden"
						id="id" name="id" />
					<div id="tabs-1">
						<table>
							<tr>
								<td><label>名称</label></td>
								<td><input id="name" name="name" type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input id="code" name="code" type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>控制值</label></td>
								<td><textarea id="value" name="value" /></textarea></td>
								<td></td>
							</tr>
							<tr>
								<td><label>是否默认值</label></td>
								<td><input id="isDefault" name="isDefault" type="checkbox"
									value="true" /></td>
								<td></td>
							</tr>
						</table>
					</div>
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
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/workflow/right.js"></script>
</body>
</html>