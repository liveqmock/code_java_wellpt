<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>数据源定义</title>
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
							<li><a href="#tabs-1">基本属性</a></li>
							<li><a href="#tabs-2">列定义</a></li>
						</ul>					
						<div id="tabs-1">
							<table style="width: 100%;">
								<tr>
									<td style="width:20%">
										数据源名称
									</td>
									<td >
										<input type="text" id="dataSourceName" name="dataSourceName" style="width:100%">
									</td>
								</tr>
								<tr>
									<td style="width:20%">
										ID
									</td>
									<td>
										<input type="text" id="dataSourceId" name="dataSourceId" style="width:100%">
									</td>
								</tr>
								<tr>
									<td style="width:20%">
										编号
									</td>
									<td>
										<input type="text" id="dataSourceNum" name="dataSourceNum" style="width:100%">
									</td>
								</tr>
								<tr>
									<td style="width:20%">
										类型
										<input type="hidden" id="dataSourceTypeName" name="dataSourceTypeName" />
									</td>
									<td><select id="dataSourceTypeId" name="dataSourceTypeId" style="width:100%;">
											<option value="0">--请选择--</option>
											<option value="1">内部数据源</option>
											<option value="2">外部数据源</option>
											<option value="3">数据接口</option>
										</select>
									</td>
								</tr>
								<tr id="inDataSource" style="display:none;">
									<td style="width:20%">
										内部数据源类别
									</td>
									<td>
										<select id="inDataScope" name="inDataScope" style="width:100%;">
											<option value="0">--请选择--</option>
											<option value="1">数据库表</option>
											<option value="2">实体类表</option>
											<option value="7">实体类表(带acl权限)</option>
											<option value="3">数据视图</option>
											<option value="4">sql</option>
											<option value="5">hql</option>
											<option value="6">带acl权限的hql</option>
										</select>
									</td>
								</tr>
								<tr id="role_choose"  style="display:">
									<td>选择角色</td>
									<td>
										<input type="text" class="w100" name="roleName"
												id="roleName" style="width:100%"/>
										<input type="hidden" class="w100"
												name="roleType" id="roleType" value="" />
									</td>
								</tr>
								<tr id="chooseOutDataSource" style="display:none;">
									<td>
										选择外部数据源配置
									</td>
									<td>
										<input type="text" id="outDataSourceName" name="outDataSourceName" style="width:100%;"/>
										<input type="hidden" id="outDataSourceId" name="outDataSourceId"/>
									</td>
								</tr>
								
								<tr id="chooseDataInterface" style="display:none;">
									<td>
										选择数据接口
									</td>
									<td>
										<input type="text" id="dataInterfaceName" name="dataInterfaceName" style="width:100%;"/>
										<input type="hidden" id="dataInterfaceId" name="dataInterfaceId"/>
									</td>
								</tr>
								
								<tr id="outDataSource" style="display:none;">
									<td style="width:20%">
										外部数据源类别
									</td>
									<td>
										<select id="outDataScope" name="outDataScope" style="width:100%;">
											<option value="0">--请选择--</option>
											<option value="1">数据库表</option>
											<option value="2">数据视图</option>
											<option value="3">sql</option>
										</select>
									</td>
								</tr>
								
								<tr id="chooseDataTr" class="inDataChooseClass" style="display:none;">
									<td style="width:20%">
										选择数据
									</td>
									<td>
										<input type="text" id="chooseDataText"  name="chooseDataText" style="width:100%;"/>
										<input type="hidden" id="chooseDataId"  name="chooseDataId"/>
									</td>
								</tr>
								<tr id="dataSqlorHqlTr" class="inDataChooseClass" style="display:none;">
									<td style="width:20%">
										sql/hql语句
									</td>
									<td>
										<input type="text" id="sqlOrHqlText" name="sqlOrHqlText" style="width:100%;"/>
										<button type="button" id="validateSql">验证</button>
									</td>
								</tr>
								<tr id="dataAclHqlTr" class="inDataChooseClass" style="display:none;">
									<td style="width:20%">
										带acl权限的hql语句
									</td>
									<td>
										<input type="text" id="aclHqlDataText" name="aclHqlDataText" style="width:100%;"/>
									</td>
								</tr>
								<tr id="searchConditionTR" class="inDataChooseClass" style="display:none;">
									<td style="width:20%">默认搜索条件</td>
									<td>
										<input type="text" id="searchCondition" name="searchCondition" style="width:100%;"/>
									</td>
								</tr>
							</table>
						</div>
						<div id = "tabs-2">
<!-- 							<div class="btn-group"> -->
<!-- 									<button id="btn_add_column" type="button" class="btn">新增列</button> -->
<!-- 									<button id="btn_del_column" type="button" class="btn">删除列</button> -->
<!-- 									<button id="btn_up_column" type="button" class="btn">上移列</button> -->
<!-- 									<button id="btn_down_column" type="button" class="btn">下移列</button> -->
<!-- 							</div> -->
							<table id="column_list"></table>
							<div id="btn_pager"></div>
					</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<button id="btn_save" type="button" class="btn">保存</button>
						<button id="btn_del" type="button" class="btn">删除</button>
						<button id="btn_url" type="button" class="btn" onclick="var uuid = $('#uuid').val();if(uuid==''){alert('请先保存视图信息');}else{window.open('${ctx}/basicdata/datasource/source_show?sourceUuid='+uuid+'');}">预览</button>
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
		src="${ctx}/resources/pt/js/basicdata/datasource/datasource_definition.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.popupTreeWindow.js"></script>
</body>
</html>