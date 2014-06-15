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
		"onlineSubject" : null,
		"onlineBody" : null,
		"smsBody" : null,
		"emailSubject" : null,
		"emailBody" : null
	};
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