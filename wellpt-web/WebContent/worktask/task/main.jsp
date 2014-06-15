<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
 
	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span " style="float: left; width: 185px">
				<div class="box-header well" style="overflow: hidden;">
					<h2>
						<i class="icon-th"></i> 工作计划管理
					</h2>
					<div class="box-icon"></div>
				</div>
				<div class="box-content">
					<ul class="nav nav-tabs" id="myTab">
						<li class="active"><a href="#info">我的</a></li>
						<li><a href="#custom">共享</a></li>
						<li><a href="#messages">下属</a></li>
					</ul>

					<div id="myTabContent" class="tab-content"
						style="overflow: hidden;">
						<div class="tab-pane active" id="info" style="overflow: hidden;">
							<ul class="nav nav-tabs nav-stacked main-menu"
								style="overflow: hidden;">

								<c:if test="${new_plan_permission=='yes' }">
									<li><a class="ajax-link"
										href="${ctx }/worktask/task/list_my_week.action"
										target="main_iframe"><i class="icon-home"></i><span
											class="hidden-tablet"> 我的本周计划</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/plan/addpage.action')"><i
											class="icon-eye-open"></i><span class="hidden-tablet">
												写计划</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/report/addpage.action')"><i
											class="icon-edit"></i><span class="hidden-tablet"> 写报告</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskapply/addpage.action')"><i
											class="icon-align-justify"></i><span class="hidden-tablet">
												新建任务</span></a></li>
								</c:if>
								<li><a href="${ctx }/worktask/taskcycle/list.action"
									target="main_iframe"><i class="icon-ban-circle"></i><span
										class="hidden-tablet"> 周期性任务</span></a></li>
								<li><a href="${ctx }/worktask/plan/list_my.action"
									target="main_iframe"><i class="icon-lock"></i><span
										class="hidden-tablet"> 我的计划</span></a></li>
								<li><a href="${ctx }/worktask/task/list_my.action"
									target="main_iframe"><i class="icon-lock"></i><span
										class="hidden-tablet"> 我的任务</span></a></li>
								<li><a href="${ctx }/worktask/report/list_my.action"
									target="main_iframe"><i class="icon-lock"></i><span
										class="hidden-tablet"> 我的报告</span></a></li>
								<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskassign/addpage.action')"><i
											class="icon-eye-open"></i><span class="hidden-tablet">
												任务下发</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskresolve/addpage.action')"><i
											class="icon-edit"></i><span class="hidden-tablet"> 任务分解</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskdelay/addpage.action')"><i
											class="icon-align-justify"></i><span class="hidden-tablet">
											任务延期</span></a></li>
								<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskchange/addpage.action')"><i
											class="icon-eye-open"></i><span class="hidden-tablet">
												任务转办</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/taskcancel/addpage.action')"><i
											class="icon-edit"></i><span class="hidden-tablet"> 任务撤销</span></a></li>
									<li><a class="ajax-link" href="#nogo"
										onclick="openwin('${ctx }/worktask/meeting/addpage.action')"><i
											class="icon-align-justify"></i><span class="hidden-tablet">
												会议纪要</span></a></li>
								<c:if test="${record_permission=='yes' }">
									<li><a href="${ctx }/worktask/daily/list_this_week.action"
										target="main_iframe"><i class="icon-lock"></i><span
											class="hidden-tablet"> 我的日志</span></a></li>
								</c:if>
								<c:if test="${admin_permission=='yes' }">
									<li><a href="${ctx }/worktask/permission/editpage.action"
										target="main_iframe"><i class="icon-lock"></i><span
											class="hidden-tablet"> 权限设置</span></a></li>
									<li><a href="${ctx }/worktask/dytable/list.action"
										target="main_iframe"><i class="icon-lock"></i><span
											class="hidden-tablet"> 动态表单设置</span></a></li>
								</c:if>
							</ul>
						</div>
						<div class="tab-pane" id="custom">
							<ul class="nav nav-tabs nav-stacked main-menu"
								style="overflow: hidden;">
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/daily/list_share.action')"><i
										class="icon-ban-circle"></i><span class="hidden-tablet">
											日志</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/task/list_share.action')"><i
										class="icon-lock"></i><span class="hidden-tablet"> 任务</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/report/list_share.action')"><i
										class="icon-lock"></i><span class="hidden-tablet">报告</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/plan/list_share.action')"><i
										class="icon-lock"></i><span class="hidden-tablet"> 计划</span></a></li>

								<li></li>




							</ul>
							<div>
							<iframe id="treeframe" name="treeframe" name="treeframe" 
					style="width: 100%; background-color: transparent; display: block;min-height: 400px;"
					frameborder="0" src="${ctx }/worktask/task/tree.action"></iframe>
							</div>
						</div>
						<div class="tab-pane" id="messages">
							<ul class="nav nav-tabs nav-stacked main-menu"
								style="overflow: hidden;">
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/daily/list_sub.action')"><i
										class="icon-ban-circle"></i><span class="hidden-tablet">
											日志</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/task/list_sub.action')"><i
										class="icon-lock"></i><span class="hidden-tablet"> 任务</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/report/list_sub.action')"><i
										class="icon-lock"></i><span class="hidden-tablet">报告</span></a></li>
								<li><a href="#nogo"
									onclick="gourl('${ctx }/worktask/plan/list_sub.action')"><i
										class="icon-lock"></i><span class="hidden-tablet"> 计划</span></a></li>
								<li></li>

								<li><a href="#nogo" onclick="check('U0000000001')"> <span
										class="hidden-tablet">admin</span></a></li>
								<c:forEach var="item" items="${subs }">
									<li><a href="#nogo" onclick="check('${item.id}')"> <span
											class="hidden-tablet">${item.name }</span></a></li>

								</c:forEach>
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
					frameborder="0" src="${ctx }/worktask/task/list_my_week.action"></iframe>
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
			window.open (url,'newwindow','height=600,width=1000,top=50,left=50,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
 
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
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
</body>
</html>