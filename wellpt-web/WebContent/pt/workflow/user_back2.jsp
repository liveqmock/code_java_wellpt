<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="userBackForm" name="userBackForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 65px;" class="align-top"><label for="AUser">A岗人员</label></td>
				<td><input id="AUser" name="AUser" class="full-width" /><input
					name="AUserID" value="" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td style="width: 65px;" class="align-top"><label for="BUsers">B岗人员</label></td>
				<td><textarea id="BUsers" name="BUsers" class="full-width"></textarea><input
					name="BUserIDs" value="" type="hidden"></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
