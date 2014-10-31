$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"category" : null,
		"type" : null,
		"sendWays" : null,
		"sendTime" : null,
		"mappingRule" : null,
		"messageEvent":null,
		"messageEventText":null,
		"isOnlinePopup":null,
		"showViewpoint":null,
		"viewpointY":null,
		"viewpointN":null,
		"viewpointNone":null,
		"askForSchedule":null,
	    "foregroundEvent":null,
	    "foregroundEventText":null,
	    "backgroundEvent":null,
	    "backgroundEventText":null,
		"onlineSubject" : null,
		"onlineBody" : null,
		"smsBody" : null,
		"emailSubject" : null,
		"emailBody" : null,
		"scheduleTitle" : null,
		"scheduleDates" : null,
		"scheduleDatee" : null,
		"scheduleAddress" : null,
		"reminderTime" : null,
		"scheduleBody" : null,
		"srcTitle" : null,
		"srcAddress" : null,
		"webServiceUrl":null,
		"usernameKey":null,
		"usernameValue":null,
		"passwordKey":null,
		"passwordValue":null,
		"tenantidKey":null,
		"tenantidValue":null,
		"children" : [],
		"changedChildren":[],
		"deletedChildren":[]
	};
	var delbean={
			"deletedChildren":[]	
	};
	// werbservice参数属性
	var child_parm_list_selector = "#child_parm_list";
	
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + "/common/jqgrid/query?queryType=messageTemplate",
		mtype : "POST",
		datatype : "json",
		colNames : [ "uuid", "名称", "ID", "编号", "分类" ],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "180",
			hidden : true,
		}, {
			name : "name",
			index : "name",
			width : "180"
		}, {
			name : "id",
			index : "id",
			width : "180"
		}, {
			name : "code",
			index : "code",
			width : "80"
		}, {
			name : "category",
			index : "category",
			width : "180"
		} ],
		rowNum : 20,
		rownumbers : true,
		rowList : [ 10, 20, 50, 100, 200 ],
		rowId : "uuid",
		pager : "#pager",
		sortname : "name",
		recordpos : "right",
		viewrecords : true,
		sortable : true,
		sortorder : "asc",
		multiselect : true,
		autowidth : true,
		height : 450,
		scrollOffset : 0,
		jsonReader : {
			root : "dataList",
			total : "totalPages",
			page : "currentPage",
			records : "totalRows",
			repeatitems : false
		},// 行选择事件
		onSelectRow : function(id) {
			// var rowData = $(this).getRowData(id);
			// toForm(rowData, $("#template_form"));
			getTemplateById(id);
		},
		// ondblClickRow : function(id) {
		// var rowData = $(this).getRowData(id);
		// var url = ctx + "/org/user/update/" + rowData["uuid"];
		// var userName = rowData["userName"];
		// TabUtils.openTab("user_edit" + id, "编辑用户" + userName, url);
		// },
		loadComplete : function(data) {
			$("#list").setSelection($("#list").getDataIDs()[0]);
		}
	}));
	//webservice属性参数 JQuery jqGrid
	$(child_parm_list_selector).jqGrid({
		datatype : "local",
		colNames : [ "uuid","id", "参数名称", "参数值","说明"],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "100",
			hidden : true
		}, {
			name : "id",
			index : "id",
			width : "100",
			hidden : true
		}, {
			name : "parmName",
			index : "parmName",
			width : "150",
			editable : true
		}, {
			name : "parmValue",
			index : "parmValue",
			width : "150",
			editable : true
		} , {
			name : "parmdesc",
			index : "parmdesc",
			width : "250",
			editable : true
		}],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : false,
		height : "100%",
		rowId : "id",
		scrollOffset : 0,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			// Modify event handler to save on blur.
			$("#" + iRow + "_" + cellname, child_parm_list_selector).one('blur', function() {
				$(child_parm_list_selector).saveCell(iRow, iCol);
			});
		}
	});
	var settingForSource = {
			async : {
				otherParam : {
					"serviceName" : "messageEventService",
					"methodName" : "getEventServerSourceList",
					"data" : 1
				}
			},check : {
				enable : false
			},
			callback : {
				onClick: dataSourceTreeNodeOnClick,
			}
		};
	$("#backgroundEventText").comboTree("clear");
	$("#backgroundEventText").comboTree("enable");
	$("#backgroundEventText").comboTree("setParams", {treeSetting : settingForSource});
	
	var settingClientForSource = {
			async : {
				otherParam : {
					"serviceName" : "messageEventService",
					"methodName" : "getEventClientSourceList",
					"data" : 1
				}
			},check : {
				enable : false
			},
			callback : {
				onClick: dataSourceClientTreeNodeOnClick,
			}
		};
	$("#messageEventText").comboTree("clear");
	$("#messageEventText").comboTree("enable");
	$("#messageEventText").comboTree("setParams", {treeSetting : settingClientForSource});
	var settingForegroundEvent = {
			async : {
				otherParam : {
					"serviceName" : "dataDictionaryService",
					"methodName" : "getFromTypeAsTreeAsync",
					"data" : "ONLINE_ARRIVE_JS_EVENT"
				}
			},check : {
				enable : false
			},
			callback : {
				onClick: foregroundEventTextTreeNodeOnClick,
			}
		};
	$("#foregroundEventText").comboTree("clear");
	$("#foregroundEventText").comboTree("enable");
	$("#foregroundEventText").comboTree("setParams", {treeSetting : settingForegroundEvent});
	//后台数据接口点击的回调函数
	function dataSourceTreeNodeOnClick(event, treeId, treeNode) {
		$("#backgroundEventText").val(treeNode.name);
		$("#backgroundEvent").val(treeNode.id);
		$("#backgroundEventText").comboTree("hide");
	}
	//消息发送client端触发事件
	function dataSourceClientTreeNodeOnClick(event, treeId, treeNode) {
		$("#messageEventText").val(treeNode.name);
		$("#messageEvent").val(treeNode.id);
		$("#messageEventText").comboTree("hide");
	}
	//前台触发js事件
	function foregroundEventTextTreeNodeOnClick(event, treeId, treeNode) {
		$("#foregroundEventText").val(treeNode.name);
		$("#foregroundEvent").val(treeNode.data);
		$("#foregroundEventText").comboTree("hide");
	}
	// 增加webservice参数
	$("#btn_parm_add").click(function(e) {
		// 判断是否已选择字典
		var mydata = {
			uuid : null
		};
		var newDate = new Date().getTime();
		$(child_parm_list_selector).jqGrid("addRowData", newDate, mydata);
	});
	//删除websevice参数
	$("#btn_parm_del").click(function(e) {
		var rowids = $(child_parm_list_selector).jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择参数!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $(child_parm_list_selector).getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				delbean["deletedChildren"].push(rowData);
			}
			$(child_parm_list_selector).jqGrid('delRowData', rowids[i]);
		}
	});
	// 收集改变的子结点
	function collectChildrenDataList(bean) {
		var changes = $(child_parm_list_selector).jqGrid("getRowData");
		for ( var i = 0; i <=(changes.length - 1) ; i++) {
			 changes[i].id=i+1;
		}
		bean["changedChildren"] = changes;
		bean["children"] = [];
		bean["deletedChildren"]=delbean["deletedChildren"];
	}
	
	// 设置字典列表数据
	function toChildDataparmList(bean) {
		var $childrenList = $(child_parm_list_selector);
		// 清空字典列表
		$childrenList.jqGrid("clearGridData");
		var children = bean["children"];
		for ( var index = 0; index < children.length; index++) {
			$childrenList.jqGrid("addRowData", children[index].uuid, children[index]);
		}
	}
	// 根据用户ID获取用户信息
	function getTemplateById(id) {
		var user = {};
		user.id = id;
		JDS.call({
			service : "messageTemplateService.getBeanById",
			data : [ id ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				$("#template_form").json2form(bean);
				toChildDataparmList(bean);//设定webservice参数的信息
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		clear();
		hideDelButton();
	});
	function clear() {
		$("#template_form").clearForm(true);
		// 清空JSON
		$.common.json.clearJson(bean);
	}

	// 保存用户信息
	$("#btn_save").click(function() {
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#template_form").form2json(bean);
		collectChildrenDataList(bean);//收集列表数据
		JDS.call({
			service : "messageTemplateService.saveBean",
			data : [ bean ],
			validate : true,
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				showDelButton();
			}
		});
		$.common.json.clearJson(delbean);
	});
	// 隐藏删除按钮
	function hideDelButton() {
		$("#btn_del").hide();
	}
	// 显示删除按钮
	function showDelButton() {
		$("#btn_del").show();
	}

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除消息模板[" + name + "]吗？")) {
			JDS.call({
				service : "messageTemplateService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					alert("删除成功!");
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源吗？")) {
			JDS.call({
				service : "messageTemplateService.deleteAllById",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					clear();
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 查询
	$("#btn_query").click(
			function() {
				var queryValue = $("#query_keyWord").val();
				var queryOther = "";
				if (queryValue.indexOf("实") > -1 || queryValue.indexOf("体") > -1) {
					queryOther = "entity";
				} else if (queryValue.indexOf("动") > -1 || queryValue.indexOf("态") > -1
						|| queryValue.indexOf("表") > -1 || queryValue.indexOf("单") > -1) {
					queryOther = "formdefinition";
				}
				$("#list").jqGrid("setGridParam", {
					postData : {
						"queryPrefix" : "query",
						"queryOr" : true,
						"query_LIKES_id_OR_name_OR_category" : queryValue,
					},
					page : 1
				}).trigger("reloadGrid");
			});

	// 回车查询
	$("#query_keyWord").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});

	Layout.layout({
		west__size : 450
	});
});