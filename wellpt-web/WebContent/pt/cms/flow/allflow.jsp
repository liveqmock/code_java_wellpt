<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div id="flow_view" class="viewContent">
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
	<div class="con">
		<c:forEach items="${flowCate }" var="cate" >
			<div class="gl_con_r">
				<div class="gl_tit4">${cate.name}</div>
				<div class="gl_con2">
					<div class="gl_con2_l" >
						<ul>
							<c:forEach items="${allflow }" var="flow">
								<c:if test="${flow.category==cate.code }">
									<li class="flowItem"><span style="display: none;"><img src="${ctx}/resources/pt/images/cms/gl_icon3.jpg" />流程图</span><a onclick="window.open('${ctx}/workflow/work/new?flowDefUuid=${flow.uuid}', '_blank');" href="#">${flow.name}</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</c:forEach>
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
});
</script>
</body>
</html>