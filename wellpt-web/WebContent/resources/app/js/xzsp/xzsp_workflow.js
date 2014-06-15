var XZSP = XZSP || {};
$(function() {
	// 判断字符串不为undefined、null、空串、空格串
	var isNotBlank = StringUtils.isNotBlank;
	// 判断字符串为undefined、null、空串、空格串
	var isBlank = StringUtils.isBlank;

	// 行政审批_办件申请单
	var form_bjsqd = "userform_xzsp_bjsqd";
	// 行政审批_办件申请单从表
	var form_bjsqd_sub = "userform_xzsp_subbjsq";
	// 行政审批_办件申请单从表_工程图
	var form_bjsqd_gst = "userform_xzsp_subbjsqd_cad";
	// 行政审批_办件申请单从表_补件
	var form_bjsqd_bj = "userform_xzsp_subbjsqd_bj";

	// 打印
	var btn_print = "B004009";
	// 发送
	var btn_send = "B013001001";
	// 收件
	var btn_sj = "B013001002";
	// 送审核
	var btn_ssh = "B013001003";
	// 送批准
	var btn_spz = "B013001004";
	// 出文
	var btn_cw = "B013001005";
	// 出文确认
	var btn_cwqr = "B013001006";
	// 取件
	var btn_qj = "B013001007";
	// 修改
	var btn_modify = "B013001011";
	// 补件
	var btn_bj = "B013001021";
	// 补件确认
	var btn_bjqr = "B013001022";
	// 重新提交
	var btn_cxtj = "B013001023";
	// 退件
	var btn_tj = "B013001031";
	// 退件确认
	var btn_tjqr = "B013001032";
	// 过程补件
	var btn_gcbj = "B013001041";
	// 过程补件确认
	var btn_gcbjqr = "B013001042";
	// 重新提交
	var btn_gcbj_cxtj = "B013001043";
	// 过程补件直接出文
	var btn_gcbjzjcw = "B013001044";
	// 过程退件
	var btn_gctj = "B013001051";
	// 过程退件确认
	var btn_gctjqr = "B013001052";
	// 转报
	var btn_zb = "B013001061";
	// 转报待确认
	var btn_zbqr = "B013001062";
	// 转报直接出文
	var btn_zbzjcw = "B013001063";
	// 过收
	var btn_gs = "B013001071";
	// 过补
	var btn_gb = "B013001072";
	// 过退
	var btn_gt = "B013001073";
	// 强制退件
	var btn_qztj = "B013001074";
	// 发起审阅
	var btn_fqsy = "B013001081";
	// 确认
	var btn_confirm = "B013001082";
	// 发送短信
	var btn_send_msg = "B013001091";

	// 窗口登记
	var TASK_ID_CKDJ = "T_CKDJ";
	// 受理核准
	var TASK_ID_SLHZ = "T_SLHZ";
	// 补件
	var TASK_ID_BJ = "T_BJ";
	// 补件登记
	var TASK_ID_BJDJ = "T_BJDJ";
	// 退件
	var TASK_ID_TJ = "T_TJ";
	// 逾期退件
	var TASK_ID_YQTJ = "T_YQTJ";

	// 审批-承办
	var TASK_ID_SPCB = "T_SPCB";
	// 审核
	var TASK_ID_SH = "T_SH";
	// 批准
	var TASK_ID_PZ = "T_PZ";
	// 过程补件
	var TASK_ID_GCBJ = "T_GCBJ";
	// 过程补件登记
	var TASK_ID_GCBJDJ = "T_GCBJDJ";
	// 过程补件核准
	var TASK_ID_GCBJHZ = "T_GCBJHZ";
	// 过程退件
	var TASK_ID_GCTJ = "T_GCTJ";
	// 特殊程序开始
	var TASK_ID_TSCXKS = "T_TSCXKS";
	// 特殊程序结束
	var TASK_ID_TSCXJS = "T_TSCXJS";

	// 出文
	var TASK_ID_CW = "T_CW";
	// 窗口发件
	var TASK_ID_CKFJ = "T_CKFJ";

	// 打印模板
	// 申请材料接收凭证-登记窗口
	var PRINT_TEMPLATE_CKDJ_RECEPTION = "XZSP_DJ_RECEPTION";
	// (收件)受理决定书
	var PRINT_TEMPLATE_SL_DECISION = "XZSP_SL_DECISION";
	// 补正材料通知书
	var PRINT_TEMPLATE_BJ_NOTIFICATION = "XZSP_BJ_NOTIFICATION";
	// 审查阶段补正材料通知书
	var PRINT_TEMPLATE_GCBJ_NOTIFICATION = "XZSP_GCBJ_NOTIFICATION";
	// 不予受理告知书
	var PRINT_TEMPLATE_TJ_NOTIFICATION = "XZSP_TJ_NOTIFICATION";
	// 不予行政许可决定书
	var PRINT_TEMPLATE_GCTJ_DECISION = "XZSP_GCTJ_DECISION";
	// 特别程序告知书
	var PRINT_TEMPLATE_ZB_NOTIFICATION = "XZSP_ZB_NOTIFICATION";

	// 事项定义->面向用户
	// 个人
	var FACING_USER_PERSONAL = "015007001";
	// 单位
	var FACING_USER_UNIT = "015007002";
	// 个人和单位
	var FACING_USER_PERSONAL_AND_UNIT = "015007003";

	var bujianReasonOptions = {
		value : {
			'过期' : '过期',
			'有误' : '有误',
			'其他' : '其他'
		},
		dataInit : function(elem) {
			$(elem).css("width", "100%");
		}
	};

	// 是否创建新流程
	var isCreate = WorkFlow.isExistsWorkData() == false;
	var dytableSelector = "#dyform";
	// 是否并联事项
	var isParallel = false;
	if ("true" == $("#custom_rt_is_parallel").val()) {
		isParallel = true;
	}
	// 事项UUID
	var mattersUuid = $("#custom_rt_mattersUuid").val();
	// 并联事项UUID
	var parallelMattersUuid = $("#custom_rt_parallelMattersUuid").val();
	// 项目UUID
	var projectUuid = $("#custom_rt_projectUuid").val();
	// 项目ID
	var projectId = null;
	var mattersUuids = [];
	// 是否显示工程图列表
	var showEngineeringDrawing = $("#custom_rt_show_engineering_drawing").val() == "true";
	// 进入办件单的方式
	var goinWay = $("#ep_goinWay").val();
	if (isNotBlank(mattersUuid)) {
		mattersUuids.push(mattersUuid);
	}
	if (isNotBlank(parallelMattersUuid)) {
		mattersUuids = mattersUuids.concat(parallelMattersUuid.split(";"));
	}
	dytable.afterDialogSelect = function(fieldName, data) {
		if (fieldName === "apply_name") {
			// 联系人姓名 预留文本字段8 reservedText8
			WorkFlow.setWorkData("reservedText8", data["apply_name"]);
			// 填充申请人材料
			fillApplicantMaterials(data);
		} else if (fieldName === "apply_unit") {
			// 填充申请单位材料
			fillUnitMaterials(data);
		} else if (fieldName === "item_matters_name") {
			// 选择项目，更新打印项目UUID
			$("input[name=extraParams\\[custom_rt_projectUuid\\]]").val(data["expand_field"]);
		}
	};

	// 填充申请人材料
	function fillApplicantMaterials(data) {
		var applicantId = data["apply_person_id"];// 申请人ID(个人身份证号，单位组机机构代码)
		JDS.call({
			service : "XZSPService.getApplicantMaterials",
			data : [ mattersUuids, applicantId, projectId ],
			success : function(result) {
				afterGetMaterials(result);
			}
		});
	}

	// 填充申请单位材料
	function fillUnitMaterials(data) {
		var unitId = data["apply_unit_id"];// 申请人ID(个人身份证号，单位组机机构代码)
		JDS.call({
			service : "XZSPService.getUnitLicenses",
			data : [ mattersUuids, unitId, projectId ],
			success : function(result) {
				afterGetMaterials(result);
			}
		});
	}
	function afterGetMaterials(result) {
		// 要更新或添加的数据
		var data = result.data;
		// 从表存在的所有数据
		var allRowData = $(dytableSelector).dytable("getAllRowData", {
			tableId : form_bjsqd_sub
		});
		for ( var j = 0; j < data.length; j++) {
			var appMaterial = data[j];
			// 标记是否更新从表数据
			var updateMaterial = false;
			for ( var i = 0; i < allRowData.length; i++) {
				var rowData = allRowData[i];
				if (rowData["apply_material_name"] == appMaterial["apply_material_name"]
						&& rowData["apply_material_shixiangid"] == appMaterial["apply_material_shixiangid"]) {
					$(dytableSelector).dytable("updateRowData", {
						tableId : form_bjsqd_sub,
						rowid : rowData["id"],
						data : appMaterial,
						type : "1"
					});
					updateMaterial = true;
				}
			}
			// 从表不存在的数据直接添加
			if (updateMaterial == false) {
				$(dytableSelector).dytable("addRowData", {
					tableId : form_bjsqd_sub,
					data : appMaterial
				});
			}
		}
	}

	// 加载事项与项目信息
	if (isCreate) {
		JDS.call({
			service : "XZSPService.getMattersAndProject",
			data : [ mattersUuid, projectUuid ],
			success : function(result) {
				var data = result.data;
				var matters = data["matters"];
				var project = data["project"];
				XZSP.matters = matters;
				XZSP.project = project;

				// 类型(串联、并联前期III) 预留字段1 reservedText1
				if (isParallel) {
					WorkFlow.setWorkData("reservedText1", matters["name"]);
				} else {
					WorkFlow.setWorkData("reservedText1", "串联");
				}
				// 面向用户
				WorkFlow.setWorkData("reservedText7", matters["facingUser"]);
				// 事项名称
				setFieldValue("matters_name", matters["name"]);
				// 事项ID
				setFieldValue("matter_id", matters["id"]);
				// 办件类型
				setFieldValue("approval_type", matters["approvalType"], null, "checkbox");
				// 所属部门单位
				setFieldValue("receiving_unit", matters["belongingUnit"]);

				// 设置流程所属单位
				if (StringUtils.isNotBlank(matters["belongingUnit"])) {
					var unitIdStr = matters["belongingUnit"].split(",");
					WorkFlow.setWorkData("ownerUnitId", unitIdStr[1]);
				}

				// 运行时参数，是否显示工程图列表
				showEngineeringDrawing = matters["showEngineeringDrawing"];
				WorkFlow.putExtraParam("custom_rt_show_engineering_drawing", showEngineeringDrawing);

				// 根据面向用户的类型加载已经存在的材料或证照
				loadMaterialAndLicenseWidthFacingUser(matters["facingUser"]);

				// 应提交的材料
				// if (matters["mattersMaterials"] != null) {
				// $.each(matters["mattersMaterials"], function(i, material) {
				// var data = {};
				// data["apply_material_name"] = material["name"];
				// data["apply_material_type"] = material["type"];
				// data["apply_paper_number"] = material["paperNumber"];
				// data["finally_paper_number"] = "";
				// data["apply_electron_number"] = material["electronicNumber"];
				// data["apply_material_file"] = "";
				// data["apply_material_shixiangid"] = matters["id"];
				// data["apply_material_shixiangname"] = matters["name"];
				// data["is_bujian_material"] = "";
				// data["bujian_reason"] = "";
				// $(dytableSelector).dytable("addRowData", {
				// tableId : form_bjsqd_sub,
				// data : data
				// });
				// });
				// }

				// 设置项目信息
				if (project != null) {
					// 项目名称
					setFieldValue("item_matters_name", project["name"]);
					// 项目ID
					setFieldValue("item_matters_id", project["id"]);
					projectId = project["id"];
					// 项目编号
					setFieldValue("item_matters_num", project["code"]);
					// 项目扩展字段
					setFieldValue("expand_field", project["uuid"], "item_matters_name");
					// 项目建设单位名称
					setFieldValue("apply_unit", project["constructionUnitName"]);
					// 项目建设单位ID
					setFieldValue("apply_unit_id", project["constructionUnitId"]);
					// 联系人姓名
					setFieldValue("apply_name", project["contactsName"]);
					// 联系人姓名 预留文本字段8 reservedText8
					WorkFlow.setWorkData("reservedText8", project["contactsName"]);
					// 联系人ID
					setFieldValue("apply_person_id", project["contactsId"]);
					// 联系人方式
					setFieldValue("apply_contact_person", project["contactsMobile"]);
					// 重点项目类型 预留数字字段3 reservedNumber3
					WorkFlow.setWorkData("reservedNumber3", project["priority"]);
				} else {
					// 重点项目类型 预留数字字段3 reservedNumber3
					WorkFlow.setWorkData("reservedNumber3", 3);
				}
			}
		});
	}
	// 根据面向用户的类型加载已经存在的材料或证照
	function loadMaterialAndLicenseWidthFacingUser(facingUser) {
		var applyPersonId = getFieldValue("apply_person_id");
		var pdata = {
			"apply_person_id" : applyPersonId
		};
		var applyUnitId = getFieldValue("apply_unit_id");
		var udata = {
			"apply_unit_id" : applyUnitId
		};
		// 个人
		if (facingUser == FACING_USER_PERSONAL) {
			if (isNotBlank(applyPersonId)) {
				// 填充申请人材料
				fillApplicantMaterials(pdata);
			}
		} else if (facingUser == FACING_USER_UNIT) {
			if (isNotBlank(applyUnitId)) {
				// 填充申请单位材料
				fillUnitMaterials(udata);
			}
		} else if (facingUser == FACING_USER_PERSONAL_AND_UNIT) {
			// 个人和单位
			if (isNotBlank(applyPersonId)) {
				// 填充申请人材料
				fillApplicantMaterials(pdata);
			}
			if (isNotBlank(applyUnitId)) {
				// 填充申请单位材料
				fillUnitMaterials(udata);
			}
		}
	}
	// 设置动态表单字段值
	function setFieldValue(mappingName, value, key, type) {
		var fieldValue = {
			mappingName : mappingName,
			value : value
		};
		if (key != null) {
			fieldValue.key = key;
		}
		if (type != null) {
			fieldValue.type = type;
		}
		$(dytableSelector).dytable("setFieldValue", fieldValue);
	}
	// 获取动态表单字段值
	function getFieldValue(mappingName) {
		var value = $(dytableSelector).dytable("getFieldForFormData", {
			formuuid : $("#wf_formUuid").val(),
			fieldMappingName : mappingName
		});
		return value;
	}
	// 获取窗口接收人员
	function getAcceptPersonIds(acceptPerson) {
		if (isBlank(acceptPerson)) {
			return [];
		}
		var apIds = [];
		var userIds = acceptPerson.split(",");
		if (userIds.length == 2) {
			var ids = userIds[1].split(";");
			for ( var i = 0; i < ids.length; i++) {
				apIds.push(ids[i]);
			}
		}
		return apIds;
	}

	var timeLimit = getFieldValue("WORKFLOW_TIME_LIMIT");
	if (isNotBlank(timeLimit)) {
		setFieldValue("promise_transact_deadline", timeLimit + " 个工作日", null, "label");
	}

	// 环节表单字段处理
	var taskId = WorkFlow.getTaskId();
	// 不是补件，隐藏补件列表
	if (taskId != TASK_ID_BJ) {
	}
	// if (taskId == TASK_ID_CKFJ) {
	$.getScript(ctx + "/resources/pt/js/common/jquery.fileUpload.js");
	// }

	// 打印选项
	var printOption = {
		name : "打印",
		functionName : "print"
	};
	// 申请材料接收凭证-登记窗口
	if (taskId == TASK_ID_CKDJ) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_CKDJ_RECEPTION);
		WorkFlow.bind(printOption);
	}
	// (收件)受理决定书 一旦部门接件后，接件窗口进行"待确认"。通过已阅、未阅处理待确认
	if (taskId == TASK_ID_SPCB) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_SL_DECISION);
		WorkFlow.bind(printOption);
	}
	// 补正材料通知书 补件登记打印
	if (taskId == TASK_ID_BJDJ) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_BJ_NOTIFICATION);
		WorkFlow.bind(printOption);
	}
	// 审查阶段补正材料通知书 过程补件登记打印
	if (taskId == TASK_ID_GCBJDJ) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_GCBJ_NOTIFICATION);
		WorkFlow.bind(printOption);
	}
	// 不予受理告知书 退件打印
	if (taskId == TASK_ID_TJ) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_TJ_NOTIFICATION);
		WorkFlow.bind(printOption);
	}
	// 不予行政许可决定书 过程退件打印
	if (taskId == TASK_ID_GCTJ) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_GCTJ_DECISION);
		WorkFlow.bind(printOption);
	}
	// 特别程序告知书
	if (taskId == TASK_ID_TSCXKS) {
		WorkFlow.setPrintTemplateId(PRINT_TEMPLATE_ZB_NOTIFICATION);
		WorkFlow.bind(printOption);
	}

	// 隐藏补件列表
	$("div.title:contains('补件列表')").hide();
	$(dytableSelector).dytable("hideSubForm", {
		"tableId" : form_bjsqd_bj
	});
	// 隐藏取件信息
	if (taskId != TASK_ID_CKFJ) {
		$("tr:contains('取件信息')").hide();
		$("tr:contains('取件时间')").hide();
		$("tr:contains('备注')").hide();
	}
	// 设置从表的附件字段为不可编辑状态
	if (taskId != TASK_ID_CKDJ) {
		$(dytableSelector).dytable("setSubFileField", {
			tableId : form_bjsqd_sub,
			field : "apply_material_file",
		});
		$(dytableSelector).dytable("setSubFieldProperty", {
			tableId : form_bjsqd_sub,
			field : "finally_paper_number",
		});
		$(dytableSelector).dytable("setSubFieldProperty", {
			tableId : form_bjsqd_sub,
			field : "add_checkbox",
		});

		$(dytableSelector).dytable("setSubFileField", {
			tableId : form_bjsqd_bj,
			field : "apply_material_file",
		});
	}

	$("#" + form_bjsqd_sub).jqGrid("setGridParam", {
		afterSaveCell : function(rowid, cellname, value, iRow, iCol) {
			if (cellname == "add_checkbox") {
				if (value == true || value == "true") {
					$("#" + form_bjsqd_sub).jqGrid("setCell", rowid, "finally_paper_number", 1);
				} else {
					$("#" + form_bjsqd_sub).jqGrid("setCell", rowid, "finally_paper_number", " ");
				}
			}
			if (cellname == "finally_paper_number") {
				var n = Number(value);
				if (n > 0) {
					$("#" + form_bjsqd_sub).jqGrid("setCell", rowid, "add_checkbox", true);
				} else {
					$("#" + form_bjsqd_sub).jqGrid("setCell", rowid, "add_checkbox", " ");
					$("#" + form_bjsqd_sub).jqGrid("setCell", rowid, "add_checkbox", false);
				}
			}
		}
	});

	// 若补件原因不为空，则显示补件原因列
	var allRowData = $(dytableSelector).dytable("getAllRowData", {
		tableId : form_bjsqd_sub
	});
	$.each(allRowData, function(i, rowData) {
		if (isNotBlank(rowData["bujian_reason"])) {
			$(dytableSelector).dytable("showSubFormField", {
				tableId : form_bjsqd_sub,
				field : "bujian_reason",
				type : "1"
			});
		}
	});
	var allRowData = $(dytableSelector).dytable("getAllRowData", {
		tableId : form_bjsqd_bj
	});
	$.each(allRowData, function(i, rowData) {
		if (isNotBlank(rowData["bujian_reason"])) {
			$(dytableSelector).dytable("showSubFormField", {
				tableId : form_bjsqd_bj,
				field : "bujian_reason",
				type : "1"
			});
		}
	});

	// 设置workBean属性
	// 设置业务类型ID
	WorkFlow.setWorkData("businessTypeId", "XZSP");
	// 显示签署意见框
	WorkFlow.showOpinion();

	// 提交前服务处理
	WorkFlow.setWorkData("beforeSubmitService", "XZSPBizService.beforeSubmit");
	// 提交后服务处理
	WorkFlow.setWorkData("afterSubmitService", "XZSPBizService.afterSubmit");

	// 行政审批打印服务
	WorkFlow.getPrintService = function() {
		return "XZSPPrintService.print";
	};

	// 提交前处理
	WorkFlow.beforeSubmit = function(bean) {
		var btnId = $(this).attr("id");
		// 串联件提交到受理人员
		if (isCreate == true && !isParallel && XZSP.matters != null) {
			if (isNotBlank(XZSP.matters["acceptPerson"])) {
				var taskUsers = bean.taskUsers;
				taskUsers[TASK_ID_SLHZ] = getAcceptPersonIds(XZSP.matters["acceptPerson"]);// ["U0010000021"];
			}
		} else if (btnId == btn_sj && XZSP.sj != "done") {// 收件
			$.get(ctx + "/xzsp/sj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowSJDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_bj && XZSP.bj != "done") {// 补件
			$.get(ctx + "/xzsp/bj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowBJDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_bjqr && XZSP.bjqr != "done") {// 补件确认
			$.get(ctx + "/xzsp/bjqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowBJQRDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_cxtj || btnId == btn_gcbj_cxtj) && XZSP.bjdj != "done") {// 重新提交
			$.get(ctx + "/xzsp/bjdj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowBJDJDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_tj || (btnId == btn_qztj && taskId == TASK_ID_BJDJ)) && XZSP.tj != "done") {// 退件、强制退件
			$.get(ctx + "/xzsp/tj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowTJDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_tjqr && XZSP.tjqr != "done") {// 退件确认
			$.get(ctx + "/xzsp/tjqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowTJQRDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_gcbj || btnId == btn_gb) && XZSP.gcbj != "done") {// 过程补件、过补
			$.get(ctx + "/xzsp/gcbj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowGCBJDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_gcbjqr && XZSP.gcbjqr != "done") {// 过程补件确认
			$.get(ctx + "/xzsp/gcbjqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowGCBJQRDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_gctj || btnId == btn_gt || (btnId == btn_qztj && taskId == TASK_ID_GCBJDJ))
				&& XZSP.gctj != "done") {// 过程退件、过退、强制退件
			$.get(ctx + "/xzsp/gctj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowGCTJDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_gctjqr && XZSP.gctjqr != "done") {// 过程退件确认
			$.get(ctx + "/xzsp/gctjqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowGCTJQRDialog(data, bean);
			});
			return false;
		} else if (btnId == btn_gcbjzjcw && XZSP.gcbjzjcw != "done") {// 过程补件直接出文
			var $gcbjdjDone = $("#custom_rt_is_gcbj_done_" + bean.taskInstUuid);
			if ($gcbjdjDone.length == 0 || $gcbjdjDone.val() == "false") {
				oConfirm("过程补件未经窗口登记，是否仍需进行过补直接出文?", function() {
					XZSP.gcbjzjcw = "done";
					$("#" + bean.submitButtonId).trigger("click");
				});
			}
			return false;
		} else if ((btnId == btn_cw || btnId == btn_gcbjzjcw || btnId == btn_zbzjcw) && XZSP.cw != "done") {// 出文、过程补件直接出文、转报直接出文
			$.get(ctx + "/xzsp/cw?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowCWDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_cwqr || $(this).text() == "过程补件出文确认") && XZSP.cwqr != "done") {// 出文确认、过程补件出文确认
			$.get(ctx + "/xzsp/cwqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowCWQRDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_zb) && XZSP.zb != "done") {// 转报
			$.get(ctx + "/xzsp/zb?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowZBDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_zbqr) && XZSP.zbqr != "done") {// 转报确认
			$.get(ctx + "/xzsp/zbqr?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowZBQRDialog(data, bean);
			});
			return false;
		} else if ((btnId == btn_qj) && XZSP.qj != "done") {// 取件
			$.get(ctx + "/xzsp/qj?mattersUuid=" + mattersUuid, function(data) {
				prepareAndShowQJDialog(data, bean);
			});
			return false;
		}
		return true;
	};
	// 保存成功后处理
	WorkFlow.afterSuccessSave = function(bean) {
		// 修改按钮 显示打印按钮，隐藏发送按钮
		if (taskId == TASK_ID_CKDJ) {
			// 判断是否打印过
			if (WorkFlow.isExistsWorkData()) {
				WorkFlow.showButton(btn_modify);
				WorkFlow.showButton(btn_send);

				WorkFlow.hideButton(btn_print);
			}

			if (!WorkFlow.isExistsWorkData()) {
				WorkFlow.hideButton(btn_send);
				WorkFlow.hideButton(btn_modify);
			}
		}
	};
	// 提交成功后处理
	WorkFlow.afterSuccessSubmit = function(bean) {
		// 如果是新提交的流程，则关闭当前窗口
		if (bean.taskId == TASK_ID_CKDJ && isBlank(bean.taskInstUuid)) {
			oAlert("发送成功!", function() {
				// 如果从项目库打开，关闭项目窗口
				if (isNotBlank(projectUuid)) {
					if (goinWay == "PROJECT_FORM_GOIN") {
						window.opener.close();
					}
				}
				window.close();
			});
			return true;
		}
		return false;
	};

	/** ********************************* 收件开始 ********************************* */
	// 准备与弹出收件窗口
	function prepareAndShowSJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_sj").length == 0) {
			$("body").append("<div id='dlg_xzsp_sj' />");
		}

		$("#dlg_xzsp_sj").html("");
		$("#dlg_xzsp_sj").html(data);

		showSJDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("sj", "XZSP_" + taskId);
	}

	// 是否发送短信处理
	function bindSendSms(sp, templateId) {
		var taskInstUuid = WorkFlow.getTaskInstUuid();
		var flowInstUuid = WorkFlow.getFlowInstUuid();
		var isSendMsg = "#" + sp + "_is_send_msg";
		var dlg = "#dlg_xzsp_" + sp;
		var content = "#" + sp + "_send_msg_content";
		var rowContent = ".row_" + sp + "_send_msg_content";
		$(isSendMsg, dlg).change(function() {
			var messageTemplate = $(this).data("messageTemplate");
			if (messageTemplate == null) {
				JDS.call({
					service : "XZSPBizService.getMessageTemplate",
					data : [ taskId, taskInstUuid, flowInstUuid, templateId ],
					async : false,
					success : function(result) {
						$(this).data("messageTemplate", result.data);
						messageTemplate = $(this).data("messageTemplate");
					}
				});
			} else {
				messageTemplate = $(this).data("messageTemplate");
			}
			$(content, dlg).text(messageTemplate.smsBody);
			if ($(this).attr("checked") === "checked") {
				$(rowContent, dlg).show();
			} else {
				$(rowContent, dlg).hide();
			}
		});
	}
	// 收集短信信息
	function collectSms(sp) {
		var isSendMsg = "#" + sp + "_is_send_msg";
		var dlg = "#dlg_xzsp_" + sp;

		var recipientSelector = "#" + "send_msg_recipient";
		var mobileSelector = "#" + "send_msg_mobile";
		var contentSelector = "#" + sp + "_bjqr_send_msg_content";
		if ($(isSendMsg, dlg).attr("checked") === "checked") {
			var recipient = $(recipientSelector).val();
			var mobile = $(mobileSelector).val();
			var content = $(contentSelector).text();
			WorkFlow.putExtraParam("is_send_msg", recipient);
			WorkFlow.putExtraParam("send_msg_recipient", mobile);
			WorkFlow.putExtraParam("send_msg_content", content);
		}
	}

	// 弹出收件窗口
	function showSJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "收件",
			autoOpen : true,
			width : 650,
			height : 350,
			resizable : false,
			modal : true,
			open : function() {
				// 根据事项UUID及串、并联关系获取办理时限
				JDS.call({
					service : "XZSPService.getMattersWorkdays",
					data : [ mattersUuid, isParallel ],
					success : function(result) {
						var data = result.data;
						// 设置工作日
						if (data.length != 0) {
							$("#sj_time_limit").html("");
							$.each(data, function() {
								var option = '<option value="' + this.workday + '">' + '(' + this.workday
										+ '个工作日)' + this.remark + '</option>';
								$("#sj_time_limit").append(option);
							});
						}
					}
				});
			},
			buttons : {
				"确定" : function(e) {
					// 设置办理时限
					var sjTimeLimit = $("#sj_time_limit > option:selected").val();
					if (isBlank(sjTimeLimit)) {
						oAlert("请选择办理时限!");
					} else {
						// 办理时限
						setFieldValue("promise_transact_deadline", sjTimeLimit);
						// 收集短信信息
						collectSms("sj");
						XZSP.sj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_sj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_sj").oDialog(options);
	}
	/** ********************************* 收件结束 ********************************* */

	/** ********************************* 补件开始 ********************************* */
	// 准备与弹出补件窗口
	function prepareAndShowBJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_bj").length == 0) {
			$("body").append("<div id='dlg_xzsp_bj' />");
		}

		$("#dlg_xzsp_bj").html("");
		$("#dlg_xzsp_bj").html(data);

		showBJDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 显示补件对话框
	function showBJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "补件",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				var $bjFileList = $("#bj_file_list").jqGrid(
						{
							datatype : "local",
							colNames : [ "uuid", "材料类型", "递交附件", "所属事项ID", "所属事项名称", "附件名称", "纸质缺件", "电子缺件",
									"应补纸质", "应补电子", "补件原因" ],
							colModel : [ {
								name : "uuid",
								index : "uuid",
								width : "180",
								hidden : true,
								key : true
							}, {
								name : "apply_material_type",
								index : "apply_material_type",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_file",
								index : "apply_material_file",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangid",
								index : "apply_material_shixiangid",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangname",
								index : "apply_material_shixiangname",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_name",
								index : "apply_material_name",
								width : "300"
							}, {
								name : "apply_paper_number",
								index : "apply_paper_number",
								width : "100"
							}, {
								name : "apply_electron_number",
								index : "apply_electron_number",
								width : "100"
							}, {
								name : "finally_paper_number",
								index : "finally_paper_number",
								width : "100",
								editable : true
							}, {
								name : "finally_electron_number",
								index : "finally_electron_number",
								width : "100",
								editable : true
							}, {
								name : "bujian_reason",
								index : "bujian_reason",
								width : "100",
								editable : true,
								edittype : "select",
								formatter : "select",
								editoptions : bujianReasonOptions
							} ],
							sortable : false,
							multiselect : true,
							cellEdit : true,// 表示表格可编辑
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							height : 320,
							scrollOffset : 0,
							afterEditCell : function(rowid, cellname, value, iRow, iCol) {
								// Modify event handler to save on blur.
								$("#" + iRow + "_" + cellname, "#bj_file_list").one('blur', function() {
									$('#bj_file_list').jqGrid("saveCell", iRow, iCol);
								});
							}
						});

				// 从表存在的所有数据
				var allRowData = $(dytableSelector).dytable("getAllRowData", {
					tableId : form_bjsqd_sub
				});
				for ( var index = 0; index < allRowData.length; index++) {
					var rowData = allRowData[index];
					var newData = {};
					newData.uuid = new UUID().id.toLowerCase();
					newData["apply_material_name"] = rowData["apply_material_name"];
					newData["apply_material_type"] = rowData["apply_material_type"];
					newData["apply_paper_number"] = rowData["apply_paper_number"];
					newData["finally_paper_number"] = rowData["finally_paper_number"];
					newData["apply_electron_number"] = rowData["apply_electron_number"];
					newData["finally_electron_number"] = rowData["finally_electron_number"];
					newData["apply_material_file"] = "";
					newData["apply_material_shixiangid"] = rowData["apply_material_shixiangid"];
					newData["apply_material_shixiangname"] = rowData["apply_material_shixiangname"];
					$bjFileList.jqGrid("addRowData", newData.uuid, newData);
				}

				$("#bj_opinion", $(".bj_form")).keyup(function(e) {
					var bj_opinionValue = $("#bj_required").val();
					var bj_opinionLabel = $("#bj_required").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var rowids = $("#bj_file_list").jqGrid("getGridParam", "selarrrow");
					var bjOpinionText = $("#bj_opinion", $(".bj_form")).val();
					if (rowids.length == 0) {
						oAlert("请选择需要补件的材料!");
					} else if (isBlank(bjOpinionText)) {
						oAlert("请填写补件意见!");
					} else {
						// 设置已经填好补件材料
						// 获取补件数据，填充到补件从表
						for ( var i = 0; i < rowids.length; i++) {
							var rowid = rowids[i];
							var rowData = $("#bj_file_list").jqGrid("getRowData", rowid);
							rowData.id = rowid;
							// 补件状态--未补件
							rowData["bujian_status"] = "1";
							$(dytableSelector).dytable("addRowData", {
								tableId : form_bjsqd_bj,
								data : rowData
							});
						}
						// 设置已经填好补件材料
						XZSP.bj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_bj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_bj").oDialog(options);
	}
	/** ********************************* 补件结束 ********************************* */

	/** ******************************** 补件确认开始 ******************************** */
	// 准备与弹出补件确认窗口
	function prepareAndShowBJQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_bjqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_bjqr' />");
		}

		$("#dlg_xzsp_bjqr").html("");
		$("#dlg_xzsp_bjqr").html(data);

		showBJQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("bjqr", "XZSP_" + taskId);
	}

	// 显示补件确认对话框
	function showBJQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "补件确认",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				var $bjFileList = $("#bjqr_file_list").jqGrid(
						{
							datatype : "local",
							colNames : [ "uuid", "材料类型", "递交附件", "所属事项ID", "所属事项名称", "附件名称", "纸质缺件", "电子缺件",
									"应补纸质", "应补电子", "补件原因" ],
							colModel : [ {
								name : "uuid",
								index : "uuid",
								width : "180",
								hidden : true,
								key : true
							}, {
								name : "apply_material_type",
								index : "apply_material_type",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_file",
								index : "apply_material_file",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangid",
								index : "apply_material_shixiangid",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangname",
								index : "apply_material_shixiangname",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_name",
								index : "apply_material_name",
								width : "300"
							}, {
								name : "apply_paper_number",
								index : "apply_paper_number",
								width : "100"
							}, {
								name : "apply_electron_number",
								index : "apply_electron_number",
								width : "100"
							}, {
								name : "finally_paper_number",
								index : "finally_paper_number",
								width : "100",
								editable : true
							}, {
								name : "finally_electron_number",
								index : "finally_electron_number",
								width : "100",
								editable : true
							}, {
								name : "bujian_reason",
								index : "bujian_reason",
								width : "100"
							} ],
							sortable : false,
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							height : 300,
							scrollOffset : 0
						});

				// 补件从表存在的所有数据
				var allRowData = $(dytableSelector).dytable("getAllRowData", {
					tableId : form_bjsqd_bj
				});
				for ( var index = 0; index < allRowData.length; index++) {
					var rowData = allRowData[index];
					if (rowData["bujian_status"] != "1") {
						continue;
					}
					var newData = {};
					newData.uuid = new UUID().id.toLowerCase();
					newData["apply_material_name"] = rowData["apply_material_name"];
					newData["apply_material_type"] = rowData["apply_material_type"];
					newData["apply_paper_number"] = rowData["apply_paper_number"];
					newData["finally_paper_number"] = rowData["finally_paper_number"];
					newData["apply_electron_number"] = rowData["apply_electron_number"];
					newData["finally_electron_number"] = rowData["finally_electron_number"];
					newData["apply_material_file"] = "";
					newData["apply_material_shixiangid"] = rowData["apply_material_shixiangid"];
					newData["apply_material_shixiangname"] = rowData["apply_material_shixiangname"];
					newData["bujian_reason"] = rowData["bujian_reason"];
					$bjFileList.jqGrid("addRowData", newData.uuid, newData);
				}

				$("#bjqr_opinion", $(".bjqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#bjqr_opinion_value").val();
					var bj_opinionLabel = $("#bjqr_opinion_value").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#bjqr_opinion", $(".bjqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写补件确认意见!");
					} else {
						// 更新补件列表数据的状态
						updateBuJianListStatus("1", "2");

						WorkFlow.setWorkData("opinionText", bjOpinionText);
						// 收集短信信息
						collectSms("bjqr");
						// 设置已经填好补件材料
						XZSP.bjqr = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_bjqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_bjqr").oDialog(options);
	}

	// 更新补件列表数据的状态
	function updateBuJianListStatus(formStatus, toStatus) {
		// 补件从表存在的所有数据
		var allRowData = $(dytableSelector).dytable("getAllRowData", {
			tableId : form_bjsqd_bj
		});
		// 更新补件状态
		for ( var index = 0; index < allRowData.length; index++) {
			var rowData = allRowData[index];
			if (rowData["bujian_status"] == formStatus) {
				// 补件状态--已补件
				rowData["bujian_status"] = toStatus;
				$(dytableSelector).dytable("updateRowData", {
					tableId : form_bjsqd_bj,
					rowid : rowData["id"],
					data : rowData,
					type : "2"
				});
			}
		}
	}
	/** ******************************** 补件确认结束 ******************************** */
	function myelem(value, options) {
		var $el = $("<div><div id='ffsss'></div></div>");
		$("#ffsss", $el).fileUpload({
			url : ctx + "/repository/file/uploadfile"
		});
		return $el[0];
	}

	function myvalue(elem, operation, value) {
		return $(elem).html();
	}
	/** ****************************** 补件登记重新提交开始 ****************************** */
	// 准备与弹出补件确认窗口
	function prepareAndShowBJDJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_bjdj").length == 0) {
			$("body").append("<div id='dlg_xzsp_bjdj' />");
		}

		$("#dlg_xzsp_bjdj").html("");
		$("#dlg_xzsp_bjdj").html(data);

		showBJDJDialog({}, bean);

		// 绑定补件弹出框事件
	}
	// 显示补件登记
	function showBJDJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "重新提交",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#bj_remark").val(data[0].opinionText);
						}
					}
				});
				var bjdj_list_selector = "#bjdj_file_list";
				var $bjFileList = $(bjdj_list_selector).jqGrid(
						{
							datatype : "local",
							colNames : [ "uuid", "材料类型", "所属事项ID", "所属事项名称", "附件名称", "应交纸质", "实交纸质",
									"<input type='checkbox'>", "应交电子", "递交附件", "补件原因" ],
							colModel : [ {
								name : "uuid",
								index : "uuid",
								width : "180",
								hidden : true,
								key : true
							}, {
								name : "apply_material_type",
								index : "apply_material_type",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangid",
								index : "apply_material_shixiangid",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangname",
								index : "apply_material_shixiangname",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_name",
								index : "apply_material_name",
								width : "300"
							}, {
								name : "apply_paper_number",
								index : "apply_paper_number",
								width : "100"
							}, {
								name : "finally_paper_number",
								index : "finally_paper_number",
								width : "100",
								editable : true
							}, {
								name : "check_paper_number",
								index : "check_paper_number",
								width : "100",
								align : "center",
								editable : true,
								edittype : "checkbox",
								editoptions : {
									value : "true:false"
								},
								formatter : "checkbox"
							}, {
								name : "apply_electron_number",
								index : "apply_electron_number",
								width : "100"
							}, {
								name : "apply_material_file",
								index : "apply_material_file",
								width : "100",
								editable : true
							}, {
								name : "bujian_reason",
								index : "bujian_reason",
								width : "100",
								edittype : "select",
								formatter : "select",
								editoptions : bujianReasonOptions
							} ],
							sortable : false,
							cellEdit : true,// 表示表格可编辑
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							height : 300,
							scrollOffset : 0,
							afterEditCell : function(rowid, cellname, value, iRow, iCol) {
								var cell_selector = "#" + iRow + "_" + cellname;
								// Modify event handler to save on blur.
								if (cellname == "check_paper_number") {
									$(cell_selector, bjdj_list_selector).bind(
											'click',
											function() {
												if ($(this).attr("checked") == "checked") {
													$(bjdj_list_selector).jqGrid("setCell", rowid,
															"finally_paper_number", 1);
												} else {
													$(bjdj_list_selector).jqGrid("setCell", rowid,
															"finally_paper_number", " ");
												}
											});
								}
								$(cell_selector, bjdj_list_selector).one('blur', function() {
									$(bjdj_list_selector).jqGrid("saveCell", iRow, iCol);
								});
							},
							afterSaveCell : function(rowid, cellname, value, iRow, iCol) {
								if (cellname == "finally_paper_number") {
									var n = Number(value);
									if (n > 0) {
										$(bjdj_list_selector).jqGrid("setCell", rowid, "check_paper_number",
												true);
									} else {
										$(bjdj_list_selector).jqGrid("setCell", rowid,
												"finally_paper_number", " ");
										$(bjdj_list_selector).jqGrid("setCell", rowid, "check_paper_number",
												false);
									}
								}
							}
						});

				// 补件从表存在的所有数据
				var allRowData = $(dytableSelector).dytable("getAllRowData", {
					tableId : form_bjsqd_bj
				});
				for ( var index = 0; index < allRowData.length; index++) {
					var rowData = allRowData[index];
					if (rowData["bujian_status"] != "2") {
						continue;
					}
					var newData = {};
					newData.uuid = new UUID().id.toLowerCase();
					newData["apply_material_name"] = rowData["apply_material_name"];
					newData["apply_material_type"] = rowData["apply_material_type"];
					newData["apply_paper_number"] = rowData["apply_paper_number"];
					newData["finally_paper_number"] = rowData["finally_paper_number"];
					newData["apply_electron_number"] = rowData["apply_electron_number"];
					newData["finally_electron_number"] = rowData["finally_electron_number"];
					newData["apply_material_file"] = "";
					newData["apply_material_shixiangid"] = rowData["apply_material_shixiangid"];
					newData["apply_material_shixiangname"] = rowData["apply_material_shixiangname"];
					newData["bujian_reason"] = rowData["bujian_reason"];
					$bjFileList.jqGrid("addRowData", newData.uuid, newData);

					$.fileuploaders.addFileSubField({
						tableId : "bjdj_file_list",
						colEnName : "apply_material_file",
						rowid : newData.uuid
					});
				}
			},
			buttons : {
				"确定" : function(e) {
					// 从表存在的所有数据
					var allRowData = $(dytableSelector).dytable("getAllRowData", {
						tableId : form_bjsqd_sub
					});
					// 补件登记从表存在的所有数据
					var bjAllRowData = $("#bjdj_file_list").jqGrid("getRowData");
					for ( var j = 0; j < bjAllRowData.length; j++) {
						var appMaterial = bjAllRowData[j];
						for ( var i = 0; i < allRowData.length; i++) {
							var rowData = allRowData[i];
							if (rowData["apply_material_name"] == appMaterial["apply_material_name"]
									&& rowData["apply_material_shixiangid"] == appMaterial["apply_material_shixiangid"]) {
								appMaterial.apply_material_file = $("input[id^=attachapply_material_file_]",
										$(appMaterial.apply_material_file)).val();
								console.log(JSON.stringify(appMaterial));
								$(dytableSelector).dytable("updateRowData", {
									tableId : form_bjsqd_sub,
									rowid : rowData["id"],
									data : appMaterial,
									type : "2"
								});
							}
						}
					}

					// 更新补件列表数据的状态
					updateBuJianListStatus("2", "3");

					// 设置已经填好补件材料
					XZSP.bjdj = "done";
					$("#" + bean.submitButtonId).trigger("click");
					$(this).oDialog("close");
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_bjdj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_bjdj").oDialog(options);
	}
	/** ****************************** 补件登记重新提交结束 ****************************** */

	/** ********************************* 退件开始 ********************************* */
	// 准备与弹出退件窗口
	function prepareAndShowTJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_tj").length == 0) {
			$("body").append("<div id='dlg_xzsp_tj' />");
		}

		$("#dlg_xzsp_tj").html("");
		$("#dlg_xzsp_tj").html(data);

		showTJDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 弹出退件窗口
	function showTJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "退件",
			autoOpen : true,
			width : 650,
			height : 350,
			resizable : false,
			modal : true,
			open : function() {
				$("#tj_opinion", $(".tj_form")).keyup(function(e) {
					var bj_opinionValue = $("#tj_manner").val();
					var bj_opinionLabel = $("#tj_manner").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#tj_opinion", $(".tj_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写退件原因!");
					} else {
						// 设置已经填好补件材料
						XZSP.tj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_tj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_tj").oDialog(options);
	}
	/** ********************************* 退件结束 ********************************* */

	/** ******************************** 退件确认开始 ******************************** */
	// 退件确认处理
	function prepareAndShowTJQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_tjqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_tjqr' />");
		}

		$("#dlg_xzsp_tjqr").html("");
		$("#dlg_xzsp_tjqr").html(data);

		showTJQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("tjqr", "XZSP_" + taskId);
	}

	// 弹出退件确认窗口
	function showTJQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "退件确认",
			autoOpen : true,
			width : 650,
			height : 500,
			resizable : false,
			modal : true,
			open : function() {
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#tj_manner").val(data[0].opinionValue);
							$("#tj_opinion").val(data[0].opinionText);
						}
					}
				});
				$("#tjqr_opinion", $(".tjqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#tjqr_shjg").val();
					var bj_opinionLabel = $("#tjqr_shjg").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#tjqr_opinion", $(".tjqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写办理意见!");
					} else {
						// 收集短信信息
						collectSms("tjqr");
						// 设置已经填好补件材料
						XZSP.tjqr = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_tjqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_tjqr").oDialog(options);
	}
	/** ******************************** 退件确认结束 ******************************** */

	/** ******************************** 过程补件开始 ******************************** */
	// 准备与弹出过程补件窗口
	function prepareAndShowGCBJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_gcbj").length == 0) {
			$("body").append("<div id='dlg_xzsp_gcbj' />");
		}

		$("#dlg_xzsp_gcbj").html("");
		$("#dlg_xzsp_gcbj").html(data);

		showGCBJDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 显示过程补件对话框
	function showGCBJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "过程补件",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				var $bjFileList = $("#gcbj_file_list").jqGrid(
						{
							datatype : "local",
							colNames : [ "uuid", "材料类型", "递交附件", "所属事项ID", "所属事项名称", "附件名称", "纸质缺件", "电子缺件",
									"应补纸质", "应补电子", "补件原因" ],
							colModel : [ {
								name : "uuid",
								index : "uuid",
								width : "180",
								hidden : true,
								key : true
							}, {
								name : "apply_material_type",
								index : "apply_material_type",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_file",
								index : "apply_material_file",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangid",
								index : "apply_material_shixiangid",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_shixiangname",
								index : "apply_material_shixiangname",
								width : "100",
								hidden : true
							}, {
								name : "apply_material_name",
								index : "apply_material_name",
								width : "300"
							}, {
								name : "apply_paper_number",
								index : "apply_paper_number",
								width : "100"
							}, {
								name : "apply_electron_number",
								index : "apply_electron_number",
								width : "100"
							}, {
								name : "finally_paper_number",
								index : "finally_paper_number",
								width : "100",
								editable : true
							}, {
								name : "finally_electron_number",
								index : "finally_electron_number",
								width : "100",
								editable : true
							}, {
								name : "bujian_reason",
								index : "bujian_reason",
								width : "100",
								editable : true,
								edittype : "select",
								formatter : "select",
								editoptions : bujianReasonOptions
							} ],
							sortable : false,
							multiselect : true,
							cellEdit : true,// 表示表格可编辑
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							height : 320,
							scrollOffset : 0,
							afterEditCell : function(rowid, cellname, value, iRow, iCol) {
								// Modify event handler to save on blur.
								$("#" + iRow + "_" + cellname, "#gcbj_file_list").one('blur', function() {
									$('#gcbj_file_list').jqGrid("saveCell", iRow, iCol);
								});
							}
						});

				// 从表存在的所有数据
				var allRowData = $(dytableSelector).dytable("getAllRowData", {
					tableId : form_bjsqd_sub
				});
				for ( var index = 0; index < allRowData.length; index++) {
					var rowData = allRowData[index];
					var newData = {};
					newData.uuid = new UUID().id.toLowerCase();
					newData["apply_material_name"] = rowData["apply_material_name"];
					newData["apply_material_type"] = rowData["apply_material_type"];
					newData["apply_paper_number"] = rowData["apply_paper_number"];
					newData["finally_paper_number"] = rowData["finally_paper_number"];
					newData["apply_electron_number"] = rowData["apply_electron_number"];
					newData["finally_electron_number"] = rowData["finally_electron_number"];
					newData["apply_material_file"] = "";
					newData["apply_material_shixiangid"] = rowData["apply_material_shixiangid"];
					newData["apply_material_shixiangname"] = rowData["apply_material_shixiangname"];
					newData["bujian_reason"] = rowData["bujian_reason"];
					$bjFileList.jqGrid("addRowData", newData.uuid, newData);
				}

				$("#gcbj_opinion", $(".gcbj_form")).keyup(function(e) {
					var bj_opinionValue = $("#gcbj_required").val();
					var bj_opinionLabel = $("#gcbj_required").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var rowids = $("#gcbj_file_list").jqGrid("getGridParam", "selarrrow");
					var bjOpinionText = $("#gcbj_opinion", $(".gcbj_form")).val();
					if (rowids.length == 0) {
						oAlert("请选择需要过程补件的材料!");
					} else if (isBlank(bjOpinionText)) {
						oAlert("请填写过程补件意见!");
					} else {
						// 设置已经填好补件材料
						WorkFlow.setWorkData("opinionText", bjOpinionText);
						// 获取补件数据，填充到补件从表
						for ( var i = 0; i < rowids.length; i++) {
							var rowid = rowids[i];
							var rowData = $("#gcbj_file_list").jqGrid("getRowData", rowid);
							rowData.id = rowid;
							// 补件状态--未补件
							rowData["bujian_status"] = "1";
							$(dytableSelector).dytable("addRowData", {
								tableId : form_bjsqd_bj,
								data : rowData
							});
						}
						XZSP.gcbj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_gcbj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_gcbj").oDialog(options);
	}
	/** ******************************** 过程补件结束 ******************************** */

	/** ******************************* 过程补件确认开始 ******************************* */
	// 准备与弹出过程补件确认窗口
	function prepareAndShowGCBJQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_gcbjqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_gcbjqr' />");
		}

		$("#dlg_xzsp_gcbjqr").html("");
		$("#dlg_xzsp_gcbjqr").html(data);

		showGCBJQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("gcbjqr", "XZSP_" + taskId);
	}
	// 显示补件确认对话框
	function showGCBJQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "过程补件确认",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				var $bjFileList = $("#gcbjqr_file_list").jqGrid({
					datatype : "local",
					colNames : [ "uuid", "附件名称", "纸质缺件", "电子缺件", "应补纸质", "应补电子", "补件原因" ],
					colModel : [ {
						name : "uuid",
						index : "uuid",
						width : "180",
						hidden : true,
						key : true
					}, {
						name : "apply_material_name",
						index : "apply_material_name",
						width : "300"
					}, {
						name : "apply_paper_number",
						index : "apply_paper_number",
						width : "100"
					}, {
						name : "apply_electron_number",
						index : "apply_electron_number",
						width : "100"
					}, {
						name : "finally_paper_number",
						index : "finally_paper_number",
						width : "100"
					}, {
						name : "finally_electron_number",
						index : "finally_electron_number",
						width : "100"
					}, {
						name : "bujian_reason",
						index : "bujian_reason",
						width : "100",
						editable : true,
						edittype : "select",
						formatter : "select",
						editoptions : bujianReasonOptions
					} ],
					sortable : false,
					cellsubmit : "clientArray", // 表示在本地进行修改
					autowidth : true,
					height : 200,
					scrollOffset : 0
				});
				// 补件从表存在的所有数据
				var allRowData = $(dytableSelector).dytable("getAllRowData", {
					tableId : form_bjsqd_bj
				});
				for ( var index = 0; index < allRowData.length; index++) {
					var rowData = allRowData[index];
					if (rowData["bujian_status"] != "1") {
						continue;
					}
					var newData = {};
					newData.uuid = new UUID().id.toLowerCase();
					newData["apply_material_name"] = rowData["apply_material_name"];
					newData["apply_material_type"] = rowData["apply_material_type"];
					newData["apply_paper_number"] = rowData["apply_paper_number"];
					newData["apply_electron_number"] = rowData["apply_electron_number"];
					newData["finally_paper_number"] = rowData["finally_paper_number"];
					newData["finally_electron_number"] = rowData["finally_electron_number"];
					newData["apply_material_shixiangid"] = rowData["apply_material_shixiangid"];
					newData["apply_material_shixiangname"] = rowData["apply_material_shixiangname"];
					newData["bujian_reason"] = rowData["bujian_reason"];
					$bjFileList.jqGrid("addRowData", newData.uuid, newData);
				}

				// 过程补件意见相关
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#gcbj_required").val(data[0].opinionValue);
							$("#gcbj_opinion").val(data[0].opinionText);
						}
					}
				});
				$("#gcbjqr_opinion", $(".gcbjqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#gcbjqr_opinion_value").val();
					var bj_opinionLabel = $("#gcbjqr_opinion_value").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#gcbjqr_opinion", $(".gcbjqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写过程补件确认办理意见!");
					} else {
						// 更新补件列表数据的状态
						updateBuJianListStatus("1", "2");

						// 收集短信信息
						collectSms("gcbjqr");
						// 设置已经填好补件材料
						XZSP.gcbjqr = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_gcbjqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_gcbjqr").oDialog(options);
	}
	/** ******************************* 过程补件确认结束 ******************************* */

	/** ******************************** 过程退件开始 ******************************** */
	// 准备与弹出过程退件窗口
	function prepareAndShowGCTJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_gctj").length == 0) {
			$("body").append("<div id='dlg_xzsp_gctj' />");
		}

		$("#dlg_xzsp_gctj").html("");
		$("#dlg_xzsp_gctj").html(data);

		showGCTJDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 弹出过程退件窗口
	function showGCTJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "过程退件",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				// $("#gctj_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile"
				// });
				var fileupload = new WellFileUpload("gctj_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(false, $("#gctj_file_upload"), false, false,
						bean.dataUuid, "gctj_files");

				$("#gctj_reason", $(".gctj_form")).keyup(function(e) {
					var bj_opinionValue = $("#gctj_manner").val();
					var bj_opinionLabel = $("#gctj_manner").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var files = WellFileUpload.files["gctj_file_uploads"];
					var fileIds = [];
					$.each(files, function() {
						fileIds.push(this.fileID);
					});
					// var getFolderId =
					// $("#gctj_file_upload").fileUpload("getFolderId");
					var bjOpinionText = $("#gctj_reason", $(".gctj_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写过程退件原因!");
					} else {
						// 过程退件文件
						setFieldValue("gctj_files", files);
						WorkFlow.putExtraParam("gctj_files", fileIds.join(";"));
						// 设置已经填好补件材料
						XZSP.gctj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_gctj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_gctj").oDialog(options);
	}
	/** ******************************** 过程退件结束 ******************************** */

	/** ******************************* 过程退件确认开始 ******************************* */
	// 准备与弹出过程退件确认窗口
	function prepareAndShowGCTJQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_gctjqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_gctjqr' />");
		}

		$("#dlg_xzsp_gctjqr").html("");
		$("#dlg_xzsp_gctjqr").html(data);

		showGCTJQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("gctjqr", "XZSP_" + taskId);
	}

	// 弹出过程退件确认窗口
	function showGCTJQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "过程退件确认",
			autoOpen : true,
			width : 650,
			height : 550,
			resizable : false,
			modal : true,
			open : function() {
				// 过程退件文件
				// var folderId = $("input[id=attachgctj_files]").val();
				// $("#gctj_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile",
				// folderId : folderId,
				// allowUpload : false,
				// allowDelete : false
				// });
				var fileupload = new WellFileUpload("gctj_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(true, $("#gctj_file_upload"), false, false,
						bean.dataUuid, "gctj_files");

				// 过程退件意见相关
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#gctj_manner").val(data[0].opinionValue);
							$("#gctj_opinion").val(data[0].opinionText);
						}
					}
				});
				$("#gctjqr_opinion", $(".gctjqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#gctjqr_shjg").val();
					var bj_opinionLabel = $("#gctjqr_shjg").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						lable : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#gctjqr_opinion", $(".gctjqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写过程退件审核意见!");
					} else {
						// 收集短信信息
						collectSms("gctjqr");
						// 设置已经填好补件材料
						XZSP.gctjqr = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_gctjqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_gctjqr").oDialog(options);
	}
	/** ******************************* 过程退件确认结束 ******************************* */

	/** ********************************* 出文开始 ********************************* */
	// 准备与弹出出文窗口
	function prepareAndShowCWDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_cw").length == 0) {
			$("body").append("<div id='dlg_xzsp_cw' />");
		}

		$("#dlg_xzsp_cw").html("");
		$("#dlg_xzsp_cw").html(data);

		showCWDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("cw", "XZSP_" + taskId);
	}

	// 弹出退件窗口
	function showCWDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "出文",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				// $("#cw_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile"
				// });
				var fileupload = new WellFileUpload("cw_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(false, $("#cw_file_upload"), false, false,
						bean.dataUuid, "cw_files");

				// 受理时间
				$("#cw_slsj").text(bean.taskStartTime);
				$("#cw_opinion", $(".cw_form")).keyup(function(e) {
					var bj_opinionValue = $("#cw_is_qualified").val();
					var bj_opinionLabel = $("#cw_is_qualified").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var files = WellFileUpload.files["cw_file_uploads"];
					var fileIds = [];
					$.each(files, function() {
						fileIds.push(this.fileID);
					});
					// var getFolderId =
					// $("#cw_file_upload").fileUpload("getFolderId");
					var bjOpinionValue = $("#cw_is_qualified").val();
					var bjOpinionText = $("#cw_opinion", $(".cw_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写出文办理意见!");
					} else if (isBlank(fileIds.join(";"))) {
						oAlert("请上传出文文件!");
					} else {
						// 出文文件
						setFieldValue("cw_files", files);
						// 标识为出文操作
						WorkFlow.putExtraParam("isCw", "true");
						// 批文编号
						WorkFlow.putExtraParam("cw_file_code", $("#cw_file_code").val());
						// 下次更新日期
						WorkFlow.putExtraParam("cw_file_next_update_time", $("#cw_file_next_update_time")
								.val());
						// 批文文件
						WorkFlow.putExtraParam("cw_files", fileIds.join(";"));
						// 是否合格(预留数据字段1)、出文时间在流向事件处理
						WorkFlow.setWorkData("reservedNumber1", bjOpinionValue);
						// 收集短信信息
						collectSms("cw");
						// 设置已经填好补件材料
						XZSP.cw = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_cw").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_cw").oDialog(options);
	}
	/** ********************************* 出文结束 ********************************* */

	/** ******************************** 出文确认开始 ******************************** */
	// 准备与弹出出文确认窗口
	function prepareAndShowCWQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_cwqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_cwqr' />");
		}

		$("#dlg_xzsp_cwqr").html("");
		$("#dlg_xzsp_cwqr").html(data);

		showCWQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("cwqr", "XZSP_" + taskId);
	}

	// 弹出退件窗口
	function showCWQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "出文确认",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				// var folderId = $("input[id=attachcw_files]").val();
				// $("#cw_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile",
				// folderId : folderId,
				// allowUpload : false,
				// allowDelete : false
				// });
				var fileupload = new WellFileUpload("cw_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(true, $("#cw_file_upload"), false, false,
						bean.dataUuid, "cw_files");

				// 出文意见相关
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#cw_is_qualified").val(data[0].opinionValue);
							$("#cw_opinion").val(data[0].opinionText);
						}
					}
				});
				$("#cwqr_opinion", $(".cwqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#cwqr_opinion_value").val();
					var bj_opinionLabel = $("#cwqr_opinion_value").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#cwqr_opinion", $(".cwqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写出文确认办理意见!");
					} else {
						var cwqrOpinionValue = $("#cwqr_opinion_value").val();
						// 审核通过，提交流程
						if (cwqrOpinionValue == 1) {
							// 标识为出文操作
							WorkFlow.putExtraParam("isCwqr", "true");

							// 收集短信信息
							collectSms("cwqr");
							// 设置已经填好补件材料
							XZSP.cwqr = "done";
							$("#" + bean.submitButtonId).trigger("click");
							$(this).oDialog("close");
						} else {
							// 审核不通过，退回前一环节
							// 退回到前环节
							WorkFlow.setWorkData("rollbackToPreTask", true);
							// 不退回到前环节已办理的人，重新解析前环节办理人
							WorkFlow.setWorkData("rollbackToPreTaskAssignee", false);
							WorkFlow.rollback.call();
						}
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_cwqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_cwqr").oDialog(options);
	}
	/** ******************************** 出文确认结束 ******************************** */

	/** ********************************* 转报开始 ********************************* */
	// 准备与弹出转报窗口
	function prepareAndShowZBDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_zb").length == 0) {
			$("body").append("<div id='dlg_xzsp_zb' />");
		}

		$("#dlg_xzsp_zb").html("");
		$("#dlg_xzsp_zb").html(data);

		showZBDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 弹出转报窗口
	function showZBDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "转报",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				// $("#zb_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile"
				// });
				var fileupload = new WellFileUpload("zb_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(false, $("#zb_file_upload"), false, false,
						bean.dataUuid, "zb_files");

				$("#zb_opinion", $(".zb_form")).keyup(function(e) {
					var bj_opinion = $(this).val();
					WorkFlow.signOpinion({
						text : bj_opinion
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var files = WellFileUpload.files["cw_file_uploads"];
					var fileIds = [];
					$.each(files, function() {
						fileIds.push(this.fileID);
					});
					// var getFolderId =
					// $("#zb_file_upload").fileUpload("getFolderId");
					var bjOpinionText = $("#zb_opinion", $(".zb_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写转报意见!");
					} else {
						// 转报文件
						setFieldValue("zb_files", files);
						WorkFlow.putExtraParam("zb_files", fileIds.join(";"));
						// 设置已经填好补件材料
						XZSP.zb = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_zb").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_zb").oDialog(options);
	}
	/** ********************************* 转报结束 ********************************* */

	/** ******************************** 转报确认开始 ******************************** */
	// 准备与弹出转报确认窗口
	function prepareAndShowZBQRDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_zbqr").length == 0) {
			$("body").append("<div id='dlg_xzsp_zbqr' />");
		}

		$("#dlg_xzsp_zbqr").html("");
		$("#dlg_xzsp_zbqr").html(data);

		showZBQRDialog({}, bean);

		// 绑定补件弹出框事件
		// 是否发送短信处理
		bindSendSms("zbqr", "XZSP_" + taskId);
	}

	// 弹出转报窗口
	function showZBQRDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "转办确认",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				// 转报文件
				// var folderId = $("input[id=attachzb_files]").val();
				// $("#zb_file_upload").fileUpload({
				// url : ctx + "/repository/file/uploadfile",
				// folderId : folderId,
				// allowUpload : false,
				// allowDelete : false
				// });
				var fileupload = new WellFileUpload("zb_file_uploads");
				// 初始化上传控件
				fileupload.initWithLoadFilesFromFileSystem(true, $("#zb_file_upload"), false, false,
						bean.dataUuid, "zb_files");

				// 转报意见相关
				JDS.call({
					service : "workService.getPreTaskOperations",
					data : [ bean.taskInstUuid ],
					success : function(result) {
						var data = result.data;
						if (data.length != 0) {
							$("#zb_opinion").val(data[0].opinionText);
						}
					}
				});
				$("#zbqr_opinion", $(".zbqr_form")).keyup(function(e) {
					var bj_opinionValue = $("#zbqr_opinion_value").val();
					var bj_opinionLabel = $("#zbqr_opinion_value").find("option:selected").text();
					var bj_opinionText = $(this).val();
					WorkFlow.signOpinion({
						value : bj_opinionValue,
						label : bj_opinionLabel,
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#zbqr_opinion", $(".zbqr_form")).val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写转报确认办理意见!");
					} else {
						// 收集短信信息
						collectSms("zbqr");
						// 设置已经填好补件材料
						XZSP.zbqr = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_zbqr").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_zbqr").oDialog(options);
	}
	/** ******************************** 转报确认结束 ******************************** */

	/** ********************************* 取件开始 ********************************* */
	// 准备与弹出取件确认窗口
	function prepareAndShowQJDialog(data, bean) {
		// 放置
		if ($("#dlg_xzsp_qj").length == 0) {
			$("body").append("<div id='dlg_xzsp_qj' />");
		}

		$("#dlg_xzsp_qj").html("");
		$("#dlg_xzsp_qj").html(data);

		showQJDialog({}, bean);

		// 绑定补件弹出框事件
	}

	// 弹出取件窗口
	function showQJDialog(option, bean) {
		// 初始化下一流程选择框
		var options = {
			title : "取件",
			autoOpen : true,
			width : 650,
			height : 450,
			resizable : false,
			modal : true,
			open : function() {
				$("#qj_remark", $(".qj_form")).keyup(function(e) {
					var bj_opinionText = $(this).val();
					$("#mini_wf_opinion").workflowMiniOpinion("signOpinion", {
						text : bj_opinionText
					});
				});
			},
			buttons : {
				"确定" : function(e) {
					var bjOpinionText = $("#qj_remark", $(".qj_form")).val();
					var qj_time = $("#qj_time").val();
					if (isBlank(bjOpinionText)) {
						oAlert("请填写取件备注!");
					} else if (isBlank(qj_time)) {
						oAlert("请选择取件时间!");
					} else {
						// 设置已经填好补件材料
						WorkFlow.setWorkData("opinionText", bjOpinionText);
						// 备注
						setFieldValue("bjsqd_remarks", bjOpinionText);
						// 取件时间
						setFieldValue("pick_up_date", qj_time);
						XZSP.qj = "done";
						$("#" + bean.submitButtonId).trigger("click");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_qj").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_qj").oDialog(options);
	}
	/** ********************************* 取件结束 ********************************* */

	/** ******************************** 强制退件开始 ******************************** */
	if ($("input[id=ep_qztj]").val() === "true") {
		// 5、10个工作内未补齐所缺材料，则允许进行强制退件
		var workData = WorkFlow.getWorkData();
		JDS.call({
			service : "XZSPBizService.isAllowQztj",
			data : [ workData.taskInstUuid, workData.flowInstUuid ],
			success : function(result) {
				if (result.data === true) {
					buildQztjBtn();
				}
			}
		});
		// 生成强制退件按钮
		function buildQztjBtn() {
			var qztjTaskId = null;
			// 补件登记环节
			if (taskId == TASK_ID_BJDJ) {
				qztjTaskId = TASK_ID_TJ;
			}
			// 过程补件登记环节
			if (taskId == TASK_ID_GCBJDJ) {
				qztjTaskId = TASK_ID_GCTJ;
			}
			var qztjBtn = "<button id='B013001074' name='B004002' copyuserids='[]' userids='[]' taskid='"
					+ qztjTaskId + "'>强制退件</button>";
			WorkFlow.addButton(qztjBtn);
			WorkFlow.bind({
				id : btn_qztj,
				functionName : "submit"
			});
		}
	}
	/** ******************************** 强制退件结束 ******************************** */

	/** ******************************** 发送短信开始 ******************************** */
	WorkFlow.bind({
		id : btn_send_msg,
		name : "发送短信",
		onClick : function() {
			var templateId = "XZSP_" + taskId;
			var taskInstUuid = WorkFlow.getTaskInstUuid();
			var flowInstUuid = WorkFlow.getFlowInstUuid();
			$.get(ctx + "/xzsp/send/msg", {
				taskId : taskId,
				taskInstUuid : taskInstUuid,
				flowInstUuid : flowInstUuid,
				templateId : templateId
			}, function(data) {
				prepareAndShowSendMsgDialog(data);
			});
		}
	});
	// 准备与弹出发送短信窗口
	function prepareAndShowSendMsgDialog(data) {
		// 放置
		if ($("#dlg_xzsp_send_msg").length == 0) {
			$("body").append("<div id='dlg_xzsp_send_msg' />");
		}

		$("#dlg_xzsp_send_msg").html("");
		$("#dlg_xzsp_send_msg").html(data);

		showSendMsgDialog({});

		// 绑定发送短信弹出框事件
		// 是否发送短信处理
		bindSendSms("fsdx", "XZSP_" + taskId);
	}
	// 弹出发送短信窗口
	function showSendMsgDialog(option) {
		// 初始化下一流程选择框
		var options = {
			title : "发送短信",
			autoOpen : true,
			width : 650,
			height : 350,
			resizable : false,
			modal : true,
			open : function() {
				// 收信人
				var recipient = getFieldValue("apply_name");
				// 收信号码
				var mobile = getFieldValue("apply_contact_person");
				$("#send_msg_recipient").val(recipient);
				$("#send_msg_mobile").val(mobile);
			},
			buttons : {
				"确定" : function(e) {
					// 收集短信信息
					collectSms("fsdx");
					var extraParams = WorkFlow.getExtraParams();
					var recipient = extraParams["send_msg_recipient"];
					var mobile = extraParams["send_msg_mobile"];
					var content = extraParams["send_msg_content"];
					JDS.call({
						service : "XZSPBizService.sendMsg",
						data : [ recipient, mobile, content ],
						success : function(result) {
							oAlert("发送成功!", function() {
								$("#dlg_xzsp_send_msg").oDialog("close");
							});
						},
						error : function(result) {
							oAlert("发送失败!");
						}
					});
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_send_msg").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_send_msg").oDialog(options);
	}
	/** ******************************** 发送短信结束 ******************************** */

	/** ******************************** 修改按钮开始 ******************************** */
	// 接件打印时，直接打开；接件操作时，需打印后才显示发送和修改按钮
	// 修改按钮 显示打印按钮，隐藏发送按钮
	if (taskId == TASK_ID_CKDJ) {
		WorkFlow.bind({
			id : btn_modify,
			name : "修改",
			onClick : function() {
				WorkFlow.showButton(btn_print);
				WorkFlow.hideButton(btn_send);
			}
		});
		// 判断是否打印过
		if (WorkFlow.isExistsWorkData()) {
			WorkFlow.hideButton(btn_print);
		}

		if (!WorkFlow.isExistsWorkData()) {
			WorkFlow.hideButton(btn_send);
			WorkFlow.hideButton(btn_modify);
		}
	}
	/** ******************************** 修改按钮结束 ******************************** */

	/** ******************************** 图纸审阅开始 ******************************** */
	// 隐藏工程图列表
	// $("div.title:contains('工程图列表')").hide();
	// $(dytableSelector).dytable("hideSubForm", {
	// "tableId" : form_bjsqd_gst
	// });
	// 图纸审阅提交指导意见
	$("#btn_review_sure").live("click", function() {
		var ins = $("#instruct").val();
		if (ins == '') {
			oAlert("请填写指导意见!");
			return false;
		}
		// 图纸审阅部门实体uuid
		var reviewUuid = $("#ep_reviewUuid").val();
		JDS.call({
			async : false,
			service : "banJianService.saveInstruct",
			data : [ reviewUuid, ins ],
			success : function(result) {
				oAlert("图纸审阅指导意见提交成功!");
			}
		});
	});

	// 图纸审阅状态
	var reviewStatus = $("#ep_drawReview").val();
	// 待审阅视图
	if (reviewStatus == 'todo') {
		// 添加确认按钮
		var reviewBtn = "<button id='btn_review_sure' name='btn_review_sure'>确认</button>";
		// 判断用户是否有操作指导意见的权限
		var resultData = "";
		JDS.call({
			async : false,
			service : "XZSPService.isGrant",
			data : [ "B013001082" ],
			success : function(result) {
				resultData = result.data;
			}
		});
		if (resultData) {
			WorkFlow.addButton(reviewBtn);
		}
		var reviewHtml = "<div class='instruct'>"
				+ "<table width='100%'><tbody><tr class='title'><td class='Label' colspan='6'>指导意见</td></tr>"
				+ "<tr><td><textarea rows='5' cols='6' id='instruct' style='margin: 6px 0 0 -12px; width:101%; height: 72px;'></textarea></td></tr>"
				+ "</tbody></table>" + "</div>";
		$(".post-detail").append(reviewHtml);
	}

	// 已审阅
	if (reviewStatus == 'havedo' || reviewStatus == 'todo') {
		var other = "";
		var banjianUuid = $("#ep_banjian_uuid").val();
		JDS.call({
			async : false,
			service : "banJianService.getListByBanjianUuid",
			data : [ banjianUuid ],
			success : function(result) {
				var data = result.data;
				for ( var i = 0; i < data.length; i++) {
					other = other + "<tr>" + "<td>" + data[i].deptName + "</td><td>"
							+ (data[i].instruct == null ? '' : data[i].instruct) + "</td>" + "<td>"
							+ (data[i].reviewTime == null ? '' : data[i].reviewTime) + "</td></tr>";
				}
			}
		});

		var reviewOtherHtml = "<div class='instructOther'>"
				+ "<table width='100%'><tbody><tr class='title'><td class='Label' colspan='3'>其他单位的指导意见</td></tr></tbody></table></div>";
		$(".post-detail").append(reviewOtherHtml);
		var reviewOther2 = "<div class='share_flow_content' style='width: 978.24px; margin-top: -30px;'><table class='table view_process_table'><thead><tr><th>单位名称</th><th>指导意见</th><th>填写时间</th></tr>"
				+ "</thead><tbody>" + other + "</tbody></table></div>";
		$(".div_body_content").append(reviewOther2);
	}
	/** ******************************** 图纸审阅结束 ******************************** */
});