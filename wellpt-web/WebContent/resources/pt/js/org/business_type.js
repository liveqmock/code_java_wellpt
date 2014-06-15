$(function() {
	// 最新选择的树结点
	var latestSelectedNode = null;
	var bean = {
		"businessTypeUuid" : null,
		"unitManagerUserName" : null,
		"unitManagerUserId" : null
	};
	var treeNodeBean = {
			"businessTypeUuid" : null,
			"uuid" : null,
			"parentUuid" : null,
			"unitName" : null,
			"unitId" : null,
			"code" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?serviceName=businessUnitTreeService',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "类别名称", "业务管理单位", "业务单位管理员","unitManagerUserId"],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "120",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "130"
			}, {
				name : "unitName",
				index : "unitName",
				width : "130",
				sortable:false
			}, {
				name : "unitManagerUserName",
				index : "unitManagerUserName",
				width : "130",
				sortable:false
			},  {
				name : "unitManagerUserId",
				index : "unitManagerUserId",
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
				getBean(rowData.uuid);
				var currentUserId = $("#currentUserId").val();
				var isUnitManager = false;
				if (rowData.unitManagerUserId != null && rowData.unitManagerUserId.indexOf(currentUserId) >= 0) 
				{
					isUnitManager = true;
				}	
				sizePaneLan(isUnitManager);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));	
	
	// 根据UUID获取组织选择项
	function getBean(uuid) {
		JDS.call({
			service : "businessUnitTreeService.getBusinessTypeBean",
			data : uuid,
			success : function(result) {
				bean = result.data;

				$("#set_unit_manager_username_form").json2form(bean);				
				
				if ($("#businessUnitTree")[0]) {
					$("#businessUnitTree")[0].contentWindow.setBusinessTypeUuid(bean.businessTypeUuid);
				}				
			}
		});
	}
	
	
	function getUnit(businessUnitTreeUuid) {
		JDS.call({
			service : "businessUnitTreeService.getBean",
			data : [ businessUnitTreeUuid ],
			success : function(result) {
				treeNodeBean = result.data;
				// 设置表单数据
				$("#uuid").val(treeNodeBean.uuid);
				$("#row_name #unitName").val(treeNodeBean.unitName);
				$("#row_name #unitId").val(treeNodeBean.unitId);
				$("#row_code #code").val(treeNodeBean.code);
				
				$("#row_name").show();
				$("#row_code").show();				
			}
		});
	}
	
	// JQuery zTree设置
	var setting = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			expandSpeed : ($.browser.msie && parseInt($.browser.version) <= 6) ? ""
					: "fast"
		},
		callback : {
			beforeClick : function(treeId, treeNode) {
				// 最新选择的树结点
				latestSelectedNode = treeNode;
				var zTree = $.fn.zTree.getZTreeObj("business_unit_tree");				
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
				}
				if (treeNode.id != null && treeNode.id != -1) {
					// 查看详细
					getUnit(treeNode.id);
				}
				$("#row_name").show();
				$("#row_code").show();
				return true;
			}
		}
	};
	// JQyery zTree加载组织单元树结点
	function loadUnitTree(businessTypeUuid) {		
		JDS.call({
			service : "businessUnitTreeService.getAsTree",
			data : [ businessTypeUuid ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#business_unit_tree"), setting,
						result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}				
			}
		});		
	}
	
	// 新增单位操作
	$("#btn_unit_add").click(
			function() {
				if (latestSelectedNode != null && latestSelectedNode.id != null
						&& latestSelectedNode.id != -1) {
					$("#parentUuid").val(latestSelectedNode.id);
				}
				$("#uuid").val("");
				$("#row_name #unitName").val("");
				$("#row_name #unitId").val("");
				$("#row_code #code").val("");
				$("#row_name").show();
				$("#row_code").show();
			}
	);	
	
	//选择业务单位管理员
	$("#btn_unit_manager_username").click(function() {
		if (typeof ($("#unitManagerUserName").attr("hiddenValue")) != "undefined") {
			$("#unitManagerUserName").attr("hiddenValue", $("#unitManagerUserId").val());
		}		
		$.unit.open({
			title : "选择业务单位管理员",
			labelField : "unitManagerUserName",
			valueField : "unitManagerUserId",
			selectType : 4,
			afterSelect : saveUsers
		});
	});
	
	window.saveUsers = function() {
		var businessTypeUuid = $("#list").jqGrid('getGridParam', 'selrow');
		if (businessTypeUuid == null || businessTypeUuid == "") {
			alert("请选择业务类别");
			return ;
		}
		var unitManagerUserId = $("#unitManagerUserId").val();
		var unitManagerUserName = $("#unitManagerUserName").val();
		$.ajax({
			type:"post",
			async:false,
			url:ctx+"/superadmin/unit/businessType/saveUnitManagerToBusinessType",
			data:{"businessTypeUuid":businessTypeUuid,"unitManagerUserId":unitManagerUserId,"unitManagerUserName":unitManagerUserName},
			success:function(result){
				if (result.success) {					 
					alert("保存成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				} else {
					alert("保存失败!");
				}
			}			
		});
	};
	
	// 删除操作
	$("#btn_unit_del").click(function() {
		if (latestSelectedNode == null || latestSelectedNode.id == -1) {
			alert("请选择节点！");
			return;
		}
		var name = latestSelectedNode.name;
		var uuid = latestSelectedNode.id;
		if (confirm("确定要删除[" + name + "]吗？")) {
			JDS.call({
				service : "businessUnitTreeService.remove",
				data : [ uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					var businessTypeUuid = $("#businessTypeUuid").val();
					loadUnitTree(businessTypeUuid);
					// 清空表单等
					$("#uuid").val("");
					$("#row_name #unitName").val("");
					$("#row_name #unitId").val("");
					$("#row_code #code").val("");
					latestSelectedNode = null;
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
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 页面布局
	Layout.layout();	
	
	function sizePaneLan(isUnitManager) {
		if (isUnitManager) {
			sizePane("west", "45%");	
			$("#businessUnitTree").show();
		} else {
			sizePane("west", "100%");
			$(".ui-layout-resizer").remove();
			$(".ui-layout-center").remove();
		}
	}
		
	window.setChildWin = function(){
		$("#businessUnitTree")[0].contentWindow.setUnit($("#unitName").val(), $("#unitId").val());
	};
});

function selectUnitName(unitName, unitId) {
	$("#unitName").val(unitName);
	$("#unitId").val(unitId);
	$.unit.open({
		title : "选择单位",
		labelField : "unitName",
		valueField : "unitId",
		type : "Unit",
		selectType : 2,
		multiple : false,
		isSetChildWin : true
	});
};
