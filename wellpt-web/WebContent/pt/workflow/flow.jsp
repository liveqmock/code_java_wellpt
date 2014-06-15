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
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/UnitTree.js"></script>
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
	InitProperty("流程属性",true,true);
	
	$().ready(function(){
		//InitProperty("流程属性",true,true);
		
		FlowLoadEvent();
		
		//设置保存后流程ID不可更改
		var id = opener.goWorkFlow.flowXML.getAttribute("id");
		var uuid = opener.goWorkFlow.flowXML.getAttribute("uuid");
		if(id != undefined && uuid != undefined){
			$("#id").attr("disabled", "disabled");
		}
		
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
			FlowOKEvent(e);
			if(window.parent.dialogCallback){
				window.parent.dialogCallback();
			}
			alert("保存成功!");
			//$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(){
			opener.goWorkFlow.equalFlowID = null;
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		// 选择发起人
		$("#Dcreators").click(function(e){
			SetUnit("creator", "选择发起人");
		});
		// 选择参与人
		$("#Dusers").click(function(e){
			SetUnit("user", "选择参与人");
		});
		//选择督办人
		$("#Dmonitors").click(function(e){
			e.target.title = "选择督办人";
			bFlowActions(3, e);
		});
		//选择监控者
		$("#Dadmins").click(function(e){
			e.target.title = "选择监控者";
			bFlowActions(4, e);
		});
		//选择开始时间
		$("#DbeginDirections").click(function(e){
			e.target.title = "选择开始流向";
			bFlowActions(6, e);
		});
		//选择开始时间
		$("#DendDirections").click(function(e){
			e.target.title = "选择结束流向";
			bFlowActions(7, e);
		});
		//文件分发对象
		$("#DfileRecipients").click(function(e){
			e.target.title = "选择文件分发对象";
			bFlowActions(8, e);
		});
		//消息分发对象
		$("#DmsgRecipients").click(function(e){
			e.target.title = "选择消息分发对象";
			bFlowActions(9, e);
		});
		//消息eqFlow等价流程
		$("#EQFlowName").click(function(e){
			bFlowActions(10, e);
		});
		//定时系统增加
		$("#timerAdd").click(function(e){
			bFlowActions(11, e);
		});
		//定时系统编辑
		$("#timerEdit").click(function(e){
			bFlowActions(12, e);
		});
		$("#DTimer").dblclick(function(e){
			bFlowActions(12, e);
		});
		//定时系统删除
		$("#timerDelete").click(function(e){
			bFlowActions(13, e);
		});
		//增加岗位代替 
		$("#backuserAdd").click(function(e){
			bFlowActions(14, e);
		});
		//编辑岗位代替 
		$("#backuserEdit").click(function(e){
			bFlowActions(15, e);
		});
		//删除岗位代替 
		$("#backuserDelete").click(function(e){
			bFlowActions(16, e);
		});
		
		function SetUnit(target, title){
			var url = ctx + "/resources/pt/js/org/unit/flow_unit.htm";
			var laArg = new Array();
			laArg["Name"] = goForm["D" + target + "1"].value;
			laArg["ID"] = goForm[target + "1"].value;
			laArg["Title"] = title;
			laArg["Multiple"] = true;
			laArg["SelectType"]=6;
			laArg["NameType"]="21";
			laArg["ShowType"]=true;
			laArg["Type"]="MyUnit";
			laArg["LoginStatus"]=false;
			laArg["Exclude"]=null;
			openDialog(url, title, 850, 500, "unitLaArg", laArg, function(){
				var laValue = $.dialog.data("unitLaValue");
				$.dialog.data("unitLaValue", null);
				if (laValue == null) {
					return;
				}
				goForm["D" + target + "s"].value = laValue["name"];
				goForm["D" + target + "1"].value = laValue["name"];
				goForm[target + "1"].value = laValue["id"];
			});
		}
		
		var printTemplateSetting = {
			view : {
				showIcon : false,
				showLine : false
			},
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getPrintTemplates"
				}
			}
		};
		$("#printTemplate").comboTree({
			labelField : "printTemplate",
			valueField : "printTemplateId",
			treeSetting : printTemplateSetting,
			autoInitValue : false,
			width: 467,
			height: 260
		});
		// 承诺期限
		var loNode = opener.goWorkFlow.flowXML.selectSingleNode("./property/formID");
		var formUuid = loNode != null ? loNode.text() : "";
		var dueTimeSetting = {
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getFormFields",
					"data" : formUuid
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick : function onClick(event, treeId, treeNode) {
					$("#dueTimeLabel").val(treeNode.name);
					$("#dueTime").val(treeNode.data);
				}
			}
		};
		$("#dueTimeLabel").comboTree({
			labelField : "dueTimeLabel",
			valueField : "dueTime",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[formUuid],
			treeSetting : dueTimeSetting,
			width: 225,
			height: 225
		});
		
		// 别名ID可编辑
		$("#id").removeAttr("disabled");
	});
</script>
</head>
<body style="overflow:scroll;">
<form>
<table cellpadding="0" cellspacing="0" border="0" style="width:100%;">
	<tr style="height:30">
		<td width="50" align=right>名称：</td>
		<td width="205">
			<input id="name" name="name" value="" class="Border_Inset" style="width:100%">
		</td>
		<td width="50" align=right Version="Simple">ID：</td>
		<td Version="Simple" width="100">
			<input id="id" name="id" value="" class="Border_Inset" style="width:100%">
		</td>
		<td align="right" width="180">
			<input type="button" id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;
			<input type="button" id="cancel" value="  取消  " class="BORDER_BUTTON">
		</td>
		<td width="15">&nbsp;</td>
	</tr>
</table>

<div id="ID_PageLabel" class="PageLabel" style="padding:10;width:570;height:315px;">

<div id="ID_PageContent" style="display:none;" Label="基本属性" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">基本属性</legend>
	<table  cellpadding="3" cellspacing="5" border="0" width="100%">
		<tr>
			<td width="15%" style="padding-left: 15px;">流程类别</td>
			<td><select id="DCategory" name="DCategory" style="width:100%"></select></td>
		</tr>
		<tr>
			<td style="padding-left: 15px;">流程编号</td>
			<td><input id="code" name="code" value="" class="Border_Inset" style="width:100%"></input></td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;"><label for="EQFlowName">等价流程</label></td>
			<td><input id="EQFlowName" name="EQFlowName" value="" class="Border_Inset" style="width:100%"><input id="EQFlowID" name="EQFlowID" value="" type="hidden"></td>
		</tr>
		<tr>
			<td style="padding-left: 15px;">对应表单</td>
			<td><select id="DForm" name="DForm" style="width:100%"></select></td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;">自由流程</td>
			<td>
				<input type="radio" id="free" name="isFree" value="1" class="AutoSize"/><label for="free">是</label>
				<input type="radio" id="nofree" name="isFree" value="0" checked class="AutoSize"/><label for="nofree">否</label>
			</td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;">启用流程</td>
			<td>
				<input type="radio" id="active" name="isActive" value="1" checked class="AutoSize"/><label for="active">启用</label>
				<input type="radio" id="deactive" name="isActive" value="0" class="AutoSize"/><label for="deactive">禁用</label>
			</td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;">应用独立</td>
			<td>
				<input type="radio" id="independent" name="isIndependent" value="1" class="AutoSize"/><label for="independent">是</label>
				<input type="radio" id="notIndependent" name="isIndependent" value="0" checked class="AutoSize"/><label for="notIndependent">否</label>
			</td>
		</tr>
	</table>
	</fieldset>
</div>
<div id="ID_PageContent" style="display:none;" Label="流程权限" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">流程权限</legend>
	<table cellpadding="1" cellspacing="3" border=0 width="100%">
		<tr>
			<td style="padding-left: 15px;"><label for="Dcreators">发起人</label></td>
			<td>
				<textarea id="Dcreators" name="Dcreators" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:50"></textarea>
				<input name="Dcreator1" value="" type="hidden"/>
				<input name="creator1" value="" type="hidden"/>
			</td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;"><label for="Dusers">参与人</label></td>
			<td>
				<textarea id="Dusers" name="Dusers" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:50"></textarea>
				<input name="Duser1" value="" type="hidden"/>
				<input name="user1" value="" type="hidden"/>
			</td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;"><label for="Dmonitors">督办人</label></td>
			<td>
				<textarea id="Dmonitors" name="Dmonitors" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:50"></textarea>
				<input name="Dmonitor1" value="" type="hidden"/>
				<input name="monitor1" value="" type="hidden"/>
				<input name="monitor2" value="" type="hidden"/>
				<input name="monitor4" value="" type="hidden"/>
				<input name="monitor8" value="" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" width="15%"><label for="Dadmins">监控者</label></td>
			<td>
				<textarea id="Dadmins" name="Dadmins" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:50"></textarea>
				<input name="Dadmin1" value="" type="hidden">
				<input name="admin1" value="" type="hidden">
				<input name="admin2" value="" type="hidden">
				<input name="admin4" value="" type="hidden">
				<input name="admin8" value="" type="hidden">
			</td>
		</tr>
	</table>
	</fieldset>
</div>
<div id="ID_PageContent" style="display:none;" Label="计时系统" Version="Simple" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">计时系统</legend>
	<table cellpadding="3" cellspacing="5" border="0" width="100%">
		<tr>
			<td><select id="DTimer" name="DTimer" size="6" style="width:98%"></select>&nbsp;</td>
			<td align="center" width="15%">
				<input type="button" id="timerAdd" value=" 增加 " class="BORDER_BUTTON"/>
				<input type="button" id="timerEdit" value=" 编辑 " class="BORDER_BUTTON"/>
				<input type="button" id="timerDelete" value=" 删除 " class="BORDER_BUTTON"/>
			</td>
		</tr>
	</table>
	<table cellpadding="1" cellspacing="3" border="0" width="100%">
		<tr>
			<td align="center" width="15%"><label for="dueTimeLabel">承诺期限</label></td>
			<td>
				<input id="dueTimeLabel" name="dueTimeLabel" value="" class="Border_Inset" style="width:50%">
				<input id="dueTime" name="dueTime" type="hidden" />
				<select name="timeUnit" style="width:20%">
					<option value="3" selected>工作日
					<option value="2">工作小时
					<option value="1">工作分钟
					<option value="86400">天
					<option value="3600">小时
					<option value="60">分钟
				</select>
			</td>
		</tr>
		<tr>
			<td align=center><label for="DbeginDirections">选择开始流向</label></td>
			<td>
				<textarea id="DbeginDirections" name="DbeginDirections" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:40"></textarea>
				<input id="beginDirections" name="beginDirections" value="" type="hidden">
			</td>
		</tr>
		<tr>
			<td align=center><label for="DendDirections">结束时间</label></td>
			<td>
				<textarea id="DendDirections" name="DendDirections" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:40"></textarea>
				<input id="endDirections" name="endDirections" value="" type="hidden">
			</td>
		</tr>
	</table>
	</fieldset>
</div>
<div id="ID_PageContent" style="display:none;" Label="高级设置" Version="Simple" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">高级设置</legend>
	<table cellpadding="0" cellspacing="2" border="0" width="100%">
		<tr style="display: none;">
			<td style="padding-left: 15px;" width=15%>正文模板</td>
			<td><textarea id="fileTemplate" name="fileTemplate" class="Border_Inset" style="width:100%;height:32"></textarea></td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" width=15%><label for="printTemplate">打印模板</label></td>
			<td><textarea id="printTemplate" name="printTemplate" class="Border_Inset" style="width:100%;height:32"></textarea>
				<input id="printTemplateId" name="printTemplateId" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;">文件分发</td>
			<td>
				<input type="radio" id="sendFile" name="isSendFile" value="1" class="AutoSize" onclick="ID_SendFile.style.display='';"/><label for="sendFile">是</label>
				<input type="radio" id="noSendFile" name="isSendFile" value="0" checked class="AutoSize" onclick="ID_SendFile.style.display='none';"/><label for="noSendFile">否</label>
			</td>
		</tr>
		<tr id="ID_SendFile" style="display:none">
			<td colspan=2>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width=30% style="padding-left: 25px;"><label for="DfileRecipients">分发对象</label></td>
					<td>
						<textarea id="DfileRecipients" name="DfileRecipients" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:36"></textarea>
						<input name="DfileRecipient1" value="" type="hidden">
						<input name="fileRecipient1" value="" type="hidden">
						<input name="fileRecipient2" value="" type="hidden">
						<input name="fileRecipient4" value="" type="hidden">
						<input name="fileRecipient8" value="" type="hidden">
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;">消息分发</td>
			<td>
				<input type="radio" id="sendMsg" name="isSendMsg" value="1" class="AutoSize" onclick="ID_SendMsg.style.display='';"><label for="sendMsg">是</label>
				<input type="radio" id="noSendMsg" name="isSendMsg" value="0" checked class="AutoSize" onclick="ID_SendMsg.style.display='none';"><label for="noSendMsg">否</label>
			</td>
		</tr>
		<tr id="ID_SendMsg" style="display:none">
			<td colspan=2>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width=30% style="padding-left: 25px;"><label for="DmsgRecipients">分发对象</label></td>
				<td>
					<textarea id="DmsgRecipients" name="DmsgRecipients" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:36"></textarea>
					<input name="DmsgRecipient1" value="" type="hidden">
					<input name="msgRecipient1" value="" type="hidden">
					<input name="msgRecipient2" value="" type="hidden">
					<input name="msgRecipient4" value="" type="hidden">
					<input name="msgRecipient8" value="" type="hidden">
				</td>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" valign=top><br>岗位替代</td>
			<td>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><select name="DBackUser" size="5" style="width:98%" ondblClick="bFlowActions(15);"></SELECT>&nbsp;</td>
					<td align="center" width="15%">
					<input type="button" id="backuserAdd" value=" 增加 " class="BORDER_BUTTON">
					<input type="button" id="backuserEdit" value=" 编辑 " class="BORDER_BUTTON">
					<input type="button" id="backuserDelete" value=" 删除 " class="BORDER_BUTTON">
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
</div>
</div>
</form>
</body>
</html>