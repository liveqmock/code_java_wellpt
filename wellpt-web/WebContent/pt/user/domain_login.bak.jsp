<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/pt/common/taglibs.jsp"%>
<head>
<title>租户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />

<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap-responsive.min.css" />
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span4 offset4">
				<form action="${ctx}/passport/user/login/security_check" method="post"
					class="form-horizontal">
					<fieldset>
						<legend>租户${tenant}的域名登录</legend>
						<div class="control-group">
							<!-- 显示登录失败原因 -->
							<c:if test="${not empty param.error}">
								<label class="control-label">登录失败：</label>
								<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
							</c:if>
						</div>
						<div class="control-group">
							<div class="controls">
								<input type="hidden" id="tenant" name="tenant" value="${tenant}"
									placeholder="tentent" />
							</div>
						</div>
						<div class="control-group">
							<label for="j_username" class="control-label">用户名</label>
							<div class="controls">
								<input type="text" id="j_username" name="j_username"
									value="oa_dev" placeholder="username" />
							</div>
						</div>
						<div class="control-group">
							<label for="j_password" class="control-label">密码</label>
							<div class="controls">
								<input type="password" id="j_password" name="j_password"
									value="oa_dev" placeholder="password" />
							</div>
						</div>
						<c:if test="${requiredVerifyCode == true}">
							<div class="control-group">
								<label for="j_verify_code" class="control-label">验证码</label>
								<div class="controls">
									<input type="text" id="j_verify_code" name="j_verify_code"
										value="" placeholder="verify code" />
									<div>
										<img id="virify_code_image" style="cursor: pointer;"
											src="${ctx}/security/verifycode/image?t=<%=new Date().getTime()%>"
											alt="验证码" width="64" height="24">
									</div>
								</div>
							</div>
						</c:if>
						<div class="control-group">
							<label for="_spring_security_remember_me" class="control-label">两周内记住我</label>
							<div class="controls">
								<input type="checkbox" id="_spring_security_remember_me"
									name="_spring_security_remember_me" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="btn btn-primary">登录</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="span4">
				<div class="row-fluid">
					<div class="span8 offset4">
						<a href="${ctx}/index">返回首页</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<script src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script src="${ctx}/resources/timers/jquery.timers.js"></script>
	<script type="text/javascript">
		$("#_spring_security_remember_me").attr("checked", "checked");

		// 登录验证码
		$("#virify_code_image").click(
				function(e) {
					var url = "${ctx}/security/verifycode/image?t="
							+ new Date().getTime();
					$(this).attr("src", url);
				});
	</script>
	<!-- 短信验证码 -->
	<c:if test="${requiredSmsVerifyCode == true}">
		<!-- Modal -->
		<div id="sms_verify_code_modal" class="modal hide fade">
			<div class="modal-header">
				<button id="modal_close" type="button" class="close"
					data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>短信验证</h3>
			</div>
			<div class="modal-body">
				<p>
				<div class="control-group">
					<div class="controls">
						<button id="btn_send_sms_verify_code" type="button">发送短信验证码</button>
						<span id="timeout_msg"></span>
					</div>
				</div>
				<div class="control-group">
					<label for="j_sms_verify_code" class="control-label">短信验证码</label>
					<div class="controls">
						<input type="text" id="j_sms_verify_code" name="j_sms_verify_code"
							value="" placeholder="sms verify code" />
					</div>
					<span id="error_msg_sms_verify_code"></span>
				</div>
				</p>
			</div>
			<div class="modal-footer">
				<a id="modal_cancel" href="#" class="btn">取消</a> <a id="modal_ok"
					href="#" class="btn btn-primary">确定</a>
			</div>
		</div>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#sms_verify_code_modal").modal({
				show : true,
				keyboard : false
			});
			// 关闭短信验证码窗口，注销登录
			$("#sms_verify_code_modal").on("hidden", function() {
				location.href = "${ctx}/security_logout";
			});
			// 发送短信验证码
			$("#btn_send_sms_verify_code").click(function(e) {
				$.ajax({
					type : "POST",
					url : "${ctx}/security/verifycode/sms",
					data : {},
					success : function(success, statusText, jqXHR) {
						$("#j_sms_verify_code").val(success.verifyCode);
						timeout = success.timeout;
						$('#timeout_msg').html(timeout);
						$("#btn_send_sms_verify_code").attr("disabled", "disabled");
						$("body").everyTime("1s", "sms", function(counter) {
							$('#timeout_msg').html(timeout - counter);
							if (counter >= timeout) {
								smsTimeout();
							}
						}, timeout);
					},
					error : function(jqXHR, statusText, error) {
						$("#error_msg_sms_verify_code").html("请输入正确的短信验证码!");
					}
				});
			});
			// 短信验证码超时处理
			function smsTimeout() {
				$.ajax({
					url : "${ctx}/security/verifycode/sms/timeout",
					success : function(success, statusText, jqXHR) {
						$("#btn_send_sms_verify_code").removeAttr("disabled");
					}
				});
			}
			// 关闭短信验证码窗口
			$("#modal_close, #modal_cancel").click(function(e) {
				$("#sms_verify_code_modal").modal("hide");
			});
			// 验证输入的短信验证码
			$("#modal_ok").click(function(e) {
				$.ajax({
					type : "POST",
					url : "${ctx}/passport/user/login/check/smsverifycode",
					data : {
						j_sms_verify_code : $("#j_sms_verify_code").val()
					},
					success : function(success, statusText, jqXHR) {
						location.href = "${ctx}/passport/user/login/success";
					},
					error : function(jqXHR, statusText, error) {
						$("#error_msg_sms_verify_code").html("请输入正确的短信验证码!");
					}
				});
			});
		});
		</script>
	</c:if>
</body>
</html>