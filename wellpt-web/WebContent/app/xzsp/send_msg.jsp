<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dyform fsdx_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">收信人</td>
				<td class="value" colspan="3"><input id="send_msg_recipient"
					name="send_msg_recipient" type="text"></td>
			</tr>
			<tr>
				<td class="Label">收信号码</td>
				<td class="value" colspan="3"><input id="send_msg_mobile"
					name="send_msg_mobile" type="text"></td>
			</tr>
			<tr class="odd">
				<td class="Label" colspan="3">短信内容</td>
				<td><textarea id="fsdx_send_msg_content"
						name="fsdx_send_msg_content" rows="5" style="width: 100%;">${template.smsBody}</textarea></td>
			</tr>
		</table>
	</div>
</div>
