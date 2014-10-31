<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<div class="dyform gcbjdj_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">补件原因</td>
				<td class="value" colspan="3"><textarea id="gcbj_reason"
						name="gcbj_reason" disabled="disabled" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">补件备注</td>
				<td class="value" colspan="3"><textarea id="bj_remark"
						name="bj_remark" disabled="disabled" style="width: 98%;"></textarea></td>
			</tr>
			<tr class="odd">
				<td class="Label" colspan="3">补办材料</td>
				<td><table id="gcbjdj_file_list"></table>
					<div id="gcbjdj_file_list_pager"></div></td>
			</tr>
			<tr>
				<td class="Label" colspan="3">附加材料</td>
				<td><div class="form_operate">
						<button name="gcbjdj_file_add">添加</button>
						<button name="gcbjdj_file_del">删除</button>
					</div>
					<table id="gcbjdj_file_list_add"></table>
					<div id="gcbjdj_file_list_add_pager"></div></td>
			</tr>
			<tr>
				<td class="Label">补件意见</td>
				<td class="value" colspan="3"><textarea id="gcbjdj_opinion"
						name="gcbjdj_opinion" style="width: 98%;"></textarea></td>
			</tr>
		</table>
	</div>
</div>
