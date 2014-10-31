<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dyform gctjqr_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">操作说明</td>
				<td class="value" colspan="3"><textarea id="gctjqr_remark"
						name="gctjqr_remark" disabled="disabled" style="width: 98%;">对所选择的审批事项进行过程退件确认，请输入过程退件确认的意见！</textarea></td>
			</tr>
			<tr>
				<td class="Label">过程退件方式</td>
				<td class="value" colspan="3"><select name="gctj_manner"
					id="gctj_manner" style="width: 100%;" disabled="disabled">
						<option value="1">暂缓办理的过程退件</option>
						<option value="2">无法办理的过程退件</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">过程退件意见</td>
				<td class="value" colspan="3"><textarea id="gctj_opinion"
						name="gctj_opinion" disabled="disabled" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">审核结果</td>
				<td class="value" colspan="3"><select name="gctjqr_shjg"
					id="gctjqr_shjg" style="width: 100%;">
						<option value="1">通过</option>
						<option value="2">不通过</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">审核意见</td>
				<td class="value" colspan="3"><textarea id="gctjqr_opinion"
						name="gctjqr_opinion" rows="8" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">过程退件附件</td>
				<td class="value" colspan="3"><div id="gctj_file_upload">
						<input id="gctj_file_uploads" name="gctj_file_uploads" type="file" />
					</div></td>
			</tr>

			<tr class="odd">
				<td class="Label"></td>
				<td class="value" colspan="3"><input id="gctjqr_is_send_msg"
					type="checkbox"><label for="gctjqr_is_send_msg">是否发送收件短信通知给申报者</label></td>
			</tr>
			<tr class="row_gctjqr_send_msg_content" style="display: none;">
				<td class="Label">收信人</td>
				<td class="value" colspan="3"><input id="send_msg_recipient"
					name="send_msg_recipient" type="text"></td>
			</tr>
			<tr class="odd row_gctjqr_send_msg_content" style="display: none;">
				<td class="Label">收信号码</td>
				<td class="value" colspan="3"><input id="send_msg_mobile"
					name="send_msg_mobile" type="text"></td>
			</tr>
			<tr class="row_gctjqr_send_msg_content" style="display: none;">
				<td class="Label">短信内容</td>
				<td class="value" colspan="3"><textarea
						id="gctjqr_send_msg_content" name="gctjqr_send_msg_content"
						rows="5" style="width: 100%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
