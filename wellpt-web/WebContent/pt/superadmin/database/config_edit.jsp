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
					<form:form id="database_config_form" commandName="databaseConfig"
						cssClass="form-horizontal">
						<fieldset>
							<input id="databaseConfigUuid" name="uuid" type="hidden"
								value="${databaseConfig.uuid}" />
							<div class="control-group">
								<label class="control-label" for="name">名称</label>
								<div class="controls">
									<form:input path="name" cssClass="input-xlarge focused"></form:input>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="name">数据库类型</label>
								<div class="controls">
									<form:select path="type" items="${databaseTypes}"
										cssClass="input-xlarge focused"></form:select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="id">地址</label>
								<div class="controls">
									<form:input path="host" cssClass="input-xlarge focused"></form:input>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="account">端口</label>
								<div class="controls">
									<form:input path="port" cssClass="input-xlarge focused"></form:input>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="account">登录名</label>
								<div class="controls">
									<form:input path="loginName" cssClass="input-xlarge focused"></form:input>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="remark">密码</label>
								<div class="controls">
									<input id="password" name="password" type="password"
										value="${databaseConfig.password}"
										class="input-xlarge focused"></input>
								</div>
							</div>
							<div>
								<c:if test="${not empty message}">
									<span>${message}</span>
								</c:if>
							</div>
							<div class="form-actions">
								<button id="btn_save" type="button" class="btn btn-primary">保存</button>
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
<script src="${ctx}/resources/validate/js/jquery.validate.js"></script>
<script type="text/javascript">
<!--
	var validator = $("#database_config_form").validate({
		rules : {
			name : {
				required : true,
				remote : {
					url : "${ctx}/common/validate/check/exists",
					type : "POST",
					data : {
						uuid : function() {
							return $('#databaseConfigUuid').val();
						},
						checkType : "databaseConfig",
						fieldName : "name",
						fieldValue : function() {
							return $('#name').val();
						}
					}
				}
			}
		},
		messages : {
			name : {
				required : "名称不能为空!",
				remote : "名称已经存在!"
			}
		}
	});
	var bean = {
		"uuid" : null,
		"name" : null,
		"loginName" : null,
		"password" : null,
		"host" : null,
		"port" : null,
		"type" : null
	};
	$("#btn_save").click(function(e) {
		if (validator.form()) {
			$("#database_config_form").form2json(bean);
			JDS.call({
				service : "databaseConfigService.save",
				data : bean,
				success : function(result) {
					alert("保存成功!");
					$("#database_config_form").json2form(result.data);
				},
				error : function() {
					alert("保存失败!");
				}
			});
		}
	});
	$("#enabled").change(function(e) {
		if (this.checked) {
			$(this).val("true");
		} else {
			$(this).val("false");
		}
	});
	$("#btn_check_connecton_status").click(function(e) {
		var databaseConfigUuid = $("#databaseConfigUuid").val();
		if (databaseConfigUuid == null || $.trim(databaseConfigUuid) == "") {
			alert("请先保存配置!");
			return;
		}
		JDS.call({
			service : "databaseConfigService.checkConnectionStatus",
			data : databaseConfigUuid,
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