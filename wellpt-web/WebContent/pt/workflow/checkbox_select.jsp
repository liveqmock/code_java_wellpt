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
<link href="${ctx}/pt/workflow/css/Dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	<!--
	window.dialogArguments = $.dialog.data("checkboxSelectLaArg");
	for ( elem in window.dialogArguments ){
		switch( elem ){
		case "Title":
			var lsTitle = window.dialogArguments["Title"];
			break;
		case "PromptInfo":
			var lsPromptInfo = window.dialogArguments["PromptInfo"];
			break;
		case "ArgPara":
			var lsPara = window.dialogArguments["ArgPara"];
			break;
		case "Limit":
			var lsLimit = window.dialogArguments["Limit"];
		case "Empty":
			var lsEmpty = window.dialogArguments["Empty"];
			break;
		}
	}
	document.write("<title>" + lsTitle + "</title>");			
	// -->
</script>

<script type="text/javascript">
	<!--
	function selectAll(pbChecked){
		var loObjs = document.getElementsByName("SelectArray");
		for(var i=0;i<loObjs.length;i++){
			loObjs[i].checked = pbChecked;
		}
	}
	function OptOnClick(poObj){
		if(poObj.checked){
			var lbAll = true;
			var loObjs = document.getElementsByName("SelectArray");
			for(var i = 0;i<loObjs.length;i++){
				if(!loObjs[i].checked){
					lbAll = false;
					break;
				}
			}
			ID_CheckBox.checked = lbAll?true:false;
		}
		else{
			ID_CheckBox.checked = false;
		}
	}
	// -->
</script>

<script type="text/javascript">
	$(function(){
		//onload事件
		if (lsPromptInfo!=null && lsPromptInfo!=""){
			document.all.ID_PROMPT_INFO.innerHTML = "&nbsp;" + lsPromptInfo;
			document.all.ID_PROMPT_INFO.style.display="block";
		}
		if (lsEmpty!=null && lsEmpty=="1") document.all.NOSelect.style.display="";
		
		//确定事件
		$("#ok").click(function(e){
			var lsReturn="";
			var loObjs = document.getElementsByName("SelectArray");
			for (var i = 0; i < loObjs.length; i++) {
				if (loObjs[i].checked == false) continue;
				if (lsReturn=="")
					lsReturn = loObjs[i].value;
				else
					lsReturn += ";" + loObjs[i].value;
			}
			if (lsReturn=="" && lsLimit=="1"){
				alert("请至少选择一项！");
				return false;
			}
			//window.returnValue = lsReturn;
			//window.close();
			$.dialog.data("checkboxSelectReVal", lsReturn);
			$.dialog.close();
		});
		//不选事件
		$("#noselect").click(function(e){
			//var lsReturn="<NULL>";
			//window.returnValue = lsReturn;
			//window.close();
			$.dialog.data("checkboxSelectReVal", null);
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			$.dialog.data("checkboxSelectReVal", null);
			//window.close();
			$.dialog.close();
		});
	});
</script>
<style type="text/css">
	<!--
	.Cont_Bg{
		font-size: 9pt;
		text-align: left;
		padding: 1,1,4,4;
		border-width: thin;
		border-color: white;
		border-left-style: inset;
		border-top-style: inset;
		border-right-style: inset;
		border-bottom-style: inset;
		background-color: white;
		width: 100%;
		height: 100%;
		overflow: Auto;
	}
	// -->
</style>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
	<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
		<tr><td id="ID_PROMPT_INFO" style="vertical-align:bottom;padding-bottom:2px;height:25;display:none;">&nbsp;</td></tr>
		<tr>
			<td style="height:100%" id=ID_SELECTAREA align='center'>
				<div class="Cont_Bg">
					<script type="text/javascript">
					<!--
					if (lsPara!="") {
						var laVls = lsPara.split(";");
						document.write('<table style="table-Layout:fixed;width:100%;" border="0" cellspacing="0" cellpadding="0">');
						for (var i=0; i<laVls.length; i++) {
							var lsText = laVls[i], lsValue = laVls[i];
							if (lsText.indexOf("|")>0){
								lsText = laVls[i].substr(0,laVls[i].indexOf("|"));
								lsValue = laVls[i].substr(laVls[i].indexOf("|")+1);
							}
							document.write('<tr>');
							document.write('<td style="width:22px;">');
							document.write('<INPUT id="checkbox' + i + '" NAME="SelectArray" TYPE=checkbox class=CHECKBOX' 
								+ ' VALUE="' + lsValue + '"' 
								+ ' onDblClick="document.all.Ok.click();"' 
								+ ' onclick="OptOnClick(this);"/>');
							document.write('</td>');
							document.write('<td style="text-overflow:ellipsis;overflow:hidden;white-space:nowrap;">');
							document.write('<label for="checkbox'+i+'" onDblClick="document.all.Ok.click();">'+lsText+'</label>');
							document.write('</td>');
							document.write('</tr>');
						}
						document.write('</table>');
					}
					// -->
					</script>
				</div>
			</td>
		</tr>
		<tr>
			<td style="text-align:center;height:30">
				<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="5"></td>
						<td>
							<input id="ID_CheckBox" type="checkbox" class="CHECKBOX" onclick="if(this.checked)selectAll(true);else selectAll(false);" title="全部选择/全部不选"/>
							<a href="#" onclick="selectAll(true);ID_CheckBox.checked=true;return false;">全选</a>
							<span>/</span>
							<a href="#" onclick="selectAll(false);ID_CheckBox.checked=false;return false;">全不选</a>
						</td>
						<td style="text-align:right;height:30">
							<input type="button" id="noselect" value="  不选  " class="BORDER_BUTTON" style="display:none">
							<input type="button" id="ok" value="  确定  " class="BORDER_BUTTON">
							<input type="button" id="cancel" class="BORDER_BUTTON" ONCLICK="window.close();" value="  取消  ">
						</td>
						<td width="15"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>