<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<div class="container-fluid">
		<div class="row-fluid sortable">
			<div class="box span12">
				<div class="box-header well" data-original-title>
					<h2>
						<i class="icon-edit"></i>${title}
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
					<form:form commandName="databaseConfig" cssClass="form-horizontal">
						<fieldset>
							<input id="databaseConfigUuid" name="uuid" type="hidden"
								value="${databaseConfig.uuid}" />
							<div class="control-group">
								<label class="control-label" for="name">名称</label>
								<div class="controls">${databaseConfig.name}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="name">数据库类型</label>
								<div class="controls">${databaseTypeName}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="id">地址</label>
								<div class="controls">${databaseConfig.host}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="account">端口</label>
								<div class="controls">${databaseConfig.port}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="account">登录名</label>
								<div class="controls">${databaseConfig.loginName}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="remark">密码</label>
								<div class="controls">******</div>
							</div>
							<div>
								<c:if test="${not empty message}">
									<span>${message}</span>
								</c:if>
							</div>
							<div class="form-actions">
								<button id="btn_edit" type="button" class="btn btn-primary">修改</button>
								<button id="btn_check_connecton_status" type="button"
									class="btn">连接性测试</button>
								<button type="button" class="btn" onclick="history.back();">返回</button>
							</div>
						</fieldset>
					</form:form>
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
<!--
	$("#enabled").change(function(e) {
		if (this.checked) {
			$(this).val("true");
		} else {
			$(this).val("false");
		}
	});
	$("#btn_edit").click(
			function(e) {
				location.href = "${ctx}/superadmin/database/config/edit/"
						+ $("#databaseConfigUuid").val();
			});
	$("#btn_check_connecton_status").click(function(e) {
		JDS.call({
			service : "databaseConfigService.checkConnectionStatus",
			data : $("#databaseConfigUuid").val(),
			success : function(result) {
				if (result.data === true) {
					alert("测试成功!");
				} else {
					alert("测试失败!");
				}
			}
		});
	});
//-->
</script>
</html>