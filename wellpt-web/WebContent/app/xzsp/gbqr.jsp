<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<div class="dyform gbqr_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">过程补件要求</td>
				<td class="value" colspan="3"><select name="gb_required"
					id="gb_required" disabled="disabled" style="width: 100%;">
						<option value="1">贵单位要在5日内补齐所缺材料，否则做退件处理</option>
						<option value="2">贵单位要在10日内补齐所缺材料，否则做退件处理</option>
				</select></td>
			</tr>
			<tr>
				<td class="Label">过程补件意见</td>
				<td class="value" colspan="3"><textarea id="gb_opinion"
						name="gb_opinion" disabled="disabled" style="width: 98%;"></textarea></td>
			</tr>
			<tr class="odd">
				<td class="Label" colspan="3">过程补件列表</td>
				<td><table id="gbqr_file_list"></table>
					<div id="gbqr_file_list_pager"></div></td>
			</tr>
			<tr>
				<td class="Label">审核结果</td>
				<td class="value" colspan="3"><select name="gbqr_opinion_value"
					id="gbqr_opinion_value" style="width: 100%;">
						<option value="1">同意</option>
						<option value="2">不同意</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">办理意见</td>
				<td class="value" colspan="3"><textarea id="gbqr_opinion"
						name="gbqr_opinion" style="width: 98%;"></textarea></td>
			</tr>

			<tr>
				<td class="Label"></td>
				<td class="value" colspan="3"><input id="gbqr_is_send_msg"
					type="checkbox"><label for="gbqr_is_send_msg">是否发送收件补件短信通知给申报者</label></td>
			</tr>
			<tr class="odd row_gbqr_send_msg_content" style="display: none;">
				<td class="Label">收信人</td>
				<td class="value" colspan="3"><input id="send_msg_recipient"
					name="send_msg_recipient" type="text"></td>
			</tr>
			<tr class="row_gbqr_send_msg_content" style="display: none;">
				<td class="Label">收信号码</td>
				<td class="value" colspan="3"><input id="send_msg_mobile"
					name="send_msg_mobile" type="text"></td>
			</tr>
			<tr class="odd row_gbqr_send_msg_content" style="display: none;">
				<td class="Label">短信内容</td>
				<td class="value" colspan="3"><textarea
						id="gbqr_send_msg_content" name="gbqr_send_msg_content" rows="5"
						style="width: 100%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
