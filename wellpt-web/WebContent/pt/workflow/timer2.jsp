<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="timerForm" name="timerForm">
		<table>
			<tr>
				<td style="width: 65px;"><label for="name">名称</label></td>
				<td><input id="name" name="name" type="text" style="width: 69%" />
					<input id="asFlowLimitTime" name="asFlowLimitTime" type="checkbox" /><label
					for="asFlowLimitTime">作为流程承诺计时</label></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="limitTimeLabel">办理时限</label></td>
				<td><select id="limitTimeType" name="limitTimeType"
					style="width: 15%">
						<option value="1">指定数字</option>
						<option value="2">选择字段</option>
				</select>&nbsp;<span id="limitTimeType_1"><input id="limitTime1"
						name="limitTime1" type="text" /></span><span id="limitTimeType_2"><select
						id="limitTimeLabel" name="limitTimeLabel">
							<option>--请选择字段--</option>
					</select></span><input id="limitTime" name="limitTime" type="hidden" />&nbsp;&nbsp;<select
					name="limitUnit" style="width: 23%">
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
				<td class="align-top"><label for="Ddutys">责任人</label></td>
				<td><textarea id="Ddutys" name="Ddutys" rows="1"
						class="full-width"></textarea> <input name="Dduty1" type="hidden" />
					<input name="duty1" type="hidden" /> <input name="duty2"
					type="hidden" /> <input name="duty4" type="hidden" /> <input
					name="duty8" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="Dtasks">计时环节</label></td>
				<td><textarea id="Dtasks" name="Dtasks" rows="1"
						class="full-width"></textarea><input name="tasks" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input id="affectMainFlow" name="affectMainFlow"
					type="checkbox" value="1" /><label for="affectMainFlow">计时暂停/恢复影响主流程</label>
					<input id="enableAlarm" name="enableAlarm" type="checkbox"
					value="1" /><label for="enableAlarm">预警提醒</label> <input
					id="enableDueDoing" name="enableDueDoing" type="checkbox" value="1" /><label
					for="enableDueDoing">逾期处理</label></td>
				<td></td>
			</tr>
			<tr id="config_enableAlarm">
				<td class="align-top"><label>预警提醒</label></td>
				<td><span id="ID_Alarm"> 提前&nbsp;<input name="alarmTime"
						value="1" style="width: 10%"> <select name="alarmUnit"
						style="width: 20%">
							<option value="3">工作日</option>
							<option value="2" selected>工作小时</option>
							<option value="1">工作分钟</option>
							<option value="86400">天</option>
							<option value="3600">小时</option>
							<option value="60">分钟</option>
					</select> 开始消息通知，共<input name="alarmFrequency" value="1" style="width: 10%" />次
						<br />
				</span> <input type="checkbox" id="alarmObjects_Doing" name="alarmObjects"
					value="Doing" checked="checked" /><label for="alarmObjects_Doing">在办人员</label>
					<input type="checkbox" id="alarmObjects_Monitor"
					name="alarmObjects" value="Monitor" /><label
					for="alarmObjects_Monitor">督办人员</label> <input type="checkbox"
					id="alarmObjects_Tracer" name="alarmObjects" value="Tracer" /><label
					for="alarmObjects_Tracer">跟踪人员</label> <input type="checkbox"
					id="alarmObjects_Admin" name="alarmObjects" value="Admin" /><label
					for="alarmObjects_Admin">流程管理人员</label> <input type="checkbox"
					id="alarmObjects_Other" name="alarmObjects" value="Other" /><label
					for="alarmObjects_Other">其他人员</label> <span ID="ID_AlarmOtherUser"
					style="display: none"> <input id="DalarmUsers"
						name="DalarmUsers" type="text" style="width: 89%;" /> <label
						for="DalarmUsers">选择</label> <input name="DalarmUser1"
						type="hidden" /> <input name="alarmUser1" type="hidden" /> <input
						name="alarmUser2" type="hidden" /> <input name="alarmUser4"
						type="hidden" /> <input name="alarmUser8" type="hidden" />
				</span>
					<table>
						<tr>
							<td align="left" width="12%"><label for="alarmFlowName">发起流程</label>
							</td>
							<td><input id="alarmFlowName" name="alarmFlowName"
								style="width: 95%;" emptyMsg="请指定要发起的预警流程！" /> <input
								name="alarmFlowID" type="hidden"></td>
						</tr>
						<tr>
							<td align="left">办理人</td>
							<td><input type="checkbox" id="alarmFlowDoings_Doing"
								name="alarmFlowDoings" value="Doing" /><label
								for="alarmFlowDoings_Doing">在办人员</label> <input type="checkbox"
								id="alarmFlowDoings_Monitor" name="alarmFlowDoings"
								value="Monitor" checked="checked" /><label
								for="alarmFlowDoings_Monitor">督办人员</label> <input
								type="checkbox" id="alarmFlowDoings_Tracer"
								name="alarmFlowDoings" value="Tracer" /><label
								for="alarmFlowDoings_Tracer">跟踪人员</label> <input type="checkbox"
								id="alarmFlowDoings_Admin" name="alarmFlowDoings" value="Admin" /><label
								for="alarmFlowDoings_Admin">流程管理人员</label> <input
								type="checkbox" id="alarmFlowDoings_Other"
								name="alarmFlowDoings" value="Other" /><label
								for="alarmFlowDoings_Other">其他人员</label></td>
						</tr>
						<tr id="ID_AlarmFlowDoing" style="display: none">
							<td align="center">&nbsp;</td>
							<td><input id="DalarmFlowDoingUsers"
								name="DalarmFlowDoingUsers" type="text" style="width: 90%;" />
								<label for="DalarmFlowDoingUsers">选择</label> <input
								name="DalarmFlowDoingUser1" type="hidden" /> <input
								name="alarmFlowDoingUser1" type="hidden" /> <input
								name="alarmFlowDoingUser2" type="hidden" /> <input
								name="alarmFlowDoingUser4" type="hidden" /> <input
								name="alarmFlowDoingUser8" type="hidden" /></td>
						</tr>
					</table></td>
				<td></td>
			</tr>
			<tr id="config_enableDueDoing">
				<td class="align-top"><label>逾期处理</label></td>
				<td><input type="checkbox" id="dueObjects_Doing"
					name="dueObjects" value="Doing" checked="checked" /><label
					for="dueObjects_Doing">消息催办在办人员</label> <span id="ID_DueMsgToDoing">
						&nbsp;&nbsp;每&nbsp;<input name="dueTime" value="1"
						style="width: 10%" /> <select name="dueUnit" style="width: 18%">
							<option value="3" selected>工作日</option>
							<option value="2">工作小时</option>
							<option value="1">工作分钟</option>
							<option value="86400">天</option>
							<option value="3600">小时</option>
							<option value="60">分钟</option>
					</select> 催办一次，共<input name="dueFrequency" value="1" style="width: 10%">次
				</span> <br> <input type="checkbox" id="dueObjects_Monitor"
					name="dueObjects" value="Monitor" /><label
					for="dueObjects_Monitor">通知督办人员</label> <input type="checkbox"
					id="dueObjects_Tracer" name="dueObjects" value="Tracer" /><label
					for="dueObjects_Tracer">通知跟踪人员</label> <input type="checkbox"
					id="dueObjects_Admin" name="dueObjects" value="Admin" /><label
					for="dueObjects_Admin">通知流程管理人员</label> <input type="checkbox"
					id="dueObjects_Other" name="dueObjects" value="Other" /><label
					for="dueObjects_Other">通知其他人员</label> <span ID="ID_DueToOtherUser"
					style="display: none"> <input id="DdueUsers"
						name="DdueUsers" type="text" style="width: 89%;" /> <label
						for="DdueUsers">选择</label> <input name="DdueUser1" type="hidden" />
						<input name="dueUser1" type="hidden" /> <input name="dueUser2"
						type="hidden" /> <input name="dueUser4" type="hidden" /> <input
						name="dueUser8" type="hidden" /><span
						style="display: block; height: 0.2em;"></span>
				</span><select name="dueAction" style="width: 95%">
						<option value="0" selected>---不处理---</option>
						<option value="1">移交给B岗人员办理</option>
						<option value="2">移交给督办人员办理</option>
						<option value="4">移交给其他人员办理</option>
						<option value="8">退回上一个办理环节</option>
						<option value="16">自动进入下一个办理环节</option>
				</select> <span id="ID_DueToUser" style="display: none"><span
						style="display: block; height: 0.2em;"></span><input
						id="DdueToUsers" name="DdueToUsers" type="text"
						style="width: 89%;" /> <!-- 			<input type="button" onclick="bTimerActions(8);" value="选择" class="HotSpot" title="选择移交人员"/> -->
						<label for="DdueToUsers">选择</label> <input name="DdueToUser1"
						type="hidden" /> <input name="dueToUser1" type="hidden" /> <input
						name="dueToUser2" type="hidden" /> <input name="dueToUser4"
						type="hidden" /> <input name="dueToUser8" type="hidden" /> </span> <span
					ID="ID_DueToTask" style="display: none"> <input
						id="DdueToTask" name="DdueToTask" style="width: 89%"
						multiplemsg="不支持多个环节，请选择一个环节！" /> <input name="dueToTask"
						type="hidden" /> <!-- 			<input type="button" onclick='bTimerActions(9);' value="选择" class="HotSpot" title="选择转办环节"/> -->
						<label for="DdueToTask">选择</label>
				</span>
					<table>
						<tr>
							<td align="left" width="12%"><label for="dueFlowName">发起流程</label></td>
							<td><input id="dueFlowName" name="dueFlowName"
								style="width: 95%;" emptyMsg="请指定要发起的催办流程！" /> <input
								name="dueFlowID" type="hidden" /></td>
						</tr>
						<tr>
							<td align="left">办理人</td>
							<td><input type="checkbox" id="dueFlowDoings_Doing"
								name="dueFlowDoings" value="Doing" /><label
								for="dueFlowDoings_Doing">在办人员</label> <input type="checkbox"
								id="dueFlowDoings_Monitor" name="dueFlowDoings" value="Monitor"
								checked /><label for="dueFlowDoings_Monitor">督办人员</label> <input
								type="checkbox" id="dueFlowDoings_Tracer" name="dueFlowDoings"
								value="Tracer" /><label for="dueFlowDoings_Tracer">跟踪人员</label>
								<input type="checkbox" id="dueFlowDoings_Admin"
								name="dueFlowDoings" value="Admin" /><label
								for="dueFlowDoings_Admin">流程管理人员</label> <input type="checkbox"
								id="dueFlowDoings_Other" name="dueFlowDoings" value="Other" /><label
								for="dueFlowDoings_Other">其他人员</label></td>
						</tr>
						<tr id="ID_DueFlowDoing" style="display: none">
							<td align="center">&nbsp;</td>
							<td><input id="DdueFlowDoingUsers" name="DdueFlowDoingUsers"
								type="text" style="width: 90%;" /> <!-- 					<input type="button" onclick="bTimerActions(11);" value="选择" class="HotSpot" title="其他办理人员"/> -->
								<label for="DdueFlowDoingUsers">选择</label> <input
								name="DdueFlowDoingUser1" type="hidden" /> <input
								name="dueFlowDoingUser1" type="hidden" /> <input
								name="dueFlowDoingUser2" type="hidden" /> <input
								name="dueFlowDoingUser4" type="hidden" /> <input
								name="dueFlowDoingUser8" type="hidden" /></td>
						</tr>
					</table></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
