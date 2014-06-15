$(function() {
	var list_selector = "#list";
	var form_selector = "#duty_agent_form";
	var bean = {
		"uuid" : null,
		"consignor" : null,
		"trustee" : null,
		"businessType" : null,
		"content" : null,
		"condition" : null,
		"status" : null,
		"fromTime" : null,
		"toTime" : null,
		"consignorName" : null,
		"trusteeName" : null,
		"formatedFromTime" : null,
		"formatedToTime" : null
	};
	$(list_selector).jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=dutyAgent',
		colNames : [ "uuid", "委托人", "受托人", "编号", "状态", "开始时间", "结束时间" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
			key : true
		}, {
			name : "consignorName",
			index : "consignorName",
			width : "100"
		}, {
			name : "trusteeName",
			index : "trustee",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "100"
		}, {
			name : "status",
			index : "status",
			width : "100",
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == 1) {
					return "激活";
				} else if (cellvalue == 0) {
					return "终止";
				}
				return cellvalue;
			}
		}, {
			name : "fromTime",
			index : "fromTime",
			width : "180",
			formatter : "date",
			formatoptions : {
				srcformat : 'Y-m-d H:i',
				newformat : 'Y-m-d H:i'
			}
		}, {
			name : "toTime",
			index : "toTime",
			width : "180",
			formatter : "date",
			formatoptions : {
				srcformat : 'Y-m-d H:i',
				newformat : 'Y-m-d H:i'
			}
		} ], // 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			getDutyAgent(rowData.uuid);
		}
	}));

	// 根据UUID获取职务代理人
	function getDutyAgent(uuid) {
		var option = {};
		option.uuid = uuid;
		JDS.call({
			service : "dutyAgentService.getBean",
			data : option.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();
				$(form_selector).json2form(bean);
				// 设置树形下拉框的值
				$("#contentName").comboTree("initValue", bean.content);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// 委托人
	$("#consignorName").click(function(e) {
		$.unit.open({
			title : "选择人员",
			labelField : "consignorName",
			valueField : "consignor",
			selectType : 4
		});
	});
	// 受托人
	$("#trusteeName").click(function(e) {
		$.unit.open({
			title : "选择人员",
			labelField : "trusteeName",
			valueField : "trustee",
			selectType : 4
		});
	});

	// 委托内容
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "dutyAgentService",
				"methodName" : "getContentAsTreeAsync",
				"data" : "workflow"
			}
		}
	};
	$("#contentName").comboTree({
		labelField : "contentName",
		valueField : "content",
		treeSetting : setting,
		initService : "dutyAgentService.getKeyValuePair",
		initServiceParam : [ "workflow" ]
	});
	// JQuery Date Picker
	// $("#formatedFromTime, #formatedToTime").datetimepicker({
	// dateFormat : "yy-mm-dd"
	// });

	// 新增操作
	$("#btn_add").click(function() {
		$(form_selector).clearForm(true);
		$("#btn_del").hide();
	});

	// 保存用户信息
	$("#btn_save").click(function() {
		$(form_selector).form2json(bean);
		JDS.call({
			service : "dutyAgentService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				// 删除成功刷新列表
				$(list_selector).trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.consignorName;
		if (confirm("确定要删除职务代理人[" + name + "]吗？")) {
			JDS.call({
				service : "dutyAgentService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$(form_selector).clearForm(true);
					// 删除成功刷新列表
					$(list_selector).trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "dutyAgentService.removeAll",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_duty_agent").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_duty_agent").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_consignorName_OR_trusteeName" : queryValue
		};
		if (queryValue == "激活") {
			postData["query_EQI_status"] = 1;
		} else if (queryValue == "终止") {
			postData["query_EQI_status"] = 0;
		}
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout();
});