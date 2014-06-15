<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>

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
			<div class="btn-group btn-group-top">
				<div class="query-fields">
					<input id="query_keyWord" />
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
			<form action="" id="template_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
						<li><a href="#tabs-2">在线消息</a></li>
						<li><a href="#tabs-3">电子邮件</a></li>
						<li><a href="#tabs-4">手机短信</a></li>
					</ul>
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table>
							<tr>
								<td><label>名称</label></td>
								<td><input class="full-width" id="name" name="name"
									type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label>ID</label></td>
								<td><input class="full-width" id="id" name="id" type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label>编号</label></td>
								<td><input class="full-width" id="code" name="code"
									type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>分类</label></td>
								<td><input class="full-width" id="category" name="category"
									type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<!-- 类型(系统、用户) -->
								<td><label>类型</label></td>
								<td><input id="type_system" name="type" type="radio"
									value="SYSTEM" /><label for="type_system">系统消息</label> <input
									id="type_user" name="type" type="radio" value="USER" /><label
									for="type_user">用户消息</label></td>
								<td></td>
							</tr>
							<tr>
								<td><label>提醒方式</label></td>
								<td><input id="sendWay_online" name="sendWays"
									type="checkbox" value="ON_LINE" /><label for="sendWay_online">在线消息</label>
									<input id="sendWay_email" name="sendWays" type="checkbox"
									value="EMAIL" /><label for="sendWay_email">邮件</label> <input
									id="sendWay_sms" name="sendWays" type="checkbox" value="SMS" /><label
									for="sendWay_sms">手机短信</label> <input id="sendWay_none"
									name="sendWays" type="checkbox" value="NONE" /><label
									for="sendWay_none">不通知</label></td>
								<td></td>
							</tr>
							<tr>
								<!-- 发送时间 (即时发送、工作时间发送) -->
								<td><label>发送时间</label></td>
								<td><input id="sendTime_in_time" name="sendTime"
									type="radio" value="IN_TIME" /><label for="sendTime_in_time">即时发送</label>
									<input id="sendTime_work_time" name="sendTime" type="radio"
									value="WORK_TIME" /><label for="sendTime_work_time">工作时间发送</label></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label>映像规则</label></td>
								<td><textarea class="full-width" id="mappingRule"
										name="mappingRule"></textarea></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-2">
						<table>
							<tr>
								<td style="width: 65px;"><label>标题</label></td>
								<td><input class="full-width" id="onlineSubject"
									name="onlineSubject" type="text" size="50" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label>内容</label></td>
								<td><textarea class="full-width" id="onlineBody"
										name="onlineBody" rows="10" cols="50"></textarea></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-3">
						<table>
							<tr>
								<td style="width: 65px;"><label>标题</label></td>
								<td><input class="full-width" id="emailSubject"
									name="emailSubject" type="text" size="50" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label>内容</label></td>
								<td><textarea class="full-width" id="emailBody"
										name="emailBody" rows="10" cols="50"></textarea></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-4">
						<table>
							<tr>
								<td class="align-top" style="width: 65px;"><label>内容</label></td>
								<td><textarea class="full-width" id="smsBody"
										name="smsBody" rows="10" cols="50"></textarea></td>
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


	<!-- Project -->
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
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/message/template.js"></script>
</body>
</html>