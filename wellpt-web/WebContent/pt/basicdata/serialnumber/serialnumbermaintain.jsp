<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>流水号维护</title>
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
</head>
<body>
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
				<form action="" id="serial_number_form">
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本信息</a></li>
						</ul>
						<div id="tabs-1">
							<input type="hidden" id="uuid" name="uuid" />
							<table>
								<tr>
									<td style="width: 65px;"><label>名称</label></td>
									<td><input class="full-width" id="name" name="name" type="text" readonly/></td>
									<td></td>
								</tr>
								<tr>
									<td><label>ID</label></td>
									<td><input class="full-width" id="id" name="id" type="text"  readonly/></td>
									<td></td>
								</tr>
								<tr>
									<td><label>编号</label></td>
									<td><input class="full-width" id="code" name="code" type="text" /></td>
									<td></td>
								</tr>
								<tr>
									<td><label>关键部分</label></td>
									<td><input class="full-width" id="keyPart" name="keyPart" type="text"   /></td>
									<td></td>
								</tr>
								<tr>
									<td><label>头部</label></td>
									<td><input class="full-width" id="headPart" name="headPart" type="text"  readonly/></td>
									<td></td>
								</tr>
								<tr>
									<td><label>尾部</label></td>
									<td><input class="full-width" id="lastPart" name="lastPart" type="text"  readonly/></td>
									<td></td>
								</tr>
								<tr>
									<td><label>指针</label></td>
									<td><input class="full-width" id="pointer" name="pointer" type="text"  /></td>
									<td></td>
								</tr>
							</table>
						</div>
					</div> 
				
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
				</form>
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
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>

	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/serialnumber/serialnumbermaintain.js"></script>
</body>
</html>