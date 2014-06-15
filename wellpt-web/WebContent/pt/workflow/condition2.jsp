<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<form id="conditionForm" name="conditionForm">
		<div class="tabs">
			<ul>
				<li><a href="#tabs-1">基本属性</a></li>
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
						<td class="align-top"><label for="description">描述</label></td>
						<td><textarea id="description" name="description" rows="5"
								class="full-width"></textarea></td>
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
</div>
