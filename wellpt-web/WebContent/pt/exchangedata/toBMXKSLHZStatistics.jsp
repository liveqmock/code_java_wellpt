<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%-- <%@ include file="/pt/dytable/form_css.jsp"%>	 --%>
<%-- <%@ include file="/pt/dytable/form_js.jsp"%>	 --%>
<title>部门许可数量汇总表</title>
<style type="text/css">
	.close_left{
		padding-left:50px;
	}
</style>
</head>
<body>
	<div class="viewContent">
	                     
		<table style="margin: 0px 6px;width: 343px;border-collapse:collapse;">
				<tr style="background: none repeat scroll 0 0 #E7EBF1;line-height: 35px;text-align: left;">
				    <td class="Label" style="border:1px solid #D9D9D9;width:70%;padding-left:6px;">单位名称</td>
				    <td class="value" style="border:1px solid #D9D9D9;width:30%;padding-left:6px;">数量</td>
				</tr>
			
			<c:forEach items="${bmxkslhzStatisticsMapList}" var="bmxkslhzStatisticsMapItem" > 
			    <c:forEach items="${bmxkslhzStatisticsMapItem}" var="entry"> 
			    
			       <tr style="line-height: 35px;text-align: left;">
					    <td class="Label" style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${entry.key}" /> </td>
					    <td class="value" style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${entry.value}" /> </td>
					</tr>
			    </c:forEach> 
			</c:forEach> 
		</table>
	</div>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
</body>
</html>