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
	<form id="remarkForm" name="remarkForm">
		<table style="width: 100%;">
			<tr>
				<td style="width: 65px;"><label for="name">信息名称</label></td>
				<td><input name="name" class="full-width" /></td>
				<td></td>
			</tr>
			<tr>
				<td><label for="fieldLabel">显示位置</label></td>
				<td><input id="fieldLabel" name="fieldLabel" class="full-width" />
					<input id="field" name="field" type="hidden" class="full-width" /></td>
				<td></td>
			</tr>
			<tr>
				<td><label>记录方式</label></td>
				<td><input id="way_1" name="way" type="radio" value="1"><label
					for="way_1">重复不替换</label> <input id="way_2" name="way" type="radio"
					value="2"><label for="way_2">重复替换</label> <input id="way_3"
					name="way" type="radio" value="3"><label for="way_3">附加</label>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><label for="DFormat">信息格式</label></td>
				<td><select name="DFormat" class="full-width"></select></td>
				<td></td>
			</tr>
		</table>
	</form>
</div>
