<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
<script>
				 
				function doview(uuid){ 
					window
					.open("${ctx }/worktask/report/view.action?uuid="
							+ uuid);
					
				}
			</script>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>我的报告</h2>
				<div class="box-icon"></div>
			</div>

			<div class="box-content">
				<form action="${ctx }/worktask/report/list_my.action" method="post">
					<div></div>
				</form>
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>

							<th width="20%">名称</th>  
							<th>共享者</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.result }" var="item">
							<tr>

								<td onclick="doview('${item.uuid}')" style="cursor: pointer;">第${item.type2 } <c:if
										test="${item.type1=='1' }">周</c:if>
									<c:if test="${item.type1=='2' }">月</c:if>
									<c:if test="${item.type1=='3' }">季度</c:if>
									<c:if test="${item.type1=='4' }">年</c:if> 报告
								</td>

								<td style="cursor: pointer;"> </td>
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




</body>
</html>