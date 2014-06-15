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
					<form:form id="tenant_form" commandName="tenant"
						cssClass="form-horizontal" method="post">
						<form:hidden path="id" />
						<form:hidden path="jdbcDatabaseName" />
						<form:hidden path="jdbcUsername" />
						<form:hidden path="jdbcPassword" />
						<fieldset>
							<input id="tenantUuid" name="uuid" type="hidden"
								value="${tenant.uuid}" /> <input id="reviewed" name="reviewed"
								type="hidden" value="${tenant.reviewed}" />
							<div class="control-group">
								<label class="control-label" for="name">单位名称</label>
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
								<label class="control-label" for="account">密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="password"
										name="password" type="password" value="${tenant.password}">
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
								<label class="control-label" for="enabled">状态</label>
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
									<c:choose>
										<c:when test="${tenant.uuid == null}">
											<form:select path="databaseConfigUuid"
												items="${databaseConfigs}" itemValue="uuid" itemLabel="name"
												cssClass="input-xlarge focused"></form:select>
										</c:when>
										<c:otherwise>
											<form:hidden path="databaseConfigUuid" />
											${databaseConfigName}
										</c:otherwise>
									</c:choose>
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
							<div class="control-group">
								<label class="control-label" for="jdbcDatabaseName">数据库名</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcDatabaseName"
										name="jdbcDatabaseName" type="text"
										value="${tenant.jdbcDatabaseName}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcUsername">数据库连接用户名</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcUsername"
										name="jdbcUsername" type="text" value="${tenant.jdbcUsername}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="jdbcPassword">数据库连接密码</label>
								<div class="controls">
									<input class="input-xlarge focused" id="jdbcPassword"
										name="jdbcPassword" type="password"
										value="${tenant.jdbcPassword}">
								</div>
							</div>
							 -->
							<div class="form-actions">
								<button id="btn_save" type="button" class="btn btn-primary">保存</button>
								<button id="btn_check_connecton_status" type="button"
									class="btn">租户库连接性测试</button>
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
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/resources/validate/js/jquery.validate.js"></script>
<script type="text/javascript">
<!--
	var validator = $("#tenant_form").validate({
		rules : {
			name : {
				required : true
			},
			account : {
				required : true,
				remote : {
					url : "${ctx}/common/validate/check/exists",
					type : "POST",
					data : {
						uuid : function() {
							return $('#tenantUuid').val();
						},
						checkType : "tenant",
						fieldName : "account",
						fieldValue : function() {
							return $('#account').val();
						}
					}
				}
			},
			password : {
				required : true,
				minlength : 5,
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			name : {
				required : "单位名称不能为空!"
			},
			account : {
				required : "账号不能为空!",
				remote : "该账号已存在!"
			},
			password : {
				required : "密码不能为空!",
				minlength : jQuery.format("密码不能小于{0}个字符!")
			},
			email : {
				required : "邮件地址不能为空!",
				email : "无效的邮件地址!"
			}
		}
	});

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
		"jdbcType" : null,
		"jdbcDriver" : null,
		"jdbcDialect" : null,
		"jdbcServer" : null,
		"jdbcPort" : null,
		"jdbcDatabaseName" : null,
		"jdbcUsername" : null,
		"jdbcPassword" : null,
		"databaseConfigUuid" : null
	};
	$("#btn_save").click(function(e) {
		if (validator.form()) {
			$("#tenant_form").form2json(bean);
			JDS.call({
				service : "tenantManagerService.saveTenant",
				data : bean,
				success : function(result) {
					alert("保存成功!");
					$("#tenant_form").json2form(result.data);
				},
				error : function() {
					alert("保存失败，无法创建租户数据库!");
				}
			});
		}
	});
	$("#btn_check_connecton_status").click(function(e) {
		var tenantUuid = $("#tenantUuid").val();
		if (tenantUuid == null || $.trim(tenantUuid) == "") {
			alert("请先保存配置!");
			return;
		}
		JDS.call({
			service : "tenantManagerService.checkDatasourceConnectionStatus",
			data : tenantUuid,
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