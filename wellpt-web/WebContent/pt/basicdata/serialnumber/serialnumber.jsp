<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>流水号定义</title>

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
			<form action="" id="serial_number_form" class="serial_number_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table>
							<tr>
								<td style="width: 90px;"><label>分类</label></td>
								<td><input id="type" name="type" type="text" class="full-width" datatype="*" nullmsg="必填项"/>
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
								</td>
							</tr>
							<tr>
								<td><label>名称</label></td>
								<td><input  id="name" name="name" type="text" class="full-width" datatype="*" nullmsg="必填项" />
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>ID</label></td>
								<td><input  id="id" name="id" type="text" class="full-width" datatype="*" nullmsg="必填项" />
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>编号</label></td>
								<td><input  id="code" name="code" type="text" class="full-width" datatype="*" nullmsg="必填项" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>关键部分</label></td>
								<td><input  id="keyPart" name="keyPart" type="text" class="full-width"  datatype="*" nullmsg="必填项" />
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>头部</label></td>
								<td><input id="headPart" name="headPart" type="text" class="full-width"  /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>初始值</label></td>
								<td>
									<input  id="initialValue" name="initialValue" type="text" style="width:86%" datatype="*" nullmsg="必填项" />
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
									<input id="isFillPosition" name="isFillPosition" type="checkbox" value="fill" /><label for="isFillPosition">补位</label>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>增量</label></td>
								<td><input  id="incremental" name="incremental" type="text" class="full-width" datatype="*" nullmsg="必填项"/>
									<div class="info"><span class="Validform_checktip"></span><span class="dec"><s class="dec1">&#9670;</s><s class="dec2">&#9670;</s></span></div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>尾部</label></td>
								<td><input  id="lastPart" name="lastPart" type="text" class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>新年度开始日期</label></td>
								<td><input  id="startDate" name="startDate" type="text" class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label></label></td>
								<td><input id="isFillNumber" name="isFillNumber"
									type="checkbox" value="notfill" /><label for="isFillNumber">需要补号</label>
									<input id="isEditor" name="isEditor"
									type="checkbox" value="notfill" /><label for="isEditor">可编辑</label>
								</td>
								<td></td>
							</tr>
							<tr id="user" style="display:none">
								<td><label>使用人</label></td>
								<td>
									<textarea id="ownerNames" name="ownerNames" class="full-width"></textarea>
									<input id="ownerIds" name="ownerIds" type="hidden" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>备注</label></td>
								<td><input id="remark" name="remark" type="text" class="full-width" /></td>
								<td></td>
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
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/serialnumber/serialnumber.js"></script>
</body>
</html>