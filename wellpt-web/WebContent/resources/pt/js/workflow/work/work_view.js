var WorkFlow = WorkFlow || {};
$(function() {
	// 保存
	var btn_save = "B004001";
	// 动态表单提交
	var btn_submit_form = "btn_dyform";
	// 提交
	var btn_submit = "B004002";
	// 退回
	var btn_rollback = "B004003";
	// 直接退回
	var btn_direct_rollback = "B004004";
	// 撤回
	var btn_cancel = "B004005";
	// 转办
	var btn_transfer = "B004006";
	// 会签
	var btn_counterSign = "B004007";
	// 关注
	var btn_attention = "B004008";
	// 套打
	var btn_print = "B004009";
	// 抄送
	var btn_copyTo = "B004010";
	// 签署意见
	var btn_sign_opinion = "B004011";
	// 取消关注
	var btn_unfollow = "B004012";
	// 办理过程
	var btn_view_process = "B004013";
	// 催办
	var btn_remind = "B004014";
	// 移交
	var btn_hand_over = "B004015";
	// 跳转
	var btn_do_goto = "B004016";
	// 办理意见
	var btn_view_opinion = "";
	// 挂起
	var btn_suspend = "B004017";
	// 恢复
	var btn_resume = "B004018";
	// 退件
	var btn_reject = "B004019";

	// 流程错误代码
	var WorkFlowErrorCode = {
		WorkFlowException : "WorkFlowException", // 工作流异常
		TaskNotAssignedUser : "TaskNotAssignedUser", // 任务没有指定参与者
		TaskNotAssignedCopyUser : "TaskNotAssignedCopyUser", // 任务没有指定抄送人
		TaskNotAssignedMonitor : "TaskNotAssignedMonitor", // 任务没有指定督办人
		ChooseSpecificUser : "ChooseSpecificUser", // 选择具体办理人
		OnlyChooseOneUser : "OnlyChooseOneUser", // 只能选择一个办理人
		JudgmentBranchFlowNotFound : "JudgmentBranchFlowNotFound", // 无法找到可用的判断分支流向
		MultiJudgmentBranch : "MultiJudgmentBranch", // 找到多个判断分支流向
		SubFlowNotFound : "SubFlowNotFound", // 没有指定子流程
		SubFlowMerge : "SubFlowMerge", // 子流程合并等待
		IdentityNotFlowPermission : "IdentityNotFlowPermission", // 用户没有权限访问流程
		RollbackTaskNotFound : "RollbackTaskNotFound" // 找不到退回操作的退回环节异常类
	};
	var bean = {
		"taskInstUuid" : null,
		"flowInstUuid" : null,
		"flowDefUuid" : null,
		"flowDefId" : null,
		"title" : null,
		"name" : null,
		"suspensionState" : null,
		"serialNoDefId" : null,
		"userId" : null,
		"userName" : null,
		"formUuid" : null,
		"dataUuid" : null,
		"formAndDataBean" : null,
		"rootFormDataBean" : null,
		"buttons" : [],
		"taskUsers" : {},
		"taskCopyUsers" : {},
		"taskMonitors" : {},
		"createTime" : null,
		"fromTaskId" : "<StartFlow>",
		"toTaskId" : null,
		"toSubFlowId" : null,
		"waitForMerge" : {},
		"customDynamicButton" : null,
		"rollbackToTaskId" : null,
		"printTemplateId" : null,
		"opinionValue" : null,
		"opinionLabel" : null,
		"opinionText" : null,
		"records" : []
	};

	// 判断字符串不为undefined、null、空串、空格串
	var isNotBlank = StringUtils.isNotBlank;
	// 判断字符串为undefined、null、空串、空格串
	var isBlank = StringUtils.isBlank;

	var dytableSelector = "#dyform";

	// 操作服务
	var saveService = "workService.save";
	var submitService = "workService.submit";
	var rollbackService = "workService.rollback";
	var directRollbackService = "workService.directRollback";
	var cancelService = "workService.cancel";
	var transferService = "workService.transfer";
	var counterSignService = "workService.counterSign";
	var attentionService = "workService.attention";
	var printUrl = "/workflow/work/print";
	var copyToService = "workService.copyTo";
	var viewOpinionService = "workService.getOpinions";
	var viewProcessService = "workService.getProcess";
	var remindService = "workService.remind";
	var unfollowdService = "workService.unfollow";
	var handOverService = "workService.handOver";
	var getToTasksdoGotoService = "workService.getToTasks";
	var suspendService = "workService.suspend";
	var resumeService = "workService.resume";
	var timelineService = "workService.getTimeline";

	bean.flowDefUuid = $("#wf_flowDefUuid").val();
	bean.flowDefId = $("#wf_flowDefId").val();
	bean.flowInstUuid = $("#wf_flowInstUuid").val();
	bean.taskInstUuid = $("#wf_taskInstUuid").val();
	bean.taskId = $("#wf_taskId").val();
	bean.formUuid = $("#wf_formUuid").val();
	bean.dataUuid = $("#wf_dataUuid").val();
	bean.title = $("#wf_title").val();
	bean.aclRole = $("#wf_aclRole").val();
	bean.serialNoDefId = $("#wf_serialNoDefId").val();
	bean.suspensionState = $("#wf_suspensionState").val();

	// 保留自定义运行时参数
	bean.extraParams = {};
	var $eps = $("input[name^=custom_rt_]", "#wf_form");
	$eps.each(function() {
		bean.extraParams[$(this).attr("name")] = $(this).val();
	});

	// 是否需要签署意见
	var requiredSignOpinion = $(":button[name='" + btn_sign_opinion + "']", ".form_operate").length > 0;

	// 如果流程ID或流程定义UUID都为空，则提示错误并返回
	if (isBlank(bean.flowDefId) && isBlank(bean.flowDefUuid)) {
		oAlert("流程定义加载出错，没有指定流程或流程不存在!");
		return;
	}

	// JQuery UI按钮
	// $("input[type=submit], a, button", $("#toolbar")).button();
	// 绑定动态表单提交按钮
	$(".form_operate").append("<button id='btn_dyform' name='btn_dyform' style='display: none'>保存</button>");

	var subDataParams = [];
	var $eps = $("input[name^=ep_]", "#wf_form");
	$eps.each(function() {
		subDataParams.push({
			"id" : $(this).val()
		});
	});

	// 初使化
	JDS
			.call({
				service : "workService.getWorkData",
				data : [ bean ],
				success : function(result) {
					var workData = result.data;
					$(dytableSelector).dytable({
						data : workData.formAndDataBean,
						subDataParams : subDataParams,
						btnSubmit : btn_submit_form,
						beforeSubmit : submitForm,
						open : function() {
							bean.taskStartTime = workData.taskStartTime;

							bean.reservedText1 = workData.reservedText1;
							bean.reservedText2 = workData.reservedText2;
							bean.reservedText3 = workData.reservedText3;
							bean.reservedText4 = workData.reservedText4;
							bean.reservedText5 = workData.reservedText5;
							bean.reservedText6 = workData.reservedText6;
							bean.reservedText7 = workData.reservedText7;
							bean.reservedText8 = workData.reservedText8;
							bean.reservedText9 = workData.reservedText9;
							bean.reservedNumber1 = workData.reservedNumber1;
							bean.reservedNumber2 = workData.reservedNumber2;
							bean.reservedNumber3 = workData.reservedNumber3;
							bean.reservedDate1 = workData.reservedDate1;
							bean.reservedDate2 = workData.reservedDate2;

							onDyformOpen();
						}
					});

					bean.records = result.data.records;

					// 是否查看办理过程
					var requiredViewProcess = $(":button[name='" + btn_view_process + "']", ".form_operate").length > 0;
					// 办理过程
					var process = result.data.workProcess;
					// 1、流程新建时
					if ((process && !process["previous"] && process["current"] && process["next"])
							|| (process && process["previous"] && process["current"] && !process["next"])) {
						var proce1 = null;
						var proce2 = null;
						if (process && process["current"] && process["next"]) {
							proce1 = process["current"];
							proce2 = process["next"];
						} else {
							proce1 = process["previous"];
							proce2 = process["current"];
						}
						$("#process .proce1").hide();
						$("#process").css("min-width", "700px");
						$("#process .proce2").addClass("proce2_start");
						$("#pre_task_name").html("");
						$("#pre_task_assignee").html("");
						$("#cur_task_name").html(proce1.taskName);
						$("#cur_task_assignee").html(proce1.assignee);
						$("#next_task_name").html(proce2.taskName);
						$("#next_task_assignee").html(proce2.assignee);

						$("#process").show();

						requiredViewProcess = false;
					} else if (process && process["previous"] && process["current"] && process["next"]) {
						// 2、流程办理中
						$("#pre_task_name").html(process["previous"].taskName);
						$("#pre_task_assignee").html(process["previous"].assignee);
						$("#cur_task_name").html(process["current"].taskName);
						$("#cur_task_assignee").html(process["current"].assignee);
						$("#next_task_name").html(process["next"].taskName);
						$("#next_task_assignee").html(process["next"].assignee);

						$("#process").show();
					} else if (process && !process["previous"] && process["current"] && !process["next"]) {
						// 3、流程已结束
						var proce1 = process["current"];
						$("#process .proce1").hide();
						$("#process").css("min-width", "700px");
						$("#process .proce2").addClass("proce2_start");
						$("#pre_task_name").html("");
						$("#pre_task_assignee").html("");
						$("#cur_task_name").html(proce1.taskName);
						$("#cur_task_assignee").html(proce1.assignee + "(已办结)");
						$("#process .proce3").hide();
						$("#next_task_name").html("");
						$("#next_task_assignee").html("");

						$("#process").show();
					}

					// 如果不需要查看办理过程，则显示查阅流程图
					if (!requiredViewProcess) {
						$("#process .view_process").unbind("click");
						$("#process .view_process a").html("查阅流程图");
						var url = ctx + "/workflow/show?open&id=" + bean.flowDefUuid;
						$("#process .view_process").click(function() {
							window.open(url);
						});
					}

					// 时间轴事件绑定
					$("#process .timeline").click(function() {
						showTimeline();
						// open(ctx +
						// "/workflow/work/view/timeline?taskInstUuid=" +
						// bean.taskInstUuid);
					});

					// 如果流程实例UUID不为空，显示共享数据
					if (isNotBlank(bean.flowInstUuid)) {
						showShareFlow(bean.flowInstUuid);
					}
				}
			});

	// 动态表单初始化后回调处理
	function onDyformOpen() {
		// 调整自适应表单宽度
		adjustWidthToForm();

		// 加载执行运行时扩展js
		var epScriptUrl = $("#custom_rt_script_url").val();
		if (isNotBlank(epScriptUrl)) {
			$.getScript(ctx + epScriptUrl);
		}

		// 加载自定义扩展JS
		var scriptUrl = $("#custom_script_url").val();
		if (isNotBlank(scriptUrl)) {
			$.getScript(ctx + scriptUrl);
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

		$(".form_content").css("width", div_body_width - 44);
		$("#process").css("width", div_body_width - 45);

		// 调整子过程办理过程宽度
		$(".share_flow_content").css("width", div_body_width - 45);

		// 显示签署意见
		if (requiredSignOpinion == true) {
			showOpinion();
		}
	}

	// 显示共享数据
	function showShareFlow(flowInstUuid) {
		JDS.call({
			service : "workService.getShareData",
			data : [ flowInstUuid ],
			success : function(result) {
				// 调整自适应表单宽度
				adjustWidthToForm();
				$("#share_flow_body").html("");
				$(result.data).each(
						function() {
							$("#share_flow").show();

							var rowData = "<tr>" + "<td>" + this.title + "</td>" + "<td>" + this.todoUser
									+ "</td>" + "<td>" + this.opinion + "</td>" + "<td>" + this.currentTask
									+ "</td>" + "<td>" + this.currentUser + "</td>" + "</tr>";
							$("#share_flow_body").append(rowData);
						});
			}
		});
	}

	/** ********************************* 保存开始 ********************************* */
	// 保存
	$(":button[name='" + btn_save + "']").each(function() {
		$(this).click($.proxy(onSave, this, true));
	});
	// 保存事件处理
	function onSave(async, callback) {
		// 操作动作及类型
		bean.action = $(this).text();
		bean.actionType = "Submit";

		// 获取表单数据
		var rootFormData = $(dytableSelector).dytable("formData");
		bean.rootFormDataBean = rootFormData;
		// 设置表单映射标题
		var formTitle = getFormTitle();
		if (isNotBlank(formTitle)) {
			bean.title = formTitle;
		}
		JDS.call({
			service : saveService,
			data : [ bean ],
			async : async,
			success : function(result) {
				var data = result.data;
				// 局部回调callback
				if (typeof (callback) == "function") {
					callback.call(this, data);
					// 全局回调，WorkFlow.afterSuccessSave
					if (WorkFlow.afterSuccessSave) {
						WorkFlow.afterSuccessSave.call(this, bean);
					}
				} else {
					// 全局回调，WorkFlow.afterSuccessSave
					if (WorkFlow.afterSuccessSave) {
						WorkFlow.afterSuccessSave.call(this, bean);
					} else {
						oAlert("保存成功!", function() {
							// 保存成功，刷新当前页面
							var taskInstUuid = bean.taskInstUuid;
							var flowInstUuid = bean.flowInstUuid;
							if (isNotBlank(taskInstUuid) || isNotBlank(flowInstUuid)) {
								window.location.reload();
							} else {
								window.location = ctx + "/workflow/work/view/draft?flowInstUuid="
										+ data["flowInstUuid"];
							}
						});
					}
				}
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ********************************* 保存结束 ********************************* */

	/** ********************************* 提交开始 ********************************* */
	// 提交
	$(":button[name='" + btn_submit + "']").each(function() {
		$(this).click($.proxy(onSubmit, this));
	});
	// 提交事件处理
	function onSubmit() {
		// 设置提交按钮ID
		var btnId = $(this).attr("id");
		bean.submitButtonId = btnId;

		// 操作动作及类型
		bean.action = $(this).text();
		bean.actionType = "Submit";

		// 提交前回调处理
		if (WorkFlow.beforeSubmit) {
			var bsRetVal = WorkFlow.beforeSubmit.call(this, bean);
			if (bsRetVal == false) {
				return;
			}
		}
		// 确保提交时有签署意见
		if (isNotBlank(bean.taskInstUuid) && !isNotBlank(bean.opinionText) && requiredSignOpinion == true) {
			showSignOpinionDialog(true);
		} else {
			// 处理自定义动态按钮
			if (btnId != btn_submit) {
				var customDynamicButton = {};
				customDynamicButton.id = btnId;
				customDynamicButton.code = $(this).attr("name");
				customDynamicButton.taskId = $(this).attr("taskId");
				// 去掉按钮提交主送对象字符串的字符'['、']'
				var customUserIds = $(this).attr("userIds");
				var customCopyUserIds = $(this).attr("copyUserIds");
				if (customUserIds.indexOf("[") == 0
						&& customUserIds.lastIndexOf("]") == (customUserIds.length - 1)) {
					var users = customUserIds.substring(1, customUserIds.length - 1);
					customDynamicButton.users = users.split(",");
				}
				// 去掉按钮提交抄送对象字符串的字符'['、']'
				if (customCopyUserIds.indexOf("[") == 0
						&& customCopyUserIds.lastIndexOf("]") == (customCopyUserIds.length - 1)) {
					var copyUsers = customCopyUserIds.substring(1, customCopyUserIds.length - 1);
					customDynamicButton.copyUsers = copyUsers.split(",");
				}
				bean.customDynamicButton = customDynamicButton;
			} else {
				bean.customDynamicButton = null;
			}

			// 提交动态表单
			$("#" + btn_submit_form).trigger('click');
		}
	}
	// 提交动态表单操作
	function submitForm() {
		// 获取表单数据
		var rootFormData = $(dytableSelector).dytable("formData");
		bean.rootFormDataBean = rootFormData;
		// 设置表单映射标题
		var formTitle = getFormTitle();
		if (isNotBlank(formTitle)) {
			bean.title = formTitle;
		}
		JDS.call({
			service : submitService,
			data : [ bean ],
			success : function(result) {
				var retVal = false;
				if (WorkFlow.afterSuccessSubmit) {
					retVal = WorkFlow.afterSuccessSubmit.call(this, bean);
				}
				if (retVal == false) {
					requiredSignOpinion = true;
					oAlert("提交成功!", function() {
						hanlderSuccess(result);
					});
				}
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ********************************* 提交结束 ********************************* */

	/** ******************************** 回调处理开始 ******************************** */
	// 处理流程操作成功信息
	function hanlderSuccess(result) {
		// 操作成功，关闭相关页面
		var taskInstUuid = bean.taskInstUuid; // 从待办工作中打开
		var flowInstUuid = bean.flowInstUuid; // 从草搞箱打开
		var flowDefUuid = bean.flowDefUuid; // 从新建工作打开
		if (taskInstUuid != null && taskInstUuid != "") {
			TabUtils.reloadAndRemoveTab("work_todo", taskInstUuid);
		} else if (flowInstUuid != null && flowInstUuid != "") {
			TabUtils.reloadAndRemoveTab("work_draft", flowInstUuid);
		} else if (flowDefUuid != null && flowDefUuid != "") {
			TabUtils.removeTab(bean.flowDefUuid, bean.flowDefUuid);
		}
	}
	// 处理流程操作返回 的错误信息
	function hanlderError(jqXHR) {
		var msg = JSON.parse(jqXHR.responseText);
		if (WorkFlowErrorCode.WorkFlowException === msg.errorCode) {
			WorkFlowException(msg.data);
		} else if (WorkFlowErrorCode.TaskNotAssignedUser === msg.errorCode) {
			TaskNotAssignedUser(msg.data);
		} else if (WorkFlowErrorCode.TaskNotAssignedCopyUser === msg.errorCode) {
			TaskNotAssignedCopyUser(msg.data);
		} else if (WorkFlowErrorCode.TaskNotAssignedMonitor === msg.errorCode) {
			TaskNotAssignedMonitor(msg.data);
		} else if (WorkFlowErrorCode.ChooseSpecificUser === msg.errorCode) {
			ChooseSpecificUser(msg.data);
		} else if (WorkFlowErrorCode.OnlyChooseOneUser === msg.errorCode) {
			OnlyChooseOneUser(msg.data);
		} else if (WorkFlowErrorCode.JudgmentBranchFlowNotFound === msg.errorCode) {
			JudgmentBranchFlowNotFound(msg.data);
		} else if (WorkFlowErrorCode.MultiJudgmentBranch === msg.errorCode) {
			MultiJudgmentBranch(msg.data);
		} else if (WorkFlowErrorCode.SubFlowNotFound === msg.errorCode) {
			SubFlowNotFound(msg.data);
		} else if (WorkFlowErrorCode.SubFlowMerge === msg.errorCode) {
			SubFlowMerge(msg.data);
		} else if (WorkFlowErrorCode.IdentityNotFlowPermission === msg.errorCode) {
			IdentityNotFlowPermission(msg.data);
		} else if (WorkFlowErrorCode.RollbackTaskNotFound === msg.errorCode) {
			RollbackTaskNotFound(msg.data);
		}
	}
	// 1、工作流异常
	function WorkFlowException(eData) {
		oAlert(eData);
	}

	// 2、任务没有指定参与者，弹出人员选择框选择参与人(人员、部门及群组)
	function TaskNotAssignedUser(eData) {
		var title = "";
		if (isNotBlank(eData.title)) {
			title = eData.title;
		}
		$.unit.open({
			title : "选择承办人" + title,
			afterSelect : function(laReturn) {
				var taskUsers = {};
				var taskId = eData.taskId;
				if (isNotBlank(laReturn.id)) {
					// 在原来的环节办理人上增加环节办理人
					taskUsers = bean.taskUsers;
					var userIds = laReturn.id.split(";");
					taskUsers[taskId] = userIds;
				} else {
					taskUsers[taskId] = null;
					bean.taskUsers = taskUsers;
				}
				// 重新触发提交事件
				if (isNotBlank(eData.submitButtonId)) {
					$("#" + eData.submitButtonId).trigger('click');
				} else {
					// $("#" + btn_submit).trigger('click');
					oAlert("请重新提交!");
				}
			}
		});
	}

	// 3、任务没有指定抄送人，弹出人员选择框选择抄送人(人员、部门及群组)
	function TaskNotAssignedCopyUser(eData) {
		var title = "";
		if (isNotBlank(eData.title)) {
			title = eData.title;
		}
		$.unit.open({
			title : "选择抄送人" + title,
			afterSelect : function(laReturn) {
				var taskCopyUsers = {};
				var taskId = eData.taskId;
				if (isNotBlank(laReturn.id)) {
					var userIds = laReturn.id.split(";");
					taskCopyUsers[taskId] = userIds;
					bean.taskCopyUsers = taskCopyUsers;
				} else {
					taskCopyUsers[taskId] = null;
					bean.taskCopyUsers = taskCopyUsers;
				}
				// 重新触发提交事件
				if (isNotBlank(eData.submitButtonId)) {
					$("#" + eData.submitButtonId).trigger('click');
				} else {
					// $("#" + btn_submit).trigger('click');
					oAlert("请重新提交!");
				}
			}
		});
	}
	// 4、任务没有指定督办人，弹出人员选择框选择督办人(人员和部门及群组)
	function TaskNotAssignedMonitor(eData) {
		var title = "";
		if (isNotBlank(eData.title)) {
			title = eData.title;
		}
		$.unit.open({
			title : "选择督办人" + title,
			afterSelect : function(laReturn) {
				var taskMonitors = {};
				var taskId = eData.taskId;
				if (isNotBlank(laReturn.id)) {
					var userIds = laReturn.id.split(";");
					taskMonitors[taskId] = userIds;
					bean.taskMonitors = taskMonitors;
				} else {
					taskMonitors[taskId] = null;
					bean.taskMonitors = taskMonitors;
				}
				// 重新触发提交事件
				if (isNotBlank(eData.submitButtonId)) {
					$("#" + eData.submitButtonId).trigger('click');
				} else {
					// $("#" + btn_submit).trigger('click');
					oAlert("请重新提交!");
				}
			}
		});
	}
	// 5、选择具体办理人
	function ChooseSpecificUser(eData) {
		var taskId = eData.taskId;
		var userIds = eData.userIds;
		var users = [];
		var dlgSelector = "#dlg_choose_specific_user";
		// 创建弹出框Div
		createDiv(dlgSelector);
		JDS.call({
			service : "workService.getUsers",
			data : [ userIds ],
			async : false,
			success : function(result) {
				users = result.data;
			}
		});
		for ( var i = 0; i < users.length; i++) {
			var user = users[i];
			var checkbox = "<div><label class='checkbox inline'><input id='" + user.id
					+ "' name='chooseSpecificUser' type='checkbox' value='" + user.id + "'>" + user.userName
					+ "</label></div>";
			$(dlgSelector).append(checkbox);
		}
		var options = {
			title : "选择具体办理人",
			modal : true,
			autoOpen : true,
			resizable : false,
			buttons : {
				"确定" : function(e) {
					if ($("input[name=chooseSpecificUser]:checked").length < 1) {
						oAlert("请选择具体办理人!");
						return;
					}
					var $checkboxes = $("input[name=chooseSpecificUser]:checked");
					var userIds = [];
					$.each($checkboxes, function(i) {
						userIds.push($(this).val());
					});
					var taskUsers = {};
					taskUsers[taskId] = userIds;
					bean.taskUsers = taskUsers;
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					if (isNotBlank(eData.submitButtonId)) {
						$("#" + eData.submitButtonId).trigger('click');
					} else {
						// $("#" + btn_submit).trigger('click');
						oAlert("请重新提交!");
					}
				},
				"取消" : function(e) {
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$(dlgSelector).html("");
			}
		};
		$(dlgSelector).oDialog(options);
	}
	// 6、只能选择一个人办理
	function OnlyChooseOneUser(eData) {
		var taskId = eData.taskId;
		var userIds = eData.userIds;
		var users = [];
		var dlgSelector = "#dlg_choose_one_user";
		// 创建弹出框Div
		createDiv(dlgSelector);
		JDS.call({
			service : "workService.getUsers",
			data : [ userIds ],
			async : false,
			success : function(result) {
				users = result.data;
			}
		});
		for ( var i = 0; i < users.length; i++) {
			var user = users[i];
			var radio = "<div><label class='radio inline'><input id='" + user.id
					+ "' name='onlyOneUser' type='radio' value='" + user.id + "'>" + user.userName
					+ "</label></div>";
			$(dlgSelector).append(radio);
		}
		var options = {
			title : "选择一个办理人",
			modal : true,
			autoOpen : true,
			resizable : false,
			buttons : {
				"确定" : function(e) {
					if ($("input[name=onlyOneUser]:checked").val() == null) {
						oAlert("请选择一个承办人!");
						return;
					}
					var taskUsers = {};
					taskUsers[taskId] = [ $("input[name=onlyOneUser]:checked").val() ];
					bean.taskUsers = taskUsers;
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					if (isNotBlank(eData.submitButtonId)) {
						$("#" + eData.submitButtonId).trigger('click');
					} else {
						// $("#" + btn_submit).trigger('click');
						oAlert("请重新提交!");
					}
				},
				"取消" : function(e) {
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$(dlgSelector).html("");
			}
		};
		$(dlgSelector).oDialog(options);
	}
	// 7、弹出环节选择框选择下一流程环节
	function JudgmentBranchFlowNotFound(eData) {
		var toTasks = eData.toTasks;
		bean.fromTaskId = eData.fromTaskId;
		if (toTasks != null) {
			for ( var i = 0; i < toTasks.length; i++) {
				var task = toTasks[i];
				var radio = "<div><label class='radio inline'><input id='" + task.id
						+ "' name='toTaskId' type='radio' value='" + task.id + "'>" + task.name
						+ "</label></div>";
				$("#dlg_select_task").append(radio);
			}
		}
		// 显示选择下一环节弹出框
		showSelectTaskDialog({}, eData);
	}
	// 显示选择下一环节弹出框
	function showSelectTaskDialog(option, eData) {
		// 初始化下一流程选择框
		var options = {
			title : "选择下一环节",
			autoOpen : true,
			height : 300,
			width : 350,
			resizable : false,
			modal : true,
			buttons : {
				"确定" : function(e) {
					if ($("input[name=toTaskId]:checked").val() == null) {
						bean.fromTaskId = null;
						oAlert("请选择环节!");
						return;
					}
					bean.toTaskId = $("input[name=toTaskId]:checked").val();
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					if (isNotBlank(eData.submitButtonId)) {
						$("#" + eData.submitButtonId).trigger('click');
					} else {
						// $("#" + btn_submit).trigger('click');
						oAlert("请重新提交!");
					}
				},
				"取消" : function(e) {
					bean.fromTaskId = null;
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_select_task").html("");
			}
		};
		options = $.extend(true, options, option);
		$("#dlg_select_task").oDialog(options);
	}
	// 9、找到多个判断分支流向
	function MultiJudgmentBranch(eData) {
		JudgmentBranchFlowNotFound(eData);
	}
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
				"data" : ""
			},
			type : "POST"
		}
	};
	// 9、弹出环节选择框选择下一子流程
	function SubFlowNotFound(eData) {
		selectSubFlowSetting.async.otherParam.data = eData.excludeFlowId;
		$("#wf_select_sub_flow").popupTreeWindow({
			title : "选择子流程",
			autoOpen : true,
			treeSetting : selectSubFlowSetting,
			afterSelect : function(retVal) {
				bean.toSubFlowId = retVal.value;
				// 重新触发提交事件
				if (isNotBlank(eData.submitButtonId)) {
					$("#" + eData.submitButtonId).trigger('click');
				} else {
					// $("#" + btn_submit).trigger('click');
					oAlert("请重新提交!");
				}
			},
			afterCancel : function() {
			}
		});
	}
	// 10、子流程合并等待异常类
	function SubFlowMerge(eData) {
		$("#dlg_sub_flow_merge").html(eData.msg);
		$("#dlg_sub_flow_merge").oDialog({
			title : "子流程合并等待",
			autoOpen : true,
			height : 300,
			width : 350,
			resizable : false,
			modal : true,
			buttons : {
				"不等待" : function(e) {
					var waitForMerge = {};
					waitForMerge[eData.subFlowInstUuid] = false;
					bean.waitForMerge = waitForMerge;
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					if (isNotBlank(eData.submitButtonId)) {
						$("#" + eData.submitButtonId).trigger('click');
					} else {
						$("#" + btn_submit).trigger('click');
					}
				},
				"等待" : function(e) {
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_sub_flow_merge").html("");
			}
		});
	}

	// 11、用户没有权限访问流程
	function IdentityNotFlowPermission(eData) {
		var taskId = eData.taskId;
		if (bean.taskUsers != null && bean.taskUsers.hasOwnProperty(taskId)) {
			delete bean.taskUsers[taskId];
		}
		oAlert(eData.msg);
	}

	// 12、找不到退回操作的退回环节异常类
	function RollbackTaskNotFound(eData) {
		var toTasks = eData.rollbackTasks;
		if (toTasks != null) {
			for ( var i = 0; i < toTasks.length; i++) {
				var task = toTasks[i];
				var radio = "<div><label class='radio inline'><input id='" + task.id
						+ "' name='rollbackToTaskId' type='radio' value='" + task.id + "'>" + task.name
						+ "</label></div>";
				$("#dlg_select_rollback_task").append(radio);
			}
		}
		// 显示选择退回环节弹出框
		showSelectRollbackTaskDialog();
	}
	// 显示选择退回环节弹出框
	function showSelectRollbackTaskDialog() {
		// 显示选择退回环节弹出框
		var options = {
			title : "选择退回环节",
			autoOpen : true,
			height : 300,
			width : 350,
			resizable : false,
			modal : true,
			buttons : {
				"确定" : function(e) {
					if ($("input[name=rollbackToTaskId]:checked").val() == null) {
						oAlert("请选择环节!");
						return;
					}
					bean.rollbackToTaskId = $("input[name=rollbackToTaskId]:checked").val();
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					$("#" + btn_rollback).trigger("click");
				},
				"取消" : function(e) {
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_select_rollback_task").html("");
			}
		};
		$("#dlg_select_rollback_task").oDialog(options);
	}
	/** ******************************** 回调处理结束 ******************************** */

	/** ********************************* 退回开始 ********************************* */
	// 如果任务不存在，则隐藏退回按钮
	hideTaskButtons(btn_rollback);
	// 如果任务不存在，则隐藏直接退回按钮
	hideTaskButtons(btn_direct_rollback);

	// 退回
	$(":button[name='" + btn_rollback + "']").each(function() {
		$(this).click($.proxy(onRollback, this));
	});
	// 退回处理
	function onRollback() {
		// 操作动作及类型
		bean.action = isBlank($(this).text()) ? "退回" : $(this).text();
		bean.actionType = "Rollback";
		JDS.call({
			service : rollbackService,
			data : [ bean ],
			success : function(result) {
				oAlert("退回成功!", function() {
					hanlderSuccess(result);
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	WorkFlow.rollback = onRollback;

	// 直接退回
	$(":button[name='" + btn_direct_rollback + "']").each(function() {
		$(this).click($.proxy(onDirectRollback, this));
	});
	// 直接退回处理
	function onDirectRollback() {
		var taskInstUuids = [];
		taskInstUuids.push(bean.taskInstUuid);
		JDS.call({
			service : directRollbackService,
			data : [ taskInstUuids ],
			success : function(result) {
				oAlert("直接退回成功!", function() {
					hanlderSuccess(result);
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ********************************* 退回结束 ********************************* */

	/** ********************************* 撤回开始 ********************************* */
	// 如果任务不存在，则隐藏撤回按钮
	hideTaskButtons(btn_cancel);

	// 撤回
	$(":button[name='" + btn_cancel + "']").each(function() {
		$(this).click($.proxy(onCancel, this));
	});
	// 撤回处理
	function onCancel() {
		var taskInstUuids = [];
		taskInstUuids.push(bean.taskInstUuid);
		JDS.call({
			service : cancelService,
			data : [ taskInstUuids ],
			success : function(result) {
				// 打开待办工作界面
				JDS.call({
					service : "workService.getTodoTaskByFlowInstUuid",
					data : [ bean.flowInstUuid ],
					success : function(result) {
						if (isNotBlank(result.data.uuid)) {
							oAlert("撤回成功!", function() {
								// 刷新父窗口
								if (returnWindow) {
									returnWindow();
								}
								var taskInstUuid = result.data.uuid;
								var flowInstUuid = bean.flowInstUuid;
								window.location = ctx + "/workflow/work/view/todo?taskInstUuid="
										+ taskInstUuid + "&flowInstUuid=" + flowInstUuid;
							});
						} else {
							oAlert("撤回失败!");
						}
					},
					error : function(jqXHR) {
						// 处理流程操作返回 的错误信息
						hanlderError(jqXHR);
					}
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ********************************* 撤回结束 ********************************* */

	/** ********************************* 转办开始 ********************************* */
	// 如果任务不存在，则隐藏转办按钮
	hideTaskButtons(btn_transfer);

	// 转办
	$(":button[name='" + btn_transfer + "']").each(function() {
		$(this).click($.proxy(onTransfer, this));
	});
	// 转办处理
	function onTransfer() {
		$.unit.open({
			title : "选择转办人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var transferUserIds = laReturn.id.split(";");
					JDS.call({
						service : transferService,
						data : [ [ bean.taskInstUuid ], transferUserIds ],
						success : function(result) {
							oAlert("转办成功!", function() {
								hanlderSuccess(result);
							});
						},
						error : function(jqXHR) {
							// 处理流程操作返回 的错误信息
							hanlderError(jqXHR);
						}
					});
				} else {
					oAlert("转办人员不能为空!");
				}
			}
		});
	}
	/** ********************************* 转办结束 ********************************* */

	/** ********************************* 会签开始 ********************************* */
	// 如果任务不存在，则隐藏会签按钮
	hideTaskButtons(btn_counterSign);

	// 会签
	$(":button[name='" + btn_counterSign + "']").each(function() {
		$(this).click($.proxy(onCounterSign, this));
	});
	// 会签处理
	function onCounterSign() {
		$.unit.open({
			title : "选择会签人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var counterSignUserIds = laReturn.id.split(";");
					JDS.call({
						service : counterSignService,
						data : [ [ bean.taskInstUuid ], counterSignUserIds ],
						success : function(result) {
							oAlert("会签成功!", function() {
								hanlderSuccess(result);
							});
						},
						error : function(jqXHR) {
							// 处理流程操作返回 的错误信息
							hanlderError(jqXHR);
						}
					});
				} else {
					oAlert("会签人员不能为空!");
				}
			}
		});
	}
	/** ********************************* 会签结束 ********************************* */

	/** ********************************* 关注开始 ********************************* */
	// 如果任务不存在，则隐藏关注按钮
	hideTaskButtons(btn_attention);

	// 关注
	$(":button[name='" + btn_attention + "']").each(function() {
		$(this).click($.proxy(onAttention, this));
	});
	// 关注处理
	function onAttention(e) {
		var taskInstUuids = [];
		taskInstUuids.push(bean.taskInstUuid);
		JDS.call({
			service : attentionService,
			data : [ taskInstUuids ],
			success : function(result) {
				oAlert("关注成功!");
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ********************************* 关注结束 ********************************* */

	/** ********************************* 套打开始 ********************************* */
	// 如果任务不存在，则隐藏打印按钮
	hideTaskButtons(btn_print);

	// 套打
	$(":button[name='" + btn_print + "']").each(function() {
		$(this).click($.proxy(onPrint, this));
	});
	// 套打处理
	function onPrint(e) {
		if (isBlank(bean.flowInstUuid)) {
			onSave(false, function(data) {
				$("input[name=flowInstUuid]", $("#print_form")).val(data["flowInstUuid"]);
				bean.flowInstUuid = data["flowInstUuid"];
				// window.location = ctx +
				// "/workflow/work/view/draft?flowInstUuid=" +
				// data["flowInstUuid"];
				// $(dytableSelector).dytable("setDataUuid", data["dataUuid"]);
				// 打印处理
				doPrint(e);
			});
		} else {
			if (isBlank($("input[name=flowInstUuid]", $("#print_form")).val())) {
				$("input[name=flowInstUuid]", $("#print_form")).val(flowInstUuid);
			}
			// 打印处理
			doPrint(e);
		}

	}
	// 打印处理
	function doPrint(e) {
		// 提交前回调处理
		if (WorkFlow.getPrintService) {
			var printService = WorkFlow.getPrintService.call(this);
			$("input[name=printService]", $("#print_form")).val(printService);
		}
		var $printForm = $("#print_form");
		$printForm.attr("action", ctx + printUrl);
		$printForm[0].submit();
		e.stopPropagation();
		// 重置打印表单
		$printForm[0].reset();
	}
	// iframe加载内容事件处理
	var document = this;
	$("#print_form_iframe").load(function(e) {
		var doc = this.contentWindow.document;
		var msg = $("#print_response_msg", doc).val();
		var pts = eval(msg);
		doPrintMsgLoad.call(document, pts);
	});
	// 提示后台返回的打印信息
	function doPrintMsgLoad(pts) {
		// 重置打印表单
		var $printForm = $("#print_form");
		$printForm[0].reset();

		if (pts && pts.constructor == Array) {
			for ( var i = 0; i < pts.length; i++) {
				var printTemplate = pts[i];
				var radio = "<div><label class='radio inline'><input id='print_template_" + printTemplate.id
						+ "' name='selectPrintTemplateId' type='radio' value='" + printTemplate.id + "'>"
						+ printTemplate.name + "</label></div>";
				$("#dlg_select_print_template").append(radio);
			}
			// 显示选择套打模板弹出框
			showSelectPrintTemplateDialog();
		} else if (typeof pts == 'object') {
			oAlert(pts.msg);
		} else {
			oAlert(pts);
		}
	}
	// 显示选择套打模板弹出框
	function showSelectPrintTemplateDialog() {
		// 显示选择套打模板弹出框
		var options = {
			title : "选择套打模板",
			autoOpen : true,
			height : 300,
			width : 350,
			resizable : false,
			modal : true,
			buttons : {
				"确定" : function(e) {
					if ($("input[name=selectPrintTemplateId]:checked").val() == null) {
						oAlert("请选择环节!");
						return;
					}
					var printTemplateId = $("input[name=selectPrintTemplateId]:checked").val();
					// 设置套打模板
					$("input[name=printTemplateId]").val(printTemplateId);
					e.stopPropagation();
					$(this).oDialog("close");
					// 重新触发提交事件
					$("#" + btn_print).trigger("click");
				},
				"取消" : function(e) {
					e.stopPropagation();
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_select_print_template").html("");
			}
		};
		$("#dlg_select_print_template").oDialog(options);
	}
	/** ********************************* 套打结束 ********************************* */

	/** ********************************* 抄送开始 ********************************* */
	// 如果任务不存在，则隐藏抄送按钮
	hideTaskButtons(btn_copyTo);

	// 抄送
	$(":button[name='" + btn_copyTo + "']").each(function() {
		$(this).click($.proxy(onCopyTo, this));
	});
	// 抄送处理
	function onCopyTo() {
		$.unit.open({
			title : "选择抄送人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var copyToUserIds = laReturn.id.split(";");
					JDS.call({
						service : copyToService,
						data : [ [ bean.taskInstUuid ], copyToUserIds ],
						success : function(result) {
							oAlert("抄送成功!", function() {
								// hanlderSuccess(result);
							});
						},
						error : function(jqXHR) {
							// 处理流程操作返回 的错误信息
							hanlderError(jqXHR);
						}
					});
				}
			}
		});
	}
	/** ********************************* 抄送开始 ********************************* */

	/** ******************************** 签署意见开始 ******************************* */
	// 如果任务不存在，则隐藏签署意见按钮
	hideTaskButtons(btn_sign_opinion);

	// 签署意见
	$(":button[name='" + btn_sign_opinion + "']").hide();
	$(":button[name='" + btn_sign_opinion + "']").each(function() {
		$(this).click($.proxy(onSignOpinion, this));
	});
	// 签署意见处理
	function onSignOpinion(e) {
		showSignOpinionDialog(false);
	}

	function showSignOpinionDialog(submitOpinion) {
		if (isNotBlank(bean.taskInstUuid) && submitOpinion === true && bean.aclRole === "TODO") {
			$("#mini_wf_opinion").workflowMiniOpinion("open");
		}
	}

	// 显示签署意见
	function showOpinion() {
		if (isNotBlank(bean.taskInstUuid) && bean.aclRole === "TODO") {
			var $btn = $(".form_toolbar>.form_operate>button:visible:first");
			var $opinion = $("#mini_wf_opinion");
			var position1 = {
				left : 1000
			};
			if ($btn.length != 0) {
				position1 = $btn.position();
			}
			var position2 = $opinion.position();
			var opinionWidth = position1.left - position2.left - 120;
			var valStr = $("input[name=opinions]").val();
			var opinions = JSON.parse(valStr);
			$("#mini_wf_opinion").workflowMiniOpinion({
				width : opinionWidth,
				opinions : opinions,
				opinionChange : function(retVal) {
					bean.opinionValue = retVal.value;
					bean.opinionLabel = retVal.label;
					bean.opinionText = retVal.text;
				}
			});
		}
	}
	/** ******************************** 签署意见结束 ******************************* */

	/** ******************************** 办理意见开始 ******************************* */
	// 如果任务不存在，则隐藏办理意见按钮
	hideTaskButtons(btn_view_opinion);
	// 办理意见
	$(":button[name='" + btn_view_opinion + "']").hide();
	$(":button[name='" + btn_view_opinion + "']").each(function() {
		$(this).click($.proxy(onViewOpinion, this));
	});
	// 办理意见处理
	function onViewOpinion(e) {
		$("#dlg_view_opinion").oDialog({
			title : "办理意见",
			autoOpen : true,
			height : 400,
			width : 450,
			open : function() {
				JDS.call({
					service : viewOpinionService,
					data : bean,
					success : function(result) {
						$("#dlg_view_opinion").html(result.data);
					},
					error : function(jqXHR) {
						// 处理流程操作返回 的错误信息
						hanlderError(jqXHR);
					}
				});
			},
			close : function() {
				$("#dlg_view_opinion").html("");
			}
		});
	}
	/** ******************************** 办理意见结束 ******************************* */

	/** ******************************** 办理过程开始 ******************************* */
	// 如果任务不存在，则隐藏办理过程按钮
	hideTaskButtons(btn_view_process);
	// 办理过程
	if ($(":button[name='" + btn_view_process + "']").length == 0) {
		$(":button[name='" + btn_view_process + "']").hide();
	} else {
		$(":button[name='" + btn_view_process + "']").hide();
	}
	$(":button[name='" + btn_view_process + "'], #process .view_process").each(function() {
		$(this).click($.proxy(onViewProcess, this));
	});
	// 办理意见处理
	function onViewProcess(e) {
		showProcess();
	}

	$("#show_rollback_record, #show_no_opinion_record").change(function(e) {
		showProcess();
	});

	function showProcess(show_rollback_record, show_no_opinion_record) {
		var show_rollback_record = $("#show_rollback_record").attr("checked") == null ? false : true;
		var show_no_opinion_record = $("#show_no_opinion_record").attr("checked") == null ? false : true;
		JDS.call({
			service : viewProcessService,
			data : [ bean.flowInstUuid, show_rollback_record, show_no_opinion_record ],
			success : function(result) {
				$("#process_content").html(result.data);
				$("#dlg_view_process").oDialog("open");

				// 调整自适应表单宽度
				adjustWidthToForm();
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}

	// 办理意见窗口
	$("#dlg_view_process").oDialog({
		title : "办理过程",
		modal : true,
		resizable : false,
		autoOpen : false,
		height : 540,
		width : 940
	});
	/** ******************************** 办理过程结束 ******************************* */

	/** ******************************** 时间轴开始 ******************************** */
	function showTimeline() {
		JDS.call({
			service : timelineService,
			data : [ bean.flowInstUuid ],
			success : function(result) {
			}
		});
	}
	/** ******************************** 时间轴结束 ******************************** */

	/** ******************************** 催办开始 ********************************** */
	// 如果任务不存在，则隐藏办理过程按钮
	hideTaskButtons(btn_remind);
	// 催办
	$(":button[name='" + btn_remind + "']").each(function() {
		$(this).click($.proxy(onRemind, this));
	});
	// 催办处理
	function onRemind(e) {
		JDS.call({
			service : remindService,
			data : [ [ bean.taskInstUuid ] ],
			success : function(result) {
				oAlert("催办成功!");
			},
			error : function(jqXHR) {
				oAlert("催办失败!");
			}
		});
	}
	/** ******************************** 催办结束 ********************************** */

	/** ******************************** 取消关注 ********************************** */
	// 如果任务不存在，则隐藏取消关注按钮
	hideTaskButtons(btn_unfollow);
	// 取消关注
	$(":button[name='" + btn_unfollow + "']").each(function() {
		$(this).click($.proxy(onUnfollow, this));
	});
	// 取消关注处理
	function onUnfollow(e) {
		JDS.call({
			service : unfollowdService,
			data : [ bean ],
			success : function(result) {
				oAlert("取消关注成功!");
			},
			error : function(jqXHR) {
				oAlert("取消关注失败!");
			}
		});
	}
	/** ******************************* 取消关注结束 ********************************* */

	/** ******************************** 移交开始 ********************************** */
	// 如果任务不存在，则隐藏移交按钮
	hideTaskButtons(btn_hand_over);
	// 移交
	$(":button[name='" + btn_hand_over + "']").each(function() {
		$(this).click($.proxy(onHandOver, this));
	});
	// 移交处理
	function onHandOver(e) {
		$.unit.open({
			title : "选择移交人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var handOverUserIds = laReturn.id.split(";");
					JDS.call({
						service : handOverService,
						data : [ [ bean.taskInstUuid ], handOverUserIds ],
						success : function(result) {
							oAlert("移交成功!", function() {
								hanlderSuccess(result);
							});
						},
						error : function(jqXHR) {
							// 处理流程操作返回 的错误信息
							hanlderError(jqXHR);
						}
					});
				} else {
					oAlert("请选择移交人员!");
				}
			}
		});
	}
	/** ******************************** 移交结束 ********************************** */

	/** ******************************** 跳转开始 ********************************** */
	// 如果任务不存在，则隐藏跳转按钮
	hideTaskButtons(btn_do_goto);
	// 跳转
	$(":button[name='" + btn_do_goto + "']").each(function() {
		$(this).click($.proxy(onDoGoto, this));
	});
	// 跳转处理
	function onDoGoto(e) {
		// doGotoService
		JDS.call({
			service : getToTasksdoGotoService,
			data : [ bean.taskInstUuid ],
			success : function(result) {
				bean.gotoTask = true;
				// 不需要签署意见
				requiredSignOpinion = false;
				showGotoTask(result.data, {
					"submitButtonId" : btn_do_goto
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}

	function showGotoTask(data, submitButton) {
		var toTasks = data.toTasks;
		bean.fromTaskId = data.fromTaskId;
		if (toTasks != null) {
			for ( var i = 0; i < toTasks.length; i++) {
				var task = toTasks[i];
				var radio = "<div><label class='radio inline'><input id='" + task.id
						+ "' name='toTaskId' type='radio' value='" + task.id + "'>" + task.name
						+ "</label></div>";
				$("#dlg_select_task").append(radio);
			}
		}
		showSelectTaskDialog({
			title : "选择跳转环节"
		}, submitButton);
	}
	/** ******************************** 跳转结束 ********************************** */

	/** ******************************** 挂起开始 ********************************** */
	// 如果任务不存在，则隐藏挂起按钮
	hideTaskButtons(btn_suspend);
	if (bean.suspensionState == 1) {
		hideButtons(btn_suspend);
	} else {
		showButtons(btn_suspend);
	}
	// 挂起
	$(":button[name='" + btn_suspend + "']").each(function() {
		$(this).click($.proxy(onSuspend, this));
	});
	// 挂起处理
	function onSuspend(e) {
		var taskInstUuids = [];
		taskInstUuids.push(bean.taskInstUuid);
		JDS.call({
			service : suspendService,
			data : [ taskInstUuids ],
			success : function(result) {
				oAlert("挂起成功!", function() {
					bean.suspensionState = 1;
					showButtons(btn_resume);
					hideButtons(btn_suspend);
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	// 创建DIV元素
	function createDiv(selector) {
		var id = selector;
		if (selector.indexOf("#") == 0) {
			id = selector.substring(1);
		}
		// 放置
		if ($(selector).length == 0) {
			$("body").append("<div id='" + id + "' />");
		}
	}
	/** ******************************** 挂起结束 ********************************** */

	/** ******************************** 恢复开始 ********************************** */
	// 如果任务不存在，则隐藏恢复按钮
	hideTaskButtons(btn_resume);
	if (bean.suspensionState == 1) {
		showButtons(btn_resume);
	} else {
		hideButtons(btn_resume);
	}
	// 恢复
	$(":button[name='" + btn_resume + "']").each(function() {
		$(this).click($.proxy(onResume, this));
	});
	// 恢复处理
	function onResume(e) {
		var taskInstUuids = [];
		taskInstUuids.push(bean.taskInstUuid);
		JDS.call({
			service : resumeService,
			data : [ taskInstUuids ],
			success : function(result) {
				oAlert("恢复成功!", function() {
					bean.suspensionState = 0;
					showButtons(btn_suspend);
					hideButtons(btn_resume);
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				hanlderError(jqXHR);
			}
		});
	}
	/** ******************************** 恢复结束 ********************************** */

	/** ******************************** 退件开始 ********************************** */
	/** ******************************** 退件结束 ********************************** */

	/** ****************************** 公共方法开始 ******************************** */
	// 任务不存在时隐藏名字为指定值的按钮
	function hideTaskButtons(name) {
		// 如果任务不存在，则隐藏签署意见按钮
		if (isBlank(bean.taskInstUuid)) {
			$(":button[name='" + name + "']").each(function() {
				$(this).hide();
			});
		}
	}
	function hideButtons(name) {
		$(":button[name='" + name + "']").each(function() {
			$(this).hide();
		});
	}
	function showButtons(name) {
		$(":button[name='" + name + "']").each(function() {
			$(this).show();
		});
	}

	// 获取动态表单标题
	function getFormTitle() {
		var formTitle = $(dytableSelector).dytable("getFieldForFormData", {
			formuuid : bean.formUuid,
			fieldMappingName : "WORKFLOW_TITLE"
		});
		return formTitle;
	}
	/** ****************************** 公共方法结束 ******************************** */
	// 显示操作按钮
	$(".form_operate").show();

	/** ****************************** 对外接口开始 ******************************** */
	// Java扩展
	// 1、printService 自定义打印服务处理
	// 2、afterSaveService 保存后服务处理
	// 3、beforeSubmitService 提交前服务处理
	// 4、afterSubmitService 提交后服务处理
	// JS扩展
	// 1、WorkFlow.afterSuccessSave 保存成功后全局回调扩展
	// 2、WorkFlow.beforeSubmit 提交前回调处理
	// 3、WorkFlow.afterSuccessSubmit 提交成功后全局回调扩展
	// 对外JS接口
	// 判断当前环节实例(工作)是否存在
	WorkFlow.isExistsWorkData = function() {
		return isNotBlank(bean.taskInstUuid) || isNotBlank(bean.flowInstUuid);
	};
	// 返回当前环节ID
	WorkFlow.getTaskId = function() {
		return $("#wf_taskId").val();
	};
	// 返回当前环节实例UUID
	WorkFlow.getTaskInstUuid = function() {
		return bean.taskInstUuid;
	};
	// 返回当前流程实例UUID
	WorkFlow.getFlowInstUuid = function() {
		return bean.flowInstUuid;
	};
	// 添加按钮
	WorkFlow.addButton = function(button) {
		$(".form_operate").append(button);
	};
	// 隐藏按钮
	WorkFlow.hideButton = function(buttonId) {
		$("button[id='" + buttonId + "']", ".form_operate").hide();
	};
	// 显示按钮
	WorkFlow.showButton = function(buttonId) {
		$("button[id='" + buttonId + "']", ".form_operate").show();
	};
	// 绑定工作流事件
	WorkFlow.bind = function(option) {
		var functionName = option.functionName;
		var id = option.id;
		var name = option.name;
		if (functionName == "print") {
			if ($("#" + btn_print).length == 0) {
				var print = '<button type="button" id="' + btn_print + '">' + name + '</button>';
				$(".form_operate").append(print);
				var $button = $("#" + btn_print, ".form_operate");
				$button.click($.proxy(onPrint, $button));
			} else {
				$("#" + btn_print).show();
				$("#" + btn_print).text(name);
			}
		} else if (functionName == "submit") {
			var $button = $("#" + id);
			$button.click($.proxy(onSubmit, $button[0]));
		} else if (functionName == "save") {
			var $button = $("#" + id);
			var callBack = option.onSuccess;
			$button.click($.proxy(onSave, $button[0], true, callBack));
		}
		if (id != null && functionName == null) {
			var $button = $("button[id='" + id + "']", ".form_operate");
			if ($button.length == 0) {
				var button = '<button type="button" id="' + id + '">' + name + '</button>';
				$(".form_operate").append(button);
				$button = $("button[id='" + id + "']", ".form_operate");
			}
			var onClick = option.onClick;
			if (onClick != null) {
				$button.click($.proxy(onClick, $button));

			}
		}
	};
	// 设置套打模板
	WorkFlow.setPrintTemplateId = function(templateId) {
		$("input[name=printTemplateId]", $("#print_form")).val(templateId);
	};
	// 设置工作数据
	WorkFlow.setWorkData = function(propName, propValue) {
		bean[propName] = propValue;
	};
	// 获取工作数据
	WorkFlow.getWorkData = function() {
		return bean;
	};
	// 放置工作额外数据
	WorkFlow.putExtraParam = function(paramName, paramValue) {
		bean.extraParams[paramName] = paramValue;
	};
	// 获取工作额外数据
	WorkFlow.getExtraParams = function() {
		return bean.extraParams;
	};
	// 显示签署意见框
	WorkFlow.showOpinion = function() {
		showOpinion();
	};
	// 签署意见
	WorkFlow.signOpinion = function(data) {
		$("#mini_wf_opinion").workflowMiniOpinion("signOpinion", data);
	};
	/** ****************************** 对外接口结束 ******************************** */
});
