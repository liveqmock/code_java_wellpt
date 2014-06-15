<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>module List</title>
</head>
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
<body>
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
				<form action="" id="module_form">
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性</a></li>
						</ul>
						<div id="tabs-1">
							<table  style="width: 100%;">
								<tr>
									<td style="width: 85px;">名称</td>
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
									<td>源数据类型</td>
									<td><select id="fromTypeId" name="fromTypeId" style="width:100%;">
									</select></td>
								</tr>
								<tr>
									<td><label>约束条件</label></td>
									<td><input id="restrain" name="restrain" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>目的方式</label></td>
									<td>
										<input id="toType3" name="toType" class="toType" type="radio" value="type3" /><label for="toType3">仅由发件人指定的收件单位</label>
										<input id="toType1" name="toType" class="toType" type="radio" value="type1" /><label for="toType1">直接选择收件单位</label>
									    <input id="toType2" name="toType" class="toType" type="radio" value="type2" /><label for="toType2">表单数据项指定收件单位</label>
									</td>
								</tr>
								<tr class="toIdClass">
									<td><label>目的单位</label><input id="toId" name="toId" type="hidden"></td>
									<td><input id="toName" name="toName" type="text" style="width:100%;"/></td>
								</tr>
								<tr class="toFieldClass">
									<td><label>目的对应字段</label></td>
									<td><select id="toField" name="toField" style="width:100%;">
											<option></option>
										</select>
									</td>
								</tr>
								<tr >
									<td></td>
									<td>
										<input id="toEmpty2" name="toEmpty" class="toEmpty" type="checkbox"/><label for="toEmpty2">允许目标单位为空</label>
									</td>
								</tr>
								<tr>
									<td><label>转换规则</label><input type="hidden" id="transformId" name="transformId"></td>
									<td><input id="transformName" name="transformName" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>重发次数</label></td>
									<td><input id="retransmissionNum" name="retransmissionNum" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>间隔秒数</label></td>
									<td><input id="interval" name="interval" type="text" style="width:100%;"/></td>
								</tr>
							</table>
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
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/exchangedata/routepage.js"></script>
	
</body>
</html>