<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>${bean.title}</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/chosen/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/exchange/sendData.css" />
<style type="text/css">
<c:if test="${showSubTable=='false'}">
.post-detail div.title,
.post-detail #subBtn-userform_product_standard_price,
.post-detail #gbox_userform_product_standard_price,
.post-detail #subBtn-userform_product_price_books,
.post-detail #gbox_userform_product_price_books{
	display: none;
}
</c:if>
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>${tableName}</h2>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div id="mini_wf_opinion" style="margin-bottom: 2px;">
					</div>
					<div class="form_operate">
						<c:forEach var="btn" items="${grantBtns}">
							<button id="${btn.code}" name="${btn.code}" >${btn.name}</button>
						</c:forEach>
					</div>
				</div>
			</div>
		<input type="hidden" id="tableName" name="tableName" value="${tableName}">	
		<input type="hidden" id="formUid" name="formUid" value="${formUid}">
		<input type="hidden" id="dataUid" name="dataUid" value="${dataUid}">
		<input type="hidden" id="showSubTable" name="showSubTable" value="${showSubTable}">
		<input type="hidden" id="readOnly" name="readOnly" value="${readOnly}">
			<!-- 动态表单 -->
			<div id="dyform"></div>
			
			<!-- Project -->
			<%@ include file="/pt/dytable/form_js.jsp"%>
			<script type="text/javascript"
				src="${ctx}/resources/chosen/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/ldx/ldxdetail.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
</body>
</html>