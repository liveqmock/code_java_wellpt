function attachEvent() {
	for ( var i = 0; i < document.all.length; i++) {
		if (document.all[i].tagName == "TD" && document.all[i].className == "ToolBar") {
			gaTools[gaTools.length] = document.all[i];
			document.all[i].onmouseover = ToolMouseOver;
			document.all[i].onmouseout = ToolMouseOut;
			document.all[i].onmousedown = ToolMouseDown;
			document.all[i].onmouseup = ToolMouseUp;
		} else {
			if (document.all[i].tagName == "TD"
					&& (document.all[i].className == "Menu" || document.all[i].className == "Menu_Big")) {
				document.all[i].onmouseover = MenuMouseOver;
				document.all[i].onmouseout = MenuMouseOut;
				document.all[i].onmousedown = MenuMouseDown;
				document.all[i].onmouseup = MenuMouseUp;
			}
		}
	}
}
function ToolMouseOver() {
	if (this.className == "ToolBar") {
		this.className = "ToolOutset";
	}
}
function ToolMouseOut() {
	if (this.className != "ToolInset") {
		this.className = "ToolBar";
	}
}
function ToolMouseDown() {
	this.className = "ToolInset";
	if (goSelectedTool != null) {
		goSelectedTool.className = "ToolBar";
	}
	goSelectedTool = this;
	showToolMsg();
}
function ToolMouseUp() {
}
var goSelectedTool = null;
var gaTools = new Array();
function MenuMouseOver() {
	this.className = "MenuOutset" + ((this.className.indexOf("_Big") != -1) ? "_Big" : "");
}
function MenuMouseOut() {
	this.className = "Menu" + ((this.className.indexOf("_Big") != -1) ? "_Big" : "");
}
function MenuMouseDown() {
	this.className = "MenuInset" + ((this.className.indexOf("_Big") != -1) ? "_Big" : "");
}
function MenuMouseUp() {
	this.className = "Menu" + ((this.className.indexOf("_Big") != -1) ? "_Big" : "");
}
function HideDoing_() {
	if (typeof ID_PromptDoing == "object") {
		ID_PromptDoing.style.display = "none";
		ID_PromptDoing.innerHTML = "";
	}
}
var goWorkFlow = null;
var saveSetting = true;
// 保存当前设置
function saveCurrentSetting() {
	if (saveSetting) {
		$("#btn_save").trigger("click");
	} else {
		saveSetting = true;
	}
}
// 设置流程属性
function setFlowProperty(workFlow, window) {
	goWorkFlow = workFlow;
	var flowProperty = goWorkFlow.flowXML.selectSingleNode("property");
	// $("#eProperty").load(ctx + "/workflow/flow2.htm");
	// if ($("#eProperty").html() == "") {
	// 保存当前设置
	saveCurrentSetting();
	$.get(ctx + "/workflow/flow2.htm", function(data) {
		$("#eProperty").html(data);
		FlowLoadEvent(flowProperty);
		FlowInitEvent(flowProperty);
		// $("#div_property").append(
		// "<div id='div_flow'>" + $("#div_flow").html() + "</div>");
		// $("#eProperty").html("");
	});
	// }
	// $("#div_flow").show();
	// $("#div_task").hide();
	// $("#div_condition").hide();
	// $("#div_sub_flow").hide();
	// $("#div_direction").hide();
}
function setTaskProperty(loTask, window, workFlow) {
	goWorkFlow = workFlow;
	var lsURL = (loTask.Type == "SUBFLOW") ? "sub/flow" : (loTask.Type == "CONDITION") ? "condition.htm"
			: "task.htm";
	var taskCallback = function() {
		if (loTask.xmlObject != null) {
			bSetObjectText(loTask);
		}
	};
	// 保存当前设置
	saveCurrentSetting();
	if ((loTask.Type == "SUBFLOW")) {
		// if ($("#div_sub_flow").html() == "") {
		$.get(ctx + "/workflow/sub/flow2.htm", function(data) {
			$("#eProperty").html(data);
			// subFlowProperty = loTask.xmlObject
			SubFlowLoadEvent(loTask, loTask.xmlObject);
			SubFlowLoadInitEvent(loTask, loTask.xmlObject);
		});
		// } else {
		// SubFlowLoadEvent(loTask, loTask.xmlObject);
		// }
		// $("#div_flow").hide();
		// $("#div_task").hide();
		// $("#div_condition").hide();
		// $("#div_sub_flow").show();
		// $("#div_direction").hide();
	} else if (loTask.Type == "CONDITION") {
		// if ($("#div_condition").html() == "") {
		$.get(ctx + "/workflow/condition2.htm", function(data) {
			$("#eProperty").html(data);
			// taskProperty = loTask.xmlObject
			ConditionLoadEvent(loTask, loTask.xmlObject);
		});
		// } else {
		// // taskProperty = loTask.xmlObject
		// ConditionLoadEvent(loTask, loTask.xmlObject);
		// }
		// $("#div_flow").hide();
		// $("#div_task").hide();
		// $("#div_condition").show();
		// $("#div_sub_flow").hide();
		// $("#div_direction").hide();
	} else {
		// if ($("#div_task").html() == "") {
		$.get(ctx + "/workflow/task2.htm", function(data) {
			$("#eProperty").html(data);
			// onload事件
			// taskProperty = loTask.xmlObject
			TaskLoadEvent(loTask, loTask.xmlObject);
			TaskInitEvent(loTask, loTask.xmlObject);
		});
		// }
		// $("#div_flow").hide();
		// $("#div_task").show();
		// $("#div_condition").hide();
		// $("#div_sub_flow").hide();
		// $("#div_direction").hide();
	}
	// openDialog(lsURL, "环节属性", 600, 430, "laArg", laArg, taskCallback, true);
}
function FlowLoadEvent(flowProperty) {
	var goForm = $("#flowForm")[0];
	if(goForm == null){
		return;
	}
	goWorkFlow.equalFlowID = null;
	var laNode = goWorkFlow.dictionXML.selectNodes("/diction/categorys/category");
	if (laNode != null && laNode.length > 0) {
		var lsValue = flowProperty.selectSingleNode("categorySN") != null ? flowProperty.selectSingleNode(
				"categorySN").text() : null;
		laNode.each(function(index) {
			var categoryNode = $(this);
			var lsSN = categoryNode.selectSingleNode("code").text();
			bAddEntry($("#DCategory", goForm)[0], categoryNode.selectSingleNode("name").text(), lsSN,
					lsSN == lsValue ? false : true);
		});

	}
	var laNode = goWorkFlow.dictionXML.selectNodes("/diction/forms/form");
	if (laNode != null && laNode.length > 0) {
		var lsValue = flowProperty.selectSingleNode("formID") != null ? flowProperty.selectSingleNode(
				"formID").text() : null;
		laNode.each(function(index) {
			var formNode = $(this);
			var lsID = formNode.selectSingleNode("id").text();
			bAddEntry($("#DForm", goForm)[0], formNode.selectSingleNode("name").text(), lsID,
					lsID == lsValue ? false : true);
		});
	}
	var laNode = goWorkFlow.flowXML.selectNodes("./timers/timer");
	if (laNode != null && laNode.length > 0) {
		laNode.each(function(index) {
			var timerNode = $(this);
			var lsName = timerNode.selectSingleNode("name").text();
			bAddEntry($("#DTimer", goForm)[0], lsName, lsName, true);
		});
	}
	var loNode = flowProperty.selectSingleNode("equalFlow");
	if (loNode != null && loNode.length > 0) {
		var lsValue = (loNode.selectSingleNode("name") != null) ? loNode.selectSingleNode("name").text()
				: null;
		if (lsValue != null && lsValue != "") {
			// goForm.EQFlowName.value = lsValue;
			$("#EQFlowName", goForm).val(lsValue);
		}
		lsValue = (loNode.selectSingleNode("id") != null) ? loNode.selectSingleNode("id").text() : null;
		if (lsValue != null && lsValue != "") {
			// goForm.EQFlowID.value = lsValue;
			$("#EQFlowID", goForm).val(lsValue);
		}
	}
	var laField = [ "beginDirections", "endDirections" ];
	for ( var j = 0; j < laField.length; j++) {
		var loNode = flowProperty.selectSingleNode(laField[j]);
		if (loNode != null && loNode.length != 0) {
			var laNode = loNode.selectNodes("unit");
			if (laNode != null && laNode.length != 0) {
				var laDValue = new Array();
				var laValue = new Array();
				var laDirection = aGetDirections();
				for ( var i = 0; i < laNode.length; i++) {
					var lsFromID = $(laNode[i]).selectSingleNode("value").text();
					var lsToID = $(laNode[i]).selectSingleNode("argValue").text();
					for ( var k = 0; k < laDirection.length; k++) {
						var laTemp = laDirection[k].split("|");
						if (laTemp[3] == lsFromID && laTemp[5] == lsToID) {
							laDValue.push(laTemp[0] + "(" + laTemp[2] + "-" + laTemp[4] + ")");
							laValue.push(lsFromID + "|" + lsToID);
							break;
						}
					}
				}
				var loField = goForm["D" + laField[j]];// eval("goForm.D" +
				// laField[j]);
				loField.value = laDValue.join("\n");
				loField = goForm[laField[j]];// eval("goForm." +
				// laField[j]);
				loField.value = laValue.join(";");
			}
		}
	}
	var loNode = flowProperty.selectSingleNode("bakUsers");
	if (loNode != null && loNode.length != 0) {
		var laNode = loNode.selectNodes("unit");
		if (laNode != null && laNode.length != 0) {
			for ( var i = 0; i < laNode.length; i++) {
				var lsFrom = $(laNode[i]).selectSingleNode("value").text();
				var laTo = $(laNode[i]).selectNodes("argValue").text();
				// var laTo = $(laNode[i]).selectNodes("argValue");
				// var laDValue = new Array();
				// var laValue = new Array();
				// for ( var j = 0; j < laTo.length; j++) {
				// var laTemp = $(laTo[j]).text().split("|");
				// laDValue.push(laTemp[0]);
				// laValue.push(laTemp[1]);
				// }
				var laTemp = lsFrom.split("|");
				var laTemp2 = laTo.split("|");
				bAddEntry(goForm.DBackUser, laTemp[0] + "->" + laTemp2[0], laTemp[1] + "->" + laTemp2[1],
						true);
			}
		}
	}
	loNode = flowProperty.selectSingleNode("messageTemplates");
	if (loNode != null && loNode.length != 0) {
		var laNode = loNode.selectNodes("template");
		if (laNode != null && laNode.length != 0) {
			for ( var i = 0; i < laNode.length; i++) {
				var type = $(laNode[i]).selectSingleNode("type").text();
				var typeName = $(laNode[i]).selectSingleNode("typeName").text();
				var id = $(laNode[i]).selectSingleNode("id").text();
				var name = $(laNode[i]).selectSingleNode("name").text();
				var condition = $(laNode[i]).selectSingleNode("condition").text();
				/*modified by huanglinchuan 2014.10.21 begin*/
				var isSendMsg=$(laNode[i]).selectSingleNode("isSendMsg")?$(laNode[i]).selectSingleNode("isSendMsg").text():"0";
				var extraMsgRecipients = $(laNode[i]).selectSingleNode("extraMsgRecipients")?$(laNode[i]).selectSingleNode("extraMsgRecipients").text():"";
				var extraMsgRecipientUserIds = $(laNode[i]).selectSingleNode("extraMsgRecipientUserIds")?$(laNode[i]).selectSingleNode("extraMsgRecipientUserIds").text():"";
				
				var template = {};
				template.type = type;
				template.typeName = typeName;
				template.id = id;
				template.name = name;
				template.condition = condition;
				template.isSendMsg=isSendMsg;
				template.extraMsgRecipients=extraMsgRecipients;
				template.extraMsgRecipientUserIds=extraMsgRecipientUserIds;
				var text = typeName + "|" + name+"|"+(isSendMsg=="1"?"启用":"关闭")+"|";
				if (condition != null && $.trim(condition) != "") {
					text = text + condition+" | ";
				}else{
					text+="无 | ";
				}
				text+=extraMsgRecipients;
				var $option = $("<option>" + text + "</option>");
				$option.data("value", template);
				$(goForm.DMessageTemplate, goForm).append($option);
				/*modified by huanglinchuan 2014.10.21 end*/
			}
		}
	}
	goForm.name.value = goWorkFlow.flowXML.getAttribute("name") == null ? "" : goWorkFlow.flowXML
			.getAttribute("name");
	goForm.id.value = goWorkFlow.flowXML.getAttribute("id") == null ? "" : goWorkFlow.flowXML
			.getAttribute("id");
	goForm.code.value = goWorkFlow.flowXML.getAttribute("code") == null ? "" : goWorkFlow.flowXML
			.getAttribute("code");
	/*add by huanglinchuan 2014.10.20 begin*/
	goForm.title_expression.value = goWorkFlow.flowXML.getAttribute("titleExpression") == null ? "" : goWorkFlow.flowXML
			.getAttribute("titleExpression");
	/*add by huanglinchuan 2014.10.20 end*/
	/*modified by huanglinchuan 2014.10.28 begin*/
	laField = [ "creator", "user", "monitor", "admin","viewer", "fileRecipient", "msgRecipient" ];
	/*modified by huanglinchuan 2014.10.28 end*/
	for ( var i = 0; i < laField.length; i++) {
		bGetUnitXMLToField(flowProperty, goForm, laField[i]);
	}
	laField = [ "isFree", "isActive", "dueTime", "timeUnit", "isSendFile", "isSendMsg", "fileTemplate",
			"printTemplate", "printTemplateId", "listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		var lsValue = (flowProperty.selectSingleNode(laField[i]) != null) ? flowProperty.selectSingleNode(
				laField[i]).text() : null;
		if (lsValue != null && lsValue != "") {
			bSetFormFieldValue(goForm, laField[i], lsValue);
		}
	}
	bSetVersionInfo(goWorkFlow.flowXML.getAttribute("verType"), window);
	// bInitPageLabel();
}
function FlowInitEvent(flowProperty) {
	var goForm = $("#flowForm")[0];
	if(goForm == null){
		return;
	}
	$(".tabs", goForm).tabs();
	$("#btn_save", goForm).button().click(function(e) {
		FlowOKEvent(flowProperty);
		// alert("设置成功!");
	});
	$("#btn_abandon", goForm).button().click(function(e) {
		saveSetting = false;
		setFlowProperty(goWorkFlow, window.eWorkFlow.contentWindow);
	});

	// eqFlow等价流程
	$("#EQFlowName").click(function(e) {
		e.target.title = "选择等价流程";
		bFlowActions(10, e, goForm);
	});

	// 选择发起人
	$("#Dcreators").click(function(e) {
		e.target.title = "选择发起人";
		bFlowActions(1, e, goForm);
	});
	// 选择参与人
	$("#Dusers").click(function(e) {
		e.target.title = "选择参与人";
		bFlowActions(2, e, goForm);
	});
	// 选择督办人
	$("#Dmonitors").click(function(e) {
		e.target.title = "选择督办人";
		bFlowActions(3, e, goForm);
	});
	// 选择监控者
	$("#Dadmins").click(function(e) {
		e.target.title = "选择监控者";
		bFlowActions(4, e, goForm);
	});
	/*add by huanglinchuan 2014.10.28 begin*/
	// 选择阅读者
	$("#Dviewers").click(function(e) {
		e.target.title = "选择阅读者";
		bFlowActions(21, e, goForm);
	});
	/*add by huanglinchuan 2014.10.28 end*/
	// 定时系统增加
	$("#timerAdd").click(function(e) {
		bFlowActions(11, e, goForm);
	});
	// 定时系统编辑
	$("#timerEdit").click(function(e) {
		bFlowActions(12, e, goForm);
	});
	$("#DTimer").dblclick(function(e) {
		bFlowActions(12, e, goForm);
	});
	// 定时系统删除
	$("#timerDelete").click(function(e) {
		bFlowActions(13, e, goForm);
	});
	// 承诺期限
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";
	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		success : function(result) {
			onGetFormFields("#dueTimeLabel", goForm, result.data);
			$("#dueTimeLabel").val($("#dueTime").val());
		}
	});
	$("#dueTimeLabel").change(function() {
		$("#dueTime").val($(this).val());
	});
	// 别名ID可编辑
	$("#id").removeAttr("disabled");
	// 选择开始时间
	$("#DbeginDirections").click(function(e) {
		e.target.title = "选择开始流向";
		bFlowActions(6, e, goForm);
	});
	// 选择开始时间
	$("#DendDirections").click(function(e) {
		e.target.title = "选择结束流向";
		bFlowActions(7, e, goForm);
	});

	// 打印模板
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
		width : 530,
		height : 260
	});

	// 事件监听
	var listernerSetting = {
		view : {
			showIcon : false,
			showLine : false
		},
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getFlowListeners"
			}
		}
	};
	$("#listenerName").comboTree({
		labelField : "listenerName",
		valueField : "listener",
		treeSetting : listernerSetting,
		autoInitValue : false,
		width : 530,
		height : 260
	});

	// 文件分发
	$("input[name=isSendFile]").change(function() {
		if (this.id === "sendFile" && this.checked) {
			$("#ID_SendFile").show();
		}
		if (this.id === "noSendFile" && this.checked) {
			$("#ID_SendFile").hide();
		}
	}).trigger("change");
	// 文件分发对象
	$("#DfileRecipients").click(function(e) {
		e.target.title = "选择文件分发对象";
		bFlowActions(8, e, goForm);
	});
	// 消息分发
	$("input[name=isSendMsg]").change(function() {
		if (this.id === "sendMsg" && this.checked) {
			$("#ID_SendMsg").show();
		}
		if (this.id === "noSendMsg" && this.checked) {
			$("#ID_SendMsg").hide();
		}
	}).trigger("change");
	// 消息分发对象
	$("#DmsgRecipients").click(function(e) {
		e.target.title = "选择消息分发对象";
		bFlowActions(9, e, goForm);
	});
	// 增加岗位代替
	$("#backuserAdd").click(function(e) {
		bFlowActions(14, e, goForm);
	});
	// 编辑岗位代替
	$("#backuserEdit").click(function(e) {
		bFlowActions(15, e, goForm);
	});
	// 删除岗位代替
	$("#backuserDelete").click(function(e) {
		bFlowActions(16, e, goForm);
	});

	// 增加消息模板
	$("#messageTemplateAdd").click(function(e) {
		bFlowActions(17, e, goForm);
	});
	// 编辑消息模板
	$("#messageTemplateEdit").click(function(e) {
		bFlowActions(18, e, goForm);
	});
	// 删除消息模板
	$("#messageTemplateDelete").click(function(e) {
		bFlowActions(19, e, goForm);
	});
}
function FlowOKEvent(flowProperty) {
	var goForm = $("#flowForm")[0];
	goWorkFlow.equalFlowID = null;
	oSetElement(flowProperty, "categorySN", sGetFormFieldValue(goForm, "DCategory"));
	oSetElement(flowProperty, "formID", sGetFormFieldValue(goForm, "DForm"));
	var loNode = oSetElement(flowProperty, "equalFlow");
	oSetElement(loNode, "name", goForm.EQFlowName.value);
	oSetElement(loNode, "id", goForm.EQFlowID.value);
	laField = [ "beginDirections", "endDirections" ];
	for ( var i = 0; i < laField.length; i++) {
		loNode = flowProperty.selectSingleNode(laField[i]);
		if (loNode != null && loNode.length != 0) {
			flowProperty.removeChild(loNode);
		}
		var loField = $("form #" + laField[i]);
		if (loField.val() != "") {
			loNode = oSetElement(flowProperty, laField[i]);
			var laValue = loField.val().split(";");
			for ( var j = 0; j < laValue.length; j++) {
				var laTemp = laValue[j].split("|");
				var loUnit = oAddElement(loNode, "unit");
				loUnit.setAttribute("type", "16");
				oAddElement(loUnit, "value", laTemp[0]);
				oAddElement(loUnit, "argValue", laTemp[1]);
			}
		}
	}
	loNode = flowProperty.selectSingleNode("bakUsers");
	if (loNode != null && loNode.length != 0) {
		flowProperty.removeChild(loNode);
	}
	loNode = oSetElement(flowProperty, "bakUsers");
	var loField = goForm.DBackUser;
	for ( var i = 0; i < loField.options.length; i++) {
		var lsText = loField.options[i].text;
		var lsValue = loField.options[i].value;
		var laText = lsText.split("->");
		var laValue = lsValue.split("->");
		var loUnit = oAddElement(loNode, "unit");
		loUnit.setAttribute("type", "16");
		oAddElement(loUnit, "value", laText[0] + "|" + laValue[0]);
		oAddElement(loUnit, "argValue", laText[1] + "|" + laValue[1]);
		// laText = laText[1].split(";");
		// laValue = laValue[1].split(";");
		// for ( var j = 0; j < laValue.length; j++) {
		// oAddElement(loUnit, "argValue", laText[j] + "|" + laValue[j]);
		// }
	}
	loNode = flowProperty.selectSingleNode("messageTemplates");
	if (loNode != null && loNode.length != 0) {
		flowProperty.removeChild(loNode);
	}
	loField = goForm.DMessageTemplate;
	loNode = oSetElement(flowProperty, "messageTemplates");
	for ( var i = 0; i < loField.options.length; i++) {
		var laValue = $(goForm.DMessageTemplate.options[i]).data("value");
		var loTemplate = oAddElement(loNode, "template");
		oAddElement(loTemplate, "type", laValue.type);
		oAddElement(loTemplate, "typeName", laValue.typeName);
		oAddElement(loTemplate, "id", laValue.id);
		oAddElement(loTemplate, "name", laValue.name);
		oAddElement(loTemplate, "condition", laValue.condition);
		/*add by huanglinchuan 2014.10.21 begin*/
		oAddElement(loTemplate,"isSendMsg",laValue.isSendMsg);
		oAddElement(loTemplate, "extraMsgRecipients", laValue.extraMsgRecipients);
		oAddElement(loTemplate, "extraMsgRecipientUserIds", laValue.extraMsgRecipientUserIds);
		/*add by huanglinchuan 2014.10.21 end*/
	}

	goWorkFlow.flowXML.setAttribute("name", goForm.name.value);
	goWorkFlow.flowXML.setAttribute("id", goForm.id.value);
	goWorkFlow.flowXML.setAttribute("code", goForm.code.value);
	/*add by huanglinchuan 2014.10.20 begin*/
	goWorkFlow.flowXML.setAttribute("titleExpression", goForm.title_expression.value);
	/*add by huanglinchuan 2014.10.20 end*/
	/*modified by huanglinchuan 2014.10.28 begin*/
	var laField = [ "creator", "user", "monitor", "admin","viewer", "fileRecipient", "msgRecipient" ];
	/*modified by huanglinchuan 2014.10.28 end*/
	for ( var i = 0; i < laField.length; i++) {
		bSetUnitFieldToXML(flowProperty, goForm, laField[i]);
	}
	laField = [ "isFree", "isActive", "dueTime", "timeUnit", "isSendFile", "isSendMsg", "fileTemplate",
			"printTemplate", "printTemplateId", "listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		oSetElement(flowProperty, laField[i], sGetFormFieldValue(goForm, laField[i]));
	}
	// $.dialog.data("flowProperty", flowProperty);
	// window.returnValue = flowProperty;
	// window.close();
}
function TaskLoadEvent(loTask, taskProperty) {
	var goForm = $("#taskForm")[0]; // document.forms[0];
	if(goForm == null){
		return;
	}
	var laNode = goWorkFlow.dictionXML.selectNodes("/diction/forms/form");
	if (laNode != null && laNode.length != 0) {
		var lsValue = taskProperty.selectSingleNode("formID") != null ? taskProperty.selectSingleNode(
				"formID").text() : null;
		for ( var i = 0; i < laNode.length; i++) {
			var lsID = $(laNode[i]).selectSingleNode("id").text();
			bAddEntry(goForm.DForm, $(laNode[i]).selectSingleNode("name").text(), lsID,
					lsID == lsValue ? false : true);
		}
	}
	var laValue = aGetXMLFieldValue(taskProperty, "untreadTasks");
	if (laValue != null) {
		goForm.untreadTasks.value = laValue.join(";");
		for ( var i = 0; i < laValue.length; i++) {
			laValue[i] = "<" + sGetTaskNameByID(laValue[i]) + ">";
		}
		goForm.DuntreadTasks.value = laValue.join(";");
	}
	var laField = [ "readFields", "editFields", "hideFields", "notNullFields", "hideBlocks" ];
	for ( var i = 0; i < laField.length; i++) {
		laValue = aGetXMLFieldValue(taskProperty, laField[i]);
		if (laValue != null) {
			var loField = goForm[laField[i]];// eval("goForm." + laField[i]);
			loField.value = laValue.join(";");
		}
	}
	laValue = aGetXMLFieldValue(taskProperty, "rights");
	aSetButtonRight(goForm.DTaskRight, "/diction/rights/right", laValue);
	// bShowHideUntread();
	// 已办按钮权限
	laValue = aGetXMLFieldValue(taskProperty, "doneRights");
	aSetButtonRight(goForm.DTaskDoneRight, "/diction/doneRights/right", laValue);
	// bShowHideUntread();
	// 监控按钮权限
	laValue = aGetXMLFieldValue(taskProperty, "monitorRights");
	aSetButtonRight(goForm.DTaskMonitorRight, "/diction/monitorRights/right", laValue);
	// bShowHideUntread();
	// 监控按钮权限
	laValue = aGetXMLFieldValue(taskProperty, "adminRights");
	aSetButtonRight(goForm.DTaskAdminRight, "/diction/adminRights/right", laValue);
	// bShowHideUntread();

	var loNode = taskProperty.selectSingleNode("buttons");
	goForm.DButton.uuid = "";
	if (loNode != null && loNode.length != 0) {
		var laNode = loNode.selectNodes("button");
		if (laNode != null && laNode.length != 0) {
			var laUUID = new Array();
			for ( var i = 0; i < laNode.length; i++) {
				var lsUUID = $(laNode[i]).getAttribute("uuid") != null ? $(laNode[i]).getAttribute("uuid")
						: "";
				var lsValue = $(laNode[i]).selectSingleNode("btnValue").text();
				var lsNewName = $(laNode[i]).selectSingleNode("newName").text();
				var $newCode = $(laNode[i]).selectSingleNode("newCode");
				var lsNewCode = $newCode == null ? "" : $newCode.text();
				var useWay = $(laNode[i]).selectSingleNode("useWay").text();
				var lsArgument = $(laNode[i]).selectSingleNode("btnArgument").text();
				var owners = $(laNode[i]).selectSingleNode("btnOwners").text();
				var ownerIds = $(laNode[i]).selectSingleNode("btnOwnerIds").text();
				var users = $(laNode[i]).selectSingleNode("btnUsers").text();
				var userIds = $(laNode[i]).selectSingleNode("btnUserIds").text();
				var copyUsers = $(laNode[i]).selectSingleNode("btnCopyUsers").text();
				var copyUserIds = $(laNode[i]).selectSingleNode("btnCopyUserIds").text();
				var ele = goWorkFlow.dictionXML.selectSingleNode("/diction/buttons/button[value='" + lsValue
						+ "']");
				var lsName = "";
				if (ele != null) {
					lsName = ele.selectSingleNode("name").text();
				}
				var button = {};
				button.btnValue = lsValue;
				button.newName = lsNewName;
				button.newCode = lsNewCode;
				button.useWay = useWay;
				button.btnArgument = lsArgument;
				button.btnOwners = owners;
				button.btnOwnerIds = ownerIds;
				button.btnUsers = users;
				button.btnUserIds = userIds;
				button.btnCopyUsers = copyUsers;
				button.btnCopyUserIds = copyUserIds;
				var $option = $("<option>" + lsName + "->" + lsNewName
						+ (lsArgument != "" ? "(" + sGetTaskNameByID(lsArgument) + ")" : "") + "</option>");
				$option.data("value", button);
				$(goForm.DButton, goForm).append($option);
				// bAddEntry(goForm.DButton, lsName + "->" + lsNewName
				// + (lsArgument != "" ? "(" + sGetTaskNameByID(lsArgument) +
				// ")" : ""), lsUUID + "|"
				// + lsValue + "|" + lsNewName + "|" + lsArgument + "|" + owners
				// + "|" + ownerIds + "|"
				// + users + "|" + userIds + "|" + copyUsers + "|" +
				// copyUserIds, true);
				if (lsUUID != "") {
					laUUID.push(lsUUID);
				}
			}
			goForm.DButton.uuid = laUUID.join(";");
		}
	}
	var loNode = taskProperty.selectSingleNode("optNames");
	if (loNode != null && loNode.length != 0) {
		var laNode = loNode.selectNodes("unit");
		if (laNode != null && laNode.length != 0) {
			for ( var i = 0; i < laNode.length; i++) {
				var lsValue = $(laNode[i]).selectSingleNode("value").text();
				var lsText = $(laNode[i]).selectSingleNode("argValue").text();
				bAddEntry(goForm.DOption, lsText + "|" + lsValue, lsText + "|" + lsValue, true);
			}
		}
	}
	var loNode = taskProperty.selectSingleNode("records");
	goForm.DRemark.uuid = "";
	if (loNode != null && loNode.length != 0) {
		var laNode = loNode.selectNodes("record");
		if (laNode != null && laNode.length != 0) {
			var laUUID = new Array();
			for ( var i = 0; i < laNode.length; i++) {
				var lsUUID = $(laNode[i]).getAttribute("uuid") != null ? $(laNode[i]).getAttribute("uuid")
						: "";
				var lsName = $(laNode[i]).selectSingleNode("name").text();
				var lsField = $(laNode[i]).selectSingleNode("field").text();
				var $lsWay = $(laNode[i]).selectSingleNode("way");
				var lsWay = $lsWay == null ? "" : $lsWay.text();
				var lsValue = $(laNode[i]).selectSingleNode("value").text();
				// bAddEntry(goForm.DRemark, lsName, lsUUID + "|" + lsName + "|"
				// + lsField + "|" + lsValue, true);
				if (lsUUID != "") {
					laUUID.push(lsUUID);
				}
				var record = {};
				record.uuid = lsUUID;
				record.name = lsName;
				record.field = lsField;
				record.way = lsWay;
				record.value = lsValue;
				var $option = $("<option>" + lsName + "</option>");
				$option.data("value", record);
				$(goForm.DRemark, goForm).append($option);
			}
			goForm.DRemark.uuid = laUUID.join(";");
		}
	}
	goForm.name.value = taskProperty.getAttribute("name") == null ? "" : taskProperty.getAttribute("name");
	goForm.id.value = taskProperty.getAttribute("id") == null ? "" : taskProperty.getAttribute("id");
	goForm.sn.value = taskProperty.getAttribute("code") == null ? "" : taskProperty.getAttribute("code");
	laField = [ "user", "copyUser", "monitor" ];
	for ( var i = 0; i < laField.length; i++) {
		bGetUnitXMLToField(taskProperty, goForm, laField[i]);
	}
	laField = [ "isSetUser", "isSetCopyUser", "isSetUserEmpty", "emptyToTask", "emptyNoteDone",
			"isSelectAgain", "isOnlyOne", "isAnyone", "isByOrder", "sameUserSubmit", "isSetMonitor",
			"isInheritMonitor", "untreadType", "snName", "serialNo", "printTemplate", "printTemplateId",
			"listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		var lsValue = (taskProperty.selectSingleNode(laField[i]) != null) ? taskProperty.selectSingleNode(
				laField[i]).text() : null;
		if (lsValue != null && lsValue != "") {
			bSetFormFieldValue(goForm, laField[i], lsValue);
		}
	}
	if (goForm.emptyToTask.value != "") {
		goForm.DemptyToTask.value = "<" + sGetTaskNameByID(goForm.emptyToTask.value) + ">";
	}
	bSetVersionInfo(goWorkFlow.flowXML.getAttribute("verType"), window);
	// bInitPageLabel();
	// 运转模式
	var parallelGateway = taskProperty.selectSingleNode("parallelGateway");
	if (parallelGateway != null && parallelGateway.length != 0) {
		var forkModeNode = parallelGateway.selectSingleNode("forkMode");
		var forkMode = forkModeNode.selectSingleNode("value").text();
		var joinModeNode = parallelGateway.selectSingleNode("joinMode");
		var joinMode = joinModeNode.selectSingleNode("value").text();
		bSetFormFieldValue(goForm, "forkMode", forkMode);
		bSetFormFieldValue(goForm, "joinMode", joinMode);
	}
}
function TaskInitEvent(loTask, taskProperty) {
	var goForm = $("#taskForm")[0];
	if(goForm == null){
		return;
	}
	$(".tabs", goForm).tabs();
	$("#btn_save", goForm).button().click(function(e) {
		TaskOKEvent(loTask, taskProperty);
		if (loTask.xmlObject != null) {
			bSetObjectText(loTask);
		}
		// alert("设置成功!");
	});
	$("#btn_abandon", goForm).button().click(function(e) {
		saveSetting = false;
		// $(loTask).trigger("dblclick");
		setTaskProperty(loTask, window.eWorkFlow.contentWindow, goWorkFlow);
	});

	// 办理人
	$("#isSetUser", goForm).change(function() {
		if ($(this).val() === "1") {
			$("#ID_SetUser_0").show();
		} else {
			$("#ID_SetUser_0").hide();
		}
	}).trigger("change");
	// 选择承办人
	$("#task_Dusers", goForm).click(function(e) {
		e.target.title = "选择承办人";
		bTaskActions(0, "", e, goForm);
	});

	// 抄送
	$("#isSetCopyUser").change(function() {
		if ($(this).val() === "1") {
			$("#ID_SetUser_1").show();
		} else {
			$("#ID_SetUser_1").hide();
		}
	}).trigger("change");
	// 选择抄送人
	$("#task_DcopyUsers").click(function(e) {
		e.target.title = "选择抄送人";
		bTaskActions(1, "", e, goForm);
	});

	// 办理人为空自动进入下一个环节
	$("#isSetUserEmpty").change(function(e) {
		if (this.checked) {
			$("#ID_SetUserEmpty1").show();
			$("#ID_SetUserEmpty2").show();
		} else {
			$("#ID_SetUserEmpty1").hide();
			$("#ID_SetUserEmpty2").hide();
		}
	}).trigger("change");
	// 选择转办环节
	$("#DemptyToTask").click(function(e) {
		bTaskActions(2, "", e, goForm);
	});

	// 选择具体办理人
	$("#isSelectAgain").change(function(e) {
		if (this.checked) {
			$("#ID_OnlyOne").show();
		} else {
			$("#ID_OnlyOne").hide();
		}
	}).trigger("change");
	// 只需要其中一个人办理
	$("#isAnyone").click(function(e) {
		if (this.checked) {
			$("#isByOrder").removeAttr("checked");
		}
	});
	// 按人员顺序依次办理
	$("#isByOrder").click(function(e) {
		if (this.checked) {
			$("#isAnyone").removeAttr("checked");
		}
	});

	// 督办人
	$("#isSetMonitor").change(function() {
		if ($(this).val() != "0") {
			$("#ID_InheritMonitors").show();
		} else {
			$("#ID_InheritMonitors").hide();
		}
		if ($(this).val() == "1") {
			$("#ID_SetMonitor").show();
		} else {
			$("#ID_SetMonitor").hide();
		}
	}).trigger("change");
	// 选择督办人
	$("#task_Dmonitors").click(function(e) {
		bTaskActions(3, "", e, goForm);
	});

	// 增加 办理权限
	$("#todoRightAdd").click(function(e) {
		bTaskActions(11, "", e, goForm);
	});
	// 删除办理权限
	$("#todoRightDelete").click(function(e) {
		bTaskActions(12, "", e, goForm);
	});
	// 清空办理权限
	$("#todoRightClear").click(function(e) {
		bTaskActions(13, "", e, goForm);
	});
	// 增加已办办理权限
	$("#doneRightAdd").click(function(e) {
		bTaskActions(111, "", e, goForm);
	});
	// 删除已办办理权限
	$("#doneRightDelete").click(function(e) {
		bTaskActions(112, "", e, goForm);
	});
	// 清空已办办理权限
	$("#doneRightClear").click(function(e) {
		bTaskActions(113, "", e, goForm);
	});
	// 增加督办办理权限
	$("#monitorRightAdd").click(function(e) {
		bTaskActions(211, "", e, goForm);
	});
	// 删除督办办理权限
	$("#monitorRightDelete").click(function(e) {
		bTaskActions(212, "", e, goForm);
	});
	// 清空督办办理权限
	$("#monitorRightClear").click(function(e) {
		bTaskActions(213, "", e, goForm);
	});
	// 增加监控办理权限
	$("#adminRightAdd").click(function(e) {
		bTaskActions(311, "", e, goForm);
	});
	// 删除监控办理权限
	$("#adminRightDelete").click(function(e) {
		bTaskActions(312, "", e, goForm);
	});
	// 清空监控办理权限
	$("#adminRightClear").click(function(e) {
		bTaskActions(313, "", e, goForm);
	});

	// 增加操作定义
	$("#buttonAdd").click(function(e) {
		bTaskActions(14, "", e, goForm);
	});
	// 编辑操作定义
	$("#buttonEdit").click(function(e) {
		bTaskActions(15, "", e, goForm);
	});
	// 删除操作定义
	$("#buttonDelete").click(function(e) {
		bTaskActions(16, "", e, goForm);
	});
	// 增加意见立场
	$("#optionAdd").click(function(e) {
		bTaskActions(17, "", e, goForm);
	});
	// 删除意见立场
	$("#optionDelete").click(function(e) {
		bTaskActions(18, "", e, goForm);
	});
	// 默认意见立场
	$("#optionDefault").click(function(e) {
		var existValue = "同意|1;不同意|0;已阅|2";
		bTaskActions(19, existValue, e, goForm);
	});

	// 表单设置
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getFormFields",
				"data" : $("#DForm").val()
			}
		}
	};
	// 编辑域
	$("#editFieldLabels", goForm).comboTree({
		labelField : "editFieldLabels",
		valueField : "editFields",
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ $("#DForm", goForm).val() ],
		treeSetting : setting,
		width : 540,
		height : 230
	});
	// 只读域
	$("#readFieldLabels", goForm).comboTree({
		labelField : "readFieldLabels",
		valueField : "readFields",
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ $("#DForm", goForm).val() ],
		treeSetting : setting,
		width : 540,
		height : 230
	});
	// 隐藏域
	$("#hideFieldLabels", goForm).comboTree({
		labelField : "hideFieldLabels",
		valueField : "hideFields",
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ $("#DForm", goForm).val() ],
		treeSetting : setting,
		width : 540,
		height : 230
	});
	// 必填域
	$("#notNullFieldLabels", goForm).comboTree({
		labelField : "notNullFieldLabels",
		valueField : "notNullFields",
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ $("#DForm", goForm).val() ],
		treeSetting : setting,
		width : 540,
		height : 230
	});
	// 区块设置
	var blockSetting = {
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getFormBlocks",
				"data" : $("#DForm").val()
			}
		}
	};
	// 隐藏区块
	$("#hideBlockLabels", goForm).comboTree({
		labelField : "hideBlockLabels",
		valueField : "hideBlocks",
		initService : "flowSchemeService.getBlockByBlockCodes",
		initServiceParam : [ $("#DForm", goForm).val() ],
		treeSetting : blockSetting,
		width : 540,
		height : 230
	});
	if ($("#DForm", goForm).val() == 0) {
		$("#editFieldLabels").comboTree("disable");
		$("#readFieldLabels").comboTree("disable");
		$("#hideFieldLabels").comboTree("disable");
		$("#notNullFieldLabels").comboTree("disable");
		$("#hideBlockLabels").comboTree("disable");
	}
	$("#DForm", goForm).change(function(e) {
		var val = $(this).val();
		$("#editFieldLabels", goForm).comboTree("clear");
		$("#readFieldLabels", goForm).comboTree("clear");
		$("#hideFieldLabels", goForm).comboTree("clear");
		$("#notNullFieldLabels", goForm).comboTree("clear");
		$("#hideBlockLabels", goForm).comboTree("clear");
		$("#editFieldLabels", goForm).comboTree("disable");
		$("#readFieldLabels", goForm).comboTree("disable");
		$("#hideFieldLabels", goForm).comboTree("disable");
		$("#notNullFieldLabels", goForm).comboTree("disable");
		$("#hideBlockLabels", goForm).comboTree("disable");
		if (val != 0) {
			var setting = {
				async : {
					otherParam : {
						"data" : val
					}
				}
			};
			$("#editFieldLabels", goForm).comboTree("enable");
			$("#readFieldLabels", goForm).comboTree("enable");
			$("#hideFieldLabels", goForm).comboTree("enable");
			$("#notNullFieldLabels", goForm).comboTree("enable");
			$("#editFieldLabels", goForm).comboTree("setParams", {
				treeSetting : setting
			});
			$("#readFieldLabels", goForm).comboTree("setParams", {
				treeSetting : setting
			});
			$("#hideFieldLabels", goForm).comboTree("setParams", {
				treeSetting : setting
			});
			$("#notNullFieldLabels", goForm).comboTree("setParams", {
				treeSetting : setting
			});
			$("#hideBlockLabels", goForm).comboTree("setParams", {
				treeSetting : setting
			});
		}
	});
	if($("#DForm", goForm).val() == "-1"){
		var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
		var formUuid = loNode != null ? loNode.text() : "";
		$("#DForm", goForm).val(formUuid)
		$("#DForm", goForm).trigger("change");
	}

	// 增加信息记录
	$("#remarkAdd").click(function(e) {
		bTaskActions(21, "", e, goForm);
	});
	// 增加信息记录
	$("#remarkEdit").click(function(e) {
		bTaskActions(22, "", e, goForm);
	});
	// 删除信息记录
	$("#remarkDelete").click(function(e) {
		bTaskActions(23, "", e, goForm);
	});

	// 生成流水号
	var setting = {
		view : {
			showIcon : false,
			showLine : false
		},
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "level"
		},
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getSerialNumbers"
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				if (treeNode != null) {
					var checked = !treeNode.checked;
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					zTree.checkNode(treeNode, checked);
					if (checked) {
						$("#snName").val(treeNode.name);
						$("#serialNo").val(treeNode.data);
					} else {
						$("#snName").val("");
						$("#serialNo").val("");
					}
				}
			}
		}
	};
	$("#snName").comboTree({
		labelField : "snName",
		valueField : "serialNo",
		treeSetting : setting,
		autoInitValue : false,
		width : 540,
		height : 230
	});

	// 打印模板
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
	$("#task_printTemplate").comboTree({
		labelField : "task_printTemplate",
		valueField : "task_printTemplateId",
		treeSetting : printTemplateSetting,
		autoInitValue : false,
		width : 540,
		height : 230
	});

	// 事件监听
	var listenerSetting = {
		view : {
			showIcon : false,
			showLine : false
		},
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getTaskListeners"
			}
		}
	};
	$("#listenerName").comboTree({
		labelField : "listenerName",
		valueField : "listener",
		treeSetting : listenerSetting,
		autoInitValue : false,
		width : 540,
		height : 230
	});
}
function bTaskActions(piIndex, psValues, event, goForm) {
	switch (piIndex) {
	case 0:
		SelectUsers("", "user", event.target.title, null, goForm);
		break;
	case 1:
		SelectUsers("", "copyUser", event.target.title, null, goForm);
		break;
	case 2:
		SelectUsers("alltask", "emptyToTask", event.target.title, true, goForm);
		break;
	case 3:
		SelectUsers("", "monitor", event.target.title, null, goForm);
		break;
	case 4:
		SelectUsers("task", "untreadTasks", event.target.title);
		break;
	case 6:
		bSetField("editFields", true, true);
		break;
	case 7:
		bSetField("readFields", true, true);
		break;
	case 8:
		bSetField("hideFields", true, true);
		break;
	case 9:
		bSetField("notNullFields", true, true);
		break;
	case 11:
		sGetButtonRight(goForm.DTaskRight, "/diction/rights/right");
		break;
	case 12:
		bMoveEntry(goForm.DTaskRight);
		// bShowHideUntread();
		break;
	case 13:
		for ( var i = goForm.DTaskRight.options.length - 1; i >= 0; i--) {
			goForm.DTaskRight.options[i] = null;
		}
		// bShowHideUntread();
		break;
	case 111:
		sGetButtonRight(goForm.DTaskDoneRight, "/diction/doneRights/right");
		break;
	case 112:
		bMoveEntry(goForm.DTaskDoneRight);
		// bShowHideUntread();
		break;
	case 113:
		for ( var i = goForm.DTaskDoneRight.options.length - 1; i >= 0; i--) {
			goForm.DTaskDoneRight.options[i] = null;
		}
		// bShowHideUntread();
		break;
	case 211:
		sGetButtonRight(goForm.DTaskMonitorRight, "/diction/monitorRights/right");
		break;
	case 212:
		bMoveEntry(goForm.DTaskMonitorRight);
		// bShowHideUntread();
		break;
	case 213:
		for ( var i = goForm.DTaskMonitorRight.options.length - 1; i >= 0; i--) {
			goForm.DTaskMonitorRight.options[i] = null;
		}
		// bShowHideUntread();
		break;
	case 311:
		sGetButtonRight(goForm.DTaskAdminRight, "/diction/adminRights/right");
		break;
	case 312:
		bMoveEntry(goForm.DTaskAdminRight);
		// bShowHideUntread();
		break;
	case 313:
		for ( var i = goForm.DTaskAdminRight.options.length - 1; i >= 0; i--) {
			goForm.DTaskAdminRight.options[i] = null;
		}
		// bShowHideUntread();
		break;
	case 14:
		var reValCallback = function(lsValue) {
			if (lsValue == null || lsValue == "") {
				return;
			}
			var laValue = lsValue;
			var ele = goWorkFlow.dictionXML.selectSingleNode("/diction/buttons/button[value='"
					+ laValue.btnValue + "']");
			var lsName = "";
			if (ele != null) {
				lsName = ele.selectSingleNode("name").text();
			}
			var lsText = lsName + "->" + laValue.newName
					+ (laValue.btnArgument != "" ? "(" + sGetTaskNameByID(laValue.btnArgument) + ")" : "");
			// bAddEntry(goForm.DButton, lsText, lsValue);
			var $option = $("<option>" + lsText + "</option>");
			$option.data("value", laValue);
			$("#DButton", goForm).append($option);
		};

		sGetButton(null, reValCallback, goForm);
		break;
	case 15:
		// var lsValue = sGetAllEntryValue(goForm.DButton, true);
		// var lsArgs = $option.data("value") || {};
		// if (lsValue == null || lsValue == "") {
		// return false;
		// }
		var $option = $("#DButton > option:selected", goForm);
		if ($option.length == 0) {
			return false;
		}
		// var lsValues = lsValue.split("|");
		// 转化为对象参数
		var lsValue = $option.data("value") || {};

		var reValCallback = function(lsValue) {
			if (lsValue == null || lsValue == "") {
				return;
			}
			var laValue = lsValue;
			var ele = goWorkFlow.dictionXML.selectSingleNode("/diction/buttons/button[value='"
					+ laValue.btnValue + "']");
			var lsName = "";
			if (ele != null) {
				lsName = ele.selectSingleNode("name").text();
			}
			var lsText = lsName + "->" + laValue.newName
					+ (laValue.btnArgument != "" ? "(" + sGetTaskNameByID(laValue.btnArgument) + ")" : "");
			// bEditEntry(goForm.DButton, lsText, lsValue);
			$option.text(lsText);
			$option.data("value", lsValue);
		};
		sGetButton(lsValue, reValCallback, goForm);
		break;
	case 16:
		bMoveEntry(goForm.DButton);
		break;
	case 17:
		var reValCallback = function(lsValue) {
			if (lsValue != null && lsValue != "") {
				bAddEntry(goForm.DOption, lsValue, lsValue);
			}
		};
		var laArg = new Array();
		laArg.Exist = ";" + sGetAllEntryValue(goForm.DOption) + ";";
		// var lsValue = vOpenModal_("option.htm", laArg, 400, 180);
		// openDialog("option.htm", "意见立场", 400, 180, "optionLaArg", laArg,
		// reValCallback);
		GetOption(goForm, laArg, reValCallback);
		break;
	case 18:
		bMoveEntry(goForm.DOption);
		break;
	case 19:
		for ( var i = goForm.DOption.options.length - 1; i >= 0; i--) {
			goForm.DOption.options[i] = null;
		}
		var laValue = psValues.split(";");
		for ( var i = 0; i < laValue.length; i++) {
			bAddEntry(goForm.DOption, laValue[i], laValue[i], true);
		}
		break;
	case 21:
		var reValCallback = function(lsReturn) {
			if (lsReturn == null) {
				return;
			}
			// var laValue = lsReturn.split("|");
			// bAddEntry(goForm.DRemark, laValue[1], lsReturn);
			var $option = $("<option>" + lsReturn.name + "</option>");
			$option.data("value", lsReturn);
			$(goForm.DRemark, goForm).append($option);
		};
		sGetRemark({
			name : ""
		}, reValCallback);
		break;
	case 22:
		// var lsValue = sGetAllEntryValue(goForm.DRemark, true);
		// if (lsValue == null || lsValue == "") {
		// return false;
		// }
		var $option = $("#DRemark > option:selected", goForm);
		if ($option.length == 0) {
			return false;
		}
		// 转化为对象参数
		var lsValue = $option.data("value") || {};
		var reValCallback = function(lsReturn) {
			if (lsReturn == null) {
				return;
			}
			var lsText = lsReturn.name;
			// var laValue = lsReturn.split("|");
			// bEditEntry(goForm.DRemark, laValue[1], lsReturn);
			$option.text(lsText);
			$option.data("value", lsReturn);
		};
		sGetRemark(lsValue, reValCallback);
		break;
	case 23:
		bMoveEntry(goForm.DRemark);
		break;
	}
}
function sGetRemark(psValue, reValCallback) {
	var laArg = new Array();
	laArg.value = (psValue == null) ? {} : psValue;
	// laArg.opener = opener;
	// return vOpenModal_("remark.htm", laArg, 400, 180);
	var liWidth = 400;
	var liHeight = 230;
	// openDialog("remark.htm", "信息记录", liWidth, liHeight, "remarkLaArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/remark2.htm", function(data) {
		$("#dlg_remark").dialog({
			title : "信息记录",
			autoOpen : true,
			resizable : false,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_remark").dialog("destroy");
				$("#dlg_remark").html("<div/>");
			},
			open : function() {
				$("#dlg_remark").html(data);
				bInitRemarkValue(laArg);
			},
			buttons : {
				"确定" : function(e) {
					if (!sRemarkCheck(laArg)) {
						return;
					}
					var returnValue = sRemarkBuildValue(laArg);
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function bInitRemarkValue(laArg) {
	var gsValue = laArg["value"];
	var goForm = $("#remarkForm")[0];
	var laNode = goWorkFlow.dictionXML.selectNodes("/diction/formats/format");
	if (laNode != null) {
		for ( var i = 0; i < laNode.length; i++) {
			var lsValue = $(laNode[i]).selectSingleNode("value").text();
			var lsName = $(laNode[i]).selectSingleNode("name").text();
			bAddEntry(goForm.DFormat, lsName, lsValue, true);
		}
	}
	if (gsValue != null && gsValue != "") {
		var laValue = gsValue;// gsValue.split("|");
		goForm.name.value = laValue.name;
		goForm.field.value = laValue.field;
		$(goForm.way, goForm).filter('[value=' + laValue.way + ']').prop('checked', true);
		bSetFormFieldValue(goForm, "DFormat", laValue.value);
	}

	// 显示位置
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getFormFields",
				"data" : formUuid
			}
		}
	};
	$("#fieldLabel").comboTree({
		labelField : "fieldLabel",
		valueField : "field",
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ formUuid ],
		treeSetting : setting,
		width : 283,
		height : 230
	});
	return true;
}
function sRemarkCheck(laArg) {
	var goForm = $("#remarkForm")[0];
	if (goForm.name.value == "") {
		alert(sGetLang("FLOW_WF_INFONAMEISEMPTY"));
		return false;
	}
	if (goForm.field.value == "") {
		alert(sGetLang("FLOW_WF_INFOFIELDISEMPTY"));
		return false;
	}
	if (goForm.DFormat.value == "") {
		alert(sGetLang("FLOW_WF_INFOFORMATISEMPTY"));
		return false;
	}
	return true;
}
function sRemarkBuildValue(laArg) {
	var gsValue = laArg["value"];
	var goForm = $("#remarkForm")[0];
	var record = {};
	record.uuid = gsValue.uuid;
	record.name = goForm.name.value;
	record.field = goForm.field.value;
	record.way = $("input[name=way]:checked", goForm).val();
	record.value = goForm.DFormat.value;
	// var lsValue = "|" + goForm.name.value + "|" + goForm.field.value + "|" +
	// goForm.DFormat.value;
	// if (gsValue != null && gsValue != "") {
	// var laValue = gsValue.split("|");
	// lsValue = laValue[0] + lsValue;
	// }
	return record;
}
function GetOption(goForm, laArg, reValCallback) {
	// openDialog("option.htm", "意见立场", 400, 180, "optionLaArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/option2.htm", function(data) {
		$("#dlg_option").dialog({
			title : "意见立场",
			autoOpen : true,
			resizable : false,
			width : 400,
			height : 200,
			modal : true,
			close : function() {
				$("#dlg_option").dialog("destroy");
				$("#dlg_option").html("<div/>");
			},
			open : function() {
				$("#dlg_option").html(data);
			},
			buttons : {
				"确定" : function(e) {
					var gsExistOpt = laArg["Exist"];
					var goForm = $("#optionForm")[0];
					if (goForm.Name.value == "") {
						alert("请输入意见立场名称。");
						return;
					} else if (goForm.Value.value == "") {
						alert("请输入意见立场值。");
						return;
					} else if (gsExistOpt.indexOf(";" + goForm.Name.value + "|") != -1) {
						alert("名称已经存在。");
						return;
					} else if (gsExistOpt.indexOf("|" + goForm.Value.value + ";") != -1) {
						alert("值已经存在。");
						return;
					} else {
						reValCallback(goForm.Name.value + "|" + goForm.Value.value);
					}
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function sGetButtonRight(field, path) {
	var lsValues = ";" + sGetAllEntryValue(field) + ";";
	var lsRights = "";
	var laNode = goWorkFlow.dictionXML.selectNodes(path);
	if (laNode != null) {
		for ( var i = 0; i < laNode.length; i++) {
			var lsValue = $(laNode[i]).selectSingleNode("value").text();
			if (lsValues.indexOf(";" + lsValue + ";") != -1) {
				continue;
			}
			var lsName = $(laNode[i]).selectSingleNode("name").text();
			if (lsRights == "") {
				lsRights = lsName + "|" + lsValue;
			} else {
				lsRights += ";" + lsName + "|" + lsValue;
			}
		}
	}

	var reValCallback = function(lsReturn) {
		// var lsReturn = $.dialog.data("checkboxSelectReVal");
		// $.dialog.data("checkboxSelectReVal", null);
		if (lsReturn == null) {
			return;
		}
		lsReturn = ";" + lsReturn + ";";
		if (laNode != null) {
			for ( var i = 0; i < laNode.length; i++) {
				var lsValue = $(laNode[i]).selectSingleNode("value").text();
				if (lsReturn.indexOf(";" + lsValue + ";") == -1) {
					continue;
				}
				var lsName = $(laNode[i]).selectSingleNode("name").text();
				bAddEntry(field, lsName, lsValue, true);
			}
		}
		// bShowHideUntread();
	};
	sSelectArray_("Multiple", field.title, "", lsRights, null, null, null, reValCallback);
}
function sGetButton(psValue, reValCallback, goForm) {
	var lsExistValue = sGetAllEntryValue(goForm.DButton);
	var laButton = new Array();
	var laNode = goWorkFlow.dictionXML.selectNodes("/diction/buttons/button");
	if (laNode != null) {
		var laValue = lsExistValue.split(";");
		for ( var i = 0; i < laValue.length; i++) {
			laValue[i] = laValue[i].split("|")[1];
		}
		lsExistValue = ";" + laValue.join(";") + ";";
		var lsCurValue = (psValue != null) ? psValue.newName : "";
		for ( var i = 0; i < laNode.length; i++) {
			var lsValue = $(laNode[i]).selectSingleNode("value").text();
			if (lsExistValue.indexOf(";" + lsValue + ";") != -1 && lsValue != lsCurValue) {
				// 复制按钮
				// continue;
			}
			var lsName = $(laNode[i]).selectSingleNode("name").text();
			laButton.push(lsName + "|" + lsValue);
		}
	}
	var laTask = aGetTasks("TASK/SUBFLOW", goForm.id.value, true);
	var laArg = new Array();
	laArg.Value = (psValue != null) ? psValue : {};
	laArg.Button = laButton;
	laArg.Task = laTask;

	// return vOpenModal_("button.htm", laArg, 400, 180);
	var liWidth = 450;
	var liHeight = 395;
	// openDialog("button.htm", "操作定义", liWidth, liHeight, "buttonLaArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/button2.htm", function(data) {
		$("#dlg_button_define").dialog({
			title : "操作定义",
			autoOpen : true,
			resizable : false,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_button_define").dialog("destroy");
				$("#dlg_button_define").html("<div/>");
			},
			open : function() {
				$("#dlg_button_define").html(data);
				ButtonLoadEvent(laArg);
				// CheckboxSelectInitEvent(laArg);
			},
			buttons : {
				"确定" : function(e) {
					if (!ButtonOKCheck(laArg)) {
						return;
					}
					var returnValue = ButtonOKEvent(laArg);
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function ButtonLoadEvent(laArg) {
	var gsValue = laArg["Value"];
	var laButton = laArg["Button"];
	var laTask = laArg["Task"];
	// onload事件
	var goForm = $("#buttonForm")[0];
	for ( var i = 0; i < laButton.length; i++) {
		var laValue = laButton[i].split("|");
		bAddEntry(goForm.DButton, laValue[0], laValue[1], true);
	}
	for ( var i = 0; i < laTask.length; i++) {
		var laValue = laTask[i].split("|");
		bAddEntry(goForm.DTask, laValue[0], laValue[1], true);
	}
	if (gsValue != "") {
		// var laValue = gsValue.split("|");
		var laValue = gsValue;
		bSetFormFieldValue(goForm, "DButton", laValue.btnValue);
		goForm.newName.value = laValue.newName == null ? "" : laValue.newName;
		goForm.newCode.value = laValue.newCode == null ? "" : laValue.newCode;
		$(goForm.useWay).filter('[value=' + laValue.useWay + ']').prop('checked', true);
		bSetFormFieldValue(goForm, "DTask", laValue.btnArgument);
		goForm.DUnits.value = laValue.btnOwners == null ? "" : laValue.btnOwners;
		goForm.Units.value = laValue.btnOwnerIds == null ? "" : laValue.btnOwnerIds;
		if (laValue.btnUserIds != null) {
			goForm.Dusers.value = laValue.btnUsers == null ? "" : laValue.btnUsers;
			var userIdArray = laValue.btnUserIds.split(",");
			if (userIdArray.length == 5) {
				goForm.Duser1.value = userIdArray[0];
				goForm.user1.value = userIdArray[1];
				goForm.user2.value = userIdArray[2];
				goForm.user4.value = userIdArray[3];
				goForm.user8.value = userIdArray[4];
			}
		}
		if (laValue.btnCopyUserIds != null) {
			goForm.DcopyUsers.value = laValue.btnCopyUsers == null ? "" : laValue.btnCopyUsers;
			var copyUserIdArray = laValue.btnCopyUserIds.split(",");
			if (copyUserIdArray.length == 5) {
				goForm.DcopyUser1.value = copyUserIdArray[0];
				goForm.copyUser1.value = copyUserIdArray[1];
				goForm.copyUser2.value = copyUserIdArray[2];
				goForm.copyUser4.value = copyUserIdArray[3];
				goForm.copyUser8.value = copyUserIdArray[4];
			}
		}
		// 新的名称
		var selectSubFlowSetting = {
			view : {
				showIcon : false
			},
			check : {
				chkStyle : "radio"
			},
			async : {
				enable : true,
				contentType : "application/json",
				url : ctx + "/json/data/services",
				otherParam : {
					"serviceName" : "resourceService",
					"methodName" : "getResourceButtonTree",
					"data" : "-1"
				},
				type : "POST"
			}
		};
		// 弹出环节选择框选择下一子流程
		$(goForm.newName, goForm).click(function() {
			$("#dlg_select_button").popupTreeWindow({
				title : "权限按钮选择",
				initValues : laValue.newCode,
				treeSetting : selectSubFlowSetting,
				afterSelect : function(retVal) {
					$(goForm.newName, goForm).val(retVal["name"]);
					$(goForm.newCode, goForm).val(retVal["value"]);
				},
				afterCancel : function() {
					$("#dlg_select_button").html("");
				},
				close : function(e) {
					$("#dlg_select_button").html("");
				}
			});
			$("#dlg_select_button").popupTreeWindow("open");
		});
	}

	// 选择所有者
	$("#button_DUnits").click(function(e) {
		$.unit.open({
			initNames : goForm.DUnits.value,
			initIDs : goForm.Units.value,
			title : "人员选择",
			multiple : true,
			selectType : 1,
			nameType : "21",
			showType : true,
			type : "MyUnit",
			loginStatus : false,
			excludeValues : null,
			afterSelect : function(laValue) {
				if (laValue == null) {
					return;
				}
				goForm.DUnits.value = laValue["name"];
				goForm.Units.value = laValue["id"];
			}
		});
	});
	// 选择参与人
	$("#button_Dusers").click(function(e) {
		e.target.title = "选择参与人";
		SelectUsers("", "user", e.target.title, null, goForm);
	});
	// 选择抄送人
	$("#button_DcopyUsers").click(function(e) {
		e.target.title = "选择抄送人";
		SelectUsers("", "copyUser", e.target.title, null, goForm);
	});
}
function ButtonOKCheck(laArg) {
	var goForm = $("#buttonForm")[0];
	var laTemp = new Array();
	laTemp[0] = sGetAllEntryValue(goForm.DButton, true);
	if (laTemp[0] == "") {
		alert("请选择按钮!");
		return false;
	}
	if (goForm.newName.value == "") {
		alert("请输入新的名称!");
		return false;
	}
	if (goForm.DTask.value == "") {
		alert("请选择目标环节!");
		return false;
	}
	return true;
}
function ButtonOKEvent(laArg) {
	var gsValue = laArg["Value"];
	var goForm = $("#buttonForm")[0];
	var laTemp = new Array();
	laTemp[0] = sGetAllEntryValue(goForm.DButton, true);
	if (laTemp[0] == "") {
		alert(goForm.DButton.emptyMsg);
	} else if (goForm.newName.value == "") {
		alert(goForm.newName.emptyMsg);
	} else {
		var laTemp = {};
		laTemp.unid = "";
		if (gsValue != "") {
			// var laValue = gsValue.split("|");
			laTemp.unid = gsValue.uunid;
		}
		laTemp.btnValue = sGetAllEntryValue(goForm.DButton, true);
		laTemp.newName = goForm.newName.value;
		laTemp.newCode = goForm.newCode.value;
		laTemp.useWay = $("input[name=useWay]:checked").val();
		var lsValue = sGetAllEntryValue(goForm.DTask, true);
		laTemp.btnArgument = lsValue == "0" ? "" : lsValue;
		laTemp.btnOwners = goForm.DUnits.value;
		laTemp.btnOwnerIds = goForm.Units.value;
		laTemp.btnUsers = goForm.Dusers.value;
		var userIds = $("input[name='Duser1']", goForm).val() + "," + $("input[name='user1']", goForm).val()
				+ "," + $("input[name='user2']", goForm).val() + "," + $("input[name='user4']", goForm).val()
				+ "," + $("input[name='user8']", goForm).val();
		laTemp.btnUserIds = userIds;
		laTemp.btnCopyUsers = goForm.DcopyUsers.value;
		var copyUserIds = $("input[name='DcopyUser1']", goForm).val() + ","
				+ $("input[name='copyUser1']", goForm).val() + ","
				+ $("input[name='copyUser2']", goForm).val() + ","
				+ $("input[name='copyUser4']", goForm).val() + ","
				+ $("input[name='copyUser8']", goForm).val();
		//modified by huanglinchuan 2014.10.22 begin
		laTemp.btnCopyUserIds = copyUserIds;
		//modified by huanglinchuan 2014.10.22 end
		return laTemp;
	}
}
function sSelectArray_(psStyle, psTitle, psInfo, psArg, pbLimit, pbEmpty, pbNoDefault, reValCallback) {
	var laArg = new Object();
	laArg.Title = psTitle;
	laArg.PromptInfo = psInfo;
	laArg.ArgPara = psArg;
	var lbLimit = (pbLimit == null) ? true : pbLimit;
	laArg.Limit = (lbLimit) ? "1" : "0";
	laArg.Empty = (pbEmpty == true) ? "1" : "0";
	laArg.SetDefault = (pbNoDefault != true) ? "1" : "0";
	if (psStyle.toLowerCase() == "single") {
		psStyle = "Radio";
	}
	if (psStyle.toLowerCase() == "multiple") {
		psStyle = "Checkbox";
	}
	// var raArr = vOpenModal_("checkbox/select.htm", laArg, 400, 270);
	// return raArr;
	var liWidth = 400;
	var liHeight = 270;
	// openDialog("checkbox/select.htm", psTitle, liWidth, liHeight,
	// "checkboxSelectLaArg", laArg, reValCallback);
	$.get(ctx + "/workflow/checkbox/select2.htm", function(data) {
		$("#dlg_checkbox_select").dialog({
			title : psTitle,
			autoOpen : true,
			resizable : false,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_checkbox_select").dialog("destroy");
				$("#dlg_checkbox_select").html("<div/>");
			},
			open : function() {
				$("#dlg_checkbox_select").html(data);
				CheckboxSelectInitEvent(laArg);
			},
			buttons : {
				"确定" : function(e) {
					var returnValue = CheckboxSelectOKEvent(laArg);
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"全选" : function(e) {
					CheckboxSelectCheckAll(true);
				},
				"全不选" : function(e) {
					CheckboxSelectCheckAll(false);
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function CheckboxSelectInitEvent(laArg) {
	var psArg = laArg["ArgPara"];
	var laVls = psArg.split(";");
	$.each(laVls, function(i) {
		if (this.indexOf("|") > 0) {
			var text = this.substr(0, this.indexOf("|"));
			var value = this.substr(this.indexOf("|") + 1);
			var checkbox = '<div><input id="checkbox_' + i + '" type="checkbox" value="' + value
					+ '" /><label for="checkbox_' + i + '">' + text + '</label></div>';
			$("#select_checkbox").append(checkbox);
		}
	});
}
function CheckboxSelectCheckAll(checked) {
	var $checkboxes = $("#select_checkbox").find(":checkbox");
	$checkboxes.each(function(i) {
		this.checked = checked;
	});
}
function CheckboxSelectOKEvent(laArg) {
	var lsReturn = "";
	var $checkboxes = $("#select_checkbox").find(":checkbox:checked");
	$checkboxes.each(function(i) {
		if (i == 0) {
			lsReturn = this.value;
		} else {
			lsReturn = lsReturn + ";" + this.value;
		}
	});
	return lsReturn;
}
function TaskOKEvent(loTask, taskProperty) {
	var goForm = $("#taskForm")[0];
	var lsValue = sGetFormFieldValue(goForm, "DForm");
	oSetElement(taskProperty, "formID", lsValue == "0" ? "" : lsValue);
	bSetXMLFieldValue(taskProperty, "untreadTasks",
			(goForm.untreadTasks.value != "") ? goForm.untreadTasks.value.split(";") : null);
	var laField = [ "readFields", "editFields", "hideFields", "notNullFields", "hideBlocks" ];
	for ( var i = 0; i < laField.length; i++) {
		var loField = goForm[laField[i]];// eval("goForm." + laField[i]);
		bSetXMLFieldValue(taskProperty, laField[i], (loField.value != "") ? loField.value.split(";") : null);
	}
	var laValue = new Array();
	for ( var i = 0; i < goForm.DTaskRight.options.length; i++) {
		laValue.push(goForm.DTaskRight.options[i].value);
	}
	bSetXMLFieldValue(taskProperty, "rights", laValue);
	// 已办权限
	var doneRightsLaValue = new Array();
	for ( var i = 0; i < goForm.DTaskDoneRight.options.length; i++) {
		doneRightsLaValue.push(goForm.DTaskDoneRight.options[i].value);
	}
	bSetXMLFieldValue(taskProperty, "doneRights", doneRightsLaValue);
	// 督办权限
	var monitorRightsLaValue = new Array();
	for ( var i = 0; i < goForm.DTaskMonitorRight.options.length; i++) {
		monitorRightsLaValue.push(goForm.DTaskMonitorRight.options[i].value);
	}
	bSetXMLFieldValue(taskProperty, "monitorRights", monitorRightsLaValue);
	// 监控权限
	var adminRightsLaValue = new Array();
	for ( var i = 0; i < goForm.DTaskAdminRight.options.length; i++) {
		adminRightsLaValue.push(goForm.DTaskAdminRight.options[i].value);
	}
	bSetXMLFieldValue(taskProperty, "adminRights", adminRightsLaValue);

	loNode = taskProperty.selectSingleNode("buttons");
	if (loNode != null) {
		taskProperty.removeChild(loNode);
	}
	loNode = oSetElement(taskProperty, "buttons");
	var laUUID = goForm.DButton.uuid.split(";");
	for ( var i = 0; i < goForm.DButton.options.length; i++) {
		// var laValue = goForm.DButton.options[i].value.split("|");
		var laValue = $(goForm.DButton.options[i]).data("value");
		var loButton = oAddElement(loNode, "button");
		loButton.setAttribute("uunid", laValue.uunid);
		oAddElement(loButton, "btnValue", laValue.btnValue);
		oAddElement(loButton, "newName", laValue.newName);
		oAddElement(loButton, "newCode", laValue.newCode);
		oAddElement(loButton, "useWay", laValue.useWay);
		oAddElement(loButton, "btnArgument", laValue.btnArgument);
		oAddElement(loButton, "btnOwners", laValue.btnOwners);
		oAddElement(loButton, "btnOwnerIds", laValue.btnOwnerIds);
		oAddElement(loButton, "btnUsers", laValue.btnUsers);
		oAddElement(loButton, "btnUserIds", laValue.btnUserIds);
		oAddElement(loButton, "btnCopyUsers", laValue.btnCopyUsers);
		oAddElement(loButton, "btnCopyUserIds", laValue.btnCopyUserIds);
		if (laValue[0] != "") {
			for ( var j = 0; j < laUUID.length; j++) {
				if (laValue[0] == laUUID[j]) {
					laUUID[j] = "";
					break;
				}
			}
		}
	}
	loNode = goWorkFlow.flowXML.selectSingleNode("deletes");
	for ( var j = 0; j < laUUID.length; j++) {
		if (laUUID[j] != "") {
			oAddElement(loNode, "button", laUUID[j]);
		}
	}
	loNode = taskProperty.selectSingleNode("optNames");
	if (loNode != null) {
		taskProperty.removeChild(loNode);
	}
	loNode = oSetElement(taskProperty, "optNames");
	for ( var i = 0; i < goForm.DOption.options.length; i++) {
		var laValue = goForm.DOption.options[i].value.split("|");
		var loUnit = oAddElement(loNode, "unit");
		loUnit.setAttribute("type", "16");
		oAddElement(loUnit, "value", laValue[1]);
		oAddElement(loUnit, "argValue", laValue[0]);
	}
	loNode = taskProperty.selectSingleNode("records");
	if (loNode != null) {
		taskProperty.removeChild(loNode);
	}
	loNode = oSetElement(taskProperty, "records");
	laUUID = goForm.DRemark.uuid.split(";");
	for ( var i = 0; i < goForm.DRemark.options.length; i++) {
		// var laValue = goForm.DRemark.options[i].value.split("|");
		var laValue = $(goForm.DRemark.options[i]).data("value");
		var loButton = oAddElement(loNode, "record");
		loButton.setAttribute("uunid", laValue.uuid);
		oAddElement(loButton, "name", laValue.name);
		oAddElement(loButton, "field", laValue.field);
		oAddElement(loButton, "way", laValue.way);
		oAddElement(loButton, "value", laValue.value);
		if (laValue[0] != "") {
			for ( var j = 0; j < laUUID.length; j++) {
				if (laValue[0] == laUUID[j]) {
					laUUID[j] = "";
					break;
				}
			}
		}
	}
	loNode = goWorkFlow.flowXML.selectSingleNode("deletes");
	for ( var j = 0; j < laUUID.length; j++) {
		if (laUUID[j] != "") {
			oAddElement(loNode, "record", laUUID[j]);
		}
	}
	taskProperty.setAttribute("name", goForm.name.value);
	taskProperty.setAttribute("id", goForm.id.value);
	taskProperty.setAttribute("code", goForm.sn.value);
	var laField = [ "user", "copyUser", "monitor" ];
	for ( var i = 0; i < laField.length; i++) {
		bSetUnitFieldToXML(taskProperty, goForm, laField[i]);
	}
	laField = [ "isSetUser", "isSetCopyUser", "isSetUserEmpty", "emptyToTask", "emptyNoteDone",
			"isSelectAgain", "isOnlyOne", "isAnyone", "isByOrder", "sameUserSubmit", "isSetMonitor",
			"isInheritMonitor", "untreadType", "snName", "serialNo", "printTemplate", "printTemplateId",
			"listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		oSetElement(taskProperty, laField[i], sGetFormFieldValue(goForm, laField[i]));
	}
	// 运转模式
	var forkMode = $("input[name=forkMode]:checked", goForm).val();
	var joinMode = $("input[name=joinMode]:checked", goForm).val();
	var parallelGateway = taskProperty.selectSingleNode("parallelGateway");
	if (parallelGateway != null) {
		taskProperty.removeChild(parallelGateway);
	}
	parallelGateway = oSetElement(taskProperty, "parallelGateway");
	var forkModeEle = oAddElement(parallelGateway, "forkMode");
	oAddElement(forkModeEle, "value", forkMode);
	var forkModeEle = oAddElement(parallelGateway, "joinMode");
	oAddElement(forkModeEle, "value", joinMode);
	// $.dialog.data("goProperty", taskProperty);
	// window.returnValue = goProperty;
	// window.close();
}
function SubFlowLoadEvent(loTask, subFlowProperty) {
	var goForm = $("#subFlowForm")[0];
	if(goForm == null){
		return;
	}
	goForm.name.value = subFlowProperty.getAttribute("name") == null ? "" : subFlowProperty
			.getAttribute("name");
	goForm.id.value = subFlowProperty.getAttribute("id") == null ? "" : subFlowProperty.getAttribute("id");
	var loNode = subFlowProperty.selectSingleNode("newFlows");
	if (loNode != null) {
		var laNode = loNode.selectNodes("newFlow");
		if (laNode != null) {
			for ( var i = 0; i < laNode.length; i++) {
				var newFlow = {};
				newFlow.name = $(laNode[i]).selectSingleNode("name").text();
				newFlow.value = $(laNode[i]).selectSingleNode("value").text();
				newFlow.conditionName = $(laNode[i]).selectSingleNode("conditionName").text();
				newFlow.conditionValue = $(laNode[i]).selectSingleNode("conditionValue").text();
				newFlow.createName = $(laNode[i]).selectSingleNode("createName") == null ? "" : $(laNode[i])
						.selectSingleNode("createName").text();
				newFlow.createValue = $(laNode[i]).selectSingleNode("createValue") == null ? ""
						: $(laNode[i]).selectSingleNode("createValue").text();
				newFlow.isMerge = $(laNode[i]).selectSingleNode("isMerge").text();
				newFlow.isWait = $(laNode[i]).selectSingleNode("isWait").text();
				newFlow.isShare = $(laNode[i]).selectSingleNode("isShare").text();
				newFlow.toTaskName = $(laNode[i]).selectSingleNode("toTaskName").text();
				newFlow.toTaskId = $(laNode[i]).selectSingleNode("toTaskId").text();
				newFlow.copyFieldNames = $(laNode[i]).selectSingleNode("copyFieldNames") == null ? "" : $(
						laNode[i]).selectSingleNode("copyFieldNames").text();
				newFlow.copyFields = $(laNode[i]).selectSingleNode("copyFields").text();
				newFlow.returnOverrideFieldNames = $(laNode[i]).selectSingleNode("returnOverrideFieldNames") == null ? ""
						: $(laNode[i]).selectSingleNode("returnOverrideFieldNames").text();
				newFlow.returnOverrideFields = $(laNode[i]).selectSingleNode("returnOverrideFields").text();
				newFlow.returnAdditionFieldNames = $(laNode[i]).selectSingleNode("returnAdditionFieldNames") == null ? ""
						: $(laNode[i]).selectSingleNode("returnAdditionFieldNames").text();
				newFlow.returnAdditionFields = $(laNode[i]).selectSingleNode("returnAdditionFields").text();
				newFlow.notifyDoing = $(laNode[i]).selectSingleNode("notifyDoing") == null ? ""
						: $(laNode[i]).selectSingleNode("notifyDoing").text();
				
				/*modified by huanglinchuan 2014.10.23 begin*/
				newFlow.createWay=$(laNode[i]).selectSingleNode("createWay")?$(laNode[i]).selectSingleNode("createWay").text():"";
				newFlow.createWayName=$(laNode[i]).selectSingleNode("createWayName")?$(laNode[i]).selectSingleNode("createWayName").text():"";
				newFlow.createInstanceWay=$(laNode[i]).selectSingleNode("createInstanceWay")?$(laNode[i]).selectSingleNode("createInstanceWay").text():"";
				newFlow.createInstanceWayName=$(laNode[i]).selectSingleNode("createInstanceWayName")?$(laNode[i]).selectSingleNode("createInstanceWayName").text():"";
				newFlow.subformId=$(laNode[i]).selectSingleNode("subformId")?$(laNode[i]).selectSingleNode("subformId").text():"";
				newFlow.subformIdName=$(laNode[i]).selectSingleNode("subformIdName")?$(laNode[i]).selectSingleNode("subformIdName").text():"";
				newFlow.taskUsers=$(laNode[i]).selectSingleNode("taskUsers")?$(laNode[i]).selectSingleNode("taskUsers").text():"";
				newFlow.taskUsersName=$(laNode[i]).selectSingleNode("taskUsersName")?$(laNode[i]).selectSingleNode("taskUsersName").text():"";
				
				var createWayDesc="";
				if(newFlow.createWay=="1"){
					createWayDesc=newFlow.createInstanceWayName+","+newFlow.taskUsersName;
				}else if(newFlow.createWay=="2"){
					createWayDesc=newFlow.subformIdName+","+newFlow.taskUsersName;
				}else{
					
				}
				var laName = newFlow.name + "|" + newFlow.createWayName + "(" + createWayDesc + ")|" + newFlow.toTaskName;
				
				/*modified by huanglinchuan 2014.10.23 end*/
				var $option = $("<option>" + laName + "</option>");
				$option.data("value", newFlow);
				$("#DNewFlows", goForm).append($option);
				// bAddEntry(goForm.DNewFlows, laName, name + "|" + value + "|"
				// + conditionName + "|"
				// + conditionValue + "|" + createName + "|" + createValue + "|"
				// +
				// isMerge + "|" + isWait
				// + "|" + isShare + "|" + toTaskName + "|" + toTaskId + "|" +
				// copyFields + "|"
				// + returnOverrideFields + "|" + returnAdditionFields + "|" +
				// notifyDoing, true);
			}
		}
	}
	// 流程前后置关系
	loNode = subFlowProperty.selectSingleNode("relations");
	if (loNode != null) {
		var laNode = loNode.selectNodes("relation");
		if (laNode != null) {
			for ( var i = 0; i < laNode.length; i++) {
				var relation = {};
				relation.newFlowName = $(laNode[i]).selectSingleNode("newFlowName").text();
				relation.newFlowId = $(laNode[i]).selectSingleNode("newFlowId").text();
				relation.taskName = $(laNode[i]).selectSingleNode("taskName").text();
				relation.taskId = $(laNode[i]).selectSingleNode("taskId").text();
				relation.frontNewFlowName = $(laNode[i]).selectSingleNode("frontNewFlowName").text();
				relation.frontNewFlowId = $(laNode[i]).selectSingleNode("frontNewFlowId").text();
				relation.frontTaskName = $(laNode[i]).selectSingleNode("frontTaskName").text();
				relation.frontTaskId = $(laNode[i]).selectSingleNode("frontTaskId").text();
				var laName = relation.newFlowName + " | " + relation.taskName + " | "
						+ relation.frontNewFlowName + " | " + relation.frontTaskName;
				// var laValue = newFlowName + "|" + newFlowId + "|" + taskName
				// + "|" + taskId + "|"
				// + frontNewFlowName + "|" + frontNewFlowId + "|" +
				// frontTaskName + "|" + frontTaskId;
				// bAddEntry(goForm.DRelations, laName, laValue);
				var $option = $("<option>" + laName + "</option>");
				$option.data("value", relation);
				$("#DRelations", goForm).append($option);
			}
		}
	}
	bSetVersionInfo(goWorkFlow.flowXML.getAttribute("verType"), window);
	// bInitPageLabel();
}
function SubFlowLoadInitEvent(loTask, subFlowProperty) {
	var goForm = $("#subFlowForm")[0];
	if(goForm == null){
		return;
	}
	$(".tabs", goForm).tabs();
	$("#btn_save", goForm).button().click(function() {
		SubFlowOKEvent(loTask, subFlowProperty);
		if (loTask.xmlObject != null) {
			bSetObjectText(loTask);
		}
		// alert("设置成功!");
	});
	$("#btn_abandon", goForm).button().click(function(e) {
		saveSetting = false;
		// $(loTask).trigger("dblclick");
		setTaskProperty(loTask, window.eWorkFlow.contentWindow, goWorkFlow);
	});

	// 增加子流程
	$("#newFlowAdd", goForm).click(
			function(e) {
				var reValCallback = function(lsReturn) {
					if (lsReturn == null) {
						return;
					}
					// var laValue = lsReturn.name + "|" + lsReturn.value + "|"
					// + lsReturn.conditionName + "|"
					// + lsReturn.conditionValue + "|" + lsReturn.createName +
					// "|" + lsReturn.createValue
					// + "|" + lsReturn.isMerge + "|" + lsReturn.isWait + "|" +
					// lsReturn.isShare + "|"
					// + lsReturn.toTaskName + "|" + lsReturn.toTaskId + "|" +
					// lsReturn.copyFields + "|"
					// + lsReturn.returnOverrideFields + "|" +
					// lsReturn.returnAdditionFields + "|"
					// + lsReturn.notifyDoing;
					
					/*modified by huanglinchuan 2014.10.23 begin*/
					var createWayDesc="";
					if(lsReturn.createWay=="1"){
						createWayDesc=lsReturn.createInstanceWayName+","+lsReturn.taskUsersName;
					}else if(lsReturn.createWay=="2"){
						createWayDesc=lsReturn.subformIdName+","+lsReturn.taskUsersName;
					}else{
						
					}
					var laName = lsReturn.name + "|" + lsReturn.createWayName + "(" + createWayDesc + ")|" + lsReturn.toTaskName;
					/*modified by huanglinchuan 2014.10.23 end*/
					var $option = $("<option>" + laName + "</option>");
					$option.data("value", lsReturn);
					$("#DNewFlows", goForm).append($option);
					// bAddEntry(goForm.DNewFlows, laName, laValue);
				};
				sGetNewFlow({}, reValCallback);
			});
	// 编辑子流程
	$("#DNewFlows").dblclick(function(e) {
		$("#newFlowEdit", goForm).trigger("click");
	});
	$("#newFlowEdit", goForm).click(
			function(e) {
				// var lsValue = sGetAllEntryValue(goForm.DNewFlows, true);
				var $option = $("#DNewFlows > option:selected", goForm);
				if ($option.length == 0) {
					return false;
				}
				// var lsValues = lsValue.split("|");
				// 转化为对象参数
				var lsArgs = $option.data("value") || {};
				// lsArgs.name = lsValues[0];
				// lsArgs.value = lsValues[1];
				// lsArgs.conditionName = lsValues[2];
				// lsArgs.conditionValue = lsValues[3];
				// lsArgs.createName = lsValues[4];
				// lsArgs.createValue = lsValues[5];
				// lsArgs.isMerge = lsValues[6];
				// lsArgs.isWait = lsValues[7];
				// lsArgs.isShare = lsValues[8];
				// lsArgs.toTaskName = lsValues[9];
				// lsArgs.toTaskId = lsValues[10];
				// lsArgs.copyFields = lsValues[11];
				// lsArgs.returnOverrideFields = lsValues[12];
				// lsArgs.returnAdditionFields = lsValues[13];
				// lsArgs.notifyDoing = lsValues[14];
				var reValCallback = function(lsReturn) {
					if (lsReturn == null) {
						return;
					}
					// var laValue = lsReturn.name + "|" + lsReturn.value + "|"
					// + lsReturn.conditionName + "|"
					// + lsReturn.conditionValue + "|" + lsReturn.createName +
					// "|" + lsReturn.createValue
					// + "|" + lsReturn.isMerge + "|" + lsReturn.isWait + "|" +
					// lsReturn.isShare + "|"
					// + lsReturn.toTaskName + "|" + lsReturn.toTaskId + "|" +
					// lsReturn.copyFields + "|"
					// + lsReturn.returnOverrideFields + "|" +
					// lsReturn.returnAdditionFields + "|"
					// + lsReturn.notifyDoing;
					/*modified by huanglinchuan 2014.10.23 begin*/
					var createWayDesc="";
					if(lsReturn.createWay=="1"){
						createWayDesc=lsReturn.createInstanceWayName+","+lsReturn.taskUsersName;
					}else if(lsReturn.createWay=="2"){
						createWayDesc=lsReturn.subformIdName+","+lsReturn.taskUsersName;
					}else{
						
					}
					var laName = lsReturn.name + "|" + lsReturn.createWayName + "(" + createWayDesc + ")|" + lsReturn.toTaskName;
					// bEditEntry(goForm.DNewFlows, laName, laValue);
					$option.text(laName);
					$option.data("value", lsReturn);
				};
				sGetNewFlow(lsArgs, reValCallback);
			});
	// 删除子流程
	$("#newFlowDelete", goForm).click(function(e) {
		bMoveEntry(goForm.DNewFlows);
	});

	// 前后置关系
	$("#relationAdd", goForm).click(
			function(e) {
				var newFlows = getNewFlows(goForm);
				var reValCallback = function(lsReturn) {
					if (lsReturn == null) {
						return;
					}
					var laName = lsReturn.newFlowName + "  |  " + lsReturn.taskName + "  |  "
							+ lsReturn.frontNewFlowName + "  |  " + lsReturn.frontTaskName;
					// var laValue = newFlowName + "|" + newFlowId + "|" +
					// taskName + "|" + taskId + "|"
					// + frontNewFlowName + "|" + frontNewFlowId + "|" +
					// frontTaskName + "|"
					// + frontTaskId;
					// bAddEntry(goForm.DRelations, laName, laValue);
					var $option = $("<option>" + laName + "</option>");
					$option.data("value", lsReturn);
					$("#DRelations", goForm).append($option);
				};
				sGetRelation(newFlows, {}, reValCallback);
			});
	// 编辑前后置关系
	$("#DRelations", goForm).dblclick(function(e) {
		$("#relationEdit", goForm).trigger("click");
	});
	$("#relationEdit", goForm).click(
			function(e) {
				var newFlows = getNewFlows(goForm);
				// var lsValue = sGetAllEntryValue(goForm.DRelations, true);
				var $option = $("#DRelations > option:selected", goForm);
				if ($option.length == 0) {
					return false;
				}
				// var lsValues = lsValue.split("|");
				// 转化为对象参数
				var relation = $option.data("value") || {};
				// relation.newFlowName = lsValues[0];
				// relation.newFlowId = lsValues[1];
				// relation.taskName = lsValues[2];
				// relation.taskId = lsValues[3];
				// relation.frontNewFlowName = lsValues[4];
				// relation.frontNewFlowId = lsValues[5];
				// relation.frontTaskName = lsValues[6];
				// relation.frontTaskId = lsValues[7];
				var reValCallback = function(lsReturn) {
					if (lsReturn == null) {
						return;
					}
					// var newFlowName = lsReturn.newFlowName;
					// var newFlowId = lsReturn.newFlowId;
					// var taskName = lsReturn.taskName;
					// var taskId = lsReturn.taskId;
					// var frontNewFlowName = lsReturn.frontNewFlowName;
					// var frontNewFlowId = lsReturn.frontNewFlowId;
					// var frontTaskName = lsReturn.frontTaskName;
					// var frontTaskId = lsReturn.frontTaskId;
					var laName = lsReturn.newFlowName + "  |  " + lsReturn.taskName + "  |  "
							+ lsReturn.frontNewFlowName + "  |  " + lsReturn.frontTaskName;
					// var laValue = newFlowName + "|" + newFlowId + "|" +
					// taskName + "|" + taskId + "|"
					// + frontNewFlowName + "|" + frontNewFlowId + "|" +
					// frontTaskName + "|"
					// + frontTaskId;
					// bEditEntry(goForm.DRelations, laName, laValue);
					$option.text(laName);
					$option.data("value", lsReturn);
				};
				sGetRelation(newFlows, relation, reValCallback);
			});
	$("#relationDelete", goForm).click(function(e) {
		bMoveEntry(goForm.DRelations);
	});
}
function getNewFlows(goForm) {
	var newFlows = [];
	for ( var i = 0; i < goForm.DNewFlows.options.length; i++) {
		var $option = $(goForm.DNewFlows.options[i]);
		var newFlow = $option.data("value");
		newFlow.name = newFlow.name;
		newFlow.id = newFlow.value;
		newFlows.push(newFlow);
	}
	return newFlows;
}
function sGetNewFlow(psValue, reValCallback) {
	// var laArg = new Array();
	// laArg.value = (psValue == null) ? "" : psValue;
	// laArg.opener = opener;
	// return vOpenModal_("remark.htm", laArg, 400, 180);
	// openDialog("flow/select.htm", "新流程", liWidth, liHeight, "newFlowLaArg",
	// laArg,
	// reValCallback);
	$.get(ctx + "/workflow/flow/select2.htm", function(data) {
		$("#dlg_new_flow").dialog({
			title : "新流程",
			autoOpen : true,
			resizable : false,
			width : 500,
			height : 550,
			overlay : false,
			modal : true,
			close : function() {
				$("#dlg_new_flow").dialog("destroy");
				$("#dlg_new_flow").html("<div/>");
			},
			open : function() {
				$("#dlg_new_flow").html(data);
				NewFlowLoadEvent(psValue);
			},
			buttons : {
				"确定" : function(e) {
					var value = $("#newFlowValue", "#newFlowForm").val();
					if (value == null || value == "") {
						alert("请选择流程!");
						return;
					}
					var returnValue = NewFlowOKEvent();
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function sGetRelation(newFlows, psValue, reValCallback) {
	$.get(ctx + "/workflow/flow/relation.htm", function(data) {
		var dialogSelector = "#dlg_new_relation";
		$(dialogSelector).dialog({
			title : "前置流程关系",
			autoOpen : true,
			resizable : false,
			width : 500,
			height : 440,
			overlay : false,
			modal : true,
			close : function() {
				$(dialogSelector).dialog("destroy");
				$(dialogSelector).html("<div/>");
			},
			open : function() {
				$(dialogSelector).html(data);
				NewRelationLoadEvent(newFlows, psValue);
				NewRelationInitEvent(newFlows, psValue);
			},
			buttons : {
				"确定" : function(e) {
					var value = $("#frontNewFlowName", "#relationForm").val();
					if (value == null || value == "" || value == "0") {
						alert("请选择前置流程!");
						return;
					}
					var retVal = NewRelationOKEvent();
					reValCallback(retVal);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
// 设置前置流程
function setFrontNewFlow(goForm, newFlows) {
	$("#frontNewFlowName", goForm).find("option[value!=0]").remove();
	$.each(newFlows, function(i) {
		var newFlowId = $("#newFlowName", goForm).val();
		if (this.id === newFlowId) {
			return;
		}
		var option = "<option value='" + this.id + "'>" + this.name + "</option>";
		$("#frontNewFlowName", goForm).append(option);
	});
}
function NewRelationLoadEvent(newFlows, psValue) {
	var goForm = $("#relationForm")[0];
	$.each(newFlows, function(i) {
		var option = "<option value='" + this.id + "'>" + this.name + "</option>";
		$("#newFlowName", goForm).append(option);
	});
	$("#newFlowName", goForm).val(psValue.newFlowId);
	// 设置前置流程
	setFrontNewFlow(goForm, newFlows);
	$("#frontNewFlowName", goForm).val(psValue.frontNewFlowId);

	// 子流程环节
	var flowId = psValue.newFlowId;
	if (flowId == null || flowId == "") {
		flowId = $("#newFlowName", goForm).val();
	}
	initTask(goForm, "#taskName", flowId, false, false, false);
	$("#taskName", goForm).val(psValue.taskId);

	// 前置子流程环节
	flowId = psValue.frontNewFlowId;
	if (flowId == null || flowId == "") {
		flowId = $("#frontNewFlowName", goForm).val();
	}
	initTask(goForm, "#frontTaskName", flowId, false, false, false);
	$("#frontTaskName", goForm).val(psValue.frontTaskId);
}
function NewRelationInitEvent(newFlows, psValue) {
	var goForm = $("#relationForm")[0];
	$("#newFlowName", goForm).change(function(e) {
		var flowId = $(this).val();
		initTask(goForm, "#taskName", flowId, false, false, false);

		// 设置前置流程
		setFrontNewFlow(goForm, newFlows);
		$("#frontNewFlowName", goForm).trigger("change");
	});

	$("#frontNewFlowName", goForm).change(function(e) {
		var flowId = $(this).val();
		initTask(goForm, "#frontTaskName", flowId, false, false, false);
	});
}
function NewRelationOKEvent() {
	var goForm = $("#relationForm")[0];
	var relation = {};
	relation.newFlowName = $("#newFlowName > option:selected", goForm).text();
	relation.newFlowId = $("#newFlowName", goForm).val();
	relation.taskName = $("#taskName > option:selected", goForm).text();
	relation.taskId = $("#taskName", goForm).val();
	relation.frontNewFlowName = $("#frontNewFlowName > option:selected", goForm).text();
	relation.frontNewFlowId = $("#frontNewFlowName", goForm).val();
	relation.frontTaskName = $("#frontTaskName > option:selected", goForm).text();
	relation.frontTaskId = $("#frontTaskName", goForm).val();
	return relation;
}
function NewFlowLoadEvent(gsValue) {
	var goForm = $("#newFlowForm")[0];
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";

	$("#newFlowName", goForm).val(gsValue.name);
	$("#newFlowValue", goForm).val(gsValue.value);
	$("#conditionName", goForm).val(gsValue.conditionName);
	$("#conditionValue", goForm).val(gsValue.conditionValue);
	$("#createName", goForm).val(gsValue.createName);
	$("#createValue", goForm).val(gsValue.createValue);
	$("#createWay", goForm).val(gsValue.createWay);
	$("#createInstanceWay", goForm).val(gsValue.createInstanceWay);
	$("#subformId", goForm).val(gsValue.subformId);
	$("#title", goForm).val(gsValue.title);
	$("#taskUsers", goForm).val(gsValue.taskUsers);
	$("#isMerge", goForm)[0].checked = (gsValue.isMerge == "1" ? true : false);
	$("#isWait", goForm)[0].checked = (gsValue.isWait == "1" ? true : false);
	$("#isShare", goForm)[0].checked = (gsValue.isShare == "1" ? true : false);
	$("#copyFieldNames", goForm).val(gsValue.copyFieldNames);
	$("#copyFields", goForm).val(gsValue.copyFields);
	$("#returnOverrideFieldNames", goForm).val(gsValue.returnOverrideFieldNames);
	$("#returnOverrideFields", goForm).val(gsValue.returnOverrideFields);
	$("#returnAdditionFieldNames", goForm).val(gsValue.returnAdditionFieldNames);
	$("#returnAdditionFields", goForm).val(gsValue.returnAdditionFields);
	$("#notifyDoing", goForm)[0].checked = (gsValue.notifyDoing == "1" ? true : false);
	if (gsValue.value != null && gsValue.value != "") {
		initToTask(gsValue.value, false);
		// $("#toTaskName").text();
		$("#toTaskName", goForm).val(gsValue.toTaskId);
	}

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
			enable : true,
			chkStyle : "radio"
		},
		callback : {
			onClick : function zTreeOnCheck(event, treeId, treeNode) {
				doCheck(event, treeId, treeNode);
			},
			onCheck : function(event, treeId, treeNode) {
				doCheck(event, treeId, treeNode);
			}
		}
	};
	function doCheck(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var checkNodes = zTree.getCheckedNodes(true);
		$.each(checkNodes, function() {
			zTree.checkNode(this, false);
		});
		zTree.checkNode(treeNode, true);
		if (!treeNode.isParent) {
			$("#newFlowName", goForm).val(treeNode.name);
			$("#newFlowValue", goForm).val(treeNode.data);
			initToTask(treeNode.data, true);
		} else {
			$("#newFlowName", goForm).val("");
			$("#newFlowValue", goForm).val("");
		}
	}
	// 流程名称
	$("#newFlowName", goForm).comboTree({
		labelField : "newFlowName",
		valueField : "newFlowValue",
		autoInitValue : false,
		treeSetting : setting,
		width : 360,
		height : 230
	});

	// 创建实例的字段
	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		success : function(result) {
			onGetFormFields("#createName", goForm, result.data);
			$("#createName").val($("#createValue").val());
		}
	});
	$("#createName").change(function() {
		$("#createValue").val($(this).val());
	});

	// 获取主表字段函数
	var getFormFields = function(formUuid) {
		var fields = [];
		JDS.call({
			service : "flowSchemeService.getFormFields",
			data : [ -1, formUuid ],
			async : false,
			success : function(result) {
				fields = result.data;
			}
		});
		return fields;
	};
	
	// 获取主表字段函数
	var getSubForms = function(formUuid) {
		var fields = [];
		JDS.call({
			service : "flowSchemeService.getSubForms",
			data : [ -1, formUuid ],
			async : false,
			success : function(result) {
				fields = result.data;
			}
		});
		return fields;
	};

	// 创建方式更改处理
	$("#createWay", goForm).change(function() {
		if ($(this).val() == 1) {
			$("#span_createInstanceWay", goForm).show();
			$("#span_subformId", goForm).hide();
			$("#span_title_users", goForm).show();
			$("#span_interface", goForm).hide();
			var fields = getFormFields(formUuid);
			onGetFormFields("#title", goForm, fields);
			onGetFormFields("#taskUsers", goForm, fields);
			$("#taskUsers", goForm).val(gsValue.taskUsers);
		} else if ($(this).val() == 2) {
			$("#span_createInstanceWay", goForm).hide();
			$("#span_subformId", goForm).show();
			$("#span_title_users", goForm).show();
			$("#span_interface", goForm).hide();
			var subForms = getSubForms(formUuid);
			/*modified by huanglinchuan 2014.10.23 begin*/			
			onGetFormFields("#subformId", goForm, subForms);
			if(subForms.length>0){
				var fields = getFormFields(subForms[0].id);
				onGetFormFields("#taskUsers", goForm, fields);
				$("#taskUsers", goForm).val(gsValue.taskUsers);
			}
			/*modified by huanglinchuan 2014.10.23 end*/
		} else if ($(this).val() == 3) {
			$("#span_createInstanceWay", goForm).hide();
			$("#span_subformId", goForm).hide();
			$("#span_title_users", goForm).hide();
			$("#span_interface", goForm).show();
		}
	}).trigger("change");

	// 拷贝信息
	// var copyFieldsSetting = {
	// view : {
	// showIcon : false,
	// showLine : false
	// },
	// async : {
	// otherParam : {
	// "serviceName" : "flowSchemeService",
	// "methodName" : "getFormFields",
	// "data" : formUuid
	// }
	// }
	// };
	$("#copyFieldNames", goForm).click(function() {
		newFlowSelectField("copyFieldNames", "copyFields");
	});
	// $("#copyFieldNames").comboTree({
	// labelField : "copyFieldNames",
	// valueField : "copyFields",
	// initService : "flowSchemeService.getFormFieldByFieldNames",
	// initServiceParam : [ formUuid ],
	// treeSetting : copyFieldsSetting,
	// width : 360,
	// height : 230
	// });
	// 返回信息覆盖
	$("#returnOverrideFieldNames", goForm).click(function() {
		newFlowSelectField("returnOverrideFieldNames", "returnOverrideFields");
	});
	// $("#returnOverrideFieldNames").comboTree({
	// labelField : "returnOverrideFieldNames",
	// valueField : "returnOverrideFields",
	// initService : "flowSchemeService.getFormFieldByFieldNames",
	// initServiceParam : [ formUuid ],
	// treeSetting : copyFieldsSetting,
	// width : 360,
	// height : 230
	// });
	// 返回信息附加
	$("#returnAdditionFieldNames", goForm).click(function() {
		newFlowSelectField("returnAdditionFieldNames", "returnAdditionFields");
	});
	// $("#returnAdditionFieldNames").comboTree({
	// labelField : "returnAdditionFieldNames",
	// valueField : "returnAdditionFields",
	// initService : "flowSchemeService.getFormFieldByFieldNames",
	// initServiceParam : [ formUuid ],
	// treeSetting : copyFieldsSetting,
	// width : 360,
	// height : 230
	// });

	// 如果选择合并，显示是否等待
	if ($("#isMerge", goForm)[0].checked) {
		$("#span_isWait").show();
	} else {
		$("#isWait", goForm)[0].checked = false;
		$("#span_isWait", goForm).hide();
	}
	$("#isMerge", goForm).change(function(e) {
		if ($("#isMerge", goForm)[0].checked) {
			$("#span_isWait", goForm).show();
		} else {
			$("#span_isWait", goForm).hide();
		}
	});
}
function newFlowSelectField(labelField, valueField) {
	var goForm = $("#newFlowForm")[0];
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";
	var newFlowId = $("#newFlowValue", goForm).val();
	if (formUuid == null || formUuid == "") {
		alert("请设置流程表单!");
		return;
	}
	if (newFlowId == null || newFlowId == "") {
		alert("请设置子流程!");
		return;
	}
	var initNames = $("#" + labelField).val();
	var initValues = $("#" + valueField).val();
	var psValue = {
		formUuid : formUuid,
		newFlowId : newFlowId,
		initNames : initNames,
		initValues : initValues
	};
	
	/*modified by huanglinchuan 2014.10.24 begin*/
	var direction="parent2child",directionSplitStr=">>";
	if(labelField=="copyFieldNames"){
		direction="parent2child";
		directionSplitStr=">>";
	}else if(labelField=="returnOverrideFieldNames"||labelField=="returnAdditionFieldNames"){
		direction="child2parent";
		directionSplitStr="<<";
	}
	psValue.direction=direction;
	psValue.directionSplitStr=directionSplitStr;
	/*modified by huanglinchuan 2014.10.24 end*/

	$.get(ctx + "/workflow/field/select.htm", function(data) {
		$("#dlg_select_field").dialog({
			title : "字段设置",
			autoOpen : true,
			resizable : false,
			width : 500,
			height : 450,
			overlay : false,
			modal : true,
			close : function() {
				$("#dlg_select_field").dialog("destroy");
				$("#dlg_select_field").html("<div/>");
			},
			open : function() {
				$("#dlg_select_field").html(data);
				FieldSelectInitEvent(psValue);
			},
			buttons : {
				"确定" : function(e) {
					var returnValue = FieldSelectOKEvent();
					if (returnValue != null) {
						$("#" + labelField, goForm).val(returnValue.name);
						$("#" + valueField, goForm).val(returnValue.value);
					} else {
						$("#" + labelField, goForm).val("");
						$("#" + valueField, goForm).val("");
					}
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function FieldSelectInitEvent(psValue) {
	var goForm = $("#fieldSelectForm")[0];
	var formUuid = psValue.formUuid;
	var flowDefId = psValue.newFlowId;
	// 主流程表单字段
	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		async : false,
		success : function(result) {
			var data = result.data;
			onGetFormFields("select[name=fields1]", goForm, data);
		}
	});
	// 子流程表单字段
	JDS.call({
		service : "flowSchemeService.getFormFieldsByFlowDefId",
		data : [ flowDefId ],
		async : false,
		success : function(result) {
			var data = result.data;
			onGetFormFields("select[name=fields2]", goForm, data);
		}
	});
	var initNames = psValue.initNames;
	var initValues = psValue.initValues;
	if (initNames != null && initNames != "" && initValues != null && initValues != "") {
		/*modified by huanglinchuan 2014.10.23 begin*/
		var names = initNames.split("\n");
		/*modified by huanglinchuan 2014.10.23 end*/
		var values = initValues.split(";");
		if (names.length === values.length) {
			var data = [];
			for ( var i = 0; i < names.length; i++) {
				data.push({
					name : names[i],
					data : values[i]
				});
			}
			onGetFormFields("select[name=selectFields]", goForm, data);
			var $options = $("#selectFields > option");
			$.each($options, function(i) {
				$(this).click(function(e) {
					var value = $(this).val();
					/*modified by huanglinchuan 2014.10.23 begin*/
					if (value != null && value.indexOf(psValue.directionSplitStr) != -1) {
						var values = value.split(psValue.directionSplitStr);
						$("#fields1", goForm).val(values[0]);
						$("#fields2", goForm).val(values[1]);
					}
					/*modified by huanglinchuan 2014.10.23 end*/
				});
			});
		}
	}
	// fieldAdd
	$("#fieldAdd", goForm).click(function() {
		var fields1Name = $("#fields1 > option:selected", goForm).text();
		var fields1Value = $("#fields1", goForm).val();
		var fields2Name = $("#fields2 > option:selected", goForm).text();
		var fields2Value = $("#fields2", goForm).val();
		/*modified by huanglinchuan 2014.10.23 begin*/
		var name = fields1Name + psValue.directionSplitStr + fields2Name;
		var value = fields1Value + psValue.directionSplitStr + fields2Value;
		if (fields1Value == "-1") {
			alert("请选择主流程表单字段!");
			return;
		}
		if (fields2Value == "-1") {
			alert("请选择子流程表单字段!");
			return;
		}
		var $option = $('<option value="' + value + '">' + name + '</option>');
		$("#selectFields", goForm).append($option);
		$option.click(function(e) {
			var value = $(this).val();
			if (value != null && value.indexOf(psValue.directionSplitStr) != -1) {
				var values = value.split(psValue.directionSplitStr);
				$("#fields1", goForm).val(values[0]);
				$("#fields2", goForm).val(values[1]);
			}
		});
		/*modified by huanglinchuan 2014.10.23 end*/
	});
	// selectFieldDelete
	$("#selectFieldDelete", goForm).click(function() {
		var $selectFields = $("#selectFields > option:selected", goForm);
		if ($selectFields.length == 0) {
			alert("请选择记录!");
		} else {
			$selectFields.remove();
		}
	});
}
function FieldSelectOKEvent() {
	var goForm = $("#fieldSelectForm")[0];
	var $options = $("#selectFields > option", goForm);
	var retVal = {};
	var names = [];
	var values = [];
	$.each($options, function(i) {
		names.push($(this).text());
		values.push($(this).val());
	});
	retVal.name = names.join("\n");
	retVal.value = values.join(";");
	return retVal;
}
function NewFlowOKEvent() {
	var goForm = $("#newFlowForm")[0];
	var newFlow = {};
	newFlow.name = $("#newFlowName", goForm).val();
	newFlow.value = $("#newFlowValue", goForm).val();
	newFlow.conditionName = $("#conditionName", goForm).val();
	newFlow.conditionValue = newFlow.conditionName;
	newFlow.createName = $("#createName > option:selected", goForm).text();
	newFlow.createValue = $("#createValue", goForm).val();
	/*modified by huanglinchuan 2014.10.23 begin*/
	newFlow.createWay=$("#createWay",goForm).val();
	newFlow.createWayName=$("#createWay > option:selected",goForm).text();
	newFlow.createInstanceWay=$("#createInstanceWay",goForm).val();
	newFlow.createInstanceWayName=$("#createInstanceWay > option:selected",goForm).text();
	newFlow.subformId=$("#subformId",goForm).val();
	newFlow.subformIdName=$("#subformId > option:selected",goForm).text();
	newFlow.taskUsers=$("#taskUsers",goForm).val();
	newFlow.taskUsersName=$("#taskUsers > option:selected",goForm).text();
	/*modified by huanglinchuan 2014.10.23 end*/
	newFlow.isMerge = $("#isMerge", goForm)[0].checked == true ? "1" : "0";
	newFlow.isWait = $("#isWait", goForm)[0].checked == true ? "1" : "0";
	newFlow.isShare = $("#isShare", goForm)[0].checked == true ? "1" : "0";
	newFlow.toTaskName = $("#toTaskName > option:selected", goForm).text();
	newFlow.toTaskId = $("#toTaskName", goForm).val();
	newFlow.copyFieldNames = $("#copyFieldNames", goForm).val();
	newFlow.copyFields = $("#copyFields", goForm).val();
	newFlow.returnOverrideFieldNames = $("#returnOverrideFieldNames", goForm).val();
	newFlow.returnOverrideFields = $("#returnOverrideFields", goForm).val();
	newFlow.returnAdditionFieldNames = $("#returnAdditionFieldNames", goForm).val();
	newFlow.returnAdditionFields = $("#returnAdditionFields", goForm).val();
	newFlow.notifyDoing = $("#notifyDoing", goForm).val();
	return newFlow;
}
// 初始化环节
function initTask(form, selector, flowDefId, async, draft, autoSubmit, excludeValue) {
	if (flowDefId == null || flowDefId == "") {
		$(selector).html("");
		return;
	}
	var goForm = $(form)[0];
	$(selector, goForm).html("");
	JDS.call({
		service : "flowSchemeService.getFlowTasks",
		data : [ flowDefId ],
		async : async,
		success : function(result) {
			var data = result.data;
			if (draft) {
				var opt1 = "<option value='DRAFT'>草搞</option>";
				$(selector, goForm).append(opt1);
			}
			if (autoSubmit) {
				var opt2 = "<option value='AUTO_SUBMIT'>自动提交</option>";
				$(selector, goForm).append(opt2);
			}
			$.each(data, function(index) {
				var id = this.id;
				var name = this.name;
				if (excludeValue === id) {
					return;
				}
				var option = "<option value='" + id + "'>" + name + "</option>";
				$(selector, goForm).append(option);
			});
		}
	});
}
// 初始化提交环节
function initToTask(flowDefId, async) {
	initTask("#newFlowForm", "#toTaskName", flowDefId, async, true, true);
}
function SubFlowOKEvent(loTask, subFlowProperty) {
	var goForm = $("#subFlowForm")[0];
	subFlowProperty.setAttribute("name", goForm.name.value);
	subFlowProperty.setAttribute("id", goForm.id.value);
	loNode = subFlowProperty.selectSingleNode("newFlows");
	if (loNode != null) {
		subFlowProperty.removeChild(loNode);
	}
	loNode = oSetElement(subFlowProperty, "newFlows");
	for ( var i = 0; i < goForm.DNewFlows.options.length; i++) {
		var laValue = $(goForm.DNewFlows.options[i]).data("value"); // goForm.DNewFlows.options[i].value.split("|");
		var loNewFlow = oAddElement(loNode, "newFlow");
		oAddElement(loNewFlow, "name", laValue.name);
		oAddElement(loNewFlow, "value", laValue.value);
		oAddElement(loNewFlow, "conditionName", laValue.conditionName);
		oAddElement(loNewFlow, "conditionValue", laValue.conditionValue);
		oAddElement(loNewFlow, "createName", laValue.createName);
		oAddElement(loNewFlow, "createValue", laValue.createValue);
		/*modified by huanglinchuan 2014.10.23 begin*/
		oAddElement(loNewFlow, "createWay", laValue.createWay);
		oAddElement(loNewFlow, "createWayName", laValue.createWayName);
		oAddElement(loNewFlow, "createInstanceWay", laValue.createInstanceWay);
		oAddElement(loNewFlow, "createInstanceWayName", laValue.createInstanceWayName);
		oAddElement(loNewFlow, "subformId", laValue.subformId);
		oAddElement(loNewFlow, "subformIdName", laValue.subformIdName);
		oAddElement(loNewFlow, "taskUsers", laValue.taskUsers);
		oAddElement(loNewFlow, "taskUsersName", laValue.taskUsersName);
		/*modified by huanglinchuan 2014.10.23 end*/
		oAddElement(loNewFlow, "isMerge", laValue.isMerge);
		oAddElement(loNewFlow, "isWait", laValue.isWait);
		oAddElement(loNewFlow, "isShare", laValue.isShare);
		oAddElement(loNewFlow, "toTaskName", laValue.toTaskName);
		oAddElement(loNewFlow, "toTaskId", laValue.toTaskId);
		oAddElement(loNewFlow, "copyFieldNames", laValue.copyFieldNames);
		oAddElement(loNewFlow, "copyFields", laValue.copyFields);
		oAddElement(loNewFlow, "returnOverrideFieldNames", laValue.returnOverrideFieldNames);
		oAddElement(loNewFlow, "returnOverrideFields", laValue.returnOverrideFields);
		oAddElement(loNewFlow, "returnAdditionFieldNames", laValue.returnAdditionFieldNames);
		oAddElement(loNewFlow, "returnAdditionFields", laValue.returnAdditionFields);
		oAddElement(loNewFlow, "notifyDoing", laValue.notifyDoing);
	}

	loNode = subFlowProperty.selectSingleNode("relations");
	if (loNode != null) {
		subFlowProperty.removeChild(loNode);
	}
	loNode = oSetElement(subFlowProperty, "relations");
	for ( var i = 0; i < goForm.DRelations.options.length; i++) {
		var laValue = $(goForm.DRelations.options[i]).data("value"); // goForm.DRelations.options[i].value.split("|");
		var loNewFlow = oAddElement(loNode, "relation");
		oAddElement(loNewFlow, "newFlowName", laValue.newFlowName);
		oAddElement(loNewFlow, "newFlowId", laValue.newFlowId);
		oAddElement(loNewFlow, "taskName", laValue.taskName);
		oAddElement(loNewFlow, "taskId", laValue.taskId);
		oAddElement(loNewFlow, "frontNewFlowName", laValue.frontNewFlowName);
		oAddElement(loNewFlow, "frontNewFlowId", laValue.frontNewFlowId);
		oAddElement(loNewFlow, "frontTaskName", laValue.frontTaskName);
		oAddElement(loNewFlow, "frontTaskId", laValue.frontTaskId);
	}
	// $.dialog.data("goProperty", goProperty);
	// $.dialog.close();
}
function ConditionLoadEvent(loTask, taskProperty) {
	var goForm = $("#conditionForm")[0];
	if(goForm == null){
		return;
	}
	// goForm.reset();
	$(".tabs", goForm).tabs();
	$("#btn_save", goForm).button();

	goForm.name.value = taskProperty.getAttribute("name");
	goForm.description.value = taskProperty.text();
	$("#btn_save", goForm).unbind("click");
	$("#btn_save", goForm).click(function() {
		taskProperty.setAttribute("name", goForm.name.value);
		taskProperty.get(0).childNodes[0].nodeValue = goForm.description.value;
		if (loTask.xmlObject != null) {
			bSetObjectText(loTask);
		}
		// alert("设置成功!");
	});
	$("#btn_abandon", goForm).button().click(function(e) {
		saveSetting = false;
		// $(loTask).trigger("dblclick");
		setTaskProperty(loTask, window.eWorkFlow.contentWindow, goWorkFlow);
	});
}
function setDirectionProperty(loLine, window, workFlow) {
	goWorkFlow = workFlow;
	var laOptName = new Array();
	var loTask = null;
	if (loLine.fromTask.Type == "CONDITION") {
		loTask = (loLine.fromTask.inLines != null && loLine.fromTask.inLines.length > 0) ? loLine.fromTask.inLines[0].fromTask
				: null;
	} else {
		loTask = loLine.fromTask;
	}
	if (loTask != null) {
		var loNode = loTask.xmlObject.selectSingleNode("optNames");
		if (loNode != null) {
			var laNode = loNode.selectNodes("unit");
			if (laNode != null) {
				for ( var i = 0; i < laNode.length; i++) {
					var lsValue = $(laNode[i]).selectSingleNode("value").text();
					var lsText = $(laNode[i]).selectSingleNode("argValue").text();
					laOptName.push(lsText + "|" + lsValue);
				}
			}
		}
	}
	// 如果是条件任务的前流向则直接返回
	if (loLine.xmlObject == undefined) {
		return;
	}

	var laArg = new Array();
	laArg.optname = laOptName.join(";");
	// laArg.Property = loLine.xmlObject;
	// laArg.opener = window;
	// var roObj = vOpenModal_("direction.htm", laArg, 600, 430);
	// if (roObj != null) {
	// bSetObjectText(loLine.labelObject);
	// }
	// if ($("#eProperty").html() == "") {
	// 保存当前设置
	saveCurrentSetting();
	$.get(ctx + "/workflow/direction2.htm", function(data) {
		$("#eProperty").html(data);
		// directionProperty = loLine.xmlObject
		DirectionLoadEvent(loLine, loLine.xmlObject, laArg.optname);
		DirectionInitEvent(loLine, loLine.xmlObject, laArg.optname);
	});
	// } else {
	// directionProperty = loLine.xmlObject
	// DirectionLoadEvent(loLine, loLine.xmlObject, laArg.optname);
	// }

	// $("#div_flow").hide();
	// $("#div_task").hide();
	// $("#div_condition").hide();
	// $("#div_sub_flow").hide();
	// $("#div_direction").show();
	// openDialog("direction.htm", "流向属性", 600, 430, "laArg", laArg,
	// dirCallback,
	// true);
}
function DirectionLoadEvent(loLine, directionProperty, optname) {
	var goForm = $("#directionForm")[0];
	if(goForm == null){
		return;
	}
	if (directionProperty.getAttribute("type") == "2") {
		// document.all.ID_PageContent_Condiction.id = "ID_PageContent";
		goForm.DCondition.optnames = optname;// window.dialogArguments.optname;
		$(".fork_condition", goForm).show();
		$(".tabs", goForm).tabs();
	} else {
		$(".fork_condition", goForm).hide();
		$(".tabs", goForm).tabs();
	}
	var loNode = directionProperty.selectSingleNode("conditions");
	if (loNode != null) {
		var laNode = loNode.selectNodes("unit");
		if (laNode != null) {
			for ( var i = 0; i < laNode.length; i++) {
				var lsValue = $(laNode[i]).selectSingleNode("value").text();
				var lsText = $(laNode[i]).selectSingleNode("argValue").text();
				bAddEntry(goForm.DCondition, lsText, lsValue, true);
			}
		}
	}
	goForm.name.value = directionProperty.getAttribute("name") == null ? "" : directionProperty
			.getAttribute("name");
	goForm.id.value = directionProperty.getAttribute("id") == null ? "" : directionProperty
			.getAttribute("id");
	var laField = [ "fileRecipient", "msgRecipient" ];
	for ( var i = 0; i < laField.length; i++) {
		bGetUnitXMLToField(directionProperty, goForm, laField[i]);
	}
	laField = [ "isDefault", "isEveryCheck", "isSendFile", "isSendMsg", "listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		var lsValue = (directionProperty.selectSingleNode(laField[i]) != null) ? directionProperty
				.selectSingleNode(laField[i]).text() : null;
		if (lsValue != null && lsValue != "") {
			bSetFormFieldValue(goForm, laField[i], lsValue);
		}
	}
	bSetVersionInfo(goWorkFlow.flowXML.getAttribute("verType"), window);
	// bInitPageLabel();
}
function DirectionInitEvent(loLine, directionProperty, optname) {
	var goForm = $("#directionForm")[0];
	if(goForm == null){
		return;
	}
	$(".tabs", goForm).tabs();
	$("#btn_save", goForm).button().click(function() {
		DirectionOKEvent(goForm, directionProperty);
		if (loLine.xmlObject) {
			bSetObjectText(loLine.labelObject);
		}
		// alert("设置成功!");
	});
	$("#btn_abandon", goForm).button().click(function(e) {
		saveSetting = false;
		// $(loLine).trigger("dblclick");
		setDirectionProperty(loLine, window.eWorkFlow.contentWindow, goWorkFlow);
	});

	$("input[name=isDefault]", goForm).change(function(e) {
		if (this.id === "isDefault_0" && this.checked) {
			$("#ID_DefaultDirection").show();
		} else if (this.id === "isDefault_1" && this.checked) {
			$("#ID_DefaultDirection").hide();
		}
	}).trigger("change");

	// 文件分发
	$("input[name=isSendFile]").change(function() {
		if (this.id === "isSendFile_1" && this.checked) {
			$("#ID_IsSendFile").show();
		}
		if (this.id === "isSendFile_0" && this.checked) {
			$("#ID_IsSendFile").hide();
		}
	}).trigger("change");

	// 消息分发
	$("input[name=isSendMsg]").change(function() {
		if (this.id === "isSendMsg_1" && this.checked) {
			$("#ID_IsSendMsg").show();
		}
		if (this.id === "isSendMsg_0" && this.checked) {
			$("#ID_IsSendMsg").hide();
		}
	}).trigger("change");

	// 增加分支条件
	$("#conditionAdd").click(function(e) {
		bDirectionActions(1, e, goForm);
	});
	// 插入分支条件
	$("#conditionInsert").click(function(e) {
		bDirectionActions(2, e, goForm);
	});
	// 编辑分支条件
	$("#conditionEdit").click(function(e) {
		bDirectionActions(3, e, goForm);
	});
	// 删除分支条件
	$("#conditionDelete").click(function(e) {
		bDirectionActions(4, e, goForm);
	});
	// 选择文件分发对象
	$("#Dir_DfileRecipients").click(function(e) {
		e.target.title = "选择文件分发对象";
		bDirectionActions(5, e, goForm);
	});
	// 选择消息分发对象
	$("#Dir_DmsgRecipients").click(function(e) {
		e.target.title = "选择消息分发对象";
		bDirectionActions(6, e, goForm);
	});

	// 事件监听
	var listernerSetting = {
		view : {
			showIcon : false,
			showLine : false
		},
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getDirectionListeners"
			}
		}
	};
	$("#listenerName").comboTree({
		labelField : "listenerName",
		valueField : "listener",
		treeSetting : listernerSetting,
		autoInitValue : false,
		width : 530,
		height : 260
	});
}
function bDirectionActions(piIndex, event, goForm) {
	switch (piIndex) {
	case 1:
		var reValCallback = function(laValue) {
			if (laValue == null || laValue.length == 0) {
				return;
			}
			bAddEntry(goForm.DCondition, laValue[0], laValue[1]);
		};
		aGetCondition(-1, reValCallback, goForm);
		break;
	case 2:
		var reValCallback = function(laValue) {
			if (laValue == null || laValue.length == 0) {
				return;
			}
			bInsertEntry(goForm.DCondition, laValue[0], laValue[1]);
		};
		aGetCondition(-2, reValCallback, goForm);
		break;
	case 3:
		var reValCallback = function(laValue) {
			if (laValue == null || laValue.length == 0) {
				return;
			}
			bEditEntry(goForm.DCondition, laValue[0], laValue[1]);
		};
		aGetCondition(goForm.DCondition.selectedIndex, reValCallback, goForm);
		break;
	case 4:
		bMoveEntry(goForm.DCondition);
		break;
	case 5:
		SelectUsers("", "fileRecipient", event.target.title, null, goForm);
		break;
	case 6:
		SelectUsers("", "msgRecipient", event.target.title, null, goForm);
		break;
	}
}
function aGetCondition(piIndex, reValCallback, goForm) {
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var lsFormName = loNode != null ? loNode.text() : "";
	if (lsFormName == "") {
		alert(parent.sGetLang("FLOW_WF_FLOWFORMISEMPTY"));
		return null;
	}
	var lsText = null, lsValue = null, lbLogic = false;
	if (piIndex >= 0) {
		lsText = goForm.DCondition.options[piIndex].text;
		lsValue = goForm.DCondition.options[piIndex].value;
		if (piIndex > 0) {
			lbLogic = true;
		}
	} else {
		if (piIndex == -2) {
			if (goForm.DCondition.selectedIndex > 0) {
				lbLogic = true;
			}
		} else {
			if (goForm.DCondition.options.length >= 1) {
				lbLogic = true;
			}
		}
	}
	var laArg = new Array();
	laArg.value = (lsValue != null) ? lsValue : "";
	laArg.text = (lsText != null) ? lsText : "";
	laArg.logic = lbLogic;
	laArg.optnames = goForm.DCondition.optnames;
	laArg.opener = opener;
	// return vOpenModal_("logic.htm", laArg, 600, 240);
	// ("logic.htm", "判断条件", 600, 240, "logicLaArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/logic2.htm", function(data) {
		$("#dlg_logic").dialog({
			title : "判断条件",
			autoOpen : true,
			resizable : false,
			width : 600,
			height : 250,
			modal : true,
			close : function() {
				$("#dlg_logic").dialog("destroy");
				$("#dlg_logic").html("<div/>");
			},
			open : function() {
				$("#dlg_logic").html(data);
				bInitLogicValue(laArg);
				LogicLoadEvent(laArg);
			},
			buttons : {
				"确定" : function(e) {
					if (!aLogicCheckValue(laArg)) {
						return;
					}
					var returnValue = aLogicBuildValue(laArg);
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function bLogicActions(piIndex, event, goForm) {
	switch (piIndex) {
	case 1:
		SelectUsers("", "Group", event.target.title, null, goForm);
		break;
	}
}
function bInitLogicValue(laArg) {
	var gsText = laArg["text"];
	var gsValue = laArg["value"];
	var gbIsLogic = laArg["logic"];
	var gsOptNames = laArg["optnames"];
	var goForm = $("#logicForm")[0];
	if (gsOptNames != null) {
		var laName = gsOptNames.split(";");
		for ( var i = 0; i < laName.length; i++) {
			bAddEntry(goForm.VoteOption, laName[i], "[VOTE="
					+ laName[i].substring(laName[i].indexOf("|") + 1, laName[i].length) + "]", true);
		}
	}
	if (gbIsLogic) {
		$("#ID_Logic", goForm).show();
	} else {
		$("#ID_Logic", goForm).hide();
	}
	// ID_Logic.style.display = gbIsLogic ? "" : "none";
	if (gsValue == null || gsValue == "") {
		return false;
	}
	var lsValue = gsValue;
	if (lsValue.indexOf("@ISMEMBER(") != -1) {
		goForm.LogicType[2].checked = true;
		var lsTemp = lsValue.substring(0, lsValue.indexOf("@ISMEMBER("));
		if (gbIsLogic) {
			if (lsTemp.indexOf("&") != -1) {
				bSetFormFieldValue(goForm, "AndOr", "&");
			} else {
				if (lsTemp.indexOf("|") != -1) {
					bSetFormFieldValue(goForm, "AndOr", "|");
				}
			}
		}
		if (lsTemp.indexOf("(") != -1) {
			bSetFormFieldValue(goForm, "gLBracket", "(");
		}
		lsTemp = lsValue.substring(lsValue.lastIndexOf('")') + 2, lsValue.length);
		if (lsTemp.indexOf(")") != -1) {
			bSetFormFieldValue(goForm, "gRBracket", ")");
		}
		lsValue = lsValue.substring(lsValue.indexOf('@ISMEMBER("') + 11, lsValue.lastIndexOf('")'));
		var laValue = lsValue.split('","');

		if (laValue[0] != "<CURUSER>") {
			// goForm.GroupType[1].checked = true;
			goForm.GroupType.value = 0;
			ID_UserField.style.display = "";
			goForm.UserField.value = laValue[0];
		} else if (laValue[0] == "<CURUSER>") {
			goForm.GroupType.value = 1;
		}
		goForm.Groups.value = laValue[1];
		var lsText = gsText.substring(gsText.indexOf('@ISMEMBER("') + 11, gsText.lastIndexOf('")'));
		goForm.DGroups.value = lsText.split('","')[1];
	} else {
		if (lsValue.indexOf("[VOTE=") != -1) {
			goForm.LogicType[1].checked = true;
			if (lsValue.indexOf(" & ") == 0) {
				if (gbIsLogic) {
					bSetFormFieldValue(goForm, "AndOr", "&");
				}
				lsValue = lsValue.substring(3, lsValue.length);
			} else {
				if (lsValue.indexOf(" | ") == 0) {
					if (gbIsLogic) {
						bSetFormFieldValue(goForm, "AndOr", "|");
					}
					lsValue = lsValue.substring(3, lsValue.length);
				}
			}
			if (lsValue.indexOf("(") == 0) {
				bSetFormFieldValue(goForm, "vLBracket", "(");
				lsValue = lsValue.substring(1, lsValue.length);
			}
			if (lsValue.lastIndexOf(")") == lsValue.length - 1) {
				bSetFormFieldValue(goForm, "vRBracket", ")");
				lsValue = lsValue.substring(0, lsValue.length - 1);
			}
			bSetFormFieldValue(goForm, "VoteOption", lsValue.substr(0, lsValue.indexOf(" ")));
			lsValue = lsValue.substr(lsValue.indexOf(" ") + 1);
			bSetFormFieldValue(goForm, "vOperator", lsValue.substr(0, lsValue.indexOf(" ")));
			goForm.vValue.value = lsValue.substr(lsValue.indexOf(" ") + 1);
		} else {
			if (lsValue.indexOf(" & ") == 0) {
				if (gbIsLogic) {
					bSetFormFieldValue(goForm, "AndOr", "&");
				}
				lsValue = lsValue.substring(3, lsValue.length);
			} else {
				if (lsValue.indexOf(" | ") == 0) {
					if (gbIsLogic) {
						bSetFormFieldValue(goForm, "AndOr", "|");
					}
					lsValue = lsValue.substring(3, lsValue.length);
				}
			}
			if (lsValue.indexOf("(") == 0) {
				bSetFormFieldValue(goForm, "LBracket", "(");
				lsValue = lsValue.substring(1, lsValue.length);
			}
			if (lsValue.lastIndexOf(")") == lsValue.length - 1) {
				bSetFormFieldValue(goForm, "RBracket", ")");
				lsValue = lsValue.substring(0, lsValue.length - 1);
			}
			goForm.FieldName.value = lsValue.substr(0, lsValue.indexOf(" "));
			lsValue = lsValue.substr(lsValue.indexOf(" ") + 1);
			bSetFormFieldValue(goForm, "Operator", lsValue.substr(0, lsValue.indexOf(" ")));
			goForm.Value.value = lsValue.substr(lsValue.indexOf(" ") + 1);
		}
	}

	bSetVersionInfo(goWorkFlow.flowXML.getAttribute("verType"), window);
	return true;
}
function LogicLoadEvent(laArg) {
	var goForm = $("#logicForm")[0];
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";

	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		success : function(result) {
			var data = result.data;
			onGetFormFields("select[name=FieldNameLabel]", goForm, data);
			$("#FieldNameLabel").val($("#FieldName").val());
		}
	});
	$("#FieldNameLabel").change(function() {
		$("#FieldName").val($(this).val());
	});

	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		success : function(result) {
			var data = result.data;
			onGetFormFields("select[name=UserFieldLabel]", goForm, data);
			$("#UserFieldLabel").val($("#UserField").val());
		}
	});
	$("#UserFieldLabel").change(function() {
		$("#UserField").val($(this).val());
	});

	// 条件类型改变处理
	$("input[name=LogicType]", goForm).change(function(e) {
		bChangeLogicType(this, laArg);
	}).trigger("change");

	// 所属类型
	$("select[name=GroupType]", goForm).change(function() {
		var v = $(this).val();
		if (v === "0") {
			$("#ID_UserField", goForm).show();
		} else {
			$("#ID_UserField", goForm).hide();
		}
	}).trigger("change");
	// 初始化群组
	var groups = goForm.Groups.value.split(",");
	if (groups.length == 5) {
		goForm.DGroup1.value = groups[0];
		goForm.Group1.value = groups[1];
		goForm.Group2.value = groups[2];
		goForm.Group4.value = groups[3];
		goForm.Group8.value = groups[4];
	}
	// 选择群组
	$("#DGroups").click(function(e) {
		e.target.title = "选择群组";
		bLogicActions(1, e, goForm);
	});
}
function bChangeLogicType(poObj, laArg) {
	// var gsOptNames = laArg["optnames"];
	// if (poObj.value == "2" && (gsOptNames == null || gsOptNames == "")) {
	// alert(Logic_ID_Vote.OptNamesIsEmpty);
	// }
	if (poObj.value == "1" && poObj.checked) {
		$("#Logic_ID_Field").show();
		$("#Logic_ID_Vote").hide();
		$("#Logic_ID_Group").hide();
	} else if (poObj.value == "2" && poObj.checked) {
		$("#Logic_ID_Field").hide();
		$("#Logic_ID_Vote").show();
		$("#Logic_ID_Group").hide();
	} else if (poObj.value == "3" && poObj.checked) {
		$("#Logic_ID_Field").hide();
		$("#Logic_ID_Vote").hide();
		$("#Logic_ID_Group").show();
	}
	// Logic_ID_Field.style.display = (poObj.value == "1") ? "" : "none";
	// Logic_ID_Vote.style.display = (poObj.value == "2") ? "" : "none";
	// Logic_ID_Group.style.display = (poObj.value == "3") ? "" : "none";
}
function aLogicCheckValue(laArg) {
	var goForm = $("#logicForm")[0];
	if (goForm.LogicType[0].checked == true) {
		if (goForm.FieldName.value == "") {
			alert("请选择字段!");
			return false;
		}
	} else {
		if (goForm.LogicType[1].checked == true) {
			if (goForm.vValue.value == "") {
				alert("请输入所占百分比!");
				return false;
			} else {
				if (goForm.vValue.value.indexOf("%") != -1) {
					alert("不需要在所占百分比中输入百分号“％”!");
					return false;
				}
			}
		} else {
			if (goForm.LogicType[2].checked == true) {
				if (goForm.GroupType.value == "") {
					alert("请指定成员!");
					return false;
				}
				if (goForm.GroupType.value == "0" && goForm.UserField.value == "") {
					alert("请选择字段!");
					return false;
				}
				if (goForm.DGroups.value == "") {
					alert("请指定群组!");
					return false;
				}
			}
		}
	}

	return true;
}
function aLogicBuildValue(laArg) {
	var gbIsLogic = laArg["logic"];
	var goForm = $("#logicForm")[0];
	goForm.Groups.value = $("input[name='DGroup1']", goForm).val() + ","
			+ $("input[name='Group1']", goForm).val() + "," + $("input[name='Group2']", goForm).val() + ","
			+ $("input[name='Group4']", goForm).val() + "," + $("input[name='Group8']", goForm).val();

	if (goForm.LogicType[0].checked == true) {
		if (goForm.FieldName.value == "") {
			alert(goForm.FieldName.emptymsg);
			return null;
		}
	} else {
		if (goForm.LogicType[1].checked == true) {
			if (goForm.vValue.value == "") {
				alert(goForm.vValue.emptymsg);
				return null;
			} else {
				if (goForm.vValue.value.indexOf("%") != -1) {
					alert(goForm.vValue.errormsg);
					return null;
				}
			}
		} else {
			if (goForm.LogicType[2].checked == true) {
				if (goForm.GroupType.value == "0" && goForm.UserField.value == "") {
					alert(goForm.UserField.emptymsg);
					return null;
				}
				if (goForm.DGroups.value == "") {
					alert(goForm.DGroups.emptymsg);
					return null;
				}
			}
		}
	}
	var laReturn = new Array();
	var lsDLogic = "", lsLogic = "";
	if (gbIsLogic && goForm.AndOr.options[goForm.AndOr.selectedIndex].value != "") {
		lsDLogic = goForm.AndOr.options[goForm.AndOr.selectedIndex].text + " ";
		lsLogic = " " + goForm.AndOr.options[goForm.AndOr.selectedIndex].value + " ";
	}
	if (goForm.LogicType[0].checked == true) {
		var lsOperator = goForm.Operator.options[goForm.Operator.selectedIndex].value;
		var lsDOperator = goForm.Operator.options[goForm.Operator.selectedIndex].text;
		var lsLBracket = goForm.LBracket.options[goForm.LBracket.selectedIndex].value;
		var lsRBracket = goForm.RBracket.options[goForm.RBracket.selectedIndex].value;
		var lsDCondition = lsDLogic + lsLBracket + "["
				+ goForm.FieldNameLabel.options[goForm.FieldNameLabel.selectedIndex].text + "] "
				+ lsDOperator + " " + goForm.Value.value + lsRBracket;
		var lsCondition = lsLogic + lsLBracket + goForm.FieldName.value + " " + lsOperator + " "
				+ goForm.Value.value + lsRBracket;
		if (goForm.FieldName.value != -1) {
			laReturn.push(lsDCondition);
			laReturn.push(lsCondition);
		}
	} else {
		if (goForm.LogicType[1].checked == true) {
			var lsDOperator = goForm.vOperator.options[goForm.vOperator.selectedIndex].text;
			var lsOperator = goForm.vOperator.options[goForm.vOperator.selectedIndex].value;
			var lsLBracket = goForm.vLBracket.options[goForm.vLBracket.selectedIndex].value;
			var lsRBracket = goForm.vRBracket.options[goForm.vRBracket.selectedIndex].value;
			var lsDVoteOption = goForm.VoteOption.options[goForm.VoteOption.selectedIndex].text;
			var lsVoteOption = goForm.VoteOption.options[goForm.VoteOption.selectedIndex].value;
			var lsDCondition = lsDLogic + lsLBracket + "[" + lsDVoteOption + "] " + lsDOperator + " "
					+ goForm.vValue.value + "%" + lsRBracket;
			var lsCondition = lsLogic + lsLBracket + lsVoteOption + " " + lsOperator + " "
					+ goForm.vValue.value + lsRBracket;
			laReturn.push(lsDCondition);
			laReturn.push(lsCondition);
		} else {
			if (goForm.LogicType[2].checked == true) {
				var lsLBracket = goForm.gLBracket.options[goForm.gLBracket.selectedIndex].value;
				var lsRBracket = goForm.gRBracket.options[goForm.gRBracket.selectedIndex].value;
				if (goForm.GroupType.value == "1") {
					var lsField = "<CURUSER>";
				} else {
					var lsField = goForm.UserField.value;
				}
				var lsDCondition = lsDLogic + lsLBracket + '@ISMEMBER("' + lsField + '","'
						+ goForm.DGroups.value + '")' + lsRBracket;
				var lsCondition = lsLogic + lsLBracket + '@ISMEMBER("' + lsField + '","'
						+ goForm.Groups.value + '")' + lsRBracket;
				laReturn.push(lsDCondition);
				laReturn.push(lsCondition);
			}
		}
	}
	return laReturn;
}
function DirectionOKEvent(goForm, directionProperty) {
	loNode = directionProperty.selectSingleNode("conditions");
	if (loNode != null) {
		directionProperty.removeChild(loNode);
	}
	loNode = oSetElement(directionProperty, "conditions");
	for ( var i = 0; i < goForm.DCondition.options.length; i++) {
		var lsText = goForm.DCondition.options[i].text;
		var lsValue = goForm.DCondition.options[i].value;
		var loUnit = oAddElement(loNode, "unit");
		loUnit.setAttribute("type", "16");
		oAddElement(loUnit, "value", lsValue);
		oAddElement(loUnit, "argValue", lsText);
	}
	directionProperty.setAttribute("name", goForm.name.value);
	directionProperty.setAttribute("id", goForm.id.value);
	var laField = [ "fileRecipient", "msgRecipient" ];
	for ( var i = 0; i < laField.length; i++) {
		bSetUnitFieldToXML(directionProperty, goForm, laField[i]);
	}
	laField = [ "isDefault", "isEveryCheck", "isSendFile", "isSendMsg", "listenerName", "listener" ];
	for ( var i = 0; i < laField.length; i++) {
		oSetElement(directionProperty, laField[i], sGetFormFieldValue(goForm, laField[i]));
	}
	// window.returnValue = directionProperty;
	// window.close();
	// $.dialog.data("directionProperty", directionProperty);
	// $.dialog.close();
}
function bFlowActions(piIndex, event, goForm) {
	function SetUnit(target, title, goForm) {
		$.unit.open({
			initNames : goForm["D" + target + "1"].value,
			initIDs : goForm[target + "1"].value,
			title : title,
			multiple : true,
			selectType : 1,
			nameType : "21",
			showType : true,
			type : "MyUnit",
			loginStatus : false,
			excludeValues : null,
			afterSelect : function(laValue) {
				if (laValue == null) {
					return;
				}
				goForm["D" + target + "s"].value = laValue["name"];
				goForm["D" + target + "1"].value = laValue["name"];
				goForm[target + "1"].value = laValue["id"];
			}
		});
	}
	switch (piIndex) {
	case 1:
		SetUnit("creator", event.target.title, goForm);
		// var callback = function(laValue) {
		// if (laValue != null) {
		// goForm.Dcreators.value = laValue.name;
		// goForm.Dcreator1.value = laValue.name;
		// goForm.creator1.value = laValue.id;
		// }
		// };
		// // PromptUnit(null, goForm.Dcreators.value, goForm.creator1.value,
		// // event.target.title, true, callback);
		// $.unit.open({
		// paInitNames : goForm.Dcreators.value,
		// paInitIDs : goForm.creator1.value,
		// psTitle : event.target.title,
		// pbMultiple : true,
		// afterSelect : callback
		// });
		break;
	case 2:
		SetUnit("user", event.target.title, goForm);
		// var callback = function(laValue) {
		// if (laValue != null) {
		// goForm.Dusers.value = laValue.name;
		// goForm.Duser1.value = laValue.name;
		// goForm.user1.value = laValue.id;
		// }
		// };
		// // PromptUnit(null, goForm.Dusers.value, goForm.user1.value,
		// // event.target.title, true, callback);
		// $.unit.open({
		// paInitNames : goForm.Dusers.value,
		// paInitIDs : goForm.user1.value,
		// psTitle : event.target.title,
		// pbMultiple : true,
		// afterSelect : callback
		// });
		break;
	case 3:
		SelectUsers("unit/field/option", "monitor", event.target.title, null, goForm);
		break;
	case 4:
		SelectUsers("unit/field/option", "admin", event.target.title, null, goForm);
		break;
	case 5:
		bSetField("dueTime", false);
		break;
	case 6:
		SelectUsers("direction", "beginDirections", event.target.title, null, goForm);
		break;
	case 7:
		SelectUsers("direction", "endDirections", event.target.title, null, goForm);
		break;
	case 8:
		SelectUsers("", "fileRecipient", event.target.title, null, goForm);
		break;
	case 9:
		SelectUsers("", "msgRecipient", event.target.title, null, goForm);
		break;
	case 10:
		var reValCallback = function(laValue) {
			if (laValue != null && laValue.length > 0) {
				goForm.EQFlowName.value = laValue[0];
				goForm.EQFlowID.value = laValue[1];
				goWorkFlow.equalFlowID = goForm.EQFlowID.value;
			}
		};
		aSelectFlow2(event.target.name, false, event.target.title, goForm.EQFlowID.value, reValCallback);
		// aSelectFlow(false, event.target.title, goForm.EQFlowID.value,
		// reValCallback);
		break;
	case 11:
		// lsValue = sGetTimer(lsValue);
		var getTimerCallBack = function(lsValue) {
			if (lsValue == null || lsValue == "") {
				return;
			}
			bAddEntry(goForm.DTimer, lsValue, lsValue);
		};
		sGetTimer("", getTimerCallBack, goForm);
		break;
	case 12:
		var lsValue = sGetAllEntryValue(goForm.DTimer, true);
		if (lsValue == null || lsValue == "") {
			return false;
		}
		// lsValue = sGetTimer(lsValue);
		var getTimerCallBack = function(lsValue) {
			if (lsValue == null || lsValue == "") {
				return;
			}
			bEditEntry(goForm.DTimer, lsValue, lsValue);
		};
		sGetTimer(lsValue, getTimerCallBack, goForm);
		break;
	case 13:
		var lsValue = sGetAllEntryValue(goForm.DTimer, true);
		if (lsValue == null || lsValue == "") {
			return false;
		}
		bMoveEntry(goForm.DTimer);
		loNode = goWorkFlow.flowXML.selectSingleNode("./timers/timer[name='" + lsValue + "']");
		if (loNode != null) {
			loNode.remove();
		}
		break;
	case 14:
		var reValCallback = function(laValue) {
			if (laValue == null || laValue.length == 0) {
				return;
			}
			bAddEntry(goForm.DBackUser, laValue[0] + "->" + laValue[2], laValue[1] + "->" + laValue[3]);
		};
		aGetBackUser(-1, reValCallback, goForm);
		break;
	case 15:
		var lsValue = sGetAllEntryValue(goForm.DBackUser, true);
		if (lsValue == null || lsValue == "") {
			return false;
		}
		var reValCallback = function(laValue) {
			if (laValue == null || laValue.length == 0) {
				return;
			}
			bEditEntry(goForm.DBackUser, laValue[0] + "->" + laValue[2], laValue[1] + "->" + laValue[3]);
		};
		aGetBackUser(goForm.DBackUser.selectedIndex, reValCallback, goForm);
		break;
	case 16:
		bMoveEntry(goForm.DBackUser);
		break;
	case 17:
		var reValCallback = function(lsReturn) {
			if (lsReturn == null) {
				return;
			}
			/*modified by huanglinchuan 2014.10.21 begin*/
			var lsText = lsReturn.typeName + "|" + lsReturn.name+"|"+(lsReturn.isSendMsg=="1"?"启用":"关闭")+"|";
			if (lsReturn.condition != null && $.trim(lsReturn.condition) != "") {
				lsText = lsText + lsReturn.condition+" | ";
			}else{
				lsText+="无  | ";
			}
			if (lsReturn.extraMsgRecipients != null && $.trim(lsReturn.extraMsgRecipients) != "") {
				lsText = lsText + lsReturn.extraMsgRecipients;
			}else{
				lsText+="无";
			}
			var $option = $("<option>" + lsText + "</option>");
			$option.data("value", lsReturn);
			$(goForm.DMessageTemplate, goForm).append($option);
			/*modified by huanglinchuan 2014.10.21 end*/
		};
		sGetMessageTemplate({}, reValCallback);
		break;
	case 18:
		var $option = $("#DMessageTemplate > option:selected", goForm);
		if ($option.length == 0) {
			return false;
		}
		// 转化为对象参数
		var lsValue = $option.data("value") || {};
		var reValCallback = function(lsReturn) {
			if (lsReturn == null) {
				return;
			}
			/*modified by huanglinchuan 2014.10.21 begin*/
			var lsText = lsReturn.typeName + "|" + lsReturn.name+"|"+(lsReturn.isSendMsg=="1"?"启用":"关闭")+"|";
			if (lsReturn.condition != null && $.trim(lsReturn.condition) != "") {
				lsText = lsText + lsReturn.condition+" | ";
			}else{
				lsText+="无 | ";
			}
			if (lsReturn.extraMsgRecipients != null && $.trim(lsReturn.extraMsgRecipients) != "") {
				lsText = lsText + lsReturn.extraMsgRecipients;
			}else{
				lsText+="无";
			}
			$option.text(lsText);
			$option.data("value", lsReturn);
			/*modified by huanglinchuan 2014.10.21 end*/
		};
		sGetMessageTemplate(lsValue, reValCallback);
		break;
	case 19:
		bMoveEntry(goForm.DMessageTemplate);
		break;
	//add by huanglinchuan 2014.10.21 begin
	case 20:
		SelectUsers("", "extraMsgRecipient", event.target.title, null, goForm);
		break;	
	//add by huanglinchuan 2014.10.21 end	
	/*add by huanglinchuan 2014.10.28 begin*/	
	case 21:
		SetUnit("viewer", event.target.title, goForm);
		break;
	/*add by huanglinchuan 2014.10.28 end*/
	}
}
function aGetBackUser(piIndex, reValCallback, goForm) {
	var laValue = new Array();
	if (piIndex > -1) {
		var laText = goForm.DBackUser.options[piIndex].text.split("->");
		var laTemp = goForm.DBackUser.options[piIndex].value.split("->");
		laValue.push(laText[0]);
		laValue.push(laTemp[0]);
		laValue.push(laText[1]);
		laValue.push(laTemp[1]);
	}
	var laArg = new Array();
	laArg.User = piIndex == -1 ? null : laValue;
	// return vOpenModal_("user/back.htm", laArg, 600, 200);
	// openDialog("user/back.htm", "岗位代替设置", 600, 200, "userbackArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/user/back2.htm", function(data) {
		$("#dlg_user_back").dialog({
			title : "岗位代替设置",
			autoOpen : true,
			resizable : true,
			width : 600,
			height : 220,
			modal : true,
			overlay : {
				background : '#000',
				opacity : 0.5
			},
			open : function() {
				$("#dlg_user_back").html(data);
				UserBackInitEvent(laArg);
			},
			buttons : {
				"确定" : function(e) {
					if (!UserBackCheck()) {
						return;
					}
					var retVal = UserBackOKEvent();
					reValCallback(retVal);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function UserBackInitEvent(laArg) {
	var goForm = $("#userBackForm")[0];
	var gaBackUser = laArg["User"];
	if (gaBackUser != null) {
		goForm.AUser.value = gaBackUser[0];
		goForm.AUserID.value = gaBackUser[1];
		goForm.BUsers.value = gaBackUser[2];
		goForm.BUserIDs.value = gaBackUser[3];
	}

	// A岗人员
	$("#AUser").click(function() {
		$.unit.open({
			initNames : goForm.AUser.value,
			initIDs : goForm.AUserID.value,
			title : "人员选择",
			multiple : false,
			selectType : 1,
			nameType : "21",
			showType : true,
			type : "MyUnit",
			loginStatus : false,
			excludeValues : null,
			afterSelect : function(laValue) {
				if (laValue == null) {
					return;
				}
				goForm.AUser.value = laValue["name"];
				goForm.AUserID.value = laValue["id"];
			}
		});
	});
	// B岗人员
	$("#BUsers").click(function() {
		$.unit.open({
			initNames : goForm.BUsers.value,
			initIDs : goForm.BUserIDs.value,
			title : "人员选择",
			multiple : false,
			selectType : 1,
			nameType : "21",
			showType : true,
			type : "MyUnit",
			loginStatus : false,
			excludeValues : null,
			afterSelect : function(laValue) {
				if (laValue == null) {
					return;
				}
				goForm.BUsers.value = laValue["name"];
				goForm.BUserIDs.value = laValue["id"];
			}
		});
	});
}
function UserBackCheck() {
	var goForm = $("#userBackForm")[0];
	if (goForm.AUser.value == "") {
		alert("请指定A岗人员!");
		return false;
	} else if (goForm.BUsers.value == "") {
		alert("请指定B岗人员!");
		return false;
	}
	return true;
}
function UserBackOKEvent() {
	var goForm = $("#userBackForm")[0];
	var laValue = new Array();
	laValue.push(goForm.AUser.value);
	laValue.push(goForm.AUserID.value);
	laValue.push(goForm.BUsers.value);
	laValue.push(goForm.BUserIDs.value);
	return laValue;
}
function sGetMessageTemplate(laArg, reValCallback) {
	var liWidth = 400;
	//modified by huanglinchuan 2014.10.21 begin
	var liHeight = 300;
	$.get(ctx + "/workflow/message/template.htm", function(data) {
		$("#dlg_message_template").dialog({
			title : "消息分发",
			autoOpen : true,
			resizable : false,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_message_template").dialog("destroy");
				$("#dlg_message_template").html("<div/>");
			},
			open : function() {
				$("#dlg_message_template").html(data);
				bInitMessageTemplate(laArg);
			},
			buttons : {
				"确定" : function(e) {
					var returnValue = sMessageTemplateBuildValue(laArg);
					reValCallback(returnValue);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
		
		$("#DextraMsgRecipients").click(function(e) {
			e.target.title = "选择消息抄送对象";
			bFlowActions(20, e, $("#messageTemplateForm")[0]);
		});
	});
	//modified by huanglinchuan 2014.10.21 end
}
function bInitMessageTemplate(laArg) {
	var type = laArg.type;
	var id = laArg.id;
	var condition = laArg.condition;
	//modified by huanglinchuan 2014.10.21 begin
	var isSendMsg=laArg.isSendMsg;
	var extraMsgRecipients=laArg.extraMsgRecipients;
	var extraMsgRecipientUserIds=laArg.extraMsgRecipientUserIds;
	//modified by huanglinchuan 2014.10.21 end
	var goForm = $("#messageTemplateForm")[0];

	JDS.call({
		service : "flowSchemeService.getMessageTemplates",
		data : [],
		success : function(result) {
			var data = result.data;
			$.each(data, function(i) {
				var option = "<option value='" + this.id + "'>" + this.name + "</option>";
				$("#templateId", goForm).append(option);
			});
			$("#type", goForm).val(type);
			$("#templateId", goForm).val(id);
			$("#condition", goForm).val(condition);
			//modified by huanglinchuan 2014.10.21 begin
			$("input[id=isSendMsg][value="+isSendMsg+"]").prop("checked",true);
			if (extraMsgRecipients != null) {
				goForm.DextraMsgRecipients.value = extraMsgRecipients == null ? "" : extraMsgRecipients;
				var extraMsgRecipientUserIdArray = extraMsgRecipientUserIds.split(",");
				if (extraMsgRecipientUserIdArray.length == 5) {
					goForm.DextraMsgRecipient1.value = extraMsgRecipientUserIdArray[0];
					goForm.extraMsgRecipient1.value = extraMsgRecipientUserIdArray[1];
					goForm.extraMsgRecipient2.value = extraMsgRecipientUserIdArray[2];
					goForm.extraMsgRecipient4.value = extraMsgRecipientUserIdArray[3];
					goForm.extraMsgRecipient8.value = extraMsgRecipientUserIdArray[4];
				}
			}
			//modified by huanglinchuan 2014.10.21 end
		}
	});
}
function sMessageTemplateBuildValue(laArg) {
	var goForm = $("#messageTemplateForm")[0];
	var retVal = {};
	var type = $("#type", goForm).val();
	var typeName = $("#type", goForm).find("option:selected").text();
	var templateId = $("#templateId", goForm).val();
	var templateName = $("#templateId", goForm).find("option:selected").text();
	var condition = $("#condition", goForm).val();
	/*add by huanglinchuan 2014.10.21 begin*/
	var isSendMsg=$("input[name=isSendMsg]:checked",goForm).val();
	retVal.isSendMsg=isSendMsg;
	retVal.extraMsgRecipients=goForm.DextraMsgRecipients.value;
	var extraMsgRecipientUserIds = $("input[name='DextraMsgRecipient1']", goForm).val() + ","
	+ $("input[name='extraMsgRecipient1']", goForm).val() + ","
	+ $("input[name='extraMsgRecipient2']", goForm).val() + ","
	+ $("input[name='extraMsgRecipient4']", goForm).val() + ","
	+ $("input[name='extraMsgRecipient8']", goForm).val();
	retVal.extraMsgRecipientUserIds=extraMsgRecipientUserIds;
	/*add by huanglinchuan 2014.10.21 end*/
	retVal.type = type;
	retVal.typeName = typeName;
	retVal.id = templateId;
	retVal.name = templateName;
	retVal.condition = condition;
	return retVal;
}
function sGetTimer(psName, reValCallback, goForm) {
	var loNode = null;
	if (psName != null && psName != "") {
		loNode = goWorkFlow.flowXML.selectSingleNode("./timers/timer[name='" + psName + "']");
	}
	var laArg = new Array();
	// laArg.Property = loNode;
	laArg.Names = sGetAllEntryValue(goForm.DTimer);
	laArg.Form = sGetFormFieldValue(goForm, "DForm");
	laArg.opener = opener;
	// dialog.top
	var liWidth = 600;
	var liHeight = 480;
	// openDialog("timer.htm", "定时系统", liWidth, liHeight, "laArg", laArg,
	// reValCallback);
	// return vOpenModal_("timer.htm", laArg, 600, 480);
	$.get(ctx + "/workflow/timer2.htm", function(data) {
		$("#dlg_timer").dialog({
			title : "计时系统",
			autoOpen : true,
			resizable : false,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_timer").dialog("destroy");
				$("#dlg_timer").html("<div/>");
			},
			open : function() {
				$("#dlg_timer").html(data);
				// onload事件
				// timerProperty = loNode;
				TimerLoadEvent(loNode, laArg);
				TimerInitEvent(loNode, laArg);
			},
			buttons : {
				"确定" : function(e) {
					if (!TimerOKCheck(loNode, laArg)) {
						return;
					}
					var laReturn = TimerOKEvent(loNode, laArg);
					reValCallback(laReturn);
					$(this).dialog("close");
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
}
function aSelectFlow2(fieldName, pbMultiple, psTitle, psValues, reValCallback) {
	// JQuery zTree设置
	var selectSubFlowSetting = {
		view : {
			showIcon : false
		},
		check : {
			chkStyle : "radio"
		},
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getFlowTree",
				"data" : "-1"
			},
			type : "POST"
		}
	};
	var innerCallback = function(lsValue) {
		if (lsValue != null) {
			var laReturn = new Array();
			laReturn.push(lsValue.name);
			laReturn.push(lsValue.value);

			if (reValCallback != undefined) {
				reValCallback(laReturn);
			}
		}
	};
	// 弹出环节选择框选择下一子流程
	$("#dlg_select_sub_flow").popupTreeWindow({
		title : psTitle,
		initValues : psValues,
		autoOpen : false,
		treeSetting : selectSubFlowSetting,
		afterSelect : function(retVal) {
			innerCallback(retVal);
		},
		afterCancel : function() {
		},
		close : function(e) {
			$("#dlg_select_sub_flow").html("<div/>");
		}
	});
	$("#dlg_select_sub_flow").popupTreeWindow("open");
}
function SelectUsers(psStyle, psTarget, psTitle, pbSingle, goForm) {
	var lsStyle = (psStyle != null && psStyle != "") ? psStyle : "unit/bizRole/task/field/option";
	var laArg = new Array();
	laArg.Style = lsStyle;
	laArg.Title = psTitle;
	var laExistTask = null;
	if (lsStyle.indexOf("task") != -1) {
		var lsID = (goForm.id) ? goForm.id.value : "";
		laExistTask = aGetTasks(lsStyle.indexOf("alltask") != -1 ? "TASK/SUBFLOW" : "", lsStyle
				.indexOf("nocurtask") != -1 ? lsID : null, lsStyle.indexOf("alltask") != -1 ? true : null);
		laArg.Tasks = laExistTask;
		laArg.MulTask = (pbSingle == true) ? false : true;
	}
	var laDirection = null;
	if (lsStyle.indexOf("direction") != -1) {
		laDirection = aGetDirections();
		laArg.Directions = laDirection;
	}
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
	if (lsStyle.indexOf("bizRole") != -1) {
		liHeight += 60;
		var laValue = new Array();
		var loField = goForm["D" + psTarget + "16"];// eval("goForm.D" +
		// psTarget + "16");
		laValue.push((loField != null) ? loField.value : "");
		loField = goForm[psTarget + "16"];// eval("goForm." + psTarget +
		// "16");
		laValue.push((loField != null) ? loField.value : "");
		laArg.bizRole = laValue;
	}
	if (lsStyle.indexOf("field") != -1) {
		liHeight += 40;
		var loField = goForm[psTarget + "2"];// eval("goForm." + psTarget +
		// "2");
		laArg.field = (loField != null) ? loField.value : "";
	}
	if (lsStyle.indexOf("task") != -1) {
		liHeight += 80;
		var loField = goForm[psTarget + (lsStyle.indexOf("unit") != -1 ? "4" : "")];// eval("goForm."
		// + psTarget + (lsStyle.indexOf("unit") != -1 ? "4" : ""));
		laArg.task = (loField != null) ? loField.value : "";
	}
	if (lsStyle.indexOf("option") != -1) {
		liHeight += 320;
		var loField = goForm[psTarget + "8"];// eval("goForm." + psTarget +
		// "8");
		laArg.option = (loField != null) ? loField.value : "";
	}
	if (lsStyle.indexOf("direction") != -1) {
		liHeight += 100;
		var loField = goForm[psTarget];// eval("goForm." + psTarget);
		laArg.direction = (loField != null) ? loField.value : "";
	}
	if (liHeight < 200) {
		liHeight += 100;
	}
	// var laValue = vOpenModal_("user/select.htm", laArg, liWidth,
	// liHeight);

	var reValCallback = function(laValue) {
		if (laValue == null) {
			return;
		}
		var laDValue = new Array();
		if (lsStyle.indexOf("unit") != -1) {
			var loField = goForm["D" + psTarget + "1"];// eval("goForm.D" +
			// psTarget + "1");
			if (loField != null) {
				loField.value = laValue.unit[0];
			}
			loField = goForm[psTarget + "1"];// eval("goForm." + psTarget +
			// "1");
			if (loField != null) {
				loField.value = laValue.unit[1];
			}
			if (laValue.unit[0] != "") {
				laDValue.push(laValue.unit[0]);
			}
		}
		if (lsStyle.indexOf("bizRole") != -1) {
			var loField = goForm["D" + psTarget + "16"];// eval("goForm.D" +
			// psTarget + "1");
			if (loField != null) {
				loField.value = laValue.bizRole[0];
			}
			loField = goForm[psTarget + "16"];// eval("goForm." + psTarget +
			// "1");
			if (loField != null) {
				loField.value = laValue.bizRole[1];
			}
			if (laValue.bizRole[0] != "") {
				laDValue.push(laValue.bizRole[0]);
			}
		}
		if (lsStyle.indexOf("field") != -1) {
			var loField = goForm[psTarget + "2"];// eval("goForm." + psTarget
			// + "2");
			if (loField != null) {
				loField.value = laValue.field;
			}
			if (laValue.field != null && laValue.field != "") {
				var laTemp = laValue.field.split(";");
				for ( var i = 0; i < laTemp.length; i++) {
					laTemp[i] = "{" + laTemp[i] + "}";
				}
				laDValue.push(laTemp.join(";"));
			}
		}
		if (lsStyle.indexOf("task") != -1) {
			var loField = goForm[psTarget + (lsStyle.indexOf("unit") != -1 ? "4" : "")];// eval("goForm."
			// + psTarget + (lsStyle.indexOf("unit") != -1 ? "4" : ""));
			if (loField != null) {
				loField.value = laValue.task;
			}
			if (laValue.task != null && laValue.task != "" && laExistTask != null) {
				var laTemp = laValue.task.split(";");
				for ( var i = 0; i < laTemp.length; i++) {
					for ( var j = 0; j < laExistTask.length; j++) {
						if (laExistTask[j].split("|")[1] == laTemp[i]) {
							break;
						}
					}
					if (j < laExistTask.length) {
						laTemp[i] = "<" + laExistTask[j].split("|")[0] + ">";
					}
				}
				if (lsStyle.indexOf("unit") == -1) {
					loField = goForm["D" + psTarget];// eval("goForm.D" +
					// psTarget);
					if (loField != null) {
						loField.value = laTemp.join(";");
					}
				} else {
					laDValue.push(laTemp.join(";"));
				}
			} else {
				if (lsStyle.indexOf("unit") == -1) {
					loField = goForm["D" + psTarget];// eval("goForm.D" +
					// psTarget);
					if (loField != null) {
						loField.value = "";
					}
				}
			}
		}

		if (lsStyle.indexOf("option") != -1) {
			var loField = goForm[psTarget + "8"];// eval("goForm." + psTarget
			// + "8");
			if (loField != null) {
				loField.value = laValue.option;
			}
			if (laValue.option != null && laValue.option != "") {
				var laTemp = laValue.option.split(";");
				for ( var i = 0; i < laTemp.length; i++) {
					laTemp[i] = "[" + laTemp[i] + "]";
				}
				laDValue.push(laTemp.join(";"));
			}
		}
		if (lsStyle.indexOf("unit") != -1) {
			var loField = goForm["D" + psTarget + "s"];// eval("goForm.D" +
			// psTarget + "s");
			if (loField != null) {
				loField.value = laDValue.join(";");
			}
		}
		if (lsStyle.indexOf("direction") != -1) {
			var loField = goForm[psTarget];// eval("goForm." + psTarget);
			if (loField != null) {
				loField.value = laValue.direction;
			}
			loField = goForm["D" + psTarget];// eval("goForm.D" + psTarget);
			if (laValue.direction != null && laValue.direction != "" && laDirection != null) {
				var laTemp = laValue.direction.split(";");
				for ( var i = 0; i < laTemp.length; i++) {
					for ( var j = 0; j < laDirection.length; j++) {
						var laDTemp = laDirection[j].split("|");
						if (laDTemp[3] + "|" + laDTemp[5] == laTemp[i]) {
							laTemp[i] = laDTemp[0] + "(" + laDTemp[2] + "-" + laDTemp[4] + ")";
							break;
						}
					}
				}
				if (loField != null) {
					loField.value = laTemp.join("\r");
				}
			} else {
				if (loField != null) {
					loField.value = "";
				}
			}
		}
	};
	// dialog.top
	// openDialog("user/select.htm", psTitle, liWidth, liHeight, "laArg", laArg,
	// reValCallback);
	$.get(ctx + "/workflow/user/select2.htm", function(data) {
		$("#dlg_select_user").dialog({
			title : psTitle,
			autoOpen : true,
			resizable : true,
			width : liWidth,
			height : liHeight,
			modal : true,
			close : function() {
				$("#dlg_select_user").dialog("destroy");
				$("#dlg_select_user").html("<div/>");
			},
			open : function() {
				$("#dlg_select_user").html(data);
				// onload事件
				SelectUserLoadEvent(laArg);
				SelectUserInitEvent(laArg);
			},
			buttons : {
				"确定" : function(e) {
					var laReturn = SelectUserOKEvent($("#userSelectForm")[0], laArg);
					reValCallback(laReturn);
					$(this).dialog("close");
				},
				"清空" : function(e) {
					bEmptyValue($("#userSelectForm")[0]);
				},
				"取消" : function(e) {
					$(this).dialog("close");
				}
			}
		});
	});
	// var selector = '<iframe id="select_user_dialog" src="' + ctx
	// + '/workflow/user/select2.htm" frameborder="0"></iframe>';
	// $(selector).dialog({
	// title : psTitle,
	// bgiframe : true,
	// autoOpen : true,
	// resizable : true,
	// width : liWidth,
	// height : liHeight,
	// modal : true,
	// overlay : {
	// background : '#000',
	// opacity : 0.5
	// },
	// open : function(e) {
	// $(this).css("width", "100%");
	// $(this).css("margin", "0");
	// $(this).css("padding", "0");
	// this.contentWindow.selectUserArguments = laArg;
	// }
	// });
}
function bTimerActions(piIndex, event, goForm) {
	switch (piIndex) {
	case 1:
		bSetField("limit", false);
		break;
	case 2:
		SelectUsers("", "duty", event.target.title, null, goForm);
		break;
	case 3:
		SelectUsers("task", "tasks", event.target.title, null, goForm);
		break;
	case 4:
		SelectUsers("", "alarmUser", event.target.title, null, goForm);
		break;
	case 5:
		var reValCallback = function(laValue) {
			if (laValue != null && laValue.length > 0) {
				goForm.alarmFlowName.value = laValue[0];
				goForm.alarmFlowID.value = laValue[1];
			}
		};
		aSelectFlow2(event.target.name, false, event.target.title, goForm.alarmFlowID.value, reValCallback);
		break;
	case 6:
		SelectUsers("", "alarmFlowDoingUser", event.target.title, null, goForm);
		break;
	case 7:
		SelectUsers("", "dueUser", event.target.title, null, goForm);
		break;
	case 8:
		SelectUsers("", "dueToUser", event.target.title, null, goForm);
		break;
	case 9:
		SelectUsers("alltask", "dueToTask", event.target.title, true, goForm);
		break;
	case 10:
		var reValCallback = function(laValue) {
			if (laValue != null && laValue.length > 0) {
				goForm.dueFlowName.value = laValue[0];
				goForm.dueFlowID.value = laValue[1];
			}
		};
		aSelectFlow2(event.target.name, false, event.target.title, goForm.dueFlowID.value, reValCallback);
		break;
	case 11:
		SelectUsers("", "dueFlowDoingUser", event.target.title, null, goForm);
		break;
	}
}
function TimerLoadEvent(timerProperty, laArg) {
	var goForm = $("#timerForm")[0];// document.forms[0];
	if (timerProperty == null) {
		return;
	}
	var laValue = aGetXMLFieldValue(timerProperty, "tasks");
	if (laValue != null) {
		goForm.tasks.value = laValue.join(";");
		for ( var i = 0; i < laValue.length; i++) {
			laValue[i] = "<" + sGetTaskNameByID(laValue[i]) + ">";
		}
		goForm.Dtasks.value = laValue.join(";");
	}
	var laField = [ "alarmObjects", "alarmFlowDoings", "dueObjects", "dueFlowDoings" ];
	for ( var i = 0; i < laField.length; i++) {
		laValue = aGetXMLFieldValue(timerProperty, laField[i]);
		if (laValue != null) {
			bSetFormFieldValue(goForm, laField[i], laValue.join(";"));
		}
	}
	var loNode = timerProperty.selectSingleNode("alarmFlow");
	if (loNode != null) {
		goForm.alarmFlowName.value = loNode.selectSingleNode("name") != null ? loNode
				.selectSingleNode("name").text() : "";
		goForm.alarmFlowID.value = loNode.selectSingleNode("id") != null ? loNode.selectSingleNode("id")
				.text() : "";
	}
	loNode = timerProperty.selectSingleNode("dueFlow");
	if (loNode != null) {
		goForm.dueFlowName.value = loNode.selectSingleNode("name") != null ? loNode.selectSingleNode("name")
				.text() : "";
		goForm.dueFlowID.value = loNode.selectSingleNode("id") != null ? loNode.selectSingleNode("id").text()
				: "";
	}
	laField = [ "duty", "alarmUser", "alarmFlowDoingUser", "dueUser", "dueToUser", "dueFlowDoingUser" ];
	for ( var i = 0; i < laField.length; i++) {
		bGetUnitXMLToField(timerProperty, goForm, laField[i]);
	}
	laField = [ "name", "limitTimeType", "limitTime1", "limitTime", "limitUnit", "affectMainFlow",
			"enableAlarm", "enableDueDoing", "alarmTime", "alarmUnit", "alarmFrequency", "dueTime",
			"dueUnit", "dueFrequency", "dueAction", "dueToTask" ];
	for ( var i = 0; i < laField.length; i++) {
		var lsValue = (timerProperty.selectSingleNode(laField[i]) != null) ? timerProperty.selectSingleNode(
				laField[i]).text() : null;
		if (lsValue != null && lsValue != "") {
			bSetFormFieldValue(goForm, laField[i], lsValue);
		}
	}
	if (goForm.dueToTask.value != "") {
		goForm.DdueToTask.value = "<" + sGetTaskNameByID(goForm.dueToTask.value) + ">";
	}
}
function TimerInitEvent(timerProperty, laArg) {
	var goForm = $("#timerForm")[0];
	// 办理时限
	$("#limitTimeType").change(function() {
		if ($(this).val() == 1) {
			$("#limitTimeType_1").show();
			$("#limitTimeType_2").hide();
		} else {
			$("#limitTimeType_1").hide();
			$("#limitTimeType_2").show();
		}
	}).trigger("change");
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var formUuid = loNode != null ? loNode.text() : "";
	JDS.call({
		service : "flowSchemeService.getFormFields",
		data : [ -1, formUuid ],
		success : function(result) {
			onGetFormFields("#limitTimeLabel", goForm, result.data);
			$("#limitTimeLabel").val($("#limitTime").val());
		}
	});
	$("#limitTimeLabel").change(function() {
		$("#limitTime").val($(this).val());
	});

	// 责任人
	$("#Ddutys").click(function(e) {
		e.target.title = "选择责任人";
		bTimerActions(2, e, goForm);
	});
	// 计时环节
	$("#Dtasks").click(function(e) {
		e.target.title = "选择计时环节";
		bTimerActions(3, e, goForm);
	});

	// 计时暂停/恢复影响主流程、预警提醒、逾期处理
	$("#affectMainFlow, #enableAlarm, #enableDueDoing").change(function(e) {
		if (this.checked) {
			this.value = "1";
			if (this.id === "enableAlarm") {
				$("#config_enableAlarm").show();
			} else if (this.id === "enableDueDoing") {
				$("#config_enableDueDoing").show();
			}
		} else {
			this.value = "0";
			if (this.id === "enableAlarm") {
				$("#config_enableAlarm").hide();
			} else if (this.id === "enableDueDoing") {
				$("#config_enableDueDoing").hide();
			}
		}
	}).trigger("change");

	// 预警提醒
	$("input[name=alarmTime], input[name=alarmFrequency]").blur(function(e) {
		if (bIsDigital(this.value, 1, "请输入数字!") == false) {
			this.value = '1';
			$(this).trigger("focus");
		}
	});
	// 选择消息提醒人员
	$("#DalarmUsers").click(function(e) {
		e.target.title = "选择消息提醒人员";
		bTimerActions(4, e, goForm);
	});
	// 选择要发起的预警流程
	$("#alarmFlowName").click(function(e) {
		e.target.title = "选择要发起的预警流程";
		bTimerActions(5, e, goForm);
	});
	// 其他办理人员
	$("#DalarmFlowDoingUsers").click(function(e) {
		e.target.title = "其他办理人员";
		bTimerActions(6, e, goForm);
	});

	// 逾期处理
	$("input[name=dueTime], input[name=dueFrequency]").blur(function(e) {
		if (bIsDigital(this.value, 1, "请输入数字!") == false) {
			this.value = '1';
			$(this).trigger("focus");
		}
	});
	// 其他消息通知人员
	$("#DdueUsers").click(function(e) {
		e.target.title = "其他消息通知人员";
		bTimerActions(7, e, goForm);
	});
	// 选择移交人员
	$("#DdueToUsers").click(function(e) {
		e.target.title = "选择移交人员";
		bTimerActions(8, e, goForm);
	});
	// 选择转办环节
	$("#DdueToTask").click(function(e) {
		e.target.title = "选择转办环节";
		bTimerActions(9, e, goForm);
	});
	// 选择要发起的催办流程
	$("#dueFlowName").click(function(e) {
		e.target.title = "选择要发起的催办流程";
		bTimerActions(10, e, goForm);
	});
	// 其他办理人员
	$("#DdueFlowDoingUsers").click(function(e) {
		e.target.title = "其他办理人员";
		bTimerActions(11, e, goForm);
	});

	$("#alarmObjects_Other").change(function() {
		if ($(this).attr("checked") === "checked") {
			$("#ID_AlarmOtherUser").show();
		} else {
			$("#ID_AlarmOtherUser").hide();
		}
	}).trigger("change");
	$("#alarmFlowDoings_Other").change(function() {
		if ($(this).attr("checked") === "checked") {
			$("#ID_AlarmFlowDoing").show();
		} else {
			$("#ID_AlarmFlowDoing").hide();
		}
	}).trigger("change");
	$("#dueObjects_Other").change(function() {
		if ($(this).attr("checked") === "checked") {
			$("#ID_DueToOtherUser").show();
		} else {
			$("#ID_DueToOtherUser").hide();
		}
	}).trigger("change");
	$("#dueFlowDoings_Other").change(function() {
		if ($(this).attr("checked") === "checked") {
			$("#ID_DueFlowDoing").show();
		} else {
			$("#ID_DueFlowDoing").hide();
		}
	}).trigger("change");
	$("#dueObjects_Doing").change(function() {
		if ($(this).attr("checked") === "checked") {
			$("#ID_DueMsgToDoing").show();
		} else {
			$("#ID_DueMsgToDoing").hide();
		}
	}).trigger("change");

	$("select[name=dueAction]").change(function() {
		if (this.value == "4") {
			$("#ID_DueToUser").show();
		} else {
			$("#ID_DueToUser").hide();
		}
		if (this.value == "16") {
			$("#ID_DueToTask").show();
		} else {
			$("#ID_DueToTask").hide();
		}
	}).trigger("change");
}
function TimerOKCheck(timerProperty, laArg) {
	var goForm = $("#timerForm")[0];
	var lsOldName = "";
	if (timerProperty != null && timerProperty.selectSingleNode("name") != null) {
		lsOldName = timerProperty.selectSingleNode("name").text();
	}
	if (goForm.name.value == "") {
		alert("请输入名称!");
		return false;
	} else {
		var gsExistNames = ";" + laArg["Names"] + ";";
		if (gsExistNames.indexOf(";" + goForm.name.value + ";") != -1 && goForm.name.value != lsOldName) {
			alert("名称已存在，请重新输入!");
			goForm.name.focus();
			return false;
		} else {
			if (goForm.limitTimeType.value == "1" && goForm.limitTime1.value == "") {
				alert("请输入办理时限!");
				return false;
			}
			if (goForm.limitTimeType.value == "2" && goForm.limitTime.value == "") {
				alert("请输入办理时限!");
				return false;
			} else if (goForm.Ddutys.value == "") {
				// alert("请指定责任人!");
				// return false;
			} else {
				if (goForm.Dtasks.value == "") {
					alert("请指定计时环节!");
					return false;
				}
			}
		}
	}
	return true;
}
function TimerOKEvent(timerProperty, laArg) {
	var goForm = $("#timerForm")[0];
	if (timerProperty == null) {
		timerProperty = $(goWorkFlow.xmlDOM.createElement("timer"));
		var loNode = goWorkFlow.flowXML.selectSingleNode("timers");
		loNode.appendChild(timerProperty);
	}
	// var timerName = timerProperty.selectSingleNode("name");
	// if (timerName != null) {
	// timerProperty.removeChild(timerName);
	// }
	// oAddElement(timerProperty, "name", goForm.name.value);
	oSetElement(timerProperty, "name", goForm.name.value);
	bSetXMLFieldValue(timerProperty, "tasks", goForm.tasks.value != "" ? goForm.tasks.value.split(";") : null);
	var laField = [ "alarmObjects", "alarmFlowDoings", "dueObjects", "dueFlowDoings" ];
	for ( var i = 0; i < laField.length; i++) {
		var lsValue = sGetFormFieldValue(goForm, laField[i]);
		bSetXMLFieldValue(timerProperty, laField[i], lsValue != "" ? lsValue.split(";") : null);
	}
	var loNode = oSetElement(timerProperty, "alarmFlow");
	oSetElement(loNode, "name", goForm.alarmFlowName.value);
	oSetElement(loNode, "id", goForm.alarmFlowID.value);
	loNode = oSetElement(timerProperty, "dueFlow");
	oSetElement(loNode, "name", goForm.dueFlowName.value);
	oSetElement(loNode, "id", goForm.dueFlowID.value);
	var laField = [ "duty", "alarmUser", "alarmFlowDoingUser", "dueUser", "dueToUser", "dueFlowDoingUser" ];
	for ( var i = 0; i < laField.length; i++) {
		bSetUnitFieldToXML(timerProperty, goForm, laField[i]);
	}
	laField = [ "name", "limitTimeType", "limitTime1", "limitTime", "limitUnit", "affectMainFlow",
			"enableAlarm", "enableDueDoing", "alarmTime", "alarmUnit", "alarmFrequency", "dueTime",
			"dueUnit", "dueFrequency", "dueAction", "dueToTask" ];
	for ( var i = 0; i < laField.length; i++) {
		oSetElement(timerProperty, laField[i], sGetFormFieldValue(goForm, laField[i]));
	}
	return goForm.name.value;
	// $.dialog.data("timerReVal", goForm.name.value);
	// $.dialog.close();
	// window.returnValue = goForm.name.value;
	// window.close();
}
function aGetXMLFieldValue(poXML, psFieldName) {
	var loNode = poXML.selectSingleNode(psFieldName);
	if (loNode == null) {
		return null;
	}
	var laValue = new Array();
	var laUnit = loNode.selectNodes("unit");
	if (laUnit == null) {
		return laValue;
	}
	laUnit.each(function(index) {
		laValue.push($(this).text());
	});

	if (laValue.length > 0) {
		return laValue;
	} else {
		return null;
	}
}
function aSetButtonRight(input, path, laValue) {
	var laNode = goWorkFlow.dictionXML.selectNodes(path);
	if (laNode != null && laNode.length != 0) {
		for ( var i = 0; i < laNode.length; i++) {
			var lsValue = $(laNode[i]).selectSingleNode("value").text();
			var lbFlag = false;
			if (laValue == null) {
				if ($(laNode[i]).selectSingleNode("isDefault").text() == "1") {
					lbFlag = true;
				}
			} else {
				for ( var j = 0; j < laValue.length; j++) {
					if (lsValue == laValue[j]) {
						break;
					}
				}
				if (j < laValue.length) {
					lbFlag = true;
				}
			}
			if (lbFlag == true) {
				bAddEntry(input, $(laNode[i]).selectSingleNode("name").text(), lsValue, true);
			}
		}
	}
	// bShowHideUntread();
}
function bShowHideUntread() {
	var lsValues = ";" + sGetAllEntryValue(goForm.DTaskRight) + ";";
	if (lsValues.toLowerCase().indexOf(";untread;") != -1) {
		ID_UntreadType.style.display = "";
		ID_Untread.style.display = (goForm.untreadType.value == "3") ? "" : "none";
	} else {
		ID_UntreadType.style.display = "none";
		ID_Untread.style.display = "none";
	}
	return true;
}
function bGetForm() {
	var loNode = goWorkFlow.flowXML.selectSingleNode("./property/formID");
	var lsFormName = loNode != null ? loNode.text() : "";
	return lsFormName;
};
function bEmptyValue(goForm) {
	goForm.DUnits.value = "";
	goForm.Units.value = "";
	goForm.FieldLabels.value = "";
	goForm.Fields.value = "";
	if (goForm.Tasks) {
		if (goForm.Tasks.length != null && goForm.Tasks.length > 0) {
			for ( var i = 0; i < goForm.Tasks.length; i++)
				goForm.Tasks[i].checked = false;
		} else {
			goForm.Tasks.checked = false;
		}
	}
	if (goForm.Directions) {
		if (goForm.Directions.length != null && goForm.Directions.length > 0) {
			for ( var i = 0; i < goForm.Directions.length; i++)
				goForm.Directions[i].checked = false;
		} else {
			goForm.Directions.checked = false;
		}
	}
	for ( var i = 0; i < goForm.UserOptions.length; i++)
		goForm.UserOptions[i].checked = false;
}
function SelectUserOKEvent(goForm, laArg) {
	var laReturn = new Array();
	var lsStyle = laArg.Style;
	if (lsStyle.indexOf("unit") != -1) {
		var laValue = new Array();
		laValue.push(goForm.DUnits.value);
		laValue.push(goForm.Units.value);
		laReturn.unit = laValue;
	}
	if (lsStyle.indexOf("bizRole") != -1) {
		var laValue = new Array();
		laValue.push(goForm.DBizRoles.value);
		laValue.push(goForm.BizRoles.value);
		laReturn.bizRole = laValue;
	}
	if (lsStyle.indexOf("field") != -1) {
		laReturn.field = goForm.Fields.value;
	}
	if (lsStyle.indexOf("task") != -1) {
		laReturn.task = sGetFormFieldValue(goForm, "Tasks");
	}
	if (lsStyle.indexOf("option") != -1) {
		laReturn.option = sGetFormFieldValue(goForm, "UserOptions");
	}
	if (lsStyle.indexOf("direction") != -1) {
		laReturn.direction = sGetFormFieldValue(goForm, "Directions");
	}
	return laReturn;
	// $.dialog.data("laValue", laReturn);
	// window.returnValue = laReturn;
	// window.close();
}
function SelectUserLoadEvent(laArg) {
	var goForm = $("#userSelectForm")[0];// document.forms[0];
	var lsStyle = laArg.Style;
	// ID_Unit.style.display = (lsStyle.indexOf("unit") != -1) ? "" : "none";
	if (lsStyle.indexOf("unit") != -1) {
		$("#ID_Unit").show();
	} else {
		$("#ID_Unit").hide();
	}
	if (lsStyle.indexOf("bizRole") != -1) {
		$("#ID_Biz_Role").show();
	} else {
		$("#ID_Biz_Role").hide();
	}
	// ID_Field.style.display = (lsStyle.indexOf("field") != -1) ? "" : "none";
	if (lsStyle.indexOf("field") != -1) {
		$("#ID_Field").show();
	} else {
		$("#ID_Field").hide();
	}
	// ID_Task.style.display = (lsStyle.indexOf("task") != -1) ? "" : "none";
	if (lsStyle.indexOf("task") != -1) {
		$("#ID_Task").show();
	} else {
		$("#ID_Task").hide();
	}
	// ID_Direction.style.display = (lsStyle.indexOf("direction") != -1) ? ""
	// : "none";
	if (lsStyle.indexOf("direction") != -1) {
		$("#ID_Direction").show();
	} else {
		$("#ID_Direction").hide();
	}
	// ID_Option_0.style.display = (lsStyle.indexOf("option") != -1) ? "" :
	// "none";
	if (lsStyle.indexOf("option") != -1) {
		$("#ID_Option_0").show();
	} else {
		$("#ID_Option_0").hide();
	}
	// ID_Option_1.style.display = (lsStyle.indexOf("option") != -1) ? "" :
	// "none";
	if (lsStyle.indexOf("option") != -1) {
		$("#ID_Option_1").show();
	} else {
		$("#ID_Option_1").hide();
	}

	if (lsStyle.indexOf("task") != -1 && lsStyle.indexOf("unit") == -1) {
		// ID_TaskList.style.height = ID_TaskList.offsetHeight + 100;
		$("#ID_TaskList").height($("#ID_TaskList").scrollTop() + 100);
	} else {
		$("#ID_TaskList").css("height", 100);
	}
	if (lsStyle.indexOf("direction") != -1 && lsStyle.indexOf("unit") == -1) {
		// ID_DirectionList.style.height = ID_DirectionList.offsetHeight + 100;
		$("#ID_DirectionList").height($("#ID_DirectionList").scrollTop() + 140);
	}
	var laTask = laArg.Tasks;
	if (laTask != null) {
		var lsHTML = "";
		var lsType = (laArg.MulTask == true) ? "checkbox" : "radio";
		for ( var i = 0; i < laTask.length; i++) {
			var laValue = laTask[i].split("|");
			lsHTML += '<INPUT TYPE="' + lsType + '" class="AutoSize" NAME="Tasks" VALUE="' + laValue[1]
					+ '" id="tasks_' + i + '"><label for="tasks_' + i + '">' + laValue[0] + "</label><br>";
		}
		// ID_TaskList.innerHTML = lsHTML;
		$("#ID_TaskList").html(lsHTML);
	}
	var laDirection = laArg.Directions;
	if (laDirection != null) {
		var lsHTML = "";
		for ( var i = 0; i < laDirection.length; i++) {
			var laValue = laDirection[i].split("|");
			var lsText = laValue[0] + "(" + laValue[2] + "-" + laValue[4] + ")";
			var lsValue = laValue[3] + "|" + laValue[5];
			lsHTML += '<INPUT TYPE=checkbox class="AutoSize" NAME="Directions" VALUE="' + lsValue
					+ '" id="directions_' + i + '"><label for="directions_' + i + '">' + lsText
					+ "</label><br>";
		}
		// ID_DirectionList.innerHTML = lsHTML;
		$("#ID_DirectionList").html(lsHTML);
	}
	var laValue = laArg.unit;
	if (laValue != null) {
		// goForm.DUnits.value = laValue[0];
		$("#DUnits").val(laValue[0]);
		// goForm.Units.value = laValue[1];
		$("#Units").val(laValue[1]);
	}
	var bizRole = laArg.bizRole;
	if (laValue != null && bizRole != null) {
		// goForm.DUnits.value = laValue[0];
		$("#DBizRoles").val(bizRole[0]);
		// goForm.Units.value = laValue[1];
		$("#BizRoles").val(bizRole[1]);
	}
	var lsValue = laArg.field;
	goForm.Fields.value = lsValue;
	lsValue = laArg.task;
	if (goForm.Tasks && lsValue != null) {
		bSetFormFieldValue(goForm, "Tasks", lsValue);
	}
	lsValue = laArg.option;
	if (lsValue != null) {
		bSetFormFieldValue(goForm, "UserOptions", lsValue);
	}
	lsValue = laArg.direction;
	if (goForm.Directions && lsValue != null) {
		bSetFormFieldValue(goForm, "Directions", lsValue);
	}
}
function SelectUserInitEvent(laArg) {
	var goForm = $("#userSelectForm")[0];
	// 组织机构
	$("#DUnits").click(function() {
		SetUnit();
	});

	function SetUnit(target, title) {
		$.unit.open({
			initNames : goForm.DUnits.value,
			initIDs : goForm.Units.value,
			title : "人员选择",
			multiple : true,
			selectType : 1,
			nameType : "21",
			showType : true,
			type : "MyUnit",
			loginStatus : false,
			excludeValues : null,
			afterSelect : function(laValue) {
				if (laValue == null) {
					return;
				}
				goForm.DUnits.value = laValue["name"];
				goForm.Units.value = laValue["id"];
			}
		});
	}

	// 单位业务角色
	var bizRoleSetting = {
		async : {
			otherParam : {
				"serviceName" : "flowSchemeService",
				"methodName" : "getBizRoles",
				"data" : []
			}
		}
	};
	$("#DBizRoles").comboTree({
		labelField : "DBizRoles",
		valueField : "BizRoles",
		autoInitValue : false,
		// initService : "flowSchemeService.getFormFieldByFieldNames",
		// initServiceParam : [ bGetForm() ],
		treeSetting : bizRoleSetting,
		width : 488,
		height : 220
	});

	// 表单域
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
		initService : "flowSchemeService.getFormFieldByFieldNames",
		initServiceParam : [ bGetForm() ],
		treeSetting : setting,
		width : 488,
		height : 220
	});
}
function sGetAllEntryValue(poField, pbSelect, psSeparator) {
	var lsReturn = "";
	var lsSeparator = (psSeparator != null && psSeparator != "") ? psSeparator : ";";
	for ( var i = 0; i < poField.options.length; i++) {
		if (poField.options[i].value == "") {
			continue;
		}
		if (pbSelect == true && poField.options[i].selected == false) {
			continue;
		}
		if (lsReturn == "") {
			lsReturn = poField.options[i].value;
		} else {
			lsReturn += lsSeparator + poField.options[i].value;
		}
	}
	return lsReturn;
}
function bMoveEntry(poFromField, poToField) {
	var liLength = 0, lsText, lsValue;
	var loFrom = poFromField;
	var loTo = poToField;
	if (loFrom.length == 0) {
		return false;
	}
	if (loFrom.selectedIndex == -1) {
		return false;
	}
	if (loTo != null) {
		liLength = loTo.options.length;
		for ( var i = 0; i < loFrom.options.length; i++) {
			if (loFrom.options[i].selected == false) {
				continue;
			}
			lsText = loFrom.options[i].text;
			lsValue = loFrom.options[i].value;
			if (lsValue == "00000" || lsValue == "") {
				continue;
			}
			var loEntry = new Option(lsText, lsValue);
			if (1 == loTo.options.length && loTo.options[0].text == "") {
				loTo.options[0] = loEntry;
			} else {
				loTo.options[liLength] = loEntry;
				liLength++;
			}
		}
	}
	var liIndex = -1;
	for ( var i = loFrom.options.length - 1; i >= 0; i--) {
		if (loFrom.options[i].selected == false) {
			continue;
		}
		if (loFrom.options[i].value == "00000" || loFrom.options[i].value == "") {
			continue;
		}
		loFrom.options[i] = null;
		liIndex = i;
	}
	if (liIndex > -1 && liIndex < loFrom.options.length) {
		loFrom.options[liIndex].selected = true;
	} else {
		if (loFrom.options.length > 0) {
			loFrom.options[loFrom.options.length - 1].selected = true;
		}
	}
}
function bAddEntry(poField, psText, psValue, pbNoSelect) {
	if (poField == null || psText == null || psValue == null) {
		return false;
	}
	var loEntry = new Option(psText, psValue);
	if (1 == poField.options.length && poField.options[0].text == "") {
		poField.options[0] = loEntry;
	} else {
		poField.options[poField.options.length] = loEntry;
	}
	if (pbNoSelect != true) {
		poField.selectedIndex = poField.options.length - 1;
	}
	return true;
}
function bInsertEntry(poField, psText, psValue) {
	if (poField == null || psText == null || psValue == null) {
		return false;
	}
	if (poField.selectedIndex == -1) {
		return bAddEntry(poField, psText, psValue);
	}
	var liLength = poField.options.length;
	var liIndex = poField.selectedIndex;
	poField.options[liLength] = new Option("", "");
	for ( var i = liLength; i > liIndex; i--) {
		poField.options[i].text = poField.options[i - 1].text;
		poField.options[i].value = poField.options[i - 1].value;
	}
	poField.options[i].text = psText;
	poField.options[i].value = psValue;
	return true;
}
function bEditEntry(poField, psText, psValue) {
	if (poField == null || psText == null || psValue == null) {
		return false;
	}
	if (poField.length == 0) {
		return false;
	}
	if (poField.selectedIndex == -1) {
		return false;
	}
	var loEntry = new Option(psText, psValue);
	var liIndex = poField.selectedIndex;
	poField.options[liIndex].text = psText;
	poField.options[liIndex].value = psValue;
	poField.selectedIndex = liIndex;
	return true;
}
function bSetXMLFieldValue(poXML, psFieldName, paValue) {
	var loNode = poXML.selectSingleNode(psFieldName);
	if (loNode != null) {
		poXML.removeChild(loNode);
	}
	loNode = oAddElement(poXML, psFieldName);
	if (paValue != null) {
		for ( var i = 0; i < paValue.length; i++) {
			var loUnit = oAddElement(loNode, "unit", paValue[i]);
			loUnit.setAttribute("type", "32");
		}
	}
	return true;
}
function bSetUnitFieldToXML(poXML, poForm, psFieldName) {
	var loNode = poXML.selectSingleNode(psFieldName + "s");
	if (loNode != null && loNode.length != 0) {
		poXML.removeChild(loNode);
	}
	loNode = oAddElement(poXML, psFieldName + "s");
	var loDField = poForm["D" + psFieldName + "1"];// eval("poForm.D" +
	// psFieldName + "1");
	var loField = poForm[psFieldName + "1"];// eval("poForm." + psFieldName +
	// "1");
	if (loDField != null && loDField.value != "" && loField != null && loField.value != "") {
		var laValue1 = loDField.value.split(";");
		var laValue2 = loField.value.split(";");
		for ( var i = 0; i < laValue1.length; i++) {
			var loUnit = oAddElement(loNode, "unit");
			loUnit.setAttribute("type", "1");
			oAddElement(loUnit, "value", laValue2[i]);
			oAddElement(loUnit, "argValue", laValue1[i]);
		}
	}
	loDField = poForm["D" + psFieldName + "16"];// eval("poForm.D" +
	// psFieldName + "16");
	loField = poForm[psFieldName + "16"];// eval("poForm." + psFieldName +
	// "16");
	if (loDField != null && loDField.value != "" && loField != null && loField.value != "") {
		var laValue1 = loDField.value.split(";");
		var laValue2 = loField.value.split(";");
		for ( var i = 0; i < laValue1.length; i++) {
			var loUnit = oAddElement(loNode, "unit");
			loUnit.setAttribute("type", "16");
			oAddElement(loUnit, "value", laValue2[i]);
			oAddElement(loUnit, "argValue", laValue1[i]);
		}
	}
	var laFlag = [ "2", "4", "8" ];
	for ( var j = 0; j < laFlag.length; j++) {
		loField = poForm[psFieldName + laFlag[j]];// eval("poForm." +
		// psFieldName + laFlag[j]);
		if (loField != null && loField.value != "") {
			var laValue = loField.value.split(";");
			for ( var i = 0; i < laValue.length; i++) {
				var loUnit = oAddElement(loNode, "unit");
				loUnit.setAttribute("type", laFlag[j]);
				oAddElement(loUnit, "value", laValue[i]);
			}
		}
	}
	return true;
}

function bGetUnitXMLToField(poXML, poForm, psFieldName) {
	var loNode = poXML.selectSingleNode(psFieldName + "s");
	if (loNode == null) {
		return false;
	}
	var laUnit = loNode.selectNodes("unit");
	if (laUnit == null || laUnit.length == 0) {
		return false;
	}
	var laAllValue = new Array();
	var laDValue1 = new Array();
	var laValue1 = new Array();
	var laValue2 = new Array();
	var laValue4 = new Array();
	var laValue8 = new Array();
	var laDValue16 = new Array();
	var laValue16 = new Array();
	for ( var i = 0; i < laUnit.length; i++) {
		switch (laUnit[i].getAttribute("type")) {
		case "1":
			laDValue1.push($(laUnit[i]).selectSingleNode("argValue").text());
			laValue1.push($(laUnit[i]).selectSingleNode("value").text());
			laAllValue.push($(laUnit[i]).selectSingleNode("argValue").text());
			break;
		case "2":
			var lsValue = $(laUnit[i]).selectSingleNode("value").text();
			laValue2.push(lsValue);
			laAllValue.push("{" + lsValue + "}");
			break;
		case "4":
			var lsValue = $(laUnit[i]).selectSingleNode("value").text();
			laValue4.push(lsValue);
			lsValue = sGetTaskNameByID(lsValue);
			laAllValue.push("<" + lsValue + ">");
			break;
		case "8":
			var lsValue = $(laUnit[i]).selectSingleNode("value").text();
			laValue8.push(lsValue);
			laAllValue.push("[" + lsValue + "]");
			break;
		case "16":
			laDValue16.push($(laUnit[i]).selectSingleNode("argValue").text());
			laValue16.push($(laUnit[i]).selectSingleNode("value").text());
			laAllValue.push($(laUnit[i]).selectSingleNode("argValue").text());
			break;
		}
	}
	var loField = poForm["D" + psFieldName + "s"];// eval("poForm.D" +
	// psFieldName + "s");
	if (loField != null) {
		loField.value = laAllValue.join(";");
	}
	loField = poForm["D" + psFieldName + "1"];// eval("poForm.D" + psFieldName
	// + "1");
	if (loField != null) {
		loField.value = laDValue1.join(";");
	}
	loField = poForm[psFieldName + "1"];// eval("poForm." + psFieldName + "1");
	if (loField != null) {
		loField.value = laValue1.join(";");
	}
	loField = poForm["D" + psFieldName + "16"];// eval("poForm.D" + psFieldName
	// + "16");
	if (loField != null) {
		loField.value = laDValue16.join(";");
	}
	loField = poForm[psFieldName + "16"];// eval("poForm." + psFieldName +
	// "16");
	if (loField != null) {
		loField.value = laValue16.join(";");
	}
	loField = poForm[psFieldName + "2"];// eval("poForm." + psFieldName + "2");
	if (loField != null) {
		loField.value = laValue2.join(";");
	}
	loField = poForm[psFieldName + "4"];// eval("poForm." + psFieldName + "4");
	if (loField != null) {
		loField.value = laValue4.join(";");
	}
	loField = poForm[psFieldName + "8"];// eval("poForm." + psFieldName + "8");
	if (loField != null) {
		loField.value = laValue8.join(";");
	}
	return true;
}
function bIsDigital() {
	var laArg = bIsDigital.arguments;
	if (laArg.length < 1) {
		return false;
	}
	var lsValue = laArg[0];
	var liType = (laArg.length > 1) ? laArg[1] : 3;
	var lsPrompt = (laArg.length > 2) ? laArg[2] : sGetLang("P_NOTDIGITAL");
	if (lsValue + "" == "") {
		if (lsPrompt != "") {
			alert(lsPrompt);
		}
		return false;
	}
	var regInteger = /^[+-]?(0|([1-9]\d*))$/;
	var regDecimal = /^[+-]?(0|([1-9]\d*))\.\d+$/;
	var regDigital = /^[+-]?(((0|([1-9]\d*)))|((0|([1-9]\d*))\.\d+))$/;
	var regIntegerP = /^[+]?[1-9]\d*$/;
	var regIntegerN = /^\-[1-9]\d*$/;
	var regDecimalP = /^[+]?(([1-9]\d*\.\d+)|(0\.\d*[1-9]\d*))$/;
	var regDecimalN = /^\-(([1-9]\d*\.\d+)|(0\.\d*[1-9]\d*))$/;
	var regDigitalP = /^[+]?(([1-9]\d*)|([1-9]\d*\.\d+)|(0\.\d*[1-9]\d*))$/;
	var regDigitalN = /^\-(([1-9]\d*)|([1-9]\d*\.\d+)|(0\.\d*[1-9]\d*))$/;
	var regIntegerNN = /^(([+-]?0)|([+]?[1-9]\d*))$/;
	var regIntegerNP = /^(([+-]?0)|(\-[1-9]\d*))$/;
	var regDecimalNN = /^(([+]?(([1-9]\d*\.\d+)|(0\.\d+)))|([+-]?0\.[0]+))$/;
	var regDecimalNP = /^((\-(([1-9]\d*\.\d+)|(0\.\d+)))|([+-]?0\.[0]+))$/;
	var regDigitalNN = /^(([+-]?0)|([+]?[1-9]\d*)|([+]?(0|([1-9]\d*))\.\d+)|([+-]?0\.[0]+))$/;
	var regDigitalNP = /^(([+-]?0)|(\-[1-9]\d*)|(\-(0|([1-9]\d*))\.\d+)|([+-]?0\.[0]+))$/;
	var lbCheck = true;
	if (liType == 1 && lsValue.match(regInteger) == null) {
		lbCheck = false;
	} else {
		if (liType == 2 && lsValue.match(regDecimal) == null) {
			lbCheck = false;
		} else {
			if (liType == 3 && lsValue.match(regDigital) == null) {
				lbCheck = false;
			} else {
				if (liType == 4 && lsValue.match(regIntegerP) == null) {
					lbCheck = false;
				} else {
					if (liType == -4 && lsValue.match(regIntegerN) == null) {
						lbCheck = false;
					} else {
						if (liType == 5 && lsValue.match(regDecimalP) == null) {
							lbCheck = false;
						} else {
							if (liType == -5 && lsValue.match(regDecimalN) == null) {
								lbCheck = false;
							} else {
								if (liType == 6 && lsValue.match(regDigitalP) == null) {
									lbCheck = false;
								} else {
									if (liType == -6 && lsValue.match(regDigitalN) == null) {
										lbCheck = false;
									} else {
										if (liType == 7 && lsValue.match(regIntegerNN) == null) {
											lbCheck = false;
										} else {
											if (liType == -7 && lsValue.match(regIntegerNP) == null) {
												lbCheck = false;
											} else {
												if (liType == 8 && lsValue.match(regDecimalNN) == null) {
													lbCheck = false;
												} else {
													if (liType == -8 && lsValue.match(regDecimalNP) == null) {
														lbCheck = false;
													} else {
														if (liType == 9
																&& lsValue.match(regDigitalNN) == null) {
															lbCheck = false;
														} else {
															if (liType == -9
																	&& lsValue.match(regDigitalNP) == null) {
																lbCheck = false;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	if (!lbCheck && lsPrompt != "") {
		alert(lsPrompt);
	}
	return lbCheck;
}
function onGetFormFields(select, form, data) {
	/*modified by huanglinchuan 2014.10.23 begin*/
	$(select, form).html("");
	/*modified by huanglinchuan 2014.10.23 end*/
	$.each(data, function() {
		var option = '<option value="' + this.data + '">' + this.name + '</option>';
		/*modified by huanglinchuan 2014.10.23 begin*/
		if(select=="select[name=fields1]"){
			var createWay=$("#createWay",$("#newFlowForm")).val();
			var subformIdName=$("#subformId > option:selected",$("#newFlowForm")).text();
			if(this.children.length>0&&createWay=="2"&&subformIdName==this.name){
				$.each(this.children,function(){
					if(this.name!="添加删除行"){
						var option = '<option value="' + this.data + '">' +subformIdName+"."+ this.name + '</option>';
						$(select, form).append(option);
					}
				});
			}else{
				$(select, form).append(option);
			}
		}else{
			$(select, form).append(option);
		}		
		/*modified by huanglinchuan 2014.10.23 end*/
	});
}
function showToolMsg() {
	var lsHTML = goSelectedTool.innerHTML;
	lsHTML = lsHTML.substring(lsHTML.indexOf("ico_") + 4, lsHTML.indexOf(".gif"));
	goSelectedTool.Type = lsHTML.toUpperCase();
	switch (goSelectedTool.Type) {
	case "BEELINE":
		var lsMsg = sGetLang("FLOW_WF_BEELINE_NAME");
		break;
	case "CURVE":
		var lsMsg = sGetLang("FLOW_WF_CURVE_NAME");
		break;
	case "TASK":
		var lsMsg = sGetLang("FLOW_WF_TASK_NAME");
		break;
	case "SUBFLOW":
		var lsMsg = sGetLang("FLOW_WF_SUBFLOW_NAME");
		break;
	case "FREETASK":
		var lsMsg = sGetLang("FLOW_WF_FREETASK_NAME");
		break;
	case "CONDITION":
		var lsMsg = sGetLang("FLOW_WF_CONDITION_NAME");
		break;
	case "BEGIN":
		var lsMsg = sGetLang("FLOW_WF_BEGIN_NAME");
		break;
	case "END":
		var lsMsg = sGetLang("FLOW_WF_END_NAME");
		break;
	case "LABEL":
		var lsMsg = sGetLang("FLOW_WF_LABEL_NAME");
		break;
	default:
		showMsg("");
		return;
	}
	showMsg(sGetLang("FLOW_WF_ADDOBJECT") + lsMsg);
}
function showMsg(psMsg, pbError) {// 显示消息；
	if (pbError == true) {
		$("#ID_MESSAGE").html("<span style='color:red'>" + psMsg + "</span>");
	} else {
		$("#ID_MESSAGE").html(psMsg);
	}
}