$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"sortOrder" : null,
		"code" : null,
		"id" : null,
		"startRow" : null,
		"entity" : null,
		"entityName" : null,
		"type" : null,
		"fileUuid":null,
		"excelColumnDefinitions" : [],
		"changeColumnDefinitions" : [],
		"deletedExcelRows" : [],
	};
	
	$("#type").change(function(){
		var type = $(this).find("option:selected").val();
		if(type=="entity"){
			$.ajax({
				type : "POST",
				url : ctx + "/basicdata/excelimportrule/getSelectEntity",
				contentType : "application/json",
				dataType : "json",
				success : function(result) {
					var rs = result.data;
					var option = "";
					for(var c in rs){
						option += "<option value='" + rs[c].name + "' v='" + rs[c].id + "'>" + rs[c].cName
						+ "</option>";
					}
					$("#entity").parent().prev().find("label").text("实体");
					$("#entity").html(option);
				},
				error : function(result) {
				}
			});
		}else if(type=="formdefinition"){
			$.ajax({
				type : "POST",
				url : ctx + "/basicdata/excelimportrule/getSelectForm",
				contentType : "application/json",
				dataType : "json",
				success : function(result) {
					var rs = result.data;
					var option = "";
					for(var c in rs){
						option += "<option value='" + rs[c].name + "' v='" + rs[c].id + "'>" + rs[c].cName
						+ "</option>";
					}
					$("#entity").parent().prev().find("label").text("动态表单");
					$("#entity").html(option);
				},
				error : function(result) {
				}
			});
		}
	});
	// 收集改变的列定义
	function collectChangeColumnDefinitions(bean) {
		var changes = $("#excel_list").jqGrid("getRowData");
		for(var i=0;i<changes.length;i++){
			changes[i].id = i;
		}
		bean["excelColumnDefinitions"] = [];
		bean["changeColumnDefinitions"] = changes;
	}
	// 设置列定义表
	function toColumnDefinitionList(bean) {
		var $columnDefinitionList = $("#excel_list");
		//清空子表列表
		$columnDefinitionList.jqGrid("clearGridData");
		var excelColumnDefinitions = bean["excelColumnDefinitions"];
		for ( var index = 0; index < excelColumnDefinitions.length; index++) {
			$columnDefinitionList.jqGrid("addRowData", excelColumnDefinitions[index].uuid,
					excelColumnDefinitions[index]);
		}
	}
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=excelImportRule',
			datatype : "json",
			mtype : "POST",
			colNames : [ "uuid", "名称", "编号", "ID", "返回类型","对象名" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "30",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "30"
			}, {
				name : "code",
				index : "code",
				width : "20"
			}, {
				name : "id",
				index : "id",
				width : "30"
			}, {
				name : "type",
				index : "type",
				width : "30",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue == "entity") {
						return '实体';
					} else if (cellvalue == "formdefinition") {
						return '动态表单';
					}
					return cellvalue;
				}
			}, {
				name : "entityName",
				index : "entityName",
				width : "30"
			}],
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "id",
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
				getExcelImportRuleById(id);
	//			var rowData = $(this).getRowData(id);
	//			getExcelImportRuleById(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));
	
	// 根据用户uuid获取Excel导入规则定义信息
	function getExcelImportRuleById(uuid) {
		var excelimportrule = {};
		excelimportrule.uuid = uuid;
		JDS.call({
			service : "excelImportRuleService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				//绑定实体或动态表单下拉
				if(bean.type=="entity"){
					$.ajax({
						type : "POST",
						url : ctx + "/basicdata/excelimportrule/getSelectEntity",
						contentType : "application/json",
						dataType : "json",
						success : function(result) {
							var rs = result.data;
							var option = "";
							for(var c in rs){
								option += "<option value='" + rs[c].name + "' v='" + rs[c].id + "'>" + rs[c].cName
								+ "</option>";
							}
							$("#entity").parent().prev().find("label").text("实体");
							$("#entity").html(option);
						},
						error : function(result) {
						}
					});
				}else if(bean.type=="formdefinition"){
					$.ajax({
						type : "POST",
						url : ctx + "/basicdata/excelimportrule/getSelectForm",
						contentType : "application/json",
						dataType : "json",
						success : function(result) {
							var rs = result.data;
							var option = "";
							for(var c in rs){
								option += "<option value='" + rs[c].name + "' v='" + rs[c].id + "'>" + rs[c].cName
								+ "</option>";
							}
							$("#entity").parent().prev().find("label").text("动态表单");
							$("#entity").html(option);
						},
						error : function(result) {
						}
					});
				}
				//延迟绑定表单，保证下拉先加载完
				setTimeout(function(){$("#excel_import_rule_form").json2form(bean);},300); 
				$(".fileName").text(bean.name+".xls");
				$(".trfileName").hide();
				if(bean.fileUuid!=""&&bean.fileUuid!=null){
					$(".trfileName").show();
				}
				//根据主表uuid获取子表的信息
				toColumnDefinitionList(bean);
			}
		});
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	// 新增操作（excel行）
	$("#excel_btn_add").click(function() {
		var records = $("#excel_list").getGridParam('records');
		$("#excel_list").jqGrid("addRowData",(records+1),{"uuid":"","第几列":"","属性名":""},"");
	});
	
	// 删除操作（excel行）
	$("#excel_btn_del").click(function() {
		var rowids = $("#excel_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			
			var rowData = $("#excel_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedExcelRows"].push(rowData);
			}
			$("#excel_list").jqGrid('delRowData', rowids[i]);
		}
	});
	
	// 新增操作
	$("#btn_add").click(function() {
		//默认实体的选择项
		$.ajax({
			type : "POST",
			url : ctx + "/basicdata/excelimportrule/getSelectEntity",
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				var rs = result.data;
				var option = "";
				for(var c in rs){
					option += "<option value='" + rs[c].name + "' v='" + rs[c].id + "'>" + rs[c].cName
					+ "</option>";
				}
				$("#entity").parent().prev().find("label").text("实体");
				$("#entity").html(option);
			},
			error : function(result) {
			}
		});
		
		clear();
		$(".trfileName").hide();
		$("#excel_list").jqGrid("clearGridData");
		document.getElementById("excel_import_rule_form").reset();
		hideDelButton();
	});
	function clear() {
		$("#excel_import_rule_form").clearForm(true);
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
		if (confirm("确定要删除Excel导入规则[" + name + "]吗？")) {
			JDS.call({
				service : "excelImportRuleService.remove",
				data : [ bean.uuid ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源？")) {
			JDS.call({
				service : "excelImportRuleService.removeAll",
				data : [ rowids ],
				success : function() {
					clear();
					// 删除成功刷新列表
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
				"query_LIKES_code_OR_name_OR_id_OR_type_OR_entityName" : queryValue,
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
	// 保存群组信息
	$("#btn_save").click(function() {
		var fileId = "";
		//上传文件
		$.ajaxFileUpload({
            url:contextPath + '/basicdata/excelimportrule/uploadExcel',//链接到服务器的地址
            secureuri:false,
            fileElementId:'upload',//文件选择框的ID属性
            dataType: 'text',  //服务器返回的数据格式
            success: function (data, status){
	           	$("#fileUuid").val(data);
	           	$(".trfileName").hide();
	           	// 表单数据收集到对象中
	     		$("#excel_import_rule_form").form2json(bean);
	     		bean.entityName = $("#entity option:selected").text();
	     		// 收集改变的列定义
	     		collectChangeColumnDefinitions(bean);
	     		JDS.call({
	     			service : "excelImportRuleService.saveBean",
	     			data : [ bean ],
	     			success : function(result) {
	     				//保存成功刷新列表
	     				$("#list").trigger("reloadGrid");
	     				$("#excel_list").trigger("reloadGrid");
	     				showDelButton();
	     				alert("保存成功！");
	     			}
	     		});
            },
            error: function (data, status, e){  
           	 $.jBox.info(dymsg.fileUploadFailure,dymsg.tipTitle);//弹出对话框
            }
		});
	});
	
	//上传按钮
	$("#uploadBtn").click(function (){
		$.ajaxFileUpload({
             url:contextPath + '/basicdata/excelimportrule/uploadExcel',//链接到服务器的地址
             secureuri:false,
             fileElementId:'upload',//文件选择框的ID属性
             dataType: 'text',  //服务器返回的数据格式
             success: function (data, status){
            	 $("#fileUuid").val(data);
            	 $(".trfileName").hide();
             },
             error: function (data, status, e){  
            	 $.jBox.info(dymsg.fileUploadFailure,dymsg.tipTitle);//弹出对话框
             }
		});
	});
	
	//下载文件
	$(".fileName").click(function (){
		if($('#fileUuid').val()==""){
			alert("请上传模版");
		}else{
			location.href=contextPath+'/basicdata/excelimportrule/downloadImage?uuid='+$('#uuid').val();
		}
	});
	//列对应表
	$("#excel_list").jqGrid({
		datatype : "local",
		mtype : "GET",
		colNames : [ "uuid", "第几列", "属性名"],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "250",
			hidden : true
		}, {
			name : "columnNum",
			index : "columnNum",
			width : "250",
			editable : true,
			
		}, {
			name : "attributeName",
			index : "attributeName",
			width : "250",
			editable : true,
		} ],
/*		rowList : [ 10, 20, 50, 100, 200 ],
		rowId : "uuid",
//		pager : "#pager",
		sortname : "name",
		recordpos : "right",
		viewrecords : true,
		sortorder : "asc",
		multiselect : false,
		autowidth : true,
		height : "auto",*/
		multiselect : true,
		autowidth : true,
		scrollOffset : 0,
		jsonReader : {
			root : "dataList",
			total : "totalPages",
			page : "currentPage",
			records : "totalRows",
			repeatitems : false
		},
		sortable : false,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			afterEditCellParams(rowid,cellname,value,iRow,iCol);
			$("#" + iRow + "_" + cellname, "#excel_list").one('blur',
					function() {
						$('#excel_list').saveCell(iRow, iCol);
					});
		}
	});
	
	function getColumn(){
		if($("#entity option:selected").attr("v")==null||$("#entity option:selected").attr("v")==""){
			return "";
		}
		var uuid = {"uuid":$("#entity option:selected").attr("v")};
		var str="";
		var type = $("#type").find("option:selected").val();
		if(type=="entity"){
			$.ajax({
				type : "POST",
				async:false,
				url : ctx + "/basicdata/excelimportrule/getSelectEntityColumn",
				data : uuid,
				success : function(result) {
					var rs = result.data;
					for(var c in rs){
						str+=";"+rs[c].columnName+":"+rs[c].columnName;
					}
					str=str.substring(1, str.length);
				}
			});
		}else if(type=="formdefinition"){
			$.ajax({
				type : "POST",
				async:false,
				url : ctx + "/basicdata/excelimportrule/getSelectFormColumn",
				data : uuid,
				success : function(result) {
					var rs = result.data;
					for(var c in rs){
						str+=";"+rs[c].columnName+":"+rs[c].columnName;
					}
					str=str.substring(1, str.length);
				}
			});
		}
		
		return str;
	}
	function afterEditCellParams(rowid,cellname,value,iRow,iCol){
		if(cellname=="attributeName"){
			var optionStr="";
			var cellId = iRow + "_" + cellname;
			var temp = getColumn();
			var t1 = temp.split(";");
			for(var i=0;i<t1.length;i++){
				var t2 = t1[i].split(":");
				optionStr+="<option value='"+t2[1]+"'>"+t2[1]+"</option>";
			}
			$("#columnValue").dialog({
				autoOpen: true,
				height: 350,
				width: 650,
				modal: true,
				open:function() {
					$("#setColumnValue_basic").html(optionStr);
				},
				buttons: {
			      "取消": function() {
			    	  $( this ).dialog( "close" );
			       },
				  "确定": function() {
					$("#excel_list").setRowData(rowid,{attributeName:$("#setColumnValue_basic option:selected").val()});
					$('#excel_list').saveCell(iRow, iCol);
					$( this ).dialog( "close" );
				  }
			    }
			});
			
		}
	}
	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=excel_list]')) {
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
	//页面布局
	Layout.layout({
		west__size :450,
		center_list_id : "excel_list",
		center_onresize : resizeJqGrid
	});
});
