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
					<a class="btn btn-info"
						href="${ctx}/superadmin/database/config/create"><i
						class="icon-edit icon-white"></i>新增 </a>
				</div>
				<table class="table table-bordered table-striped table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>类型</th>
							<th>地址</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="databaseConfig" items="${databaseConfigs}">
							<tr>
								<td style="cursor: pointer;">${databaseConfig.name}</td>
								<td style="cursor: pointer;" class="center">${databaseConfig.type}</td>
								<td style="cursor: pointer;" class="center">${databaseConfig.host}</td>
								<td style="cursor: pointer;" class="center"><a href='#nogo'
									id="database_config_view_${databaseConfig.uuid}">查看</a>|<a
									href='#nogo' id="database_config_edit_${databaseConfig.uuid}">修改</a>|<a
									href='#nogo' id="database_config_del_${databaseConfig.uuid}">删除</a></td>
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
<!--
	$("a[id^=database_config_view]")
			.each(
					function() {
						$(this)
								.click(
										function(e) {
											var databaseConfigUuid = this.id
													.substring(this.id
															.lastIndexOf("_") + 1);
											location.href = "${ctx}/superadmin/database/config/view/"
													+ databaseConfigUuid;
										})
					});

	$("a[id^=database_config_edit]")
			.each(
					function() {
						$(this)
								.click(
										function(e) {
											var databaseConfigUuid = this.id
													.substring(this.id
															.lastIndexOf("_") + 1);
											location.href = "${ctx}/superadmin/database/config/edit/"
													+ databaseConfigUuid;
										});
					});

	$("a[id^=database_config_del]").each(
			function() {
				$(this).click(
						function(e) {
							var databaseConfigUuid = this.id.substring(this.id
									.lastIndexOf("_") + 1);
							if (confirm("确认要删除数据库配置吗?")) {
								deleteDatabaseConfig(databaseConfigUuid);
							}
						});
			});

	function deleteDatabaseConfig(databaseConfigUuid) {
		JDS
				.call({
					service : "databaseConfigService.remove",
					data : databaseConfigUuid,
					success : function(result) {
						alert("删除成功!");
						location.href = "${ctx}/superadmin/database/config/list.action";
					},
					error : function(xhr, textStatus, errorThrown) {
						var data = JSON.parse(xhr.responseText);
						alert(data.msg);
					}
				});
	}
//-->
</script>
</html>