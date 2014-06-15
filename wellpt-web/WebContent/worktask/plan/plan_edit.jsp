<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/header.jsp"%>
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
	<br/>
	<button id="save" type="button" value="保存"class="btn btn-primary">保存</button>	<button class="btn" id="closebtn">取消</button>
	 <script type="text/javascript">
	//全局变量
	var formUid = '${formUid}';
	var dataUid = '${dataUid}';
	var type1_name = '${type1.fieldName}';
	var type1_value = '${type1.fieldValue}';
	var type2_name = '${type2.fieldName}';
	var type2_value = '${type2.fieldValue}';
	var weekOfYear = '${weekofyear}';
	var userId='${userId}';
	var season='${season}';
	var year='${year}';
	var month='${month}';
	var userId='${userId}';
</script>
	<!-- 动态表单 -->
	<div id="abc"></div>
	 
	 
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
	<script src="${ctx}/resources/fileupload/js/header.js"></script>
		<script src="${ctx}/resources/fileupload/js/util.js"></script>
		<script src="${ctx}/resources/fileupload/js/button.js"></script>
		<script src="${ctx}/resources/fileupload/js/ajax.requester.js"></script>
		<script
			src="${ctx}/resources/fileupload/js/deletefile.ajax.requester.js"></script>
		<script src="${ctx}/resources/fileupload/js/handler.base.js"></script>
		<script src="${ctx}/resources/fileupload/js/window.receive.message.js"></script>
		<script src="${ctx}/resources/fileupload/js/handler.form.js"></script>
		<script src="${ctx}/resources/fileupload/js/handler.xhr.js"></script>
		<script src="${ctx}/resources/fileupload/js/uploader.basic.js"></script>
		<script src="${ctx}/resources/fileupload/js/dnd.js"></script>
		<script src="${ctx}/resources/fileupload/js/uploader.js"></script>
		<script src="${ctx}/resources/fileupload/js/jquery-plugin.js"></script>
		<script src="${ctx}/resources/fileupload/js/uuid.js"></script>
	    <script src="${ctx}/resources/fileupload/fileuploader.js"></script>

	
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.numberInput.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>
	<script type="text/javascript"
		src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
	<script type="text/javascript"
		src="${ctx}/resources/worktask/js/plan/workplan.js"></script>
</body>
</html>