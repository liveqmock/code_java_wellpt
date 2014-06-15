<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>人员定义</title>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/form.css" />
<style type="text/css">
.form_content {
	width: 96%;
}
</style>
</head>
<body>
	<div class="div_body">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>人员定义</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate"></div>
			</div>
		</div>
		<div class="form_content">
			<div class="tabbable">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">基础属性</a></li>
					<li><a href="#tab2" data-toggle="tab">个人属性</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="dytable_form">
							<div class="post-sign">
								<div class="post-detail">
									<table>
										<tr class="odd">
											<td class="Label">姓名</td>
											<td>${contact.userName}</td>
											<td class="Label">姓别</td>
											<td><c:if test="${contact.sex == '1'}">男</c:if> <c:if
													test="${contact.sex == '2'}">女</c:if></td>
										</tr>
										<tr>
											<td class="Label">登录帐号</td>
											<td>${contact.loginName}</td>
											<td class="Label">密码</td>
											<td></td>
										</tr>
										<tr class="odd">
											<td class="Label">编号</td>
											<td></td>
											<td class="Label">密级</td>
											<td></td>
										</tr>
										<tr>
											<td class="Label">岗位</td>
											<td colspan="3">${contact.jobName}</td>
										</tr>
										<tr class="odd">
											<td class="Label">上级主管</td>
											<td>${contact.leaderNames}</td>
											<td class="Label">职务代理人</td>
											<td>${contact.deputyNames}</td>
										</tr>
										<tr>
											<td class="Label">备注</td>
											<td colspan="3">${contact.remark}</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tab2">
						<div class="dytable_form">
							<div class="post-sign">
								<div class="post-detail">
									<table>
										<tr class="odd">
											<td rowspan="1" class="Label">头像</td>
											<td rowspan="1" colspan="3"></td>
										</tr>
										<tr>
											<td class="Label">昵称</td>
											<td></td>
											<td class="Label">移动电话</td>
											<td></td>
										</tr>
										<tr class="odd">
											<td class="Label">电子邮件</td>
											<td>${contact.email}</td>
											<td class="Label">传真</td>
											<td></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="body_foot"></div>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(function() {
			// 动态表单单据关闭
			$(".form_header h2").after(
					$("<div class='form_close' title='关闭'></div>"));
			$(".form_header .form_close").click(function(e) {
				window.close();
			});

			$(window).resize(function(e) {
				// 调整自适应表单宽度
				adjustWidthToForm();
			});
			// 调整自适应表单宽度
			function adjustWidthToForm() {
				var div_body_width = $(window).width() * 0.76 - 5;
				$(".body_top").css("width", $(window).width() * 0.76);
				$(".form_header").css("width", div_body_width);
			}
			$(window).trigger("resize");
		});
	</script>
</body>
</html>