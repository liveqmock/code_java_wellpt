<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
<script>
				 
				function doview(uuid){ 
					window
					.open("${ctx }/worktask/dytable/editPage.action?uuid="
							+ uuid);
					
				}
			</script>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>我的任务</h2>
				<div class="box-icon"></div>
			</div>

			<div class="box-content">
				<form action="${ctx }/worktask/task/list_my.action" method="post">
					<div></div>
				</form>
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th>项目名称</th> 
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dys}" var="item">
							<tr onclick="doview('${item.uuid}')" style="cursor: pointer;">

								<td>${item.name }</td> 
							</tr>
						</c:forEach>
					</tbody>
				</table>
 
			</div>
		</div>
		<!--/span-->
	</div>

	<!-- 不能删除 -->
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>




</body>
</html>