<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>商事登记汇总表</title>
</head>
<body>
	<div class="viewContent">
		<table style=" display: block;margin: 12px;text-align:center;">
			<tbody>
		         <tr>
					<td style="width: 160px;">本月新增商事主体统计:</td>
					<td style="width: 50px;"><font color=red>${currentMonthCommercialSubject}</font>   件</td>
					<td style="width: 160px;text-align:right;">所有商事主体统计:</td>
					<td style="width: 50px;"><font color=red>${allCommercialSubject}</font>   件</td>
				</tr>
				<tr style="text-align:center;">
					<td style="width: 160px;">本月新增行政许可统计:</td>
		        	<td style="width: 50px;"><font color=red>${currentMonthAdministrativeLicense}</font>   件</td>
					<td style="width: 160px;text-align:right;">所有行政许可统计:</td>
		        	<td style="width: 50px;"><font color=red>${allAdministrativeLicense}</font>   件</td>
		        </tr>  
			</tbody>						
		</table>
	</div>
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript">
		$(function(){
			$(window).resize(function(e) {
				//调整自适应表单宽度
				adjustWidthToForm();
			});
			//调整自适应表单宽度
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