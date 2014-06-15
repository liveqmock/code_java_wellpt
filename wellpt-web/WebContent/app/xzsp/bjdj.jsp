<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<div>
	<div class="dytable_form bjdj_form">
		<div class="post-sign">
			<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table style="width: 100%">
					<tr class="odd">
						<td class="Label">补件原因</td>
						<td class="value" colspan="3"><textarea id="bj_reason"
								name="bj_reason" disabled="disabled" style="width: 98%;">(1)申请人为单位的：提供营业执照或组机机构代码证(复印件)；申请人为公民的：提供身份证(复印件)；
								</textarea></td>
					</tr>
					<tr>
						<td class="Label">补件备注</td>
						<td class="value" colspan="3"><textarea id="bj_remark"
								name="bj_remark" disabled="disabled" style="width: 98%;"></textarea></td>
					</tr>
					<tr class="odd">
						<td class="Label" colspan="3">补办材料</td>
						<td><table id="bjdj_file_list"></table>
							<div id="bjdj_file_list_pager"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
