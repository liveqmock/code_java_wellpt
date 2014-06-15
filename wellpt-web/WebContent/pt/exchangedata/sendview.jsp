<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<%-- <link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"> --%>
</head>
<body>
<div id="flow_view" class="viewContent">
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
	<div class="con">
		
			<div class="gl_con_r">
				<div class="gl_tit4"></div>
				<div class="gl_con2">
					<div class="gl_con2_l" >
						<ul>
							<c:if test="${not empty typeList and fn:length(typeList)>0}">
							<c:forEach items="${typeList}" var="flow">
									<li class="flowItem">
										<c:if test="${empty dataId}">
										<a class="toSendPage" style="cursor:pointer;" typeUuid=${flow.uuid} rel=20>${flow.name}</a>
										</c:if>
										<c:if test="${not empty dataId}">
										<a class="toSendPage" style="cursor:pointer;" typeUuid=${flow.uuid} dataId=${dataId} recVer=${recVer} rel=${rel} >${flow.name}</a>
										</c:if>
									</li>
							</c:forEach>
							</c:if>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		<c:if test="${empty typeList}"><div style="margin-top: 15px;">该单位没有可选业务</div></c:if>
	</div>
</div>
<script>
$(function(){
	$(".flowItem").unbind("mouseover");
	$(".flowItem").mouseover(function(){
		$(this).find("span").show();
	});
	$(".flowItem").unbind("mouseout");
	$(".flowItem").mouseout(function(){
		$(this).find("span").hide();
	});
	$(".gl_con_r").each(function(){
		if($(this).find(".flowItem").length>0){
			
		}else{
			$(this).remove();
		}
	});
	$(".toSendPage").click(function(){
		var rel = $(this).attr("rel");
		var typeUuid = $(this).attr("typeUuid");
		var dataId = $(this).attr("dataId");
		var recVer = $(this).attr("recVer");
		closeDialog();
		if(rel == 20){
			window.open("${ctx}/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=20");
		}else if(rel == 23){
			window.open("${ctx}/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=23&dataId="+dataId+"&recVer="+recVer);
		}else if(rel == 24){
			window.open("${ctx}/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=24&dataId="+dataId+"&recVer="+recVer);
		}
	});
	
	//业务类型只有一个，直接跳转到发件页
	 if($(".toSendPage").length==1) {
		 	var msg = $(".toSendPage");
		    var rel = msg.attr("rel");
			var typeUuid = msg.attr("typeUuid");
			if(rel == 20){
				window.open(ctx +"/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=20");
			}
	 } 
	
});
</script>
</body>
</html>