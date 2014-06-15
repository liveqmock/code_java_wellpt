<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>
<title>动态表单解释</title>
<script type="text/javascript">
	//全局变量
	var formUid = '${formUid}';
	var dataUid = '${dataUid}';
</script>
</head>
<body id="explainBody">
	<input type="hidden" id='formUuid' value='${formUuid}'>
	<input type="hidden" id='dataUuid' value='${dataUuid}'>
	<input type="hidden" id='status' value='${status}'>
	<input type="hidden" id='isHideFolder' value='${isHideFolder}'>
	<input type="hidden" id='flag' value='2'>
	<div class="div_body">
		<div class="form_header">
			<div class="form_title">
				<c:choose>
					<c:when test="${status eq 1}">
					<h2>新建物品分类</h2>
					</c:when>
					<c:when test="${status eq 2}">
					<h2>新建物品名称</h2>
					</c:when>
					<c:when test="${status eq 3}">
					<h2>物品规格定义</h2>
					</c:when>
					<c:when test="${isHideFolder ne null}">
					<h2>物品名称定义</h2>
					</c:when>
					<c:otherwise>
					<h2>物品分类定义</h2>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				<button id="save" type="button" value="保存">保存</button>
			</div>
		</div>
		<!-- 动态表单 -->
		<div id="abc">
		</div>
	</div>
	<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript"
	src="${ctx}/resources/pt/js/psi/stock_deploy.js"></script>
</body>
</html>
