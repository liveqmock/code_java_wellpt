<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>收文登记</title>
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
				<h2>收文登记</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
				<c:forEach var="btn" items="${btns}">
					<button id="${btn.code}" name="${btn.code}">${btn.name}</button>
				</c:forEach>
				</div>
			</div>
		</div>
		<div class="form_content">
			<div class="tabbable">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">文件属性</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="dytable_form">
							<div class="post-sign">
								<div class="post-detail">
									<table>
										<tr class="odd">
											<td class="Label">文件标题  *</td>
											<td class="value" colspan="3">
											<input id="title" name="title" type="text" value="${obj.title}">
											</td>
										</tr>
										<tr>
											<td class="Label">来文文号</td>
											<td class="value">
											<input id="title" name="referNumber" type="text" value="${obj.referNumber}">
											</td>
											<td class="Label">文件种类</td>
											<td class="value">
											<input id="title" name="type" type="text" value="${obj.type}">
											</td> 
										</tr>
										<tr class="odd">
											<td class="Label">文件编号 *</td>
											<td class="value">
											<input id="title" name="fileCode" type="text" value="${obj.fileCode}">
											</td>
											<td class="Label">来文单位</td>
											<td class="value">
											<input id="title" name="comeUnit" type="text" value="${obj.comeUnit}">
											</td>
										</tr>
										<tr>
											<td class="Label">文件密级</td>
											<td class="value">
											<input id="title" name="fileSecurity" type="text" value="${obj.fileSecurity}">
											</td>
											<td class="Label">缓急程度</td>
											<td class="value">
											<input id="title" name="urgencyDegree" type="text" value="${obj.urgencyDegree}">
											</td>
										</tr>
										<tr class="odd">
											<td class="Label">收文日期</td>
											<td class="value">
											<input id="title" name="receiveDate" type="text" value="${obj.receiveDate}">
											</td>
											<td class="Label">来文方式</td>
											<td class="value">
											<input id="title" name="fileSource" type="text" value="${obj.fileSource}">
											</td>
										</tr>
										<tr>
											<td class="Label">登记人</td>
											<td class="value">
											${obj.booker}
											</td>
											<td class="Label">登记时间</td>
											<td class="value">
											${obj.bookDate}
											</td>
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
	<input type="hidden" id="openType" name="openType" value="${openType}">
	<input type="hidden" id="bookerUserId" name="bookerUserId" value="${obj.bookerUserId}">
	<div class="body_foot"></div>
	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/schooldemo/js/school_receivefile.js"></script>
</body>
</html>