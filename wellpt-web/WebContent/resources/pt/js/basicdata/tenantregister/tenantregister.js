$(function() {
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
		"jdbcDriver" : null,
		"jdbcDialect" : null,
		"jdbcServer" : null,
		"jdbcPort" : null,
		"jdbcDatabaseName" : null,
		"jdbcUsername" : null,
		"jdbcPassword" : null,
		"databaseConfigUuid" : null,
		"depatermentType" : null,
		"address" : null,
		"postcode" : null,
		"businessLicenseNum" : null,
		"businessLicenseAddress" : null,
		"businessDeadline" : null,
		"businessDeadlineCheck" : null,
		"businessScope" : null,
		"businessLicensePic":null,
		"registerFigure":null,
		"institutionalCode":null,
		"businessIdCardName":null,
		"businessIdCardNum":null,
		"mobileNum":null,
		"position":null,
		"businessIdCardPicUuid":null,
		"authorizedOperatorsFileUuid":null,
		"governmentRegisterFormUuid":null
	};
	$("#list").jqGrid({
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
			"query_EQI_status" : 1
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
	});

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
				$("#btn_check_connecton_status").show();

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
		$("#btn_check_connecton_status").hide();
	});

	
	// 单位注册
	$("#btn_save").click(function(e) {
		
			// 清空JSON
			$.common.json.clearJson(bean);
			$("#register_form").form2json(bean);
			if (bean.status === false) {
				bean.status = 0;
			} else {
				bean.status = 1;
			}
			$.ajaxFileUpload({
				url:ctx + '/security/tenant/uploadBusinessIdCardPic.action',//链接到服务器的地址
				secureuri:false,
				fileElementId:'businessIdCardPic',//原先是fileElementId:’id’ 只能上传一个  
				dataType: 'text',  //服务器返回的数据格式
				success: function (data, status){
					$("#businessIdCardPicUuid").val(data);
				}
			});
		
				$.ajaxFileUpload({
					url:ctx + '/security/tenant/uploadAuthorizedOperatorsFile.action',//链接到服务器的地址
					secureuri:false,
					fileElementId:'authorizedOperatorsFile',//原先是fileElementId:’id’ 只能上传一个  
					dataType: 'text',  //服务器返回的数据格式
					success: function (data, status){
						$("#authorizedOperatorsFileUuid").val(data);
					}
				});
				$.ajaxFileUpload({
					url:ctx + '/security/tenant/uploadGovernmentRegisterForm.action',//链接到服务器的地址
					secureuri:false,
					fileElementId:'governmentRegisterForm',//原先是fileElementId:’id’ 只能上传一个  
					dataType: 'text',  //服务器返回的数据格式
		            success: function (data, status){
			           	$("#governmentRegisterFormUuid").val(data);
		            }
				});
		
				setTimeout(function(){
				 	$("#register_form").form2json(bean);
		     		JDS.call({
						service : "tenantManagerService.register",
						data : [bean],
						validate : true,
						async: false,
						success : function(result) {
							alert("保存成功！");
							$("#register_form").clearForm(true);
							// 删除成功刷新列表
							$("#list").trigger("reloadGrid");
//								if(result.data){
//									window.location.href="${ctx}/register/success";
//								}else{
//									window.location.href="${ctx}/register/failure";
//								}
						},
						error : function() {
							alert("保存失败，无法创建单位数据库!");
						}
					});
				},3000); 
				

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

	// 连接性测试
	$("#btn_check_connecton_status").click(function(e) {
		var uuid = $("#uuid").val();
		if (uuid == null || $.trim(uuid) == "") {
			alert("请先保存配置!");
			return;
		}
		JDS.call({
			service : "tenantManagerService.checkDatasourceConnectionStatus",
			data : uuid,
			success : function(result) {
				if (result.data === true) {
					alert("测试成功!");
				} else {
					alert("测试失败!");
				}
			}
		});
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
		postData["query_EQI_status"] = 1;
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		if ("启用".indexOf(queryValue) != -1) {
			postData = {
				"queryPrefix" : "query",
				"query_EQI_status" : 1
			};
		}
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});


});