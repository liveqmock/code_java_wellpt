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
<style type="text/css">
	#tabs ul {
  		padding: 0;
  		margin: 0 0 10px 25px;
  		margin-left: 0;
	}
	#tabs ul li {
		float: left;
		list-style: none;
		border-bottom: 1px dotted #ccc;
		padding-right: 5px;
	}
	#tabs ul li a {
	}
	#tabs ul li a:hover {
		color: #000;
		font-weight: bold;
	}
	#tabs ul a {
		text-decoration: none;
	}
</style>
<script type="text/javascript">
<!--
	InitProperty("环节属性",true,true);
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		TaskLoadEvent();
		
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
			TaskOKEvent();
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.data("goProperty", null);
			$.dialog.close();
		});
		//选择承办人
		$("#Dusers").click(function(e){
			e.target.title = "选择承办人";
			bTaskActions(0, "", e);
		});
		//选择抄送人
		$("#DcopyUsers").click(function(e){
			e.target.title = "选择抄送人";
			bTaskActions(1, "", e);
		});
		//选择转办环节
		$("#alltask").click(function(e){
			bTaskActions(2, "", e);
		});
		//选择督办人
		$("#Dmonitors").click(function(e){
			bTaskActions(3, "", e);
		});
		//辑辑域
		$("#editFields").click(function(e){
			bTaskActions(6, "", e);
		});
		//只读域
		$("#readFields").click(function(e){
			bTaskActions(7, "", e);
		});
		//隐藏域
		$("#hideFields").click(function(e){
			bTaskActions(8, "", e);
		});
		//必填域
		$("#notNullFields").click(function(e){
			bTaskActions(9, "", e);
		});
		//增加 办理权限
		$("#rightAdd").click(function(e){bTaskActions(11, "", e);});
		//删除办理权限
		$("#rightDelete").click(function(e){bTaskActions(12, "", e);});
		//清空办理权限
		$("#rightClear").click(function(e){bTaskActions(13, "", e);});
		//增加已办办理权限
		$("#doneRightAdd").click(function(e){bTaskActions(111, "", e);});
		//删除已办办理权限
		$("#doneRightDelete").click(function(e){bTaskActions(112, "", e);});
		//清空已办办理权限
		$("#doneRightClear").click(function(e){bTaskActions(113, "", e);});
		//增加督办办理权限
		$("#monitorRightAdd").click(function(e){bTaskActions(211, "", e);});
		//删除督办办理权限
		$("#monitorRightDelete").click(function(e){bTaskActions(212, "", e);});
		//清空督办办理权限
		$("#monitorRightClear").click(function(e){bTaskActions(213, "", e);});
		//增加监控办理权限
		$("#adminRightAdd").click(function(e){bTaskActions(311, "", e);});
		//删除监控办理权限
		$("#adminRightDelete").click(function(e){bTaskActions(312, "", e);});
		//清空监控办理权限
		$("#adminRightClear").click(function(e){bTaskActions(313, "", e);});
		//增加操作定义
		$("#buttonAdd").click(function(e){
			bTaskActions(14, "", e);
		});
		//编辑操作定义
		$("#buttonEdit").click(function(e){
			bTaskActions(15, "", e);
		});
		//删除操作定义
		$("#buttonDelete").click(function(e){
			bTaskActions(16, "", e);
		});
		//增加意见立场
		$("#optionAdd").click(function(e){
			bTaskActions(17, "", e);
		});
		//删除意见立场
		$("#optionDelete").click(function(e){
			bTaskActions(18, "", e);
		});
		//默认意见立场
		$("#optionDefault").click(function(e){
			var existValue = "同意|1;不同意|0;已阅|2";
			bTaskActions(19, existValue, e);
		});
		//增加信息记录 
		$("#remarkAdd").click(function(e){
			bTaskActions(21, "", e);
		});
		//增加信息记录 
		$("#remarkEdit").click(function(e){
			bTaskActions(22, "", e);
		});
		//删除信息记录 
		$("#remarkDelete").click(function(e){
			bTaskActions(23, "", e);
		});
		
		$("#tabs > ul > li > a").click(function() {
			for(var i = 1; i <= 4; i++) {
				var currentTabId = $(this).attr("href");
				var checkTabId = "#tabs-" + i;
				if(currentTabId == checkTabId) {
					$(checkTabId).show();
				} else {
					$(checkTabId).hide();
				}
			}
			// 重置字体
			$("#tabs > ul > li > a").css("font-weight", "normal");
			// 设置选中粗休
			$(this).css("font-weight", "bold");
		});
		$("#tabs > ul > li > a").first().trigger("click");
	});
</script>
</head>

<body style="overflow:scroll;">
<form>
<table cellpadding="0" cellspacing="0" border="0" style="width:100%;">
	<tr style="height:30">
		<td width="50" align=right>名称：</td>
		<td width="205"><input name="name" value="" class="Border_Inset" style="width:100%"/></td>
		<td width="50" align=right Version="Simple">ID：</td>
		<td Version="Simple" width="100"><input name="id" value="" class="Border_Inset" style="width:100%"></td>
		<td align="right" width="180">
			<input type="button" id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
			<input type="button" id="cancel" class="BORDER_BUTTON" value="  取消  "/>
			</td>
		<td width="15">&nbsp;</td>
	</tr>
</table>

<div id="ID_PageLabel" class="PageLabel" style="padding:10;width:570;height:315px;">

<div id=ID_PageContent style="display:none;" Label="人员设置" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">人员设置</legend>
	<table border="0" cellpadding="1" cellspacing="2" width="100%">
		<tr>
			<td style="padding-left: 15px;"  width=20%><label for="isSetUser">办理人</label></td>
			<td>
				<select id="isSetUser" name="isSetUser" onchange="ID_SetUser_0.style.display=(this.value=='1')?'':'none';" style="width:100%">
					<option value="1">现在确定
					<option value="2" selected>由前一环节办理人确定
				</select>
			</td>
		</tr>
		<tr id="ID_SetUser_0" style="display:none">
			<td style="padding-left: 15px;"  width="20%"><label for="Dusers">选择承办人</label></td>
			<td>
				<textarea id="Dusers" name="Dusers" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:20"></textarea>
				<input name="Duser1" value="" type="hidden"/>
				<input name="user1" value="" type="hidden"/>
				<input name="user2" value="" type="hidden"/>
				<input name="user4" value="" type="hidden"/>
				<input name="user8" value="" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;"  width=20%><label for="isSetCopyUser">抄送</label></td>
			<td>
				<select id="isSetCopyUser" name="isSetCopyUser" onchange="ID_SetUser_1.style.display=(this.value=='1')?'':'none';" style="width:100%">
					<option value="0" selected>不设置抄送人
					<option value="1">现在确定
					<option value="2">由前一环节办理人确定
				</select>
			</td>
		</tr>
		<tr id="ID_SetUser_1" style="display:none">
			<td style="padding-left: 15px;"  width="20%"><label for="DcopyUsers">选择抄送人</label></td>
			<td>
				<textarea id="DcopyUsers" name="DcopyUsers" onfocus="this.blur()" class="Border_Inset" style="width:100%;height:20"></textarea>
				<input name="DcopyUser1" value="" type="hidden"/>
				<input name="copyUser1" value="" type="hidden"/>
				<input name="copyUser2" value="" type="hidden"/>
				<input name="copyUser4" value="" type="hidden"/>
				<input name="copyUser8" value="" type="hidden"/>
			</td>
		</tr>
		<tr Version="Simple">
			<td>&nbsp;</td>
			<td>
				<table border="0" cellpadding='0' cellspacing='0' width="100%">
					<tr title="当本环节办理人为空时，可以进一步确认环节走向">
						<td width=45%>
							<input type="checkbox" id="isSetUserEmpty" name="isSetUserEmpty" value="1" class="AutoSize" onclick="if (this.checked){ID_SetUserEmpty1.style.display='';ID_SetUserEmpty2.style.display='';}else{ID_SetUserEmpty1.style.display='none';ID_SetUserEmpty2.style.display='none';}"/><label for="isSetUserEmpty">办理人为空自动进入下一个环节</label>
						</td>
						<td id="ID_SetUserEmpty1" style="display:none">
							<input name="DemptyToTask" value="" class="Border_Inset" style="width:80%" onfocus="this.blur()" emptymsg="请指定办理人为空自动进入的环节！" multiplemsg="不支持多个环节，请选择一个环节！"/>
							<input type="button" id="alltask" value="选择" class="HotSpot" title="选择转办环节"/>
							<input name="emptyToTask" value="" type="hidden"/>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr id="ID_SetUserEmpty2" style="display:none">
						<td colspan=3>
							<input type="checkbox" id="emptyNoteDone" name="emptyNoteDone" value="1" class="AutoSize"/><label for="emptyNoteDone">办理人为空转办时消息通知已办人员</label>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;"  title="当本环节同时有多个办理人时，可以进一步确定办理人">多办理人时</td>
			<td>
				<input type="checkbox" id="isSelectAgain" name="isSelectAgain" value="1" class="AutoSize" onclick="if(this.checked){ID_OnlyOne.style.display='';}else{goForm.isOnlyOne.checked=false;ID_OnlyOne.style.display='none';}"/><label for="isSelectAgain">选择具体办理人</label>
				<span id="ID_OnlyOne" style="display:none"><input type="checkbox" id="isOnlyOne" name="isOnlyOne" value="1" class="AutoSize"><label for="isOnlyOne">只能选择一个人办理</label></span><br>
				<span Version="Simple"><input type="checkbox" id="isAnyone" name="isAnyone" value="1" class="AutoSize" onclick="if(this.checked)goForm.isByOrder.checked=false;"/><label for="isAnyone">只需要其中一个人办理</label></span>
				<span Version="Simple"><input type="checkbox" id="isByOrder" name="isByOrder" value="1" class="AutoSize" onclick="if(this.checked)goForm.isAnyone.checked=false;"/><label for="isByOrder">按人员顺序依次办理</label></span>
			</td>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;"  ><label for="sameUserSubmit">与前环节相同</label></td>
			<td> 
				<select id="sameUserSubmit" name="sameUserSubmit" style="width:100%">
				<option value="2">不提醒环节变化，等待办理人提交
				<option value="0" selected>自动提交，让办理人确认是否继承上一环节意见
				<option value="1">自动提交，且自动继承意见
				</select>
			</td>
		</tr>
		</tr>
		<tr Version="Simple">
			<td style="padding-left: 15px;" ><label for="isSetMonitor">督办人</label></td>
			<td>
				<select id="isSetMonitor" name="isSetMonitor" onchange="if (this.value!='0'){ID_InheritMonitors.style.display='';}else{ ID_InheritMonitors.style.display='none';} if (this.value=='1'){ID_SetMonitor.style.display='';}else{ ID_SetMonitor.style.display='none';}" style="width:100%">
				<option value="0" selected>不设置督办人
				<option value="1">现在确定
				<option value="2">由前一环节办理人确定
				</select>
			</td>
		</tr>
		<tr id="ID_SetMonitor" style="display:none">
			<td style="padding-left: 15px;" ><label for="Dmonitors">选择督办人</label></td>
			<td>
				<textarea id="Dmonitors" name="Dmonitors" onfocus="this.blur()" class="Border_Inset" style="width:92%;height:20"></textarea>
<!-- 				<input type="button" id="monitor" value="选择" class="HotSpot" title="选择督办人"/> -->
				<input name="Dmonitor1" value="" type="hidden"/>
				<input name="monitor1" value="" type="hidden"/>
				<input name="monitor2" value="" type="hidden"/>
				<input name="monitor4" value="" type="hidden"/>
				<input name="monitor8" value="" type="hidden"/>
			</td>
		</tr>
		<tr id="ID_InheritMonitors">
			<td>&nbsp;</td>
			<td><input type="checkbox" id="isInheritMonitor" name="isInheritMonitor" value="1" class="AutoSize"/><label for="isInheritMonitor">同时继承已存在的督办人</label></td>
		</tr>
	</table>
	</fieldset>
</div>
<div id=ID_PageContent style="display:none;" Label="权限设置" LabelWidth="70">
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" style="font-weight: bold">待办权限</a></li>
		<li><a href="#tabs-2">已办权限</a></li>
		<li><a href="#tabs-3">督办权限</a></li>
		<li><a href="#tabs-4">监控权限</a></li>
	</ul>
	<div style="clear: both;"></div>
	<div id="tabs-1">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">待办权限</legend>
	<table border="0" cellpadding="1" cellspacing="3" width="100%">
	<tr>
		<td width=15% style="padding-left: 15px;" >办理权限</td>
		<td colspan="3">
		<table  border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr>
				<td><select name="DTaskRight" Multiple Size=5 style="width:98%" title="办理权限"></select>&nbsp;</td>
				<td align="center" width="20%">
					<input type="button" id="rightAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="rightDelete" value=" 删除 " class="BORDER_BUTTON"/>
					<input type="button" id="rightClear" value=" 清空 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr id="ID_UntreadType" Version="Simple" style="display:none">
		<td style="padding-left: 15px;" >退回办理</td>
		<td colspan="3">
			<select name="untreadType" onchange="if (this.value=='3') ID_Untread.style.display='';else ID_Untread.style.display='none';" style="width:100%">
			<option value="1">直接退回前一办理环节
			<option value="2" selected>退回时从所有已办理环节中选择
			<option value="3">现在选择要退回的可能环节
			</select>
		</td>
	</tr>
	<tr id="ID_Untread" Version="Simple" style="display:none">
		<td style="padding-left: 15px;" ><input type="button" onclick='bTaskActions(4);' value="退回环节" class="HotSpot" title="选择退回环节"></td>
		<td colspan="3">
			<textarea name="DuntreadTasks" class="Border_Inset" onkeypress="event.cancelBubble=true;" style="width:100%;height:32"></textarea>
			<input name="untreadTasks" value="" type="hidden"/>
		</td>
	</tr>
	<tr Version="Simple">
		<td width=15% style="padding-left: 15px;" >操作定义</td>
		<td width=40%>
		<table border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr><td colspan="2" style="line-height:200%;vertical-algin:bottom">默认名称->新的名称(目标环节)</td></tr>
			<tr>
				<td><select name="DButton" Size=5 style="width:98%" onDblClick="bTaskActions(15);"></select></td>
				<td align=center width=20% vAlign=middle>
					<input type="button" id="buttonAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="buttonEdit" value=" 编辑 " class="BORDER_BUTTON"/>
					<input type="button" id="buttonDelete" value=" 删除 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
		<td width=15% align=center>意见立场</td>
		<td width=30%>
		<table border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr><td colspan="2" style="line-height:200%;vertical-algin:bottom">名称|值</td></tr>
			<tr>
				<td><select name="DOption" Size=5 style="width:98%"></select></td>
				<td align=center width=20% vAlign=middle>
					<input type="button" id="optionAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="optionDelete" value=" 删除 " class="BORDER_BUTTON"/>
					<input type="button" id="optionDefault" value=" 默认 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</fieldset>
	</div>
	<div id="tabs-2">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">已办权限</legend>
	<table border="0" cellpadding="1" cellspacing="3" width="100%">
	<tr>
		<td width=15% style="padding-left: 15px;" >办理权限</td>
		<td colspan="3">
		<table  border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr>
				<td><select name="DTaskDoneRight" Multiple Size=5 style="width:98%" title="办理权限"></select>&nbsp;</td>
				<td align="center" width="20%">
					<input type="button" id="doneRightAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="doneRightDelete" value=" 删除 " class="BORDER_BUTTON"/>
					<input type="button" id="doneRightClear" value=" 清空 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</fieldset>
	</div>
	<div id="tabs-3">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">督办权限</legend>
	<table border="0" cellpadding="1" cellspacing="3" width="100%">
	<tr>
		<td width=15% style="padding-left: 15px;" >办理权限</td>
		<td colspan="3">
		<table  border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr>
				<td><select name="DTaskMonitorRight" Multiple Size=5 style="width:98%" title="办理权限"></select>&nbsp;</td>
				<td align="center" width="20%">
					<input type="button" id="monitorRightAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="monitorRightDelete" value=" 删除 " class="BORDER_BUTTON"/>
					<input type="button" id="monitorRightClear" value=" 清空 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</fieldset>
	</div>
	<div id="tabs-4">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">监控权限</legend>
	<table border="0" cellpadding="1" cellspacing="3" width="100%">
	<tr>
		<td width=15% style="padding-left: 15px;" >办理权限</td>
		<td colspan="3">
		<table  border="0" cellpadding='0' cellspacing='0' width="100%">
			<tr>
				<td><select name="DTaskAdminRight" Multiple Size=5 style="width:98%" title="办理权限"></select>&nbsp;</td>
				<td align="center" width="20%">
					<input type="button" id="adminRightAdd" value=" 增加 " class="BORDER_BUTTON"/>
					<input type="button" id="adminRightDelete" value=" 删除 " class="BORDER_BUTTON"/>
					<input type="button" id="adminRightClear" value=" 清空 " class="BORDER_BUTTON"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>
	</fieldset>
	</div>
</div>
</div>
<div id=ID_PageContent style="display:none;" Label="表单控制" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">表单及域控制</legend>
	<table  border="0" cellpadding='0' cellspacing='1' width="100%">
		<tr Version="Simple">
			<td style="padding-left: 15px;"  width=15%>使用表单</td>
			<td><select id="DForm" name="DForm" style="width:100%"><option value="0">请选择</select></td>
		</tr>
		<tr>
			<td style="padding-left: 15px;"  width=15%><label for="editFieldLabels">编辑域</label></td>
			<td><textarea id="editFieldLabels" name="editFieldLabels" class="Border_Inset" onkeypress="event.cancelBubble=true;" style="width:100%;height:40"></textarea>
				<input id="editFields" name="editFields" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" ><label for="readFieldLabels">只读域</label></td>
			<td><textarea id="readFieldLabels" name="readFieldLabels" class="Border_Inset" onkeypress="event.cancelBubble=true;" style="width:100%;height:40"></textarea>
				<input id="readFields" name="readFields" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" ><label for="hideFieldLabels">隐藏域</label></td>
			<td><textarea id="hideFieldLabels" name="hideFieldLabels" class="Border_Inset" onkeypress="event.cancelBubble=true;" style="width:100%;height:40"></textarea>
				<input id="hideFields" name="hideFields" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" ><label for="notNullFieldLabels">必填域</label></td>
			<td><textarea id="notNullFieldLabels" name="notNullFieldLabels" class="Border_Inset" onkeypress="event.cancelBubble=true;" style="width:100%;height:40"></textarea>
				<input id="notNullFields" name="notNullFields" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 15px;" >信息记录</td>
			<td>
			<table  border="0" cellpadding='0' cellspacing='0' width="95%">
				<tr>
					<td><select name="DRemark" Size=4 style="width:98%" onDblClick="bTaskActions(22);"></select>&nbsp;</td>
					<td align=center width=15%>
						<input type="button" id="remarkAdd" value=" 增加 " class="BORDER_BUTTON"/>
						<input type="button" id="remarkEdit" value=" 编辑 " class="BORDER_BUTTON"/>
						<input type="button" id="remarkDelete" value=" 删除 " class="BORDER_BUTTON"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
</div>
<div id=ID_PageContent style="display:none;" Label="高级设置" Version="Simple" LabelWidth="70">
	<fieldset style="width:100%;height:100%">
	<legend style="color:black;">高级选项</legend>
	<table border="0" cellpadding="1" cellspacing="3" width="100%">
		<tr>
			<td style="padding-left: 20px;"  width=20%>环节编号</td>
			<td><input name="sn" value="" class="Border_Inset" style="width:100%"/></td>
		</tr>
		<tr>
			<td style="padding-left: 20px;"  width=20%>生成流水号</td>
			<td>
				<input id="snName" name="snName" value="" class="Border_Inset" style="width:100%"/>
				<input id="serialNo" name="serialNo" value="" type="hidden"/>
			</td>
		</tr>
		<tr>
			<td style="padding-left: 20px;" ><label for="printTemplate">打印模板</label></td>
			<td><textarea id="printTemplate" name="printTemplate" class="Border_Inset" style="width:100%;height:32"></textarea>
				<input id="printTemplateId" name="printTemplateId" type="hidden"/>
			</td>
		</tr>
	</table>
	</fieldset>
</div>
</div>
</form>
<script type="text/javascript">
$().ready(function(){
});
</script>
</body>
</html>