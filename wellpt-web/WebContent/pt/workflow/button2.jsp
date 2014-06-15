<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="buttonForm" name="buttonForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 65px;"><label for="DButton">默认名称</label></td>
				<td><select name="DButton" style="width: 100%"
					class="full-width" emptyMsg="请选择按钮！"></select></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="newName">新的名称</label></td>
				<td><input id="newName" name="newName" type="text"
					class="full-width" emptyMsg="请输入新的名称。"></td>
				<td><input id="newCode" name="newCode" type="hidden" /></td>
			</tr>
			<tr>
				<td><label></label></td>
				<td><div>
						<input
							id="useWay_1" name="useWay" type="radio" value="1"><label
							for="useWay_1">替换原按钮</label> <input id="useWay_2" name="useWay"
							type="radio" value="2"><label for="useWay_2">新增操作</label>
					</div></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="DTask">目标环节</label></td>
				<td><select id="DTask" name="DTask" class="full-width"><option
							value="">--请选择--</option></select></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="button_DUnits">使用人</label></td>
				<td><textarea id="button_DUnits" name="DUnits"
						emptyMsg="请选择使用人" class="full-width"></textarea> <input
					id="button_Units" name="Units" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="button_Dusers">参与人</label></td>
				<td><textarea id="button_Dusers" name="Dusers"
						class="full-width" emptyMsg="请选择使用人"></textarea> <input
					name="Duser1" type="hidden" /> <input name="user1" type="hidden" />
					<input name="user2" type="hidden" /> <input name="user4"
					type="hidden" /> <input name="user8" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="button_DcopyUsers">抄送人</label></td>
				<td><textarea id="button_DcopyUsers" name="DcopyUsers"
						class="full-width" emptyMsg="请选择使用人"></textarea> <input
					name="DcopyUser1" type="hidden" /> <input name="copyUser1"
					type="hidden" /> <input name="copyUser2" type="hidden" /> <input
					name="copyUser4" type="hidden" /> <input name="copyUser8"
					type="hidden" /></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
