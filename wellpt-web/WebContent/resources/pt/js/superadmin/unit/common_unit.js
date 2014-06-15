$(function() {

	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"email" : null,
		"remark" : null,
		"tenantName" : null,
		"tenantId" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?serviceName=commonUnitService',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "名称", "编号", "电子邮箱", "备注", "租户" ],
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
				name : "code",
				index : "code",
				width : "180"
			}, {
				name : "email",
				index : "email",
				width : "180"
			}, {
				name : "remark",
				index : "remark",
				width : "180"
			}, {
				name : "tenantName",
				index : "tenantName",
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
			service : "commonUnitService.getBean",
			data : uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();

				$("#common_unit_form").json2form(bean);
				
				// 单元
				if (bean.tenantId != null && bean.tenantId != "") {
					$("#row_name").show();
					$("#row_code").show();
					$("#row_email").show();
					$("#row_remark").show();
					$("#row_tenant").show();
				} else {
					$("#row_name").show();
					$("#row_code").show();
					$("#row_email").hide();
					$("#row_remark").show();
					$("#row_tenant").hide();
				}
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	// 新增单位操作
	$("#btn_unit_add").click(
			function() {
				$("#common_unit_form").clearForm(true);
				$("#btn_del").hide();
				$("#type").val("1");
				$("#row_name").show();
				$("#row_code").show();
				$("#row_email").show();
				$("#row_remark").show();
				$("#row_tenant").show();
			});
	// 新增分类操作
	$("#btn_category_add").click(
			function() {
				$("#common_unit_form").clearForm(true);
				$("#btn_del").hide();
				$("#type").val("2");
				$("#row_name").show();
				$("#row_code").show();
				$("#row_email").hide();
				$("#row_remark").show();
				$("#row_tenant").hide();
			});
	

	// 保存单位信息
	$("#btn_save").click(function(e) {
		if ($("#row_name #name").val() == '') {
			alert("请填写名称");
			return ;
		}
		if ($("#row_tenant").css("display") != 'none') {
			//不是分类，判断是否有选择租户
			if ($("#row_tenant #tenantName").val() == '') {
				alert("请选择租户");
				return ;
			}
		}	
		$("#common_unit_form").form2json(bean);		
		//如果是分类，则不保存email和租户UUID
		if ($("#type").val() == "2") {
			bean.tenantId=null;
		}	
		JDS.call({
			service : "commonUnitService.saveBean",
			data : bean,
			success : function(result) {
				alert("保存成功!");
				$("#common_unit_form").clearForm(true);
				// 删除成功刷新列表
				$("#list").trigger("reloadGrid");
			},
			error : function() {
				alert("保存失败，无法创建单位数据库!");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除单位[" + name + "]吗?")) {
			JDS.call({
				service : "commonUnitService.removeByUuid",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#common_unit_form").clearForm(true);
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
				service : "commonUnitService.removeByUuids",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					$("#common_unit_form").clearForm(true);
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