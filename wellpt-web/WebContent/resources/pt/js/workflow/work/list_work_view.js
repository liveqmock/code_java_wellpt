var WorkFlow = {};
$(function() {
	// 套打角本
	var printScript = '<div style="display: none">'
			+ '<iframe id="print_form_iframe" name="print_form_iframe" style="width: 1px; height: 0px;"></iframe>'
			+ '<form id="print_form" name="print_form" action="" method="post"'
			+ '	target="print_form_iframe">' + '	<input type="hidden" name="filename" value="" />'
			+ '	<input type="hidden" name="taskUuid" value="" />'
			+ '	<input type="hidden" name="formUuid" value="" />'
			+ '	<input type="hidden" name="dataUuid" value="" />'
			+ '	<input type="hidden" name="printTemplateId"' + '		value="sdf" /></form>'
			+ '<div id="dlg_select_print_template"></div>' + '</div>';
	var bean = null;
	var loadWorkData = true;
	var getDraftService = "workService.getDraft";
	var getTodoService = "workService.getTodo";

	// 批量操作，需进行复杂交互
	// 提交
	var submitService = "workService.submit";
	// 转办
	var transferService = "workService.transfer";
	// 会签
	var counterSignService = "workService.counterSign";
	// 抄送
	var copyToService = "workService.copyTo";
	// 套打
	var printUrl = "/workflow/work/print";
	// 移交
	var handOverService = "workService.handOver";
	// 跳转
	var getToTasksdoGotoService = "workService.getToTasks";

	// 批量操作，无需进行复杂交互
	// 删除草搞
	var deleteDraftService = "workService.deleteDraft";
	// 直接退回
	var directRollbackService = "workService.directRollback";
	// 撤回
	var cancelService = "workService.cancel";
	// 撤回结束流程
	var cancelOverService = "workService.cancelOver";
	// 关注
	var attentionService = "workService.attention";
	// 取消关注
	var unfollowService = "workService.unfollow";
	// 签署意见
	var signOpinionService = "workService.signOpinion";
	// 已阅
	var markReadService = "workService.markRead";
	// 未阅
	var markUnreadService = "workService.markUnread";
	// 催办
	var remindService = "workService.remind";

	// 流程错误代码
	var WorkFlowErrorCode = {
		WorkFlowException : "WorkFlowException", // 工作流异常
		TaskNotAssignedUser : "TaskNotAssignedUser", // 任务没有指定参与者
		TaskNotAssignedCopyUser : "TaskNotAssignedCopyUser", // 任务没有指定抄送人
		TaskNotAssignedMonitor : "TaskNotAssignedMonitor", // 任务没有指定督办人
		OnlyChooseOneUser : "OnlyChooseOneUser", // 只能选择一个用户
		JudgmentBranchFlowNotFound : "JudgmentBranchFlowNotFound", // 无法找到可用的判断分支流向
		MultipleJudgmentBranch : "MultipleJudgmentBranch", // 找到多个判断分支流向
		SubFlowNotFound : "SubFlowNotFound", // 没有指定子流程
		SubFlowMerge : "SubFlowMerge", // 子流程合并等待
		IdentityNotFlowPermission : "IdentityNotFlowPermission", // 用户没有权限访问流程
		RollbackTaskNotFound : "RollbackTaskNotFound", // 找不到退回操作的退回环节异常类
	};

	// 如果工作信息不存在则加载工作信息
	function loadTodoWorkData(taskUuid) {
		var bean = null;
		JDS.call({
			service : getTodoService,
			data : [ taskUuid, null ],
			async : false,
			success : function(result) {
				bean = result.data;
			},
			error : function(jqXHR) {
				oAlert("操作失败!");
			}
		});
		return bean;
	}

	// 提交草搞
	WorkFlow.newWorkById = function(flowDefId) {
		var url = ctx + "/workflow/work/new/" + flowDefId;
		window.open(url);
	};

	// 提交草搞
	WorkFlow.submitDraft = function(flowInstUuid) {
		var element = $(this);

		var flowInstUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				flowInstUuids.push($(this).val());
			});
			if (flowInstUuids.length == 0) {
				oAlert("请选择一条记录!");
				return;
			}
		} else {
			flowInstUuids.push(flowInstUuid);
		}
		if (flowInstUuids.length > 1) {
			oAlert("只能选择一条记录!");
			return;
		}

		// 如果工作信息不存在则加载工作信息
		if (loadWorkData === true) {
			JDS.call({
				service : getDraftService,
				data : [ flowInstUuids[0] ],
				async : false,
				success : function(result) {
					bean = result.data;
				},
				error : function(jqXHR) {
					oAlert("操作失败!");
				}
			});
		} else {
			loadWorkData = true;
		}
		// 提交工作
		doSubmit(element, bean);
	};

	// 提交工作
	WorkFlow.submit = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择一条记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		if (taskUuids.length > 1) {
			oAlert("只能选择一条记录!");
			return;
		}

		// 加载工作信息
		if (loadWorkData === true) {
			bean = loadTodoWorkData(taskUuid);
		} else {
			loadWorkData = true;
		}

		// 提交工作
		doSubmit(element, bean);
	};
	// 提交工作
	function doSubmit(element, bean) {
		JDS.call({
			service : submitService,
			data : [ bean ],
			success : function(result) {
				oAlert("提交成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				hanlderError(element, jqXHR);
			}
		});
	}
	// 处理流程操作返回 的错误信息
	function hanlderError(element, jqXHR) {
		var msg = JSON.parse(jqXHR.responseText);
		if (WorkFlowErrorCode.WorkFlowException === msg.errorCode) {
			oAlert(msg.data);
		} else if (WorkFlowErrorCode.TaskNotAssignedUser === msg.errorCode) {
			TaskNotAssignedUser(element, msg.data);
		} else if (WorkFlowErrorCode.OnlyChooseOneUser === msg.errorCode) {
			OnlyChooseOneUser(msg.data);
		} else {
			oAlert("操作失败，请打开单据操作!");
		}
	}
	// 弹出人员选择框选择参与人(人员和部门)
	function TaskNotAssignedUser(element, data) {
		$.unit.open({
			title : "选择承办人",
			afterSelect : function(laReturn) {
				var taskUsers = {};
				var taskId = data.taskId;
				if (laReturn.id != null && laReturn.id != "") {
					// 在原来的环节办理人上增加环节办理人
					taskUsers = bean.taskUsers;
					var userIds = laReturn.id.split(";");
					taskUsers[taskId] = userIds;
				} else {
					taskUsers[taskId] = null;
					bean.taskUsers = taskUsers;
				}
				// 重新提交
				doSubmit(element, bean);
			}
		});
	}
	// 只能选择一个人办理
	function OnlyChooseOneUser(element, data) {
		oAlert("操作失败，请打开单据操作!");
	}

	// 转办
	WorkFlow.transfer = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		$.unit.open({
			title : "选择转办人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var transferUserIds = laReturn.id.split(";");
					JDS.call({
						service : transferService,
						data : [ taskUuids, transferUserIds ],
						success : function(result) {
							oAlert("转办成功!", function() {
								refreshWindow(element);
							});
						},
						error : function(jqXHR) {
							oAlert("转办失败!");
						}
					});
				} else {
					oAlert("转办人员不能为空!");
				}
			}
		});
	};

	// 会签
	WorkFlow.counterSign = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		$.unit.open({
			title : "选择会签人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var counterSignUserIds = laReturn.id.split(";");
					JDS.call({
						service : counterSignService,
						data : [ taskUuids, counterSignUserIds ],
						success : function(result) {
							oAlert("会签成功!", function() {
								refreshWindow(element);
							});
						},
						error : function(jqXHR) {
							oAlert("会签失败!");
						}
					});
				} else {
					oAlert("会签人员不能为空!");
				}
			}
		});
	};

	// 抄送
	WorkFlow.copyTo = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		// 抄送处理
		$.unit.open({
			title : "选择抄送人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var copyToUserIds = laReturn.id.split(";");
					JDS.call({
						service : copyToService,
						data : [ taskUuids, copyToUserIds ],
						success : function(result) {
							oAlert("抄送成功!");
							refreshWindow(element);
						},
						error : function(jqXHR) {
							oAlert("抄送失败!");
						}
					});
				}
			}
		});
	};

	// 套打
	// iframe加载内容事件处理
	var document = this;
	WorkFlow.print = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择一条记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		if (taskUuids.length > 1) {
			oAlert("只能选择一条记录!");
			return;
		}

		if ($("#print_form_iframe").length == 0) {
			$(element).append($(printScript));
		}

		// 加载工作信息
		var bean = loadTodoWorkData(taskUuids[0]);
		$("#print_form_iframe").load(function(e) {
			var doc = this.contentWindow.document;
			var msg = $("#print_response_msg", doc).val();
			var pts = eval(msg);
			doPrintMsgLoad.call(document, pts);
		});
		var $printForm = $("#print_form");
		$("input[name=filename]", $printForm).val(bean.title + ".doc");
		$("input[name=taskUuid]", $printForm).val(bean.taskUuid);
		$("input[name=formUuid]", $printForm).val(bean.formUuid);
		$("input[name=dataUuid]", $printForm).val(bean.dataUuid);
		$("input[name=printTemplateId]", $printForm).val(bean.printTemplateId);
		$printForm.attr("action", ctx + printUrl);
		$printForm[0].submit();
		// 重置打印表单
		$printForm[0].reset();
	};
	// 提示后台返回的打印信息
	function doPrintMsgLoad(pts) {
		// 重置打印表单
		var $printForm = $("#print_form");
		$printForm[0].reset();

		if (pts && pts.constructor == Array) {
			for ( var i = 0; i < pts.length; i++) {
				var printTemplate = pts[i];
				var radio = "<div><input id='print_template_" + printTemplate.id
						+ "' name='selectPrintTemplateId' type='radio' value='" + printTemplate.id
						+ "'><label for='print_template_" + printTemplate.id + "'>" + printTemplate.name
						+ "</label></div>";
				$("#dlg_select_print_template").append(radio);
			}
			$("#dlg_select_print_template").dialog({
				autoOpen : true
			});
		} else if (typeof pts == 'object') {
			oAlert(pts.msg);
		} else {
			oAlert(pts);
		}
	}

	// 移交
	WorkFlow.handOver = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		// 移交处理
		$.unit.open({
			title : "选择移交人员",
			afterSelect : function(laReturn) {
				if (laReturn.id != null && laReturn.id != "") {
					var handOverUserIds = laReturn.id.split(";");
					JDS.call({
						service : handOverService,
						data : [ taskUuids, handOverUserIds ],
						success : function(result) {
							oAlert("移交成功!", function() {
								refreshWindow(element);
							});
						},
						error : function(jqXHR) {
							oAlert("移交失败!");
						}
					});
				} else {
					oAlert("请选择移交人员!");
				}
			}
		});
	};

	// 跳转
	WorkFlow.doGoto = function(taskUuid) {
		var element = $(this);
		if ($("#dlg_select_task").length == 0) {
			$(element).append($("<div id='dlg_select_task'></div>"));
			$("#dlg_select_task").dialog({
				title : "选择下一环节",
				autoOpen : false,
				height : 300,
				width : 350,
				resizable : false,
				modal : true,
				close : function() {
					$("#dlg_select_task").html("");
				}
			});
		}
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择一条记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		if (taskUuids.length > 1) {
			oAlert("只能选择一条记录!");
			return;
		}

		// 加载工作信息
		bean = loadTodoWorkData(taskUuids[0]);
		JDS.call({
			service : getToTasksdoGotoService,
			data : [ taskUuids[0] ],
			success : function(result) {
				bean.gotoTask = true;
				var data = result.data;
				var toTasks = data.toTasks;
				bean.fromTaskId = data.fromTaskId;
				for ( var i = 0; i < toTasks.length; i++) {
					var task = toTasks[i];
					var radio = "<div><label class='radio inline'><input id='" + task.id
							+ "' name='toTaskId' type='radio' value='" + task.id + "'>" + task.name
							+ "</label></div>";
					$("#dlg_select_task").append(radio);
				}
				$("#dlg_select_task").dialog({
					autoOpen : true,
					buttons : {
						"确定" : function(e) {
							doSubmit(element, bean);
							$(this).dialog("close");
						},
						"取消" : function(e) {
							e.stopPropagation();
							$(this).dialog("close");
						}
					}
				});
			},
			error : function(jqXHR) {
				// 处理流程操作返回 的错误信息
				oAlert("操作失败!");
			}
		});
	};

	/** *************************** 批量操作开始，无需进行复杂交互 *************************** */
	// 删除草搞
	WorkFlow.deleteDraft = function(flowInstUuid) {
		var element = $(this);
		var flowInstUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				flowInstUuids.push($(this).val());
			});
			if (flowInstUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			flowInstUuids.push(flowInstUuid);
		}
		oConfirm("确定删除工作草搞?", function() {
			JDS.call({
				service : deleteDraftService,
				data : [ flowInstUuids ],
				success : function(result) {
					oAlert("删除成功!", function() {
						refreshWindow(element);
					});
				}
			});
		});
	};
	// 直接退回
	WorkFlow.directRollback = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : directRollbackService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("直接退回成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				hanlderError(element, jqXHR);
				// oAlert("直接退回失败!");
			}
		});
	};

	// 撤回
	WorkFlow.cancel = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : cancelService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("撤回成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				hanlderError(element, jqXHR);
				// oAlert("操作失败!");
			}
		});
	};

	// 撤回已结束流程
	WorkFlow.cancelOver = function(flowInstUuid) {
		var element = $(this);
		var flowInstUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				flowInstUuids.push($(this).val());
			});
			if (flowInstUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			flowInstUuids.push(flowInstUuid);
		}
		JDS.call({
			service : cancelOverService,
			data : [ flowInstUuids ],
			success : function(result) {
				oAlert("撤回成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				hanlderError(element, jqXHR);
				// oAlert("操作失败!");
			}
		});
	};

	// 关注
	WorkFlow.attention = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : attentionService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("关注成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				oAlert("关注失败!");
			}
		});
	};

	// 取消关注
	WorkFlow.unfollow = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : unfollowService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("取消关注成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				oAlert("取消关注失败!");
			}
		});
	};

	// 工作标记已阅
	WorkFlow.markRead = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : markReadService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("标记已阅成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				oAlert("标记已阅失败!");
			}
		});
	};

	// 工作标记未阅
	WorkFlow.markUnread = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : markUnreadService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("标记未阅成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				oAlert("标记未阅失败!");
			}
		});
	};
	// 签署意见
	WorkFlow.signOpinion = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}

		// 弹出签署意见弹出框
		$.workflowOpinion.open({
			ok : function(retVal) {
				var text = retVal.text;
				var value = retVal.value;
				JDS.call({
					service : signOpinionService,
					data : [ taskUuids, text, value ],
					success : function(result) {
						oAlert("意见签署成功!", function() {
							refreshWindow(element);
						});
					},
					error : function(jqXHR) {
						oAlert("意见签署失败!");
					}
				});
			},
			cancel : function() {
			}
		});
	};

	// 催办
	WorkFlow.remind = function(taskUuid) {
		var element = $(this);
		var taskUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				taskUuids.push($(this).val());
			});
			if (taskUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			taskUuids.push(taskUuid);
		}
		JDS.call({
			service : remindService,
			data : [ taskUuids ],
			success : function(result) {
				oAlert("催办成功!", function() {
					refreshWindow(element);
				});
			},
			error : function(jqXHR) {
				oAlert("催办失败!");
			}
		});
	};
	/** *************************** 批量操作结束，无需进行复杂交互 *************************** */

});