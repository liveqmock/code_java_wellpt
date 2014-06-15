<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="relationForm" name="relationForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 90px;"><label for="newFlowName">子流程</label></td>
				<td><select id="newFlowName" name="newFlowName"
					class="full-width"></select></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="taskName">环节</label></td>
				<td><select id="taskName" name="taskName" class="full-width"></select></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="frontNewFlowName">前置子流程</label></td>
				<td><select id="frontNewFlowName" name="frontNewFlowName"
					class="full-width"><option value="0">--选择子流程--</option></select></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="frontTaskName">前置环节</label></td>
				<td><select id="frontTaskName" name="frontTaskName"
					class="full-width"></select></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
