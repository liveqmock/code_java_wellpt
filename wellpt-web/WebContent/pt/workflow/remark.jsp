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
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
<!--
	window.dialogArguments = $.dialog.data("remarkLaArg");
	var gsValue = window.dialogArguments["value"];
	var opener = window.dialogArguments["opener"];
	InitProperty("信息记录定义");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		bInitRemarkValue();
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("remarkReVal", null);
				$.dialog.close();
			}
		});
		
		//确定事件
		$("#ok").click(function(e){
			//window.returnValue = sRemarkBuildValue();
			//if (window.returnValue!=null) window.close();
			$.dialog.data("remarkReVal", sRemarkBuildValue());
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			$.dialog.data("remarkReVal", null);
			//window.close();
			$.dialog.close();
		});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id="ID_PROMPT_INFO" style="height:25;vertical-align:bottom;">请定义信息记录项：</td></tr>
  <tr>
    <td style="height:100%" valign="top">
 	<table border="0" cellpadding="1" cellspacing="3" width="100%">
		<tr>
			<td align="center" width=15%>信息名称</td>
			<td><input name="name" value="" class="Border_Inset" style="width:100%"/></td>
		</tr>
		<tr>
			<td align="center">显示位置</td>
			<td><input id="fieldLabel" name="fieldLabel" value="" class="Border_Inset" style="width:100%"/>
			<input id="field" name="field" type="hidden" value="" class="Border_Inset" style="width:100%"/></td>
		</tr>
		<tr>
			<td align="center">信息格式</td>
			<td><select name="DFormat" style="width:100%"></select></td>
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
	     <input type="button" id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
	     <input type="button" id="cancel" class="BORDER_BUTTON" value="  取消  "/>
		</td>
	  	<td width="15"></td>
	</tr></table>
    </td>
  </tr>
</table>
</form>
</body>
<script type="text/javascript">
	$().ready(function(){
		var loNode = opener.goWorkFlow.flowXML.selectSingleNode("./property/formID");
		var formUuid = loNode != null ? loNode.text() : "";
		var setting = {
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getFormFields",
					"data" : formUuid
				}
			}
		};
		$("#fieldLabel").comboTree({
			labelField : "fieldLabel",
			valueField : "field",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[formUuid],
			treeSetting : setting,
			width: 308,
			height: 250
		});
	});
</script>
</html>