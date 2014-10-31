<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="messageTemplateForm" name="messageTemplateForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 65px;" class="align-top"><label for="type">消息类型</label></td>
				<td><select id="type" name="type" style="width: 100%">
						<option value="TODO">1、工作到达通知</option>
						<option value="SUPERVISE">2、督办工作到达通知</option>
						<option value="COUNTER_SIGN">3、会签工作到达通知</option>
						<option value="TRANSFER">4、转办工作到达通知</option>
						<option value="ENTRUST">5、工作委托到达通知</option>
						<option value="COUNTER_SIGN_RETURN">6、会签工作返回通知</option>
						<option value="ROLL_BACK">7、工作退回通知</option>
						<option value="OVER">8、流程结束消息通知</option>
						<option value="OVER_SEND_FILE">9、流程结束文件分发</option>
						<option value="DIRECTION_SEND_MSG">10、流向消息通知</option>
						<option value="DIRECTION_SEND_FILE">11、流向文件分发</option>
						<option value="READ_RETURN_RECEIPT">12、办理人阅读回执</option>
						<option value="EMPTY_NOTE_DONE">13、办理人为空消息通知</option>
						<option value="ALARM_DOING">14、预警提醒办理人</option>
						<option value="ALARM_SUPERVISE">15、预警提醒督办人</option>
						<option value="ALARM_TRACER">16、预警提醒跟踪人</option>
						<option value="ALARM_OTHER">17、预警提醒流程其他人员</option>
						<option value="ALARM_ADMIN">18、预警提醒流程管理员</option>
						<option value="DUE_DOING">19、逾期工作通知办理人</option>
						<option value="DUE_SUPERVISE">20、逾期工作通知督办人</option>
						<option value="DUE_TRACER">21、逾期工作通知跟踪人</option>
						<option value="DUE_OTHER">22、逾期工作通知其他人员</option>
						<option value="DUE_ADMIN">23、逾期工作通知流程管理员</option>
						<option value="">24、逾期工作处理通知原办理人-退回原环节</option>
						<option value="">25、逾期工作处理通知原办理人-特送</option>
						<option value="">26、逾期工作处理通知原办理人-自动提交到下一环节</option>
						<option value="">27、终止工作委托通知被委托人</option>
						<option value="">28、文件特送通知原办理人</option>
						<option value="">29、工作撤回通知原办理人</option>
						<option value="">30、手动工作催办通知</option>
						<option value="">31、督办意见通知在办人</option>
						<option value="">32、办理人为空转办提醒</option>
						<option value="">33、催办意见通知在办人</option>
						<option value="NOTIFY_SUB_FLOW_DOING">34、子流程办结通知其他子流程在办人员</option>
				</select></td>
				<td></td>
			</tr>
			<tr>
				<td style="width: 65px;" class="align-top"><label
					for="templateId">消息格式</label></td>
				<td><select id="templateId" name="templateId"
					style="width: 100%">
				</select></td>
				<td></td>
			</tr>
			<tr>
				<td style="width: 65px;" class="align-top"><label
					for="condition">约束条件</label></td>
				<td><input id="condition" name="condition" type="text"
					style="width: 100%"></td>
				<td></td>
			</tr>
			<!-- modified by huanglinchuan 2014.10.21 begin -->
			<tr>
				<td style="width: 65px;" class="align-top"><label
					for="condition">抄送对象</label></td>
				<td><textarea id="DextraMsgRecipients" name="DextraMsgRecipients" class="full-width"></textarea>
				    <input name="DextraMsgRecipient1"
							value="" type="hidden"> <input name="extraMsgRecipient1"
							type="hidden" /> <input name="extraMsgRecipient2" type="hidden" /> <input
							name="extraMsgRecipient4" type="hidden" /> <input
							name="extraMsgRecipient8" type="hidden" />
					注意！这里设置的是消息需要额外抄送人员。
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="width: 65px;" class="align-top"><label
					for="condition">是否启用</label></td>
				<td><input id="isSendMsg" name="isSendMsg" type="radio" value="1" checked>是<input id="isSendMsg" name="isSendMsg" type="radio" value="0">否</td>
				<td></td>
			</tr>
			<!-- modified by huanglinchuan 2014.10.21 end -->
		</table>
	</form>
</div>
