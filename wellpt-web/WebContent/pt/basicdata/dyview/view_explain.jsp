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
			<!-- 视图列特殊计算需要的参数开始 -->
			<input type="hidden" id="specialField" name="specialField" value="${viewDefinitionBean.specialField}">
			<input type="hidden" id="specialFieldMethod" name="specialFieldMethod" value="${viewDefinitionBean.specialFieldMethod}">
			<input type="hidden" id="requestParamName" name="requestParamName" value="${viewDefinitionBean.requestParamName}">
			<input type="hidden" id="requestParamId" name="requestParamId" value="${viewDefinitionBean.requestParamId}">
			<input type="hidden" id="responseParamName" name="responseParamName" value="${viewDefinitionBean.responseParamName}">
			<input type="hidden" id="responseParamId" name="responseParamId" value="${viewDefinitionBean.responseParamId}">
			<!-- 视图列特殊计算需要的参数结束 -->
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
			${buttonTemplate2}
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
	//特殊字段的二次请求数据处理
	if($("#specialField").val() == "true") {
		var viewUuid = $("#viewUuid").val();
		var viewDataUuid = new Array();
		var viewDataArray = new Array();
		 $(".checkeds").each(function(){
				var $this = $(this);
				var dataUuid = $this.val();
				var viewDataNew = new Object();
				var viewData = $this.parents("tr").attr("jsonstr");
				viewData = viewData.replace("{","");
				viewData = viewData.replace("}","");
				var viewDatas = viewData.split(",");
				for(var index=0;index<viewDatas.length;index++) {
					var viewDataMap = viewDatas[index].split("=");
					var viewDataMapFirst = viewDataMap[0].replace(" ","");
					viewDataNew[viewDataMapFirst] = viewDataMap[1];
				}
				viewDataArray.push(viewDataNew);
				viewDataUuid.push(dataUuid);
			});
// 			alert("viewDataArray " + JSON.stringify(viewDataArray));
			JDS.call({
			async:false,
				service:"getViewDataService.getSpecialFieldValues",
				data:[viewUuid,viewDataUuid,viewDataArray],
				success:function(result) {
					var data = result.data;
					for(var i=0;i<data.length;i++){
						var $nowTr = $("#template").find("tr").eq(i);
						var tempData = data[i];
						for(var key in tempData){
							$nowTr.find("td[field='"+key+"']").text(tempData[key]);
						}
					}
				}
			});
	}
	
	</script>	
	<link href="${ctx}/resources/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</body>
</html>

