$(function(){
	
	// 最新选择的树结点
	var fieldData = null; 
	var bean = {
			"uuid":null,
			"dataSourceNum":null,
			"dataSourceId":null,
			"dataSourceName":null,
			"dataSourceTypeName":null,
			"dataSourceTypeId":null,
			"inDataScope":null,
			"outDataScope":null,
			"chooseDataText":null,
			"chooseDataId":null,
			"sqlOrHqlText":null,
			"aclHqlDataText":null,
			"searchCondition":null,
			"outDataSourceName":null,
			"outDataSourceId":null,
			"dataInterfaceName":null,
			"dataInterfaceId":null,
			"roleType":null,
			"roleName":null,
			"roleValue":null
	};
	
	var dataSourceColumnBean = {
			"titleName":null,
			"columnName":null,
			"fieldName":null,
			"columnDataType":null,
			"entityName":null,
			"columnAliase":null,
			"isExport":null,
			"defaultSort":null
	}
	
	var setting2 = {
			async : {
				otherParam : {
					"serviceName" : "dataDictionaryService",
					"methodName" : "getViewTypeAsTreeAsync",
					"data" : "DATA_PERMISSION"
				}
			},
			callback : {
				onCheck: treeNodeOnClickForRole,
				onClick:treeNodeOnClickForRole
			}
		};
	
	function treeNodeOnClickForRole(event, treeId, treeNode) { 
		bean["roleValue"] = treeNode.attribute;
		bean["roleType"] = treeNode.data;
		if (treeNode.isParent) {
				$("#roleName").val("");
				$("#roleType").val("");
		}
		// 设置值
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var checkNodes = zTree.getCheckedNodes(true);
		var path = "";
		var value = "";
		for ( var index = 0; index < checkNodes.length; index++) {
			var checkNode = checkNodes[index];
				if (path == "") {
					path = getAbsolutePath(checkNode);
				} else {
					path = path + ";" + getAbsolutePath(checkNode);
				}
				if (value == "") {
					value = checkNode.data;
				} else {
					value = value + ";" + checkNode.data;
				}
		}
		$("#roleName").val(path);
		$("#roleType").val(value);
	}
	
	// 获取树结点的绝对路径
	function getAbsolutePath(treeNode) {
		var path = treeNode.name;
		var parentNode = treeNode.getParentNode();
		while (parentNode != null) {
			path = parentNode.name + "/" + path;
			parentNode = parentNode.getParentNode();
		}
		return path;
	}
	
	
	//角色的展示树
	$("#roleName").comboTree({
		labelField: "roleName",
		valueField: "roleType",
		treeSetting : setting2,
		width: 220,
		height: 220
	});
	
	//当类型select框值变化时候调用
	$("#dataSourceTypeId").change(function(e){
		var val = $(this).val();
		if(val == dataSourceType.inData) {//内部数据源
			$("#inDataSource").css("display","");
			$(".outDataSource").css("display","none");
			$("#chooseOutDataSource").css("display","none");
			//清空选择数据的数据
			$("#chooseDataText").val("");
			$("#chooseDataText").comboTree("clear");
			$("#chooseDataText").comboTree("disable");
		}else if(val == dataSourceType.outData){//外部数据源
			$("#chooseOutDataSource").css("display","");
			$("#inDataSource").css("display","none");
			//清空选择数据的数据
			$("#chooseDataText").val("");
			$("#chooseDataText").comboTree("clear");
			$("#chooseDataText").comboTree("disable");
		}else if(val == dataSourceType.dataSource) {//数据接口
			$("#searchConditionTR").css("display","");
			$("#chooseDataTr").css("display","");
			$("#inDataSource").css("display","none");
			$("#chooseOutDataSource").css("display","none");
			//清空选择数据的数据
			$("#chooseDataText").val("");
			$("#chooseDataText").comboTree("clear");
			$("#chooseDataText").comboTree("disable");
			var sourceSetting = {
					async : {
						
						otherParam: {
							"serviceName" : "dataSourceDataService",
							"methodName" : "getDataSourceList",
							"data": val
						}
					},
					check : {
						enable : false
					},
					callback : {
						onClick: dataSourceTreeNodeOnClick,
					}	
			}
			$("#chooseDataText").comboTree("enable");
			$("#chooseDataText").comboTree("setParams", {treeSetting : sourceSetting});
			
		}
		else {
			$(".outDataSource").css("display","none");
			$(".inDataChooseClass").css("display","none");
			$("#inDataSource").css("display","none");
		}
	});
	
	//内部数据源数据类别改变
	$("#inDataScope").change(function(e) {
		var val = $(this).val();
		if(val == 1) {//数据库表
			$("#chooseDataTr").css("display","");
			$("#searchConditionTR").css("display","");
			$("#dataSqlorHqlTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","none");
			$("#chooseDataText").comboTree("clear");
			$("#chooseDataText").comboTree("disable");
			if(val !=0) {
				var inDataSetting = {
						async : {
							
							otherParam: {
								"serviceName" : "dataSourceDataService",
								"methodName" : "getDataBaseTable",
								"data": val
							}
						},
						check : {
							enable : false
						},
						callback : {
							onClick: treeNodeOnClick,
						}
				};
			$("#chooseDataText").comboTree("enable");
			$("#chooseDataText").comboTree("setParams", {treeSetting : inDataSetting});
			}
		}
		else if(val == 2) {//实体类表(不带acl权限)
			$("#chooseDataTr").css("display","");
			$("#searchConditionTR").css("display","");
			$("#dataSqlorHqlTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","none");
			getDataByType(val);
		}
		else if(val == 7) {//实体类表(带acl权限)
			$("#chooseDataTr").css("display","");
			$("#searchConditionTR").css("display","");
			$("#dataSqlorHqlTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","none");
			getDataByType(val);
		}
		else if(val == 3) {
			$("#chooseDataTr").css("display","");
			$("#searchConditionTR").css("display","");
			$("#dataSqlorHqlTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","none");
			$("#chooseDataText").comboTree("clear");
			$("#chooseDataText").comboTree("disable");
			if(val !=0) {
				var inDataSetting = {
						async : {
							
							otherParam: {
								"serviceName" : "dataSourceDataService",
								"methodName" : "getDataBaseView",
								"data": val
							}
						},
						check : {
							enable : false
						},
						callback : {
							onClick: treeNodeOnClick,
						}
				};
			$("#chooseDataText").comboTree("enable");
			$("#chooseDataText").comboTree("setParams", {treeSetting : inDataSetting});
			}
		}else if(val == 4 || val == 5) {
			$("#chooseDataTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#chooseDataTr").css("display","none");
			$("#searchConditionTR").css("display","none");
			$("#dataSqlorHqlTr").css("display","");
			$("#dataAclHqlTr").css("display","none");
		}else if(val == 6) {
			$("#chooseDataTr").clearForm(true);
			$("#dataSqlorHqlTr").clearForm(true);
			$("#chooseDataTr").css("display","none");
			$("#searchConditionTR").css("display","none");
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","");
		}
	});
	//获取外部数据源配置列表
	$("#chooseOutDataSource").live("click",function() {
		var outSetting = {
				async : {
					
					otherParam: {
						"serviceName" : "dataSourceProfileService",
						"methodName" : "getAllByTree",
						"data": ""
					}
				},
				check : {
					enable : false
				},
				callback : {
					onClick: outTreeNodeOnClick,
				}
		};
		$("#outDataSourceName").comboTree("enable");
		$("#outDataSourceName").comboTree("setParams", {treeSetting : outSetting});
	});
	//外部配置选择点击的回调函数
	function outTreeNodeOnClick(event, treeId, treeNode) {
		$("#outDataSourceName").val(treeNode.name);
		$("#outDataSourceId").val(treeNode.id);
		$("#outDataSource").css("display","");
		$("#outDataSourceName").comboTree("hide");
	}
	//外部数据源数据类别改变
	$("#outDataScope").change(function(e) {
		var val = $(this).val();
		if(val == outDataType.table || val == outDataType.view) {//数据库表
			$("#chooseDataTr").css("display","");
			$("#searchConditionTR").css("display","");
			$("#dataSqlorHqlTr").clearForm(true);
			$("#dataAclHqlTr").clearForm(true);
			$("#dataSqlorHqlTr").css("display","none");
			$("#dataAclHqlTr").css("display","none");
			var outDataSourceId = $("#outDataSourceId").val();
			var outDataSetting = {
						async : {
							
							otherParam: {
								"serviceName" : "dataSourceDataService",
								"methodName" : "getDataBaseInfoByOut",
								"data": [outDataSourceId,val]
							}
						},
						check : {
							enable : false
						},
						callback : {
							onClick: outDataTreeNodeOnClick,
						}
				};
			$("#chooseDataText").comboTree("enable");
			$("#chooseDataText").comboTree("setParams", {treeSetting : outDataSetting});
			
			$("#chooseDataText").die().live("keyup",(function(event){
				var code = event.keyCode;
	    	    if (code == 13) {
	    	    	var id = $("#outDataSourceId").val();
	    	    	var tableName = $("#chooseDataText").val();
	    			JDS.call({
	    				service:"dataSourceDataService.getColumnsByOutTable",
	    				data:[id,tableName,"1"],
	    				success:function(result) {
	    					data = result.data;
	    					$("#column_list").jqGrid("clearGridData");
	    					for(var i=0;i<data.length;i++) {
	    						$("#column_list").addRowData(i,{"titleName":data[i].name,"columnName":data[i].name,"columnDataType":data[i].dataType},"first");
	    					}
	    				},
	    				error:function(msg){
	    					alert(JSON.stringify(msg));
	    				}
	    			});
	    			$("#chooseDataText").comboTree("hide");
	    	    }
			}));
		}else if(val == outDataType.sql) {//sql
			$("#dataSqlorHqlTr").css("display","");
			$("#chooseDataTr").css("display","none");
		}
	});
	//外部数据源取数据回调函数
	function outDataTreeNodeOnClick(event, treeId, treeNode) {
		
		var outDataScope = $("#outDataScope").val();
		if(outDataScope == outDataType.table || outDataScope == outDataType.view) {
			$("#chooseDataText").val(treeNode.name);
			$("#chooseDataId").val(treeNode.id);
			var tableName = treeNode.name;
			var id = $("#outDataSourceId").val();
			JDS.call({
				service:"dataSourceDataService.getColumnsByOutTable",
				data:[id,tableName,"1"],
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var i=0;i<data.length;i++) {
						$("#column_list").addRowData(i,{"titleName":data[i].name,"columnName":data[i].name,"fieldName":data[i].name,"columnDataType":data[i].dataType},"first");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
			$("#chooseDataText").comboTree("hide");	
		}
	}
	
	//内部选择数据库数据的回调函数
	function treeNodeOnClick(event, treeId, treeNode) {

		var inDataScope = $("#inDataScope").val();
		if(inDataScope == inDataType.table || inDataScope == inDataType.view) {//数据库表或者数据视图
			$("#chooseDataText").val(treeNode.name);
			$("#chooseDataId").val(treeNode.id);
			var tableName = treeNode.name;
			JDS.call({
				service:"dataSourceDataService.getColumnsByTable",
				data:[tableName],
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var i=0;i<data.length;i++) {
						$("#column_list").addRowData(i,{"titleName":data[i][0],"columnName":data[i][0],"fieldName":data[i][0],"columnAliase":data[i][0],"columnDataType":data[i][1],"isExport" : true},"first");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
			$("#chooseDataText").comboTree("hide");	
		}else if (inDataScope == inDataType.entity || inDataScope == inDataType.entityByAcl) {//实体类表(不带权限)
			$("#chooseDataText").val(treeNode.data.chineseName);
			$("#chooseDataId").val(treeNode.data.uuid);
//			$("#formuuid").val(treeNode.data.uuid);
			var formuuid = treeNode.data.uuid;
			var FORMUUID = treeNode.data.uuid;
//			bean["formuuid"] = treeNode.data.uuid;
			JDS.call({
				service:"getViewDataNewService.getSystemTableColumns",
				data:[formuuid],
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var i=0;i<data.length;i++) {
						$("#column_list").addRowData(i,{"titleName":data[i].chineseName,"columnName":data[i].attributeName,"fieldName":data[i].fieldName,"entityName":data[i].entityName,"columnAliase":data[i].columnAliases,"columnDataType":data[i].columnType,"isExport" : false},"first");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
			$("#chooseDataText").comboTree("hide");	
		}
	}
	
	//数据接口点击的回调函数
	function dataSourceTreeNodeOnClick(event, treeId, treeNode) {
		$("#chooseDataText").val(treeNode.name);
		$("#chooseDataId").val(treeNode.id);
		var id = treeNode.id;
		JDS.call({
			service:"dataSourceDataService.getSourceColumns",
			data:[id],
			async : false,
			success:function(result) {
				data = result.data;
				$("#column_list").jqGrid("clearGridData");
				for(var i=0;i<data.length;i++) {
					if(data[i].titleName != null && data[i].titleName != "") {
						$("#column_list").addRowData(i,{"titleName":data[i].titleName,"columnName":data[i].columnName,"fieldName":data[i].fieldName,"columnAliase":data[i].columnAliase,"columnDataType":data[i].columnDataType,"isExport" : true},"first");
					}else {
						$("#column_list").addRowData(i,{"titleName":data[i].columnName,"columnName":data[i].columnName,"fieldName":data[i].fieldName,"columnAliase":data[i].columnAliase,"columnDataType":data[i].columnDataType,"isExport" : true},"first");
					}
				}
			}
		});
		$("#chooseDataText").comboTree("hide");
	}
	
	//获得实体类表的所有数据
	function getDataByType(val) {
		$("#chooseDataText").comboTree("clear");
		$("#chooseDataText").comboTree("disable");
		if(val !=0) {
			var setting = {
					async : {
						
						otherParam: {
							"serviceName" : "getViewDataNewService",
							"methodName" : "getForms",
							"data": val
						}
					},
					check : {
						enable : false
					},
					callback : {
						onClick: treeNodeOnClick,
					}
			};
		$("#chooseDataText").comboTree("enable");
		$("#chooseDataText").comboTree("setParams", {treeSetting : setting});
		}
	}
	
	$("#column_list").jqGrid({
		datatype : "local",
		colNames:["显示标题","列名","字段名","列数据类型",'列所属的类','列别名',"是否输出","默认排序"],
		colModel:[
		          {
		      	  name:'titleName',
		      	  index:'titleName',
		      	  width:'50',
		      	  editable : true,
		        },
		        {
		      	  name:'columnName',
		      	  index:'columnName',
		      	  width:'50',
		      	  editable : false,
		        },
		        {
		      	  name:'fieldName',
		      	  index:'fieldName',
		      	  width:'50',
		      	  editable : false,
			    },
		        {
		      	  name:'columnDataType',
		      	  index:'columnDataType',
		      	  width:'50',
		      	  editable : false,
		        },
		        {   name:'entityName',
			           index:'entityName',
			           hidden:true,
		         }, 
		         {   name:'columnAliase',
			           index:'columnAliase',
			           hidden:true,
		         }, 
		        {
		      	  name:'isExport',
		      	  index:'isExport',
		      	  width:"50",
		      	   editable : true,
		      	   edittype : "checkbox",
		 			   editoptions : {
		 				value : "true:false"
		 			   },
		 			   formatter : "checkbox"
		        },
		        {
		      	  name:'defaultSort',
		      	   index:'defaultSort',
		      	   width:"50",
		      	   editable : true,
		      	   edittype : "select",
		      	   editoptions : {
							value:"1:无排序;2:升序;3:降序"
						},
		        	},
        ],
		          	 autoScroll: true,
			         scrollOffset : 0,
			         sortname : "titleName",
			         sortable : true,
					 sortorder : "asc",
			         cellEdit : true,// 表示表格可编辑
			         cellsubmit : "clientArray", // 表示在本地进行修改
			         multiselect : true,
			         autowidth :true,
			         height:400,
			         afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			        	 $("#" + iRow + "_" + cellname, "#column_list").one('blur',
									function() {
										$('#column_list').saveCell(iRow, iCol);
									});
			         }
	});
	
	$("#list").jqGrid (
		$.extend($.common.jqGrid.settings, {
			url:ctx + '/common/jqgrid/query?queryType=dataSourceDefinition',
			mtype:"POST",
			datatype:"json",
			colNames:["uuid","数据源名称","编号","ID"],
			colModel:[{
				name:"uuid",
				index:"uuid",
				hidden:true,
			},{
				name:"dataSourceName",
				index:"dataSourceName",
				width:"50"
			},{
				name:"dataSourceNum",
				index:"dataSourceNum",
				width:"30"
			},{
				name:"dataSourceId",
				index:"dataSourceId",
				width:"30"
			}],
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "dataSourceNum",
			viewrecords : true,
			sortable : true,
			sortorder : "asc",
			multiselect : true,
			autowidth : true,
			height : 600,
			scrollOffset : 0,
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},
			// 行选择事件
			onSelectRow : function(id) {
				getDataSourceProfileById(id);
				
			},
			loadComplete:function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}	
	));
	
	//根据数据源id获取数据源
	function getDataSourceProfileById(id) {
		JDS.call({
			service:"dataSourceDefinitionService.getBeanByJqGridId",
			data:[id],
			success:function(result) {
				bean = result.data;
				if(bean.dataSourceTypeId == 1) {
					//内部
					$("#inDataSource").css("display","");
					if(bean.inDataScope == 1 || bean.inDataScope ==2 || bean.inDataScope ==3 || bean.inDataScope ==7) {
						$("#chooseDataTr").css("display","");
						$("#chooseOutDataSource").css("display","none");
						$("#outDataSource").css("display","none");
					}else if(bean.inDataScope == 4 || bean.inDataScope == 5) {
						$("#chooseDataTr").css("display","none");
						$("#dataSqlorHqlTr").css("display","");
						$("#chooseOutDataSource").css("display","none");
						$("#outDataSource").css("display","none");
					}else if(bean.inDataScope == 6 ) {
						$("#chooseDataTr").css("display","none");
						$("#dataAclHqlTr").css("display","");
						$("#chooseOutDataSource").css("display","none");
						$("#outDataSource").css("display","none");
					}
					$("#searchConditionTR").css("display","");
				}else if(bean.dataSourceTypeId == 2) {
					//外部
					$("#chooseOutDataSource").css("display","");
					$("#inDataSource").css("display","none");
					$("#outDataSource").css("display","");
					$("#dataAclHqlTr").css("display","none");
					if(bean.outDataScope == 1 || bean.outDataScope ==2) {
						$("#chooseDataTr").css("display","");
						$("#dataSqlorHqlTr").css("display","none");
					}else if(bean.outDataScope == 3) {
						$("#chooseDataTr").css("display","none");
						$("#dataSqlorHqlTr").css("display","");
					}
					$("#searchConditionTR").css("display","");
				} else if(bean.dataSourceTypeId == 3) {
					$("#inDataScope").val("0");
					$("#inDataSource").css("display","none");
					$("#searchConditionTR").css("display","");
					$("#chooseDataTr").css("display","");
				}
				$("#column_form").json2form(bean);
				$("#chooseDataText").comboTree("disable");
				if(bean.dataSourceTypeId == 1) {
					if($("#inDataScope").val() ==1) {
						var inDataSetting = {
								async : {
									otherParam : {
										"serviceName" : "dataSourceDataService",
										"methodName" : "getDataBaseTable",
										"data" : 1
									}
								},check : {
									enable : false
								},
								callback : {
									onClick: treeNodeOnClick,
								}
							};
						$("#chooseDataText").comboTree("clear");
						$("#chooseDataText").comboTree("enable");
						$("#chooseDataText").comboTree("setParams", {treeSetting : inDataSetting});
					}else if($("#inDataScope").val() ==2) {
						var inDataSetting = {
								async : {
									otherParam : {
										"serviceName" : "getViewDataNewService",
										"methodName" : "getForms",
										"data" : 2
									}
								},
								check : {
									enable : false
								},
								callback : {
									onClick: treeNodeOnClick,
								}
							};
						$("#chooseDataText").comboTree("clear");
						$("#chooseDataText").comboTree("enable");
						$("#chooseDataText").comboTree("setParams", {treeSetting : inDataSetting});
					}else if($("#inDataScope").val() ==3 || $("#inDataScope").val() == 7) {
						var inDataSetting = {
								async : {
									
									otherParam: {
										"serviceName" : "getViewDataNewService",
										"methodName" : "getForms",
										"data": $("#inDataScope").val()
									}
								},
								check : {
									enable : false
								},
								callback : {
									onClick: treeNodeOnClick,
								}
						};
						$("#chooseDataText").comboTree("clear");
						$("#chooseDataText").comboTree("enable");
						$("#chooseDataText").comboTree("setParams", {treeSetting : inDataSetting});
					}
				}else if(bean.dataSourceTypeId == 2) {
					var outDataSourceId = $("#outDataSourceId").val();
					var outDataSetting = {
							async : {
								otherParam : {
									"serviceName" : "dataSourceDataService",
									"methodName" : "getDataBaseInfoByOut",
									"data" : [outDataSourceId,$("#outDataScope").val()]
								}
							},
							check : {
								enable : false
							},
							callback : {
								onClick: outDataTreeNodeOnClick,
							}
						};
					$("#chooseDataText").comboTree("clear");
					$("#chooseDataText").comboTree("enable");
					$("#chooseDataText").comboTree("setParams", {treeSetting : outDataSetting});
				}else if(bean.dataSourceTypeId == 3) {
					var settingForSource = {
							async : {
								otherParam : {
									"serviceName" : "dataSourceDataService",
									"methodName" : "getDataSourceList",
									"data" : 1
								}
							},check : {
								enable : false
							},
							callback : {
								onClick: dataSourceTreeNodeOnClick,
							}
						};
					$("#inDataScope").val("0");
					$("#chooseDataText").comboTree("clear");
					$("#chooseDataText").comboTree("enable");
					$("#chooseDataText").comboTree("setParams", {treeSetting : settingForSource});
				}
				
				//设置数据源列定义的列表
				toColumnList(bean);
			}
		});
	}
	//设置数据源列的定义
	function toColumnList(bean) {
		var $columnList = $("#column_list");
		$columnList.jqGrid("clearGridData");
		var columns = bean['dataSourceColumns'];
		for(var index =0;index<columns.length;index++) {
			$columnList.jqGrid("addRowData",columns[index].uuid, columns[index]);
		}
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	//清空表单，选择框
	function clear() {
		//清空表单
		$("#column_form").clearForm(true);
		$("#column_list").jqGrid("clearGridData");
	}
	//收集列定义数据
	function collectColumn(bean) {
		var change1 = $("#column_list").jqGrid("getRowData");
		for(var i=0;i<change1.length;i++){
			change1[i].id = i;
		}
		bean["dataSourceColumnBean"] = change1;
	}
	//显示删除按钮
	function showDelButton(){
		$("#btn_del").show();
	}
	//保存操作
	$("#btn_save").click(function() {
		$("#column_form").form2json(bean);
		collectColumn(bean);
//		alert("bean " + JSON.stringify(bean));
		JDS.call({
			service:"dataSourceDefinitionService.saveBean",
			data:[bean],
			success:function(result) {
				alert("保存成功!");
				$("#list").trigger("reloadGrid");
				showDelButton();
			},
			error:function(result) {
				alert("保存失败");
			}
		});
	});
	//删除操作
	$("#btn_del").click(function() {
		if ($(this).attr("id") == "btn_del") {
			var id = $("#list").jqGrid('getGridParam', 'selrow');
			if (id == "" || id == null) {
				alert("请选择记录！");
				return true;
			}
			// 设置要删除的id
			bean.id = id;
//			alert(bean.id);
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录！");
				return true;
			}
		} 
		var dataSourceName = $("#list").getRowData(bean.id)["dataSourceName"];
		if (confirm("确定要删除资源[" + dataSourceName + "]吗？")) {
			JDS.call({
				service:"dataSourceDefinitionService.deleteById",
				data:[bean.id],
				success:function(result) {
					alert("删除成功!");
					//删除成功刷新列表
					clear();
					$("#list").trigger("reloadGrid");
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
		if (confirm("确定要删除所选资源吗？")) {
			JDS.call({
				service:"dataSourceDefinitionService.deleteAllById",
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
	
	//隐藏删除按钮
	function hideDelButton(){
		$("#btn_del").hide();
	}
	
	//新增操作
	$("#btn_add").click(function() {
		//清空表单，选择框
		clear();
		$("#chooseOutDataSource").css("display","none");
		$("#outDataSource").css("display","none");
		$(".inDataChooseClass").css("display","none");
		hideDelButton();
	});
	
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		var queryValue2 = $("#select_query option:selected").text();
		if(queryValue2=='-请选择-'){
			queryValue2 = '';
		}
		$("#list").jqGrid("setGridParam", {
			postData : {
				"queryPrefix" : "query",
				"queryOr" : false,
				"query_LIKES_dataSourceName_OR_dataSourceNum_OR_dataSourceId" : queryValue,
//				"query_EQI_dataScope" : queryInt,
				"query_LIKES_cateName" : queryValue2,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	$("#query_keyWord").keypress(function(event) {
		var code = event.keyCode;
	    if (code == 13) {
	    	var queryValue = $("#query_keyWord").val();
	    	var queryValue2 = $("#select_query option:selected").text();
	    	if(queryValue2=='-请选择-'){
				queryValue2 = '';
			}
//			var queryInt = 0;
//			if(queryValue.indexOf("动")>-1||queryValue.indexOf("态")>-1||queryValue.indexOf("表")>-1||queryValue.indexOf("单")>-1){
//				queryInt = 1;
//			}else if(queryValue.indexOf("实")>-1||queryValue.indexOf("体")>-1||queryValue.indexOf("类")>-1||queryValue.indexOf("表")>-1){
//				queryInt = 2;
//			}else if(queryValue.indexOf("模")>-1||queryValue.indexOf("块")>-1||queryValue.indexOf("数")>-1||queryValue.indexOf("据")>-1){
//				queryInt = 3;
//			}
			$("#list").jqGrid("setGridParam", {
				postData : {
					"queryPrefix" : "query",
					"queryOr" : false,
					"query_LIKES_dataSourceName_OR_dataSourceNum_OR_dataSourceId" : queryValue,
//					"query_EQI_dataScope" : queryInt,
					"query_LIKES_dataSourceName" : queryValue2,
				},
				page : 1
			}).trigger("reloadGrid");
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
		west__size :420,
		center_onresize : resizeJqGrid
	});
	
	//验证sql
	$("#validateSql").live("click",function() {
		var sqlValue = $("#sqlOrHqlText").val();
		var dataSourceTypeId = $("#dataSourceTypeId").val();
		//数据接口的id
		var outDataSourceId = $("#outDataSourceId").val();
			var map = {};
			JDS.call({
				service:"dataSourceDataService.queryByValidate",
				data:[dataSourceTypeId,outDataSourceId,sqlValue,map],
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var index=0;index<data.length;index++) {
						$("#column_list").addRowData(index,{"columnName":data[index].columnName,"fieldName":data[index].columnName,"columnDataType":data[index].columnType},"first");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
		
	})
});


$('#subcolor').live("click",function(){
	if($(".colors").css("display")=="block"){
		$(".colors").css("display","none");
	}else if($(".colors").css("display")=="none"){
		$(".colors").css("display","block");
	}
});


//动态生成jqgrid中的select内容
function gettypes(formUuid,tableType){}

function selColor(color){
	 $("#scolor").val(color);
     $("#subject").css("color",color);
     $("#fontcolor").css("background-color",color);
     $("#fontColor").val(color);
}

function selectQuery(element) {
	var text = $("#" +element.id + " option:selected").text();
	if(text=='-请选择-'){
		text = '';
	}
	$("#list").jqGrid("setGridParam", {
		postData : {
			"queryPrefix" : "query",
			"queryOr" : false,
			"query_LIKES_cateName" : text,
		},
		page : 1
	}).trigger("reloadGrid");
}