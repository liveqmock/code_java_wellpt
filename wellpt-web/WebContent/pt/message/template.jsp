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
						<li><a href="#tabs-5">日程</a></li>
						<li><a href="#tabs-6">restful web services</a></li>
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
									for="sendWay_sms">手机短信</label>
									<!--  <input id="sendWay_schedule" name="sendWays"
									type="checkbox" value="SCHEDULE" /><label for="sendWay_schedule">日程</label>-->
									<input id="sendWay_restful" name="sendWays"
									type="checkbox" value="WEB_SERVICE" /><label for="sendWay_restful">restful web services</label>  <input id="sendWay_none"
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
							<tr>
								<td class="align-top"><label>发送消息触发事件</label></td>
								<td>
								<input type="text" id="messageEventText"  name="messageEventText" style="width:400px;"/>
									<input type="hidden" id="messageEvent"  name="messageEvent"/></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-2">
						<table>
						    <tr>
						      <td><label>弹窗提醒</label></td>
								<td><input id="online_popup_y" name="isOnlinePopup" type="radio" 
									value="Y" /><label for="online_popup_y">是</label> 
									<input id="online_popup_n" name="isOnlinePopup" type="radio" value="N" /><label
									for="online_popup_n">否</label>
								</td>
							</tr>
							  <tr>
						      <td><label>征求意见</label></td>
								<td><input id="show_viewpoint_y" name="showViewpoint" type="radio" 
									value="Y" /><label for="show_viewpoint_y">是</label> 
									<input id="show_viewpoint_n" name="showViewpoint" type="radio" value="N" /><label
									for="show_viewpoint_n">否</label>
								</td>
							</tr>
							 <tr>
						      <td><label>意见立场</label></td>
								<td>
								<table >
								   <tr>
								   <td><input id="viewpoint_y" name="viewpointY" type="text" 
									value="" /> (通过)</td>
								  <td><label></label></td>
								   <td><input id="viewpoint_n" name="viewpointN" type="text" 
									value="" />(不通过)</td>
								   <td></td>
								   <td><input id="viewpoint_none" name="viewpointNone" type="text" 
									value="" />(不处理) </td>
									<td></td>
								   </tr>
								 </table>  
								</td>
							</tr>
							<tr>
								<td><label>确定立场后操作</label></td>
								<td>
								<table style="border:1px solid #000000;">
								<tr>
								<td><label>前台JS事件</label></td>
								<td><input type="text" id="foregroundEventText"  name="foregroundEventText" style="width:400px;"/>
									<input type="hidden" id="foregroundEvent"  name="foregroundEvent"/></td>
								</tr>
								<tr>
								<td><label>后台事件</label></td>
								<td><input type="text" id="backgroundEventText"  name="backgroundEventText" style="width:400px;"/>
									<input type="hidden" id="backgroundEvent"  name="backgroundEvent"/></td>
								</tr>
								</table>
								<td></td>
							</tr>
							<tr>
								<td><label>是否咨询列入日程</label></td>
								<td><input id="ask_for_schedule_y" name="askForSchedule" type="radio" 
									value="Y" /><label for="ask_for_schedule_y">是</label> 
									<input id="ask_for_schedule_n" name="askForSchedule" type="radio" value="N" /><label
									for="ask_for_schedule_n">否</label>
								</td>
								</tr>
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
					<div id="tabs-5">
						<table>
						    <tr>
								<td style="width: 65px;"><label>标题</label></td>
								<td><input class="full-width" id="scheduleTitle"
									name="scheduleTitle" type="text" size="50" /></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label>起止时间</label></td>
								<td>
								<table>
								<tr>
								<td>
								<input class="full-width" id="scheduleDates"
									name="scheduleDates" type="text" size="50" />
								</td>
								<td>至</td>
								<td>
								<input class="full-width" id="scheduleDatee"
									name="scheduleDatee" type="text" size="50" />
								</td>
								</tr>
								</table>
								</td>
								<td></td>
							</tr>
							 <tr>
								<td style="width: 65px;"><label>地点</label></td>
								<td><input class="full-width" id="scheduleAddress"
									name="scheduleAddress" type="text" size="50" /></td>
								<td></td>
							</tr>
							 <tr>
								<td style="width: 65px;"><label>提醒时间</label></td>
								<td> 
								<input class="full-width" id="reminderTime"
									name="reminderTime" type="text" size="50" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top" style="width: 65px;"><label>内容</label></td>
								<td><textarea class="full-width" id="scheduleBody"
										name="scheduleBody" rows="10" cols="50"></textarea></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label>源标题</label></td>
								<td><input class="full-width" id="srcTitle"
									name="srctitle" type="text" size="50" /></td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;"><label>源地址</label></td>
								<td><input class="full-width" id="srcAddress"
									name="srcAddress" type="text" size="50" /></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-6">
						<table>
							<tr>
								<td style="width: 65px;"><label>webService地址</label></td>
								<td><input class="full-width" id="webServiceUrl" name="webServiceUrl"
									type="text" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>验证账号</label></td>
								<td>
								<table>
								<tr>
								<td><input class="full-width" id="usernameKey" name="usernameKey" 
									type="text" />
								</td>
								<td>(key)</td>
								<td>
									<input class="full-width" id="usernameValue" name="usernameValue"
									type="text" />
								</td>
								<td>(value)</td>
								</tr>
								</table>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>验证密码</label></td>
								<td>
								<table>
								<tr>
								<td><input class="full-width" id="passwordKey" name="passwordKey"
									type="text" />
								</td>
								<td>(key)</td>
								<td>
									<input class="full-width" id="passwordValue" name="passwordValue"
									type="text" />
								</td>
								<td>(value)</td>
								</tr>
								</table>
								</td>
								<td></td>
							</tr>
							<tr>
								<td><label>租户id</label></td>
								<td>
								<table>
								<tr>
								<td><input class="full-width" id="tenantidKey" name="tenantidKey"
									type="text" />
								</td>
								<td>(key)</td>
								<td>
									<input class="full-width" id="tenantidValue" name="tenantidValue"
									type="text" />
								</td>
								<td>(value)</td>
								</tr>
								</table>
								</td>
								<td></td>
							</tr>
						</table>
						<div id="tabs-2">
							<div class="btn-group">
								<button id="btn_parm_add" type="button" class="btn">新增</button>
								<button id="btn_parm_del" type="button" class="btn">删除</button>
							</div>
							<table id="child_parm_list"></table>
						</div>
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
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/message/template.js"></script>
		
</body>
</html>