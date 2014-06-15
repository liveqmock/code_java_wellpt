<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/header.jsp"%>

<body>
	
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i> <spring:message code="exchange.label.outbox.moduleName" />
				</h2>
				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round"><i
						class="icon-cog"></i></a> <a href="#"
						class="btn btn-minimize btn-round"><i
						class="icon-chevron-up"></i></a> <a href="#"
						class="btn btn-close btn-round"><i class="icon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">

				<form id="search_form" class="well form-inline" method="post"
					action="">
					<div class="btn-group">
						<button id="btn_add" type="button" class="btn"><spring:message code="exchange.btn.new" /></button>
						<button id="btn_del" type="button" class="btn"><spring:message code="exchange.btn.del" /></button>
					</div>
				</form>
				<div id="table">
					<table id="outbox_list"></table>
					<div id="pager"></div>
				</div>

			</div>
		</div>
		<!--/span-->

	</div>
	<%@ include file="/exchange/cm_form.jsp"%>

	<!-- Project -->
	<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<!-- 动态表单 -->
	<script type="text/javascript">
		//动态表单变量
		var formUid = '${form_uuid}';
		var dataUid = '${data_uuid}';
	</script>
	<link rel="stylesheet" type="text/css" media="screen"
		href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/resources/validform/css/style.css" />
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
		type="text/css" />
	<!-- 动态表单 开始-->
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.fileuploader.js"></script>
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>
	<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
	<script type="text/javascript" src="${ctx}/resources/fileupload/fileuploader.js"></script>
	<script type="text/javascript" src="${ctx}/resources/fileupload/uuid.js"></script>	
	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_zh_CN.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_definition.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.numberInput.js"></script>
	<!-- 动态表单结束 -->
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<link rel="stylesheet" type="text/css" 
		href="${ctx}/resources/exchange/css/exchange.css" />
	<script type="text/javascript"
		src="${ctx}/resources/exchange/js/common.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/exchange/js/outbox.js"></script>
</body>
</html>