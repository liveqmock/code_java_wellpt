<%@ page import="java.util.*"
language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>首件巡检题库数据导入页面</title>
</head>
<body>
<table>
<tr>
<form action="../../../mps/updqb" method="post"  enctype="multipart/form-data">
<input type="text"  name="qid"  style="display:none;"/>
<td>上传文件<input type="file" name="attachment"></td>
</tr>
<tr>
<td >
<input type="submit"  />
</form>
<form method="POST"  action="../../../downloadquestion" name="s1" id="s1" >
<input type="submit"   value="下载" style="margin-top:20px;"/>
</form>
</td>
</tr>
<tr>
<td >注释：请下载上面的模板并根据要求格式填写上传的数据。格式不符则出错，导入失败。</td>
</tr>
</table>
</body>
</html>