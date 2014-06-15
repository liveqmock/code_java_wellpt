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
	if(window.parent.dialogArguments != null){
		window.dialogArguments = window.parent.dialogArguments;
	}else{
		window.dialogArguments = $.dialog.data("laArg");
	}
	for (elem in window.dialogArguments ){
		switch( elem ){
		case "Property":
			var goProperty = window.dialogArguments["Property"];
			break;
		case "opener":
			var opener = window.dialogArguments["opener"];
			break;
		}
	}
	InitProperty("判断点属性");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		goForm.name.value=goProperty.getAttribute("name");
		goForm.description.value = goProperty.text();
		bInitPageLabel();
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("goProperty", null);
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e){
			goProperty.setAttribute("name",goForm.name.value);
			//goProperty.childNodes[0].nodeValue=goForm.description.value;
			goProperty.get(0).childNodes[0].nodeValue=goForm.description.value;
			//window.returnValue = goProperty;
			//window.close();
			$.dialog.data("goProperty", goProperty);
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		//事件阻止
		$("#description").keypress(function(e){
			e.stopPropagation();
		});
	});
</script>
</head>

<body style="overflow:scroll;">
<form>
	<table cellpadding=0 cellspacing=0 border=0 style="width:100%;">
		<tr style="height:30">
			<td width="50" align=right>名称：</td>
			<td width="335"><input name="name" value="" class="Border_Inset" style="width:100%"/></td>
			<td align="right" width="180">
				<input type=button id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
				<input type=button id="cancel" class="BORDER_BUTTON" value="  取消  "/>
			</td>
			<td width="15">&nbsp;</td>
		</tr>
	</table>
	<div id="ID_PageLabel" class="PageLabel" style="padding:10;width:570;height:300px;">
		<div id=ID_PageContent style="display:none;" Label="描述">
		<fieldset style="width:100%;height:100%">
		<legend style="color:black;">描述</legend>
		<textarea name="description" id="description" style="width:100%;height:95%" class="Border_Inset"></textarea>
		</fieldset>
		</div>
	</div>
</form>
</body>
</html>