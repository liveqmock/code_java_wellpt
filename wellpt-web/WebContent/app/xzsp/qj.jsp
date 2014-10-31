<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<div class="dyform qj_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">备注</td>
				<td class="value" colspan="3"><textarea id="qj_remark" rows="8"
						name="qj_remark" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">取件时间</td>
				<td class="value" colspan="3"><input id="qj_time"
					name="qj_time" type="text"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
					class="Wdate full-width" /></td>
			</tr>
		</table>
	</div>
</div>
