$(function() {
	var bean = {
		"uuid": null,	
		"name": null,
		"id": null,	
		"code": null,
		"type" : null,
		"tableCnName" : null,
		"tableEnName" : null,
		"definitionUuid" : null,
		"joinTable" : null,
		"whereStr" : null,
		"method" : null,
		"isUserUse" : null,
		"tenant" : null,
		"isEnable" : null,
		"isRelationTable" : null,
		"relationTable" : null,
		"synchronousSourceFields":[]
	};
	var obj_ = new Object();
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "exchangeDataSynchronousService",
					"methodName" : "getSynchronousSourceTablesByType",
					"data":$("#type").val()
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: treeNodeOnClick,
			}
	};
	$("#tableCnName").comboTree({
		labelField: "tableCnName",
		valueField: "definitionUuid",
		treeSetting : setting,
		width: 220,
		height: 220
	});
	
	$("#isRelationTable").click(function(){
		if($(this).attr("checked")=="checked"){
			$("#column_list").jqGrid("showCol",["foreignKeyTable"]); 
		}else{
			$("#column_list").jqGrid("hideCol",["foreignKeyTable"]); 
		}
	});
	
	//select框值变化时候调用
	$("#type").change(function(e){
		var val = $(this).val();
		if(val=="other"){
			$(".tableEnNameClass").show();
			$("#column_list").jqGrid('setGridParam',{ 
				colNames:['字段名','列字段','数据类型','用户使用','对应外键表'],
				colModel:[{},
							{   	
								name:'fieldCnName',
								index:'fieldCnName',
								editable : true,
								edittype : "text",
								width:"200",
							},
				            {	   
							   name:'fieldEnName',
				        	   index:'fieldEnName',
				        	   editable : true,
				        	   width:"200",
				            },
				            {	   
							   name:'dataType',
				        	   index:'dataType',
				        	   editable : true,
				        	   width:"200",
					        },
				             {	   
								   name:'isUserUse',
					        	   index:'isUserUse',
					        	   width:"200",
								   editable : true,
								   edittype : "checkbox",
						           editoptions : {
						        	  value:"true:false"
						           },
						           formatter : "checkbox",
				             },
				             {	   
								   name:'foreignKeyTable',
					        	   index:'foreignKeyTable',
					        	   width:"200",
								   editable : true,
								   hidden : true,
				             },
				         ],
			}).trigger("reloadGrid"); //重新载入
		}else{
			$(".tableEnNameClass").hide();
			$("#tableCnName").comboTree("clear");
			$("#tableCnName").comboTree("disable");
			var setting = {
					async : {
						otherParam: {
							"data": val
						}
					}
			};
			$("#tableCnName").comboTree("enable");
			$("#tableCnName").comboTree("setParams", {treeSetting : setting});
		}
	});
		
	function treeNodeOnClick(event, treeId, treeNode) {
		$("#tableCnName").val(treeNode.name);
		$("#definitionUuid").val(treeNode.id);
		$("#tableEnName").val(treeNode.data);
		$("#column_list").jqGrid("clearGridData");
		var temp = getColumnsData($("#type").val(),$("#definitionUuid").val());
		$("#column_list").jqGrid('setGridParam',{ 
			colNames:['字段名','列字段','数据类型','用户使用','对应外键表'],
			colModel:[ {},
						 {   	
							   name:'fieldCnName',
							   index:'fieldCnName',
							   editable : true,
							   edittype : "select",
							   width:"200",
							   editoptions : {
								   value:temp
							   },
						 },
						 {	   
							   name:'fieldEnName',
							   index:'fieldEnName',
							   editable : true,
							   width:"200",
						 },
						 {	   
							   name:'dataType',
				        	   index:'dataType',
				        	   editable : true,
				        	   width:"200",
					     },
						 {	   
							   name:'isUserUse',
				        	   index:'isUserUse',
				        	   width:"200",
							   editable : true,
							   edittype : "checkbox",
					           editoptions : {
					        	  value:"true:false"
					           },
					           formatter : "checkbox",
				        },
				        {	   
					           name:'foreignKeyTable',
				        	   index:'foreignKeyTable',
				        	   width:"200",
							   editable : true,
							   hidden : true,
			             },
			         ],
		}).trigger("reloadGrid"); //重新载入
		var data_ = temp.split(";");
		for(var i=0;i<data_.length;i++) {
			var fieldCnName = data_[i].split(":")[1];
			var	fieldEnName = data_[i].split(":")[0];
			var	dataType = obj_[data_[i].split(":")[0]];
			$("#column_list").addRowData(i,{"fieldCnName":fieldCnName,"fieldEnName":fieldEnName,"isUserUse":true,"dataType":dataType},"first");
		}
		$("#tableCnName").comboTree("hide");	
	}
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=synchronousSourceTable",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "名称", "表名", "数据库表" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "40"
			},{
				name : "tableCnName",
				index : "tableCnName",
				width : "30"
			},{
				name : "tableEnName",
				index : "tableEnName",
				width : "30"
			} ],
			
			onSelectRow : function(id) {
				getDataById(id);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
	}));

	// 行选择
	function getDataById(uuid) {
		$("#data_form").clearForm(true);
		JDS.call({
			service : "exchangeDataSynchronousService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				$("#data_form").json2form(bean);
				$("#tableDefinitionText").comboTree("clear");
				$("#tableDefinitionText").comboTree("disable");
				var setting = {
						async : {
							otherParam: {
								"data": bean["type"],
							}
						}
				};
				$("#tableDefinitionText").comboTree("enable");
				$("#tableDefinitionText").comboTree("setParams", {treeSetting : setting});
				if(bean["type"]=="other"){
					$(".tableEnNameClass").show();
					//设置视图列的列表
					toColumnList(bean);
					$("#column_list").jqGrid('setGridParam',{ 
						colNames:['字段名','列字段','数据类型','用户使用','对应外键表'],
						colModel:[{},
									{   	
										name:'fieldCnName',
										index:'fieldCnName',
										edittype : "text",
										editable : true,
										width:"200",
									},
						            {	   
									   name:'fieldEnName',
						        	   index:'fieldEnName',
						        	   editable : true,
						        	   width:"200",
						            },
						            {	   
									   name:'dataType',
						        	   index:'dataType',
						        	   editable : true,
						        	   width:"200",
							        },
						             {	   
										   name:'isUserUse',
							        	   index:'isUserUse',
							        	   width:"200",
										   editable : true,
										   edittype : "checkbox",
								           editoptions : {
								        	  value:"true:false"
								           },
								           formatter : "checkbox",
						             },
						             {	   
								           name:'foreignKeyTable',
							        	   index:'foreignKeyTable',
							        	   width:"200",
										   editable : true,
										   hidden : true,
						             },
						         ],
					}).trigger("reloadGrid"); //重新载入
				}else{
					$(".tableEnNameClass").hide();
					//设置视图列的列表
					toColumnList(bean);
					$("#column_list").jqGrid('setGridParam',{ 
						colNames:['字段名','列字段','数据类型','用户使用','对应外键表'],
						colModel:[{},
									{   	
										name:'fieldCnName',
										   index:'fieldCnName',
										   editable : true,
							        	   edittype : "select",
							        	   editoptions : {
							        		   value:getColumnsData($("#type").val(),bean["definitionUuid"])
										   },
										   width:"200",
									},
						            {	   
									   name:'fieldEnName',
						        	   index:'fieldEnName',
						        	   editable : true,
						        	   width:"200",
						            },
						            {	   
									   name:'dataType',
						        	   index:'dataType',
						        	   editable : true,
						        	   width:"200",
							        },
						             {	   
										   name:'isUserUse',
							        	   index:'isUserUse',
							        	   width:"200",
										   editable : true,
										   edittype : "checkbox",
								           editoptions : {
								        	  value:"true:false"
								           },
								           formatter : "checkbox",
						             },
						             {	   
								           name:'foreignKeyTable',
							        	   index:'foreignKeyTable',
							        	   width:"200",
										   editable : true,
										   hidden : true,
						             },
						         ],
					}).trigger("reloadGrid"); //重新载入
				}
				if(bean.isRelationTable==true){
					$("#column_list").jqGrid("showCol",["foreignKeyTable"]); 
				}else{
					$("#column_list").jqGrid("hideCol",["foreignKeyTable"]); 
				}
			}
		});
		
	}
	//设置列
	function toColumnList(bean) {
		var $columnList = $("#column_list");
		$columnList.jqGrid("clearGridData");
		var columns = bean["synchronousSourceFields"];
		for( var index=0; index<columns.length;index++) {
			$columnList.jqGrid("addRowData",columns[index].uuid, columns[index]);
		}
	}
	//列的备选项
	function getColumnsData(type,definitionUuid){
		obj_ = new Object();
		var str = "";
		JDS.call({
			service:"exchangeDataSynchronousService.getColumnsData",
			data:[type,definitionUuid],
			async : false,
			success:function(result) {
				var data = result.data;
				for(var i=0;i<data.length;i++) {
					var ename = data[i]["fieldEnName"];
					var cname = data[i]["fieldCnName"];
					if(cname==undefined){
						str += ";" + ename + ":"+ ename;
					}else{
						str += ";" + ename + ":"+ cname;
					}
					obj_[ename] = data[i]["dataType"];
				}
				str = str.substring(1);
			}
		});
		return str;
	}
	$("#column_list").jqGrid({
		datatype : "local",
		colNames:['字段名','列字段','数据类型','用户使用','对应外键表'],
		colModel:[
					{   	
					   name:'fieldCnName',
					   index:'fieldCnName',
					   editable : true,
		        	   width:"200",
					},
		            {	   
					   name:'fieldEnName',
		        	   index:'fieldEnName',
		        	   editable : true,
		        	   width:"200",
		            },
		            {	   
						   name:'dataType',
			        	   index:'dataType',
			        	   editable : true,
			        	   width:"200",
				    },
		            {	   
						   name:'isUserUse',
			        	   index:'isUserUse',
			        	   width:"200",
						   editable : true,
						   edittype : "checkbox",
				           editoptions : {
				        	  value:"true:false"
				           },
				           formatter : "checkbox",
			        },
			        {	   
				           name:'foreignKeyTable',
			        	   index:'foreignKeyTable',
			        	   width:"200",
						   editable : true,
						   hidden : true,
		             },
		         ],
		         autoScroll: true,
		         scrollOffset : 0,
		         sortable : false,
		         rowNum : 100,
		         cellEdit : true,// 表示表格可编辑
		         cellsubmit : "clientArray", // 表示在本地进行修改
		         multiselect : true,
		         autowidth :true,
		         height:400,
		         afterEditCell : function(rowid, cellname, value, iRow, iCol) {
		        	 if(cellname=="fieldCnName"){
		        		 $("#" + iRow + "_" + cellname, "#column_list").find("option").one('click',function() {
		        			 $("#column_list").setCell(rowid,"fieldEnName",$(this).val());
				        		$("#column_list").setCell(rowid,"dataType",obj_[$(this).val()]);
								$('#column_list').saveCell(iRow, iCol);
							 });
		        	 }
		        	 
		        	 $("#" + iRow + "_" + cellname, "#column_list").one('blur',function(){
		        		 $('#column_list').saveCell(iRow, iCol);
		        	 });
		         }
	});
	
	// 新增
	$("#btn_add").click(function() {
		$("#data_form").clearForm(true);
		hideDelButton();
	});
	function clear() {
		$("#module_form").clearForm(true);
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

	// 新增列
	$("#btn_add_column").click(function() {
		var mydata = {
			isUserUse : true,
		};
		var newDate = new Date().getTime();
		$("#column_list").jqGrid("addRowData", newDate,mydata);
	});
	
	//删除列
	$("#btn_del_column").click(function() {
		var rowids = $("#column_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择列!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#column_list").getRowData(rowids[i]);
			if (rowData != null) {
				bean["synchronousSourceFields"].push(rowData);
			}
			$("#column_list").jqGrid('delRowData', rowids[i]);
		}
	});
	
	
	// 保存
	$("#btn_save").click(function() {
		$("#data_form").form2json(bean);
		collectColumn(bean);
		JDS.call({
			service : "exchangeDataSynchronousService.saveBean",
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
	});
	//收集列数据
 	function collectColumn(bean) {
		var change1 = $("#column_list").jqGrid("getRowData");
		//给收集到的数据设置id
		for(var i=0;i<change1.length;i++){
			change1[i].uuid = i;
		}
		bean["synchronousSourceFields"] = change1;
	}
	// 删除
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录！");
			return true;
		}
		var uuids = new Array();
		uuids[0] = bean.uuid;
		var name = bean.name;
		if (confirm("确定要删除页面元素[" + name + "]吗？")) {
			JDS.call({
				service : "exchangeDataSynchronousService.delSynchronousSourceTables",
				data : [ uuids ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除
	$("#btn_delAll").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		if (confirm("确定要删除所选资源？")) {
			JDS.call({
				service:"exchangeDataSynchronousService.delSynchronousSourceTables",
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
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_name_OR_tableCnName_OR_tableEnName" : queryValue,
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
	
	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable[id=column_list],.ui-jqgrid-btable[id=selectCond]')) {
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
		west__size :480,
		center_onresize : resizeJqGrid
	});
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();
});
