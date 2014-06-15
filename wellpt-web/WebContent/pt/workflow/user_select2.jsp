<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="userSelectForm" name="userSelectForm">
		<table style="width: 100%;">
			<tr id="ID_Unit">
				<td style="width: 65px;" class="align-top"><label for="DUnits">组织机构</label></td>
				<td colspan="2"><textarea id="DUnits" name="DUnits" rows="2"
						class="full-width"></textarea><input id="Units" name="Units"
					value="" type="hidden" /></td>
			</tr>
			<tr id="ID_Biz_Role">
				<td class="align-top"><label for="ID_Biz_Roles">单位业务角色</label></td>
				<td colspan="2"><textarea id="DBizRoles" name="DBizRoles"
						rows="2" class="full-width"></textarea><input id="BizRoles"
					name="BizRoles" type="hidden" /></td>
			</tr>
			<tr id="ID_Field">
				<td class="align-top"><label for="FieldLabels">文 档 域</label></td>
				<td colspan="2"><textarea id="FieldLabels" name="FieldLabels"
						rows="2" class="full-width"></textarea><input id="Fields"
					name="Fields" type="hidden" /></td>
			</tr>
			<tr id="ID_Task">
				<td style="width: 65px;" class="align-top"><label
					for="ID_TaskList">办理环节</label></td>
				<td colspan="2"><div id="ID_TaskList"
						style="text-align: left; overflow: auto; border-width: thin; border: 1px solid #79b7e7;"></div></td>
			</tr>
			<tr id="ID_Direction">
				<td style="width: 65px;" class="align-top"><label
					for="ID_DirectionList">工作流向</label></td>
				<td colspan="2"><div id="ID_DirectionList"
						style="text-align: left; overflow: auto; border-width: thin; border: 1px solid #79b7e7;"></div></td>
			</tr>
			<tr id="ID_Option_0">
				<td class="align-top"><label>人员选项</label></td>
				<td><input type="checkbox" name="UserOptions" value="PriorUser"
					id="useroptions_1" /><label for="useroptions_1">前办理人</label><br>
					<input type="checkbox" name="UserOptions" value="LeaderOfPriorUser"
					id="useroptions_2" /><label for="useroptions_2">前办理人的直接领导</label><br>
					<input type="checkbox" name="UserOptions"
					value="AllLeaderOfPriorUser" id="useroptions_3" /><label
					for="useroptions_3">前办理人的所有领导</label><br> <input
					type="checkbox" name="UserOptions" value="Creator"
					id="useroptions_4" /><label for="useroptions_4">申请人</label><br>
					<input type="checkbox" name="UserOptions" value="LeaderOfCreator"
					id="useroptions_5" /><label for="useroptions_5">申请人的直接领导</label><br>
					<input type="checkbox" name="UserOptions"
					value="AllLeaderOfCreator" id="useroptions_6" /><label
					for="useroptions_6">申请人的所有领导</label><br> <input
					type="checkbox" name="UserOptions" value="PriorTaskUser"
					id="useroptions_7" /><label for="useroptions_7">前一个环节办理人</label></td>
				<td><input type="checkbox" name="UserOptions"
					value="DeptOfPriorUser" id="useroptions_8" /><label
					for="useroptions_8">前办理人的部门</label><br> <input type="checkbox"
					name="UserOptions" value="ParentDeptOfPriorUser" id="useroptions_9" /><label
					for="useroptions_9">前办理人的上级部门</label><br> <input
					type="checkbox" name="UserOptions" value="RootDeptOfPriorUser"
					id="useroptions_10" /><label for="useroptions_10">前办理人的根部门</label><br>
					<input type="checkbox" name="UserOptions" value="DeptOfCreator"
					id="useroptions_11" /><label for="useroptions_11">申请人的部门</label><br>
					<input type="checkbox" name="UserOptions"
					value="ParentDeptOfCreator" id="useroptions_12" /><label
					for="useroptions_12">申请人的上级部门</label><br> <input
					type="checkbox" name="UserOptions" value="RootDeptOfCreator"
					id="useroptions_13" /><label for="useroptions_13">申请人的根部门</label><br>
					<input type="checkbox" name="UserOptions" value="Corp"
					id="useroptions_14" /><label for="useroptions_14">全组织</label></td>
			</tr>
			<tr id="ID_Option_1">
				<td class="align-top"><label>人员过滤</label></td>
				<td><input type="checkbox" name="UserOptions"
					value="SameDeptAsPrior" id="useroptions_15" /><label
					for="useroptions_15">限于前办理人的直系部门人员</label><br> <input
					type="checkbox" name="UserOptions" value="SameRootDeptAsPrior"
					id="useroptions_16" /><label for="useroptions_16">限于前办理人的同一根部门人员</label><br>
					<input type="checkbox" name="UserOptions"
					value="SameAllLeaderAsPrior" id="useroptions_17" /><label
					for="useroptions_17">限于前办理人的所有领导</label></td>
				<td><input type="checkbox" name="UserOptions"
					value="SameDeptAsCreator" id="useroptions_18" /><label
					for="useroptions_18">限于申请人的直系部门人员</label><br> <input
					type="checkbox" name="UserOptions" value="SameRootDeptAsCreator"
					id="useroptions_19" /><label for="useroptions_19">限于申请人的同一根部门人员</label><br>
					<input type="checkbox" name="UserOptions"
					value="SameAllLeaderAsCreator" id="useroptions_20" /><label
					for="useroptions_20">限于申请人的所有领导</label></td>
			</tr>
		</table>
	</form>
</div>
