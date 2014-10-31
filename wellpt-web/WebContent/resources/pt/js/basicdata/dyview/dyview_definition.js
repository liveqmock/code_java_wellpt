$(function(){
	
	// 最新选择的树结点
	var fieldData = null; 
	
	var bean = {
			"uuid":null,
			"viewName":null,
			"code":null,
			"viewId":null,
			"showTitle":null,
			"showCheckBox":null,
			"cateUuid": null,
			"cateName": null,
			"isRead":null,
			"readKey":null,
			"url":null,
			"jsSrc":null,
			"dataScope":null,
			"roleValue":null,
			"roleType":null,
			"checkKey":null,
			"lineType":null,
			"roleName":null,
			"tableDefinitionText":null,
			"tableDefinitionName":null,
			"formuuid":null,
			"columnFields":[],
			"columnDeFields":[],
			"columnCssDefinitionFields":[],
			"selectFields":null,
			"selectDeleteFields":null,
			"customButtonFields":[],
			"buttons":[],
			"pageFields":null,
			"buttonPlace":null,
			"specialField":null,
			"specialFieldMethod":null,
			"requestParamName":null,
			"requestParamId":null,
			"responseParamName":null,
			"responseParamId":null,
			"dataModuleName":null,
			"dataModuleId":null
	};
	
	var pagebean = {
			"isPaging":null,
			"pageNum":null
	};
	
	var selectbean = {
			"forCondition":null,
			"forKeySelect":null,
			"vagueKeySelect":null,//是否全视图模糊关键字查询
			"exactKeySelect":null,//是否根据配置的关键字进行精确查询
			"forTimeSolt":null,
			"searchField":null,
			"conditionTypeFields":[],
			"exactKeySelectCols":[],
			"selectShow":null
	};
	
	var selectdebean = {
			"forCondition":null,
			"forKeySelect":null,
			"forTimeSolt":null,
			"searchField":null,
			"conditionTypeFields":[],
			"exactKeySelectCols":[],
			"selectShow":null
	};
	//特殊列值计算的点击事件
	
	$("#specialField").die().live("click",function() {
		if($("#specialField").prop("checked") == false){
			$(".specialFieldTemp").css("display","none");
			$("#specialFieldMethod").empty();
			$("#requestParamName").empty();
			$("#responseParamName").empty();
		}else if($("#specialField").prop("checked") == true) {
			$(".specialFieldTemp").css("display","");
			var tableType = $("#dataScope").val();
 			if(tableType == "1") {//动态表单字段
 				var setting = {
 						async : {
 							otherParam : {
 								"serviceName" : "formDefinitionService",
 								"methodName" : "getFieldByFormUuid",
 								"data" : bean["formuuid"]
 							}
 						},
 					};
 				$("#requestParamName").comboTree({
 					labelField: "requestParamName",
 					valueField: "requestParamId",
 					treeSetting : setting,
 					width: 220,
 					height: 220
 				});
 				
 				$("#responseParamName").comboTree({
 					labelField: "responseParamName",
 					valueField: "responseParamId",
 					treeSetting : setting,
 					width: 220,
 					height: 220
 				});
 				
 			}else if(tableType == "2") {//实体类字段
 				var setting2 = {
 						async : {
 							otherParam : {
 								"serviceName" : "systemTableAttributeService",
 								"methodName" : "getAttributesTreeNodeByrelationship",
 								"data" : bean["formuuid"]
 							}
 						},
 					};
 				$("#requestParamName").comboTree({
 					labelField: "requestParamName",
 					valueField: "requestParamId",
 					treeSetting : setting2,
 					width: 220,
 					height: 220
 				});
 				
 				$("#responseParamName").comboTree({
 					labelField: "responseParamName",
 					valueField: "responseParamId",
 					treeSetting : setting2,
 					width: 220,
 					height: 220
 				});
 				
 			}else if(tableType == "3") {
 				var setting3 = {
 						async : {
 							otherParam : {
 								"serviceName" : "getViewDataService",
 								"methodName" : "getViewColumnsTree",
 								"data" : bean["formuuid"]
 							}
 						},
 					};
 				
 				$("#requestParamName").comboTree({
 					labelField: "requestParamName",
 					valueField: "requestParamId",
 					treeSetting : setting3,
 					width: 220,
 					height: 220
 				});
 				
 				$("#responseParamName").comboTree({
 					labelField: "responseParamName",
 					valueField: "responseParamId",
 					treeSetting : setting3,
 					width: 220,
 					height: 220
 				});
 			}
		}
	});
	
	/**
	 * 视图的样式设置
	 */
	//新增按钮的点击事件
	$("#newButton_css").live("click",function() {
		var trHtml = '';
		 trHtml += '<tr><td width="10%"><input type="checkbox" class="checkboxClass"></td><td width="90%"><span>列:</span><select style="width:100px;" id="viewColumn" name="viewColumn" class="viewColumnClass"></select>';
		 trHtml +=	'<span>条件:</span><select name="columnCondition" id="columnCondition" class="columnConditionClass" style="width:70px;">';
		 trHtml += '<option value="1">等于</option>';
		 trHtml += '<option value="2">不等于</option>';
		 trHtml += '<option value="3">小于</option>';
		 trHtml += '<option value="4">小等于</option>';
		 trHtml += '<option value="5">大于</option>';
		 trHtml += '<option value="6">大等于</option>';
		 trHtml += '<option value="7">包含</option>';
		 trHtml += '<option value="8">不包含</option></select>';
		 trHtml += '<span>值:</span>';
		 trHtml += '<input type="text" id="columnConditionValue" name="columnConditionValue" class="columnConditionValueClass" style="width:150px;">';
		 trHtml += '<span>样式:</span><input type="text" id="columnCss" name="columnCss" class="columnClass" style="width:50px;"></td></tr>';
		$("#columnTableCss").append(trHtml);
	});
	//列点击事件
	$('.viewColumnClass').live("click",function() {
		var tableType = $("#dataScope").val();
		var $this = $(this);
		if(tableType == "1") {
 			//同步调用获取字段信息
 			JDS.call({
 				async:false,
 				service:"getViewDataService.getFieldByForm",
 				data:[bean["formuuid"]],
 				success:function(result) {
 					data = result.data;
 					Data = data;//获取回来的字段信息
 				}
 			});
 			for (var i=0;i<Data.length;i++) {
 				var option = $("<option>").text(Data[i].displayName).val(Data[i].name);
 				$this.append(option);
 			}
			}else if(tableType == "2") {
				//同步调用获取字段信息
 			JDS.call({
 				async:false,
 				service:"getViewDataService.getSystemTableColumns",
 				data:[bean["formuuid"]],
 				success:function(result) {
 					data = result.data;
 					Data = data;//获取回来的字段信息
// 					alert("DATA "  + JSON.stringify(Data));
 				}
 			});
 			for (var i=0;i<Data.length;i++) {
 				var option = $("<option>").text(Data[i].chineseName).val(Data[i].attributeName).attr("entityName",Data[i].entityName).attr("columnAliases",Data[i].columnAliases).attr("columnDataType",Data[i].columnType);
 				$this.append(option);
 			}
			}else if(tableType == "3") {
				JDS.call({
 				async:false,
 				service:"getViewDataService.getViewColumns",
 				data:[bean["formuuid"]],
 				success:function(result) {
 					data = result.data;
 					Data = data;//获取回来的字段信息
 				}
 			});
				for (var i=0;i<Data.length;i++) {
 				var option = $("<option>").text(Data[i].columnName).val(Data[i].columnAlias).attr("columnAliases",Data[i].columnAlias);
 				$this.append(option);
 			}
			}
	});
	
	//删除按钮的点击事件
	$("#delButton_css").live("click",function() {
		$(".checkboxClass").each(function() {
			if($(this).attr("checked") == "checked") {
				$(this).parents("tr").remove();
//				result.viewColumn = $(this).parents("tr").find("#viewColumn").val();
//				result.columnCondition = $(this).parents("tr").find("#columnCondition").val();
//				result.columnConditionValue = $(this).parents("tr").find("#columnConditionValue").val();
//				result.fontColor = $(this).parents("tr").find("#columnCss").attr("fontColor");
//				result.fontWide = $(this).parents("tr").find("#columnCss").attr("fontWide");
//				delinfo.push(result);
			}
		}); 
	});
	
	
	$(".columnClass").live("click",function() {
		var $this = $(this);
		var font_wide = $this.attr("fontwide");
		var font_color = $this.attr("fontcolor");
		$("#columnCssSet").dialog({
			autoOpen: true,
			height: 350,
			width: 650,
			modal: true,
			open:function() {
				$('input:radio[name=fontWide]:eq("'+font_wide+'")').attr("checked",'checked');
				$("#fontcolor").css("background-color",font_color);
			},
			buttons: {
		      "确定": function() {
		    	  var fontWide = $('input:radio[name=fontWide]:checked').val();
		    	  var fontColor = $(this).find("#fontColor").val();
		    	  $this.attr("fontWide",fontWide);
		    	  $this.attr("fontColor",fontColor);
		    	  $( this ).dialog( "close" );
		      } ,
		      "取消": function() {
		    	  $( this ).dialog( "close" );
		      }
		    }
		});
	});
	
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "getViewDataService",
					"methodName" : "getForms",
					"data":$("#dataScope").val()
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: treeNodeOnClick,
			}
			
	};
	
	
	
	$("#tableDefinitionText").comboTree({
		labelField: "tableDefinitionText",
		valueField: "tableDefinitionId",
		treeSetting : setting,
		width: 220,
		height: 220
	});
	
	var dataModuleSetting = {
			async : {
				otherParam : {
					"serviceName" : "getViewDataService",
					"methodName" : "getAllExcelExportRules",
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: treeNodeOnClickForDataModule,
			}
			
	};
	
	$("#dataModuleName").comboTree({
		labelField: "dataModuleName",
		valueField: "dataModuleId",
		treeSetting : dataModuleSetting,
		width: 220,
		height: 150
	});
	
	function treeNodeOnClickForDataModule(event, treeId, treeNode) { 
		bean["dataModuleId"] = treeNode.id;
		bean["dataModuleName"] = treeNode.name;
		$("#dataModuleName").val(treeNode.name);
		$("#dataModuleId").val(treeNode.id);
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
	
	var setting3 = {
		async : {
			otherParam : {
				"serviceName" : "dataDictionaryService",
				"methodName" : "getViewTypeAsTreeAsync",
				"data" : "DATA_PERMISSION"
			}
		}
	};

	$("#roleName").comboTree({
		labelField: "roleName",
		valueField: "roleType",
		treeSetting : setting2,
		width: 220,
		height: 220
	});
	
	function treeNodeOnClickForRole(event, treeId, treeNode) { 
//		alert("treeNode " +JSON.stringify(treeNode));
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
	
	// 缓存对象
	var cache = {
	};
	
	function getLabelByValue(rowid) {
		if (rowid == null) {
			return "";
		}
		var label = "";
		if(cache[rowid] != undefined) {
			label = cache[rowid].name;
		}
		return label;
	}
	function getValuesByLabel(rowid) {
		if (rowid == null) {
			return "";
		}
		var value = "";
		if(cache[rowid] != undefined) {
			value = cache[rowid].value;
		}
		return value;
	}
	
	// 检测是否是IE浏览器
	function isIE() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'msie') {
			return true;
		} else {
			return false;
		}
	}
	// 检测是否是chrome浏览器
	function isChrome() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'chrome' || _browser == 'webkit') {
			return true;
		} else {
			return false;
		}
	}
	// 检测是否是Firefox浏览器
	function isMozila() {
		var _uaMatch = $.uaMatch(navigator.userAgent);
		var _browser = _uaMatch.browser;
		if (_browser == 'mozilla') {
			return true;
		} else {
			return false;
		}
	}
	
	$("#list").jqGrid (
		$.extend($.common.jqGrid.settings, {
			url:ctx + '/common/jqgrid/query?queryType=viewDefinition',
			mtype:"POST",
			datatype:"json",
			colNames:["uuid","视图名称","编号","视图的数据源","来自的表","表名","表uuid","所属模块id","所属模块"],
			colModel:[{
				name:"uuid",
				index:"uuid",
				hidden:true,
			},{
				name:"viewName",
				index:"viewName",
				width:"50"
			},{
				name:"code",
				index:"code",
				width:"30"
			},{
				name:"dataScope",
				index:"dataScope",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue == 1) {
						return '动态表单';
					} else if (cellvalue == 2) {
						return '实体类表';
					} else if (cellvalue == 3) {
						return '模块数据';
					}
					return cellvalue;
				},
				width:"30"
			},{
				name:"tableDefinitionText",
				index:"tableDefinitionText",
				width:"30"
			},
			{
				name:"tableDefinitionName",
				index:"tableDefinitionName",
				hidden:true,
			},
			{
				name:"formuuid",
				index:"formuuid",
				hidden:true,
			},
			{
				name:"cateUuid",
				index:"cateUuid",
				hidden:true,
			},
			{
				name:"cateName",
				index:"cateName",
				hidden:true,
			},
			],
			rowNum : 20,
			rownumbers : true,
			rowList : [ 10, 20, 50, 100, 200 ],
			rowId : "uuid",
			pager : "#pager",
			sortname : "code",
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
				$("#selectCond").jqGrid("clearGridData");
				getViewById(id);
			},
			loadComplete:function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}	
	));
	$.ajax({
		type : "POST",
		url : ctx +"/basicdata/dyview/getModuleData",
		contentType : "application/json",
		dataType : "json",
		success : function(result) {
			$("#cateUuid").html(result.data);
			$("#select_query").html("<option value=''>-请选择-</option>"+result.data);
		}
	});
	//根据视图的id获取视图的信息
	function getViewById(id) {
		JDS.call({
			service:"viewDefinitionService.getBeanById",
			data:[id],
			success:function(result) {
				//设置视图的特殊列值计算
				if($("#specialField").prop("checked") == false){
					$(".specialFieldTemp").css("display","none");
				}else if($("#specialField").prop("checked") == true) {
					$(".specialFieldTemp").css("display","");
					var tableType = $("#dataScope").val();
		 			if(tableType == "1") {//动态表单字段
		 				var setting = {
		 						async : {
		 							otherParam : {
		 								"serviceName" : "formDefinitionService",
		 								"methodName" : "getFieldByFormUuid",
		 								"data" : bean["formuuid"]
		 							}
		 						},
		 					};
		 				$("#requestParamName").comboTree({
		 					labelField: "requestParamName",
		 					valueField: "requestParamId",
		 					treeSetting : setting,
		 					width: 220,
		 					height: 220
		 				});
		 				
		 				$("#responseParamName").comboTree({
		 					labelField: "responseParamName",
		 					valueField: "responseParamId",
		 					treeSetting : setting,
		 					width: 220,
		 					height: 220
		 				});
		 				
		 			}else if(tableType == "2") {//实体类字段
		 				var setting2 = {
		 						async : {
		 							otherParam : {
		 								"serviceName" : "systemTableAttributeService",
		 								"methodName" : "getAttributesTreeNodeByrelationship",
		 								"data" : bean["formuuid"]
		 							}
		 						},
		 					};
		 				$("#requestParamName").comboTree({
		 					labelField: "requestParamName",
		 					valueField: "requestParamId",
		 					treeSetting : setting2,
		 					width: 220,
		 					height: 220
		 				});
		 				
		 				$("#responseParamName").comboTree({
		 					labelField: "responseParamName",
		 					valueField: "responseParamId",
		 					treeSetting : setting2,
		 					width: 220,
		 					height: 220
		 				});
		 				
		 			}else if(tableType == "3") {
		 				var setting3 = {
		 						async : {
		 							otherParam : {
		 								"serviceName" : "getViewDataService",
		 								"methodName" : "getViewColumnsTree",
		 								"data" : bean["formuuid"]
		 							}
		 						},
		 					};
		 				
		 				$("#requestParamName").comboTree({
		 					labelField: "requestParamName",
		 					valueField: "requestParamId",
		 					treeSetting : setting3,
		 					width: 220,
		 					height: 220
		 				});
		 				
		 				$("#responseParamName").comboTree({
		 					labelField: "responseParamName",
		 					valueField: "responseParamId",
		 					treeSetting : setting3,
		 					width: 220,
		 					height: 220
		 				});
		 			}
				}
				
				bean = result.data;
				//绑定按时间搜索的选项
				var searchFieldOption = "";
				var columnDefinitionsField = bean.columnDefinitions;
				for(var ii=0;ii<columnDefinitionsField.length;ii++){
					if(columnDefinitionsField[ii].columnDataType=="DATE"){
//						searchFieldOption += "<option value='"+columnDefinitionsField[ii].fieldName+"'>"+columnDefinitionsField[ii].titleName+"</option>";
						if(columnDefinitionsField[ii].otherName != "" && columnDefinitionsField[ii].otherName != null) {
							searchFieldOption += columnDefinitionsField[ii].otherName + ":" + columnDefinitionsField[ii].fieldName+";";
						}else {
							searchFieldOption += columnDefinitionsField[ii].titleName + ":" + columnDefinitionsField[ii].fieldName+";";
						}
					}
				}
//				$("#searchField").html(searchFieldOption);
				
				showDelButton();
				if(JSON.stringify(bean["selectDefinitions"]) != "null") {
					for(var i=0;i<bean["selectDefinitions"].conditionType.length;i++) {
						var b = {"name":bean["selectDefinitions"].conditionType[i].name,"value":bean["selectDefinitions"].conditionType[i].conditionValue};
							var rowid =bean["selectDefinitions"].conditionType[i].uuid;
							if (cache[rowid] == null){
								cache[rowid] = b;
							}else {
								cache[rowid] = b;
							}
					}
				}
				$("#column_form").clearForm(true);
				//设置JSP页面的分页定义
				if(bean["pageDefinitions"] != null) {
					pagebean.pageNum = bean["pageDefinitions"].pageNum ;
					pagebean.isPaging = bean["pageDefinitions"].isPaging;
					$("#column_form").json2form(pagebean);
				}
				//设置JSP页面的查询定义
				if(bean["selectDefinitions"] != null) {
					selectbean.forCondition = bean["selectDefinitions"].forCondition;
					selectbean.forKeySelect = bean["selectDefinitions"].forKeySelect;
					selectbean.vagueKeySelect = bean["selectDefinitions"].vagueKeySelect;
					selectbean.exactKeySelect = bean["selectDefinitions"].exactKeySelect;
					selectbean.forTimeSolt = bean["selectDefinitions"].forTimeSolt;
					selectbean.searchField = bean["selectDefinitions"].searchField;
					selectbean.selectShow = bean["selectDefinitions"].selectShow;
					$("#column_form").json2form(selectbean);
					
					selectdebean.forCondition = bean["selectDefinitions"].forCondition;
					selectdebean.forKeySelect = bean["selectDefinitions"].forKeySelect;
					
					selectdebean.forTimeSolt = bean["selectDefinitions"].forTimeSolt;
					selectdebean.searchField = bean["selectDefinitions"].searchField;
					selectdebean.selectShow = bean["selectDefinitions"].selectShow;
					selectConditionShow();
					$("#column_form").json2form(bean);
					
					$("#selectCond").jqGrid('setGridParam',{ 
						  colNames:["uuid","名称","字段","字段类型","备选项","备选项来源"],
							colModel:[ 
										
					          {
									name:"conditionName",
									index:"conditionName",
									width:"100",
									editable : true
								},
							           {
					        	  name:"uuid",
									index:"uuid",
									width:"100",
									hidden : true
					          },
							          {
								name:"conditionName",
								index:"conditionName",
								width:"100",
								editable : true
							},
							{
								name:"appointColumn",
								index:"appointColumn",
								width:"100",
								editable : true,
								edittype : "select",
								editoptions : {
									value:gettypes(bean["formuuid"],$("#dataScope").val())
								},
							},
							{
								name:"appointColumnType",
								index:"appointColumnType",
								hidden:true
							},
							{
								name:"conditionScope",
								index:"conditionScope",
								width:"100",
								editable : true,
								edittype : "select",
								editoptions : {
									value:"1:数据字典;2:常量;3:列数据;4:日期"
								},
							},{
								name:"conditionValue",
								index:"conditionValue",
								width:"240",
								editable : true,
								formatter : function(cellvalue, options, rowObject) {
									return getLabelByValue(options.rowId);
								},
								unformat : function(cellvalue, options, cell) {
									return getValuesByLabel(options.rowId);
								}
							}
							],
							scrollOffset : 0,
							rowId:"uuid",
							multiselect : true,
							height : 300,
							multiselectWidth:20,
							autowidth : true,
							cellEdit : true,// 表示表格可编辑
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							afterEditCell : function(rowid, cellname, value, iRow, iCol) {
								var formUuid = bean["formuuid"];
								afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
							}
					    }).trigger("reloadGrid"); //重新载入 
					
					$("#selectKey").jqGrid('setGridParam',{
						colNames:["uuid","显示名称","来源字段"],
						colModel:[ 
								    {
									},
								{
									name:"uuid",
									index:"uuid",
									width:"100",
									hidden : true
								},
							    {
									name:"keyName",
									index:"keyName",
									width:"100",
									editable : true
								},
								{
									name:"keyValue",
									index:"keyValue",
									width:"100",
									editable : true,
									edittype : "select",
									editoptions : {
										value:gettypes(bean["formuuid"],$("#dataScope").val())
									}
								}
							],
							scrollOffset : 0,
							rowId:"uuid",
							multiselect : true,
							height : 300,
							multiselectWidth:20,
							autowidth : true,
							cellEdit : true,// 表示表格可编辑
							cellsubmit : "clientArray", // 表示在本地进行修改
							autowidth : true,
							afterEditCell : function(rowid, cellname, value, iRow, iCol) {
								var cellId = iRow + "_" + cellname;
								$("#" + cellId, "#selectKey").one('blur', function() {
									$('#selectKey').saveCell(iRow, iCol);
								});
							}
					    }).trigger("reloadGrid"); //重新载入 
						
				}
				if($("#dataScope").val() != 2) {
					$("#role_choose").css("display","none");
				}else {
					$("#role_choose").css("display","");
				}
				
				if($("#forCondition").prop("checked") == false){
					$("#a").css("display","none");
				}

				if($("#forKeySelect").prop("checked") == false){
					$("#b").css("display","none");
				}
				
				
				$("#tableDefinitionText").comboTree("disable");
				
				if($("#dataScope").val() != 0) {
				var setting = {
						async : {
							otherParam : {
								"data" : $("#dataScope").val()
							}
						}
					};
				$("#role_choose").css("display","");
				$("#tableDefinitionText").comboTree("enable");
				$("#tableDefinitionText").comboTree("setParams", {treeSetting : setting});
				}
			
				//设置视图列的列表
				toColumnList(bean);
				
				$("#column_list").jqGrid('setGridParam',{ 
					colNames:['显示标题','标题','列类型','列数据类型','列所属的类','列别名','视图列排序','对应的表字段名','列值','列值的类型','列宽','是否解析html','默认排序','列是否隐藏','允许点击排序','显示行','列权限'],
					colModel:[
						      {	  
					           },
					           {   name:'otherName',
					        	   index:'otherName',
					        	   width:"50",
					        	   editable : true,
					           },
					           {	   name:'titleName',
					        	   index:'titleName',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "select",
					        	   editoptions : {
										value:gettypes(bean["formuuid"],$("#dataScope").val())
									},
					           },
					           {	   name:'columnType',
						           	   index:'columnType',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "select",
						        	   editoptions : {
											value:"1:直接展示;2:数据字典;3:常量;4:用户;5:acl用户;6:单位"
										},
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
					           {   name:'sortOrder',
						           index:'sortOrder',
						           hidden:true
					           }, 
					           {	   name:'fieldName',
						           index:'fieldName',
						           hidden:true
					           }, 
					          {	   name:'value',
					        	   index:'value',
					        	   width:"50",
					        	   editable : true
					           },
					           {   name:'valueType',
					        	   index:'valueType',
					        	   hidden:true
					           }, 
					          {	   name:'width',
					        	   index:'width',
					        	   width:"50",
					        	   editable : true
					           },
					          {	   name:'parseHtml',
					        	   index:'parseHtml',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "checkbox",
					   			   editoptions : {
					   				value : "true:false"
					   			   },
					   			   formatter : "checkbox"
					           },
					          {	   name:'defaultSort',
					        	   index:'defaultSort',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "select",
					        	   editoptions : {
										value:"1:无排序;2:升序;3:降序"
									},
					           },
					          {	   name:'hidden',
					        	   index:'hidden',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "checkbox",
					   			   editoptions : {
					   				value : "true:false"
					   			   },
					   			   formatter : "checkbox"
					           },
					          {	   name:'sortAble',
					        	   index:'sortAble',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "checkbox",
					   			   editoptions : {
					   				value : "true:false"
					   			   },
					   			   formatter : "checkbox"
					           },
					           {   name:'showLine',
					           	   index:'showLine',
					           	   width:"50",
					        	   editable : true,
					        	   edittype : "select",
					        	   editoptions : {
										value:"1:第一行;2:第二行"
									},
					           },
					           {
					        	   name:'fieldPermission',
					        	   index:'fieldPermission',
					        	   width:"50",
					        	   editable : true,
					        	   edittype : "checkbox",
					   			   editoptions : {
					   				value : "true:false"
					   			   },
					   			   formatter : "checkbox"
					           }
					         ],
				}).trigger("reloadGrid"); //重新载入
				
				//设置视图的查询条件的列表
				toSelectList(bean);
				//设置样式
				toColumnCssList(bean);
				//设置JSP页面的按钮定义
				toCustomButtonList(bean);
				if(bean["customButtons"] !=null) {
					$("#column_form").json2form(selectbean);
				}
				$("#searchField").val(searchFieldOption);
			}
		});
	}
	//获得按钮组别
	var groupList = ""; 
	$.ajax({
		type : "POST",
		async:false,
		url : ctx + "/basicdata/dyview/getDataDictionaryByCode",
		data : {"code":"BUTTON_GROUP"},
		success : function(result) {
			groupList = result;
		}
	});
	//设置视图按钮定义的列表
	function toCustomButtonList(bean) {
		var customButton = bean["customButtons"];
		$(".customButton_content").remove();
		for(var index = 0;index<customButton.length;index++) {
			var customButtonObj = customButton[index];
			var buttonContent  = "<tr class='customButton_content'>";
		    buttonContent += "<td>";
		    buttonContent += "<input type='checkbox' class='button_check' value='"+customButtonObj.uuid+"'/>";
		    buttonContent += "</td>";
		    
		    buttonContent += "<td>";
		    buttonContent += "<input type='text' class='button_name' style='width:70px;' value='"+customButtonObj.name+"'/>";
		    buttonContent += "</td>";
	    	buttonContent += "<td>";
	    	if(customButtonObj.place != null) {
	    		var place = customButtonObj.place.split(";");
	    	}
			
			var place1 = false;
			var place2 = false;
			var place3 = false;
			var place4 = false;
    		for(var i=0;i<place.length;i++){
				if("头部"==place[i]){
					place1 = true;
				}
				if("第一行"==place[i]){
					place2 = true;
				}
				if("第二行"==place[i]){
					place3 = true;
				}
				if("头尾部"==place[i]) {
					place4 = true;
				}
			}
    		if(place1){
    			buttonContent += "<input class='button_place' type='checkbox' checked="+true+" value='头部'>头部 ";
    		}else{
    			buttonContent += "<input class='button_place' type='checkbox' value='头部'>头部 ";
    		}
    		if(place2){
    			buttonContent += "<input class='button_place' type='checkbox' checked="+true+" value='第一行'>第一行 ";
    		}else{
    			buttonContent += "<input class='button_place' type='checkbox' value='第一行'>第一行 ";
    		}
			if(place3){
				buttonContent += "<input class='button_place' type='checkbox' checked="+true+" value='第二行'>第二行";
			}else{
				buttonContent += "<input class='button_place' type='checkbox'  value='第二行'>第二行";
			}
			if(place4){
				buttonContent += "<input class='button_place' type='checkbox' checked="+true+" value='头尾部'>头尾部";
			}else{
				buttonContent += "<input class='button_place' type='checkbox'  value='头尾部'>头尾部";
			}
		    buttonContent += "</td>";
		    buttonContent += "<td>";
		   
		    var option ="<option value='' selected="+true+">无</option>";
			for(var key in groupList){
				if(customButtonObj.buttonGroup==groupList[key].name){
					option+= "<option value='"+groupList[key].name+"' selected="+true+">"+groupList[key].name+"</option>";
				}else{
					option+= "<option value='"+groupList[key].name+"'>"+groupList[key].name+"</option>";
				}
			}
			buttonContent += "<select class='button_group' style='width:70px;' >";
			buttonContent += option;
			buttonContent += "</select>";
		    buttonContent += "</td>";
		    	
		    buttonContent += "<td>";
		    buttonContent += "<input type='text' class='button_resaultName' style='width:70px;' value='"+customButtonObj.resaultName+"'/>";
			buttonContent += "<input type='hidden' class='button_code' style='width:70px;' value='"+customButtonObj.code+"'/>";
			buttonContent += "</td>";
		    
		    buttonContent += "<td>";
		    buttonContent += "<textarea class='button_jscontent' rows='2' cols='20' style='width:160px;'>"+customButtonObj.jsContent+"</textarea>";
			buttonContent += "</td>";
			
			buttonContent += "</tr>";
			
			$("#customButton_title").parent().append(buttonContent);
		}
		$(".button_resaultName").live("focus",function(){
			var this_ = $(this);
			$("#dlg_choose_button").popupTreeWindow({
				title : "权限按钮选择",
				initValues : this_.next().val(),
				treeSetting : selectSubFlowSetting,
				afterSelect : function(retVal) {
					this_.parent().find(".button_code").val(retVal["value"]);
					this_.val(retVal["path"]);
				},
				afterCancel : function() {
				},
				close : function(e) {
				}
			});
			$("#dlg_choose_button").popupTreeWindow("open");
		});
	}
	//按钮设置的全选
	$(".customButton_allCheck").click(function(){
		if($(this).attr("checked")==undefined){
			$(".button_check").attr("checked",false);
		}else if($(this).attr("checked")=="checked"){
			$(".button_check").attr("checked",true);
		}
	});	
	//删除按钮
	$("#btn_del_btn").click(function(){
		$(".button_check:checked").each(function(){
			$(this).parents(".customButton_content").remove();
		});
	});	
	//添加按钮
	$("#btn_add_btn").click(function(){
		var buttonContent  = "<tr class='customButton_content'>";
	    buttonContent += "<td>";
	    buttonContent += "<input type='checkbox' class='button_check' value=''/>";
	    buttonContent += "</td>";
	    
	    buttonContent += "<td>";
	    buttonContent += "<input type='text' class='button_name' style='width:70px;' value=''/>";
	    buttonContent += "</td>";
    	
	    buttonContent += "<td>";
		buttonContent += "<input class='button_place' type='checkbox' value='头部'>头部 ";
		buttonContent += "<input class='button_place' type='checkbox' value='第一行'>第一行 ";
		buttonContent += "<input class='button_place' type='checkbox'  value='第二行'>第二行 ";
		buttonContent += "<input class='button_place' type='checkbox'  value='头尾部'>头尾部";
	    buttonContent += "</td>";
	   
	    buttonContent += "<td>";
	    var option ="<option value='' selected="+true+">无</option>";
		for(var key in groupList){
			option+= "<option value='"+groupList[key].name+"'>"+groupList[key].name+"</option>";
		}
		buttonContent += "<select class='button_group' style='width:70px;' >";
		buttonContent += option;
		buttonContent += "</select>";
	    buttonContent += "</td>";
	    	
	    buttonContent += "<td>";
	    buttonContent += "<input type='text' class='button_resaultName' style='width:70px;' value=''/>";
		buttonContent += "<input type='hidden' class='button_code' style='width:70px;' value=''/>";
		buttonContent += "</td>";
	    
	    buttonContent += "<td>";
	    buttonContent += "<textarea class='button_jscontent' rows='2' cols='20' style='width:160px;'></textarea>";
		buttonContent += "</td>";
		
		buttonContent += "</tr>";
		
		$("#customButton_title").parent().append(buttonContent);
	});
	// JQuery zTree设置
	var selectSubFlowSetting = {
		view : {
			showIcon : false
		},
		check : {
			chkStyle : "radio"
		},
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "resourceService",
				"methodName" : "getResourceButtonTree",
				"data" : "-1"
			},
			type : "POST"
		}
	};
	
	function toColumnCssList(bean) {
		var $columnTableCss = $("#columnTableCss");
		$columnTableCss.find("tr").remove();
		var columnCss = bean["columnCssDefinition"];
		if(columnCss != undefined) {
			for(var index=0;index<columnCss.length;index++) {
				var trHtml = '';
				 trHtml += '<tr><td width="10%"><input type="checkbox" class="checkboxClass"></td><td width="90%"><span>列:</span>';
				 trHtml += '<select style="width:100px;" id="viewColumn_'+index+'"'+ 'name="viewColumn" class="viewColumnClass">';
				 trHtml += '</select>';
				 trHtml +=	"<span>条件:</span><select name='columnCondition' id='columnCondition_"+index+"'";
				 trHtml +=	'class="columnConditionClass" style="width:70px;">';
				 trHtml += '<option value="1">等于</option>';
				 trHtml += '<option value="2">不等于</option>';
				 trHtml += '<option value="3">小于</option>';
				 trHtml += '<option value="4">小等于</option>';
				 trHtml += '<option value="5">大于</option>';
				 trHtml += '<option value="6">大等于</option>';
				 trHtml += '<option value="7">包含</option>';
				 trHtml += '</select>';
				 trHtml += '<span>值:</span>';
				 trHtml += '<input type="text" id="columnConditionValue_'+index+'"'+  ' name="columnConditionValue" class="columnConditionValueClass" style="width:150px;">';
				 trHtml += '<span>样式:</span><input type="text" id="columnCss_'+index+'"'+  ' name="columnCss" class="columnClass" style="width:50px;"></td></tr>';
				 $columnTableCss.append(trHtml);
				 var option = $("<option>").text(columnCss[index].viewColumn).val(columnCss[index].viewColumn);
				 $("#viewColumn_"+index).append(option);
				 $("#columnCondition_"+index).children("option[value='"+columnCss[index].columnCondition+"']").attr("selected","selected");
				 $("#columnConditionValue_"+index).val(columnCss[index].columnConditionValue);
				 $("#columnCss_"+index).attr("fontColor",columnCss[index].fontColor).attr("fontWide",columnCss[index].fontWide);
			}
		}
	}
	
	//设置视图列的列表
	function toColumnList(bean) {
		var $columnList = $("#column_list");
		$columnList.jqGrid("clearGridData");
		var columns = bean["columnDefinitions"];
		for( var index=0; index<columns.length;index++) {
			$columnList.jqGrid("addRowData",columns[index].uuid, columns[index]);
		}
	}
	
	//设置视图的查询条件的列表
	function toSelectList(bean) {
		var $selectCond = $("#selectCond");
		$selectCond.jqGrid("clearGridData");
		if(JSON.stringify(bean["selectDefinitions"]) != "null") {
		
			var selects = bean["selectDefinitions"].conditionType;
		
			for( var index=0; index<selects.length;index++) {
				$selectCond.jqGrid("addRowData",selects[index].uuid, selects[index]);
			}
		}
		var $selectKey = $("#selectKey");
		$selectKey.jqGrid("clearGridData");
		if(JSON.stringify(bean["selectDefinitions"]) != "null") {
			var selectKeys = bean["selectDefinitions"].exactKeySelectCols;
			for( var index=0; index<selectKeys.length;index++) {
				$selectKey.jqGrid("addRowData",selectKeys[index].uuid, selectKeys[index]);
			}
		}
	}
	
	$("#column_list").jqGrid({
		datatype : "local",
		colNames:['显示标题','标题','列类型','列数据类型','列所属的类','列别名','视图列排序','对应的表字段名','列值','列值的类型','列宽','是否解析html','默认排序','列是否隐藏','允许点击排序','显示行','列权限'],
		colModel:[
					{   name:'otherName',
						   index:'otherName',
						   width:"50",
						   editable : true,
					},
		           {	   name:'titleName',
		        	   index:'titleName',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "select",
		        	   editoptions : {
							value:"1:请选择"
						},
		           },
		           {	   name:'columnType',
			           	   index:'columnType',
			        	   width:"50",
			        	   editable : true,
			        	   edittype : "select",
			        	   editoptions : {
								value:"1:直接展示;2:数据字典;3:常量;4:用户;5:acl用户;6:单位"
							},
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
		           {   name:'sortOrder',
			           index:'sortOrder',
			           hidden:true
		           }, 
		           {	   name:'fieldName',
			           index:'fieldName',
			           hidden:true
		           }, 
		          {	   name:'value',
		        	   index:'value',
		        	   width:"50",
		        	   editable : true
		           },
		           {   name:'valueType',
		        	   index:'valueType',
		        	   hidden:true
		           }, 
		          {	   name:'width',
		        	   index:'width',
		        	   width:"50",
		        	   editable : true
		           },
		          {	   name:'parseHtml',
		        	   index:'parseHtml',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "checkbox",
		   			   editoptions : {
		   				value : "true:false"
		   			   },
		   			   formatter : "checkbox"
		           },
		          {	   name:'defaultSort',
		        	   index:'defaultSort',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "select",
		        	   editoptions : {
							value:"1:无排序;2:升序;3:降序"
						},
		           },
		          {	   name:'hidden',
		        	   index:'hidden',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "checkbox",
		   			   editoptions : {
		   				value : "true:false"
		   			   },
		   			   formatter : "checkbox"
		           },
		          {	   name:'sortAble',
		        	   index:'sortAble',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "checkbox",
		   			   editoptions : {
		   				value : "true:false"
		   			   },
		   			   formatter : "checkbox"
		           },
		           {   name:'showLine',
		           	   index:'showLine',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "select",
		        	   editoptions : {
							value:"1:第一行;2:第二行"
						},
		           },
		           {
		        	   name:'fieldPermission',
		        	   index:'fieldPermission',
		        	   width:"50",
		        	   editable : true,
		        	   edittype : "checkbox",
		   			   editoptions : {
		   				value : "true:false"
		   			   },
		   			   formatter : "checkbox"
		           }
		         ],
		         autoScroll: true,
		         scrollOffset : 0,
		         sortable : false,
		         cellEdit : true,// 表示表格可编辑
		         cellsubmit : "clientArray", // 表示在本地进行修改
		         multiselect : true,
		         autowidth :true,
		         height:400,
		         afterEditCell : function(rowid, cellname, value, iRow, iCol) {
		        	 	var cellId = iRow + "_" + cellname;
		        	 	var rowData = $(this).getRowData(rowid);
		        	 	if(cellname == "value" && rowData["columnType"] == "acl用户"){
		        	 		$("#acl_role_choose").dialog({
		        	 			title : "acl用户设置",
	        					autoOpen: true,
	        					width: 450,
	        					height: 300,
	        					modal: true,
	        					open: function() {
	        						$("#aclRoleName").comboTree({
	        							labelField: "aclRoleName",
	        							valueField: "aclRoleValue",
	        							treeSetting : setting3,
	        							width: 220,
	        							height: 220
	        						});
	        						if(value.indexOf("|") != -1){
	        							var entityClassName = value.substring(0, value.indexOf("|"));
	        							var roleValue = value.substring(value.indexOf("|") + 1, value.length);
	        							$("#aclEntityClass").val(entityClassName);
	        							$("#aclRoleName").comboTree("initValue", roleValue);
	        						}else{
	        							$("#aclRoleName").comboTree("initValue", value);
	        						}
	        					},
	        					buttons: {
	        						"确定": function(e){
	        							var aclEntityClass = $("#aclEntityClass").val();
	        							var roleValue = $("#aclRoleValue").val();
	        							$("#column_list").setRowData(rowid,{value: aclEntityClass + "|" + roleValue});
	        							$(this).dialog("close");
	        						},
	        						"取消": function(e){$(this).dialog("close");}
	        					},
	        					close : function(e){
	        						$('#column_list').saveCell(iRow, iCol);
	        					}
		        	 		});
		        	 	} else if(cellname == "value") {
		        	 		$("#" + cellId).focus(function() {
		        	 			var columnValue = $("#setColumnValue_basic");
		        	 			columnValue.empty();
		        	 			var tableType = $("#dataScope").val();
		        	 			var Data = null;
		        	 			if(tableType == "1") {
			        	 			//同步调用获取字段信息
			        	 			JDS.call({
			        	 				async:false,
			        	 				service:"getViewDataService.getFieldByForm",
			        	 				data:[bean["formuuid"]],
			        	 				success:function(result) {
			        	 					data = result.data;
			        	 					Data = data;//获取回来的字段信息
			        	 				}
			        	 			});
			        	 			for (var i=0;i<Data.length;i++) {
			        	 				var option = $("<option>").text(Data[i].displayName).val(Data[i].name);
			        	 				columnValue.append(option);
			        	 			}
		        	 			}else if(tableType == "2") {
		        	 				//同步调用获取字段信息
			        	 			JDS.call({
			        	 				async:false,
			        	 				service:"getViewDataService.getSystemTableColumns",
			        	 				data:[bean["formuuid"]],
			        	 				success:function(result) {
			        	 					data = result.data;
			        	 					Data = data;//获取回来的字段信息
//			        	 					alert("DATA "  + JSON.stringify(Data));
			        	 				}
			        	 			});
			        	 			for (var i=0;i<Data.length;i++) {
			        	 				var option = $("<option>").text(Data[i].chineseName).val(Data[i].attributeName).attr("entityName",Data[i].entityName).attr("columnAliases",Data[i].columnAliases).attr("columnDataType",Data[i].columnType);
			        	 				columnValue.append(option);
			        	 			}
		        	 			}else if(tableType == "3") {
		        	 				JDS.call({
			        	 				async:false,
			        	 				service:"getViewDataService.getViewColumns",
			        	 				data:[bean["formuuid"]],
			        	 				success:function(result) {
			        	 					data = result.data;
			        	 					Data = data;//获取回来的字段信息
			        	 				}
			        	 			});
		        	 				for (var i=0;i<Data.length;i++) {
			        	 				var option = $("<option>").text(Data[i].columnName).val(Data[i].columnAlias).attr("columnAliases",Data[i].columnAlias).attr("columnDataType",Data[i].columnType);
			        	 				columnValue.append(option);
			        	 			}
		        	 			}
		        	 			$("#columnValue").dialog({
		        					autoOpen: true,
		        					height: 350,
		        					width: 650,
		        					modal: true,
		        					open:function() {
		        						var valueType = $('#column_list').getCell(rowid, "valueType");
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
	        					      "确定": function() {
	        					    	  var data = null;
	        					    	  var valueData = null;
	        					    	  var text = null;
	        					    	  var value = null;
	        					    	  var a = null;
	        					    	  var b = null;
	        					    	  var c = null;
	        					    	  if($("#columnBasic").prop("checked") == true ) {
	        					    		   text = columnValue.find("option:selected").text();
	        					    		   value = columnValue.val();
	        					    	  }
	        					    	  if($("#columnSenior").prop("checked") == true ) {
	        					    		  text = $("#setColumnValue_senior").val();
	        					    		  value = $("#setColumnValue_senior").val();
	        					    	  }
	        					    	  if(value == null){
	        					    		  alert("请设置列值！");
	        					    		  return;
	        					    	  }else if($("#columnBasic").prop("checked") == true){
	        					    		  data = text;
	        					    		  valueData = value;
	        					    		  a = columnValue.find("option:selected").attr("entityName");
	        					    		  b = columnValue.find("option:selected").attr("columnAliases");
	        					    		  c = columnValue.find("option:selected").attr("columnDataType");
	        					    		  $("#column_list").setRowData(rowid,{value:data});
	        					    		  $("#column_list").setRowData(rowid,{fieldName:valueData});
	        					    		  $("#column_list").setRowData(rowid,{entityName:a});
	        					    		  $("#column_list").setRowData(rowid,{columnAliase:b});
	        					    		  $("#column_list").setRowData(rowid,{columnDataType:c});
	        					    		  $("#column_list").setRowData(rowid,{valueType:"1"});
													$(this).dialog("close");
													$('#column_list').saveCell(iRow, iCol);
	        					    	  }else if($("#columnSenior").prop("checked") == true) {
	        					    		  data = text;
	        					    		  var s = data;
	        					    			  var s1=s.match("\\{.*?\\}")+"";
	        					    			  var s2=s1.replace("{", "").replace("}", "") ;
	        					    			  columnValue.find("option").each(function() {
	        					    				 if(s2 == $(this).text()) {
		        					    				  a = $(this).attr("entityName");
		        					    				  b = $(this).attr("columnAliases");
		        					    				  c = $(this).attr("columnDataType");
		        					    				  s2 = s2.split(".")[s2.split(".").length-1];
		        					    				  var s2Temp = s2;
		        					        	 			JDS.call({
		        					        	 				async:false,
		        					        	 				service:"getViewDataService.getSystemTableColumns",
		        					        	 				data:[bean["formuuid"]],
		        					        	 				success:function(result) {
		        					        	 					var dates = result.data;
		        					        	 					for (var i=0;i<dates.length;i++) {
				        					        	 				if(dates[i].chineseName.indexOf(s2Temp)>-1){
				        					        	 					s2 = dates[i].attributeName;
				        					        	 				}
				        					        	 			}
		        					        	 				}
		        					        	 			});
		        					    			  }
	        					    			  });
	        					    		  $("#column_list").setRowData(rowid,{value:data});
	        					    		  $("#column_list").setRowData(rowid,{fieldName:s2});
	        					    		  $("#column_list").setRowData(rowid,{entityName:a});
	        					    		  $("#column_list").setRowData(rowid,{columnAliase:b});
	        					    		  $("#column_list").setRowData(rowid,{columnDataType:c});
	        					    		  $("#column_list").setRowData(rowid,{valueType:"2"});
	        					    		  $(this).dialog("close");
												$('#column_list').saveCell(iRow, iCol);
	        					    	  }
	        					      } ,
	        					      "取消": function() {
	        					    	  $( this ).dialog( "close" );
	        					      }
	        					    }
		        				});
		        	 			$('#column_list').saveCell(iRow, iCol);
		        	 		});
		        	 		return;
		        	 	}
		        	 	// Modify event handler to save on blur.
						$("#" + iRow + "_" + cellname, "#column_list").one('blur',
								function() {
									$('#column_list').saveCell(iRow, iCol);
								});
					}
				
	});
	
	//select框值变化时候调用
	$("#dataScope").change(function(e){
		var val = $(this).val();
		if(val == 2) {
			$("#role_choose").css("display","");
		}
		$("#tableDefinitionText").comboTree("clear");
		$("#tableDefinitionText").comboTree("disable");
		if(val !=0) {
			var setting = {
					async : {
						otherParam: {
							"data": val
						}
					}
			};
		$("#tableDefinitionText").comboTree("enable");
		$("#tableDefinitionText").comboTree("setParams", {treeSetting : setting});
		}
	});
		
	function onAsyncSuccess(){
		var nodes = $.fn.zTree.getZTreeObj("tableTree").getNodesByParam('id',$(currTableObj).attr('tableId'),null);
		if(nodes && nodes.length > 0){
			$('#tableDefinitionText').val(nodes[0].name);
		}else{
			$('#tableDefinitionText').val();
		}
	}
	
	//表调用的回调函数
	function treeNodeOnClick(event, treeId, treeNode) {
		//将后台传回的json串转化为对象
		var tableType = $("#dataScope").val();
		if(tableType == "1") {
			var df = eval("(" + treeNode.data + ")");
			$("#tableDefinitionText").val(df.displayName);
			$("#tableDefinitionName").val(df.name);
			$("#formuuid").val(df.uuid);
			var formuuid = df.uuid;
			var FORMUUID = df.uuid;
			bean["formuuid"] = df.uuid;
			JDS.call({
				service:"getViewDataService.getFieldByForm",
				data:[formuuid],
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var i=0;i<data.length;i++) {
						$("#column_list").addRowData(i,{"titleName":data[i].displayName,"fieldName":data[i].name},"first");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
			$("#tableDefinitionText").comboTree("hide");	
			  selectConditionShow();
			  $("#selectKey").jqGrid('setGridParam',{
					colNames:["uuid","显示名称","来源字段"],
					colModel:[
							    {
									name:"keyName",
									index:"keyName",
									width:"100",
									editable : true
								},
							{
								name:"uuid",
								index:"uuid",
								width:"100",
								hidden : true
							},
						    {
								name:"keyName",
								index:"keyName",
								width:"100",
								editable : true
							},
							{
								name:"keyValue",
								index:"keyValue",
								width:"100",
								editable : true,
								edittype : "select",
								editoptions : {
									value:gettypes(bean["formuuid"],$("#dataScope").val())
								}
							}
						],
						scrollOffset : 0,
						rowId:"uuid",
						multiselect : true,
						height : 300,
						multiselectWidth:20,
						autowidth : true,
						cellEdit : true,// 表示表格可编辑
						cellsubmit : "clientArray", // 表示在本地进行修改
						autowidth : true,
						afterEditCell : function(rowid, cellname, value, iRow, iCol) {
							var cellId = iRow + "_" + cellname;
							$("#" + cellId, "#selectKey").one('blur', function() {
								$('#selectKey').saveCell(iRow, iCol);
							});
						}
				    }).trigger("reloadGrid"); //重新载入 
			  
			  $("#selectCond").jqGrid('setGridParam',{ 
				  colNames:["uuid","名称","字段","字段类型","备选项","备选项来源"],
					colModel:[ 
							{
								name:"conditionName",
								index:"conditionName",
								width:"100",
								editable : true
							},  
					           {
			        	  name:"uuid",
							index:"uuid",
							width:"100",
							hidden : true
			          },
					           
					          {
						name:"conditionName",
						index:"conditionName",
						width:"100",
						editable : true
					},
					{
						name:"appointColumn",
						index:"appointColumn",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:gettypes(FORMUUID,$("#dataScope").val())
						},
					},
					{
						name:"appointColumnType",
						index:"appointColumnType",
						hidden : true
					},
					{
						name:"conditionScope",
						index:"conditionScope",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:"1:数据字典;2:常量;3:列数据;4:日期"
						},
					},{
						name:"conditionValue",
						index:"conditionValue",
						width:"240",
						editable : true,
						formatter : function(cellvalue, options, rowObject) {
							return getLabelByValue(options.rowId);
						},
						unformat : function(cellvalue, options, cell) {
							
							return getValuesByLabel(options.rowId);
						}
					}
					],
					scrollOffset : 0,
					rowId:"uuid",
					multiselect : true,
					autowidth : true,
					cellEdit : true,// 表示表格可编辑
					cellsubmit : "clientArray", // 表示在本地进行修改
					autowidth : true,
					afterEditCell : function(rowid, cellname, value, iRow, iCol) {
						var formUuid = FORMUUID;
						afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
					}
			    }).trigger("reloadGrid"); //重新载入 
		}else if (tableType == "2") {
			$("#tableDefinitionText").val(treeNode.data.chineseName);
			$("#tableDefinitionName").val(treeNode.data.tableName);
			$("#formuuid").val(treeNode.data.uuid);
			var formuuid = treeNode.data.uuid;
			var FORMUUID = treeNode.data.uuid;
			bean["formuuid"] = treeNode.data.uuid;
			JDS.call({
				service:"getViewDataService.getSystemTableColumns",
				data:[formuuid],
				success:function(result) {
					data = result.data;
//					alert("data " + JSON.stringify(data));
					$("#column_list").jqGrid("clearGridData");
					$("#column_list").jqGrid('setGridParam',{ 
						colNames:['显示标题','标题','列类型','列数据类型','列所属的类','列别名','视图列排序','对应的表字段名','列值','列值的类型','列宽','是否解析html','默认排序','列是否隐藏','允许点击排序','显示行','列权限'],
						colModel:[
							      {	  
						           },
						           {   name:'otherName',
						        	   index:'otherName',
						        	   width:"50",
						        	   editable : true,
						           },
						           {   name:'titleName',
						        	   index:'titleName',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "select",
						        	   editoptions : {
											value:gettypes(FORMUUID,$("#dataScope").val())
										},
						           },
						           {	   name:'columnType',
							           	   index:'columnType',
							        	   width:"50",
							        	   editable : true,
							        	   edittype : "select",
							        	   editoptions : {
												value:"1:直接展示;2:数据字典;3:常量;4:用户"
											},
						           },
						           {   name:'columnDataType',
							           index:'columnDataType',
							           hidden:true,
						           },
						           {   name:'entityName',
							           index:'entityName',
							           hidden:true,
						           }, 
						           {   name:'columnAliase',
							           index:'columnAliase',
							           hidden:true,
						           }, 
						           {   name:'sortOrder',
							           index:'sortOrder',
							           hidden:true
						           }, 
						           {   name:'fieldName',
							           index:'fieldName',
							           hidden:true
						           }, 
						          {	   name:'value',
						        	   index:'value',
						        	   width:"50",
						        	   editable : true
						           },
						           {   name:'valueType',
						        	   index:'valueType',
						        	   hidden:true
						           }, 
						          {	   name:'width',
						        	   index:'width',
						        	   width:"50",
						        	   editable : true
						           },
						          {	   name:'parseHtml',
						        	   index:'parseHtml',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "checkbox",
						   			   editoptions : {
						   				value : "true:false"
						   			   },
						   			   formatter : "checkbox"
						           },
						          {	   name:'defaultSort',
						        	   index:'defaultSort',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "select",
						        	   editoptions : {
											value:"1:无排序;2:升序;3:降序"
										},
						           },
						          {	   name:'hidden',
						        	   index:'hidden',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "checkbox",
						   			   editoptions : {
						   				value : "true:false"
						   			   },
						   			   formatter : "checkbox"
						           },
						          {	   name:'sortAble',
						        	   index:'sortAble',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "checkbox",
						   			   editoptions : {
						   				value : "true:false"
						   			   },
						   			   formatter : "checkbox"
						           },
						           {   name:'showLine',
						           	   index:'showLine',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "select",
						        	   editoptions : {
											value:"1:第一行;2:第二行"
										},
						           },
						           {
						        	   name:'fieldPermission',
						        	   index:'fieldPermission',
						        	   width:"50",
						        	   editable : true,
						        	   edittype : "checkbox",
						   			   editoptions : {
						   				value : "true:false"
						   			   },
						   			   formatter : "checkbox"
						           }
						         ],
					}).trigger("reloadGrid"); //重新载入
					for(var i=0;i<data.length;i++) {
						$("#column_list").setCell(i,{"entityName":data[i].entityName,"columnAliase":data[i].columnAliases,"columnDataType":data[i].columnType},"first");
					}
				}
			});
			$("#tableDefinitionText").comboTree("hide");
			 selectConditionShow();
			 
			 $("#selectKey").jqGrid('setGridParam',{
					colNames:["uuid","显示名称","来源字段"],
					colModel:[
							    {
									name:"keyName",
									index:"keyName",
									width:"100",
									editable : true
								},
					          {
								name:"uuid",
								index:"uuid",
								width:"100",
								hidden : true
							},
						    {
								name:"keyName",
								index:"keyName",
								width:"100",
								editable : true
							},
							{
								name:"keyValue",
								index:"keyValue",
								width:"100",
								editable : true,
								edittype : "select",
								editoptions : {
									value:gettypes(bean["formuuid"],$("#dataScope").val())
								}
							}
						],
						scrollOffset : 0,
						rowId:"uuid",
						multiselect : true,
						height : 300,
						multiselectWidth:20,
						autowidth : true,
						cellEdit : true,// 表示表格可编辑
						cellsubmit : "clientArray", // 表示在本地进行修改
						autowidth : true,
						afterEditCell : function(rowid, cellname, value, iRow, iCol) {
							var cellId = iRow + "_" + cellname;
							$("#" + cellId, "#selectKey").one('blur', function() {
								$('#selectKey').saveCell(iRow, iCol);
							});
						}
				    }).trigger("reloadGrid"); //重新载入 
			 
			  $("#selectCond").jqGrid('setGridParam',{ 
				  colNames:["uuid","名称","字段","字段类型","备选项","备选项来源"],
					colModel:[ 
							{
								name:"conditionName",
								index:"conditionName",
								width:"100",
								editable : true
							},  
					           {
			        	  name:"uuid",
							index:"uuid",
							width:"100",
							hidden : true
			          },
					           
					          {
						name:"conditionName",
						index:"conditionName",
						width:"100",
						editable : true
					},
					{
						name:"appointColumn",
						index:"appointColumn",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:gettypes(FORMUUID,$("#dataScope").val())
						},
					},
					{
						name:"appointColumnType",
						index:"appointColumnType",
						hidden : true
					},
					{
						name:"conditionScope",
						index:"conditionScope",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:"1:数据字典;2:常量;3:列数据;4:日期"
						},
					},{
						name:"conditionValue",
						index:"conditionValue",
						width:"240",
						editable : true,
						formatter : function(cellvalue, options, rowObject) {
							return getLabelByValue(options.rowId);
						},
						unformat : function(cellvalue, options, cell) {
							return getValuesByLabel(options.rowId);
						}
					}
					],
					scrollOffset : 0,
					rowId:"uuid",
					multiselect : true,
					autowidth : true,
					cellEdit : true,// 表示表格可编辑
					cellsubmit : "clientArray", // 表示在本地进行修改
					autowidth : true,
					afterEditCell : function(rowid, cellname, value, iRow, iCol) {
						var formUuid = FORMUUID;
						afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
					}
			    }).trigger("reloadGrid"); //重新载入
		}else if(tableType == "3") {
			$("#tableDefinitionText").val(treeNode.name);
			$("#tableDefinitionName").val(treeNode.id);
			$("#formuuid").val(treeNode.id);
			var id = treeNode.id;
			var formuuid = treeNode.uuid;
			var FORMUUID = treeNode.uuid;
			bean["formuuid"] = treeNode.id;
			JDS.call({
				service:"getViewDataService.getViewColumns",
				data:[id],
				async : false,
				success:function(result) {
					data = result.data;
					$("#column_list").jqGrid("clearGridData");
					for(var i=0;i<data.length;i++) {
						$("#column_list").addRowData(i,{"titleName":data[i].columnName,"fieldName":data[i].attributeName,"columnDataType":data[i].columnType},"first");
					}
				}
			});
			$("#tableDefinitionText").comboTree("hide");
			selectConditionShow();
			$("#selectKey").jqGrid('setGridParam',{
				colNames:["uuid","显示名称","来源字段"],
				colModel:[
						    {
								name:"keyName",
								index:"keyName",
								width:"100",
								editable : true
							},
						{
							name:"uuid",
							index:"uuid",
							width:"100",
							hidden : true
						},
					    {
							name:"keyName",
							index:"keyName",
							width:"100",
							editable : true
						},
						{
							name:"keyValue",
							index:"keyValue",
							width:"100",
							editable : true,
							edittype : "select",
							editoptions : {
								value:gettypes(bean["formuuid"],$("#dataScope").val())
							}
						}
					],
					scrollOffset : 0,
					rowId:"uuid",
					multiselect : true,
					height : 300,
					multiselectWidth:20,
					autowidth : true,
					cellEdit : true,// 表示表格可编辑
					cellsubmit : "clientArray", // 表示在本地进行修改
					autowidth : true,
					afterEditCell : function(rowid, cellname, value, iRow, iCol) {
						var cellId = iRow + "_" + cellname;
						$("#" + cellId, "#selectKey").one('blur', function() {
							$('#selectKey').saveCell(iRow, iCol);
						});
					}
			    }).trigger("reloadGrid"); //重新载入 
			
			  $("#selectCond").jqGrid('setGridParam',{ 
				  colNames:["uuid","名称","字段","字段类型","备选项","备选项来源"],
					colModel:[ 
							{
								name:"conditionName",
								index:"conditionName",
								width:"100",
								editable : true
							},  
					           {
			        	  name:"uuid",
							index:"uuid",
							width:"100",
							hidden : true
			          },
					           
					          {
						name:"conditionName",
						index:"conditionName",
						width:"100",
						editable : true
					},
					{
						name:"appointColumn",
						index:"appointColumn",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:gettypes(FORMUUID,$("#dataScope").val())
						},
					},
					{
						name:"appointColumnType",
						index:"appointColumnType",
						hidden : true
					},
					{
						name:"conditionScope",
						index:"conditionScope",
						width:"100",
						editable : true,
						edittype : "select",
						editoptions : {
							value:"1:数据字典;2:常量;3:列数据;4:日期"
						},
					},{
						name:"conditionValue",
						index:"conditionValue",
						width:"240",
						editable : true,
						formatter : function(cellvalue, options, rowObject) {
							return getLabelByValue(options.rowId);
						},
						unformat : function(cellvalue, options, cell) {
							
							return getValuesByLabel(options.rowId);
						}
					}
					],
					scrollOffset : 0,
					rowId:"uuid",
					multiselect : true,
					autowidth : true,
					cellEdit : true,// 表示表格可编辑
					cellsubmit : "clientArray", // 表示在本地进行修改
					autowidth : true,
					afterEditCell : function(rowid, cellname, value, iRow, iCol) {
						var formUuid = id;
						afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
					}
			    }).trigger("reloadGrid"); //重新载入
		}
		
	}
	
	//afterEditCellParams中需要传入的参数
	function afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid) {
		var uuid = formUuid;
		var cellId = iRow + "_" + cellname;
		var fieldName = $('#selectCond').getCell(rowid, "appointColumn");
		var condScope = $('#selectCond').getCell(rowid, "conditionScope");
		//设置字段的字段类型
		if(cellname == "appointColumn") {
			$("#" + cellId).change(function() {
				var p1=$(this).children('option:selected').val();
				var tableType = $("#dataScope").val();
				if(tableType == "1") {
					for(var i=0;i<fieldData.length;i++) {
						if(p1 == fieldData[i].descname) {
							$("#selectCond").setRowData(rowid,{appointColumnType:fieldData[i].type});
						}
					}
					$('#selectCond').saveCell(iRow, iCol);
				}else if(tableType == "2") {
					for(var i=0;i<fieldData.length;i++) {
						if(p1 == fieldData[i].chineseName) {
							$("#selectCond").setRowData(rowid,{appointColumnType:fieldData[i].columnType});
						}
					}
					$('#selectCond').saveCell(iRow, iCol);
				}else if(tableType == "3")	{
					for(var i=0;i<fieldData.length;i++) {
						if(p1 == fieldData[i].columnName) {
							$("#selectCond").setRowData(rowid,{appointColumnType:fieldData[i].columnType});
						}
					}
					$('#selectCond').saveCell(iRow, iCol);
				}
			});
		} else 
		if(cellname == "conditionValue") {
			var tableType = $("#dataScope").val();
			$("#" + cellId).val(getLabelByValue(rowid));
			var appointColumnType = $('#selectCond').getCell(rowid, "appointColumnType");
			$("#"+ cellId).click(function() {
				var defaultCondition = $("#defaultCondition").val();
			  if(tableType == "1") {
				if(condScope == "列数据" && "2" != appointColumnType) {
					
					$("#" + cellId).popupWindow({
							initValues : {tableType:tableType,formUuid:uuid,fieldName:fieldName,currentPage:1,value:value,label:getLabelByValue(rowid),defaultCondition:defaultCondition},
							autoOpen : true,
							afterCancel : function() {
								$('#selectCond').saveCell(iRow, iCol);
							},
							afterSelect : function(data) {
								var name = "";
								var value = "";
								for(var i=0;i<data.length;i++) {
									if(name == "") {
										name = data[i].descName;
										value = data[i].value;
									} else {
										name = name + ";" + data[i].descName;
										value = value + ";" + data[i].value;
									}
								}
								$("#" + cellId).val(name);
								var b = {"name":name,"value":value};
									if (cache[rowid] == null){
										cache[rowid] = b;
									}else {
										cache[rowid] = b;
									}
								$('#selectCond').saveCell(iRow, iCol);
							}		
					});
				}else if(condScope == "日期"){
					var name = "无需设置";
					$("#" + cellId).val(name);
					var b = {"name":name,"conditionValue":value};
					if (cache[rowid] == null){
						cache[rowid] = b;
					}else {
						cache[rowid] = b;
					}
					$('#selectCond').saveCell(iRow, iCol);
				}else if(condScope == "数据字典" && "2" != appointColumnType) {
					$("#" + cellId).popupTreeWindow({
						initValues : value,
						autoOpen : true,
						afterCancel : function() {
							$('#selectCond').saveCell(iRow, iCol);
						},
						afterSelect : function(retVal) {
							var name = "";
							var value = "";
								if(name == "") {
									name = retVal.name;
									value = retVal.value;
								} 
							$("#" + cellId).val(name);
							var b = {"name":name,"conditionValue":value};
								if (cache[rowid] == null){
									cache[rowid] = b;
								}else {
									cache[rowid] = b;
								}
							$('#selectCond').saveCell(iRow, iCol);
						}
					});
				}else if(condScope == "常量" && "2" != appointColumnType) {
					var name = $("#" + cellId).val();
					var b = {"name":name,"conditionValue":value};
					if (cache[rowid] == null){
						cache[rowid] = b;
					}else {
						cache[rowid] = b;
					}
					$('#selectCond').saveCell(iRow, iCol);
				}
			  }else if(tableType == "2") {
				  if(condScope == "列数据" && "DATE" != appointColumnType) {
						$("#" + cellId).popupWindow({
								initValues : {tableType:tableType,formUuid:uuid,fieldName:fieldName,currentPage:1,value:value,label:getLabelByValue(rowid)},
								autoOpen : true,
								afterCancel : function() {
									$('#selectCond').saveCell(iRow, iCol);
								},
								afterSelect : function(data) {
									var name = "";
									var value = "";
									for(var i=0;i<data.length;i++) {
										if(name == "") {
											name = data[i].descName;
											value = data[i].value;
										} else {
											name = name + ";" + data[i].descName;
											value = value + ";" + data[i].value;
										}
									}
									$("#" + cellId).val(name);
									var b = {"name":name,"value":value};
										if (cache[rowid] == null){
											cache[rowid] = b;
										}else {
											cache[rowid] = b;
										}
									$('#selectCond').saveCell(iRow, iCol);
								}		
						});
					}else if(condScope == "日期"){
						var name = "无需设置";
						$("#" + cellId).val(name);
						var b = {"name":name,"conditionValue":value};
						if (cache[rowid] == null){
							cache[rowid] = b;
						}else {
							cache[rowid] = b;
						}
						$('#selectCond').saveCell(iRow, iCol);
					}else if(condScope == "数据字典" && "DATE" != appointColumnType) {
						$("#" + cellId).popupTreeWindow({
							initValues : value,
							autoOpen : true,
							afterCancel : function() {
								$('#selectCond').saveCell(iRow, iCol);
							},
							afterSelect : function(retVal) {
								var name = "";
								var value = "";
									if(name == "") {
										name = retVal.name;
										value = retVal.value;
									} 
								$("#" + cellId).val(name);
								var b = {"name":name,"conditionValue":value};
									if (cache[rowid] == null){
										cache[rowid] = b;
									}else {
										cache[rowid] = b;
									}
								$('#selectCond').saveCell(iRow, iCol);
							}
						});
					}else if(condScope == "常量" && "DATE" != appointColumnType) {
						var name = $("#" + cellId).val();
						var b = {"name":name,"conditionValue":value};
						if (cache[rowid] == null){
							cache[rowid] = b;
						}else {
							cache[rowid] = b;
						}
						$('#selectCond').saveCell(iRow, iCol);
					}
			}else if(tableType == "3") {
				 if(condScope == "列数据" && "DATE" != appointColumnType) {
						$("#" + cellId).popupWindow({
								initValues : {tableType:tableType,formUuid:uuid,fieldName:fieldName,currentPage:1,value:value,label:getLabelByValue(rowid),defaultCondition:defaultCondition},
								autoOpen : true,
								afterCancel : function() {
									$('#selectCond').saveCell(iRow, iCol);
								},
								afterSelect : function(data) {
									var name = "";
									var value = "";
									for(var i=0;i<data.length;i++) {
										if(name == "") {
											name = data[i].descName;
											value = data[i].value;
										} else {
											name = name + ";" + data[i].descName;
											value = value + ";" + data[i].value;
										}
									}
									$("#" + cellId).val(name);
									var b = {"name":name,"value":value};
										if (cache[rowid] == null){
											cache[rowid] = b;
										}else {
											cache[rowid] = b;
										}
									$('#selectCond').saveCell(iRow, iCol);
								}		
						});
					}else if(condScope == "日期"){
						var name = "无需设置";
						$("#" + cellId).val(name);
						var b = {"name":name,"conditionValue":value};
						if (cache[rowid] == null){
							cache[rowid] = b;
						}else {
							cache[rowid] = b;
						}
						$('#selectCond').saveCell(iRow, iCol);
					}else if(condScope == "数据字典" && "DATE" != appointColumnType) {
						$("#" + cellId).popupTreeWindow({
							initValues : value,
							autoOpen : true,
							afterCancel : function() {
								$('#selectCond').saveCell(iRow, iCol);
							},
							afterSelect : function(retVal) {
								var name = "";
								var value = "";
									if(name == "") {
										name = retVal.name;
										value = retVal.value;
									} 
								$("#" + cellId).val(name);
								var b = {"name":name,"conditionValue":value};
									if (cache[rowid] == null){
										cache[rowid] = b;
									}else {
										cache[rowid] = b;
									}
								$('#selectCond').saveCell(iRow, iCol);
							}
						});
					}else if(condScope == "常量" && "DATE" != appointColumnType) {
						var name = $("#" + cellId).val();
						var b = {"name":name,"conditionValue":value};
						if (cache[rowid] == null){
							cache[rowid] = b;
						}else {
							cache[rowid] = b;
						}
						$('#selectCond').saveCell(iRow, iCol);
					}
			}	
			});
		}
		// Modify event handler to save on blur.
		$("#" + cellId, "#selectCond").one('blur', function() {
			if(cellId.indexOf("conditionValue") >0) {
				var name = $("#" + cellId).val();
				var b = {"name":name,"conditionValue":value};
				if (cache[rowid] == null){
					cache[rowid] = b;
				}else {
					cache[rowid] = b;
				}
			}
			$('#selectCond').saveCell(iRow, iCol);
		});
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	$("#selectKey").jqGrid(
			{
				datatype:"local",
				colNames:["uuid","显示名称","来源字段"],
				colModel:[
				{
					name:"uuid",
					index:"uuid",
					width:"100",
					hidden : true
				},
			    {
					name:"keyName",
					index:"keyName",
					width:"100",
					editable : true
				},
				{
					name:"keyValue",
					index:"keyValue",
					width:"100",
					editable : true,
					edittype : "select",
					editoptions : {
						value:gettypes(bean["formuuid"],$("#dataScope").val())
					}
				}
			],
			scrollOffset : 0,
			rowId : "uuid",
			multiselect : true,
			autowidth : true,
			cellEdit : true,// 表示表格可编辑
			cellsubmit : "clientArray", // 表示在本地进行修改
			autowidth : true,
			afterEditCell : function(rowid, cellname, value, iRow, iCol) {
				var cellId = iRow + "_" + cellname;
				$("#" + cellId, "#selectKey").one('blur', function() {
					$('#selectKey').saveCell(iRow, iCol);
				});
			}
	    }
	);
	
	$("#selectCond").jqGrid (
			{
				datatype:"local",
				colNames:["uuid","名称","字段","字段类型","备选项","备选项来源"],
				colModel:[
		          {
		        	  name:"uuid",
						index:"uuid",
						hidden : true
		          },
				          {
					name:"conditionName",
					index:"conditionName",
					width:"100",
					editable : true
				},
				{
					name:"appointColumn",
					index:"appointColumn",
					width:"100",
					editable : true,
					edittype : "select",
					editoptions : {
						value:gettypes(bean["formuuid"],$("#dataScope").val())
					},
				},
				{
					name:"appointColumnType",
					index:"appointColumnType",
					hidden:true
				},
				{
					name:"conditionScope",
					index:"conditionScope",
					width:"100",
					editable : true,
					edittype : "select",
					editoptions : {
						value:"1:数据字典;2:常量;3:列数据;4:日期"
					},
				},{
					name:"conditionValue",
					index:"conditionValue",
					width:"240",
					editable : true,
					formatter : function(cellvalue, options, rowObject) {
						return getLabelByValue(options.rowId);
					},
					unformat : function(cellvalue, options, cell) {
						return getValuesByLabel(options.rowId);
					}
				},
				],
				scrollOffset : 0,
				rowId : "uuid",
				multiselect : true,
				autowidth : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var formUuid = bean["formuuid"];
					afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
				}
			}
		);
	//查询条件的展示
	function selectConditionShow() {
//		var vagueKeySelectVal=$('input[type=checkbox][id="vagueKeySelect"]').attr("checked");
//		var exactKeySelectVal=$('input[type=checkbox][id="exactKeySelect"]').attr("checked");
		var vagueKeySelectVal = $("#vagueKeySelect").prop("checked");
		var exactKeySelectVal = $("#exactKeySelect").prop("checked");
		if($("#forCondition").prop("checked") == true) {
			$("#a").css("display","");
		}else if($("#forCondition").prop("checked") == false){
			$("#a").css("display","none");
		}
		if($("#forKeySelect").prop("checked") == true ) {
			if(vagueKeySelectVal == false && exactKeySelectVal == false) {
				$("#vagueKeySelect").attr("checked","checked");
			}
		}
//		var val=$('input:radio[name="exactKeySelect"]:checked').attr("id");
		
		if($("#forKeySelect").prop("checked") == true ) {
			$("#b").css("display","");
//			if(val == undefined) {
//				$("input[type='checkbox'][value='0']").attr("checked, true");
//			}
			
		}else if($("#forKeySelect").prop("checked") == false){
			$("#b").css("display","none");
			$("#c").css("display","none");
		}
		
		if(exactKeySelectVal == true) {
			$("#c").css("display","");
		}else {
			$("#c").css("display","none");
		}
		
		 $("input[name=exactKeySelect]").click(function(){
			 var exactKeySelectVal=$('input[type=checkbox][id="exactKeySelect"]').attr("checked"); 
			 if(exactKeySelectVal == "checked") {
					$("#c").css("display","");
				}else if(exactKeySelectVal == "undefined") {
					$("#c").css("display","none");
				}
		 });
		
	}
	
	
	
	//动态生成jqgrid中的select内容
	function gettypes(formUuid,tableType){
		if(formUuid == null) {
			return;
		}
		if(tableType == null) {
			return;
		}
		var str="请选择:请选择;";
		var formuuid = {"formUuid":formUuid};
		if(tableType == "1") {
			var url = contextPath + '/basicdata/dyview/get_select_column.action';
			$.ajax({
				type:"post",
				async:false,
				data:formuuid,
				url:url,
				success:function(data){
					if (data != null) {
						fieldData = data;
						var jsonobj=eval(data);
				        var length=jsonobj.length;
				        for(var i=0;i<length;i++){
				            if(i!=length-1){
				            	 str+=jsonobj[i].displayName+":"+jsonobj[i].displayName+";";
				            }else{
				              	 str+=jsonobj[i].displayName+":"+jsonobj[i].displayName;
				            }
				         }   
					}
				}
			});
		}else if(tableType == "2") {
			var url = contextPath + '/basicdata/dyview/get_select_column2.action';
			$.ajax({
				type:"post",
				async:false,
				data:formuuid,
				url:url,
				success:function(data){
					if (data != null) {
						fieldData = data;
						var jsonobj=eval(data);
				        var length=jsonobj.length;
				        for(var i=0;i<length;i++){
				            if(i!=length-1){
				            	 str+=jsonobj[i].attributeName+":"+jsonobj[i].chineseName+";";
				            }else{
				              	 str+=jsonobj[i].attributeName+":"+jsonobj[i].chineseName;
				            }
				         }   
					}
				}
			});
		}else if(tableType == "3") {
			var url = contextPath + '/basicdata/dyview/get_select_column3.action';
			$.ajax({
				type:"post",
				async:false,
				data:formuuid,
				url:url,
				success:function(data){
					if (data != null) {
						fieldData = data;
						var jsonobj=eval(data);
				        var length=jsonobj.length;
				        for(var i=0;i<length;i++){
				            if(i!=length-1){
				            	 str+=jsonobj[i].columnName+":"+jsonobj[i].columnName+";";
				            }else{
				              	 str+=jsonobj[i].columnName+":"+jsonobj[i].columnName;
				            }
				         }   
					}
				}
			});
		}
			 return str;
    }
	//收集列数据 收集查询的条件属性
 	function collectColumn(bean) {
		var change1 = $("#column_list").jqGrid("getRowData");
//		var change1 = $("#column_list").getChangedCells('all');
		//给收集到的数据设置id
		for(var i=0;i<change1.length;i++){
			change1[i].id = i;
			change1[i].sortOrder = i;
		}
		bean["columnFields"] = change1;
//		var change2 = $("#selectCond").jqGrid("getRowData");
		var change2 = $("#selectCond").getChangedCells('all');
		//给收集到的数据设置id
		for(var i=0;i<change2.length;i++) {
			var id = change2[i].id;
			change2[i].name = cache[id].name;
			change1[i].sortOrder = i;
		}
		selectbean["conditionTypeFields"] = change2;
		var change5 = $("#selectKey").getChangedCells('all');
		selectbean["exactKeySelectColBeans"] = change5;
		
		//收集视图按钮
		var change3 = [];
		$(".customButton_content").each(function() {
			var this_ = $(this);
			var result = {};
			result.uuid = this_.find(".button_checkd").val();
			result.name = this_.find(".button_name").val();
			var place = "";
			this_.find(".button_place:checked").each(function(){
				place += $(this).val()+";";
			});
			result.place = place;
			result.buttonGroup = this_.find(".button_group option:selected").val();
			result.resaultName = this_.find(".button_resaultName").val();
			result.code = this_.find(".button_code").val();
			result.jsContent = this_.find(".button_jscontent").val();
			change3.push(result);
		});
		bean["customButtonFields"] = change3;
		
		var change4 = [];
		$("#columnTableCss").find("tr").each(function(index) {
			var $this = $(this);
			var result = {};
			result.id = index;
			result.viewColumn = $this.find("[name='viewColumn']").val();
			result.columnCondition = $this.find("[name='columnCondition']").val();
			result.columnConditionValue = $this.find("[name='columnConditionValue']").val();
			result.fontColor = $this.find("[name='columnCss']").attr("fontColor");
			result.fontWide = $this.find("[name='columnCss']").attr("fontWide");
			change4.push(result);
		});
		bean["columnCssDefinitionFields"] = change4;
	}
	
	//查询定义按条件查询点击事件--是否展现备选项选择框
	$("#forCondition").click(function() {
		$("#selectCond").jqGrid("clearGridData");
		selectConditionShow();
	});
	
	//查询定义关键字查询点击事件
	$("#forKeySelect").click(function() {
		$("#selectKey").jqGrid("clearGridData");
		selectConditionShow();
	});
	
	//查询定义关键字——精确关键字定义查询点击事件
	$("#exactKeySelect").click(function() {
		selectConditionShow();
	});
	
	// 新增列
	$("#btn_add_column").click(function() {
			var mydata = {
					uuid : null
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
				bean["columnDeFields"].push(rowData);
			}
			$("#column_list").jqGrid('delRowData', rowids[i]);
		}
	});
	
	//上移列
	$("#btn_up_column").click(function() {
		var rowids = $("#column_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择列!");
			return;
		}
		
		for (var i = (rowids.length - 1); i >= 0; i--) {
			var checkId = $("#jqg_column_list_"+ rowids[i]);
			if($(checkId).attr('checked')){
				 var checkedTR=$(checkId).parent().parent();
					var upTR=checkedTR.prev();
					 checkedTR.insertBefore(upTR);
					 	$(checkId).attr("checked",true);
			}
		}
	});
	
	//下移列
	$("#btn_down_column").click(function() {
		var rowids = $("#column_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择列!");
			return;
		}
		
		for (var i = (rowids.length - 1); i >= 0; i--) {
			var checkId = $("#jqg_column_list_"+ rowids[i]);
			if($(checkId).attr('checked')){
				 var checkedTR=$(checkId).parent().parent();
					var upTR=checkedTR.next();
					 checkedTR.insertAfter(upTR);
					 	$(checkId).attr("checked",true);
			}
		}
	});
	
	
	// 新增条件
	$("#btn_add_cond").click(function() {
			var mydata = {
					uuid : null
				};
			var newDate = new Date().getTime();
			$("#selectCond").jqGrid("addRowData", newDate,mydata);
	});
	
	
	// 删除条件操作
	$("#btn_del_cond").click(function() {
		bean["selectDeleteFields"] = selectdebean;
		var rowids = $("#selectCond").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择条件!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#selectCond").getRowData(rowids[i]);
			if (rowData != null) {
				bean["selectDeleteFields"].conditionTypeFields.push(rowData);
			}
			$("#selectCond").jqGrid('delRowData', rowids[i]);
		}
	});
	
	// 新增精确关键字查询条件
	$("#btn_add_key").click(function() {
		var mydata = {
				uuid : null
			};
		var newDate = new Date().getTime();
		$("#selectKey").jqGrid("addRowData", newDate,mydata);
	});
	
	// 删除条件操作
	$("#btn_del_key").click(function() {
		bean["selectDeleteFields"] = selectdebean;
		var rowids = $("#selectKey").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择条件!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#selectKey").getRowData(rowids[i]);
			if (rowData != null) {
				bean["selectDeleteFields"].exactKeySelectCols.push(rowData);
			}
			$("#selectKey").jqGrid('delRowData', rowids[i]);
		}
	});
	
	//隐藏删除按钮
	function hideDelButton(){
		$("#btn_del").hide();
	}
	//显示删除按钮
	function showDelButton(){
		$("#btn_del").show();
	}

	
	//新增操作
	$("#btn_add").click(function() {
		//清空表单，选择框
		clear();
		$("#selectCond").clearForm(true);
		$("#selectCond").jqGrid("clearGridData");
		selectConditionShow();
		//设置自定义按钮
		setCustomButton();
		hideDelButton();
	});
	//设置自定义按钮
	function setCustomButton() {
		var code = "003006";
		JDS.call({
			service:"getViewDataService.getCustomButtonByCode",
			data:code,
			success:function(result) {
				for(var i=0;i<result.data.length;i++) {
					if(result.data[i].isDefault == true) {
						$("#"+result.data[i].code+"_custom").attr("checked","checked");
					}
				}
			}
		});
	}
	
	//清空表单，选择框
	function clear() {
		//清空表单
		$("#column_form").clearForm(true);
		$("#column_list").jqGrid("clearGridData");
	}
	
	
	//保存操作
	$("#btn_save").click(function() {
		$("#cateName").val($("#cateUuid").find("option:selected").text());
		$("#column_form").form2json(bean);
		$("#column_form").form2json(pagebean);
		$("#column_form").form2json(selectbean);
		bean["pageFields"] = pagebean;
		collectColumn(bean);
		bean["selectFields"] = selectbean;
		bean["roleName"] = null;
		JDS.call({
			service:"viewDefinitionService.saveBean",
			data:[bean],
			success:function(result) {
				alert("保存成功!");
				$("#list").trigger("reloadGrid");
				showDelButton();
			},
			error:function(result) {
				
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
		var viewName = $("#list").getRowData(bean.id)["viewName"];
		if (confirm("确定要删除资源[" + viewName + "]吗？")) {
			JDS.call({
				service:"viewDefinitionService.deleteById",
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
				service:"viewDefinitionService.deleteAllById",
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
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		var queryValue2 = $("#select_query option:selected").text();
		if(queryValue2=='-请选择-'){
			queryValue2 = '';
		}
//		var queryInt = 0;
//		if(queryValue.indexOf("动")>-1||queryValue.indexOf("态")>-1||queryValue.indexOf("表")>-1||queryValue.indexOf("单")>-1){
//			queryInt = 1;
//		}else if(queryValue.indexOf("实")>-1||queryValue.indexOf("体")>-1||queryValue.indexOf("类")>-1||queryValue.indexOf("表")>-1){
//			queryInt = 2;
//		}else if(queryValue.indexOf("模")>-1||queryValue.indexOf("块")>-1||queryValue.indexOf("数")>-1||queryValue.indexOf("据")>-1){
//			queryInt = 3;
//		}
		$("#list").jqGrid("setGridParam", {
			postData : {
				"queryPrefix" : "query",
				"queryOr" : false,
				"query_LIKES_viewName_OR_code_OR_tableDefinitionText" : queryValue,
//				"query_EQI_dataScope" : queryInt,
				"query_LIKES_cateName" : queryValue2,
			},
			page : 1
		}).trigger("reloadGrid");
		
		$("#column_list").jqGrid("setGridParam", {postData:{}}).trigger("reloadGrid");
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
					"query_LIKES_viewName_OR_code_OR_tableDefinitionText" : queryValue,
//					"query_EQI_dataScope" : queryInt,
					"query_LIKES_cateName" : queryValue2,
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

});
$('#subcolor').live("click",function(){
	if($(".colors").css("display")=="block"){
		$(".colors").css("display","none");
	}else if($(".colors").css("display")=="none"){
		$(".colors").css("display","block");
	}
	
});

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