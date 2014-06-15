<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>进销存模型定义</title>
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
						<input id="query_keyWord"/>
						<button id="btn_query" type="button" class="btn">查询</button>
					</div>
					<button id="btn_add" type="button" class="btn">新增</button>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<table id="list"></table>
				<div id="pager"></div>
			</div>
	</div>
	<div class="ui-layout-center">
			<div>
				<form action="" id="column_form">
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性</a></li>
						</ul>					
						<div id="tabs-1">
							<table style="width: 100%;">
								<tr>
									<td>
										库存名称
									</td>
									<td >
										<input type="text" id="stockName" name="stockName" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										ID
									</td>
									<td>
										<input type="text" id="stockId" name="stockId" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										编号
									</td>
									<td>
										<input type="text" id="stockNumber" name="stockNumber" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>库存类型<input type="hidden" id="cateName" name="cateName" />
									</td>
									<td><select id="stockType" name="stockType" style="width:100%;"></select>
									</td>
								</tr>
								<tr>
									<td>
										来源表单
									</td>
									<td>
									<input type="text" class="w100" name="fromDytable"
										id="fromDytable" value="" style="width:100%"/>
									<input type="hidden" class="w100" name="fromDytableId"
										id="fromDytableId" value="" style="width:100%"/>
									</td>
								</tr>
								<tr>
									<td>
										库存周期
									</td>
									<td>
										<input type="text" id="stockCircle" name="stockCircle" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										库存数量
									</td>
									<td>
										<input type="text" id="balance" name="balance" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										库存上限数量
									</td>
									<td>
										<input type="text" id="toplimit" name="toplimit" style="width:100%">
									</td>
								</tr>
								<tr>
									<td>
										库存下限数量
									</td>
									<td>
										<input type="text" id="lowlimit" name="lowlimit" style="width:100%">
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
				</form>
					<div>
					</div>
			</div>
		</div>
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
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
		<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/psi/stock_definition.js"></script>
</body>
</html>