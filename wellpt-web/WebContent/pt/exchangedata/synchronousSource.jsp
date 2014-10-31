<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>module List</title>
</head>

<body>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />

		<div class="ui-layout-west">
			<div>
				<div class="btn-group btn-group-top">
					<div class="query-fields">
						<input id="query_keyWord"/>
						<button id="btn_query" type="button" class="btn">查询</button>
					</div>
					<button id="btn_add" type="button" class="btn">新增</button>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="ui-layout-center">
			<div>
				<form action="" id="data_form">
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性</a></li>
							<li><a href="#tabs-2">字段定义</a></li>
						</ul>
						<div id="tabs-1">
							<table  style="width: 100%;">
								<tr>
									<td style="width: 90px;">名称</td>
									<td><input id="name" name="name" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td><label>ID</label></td>
									<td><input id="id" name="id" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>编号</label></td>
									<td><input id="code" name="code" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td>数据类型</td>
									<td><select id="type" name="type" style="width:100%;">
									<option value="table">动态表单</option>
									<option value="entity">实体表</option>
									<option value="other">其他</option>
									</select></td>
								</tr>
								<tr>
									<td><label>表中文名 </label></td>
									<td><input id="tableCnName" name="tableCnName" type="text" style="width:100%;"/>
									    <input id="definitionUuid" name="definitionUuid" type="hidden">
									</td>
								</tr>
								<tr class="tableEnNameClass" style="display: none;">
									<td><label>表英文名 </label></td>
									<td>
									    <input id="tableEnName" name="tableEnName" type="text" style="width:100%;"></td>
								</tr>
								<tr>
									<td><label>joinTable </label></td>
									<td><input id="joinTable" name="joinTable" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>关联表条件 </label></td>
									<td><input id="whereStr" name="whereStr" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>触发方法 </label></td>
									<td><input id="method" name="method" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>相关关系表ID </label></td>
									<td><textarea  class="full-width" id="relationTable" name="relationTable" type="text" ></textarea></td>
								</tr>
								<tr>
									<td><label>同步租户 </label></td>
									<td><input id="tenant" name="tenant" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label> </label></td>
									<td><input id="isUserUse" name="isUserUse" type="checkbox"/>用户可选&nbsp;
									<input id="isEnable" name="isEnable" type="checkbox"/>启用
									<input id="isRelationTable" name="isRelationTable" type="checkbox"/>关系表</td>
								</tr>
							</table>
						</div>
						<div id = "tabs-2">
							<div class="btn-group">
									<button id="btn_add_column" type="button" class="btn">新增列</button>
									<button id="btn_del_column" type="button" class="btn">删除列</button>
							</div>
							<table id="column_list"></table>
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
				</form>		
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
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/exchangedata/synchronousSource.js"></script>
</body>
</html>