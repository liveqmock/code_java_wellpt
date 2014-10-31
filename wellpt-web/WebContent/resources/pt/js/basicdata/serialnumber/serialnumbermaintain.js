$(function() {
	var bean = {
		"uuid" : null,
		"id" : null,
		"code" : null,
		"name" : null,
		"keyPart" : null,
		"headPart" : null,
		"lastPart" : null,
		"pointer" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=serialNumberMaintain',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "编号", "名称","ID", "关键字", "头部", "尾部", "指针" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				key : true,
				hidden : true,
			}, {
				name : "code",
				index : "code",
				width : "80"
			}, {
				name : "name",
				index : "name",
				width : "180"
			}, {
				name : "id",
				index : "id",
				width : "180"
			}, {
				name : "keyPart",
				index : "keyPart",
				width : "180"
			}, {
				name : "headPart",
				index : "headPart",
				width : "180"
			}, {
				name : "lastPart",
				index : "lastPart",
				width : "180"
			}, {
				name : "pointer",
				index : "pointer",
				width : "180"
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
				isFillUser();
				var rowData = $(this).getRowData(id);
				getserialNumberMaintainById(rowData.uuid);
			},
			loadComplete : function(data) {
				isFillUser();
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据流水号uuid获取流水号维护信息
	function getserialNumberMaintainById(uuid) {
		var serialNumberMaintain = {};
		serialNumberMaintain.uuid = uuid;
		JDS.call({
			service : "serialNumberMaintainService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#serial_number_form").json2form(bean);
				isFillUser();
			}
		});
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		//点击新增时隐藏联系人
        $("#user").hide();  
		clear();
		$("#btn_del").hide();
		$("#btn_delAll");
	});
	function clear() {
		$("#serial_number_form").clearForm(true);
		// 清空JSON
		$.common.json.clearJson(bean);
	}

	// 保存用户信息
	$("#btn_save").click(function() {
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#serial_number_form").form2json(bean);
		JDS.call({
			service : "serialNumberMaintainService.saveBean",
			data : [ bean ],
			validate : true,
			success : function(result) {
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				alert("保存成功！");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除流水号维护[" + name + "]吗？")) {
			JDS.call({
				service : "serialNumberMaintainService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	//加载时判断使用人是否被选中
	function isFillUser() {
		//如果是选中复选框
		if ($("input[name='isEditor']").attr("checked")) {
	        $("#user").show();  
	    }  else {
			//如果是取消选中
	    	$("#user").hide();  
		}
	}
	// 选择流水号维护使用人
	$("#ownerNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "ownerNames",
			valueField : "ownerIds"
		});
	});
	
	$(function () {
		$("input[name='category']").click(function () {
			isFillUser();
		});
	});
	
	//加载时判断可编辑框是否被选中
	 $("#iseditor").focus(function(){  
        $(".hide").show();  
     });
	 
	 //获取所有可编辑的流水号
	$("#iseditor").focus(function(){
		JDS.call({
			service : "serialNumberMaintainService.getByIsEditor",
				data : [ true ],
				success : function(result) {
//					alert(JSON.stringify(result.data));
					$.each(result.data, function(i) {
//						alert(result.data[i].headPart+result.data[i].pointer+result.data[i].lastPart);
						$("#serialName").append("<option value\"" + result.data[i].uuid + "\">" + result.data[i].name + "</option>/n/r");
						$("#serialName").change(function(){
							$("#serialNum").val(result.data[i].headPart+result.data[i].pointer+result.data[i].lastPart);
						});
		            });
				},
				error : function(result){}
			});
		});
	
	//提交可编辑流水号处理
	$("#btn_submit").click(function(){
		//将流水号加到可编辑框里
		var value = $("#iseditor").val($("#serialNum").val()); 
		/*var uuid = $('#serialName').val();
		JDS.call({
			service : "serialNumberMaintainService.savePointer",
				data : [ value,uuid ],
				success : function(result) {
					
				},
				error : function(result){}
			});
		});*/
	});
	
	//批量删除操作
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源吗？")) {
			JDS.call({
				service:"serialNumberMaintainService.deleteAllById",
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
		var queryOther = "";
		if(queryValue.indexOf("实")>-1||queryValue.indexOf("体")>-1){
			queryOther = "entity";
		}else if(queryValue.indexOf("动")>-1||queryValue.indexOf("态")>-1||queryValue.indexOf("表")>-1||queryValue.indexOf("单")>-1){
			queryOther = "formdefinition";
		}
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_code_OR_name_OR_id_OR_keyPart_OR_headPart_OR_lastPart_OR_pointer" : queryValue,
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
		west__size :450
	});
});

	
