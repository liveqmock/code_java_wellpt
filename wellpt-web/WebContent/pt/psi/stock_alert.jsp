<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>物品配置单</title>
</head>
<body id="explainBody">
	<div class="viewContent">
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
<link rel="stylesheet"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
	<input type="hidden" id='formUuid' value='7a617713-4de9-48d4-a2d2-68df0acafc79'>
	<input type="hidden" id='dataUuid' value='${dataUuid}'>
	<input type="hidden" id='flag' value='1'>
	<input type="hidden" id='drId' value="20136315910846">
		<div id="abc" >
		</div>
		<div class="form_operate" style="margin-left: 300px; margin-top: 10px;">
		<button id="save" type="button" value="保存">保存</button>
		</div>
		
	
	<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript">
	$.jgrid.no_legacy_api = true;
	$.jgrid.useJSON = true;
	// 加载全局国际化资源
	I18nLoader.load("/resources/pt/js/global");
	// 加载动态表单定义模块国际化资源
	I18nLoader.load("/resources/pt/js/dytable/dytable");
</script>
<script type="text/javascript"
	src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/resources/fileupload/js/uuid.js"></script>
<script src="${ctx}/resources/form/form_body.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.numberInput.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_dialog_js.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/calendar/calendar_timeEmploy.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_explain.js"></script>
	
<script type="text/javascript"
	src="${ctx}/resources/pt/js/psi/stock_deploy.js"></script>
	</div>
</body>
</html>