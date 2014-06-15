<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/header.jsp"%>

<body>
	<!-- topbar starts -->
	<jsp:include page="cm_top.jsp" flush="true|false" />
	<!-- topbar ends -->
	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<!-- 列表导航 -->
					<jsp:include page="cm_menu.jsp" flush="true|false" />

				</div>
				<!--/.well -->
			</div>
			<!--/span2-->
			<!-- 左侧菜单 ends -->


			<!-- 核心内容 span10-->
			<div id="content" class="span10">
				<!-- content starts -->


				<div>
					<ul class="breadcrumb">
						<li><a href="#">系统</a> <span class="divider">/</span></li>
						<li><a href="#">公文交换</a> <span class="divider">/</span></li>
						<li><a href="config">外部单位配置</a></li>
					</ul>
				</div>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> <spring:message code="exchange.label.outunit.moduleName" />
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
							<form id="search_form" class="well form-inline" method="post" action="">
							<div class="btn-group">
								<button id="btn_add" type="button" class="btn"><spring:message code="exchange.btn.new" /></button>
								<button id="btn_edit" type="button" class="btn"><spring:message code="exchange.btn.edit" /></button>
								<button id="btn_del" type="button" class="btn"><spring:message code="exchange.btn.del" /></button>
								<div class="pull-right input-append">
									<select id="query_type" name="query_type" class="input-small">
									<option value=''><spring:message code="exchange.label.classification" /></option>
									<option value='no'><spring:message code="exchange.label.outunit.no" /></option>
									<option value='name'><spring:message code="exchange.label.outunit.name" /></option>
									<option value='type'><spring:message code="exchange.label.outunit.type" /></option>
									</select>
									<input style="display:none" />
									<input type="text" id="query_key" name="query_key" value="" class="input-small" placeholder="关键词" />
									<button id="btn_query" class="btn" type="button"><spring:message code="exchange.btn.search" /></button>
								</div>
							</div>
							</form>
							<div id="table">
								<table id="outunit_list"></table>
								<div id="pager"></div>
							</div>

						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->

				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->

		<hr>
		<div class="row-fluid sortable" id="outunit_form_dialog" style="dispaly:none;">
			<div class="box span12">
				<div class="box-content">
					<form class="form-horizontal" id="outunit_form">
						<input type="hidden" id="uuid" name="uuid" />
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="no"><spring:message code="exchange.label.outunit.no" /></label>
								<div class="controls">
									<input id="no" name="no" type="text" datatype="s5-16" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="name"><spring:message code="exchange.label.outunit.name" /></label>
								<div class="controls">
									<input id="name" name="name" type="text" datatype="s5-16"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="type"><spring:message code="exchange.label.outunit.type" /></label>
								<div class="controls">
									<select id="type" name="type"></select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="email"><spring:message code="exchange.label.outunit.email" /></label>
								<div class="controls">
									<input id="email" name="email" type="text" datatype="s5-16"/>
								</div>
							</div>
						</fieldset>
					</form>

				</div>
			</div>
		</div>

		<%@ include file="/pt/common/footer.jsp"%>
	</div>
	<!--/.fluid-container-->
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<!-- Project -->	
	<script type="text/javascript">
		//动态表单变量
		var formUid = '${form_uuid}';
		var dataUid = '${data_uuid}';
	</script>
	
	<script type="text/javascript" src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/validform/js/Validform_v5.2.1_min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/validform/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/exchange/css/exchange.css" />
	<script type="text/javascript" src="${ctx}/resources/exchange/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/resources/exchange/js/outunit.js"></script>
</body>
</html>