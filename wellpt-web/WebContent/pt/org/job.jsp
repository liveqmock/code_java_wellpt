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
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<div class="query-fields">
				<input id="query_job" name="query_job"
						size="12" /> 
					<button id="btn_query" type="button" class="btn">查询</button>
					<input id="deptUuid" name="deptUuid" type="hidden">
				</div>
				<privilege:button authority="B001009001" id="btn_add" class="btn">新 增</privilege:button>
				<privilege:button authority="B001009003" id="btn_del_all"
					class="btn">删 除</privilege:button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form action="" id="job_form" name="job_form">
				<div class="tabs">
					<ul class="ul-head">
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">角色信息</a></li>
						<li><a href="#tabs-3">角色树</a></li>
					<!-- 	<li><a href="#tabs-4">权限信息</a></li> -->
					<!-- 	<li><a href="#tabs-5">权限树</a></li> -->
					</ul>
					<input type="hidden" id="uuid" name="uuid" /> <input type="hidden"
						id="id" name="id" />
						<input type="hidden"
						id="externalId" name="externalId" />
					<div id="tabs-1">
						<table>
							<tr>
								<td><label for="name">名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width"></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label for="code">编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
						
							<tr>
								<td><label for="dutyName">职务</label></td>
								<td><input id="dutyName" name="dutyName" type="text"
									class="full-width" /></td>
								<td><input type="hidden" id="dutyUuid" name="dutyUuid" /></td>
							</tr>
							<tr>
								<td><label for="functionNames">职能线</label></td>
								<td><input id="functionNames" name="functionNames" type="text"
									class="full-width" /></td>
								<td><input type="hidden" id="functionUuids" name="functionUuids" /></td>
							</tr>
							<tr>
								<td><label for="leaderNames">汇报对象</label></td>
								<td><input type="text" id="leaderNames" name="leaderNames"
									class="full-width" /></td>
								<td><input type="hidden" id="leaderIds" name="leaderIds" /></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label for="departmentName">所属部门</label></td>
								<td><input type="text" id="departmentName"
									name="departmentName" class="full-width" /></td>
								<td><input type="hidden" id="departmentId"
									name="departmentId" />
									<input type="hidden" id="departmentUuid"
									name="departmentUuid" /></td>
							</tr>
							
							<tr>
								<td class="align-top"><label for="remark">备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
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
							<ul id="job_role_nested_role_tree" class="ztree">
							</ul>
						</div>
					</div>
					
					<div id="tabs-4" style="display: none">
						<div style="float: left">
							<ul id="privilege_tree" class="ztree">
							</ul>
						</div>
						<div style="width: 200px; margin-left: 100px; float: left">
							<div>
								<label>已选权限</label>
							</div>
							<select id="selected_privilege" multiple="multiple"
								style="height: 200px; width: 200px;">
							</select>
						</div>
						<div style="clear: both;"></div>
					</div>
					
					<div id="tabs-5" style="display: none">
						<div>
							<ul id="job_privilege_tree" class="ztree">
							</ul>
						</div>
					</div>
					
					
				</div>
				<div class="btn-group btn-group-bottom">
					<privilege:button authority="B001009002" id="btn_save" class="btn">保存</privilege:button>
					<privilege:button authority="B001009003" id="btn_del" class="btn">删除</privilege:button>
					<!--<privilege:dynamicButton authority="001" cssClass="btn" />-->
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
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/job.js"></script>

	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	


</body>
</html>