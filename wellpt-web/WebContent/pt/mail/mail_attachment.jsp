<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>附件上传测试</title>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/fileupload/fileupload.css" />
</head>
<body>

	<input type="text" id="text_title" />

<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.fileuploader.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
<script type="text/javascript"
		src="${ctx}/resources/fileupload/js/uuid.js"></script>	
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script type="text/javascript">
$("#text_title").click(function() {
	$("#text_title").parent().append(fileHtmlStr());
	fileuploader_upload();
});

</script>	
</body>
</html>
