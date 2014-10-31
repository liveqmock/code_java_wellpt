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
						<div style="float: left;margin-top: 3px;width: 85px;">
							<select id="select_query" name="select_query" onchange="selectQuery(this)" style="width:100%;"></select>
						</div>
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
									<td style="width: 65px;">名称</td>
									<td><input id="name" name="name" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td><label>ID</label></td>
									<td><input id="moduleId" name="moduleId" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>编号</label></td>
									<td><input id="code" name="code" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>标题</label></td>
									<td><input id="title" name="title" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td>分类<input type="hidden" id="cateName" name="cateName" />
									</td>
									<td><select id="cateUuid" name="cateUuid" style="width:100%;"></select>
									</td>
								</tr>
	
								<tr>
									<td>元素类型</td>
									<td><select id="isEdit" name="isEdit" style="width:100%;">
											<option value="false">视图元素</option>
											<option value="newview">新版视图元素</option>
											<option value="true">静态元素</option>
											<option value="category">导航元素</option>
											<option value="other">url元素</option>
									</select></td>
								</tr>
								<tr class="pathClass">
									<td>视图</td>
									<td>
<!-- 									<select id="path" name="path" style="width:100%;"/></select> -->
									<input id="viewName" name="viewName" type="text" style="width:100%;"/>
									<input id="path" name="path" type="hidden"/>
									</td>
								</tr>
								<tr class="urlClass" style="display: none;">
									<td>url</td>
									<td><input id="customPath" value="" style="width:100%;"/>
									</td>
								</tr>
								<tr class="cateEffectClass" style="display:none;">
									<td>导航效果</td>
									<td>
										<input id="categoryEffect" name="categoryEffect" type="text" style="width:100%;"/>
										<input id="categoryEffectValue" name="categoryEffectValue" type="hidden" style="width:100%;"/>
									</td>
								</tr>
								<tr class="cateClass" style="display:none;">
									<td>导航</td>
									<td>
										<input id="categoryName" name="categoryName" type="text" style="width:100%;"/>
										<input id="categoryPath" name="categoryPath" type="hidden"/>
									</td>
								</tr>
								<tr>
									<td>宽度</td>
									<td><input id="defaultWidth" name="defaultWidth" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>高度</td>
									<td><input id="defaultHeight" name="defaultHeight" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>更多</td>
									<td><input id="moreUrl" name="moreUrl" type="text" style="width:100%;"/>
									</td>
									<td></td>
								</tr>
								<tr>
									<td>js</td>
									<td><input id="jsDir" name="jsDir" type="text" style="width:100%;"/>
									</td>
								</tr>
								<tr class="htmlContentClass" style="display: none;">
									<td>页面内容</td>
									<td><textarea id="htmlContent" name="htmlContent" class="ckeditor" style="width:100%;"></textarea></td>
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
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.ckeditor.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<!-- 	<script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/common/jquery.dyviewTree.js"></script> --%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/cms/module.js"></script>
	<script type="text/javascript" src="${ctx}/resources/utils/ajaxfileupload.js"></script>
	
</body>
</html>