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
<script src="${ctx}/pt/workflow/js/jquery.workflow.js"></script>
<script src="${ctx}/pt/workflow/js/property.js"></script>
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
	window.dialogArguments = $.dialog.data("newFlowLaArg");
	var opener = window.dialogArguments["opener"];
	InitProperty("信息记录定义");
// -->
</script>
<script type="text/javascript">
	$().ready(function(){
		var loNode = opener.goWorkFlow.flowXML.selectSingleNode("./property/formID");
		var formUuid = loNode != null ? loNode.text() : "";
		// 动态表单字段
		JDS.call({
			service: "flowSchemeService.getFormFields",
			data: ["-1", formUuid],
			async: false,
			success : function(result) {
				var option = "<option value=''></option>";
				$("#creatName").append(option);
				var data = result.data;
				$.each(data, function(index) {
					var id = this.id;
					var name = this.name;
					var option = "<option value='" + id + "'>" + name + "</option>";
					$("#creatName").append(option);
				});
			}
		});
		// 流程选择
		var setting = {
			view : {
				showIcon : false,
				showLine : false
			},
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getFlowTree",
					"data" : formUuid
				}
			},
			check : {
				enable: true,
				chkStyle: "radio"
			},
			callback : {
				onClick : function zTreeOnCheck(event, treeId, treeNode){
					doCheck(event, treeId, treeNode);
				},
				onCheck : function(event, treeId, treeNode){
					doCheck(event, treeId, treeNode);
				}
			}
		};
		function doCheck(event, treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.checkAllNodes(false);
			zTree.checkNode(treeNode, true);
			if(!treeNode.isParent){
				$("#name").val(treeNode.name);
				$("#value").val(treeNode.data);
				initToTask(treeNode.data, true);
			}else {
				$("#name").val("");
				$("#value").val("");
			}
		}
		// 初始化环节
		function initToTask(flowDefId, async){
			$("#toTaskName").html("");
			JDS.call({
				service : "flowSchemeService.getFlowTasks",
				data : [flowDefId],
				async : async,
				success : function(result){
					var data = result.data;
					var draftOption = "<option value='DRAFT'>草搞</option>";
					$("#toTaskName").append(draftOption);
					var autoSubmitOption = "<option value='AUTO_SUBMIT'>自动提交</option>";
					$("#toTaskName").append(autoSubmitOption);
					$.each(data, function(index) {
						var id = this.id;
						var name = this.name;
						var option = "<option value='" + id + "'>" + name + "</option>";
						$("#toTaskName").append(option);
					});
				}
			});
		}
		// 流程名称
		$("#name").comboTree({
			labelField : "name",
			valueField : "value",
			treeSetting : setting,
			width: 378,
			height: 250
		});

		//onload事件
		goForm = document.forms[0];
		//bInitRemarkValue();
		var gsValue = window.dialogArguments["value"];
		$("#name").val(gsValue.name);
		$("#value").val(gsValue.value);
		$("#conditionName").val(gsValue.conditionName);
		$("#conditionValue").val(gsValue.conditionValue);
		//$("#creatName").text();
		$("#creatName").val(gsValue.creatValue);
		$("#isMerge")[0].checked = (gsValue.isMerge == "1" ? true : false);
		$("#isWait")[0].checked = (gsValue.isWait == "1" ? true : false);
		$("#isShare")[0].checked = (gsValue.isShare == "1" ? true : false);
		$("#copyFields").val(gsValue.copyFields);
		$("#returnFields").val(gsValue.returnFields);
		if(gsValue.value != null && gsValue.value != ""){
			initToTask(gsValue.value, false);
			//$("#toTaskName").text();
			$("#toTaskName").val(gsValue.toTaskId);
		}
		
		//键盘事件
		$(document).keypress(function(e){
			var keyCode = e.which;
			if(keyCode == 13){
				$("#ok").click();
			}
			if(keyCode == 27){
				//window.close();
				$.dialog.data("newFlowReVal", null);
				$.dialog.close();
			}
		});
		
		//确定事件
		$("#ok").click(function(e){
			//window.returnValue = sRemarkBuildValue();
			//if (window.returnValue!=null) window.close();
			var newFlow = {};
			newFlow.name = $("#name").val();
			newFlow.value = $("#value").val();
			if(newFlow.value == null || newFlow.value == ""){
				alert("请选择流程!");
				return;
			}
			newFlow.conditionName = $("#conditionName").val();
			newFlow.conditionValue = newFlow.conditionName;
			newFlow.creatName = $("#creatName > option:selected").text();
			newFlow.creatValue = $("#creatName").val();
			newFlow.isMerge = $("#isMerge")[0].checked == true ? "1" : "0";
			newFlow.isWait = $("#isWait")[0].checked == true ? "1" : "0";
			newFlow.isShare = $("#isShare")[0].checked == true ? "1" : "0";
			newFlow.toTaskName = $("#toTaskName > option:selected").text();
			newFlow.toTaskId = $("#toTaskName").val();
			newFlow.copyFields = $("#copyFields").val();
			newFlow.returnFields = $("#returnFields").val();
			$.dialog.data("newFlowReVal", newFlow);
			$.dialog.close();
		});
		//取消事件
		$("#cancel").click(function(e){
			$.dialog.data("newFlowReVal", null);
			//window.close();
			$.dialog.close();
		});
		
		// 拷贝字段
		var copyFieldsSetting = {
			view : {
				showIcon : false,
				showLine : false
			},
			async : {
				otherParam : {
					"serviceName" : "flowSchemeService",
					"methodName" : "getFormFields",
					"data" : formUuid
				}
			}
		};
		$("#copyFieldNames").comboTree({
			labelField : "copyFieldNames",
			valueField : "copyFields",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[formUuid],
			treeSetting : copyFieldsSetting,
			width: 422,
			height: 260
		});
		$("#returnFieldNames").comboTree({
			labelField : "returnFieldNames",
			valueField : "returnFields",
			initService:"flowSchemeService.getFormFieldByFieldNames",
			initServiceParam:[formUuid],
			treeSetting : copyFieldsSetting,
			width: 422,
			height: 260
		});
		
		// 如果选择合并，显示是否等待
		if($("#isMerge")[0].checked) {
			$("#span_isWait").show();
		}else {
			$("#isWait")[0].checked = false;
			$("#span_isWait").hide();
		}
		$("#isMerge").change(function(e) {
			if($("#isMerge")[0].checked) {
				$("#span_isWait").show();
			}else {
				$("#span_isWait").hide();
			}
		});
	});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<form>
<table style="height:100%;width:100%" border="1" cellspacing="0" cellpadding="0">
  <tr><td id="ID_PROMPT_INFO" style="height:25;vertical-align:bottom;">流程设置：</td></tr>
  <tr>
    <td style="height:100%" valign="top">
 	<table border="0" cellpadding="1" cellspacing="3" width="98%">
		<tr>
			<td align="right" width=20%>流程名称</td>
			<td><input id="name" name="name" value="" class="Border_Inset" style="width:100%"/>
			<input id="value" name="value" type="hidden" value=""/></td>
		</tr>
		<tr>
			<td align="right">触发条件</td>
			<td><input id="conditionName" name="conditionName" value="" class="Border_Inset" style="width:100%"/>
			<input id="conditionValue" name="conditionValue" type="hidden" value=""/></td>
		</tr>
		<tr>
			<td align="right">创建实例的字段</td>
			<td><select id="creatName" name="creatName" style="width:100%"></select></td>
		</tr>
		<tr>
			<td align="right"></td>
			<td><span id="span_isMerge"><input id="isMerge" name="isMerge" type="checkbox" value="0"/><label for="isMerge">合并流程</label></span>
			<span id="span_isWait"><input id="isWait" name="isWait" type="checkbox" value="0"/><label for="isWait">合并等待</label></span>
			<span><input id="isShare" name="isShare" type="checkbox" value="0"/><label for="isShare">共享流程</label></span></td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td align="right">共享流程</td> -->
<!-- 			<td><input id="isShare" name="isShare" type="checkbox" value="0"/></td> -->
<!-- 		</tr> -->
		<tr>
			<td align="right">提交环节</td>
			<td><select id="toTaskName" name="toTaskName" style="width:100%"></select></td>
		</tr>
		<tr>
			<td align="right">拷贝信息</td>
			<td><textarea id="copyFieldNames" name="copyFieldNames" class="Border_Inset" style="width:100%;height:40"></textarea>
			<input id="copyFields" name="copyFields" type="hidden" /></td>
		</tr>
		<tr>
			<td align="right">返回信息</td>
			<td><textarea id="returnFieldNames" name="returnFieldNames" class="Border_Inset" style="width:100%;height:40"></textarea>
			<input id="returnFields" name="returnFields" type="hidden" /></td>
		</tr>
	</table>
    </td>
  </tr>
  <tr>
    <td align='center' style="height:30">
	<table style="width:100%;height:100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  	<td></td>
	  	<td align="right" style="height:30">
	     <input type="button" id="ok" value="  确定  " class="BORDER_BUTTON"/>&nbsp;&nbsp;
	     <input type="button" id="cancel" class="BORDER_BUTTON" value="  取消  "/>
		</td>
	  	<td width="15"></td>
	</tr></table>
    </td>
  </tr>
</table>
</form>
</body>
</html>