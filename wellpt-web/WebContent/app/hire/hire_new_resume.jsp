<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title id="title">动态表单解释</title>
<script type="text/javascript">
	//全局变量
	var formUid = '${formUuid}';
	var dataUid = '${dataUuid}';
	var flag = '${flag}';
	var formDefinitionFromDefinitionModule = "${formDefinition}";
	//var formDefinitionFromDefinition4Model = "${formDefinitionFromDefinition4Model}";
</script>
</head>
<body>
<!-- 隐藏字段 start-->
<input type="hidden" id='formUuid' value='${formUuid}'>
<input type="hidden" id='dataUuid' value='${dataUuid}'>
<input type="hidden" id='flag' value='${flag}'>
<input type="hidden" id='isEdit' value='${isEdit}'>
<input type="hidden" id="resumeStatus" value='${resumeStatus}'>
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
				<!-- 待阅:1 ; 已阅:2 ; 已选:3 ;进入面试:4 ; 录用审批中:5 ; 黑名单:6 -->
				<c:if test="${rowclick eq 'true'}">
				
					<c:choose>
						
						<c:when test="${resumeStatus eq '1' }">
							<h2><spring:message code="HireManager.title.waitRead"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<c:when test="${resumeStatus eq '2' }">
							<h2><spring:message code="HireManager.title.hadRead"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<c:when test="${resumeStatus eq '3'}">
							<h2><spring:message code="HireManager.title.hadSelected"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<c:when test="${resumeStatus eq '4'}">
							<h2><spring:message code="HireManager.title.intoInterview"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<c:when test="${resumeStatus eq '5'}">
							<h2><spring:message code="HireManager.title.staffEmploy"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<c:when test="${resumeStatus eq '6'}">
							<h2><spring:message code="HireManager.title.blackList"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						<%--
						<c:when test="${resumeStatus eq '5'">
							<h2><spring:message code="HireManager.title.staffEmploy"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when>
						 
						<c:when test="${resumeStatus eq '6'">
							<h2><spring:message code="HireManager.title.hadSelected"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:when> --%>
						<c:otherwise>
							<h2><spring:message code="HireManage.title.rowclick"/></h2>
							<div class="form_close" title="关闭"></div>
						</c:otherwise>
					</c:choose>
					
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
						<!-- 1:待阅   2：已阅  3：已选  -->
						<c:if test="${resumeStatus eq '1' }">
							<button id="hadRead" type="button"><spring:message code="hireManager.btn.hadRead"/></button>
						</c:if>
						
						<c:if test="${resumeStatus eq '2' }">
							<button id="hadSelected" type="button"><spring:message code="hireManager.btn.hadSelected"/></button>
						</c:if>
						<button id="save" type="button" ><spring:message code="hireManager.btn.save" /></button>
						<button id="edit" type="button" ><spring:message code="hireManager.btn.edit" /></button>
					</c:if>
				</div>
				<!-- 自定义表单 -->
			</div>
	</div>
	<form action=""  id="abc" >
	</form>
</div>
	<!-- Project -->
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript"
		src='${ctx}/resources/app/js/hire/hire_new_resume.js'></script>
</body>
</html>