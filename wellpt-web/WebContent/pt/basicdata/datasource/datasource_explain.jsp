<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>数据源解析</title>
<c:if test="${openBy == 'definition'}">
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/basicdata/dyview/dyview_demo.js"></script>
</c:if>
</head>
<body>
	<div class="dataSourceContent" dataSourceNum="${queryItemCount}">
		<div>
		<div>
			<table class="table">
					<tbody id="template" style="clear: both;">
					<c:forEach items="${queryItems}" var="temp">
						<tr>
							<td>
								${temp}	
							</td>
						</tr>
			         </c:forEach>
					</tbody>
			</table>
		</div>
		</div>
	</div>	
</body>
</html>

