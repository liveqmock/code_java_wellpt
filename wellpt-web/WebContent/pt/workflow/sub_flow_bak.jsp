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
	InitProperty("新流程属性",true);
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		SubFlowLoadEvent();
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
			SubFlowOKEvent();
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		//选择子流程
		$("#subFlow").click(function(e){
			bSubFlowActions(1, null, e);
		});
	});
</script>
</head>

<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table cellpadding=0 cellspacing=0 border=0 style="width:100%;">
<tr style="height:30"><td width="50" align=right>名称：</td><td width="205"><INPUT NAME="name" VALUE="" CLASS="Border_Inset" STYLE="width:100%"></td><td width="50" align=right>ID：</td><td width="100"><INPUT NAME="id" VALUE="" CLASS="Border_Inset" STYLE="width:100%"></td><td align="right" width="180"><input type=button id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;<input type=button id="cancel" class="BORDER_BUTTON" value="  取消  "></td><td width="15">&nbsp;</td></tr>
</table>

<div id="ID_PageLabel" class="PageLabel" style="padding:10;width:570;height:300px;">

<div id=ID_PageContent style="display:none;" Label="基本属性">
<FIELDSET style="width:100%;height:100%"><LEGEND style="color:black;">基本属性</LEGEND>
<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="100%">
<tr>
	<td width=20% align=center>
		<span id="ID_NewFlowLebal" style="display:none">新流程</span>
		<span id="ID_SelectFlows"><INPUT id="subFlow" TYPE=button VALUE="新流程" CLASS="HotSpot" title="选择新流程"></>
	</td>
	<td>
	<INPUT TYPE=radio NAME="isSetFlow" VALUE="0" CHECKED CLASS="AutoSize" onClick="bSubFlowActions(3,'0');">现在确定
	<INPUT TYPE=radio NAME="isSetFlow" VALUE="1" CLASS="AutoSize" onClick="bSubFlowActions(3,'1');">工作提交时选择具体流程
	</td>
</tr>
<tr id="ID_NewFlowNames">
	<td align=center>&nbsp;</td>
	<td><TEXTAREA NAME="newFlowNames" onFocus="this.blur()" CLASS="Border_Inset" STYLE="width:100%;height:40"></TEXTAREA>
		<input name="newFlowIDs" value="" type="hidden"></td>
</tr>
<tr>
	<td align=center>&nbsp;</td>
	<td><INPUT TYPE=checkbox NAME="isAutoSubmit" VALUE="1" CLASS="AutoSize">自动提交新流程</td>
</tr>
<tr>
	<td align=center>拷贝信息</td>
	<td><INPUT TYPE=checkbox NAME="isCopyAll" VALUE="1"  checked CLASS="AutoSize" onClick="if (this.checked==true) ID_SetCopyField.style.display='none'; else ID_SetCopyField.style.display='';">整个文档</td>
</tr>
<tr id="ID_SetCopyField" style="display:none">
	<td align=center><INPUT TYPE=button onClick='bSubFlowActions(2);' VALUE="表单域" CLASS="HotSpot"></td>
	<td><TEXTAREA NAME="copyFields" CLASS="Border_Inset" STYLE="width:100%;height:40"></TEXTAREA>
	<br><INPUT TYPE=checkbox NAME="isCopyBody" VALUE="1" CLASS="AutoSize">拷贝文档中的正文
	<INPUT TYPE=checkbox NAME="isCopyAttach" VALUE="1" CLASS="AutoSize">拷贝文档中的附件
	</td>
</tr>
</table>
</FIELDSET>
</div>

</div>


</form>


</body>
</html>