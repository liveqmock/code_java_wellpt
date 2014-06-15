<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>excel导入规则</title>

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
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
</head>
<body>
		<div class="ui-layout-west">
			<div>
				<div class="btn-group  btn-group-top">
					<div class="query-fields">
						<input id="query_keyWord"/>
						<button id="btn_query" type="button" class="btn">查询</button>
					</div>
					<button id="btn_add" type="button" class="btn">新 增</button>
					<button id="btn_delAll" type="button" class="btn">删除</button>
				</div>
				<table id="list"></table>
				<div id="pager"></div>
			</div>
		</div>
		<div class="ui-layout-center">
			<div>
				<form action="" id="excel_import_rule_form" name="excel_import_rule_form" enctype='multipart/form-data'>
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">列定义</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<input type="hidden" id="createTime" name="createTime" />
						<table>
							<tr>
								<td style="width: 65px;"><label>名称</label></td>
								<td><input class="full-width" id="name" name="name" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input class="full-width" id="code" name="code" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>ID</label></td>
								<td><input class="full-width" id="id" name="id" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>开始行</label></td>
								<td><input class="full-width" id="startRow" name="startRow" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>返回类型</label></td>
								<td>
									<select class="full-width" id="type" name="type" datatype="*" nullmsg="必填项">
									<option value="entity">实体</option>
									<option value="formdefinition">动态表单</option>
									</select>
								</td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>实体</label></td>
								<td>
									<select class="full-width" id="entity" name="entity" datatype="*" nullmsg="必填项">
									</select>
								</td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>模版</label></td>
								<td>
									<input type="file" id="upload" name="upload"/><input style="display: none;" type="button" id="uploadBtn" value="上传"/>
								    <input type="hidden" name="fileUuid" id="fileUuid"/>
								</td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr class="trfileName" style="display: none;">
								<td></td>
								<td>
									<span class="fileName" style="color: blue;cursor: pointer;text-decoration: underline;"></span>
								</td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
						</table>
					</div>
					<div id="tabs-2">
						<div class="btn-group">
							<button id="excel_btn_add" type="button" class="btn">新 增</button>
							<button id="excel_btn_del" type="button" class="btn">删除</button>
						</div>
						<table id="excel_list"></table>
						<div id="btn_pager"></div>
					</div>
				</div>
				<div class="btn-group  btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form>
		</div>
	</div>

	<div id="columnValue" title="列值设置" style="display:none">
		<table>
			<tr>
				<td><label>基本列值设置</label></td>
				<td>
					<div id="columnValue_basic" >
						<select id="setColumnValue_basic" name="setColumnValue_basic">
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<script type="text/javascript" src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/excelimportrule/excelimportrule.js"></script>
</body>
</html>