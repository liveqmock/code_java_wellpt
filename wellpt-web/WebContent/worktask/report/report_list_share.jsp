<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
<script>
				 
				function dodel(uuid){ 
					window
					.open("${ctx }/worktask/plan/view.action?uuid="
							+ uuid);
					
				}
			</script>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>共享的报告</h2>
				<div class="box-icon"></div>
			</div>

			<div class="box-content">
<%-- 				<form action="${ctx }/worktask/report/list_share.action" method="post"> --%>
<%-- 					<div><input type="text" name="username"   id="username" value="${username }"/> <input --%>
<%-- 							type="hidden" name="userId"  id="userId"  value="${userId }"/> --%>

<!-- 						<button type="submit" class="btn btn-primary" id="submitbtn">查询</button></div> -->
<!-- 				</form> -->
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

								<td style="cursor: pointer;">第${item.type2 } <c:if
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


	<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript">
		$.jgrid.no_legacy_api = true;
		$.jgrid.useJSON = true;
		// 加载全局国际化资源
		I18nLoader.load("/resources/pt/js/global");
	</script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src='${ctx}/resources/pt/js/org/unit/jquery.unit.js'></script>
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
	<script>
		$().ready(function() {
			$('#username').click(function() {

				$.unit.open({
					valueField : "userId",
					labelField : "username",
					selectType : 4
				});

			});
		});
	</script>


</body>
</html>