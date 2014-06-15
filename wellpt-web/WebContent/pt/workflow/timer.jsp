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
<script type="text/javascript" src="${ctx}/pt/workflow/js/JSConstant.js"></script>
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
	window.dialogArguments = $.dialog.data("laArg");
	gsExistNames = ";"+window.dialogArguments["Names"]+";";
	gsFormID = window.dialogArguments["Form"];
	InitProperty("计时系统",true);
// -->
</script>
</head>
<script type="text/javascript">
	$(function(){
		//onload事件
		TimerLoadEvent();
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("timerReVal", null);
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e){
			TimerOKEvent();
		});
		//取消事件
		$("#cancel").click(function(){
			$.dialog.data("timerReVal", null);
			//window.close();
			$.dialog.close();
		});
		// 办理时限
		var loNode = opener.goWorkFlow.flowXML.selectSingleNode("./property/formID");
		var formUuid = loNode != null ? loNode.text() : "";
		var limitSetting = {
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
					$("#limitTimeLabel").val(treeNode.name);
					$("#limitTime").val(treeNode.data);
				}
			}
		};
		$("#limitTimeLabel").comboTree({
			labelField : "limitTimeLabel",
			valueField : "limitTime",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[formUuid],
			treeSetting : limitSetting,
			width: 195,
			height: 225
		});
		// 责任人
		$("#Ddutys").click(function(e){
			e.target.title = "选择责任人";
			bTimerActions(2, e);
		});
		// 计时环节
		$("#Dtasks").click(function(e){
			e.target.title = "选择计时环节";
			bTimerActions(3, e);
		});
		// 选择消息提醒人员
		$("#DalarmUsers").click(function(e){
			e.target.title = "选择消息提醒人员";
			bTimerActions(4, e);
		});
		// 选择要发起的预警流程
		$("#alarmFlowName").click(function(e){
			e.target.title = "选择要发起的预警流程";
			bTimerActions(5, e);
		});
		// 其他办理人员
		$("#DalarmFlowDoingUsers").click(function(e){
			e.target.title = "其他办理人员";
			bTimerActions(6, e);
		});
		// 逾期处理
		// 其他消息通知人员
		$("#DdueUsers").click(function(e){
			e.target.title = "其他消息通知人员";
			bTimerActions(7, e);
		});
		// 选择移交人员
		$("#DdueToUsers").click(function(e){
			e.target.title = "选择移交人员";
			bTimerActions(8, e);
		});
		// 选择转办环节
		$("#DdueToTask").click(function(e){
			e.target.title = "选择转办环节";
			bTimerActions(9, e);
		});
		// 选择要发起的催办流程
		$("#dueFlowName").click(function(e){
			e.target.title = "选择要发起的催办流程";
			bTimerActions(10, e);
		});
		// 其他办理人员
		$("#DdueFlowDoingUsers").click(function(e){
			e.target.title = "其他办理人员";
			bTimerActions(11, e);
		});
	});
</script>

<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr>
  	<td id="ID_PROMPT_INFO" style="height:25;vertical-align:bottom;" style="display:none">定义计时系统：</td>
  </tr>
  <tr>
    <td style="height:100%" valign="top">
    <table border="0" cellpadding="1" cellspacing="1" width="100%">
		<tr>
			<td width=15% style="padding-left: 15px;">名称</td>
			<td>
				<input name="name" value="" class="Border_Inset" style="width:40%" emptyMsg="请输入名称！" existMsg="名称已存在，请重新输入。"/>
				<label for="limitTimeLabel">办理时限</label>
				<input id="limitTimeLabel" name="limitTimeLabel" value="" class="Border_Inset" style="width:22%" emptyMsg="请输入办理时限！"/>
				<input id="limitTime" name="limitTime" type="hidden" emptyMsg="请输入办理时限！"/>
				<select name="limitUnit" style="width:15%">
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
			<td style="padding-left: 15px;"><label for="Ddutys">责任人</label></td>
			<td>
				<textarea id="Ddutys" name="Ddutys" onfocus="this.blur()" class="Border_Inset" style="width:95%;height:18" emptyMsg="请指定责任人！"></textarea>
				<input name="Dduty1" value="" type="hidden"/>
				<input name="duty1" value="" type="hidden"/>
				<input name="duty2" value="" type="hidden"/>
				<input name="duty4" value="" type="hidden"/>
				<input name="duty8" value="" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;"><label for="Dtasks">计时环节</label></td>
			<td>
				<textarea id="Dtasks" name="Dtasks" onfocus="this.blur()" class="Border_Inset" style="width:95%;height:30" emptyMsg="请指定计时环节！"></textarea>
				<input name="tasks" value="" type="hidden"/>
			</td>
		</tr>
		<tr>
		<td valign="top" style="padding-left: 15px;">预警提醒</td>
		<td valign="top">
		<span id="ID_Alarm">
		提前&nbsp;<input name="alarmTime" value="1" onblur="if (bIsDigital(this.value,1)==false){this.value='1';this.focus();}" style="width:10%">
		<select name="alarmUnit" style="width:20%">
			<option value="3">工作日
			<option value="2" selected>工作小时
			<option value="1">工作分钟
			<option value="86400">天
			<option value="3600">小时
			<option value="60">分钟
		</select>
		开始消息通知，共<input name="alarmFrequency" value="1" onblur="if (bIsDigital(this.value,1)==false){this.value='1';this.focus();}" style="width:10%"/>次
		<br>
		</span>
		<input type="checkbox" id="alarmObjects_Doing" name="alarmObjects" value="Doing" class="AutoSize" checked/><label for="alarmObjects_Doing">在办人员</label>
		<input type="checkbox" id="alarmObjects_Monitor" name="alarmObjects" value="Monitor" class="AutoSize"/><label for="alarmObjects_Monitor">督办人员</label>
		<input type="checkbox" id="alarmObjects_Tracer" name="alarmObjects" value="Tracer" class="AutoSize"/><label for="alarmObjects_Tracer">跟踪人员</label>
		<input type="checkbox" id="alarmObjects_Admin" name="alarmObjects" value="Admin" class="AutoSize"/><label for="alarmObjects_Admin">流程管理人员</label>
		<input type="checkbox" id="alarmObjects_Other" name="alarmObjects" value="Other" class="AutoSize" onclick="ID_AlarmOtherUser.style.display=this.checked?'':'none';"/><label for="alarmObjects_Other">其他人员</label>
		<span ID="ID_AlarmOtherUser" style="display:none">
			<textarea id="DalarmUsers" name="DalarmUsers" onfocus="this.blur()" class="Border_Inset" style="width:89%;height:18"></textarea>
			<label for="DalarmUsers">选择</label>
			<input name="DalarmUser1" value="" type="hidden"/>
			<input name="alarmUser1" value="" type="hidden"/>
			<input name="alarmUser2" value="" type="hidden"/>
			<input name="alarmUser4" value="" type="hidden"/>
			<input name="alarmUser8" value="" type="hidden"/>
		</span>
		<table  border="0" cellpadding="1" cellspacing="1" width="98%">
			<tr>
				<td align="center" width="12%">
					<label for="alarmFlowName">发起流程</label>
				</td>
				<td>
					<input id="alarmFlowName" name="alarmFlowName" class="Border_Inset" style="width:95%;" emptyMsg="请指定要发起的预警流程！" onfocus="this.blur()"/>
					<input name="alarmFlowID" value="" type="hidden">
				</td>
			</tr>
			<tr>
				<td align="center">办理人</td>
				<td>
					<input type="checkbox" id="alarmFlowDoings_Doing" name="alarmFlowDoings" value="Doing" class="AutoSize"/><label for="alarmFlowDoings_Doing">在办人员</label>
					<input type="checkbox" id="alarmFlowDoings_Monitor" name="alarmFlowDoings" value="Monitor" class="AutoSize" checked/><label for="alarmFlowDoings_Monitor">督办人员</label>
					<input type="checkbox" id="alarmFlowDoings_Tracer" name="alarmFlowDoings" value="Tracer" class="AutoSize"/><label for="alarmFlowDoings_Tracer">跟踪人员</label>
					<input type="checkbox" id="alarmFlowDoings_Admin" name="alarmFlowDoings" value="Admin" class="AutoSize"/><label for="alarmFlowDoings_Admin">流程管理人员</label>
					<input type="checkbox" id="alarmFlowDoings_Other" name="alarmFlowDoings" value="Other" class="AutoSize" onclick="ID_AlarmFlowDoing.style.display=(this.checked)?'':'none';"/><label for="alarmFlowDoings_Other">其他人员</label>
				</td>
			</tr>
			<tr id="ID_AlarmFlowDoing" style="display:none">
				<td align="center">&nbsp;</td>
				<td>
					<textarea id="DalarmFlowDoingUsers" name="DalarmFlowDoingUsers" onfocus="this.blur()" class="Border_Inset" style="width:90%;height:18"></textarea>
					<label for="DalarmFlowDoingUsers">选择</label>
					<input name="DalarmFlowDoingUser1" value="" type="hidden"/>
					<input name="alarmFlowDoingUser1" value="" type="hidden"/>
					<input name="alarmFlowDoingUser2" value="" type="hidden"/>
					<input name="alarmFlowDoingUser4" value="" type="hidden"/>
					<input name="alarmFlowDoingUser8" value="" type="hidden"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td valign="top" style="padding-left: 15px;">逾期处理</td>
		<td valign="top">
		<input type="checkbox" id="dueObjects_Doing" name="dueObjects" value="Doing" class="AutoSize" checked onclick="ID_DueMsgToDoing.style.display=this.checked?'':'none';"/><label for="dueObjects_Doing">消息催办在办人员</label>
		<span id="ID_DueMsgToDoing">
		&nbsp;&nbsp;每&nbsp;<input name="dueTime" value="1" onblur="if (bIsDigital(this.value,1)==false){this.value='1';this.focus();}" style="width:10%"/>
		<select name="dueUnit" style="width:18%">
			<option value="3" selected>工作日
			<option value="2">工作小时
			<option value="1">工作分钟
			<option value="86400">天
			<option value="3600">小时
			<option value="60">分钟
		</select>
		催办一次，共<input name="dueFrequency" value="1" onblur="if (bIsDigital(this.value,1)==false){this.value='1';this.focus();}" style="width:10%">次
		</span>
		<br>
		<input type="checkbox" id="dueObjects_Monitor" name="dueObjects" value="Monitor" class="AutoSize"/><label for="dueObjects_Monitor">通知督办人员</label>
		<input type="checkbox" id="dueObjects_Tracer" name="dueObjects" value="Tracer" class="AutoSize"/><label for="dueObjects_Tracer">通知跟踪人员</label>
		<input type="checkbox" id="dueObjects_Admin" name="dueObjects" value="Admin" class="AutoSize"/><label for="dueObjects_Admin">通知流程管理人员</label>
		<input type="checkbox" id="dueObjects_Other" name="dueObjects" value="Other" class="AutoSize" onclick="ID_DueToOtherUser.style.display=this.checked?'':'none';"/><label for="dueObjects_Other">通知其他人员</label>
		<span ID="ID_DueToOtherUser" style="display:none">
			<textarea id="DdueUsers" name="DdueUsers" onfocus="this.blur()" class="Border_Inset" style="width:89%;height:18"></textarea>
			<label for="DdueUsers">选择</label>
			<input name="DdueUser1" value="" type="hidden"/>
			<input name="dueUser1" value="" type="hidden"/>
			<input name="dueUser2" value="" type="hidden"/>
			<input name="dueUser4" value="" type="hidden"/>
			<input name="dueUser8" value="" type="hidden"/>
		</span>
		<select name="dueAction" style="width:95%" onchange="ID_DueToUser.style.display=this.value=='4'?'':'none';ID_DueToTask.style.display=this.value=='16'?'':'none';">
			<option value="0" selected>---不处理---
			<option value="1">移交给B岗人员办理
			<option value="2">移交给督办人员办理
			<option value="4">移交给其他人员办理
			<option value="8">退回上一个办理环节
			<option value="16">自动进入下一个办理环节
		</select>
		<span id="ID_DueToUser" style="display:none">
			<textarea id="DdueToUsers" name="DdueToUsers" onfocus="this.blur()" class="Border_Inset" style="width:89%;height:18"></textarea>
<!-- 			<input type="button" onclick="bTimerActions(8);" value="选择" class="HotSpot" title="选择移交人员"/> -->
			<label for="DdueToUsers">选择</label>
			<input name="DdueToUser1" value="" type="hidden"/>
			<input name="dueToUser1" value="" type="hidden"/>
			<input name="dueToUser2" value="" type="hidden"/>
			<input name="dueToUser4" value="" type="hidden"/>
			<input name="dueToUser8" value="" type="hidden"/>
		</span>
		<span ID="ID_DueToTask" style="display:none">
			<input id="DdueToTask" name="DdueToTask" value="" class="Border_Inset" style="width:89%" onfocus="this.blur()" multiplemsg="不支持多个环节，请选择一个环节！"/>
			<input name="dueToTask" value="" type="hidden"/>
<!-- 			<input type="button" onclick='bTimerActions(9);' value="选择" class="HotSpot" title="选择转办环节"/> -->
			<label for="DdueToTask">选择</label>
		</span>
		<table border="0" cellpadding="1" cellspacing="1" width="98%">
			<tr>
				<td align="center" width="12%"><label for="dueFlowName">发起流程</label></td>
				<td>
					<input id="dueFlowName" name="dueFlowName" class="Border_Inset" style="width:95%;" emptyMsg="请指定要发起的催办流程！" onfocus="this.blur()"/>
					<input name="dueFlowID" value="" type="hidden"/>
				</td>
			</tr>
			<tr>
				<td align="center">办理人</td>
				<td><input type="checkbox" id="dueFlowDoings_Doing" name="dueFlowDoings" value="Doing" class="AutoSize"/><label for="dueFlowDoings_Doing">在办人员</label>
				<input type="checkbox" id="dueFlowDoings_Monitor" name="dueFlowDoings" value="Monitor" class="AutoSize" checked/><label for="dueFlowDoings_Monitor">督办人员</label>
				<input type="checkbox" id="dueFlowDoings_Tracer" name="dueFlowDoings" value="Tracer" class="AutoSize"/><label for="dueFlowDoings_Tracer">跟踪人员</label>
				<input type="checkbox" id="dueFlowDoings_Admin" name="dueFlowDoings" value="Admin" class="AutoSize"/><label for="dueFlowDoings_Admin">流程管理人员</label>
				<input type="checkbox" id="dueFlowDoings_Other" name="dueFlowDoings" value="Other" class="AutoSize" onclick="ID_DueFlowDoing.style.display=(this.checked)?'':'none';"/><label for="dueFlowDoings_Other">其他人员</label></td>
			</tr>
			<tr id="ID_DueFlowDoing" style="display:none">
				<td align="center">&nbsp;</td>
				<td>
					<textarea id="DdueFlowDoingUsers" name="DdueFlowDoingUsers" onfocus="this.blur()" class="Border_Inset" style="width:90%;height:18"></textarea>
<!-- 					<input type="button" onclick="bTimerActions(11);" value="选择" class="HotSpot" title="其他办理人员"/> -->
					<label for="DdueFlowDoingUsers">选择</label>
					<input name="DdueFlowDoingUser1" value="" type="hidden"/>
					<input name="dueFlowDoingUser1" value="" type="hidden"/>
					<input name="dueFlowDoingUser2" value="" type="hidden"/>
					<input name="dueFlowDoingUser4" value="" type="hidden"/>
					<input name="dueFlowDoingUser8" value="" type="hidden"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
    </td>
  </tr>
  <tr>
    <td align="center" style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  	<td></td>
	  	<td align="right" style="height:30">
		     <input type=button id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
		     <input type=button id="cancel" class="BORDER_BUTTON" value="  取消  "/>
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