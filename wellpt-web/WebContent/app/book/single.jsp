<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<%@ include file="/pt/common/taglibs.jsp"%>
	<%@ include file="/pt/common/meta.jsp"%>
	<%@ include file="/pt/dyform/dyform_css.jsp"%>
	<title id="title">书籍信息</title>
</head>
<body>
	<!-- 隐藏字段 start-->
	<input type="hidden" id='formUuid' value='${formUuid}'>
	<input type="hidden" id='dataUuid' value='${dataUuid}'>
	<input type="hidden" id='flag' value='${flag}'>
	<input type="hidden" id='isEdit' value='${isEdit}'>
	<!-- 隐藏字段 end -->
	
	<div class="div_body">
		<div class="form_header">
				<div class="form_title">
					<!--新增简历标题-->
					<c:if test="${new_resume eq 'true' }">
						<h2><spring:message code="HireManage.title.new_resume"/></h2>
						<div class="form_close" title="关闭"></div>
					</c:if>
					<!-- 行点击标题 -->
					<c:if test="${rowclick eq 'true'}">
						<h2><spring:message code="HireManage.title.rowclick"/></h2>
						<div class="form_close" title="关闭"></div>
					</c:if>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div class="form_operate">
						<!-- 新建简历按钮start -->
						<c:if test="${new_resume eq 'true' }">
							<button id="save" type="button" ><spring:message code="hireManager.btn.save" /></button>
						</c:if>
						<!-- 新建简历按钮end -->
						
						<!-- 行点击跳转按钮start -->
						<c:if test="${rowclick eq 'true'}">
							<button id="save" type="button" ><spring:message code="hireManager.btn.save" /></button>
							<button id="edit" type="button" ><spring:message code="hireManager.btn.edit" /></button>
						</c:if>
					</div>
					<!-- 自定义表单 -->
				</div>
		</div>
		<form action=""  id="book_dyform" >
		</form>
	</div>
	<!-- Project -->
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript" src="${ctx}/resources/app/js/book/single.js"></script>
</body>
</html>