<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<link href="${ctx}/resources/fileupload/fileupload.css" rel="stylesheet">
<div>
	<div class="dytable_form cwqr_form">
		<div class="post-sign">
			<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table style="width: 100%">
					<tr class="odd">
						<td class="Label">部门出文意见</td>
						<td class="value" colspan="3"><textarea id="cw_opinion"
								name="cw_opinion" disabled="disabled" style="width: 98%;">当前操作步骤</textarea></td>
					</tr>
					<tr>
						<td class="Label">是否合格出文</td>
						<td class="value" colspan="3"><select name="cw_is_qualified"
							id="cw_is_qualified" disabled="disabled" style="width: 100%;">
								<option value="1">合格</option>
								<option value="2">不合格</option>
						</select></td>
					</tr>
					<tr class="odd">
						<td class="Label">出文文件</td>
						<td class="value" colspan="3">
							<div id="cw_file_upload">
							<input id="cw_file_uploads" name="cw_file_uploads" type="file"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="Label">审核结果</td>
						<td class="value" colspan="3"><select
							name="cwqr_opinion_value" id="cwqr_opinion_value"
							style="width: 100%;">
								<option value="1">通过</option>
								<option value="2">不通过</option>
						</select></td>
					</tr>
					<tr class="odd">
						<td class="Label">办理意见</td>
						<td class="value" colspan="3"><textarea id="cwqr_opinion"
								name="cwqr_opinion" style="width: 98%;"></textarea></td>
					</tr>

					<tr>
						<td class="Label"></td>
						<td class="value" colspan="3"><input id="cwqr_is_send_msg"
							type="checkbox"><label for="cwqr_is_send_msg">是否发送办结确认审核意见短信通知给申报者</label></td>
					</tr>
					<tr class="odd row_cwqr_send_msg_content" style="display: none;">
						<td class="Label">收信人</td>
						<td class="value" colspan="3"><input id="send_msg_recipient"
							name="send_msg_recipient" type="text"></td>
					</tr>
					<tr class="row_cwqr_send_msg_content" style="display: none;">
						<td class="Label">收信号码</td>
						<td class="value" colspan="3"><input id="send_msg_mobile"
							name="send_msg_mobile" type="text"></td>
					</tr>
					<tr class="odd row_cwqr_send_msg_content" style="display: none;">
						<td class="Label">短信内容</td>
						<td class="value" colspan="3"><textarea
								id="cwqr_send_msg_content" name="cwqr_send_msg_content" rows="5"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

</div>
