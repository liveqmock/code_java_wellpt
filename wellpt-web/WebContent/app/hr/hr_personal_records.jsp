<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<html>
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

<div class="div_body">
	<div class="form_header">
			<div class="form_title">
				<!--新增人事档案信息标题-->
				<c:if test="${new_personal_records eq 'true' }">
					<h2><spring:message code="hrManager.title.new_personal_records"/></h2>
					<div class="form_close" title="关闭"></div>
				</c:if>
				<!-- 行点击标题 -->
				<c:if test="${rowclick eq 'true'}">
					<h2><spring:message code="hrManager.title.rowclick"/></h2>
					<div class="form_close" title="关闭"></div>
				</c:if>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<!-- 新建简历按钮start -->
					<c:if test="${new_personal_records eq 'true' }">
						<button id="save" type="button" ><spring:message code="hrManager.btn.save" /></button>
					</c:if>
					<!-- 新建简历按钮end -->
					
					<!-- 行点击跳转按钮start -->
					<c:if test="${rowclick eq 'true'}">
						<button id="save" type="button" ><spring:message code="hrManager.btn.save" /></button>
						<button id="edit" type="button" ><spring:message code="hrManager.btn.edit" /></button>
					</c:if>
				</div>
			</div>
	</div>
	<form action=""  id="abc" >
	</form>
</div>
<a>ok</a>

<!-- Project -->
	<%@ include file="/pt/dyform/dyform_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript"
		src='${ctx}/resources/app/js/hr/hr_personal_records.js'></script>
</body>
</html>