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
<link rel="stylesheet" type="text/css" 
	href="${ctx}/resources/theme/css/wellnewoa.css" />
<link rel="stylesheet" type="text/css" 
	href="${ctx}/resources/pt/css/form.css" />
</head>
<body>
				
			    <div >
			    	<table>
					<tr>
						<td>可编辑：</td>
						<td><input type="text" name="iseditor" id="iseditor" ></td>
						<td><input type="button" name="btn_dialog"  id="btn_dialog" value="弹出框2"/></td>
					</tr>
				</table>
				</div> 

	<!-- Project -->
	<script type="text/javascript" 
		src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript"	
		src="${ctx}/resources/pt/js/basicdata/serialnumber/serialform.js"></script>
	<script type="text/javascript"	
		src="${ctx}/resources/pt/js/basicdata/serialnumber/serial.js"></script>
		
</body>
</html>