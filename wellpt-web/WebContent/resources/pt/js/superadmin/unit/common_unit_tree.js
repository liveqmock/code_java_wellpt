$(function() {
	// 最新选择的树结点
	var latestSelectedNode = null;
	var form_selector = "#unit_tree_form";
	var bean = {
		"uuid" : null,
		"parentUuid" : null,
		"parentName" : null,
		"code" : null,
		"unitId" : null,
		"email" : null,
		"remark" : null,
		"tenantName" : null,
		"tenantId" : null,
		"unitName" : null,
		"unitShortName" : null,
		"unitUuid" : null
	};

	// 根据用户ID获取用户信息
	function getUnit(unitTreeUuid) {
		JDS.call({
			service : "commonUnitTreeService.getBean",
			data : [ unitTreeUuid ],
			success : function(result) {
				bean = result.data;
				// 设置表单数据
				$(form_selector).json2form(bean);
				
				// 单元
				if (bean.tenantId != null && bean.tenantId != "") {
					$("#type").val("1");
					$("#row_tenant").show();
				} else {
					$("#type").val("2");					
					$("#row_tenant").hide();
				}
				$("#row_name").show();
				$("#row_shortName").show();
				$("#row_code").show();
				$("#row_id").show();
				$("#row_email").hide();
				$("#row_remark").show();
				$("#row_parent").show();
				$("#btn_save").show();
				$("#btn_del").show();
				
				$("#tenantId option").each(function(){
					if ($(this).val() == bean.tenantId) {
						$(this).attr("selected", "selected");
					}
				});
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	$("#btn_save").hide();
	$("#btn_del").hide();

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
				var zTree = $.fn.zTree.getZTreeObj("unit_tree");
				if (treeNode.isParent) {
					zTree.expandNode(treeNode);
					// 清空form表单数据
					clear();
				}
				if (treeNode.id != null && treeNode.id != -1) {
					// 查看详细
					getUnit(treeNode.id);
				}
				$("#row_name").show();
				$("#row_shortName").show();
				$("#row_code").show();
				$("#row_parent").show();
				return true;
			}
		}
	};
	// JQyery zTree加载组织单元树结点
	function loadUnitTree() {
		JDS.call({
			service : "commonUnitTreeService.getAsTree",
			data : [ -1 ],
			success : function(result) {
				var zTree = $.fn.zTree.init($("#unit_tree"), setting,
						result.data);
				var nodes = zTree.getNodes();
				// 默认展开第一个节点
				if (nodes.length > 0) {
					var node = nodes[0];
					zTree.expandNode(node, true, false, false, true);
				}
				latestSelectedNode = null;
			}
		});
		
	}
	loadUnitTree();

	// 新增单位操作
	$("#btn_unit_add").click(
			function() {
				$("#unit_tree_form").clearForm(true);
				$("#btn_del").hide();
				if (latestSelectedNode != null && latestSelectedNode.id != null
						&& latestSelectedNode.id != -1) {
					$("#parentUuid").val(latestSelectedNode.id);
					$("#parentName").val(latestSelectedNode.name);
				}
				$("#type").val("1");
				$("#row_name").show();
				$("#row_shortName").show();
				$("#row_code").show();
				$("#row_id").show();
				$("#row_email").show();
				$("#row_remark").show();
				$("#row_tenant").show();
				$("#row_parent").show();
				$("#btn_save").show();
				$("#btn_del").hide();
			});
	// 新增分类操作
	$("#btn_category_add").click(
			function() {
				$("#unit_tree_form").clearForm(true);
				$("#btn_del").hide();
				if (latestSelectedNode != null && latestSelectedNode.id != null
						&& latestSelectedNode.id != -1) {
					$("#parentUuid").val(latestSelectedNode.id);
					$("#parentName").val(latestSelectedNode.name);
				}
				$("#type").val("2");
				$("#row_name").show();
				$("#row_shortName").show();
				$("#row_code").show();
				$("#row_id").show();
				$("#row_email").hide();
				$("#row_remark").show();
				$("#row_tenant").hide();
				$("#row_parent").show();
				$("#btn_save").show();
				$("#btn_del").hide();
			});
	/*
	$("#unitName").click(function() {
		$("#list").jqGrid(
				$.extend($.common.jqGrid.settings, {
					url : ctx + '/common/jqgrid/query?serviceName=commonUnitService',
					mtype : 'POST',
					datatype : "json",
					colNames : [ "uuid", "名称", "编号", "电子邮箱", "备注", "租户" ],
					colModel : [ {
						name : "uuid",
						index : "uuid",
						width : "100",
						hidden : true,
						key : true
					}, {
						name : "name",
						index : "name",
						width : "100"
					}, {
						name : "code",
						index : "code",
						width : "100"
					}, {
						name : "email",
						index : "email",
						width : "100"
					}, {
						name : "remark",
						index : "remark",
						width : "100"
					}, {
						name : "tenantName",
						index : "tenantName",
						width : "100", 
						sortable:false
					} ],
					rowNum : 10,
					rownumbers : true,
					rowList : [ 10, 20, 50, 100, 200 ],
					rowId : "uuid",
					pager : "#pager",
					sortname : "code",
					recordpos : "right",
					viewrecords : true,
					sortable : true,
					sortorder : "asc",
					multiselect : false,
					autowidth : true,
					height : '100%',
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
					},
					loadComplete : function(data) {
						$("#list").setSelection($("#list").getDataIDs()[0]);
					}
				}
			)
		);
		$("#selectCommonUnit").dialog({
			autoOpen: true,
			height: 400,
			width: 580,
			modal: true,
			buttons: {		      
			  "确定": function() {
				  var unitUuid = $("#list").jqGrid('getGridParam', 'selrow');
				  var unitName = $("#list").getRowData(unitUuid)["name"];
				  $("#unitUuid").val(unitUuid);
				  $("#unitName").val(unitName);
				  $(this).dialog( "close" );
			  },
			  "取消": function() {
				  $(this).dialog( "close" );
			  }
		    }
		});
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
	*/
	// 清空表单及成员列表数据
	function clear() {
		// 清空表单
		$(form_selector).clearForm(true);

	}
	
	$("#unitId").blur(function() {
		if ($("#unitId").val() != "") {
			validateId($("#unitUuid").val(), $("#unitId").val());
		}		
	});
	
	function validateId(uuid, unitId, isSave) {
		$.ajax({
			type:"get",
			async:false,
			url:ctx+"/superadmin/unit/commonUnitTree/validateId?uuid="+uuid+"&unitId="+unitId,
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
	
	function saveBean() {
		$("#unit_tree_form").form2json(bean);		
		//如果是分类，则不保存email和租户UUID
		if ($("#type").val() == "2") {
			bean.tenantId=null;
		}
		JDS.call({
			service : "commonUnitTreeService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 加载组织单元树结点
				loadUnitTree();
			}
		});
	}
	// 保存单位信息
	$("#btn_save").click(function() {
		if ($("#row_name #unitName").val() == '') {
			alert("请填写单位名称");
			return ;
		}	
		if ($("#row_tenant").css("display") != 'none') {
			//不是分类，判断是否有选择租户			
			if ($("#row_tenant #tenantId").val() == null || $("#row_tenant #tenantId").val() == '') {
				alert("请选择租户");
				return ;
			}
		}	
		if ($("#row_id").css("display") != 'none') {
			if ($("#unitId").val() == "") {
				alert("请填写ID");
			}
			if ($("#unitId").val() != "") {
				validateId($("#unitUuid").val(), $("#unitId").val(), true);
			}	
		} else {
			saveBean();
		}
		
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (latestSelectedNode == null || latestSelectedNode.id == -1) {
			alert("请选择节点！");
			return;
		}
		var name = latestSelectedNode.name;
		var uuid = latestSelectedNode.id;
		if (confirm("确定要删除[" + name + "]吗？")) {
			JDS.call({
				service : "commonUnitTreeService.remove",
				data : [ uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadUnitTree();
					// 清空表单等
					clear();
				}
			});
		}
	});
	

	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=unit_member_list]')) {
			grid.each(function(index) {
				var tabmargin = 60;
				if (Browser.isIE()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isChrome()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else if (Browser.isMozila()) {
					$(this).setGridWidth(pane.width() - tabmargin);
				} else {
					$(this).setGridWidth(pane.width() - tabmargin);
				}
			});
		}
	}
	// 页面布局
	Layout.layout({
		west__size : 250,
		center_list_id : "unit_member_list",
		center_onresize : resizeJqGrid
	});
});