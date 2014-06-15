<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/common/css.jsp"%>
<%@ include file="/pt/common/script.jsp"%>
<title>Super Admin</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
</head>
<body>
	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span " style="float: left; width: 185px">
				<div class="box-content">
					<ul class="nav nav-tabs" id="myTab">
						<li class="active"><a href="#info">租户管理</a></li>
						<li><a href="${ctx}/security_logout">退出</a></li>
					</ul>

					<div id="myTabContent" class="tab-content"
						style="overflow: hidden;">
						<div class="tab-pane active" id="info" style="overflow: hidden;">
							<ul class="nav nav-tabs nav-stacked main-menu"
								style="overflow: hidden;">
								<li><a href="${ctx}/superadmin/database/config/list.action"
									target="main_iframe"><i class="icon-ban-circle"></i><span
										class="hidden-tablet">数据库配置</span></a></li>
								<li><a href="${ctx}/superadmin/tenant/normal/list.action"
									target="main_iframe"><i class="icon-ban-circle"></i><span
										class="hidden-tablet">租户列表</span></a></li>
								<li><a href="${ctx}/superadmin/tenant/review/list.action"
									target="main_iframe"><i class="icon-lock"></i><span
										class="hidden-tablet">审核列表</span></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!--/span2-->
			<!-- 左侧菜单 ends -->

			<!-- 核心内容 span10-->
			<div id="content" class="span10"
				style="float: right; padding: 0px; margin: 0px; width: 82%;">
				<iframe id="main_iframe" name="main_iframe" name="main_iframe"
					style="width: 100%; background-color: transparent; display: block;"
					frameborder="0" src="${ctx}/superadmin/tenant/review/list.action"></iframe>
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->




	</div>
	<!--/.fluid-container-->
	<script>
		function set_iframe() {
			try {
				var h = $('#main_iframe').contents().find("#iframe_fix")
						.offset().top + 50;
				$('#main_iframe').height(h);

			} catch (e) {

			}
		}
		function openwin(url) {
			window
					.open(
							url,
							'newwindow',
							'height=600,width=1000,top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');

		}
		window.setInterval("set_iframe()", 500);

		var uid = null;
		function check(userid) {
			uid = userid;
		}
		function gourl(url) {
			if (uid == null || uid == '') {
				return;
			}
			url = url + "?userId=" + uid;
			$('#main_iframe').attr("src", url);
		}
	</script>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
</body>
</html>