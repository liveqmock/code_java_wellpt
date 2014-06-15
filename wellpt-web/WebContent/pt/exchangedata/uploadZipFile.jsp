<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>上传ZIP</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/fileupload.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.src.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/pt/js/fileupload/jquery.iframe-transport.js"></script>
	 <%System.out.println("----------------2--------"); %>
</head>
<body>
	<div id="abcd"></div>
<!-- 	<button id="btn_fileupload_ctlid_single_demo" type="button" >提交</button> -->
</body>
<script type="text/javascript">
	 $(function(){
		var ctlID2 = "fileupload_ctlid_single_demo"; 
		fileupload = new WellFileUpload(ctlID2);
		fileupload.init(false,  $("#abcd"),  false, true, []);
		$("#btn_"  + ctlID2).click(function(){
			console.log(JSON.stringify(WellFileUpload.files[ctlID2]));
		});
	 });
</script>
</html>
