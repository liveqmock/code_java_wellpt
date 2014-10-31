<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<form id="taskForm" name="taskForm">
		<div class="tabs">
			<ul>
				<li><a href="#tabs-1">基本设置</a></li>
				<li><a href="#tabs-2">权限设置</a></li>
				<li><a href="#tabs-3">表单控制</a></li>
				<li><a href="#tabs-4">运转模式</a></li>
				<li><a href="#tabs-5">高级设置</a></li>
			</ul>
			<div id="tabs-1">
				<table>
					<tr>
						<td style="width: 75px;"><label for="name">名称</label></td>
						<td><input id="name" name="name" type="text"
							class="full-width" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="id">ID</label></td>
						<td><input id="id" name="id" type="text" class="full-width"></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="isSetUser">办理人</label></td>
						<td><select id="isSetUser" name="isSetUser"
							class="full-width">
								<option value="1">现在确定</option>
								<option value="2" selected="selected">由前一环节办理人确定</option>
						</select></td>
						<td></td>
					</tr>
					<tr id="ID_SetUser_0" style="display: none;">
						<td class="align-top"><label for="task_Dusers">选择承办人</label></td>
						<td><textarea id="task_Dusers" name="Dusers"
								class="full-width"></textarea> <input name="Duser1"
							type="hidden" /> <input name="user1" type="hidden" /> <input
							name="user2" type="hidden" /> <input name="user4" type="hidden" />
							<input name="user8" type="hidden" /><input name="Duser16"
							type="hidden" /> <input name="user16" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="isSetCopyUser">抄送</label></td>
						<td><select id="isSetCopyUser" name="isSetCopyUser"
							class="full-width">
								<option value="0" selected="selected">不设置抄送人</option>
								<option value="1">现在确定</option>
								<option value="2">由前一环节办理人确定</option>
						</select></td>
						<td></td>
					</tr>
					<tr id="ID_SetUser_1" style="display: none;">
						<td class="align-top"><label for="task_DcopyUsers">选择抄送人</label></td>
						<td><textarea id="task_DcopyUsers" name="DcopyUsers"
								class="full-width"></textarea> <input name="DcopyUser1"
							type="hidden" /> <input name="copyUser1" type="hidden" /> <input
							name="copyUser2" type="hidden" /> <input name="copyUser4"
							type="hidden" /> <input name="copyUser8" type="hidden" /> <input
							name="DcopyUser16" type="hidden" /> <input name="copyUser16"
							type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="checkbox" id="isSetUserEmpty"
							name="isSetUserEmpty" value="1" /><label for="isSetUserEmpty">办理人为空自动进入下一个环节</label>
							<span id="ID_SetUserEmpty1" style="display: none"><input
								id="DemptyToTask" name="DemptyToTask" onfocus="this.blur()"
								emptymsg="请指定办理人为空自动进入的环节！" multiplemsg="不支持多个环节，请选择一个环节！" /> <label
								for="DemptyToTask">选择</label><input name="emptyToTask"
								type="hidden" /></span></td>
						<td></td>
					</tr>
					<tr id="ID_SetUserEmpty2" style="display: none;">
						<td></td>
						<td><input id="emptyNoteDone" name="emptyNoteDone"
							type="checkbox" value="1" /><label for="emptyNoteDone">办理人为空转办时消息通知已办人员</label></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label>多办理人时</label></td>
						<td><input type="checkbox" id="isSelectAgain"
							name="isSelectAgain" value="1" /><label for="isSelectAgain">选择具体办理人</label>
							<span id="ID_OnlyOne" style="display: none"><input
								type="checkbox" id="isOnlyOne" name="isOnlyOne" value="1"><label
								for="isOnlyOne">只能选择一个人办理</label></span><br> <span><input
								type="checkbox" id="isAnyone" name="isAnyone" value="1" /><label
								for="isAnyone">只需要其中一个人办理</label></span> <span><input
								type="checkbox" id="isByOrder" name="isByOrder" value="1" /><label
								for="isByOrder">按人员顺序依次办理</label></span></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="sameUserSubmit">与前环节相同</label></td>
						<td><select id="sameUserSubmit" name="sameUserSubmit"
							class="full-width">
								<option value="2">不提醒环节变化，等待办理人提交</option>
								<option value="0" selected>自动提交，让办理人确认是否继承上一环节意见</option>
								<option value="1">自动提交，且自动继承意见</option>
						</select></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="isSetMonitor">督办人</label></td>
						<td><select id="isSetMonitor" name="isSetMonitor"
							class="full-width">
								<option value="0" selected>不设置督办人</option>
								<option value="1">现在确定</option>
								<option value="2">由前一环节办理人确定</option>
						</select></td>
						<td></td>
					</tr>
					<tr id="ID_SetMonitor">
						<td class="align-top"><label for="task_Dmonitors">选择督办人</label></td>
						<td><textarea id="task_Dmonitors" name="Dmonitors" rows="1"
								class="full-width"></textarea> <input name="Dmonitor1"
							type="hidden" /> <input name="monitor1" type="hidden" /> <input
							name="monitor2" type="hidden" /> <input name="monitor4"
							type="hidden" /> <input name="monitor8" type="hidden" /></td>
						<td></td>
					</tr>
					<tr id="ID_InheritMonitors">
						<td></td>
						<td><input id="isInheritMonitor" name="isInheritMonitor"
							type="checkbox" value="1" /><label for="isInheritMonitor">同时继承已存在的督办人</label></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-2">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-21">待办权限</a></li>
						<li><a href="#tabs-22">已办权限</a></li>
						<li><a href="#tabs-23">督办权限</a></li>
						<li><a href="#tabs-24">监控权限</a></li>
					</ul>
					<div id="tabs-21">
						<table>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DTaskRight">办理权限</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<select id="DTaskRight" name="DTaskRight" size="5"
												class="full-width" title="办理权限">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<button id="todoRightAdd" type="button" class="btn">增加</button>
											<button id="todoRightDelete" type="button" class="btn">删除</button>
											<button id="todoRightClear" type="button" class="btn">清空
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DButton">操作定义</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<label>默认名称->新的名称(目标环节)</label> <select id="DButton"
												name="DButton" size="5" class="full-width">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<label>&nbsp;</label>
											<button id="buttonAdd" type="button" class="btn">增加</button>
											<button id="buttonEdit" type="button" class="btn">编辑</button>
											<button id="buttonDelete" type="button" class="btn">删除
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
							<tr style="display: none;">
								<td></td>
								<td><textarea name="DuntreadTasks" class="full-width"></textarea></td>
								<td><input name="untreadTasks" type="hidden" /></td>
							</tr>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DOption">意见立场</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<label>名称|值</label> <select id="DOption" name="DOption"
												size="5" class="full-width">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<label>&nbsp;</label>
											<button id="optionAdd" type="button" class="btn">增加</button>
											<button id="optionDelete" type="button" class="btn">删除</button>
											<button id="optionDefault" type="button" class="btn">默认
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-22">
						<table>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DTaskDoneRight">已办权限</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<select id="DTaskDoneRight" name="DTaskDoneRight" size="5"
												class="full-width">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<button id="doneRightAdd" type="button" class="btn">增加</button>
											<button id="doneRightDelete" type="button" class="btn">删除</button>
											<button id="doneRightClear" type="button" class="btn">清空
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-23">
						<table>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DTaskMonitorRight">督办权限</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<select id="DTaskMonitorRight" name="DTaskMonitorRight"
												size="5" class="full-width">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<button id="monitorRightAdd" type="button" class="btn">增加</button>
											<button id="monitorRightDelete" type="button" class="btn">删除</button>
											<button id="monitorRightClear" type="button" class="btn">清空
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="tabs-24">
						<table>
							<tr>
								<td style="width: 65px;" class="align-top"><label
									for="DTaskAdminRight">监控权限</label></td>
								<td>
									<div>
										<div style="float: left; width: 85%">
											<select id="DTaskAdminRight" name="DTaskAdminRight" size="5"
												class="full-width">
											</select>
										</div>
										<div style="float: right; width: 4em;">
											<button id="adminRightAdd" type="button" class="btn">增加</button>
											<button id="adminRightDelete" type="button" class="btn">删除</button>
											<button id="adminRightClear" type="button" class="btn">清空
											</button>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div id="tabs-3">
				<table>
					<tr>
						<td style="width: 65px;"><label for="DForm">使用表单</label></td>
						<td><select id="DForm" name="DForm" class="full-width">
								<option value="-1">--请选择--</option>
						</select></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="editFieldLabels">编辑域</label></td>
						<td><textarea id="editFieldLabels" name="editFieldLabels"
								class="full-width"></textarea> <input id="editFields"
							name="editFields" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="readFieldLabels">只读域</label></td>
						<td><textarea id="readFieldLabels" name="readFieldLabels"
								class="full-width"></textarea> <input id="readFields"
							name="readFields" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="hideFieldLabels">隐藏域</label></td>
						<td><textarea id="hideFieldLabels" name="hideFieldLabels"
								class="full-width"></textarea> <input id="hideFields"
							name="hideFields" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="notNullFieldLabels">必填域</label></td>
						<td><textarea id="notNullFieldLabels"
								name="notNullFieldLabels" class="full-width"></textarea> <input
							id="notNullFields" name="notNullFields" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="hideBlockLabels">隐藏区块</label></td>
						<td><textarea id="hideBlockLabels"
								name="hideBlockLabels" class="full-width"></textarea> <input
							id="hideBlocks" name="hideBlocks" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="DRemark">信息记录</label></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<select id="DRemark" name="DRemark" size="4" class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em;">
									<button id="remarkAdd" type="button" class="btn">增加</button>
									<button id="remarkEdit" type="button" class="btn">编辑</button>
									<button id="remarkDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-4">
				<table>
					<tr>
						<td style="width: 65px;"><label for="forkMode">分支模式</label></td>
						<td><input id="forkMode_1" name="forkMode" type="radio"
							value="1" checked="checked" /><label for="forkMode_1">单一分支</label>
							<input id="forkMode_2" name="forkMode" type="radio" value="2" /><label
							for="forkMode_2">多路分支</label> <input id="forkMode_3"
							name="forkMode" type="radio" value="3" /><label for="forkMode_3">全部分支</label></td>
					</tr>
					<tr>
						<td class="align-top"><label for="joinMode">聚合模式</label></td>
						<td><input id="joinMode_1" name="joinMode" type="radio"
							value="1" checked="checked" /><label for="joinMode_1">单一聚合</label>
							<input id="joinMode_2" name="joinMode" type="radio" value="2" /><label
							for="joinMode_2">多路聚合</label> <input id="joinMode_3"
							name="joinMode" type="radio" value="3" /><label for="joinMode_3">全部聚合</label></td>
					</tr>
				</table>
			</div>
			<div id="tabs-5">
				<table>
					<tr>
						<td style="width: 65px;" class="align-top"><label for="sn">环节编号</label></td>
						<td><input id="sn" name="sn" type="text" class="full-width" /></td>
						<td></td>
					</tr>
					<tr>
						<td style="width: 65px;" class="align-top"><label
							for="snName">生成流水号</label></td>
						<td><textarea id="snName" name="snName" class="full-width"></textarea><input
							id="serialNo" name="serialNo" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td style="width: 65px;" class="align-top"><label
							for="task_printTemplate">打印模板</label></td>
						<td><textarea id="task_printTemplate" name="printTemplate"
								class="full-width"></textarea><input id="task_printTemplateId"
							name="printTemplateId" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top"><label for="listenerName">事件监听</label></td>
						<td><textarea id="listenerName" name="listenerName"
								class="full-width"></textarea><input id="listener"
							name="listener" type="hidden" /></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btn-group btn-group-bottom">
			<button id="btn_save" type="button" class="btn"
				style="display: none;">保存</button>
			<button id="btn_abandon" type="button" class="btn"
				style="display: none;">放弃</button>
		</div>
	</form>
	<div id="dlg_button_define"></div>
	<div id="dlg_option"></div>
	<div id="dlg_remark"></div>
</div>
