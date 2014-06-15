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
	opener = window.dialogArguments["opener"];
	InitProperty((window.dialogArguments["Title"]!=null && window.dialogArguments["Title"]!="")?window.dialogArguments["Title"]:"选择人员");
function bEmptyValue(){
	goForm.DUnits.value="";
	goForm.Units.value="";
	goForm.Fields.value="";
	if (goForm.Tasks){
		if (goForm.Tasks.length!=null && goForm.Tasks.length>0){
			for(var i=0;i<goForm.Tasks.length;i++) goForm.Tasks[i].checked=false;
		}else{
			goForm.Tasks.checked=false;
		}
	}
	if (goForm.Directions){
		if (goForm.Directions.length!=null && goForm.Directions.length>0){
			for(var i=0;i<goForm.Directions.length;i++) goForm.Directions[i].checked=false;
		}else{
			goForm.Directions.checked=false;
		}
	}
	for(var i=0;i<goForm.UserOptions.length;i++) goForm.UserOptions[i].checked=false;
}
function SetUnit(){
	var url = ctx + "/resources/pt/js/org/unit/flow_unit.htm";
	var laArg = new Array();
	laArg["Name"] = goForm.DUnits.value;
	laArg["ID"] = goForm.Units.value;
	laArg["Title"] = "人员选择";
	laArg["Multiple"] = true;
	laArg["SelectType"]=6;
	laArg["NameType"]="21";
	laArg["ShowType"]=true;
	laArg["Type"]="MyUnit";
	laArg["LoginStatus"]=false;
	laArg["Exclude"]=null;
	openDialog(url, "人员选择", 850, 500, "unitLaArg", laArg, function(){
		var laValue = $.dialog.data("unitLaValue");
		$.dialog.data("unitLaValue", null);
		if (laValue == null) {
			return;
		}
		goForm.DUnits.value = laValue["name"];
		goForm.Units.value = laValue["id"];
	});
	//var laValue = PromptUnit(null,goForm.DUnits.value,goForm.Units.value,"",true);
	//if(laValue!=null){
	//	goForm.DUnits.value = laValue["name"];
	//	goForm.Units.value = laValue["id"];
	//}
}
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		SelectUserLoadEvent();
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("laValue", null);
				$.dialog.close();
			}
		});
		
		//确定事件
		$("#ok").click(function(e){
			SelectUserOKEvent();
			$.dialog.close();
		});
		//清空事件
		$("#clear").click(function(e){
			bEmptyValue();
		});
		//取消事件
		$("#cancel").click(function(){
			$.dialog.data("laValue", null);
			//window.close();
			$.dialog.close();
		});
		//组织机构
		$("#DUnits").click(function(){
			SetUnit();
		});
		
		//表单域
		var setting = {
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getFormFields",
					"data" : bGetForm()
				}
			}
		};
		$("#FieldLabels").comboTree({
			labelField : "FieldLabels",
			valueField : "Fields",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[bGetForm()],
			treeSetting : setting,
			width: 488,
			height: 260
		});
	});
</script>
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
</head>

<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table  style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td align='center' style="height:100%" valign="top"> 
	<table style="width:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr id="ID_Unit" style="display:none">
	    <td width="15%" style="padding-left:10px;"><label for="DUnits">组织机构</label></td>
	    <td width="85%" ><textarea id="DUnits" rows=3 NAME="DUnits"></textarea><input id="Units" name="Units" value="" type="hidden"/></td>
	  </tr>
	  <tr id="ID_Field" style="display:none">
	    <td width="15%" style="padding-left:10px;"><label for="FieldLabels">文 档 域</label> </td>
	    <td width="85%" valign="middle"><textarea id="FieldLabels" rows=2 NAME="FieldLabels"></textarea><input id="Fields" name="Fields" type="hidden"></td>
	  </tr>
	  <tr id="ID_Task" style="display:none;">
	    <td width="15%" style="padding-left:10px;">办理环节</td>
	    <td width="85%" ><div id="ID_TaskList" align="left" style="background-color:white;border-style:solid;border-width:thin;border-color:white;padding:1,1,4,4;border-left-style:inset;border-top-style:inset;border-right-style:inset;border-bottom-style:inset;width:100%;height:65px;overflow:auto;font-size:9pt;"></div></td>
	  </tr>
	  <tr id="ID_Direction" style="display:none;">
	    <td width="15%" style="padding-left:10px;">工作流向</td>
	    <td width="85%" ><div id="ID_DirectionList" align="left" style="background-color:white;border-style:solid;border-width:thin;border-color:white;padding:1,1,4,4;border-left-style:inset;border-top-style:inset;border-right-style:inset;border-bottom-style:inset;width:100%;height:85px;overflow:auto;font-size:9pt;"></div></td>
	  </tr>
	  <tr id="ID_Option_0" style="display:none">
	    <td width="15%" valign=top style="padding-left:10px;padding-top:5px;">人员选项</td>
	    <td width="85%" >
		<table  style="height:100%;width:100%" border="0" cellspacing="0" cellpadding="0"><tr>
			<td width="50%">
			    <input type="checkbox" name="UserOptions" value="PriorUser" class="AutoSize" id="useroptions_1"/><label for="useroptions_1">前办理人</label><br>
			    <input type="checkbox" name="UserOptions" value="LeaderOfPriorUser" class="AutoSize" id="useroptions_2"/><label for="useroptions_2">前办理人的直接领导</label><br>
			    <input type="checkbox" name="UserOptions" value="AllLeaderOfPriorUser" class="AutoSize" id="useroptions_3"/><label for="useroptions_3">前办理人的所有领导</label><br>
			    <input type="checkbox" name="UserOptions" value="Creator" class="AutoSize" id="useroptions_4"/><label for="useroptions_4">申请人</label><br> 
			    <input type="checkbox" name="UserOptions" value="LeaderOfCreator" class="AutoSize" id="useroptions_5"/><label for="useroptions_5">申请人的直接领导</label><br>
			    <input type="checkbox" name="UserOptions" value="AllLeaderOfCreator" class="AutoSize" id="useroptions_6"/><label for="useroptions_6">申请人的所有领导</label><br>
			    <input type="checkbox" name="UserOptions" value="PriorTaskUser" class="AutoSize" id="useroptions_7"/><label for="useroptions_7">前一个环节办理人</label>
			</td>
			<td>
			    <input type="checkbox" name="UserOptions" value="DeptOfPriorUser" class="AutoSize" id="useroptions_8"/><label for="useroptions_8">前办理人的部门</label><br>
			    <input type="checkbox" name="UserOptions" value="ParentDeptOfPriorUser" class="AutoSize" id="useroptions_9"/><label for="useroptions_9">前办理人的上级部门</label><br>
			    <input type="checkbox" name="UserOptions" value="RootDeptOfPriorUser" class="AutoSize" id="useroptions_10"/><label for="useroptions_10">前办理人的根部门</label><br>
			    <input type="checkbox" name="UserOptions" value="DeptOfCreator" class="AutoSize" id="useroptions_11"/><label for="useroptions_11">申请人的部门</label><br>
			    <input type="checkbox" name="UserOptions" value="ParentDeptOfCreator" class="AutoSize" id="useroptions_12"/><label for="useroptions_12">申请人的上级部门</label><br>
			    <input type="checkbox" name="UserOptions" value="RootDeptOfCreator" class="AutoSize" id="useroptions_13"/><label for="useroptions_13">申请人的根部门</label><br>
			    <input type="checkbox" name="UserOptions" value="Corp" class="AutoSize" id="useroptions_14"/><label for="useroptions_14">全组织</label>
			</td>
			</tr>
		</table>
	    </td> 
	  </tr>
	  <tr id="ID_Option_1" style="display:none">
	    <td width="15%" valign="top" style="padding-left:10px;padding-top:5px;">人员过滤</td>
	    <td width="85%" >
		<table style="height:100%;width:100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td width="50%">
			    <input type="checkbox" name="UserOptions" value="SameDeptAsPrior" class="AutoSize" id="useroptions_15"/><label for="useroptions_15">限于前办理人的直系部门人员</label><br>
			    <input type="checkbox" name="UserOptions" value="SameRootDeptAsPrior" class="AutoSize" id="useroptions_16"/><label for="useroptions_16">限于前办理人的同一根部门人员</label><br>
			    <input type="checkbox" name="UserOptions" value="SameAllLeaderAsPrior" class="AutoSize" id="useroptions_17"/><label for="useroptions_17">限于前办理人的所有领导</label>
			</td>
			<td>
			    <input type="checkbox" name="UserOptions" value="SameDeptAsCreator" class="AutoSize" id="useroptions_18"/><label for="useroptions_18">限于申请人的直系部门人员</label><br>
			    <input type="checkbox" name="UserOptions" value="SameRootDeptAsCreator" class="AutoSize" id="useroptions_19"/><label for="useroptions_19">限于申请人的同一根部门人员</label><br>
			    <input type="checkbox" name="UserOptions" value="SameAllLeaderAsCreator" class="AutoSize" id="useroptions_20"/><label for="useroptions_20">限于申请人的所有领导</label>
			</td>
			</tr>
		</table>
	    </td> 
	  </tr>
	</table>
    </td>
  </tr>
  <tr>
    <td style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td></td>
			<td align="right" style="height:30">
			<input type="button" id="ok" value="  确定  " class="BORDER_BUTTON"/>
			<input type="button" id="clear" value="  清空  " class="BORDER_BUTTON"/>
			<input type="button" id="cancel" value="  取消  " class="BORDER_BUTTON"/>
			</td>
			<td width=15></td>
		</tr>
	</table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
