<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="xct">
	<div class="xct-list">
		<c:forEach items="${list }" var="l">
			<div class="xct-list-item">
				<a class="xct-list-item-a" opentype="${l.cc.openType}" inputurl="${ctx}${l.cc.inputUrl}">
<%-- 					<div class="module_div_img"><img class="item_map" src="${ctx}${l.cc.icon}"></div> --%>
					<div class="module_div_name">${l.cc.title }</div>
					<c:if test="${l.cc.showNum == true}"><div class="nums">${l.count}</div></c:if>
					
				</a>
			</div>
		</c:forEach>
	</div>
</div>
<script>
	$(".xct-list-item").click(function(){
		var url = $(this).find("a").attr("inputurl");
		location.href = url;	
	});
</script>
</body>
</html>