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
			"clickEvent":null,
			"url":null,
			"jsSrc":null,
			"checkKey":null,
			"lineType":null,
			"tableDefinitionText":null,
			"tableDefinitionId":null,
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
			"forFieldSelect":null,
			"fieldSelectBeans":[],
			"conditionTypeFields":[],
	};
	
	var selectdebean = {
			"forCondition":null,
			"forKeySelect":null,
			"forFieldSelect":null,
			"fieldSelectBeans":[],
			"conditionTypeFields":[],
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
			var setting = {
						async : {
							otherParam : {
								"serviceName" : "dataSourceDefinitionService",
								"methodName" : "getDataSourceFieldsByTree",
								"data" : bean["tableDefinitionId"]
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
		var $this = $(this);
		//同步调用获取字段信息
			JDS.call({
				async:false,
				service:"dataSourceDefinitionService.getDataSourceFieldsById",
				data:[bean["tableDefinitionId"]],
				success:function(result) {
					data = result.data;
					Data = data;//获取回来的字段信息
				}
			});
			for (var i=0;i<Data.length;i++) {
 				var option = $("<option>").text(Data[i].titleName).val(Data[i].fieldName);
 				$this.append(option);
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
	
	//取回所有的数据源信息
	var setting = {
			async : {
				otherParam : {
					"serviceName" : "dataSourceDefinitionService",
					"methodName" : "getAllByTreeNode",
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
	
	//取回所有的数据源信息(供查询用)
	var settingForSelect = {
			async : {
				otherParam : {
					"serviceName" : "dataSourceDefinitionService",
					"methodName" : "getAllByTreeNode",
				}
			},
			check : {
				enable : false
			},
			callback : {
				onClick: treeNodeOnClickForSelect,
			}
			
	};
	
	
	$("#dataSourceName").comboTree({
		labelField: "dataSourceName",
		valueField: "dataSourceId",
		treeSetting : settingForSelect,
		width: 220,
		height: 220
	});
	
	var dataModuleSetting = {
			async : {
				otherParam : {
					"serviceName" : "getViewDataNewService",
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
	
	
	
	var setting3 = {
		async : {
			otherParam : {
				"serviceName" : "dataDictionaryService",
				"methodName" : "getViewTypeAsTreeAsync",
				"data" : "DATA_PERMISSION"
			}
		}
	};
	
	function treeNodeOnClickForDataDict(event, treeId, treeNode) { 
		if (treeNode.isParent) {
				$("#dataDictName").val("");
				$("#dataDictValue").val("");
		}
		// 设置值
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var checkNodes = zTree.getCheckedNodes(true);
		var path = "";
		var value = "";
		for ( var index = 0; index < checkNodes.length; index++) {
			var checkNode = checkNodes[index];
				if (path == "" || path == null) {
					path = getAbsolutePath(checkNode);
				} else {
					path = path + ";" + getAbsolutePath(checkNode);
				}
				if (value == "" || value == null) {
					value = checkNode.data;
				} else {
					value =  checkNode.data;
				}
		}
		$("#dataDictName").val(path);
		$("#dataDictValue").val(value);
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
			url:ctx + '/common/jqgrid/query?queryType=viewDefinitionNew',
			mtype:"POST",
			datatype:"json",
			colNames:["uuid","视图名称","编号","来自的数据源","数据源Id","所属模块id","所属模块"],
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
				name:"tableDefinitionText",
				index:"tableDefinitionText",
				width:"30"
			},
			{
				name:"tableDefinitionId",
				index:"tableDefinitionId",
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
			 //行选择事件
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
		url : ctx +"/cms/module/getModuleData",
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
			service:"viewDefinitionNewService.getBeanById",
			data:[id],
			success:function(result) {
				//设置视图的特殊列值计算
				if($("#specialField").prop("checked") == false){
					$(".specialFieldTemp").css("display","none");
					$("#specialFieldMethod").empty();
					$("#requestParamName").empty();
					$("#responseParamName").empty();
				}else if($("#specialField").prop("checked") == true) {
					$(".specialFieldTemp").css("display","");
					var setting = {
								async : {
									otherParam : {
										"serviceName" : "dataSourceDefinitionService",
										"methodName" : "getDataSourceFieldsByTree",
										"data" : bean["tableDefinitionId"]
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
				}
				
				bean = result.data;
				//绑定按时间搜索的选项
				var searchFieldOption = "";
				var columnDefinitionsField = bean.columnDefinitionNews;
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
				for(var i=0;i<bean["selectDefinitionNews"].conditionTypeNew.length;i++) {
					var b = {"name":bean["selectDefinitionNews"].conditionTypeNew[i].name,"value":bean["selectDefinitionNews"].conditionTypeNew[i].conditionValue};
						var rowid =bean["selectDefinitionNews"].conditionTypeNew[i].uuid;
						if (cache[rowid] == null){
							cache[rowid] = b;
						}else {
							cache[rowid] = b;
						}
				}
				$("#column_form").clearForm(true);
				$("#column_form").json2form(bean);
				//设置JSP页面的分页定义
				if(bean["pageDefinitionNews"] != null) {
					pagebean.pageNum = bean["pageDefinitionNews"].pageNum ;
					pagebean.isPaging = bean["pageDefinitionNews"].isPaging;
					$("#column_form").json2form(pagebean);
				}
				//设置JSP页面的查询定义
				if(bean["selectDefinitionNews"] != null) {
					selectbean.forCondition = bean["selectDefinitionNews"].forCondition;
					selectbean.forKeySelect = bean["selectDefinitionNews"].forKeySelect;
					selectbean.forFieldSelect = bean["selectDefinitionNews"].forFieldSelect;
					$("#column_form").json2form(selectbean);
					selectdebean.forCondition = bean["selectDefinitionNews"].forCondition;
					selectdebean.forKeySelect = bean["selectDefinitionNews"].forKeySelect;
					selectbean.forFieldSelect = bean["selectDefinitionNews"].forFieldSelect;
					
					selectConditionShow();
					$("#column_form").json2form(bean);
					
					$("#selectField").jqGrid('setGridParam',{ 
						  colNames:["uuid","标题","字段ID","查询类型","查询类型Id","查询默认值","是否按区域查询","是否精确查询","是否模糊查询","日期格式","组织选择框","备选项来源","备选项设置","字典名称","字典编码","数据源名称","数据源id","数据源选项的名称列","数据源选项的值列","sortOrder"],
						colModel:[
						          {},
						{
							  name:"uuid",
								index:"uuid",
								hidden : true
						},
						{
							name:"showName",
							index:"showName",
							width:"100",
							editable : true,
						},
						{
							name:"field",
							index:"field",
							width:"100",
							editable : true,
							edittype : "select",
							editoptions : {
								value:gettypesForFieldSelect(bean["tableDefinitionId"])
							},
						},
						{
							name:"selectType",
							index:"selectType",
							width:"100",
							editable : true,
						},
						{
							name:"selectTypeId",
							index:"selectTypeId",
							hidden : true
						},
						{
							  name:"defaultValue",
								index:"defaultValue",
								hidden : true
						},
						{
							  name:"isArea",
								index:"isArea",
								hidden : true
						},
						{
							  name:"isExact",
								index:"isExact",
								hidden : true
						},
						{
							  name:"isLike",
								index:"isLike",
								hidden : true
						},
						{
							  name:"contentFormat",
								index:"contentFormat",
								hidden : true
						},
						{
							  name:"orgInputMode",
								index:"orgInputMode",
								hidden : true
						},
						{
							  name:"optionDataSource",
								index:"optionDataSource",
								hidden : true
						},
						{
							  name:"optdata",
								index:"optdata",
								hidden : true
						},
						{
							  name:"dictName",
								index:"dictName",
								hidden : true
						},
						{
							  name:"dictCode",
								index:"dictCode",
								hidden : true
						},
						{
							  name:"dataSourceName",
								index:"dataSourceName",
								hidden : true
						},
						{
							  name:"dataSourceId",
								index:"dataSourceId",
								hidden : true
						},
						{
							  name:"selectNameColumn",
								index:"selectNameColumn",
								hidden : true
						},
						{
							  name:"selectValueColumn",
								index:"selectValueColumn",
								hidden : true
						},
						{
							  name:"sortOrder",
								index:"sortOrder",
								hidden : true
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
								var tableDefinitionId = bean["tableDefinitionId"];
								afterEditCellParamsByField(rowid,cellname,value,iRow,iCol,tableDefinitionId);
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
									value:gettypes(bean["tableDefinitionId"])
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
								var tableDefinitionId = bean["tableDefinitionId"];
								afterEditCellParams(rowid,cellname,value,iRow,iCol,tableDefinitionId);
							}
					    }).trigger("reloadGrid"); //重新载入 
				}
				
				if($("#forFieldSelect").prop("checked") == false){
					$("#selectFieldDiv").css("display","none");
				}
				
				if($("#forCondition").prop("checked") == false){
					$("#a").css("display","none");
				}
				
				
				
				$("#tableDefinitionText").comboTree("disable");
					$("#tableDefinitionText").comboTree("enable");
					$("#tableDefinitionText").comboTree("setParams", {treeSetting : setting});
			
				//设置视图列的列表
				toColumnList(bean);
				
				$("#column_list").jqGrid('setGridParam',{ 
					colNames:['显示标题','标题','列类型','列数据类型','列所属的类','列别名','视图列排序','对应的表字段名','列值','列值的类型','列宽','是否解析html','默认排序','列是否隐藏','允许点击排序','显示行','列权限','数据字典名称','数据字典类型'],
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
										value:gettypes(bean["tableDefinitionId"])
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
					           },{
					        	   name:'dataDictName',
					        	   index:'dataDictName',
					        	   hidden:true
					           },
					           {
					        	   name:'dataDictValue',
					        	   index:'dataDictValue',
					        	   hidden:true
					           }
					         ],
				}).trigger("reloadGrid"); //重新载入
				
				//设置视图的查询条件的列表
				toSelectList(bean);
				//设置样式
				toColumnCssList(bean);
				//设置JSP页面的按钮定义
				toCustomButtonList(bean);
				if(bean["customButtonNews"] !=null) {
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
		url : ctx + "/basicdata/view/getDataDictionaryByCode",
		data : {"code":"BUTTON_GROUP"},
		success : function(result) {
			groupList = result;
		}
	});
	//设置视图按钮定义的列表
	function toCustomButtonList(bean) {
		var customButton = bean["customButtonNews"];
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
	    	}else {
	    		var place = [];
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
		var columnCss = bean["columnCssDefinitionNew"];
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
		var columns = bean["columnDefinitionNews"];
		for( var index=0; index<columns.length;index++) {
			$columnList.jqGrid("addRowData",columns[index].uuid, columns[index]);
		}
	}
	
	//设置视图的查询条件的列表
	function toSelectList(bean) {
		var $selectCond = $("#selectCond");
		var $selectField = $("#selectField");
		$selectCond.jqGrid("clearGridData");
		$selectField.jqGrid("clearGridData");
		var selects = bean["selectDefinitionNews"].conditionTypeNew;
		for( var index=0; index<selects.length;index++) {
			$selectCond.jqGrid("addRowData",selects[index].uuid, selects[index]);
		}
		
		var fieldSelects = bean["selectDefinitionNews"].fieldSelects;
		for( var index=0; index<fieldSelects.length;index++) {
			$selectField.jqGrid("addRowData",fieldSelects[index].uuid, fieldSelects[index]);
		}
	}
	
	$("#column_list").jqGrid({
		datatype : "local",
		colNames:['显示标题','标题','列类型','列数据类型','列所属的类','列别名','视图列排序','对应的数据库字段名','列值','列值的类型','列宽','是否解析html','默认排序','列是否隐藏','允许点击排序','显示行','列权限','数据字典名称','数据字典类型'],
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
		           },{
		        	   name:'dataDictName',
		        	   index:'dataDictName',
		        	   hidden:true
		           },
		           {
		        	   name:'dataDictValue',
		        	   index:'dataDictValue',
		        	   hidden:true
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
		        	 	}else if(cellname == "value" && rowData["columnType"] == "数据字典"){
		        	 		$("#" + cellId).focus(function() {
		        	 			$("#data_dict").css("display","");
		        	 			var columnValue = $("#setColumnValue_basic");
		        	 			columnValue.empty();
		        	 			var Data = null;
		        	 			//同步调用获取字段信息
		        				JDS.call({
		        					async:false,
		        					service:"dataSourceDefinitionService.getDataSourceFieldsById",
		        					data:[bean["tableDefinitionId"]],
		        					success:function(result) {
		        						data = result.data;
		        						Data = data;//获取回来的字段信息
		        					}
		        				});
		        				for (var i=0;i<Data.length;i++) {
		        	 				var option = $("<option entityName='"+Data[i].entityName+"' columnAliases='"+Data[i].columnAliase+"' columnDataType='"+Data[i].columnDataType+"'>").text(Data[i].titleName).val(Data[i].columnName);
		        	 				columnValue.append(option);
		        	 			}
		        	 			
		        	 			$("#columnValue").dialog({
		        					autoOpen: true,
		        					height: 350,
		        					width: 650,
		        					modal: true,
		        					open:function() {
		        						var settingForDataDict = {
	        									async : {
	        										otherParam : {
	        											"serviceName" : "dataDictionaryService",
	        											"methodName" : "getAsTreeAsyncByCheckAll",
	        										}
	        									},
	        									check : {
	        										enable : true
	        									},
	        									callback : {
	        										onCheck: treeNodeOnClickForDataDict,
	        										onClick:treeNodeOnClickForDataDict
	        									}
	        								};
	        							
	        							$("#dataDictName").comboTree({
	        								labelField: "dataDictName",
	        								valueField: "dataDictValue",
	        								treeSetting : settingForDataDict,
	        								width: 220,
	        								height: 220
	        							});
		        						var valueType = $('#column_list').getCell(rowid, "valueType");
		        						var a = $("#" + cellId).val();
		        						if(valueType == "1") {
		        							$("#dataDictName").val(rowData.dataDictName);
		    		        	 			$("#dataDictValue").val(rowData.dataDictValue);
		        							$("#columnBasic").attr("checked",true);
		        							$("#columnSenior").attr("checked",false);
		        							$("#columnValue_basic").css("display","");
		        							$("#columnValue_senior").css("display","none");
		        							$("#setColumnValue_basic").find("option").each(function(){
		        								if($(this).text()==a){
		        									$(this).attr("selected",true);
		        								}
		        							});
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
	        					    		  $("#column_list").setRowData(rowid,{dataDictName:$("#dataDictName").val()});
	        					    		  $("#column_list").setRowData(rowid,{dataDictValue:$("#dataDictValue").val()});
													$(this).dialog("close");
													$('#column_list').saveCell(iRow, iCol);
	        					    	  }
	        					      } ,
	        					      "取消": function() {
	        					    	  $( this ).dialog( "close" );
	        					      }
	        					    }
		        				});
		        	 		});
		        	 	
		        	 	}
		        	 	else if(cellname == "value") {
		        	 		$("#" + cellId).focus(function() {
		        	 			var columnValue = $("#setColumnValue_basic");
		        	 			columnValue.empty();
		        	 			var Data = null;
		        	 			//同步调用获取字段信息
		        				JDS.call({
		        					async:false,
		        					service:"dataSourceDefinitionService.getDataSourceFieldsById",
		        					data:[bean["tableDefinitionId"]],
		        					success:function(result) {
		        						data = result.data;
		        						Data = data;//获取回来的字段信息
		        					}
		        				});
		        				for (var i=0;i<Data.length;i++) {
		        	 				var option = $("<option entityName='"+Data[i].entityName+"' columnAliases='"+Data[i].columnAliase+"' columnDataType='"+Data[i].columnDataType+"'>").text(Data[i].titleName).val(Data[i].columnName);
		        	 				columnValue.append(option);
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
		        					        	 				data:[bean["tableDefinitionId"]],
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
	
//	//select框值变化时候调用
//	$("#dataScope").change(function(e){
//		var val = $(this).val();
//		if(val == 2) {
//			$("#role_choose").css("display","");
//		}
//		$("#tableDefinitionText").comboTree("clear");
//		$("#tableDefinitionText").comboTree("disable");
//		if(val !=0) {
//			var setting = {
//					async : {
//						otherParam: {
//							"data": val
//						}
//					}
//			};
//		$("#tableDefinitionText").comboTree("enable");
//		$("#tableDefinitionText").comboTree("setParams", {treeSetting : setting});
//		}
//	});
		
	function onAsyncSuccess(){
		var nodes = $.fn.zTree.getZTreeObj("tableTree").getNodesByParam('id',$(currTableObj).attr('tableId'),null);
		if(nodes && nodes.length > 0){
			$('#tableDefinitionText').val(nodes[0].name);
		}else{
			$('#tableDefinitionText').val();
		}
	}
	
	function treeNodeOnClickForSelect(event, treeId, treeNode) {
		
		$("#dataSourceName").val(treeNode.name);
		$("#dataSourceId").val(treeNode.id);
		bean["tableDefinitionId"] = treeNode.id;
		var dataSourceId = treeNode.id;
		JDS.call({
			service:"dataSourceDefinitionService.getDataSourceFieldsById",
			data:dataSourceId,
			success:function(result) {
				var data = result.data;
				var optionHtml = "";
				for(var i=0;i<data.length;i++) {
					optionHtml += "<option value='"+data[i].columnName+"'>"+data[i].titleName+"</option>";
				}
				$("#selectNameColumn").html("<option value=''>-请选择-</option>"+optionHtml);
				$("#selectValueColumn").html("<option value=''>-请选择-</option>"+optionHtml);
			},
			error:function(msg){
				alert(JSON.stringify(msg));
			}
		});
	}
	
	//选择数据源后点击的回调函数
	function treeNodeOnClick(event, treeId, treeNode) {
		//将后台传回的json串转化为对象
		$("#tableDefinitionText").val(treeNode.name);
		$("#tableDefinitionId").val(treeNode.id);
		bean["tableDefinitionId"] = treeNode.id;
		var FORMUUID = bean["tableDefinitionId"];
		var dataSourceId = treeNode.id;
		JDS.call({
			service:"dataSourceDefinitionService.getDataSourceFieldsById",
			data:dataSourceId,
			success:function(result) {
				var data = result.data;
				$("#column_list").jqGrid("clearGridData");
				for(var i=0;i<data.length;i++) {
					if(data[i].fieldName != null) {
						$("#column_list").addRowData(i,{"titleName":data[i].titleName,"columnType":"直接展示","value":data[i].columnName,"valueType":1,"columnDataType":data[i].columnDataType,"entityName":data[i].entityName,"fieldName":data[i].fieldName,"columnAliase":data[i].columnAliase},"first");
					}else {
						$("#column_list").addRowData(i,{"titleName":data[i].titleName,"columnType":"直接展示","value":data[i].columnName,"valueType":1,"columnDataType":data[i].columnDataType,"entityName":data[i].entityName,"fieldName":data[i].columnName,"columnAliase":data[i].columnAliase},"first");
					}
				}
			},
			error:function(msg){
				alert(JSON.stringify(msg));
			}
		});
		$("#tableDefinitionText").comboTree("hide");
		selectConditionShow();
		
			
		$("#selectField").jqGrid('setGridParam',{ 
			  colNames:["uuid","标题","字段ID","查询类型","查询类型ID","查询默认值","是否按区域查询","是否精确查询","是否模糊查询","日期格式","组织选择框","备选项来源","备选项设置","字典名称","字典编码","数据源名称","数据源id","数据源选项的名称列","数据源选项的值列","sortOrder"],
			colModel:[
			          {},
			{
				  name:"uuid",
					index:"uuid",
					hidden : true
			},
			{
				name:"showName",
				index:"showName",
				width:"100",
				editable : true,
			},
			{
				name:"field",
				index:"field",
				width:"100",
				editable : true,
				edittype : "select",
				editoptions : {
					value:gettypesForFieldSelect(bean["tableDefinitionId"])
				},
			},
			{
				name:"selectType",
				index:"selectType",
				width:"100",
				editable : true,
			},
			{
				name:"selectTypeId",
				index:"selectTypeId",
				hidden : true
			}
			,{
				  name:"defaultValue",
					index:"defaultValue",
					hidden : true
			},
			{
				  name:"isArea",
					index:"isArea",
					hidden : true
			},
			{
				  name:"isExact",
					index:"isExact",
					hidden : true
			},
			{
				  name:"isLike",
					index:"isLike",
					hidden : true
			},
			{
				  name:"contentFormat",
					index:"contentFormat",
					hidden : true
			},
			{
				  name:"orgInputMode",
					index:"orgInputMode",
					hidden : true
			},
			{
				  name:"optionDataSource",
					index:"optionDataSource",
					hidden : true
			},
			{
				  name:"optdata",
					index:"optdata",
					hidden : true
			},
			{
				  name:"dictName",
					index:"dictName",
					hidden : true
			},
			{
				  name:"dictCode",
					index:"dictCode",
					hidden : true
			},
			{
				  name:"dataSourceName",
					index:"dataSourceName",
					hidden : true
			},
			{
				  name:"dataSourceId",
					index:"dataSourceId",
					hidden : true
			},
			{
				  name:"selectNameColumn",
					index:"selectNameColumn",
					hidden : true
			},
			{
				  name:"selectValueColumn",
					index:"selectValueColumn",
					hidden : true
			},
			{
				  name:"sortOrder",
					index:"sortOrder",
					hidden : true
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
					var tableDefinitionId = bean["tableDefinitionId"];
					afterEditCellParamsByField(rowid,cellname,value,iRow,iCol,tableDefinitionId);
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
						value:gettypes(bean["tableDefinitionId"])
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
				sortname : "uuid",
				sortable : true,
				sortorder : "asc",
				multiselect : true,
				autowidth : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var tableDefinitionId = FORMUUID;
					afterEditCellParams(rowid,cellname,value,iRow,iCol,tableDefinitionId);
				}
		    }).trigger("reloadGrid"); //重新载入 
	}
	
	function afterEditCellParamsByField(rowid,cellname,value,iRow,iCol,formUuid) {
		var fieldSelects_ = bean["selectDefinitionNews"].fieldSelects;
		//初始化数据字典树.
		$.ControlConfigUtil.initDictCode();
		for(var i=0;i<fieldSelects_.length;i++) {
			if(fieldSelects_[i].uuid == rowid) {
				var selectType_ = fieldSelects_[i].selectType;
				var selectTypeId_ = fieldSelects_[i].selectTypeId;
				$("#selectType").val(selectTypeId_);
				var defaultValue_ = fieldSelects_[i].defaultValue;
				$("#defaultValue").val(defaultValue_);
				var isArea_ = fieldSelects_[i].isArea;
				$("#isArea").attr("checked",isArea_);
				var isExact_ = fieldSelects_[i].isExact;
				$("#isExact").attr("checked",isExact_);
				var exactValue_ = fieldSelects_[i].exactValue;
				$("#exactValue").val(exactValue_);
				var isLike_ = fieldSelects_[i].isLike;
				$("#isLike").attr("checked",isLike_);
				var contentFormat_ = fieldSelects_[i].contentFormat;
				$("#contentFormat").val(contentFormat_);
				var orgInputMode_ = fieldSelects_[i].orgInputMode;
				$("#orgInputMode").val(orgInputMode_);
				var optionDataSource_ = fieldSelects_[i].optionDataSource;
				$("input[name=optionDataSource]:eq('"+optionDataSource_+"')").attr("checked","checked");
				var optdata_ = fieldSelects_[i].optdata;
				$("#optdata").val(optdata_);
				var dictName_ = fieldSelects_[i].dictName;
				$("#dictName").val(dictName_);
				var dictCode_ = fieldSelects_[i].dictCode;
				$("#dictCode").val(dictCode_);
				var dataSourceId = fieldSelects_[i].dataSourceId;
				$("#dataSourceId").val(dataSourceId);
				var dataSourceName = fieldSelects_[i].dataSourceName;
				$("#dataSourceName").val(dataSourceName);
				$("#tableDefinitionText").comboTree("setParams", {treeSetting : settingForSelect});
				var selectNameColumn = fieldSelects_[i].selectNameColumn;
				var selectValueColumn = fieldSelects_[i].selectValueColumn;
				$("#selectNameColumn").append("<option value='"+selectNameColumn+"'>"+selectNameColumn+"</option>");
				$("#selectValueColumn").append("<option value='"+selectValueColumn+"'>"+selectValueColumn+"</option>");
			}
		}
		
		var formUuid = bean["tableDefinitionId"];
		var cellId = iRow + "_" + cellname;
//	 	var rowData = $(this).getRowData(rowid);
	 	if(cellname == "selectType") {
	 		$("#select_fieldtype_choose").dialog({
	 			title : "字段查询设置",
				autoOpen: true,
				width: 450,
				height: 450,
				modal: true,
				open: function() {
					var checkvalue=$("input[name='optionDataSource']:checked").val();
					 if(checkvalue=='0'){
						 $('#dicCode_tr').hide();
						 $('#backup_set_tr').css("display","");
						 $('#dataSource_tr').css("display","none");
						 $('#select_name_column_tr').css("display","none");
						 $('#select_value_column_tr').css("display","none");
						 $("input[name='dictCode']").val();
						 $("input[name='dictName']").val();
					 }else if(checkvalue=='1') {
						 $("#backup_set_tr").css("display","none");
						 $("#dicCode_tr").css("display","");
						 $("#optdata").val();
						 $('#dataSource_tr').css("display","none");
						 $('#select_name_column_tr').css("display","none");
						 $('#select_value_column_tr').css("display","none");
					 }else if(checkvalue=='2'){
						 $("#dictName").val("");
						 $("#dictCode").val("");
						 $("#dicCode_tr").css("display","none");
						 $("#backup_set_tr").css("display","none");
						 $('#dataSource_tr').css("display","");
						 $('#select_name_column_tr').css("display","");
						 $('#select_value_column_tr').css("display","");
					 }
					
					$("input[name='optionDataSource']").change(
							 function(){
								 var checkvalue=$("input[name='optionDataSource']:checked").val();
								 if(checkvalue=='0'){
									 $('#dicCode_tr').css("display","none");
									 $('#backup_set_tr').css("display","");
									 $('#dataSource_tr').css("display","none");
									 $('#select_name_column_tr').css("display","none");
									 $('#select_value_column_tr').css("display","none");
									 $("input[name='dictCode']").val();
									 $("input[name='dictName']").val();
								 }else if(checkvalue=='1') {
									 $("#dicCode_tr").css("display","");
									 $("#backup_set_tr").css("display","none");
									 $("#optdata").val();
									 $('#dataSource_tr').css("display","none");
									 $('#select_name_column_tr').css("display","none");
									 $('#select_value_column_tr').css("display","none");
								 }else if(checkvalue=='2'){
									 $("#dictName").val("");
									 $("#dictCode").val("");
									 $("#dicCode_tr").css("display","none");
									 $("#backup_set_tr").css("display","none");
									 $('#dataSource_tr').css("display","");
									 $('#select_name_column_tr').css("display","");
									 $('#select_value_column_tr').css("display","");
								 }
							 }
						 );
					
					var selectTypeValue = $("#selectType").find("option:selected").val();
					if(selectTypeValue == "TEXT") {
						$("#isArea_tr").css("display","");
						$("#isExact_tr").css("display","");
						$("#isLike_tr").css("display","");
						$("#default_tr").css("display","");
						
						$("#date_tr").css("display","none");
						$("#org_tr").css("display","none");
						$("#backup_data_tr").css("display","none");
						$("#backup_set_tr").css("display","none");
						$("#dialog_tr").css("display","none");
					}else if(selectTypeValue == "DATE") {
						$("#isArea_tr").css("display","none");
						$("#isExact_tr").css("display","none");
						$("#isLike_tr").css("display","none");
						$("#default_tr").css("display","none");
						
						$("#date_tr").css("display","");
						$("#org_tr").css("display","none");
						$("#backup_data_tr").css("display","none");
						$("#backup_set_tr").css("display","none");
						$("#dialog_tr").css("display","none");
					}else if(selectTypeValue == "ORG") {
						$("#date_tr").css("display","none");
						$("#org_tr").css("display","");
						$("#backup_data_tr").css("display","none");
						$("#backup_set_tr").css("display","none");
						$("#dialog_tr").css("display","none");
					}else if(selectTypeValue == "SELECT" || selectTypeValue == "RADIO" || selectTypeValue == "CHECKBOX") {
						var checkvalue=$("input[name='optionDataSource']:checked").val();
						 if(checkvalue=='0'){
							 $("#dataSourceName").val("");
							 $("#dataSourceId").val("");
							 $("#dictName").val("");
							 $("#dictCode").val("");
							 $('#dicCode_tr').css("display","none");
							 $('#backup_set_tr').css("display","");
							 $('#backup_data_tr').css("display","");
						 }else if(checkvalue=='1') {
							 $("#dataSourceName").val("");
							 $("#dataSourceId").val("");
							 $("#optdata").val("");
							 $("#backup_set_tr").css("display","none");
							 $("#dicCode_tr").css("display","");
							 $("#optdata").val();
							 $('#dataSource_tr').css("display","none");
							 $('#select_name_column_tr').css("display","none");
							 $('#select_value_column_tr').css("display","none");
						 }
						 else{
							 $("#dictName").val("");
							 $("#dictCode").val("");
							 $("#optdata").val("");
							 $("#dicCode_tr").css("display","");
							 $('#backup_data_tr').css("display","");
							 $("#backup_set_tr").css("display","none");
						 }
						$("#isArea_tr").css("display","none");
						$("#isExact_tr").css("display","none");
						$("#isLike_tr").css("display","none");
						$("#default_tr").css("display","none");
						
						$("#date_tr").css("display","none");
						$("#org_tr").css("display","none");
						$("#dialog_tr").css("display","none");
					}else if(selectTypeValue == "DIAlOG") {
						$("#date_tr").css("display","none");
						$("#dicCode_tr").css("display","none");
						$("#org_tr").css("display","none");
						$("#backup_data_tr").css("display","none");
						$("#backup_set_tr").css("display","none");
						$("#dialog_tr").css("display","");
					}
					
					if($("#isExact").prop("checked")==true) {
						$("#exactValueDiv").css("display","");
					}else {
						$("#exactValueDiv").css("display","none");
					}
					$("#selectType").change(function() {
						var $this = $(this);
						var selectType=$this.children('option:selected').val();
						if(selectType == "TEXT") {
							$("#date_tr").css("display","none");
							$("#org_tr").css("display","none");
							$("#backup_data_tr").css("display","none");
							$("#backup_set_tr").css("display","none");
							$("#dialog_tr").css("display","none");
						}else if(selectType == "DATE") {
							$("#isArea_tr").css("display","none");
							$("#isExact_tr").css("display","none");
							$("#isLike_tr").css("display","none");
							$("#default_tr").css("display","none");
							
							$("#date_tr").css("display","");
							$("#org_tr").css("display","none");
							$("#backup_data_tr").css("display","none");
							$("#backup_set_tr").css("display","none");
							$("#dialog_tr").css("display","none");
						}else if(selectType == "ORG") {
							$("#date_tr").css("display","none");
							$("#org_tr").css("display","");
							$("#backup_data_tr").css("display","none");
							$("#backup_set_tr").css("display","none");
							$("#dialog_tr").css("display","none");
						}else if(selectType == "SELECT" || selectType == "RADIO" || selectType == "CHECKBOX") {
							$("#isArea_tr").css("display","none");
							$("#isExact_tr").css("display","none");
							$("#isLike_tr").css("display","none");
							$("#default_tr").css("display","none");
							
							$("#date_tr").css("display","none");
							$("#org_tr").css("display","none");
							$("#backup_data_tr").css("display","");
							$("#backup_set_tr").css("display","");
							$("#dialog_tr").css("display","none");
						}else if(selectType == "DIAlOG") {
							$("#dicCode_tr").css("display","none");
							$("#date_tr").css("display","none");
							$("#org_tr").css("display","none");
							$("#backup_data_tr").css("display","none");
							$("#backup_set_tr").css("display","none");
							$("#dialog_tr").css("display","");
						}
					});
					$("#isExact").click(function(){
						if($(this).prop("checked")==true) {
							$("#exactValueDiv").css("display","");
						}else {
							$("#exactValueDiv").css("display","none");
						}
					})
				},
				buttons: {
					"确定": function(e){ 
						var selectType = $("#selectType").find("option:selected").text();
						
						var selectTypeId = $("#selectType").find("option:selected").val();
						//默认值
						var defaultValue = $("#defaultValue").val();
						//是否按区域显示
						var isArea = $("#isArea").prop("checked");
						//精确查询
						var isExact = $("#isExact").prop("checked");
						//精确查询的条件
						var exactValue = $("#exactValue").find("option:selected").val();
						//模糊查询
						var isLike = $("#isLike").prop("checked");
						//日期格式
						var contentFormat = $("#contentFormat").find("option:selected").val();
						//组织选择框
						var orgInputMode = $("#orgInputMode").find("option:selected").val();
						//备选项来源
						var optionDataSource = $("input[name='optionDataSource']:checked").val();
						//备选项设置
						var optdata = $("#optdata").val();
						//字典名称
						var dictName = $("#dictName").val();
						//字典编码
						var dictCode = $("#dictCode").val();
						//数据源名称
						var dataSourceName = $("#dataSourceName").val();
						//数据源id
						var dataSourceId = $("#dataSourceId").val();
						//数据源选项的名称列
						var selectNameColumn = $("#selectNameColumn").find("option:selected").val();
						//数据源选项的值列
						var selectValueColumn = $("#selectValueColumn").find("option:selected").val();
						$("#selectField").setRowData(rowid,{selectType:selectType});
						$("#selectField").setRowData(rowid,{selectTypeId:selectTypeId});
						$("#selectField").setRowData(rowid,{defaultValue:defaultValue});
						$("#selectField").setRowData(rowid,{isArea:isArea});
						$("#selectField").setRowData(rowid,{isExact:isExact});
						$("#selectField").setRowData(rowid,{exactValue:exactValue});
						$("#selectField").setRowData(rowid,{isLike:isLike});
						$("#selectField").setRowData(rowid,{contentFormat:contentFormat});
						$("#selectField").setRowData(rowid,{orgInputMode:orgInputMode});
						$("#selectField").setRowData(rowid,{optionDataSource:optionDataSource});
						$("#selectField").setRowData(rowid,{optdata:optdata});
						$("#selectField").setRowData(rowid,{dictName:dictName});
						$("#selectField").setRowData(rowid,{dictCode:dictCode});
						$("#selectField").setRowData(rowid,{dataSourceName:dataSourceName});
						$("#selectField").setRowData(rowid,{dataSourceId:dataSourceId});
						$("#selectField").setRowData(rowid,{selectNameColumn:selectNameColumn});
						$("#selectField").setRowData(rowid,{selectValueColumn:selectValueColumn});
						$(this).dialog("close");
						$('#selectField').saveCell(iRow, iCol);
					},
					"取消": function(e){$(this).dialog("close");}
				},
				close : function(e){
					$('#selectField').saveCell(iRow, iCol);
				}
	 		});
	 	}
	 	$("#" + cellId, "#selectField").one('blur', function() {
	 		$('#selectField').saveCell(iRow, iCol);
		});
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
				//要干掉 2014-08-27 
				var tableType = "1";
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
			//干掉 2014-08-27
			var tableType = "2";
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
	
	
	$("#selectField").jqGrid (
			{
				datatype:"local",
				colNames:["uuid","标题","字段ID","查询类型","查询类型Id","查询默认值","是否按区域查询","是否精确查询","是否模糊查询","日期格式","组织选择框","备选项来源","备选项设置","字典名称","字典编码","数据源名称","数据源id","数据源选项的名称列","数据源选项的值列","sortOrder"],
				colModel:[
		          {
		        	  name:"uuid",
						index:"uuid",
						hidden : true
		          },
		          {
						name:"showName",
						index:"showName",
						width:"100",
						editable : true,
					},
				{
					name:"field",
					index:"field",
					width:"100",
					editable : true,
					edittype : "select",
					editoptions : {
						value:gettypesForFieldSelect(bean["tableDefinitionId"])
					},
				},
				{
					name:"selectType",
					index:"selectType",
					width:"100",
					editable : true,
				},
				{
					name:"selectTypeId",
					index:"selectTypeId",
					hidden : true
				},
				{
		        	  name:"defaultValue",
						index:"defaultValue",
						hidden : true
		          },
		          {
		        	  name:"isArea",
						index:"isArea",
						hidden : true
		          },
		          {
		        	  name:"isExact",
						index:"isExact",
						hidden : true
		          },
		          {
		        	  name:"isLike",
						index:"isLike",
						hidden : true
		          },
		          {
		        	  name:"contentFormat",
						index:"contentFormat",
						hidden : true
		          },
		          {
		        	  name:"orgInputMode",
						index:"orgInputMode",
						hidden : true
		          },
		          {
		        	  name:"optionDataSource",
						index:"optionDataSource",
						hidden : true
		          },
		          {
		        	  name:"optdata",
						index:"optdata",
						hidden : true
		          },
		          {
		        	  name:"dictName",
						index:"dictName",
						hidden : true
		          },
		          {
		        	  name:"dictCode",
						index:"dictCode",
						hidden : true
		          },
		          {
					  name:"dataSourceName",
						index:"dataSourceName",
						hidden : true
				},
				{
					  name:"dataSourceId",
						index:"dataSourceId",
						hidden : true
				},
				{
					  name:"selectNameColumn",
						index:"selectNameColumn",
						hidden : true
				},
				{
					  name:"selectValueColumn",
						index:"selectValueColumn",
						hidden : true
				},
		          {
		        	  name:"sortOrder",
						index:"sortOrder",
						hidden : true
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
					var formUuid = bean["tableDefinitionId"];
					afterEditCellParamsByField(rowid,cellname,value,iRow,iCol,formUuid);
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
						value:gettypes(bean["tableDefinitionId"])
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
				sortname : "uuid",
				sortable : true,
				sortorder : "asc",
				multiselect : true,
				autowidth : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					var formUuid = bean["tableDefinitionId"];
					afterEditCellParams(rowid,cellname,value,iRow,iCol,formUuid);
				}
			}
		);
	//查询条件的展示
	function selectConditionShow() {
		if($("#forFieldSelect").prop("checked") == true){
			$("#selectFieldDiv").css("display","");
		}else if($("#forFieldSelect").prop("checked") == false) {
			$("#selectFieldDiv").css("display","none");
		}
		if($("#forCondition").prop("checked") == true) {
			$("#a").css("display","");
		}else if($("#forCondition").prop("checked") == false){
			$("#a").css("display","none");
		}
//		var val=$('input:radio[name="exactKeySelect"]:checked').attr("id");
	}
	
	
	
	//动态生成jqgrid中的select内容
	function gettypes(formUuid){
		if(formUuid == null) {
			return;
		}
		var str="请选择:请选择;";
		var formuuid = {"formUuid":formUuid};
		var url = contextPath + '/basicdata/view/get_select_column.action';
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
			            	 str+=jsonobj[i].titleName+":"+jsonobj[i].titleName+";";
			            }else{
			              	 str+=jsonobj[i].titleName+":"+jsonobj[i].titleName;
			            }
			         }   
				}
			}
		});
		return str;
    }
	
	//动态生成jqgrid中的select内容(字段查询)
	function gettypesForFieldSelectTitle(formUuid){
		if(formUuid == null) {
			return;
		}
		var str="请选择:请选择;";
		var formuuid = {"formUuid":formUuid};
		var url = contextPath + '/basicdata/view/get_select_column.action';
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
			            	 str+=jsonobj[i].titleName+":"+jsonobj[i].titleName+";";
			            }else{
			              	 str+=jsonobj[i].titleName+":"+jsonobj[i].titleName;
			            }
			         }   
				}
			}
		});
		return str;
    }
	
	//动态生成jqgrid中的select内容(字段查询)
	function gettypesForFieldSelect(formUuid){
		if(formUuid == null) {
			return;
		}
		var str="请选择:请选择;";
		var formuuid = {"formUuid":formUuid};
		var url = contextPath + '/basicdata/view/get_select_column.action';
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
			            	 str+=jsonobj[i].columnAliase+":"+jsonobj[i].columnAliase+";";
			            }else{
			              	 str+=jsonobj[i].columnAliase+":"+jsonobj[i].columnAliase;
			            }
			         }   
				}
			}
		});
		return str;
    }
	
	
	//收集列数据 收集查询的条件属性
 	function collectColumn(bean) {
		var change1 = $("#column_list").jqGrid("getRowData");
		//给收集到的数据设置id
		for(var i=0;i<change1.length;i++){
			change1[i].id = i;
			change1[i].sortOrder = i;
		}
		bean["columnFields"] = change1;
		var change2 = $("#selectCond").getChangedCells('all');
		//给收集到的数据设置id
		for(var i=0;i<change2.length;i++) {
			var id = change2[i].id;
			change2[i].name = cache[id].name;
			change1[i].sortOrder = i;
		}
		selectbean["conditionTypeFields"] = change2;
		//收集查询字段的定义
//		var changeField = $("#selectField").getChangedCells('all');
		var changeField = $("#selectField").jqGrid("getRowData");
		for(var i=0;i<changeField.length;i++){
			changeField[i].id = i;
			changeField[i].sortOrder = i;
		}
		//给收集到的数据设置id
		selectbean["fieldSelectBeans"] = changeField;
//		alert("changeField " + JSON.stringify(changeField));
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
	$("#forFieldSelect").click(function() {
		$("#selectField").jqGrid("clearGridData");
		selectConditionShow();
	});
 	
	//查询定义按条件查询点击事件--是否展现备选项选择框
	$("#forCondition").click(function() {
		$("#selectCond").jqGrid("clearGridData");
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
	
	//新增查询字段
	$("#btn_add_field").click(function() {
		var mydata = {
				uuid : null
			};
		var newDate = new Date().getTime();
		$("#selectField").jqGrid("addRowData", newDate,mydata);
	});
	//删除查询字段
	$("#btn_del_field").click(function() {
		bean["selectDeleteFields"] = selectdebean;
		var rowids = $("#selectField").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择条件!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#selectField").getRowData(rowids[i]);
			if (rowData != null) {
				bean["selectDeleteFields"].fieldSelectBeans.push(rowData);
			}
			$("#selectField").jqGrid('delRowData', rowids[i]);
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
		$("#selectField").clearForm(true);
		$("#selectField").jqGrid("clearGridData");
		selectConditionShow();
		//设置自定义按钮
//		setCustomButton();
		hideDelButton();
	});
	//设置自定义按钮
	function setCustomButton() {
		var code = "003006";
		JDS.call({
			service:"getViewDataNewService.getCustomButtonByCode",
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
		JDS.call({ 
			service:"viewDefinitionNewService.saveBean",
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
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录！");
				return true;
			}
		} 
		var viewName = $("#list").getRowData(bean.id)["viewName"];
		if (confirm("确定要删除资源[" + viewName + "]吗？")) {
			JDS.call({
				service:"viewDefinitionNewService.deleteById",
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
				service:"viewDefinitionNewService.deleteAllById",
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
		if (grid = $('.ui-jqgrid-btable[id=column_list],.ui-jqgrid-btable[id=selectCond],.ui-jqgrid-btable[id=selectField]')) {
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