<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.wellsoft.pt.org.entity.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<script>
		function doview(uuid) {
			window.open("${ctx }/worktask/task/view.action?uuid=" + uuid);

		}
	</script>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>我的本周计划</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th>任务名称</th>
							<th>类别</th>
							<th>任务状态</th>
							<th>目标与要求</th>
							<th>计划开始时间</th>
							<th>计划完成时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ps }" var="item">
							<tr style="cursor: pointer;">
								<td>${item.taskName }</td>
								<td class="center">${item.typename }</td>
								<td class="center">${item.statename }</td>
								<td class="center">${item.target }</td>
								<td class="center">${item.planBeginTime }</td>
								<td class="center">${item.planEndTime }</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<!--/span-->
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>未完成任务</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th>任务名称</th>
							<th>类别</th>
							<th>任务状态</th>
							<th>目标与要求</th>
							<th>计划开始时间</th>
							<th>计划完成时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tasks }" var="item">
							<tr onclick="doview('${item.uuid}')" style="cursor: pointer;">
								<td>${item.taskName }</td>
								<td class="center">${item.typename }</td>
								<td class="center">${item.statename }</td>
								<td class="center">${item.target }</td>
								<td class="center">${item.planBeginTime }</td>
								<td class="center">${item.planEndTime }</td>
 
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<!--/span-->
	</div>
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>
</body>
</html>