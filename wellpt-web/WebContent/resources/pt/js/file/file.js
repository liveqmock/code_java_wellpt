I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	// 初使化
	var needDif = "0";// 需要对比
	var uuid = $("#uuid").val();
	var bean = {
		"uuid" : null,
		"creator" : null,
		"createTime" : null,
		"modifier" : null,
		"modifyTime" : null,
		"attach" : null,
		"title" : null,
		"author" : null,
		"fileType" : null,
		"fmFolder" : null,
		"folderRole" : [],
		"showTitles" : null,
		"status" : null,
		"flowInstUuid" : null,
		"isLock" : null,
		"currentVersion" : null,
		"parentFolderIds" : null,
		"fileVersions" : [],
		"reservedText1" : null,
		"reservedNumber1" : null,
		"reservedDate1" : null,
		"reservedText2" : null,
		"reservedNumber2" : null,
		"reservedDate2" : null,
		"reservedNumber3" : null,
		"reservedText3" : null,
		"reservedText4" : null,
		"reservedText5" : null,
		"reservedText6" : null,
		"reservedText7" : null,
		"reservedText8" : null,
		"initId" : null,
		"sourceFileId" : null,
		"dynamicFormId" : null,
		"dynamicDataId" : null,
		"folderId" : null,
		"remark" : null,
		"editFile" : null,
		"editFileChange" : null,
		"readFile" : null,
		"readFileChange" : null,
		"uploadFileNames" : null,
		"uploadFileIds" : null
	};
	var customformData = {};
	var historyVersionDialogOptions = {
		autoOpen : false,
		bgiframe : true,
		modal : true,
		title : fileManager.historyVersion,
		overlay : {
			backgroundColor : '#000',
			opacity : 0.5
		},
		buttons : {

		}
	};

	historyVersionDialogOptions.buttons[fileManager.btnClose] = function() {
		$("#historyVersionDialog").oDialog('close');
	};

	var showDifDialogOptions = {
		autoOpen : false,
		bgiframe : true,
		title : fileManager.fileIn,
		modal : true,
		height : 300,
		width : 400,
		overlay : {
			backgroundColor : '#000',
			opacity : 0.5
		},
		buttons : {}
	};

	showDifDialogOptions.buttons[fileManager.buttonYes] = function() {
		$("#showDifDialog").oDialog('close');

		if ($("#operateInType").val() == "versionSave") {
			isDifing = "1";
			customformData.currentAction = "SAVE_NEW_VERSION";// 保存成新版本
			customformData.currentSetVersion = $("#currentSetVersion").val();
			customformData.versionRemark = $("#versionRemark").val();
			$("#status").val("2");

			submit();
		}

		if ($("#operateInType").val() == "versionCover") {
			isDifing = "1";
			customformData.currentAction = "SAVE_CURRENT_VERSION";// 保存成当前版本
			$("#status").val("2");
			submit();
		}
		;
	};

	showDifDialogOptions.buttons[fileManager.buttonCancel] = function() {
		$("#showDifDialog").oDialog('close');
	};
	
	
	
	function initDynamicTable() {
		if (needSelect == "0") {
			var fileUuid = $("#uuid").val();
			JDS.call({
				service : 'fileManagerService.getFileData',
				data : [ fileUuid, $("#dynamicFormId").val(),
						$("#dynamicDataId").val() ],
				success : function(result) {
					var canDownload = $("#canDownload").val();
					//如果为1表示允许下载,参考form_demo.js
					if(canDownload == "1"){
						$("#fileDynamicForm").dytable({
							data : result.data.formAndDataBean,
							isFile2swf : false,
							setReadOnly : readOnly,
							supportDown : "1",
							beforeSubmit : otherSubmit,
							open : function() {
								// 调整自适应表单宽度
								adjustWidthToForm();
								
								var scriptUrl  = $("#scriptUrl").val();
								$.getScript(ctx + scriptUrl);
							}
						});
					} else {//不为1表示防止下载
						$("#fileDynamicForm").dytable({
							data : result.data.formAndDataBean,
							isFile2swf : true,
							setReadOnly : readOnly,
							supportDown : "2",
							beforeSubmit : otherSubmit,
							open : function() {
								// 调整自适应表单宽度
								adjustWidthToForm();
								
								var scriptUrl  = $("#scriptUrl").val();
								$.getScript(ctx + scriptUrl);
							}
						});
					}
					
					initFileInfo();// 初始化文件表单的相关信息
				},
				error : function(xhr, textStatus, errorThrown) {
				}
			});
		}
	}
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		adjustWidthToForm();
	});
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	initDynamicTable();

	function otherSubmit() {
	}

	function initFileInfo() {
		$("#title").val($("#fileDynamicForm").dytable("getFieldForFormData", {
			formuuid : $("#dynamicFormId").val(),
			fieldMappingName : "File_title"
		}));

		$("#editFile").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_editFile"
				}));

		$("#sourceEditFileValue").html($("#editFile").val());

		$("#readFile").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_readFile"
				}));

		$("#sourceReadFileValue").html($("#readFile").val());

		if (uuid.length == 0) {// 新创建的情况
			$("#fileDynamicForm").dytable("setFieldValue", {
				mappingName : "File_parent_id",
				value : $("#folderId").val()
			});
		}

		// 文件历史版本对话框
		$("#historyVersionDialog").oDialog(historyVersionDialogOptions);

		// 对比当前文件与库中文件的不同
		$("#showDifDialog").oDialog(showDifDialogOptions);

	}

	$("#FILE_PRINT").click(function() {
		//如果需要二次打印，则需要弹出二次打印框并传入print_form中
		var is_print_second = $("#is_print_second").val();
		if(is_print_second == "true"){
			var second_print_reason_dialog_html = $("#second_print_reason_dialog_html").val();//弹出框html
			var json = new Object(); 
	        json.content = second_print_reason_dialog_html;
	        json.title = "请输入打印原因：";
	        json.height= 350;
	        json.width= 350;
	        var buttons = new Object(); 
	        buttons.确定 = getDialogFormContent;
	        json.buttons = buttons;
	        showDialog(json);
		}  else {
			var print_form_acitonUrl= $("#print_form_url").val();
			print_form.action = print_form_acitonUrl;
			print_form.submit();
		}
	});
	
	//二次打印点击确定后提交事件
	function getDialogFormContent(){
		var dialog_form_content = $("#xzsp_second_print_reason_dialog_content").val();
		$("#secondPrintReason").val(dialog_form_content);
		var print_form_acitonUrl= $("#print_form_url").val();
		print_form.action = print_form_acitonUrl;
		print_form.submit();
		$("#dialogModule").dialog("close");
	}

	$("#FILE_VERSION").click(
			function() {
				JDS.call({
					service : "fileManagerService.getVersions",
					data : [ $("#initId").val() ],
					success : function(result) {
						$("table[id='historyFiles']>tbody").html("");
						$.each(result.data, function(i) {
							var tr = "<tr><td><a target='_blank' href='" + ctx
									+ "/fileManager/file/showVersion?id="
									+ result.data[i].uuid + "&fileId="
									+ $("#uuid").val() + "'>"
									+ result.data[i].currentVersion
									+ "</a></td><td>"
									+ result.data[i].createTime + "</td><td>"
									+ result.data[i].remark
									+ "</td><td><a target='_blank' href='"
									+ ctx + "/fileManager/file/showVersion?id="
									+ result.data[i].uuid + "&fileId="
									+ $("#uuid").val() + "'>"
									+ fileManager.fileVersionView
									+ "</a></td></tr>";
							$("table[id='historyFiles']").append(tr);

						});
						$("#historyVersionDialog").oDialog("open");
					}
				});

			});

	$("#deleteButton").die().live( 'click', function() {
//		if(!checkCAKey()) {
//			return false;
//		}
		oConfirm(fileManager.deleteConfirm, function() {
			JDS.call({
				service : "fileManagerService.deleteFile",
				data : [ $("#uuid").val() ],
				success : function(result) {

					if (result.data == 1) {
						oAlert(fileManager.deleteSuccess, function() {
							afterSaveRefleshWindow("");
							// window.location.href=ctx+'/fileManager/folder/indexList?id='+$("#folderId").val();
						});
					}

				}
			});
		});
	});
	
	// 在对比不同后的正常保存
	$("#normalDifSave").click(
			function() {
				oConfirm(fileManager.sureToDoList, function() {
					isDifing = "1";
					submit();

					JDS.call({
						service : "folderManagerService.updateFolderDest",
						data : [ $("#currentId").val(), $("#objType").val(),
								$("#currentOperate").val(),
								$("#moveToFolderId").val() ],
						success : function(result) {

							window.location.href = ctx
									+ '/fileManager/folder/indexList?id='
									+ $("#folderId").val();
						}
					});

					$(this).oDialog('close');
					isDifing = "0";
				});
			});

	function moveLeft(currentMoveId) {
		var str = currentMoveId.sprlit(",");
		for (i = 0; i < str.length; i++) {
			$("#left_" + str[i]).val($("#right_" + str[i]).val());

		}
	}

	$("#readFileNames").click(function() {
		$.unit.open({
			title : fileManager.chooseUser,
			labelField : "readFileNames",
			valueField : "readFile",
			initNames : $("#readFileNames").val(),
			initIds : $("#readFile").val(),
			selectType : 4
		});
	});

	$("#FILE_OUT").click(function() {
		customformData.currentAction = "FILE_OUT";// 签出，不修改，不需要对比
		submit();
	});

	$("#FILE_SEND_FOLW").click(function() {
		customformData.currentAction = "FILE_SEND_FOLW"; // 送审批，修改，对比

		if (uuid.length == 0) {// 新创建的情况
			$("#status").val("3");// 待审批状态
		} else {
			needDif = "1";
		}

		submit();
	});

	var chooseVersionNum = "0";// 需要进行版本归集,选择版本号
	$("#FILE_IN").click(function() {
		customformData.currentAction = "FILE_IN";// 签入，修改，对比，选择版本

		if (uuid.length == 0) {// 新创建的情况
			$("#status").val("2");// 正式状态
		} else {
			needDif = "1";
			chooseVersionNum = "1";
		}

		submit();
	});

	$("#FILE_SEND_CHECK_IN").click(function() {
		customformData.currentAction = "FILE_SEND_CHECK_IN";// 送签入， 修改，对比

		if (uuid.length == 0) {// 新创建的情况
			$("#status").val("4");// 待签入状态
		} else {
			needDif = "1";
		}
		submit();
	});

	// 在提交审批过程中发起人放弃当前副本
	$("#FILE_CANCEL").click(function() {
		customformData.currentAction = "FILE_CANCEL";
		oConfirm(fileManager.sureToCancel, function() {
			submit();
		});
	});
	$("#btn_file_edit").die().live( 'click', function(e) {
//				if(!checkCAKey()) {
//					return false;
//				}
				var methodName = $("#methodName").val();
				var scriptUrl = $("#scriptUrl").val();
				var url = ctx + "/fileManager/file/edit?uuid=" + uuid
						+ "&editStatus=1"+"&methodName="+methodName+"&scriptUrl="+scriptUrl;
				location.href = url;
			});

	$("#save").click(function() {
//		if(!checkCAKey()) {
//			return false;
//		}
		customformData.currentAction = "SAVE_DRAFT";// 保存成草稿

		var sourceStatus = $("#status").val();

		$("#status").val("1");// 草稿状态

		if ("add" == $("#operate").val() || sourceStatus == "1") {// 在新增的情况下或草稿状态，不需要进行版本对比
			needDif = "0";
		} else {
			needDif = "1";
		}
		submit();
	});

	$("#saveNormal").die().live( 'click', function() {
//		if(!checkCAKey()) {
//			return false;
//		}
		customformData.currentAction = "SAVE_NORMAL";// 保存成正式
		$("#status").val("2");
		needDif = "1";
		submit();
	});

	$("#versionDiscard").click(function() {
		oConfirm(fileManager.sureToCancel, function() {
			isDifing = "1";
			customformData.currentAction = "DISCARD_SAVE";// 放弃新修改【本次修改】
			$("#status").val("2");
			submit();
		});
	});

	//刷新父页面的窗口
	function refleshParentWin(folderUuid) {
		pageLock("show");
		window.opener.refleshParentWin(folderUuid);//父窗口右侧刷新
		window.opener.loadLeftFolderTree1();//父窗口导航树刷新
		window.close();
		pageLock("hide");
	}

	var isDifing = "0";

	var isInitFirst = "1";// 是第一次初始化，用于版本号增加

	function submit() {
		$("#title").val($("#fileDynamicForm").dytable("getFieldForFormData", {
			formuuid : $("#dynamicFormId").val(),
			fieldMappingName : "File_title"
		}));
		if ($("#title").val().length == 0) {
			oAlert(fileManager.titleNotBeNull);
			return;
		}
		$("#editFile").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_editFile"
				}));

		$("#sourceEditFileValue").html($("#editFile").val());

		$("#readFile").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_readFile"
				}));

		$("#reservedText1").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText1"
				}));
		$("#reservedText2").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText2"
				}));
		$("#reservedText3").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText3"
				}));
		$("#reservedText4").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText4"
				}));
		$("#reservedText5").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText5"
				}));
		$("#reservedText6").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText6"
				}));
		$("#reservedText7").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText7"
				}));
		$("#reservedText8").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedText8"
				}));
		$("#reservedNumber1").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedNumber1"
				}));
		$("#reservedNumber2").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedNumber2"
				}));
		$("#reservedNumber3").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedNumber3"
				}));
		$("#reservedDate1").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedDate1"
				}));
		$("#reservedDate2").val(
				$("#fileDynamicForm").dytable("getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_reservedDate2"
				}));

		$("#sourceReadFileValue").html($("#readFile").val());

		var currentParentId = $("#fileDynamicForm").dytable(
				"getFieldForFormData", {
					formuuid : $("#dynamicFormId").val(),
					fieldMappingName : "File_parent_id"
				});
		if (currentParentId && currentParentId.length > 0) {
			$("#folderId").val(currentParentId);
		}

		checkInputValueEq("editFile", "sourceEditFileValue", "editFileChange");
		checkInputValueEq("readFile", "sourceReadFileValue", "readFileChange");

		var rootFormData = $("#fileDynamicForm").dytable("formData");

		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#fileForm").form2json(bean);
		customformData.rootFormDataBean = rootFormData;
		customformData.fileBean = bean;
		customformData.operate = $("#operate").val();
		customformData.attach = $('#attach').val();
		$('#save').attr('disabled', "true");
		if (chooseVersionNum == "1") {// 修改之后，需要对比

			if (isInitFirst == '1') {
				var currentVersion = parseFloat($("#currentSetVersion").val());

				currentVersion = currentVersion + 0.1;
				currentVersion = Math.round((currentVersion * 100) * 1000) / 100000;
				currentVersion = toDecimal1(currentVersion);
				$("#currentSetVersion").val(currentVersion);
				isInitFirst = "0";
			}
			// 选择版本号

			$("#showDifDialog").oDialog("open");
			chooseVersionNum = "0";

		} else {
			JDS
					.call({
						service : 'fileManagerService.saveOrUpdateFile',
						data : customformData,
						success : function(result) {
							// window.location.href=ctx+"/fileManager/folder/indexList?id="+$("#folderId").val();
							if(result.data.indexOf("字段") > -1 ){//如果返回的信息包含“字段”，则为表单提示信息（如字段唯一等）
								oAlert(result.data);
							} else {
								if (customformData.currentAction == "SAVE_CURRENT_VERSION") {
									oAlert(fileManager.fileInSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "SAVE_NEW_VERSION") {
									oAlert(fileManager.fileInSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "FILE_OUT") {
									oAlert(fileManager.fileOutSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "FILE_SEND_FOLW") {
									oAlert(fileManager.fileSendFlowSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "FILE_SEND_CHECK_IN") {
									oAlert(fileManager.fileSendCheckInSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "SAVE_DRAFT"
										|| customformData.currentAction == "SAVE_NORMAL") {
									oAlert(fileManager.saveSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else if (customformData.currentAction == "DISCARD_SAVE"
										|| customformData.currentAction == "FILE_CANCEL") {
									oAlert(fileManager.fileCancelSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								} else {
									oAlert(fileManager.operateSuccess, function(){
										afterSaveRefleshWindow(result.data);
				    				});
								}
							}
						},
						error : function(xhr, textStatus, errorThrown) {
							$('#save').attr('disabled', "false");
							var msg = JSON.parse(xhr.responseText);
							if (msg && msg.errorCode === "TaskNotAssignedUser") {
								// 选择流程审批人员
								selectFlowUsers(msg.data);
							} else if (msg
									&& msg.errorCode === "FileHasCheckOut") {
								oAlert(msg.data);
							} else {
								$("#fileDynamicForm").dytable(
										"showCompareData", {
											data : msg.data,
											id : "fileDynamicForm"
										});
								// $("#fileDynamicForm").dytable("showCompareData",
								// {data:$("#dynamicDataId").val(),id:"fileDynamicForm"});
							}
						}
					});
		}
	}
	
	//保存完文档点击“确定”之后调用的刷新方法
	function afterSaveRefleshWindow(fileUuid){
		//判断父窗口是否有方法名为methodName（用户传进来的）的方法，若没有才调用文件库本身的刷新父窗口方法
		var methodName = $("#methodName").val();
		if(window.opener[methodName]){
			window.opener[methodName].call(window.opener,fileUuid);
			//关闭新建文档窗口
			window.close();
		}else if(File.afterSaveFileSuccess){//如果没有传methodName,再判断传入的scriptUrl中是否包含“afterSaveFileSuccess”方法，如果有，则执行此方法
			File.afterSaveFileSuccess.call(this,fileUuid);
		}else {
			refleshParentWin($("#folderId").val());
		}
	}

	// 选择流程审批人员
	function selectFlowUsers(data) {
		$.unit.open({
			title : fileManager.selectSendFlowUsers,//"选择审批人"
			afterSelect : function(laReturn) {
				var taskUsers = {};
				var taskId = data.taskId;
				if (laReturn.id != null && laReturn.id != "") {
					var userIds = laReturn.id.split(";");
					taskUsers[taskId] = userIds;
					customformData.taskUsers = taskUsers;
					// 重新触发送审批事件
					$("#FILE_SEND_FOLW").trigger("click");
				} else {
					taskUsers[taskId] = {};
					customformData.taskUsers = taskUsers;
				}
			}
		});
	}

	/**
	 * 保留一位小数，不足一位补0
	 */
	function toDecimal1(x) {

		var f = Math.round(x * 10) / 10;
		var s = f.toString();
		var rs = s.indexOf('.');
		if (rs < 0) {
			rs = s.length;
			s += '.';
		}
		while (s.length <= rs + 1) {
			s += '0';
		}
		return s;
	}

	/*
	 * 判断 inputIds元素的值，是否与divIds中的元素值一致
	 */
	function checkInputValueEq(inputIds, divIds, setValue) {
		var sourceValue = $("#" + inputIds).val();

		var destValue = $("#" + divIds).html();

		var sourceArr = sourceValue.split(";");

		var destArr = destValue.split(";");

		if (sourceArr.sort().toString() == destArr.sort().toString()) {

			$("#" + setValue).val("0");

		} else {

			$("#" + setValue).val("1");

		}

	}
	
	File.addButton = function(button) {
		$(".form_operate").append(button);
	};
});

//判断文档是否存在
File.isFileExist = function(){
	var fileUuid = $("#uuid").val();
	if(fileUuid == "" || fileUuid == undefined){
		return false;
	} else {
		return true;
	}
};

//添加指定的功能按钮（编辑：editor；删除：delete; 保存：save;），若有多个功能按钮的话则以逗号（,）隔开
File.addFunctionsButton = function(functionsName){
	var functionsNameArray = functionsName.split(",");
	var form_operate_html = $(".form_operate").html();
	for(var i=0;i<functionsNameArray.length;i++){
		if(functionsNameArray[i] == 'editor'){//编辑按钮
			var btn_file_edit = '<button type="button" id="btn_file_edit">编辑</button>';
			form_operate_html = form_operate_html+btn_file_edit;
		} else if(functionsNameArray[i] == 'delete'){//删除按钮
			var deleteButton = '<button type="button" id="deleteButton">删除</button>';
			form_operate_html = form_operate_html+deleteButton;
		} else if(functionsNameArray[i] == 'save'){
			var saveNormal = '<button type="button" id="saveNormal">保存</button>';
			form_operate_html = form_operate_html+saveNormal;
		}
	}
	$(".form_operate").html(form_operate_html);
};

//获取url参数
function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	return arrayObj;
}