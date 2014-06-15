<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
var fromtype='${fromtype}';
</script>
<body>
	<div class="form-actions">
			<button type="button" class="btn btn-primary" id="submitbtn">确定</button>
			<button class="btn" id="cancelBtn">取消</button>
		</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>未完成的任务</h2>
				<div class="box-icon"></div>
			</div>

			<div class="box-content">
				<form action="${ctx }/worktask/plan/list_my.action" method="post">
					<div></div>
				</form>
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th></th>
							<th>任务名称</th>
							<th>类别</th>
							<th>任务状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tasks }" var="item">
							<tr>

								<td>
								<input type="checkbox" name="ids" value="${item.uuid }" />
								<input type="hidden" id="name_${item.uuid }" value="${item.taskName }" />
								<input type="hidden" id="state_${item.uuid }" value="${item.taskState }" />
								<input type="hidden" id="type_${item.uuid }" value="${item.taskType }" />
								<input type="hidden" id="target_${item.uuid }" value="${item.target }" />
								<input type="hidden" id="begin_${item.uuid }" value="${item.planBeginTime}" />
								<input type="hidden" id="end_${item.uuid }" value="${item.planEndTime}" />
								<input type="hidden" id="count_${item.uuid }" value="${item.planWorkCount}" />
								</td>
								<td>${item.taskName }</td>
								<td class="center">${item.typename }</td>
								<td class="center">${item.statename }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="pagination pagination-centered">${pageUtils }</div>
			</div>
		</div>
		<!--/span-->
	</div>

	<!-- 不能删除 -->
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>



	<script src="${ctx }/resources/worktask/js/task/listUnfinished.js?"></script>

</body>
</html>