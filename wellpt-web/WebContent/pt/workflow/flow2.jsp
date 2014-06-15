<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<form id="flowForm" name="flowForm">
		<div class="tabs">
			<ul>
				<li><a href="#tabs-1">基本属性</a></li>
				<li><a href="#tabs-2">流程权限</a></li>
				<li><a href="#tabs-3">计时系统</a></li>
				<li><a href="#tabs-4">高级设置</a></li>
			</ul>
			<div id="tabs-1">
				<table>
					<tr>
						<td style="width: 65px;"><label for="flow_name">名称</label></td>
						<td><input id="flow_name" name="name" type="text"
							class="full-width" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="flow_id">ID</label></td>
						<td><input id="flow_id" name="id" type="text"
							class="full-width"></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="DCategory">流程类别</label></td>
						<td><select id="DCategory" name="DCategory"
							class="full-width"></select></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="code">流程编号</label></td>
						<td><input id="code" name="code" type="text"
							class="full-width" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="EQFlowName">等价流程</label></td>
						<td><input id="EQFlowName" name="EQFlowName" type="text"
							class="full-width" /> <input id="EQFlowID" name="EQFlowID"
							type="hidden"></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="DForm">对应表单</label></td>
						<td><select id="DForm" name="DForm" class="full-width"></select>
						</td>
						<td></td>
					</tr>
					<tr>
						<td><label>自由流程</label></td>
						<td><input type="radio" id="free" name="isFree" value="1" /><label
							for="free">是</label> <input type="radio" id="nofree"
							name="isFree" value="0" checked /><label for="nofree">否</label></td>
						<td></td>
					</tr>
					<tr>
						<td><label>启用流程</label></td>
						<td><input type="radio" id="active" name="isActive" value="1"
							checked="checked" /><label for="active">启用</label> <input
							type="radio" id="deactive" name="isActive" value="0" /><label
							for="deactive">禁用</label></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-2">
				<table>
					<tr>
						<td style="width: 65px;"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="Dcreators">发起人</label></td>
						<td><textarea id="Dcreators" name="Dcreators"
								class="full-width"></textarea> <input name="Dcreator1" value=""
							type="hidden" /> <input name="creator1" value="" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="Dusers">参与人</label></td>
						<td><textarea id="Dusers" name="Dusers" class="full-width"></textarea>
							<input name="Duser1" value="" type="hidden" /> <input
							name="user1" value="" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="Dmonitors">督办人</label></td>
						<td><textarea id="Dmonitors" name="Dmonitors"
								class="full-width"></textarea> <input name="Dmonitor1" value=""
							type="hidden" /> <input name="monitor1" value="" type="hidden" />
							<input name="monitor2" value="" type="hidden" /> <input
							name="monitor4" value="" type="hidden" /> <input name="monitor8"
							value="" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="Dadmins">监控者</label></td>
						<td><textarea id="Dadmins" name="Dadmins" class="full-width"></textarea>
							<input name="Dadmin1" value="" type="hidden" /> <input
							name="admin1" value="" type="hidden" /> <input name="admin2"
							value="" type="hidden" /> <input name="admin4" value=""
							type="hidden" /> <input name="admin8" value="" type="hidden" /></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-3">
				<table>
					<tr>
						<td style="width: 75px;" class="align-top"><label
							for="DTimer">计时系统</label></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<select id="DTimer" name="DTimer" size="6" class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em;">
									<button id="timerAdd" type="button" class="btn">增加</button>
									<button id="timerEdit" type="button" class="btn">编辑</button>
									<button id="timerDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
					<tr style="display: none;">
						<td><label for="DForm">承诺期限</label></td>
						<td><select id="dueTimeLabel" name="dueTimeLabel"></select><input
							id="dueTime" name="dueTime" type="hidden" /> <select
							name="timeUnit" style="width: 20%">
								<option value="3" selected>工作日</option>
								<option value="2">工作小时</option>
								<option value="1">工作分钟</option>
								<option value="86400">天</option>
								<option value="3600">小时</option>
								<option value="60">分钟</option>
						</select></td>
						<td></td>
					</tr>
					<tr style="display: none;">
						<td class="align-top"><label for="DbeginDirections">选择开始流向</label></td>
						<td><textarea id="DbeginDirections" name="DbeginDirections"
								class="full-width"></textarea><input id="beginDirections"
							name="beginDirections" type="hidden" /></td>
						<td></td>
					</tr>
					<tr style="display: none;">
						<td class="align-top"><label for="DendDirections">结束时间</label></td>
						<td><textarea id="DendDirections" name="DendDirections"
								class="full-width"></textarea><input id="endDirections"
							name="endDirections" type="hidden" /></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-4">
				<table>
					<tr>
						<td style="width: 65px;" class="align-top"><label
							for="printTemplate">打印模板</label></td>
						<td><textarea id="printTemplate" name="printTemplate"
								class="full-width"></textarea><input id="printTemplateId"
							name="printTemplateId" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="listenerName">事件监听</label></td>
						<td><textarea id="listenerName" name="listenerName"
								class="full-width"></textarea><input id="listener"
							name="listener" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label>文件分发</label></td>
						<td><input id="sendFile" name="isSendFile" type="radio"
							value="1" /><label for="sendFile">是</label> <input
							id="noSendFile" name="isSendFile" type="radio" value="0"
							checked="checked" /><label for="noSendFile">否</label></td>
						<td></td>
					</tr>
					<tr id="ID_SendFile" style="display: none;">
						<td class="align-top"><label for="DfileRecipients">分发对象</label></td>
						<td><textarea id="DfileRecipients" name="DfileRecipients"
								class="full-width"></textarea><input name="DfileRecipient1"
							value="" type="hidden"> <input name="fileRecipient1"
							type="hidden" /> <input name="fileRecipient2" type="hidden" />
							<input name="fileRecipient4" type="hidden" /> <input
							name="fileRecipient8" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label>消息分发</label></td>
						<td><input id="sendMsg" name="isSendMsg" type="radio"
							value="1" /><label for="sendMsg">是</label> <input id="noSendMsg"
							name="isSendMsg" type="radio" value="0" checked="checked" /><label
							for="noSendMsg">否</label></td>
						<td></td>
					</tr>
					<tr id="ID_SendMsg" style="display: none;">
						<td class="align-top"><label for="DmsgRecipients">分发对象</label></td>
						<td><textarea id="DmsgRecipients" name="DmsgRecipients"
								class="full-width"></textarea><input name="DmsgRecipient1"
							value="" type="hidden"> <input name="msgRecipient1"
							type="hidden" /> <input name="msgRecipient2" type="hidden" /> <input
							name="msgRecipient4" type="hidden" /> <input
							name="msgRecipient8" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td style="width: 75px;" class="align-top"><label
							for="DBackUser">岗位替代</label></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<select id="DBackUser" name="DBackUser" size="6"
										class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em;">
									<button id="backuserAdd" type="button" class="btn">增加</button>
									<button id="backuserEdit" type="button" class="btn">编辑</button>
									<button id="backuserDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
					<tr>
						<td style="width: 75px;" class="align-top"><label
							for="DMessageTemplate">消息模板</label></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<select id="DMessageTemplate" name="DMessageTemplate" size="6"
										class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em;">
									<button id="messageTemplateAdd" type="button" class="btn">增加</button>
									<button id="messageTemplateEdit" type="button" class="btn">编辑</button>
									<button id="messageTemplateDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btn-group btn-group-bottom">
			<button id="btn_save" type="button" class="btn"
				style="display: none;">保存</button>
			<button id="btn_abandon" type="button" class="btn"
				style="display: none;">放弃</button>
		</div>
	</form>
	<div id="dlg_timer"></div>
	<div id="dlg_user_back"></div>
	<div id="dlg_message_template"></div>
</div>
