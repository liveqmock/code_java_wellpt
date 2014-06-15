<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Group List</title>

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
		<div class="ui-layout-center">
			<div>
				<form action="" id="datadict_form">
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本信息</a></li>
							<li><a href="#tabs-2">字典列表</a></li>
							<li><a href="#tabs-3">其他属性</a></li>
						</ul>
						<div id="tabs-1">
							<input type="hidden" id="uuid" name="uuid" /> <input
								type="hidden" id="id" name="id" /> <input type="hidden"
								id="parentUuid" name="parentUuid" /> <input type="hidden"
								id="parentName" name="parentName" />
							<table>
								<tr>
									<td style="width: 65px;"><label>名称</label></td>
									<td><input class="full-width"  id="name" name="name" type="text" /></td>
									<td></td>
								</tr>
								<tr>
									<td><label>代码</label></td>
									<td><input class="full-width"  id="code" name="code" type="text" /></td>
									<td></td>
								</tr>
								<tr>
									<td><label>类型</label></td>
									<td><input class="full-width"  id="type" name="type" type="text" /></td>
									<td></td>
								</tr>
								<tr>
									<td class="align-top"><label>所有者</label></td>
									<td>
										<textarea class="full-width" id="ownerNames" name="ownerNames"></textarea>
										<input id="ownerIds" name="ownerIds" type="hidden" /></td>
									<td></td>
								</tr>
							</table>
						</div>
					
						<div id="tabs-2">
							<div class="btn-group">
								<button id="btn_datadict_add" type="button" class="btn">新增</button>
								<button id="btn_datadict_del" type="button" class="btn">删除</button>
							</div>
							<table id="child_datadict_list"></table>
						</div>
						
						<div id="tabs-3">
							<div class="btn-group">
								<button id="btn_attr_add" type="button" class="btn">新增</button>
								<button id="btn_attr_del" type="button" class="btn">删除</button>
							</div>
							<table id="datadict_attribute_list"></table>
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
				</form>
			</div>
		</div>
		<div class="ui-layout-west">
			<div>
				<div class="btn-group btn-group-top">
					<button id="btn_add" type="button" class="btn">新增</button>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<ul id="datadict_tree" class="ztree"></ul>
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
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/datadict/datadict.js"></script>
</body>
</html>