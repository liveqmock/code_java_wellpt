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
	window.dialogArguments = $.dialog.data("flowSelectLaArg");
	opener = window.dialogArguments["opener"];
	InitProperty((window.dialogArguments["Title"]!=null && window.dialogArguments["Title"]!="")?window.dialogArguments["Title"]:"选择流程");
function ClickOnNode(poObj){
	if (poObj.children[0].src.indexOf("expand.gif")!=-1){
		poObj.children[0].src="${ctx}/pt/workflow/images/collapse.gif";
		loTR = poObj.parentElement.nextSibling;
		if(loTR!=null && loTR.innerHTML.indexOf("expand.gif")==-1 && loTR.innerHTML.indexOf("collapse.gif")==-1)
			loTR.style.display="";
	}else{
		poObj.children[0].src="${ctx}/pt/workflow/images/expand.gif";
		loTR = poObj.parentElement.nextSibling;
		if(loTR!=null && loTR.innerHTML.indexOf("expand.gif")==-1 && loTR.innerHTML.indexOf("collapse.gif")==-1)
			loTR.style.display="none";
	}
}
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		SelectFlowLoadEvent();
		
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
			var lsValue = opener.sGetFormFieldValue(goForm,"Flows");
			//window.returnValue = lsValue;
			//window.close();
			$.dialog.data("flowSelectReVal", lsValue);
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.close();
		});
	});
</script>
</head>

<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id=ID_PROMPT_INFO style="height:25;vertical-align:bottom;" style="display:">请选择流程：</td></tr>
  <tr>
    <td style="height:100%;padding:10px;" valign="top" id="ID_FlowTree">

    </td>
  </tr>
  <tr>
    <td align='center' style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr><td></td><td align="right" style="height:30">
	     <input type=button id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;
	     <input type=button id="cancel" class="BORDER_BUTTON" value="  取消  ">
	  </td>
	  <td width=15></td>
	</tr></table>
    </td>
  </tr>
</table>

</form>


</body>
</html>