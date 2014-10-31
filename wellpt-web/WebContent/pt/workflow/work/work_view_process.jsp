<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>办理过程</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/workflow/work_view.css" />
<style type="text/css">
body{background-color:white}
.div_body{margin:0px; width:99%; background:url("") repeat-y scroll 100% 0 #fff;}
.body_foot{background:none;}
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>办理过程</h2>
				</div>
			</div>
			<input type="hidden" name="flowInstUuid" value="${flowInstUuid}" />
			<div class="form_content" style="width: 100%"></div>
			<!-- Project -->
			<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/jquery/jquery.js"></script>
			<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
	<script type="text/javascript">
		$(function() {
			var flowInstUuid = $("input[name='flowInstUuid']").val();
			$.ajax({
				type : "GET",
				url : ctx + "/workflow/work/get/flow/process",
				data : {
					flowInstUuid : flowInstUuid
				},
				success : function(success, statusText, jqXHR) {
					$(".form_content").html(success);
					$(".view_process_table").width("100%");
				},
				error : function(jqXHR, statusText, error) {
				}
			});
		});
	</script>
</body>
</html>