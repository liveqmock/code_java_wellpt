<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>
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
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}
</style>

</head>
<body>
				<form action="/web/basicdata/ckeditor/dealForm.action" method="post">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-2">ckeditor测试表单</a></li>
					</ul>
					    <div id="tabs-2">
					    	<table class="stab">
					    	
							<tr>
								<td>
									标题：<input type="text" name="title" value="${ckEditor.title }" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>
									用户名：<input type="text" name="userName"  value="${ckEditor.userName}" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td>
									<%-- <textarea  class="ckeditor" name="ckeditor">${ckEditor.content}</textarea>   --%>
								</td>
								<td></td>
							</tr>
							<tr>
								<td>
									<input type="submit" value="提交" />
								</td>
								<td></td>
							</tr>
						</table>
						 <textarea class="ckeditor" ></textarea>
				</div> 
			</div>
			</form>
		</div>
				
			</div>
	
	

 	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.ckeditor.js"></script>
</body>
</html>