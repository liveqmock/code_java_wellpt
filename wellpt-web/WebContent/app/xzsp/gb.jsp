<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<div>
	<div class="dyform gb_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">补件要求</td>
				<td class="value" colspan="3"><select name="gb_required"
					id="gb_required" style="width: 100%;">
						<option value="1">贵单位要在5日内补齐所缺材料，否则做退件处理</option>
						<option value="2">贵单位要在10日内补齐所缺材料，否则做退件处理</option>
				</select></td>
			</tr>
			<tr>
				<td class="Label">补件意见</td>
				<td class="value" colspan="3"><textarea id="gb_opinion"
						name="gb_opinion" style="width: 98%;"></textarea></td>
			</tr>
			<tr class="odd">
				<td class="Label" colspan="3">补件列表</td>
				<td>
					<table id="gb_file_list"></table>
					<div id="gb_file_list_pager"></div>
				</td>
			</tr>
		</table>
	</div>
</div>
