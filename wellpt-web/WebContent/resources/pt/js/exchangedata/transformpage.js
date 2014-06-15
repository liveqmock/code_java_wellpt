$(function() {
	var bean = {
		"uuid": null,	
		"name": null,
		"id": null,	
		"code": null,
		"sourceId": null,
		"destinationId": null,
		"xsl": null,
	};
	
	
//	var options = new Object();
//	options.labelField = "viewName";
//	options.valueField = "path";
//	options.width = 250;
//	options.height = 220;
//	$.dyviewTree.open(options);
	JDS.call({
		service : "exchangeDataTypeService.getExDataTypeList",
		async : false,
		success: function(result) {
			$("#sourceId").empty();
			$("#destinationId").empty();
			var option_ = "";
			$.each(result.data, function(i) {
				option_ += "<option value=\"" + result.data[i].id + "\">" + result.data[i].name + "</option>/n/r";
     		});
			$("#sourceId").html(option_);
		    $("#destinationId").html(option_);
		}
	});
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=exchangeDataTransform",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "名称", "ID", "编号" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "40"
			},{
				name : "id",
				index : "id",
				width : "30"
			},{
				name : "code",
				index : "code",
				width : "30"
			} ],
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "code",
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
				getModuleById(id);
	//			var rowData = $(this).getRowData(id);
	//			getModuleById(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据模块ID获取模块信息
	function getModuleById(uuid) {
		var exchangeDataTransform = {};
		exchangeDataTransform.uuid = uuid;
		JDS.call({
			service : "exchangeDataTransformService.getBeanByUuid",
			async : false,
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				$("#module_form").json2form(bean);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// 新增
	$("#btn_add").click(function() {
		$("#module_form").clearForm(true);
		hideDelButton();
		
	});
	function clear() {
		$("#module_form").clearForm(true);
		// 清空JSON
		$.common.json.clearJson(bean);
	}
	
	//隐藏删除按钮
	function hideDelButton(){
		$("#btn_del").hide();
	}
	//显示删除按钮
	function showDelButton(){
		$("#btn_del").show();
	}

	// 保存
	$("#btn_save").click(function() {
		/*$("#cateName").val($("#cateUuid").find("option:selected").text());
		if(!($("#defaultWidth").val().match(/^\+?[1-9][0-9]*$/)||$("#defaultHeight").val().match(/^\+?[1-9][0-9]*$/))){
			alert("宽高请输入合法的数字");
			return false;
		}*/
		//收集表单数据
		$("#module_form").form2json(bean);
		
		JDS.call({
			service : "exchangeDataTransformService.saveBean",
			data : [ bean ],
			async:false,
			validate : true,
			success : function(result) {
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				showDelButton();
				alert("保存成功！");
			}
		});
	});

	// 删除
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除页面元素[" + name + "]吗？")) {
			JDS.call({
				service : "exchangeDataTransformService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源？")) {
			JDS.call({
				service:"exchangeDataTransformService.deleteAllByIds",
				data:[rowids],
				success:function(result) {
					alert("删除成功!");
					//删除成功刷新列表
					clear();
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	//查询
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_name_OR_id_OR_code" : queryValue,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	//回车查询
	$("#query_keyWord").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	Layout.layout({
		west__size : 480
	});
});
