<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>Privilege List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/pt/css/form_head.css" />	
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	padding: 10px;
}
#sms_valid_period {
    margin-top: 6px;
}
</style>

</head>
<body>
	<div id="container">
		<form action="" id="privilege_form">
			<div id="dyform">
		
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-1">用户登录时需要图形码校验的IP设置</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">用户登录时需要图形码校验的IP设置</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-1">
						<div class="btn-group">
							<button id="btn_login_verify_code_add" type="button" class="btn">新增</button>
							<button id="btn_login_verify_code_del" type="button" class="btn">删除</button>
						</div>
						<table id="login_verify_code_list"></table>
						<div id="login_verify_code_pager"></div>
						<br />
					</div>
				</div>
	
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-2">允许登录本系统的用户</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">允许登录本系统的用户</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-2">
						<textarea id="usernames" name="usernames" style="width: 99.5%"></textarea>
						<input id="userIds" name="userIds" type="hidden" />
					</div>
					<br />
				</div>
				
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-3">用户登录的IP设置</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">用户登录的IP设置</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-3">
						<div class="btn-group">
							<button id="btn_ip_login_limit_add" type="button" class="btn">新增</button>
							<button id="btn_ip_login_limit_del" type="button" class="btn">删除</button>
						</div>
						<table id="ip_login_limit_list"></table>
						<div id="ip_login_limit_pager"></div>
						<br />
					</div>
				</div>
				
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-4">手机短信二次验证的IP设置</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">手机短信二次验证的IP设置</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-4">
						<div class="btn-group">
							<button id="btn_ip_sms_verify_add" type="button" class="btn">新增</button>
							<button id="btn_ip_sms_verify_del" type="button" class="btn">删除</button>
						</div>
						<table id="ip_sms_verify_list"></table>
						<div id="ip_sms_verify_pager"></div>
					</div>
					<div id="sms_valid_period">
						<label for="validPeriod">短信验证码有效期限：</label><input id="validPeriod"
							name="validPeriod" type="text" style="width:30px;"/><label for="validPeriod">秒</label>
					</div>
					<br />
				</div>
				
						<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-5">用户登录的域设置</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">用户登录的域设置</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-5">
						<div class="btn-group">
							<button id="btn_domain_login_limit_add" type="button" class="btn">新增</button>
							<button id="btn_domain_login_limit_del" type="button" class="btn">删除</button>
						</div>
						<table id="domain_login_limit_list"></table>
						<div id="domain_login_limit_pager"></div>
						<br />
					</div>
				</div>
	
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
				<br />
			</div>
		</form>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/security/ip_config.js"></script>
</body>
</html>