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
<title>接收逾期汇总表</title>
<style type="text/css">

</style>
</head>
<body>
	<div class="viewContent">
	                     
		<table style="margin: 0px 6px;width: 343px;border-collapse:collapse;">
			<tr style="background: none repeat scroll 0 0 #E7EBF1;line-height: 35px;text-align: left;">
			    <td style="border:1px solid #D9D9D9;width:70%;padding-left:6px;">单位名称</td>
			    <td style="border:1px solid #D9D9D9;width:30%;padding-left:6px;">逾期数量</td>
			</tr>
			
			<c:forEach items="${jsyqStatisticsMapList}" var="jsyqStatisticsMapItem" > 
			    <c:forEach items="${jsyqStatisticsMapItem}" var="entry"> 
			       <tr style="line-height: 35px;text-align: left;">
					    <td style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${entry.key}" /> </td>
					    <td style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${entry.value}" /> </td>
					</tr>
			    </c:forEach> 
			 </c:forEach> 
		</table>
	</div>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript">
		$(function(){
			$(window).resize(function(e) {
				// 调整自适应表单宽度
				adjustWidthToForm();
			});
			// 调整自适应表单宽度
			function adjustWidthToForm() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
			}
			
			$(".form_header .form_close").click(function(e) {
				returnWindow();
				window.close();
			});
		});
	</script>
</body>
</html>