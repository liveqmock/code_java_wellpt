<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="rtx.RTXSvrApi"%> 
<% 

    RTXSvrApi rtxApi = new RTXSvrApi(); 
    String account = (String)request.getAttribute("loginName");//当前用户登录名
    String rtxServerIp = (String)request.getAttribute("rtxServerIp"); //服务器ip
    String key= rtxApi.getSessionKey(account); //获取sessionKey
    String rtxClientDownloadAddress = (String)request.getAttribute("rtxClientDownloadAddress");//rtx客户端下载地址
    System.out.println(account);
    System.out.println(rtxServerIp);
    System.out.println(key);
%> 
 <html>
<head>
</head>
<body text="#000000">
<html><head><script language="vbscript" id="clientEventHandlersVBS">
<!--
'单点登录
Sub Window_OnLoad()
    on error resume next
    Set objProp = RTXAX.GetObject("Property")   
    objProp.Value("RTXUsername") = "<%=account%>"
    objProp.Value("LoginSessionKey") = "<%=key%>"
    objProp.Value("ServerAddress") = "<%=rtxServerIp%>"
    objProp.Value("ServerPort") = 8000

    lsRTXDownload= "<%=rtxClientDownloadAddress%>"   

    RTXAX.Call 2, objProp
    If Err.Number <> 0 Then
	   if Err.Number=438 then 
	   	 MsgBox "请下载安装RTX客户端软件"
	   	 if lsRTXDownload<>"" Then Window.Open lsRTXDownload
	   Else
           MsgBox "Error # " & CStr(Err.Number) & " " & Err.Description
	   End if
        Err.Clear   ' Clear the error.
    End If
End Sub
-->
</script></head><body>
<OBJECT classid="clsid:5EEEA87D-160E-4A2D-8427-B6C333FEDA4D" id=RTXAX style="display:none"></OBJECT>
</body></html>
</body>
</html>
 