<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<form id="subFlowForm" name="subFlowForm">
		<div class="tabs">
			<ul>
				<li><a href="#tabs-1">子流程</a></li>
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
					<tr>
						<td class="align-top" style="padding-top: 1.6em;"><label
							for="DTimer">子流程</label></td>
						<td>
							<div>
								<div style="float: left; width: 85%">
									<label>流程名称&nbsp;&nbsp;|&nbsp;&nbsp;创建方式&nbsp;&nbsp;|&nbsp;&nbsp;提交环节</label>
									<select id="DNewFlows" name="DNewFlows" size="6"
										class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em; margin-top: 1.3em;">
									<button id="newFlowAdd" type="button" class="btn">增加</button>
									<button id="newFlowEdit" type="button" class="btn">编辑</button>
									<button id="newFlowDelete" type="button" class="btn">删除</button>
								</div>
							</div>
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="align-top" style="padding-top: 1.6em;"><label
							for="DRelations">前置关系</label></td>
						<td><div>
								<div style="float: left; width: 85%">
									<label>子流程&nbsp;&nbsp;|&nbsp;&nbsp;环节&nbsp;&nbsp;|&nbsp;&nbsp;前置子流程&nbsp;&nbsp;|&nbsp;&nbsp;前置环节</label>
									<select id="DRelations" name="DRelations" size="6"
										class="full-width">
									</select>
								</div>
								<div style="float: right; width: 4em; margin-top: 1.3em;">
									<button id="relationAdd" type="button" class="btn">增加</button>
									<button id="relationEdit" type="button" class="btn">编辑</button>
									<button id="relationDelete" type="button" class="btn">删除</button>
								</div>
							</div></td>
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
	<div id="dlg_new_flow"></div>
	<div id="dlg_select_field"></div>
	<div id="dlg_new_relation"></div>
</div>
