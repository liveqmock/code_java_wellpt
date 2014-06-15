<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>

	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span " style="float: left;">
				<div class="well nav-collapse sidebar-nav">
					<!-- 列表导航 -->
					<ul class="nav nav-tabs nav-stacked main-menu"> 
						<li class="nav-header hidden-tablet">工作计划管理</li>
						 
						<li><a href="${ctx }/worktask/daily/list_share.action"
							target="main_iframe"><i class="icon-ban-circle"></i><span
								class="hidden-tablet"> 日志</span></a></li>
						<li><a href="${ctx }/worktask/task/list_sub.action"
							target="main_iframe"><i class="icon-lock"></i><span
								class="hidden-tablet"> 任务</span></a></li>
						<li><a href="${ctx }/worktask/report/list_sub.action"
							target="main_iframe"><i class="icon-lock"></i><span
								class="hidden-tablet"> 报告</span></a></li>
						<li><a href="${ctx }/worktask/plan/list_sub.action"
							target="main_iframe"><i class="icon-lock"></i><span
								class="hidden-tablet"> 计划</span></a></li>
						<li><a class="ajax-link" href="${ctx }/worktask/task/main.action"><i
								class="icon-align-justify"></i><span class="hidden-tablet">
									我的</span></a></li>

						<li><a class="ajax-link" href="${ctx }/worktask/task/share.action"><i
								class="icon-align-justify"></i><span class="hidden-tablet">
									共享</span></a></li>

					</ul>
				</div>
				<!--/.well -->
			</div>
			<!--/span2-->
			<!-- 左侧菜单 ends -->


			<!-- 核心内容 span10-->
			<div id="content" class="span10" style="float: left; width: 81%">
				<iframe id="main_iframe" name="main_iframe" 
					style="width: 100%; background-color: transparent; display: block;"
					frameborder="0" src=""></iframe>
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
				if(h<700)
					h=700;
				$('#main_iframe').height(h);
			} catch (e) {
				//alert(e)
			}
		}
		function openwin(url) {
			window.open(url);
		}
		window.setInterval("set_iframe()", 500);
	</script>

</body>
</html>