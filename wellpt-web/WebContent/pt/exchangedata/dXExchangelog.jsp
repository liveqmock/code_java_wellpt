<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>module List</title>
</head>

<body>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />

		<div class="ui-layout-west">
			<div>
				<div class="btn-group btn-group-top">
					<div class="query-fields">
						<input id="query_keyWord"/>
						<button id="btn_query" type="button" class="btn">查询</button>
					</div>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="ui-layout-center">
			<div>
				<form action="" id="module_form">
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性1</a></li>
						</ul>
						<div id="tabs-1">
							<table  style="width: 100%;">
								<tr>
									<td><label>批次号</label></td>
									<td><input id="batchId" name="batchId" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>源单位</label></td>
									<td><input id="fromUnitId" name="fromUnitId" type="text" style="width:100%;"/></td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td><label>目的单位</label></td> -->
<!-- 									<td><input id="toUnitId" name="toUnitId" type="text" style="width:100%;"/></td> -->
<!-- 								</tr> -->
								<tr>
									<td style="width: 80px;">创建时间</td>
									<td><input id="createTime" name="createTime" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td style="width: 80px;">说明</td>
									<td><input id="msg" name="msg" type="text" style="width:100%;"/>
									</td>
								</tr>
							</table>
						</div>
	
					</div>
					<div class="btn-group btn-group-bottom">
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
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/exchangedata/dXExchangelog.js "></script>
	
</body>
</html>