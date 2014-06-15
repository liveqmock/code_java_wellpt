<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dytable_form zbqr_form">
		<div class="post-sign">
			<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table style="width: 100%">
					<tr class="odd">
						<td class="Label">转报意见</td>
						<td class="value" colspan="3"><textarea id="zb_opinion"
								rows="4" name="zb_opinion" disabled="disabled"
								style="width: 98%;"></textarea></td>
					</tr>
					<tr>
						<td class="Label">转报附件</td>
						<td class="value" colspan="3"><div id="zb_file_upload">
						<input id="zb_file_uploads" name="zb_file_uploads" type="file"/>
						</div></td>
					</tr>
					<tr>
						<td class="Label">审核结果</td>
						<td class="value" colspan="3"><select
							name="zbqr_opinion_value" id="zbqr_opinion_value"
							style="width: 100%;">
								<option value="1">同意</option>
								<option value="2">不同意</option>
						</select></td>
					</tr>
					<tr class="odd">
						<td class="Label">办理意见</td>
						<td class="value" colspan="3"><textarea id="zbqr_opinion"
								name="zbqr_opinion" style="width: 98%;"></textarea></td>
					</tr>

					<tr>
						<td class="Label"></td>
						<td class="value" colspan="3"><input id="zbqr_is_send_msg"
							type="checkbox"><label for="zbqr_is_send_msg">是否发送收件补件短信通知给申报者</label></td>
					</tr>
					<tr class="odd row_zbqr_send_msg_content" style="display: none;">
						<td class="Label">收信人</td>
						<td class="value" colspan="3"><input id="send_msg_recipient"
							name="send_msg_recipient" type="text"></td>
					</tr>
					<tr class="row_zbqr_send_msg_content" style="display: none;">
						<td class="Label">收信号码</td>
						<td class="value" colspan="3"><input id="send_msg_mobile"
							name="send_msg_mobile" type="text"></td>
					</tr>
					<tr class="odd row_zbqr_send_msg_content" style="display: none;">
						<td class="Label">短信内容</td>
						<td class="value" colspan="3"><textarea
								id="zbqr_send_msg_content" name="zbqr_send_msg_content" rows="5"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

</div>
