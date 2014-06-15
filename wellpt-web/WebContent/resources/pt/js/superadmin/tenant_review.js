$(function() {
	var validator = $("#review_form").validate({
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
				"query_EQI_status" : 2
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
				$("#review_form").json2form(bean);

				validator.form();
				
				
					//运营者手持身份证件照片
					$("#btn_download_businessIdCardPic").text(bean.businessIdCardName);
					//授权运营书
					$("#btn_download_authorizedOperatorsFile").text("授权运营书");
					//政府信息登记表
					$("#btn_download_governmentRegisterForm").text("政府信息登记表");
				if(bean.depatermentType =='business'){
					$(".bussiness").show();
					$(".government").hide();
				} else if(bean.depatermentType=='government'){
					$(".government").show();
					$(".bussiness").hide();
				}
			}
		});
	}

	//下载运营者手持身份证件照片
	$("#btn_download_businessIdCardPic").click(function(){
		location.href=ctx+'/security/tenant/download?uuid='+$('#businessIdCardPicUuid').val();
	});
	//下载授权运营书
	$("#btn_download_authorizedOperatorsFile").click(function(){
		location.href=ctx+'/security/tenant/download?uuid='+$('#authorizedOperatorsFileUuid').val();
	});
	//下载政府信息登记表
	 $("#btn_download_governmentRegisterForm").click(function(){
			location.href=ctx+'/security/tenant/download?uuid='+$('#governmentRegisterFormUuid').val();
	 });
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		$("#review_form").clearForm(true);
	});

	// 审核提交
	$("#btn_submit").click(function(e) {
		if (validator.form()) {
			$("#review_form").form2json(bean);
			JDS.call({
				service : "tenantManagerService.review",
				data : bean,
				success : function(result) {
					alert("审核成功!");
					$("#review_form").clearForm(true);
					// 审核成功刷新列表
					$("#list").trigger("reloadGrid");
				},
				error : function() {
					alert("审核失败，无法创建数据库!");
				}
			});
		}
	});

	// 删除操作
	$("#btn_reject").click(function() {
		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要拒绝单位[" + name + "]的申请吗?")) {
			JDS.call({
				service : "tenantManagerService.rejectTenant",
				data : bean.uuid,
				success : function(result) {
					alert("操作成功!");
					$("#review_form").clearForm(true);
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_reject_all").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要拒绝所选单位的申请吗?")) {
			JDS.call({
				service : "tenantManagerService.rejectAllTenant",
				data : [ rowids ],
				success : function(result) {
					alert("操作成功!");
					$("#review_form").clearForm(true);
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
		postData["query_EQI_status"] = 2;
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		if ("待审核".indexOf(queryValue) != -1) {
			postData = {
				"queryPrefix" : "query",
				"query_EQI_status" : 2
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