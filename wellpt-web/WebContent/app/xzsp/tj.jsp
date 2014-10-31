<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dyform tj_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">操作说明</td>
				<td class="value" colspan="3"><textarea id="tj_remark"
						name="tj_remark" disabled="disabled" style="width: 98%;">对所选择的审批事项进行退件的操作，请输入退件的意见！</textarea></td>
			</tr>
			<tr>
				<td class="Label">退件方式</td>
				<td class="value" colspan="3"><select name="tj_manner"
					id="tj_manner" style="width: 100%;">
						<option value="1">暂缓办理的退件</option>
						<option value="2">无法办理的退件</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">退件原因</td>
				<td class="value" colspan="3"><textarea id="tj_opinion"
						rows="8" name="tj_opinion" style="width: 98%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
