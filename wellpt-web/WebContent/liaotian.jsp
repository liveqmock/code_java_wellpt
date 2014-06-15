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
 <script type="text/javascript">  
        //通过该方法与后台交互，确保推送时能找到指定用户  
         function onPageLoad(){  
            var userId = '123';  
            directController.onPageLoad(userId);  
          }  
         //推送信息  
         function showMessage(autoMessage){  
                alert(autoMessage);  
        }  
  </script>  
  </head>
  <body onload="onPageLoad();dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);"> 
	This is my DWR DEOM page. <hr>  
    <br>  
    <div id="DemoDiv">demo</div>  
</body>
</html>

