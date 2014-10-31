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
					<button id="btn_add" type="button" class="btn">新增</button>
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
							<li><a href="#tabs-1">基本属性</a></li>
						</ul>
						<div id="tabs-1">
							<table  style="width: 100%;">
								<tr>
									<td style="width: 90px;">名称</td>
									<td><input id="name" name="name" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td><label>ID</label></td>
									<td><input id="id" name="id" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>编号</label></td>
									<td><input id="code" name="code" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td>业务类别</td>
									<td><select id="businessTypeId" name="businessTypeId" style="width:100%;">
									</select></td>
								</tr>
								<tr>
									<td><label>上报单位</label><input id="unitId" name="unitId" type="hidden"></td>
									<td><input id="unitName" name="unitName" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>动态表单</label></td>
									<td><input id="formName" name="formName" type="text" style="width:100%;"/>
									   <input id="formId" name="formId" type="hidden"></td>
								</tr>
								<tr style="display: none;">
									<td><label>终端数据表名称</label></td>
									<td><input id="tableName" name="tableName" type="text" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>接收时限(天)</label></td>
									<td><input id="receiveLimit" name="receiveLimit" type="text" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>上报时限(天)</label></td>
									<td><input id="reportLimit" name="reportLimit" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>接口实现模块</label></td>
									<td>
									
									<input id="businessName" name="businessName" type="text" style="width:100%;"/>
									<input id="businessId" name="businessId" type="hidden" style="width:100%;"/></td>
								</tr>
								<tr>
									<td></td>
									<td>
									<input id="showToUnit" name="showToUnit" type="checkbox"/>允许输入收件单位
									<input id="retain" name="retain" type="checkbox"/>数据留存
									<input id="toSys" name="toSys" type="checkbox"/>查询系统
									<input id="synchronous" name="synchronous" type="checkbox"/>同步处理
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<div id="attach">
											<span class="fileDownLoad" style="color: blue;cursor: pointer;text-decoration: underline;"></span> (点击下载)
										</div>
										<input id="text" type="hidden">
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
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<!-- 	<script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/common/jquery.dyviewTree.js"></script> --%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/exchangedata/datatype.js"></script>
</body>
</html>