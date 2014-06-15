<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<form id="directionForm" name="directionForm">
		<div class="tabs">
			<ul>
				<li><a href="#tabs-1">基本属性</a></li>
				<li class="fork_condition"><a href="#tabs-2">分支条件</a></li>
				<li><a href="#tabs-3">消息分发</a></li>
			</ul>
			<div id="tabs-1">
				<table>
					<tr>
						<td style="width: 65px;"><label for="name">名称</label></td>
						<td><input id="name" name="name" type="text"
							class="full-width" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label for="id">ID</label></td>
						<td><input id="id" name="id" type="text" class="full-width"></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-2" class="fork_condition">
				<table>
					<tr>
						<td style="width: 65px;"><label>分支条件</label></td>
						<td><input id="isDefault_0" type="radio" name="isDefault"
							value="0" checked="checked"><label for="isDefault_0">设置条件</label>
							<input id="isDefault_1" type="radio" name="isDefault" value="1"><label
							for="isDefault_1">缺省分支(其他分支都不满足时使用此分支)</label></td>
						<td></td>
					</tr>
					<tr id="ID_DefaultDirection">
						<td></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<select id="DCondition" name="DCondition" size="6"
										class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em;">
									<button id="conditionAdd" type="button" class="btn">增加</button>
									<button id="conditionInsert" type="button" class="btn">插入</button>
									<button id="conditionEdit" type="button" class="btn">编辑</button>
									<button id="conditionDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td><input id="isEveryCheck" type="checkbox"
							name="isEveryCheck" value="1"><label for="isEveryCheck">每次提交时检查条件</label></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="tabs-3">
				<table>
					<tr>
						<td style="width: 65px;"><label>文件分发</label></td>
						<td><input id="isSendFile_1" type="radio" name="isSendFile"
							value="1"><label for="isSendFile_1">是</label> <input
							id="isSendFile_0" type="radio" name="isSendFile" value="0"
							checked="checked" /><label for="isSendFile_0">否</label></td>
						<td></td>
					</tr>
					<tr id="ID_IsSendFile" style="display: none;">
						<td class="align-top"><label for="Dir_DfileRecipients">分发对象</label></td>
						<td><textarea id="Dir_DfileRecipients" name="DfileRecipients"
								class="full-width"></textarea> <input name="DfileRecipient1"
							type="hidden" /> <input name="fileRecipient1" type="hidden" />
							<input name="fileRecipient2" type="hidden" /> <input
							name="fileRecipient4" type="hidden" /> <input
							name="fileRecipient8" type="hidden" /></td>
						<td></td>
					</tr>
					<tr>
						<td><label>消息分发</label></td>
						<td><input id="isSendMsg_1" type="radio" name="isSendMsg"
							value="1"><label for="isSendMsg_1">是</label> <input
							id="isSendMsg_0" type="radio" name="isSendMsg" value="0"
							checked="checked"><label for="isSendMsg_0">否</label></td>
						<td></td>
					</tr>
					<tr id="ID_IsSendMsg" style="display: none;">
						<td class="align-top"><label for="Dir_DmsgRecipients">分发对象</label></td>
						<td><textarea id="Dir_DmsgRecipients" name="DmsgRecipients"
								class="full-width"></textarea> <input name="DmsgRecipient1"
							type="hidden" /> <input name="msgRecipient1" type="hidden" /> <input
							name="msgRecipient2" type="hidden" /> <input
							name="msgRecipient4" type="hidden" /> <input
							name="msgRecipient8" type="hidden" /></td>
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
	<div id="dlg_logic"></div>
</div>
