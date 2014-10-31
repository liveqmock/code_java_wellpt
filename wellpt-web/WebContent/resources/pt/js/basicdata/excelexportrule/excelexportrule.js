$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"id" : null,
		"startRow" : null,
		"viewUuid":null,
		"viewName":null,
		"fileUuid":null,
		"excelExportColumnDefinition" : [],
		"changeColumnDefinitions" : [],
		"deletedExcelRows" : [],
	};
	
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "viewDefinitionService",
					"methodName" : "getViewAsTreeAsync",
				}
			},
			check : {
				enable : true,
				chkStyle : "radio"
			},
	};
	
	//来源视图的
	$("#viewName").comboTree({
		labelField: "viewName",
		valueField: "viewUuid",
		treeSetting : setting,
		width: 250,
		height: 220
	});
	
	// 收集改变的列定义
	function collectChangeColumnDefinitions(bean) {
		var changes = $("#excel_list").jqGrid("getRowData");
		for(var i=0;i<changes.length;i++){
			changes[i].id = i;
		}
		bean["excelExportColumnDefinition"] = [];
		bean["changeColumnDefinitions"] = changes;
	}
	// 设置列定义表
	function toColumnDefinitionList(bean) {
		var $columnDefinitionList = $("#excel_list");
		//清空子表列表
		$columnDefinitionList.jqGrid("clearGridData");
		var excelExportColumnDefinitions = bean["excelExportColumnDefinition"];
		for ( var index = 0; index < excelExportColumnDefinitions.length; index++) {
			$columnDefinitionList.jqGrid("addRowData", excelExportColumnDefinitions[index].uuid,
					excelExportColumnDefinitions[index]);
		}
	}
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=excelExportDefinition',
			datatype : "json",
			mtype : "POST",
			colNames : [ "uuid", "名称", "编号", "ID", "开始行"],
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
				name : "startRow",
				index : "startRow",
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
				getExcelExportRuleById(id);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));
	
	// 根据用户uuid获取Excel导出规则定义信息
	function getExcelExportRuleById(uuid) {
		var excelimportrule = {};
		excelimportrule.uuid = uuid;
		JDS.call({
			service : "excelExportRuleService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				//绑定选中视图
				var viewName = bean.viewName;
				var viewUuid = bean.viewUuid;
				$("#viewName").val(viewName);
				$("#viewUuid").val(viewUuid);
				$("#excel_export_rule_form").json2form(bean);
				
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
		clear();
		$(".trfileName").hide();
		$("#excel_list").jqGrid("clearGridData");
		document.getElementById("excel_export_rule_form").reset();
		hideDelButton();
	});
	function clear() {
		$("#excel_export_rule_form").clearForm(true);
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
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_code_OR_name_OR_id_OR_startRow" : queryValue,
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
            url:contextPath + '/basicdata/excelexportrule/uploadExcel',//链接到服务器的地址
            secureuri:false,
            fileElementId:'upload',//文件选择框的ID属性
            dataType: 'text',  //服务器返回的数据格式
            success: function (data, status){
	           	$("#fileUuid").val(data);
	           	$(".trfileName").hide();
	           	// 表单数据收集到对象中
	     		$("#excel_export_rule_form").form2json(bean);
	     		// 收集改变的列定义
	     		collectChangeColumnDefinitions(bean);
	     		JDS.call({
	     			service : "excelExportRuleService.saveBean",
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
             url:contextPath + '/basicdata/excelexportrule/uploadExcel',//链接到服务器的地址
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
			location.href=contextPath+'/basicdata/excelexportrule/downloadImage?uuid='+$('#uuid').val();
		}
	});
	//列对应表
	$("#excel_list").jqGrid({
		datatype : "local",
		mtype : "GET",
		colNames : [ "uuid", "第几列", "显示名","字段名",'列数据类型','列所属的类','列别名',"列值的类型"],
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
			
		},{
			name : "titleName",
			index : "titleName",
			hidden : true
		},
		{
			name : "attributeName",
			index : "attributeName",
			width : "250",
			editable : true,
		},
		 {   name:'columnDataType',
	           index:'columnDataType',
	           hidden:true
        },
        {   name:'entityName',
	           index:'entityName',
	           hidden:true
        }, 
        {   name:'columnAliase',
	           index:'columnAliase',
	           hidden:true
        }, 
		{
			name:"valueType",
			index:"valueType",
			hidden:true
		} 
		],
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
	
	function afterEditCellParams(rowid,cellname,value,iRow,iCol){
		if(cellname=="attributeName"){
			var optionStr="";
			var cellId = iRow + "_" + cellname;
			$("#" + cellId).focus(function() {
				var columnValue = $("#setColumnValue_basic");
				columnValue.empty();
				var Data = null;
				var viewUuid = $("#viewUuid").val();	
				//根据视图的uuid 获取视图的定义
				JDS.call({
					service : "viewDefinitionService.getBeanByUuid",
					data : [ viewUuid ],
					success : function(result) {
						var dataScope = result.data.dataScope;
						var formuuid = result.data.formuuid;
						if(dataScope == 1) {//数据源来源动态表单
							//同步调用获取字段信息
		    	 			JDS.call({
		    	 				async:false,
		    	 				service:"getViewDataService.getFieldByForm",
		    	 				data:[formuuid],
		    	 				success:function(result) {
		    	 					data = result.data;
		    	 					Data = data;//获取回来的字段信息
		    	 				}
		    	 			});
		    	 			for (var i=0;i<Data.length;i++) {
		    	 				var option = $("<option>").text(Data[i].descname).val(Data[i].fieldName);
		    	 				columnValue.append(option);
		    	 			}
						}else if(dataScope == 2) {//数据源来源实体类
							//同步调用获取字段信息
	        	 			JDS.call({
	        	 				async:false,
	        	 				service:"getViewDataService.getSystemTableColumns",
	        	 				data:[formuuid],
	        	 				success:function(result) {
	        	 					data = result.data;
	        	 					Data = data;//获取回来的字段信息
	        	 				}
	        	 			});
	        	 			for (var i=0;i<Data.length;i++) {
	        	 				var option = $("<option>").text(Data[i].chineseName).val(Data[i].attributeName).attr("entityName",Data[i].entityName).attr("columnAliase",Data[i].columnAliases).attr("columnDataType",Data[i].columnType);
	        	 				columnValue.append(option);
	        	 			}
						}else if(dataScope == 3) {//数据源来源模块数据
							JDS.call({
	        	 				async:false,
	        	 				service:"getViewDataService.getViewColumns",
	        	 				data:[formuuid],
	        	 				success:function(result) {
	        	 					data = result.data;
	        	 					Data = data;//获取回来的字段信息
	        	 				}
	        	 			});
        	 				for (var i=0;i<Data.length;i++) {
	        	 				var option = $("<option>").text(Data[i].columnName).val(Data[i].columnAlias).attr("columnAliase",Data[i].columnAlias).attr("columnDataType",Data[i].columnType);
	        	 				columnValue.append(option);
	        	 			}
						}
					}
				});
			
			$("#columnValue").dialog({
				autoOpen: true,
				height: 350,
				width: 650,
				modal: true,
				open:function() {
					var valueType = $('#excel_list').getCell(rowid, "valueType");
					var a = $("#" + cellId).val();
					if(valueType == "1") {
						$("#columnBasic").attr("checked",true);
						$("#columnSenior").attr("checked",false);
						$("#columnValue_basic").css("display","");
						$("#columnValue_senior").css("display","none");
						$("#setColumnValue_basic").find("option").each(function(){
							if($(this).text()==a){
								$(this).attr("selected",true);
							}
						});
					}else if(valueType == "2") {
						$("#columnSenior").attr("checked",true);
						$("#columnBasic").attr("checked",false);
						$("#columnValue_senior").css("display","");
						$("#columnValue_basic").css("display","none");
						$("#setColumnValue_senior").val(a);
					}
					$("#columnBasic").click(function() {
						if($("#columnBasic").prop("checked") == true ) {
							$("#columnSenior").attr("checked",false);
        	 				$("#columnValue_basic").css("display","");
        	 				$("#columnValue_senior").css("display","none");
        	 				
        	 			}else if($("#columnBasic").prop("checked") == false){
        	 				$("#columnValue_basic").css("display","none");
        	 			}
					});
					
					$("#columnSenior").click(function() {
						if($("#columnSenior").prop("checked") == true ) {
							$("#columnBasic").attr("checked",false);
        	 				$("#columnValue_senior").css("display","");
        	 				$("#columnValue_basic").css("display","none");
        	 				
        	 			}else if($("#columnSenior").prop("checked") == false){
        	 				$("#columnValue_senior").css("display","none");
        	 			}
					});
				},
				buttons: {
			      "取消": function() {
			    	  $( this ).dialog( "close" );
			       },
				  "确定": function() {
					  	var a = null;
					  	var b = null;
					  	var c = null;
					if($("#columnBasic").prop("checked") == true) {
						a = columnValue.find("option:selected").attr("entityName");
						b = columnValue.find("option:selected").attr("columnAliase");
						c = columnValue.find("option:selected").attr("columnDataType");
						alert(a+","+b+","+c);
						$("#excel_list").setRowData(rowid,{attributeName:$("#setColumnValue_basic option:selected").val()});
						$("#excel_list").setRowData(rowid,{titleName:$("#setColumnValue_basic option:selected").text()});
						$("#excel_list").setRowData(rowid,{entityName:a});
						$("#excel_list").setRowData(rowid,{columnAliase:b});
						$("#excel_list").setRowData(rowid,{columnDataType:c});
						$("#excel_list").setRowData(rowid,{valueType:"1"});
					}else if($("#columnSenior").prop("checked") == true) {
						//高级列值设置
						$("#excel_list").setRowData(rowid,{attributeName:$("#setColumnValue_basic option:selected").val()});
						$("#excel_list").setRowData(rowid,{titleName:$("#setColumnValue_basic option:selected").text()});
						$("#excel_list").setRowData(rowid,{valueType:"2"});
					}
					$('#excel_list').saveCell(iRow, iCol);
					$( this ).dialog( "close" );
				  }
			    }
			});
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
