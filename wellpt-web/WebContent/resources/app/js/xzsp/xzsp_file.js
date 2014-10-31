$(function() {
	var xzsp_view = "/resources/app/js/xzsp/xzsp_view.js";
	var xzsp_project = "/resources/app/js/xzsp/xzsp_project.js";
	$.getScript(ctx + xzsp_project);
	var projectProcess = "/resources/pt/js/common/jquery.projectProcess.js";
	
	/***网上申报新建项目的处理开始***********************************/
	var dytableSelector = "#fileDynamicForm";
	var search = readSearch();
	var fileShowTitle = urldecode(search.fileShowTitle);
	if(fileShowTitle == "新增项目") {
		var userDetails = SpringSecurityUtils.getUserDetails();
		$(dytableSelector).dyform("setFieldAsHide", "SHJRHMD");
		$(dytableSelector).dyform("setFieldAsLabelByFieldName", "XMBH");
		$(dytableSelector).dyform("hideSubForm", "uf_xzsp_black");
		$(dytableSelector).dyform("setFieldValueByFieldName","XMDWSYZ","{'"+userDetails.userId+"':'"+userDetails.username+"'}");
		$(dytableSelector).dyform("setFieldValueByFieldName","XMDWSYZMC",userDetails.username);
		$(dytableSelector).dyform("setFieldValueByFieldName","XMDWSYZDM",userDetails.userId);
		$(dytableSelector).dyform("setFieldAsLabelByFieldName","XMDWSYZ");
		
		$(":button[id=saveNormal]").remove();
		$(":button[id=save]").html("提交");
	}else if(fileShowTitle == '新建市民') {
		$(dytableSelector).dyform("setFieldAsHide", "SHJRHMD");
		$(dytableSelector).dyform("hideSubForm", "uf_xzsp_black");
		$(":button[id=save]").hide();
		$(":button[id=saveNormal]").html("保存");
	}
	else {
		$(":button[id=save]").hide();
		$(":button[id=saveNormal]").html("保存");
	}
	/***网上申报新建项目的处理结束***********************************/
	//项目单提交前执行方法
//	File.beforeSubmitService = 'ProjectService.beforeSubmit';
	File.setFileData("beforeSubmitService", "projectService.beforeSubmit");
	//项目单提交后执行方法
//	File.afterSubmitService = 'ProjectService.afterSubmit';
	File.setFileData("afterSubmitService", "projectService.afterSubmit");
	
	if (!File.isFileExist()) {
		// 保存项目单后回调事件
		File.afterSaveFileSuccess = function(fileUuid) {
			location.href = ctx + "/fileManager/file/edit?uuid=" + fileUuid
					+ "&methodName=reloadFileParentWindow&scriptUrl=/resources/app/js/xzsp/xzsp_file.js",
					"project";
			returnWindow();
		};
	} else {
		var btn_view_project_tree = '<button type="button" id="btn_view_project_tree">查看项目树</button>';
		var btn_accept_project = '<button type="button" id="btn_accept_project">接件</button>';
		var btn_project_process = '<button type="button" id="btn_project_process">生命周期</button>';
		$(".form_operate button:first").before(btn_view_project_tree);
		$(".form_operate button:first").before(btn_accept_project);
		$(".form_operate button:first").before(btn_project_process);

		// 接件事件处理
		$("#btn_accept_project").click(function() {
			var projectUuid = $("input[id=uuid]").val();
			$.getScript(ctx + xzsp_view, function() {
				XZSP.workflow.getMattersByLibId.call(this, 'MATTERS_LIB', projectUuid, 'PROJECT_FORM_GOIN');
			});
		});

		// 查看项目树
		$("#btn_view_project_tree").click(function() {
			var projectUuid = $("input[id=uuid]").val();
			prepareAndShowProjectTreeDialog(projectUuid);
		});
		
		// 查看生命周期
		$("#btn_project_process").click(function() {
			$.getScript(ctx + projectProcess);
			var projectProcessType = 'XZSP_PROJECT_PROCESS_JSXM';
			var projectCode = $("input[id=reservedText4]").val();
			 XZSP_PROJECT.getProjectProcess(projectCode,projectProcessType);
		});
	}

	/** ****************************** 查看项目树开始 ****************************** */
	// 准备与项目树窗口
	function prepareAndShowProjectTreeDialog(projectUuid) {
		// 放置
		if ($("#dlg_view_project_tree").length == 0) {
			$("body").append(
					"<div id='dlg_view_project_tree' ><ul id='project_tree' class='ztree'></ul></div>");
		}

		showViewProjectTreeDialog({});

		// 绑定补件弹出框事件
		// JQuery zTree
		var setting = {};
		// 加载项目树
		JDS.call({
			service : "projectService.getProjectTree",
			data : [ projectUuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#project_tree", "#dlg_view_project_tree"), setting,
						result.data);
				zTree.expandAll(true);
			}
		});
	}

	function showViewProjectTreeDialog(option) {
		// 初始化下一流程选择框
		var options = {
			title : "查看项目树",
			autoOpen : true,
			width : 650,
			height : 400,
			resizable : false,
			modal : true,
			open : function() {
			},
			buttons : {},
			close : function() {
				$("#dlg_view_project_tree").html("<ul id='project_tree' class='ztree'></ul>");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_view_project_tree").oDialog(options);
	}
	/** ****************************** 查看项目树结束 ****************************** */

	// 判断该用户是否具有操作黑名单的权限
	var resultData = "";
	JDS.call({
		async : false,
		service : "XZSPService.isGrant",
		data : [ "B013002003" ],
		success : function(result) {
			resultData = result.data;
		}
	});
	if (resultData) {
		$('input[type=radio][name="SHJRHMD"]')
				.live(
						"click",
						function() {
							var join_reason =  SpringSecurityUtils.getCurrentUserName();
							var joinTime = "";
							var myDate = new Date();
							joinTime = myDate.getFullYear() + "-" + parseInt(1+myDate.getMonth()) + "-"
									+ myDate.getDate() + " " + myDate.getHours() + ":" + myDate.getMinutes()
									+ ":" + myDate.getSeconds();
							var json = new Object();
							var data = "<div style='padding:20px;'><table style='width: 90%;'><tr><td>加入或解除黑名单原因</td>"
									+ "<td><textarea style='width:380px;height: 150px;' name='join_reason' id='join_reason'></textarea>"
									+ "</td></tr>"
									+ "<tr><td>操作人员</td><td><span id='join_person' name='join_person' value='"
									+ join_reason
									+ "'>"
									+ join_reason
									+ "</span></td></tr>"
									+ "<tr><td>加入时间</td><td><span id='join_time' name='join_time' value='"
									+ joinTime + "'>" + joinTime + "</span></td></tr>";
							"</table></div>";
							json.content = data;
							json.title = "黑名单";
							json.height = 400;
							json.width = 600;
							json.buttons = {
								"确定" : function() {
									if ($("#join_reason").val() == undefined || $("#join_reason").val() == "") {
										oAlert("请填写加入或解除黑名单原因!");
									} else {
										var blackData = new Object();
										blackData['joinblack_reason'] = $("#join_reason").val();
										blackData['CZRY'] = $("#join_person").attr("value");
										blackData['CZSJ'] = $("#join_time").attr("value");
										var dytableSelector = "#fileDynamicForm";
									 
										$(dytableSelector).dyform("addRowData",
											 "uf_xzsp_black",
											 blackData
										);
										$("#dialogModule").dialog("close");
									}
								},
							};
							showDialog(json);
						});
	} else {
		$('input[type=radio][name="_radio_inputSHJRHMD"]').attr("disabled", "disabled");
	}

	// 获取项目卡的uuid
	function getProjectFormUuid() {
		var projectFormUuid = '';
		JDS.call({
			async : false,
			service : "projectService.getProjectFormUuid",
			data : [],
			success : function(result) {
				projectFormUuid = result.data;
			}
		});
		return projectFormUuid;
	}

	// 判断是否为项目卡
	var projectFormUuid = getProjectFormUuid();
	if ($("#dynamicFormId").val() == projectFormUuid) {
		var fmFileUuid = $("#uuid").val();
		if (fmFileUuid != "" && fmFileUuid != undefined) {
			// 判断是否需要二次打印
			JDS
					.call({
						async : false,
						service : "XZSPPrintService.isXZSPProjectCardSecondPrintRecord",
						data : [ $("#uuid").val(), "XZSP_PROJECT_REGISTER" ],
						success : function(result) {
							if (result.data == "true") {
								$("#is_print_second").val(result.data);
								var second_print_reason_dialog_html = "<div ><div id='dialog_form'><div class='dialog_form_content'><table width='100%'><tbody><tr ><td class='value'><div ><textarea id='xzsp_second_print_reason_dialog_content'  rows='11' cols='100' style='width: 299px;' name='second_print_reason_dialog_content'></textarea></div></td></tr></tbody></table></div></div></div>";
								$("#second_print_reason_dialog_html").val(second_print_reason_dialog_html);
							}
						}
					});
		}
		$("#print_form_url")
				.val(
						ctx
								+ "/fileManager/file/print?printService=XZSPPrintService.printProjectCard&printTemplateId=XZSP_PROJECT_REGISTER");// 项目卡执行打印的url
	}
});


//从url中获取参数值
function readSearch() {
	var search = window.location.search;
	var s = new Object();
	var searchArray = search.replace("?", "").split("&");
	for ( var i = 0; i < searchArray.length; i++) {
		var paraArray = searchArray[i].split("=");
		var key = paraArray[0];
		var value = paraArray[1];
		s[key] = value;
	}
	return s;
}