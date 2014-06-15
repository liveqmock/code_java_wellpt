<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>cms page list</title>

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
		<div class="ui-layout-west">
			<div>
				<div class="btn-group  btn-group-top">
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
				<form action="" id="page_form">
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs">
						<ul>
							<li><a href="#tabs-1">基本属性</a></li>
						</ul>	
						<div id="tabs-1">
							<table style="width: 100%;">
								<tr>
									<td style="width: 65px;">名称</td>
									<td><input id="name" name="name" type="text" style="width:100%;"/></td>
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
									<td>使用者</td>
									<td><input id="userNames" name="userNames" type="text" style="width:100%;"/>
										<input id="users" name="users" type="hidden" /></td>
								</tr>
								<tr>
									<td>样式主题</td>
									<td><select id="cssContent" name="cssContent" style="width:100%;"></select></td>
								</tr>
								<tr>
									<td style="width: 65px;">标题</td>
									<td><input id="title" name="title" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td>页面类型</td>
									<td>
										<input type="checkbox" value="index" id="pageType" name="pageType"/>主页
										<input type="checkbox" value="default" id="type"  name="type" />
										默认(给未定义首页的人员使用)
									</select>
									</td>
								</tr>
<!-- 								<tr> -->
<!-- 									<td>页面类型</td> -->
<!-- 									<td><select id="pageType" name="pageType" style="width:100%;"> -->
<!-- 											<option value="index">主页</option> -->
<!-- 											<option value="category">二级页</option> -->
<!-- 											<option value="detail">详情页</option> -->
<!-- 											<option value="other">其他</option> -->
<!-- 									</select></td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td>类型</td> -->
<!-- 									<td> -->
<!-- 										<select id="type" name="type" style="width:100%;"> -->
<!-- 											<option value="common">普通页面</option> -->
<!-- 											<option value="default">默认页面</option> -->
<!-- 										</select> -->
<!-- 									</td> -->
<!-- 								</tr> -->
							</table>
						</div>
					</div>
					<div class="btn-group  btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
						<button id="btn_config" type="button" class="btn">配置</button>
						<button id="btn_preview" type="button" class="btn">预览</button>
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
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js "></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/cms/cmspage.js"></script>
</body>
</html>