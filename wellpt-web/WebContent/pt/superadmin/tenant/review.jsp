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
						<i class="icon-edit"></i>审核租户
					</h2>
					<div class="box-icon">
						<a href="#" class="btn btn-setting btn-round"><i
							class="icon-cog"></i></a> <a href="#"
							class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
						<a href="#" class="btn btn-close btn-round"><i
							class="icon-remove"></i></a>
					</div>
				</div>
				<div id="review_form" class="box-content">
					<form:form commandName="tenant" cssClass="form-horizontal"
						method="post">
						<form:hidden path="id" />
						<form:hidden path="password" />
						<form:hidden path="jdbcDatabaseName" />
						<form:hidden path="jdbcUsername" />
						<fieldset>
							<input id="tenantUuid" name="uuid" type="hidden"
								value="${tenant.uuid}" /> <input id="reviewed" name="reviewed"
								type="hidden" value="${tenant.reviewed}" />
							<div class="control-group">
								<label class="control-label" for="name">租户名称</label>
								<div class="controls">
									<input class="input-xlarge focused" id="name" name="name"
										type="text" value="${tenant.name}">
								</div>
							</div>
							<!-- 
							<div class="control-group">
								<label class="control-label" for="id">租户ID</label>
								<div class="controls">
									<input class="input-xlarge focused" id="id" name="id"
										type="text" value="${tenant.id}">
								</div>
							</div> 
							-->
							<div class="control-group">
								<label class="control-label" for="account">账号</label>
								<div class="controls">
									<input class="input-xlarge focused" id="account" name="account"
										type="text" value="${tenant.account}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="account">邮件地址</label>
								<div class="controls">
									<input class="input-xlarge focused" id="email" name="email"
										type="text" value="${tenant.email}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">状态</label>
								<div class="controls">
									<label class="checkbox"> <form:checkbox path="enabled" />激活
									</label>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="remark">描述</label>
								<div class="controls">
									<input class="input-xlarge focused" id="remark" name="remark"
										type="text" value="${tenant.remark}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="name">数据库类型</label>
								<div class="controls">
									<form:select path="databaseConfigUuid"
										items="${databaseConfigs}" itemValue="uuid" itemLabel="name"
										cssClass="input-xlarge focused"></form:select>
								</div>
							</div>
							<!-- 
							<div class="control-group">
								<label class="control-label" for="jdbcType">数据库类型</label>
								<div class="controls">
									<select id="driver" name="jdbcType">
										<option>SQL Server 2008</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcServer">数据库服务地址</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcServer"
										name="jdbcServer" type="text" value="${tenant.jdbcServer}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcPort">数据库服务端口</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcPort"
										name="jdbcPort" type="text" value="${tenant.jdbcPort}">
								</div>
							</div>
							 -->
							<div class="control-group">
								<label class="control-label" for="jdbcDatabaseName">数据库名</label>
								<div class="controls">${tenant.jdbcDatabaseName}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcUsername">数据库连接用户名</label>
								<div class="controls">${tenant.jdbcUsername}</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcPassword">数据库连接密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcPassword"
										name="jdbcPassword" type="password"
										value="${tenant.jdbcPassword}">
								</div>
							</div>

							<div class="form-actions">
								<button id="btn_save" type="button" class="btn btn-primary">保存</button>
								<button id="btn_submit" type="button" class="btn btn-primary">提交</button>
								<button id="btn_check_connecton_status" type="button"
									class="btn">租户库连接性测试</button>
								<button id="btn_back" type="button" class="btn"
									onclick="history.back();">返回</button>
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
	var bean = {
		"uuid" : null,
		"name" : null,
		"id" : null,
		"account" : null,
		"password" : null,
		"email" : null,
		"enabled" : null,
		"reviewed" : null,
		"remark" : null,
		"databaseConfigUuid" : null,
		"jdbcType" : null,
		"jdbcDriver" : null,
		"jdbcDialect" : null,
		"jdbcServer" : null,
		"jdbcPort" : null,
		"jdbcDatabaseName" : null,
		"jdbcUsername" : null,
		"jdbcPassword" : null
	};
	$("#btn_save").click(function(e) {
		$("#review_form").form2json(bean);
		bean.enabled = (bean.enabled == 1) ? true : false;
		JDS.call({
			service : "tenantManagerService.saveTenant",
			data : bean,
			success : function(result) {
				alert("保存成功!");
			}
		});
	});
	$("#btn_submit").click(function(e) {
		$("#review_form").form2json(bean);
		bean.enabled = (bean.enabled == 1) ? true : false;
		JDS.call({
			service : "tenantManagerService.review",
			data : bean,
			success : function(result) {
				alert("审核成功!");
				location.href = "${ctx}/superadmin/tenant/normal/list.action";
			},
			error : function() {
				alert("审核失败，无法创建数据库!");
			}
		});
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
//-->
</script>
</html>