$(function() {
	var validator = $("#tenant_form").validate({
		rules : {
			name : {
				required : true
			},
			account : {
				required : true,
				remote : {
					url : ctx + "/common/validate/check/exists",
					type : "POST",
					data : {
						uuid : function() {
							return $('#uuid').val();
						},
						checkType : "tenant",
						fieldName : "account",
						fieldValue : function() {
							return $('#account').val();
						}
					}
				}
			},
			password : {
				required : true,
				minlength : 5,
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			name : {
				required : "单位名称不能为空!"
			},
			account : {
				required : "账号不能为空!",
				remote : "该账号已存在!"
			},
			password : {
				required : "密码不能为空!",
				minlength : jQuery.format("密码不能小于{0}个字符!")
			},
			email : {
				required : "邮件地址不能为空!",
				email : "无效的邮件地址!"
			}
		}
	});

	var bean = {
		"uuid" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"account" : null,
		"password" : null,
		"email" : null,
		"status" : null,
		"remark" : null,
		"jdbcType" : null,
		"jdbcServer" : null,
		"jdbcPort" : null,
		"jdbcDatabaseName" : null,
		"jdbcUsername" : null,
		"jdbcPassword" : null,
		"databaseConfigUuid" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?serviceName=tenantService',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "名称", "编号", "账号", "状态" ],
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
				width : "100"
			}, {
				name : "account",
				index : "account",
				width : "180"
			}, {
				name : "status",
				index : "status",
				width : "180",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue == 0) {
						return "禁用";
					} else if (cellvalue == 1) {
						return "启用";
					} else if (cellvalue == 2) {
						return "待审核";
					} else if (cellvalue == 3) {
						return "审核失败";
					} else if (cellvalue == 4) {
						return "已删除";
					}
					return cellvalue;
				}
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
				"query_EQI_status" : 3
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
				getTenant(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据UUID获取组织选择项
	function getTenant(uuid) {
		var tenant = {};
		tenant.uuid = uuid;
		JDS.call({
			service : "tenantManagerService.get",
			data : tenant.uuid,
			success : function(result) {
				bean = result.data;
				$("#btn_del").show();

				$("#tenant_form").json2form(bean);

				validator.form();

				$("#databaseConfigUuid").attr("disabled", "disabled");
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		$("#tenant_form").clearForm(true);
		$("#databaseConfigUuid").removeAttr("disabled");
		$("#btn_del").hide();
	});

	// 保存数据库配置信息
	$("#btn_save").click(function(e) {
		if (validator.form()) {
			$("#tenant_form").form2json(bean);
			if (bean.status === false) {
				bean.status = 0;
			} else {
				bean.status = 1;
			}
			JDS.call({
				service : "tenantManagerService.saveTenant",
				data : bean,
				success : function(result) {
					alert("保存成功!");
					$("#tenant_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				},
				error : function() {
					alert("保存失败，无法创建单位数据库!");
				}
			});
		}
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
				service : "tenantManagerService.deleteTenant",
				data : bean.uuid,
				success : function(result) {
					alert("删除成功!");
					$("#tenant_form").clearForm(true);
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
				service : "tenantManagerService.deleteAllTenant",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					$("#tenant_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_tenant_normal").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_tenant_normal").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code_OR_account" : queryValue
		};
		postData["query_EQI_status"] = 3;
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		if ("审核失败".indexOf(queryValue) != -1) {
			postData = {
				"queryPrefix" : "query",
				"query_EQI_status" : 3
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