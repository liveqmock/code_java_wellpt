<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>首件巡检题库维护界面</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>题库维护界面</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate" >
					<button id="form_save" type="button">添加</button>
					<button id="form_close" type="button">修改</button>
					<button id="form_close" type="button">删除</button>
					<button id="form_save" type="button">查询答案</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<input name="wlids" id="wlids" value="${wlids}" type="hidden">
			<table>
				<tbody>
					<tr>
						<td class="title"><input type="checkbox" class="checkall">
						</td>
						<td class="title">题级一</td>
						<td class="title">题级二</td>
						<td class="title">题级三</td>
						<td class="title">题目内容</td>
						<td class="title">题库类型</td>
						<td class="title">工序名</td>
						<td class="title">是否关键工序</td>
					
						
						
						
					</tr>
				
				</tbody>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(
			function() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
				$(".checkall").live(
						"click",
						function() {
							if ($(this).attr("checked")) {
								$(this).parents("table").find("input:checkbox")
										.attr("checked", true);
							} else {
								$(this).parents("table").find("input:checkbox")
										.attr("checked", false);
							}
						});
				$("#form_close").click(function() {
					window.close();
				});
				
			});
</script>
</html>