<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>签署意见</title>
<%@ include file="/pt/dytable/form_css.jsp"%>

<style type="text/css">
#toolbar .sign-opinion {
	border: 1px solid #D7D7D7;
	background-color: #FFF;
	margin-left: 20px;
	height: 24px;
	width: 590px;
	float: left;
	text-align: left;
	color: #0F599C;
	position: relative;
}

#toolbar .sign-opinion-label {
	margin-left: 7px;
	height: 24px;
	line-height: 24px;
}

.sign-opinion a {
	border:none;
	border-right: 1px solid #D7D7D7;
	display: inline-block;
	height: 17px;
	line-height: 17px;
	padding: 0px 0px 0px 6px;
}

.sign-opinion .sign-open {
	right: 0;
	bottom: 0;
	height: 9px;
	width: 9px;
	position: absolute;
	background: url("../../resources/pt/images/workflow/sign-open.png");
}

.btn-sign-opinion {
	margin-top: 2px;
	margin-left: 7px;
	float: left;
	height: 22px;
	width: 22px;
	background: url("../../resources/pt/images/workflow/sign_opinion_0.png");
	margin-left: 7px;
}
</style>
</head>
<body>
	<div class="div_body">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>签署意见</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="wf_opinion">
					<div class="sign-opinion">
						<span class="sign-opinion-label">签署意见</span><a href="#">&nbsp;</a><span
							class="sign-open"></span>
					</div>
					<div class="btn-sign-opinion"></div>
				</div>
				<div class="form_operate">
					<button id="btn_save" class="btn">保存</button>
					<button id="btn_submit" class="btn">提交</button>
				</div>
			</div>
		</div>
		<!-- Project -->
		<%@ include file="/pt/dytable/form_js.jsp"%>
		<script type="text/javascript"
			src="${ctx}/resources/pt/js/common/jquery.workflowOpinion.js"></script>
	</div>
	<div class="body_foot"></div>
</body>
</html>