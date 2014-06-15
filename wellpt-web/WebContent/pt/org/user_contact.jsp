<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>联系人定义</title>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/form.css" />

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />

<style type="text/css">
.form_content {
	width: 96%;
}
.dytable_form .post-sign .post-detail .ai {
    width: 240px;
}

</style>
</head>
<body>
	<div class="div_body">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>联系人信息</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate"></div>
			</div>
		</div>
		<div class="form_content">
			<div class="tabbable">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">基础属性</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="dytable_form">
							<div class="post-sign">
								<div class="post-detail">
									<table>
										<tr class="odd">
											<td class="Label">姓名</td>
											<td class="ai" id="userName"></td>
											<td class="Label">性别</td>
											<td class="ai" id="sex"></td>
										</tr>
										<tr>
											<td class="Label">所属部门</td>
											<td class="ai" id="departmentName"></td>
											<td class="Label">岗位</td>
											<td class="ai" id="jobName"></td> 
										</tr>
										<tr class="odd">
											<td class="Label">联系人</td>
											<td class="ai" id="contactName"></td>
											<td class="Label">员工编号</td>
											<td class="ai" id="code"></td>
										</tr>
										<tr>
											<td class="Label">手机</td>
											<td class="ai" id="mobilePhone"></td>
											<td class="Label">办公电话</td>
											<td class="ai" id="officePhone"></td>
										</tr>
										<tr class="odd">
											<td class="Label">邮件</td>
											<td class="ai" id="email"></td>
											<td class="Label">身份证号</td>
											<td class="ai" id="idNumber"></td>
										</tr>
										<tr>
											<td class="Label">传真</td>
											<td class="ai" id="fax"></td>
											<td class="Label">备注</td>
											<td class="ai" id="remark"></td>
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
	<input type="hidden" id="uuid" name="uuid" value="${uuid}">
	<div class="body_foot"></div>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/user_contact.js"></script>
</body>
</html>