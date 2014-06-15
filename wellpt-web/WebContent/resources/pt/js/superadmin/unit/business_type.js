$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"unitName" : null,
		"unitId" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?serviceName=businessTypeService',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "类别名称", "ID", "编号", "业务管理单位" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
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
				width : "100"
			}, {
				name : "unitName",
				index : "unitName",
				width : "180",
				sortable:false
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
			postData : {
				"queryPrefix" : "query",
				"query_EQI_status" : 0
			},
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},// 行选择事件
			onSelectRow : function(id) {
				var rowData = $(this).getRowData(id);
				getBean(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据UUID获取组织选择项
	function getBean(uuid) {
		JDS.call({
			service : "businessTypeService.getBean",
			data : uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();

				$("#business_type_form").json2form(bean);
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		$("#business_type_form").clearForm(true);
		$("#btn_del").hide();
	});
	
	$("#id").blur(function() {
		if ($("#id").val() != "") {
			validateId($("#id").val(), $("#uuid").val());
		}		
	});
	
	function validateId(id, uuid, isSave) {
		$.ajax({
			type:"get",
			async:false,
			url:ctx+"/superadmin/unit/businessType/validateId?id="+id+"&uuid="+uuid,
			success:function(result){
				if (!result.success) {
					alert("ID已经存在!");
				} else {
					if (isSave) {
						saveBean();
					}
				}
			}			
		});
	}
	
	//业务管理单位
	$("#unitName").click(function() {
		$.unit.open({
			title : "选择业务管理单位",
			labelField : "unitName",
			valueField : "unitId",
			type : "Unit",
			selectType : 2,//都能选
			multiple : false
		});
	});
	
	function saveBean() {
		$("#business_type_form").form2json(bean);
		JDS.call({
			service : "businessTypeService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				$("#business_type_form").clearForm(true);
				// 删除成功刷新列表
				$("#list").trigger("reloadGrid");
			},
			error : function() {
				alert("保存失败，无法创建单位数据库!");
			}
		});
	}

	// 保存业务类别信息
	$("#btn_save").click(function(e) {
		if ($("#business_type_form #name").val() == "") {
			alert("请填写类别名称");
		}
		if ($("#id").val() == "") {
			alert("请填写ID");
		}
		if ($("#id").val() != "") {
			validateId($("#id").val(), $("#uuid").val(), true);
		}
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除业务类别[" + name + "]吗?")) {
			JDS.call({
				service : "businessTypeService.remove",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#business_type_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
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
				service : "businessTypeService.removes",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					$("#business_type_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_value").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_value").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_account" : queryValue
		};
		postData["query_EQI_status"] = 0;
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		if ("禁用".indexOf(queryValue) != -1) {
			postData = {
				"queryPrefix" : "query",
				"query_EQI_status" : 0
			};
		}
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout();
});