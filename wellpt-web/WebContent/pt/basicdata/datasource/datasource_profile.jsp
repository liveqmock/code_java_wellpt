<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>外部数据源配置文件</title>
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
<style type="text/css">
.fontColorCss {
	background-color: #000000;
    display: block;
    float: left;
    height: 11px;
    margin-left: 10px;
    margin-top: 12px;
    width: 11px;
}
.subcolorCss {
   background: url("${ctx}/resources/theme/images/v1_icon3.png") no-repeat scroll 0 -263px transparent;
    cursor: pointer;
    float: left;
    height: 20px;
    margin-left: 4px;
    margin-top: 8px;
    position: relative;
    width: 20px;
}

.colors {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid #DDDDDD;
    position: absolute;
    top: 18px;
    width: 58px;
}

.selectcolor {
    display: block;
    float: left;
    height: 15px;
    margin: 2px;
    width: 15px;
}

</style>
</head>
<body>
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
				<form action="" id="column_form">
					<input type="hidden" id="uuid" name="uuid">
					<input type="hidden" id="createTime" name="createTime"> 
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">连接配置</a></li>
						</ul>					
						<div id="tabs-1">
							<table style="width: 100%;">
								<tr>
									<td style="width:20%">
										外部数据源名称
									</td>
									<td >
										<input type="text" id="dataSourceProfileName" name="dataSourceProfileName" style="width:100%">
									</td>
								</tr>
								<tr>
									<td style="width:20%">
										ID
									</td>
									<td>
										<input type="text" id="dataSourceProfileId" name="dataSourceProfileId" style="width:100%">
									</td>
								</tr>
								<tr>
									<td style="width:20%">
										编号
									</td>
									<td>
										<input type="text" id="dataSourceProfileNum" name="dataSourceProfileNum" style="width:100%">
									</td>
								</tr>
								
								<tr>
									<td>
										类型
									</td>
									<td>
										<select id="outDataSourceType" name="outDataSourceType" style="width:100%;">
											<option value="0">--请选择--</option>
											<option value="1">数据库</option>
											<option value="2">XML</option>
											<option value="3">文件</option>
											<option value="4">Web Services</option>
										</select>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;" >
									<td>
										数据库类型
									</td>
									<td>
										<select id="databaseType" name="databaseType" style="width:100%;">
											<option value="1">oracle</option>
											<option value="2">sqlserver</option>
											<option value="3">mysql</option>
										</select>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										数据库服务名
									</td>
									<td>
										<input id="databaseSid" name="databaseSid" style="width:100%;"/>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										主机名
									</td>
									<td>
										<input id="host" name="host" style="width:100%;"/>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										端口
									</td>
									<td>
										<input id="port" name="port" style="width:100%;"/>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										owner
									</td>
									<td>
										<input id="owner" name="owner" style="width:100%;"/>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										用户名
									</td>
									<td>
										<input id="userName" name="userName" style="width:100%;"/>
									</td>
								</tr>
								<tr class="outDataSource" style="display:none;">
									<td>
										密码
									</td>
									<td>
										<input id="passWord" name="passWord" style="width:100%;"/>
									</td>
								</tr>
								<tr>
									<td>
										测试连接
									</td>
									<td>
										<button type="button" id="dataSourceLinkTest" name="dataSourceLinkTest">连接</button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
					</div>
				</form>
					<div>
					</div>
			</div>
		</div>
	
	<div id="dlg_choose_button"></div>
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
		src="${ctx}/resources/pt/js/common/jquery.dytableTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.popupWindow.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
		<script type="text/javascript"
	src="${ctx}/resources/pt/js/basicdata/datasource/datasource_constant.js"></script>	
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/datasource/datasource_profile.js"></script>
<!-- 	<script type="text/javascript" -->
<%-- 		src="${ctx}/resources/pt/js/basicdata/datasource/datasource_definition.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
</body>
</html>