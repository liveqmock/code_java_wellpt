<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>


<!DOCTYPE html>
<html lang="en">
<head>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
 <link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
 <link rel="stylesheet" type="text/css" 
		href="${ctx}/resources/theme/css/wellnewoa.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/fileupload/fileupload.css" />
	
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/css/form.css" />
	
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
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
	


<title>统一上传附件 示例</title>
 
</head>
<body id="explainBody">
	 
	<input type="hidden" id="text" value='请选择未完成工作' >
	<input type="hidden" id="subtable" value="001">
	<button id="btn_fileupload_ctlid_multiple_demo" type="button" value="提交多文件上传控件的文件信息">提交多文件上传控件的文件信息</button>
	<button id="btn_fileupload_ctlid_single_demo" type="button" value="提交单文件上传控件的文件信息">提交单文件上传控件的文件信息</button>
 	
	<!-- 自定义表单 -->
	<form action="">
		<label>统一上传控件示例</label> <input type="text" id="creator" name="creator" />
	</form>
	<!-- 动态表单 -->
	<div>
		简单多文件上传
		<div id="abc">
			 
		</div>
	</div>
	
	<br/>
	<div>
		简单单文件上传
		<div id="abcd">
			 
		</div>
	</div>
	
	<div>
		<input id="current_form_url_prefix" type="hidden"
			value="${ctx}/dytable/demo"> <a
			id="current_form_url" href="" target="_blank" style="display: none">查看当前动态表单</a>
	</div>
				 
<%@ include file="/pt/dytable/form_js.jsp"%>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/dytable/grid_demo.js"></script>
		<script type="text/javascript">
 
 $(function(){

		//创建上传控件
		 var ctlID = "fileupload_ctlid_multiple_demo";
		 var fileupload = new WellFileUpload(ctlID);

		//初始化上传控件
		//fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
		 //初始化上传控件
			fileupload.init(false,  $("#abc"),  false, true, []);
		 
		 $("#btn_"  + ctlID).click(function(){
			 alert("请查看日志");
			 console.log(JSON.stringify(WellFileUpload.files[ctlID]));
		 });
		 
			//创建上传控件
			var ctlID2 = "fileupload_ctlid_single_demo"; 
			fileupload = new WellFileUpload(ctlID2);

			//初始化上传控件
			//fileupload.initWithLoadFilesFromFileSystem(false,  $attachContainer,  formSign == "2", true, idValue, fieldName);
			 //初始化上传控件
				fileupload.init(false,  $("#abcd"),  false, false, []);
			 
			 
			 
				 
				 $("#btn_"  + ctlID2).click(function(){
					 alert("请查看日志");
					 console.log(JSON.stringify(WellFileUpload.files[ctlID2]));
				 });
			 
		 
		 
 });
 
 </script>
</body>
</html>
