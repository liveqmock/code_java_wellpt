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
		<div style="height: 40%">
			<div class="btn-group btn-group-top">
			    <input name="currentUserId" id="currentUserId" type="hidden" value="${userId }"/>
				<div class="query-fields">
					<input id="query_value" name="query_value" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<privilege:access ifGranted="B001007001">
					<button type="button" class="btn" id="btn_unit_manager_username" >设置业务单位管理员</button>
				</privilege:access>	
				
				<input id="unitName" name="unitName" type="hidden" class="full-width" />
				<input id="unitId" name="unitId" type="hidden"	class="full-width" />
			</div>
			<table id="list"></table>
			<div id="pager"></div>			
		</div>
	</div>	
	
	<div class="ui-layout-center" >	
		<iframe frameborder="0" width="100%" height="100%" name="businessUnitTree" id="businessUnitTree" 
		src="${ctx}/org/business/unit/business_unit_tree"></iframe>	
	</div>
	
	<div id="set_unit_manager_username" title="设置业务单位管理员" style="display:none">
		<form action="" id="set_unit_manager_username_form" name="set_unit_manager_username_form"  method="post">
		<table>
			<tr>
				<td><label for="commonUser">选择用户</label></td>
					<td><input id="unitManagerUserName" name="unitManagerUserName" type="text"
							class="full-width" />		
						<input id="unitManagerUserId" name="unitManagerUserId" type="hidden"
							class="full-width" />
				</td>
				<td></td>
			</tr>
		</table>
		</form>
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
		src="${ctx}/resources/pt/js/org/business_type.js"></script>
</body>
</html>