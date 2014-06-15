<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>
<style type="text/css">
</style>
</head>
<body>
			<div style="display: none">  
				<iframe  name="upload_file" style="width: 1px; height: 0px;" ></iframe> 
			</div>
			<form id="upload_form" action="${ctx }/ckfinder/upload/image?command=upload" method="post" enctype="multipart/form-data"  target="upload_file">
				<input type="file" name="file" id="file" value="上传" />
				<input type="submit" value="上传" />
			</form>	
			<input type="hidden" name="pagePath" id="_page_path" value="${pagePath }">			
			
			<script type="text/javascript">
		        var _page_path = document.getElementById("_page_path").value;
		        if(null!=_page_path  && ""!=_page_path){
		               window.returnValue=_page_path;
		               window.close();
		        }
		    </script>
			
</body>
</html>
