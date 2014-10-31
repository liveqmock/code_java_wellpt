<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<link href="${ctx}/resources/fileupload/fileupload.css" rel="stylesheet">
<div>
	<div class="dyform gctj_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">操作说明</td>
				<td class="value" colspan="3"><textarea id="gctj_remark"
						name="gctj_remark" disabled="disabled" style="width: 98%;">当前操作步骤：过程退件</textarea></td>
			</tr>
			<tr>
				<td class="Label">过程退件方式</td>
				<td class="value" colspan="3"><select name="gctj_manner"
					id="gctj_manner" style="width: 100%;">
						<option value="1">暂缓办理的过程退件</option>
						<option value="2">无法办理的过程退件</option>
				</select></td>
			</tr>
			<tr class="odd">
				<td class="Label">过程退件原因</td>
				<td class="value" colspan="3"><textarea id="gctj_reason"
						rows="8" name="gctj_reason" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">过程退件附件</td>
				<td class="value" colspan="3"><div id="gctj_file_upload">
						<input id="gctj_file_uploads" name="gctj_file_uploads" type="file" />
					</div></td>
			</tr>
		</table>
	</div>
</div>
