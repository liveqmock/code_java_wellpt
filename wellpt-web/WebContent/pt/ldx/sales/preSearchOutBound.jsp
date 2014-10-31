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
<title>数据查询</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>数据查询</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div
					style="width: 100%; text-align: left; color: #000000; padding-left: 25px; font: 12px/1.5 Microsoft YaHei;">
					外向发货编号：<input name="wxfhdh" id="wxfhdh" value="${wxfhdh}">
				</div>
				<div class="form_operate">
					<button id="form_search" type="button">查询</button>
					<button id="form_export" type="button">导出</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="wlgcform" class="dyform">
			<div id="result"></div>
			<br />
			<p></p>
		</form>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
		$("#form_close").click(function() {
			window.close();
		});
		$("#form_search").click(function() {
			var wxfhdh = $("#wxfhdh").val();
			if ('' == wxfhdh) {
				alert('外向发货编号不允许为空！');
				return;
			}
			$.ajax({
				type : "get",
				async : false,
				url : ctx + '/sales/searchOutBound',
				data : {
					"wxfhdh" : wxfhdh
				},
				success : function(data) {
					$("#result").html(data);
				},
				error : function(data) {
					alert("查询出错！");
				}
			});
		});
		$("#form_export").click(function() {
			var wxfhdh = $("#wxfhdh").val();
			if ('' == wxfhdh) {
				alert('外向发货编号不允许为空！');
				return;
			}
			var url = ctx + "/sales/export?wxfhdh=" + wxfhdh;
			window.location.href = url;
		});
	});
</script>
</html>