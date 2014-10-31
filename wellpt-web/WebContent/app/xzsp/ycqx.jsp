<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<div class="dyform ycqx_form">
		<!-- 表类型(tableType):1主表,2从表 -->
		<table style="width: 100%">
			<tr class="odd">
				<td class="Label">备注</td>
				<td class="value" colspan="3"><textarea id="ycqx_remark"
						rows="8" name="ycqx_remark" style="width: 98%;"></textarea></td>
			</tr>
			<tr>
				<td class="Label">延长工作日</td>
				<td class="value" colspan="3"><select name="ycqx_workday"
					id="ycqx_workday" style="width: 100%;">
						<option value="1">1个工作日</option>
						<option value="2">2个工作日</option>
						<option value="3">3个工作日</option>
						<option value="4">4个工作日</option>
						<option value="5">5个工作日</option>
						<option value="6">6个工作日</option>
						<option value="7">7个工作日</option>
						<option value="8">8个工作日</option>
						<option value="9">9个工作日</option>
				</select>
			</tr>
		</table>
	</div>
</div>
