<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>项目进程</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/js/common/jquery.projectProcess.css" />
<style type="text/css">
.div_body {
    width: 1030px;
    height: 100%
}
.body_foot {
	width: 1025px;
	margin: 0;
}
#container {
	color: #000;
	margin: 0 auto;
	background-color: #fff;
	width: 1000px;
}
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<div class="form_title">
					<h2>项目进程</h2>
					<div class="form_close" title="关闭"></div>
				</div>
				<div id="toolbar" class="form_toolbar" style="padding: 8px 0 10px 0px;"></div>
			</div>
			<div id="dyform">
				<div class="dytable_form">
					<div class="post-sign">
						<div class="post-detail">
							<div id="container">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="body_foot" style="height:3px;"></div>
	</div>
</body>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.projectProcess.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
		var list = '[{"type":"新景舜弘大厦","data":[{"name":"工可阶段","status":"notDo","matter":[{"name":"规划局","status":"notDo","position":"before"},{"name":"国土局","status":"notDo","position":"after"}]}]}]';
		list = JSON.parse(list);
		for(var i=0;i<list.length;i++){
			var str = "<div class='projectProcess"+i+"'></div>";
			$("#container").append(str);
			$(".projectProcess"+i).projectProcess({
				data : list[i]
			});
		}
		$(".form_close").click(function(){
			window.close();
		});
	});
	</script>
</html>