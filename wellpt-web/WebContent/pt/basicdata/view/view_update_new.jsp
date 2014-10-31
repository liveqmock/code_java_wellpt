<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<input type="hidden" id="page" name="page" value="${page}" />
			<input value="${mark}" type="hidden" id="mark" name="mark"/>
			<input type="hidden" id="pageDefinition" name="pageDefinition" value="${pageDefinition}" />
			<input type="hidden" id="pageCurrentPage" name="pageCurrentPage" value="${page.currentPage}"/>
			<input type="hidden" id="pageTotalCount" name="pageTotalCount" value="${page.totalCount}"/>
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
			<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages}"/>
			<input type="hidden" id="title" name="title" value="${title}"/>
			<input type="hidden" id="orderbyArr" name="orderbyArr" value="${orderbyArr}"/>
			<input type="hidden" id="parmStr" name="parmStr" value="${parmStr}">
<!-- 			<style> -->
<!-- 				.dataTr:hover{ -->
<!-- 					background-color: #ddd; -->
<!-- 					cursor: pointer; -->
<!-- 				} -->
<!-- 				.sortAble:hover{ -->
<!-- 					cursor: pointer; -->
<!-- 				} -->
<!-- 			</style> -->
<table class="table">
		${titleSource}
		<tbody id="template">
		${templateAll} 
		</tbody>
</table>
<%@ include file="/pt/basicdata/view/page_new.jsp"%>
<c:forEach items="${srcList}" var="src">
		<script type="text/javascript" src="${ctx}${src.src}"></script>
	</c:forEach>


