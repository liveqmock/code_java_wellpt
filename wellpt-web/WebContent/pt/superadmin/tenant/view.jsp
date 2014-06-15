<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="container-fluid">
		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					<h2>
						<i class="icon-edit"></i> 租户信息
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i
							class="icon-remove"></i></a>
					</div>
				</div>
				<div class="box-content">
					<form class="form-horizontal">
						<fieldset>
							<input id="tenantUuid" type="hidden" value="${tenant.uuid}" />
							<div class="control-group">
								<label class="control-label" for="focusedInput">租户名称</label>
								<div class="controls">${tenant.name}</div>
							</div>
							<div class="control-group">
								<label class="control-label">租户ID</label>
								<div class="controls">${tenant.id}</div>
							</div>
							<div class="control-group">
								<label class="control-label">账号</label>
								<div class="controls">${tenant.account}</div>
							</div>
							<div class="control-group">
								<label class="control-label">邮件地址</label>
								<div class="controls">${tenant.email}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="optionsCheckbox2">状态</label>
								<div class="controls">
									<label class="checkbox"><c:if
											test="${tenant.reviewed == true}">已激活</c:if> <c:if
											test="${tenant.reviewed == false}">审核中</c:if></label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="inputWarning">描述</label>
								<div class="controls">${tenant.remark}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="inputError">数据库类型</label>
								<div class="controls">${tenant.jdbcType}</div>
							</div>
							<div class="control-group success">
								<label class="control-label" for="inputSuccess">数据库名称</label>
								<div class="controls">${tenant.jdbcDatabaseName}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="selectError3">登陆数据库用户名</label>
								<div class="controls">${tenant.jdbcUsername}</div>
							</div>
							<div class="form-actions">
								<button id="btn_edit" type="button" class="btn btn-primary">修改</button>
								<button id="btn_check_connecton_status" type="button"
									class="btn">租户库连接性测试</button>
								<button type="button" class="btn" onclick="history.back();">返回</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			<!--/span-->
		</div>
		<!--/row-->
	</div>

	<!-- 不能删除 -->
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>
</body>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
	$("#btn_edit").click(
			function(e) {
				location.href = "${ctx}/superadmin/tenant/edit/"
						+ $("#tenantUuid").val();
			});
	$("#btn_check_connecton_status").click(function(e) {
		JDS.call({
			service : "tenantManagerService.checkDatasourceConnectionStatus",
			data : $("#tenantUuid").val(),
			success : function(result) {
				if (result.data === true) {
					alert("测试成功!");
				} else {
					alert("测试失败!");
				}
			}
		});
	});
</script>
</html>