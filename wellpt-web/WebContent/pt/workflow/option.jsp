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
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
<script src="${ctx}/pt/workflow/js/property.js"></script>
<script type="text/javascript">
<!--
	window.dialogArguments = $.dialog.data("optionLaArg");
	var gsExistOpt = window.dialogArguments["Exist"];
	InitProperty("意见立场定义");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e){
			if (goForm.Name.value==""){
				alert(goForm.Name.emptyMsg);
			}else if (goForm.Value.value==""){
				alert(goForm.Value.emptyMsg);
			}else if (gsExistOpt.indexOf(";"+goForm.Name.value+"|")!=-1){
				alert(goForm.Name.existMsg);
			}else if (gsExistOpt.indexOf("|"+goForm.Value.value+";")!=-1){
				alert(goForm.Value.existMsg);
			}else{
				//window.returnValue = goForm.Name.value+"|"+goForm.Value.value;
				//window.close();
				$.dialog.data("optionReVal", goForm.Name.value+"|"+goForm.Value.value);
				$.dialog.close();
			}
		});
		//取消事件
		$("#cancel").click(function(e){
			$.dialog.data("optionReVal", null);
			//window.close();
			$.dialog.close();
		});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id=ID_PROMPT_INFO style="height:25;vertical-align:bottom;">意见立场定义：</td></tr>
  <tr>
    <td style="height:100%" valign="top">
   	<table  border="0" cellpadding="1" cellspacing="3" width="100%">
		<tr>
			<td width="15%" align="center">意见名称</td>
			<td><input name="Name" value="" class="Border_Inset" style="width:90%" emptyMsg="请输入意见立场名称。" existMsg="名称已经存在。"/></td>
		</tr>
		<tr>
			<td align="center">意见值</td>
			<td><input name="Value" value="" class="Border_Inset" style="width:90%" emptyMsg="请输入意见立场值。" existMsg="值已经存在。"/></td>
		</tr>
	</table>
    </td>
  </tr>
  <tr>
    <td align='center' style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  	<td></td>
	  	<td align="right" style="height:30">
	   		<input type=button id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;
	   		<input type=button id="cancel" class="BORDER_BUTTON" value="  取消  ">
		</td>
		<td width="15"></td>
	  </tr>
	</table>
    </td>
  </tr>
</table>
</form>
</body>
</html>