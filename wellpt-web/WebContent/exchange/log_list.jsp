<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>外部单位列表</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<script type="text/javascript">
	
</script>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
			</div>
			<div class="span10">
				<h3>列表</h3>
				<form id="search_form" class="well form-inline" method="post"
					action="">
					<div class="btn-group">
						<button id="btn_add" type="button" class="btn">新增</button>
						<button id="btn_edit" type="button" class="btn">修改</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
					<div class="pull-right input-append">
						<input type="text" id="query_key" name="query_key" value="" class="input-small" placeholder="关键词" />
						<button id="btn_query" class="btn" type="button">查询</button>
					</div>
				</form>
				<div id="table">
					<table id="outunit_list"></table>
					<div id="pager"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="outunit_form_dialog" style="display: none">
		<form action="" id="outunit_form">
			<input type="hidden" id="uuid" name="uuid" />
			<table>
				<tr>
					<td><label>编号</label></td>
					<td><input id="no" name="no" type="text" /></td>
					<td></td>
				</tr>
				<tr>
					<td><label>名称</label></td>
					<td><input id="name" name="name" type="text" /></td>
					<td></td>
				</tr>
				<tr>
					<td><label>类型</label></td>
					<td><input id="type" name="type" type="text" /></td>
					<td></td>
				</tr>
				<tr>
					<td><label>Email</label></td>
					<td><input id="email" name="email" type="text" /></td>
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
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" 
		href="${ctx}/resources/exchange/css/exchange.css" />
	<script type="text/javascript"
		src="${ctx}/resources/exchange/js/common.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/exchange/js/outunit.js"></script>
</body>
</html>