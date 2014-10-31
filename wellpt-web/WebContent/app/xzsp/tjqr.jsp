<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dyform tjqr_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">操作说明</td>
				<td class="value" colspan="3"><textarea id="tjqr_remark"
						name="tjqr_remark" disabled="disabled" style="width: 98%;">对所选择的审批事项进行退件确认的操作，请输入退件确认的意见!</textarea></td>
			</tr>
			<tr>
				<td class="Label">退件方式</td>
				<td class="value" colspan="3"><select name="tj_manner"
					id="tj_manner" style="width: 100%;" disabled="disabled">
						<option value="1">暂缓办理的退件</option>
						<option value="2">无法办理的退件</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">退件意见</td>
				<td class="value" colspan="3"><textarea id="tj_opinion"
						name="tj_opinion" disabled="disabled" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">审核结果</td>
				<td class="value" colspan="3"><select name="tjqr_shjg"
					id="tjqr_shjg" style="width: 100%;">
						<option value="1">通过</option>
						<option value="2">不通过</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">办理意见</td>
				<td class="value" colspan="3"><textarea id="tjqr_opinion"
						name="tjqr_opinion" rows="8" style="width: 98%;"></textarea></td>
			</tr>

			<tr>
				<td class="Label"></td>
				<td class="value" colspan="3"><input id="tjqr_is_send_msg"
					type="checkbox"><label for="tjqr_is_send_msg">是否发送退件短信通知给申报者</label></td>
			</tr>
			<tr class="odd row_tjqr_send_msg_content" style="display: none;">
				<td class="Label">收信人</td>
				<td class="value" colspan="3"><input id="send_msg_recipient"
					name="send_msg_recipient" type="text"></td>
			</tr>
			<tr class="row_tjqr_send_msg_content" style="display: none;">
				<td class="Label">收信号码</td>
				<td class="value" colspan="3"><input id="send_msg_mobile"
					name="send_msg_mobile" type="text"></td>
			</tr>
			<tr class="odd row_tjqr_send_msg_content" style="display: none;">
				<td class="Label">短信内容</td>
				<td class="value" colspan="3"><textarea
						id="tjqr_send_msg_content" name="tjqr_send_msg_content" rows="5"
						style="width: 100%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
