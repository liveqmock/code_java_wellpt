$(function() {
	var bean = {
			"uuid" : null,
			"businessManagerUserId" : null,
			"businessManagerUserName" : null,
			"businessSenderId" : null,
			"businessSenderName" : null,
			"businessReceiverId" : null,
			"businessReceiverName" : null
	};
	$("#list").jqGrid(
			$.extend($.common.jqGrid.settings, {
				url : ctx + '/common/jqgrid/query?serviceName=businessManageService',
				mtype : 'POST',
				datatype : "json",
				colNames : [ "uuid", "类别名称", "业务单位","unitManagerUserId", "业务负责人","businessManagerUserId", "业务发送人","businessSenderId", "业务接收人","businessReceiverId"],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "120",
					hidden : true,
					key : true
				}, {
					name : "businessTypeName",
					index : "businessTypeName",
					width : "130"
				}, {
					name : "unitName",
					index : "unitName",
					width : "130",
					sortable:false
				}, {
					name : "unitManagerUserId",
					index : "unitManagerUserId",
					width : "130",
					hidden : true
				}, {
					name : "businessManagerUserName",
					index : "businessManagerUserName",
					width : "130",
					sortable:false
				},  {
					name : "businessManagerUserId",
					index : "businessManagerUserId",
					width : "130",
					hidden : true
				}, {
					name : "businessSenderName",
					index : "businessSenderName",
					width : "130",
					sortable:false
				},  {
					name : "businessSenderId",
					index : "businessSenderId",
					width : "130",
					hidden : true
				}, {
					name : "businessReceiverName",
					index : "businessReceiverName",
					width : "130",
					sortable:false
				},  {
					name : "businessReceiverId",
					index : "businessReceiverId",
					width : "130",
					hidden : true
				}],
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
				height : 150,
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
					var currentUserId = $("#currentUserId").val();
					var isBusinessManager = false;
					if (rowData.businessManagerUserId != null && rowData.businessManagerUserId.indexOf(currentUserId) >= 0) 
					{
						isBusinessManager = true;
					}	
					getBean(rowData.uuid, isBusinessManager);
				},
				loadComplete : function(data) {
					$("#list").setSelection($("#list").getDataIDs()[0]);
				}
			}));
	
	// 根据UUID获取组织选择项
	function getBean(uuid, isBusinessManager) {
		JDS.call({
			service : "businessManageService.getBusinessUnitTreeBean",
			data : uuid,
			success : function(result) {
				bean = result.data;
				$("#business_manage_form").json2form(bean);
				$("#btn_save").show();
				if(bean.currentUserIsAdmin === true){
					sizeBusinessManager(true);
				} else{
					sizeBusinessManager(isBusinessManager);
				}
			}
		});
	}	
	
	function sizeBusinessManager(isBusinessManager) {
		if (isBusinessManager) {
			$("#row_business_sender").show();
			$("#row_business_receiver").show();			
		} else {
			$(".business_execute_li").hide();
			$("#row_business_sender").hide();
			$("#row_business_receiver").hide();
		}
		$("#btn_save").show();
	}
	
	// 保存用户信息
	$("#btn_save").click(function() {
		$("#business_manage_form").form2json(bean);
		JDS.call({
			service : "businessUnitTreeService.saveUserIdToBusinessUnitTree",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
			}
		});
	});	
	
	//选择业务负责人
	$("#businessManagerUserName").click(function() {
		$.unit.open({
			title : "选择业务负责人",
			labelField : "businessManagerUserName",
			valueField : "businessManagerUserId",
			selectType : 4
		});
	});		
	
	//选择业务执行人
	$("#businessSenderName").click(function() {
		$.unit.open({
			title : "选择业务发送人",
			labelField : "businessSenderName",
			valueField : "businessSenderId",
			selectType : 4
		});
	});
	$("#businessReceiverName").click(function() {
		$.unit.open({
			title : "选择业务接收人",
			labelField : "businessReceiverName",
			valueField : "businessReceiverId",
			selectType : 4
		});
	});	
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	//隐藏按钮
	$("#btn_save").hide();
	
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
