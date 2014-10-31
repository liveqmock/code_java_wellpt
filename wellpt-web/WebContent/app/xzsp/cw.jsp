<%@page import="java.util.Calendar"%>
<%@page import="com.wellsoft.pt.utils.date.DateUtils"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<link href="${ctx}/resources/fileupload/fileupload.css" rel="stylesheet">
<div>
	<div class="dyform cw_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">是否合格出文</td>
				<td class="value" colspan="3"><select name="cw_is_qualified"
					id="cw_is_qualified" style="width: 100%;">
						<option value="1">合格</option>
						<option value="2">不合格</option>
				</select></td>
			</tr>
			<tr>
				<td class="Label">出文文件</td>
				<td class="value" colspan="3">
					<div id="cw_file_upload">
						<input id="cw_file_uploads" name="cw_file_uploads" type="file" />
					</div>
				</td>
			</tr>
			<tr class="odd">
				<td class="Label">批文编号</td>
				<td class="value" colspan="3"><input id="cw_file_code"
					name="cw_file_code" type="text" /></td>
			</tr>
			<tr>
				<td class="Label">下次更新日期</td>
				<td class="value" colspan="3"><input
					id="cw_file_next_update_time" name="cw_file_next_update_time"
					type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
					class="Wdate full-width" /></td>
			</tr>
			<tr class="odd">
				<td class="Label">办理时间</td>
				<td class="value" colspan="3">受理时间：<span id="cw_slsj"></span>
					&nbsp;&nbsp;当前时间：<%=DateUtils.formatDate(Calendar.getInstance().getTime())%></td>
			</tr>
			<tr>
				<td class="Label">特殊环节</td>
				<td class="value" colspan="3"></td>
			</tr>
			<tr class="odd">
				<td class="Label">办理意见</td>
				<td class="value" colspan="3"><textarea id="cw_opinion"
						rows="4" name="cw_opinion" style="width: 98%;"></textarea></td>
			</tr>

			<tr>
				<td class="Label"></td>
				<td class="value" colspan="3"><input id="cw_is_send_msg"
					type="checkbox"><label for="cw_is_send_msg">是否发送办结短信通知给申报者</label></td>
			</tr>
			<tr class="odd row_cw_send_msg_content" style="display: none;">
				<td class="Label">收信人</td>
				<td class="value" colspan="3"><input id="send_msg_recipient"
					name="send_msg_recipient" type="text"></td>
			</tr>
			<tr class="row_cw_send_msg_content" style="display: none;">
				<td class="Label">收信号码</td>
				<td class="value" colspan="3"><input id="send_msg_mobile"
					name="send_msg_mobile" type="text"></td>
			</tr>
			<tr class="odd row_cw_send_msg_content" style="display: none;">
				<td class="Label">短信内容</td>
				<td class="value" colspan="3"><textarea
						id="cw_send_msg_content" name="cw_send_msg_content" rows="5"
						style="width: 100%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
