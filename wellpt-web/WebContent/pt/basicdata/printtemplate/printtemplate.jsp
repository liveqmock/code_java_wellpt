<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>打印模板</title>

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
				<div class="btn-group">
					<div style="display: none">
						<iframe  name="download_print_template_form_iframe" style="width: 1px; height: 0px;" ></iframe>
					</div>
					<form id="print_form" action="${ctx }/basicdata/printtemplate/download.action" method="post" target="download_print_template_form_iframe">
						<input type="hidden" id="downloaduuid" name="downloaduuid" value="" />
					</form>
				</div>
				<form action="" id="print_template_form" class="print_template_form" enctype='multipart/form-data'>
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table>
							<tr>
								<td style="width: 105px;"><label>分类</label></td>
								<td><input class="full-width" id="type" name="type" type="text" class="inputxt" datatype="*" nullmsg="必填项"/></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>名称</label></td>
								<td><input class="full-width" id="name" name="name" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>ID</label></td>
								<td><input class="full-width" id="id" name="id" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input class="full-width" id="code" name="code" type="text" datatype="*" nullmsg="必填项" /></td>
								<td><div class="Validform_checktip"></div></td>
							</tr>
							<tr>
								<td class="align-top"><label>使用人</label></td>
								<td>
									<textarea class="full-width" id="ownerNames" name="ownerNames"></textarea>
									<input id="ownerIds" name="ownerIds" type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<!-- 类型(word、html) -->
								<td><label>模板类型</label></td>
								<td><input id="template_type_word" name="templateType" type="radio" value="wordType" checked="checked"/><label
									for="template_type_word">Word模板</label> <input id="template_type_html"
									name="templateType" type="radio" value="htmlType" /><label for="template_type_html">HTML模板</label></td>
								<td></td>
							</tr>
							
							<tr>
								<td><label>多次套打结果间隔</label></td>
								<td><input id="print_interval_paging" name="printInterval" type="radio" value="paging" checked="checked"/><label
									for="print_interval_paging" >分页</label> <input id="print_interval_multi_line"
									name="printInterval" type="radio" value="multi_line" /><label for="print_interval_multi_line">多行</label></td>
								<td></td>
							</tr>
							<tr id="printInterval" style="display:none">
								<td><label>行数</label></td>
								<td><input id="rowNumber" name="rowNumber" type="text"  /></td>
								<td></td>
							</tr>
							<tr>
								<td>其他选项</td>
								<td>
									<input id="isSaveTrace" name="isSaveTrace"
									type="checkbox" value="" /><label>保留正文修改痕迹</label>
									<input id="isReadOnly" name="isReadOnly"
									type="checkbox" value="" /><label>只读标志</label>
									<input id="isSavePrintRecord" name="isSavePrintRecord"
									type="checkbox" value="" /><label>保存打印记录</label>
									<input id="isSaveSource" name="isSaveSource"
									type="checkbox" value="" /><label>保存到源文档</label>
								</td>
								<td></td>
							</tr>
							
							<tr id="fileName" style="display:none">
								<td><label>文件名格式</label></td>
								<td><input id="fileNameFormat" name="fileNameFormat" type="text"  /></td>
								<td></td>
							</tr>
							<tr id="fileName" >
								<td><label>上传模板文件</label></td>
								<td>
									<input type="file" name="uploadFile" id="uploadFile" value="上传" />
								</td>
								<td></td>
							</tr>
							<tr class="trfileName" >
								<td></td>
								<td>
									<span id="btn_download" class="fileName" style="color: blue;cursor: pointer;text-decoration: underline;"></span>
								</td>
								<td><div class="Validform_checktip"></div></td>
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
	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/printtemplate/printtemplate.js"></script>
</body>
</html>