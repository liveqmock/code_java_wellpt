<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>系统表结构</title>

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
<style>
.info {
	background-color: #FFFFFF;
    border: 1px solid #CCCCCC;
    color: #fff;
    display: none;
    line-height: 20px;
    padding: 2px 20px 2px 5px;
    position: absolute;
    z-index: 99;
    margin-left: 2px;
    margin-top: 5px;
}


.dec .dec1 {
    color: #CCCCCC;
}

.dec s {
    font-family: simsun;
    font-size: 16px;
    height: 19px;
    left: 7px;
    line-height: 19px;
    position: absolute;
    text-decoration: none;
    top: -9px;
    width: 17px;
}
</style>
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
				<form action="" id="system_table_form" class="system_table_form">
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1">基本信息</a></li>
							<li><a href="#tabs-2">列名</a></li>
							<li id="main_secondary_table_relationship"><a href="#tabs-3">主从表关系</a></li>
						</ul>
						<div id="tabs-1">
							<input type="hidden" id="uuid" name="uuid" />
							<table>
								<tr>
									<td style="width: 65px;"><label>中文名</label></td>
									<td><input id="chineseName" name="chineseName" type="text" class="full-width" ></td>
									<td></td>
								</tr>
								<tr>
									<td><label>表名</label></td>
									<td><input id="tableName" name="tableName" type="text" class="full-width" 
										datatype="*" nullmsg="必填项" />
										<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>	
									</td>
									
								</tr>
								<tr>
									<td><label>完全限定名</label></td>
									<td><input id="fullEntityName" name="fullEntityName" class="full-width" 
										type="text" datatype="*" nullmsg="必填项" />
										<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>	
									</td>
								</tr>
								<tr>
									<td><label>编号</label></td>
									<td><input id="code" name="code" type="text" class="full-width" ></td>
									<td></td>
								</tr>
								<tr>
									<td><label>模块名</label></td>
									<td>
										<input type="text"  class="full-width"  name="showModuleName" id="showModuleName" value=""  /><input type="hidden" name="moduleName" id="moduleName" value="" />
									</td>
								</tr>
								<tr>
									<td class="align-top"><label>备注</label></td>
									<td><textarea id="remark" name="remark" class="full-width" ></textarea></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div id="tabs-2">
							<table id="child_systemtable_list"></table>
						</div>
						
						<div id="tabs-3">
							<div class="btn-group">
								<button id="relationship_btn_add" type="button" class="btn">新 增</button>
								<button id="relationship_btn_del" type="button" class="btn">删除</button>
							</div>
							<table id="main_secondary_table_relationship_list"></table>
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
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
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
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/systemtable/systemtable.js"></script>
</body>
</html>