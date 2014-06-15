<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>视图解析</title>
<c:if test="${openBy == 'definition'}">
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_demo.js"></script>
</c:if>
</head>
<body>
	<div class="viewContent" viewnum="${queryItemCount}" id="update_${viewAndDataBean.viewUuid}">
		<div>
		<!-- 视图 -->
		<div id="abc" class="abc${viewAndDataBean.viewUuid}">
			<input type="hidden" id="viewUuid" name="viewUuid" value="${viewAndDataBean.viewUuid}" />
			<input type="hidden" id="columnDefinitions" name="columnDefinitions" value="${columnDefinitions }" />
			<input type="hidden" id="conditionTypes" name="conditionTypes" value="${conditionTypes}">
			<input type="hidden" id="parmStr" name="parmStr" value="${parmStr}">
			${htmlParmStr}
			
			${selectTemplate}
			${buttonTemplate}
			<input type="hidden" id="page" name="page" value="${page}" />
			<input value="${mark}" type="hidden" id="mark" name="mark"/>
			<input type="hidden" id="pageDefinition" name="pageDefinition" value="${pageDefinition}" />
			<input type="hidden" id="pageCurrentPage" name="pageCurrentPage" value="${page.currentPage}"/>
			<input type="hidden" id="pageTotalCount" name="pageTotalCount" value="${page.totalCount}"/>
			<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"/>
			<input type="hidden" id="totalPages" name="totalPages" value="${page.totalPages}"/>
			<c:forEach items="${selectDefinition.exactKeySelectCols}" var="exactKeySelectCols">
				<input type="hidden" value="${exactKeySelectCols.uuid}" class="keyWordArray"/>
			</c:forEach>
			
			<!-- 原来view_update页面的参数 -->
			<c:set var="ctx" value="${pageContext.request.contextPath}"/>
			<input type="hidden" id="title" name="title" value="${title}"/>
			<input type="hidden" id="orderbyArr" name="orderbyArr" value="${orderbyArr}"/>
			
			<table class="table">
					<thead>
					${titleSource}
					</thead>
					<tbody id="template" style="clear: both;">
					${template} 
					</tbody>
			</table>
			<c:if test="${viewDefinitionBean.pageAble==true}">
			<%@ include file="/pt/basicdata/dyview/page.jsp"%>
			</c:if>
		</div>
		</div>
	</div>	
	<c:if test="${openBy!= 'dytable'}">
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_explain.js"></script>
	</c:if>
	<c:if test="${openBy== 'dytable'}">
		<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_demo.js"></script>
	</c:if>
	<c:forEach items="${srcList}" var="src">
		<script type="text/javascript" src="${ctx}${src.src}"></script>
	</c:forEach>
	<script type="text/javascript">
	if($("#abc").length>0){
		$(function() {
			$("#abc").dyView({
				data:{viewUuid:$("#viewUuid").val(),
					columnDefinitions:$("#columnDefinitions").val(),
					page:$("#page").val(),
					pageCurrentPage:$("#pageCurrentPage").val(),
					pageTotalCount:$("#pageTotalCount").val(),
					pageSize:$("#pageSize").val(),
					pageDefinition:$("#pageDefinition").val(),
					conditionTypes:$("#conditionTypes").val(),
					clickType:"view_show"
					}	
			});
			$("#beginTime").focus(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			});
			$("#endTime").focus(function(){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			});
		});
	}
	var viewUuid = $("#viewUuid").val();
	var pageSize = $("#pageSize").val();
	$("#update_"+viewUuid).find("#pageSizeSelect").append("<option value='"+pageSize+"'>"+pageSize+"</option>");
	$("#update_"+viewUuid).find("#pageSizeSelect").children("option[value='"+pageSize+"']").attr("selected","selected");
	</script>	
	<link href="${ctx}/resources/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>

