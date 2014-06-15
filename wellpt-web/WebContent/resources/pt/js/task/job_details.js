$(function() {
	var list_selector = "#list";
	var form_selector = "#job_details_form";
	var bean = {
		"uuid" : null,
		"creator" : null,
		"createTime" : null,
		"modifier" : null,
		"modifyTime" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"jobClassName" : null,
		"type" : null,
		"expression" : null,
		"repeatInterval" : null,
		"remark" : null,
		"tenantId" : null,
		"nextFireTime" : null,
		"state" : null,
		"timingMode" : null,
		"repeatDayOfWeek" : null,
		"repeatDayOfMonth" : null,
		"repeatDayOfSeason" : null,
		"repeatDayOfYear" : null,
		"timePoint" : null,
		"repeatIntervalTime" : null,
		"repeatCount" : null,
		"startTime" : null,
		"endTime" : null
	};

	var jobStates = {
		"0" : "运行中",
		"1" : "暂停中",
		"2" : "已完成",
		"3" : "错误",
		"4" : "阻塞中",
		"-1" : "已停止"
	};

	$(list_selector).jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?serviceName=jobDetailsService',
		colNames : [ "uuid", "名称", "ID", "编号", "下次执行时间", "状态" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "name",
			index : "name",
			width : "200"
		}, {
			name : "id",
			index : "id",
			width : "150"
		}, {
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "nextFireTime",
			index : "nextFireTime",
			width : "200"
		}, {
			name : "state",
			index : "state",
			width : "200",
			formatter : function(cellvalue, options, rowObject) {
				if (jobStates[cellvalue]) {
					return jobStates[cellvalue];
				}
				return "未知状态";
			}
		}
		// , {
		// name : "type",
		// index : "type",
		// width : "300",
		// formatter : function(cellvalue, options, rowObject) {
		// if (cellvalue === "timing") {
		// return "定时任务";
		// } else if (cellvalue === "temporary") {
		// return "临时任务";
		// }
		// return cellvalue;
		// }
		// }
		],// 行选择事件
		onSelectRow : function(uuid) {
			getJobDetails(uuid);
		}
	}));

	// 根据用户ID获取用户信息
	function getJobDetails(uuid) {
		JDS.call({
			service : "jobDetailsService.getBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$(form_selector).json2form(bean, true);

				// 触发定时方式改变事件
				var timingMode = bean.timingMode;
				$("input[value=" + timingMode + "]").trigger("change");

				var rowData = $(list_selector).getRowData(bean.uuid);
				var state = rowData["state"];
				if (state === jobStates[-1]) {
					$("#btn_start").show();
					$("#btn_pause").hide();
					$("#btn_resume").hide();
					$("#btn_stop").hide();
				} else if (state === jobStates[0]) {
					$("#btn_start").hide();
					$("#btn_pause").show();
					$("#btn_resume").hide();
					$("#btn_stop").show();
				} else if (state === jobStates[1]) {
					$("#btn_start").hide();
					$("#btn_pause").hide();
					$("#btn_resume").show();
					$("#btn_stop").show();
				} else if (state === jobStates[2]) {
					$("#btn_start").hide();
					$("#btn_pause").hide();
					$("#btn_resume").hide();
					$("#btn_stop").show();
				} else {
					$("#btn_start").hide();
					$("#btn_pause").hide();
					$("#btn_resume").hide();
					$("#btn_stop").hide();
				}
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	$("#btn_upload").button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		clear();

		$("#btn_del").hide();
	});
	function clear() {
		$(form_selector).clearForm(true);
	}

	// 保存用户信息
	$("#btn_save").click(function() {
		// 收集表单数据
		$(form_selector).form2json(bean, true);
		JDS.call({
			service : "jobDetailsService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 启动任务
	$("#btn_start").click(function() {
		var rowids = $(list_selector).jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0 || rowids.length > 1) {
			alert("请选择一条记录!");
			return true;
		}
		var uuid = rowids[0];
		JDS.call({
			service : "jobDetailsService.start",
			data : [ uuid ],
			success : function(result) {
				alert("启动成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 暂停任务
	$("#btn_pause").click(function() {
		var rowids = $(list_selector).jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0 || rowids.length > 1) {
			alert("请选择一条记录!");
			return true;
		}
		var uuid = rowids[0];
		JDS.call({
			service : "jobDetailsService.pause",
			data : [ uuid ],
			success : function(result) {
				alert("暂停成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 恢复任务
	$("#btn_resume").click(function() {
		var rowids = $(list_selector).jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0 || rowids.length > 1) {
			alert("请选择一条记录!");
			return true;
		}
		var uuid = rowids[0];
		JDS.call({
			service : "jobDetailsService.resume",
			data : [ uuid ],
			success : function(result) {
				alert("恢复成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 停止任务
	$("#btn_stop").click(function() {
		var rowids = $(list_selector).jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0 || rowids.length > 1) {
			alert("请选择一条记录!");
			return true;
		}
		var uuid = rowids[0];
		JDS.call({
			service : "jobDetailsService.stop",
			data : [ uuid ],
			success : function(result) {
				alert("停止成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (StringUtils.isBlank(bean.uuid)) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除任务[" + name + "]吗?")) {
			JDS.call({
				service : "jobDetailsService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#option_form").clearForm(true);
					// 删除成功刷新列表
					$(list_selector).trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		var rowids = $(list_selector).jqGrid("getGridParam", "selarrrow");
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "jobDetailsService.removeAll",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$(list_selector).trigger("reloadGrid");
				}
			});
		}
	});

	// // 定时任务
	// $("input[name=type]").on("change", function(e) {
	// if ($(this).val() === "timing") {
	// $(".type-timing").show();
	// $(".type-temporary").hide();
	// } else {
	// $(".type-timing").hide();
	// $(".type-temporary").show();
	// }
	// });
	// $("input[id=type_timing]").trigger("change");

	// 定时方式
	$("input[name=timingMode]").on("change", function(e) {
		var v = $(this).val();
		if (v === "1") {// 每天
			$(".timingMode-day").show();
			$(".timingMode-week").hide();
			$(".timingMode-month").hide();
			$(".timingMode-season").hide();
			$(".timingMode-year").hide();
			$(".timingMode-interval").hide();
		} else if (v === "2") {// 每周
			$(".timingMode-day").show();
			$(".timingMode-week").show();
			$(".timingMode-month").hide();
			$(".timingMode-season").hide();
			$(".timingMode-year").hide();
			$(".timingMode-interval").hide();
		} else if (v === "3") {// 每月
			$(".timingMode-day").show();
			$(".timingMode-week").hide();
			$(".timingMode-month").show();
			$(".timingMode-season").hide();
			$(".timingMode-year").hide();
			$(".timingMode-interval").hide();
		} else if (v === "4") {// 每季
			$(".timingMode-day").show();
			$(".timingMode-week").hide();
			$(".timingMode-month").hide();
			$(".timingMode-season").show();
			$(".timingMode-year").hide();
			$(".timingMode-interval").hide();
		} else if (v === "5") {// 每年
			$(".timingMode-day").show();
			$(".timingMode-week").hide();
			$(".timingMode-month").hide();
			$(".timingMode-season").hide();
			$(".timingMode-year").show();
			$(".timingMode-interval").hide();
		} else if (v === "6") {// 间隔时间
			$(".timingMode-day").hide();
			$(".timingMode-week").hide();
			$(".timingMode-month").hide();
			$(".timingMode-season").hide();
			$(".timingMode-year").hide();
			$(".timingMode-interval").show();
		}
	});
	$("input[id=timingMode_day]").trigger("change");

	// 列表查询
	$("#query_job_details").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_job_details").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_jobClassName" : queryValue
		};
		$(list_selector).jqGrid("setGridParam", {
			postData : null
		});
		$(list_selector).jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout({
		west__size : 600
	});
});