$(function() {
	
	
	var bean = {
		"uuid" : null,
		"type" : null,
		"name" : null,
		"id" : null,
		"code" : null,
		"templateType" : null,
		"printInterval" : null,
		"rowNumber" : null,
		"isSaveTrace" : null,
		"isReadOnly" : null,
		"isSaveSource" : null,
		"fileNameFormat" : null,
		"isSavePrintRecord" : null,
		"keyWords":null
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			mtype : 'POST',
			url : ctx + '/common/jqgrid/query?queryType=printTemplate',
			datatype : "json",
			colNames : [ "uuid", "编号", "名称", "ID","分类" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
			}, {
				name : "code",
				index : "code",
				width : "60"
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
				isPrintIntervalMultiLineChecked();
				isSaveSourceChecked();
				
				var rowData = $(this).getRowData(id);
				getPrintTemplateById(rowData.uuid);
			},
			loadComplete : function(data) {
				isPrintIntervalMultiLineChecked();
				isSaveSourceChecked();
				
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据用户uuid获取打印模板定义信息
	function getPrintTemplateById(uuid) {
		var printtemplate = {};
		printtemplate.uuid = uuid;
		JDS.call({
			service : "printTemplateService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				$("#print_template_form").json2form(bean);
				isPrintIntervalMultiLineChecked();
				isSaveSourceChecked();
				if(result.data.templateType =="htmlType"){
					$(".fileName").text(result.data.name+".html");
				}else if(result.data.templateType =="wordType"){
					$(".fileName").text(result.data.name+".doc");
				}
				$(".trfileName").hide();
				if(result.data.fileUuid!=""&&result.data.fileUuid!=null){
					$(".trfileName").show();
				}
            	 
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
		$("#fileName").hide();  
        $("#printInterval").hide();  
		clear();
		hideDelButton();
		document.getElementById("print_template_form").reset();
	});
	function clear() {
		$("#print_template_form").clearForm(true);
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
		if (confirm("确定要删除打印模板[" + name + "]吗？")) {
			JDS.call({
				service : "printTemplateService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	
	// 选择打印模板使用人
	$("#ownerNames").click(function() {
		$.unit.open({
			title : "选择人员",
			labelField : "ownerNames",
			valueField : "ownerIds"
		});
	});
	
	$(function () {
		$("input[name='isSaveSource']").click(function () {
			isSaveSourceChecked();
		});
	});
	//加载时判断文件名格式是否被选中
	function isSaveSourceChecked() {
		//如果是选中复选框
		if ($("input[name='isSaveSource']").attr("checked")) {
	        $("#fileName").show();  
	    }  else {
			//如果是取消选中
	    	$("#fileName").hide();  
		}
	}
	
	$(function () {
		$("input[name='printInterval']").click(function () {
			isPrintIntervalMultiLineChecked();
		});
	});
	//加载时判断文件名格式是否被选中
	function isPrintIntervalMultiLineChecked() {
		//如果是选中多行选项
		if ($("input[name='printInterval']").get(1).checked) {
			$("#printInterval").show();  
		}  else {
			//如果是取消选中
			$("#printInterval").hide();  
		}
	};
	
	$("#btn_save").click(function(){
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#print_template_form").form2json(bean);
		
		JDS.call({
			service : "printTemplateService.saveBean",
			data : [ bean ],
			validate : true,
			success : function(result) {
	    		$.ajaxFileUpload({
	    			 url:ctx + '/basicdata/printtemplate/uploadFile.action',//链接到服务器的地址
	                 secureuri:false,
	                 fileElementId:'uploadFile',//文件选择框的ID属性
	                 dataType: 'text',  //服务器返回的数据格式
	                 data: {//加入的文本参数  
	                     "uuid": result.data.uuid
	                 },  
	                 success: function (data1, status){
	                	 alert("保存成功！");
	                 }
	    		});
	    		// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				showDelButton();
			}
		});
	});
	
	//下载打印模板定义
	 $("#btn_download").click(function(){
		 var uuid = bean.uuid;
		 $("#downloaduuid").val(uuid);
		 $("#print_form").submit();
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
				service:"printTemplateService.deleteAllById",
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
				"query_EQS_type" : queryOther,
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