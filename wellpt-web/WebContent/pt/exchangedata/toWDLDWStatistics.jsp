<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>未登录单位统计</title>
</head>
<body>
	<div class="viewContent">
		<table id="notLoginUnit" style="margin: 0px 6px;width: 480px;border-collapse:collapse;">
			<tr style="background: none repeat scroll 0 0 #E7EBF1;line-height: 35px;text-align: left;">
			    <td class="Label" style="border:1px solid #D9D9D9;width:10%;padding-left:6px;">序号</td>
			    <td class="Label" style="border:1px solid #D9D9D9;width:22%;padding-left:6px;">单位代码</td>
			    <td class="value" style="border:1px solid #D9D9D9;width:47%;padding-left:6px;">单位名称</td>
			    <td class="value" style="border:1px solid #D9D9D9;width:20%;padding-left:6px;">待收件数量</td>
			</tr>
			<c:forEach items="${notLoginCommonUnitList}" var="notLoginCommonUnit">
		        <tr style="line-height: 35px;text-align: left;">
				    <td class="Label" style="border:1px solid #D9D9D9;padding-left:6px;text-align: center;"></td>
				    <td class="Label" style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${notLoginCommonUnit.id}" /> </td>
		   			<td class="value" style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${notLoginCommonUnit.name}" /> </td>
		   			<td class="value" style="border:1px solid #D9D9D9;padding-left:6px;"><c:out value="${notLoginCommonUnit.receiveCount}" /> </td>
				</tr>
			</c:forEach> 
		</table>
	</div>
	<script type="text/javascript">
	    var len = $('#notLoginUnit tr').length;
	    for(var i = 1;i<len;i++){
	        $('#notLoginUnit tr:eq('+i+') td:first').text(i);
	    }
	</script>
</body>
</html>