<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<style>
<!--
.ui-dialog {
	overflow: visible;
}

.ui-dialog .ui-dialog-content {
	overflow: visible;
}
-->
</style>
<div class="tabs ui-widget ui-widget-content" style="border: none;">
	<form id="newFlowForm" name="newFlowForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 90px;"><label for="name">流程名称</label></td>
				<td><input id="newFlowName" name="newFlowName" type="text"
					class="full-width" /> <input id="newFlowValue" name="newFlowValue"
					type="hidden" value="" /></td>
				<td></td>
			</tr>
			<tr style="display: none;">
				<td><label for="conditionName">触发条件</label></td>
				<td><input id="conditionName" name="conditionName" type="text"
					class="full-width" /> <input id="conditionValue"
					name="conditionValue" type="hidden" value="" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="createName">流程实例</label></td>
				<td><span> <label>创建方式</label> <select id="createWay"
						style="width: 120px;">
							<option value="1">主表</option>
							<option value="2">从表</option>
							<option value="3">自定义</option>
					</select>
				</span> <span id="span_createInstanceWay" style="display: none;"> <label>实例数量</label>
						<select id="createInstanceWay" style="width: 120px;">
							<option value="1">单一实例</option>
							<option value="2">按办理人生成</option>
					</select>
				</span> <span id="span_subformId" style="display: none;"><label>选择从表</label>
						<select id="subformId" style="width: 120px;">
						    <!-- modified by huanglinchuan 2014.10.23 begin -->
						    <!-- 
							<option value="1">单一实例</option>
							<option value="2">按办理人生成</option>
							 -->
							
					</select> </span>
					<p /> <span id="span_title_users"><!-- <label>流程标题</label><select
						id="title" name="title" style="width: 120px;">
							<option value="-1">--请选择字段--</option>
					</select> --> <label>办理人</label><select id="taskUsers" name="taskUsers"
						style="width: 120px;">
							<option value="-1">--请选择字段--</option>
					</select></span>
					<!-- modified by huanglinchuan 2014.10.23 end -->
					<p /> <span id="span_interface" style="display: none;"><label>接口实现</label>
						<select id="interface"></select></span> <select id="createName"
					name="createName" class="full-width" style="display: none;"><option
							value="-1">--请选择字段--</option></select> <input id="createValue"
					name="createValue" type="hidden" class="full-width" /></td>
				<td></td>
			</tr>
			<tr>
				<td><label></label></td>
				<td><span id="span_isMerge"><input id="isMerge"
						name="isMerge" type="checkbox" value="0" /><label for="isMerge">合并流程</label></span>
					<span id="span_isWait"><input id="isWait" name="isWait"
						type="checkbox" value="0" /><label for="isWait">合并等待</label></span> <span><input
						id="isShare" name="isShare" type="checkbox" value="0" /><label
						for="isShare">共享流程</label></span></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="toTaskName">提交环节</label></td>
				<td><select id="toTaskName" name="toTaskName"
					class="full-width"></select></td>
				<td></td>
			</tr>
			<!-- modified by huanglinchuan 2014.10.23 begin -->
			<tr>
				<td class="align-top"><label for="copyFieldNames">拷贝信息</label></td>
				<td>主流程表单字段&gt;&gt;子流程表单字段<br/><textarea id="copyFieldNames" name="copyFieldNames"
						class="full-width" rows="5"></textarea> <input id="copyFields"
					name="copyFields" type="hidden" /></td>
				<td></td>
			</tr>
			<!-- returnFieldNames returnFields -->
			<tr>
				<td class="align-top"><label for="returnOverrideFieldNames">返回信息覆盖</label></td>
				<td>主流程表单字段&lt;&lt;子流程表单字段<br/><textarea id="returnOverrideFieldNames"
						name="returnOverrideFieldNames" class="full-width" rows="3"></textarea> <input
					id="returnOverrideFields" name="returnOverrideFields" type="hidden" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="align-top"><label for="returnAdditionFieldNames">返回信息附加</label></td>
				<td>主流程表单字段&lt;&lt;子流程表单字段<br/><textarea id="returnAdditionFieldNames"
						name="returnAdditionFieldNames" class="full-width" rows="3"></textarea> <input
					id="returnAdditionFields" name="returnAdditionFields" type="hidden" /></td>
				<td></td>
			</tr>
			<!-- modified by huanglinchuan 2014.10.23 end -->
			<tr>
				<td></td>
				<td><input id="notifyDoing" name="notifyDoing" type="checkbox"
					value="1" /><label for="notifyDoing">办结通知其他子流程在办人员</label></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
