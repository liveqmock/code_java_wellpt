<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>动态表单解释</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.datepicker.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
body,table,input,select {
	font-size: 12px
}

TABLE {
	font-size: 12px;
	border-collapse: collapse;
	border: 1px solid #888888
}

TD {
	border: 1px solid #888888
}

TD.label {
	background-color: #EEEEEE;
	color: #0066CC;
	text-align: right;
	padding-right: 8px
}
</style>

<script type="text/javascript">
	//全局变量
	var formUid = '${formUid}';
	var dataUid = '${dataUid}';
</script>

</head>
<body id="explainBody">
	<input type="hidden" id='formUuid' value='${formUuid}'>
	<input type="hidden" id='dataUuid' value='${dataUuid}'>
	<button id="save" type="button" value="保存">保存</button>
	<!-- 自定义表单 -->
	<form action="">
	<label>动态表单示例</label>
	<input type="text" id="creator" name="creator" />
	</form>
	<!-- 动态表单 -->
	<div id="abc">
	</div>
	<div>
		<input id="current_form_url_prefix" type="hidden" value="${ctx}/dytable/form_explain_index.action">
		<a id="current_form_url" href="" target="_blank" style="display: none">查看当前动态表单</a>
	</div>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
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
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/worktask/workplan.js"></script>
</body>
</html>