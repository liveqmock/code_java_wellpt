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
<script src="${ctx}/pt/workflow/js/property.js"></script>
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
<!--
	window.dialogArguments = $.dialog.data("buttonLaArg");
	var gsValue = window.dialogArguments["Value"];
	var laButton=window.dialogArguments["Button"];
	var laTask=window.dialogArguments["Task"];
	var opener = window.dialogArguments["opener"];
	InitProperty("操作定义");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		for (var i=0;i<laButton.length;i++){
			var laValue = laButton[i].split("|");
			bAddEntry(goForm.DButton,laValue[0],laValue[1],true);
		}
		for (var i=0;i<laTask.length;i++){
			var laValue = laTask[i].split("|");
			bAddEntry(goForm.DTask,laValue[0],laValue[1],true);
		}
		if (gsValue!=""){
			var laValue = gsValue.split("|");
			opener.bSetFormFieldValue(goForm,"DButton",laValue[1]);
			goForm.newName.value = laValue[2];
			opener.bSetFormFieldValue(goForm,"DTask",laValue[3]);
			goForm.DUnits.value = laValue[4];
			goForm.Units.value = laValue[5];
			goForm.Dusers.value = laValue[6];
			var userIdArray = laValue[7].split(",");
			if(userIdArray.length == 5){
				goForm.Duser1.value = userIdArray[0];
				goForm.user1.value = userIdArray[1];
				goForm.user2.value = userIdArray[2];
				goForm.user4.value = userIdArray[3];
				goForm.user8.value = userIdArray[4];
			}
			goForm.DcopyUsers.value = laValue[8];
			var copyUserIdArray = laValue[9].split(",");
			if(userIdArray.length == 5){
				goForm.DcopyUser1.value = copyUserIdArray[0];
				goForm.copyUser1.value = copyUserIdArray[1];
				goForm.copyUser2.value = copyUserIdArray[2];
				goForm.copyUser4.value = copyUserIdArray[3];
				goForm.copyUser8.value = copyUserIdArray[4];
			}
		}
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("buttonReVal", null);
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e){
			var laTemp = new Array();
			laTemp[0] = sGetAllEntryValue(goForm.DButton,true);
			if (laTemp[0]==""){
				alert(goForm.DButton.emptyMsg);
			}else if (goForm.newName.value==""){
				alert(goForm.newName.emptyMsg);
			}else{
				var laTemp = new Array();
				laTemp[0] = "";
				if (gsValue!=""){
					var laValue = gsValue.split("|");
					laTemp[0] = laValue[0];
				}
				laTemp[1] = sGetAllEntryValue(goForm.DButton,true);
				laTemp[2] = goForm.newName.value;
				var lsValue = sGetAllEntryValue(goForm.DTask,true);
				laTemp[3] = lsValue=="0"?"":lsValue;
				laTemp[4] = goForm.DUnits.value;
				laTemp[5] = goForm.Units.value;
				laTemp[6] = goForm.Dusers.value;
				var userIds = $("input[name='Duser1']").val() + "," + $("input[name='user1']").val() + "," 
					+ $("input[name='user2']").val() + "," 
					+ $("input[name='user4']").val()  + "," + $("input[name='user8']").val();
				laTemp[7] = userIds;
				laTemp[8] = goForm.DcopyUsers.value;
				var copyUserIds = $("input[name='DcopyUser1']").val() + "," + $("input[name='copyUser1']").val() + "," 
					+ $("input[name='copyUser2']").val() + "," 
					+ $("input[name='copyUser4']").val()  + "," + $("input[name='copyUser8']").val();
				laTemp[9] = copyUserIds;
				//window.returnValue = laTemp.join("|");
				//window.close();
				$.dialog.data("buttonReVal", laTemp.join("|"));
				$.dialog.close();
			}
		});
		//取消事件
		$("#cancel").click(function(e){
			$.dialog.data("buttonReVal", null);
			//window.close();
			$.dialog.close();
		});
		
		// 选择所有者
		$("#DUnits").click(function(e){
			SetUnit();
		});
		// 选择参与人
		$("#Dusers").click(function(e){
			e.target.title = "选择参与人";
			SelectBtnUsers("", "user", e.target.title);
		});
		// 选择抄送人
		$("#DcopyUsers").click(function(e){
			e.target.title = "选择抄送人";
			SelectBtnUsers("", "copyUser", e.target.title);
		});
		
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
		}
		
		function SelectBtnUsers(psStyle, psTarget, psTitle, pbSingle) {
			var lsStyle = (psStyle != null && psStyle != "") ? psStyle
					: "unit/task/field/option";
			var laArg = new Array();
			laArg.Style = lsStyle;
			laArg.Title = psTitle;
			var laExistTask = null;
			if (lsStyle.indexOf("task") != -1) {
				var lsID = (goForm.id) ? goForm.id.value : "";
				laExistTask = opener.aGetTasks(
						lsStyle.indexOf("alltask") != -1 ? "TASK/SUBFLOW" : "", lsStyle
								.indexOf("nocurtask") != -1 ? lsID : null, lsStyle
								.indexOf("alltask") != -1 ? true : null);
				laArg.Tasks = laExistTask;
				laArg.MulTask = (pbSingle == true) ? false : true;
			}
			laArg.opener = opener;
			var liWidth = 600, liHeight = 80;
			if (lsStyle.indexOf("unit") != -1) {
				liHeight += 50;
				var laValue = new Array();
				var loField = goForm["D" + psTarget + "1"];// eval("goForm.D" +
				// psTarget + "1");
				laValue.push((loField != null) ? loField.value : "");
				loField = goForm[psTarget + "1"];// eval("goForm." + psTarget + "1");
				laValue.push((loField != null) ? loField.value : "");
				laArg.unit = laValue;
			}
			if (lsStyle.indexOf("field") != -1) {
				liHeight += 40;
				var loField = goForm[psTarget + "2"];// eval("goForm." + psTarget +
				// "2");
				laArg.field = (loField != null) ? loField.value : "";
			}
			if (lsStyle.indexOf("task") != -1) {
				liHeight += 80;
				var loField = goForm[psTarget
						+ (lsStyle.indexOf("unit") != -1 ? "4" : "")];// eval("goForm."
				// + psTarget + (lsStyle.indexOf("unit") != -1 ? "4" : ""));
				laArg.task = (loField != null) ? loField.value : "";
			}
			if (lsStyle.indexOf("option") != -1) {
				liHeight += 200;
				var loField = goForm[psTarget + "8"];// eval("goForm." + psTarget +
				// "8");
				laArg.option = (loField != null) ? loField.value : "";
			}
			if (liHeight < 200) {
				liHeight += 100;
			}
			// var laValue = opener.vOpenModal_("user/select.htm", laArg, liWidth,
			// liHeight);

			var reValCallback = function() {
				var laValue = $.dialog.data("laValue");
				if (laValue == null) {
					return;
				}
				var displayValue = "";
				if(laValue.unit != null && laValue.unit.length == 2){
					goForm['D' + psTarget + '1'].value = laValue.unit[0];
					goForm[psTarget + '1'].value = laValue.unit[1];
					displayValue = laValue.unit[0];
				} else {
					goForm['D' + psTarget + '1'].value = "";
					goForm[psTarget + '1'].value = "";
				}
				if(laValue.field != null && laValue.field != ""){
					goForm[psTarget + '2'].value = laValue.field;
					displayValue += (displayValue == "" ? "" : ";") + laValue.field;
				} else {
					goForm[psTarget + '2'].value = "";
				}
				if(laValue.task != null && laValue.task != ""){
					goForm[psTarget + '4'].value = laValue.task;
					displayValue += (displayValue == "" ? "" : ";") + laValue.task;
				} else {
					goForm[psTarget + '4'].value = "";
				}
				if(laValue.option != null && laValue.option != ""){
					goForm[psTarget + '8'].value = laValue.option;
					displayValue += (displayValue == "" ? "" : ";") + laValue.option;
				} else {
					goForm[psTarget + '8'].value = "";
				}
				goForm['D' + psTarget + 's'].value = displayValue;
				
				$.dialog.data("laValue", null);
			};
			// dialog.top
			openDialog("user/select.htm", psTitle, liWidth, liHeight, "laArg", laArg,
					reValCallback);
		}
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id=ID_PROMPT_INFO style="height:25;vertical-align:bottom;display:none">操作定义：</td></tr>
  <tr>
	<td style="height:100%" valign="top">
		<table border="0" cellpadding="1" cellspacing="3" width="100%">
			<tr>
				<td width=15% align="center">默认名称</td>
				<td><select name="DButton" style="width:100%" emptyMsg="请选择按钮！"></select></td>
			</tr>
			<tr>
				<td align="center">新的名称</td>
				<td><input name="newName" value="" class="Border_Inset" style="width:100%" emptyMsg="请输入新的名称。"/></td>
			</tr>
			<tr>
				<td align="center">目标环节</td>
				<td><select name="DTask" style="width:100%"><option value="0">请选择</select></td>
			</tr>
			<tr>
				<td align="center">使用人</td>
				<td><textarea id="DUnits" name="DUnits" value="" class="Border_Inset" style="width:100%" emptyMsg="请选择使用人"></textarea>
					<input id="Units" name="Units" value="" type="hidden"/>
				</td>
			</tr>
			<tr>
				<td align="center">参与人</td>
				<td><textarea id="Dusers" name="Dusers" value="" class="Border_Inset" style="width:100%" emptyMsg="请选择使用人"></textarea>
				<input name="Duser1" value="" type="hidden"/>
				<input name="user1" value="" type="hidden"/>
				<input name="user2" value="" type="hidden"/>
				<input name="user4" value="" type="hidden"/>
				<input name="user8" value="" type="hidden"/></td>
			</tr>
			<tr>
				<td align="center">抄送人</td>
				<td><textarea id="DcopyUsers" name="DcopyUsers" value="" class="Border_Inset" style="width:100%" emptyMsg="请选择使用人"></textarea>
				<input name="DcopyUser1" value="" type="hidden"/>
				<input name="copyUser1" value="" type="hidden"/>
				<input name="copyUser2" value="" type="hidden"/>
				<input name="copyUser4" value="" type="hidden"/>
				<input name="copyUser8" value="" type="hidden"/></td>
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
		     	<input type=button id="ok" value="  确定  " class="BORDER_BUTTON">&nbsp;&nbsp;
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