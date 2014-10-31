<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="logicForm" name="logicForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 65px;" class="align-top"><label>条件类型</label></td>
				<td><input id="LogicType_1" type="radio" name="LogicType"
					value="1" checked="checked"><label for="LogicType_1">通过字段值比较设置条件</label>
					<input id="LogicType_2" type="radio" name="LogicType" value="2"><label
					for="LogicType_2">通过投票比例设置条件</label> <input id="LogicType_3"
					type="radio" name="LogicType" value="3"><label
					for="LogicType_3">通过人员归属设置条件</label></td>
				<td></td>
			</tr>
			<tr id="ID_Logic">
				<td colspan="2"><label><font>与前一个条件的逻辑关系</font></label>&nbsp;<select
					name="AndOr" style="width: 50px;"><option value="&"
							selected="selected">并且
						<option value="|">或者</select></td>
				<td></td>
			</tr>
			<tr id="Logic_ID_Field">
				<td colspan="2"><span><select name="LBracket"
						style="width: 50px;"><option selected="selected"> </option>
							<option value="(">(</option></select></span>&nbsp;<span><select
						id="FieldNameLabel" name="FieldNameLabel" style="width: 100px;"><option
								value="">--选择字段--</option></select> <input id="FieldName"
						name="FieldName" type="hidden" /></span> <span><label
						for="FieldNameLabel">&nbsp;浏览</label></span><span> <select
						name="Operator" style="width: 100px;">
							<option value="&gt;">大于</option>
							<option value="&gt;=">大于等于</option>
							<option value="&lt;">小于</option>
							<option value="&lt;=">小于等于</option>
							<option value="==">等于</option>
							<option value="!=">不等于</option>
							<option value="like">包含</option>
							<option value="notlike">不包含</option>
					</select>
				</span><span>&nbsp;<input name="Value" style="width: 200px;" /></span> <span><select
						name="RBracket" style="width: 50px;"><option
								selected="selected"> </option>
							<option value=")">)</option></select></span></td>
				<td></td>
			</tr>
			<tr id="Logic_ID_Vote">
				<td colspan="2"><span><select name="vLBracket"
						style="width: 50px;"><option selected="selected"></option>
							<option value="(">(</option></select></span>&nbsp;<span><select
						name="VoteOption" style="width: 130px;"></select></span>&nbsp;<span><select
						name="vOperator" style="width: 100px;">
							<option VALUE="&gt;" selected="selected">大于</option>
							<option VALUE="&gt;=">大于等于</option>
							<option VALUE="&lt;">小于</option>
							<option VALUE="&lt;=">小于等于</option>
							<option VALUE="==">等于</option>
							<option VALUE="!=">不等于</option>
					</select></span> <span><input name="vValue" style="width: 190px;"
						emptymsg="请输入所占百分比！" errormsg="不需要在所占百分比中输入百分号“％”！">% </span> <span><select
						name="vRBracket" style="width: 50px;"><option
								selected="selected"> </option>
							<option value=")">)</option></select></span></td>
				<td></td>
			</tr>
			<tr id="Logic_ID_Group">
				<td colspan="2"><div>
						<div style="float: left;">
							<span><select name="gLBracket"
								style="width: 50px; margin-top: 2em;"><option
										selected="selected"> </option>
									<option value="(">(</option></select></span>&nbsp;
						</div>
						<div style="float: left;">
							<div style="display: inline-block;">
								<select name="GroupType" style="width: 200px;"><option
										value="">--选择成员--</option>
									<option value="1">当前办理人为所选人员之一</option>
									<option value="0">表单字段所选人员为所选人员之一</option></select><br /> <span
									id="ID_UserField"><label for="UserFieldLabel"></label><br />
									<select id="UserFieldLabel" name="UserFieldLabel"
									style="width: 200px;"><option value="">--选择表单字段--</option></select>
									<input id="UserField" name="UserField" type="hidden" /></span>
							</div>
							<span><textarea id="DGroups" name="DGroups"
									emptymsg="请指定群组！" style="width: 235px;"></textarea> <input
								name="Groups" type="hidden" /> <input name="DGroup1"
								type="hidden" /> <input name="Group1" type="hidden" /> <input
								name="Group2" type="hidden" /> <input name="Group4"
								type="hidden" /> <input name="Group8" type="hidden" /></span>
						</div>
						<div style="float: left;">
							<span>&nbsp;<select name="gRBracket"
								style="width: 50px; margin-top: 2em;"><option
										selected="selected"> </option>
									<option value=")">)</option></select></span>
						</div>
						<div style="clear: both;"></div>
					</div></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
