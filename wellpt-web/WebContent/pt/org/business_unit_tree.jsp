<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Tenant List</title>

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
				<button id="btn_unit_add" type="button" class="btn">新增单位</button>
				<button id="btn_unit_type" type="button" class="btn">新增分类</button>	
			</div>
			<ul id="business_unit_tree" class="ztree"></ul>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form:form id="business_unit_tree_form" commandName="tenant">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="businessTypeUuid" name="businessTypeUuid" />
					<div id="tabs-1">	
						<table>
							<tr id="row_parent" style="display: none;">
								<td><label>父单位</label></td>
								<td><input id="parentName" name="parentName" type="text" disabled="disabled"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr id="row_name" style="display: none;">
								<td style="width: 65px;">				
								<input type="hidden" id="type" name="type" />	
								<input type="hidden" id="uuid" name="uuid" /> 
								<input type="hidden" id="parentUuid" name="parentUuid" />
								<label>单位名称</label></td>
								<td><input id="unitName" name="unitName" type="text"
									class="full-width" />
									<input id="unitId" name="unitId" type="hidden"
									class="full-width" />
									</td>
								<td></td>
							</tr>
							<tr id="row_code" style="display: none;">
								<td><label>编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>							
						</table>
					</div>					
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form:form>
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
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validate/js/jquery.validate.js"></script>
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
		src="${ctx}/resources/pt/js/org/business_unit_tree.js"></script>
</body>
</html>