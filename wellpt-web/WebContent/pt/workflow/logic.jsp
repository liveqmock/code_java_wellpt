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
	window.dialogArguments = $.dialog.data("logicLaArg");
	var gsText = window.dialogArguments["text"];
	var gsValue = window.dialogArguments["value"];
	var gbIsLogic = window.dialogArguments["logic"];
	var gsOptNames = window.dialogArguments["optnames"];
	var opener = window.dialogArguments["opener"];
	InitProperty("判断条件");
// -->
</script>
<script type="text/javascript">
	$(function(){
		//onload事件
		goForm = document.forms[0];
		bInitLogicValue();
		
		var groups = goForm.Groups.value.split(",");
		if(groups.length == 5){
			goForm.DGroup1.value = groups[0];
			goForm.Group1.value = groups[1];
			goForm.Group2.value = groups[2];
			goForm.Group4.value = groups[3];
			goForm.Group8.value = groups[4];
		}
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("logicReVal", null);
				$.dialog.close();
			}
		});
		//确定事件
		$("#ok").click(function(e){
			//window.returnValue = aLogicBuildValue();
			//if (window.returnValue!=null) window.close();
			goForm.Groups.value = $("input[name='DGroup1']").val() + "," + $("input[name='Group1']").val() + "," 
					+ $("input[name='Group2']").val() + "," 
					+ $("input[name='Group4']").val()  + "," + $("input[name='Group8']").val();
			var returnValue = aLogicBuildValue();
			if(returnValue != null){
				$.dialog.data("logicReVal", returnValue);
				$.dialog.close();
			}
		});
		//取消事件
		$("#cancel").click(function(e){
			//window.close();
			$.dialog.data("logicReVal", null);
			$.dialog.close();
		});
		
		// 选择群组
		$("#DGroups").click(function(e){
			e.target.title = "选择群组";
			SelectGroupUsers("", "Group", e.target.title);
		});
		
		function SelectGroupUsers(psStyle, psTarget, psTitle, pbSingle) {
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
<table  style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id=ID_PROMPT_INFO style="height:25;vertical-align:bottom;">请定义比较条件项：</td></tr>
  <tr>
    <td style="height:100%" valign="top">
    	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="95%" align=center>
	<tr>
	<td>
		<INPUT id="LogicType_1" TYPE=radio NAME="LogicType" VALUE="1" CHECKED CLASS="AutoSize" onclick='bChangeLogicType(this);'><label for="LogicType_1">通过字段值比较设置条件</label>
		<span Version="Simple"><INPUT id="LogicType_2" TYPE=radio NAME="LogicType" VALUE="2" CLASS="AutoSize" onclick='bChangeLogicType(this);'><label for="LogicType_2">通过投票比例设置条件</label></span>
		<INPUT id="LogicType_3" TYPE=radio NAME="LogicType" VALUE="3" CLASS="AutoSize" onclick='bChangeLogicType(this);'><label for="LogicType_3">通过人员归属设置条件</label>
	</td>
	</tr>
	</table>
   	<TABLE id="ID_Logic" border=0 cellpadding='1' cellspacing='3' WIDTH="95%" align=center>
	<tr><td width="25%"><font>与前一个条件的逻辑关系</font></td>
	<td><SELECT NAME="AndOr" style="width:auto"><OPTION VALUE="&" SELECTED>并且<OPTION VALUE="|">或者</SELECT></td>
	</tr>
	</table>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="95%" align=center ID="ID_Field">
	<tr>
	<td  width=10%>&nbsp;</td>
	<td  width=25%></td>
	<td  width=10%>&nbsp;</td>
	<td  width=15%></td>
	<td  width=30%></td>
	<td  width=10%>&nbsp;</td>
	</tr>
	<tr>
	<td><SELECT NAME="LBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE="(">(</SELECT></td>
	<td>
		<select name="FieldName"><option value="-1">--选择字段--</option></select>
	</td>
	<td><label for="FieldName">浏览</label>
<!-- 	<INPUT TYPE=button onClick='bSetField("FieldName",false);' VALUE="浏览" CLASS="HotSpot"> -->
	</td>
	<td><SELECT NAME="Operator" STYLE="width:100%">
	<OPTION VALUE="&gt;">大于
	<OPTION VALUE="&gt;=">大于等于
	<OPTION VALUE="&lt;">小于
	<OPTION VALUE="&lt;=">小于等于
	<OPTION VALUE="==">等于
	<OPTION VALUE="!=">不等于
	<OPTION VALUE="like">包含
	<OPTION VALUE="notlike">不包含</SELECT>
	</td>
	<td><INPUT NAME="Value" VALUE="" CLASS="Border_Inset" STYLE="width:100%"></td>
	<td><SELECT NAME="RBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE=")">)</SELECT></td>
	</tr>
	</table>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="95%" align=center ID="ID_Vote" style="display:none" OptNamesIsEmpty="请在此流向的开始环节定义意见立场选项！">
	<tr>
	<td  width=10%>&nbsp;</td>
	<td  width=25%></td>
	<td  width=25%></td>
	<td  width=30%></td>
	<td  width=10%>&nbsp;</td>
	</tr>
	<tr>
	<td><SELECT NAME="vLBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE="(">(</SELECT></td>
	<td><SELECT NAME="VoteOption" STYLE="width:100%"></SELECT></td>
	<td><SELECT NAME="vOperator" STYLE="width:100%">
	<OPTION VALUE="&gt;" selected>大于
	<OPTION VALUE="&gt;=">大于等于
	<OPTION VALUE="&lt;">小于
	<OPTION VALUE="&lt;=">小于等于
	<OPTION VALUE="==">等于
	<OPTION VALUE="!=">不等于</SELECT>
	</td>
	<td><INPUT NAME="vValue" VALUE="" CLASS="Border_Inset" STYLE="width:90%" emptymsg="请输入所占百分比！" errormsg="不需要在所占百分比中输入百分号“％”！">%</td>
	<td><SELECT NAME="vRBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE=")">)</SELECT></td>
	</tr>
	</table>
	<TABLE  border=0 cellpadding='1' cellspacing='3' WIDTH="95%" align=center ID="ID_Group" style="display:none">
	<tr>
	<td width=10%>&nbsp;</td>
	<td width=15%>&nbsp;&nbsp;</td>
	<td>
<!-- 		<INPUT TYPE=radio NAME="GroupType" VALUE="1" CHECKED CLASS="AutoSize" onclick='ID_UserField.style.display="none";'>当前办理人 -->
<!-- 		<INPUT TYPE=radio NAME="GroupType" VALUE="0" CLASS="AutoSize" onclick='ID_UserField.style.display="";'>由域值指定 -->
	</td>
	<td width=10%>&nbsp;</td>
	</tr>
	<tr>
	<td><SELECT NAME="gLBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE="(">(</SELECT></td>
	<td><select name="GroupType"><option value="-1">选择成员</option><option value="1">当前办理人为所选人员之一</option><option value="0">表单字段所选人员为所选人员之一</option></select>
	<span id="ID_UserField" style="display:none"><label for="UserFieldLabel">&nbsp;</label>
		<select id="UserField" NAME="UserField" ><option value="-1">选择表单字段</option></select>
<!-- 			<INPUT TYPE=button onClick='bSetField("UserField",false);' VALUE="选择" CLASS="HotSpot"> -->
			</span>
	</td>
	<td><TEXTAREA id="DGroups" NAME="DGroups" onFocus="this.blur()" CLASS="Border_Inset" STYLE="width:100%;height:60" emptymsg="请指定群组！"></TEXTAREA>
		<input Name="Groups" value="" type="hidden"/>
		<input Name="DGroup1" value="" type="hidden"/>
		<input name="Group1" value="" type="hidden"/>
		<input name="Group2" value="" type="hidden"/>
		<input name="Group4" value="" type="hidden"/>
		<input name="Group8" value="" type="hidden"/></td>
	<td><SELECT NAME="gRBracket" STYLE="width:100%"><OPTION VALUE="" SELECTED><OPTION VALUE=")">)</SELECT></td>
	</tr>
	</table>
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
<script type="text/javascript">
$().ready(function(){
	$("select[name=GroupType]").bind("change", function(){
		var v = $(this).val();
		if(v === "0"){
			$("#ID_UserField").show();
		}else{
			$("#ID_UserField").hide();
		}
	});
	var loNode = opener.goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";
	
	JDS.call({
		service:"flowSchemeService.getFormFields",
		data: [-1, formUuid],
		success : function(result){
			var data = result.data;
			$.each(data, function(){
				var option = "<option value='" + this.data + "'>" + this.name + "</option>";
				$("select[name=FieldName]").append(option);
			});
		}
	});
	
	JDS.call({
		service:"flowSchemeService.getFormFields",
		data: [-1, formUuid],
		success : function(result){
			var data = result.data;
			$.each(data, function(){
				var option = "<option value='" + this.data + "'>" + this.name + "</option>";
				$("select[name=UserField]").append(option);
			});
		}
	});
});
</script>
</html>