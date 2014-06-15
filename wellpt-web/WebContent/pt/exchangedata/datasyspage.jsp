<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>module List</title>
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
									<td style="width: 130px;">名称</td>
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
									<td><label>服务器IP</label></td>
									<td><input id="serverIp" name="serverIp" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>单位</label><input id="unitId" name="unitId" type="hidden"></td>
									<td><input id="unitName" name="unitName" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>上报数据类型</label></td>
									<td><input id="typeName" name="typeName" type="text" style="width:100%;"/>
									   <input id="typeId" name="typeId" type="hidden"></td>
								</tr>
								<tr>
									<td><label>接收数据类型</label></td>
									<td><input id="typeName1" name="typeName1" type="text" style="width:100%;"/>
									   <input id="typeId1" name="typeId1" type="hidden"></td>
								</tr>
								<tr>
									<td><label>数据接收接口地址</label></td>
									<td><input id="receiveUrl" name="receiveUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>上传结果回调接口地址</label></td>
									<td><input id="sendCallbackUrl" name="sendCallbackUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>路由回调接口地址(商改平台使用)</label></td>
									<td><input id="routeCallbackUrl" name="routeCallbackUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>回复消息告知接口地址(商改平台使用)</label></td>
									<td><input id="replyMsgUrl" name="replyMsgUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>撤销接口地址(商改平台使用)</label></td>
									<td><input id="cancelUrl" name="cancelUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>撤销回调接口地址(商改平台使用)</label></td>
									<td><input id="cancelCallbackUrl" name="cancelCallbackUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>FTP服务器地址</label></td>
									<td><input id="ftpServerUrl" name="ftpServerUrl" type="text" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>FTP用户名</label></td>
									<td><input id="ftpUserName" name="ftpUserName" type="text" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>FTP密码</label></td>
									<td><input id="ftpUserPassword" name="ftpUserPassword" type="password" style="width:100%;"/></td>
								</tr>
								<tr style="display: none;">
									<td><label>FTP路径</label></td>
									<td><input id="ftpFilePath" name="ftpFilePath" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>证书主体</label></td>
									<td><input id="subjectDN" name="subjectDN" type="text" style="width:100%;"/></td>
								</tr>
								<tr>
									<td><label>备注</label></td>
									<td><input id="remark" name="remark" type="text" style="width:100%;"/></td>
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
		src="${ctx}/resources/pt/js/exchangedata/datasyspage.js"></script>
	
	
</body>
</html>