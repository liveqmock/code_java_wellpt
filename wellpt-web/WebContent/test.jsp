<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="${ctx}/resources/jquery/jquery.js" type='text/javascript' ></script>
<script src='${ctx}/dwr/engine.js' type='text/javascript'></script> 
<script src='${ctx}/dwr/util.js' type='text/javascript'></script>
<script src='${ctx}/dwr/interface/directController.js' type='text/javascript'></script>  
<script src='${ctx}/resources/pt/js/common/jquery.alerts.js' type='text/javascript'></script>  
<script type="text/javascript">  
        function aa(){  
        	alert(1);
        	directController.showMenu(1, function(data) {  
                alert(data); 
            });   
        }  
</script>  
<style>
#popup_title {
    background: url("${ctx}/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
    height: 27px;
    padding-top: 3px;
}
#popup_title_name{
	color: #FFFFFF;
    font-size: 15px;
    text-indent: 15px;
    float: left;
}
#popup_close{
	background: url("${ctx}/resources/theme/images/v1_close.png") no-repeat scroll 0 0 transparent;
    cursor: pointer;
    float: right;
    height: 20px;
    margin-top: 5px;
    width: 20px;
}
#popup_close:hover{
	background: url("${ctx}/resources/theme/images/v1_close2.png") no-repeat scroll 0 0 transparent;
}
#popup_content {
    height: 120px;
}
#popup_message_div {
    height: 100px;
}
#popup_message_icon {
    float: left;
    height: 50px;
    margin-left: 30px;
    margin-top: 30px;
    width: 50px;
    background: url("${ctx}/resources/theme/images/v1_alert.png") no-repeat scroll 0 0 transparent;
}
#popup_message {
	float: left;
    font-size: 14px;
    height: 60px;
    margin-left: 10px;
    padding-top: 40px;
    width: 300px;
}
#close_time_div {
    text-align: center;
}
</style>
</head>
<body>
	<form action="/wellpt-web/basicdata/excelimportrule/submitExcel3" method="post" enctype="multipart/form-data">
		file:<input type="file" name="uploadFile"/>
		code:<input type="text" value="3" name="code"/>
		<input type="submit" value="submit"/>
	</form>
	<br />
	
	<input type="button" value="callDwr" onclick="aa();" /> 
<!-- 	<iframe src="/wellpt-web/passport/admin/home" width=400 height=400 align=center scrolling=no ></iframe>  -->
	<input type="button" value="oAlert" onclick="oAlert('测试内容','测试标题');" />
	<input type="button" value="pageLock" onclick="pageLock('show');" />
</body>
</html>

