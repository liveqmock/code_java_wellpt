<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dytable_form sj_form">
		<div class="post-sign">
			<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table style="width: 100%">
					<tr class="odd">
						<td class="Label">操作说明</td>
						<td class="value" colspan="3"><textarea id="sj_remark"
								name="sj_remark" disabled="disabled" style="width: 98%;">收件后开始计算时间</textarea></td>
					</tr>
					<tr>
						<td class="Label">受理承诺天数</td>
						<td class="value" colspan="3"><select name="sj_time_limit"
							id="sj_time_limit" style="width: 100%;">
								<option value="1">1个工作日</option>
								<option value="2">2个工作日</option>
						</select></td>
					</tr>

					<tr class="odd">
						<td class="Label"></td>
						<td class="value" colspan="3"><input id="sj_is_send_msg"
							type="checkbox"><label for="sj_is_send_msg">是否发送收件短信通知给申报者</label></td>
					</tr>
					<tr class="row_sj_send_msg_content" style="display: none;">
						<td class="Label">收信人</td>
						<td class="value" colspan="3"><input id="send_msg_recipient"
							name="send_msg_recipient" type="text"></td>
					</tr>
					<tr class="odd row_sj_send_msg_content" style="display: none;">
						<td class="Label">收信号码</td>
						<td class="value" colspan="3"><input id="send_msg_mobile"
							name="send_msg_mobile" type="text"></td>
					</tr>
					<tr class="row_sj_send_msg_content" style="display: none;">
						<td class="Label">短信内容</td>
						<td class="value" colspan="3"><textarea
								id="sj_send_msg_content" name="sj_send_msg_content" rows="5"></textarea></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

</div>
