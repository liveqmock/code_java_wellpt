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
	InitProperty("流向属性",true);
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		DirectionLoadEvent();
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
			DirectionOKEvent();
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		//增加分支条件
		$("#conditionAdd").click(function(e){
			bDirectionActions(1, e);
		});
		//插入分支条件
		$("#conditionInsert").click(function(e){
			bDirectionActions(2, e);
		});
		//编辑分支条件
		$("#conditionEdit").click(function(e){
			bDirectionActions(3, e);
		});
		//删除分支条件
		$("#conditionDelete").click(function(e){
			bDirectionActions(4, e);
		});
		//选择文件分发对象
		$("#DfileRecipients").click(function(e){
			e.target.title = "选择文件分发对象";
			bDirectionActions(5, e);
		});
		//选择消息分发对象
		$("#DmsgRecipients").click(function(e){
			e.target.title = "选择消息分发对象";
			bDirectionActions(6, e);
		});
	});
</script>
</head>

<body style="overflow:scroll;">
<form>
<table cellpadding=0 cellspacing=0 border=0 style="width:100%;">
<tr style="height:30"><td width="50" align=right>名称：</td>
	<td width="205"><INPUT NAME="name" VALUE="" CLASS="Border_Inset" STYLE="width:100%"/></td>
	<td width="50" align=right Version="Simple">ID：</td>
	<td Version="Simple" width="100"><INPUT NAME="id" VALUE="" CLASS="Border_Inset" STYLE="width:100%"></td>
	<td align="right" width="180">
		<input type=button id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;
		<input type=button id="cancel" class="BORDER_BUTTON" value="  取消  ">
	</td>
	<td width="15">&nbsp;</td>
</tr>
</table>

<div id="ID_PageLabel" class="PageLabel" style="padding:10;width:570;height:315px;">

<div id="ID_PageContent_Condiction" style="display:none;" Label="分支条件">
<FIELDSET style="width:100%;height:100%"><LEGEND style="color:black;">分支条件</LEGEND>
<TABLE  border=0 cellpadding='0' cellspacing='0' WIDTH="100%">
<tr><td>
<input id="isDefault_0" type="radio" name="isDefault" value="0" class="AutoSize" onClick="if (this.checked==true) {ID_DefaultDirection.style.display='';}else{ID_DefaultDirection.style.display='none';}"><label for="isDefault_0">设置条件</label>
<INPUT id="isDefault_1" TYPE="radio" NAME="isDefault" VALUE="1" CLASS="AutoSize" onClick="if (this.checked==true) {ID_DefaultDirection.style.display='none';}else{ID_DefaultDirection.style.display='';}"><label for="isDefault_1">缺省分支(其他分支都不满足时使用此分支)</label>
</td></tr>
<tr id="ID_DefaultDirection"><td>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="100%">
	<tr>
	<td><SELECT NAME="DCondition" Size=8 STYLE="width:100%" onDblClick='bDirectionActions(3);'></SELECT></td>
	<td align=center width=20%>
	<input type=button id="conditionAdd" value=" 增加 " class="BORDER_BUTTON">
	<input type=button id="conditionInsert" value=" 插入 " class="BORDER_BUTTON">
	<input type=button id="conditionEdit" value=" 编辑 " class="BORDER_BUTTON">
	<input type=button id="conditionDelete" value=" 删除 " class="BORDER_BUTTON">
	</td>
	</tr>
	</table>
</td></tr>
<tr Version="Simple"><td><INPUT id="isEveryCheck" TYPE=checkbox NAME="isEveryCheck" VALUE="1" CLASS="AutoSize"><label for="isEveryCheck">每次提交时检查条件</label></td></tr>
</table>
</FIELDSET>
</div>
<div id="ID_PageContent" style="display:none;" Label="消息分发" Version="Simple">
<FIELDSET style="width:100%;height:100%"><LEGEND style="color:black;">消息分发</LEGEND>
<TABLE  border=0 cellpadding='0' cellspacing='2' WIDTH="100%">
<tr>
	<td style="padding-left: 15px;" width=15%>文件分发</td>
	<td>
	<INPUT id="isSendFile_1" TYPE=radio NAME="isSendFile" VALUE="1" CLASS="AutoSize" onclick="ID_SendFile.style.display='';"><label for="isSendFile_1">是</label>
	<INPUT id="isSendFile_0" TYPE=radio NAME="isSendFile" VALUE="0" CHECKED CLASS="AutoSize" onclick="ID_SendFile.style.display='none';"><label for="isSendFile_0">否</label>
	</td>
</tr>
<tr id=ID_SendFile style="display:none"><td colspan=2>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="100%"><tr>
	<td style="padding-left: 15px;" width=30%><label for="DfileRecipients">分发对象</label></td>
	<td><TEXTAREA id="DfileRecipients" NAME="DfileRecipients" onFocus="this.blur()" CLASS="Border_Inset" STYLE="width:100%;height:36"></TEXTAREA>
		<INPUT NAME="DfileRecipient1" VALUE="" type="hidden">
		<INPUT NAME="fileRecipient1" VALUE="" type="hidden">
		<INPUT NAME="fileRecipient2" VALUE="" type="hidden">
		<INPUT NAME="fileRecipient4" VALUE="" type="hidden">
		<INPUT NAME="fileRecipient8" VALUE="" type="hidden"></td>
	</tr></table>
</td></tr>
<tr>
	<td style="padding-left: 15px;">消息分发</td>
	<td>
	<INPUT id="isSendMsg_1" TYPE=radio NAME="isSendMsg" VALUE="1" CLASS="AutoSize" onclick="ID_SendMsg.style.display='';"><label for="isSendMsg_1">是</label>
	<INPUT id="isSendMsg_0" TYPE=radio NAME="isSendMsg" VALUE="0" CHECKED CLASS="AutoSize" onclick="ID_SendMsg.style.display='none';"><label for="isSendMsg_0">否</label>
	</td>
</tr>
<tr id=ID_SendMsg style="display:none"><td colspan=2>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="100%">
	<tr>
	<td style="padding-left: 15px;" width=30%><label for="DmsgRecipients">分发对象</label></td>
	<td><TEXTAREA id="DmsgRecipients" NAME="DmsgRecipients" onfocus="this.blur()" CLASS="Border_Inset" STYLE="width:100%;height:36"></TEXTAREA>
		<INPUT NAME="DmsgRecipient1" VALUE="" type="hidden">
		<INPUT NAME="msgRecipient1" VALUE="" type="hidden">
		<INPUT NAME="msgRecipient2" VALUE="" type="hidden">
		<INPUT NAME="msgRecipient4" VALUE="" type="hidden">
		<INPUT NAME="msgRecipient8" VALUE="" type="hidden"></td>
	</tr>
	</table>
</td></tr>
</table>
</FIELDSET>
</div>

</div>

</form>
</body>
</html>