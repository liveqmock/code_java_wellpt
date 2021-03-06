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
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
</head>
<body>
		<div class="ui-layout-center">
			<div>
				<form action="" id="datadict_form">
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本信息</a></li>
						</ul>
						<div id="tabs-1">
							<input type="hidden" id="uuid" name="uuid" /> 
							<input type="hidden" id="remark" name="remark" />
							<table>
								<tr>
									<td style="width: 65px;">名称</td>
									<td><input class="full-width" id="showTitle" name="showTitle" type="text" /></td>
								</tr>
								<tr>
									<td style="width: 65px;">显示标题</td>
									<td><input class="full-width" id="title" name="title" type="text" /></td>
								</tr>
								<tr>
									<td>ID</td>
									<td><input class="full-width" id="code" name="code" type="text" /></td>
								</tr>
								<tr>
									<td>编号</td>
									<td><input class="full-width" id="ecode" name="ecode" type="text" /></td>
								</tr>
								<tr>
									<td>分类<input type="hidden" id="cateName" name="cateName" />
									</td>
									<td><select id="cateUuid" name="cateUuid" style="width:100%;"></select>
									</td>
								</tr>
								<tr>
									<td>权限资源</td>
									<td><input class="full-width" id="resources_btncode" name="resources_btncode" type="text" />
										<input class="full-width" id="resources" name="resources" type="hidden" />
									</td>
								</tr>
								<tr>
									<td>图标</td>
									<td><input class="full-width" id="icon" name="icon" type="text" /></td>
									<td></td>
								</tr>
								<tr>
									<td>目标位置</td>
									<td>
										<input class="newPage" name="newPage" type="radio" value="new"/>新页面
										<input class="newPage" name="newPage" type="radio" value="this"/>当前页面
										<input class="newPage" name="newPage" type="radio" value="windows"/>当前页面窗口
										<input class="newPage" name="newPage" type="radio" value="dialog"/>弹出窗
										<input class="newPage" name="newPage" type="radio" value="none"/>无
<!-- 									<select class="full-width" id="newPage" name="newPage" > -->
<!-- 											<option value="new">新页面</option> -->
<!-- 											<option value="this">当前页面</option> -->
<!-- 											<option value="windows">窗口</option> -->
<!-- 											<option value="dialog">弹出窗</option> -->
<!-- 											<option value="none">无</option> -->
<!-- 									</select> -->
									</td>
								</tr>
								<tr style="display: none;">
									<td>窗口id</td>
									<td><input class="full-width" id="divId" name="divId" type="text" style="width: 75%;"/>
										<input class="full-width" id="fullWindow" name="fullWindow" type="checkbox" style="width: 20px;"/>包括标题
<!-- 										<select class="full-width" id="fullWindow" name="fullWindow"><option value="no">窗口内容</option><option value="yes">整个窗口</option></select> -->
									</td>
								</tr>
								<tr>
									<td>目标类型</td>
									<td>
										<input class="openType" name="openType" type="radio" value="pageUrl"/><span>页面</span>
										<input class="openType" name="openType" type="radio" value="moduleId"/><span>页面元素</span>
										<input class="openType" name="openType" type="radio" value="inputUrl"/><span>url</span>
										<input class="openType" name="openType" type="radio" value="jsContent"/><span>自定义js</span>
<!-- 										<select class="full-width" id="openType" name="openType" > -->
<!-- 											<option value="pageUrl">页面</option> -->
<!-- 											<option value="moduleId">页面元素</option> -->
<!-- 											<option value="inputUrl">url</option> -->
<!-- 											<option value="jsContent">自定义js</option> -->
<!-- 										</select> -->
									</td>
								</tr>
								<tr style="display: none;">
									<td>页面元素</td>
									<td>
										<input class="full-width" id="moduleName" name="moduleName" type="text"/>
										<input class="full-width" id="moduleId" name="moduleId" type="hidden"/>
	<!-- 									<select id="moduleId" name="moduleId" ></select> -->
									</td>
								</tr>
								<tr style="display: none;">
									<td>url</td>
									<td><input class="full-width" id="inputUrl" name="inputUrl" type="text" /></td>
								</tr>
								<tr style="display: none;">
									<td>页面</td>
									<td><select onchange="change(this.value)" class="full-width" id="pageUrl" name="pageUrl"></select></td>
								</tr>
								<tr style="display: none;">
									<td></td>
									<td>
										<div>
										指定页面窗口
										<select id="appointWindow" name="appointWindow" style="width:20%;"></select>
										-->指定对应的页面元素
										<input id="appointPageElement" name="appointPageElement" type="text" style="width:20%;" />
										<input id="appointPageElementId" name="appointPageElementId" type="hidden" />
											<input class="addAppointWindow" type="button" value="添加" style="width:10%;"/>
										</div>
										<div class="definitionDiv" style="margin-bottom: 5px;">
											<table class="definitiontrtable">
												<tr class="definitiontitle" style="background:#F7F7F7;">
													<td>指定窗口</td>
													<td>对应页面元素</td>
													<td style="display: none;">指定窗口的ID</td>
													<td style="display: none;">对应页面元素的ID</td>
													<td>操作</td>
												</tr>
											</table>	
										</div>
									</td>
								</tr>
								<tr style="display: none;">
									<td>指定导航</td>
									<td>
										<input class="full-width" id="appointCategoryName" name="appointCategoryName" type="text"/>
										<input class="full-width" id="appointCategoryId" name="appointCategoryId" type="hidden"/>
									</td>
								</tr>
								<tr style="display: none;">
									<td>js</td>
									<td><textarea class="full-width" id="jsContent" name="jsContent"></textarea></td>
								</tr>
								<tr>
									<td></td>
									<td><input id="showNum" name="showNum" type="checkbox" />显示数量&nbsp;&nbsp;<input id="openSearch" name="openSearch" type="checkbox" />开启搜索</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="btn-group  btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
					</div>
				</form>
			</div>
		</div>
		<div class="pane ui-layout-west">
			<div class="btn-group  btn-group-top" style="margin-top: 0px;position: fixed;background-color: #fff;min-width: 200px;">
				<button id="btn_add" type="button" class="btn">新增</button>
				<button id="btn_del" type="button" class="btn">删除</button>
			</div>
			<div style="margin-top: 30px;">
				<ul id="datadict_tree" class="ztree"></ul>
			</div>
		</div>
	
<div id="dlg_choose_button"></div>
	<div id="addButton" title="按钮设置" style="display:none">
		<table>
			<tr>
				<td>基本列值设置</td>
				<td>
					<div id="columnValue_basic" >
						<select id="setColumnValue_basic" name="setColumnValue_basic">
						</select>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- Project -->
	<script type="text/javascript" 
		src="${ctx}/resources/jquery/jquery.js"></script>
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
		src="${ctx}/resources/ztree/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript" 
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/cms/cmscategory.js"></script>
</body>
</html>