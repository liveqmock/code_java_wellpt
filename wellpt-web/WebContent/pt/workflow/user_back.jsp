<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/resources/artDialog/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}/resources/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
<script type="text/javascript" src="${ctx}/pt/workflow/js/property.js"></script>
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
<!--
	window.dialogArguments = $.dialog.data("userbackArg");
	var gaBackUser = window.dialogArguments["User"];
	InitProperty("岗位替代设置");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		if (gaBackUser!=null){
			goForm.AUser.value=gaBackUser[0];
			goForm.AUserID.value = gaBackUser[1];
			goForm.BUsers.value=gaBackUser[2];
			goForm.BUserIDs.value = gaBackUser[3];
		}
		
		//确定事件
		$("#ok").click(function(e){
			if (goForm.AUser.value==""){
				alert(goForm.AUser.emptyMsg);
			}else if (goForm.BUsers.value==""){
				alert(goForm.BUsers.emptyMsg);
			}else{
				var laValue = new Array();
				laValue.push(goForm.AUser.value);
				laValue.push(goForm.AUserID.value);
				laValue.push(goForm.BUsers.value);
				laValue.push(goForm.BUserIDs.value);
				//window.returnValue = laValue;
				//window.close();
				$.dialog.data("userbackReVal", laValue);
				$.dialog.close();
			}
		});
		//取消事件
		$("#cancel").click(function(){
			//window.close();
			$.dialog.data("userbackReVal", null);
			$.dialog.close();
		});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table  style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id=ID_PROMPT_INFO style="height:25;vertical-align:bottom;">定义岗位替代：</td></tr>
  <tr>
    <td style="height:100%" valign="top">
    
<br>
    	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="100%">
<tr>
	<td align="center" width="15%"><INPUT TYPE=button onClick='PromptUnit(false,"AUser","All",this.title,true,false,4,"",false,false);' VALUE="A岗人员" CLASS="HotSpot" title="选择A岗人员"></td>
	<td><input NAME="AUser" CLASS="Border_Inset" STYLE="width:95%;" emptyMsg="请指定A岗人员！">
		<input Name="AUserID" value="" type="hidden"></td>
</tr>
<tr>
	<td align="center"><INPUT TYPE=button onClick='PromptUnit(false,"BUser","All",this.title,true,false,4,"",false,false);' VALUE="B岗人员" CLASS="HotSpot" title="选择B岗人员"></td>
	<td><TEXTAREA NAME="BUsers" CLASS="Border_Inset" STYLE="width:95%;height:30" emptyMsg="请指定B岗人员！"></TEXTAREA>
		<input Name="BUserIDs" value="" type="hidden"></td>
</tr>
</table>


    </td>
  </tr>
  <tr>
    <td align='center' style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr><td></td><td align="right" style="height:30">
	     <input type=button id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
	     <input type=button id="cancel" class="BORDER_BUTTON" value="  取消  "/>
	  </td>
	  <td width=15></td>
	</tr></table>
    </td>
  </tr>
</table>
</form>
</body>
</html>