<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<link href="${ctx}/resources/fileupload/fileupload.css" rel="stylesheet">
<div>
	<div class="dytable_form zb_form">
		<div class="post-sign">
			<div class="post-detail">
				<!-- 表类型(tableType):1主表,2从表 -->
				<table style="width: 100%">
					<tr class="odd">
						<td class="Label">转报意见</td>
						<td class="value" colspan="3"><textarea id="zb_opinion"
								rows="4" name="zb_opinion" style="width: 98%;"></textarea></td>
					</tr>
					<tr>
						<td class="Label">转报文件</td>
						<td class="value" colspan="3">
							<div id="zb_file_upload">
							<input id="zb_file_uploads" name="zb_file_uploads" type="file"/>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
