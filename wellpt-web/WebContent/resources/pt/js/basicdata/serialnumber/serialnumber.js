$(function() {
	
	
	var bean = {
		"uuid" : null,
		"type" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"keyPart" : null,
		"headPart" : null,
		"initialValue" : null,
		"isFillPosition" : null,
		"incremental" : null,
		"lastPart" : null,
		"isFillNumber" : null,
		"startDate" : null,
		"isEditor" : null,
		"remark" : null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=serialNumber',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "编号", "名称", "ID", "分类", "需要补号", "可编辑" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
			}, {
				name : "code",
				index : "code",
				width : "70"
			}, {
				name : "name",
				index : "name",
				width : "180"
			}, {
				name : "id",
				index : "id",
				width : "180"
			}, {
				name : "type",
				index : "type",
				width : "180"
			}, {
				name : "isFillNumber",
				index : "isFillNumber",
				width : "180",
				editable : true,
				edittype : "checkbox",
				editoptions : {
					value : "true:false"
				},
				formatter : "checkbox"
			}, {
				name : "isEditor",
				index : "isEditor",
				width : "180",
				editable : true,
				edittype : "checkbox",
				editoptions : {
					value : "true:false"
				},
				formatter : "checkbox"
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
				getSerialNumberById(rowData.uuid);
			},
			loadComplete : function(data) {
				isFillUser();
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据用户uuid获取流水号定义信息
	function getSerialNumberById(uuid) {
		var serialnumber = {};
		serialnumber.uuid = uuid;
		JDS.call({
			service : "serialNumberService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
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
		hideDelButton();
	});
	function clear() {
		$("#serial_number_form").clearForm(true);
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


	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除流水号定义[" + name + "]吗？")) {
			JDS.call({
				service : "serialNumberService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	
	// 选择流水号定义使用人
	$("#ownerNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "ownerNames",
			valueField : "ownerIds"
		});
	});
	
	$(function () {
		$("input[name='isEditor']").click(function () {
			isFillUser();
		});
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
	// 保存用户信息,提交前必须验证
	$(".serial_number_form").Validform({//指定具体参数，实现更多功能;
		btnSubmit:"#btn_save",
//		tiptype:2,
		tiptype:function(msg,o,cssctl){
			//msg：提示信息;
			//o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			//cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.parents("td").find(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
				
				var infoObj=o.obj.parents("td").find(".info");
				if(o.type==2){
					infoObj.fadeOut(200);
				}else{
					if(infoObj.is(":visible")){return;}
					infoObj.css({
					}).show().animate({
					},200);
				}
				
			}	
		},
		postonce:true,
        callback:function(data){            
        	// 清空JSON
    		$.common.json.clearJson(bean);
    		// 收集表单数据
    		$("#serial_number_form").form2json(bean);
    		
    		JDS.call({
    			service : "serialNumberService.saveBean",
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
        }
	});
	
	//批量删除操作
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选项吗？")) {
			JDS.call({
				service:"serialNumberService.deleteAllById",
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
				"query_LIKES_code_OR_name_OR_id_OR_type" : queryValue,
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
		west__size :430
	});
});