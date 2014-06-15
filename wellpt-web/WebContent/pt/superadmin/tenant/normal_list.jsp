<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>租户列表</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<div>
					<a class="btn btn-info" href="${ctx}/superadmin/tenant/create"><i
						class="icon-edit icon-white"></i>新增 </a>
				</div>
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
								<td style="cursor: pointer;" class="center">${tenant.enabled}</td>
								<td style="cursor: pointer;" class="center"><a href='#nogo'
									id="tenant_view_${tenant.uuid}">查看</a>|<a href='#nogo'
									id="tenant_edit_${tenant.uuid}">修改</a>|<a href='#nogo'
									id="tenant_del_${tenant.uuid}">删除</a></td>
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
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
	$("a[id^=tenant_view]").each(function() {
		$(this).click(function(e) {
			var tenantUuid = this.id.substring(this.id.lastIndexOf("_") + 1);
			location.href = "${ctx}/superadmin/tenant/view/" + tenantUuid;
		});
	});

	$("a[id^=tenant_edit]").each(function() {
		$(this).click(function(e) {
			var tenantUuid = this.id.substring(this.id.lastIndexOf("_") + 1);
			location.href = "${ctx}/superadmin/tenant/edit/" + tenantUuid;
		});
	});

	$("a[id^=tenant_del]").each(function() {
		$(this).click(function(e) {
			var tenantUuid = this.id.substring(this.id.lastIndexOf("_") + 1);
			if (confirm("确认要删除租户吗?")) {
				deleteTenant(tenantUuid);
			}
		});
	});
	function deleteTenant(tenantUuid) {
		JDS.call({
			service : "tenantManagerService.deleteTenant",
			data : tenantUuid,
			success : function(result) {
				alert("删除成功!");
				location.href = "${ctx}/superadmin/tenant/normal/list.action";
			},
			error : function() {
				alert("删除失败!");
			}
		});
	}
</script>
</html>