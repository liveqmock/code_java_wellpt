<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Time Line</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/js/common/jquery.commercialRegister.css" />
<style type="text/css">
#container {
	color: #000;
	margin: 0 auto;
	background-color: #fff;
	width: 980px;
	overflow: scroll;
}
</style>
</head>
<body>
	<div id="container">
		<h2>商事登记簿</h2>
		<div class="commercial_register"></div>
		<div class="commercial_register"></div>
		<div class="commercial_register"></div>
	</div>
	<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.commercialRegister.js"></script>
	<script type="text/javascript">
		$(function() {
			JDS.call({
				service : "commercialBusinessService.getCommercialRegister",
				data : [],
				success : function(result) {
					var data = result.data;
					$(".commercial_register").commercialRegister({
						data : data
					});
				}
			});
		});
	</script>
</body>
</html>