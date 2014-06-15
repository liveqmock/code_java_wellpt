<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>审核列表</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>账号</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tenant" items="${tenants}">
							<tr>
								<td style="cursor: pointer;">${tenant.name}</td>
								<td style="cursor: pointer;" class="center">${tenant.account}</td>
								<td style="cursor: pointer;" class="center">审核中</td>
								<td style="cursor: pointer;" class="center"><a href='#nogo'
									id="tenant_review_${tenant.uuid}">审核</a></td>
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
<script type="text/javascript">
<!--
	$("a[id^=tenant_view]").each(function() {
		$(this).click(function(e) {
			var tenantUuid = this.id.substring(this.id.lastIndexOf("_") + 1);
			location.href = "${ctx}/superadmin/tenant/view/" + tenantUuid;
		});
	});

	$("a[id^=tenant_review]").each(function() {
		$(this).click(function(e) {
			var tenantUuid = this.id.substring(this.id.lastIndexOf("_") + 1);
			location.href = "${ctx}/superadmin/tenant/review/" + tenantUuid;
		});
	});
//-->
</script>
</html>