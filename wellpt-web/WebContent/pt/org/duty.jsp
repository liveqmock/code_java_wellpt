<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Duty List</title>

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
					<input id="query_duty" name="query_duty" />
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
			<form:form commandName="duty" action="" id="duty_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">角色信息</a></li>
						<li><a href="#tabs-3">角色树</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<input type="hidden"
						id="externalId" name="externalId" />
						 <input type="hidden" id="id" name="id" />
					<div id="tabs-1">
						<table>
							
							<tr>
								<td><label>名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" /> </td>
								<td></td>
							</tr>
							
							<tr>
								<td><label>编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							
							<tr>
								<td><label>职级</label></td>
								<td>
									<select id="dutyLevel" name="dutyLevel" type="text" class="full-width">
									<option value ="M1">M1</option>
									<option value ="M2">M2</option>
									<option value ="M3">M3</option>
									<option value ="M4">M4</option>
									<option value ="M5">M5</option>
									<option value ="M6">M6</option>
									<option value ="M7">M7</option>
									<option value ="M8">M8</option>
									<option value ="M9">M9</option>
									<option value ="M10">M10</option>
									<option value ="M11">M11</option>
									<option value ="M12">M12</option>
									<option value ="M13">M13</option>
									<option value ="M14">M14</option>
									<option value ="M15">M15</option>
									<option value ="M16">M16</option>
									</select>
									</td>
								<td></td>
							</tr>
							
							<tr>
								<td><label>职位系列</label></td>
								<td><input id="seriesName" name="seriesName" type="text"
									class="full-width" /></td>
								<td><input type="hidden" id="seriesUuid" name="seriesUuid" /></td>
							</tr>
							
							<tr>
								<td><label>备注</label></td>
								<td><input id="remark" name="remark" type="text"
									class="full-width" /></td>
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
							<ul id="duty_role_nested_role_tree" class="ztree">
							</ul>
						</div>
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
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/duty.js"></script>
</body>
</html>