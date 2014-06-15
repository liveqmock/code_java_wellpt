$(function() {
	//json用来存放模块真实值：显示值
	var  variable = {};
	getModuleShowName();
	//用来存放显示值：真实值（用于模块名查找）
	var  variable1 = {};
	getModuleShowName1();
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
	
	//获取模块名的显示值
	function getModuleShowName(){
		JDS.call({
			service : "systemTableService.getAllModuleName",
			data : [  ],
			async: false,
			success : function(result) {
				$.each(result.data, function(){
					variable[this.code] = this.name;
				});
			}
		});
	}
	//获取模块名的显示值
	function getModuleShowName1(){
		JDS.call({
			service : "systemTableService.getAllModuleName",
			data : [  ],
			async: false,
			success : function(result) {
				$.each(result.data, function(){
					variable1[this.name] = this.code;
				});
			}
		});
	}
	
	// 系统数据子表（属性表）id
	var childSystemTableListId = "child_systemtable_list";
	
	var bean = {
		"uuid" : null,
		"tableName" : null,
		"fullEntityName" : null,
		"chineseName" : null,
		"code" : null,
		"remark" : null,
		"moduleName" : null,
		"attributes" : [],
		"relationships" : [],
		"changedAttributes" : [],
		"changedRelationships" : [],
		"deletedRelationships" : []
	};
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=systemTable',
			mtype : 'POST',
			datatype : "json",
			colNames : [ "uuid", "编号", "中文名", "表名", "完全限定名", "模块名" ],
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
				name : "chineseName",
				index : "chineseName",
				width : "180"
			}, {
				name : "tableName",
				index : "tableName",
				width : "180"
			}, {
				name : "fullEntityName",
				index : "fullEntityName",
				width : "180"
			}, {
				name : "moduleName",
				index : "moduleName",
				width : "180",
				formatter : function(cellvalue, options, rowObject) {
					if(cellvalue == null||cellvalue == ""){
						return "";
					}
					return variable[cellvalue];
				}
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
				var rowData = $(this).getRowData(id);
				getSystemTableById(rowData.uuid);
	//			$("#main_secondary_table_relationship").jqGrid("clearGridData");
	//			getViewById(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));
	
		//jqgrid列表重载
		function getViewById(uuid) {
			JDS.call({
				service : "systemTableService.getBeanById",
				data : [ uuid ],
				success : function(result) {
					bean = result.data;

					$("#main_secondary_table_relationship_list").jqGrid('setGridParam',{ 
						colNames : [ "uuid", "主表名", "从表名", "主从表关系" ,"关联属性"],
						colModel : [ {
							name : "1",
							index : "1",
							width : "180",
						}, 
						             {
							name : "uuid",
							index : "uuid",
							width : "180",
							hidden : true
						}, {
							name : "mainTableName",
							index : "mainTableName",
							width : "180",
							editable : true
						}, {
							name : "secondaryTableName",
							index : "secondaryTableName",
							width : "180",
							editable : true
						}, {
							name : "tableRelationship",
							index : "tableRelationship",
							width : "180",
							editable : true,
							edittype : "select",
							editoptions : {
								value:"1:一对一;2:一对多;3:多对一;4:多对多"
							}
						}, {
							name : "associatedAttributes",
							index : "associatedAttributes",
							width : "180",
							editable : true,
						} ],
						sortable : false,
						multiselect : true,
						cellEdit : true,// 表示表格可编辑
						cellsubmit : "clientArray", // 表示在本地进行修改
						autowidth : true,
						scrollOffset : 0,
						afterEditCell : function(rowid, cellname, value, iRow, iCol) {
							// Modify event handler to save on blur.
							$("#" + iRow + "_" + cellname, "#main_secondary_table_relationship_list")
							.one(
									'blur',
									function() {
										$('#main_secondary_table_relationship_list').saveCell(
												iRow, iCol);
									});
						}
					}).trigger("reloadGrid"); //重新载入 
				}
			});
			
		
		}
	
	//获取关联属性
	function getAssociatedAttributes(){
		var str="请选择:请选择;";
		JDS.call({
			service : "systemTableService.getAssociatedAttributes",
			data : [ bean ],
			validate : true,
			async:true,//设为同步
			success : function(data) {
				if (data != null) {
					var jsonobj=eval(data.data);
//					alert("jsonobj:"+JSON.stringify(jsonobj));
			        var length=jsonobj.length;
			        for(var i=0;i<length;i++){
			        	if(i!=length-1){
			        		str+=jsonobj[i]+":"+jsonobj[i]+";";
			        	}else{
			        		str+=jsonobj[i]+":"+jsonobj[i];
			        	}
			         }  
				}
			}
		});
		alert("关联属性:"+str);
		return str;
    }

	// 根据用户uuid获取系统表数据信息
	function getSystemTableById(uuid) {
		var systemtable = {};
		systemtable.uuid = uuid;
		JDS.call({
			service : "systemTableService.getBeanById",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				$("#system_table_form").json2form(bean);
				//根据主表uuid获取属性表的信息
				toChildSystemTableList(bean);
				//根据主表uuid获取关系表的信息
				toChildRelationshipsList(bean);
				//在初始化的时候要把显示值设进文本框中
				$("#showModuleName").comboTree("initValue", bean.moduleName);
//				$("#showRoleValue").comboTree("initValue", bean.roleValue);
			}
		});
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	// 新增操作（表关系）
	$("#relationship_btn_add").click(function() {
		var records = $("#main_secondary_table_relationship_list").getGridParam('records');
		$("#main_secondary_table_relationship_list").jqGrid("addRowData",(records+1),{"uuid":"","主表名":"","从表名":"","主从表关系":"","关联属性":""},"");
	});
	// 删除操作（表关系）
	$("#relationship_btn_del").click(function() {
		var rowids = $("#main_secondary_table_relationship_list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return;
		}
		for ( var i = (rowids.length - 1); i >= 0; i--) {
			var rowData = $("#main_secondary_table_relationship_list").getRowData(rowids[i]);
			if (rowData.uuid != null && $.trim(rowData.uuid) != "") {
				bean["deletedRelationships"].push(rowData);
			}
			$("#main_secondary_table_relationship_list").jqGrid('delRowData', rowids[i]);
		}
	});
	

	// 新增操作
	$("#btn_add").click(function() {
		$("#" + childSystemTableListId).jqGrid("clearGridData");
		$('#main_secondary_table_relationship_list').jqGrid("clearGridData");
		clear();
		hideDelButton();
	});
	function clear() {
		$("#system_table_form").clearForm(true);
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
		var tableName = bean.tableName;
		if (confirm("确定要删除表[" + tableName + "]吗？")) {
			JDS.call({
				service : "systemTableService.remove",
				data : [ bean ],
				success : function(result) {
					clear();
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	
	
	// 保存系统表,提交前必须验证
	$(".system_table_form").Validform({//指定具体参数，实现更多功能;
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
    		$("#system_table_form").form2json(bean);
    		
    		// 收集改变的属性表信息
    		collectAttributesList(bean);
    		// 收集改变的关系表信息
    		collectRelationshipsList(bean);
    		JDS.call({
    			service : "systemTableService.saveBean",
    			data : [ bean ],
    			async:false,
    			validate : true,
    			success : function(result) {
    				if(result.data){
    					// 保存成功刷新列表
        				alert("保存成功！");
        				$("#list").trigger("reloadGrid");
        				showDelButton();
    				} else {
    					alert("该类不存在");
    				}
    			}
    		});
        }
	});
	
	

	//系统表关系jqGrid
	$('#main_secondary_table_relationship_list').jqGrid({
		datatype : "local",
		colNames : [ "uuid", "主表名", "从表名", "主从表关系" ,"关联属性"],
		colModel : [ {
			name : "uuid",
			index : "uuid",
			width : "140",
			hidden : true
		}, {
			name : "mainTableName",
			index : "mainTableName",
			width : "140",
			editable : true
		}, {
			name : "secondaryTableName",
			index : "secondaryTableName",
			width : "140",
			editable : true
		}, {
			name : "tableRelationship",
			index : "tableRelationship",
			width : "70",
			editable : true,
			edittype : "select",
        	editoptions : {
        		value:"1:一对一;2:一对多;3:多对一;4:多对多"
        	}
		}, {
			name : "associatedAttributes",
			index : "associatedAttributes",
			width : "140",
			editable : true,
		} ],
		sortable : false,
		multiselect : true,
		cellEdit : true,// 表示表格可编辑
		cellsubmit : "clientArray", // 表示在本地进行修改
		autowidth : true,
		scrollOffset : 0,
		afterEditCell : function(rowid, cellname, value, iRow, iCol) {
			// Modify event handler to save on blur.
			$("#" + iRow + "_" + cellname, "#main_secondary_table_relationship_list")
					.one(
							'blur',
							function() {
								$('#main_secondary_table_relationship_list').saveCell(
										iRow, iCol);
							});
		}
	});
	
	
	
			//系统属性表jqGrid
			$("#" + childSystemTableListId).jqGrid({
				datatype : "local",
				colNames : [ "uuid", "列名", "中文名", "备注" ,"数据类型","列别名","数据字典","常量","组织选择框","所属类"],
				colModel : [ {
					name : "uuid",
					index : "uuid",
					width : "70",
					hidden : true
				}, {
					name : "attributeName",
					index : "attributeName",
					width : "70",
					editable : true
				}, {
					name : "chineseName",
					index : "chineseName",
					width : "50",
					editable : true
				}, {
					name : "remark",
					index : "remark",
					width : "40",
					editable : true
				}, {
					name : "columnType",
					index : "columnType",
					width : "50",
					editable : true,
					edittype : "select",
		        	editoptions : {
		        		value:"1:INTEGER;2:LONG;3:DOUBLE;4:DATE;5:STRING;6:BOOLEAN;7:CLOB"
		        	}
				} , {
					name : "columnAliases",
					index : "columnAliases",
					width : "70",
					editable : true,
				} , {
					name : "dataDictionary",
					index : "dataDictionary",
					width : "70",
					editable : true,
				} , {
					name : "constant",
					index : "constant",
					width : "50",
					editable : true,
				} , {
					name : "isOrganizeSelectionBox",
					index : "isOrganizeSelectionBox",
					width : "70",
					editable : true,
					edittype : "checkbox",
		        	editoptions : {
		        		value:"true:false"
		        	}
				}, {
					name : "entityName",
					index : "entityName",
					width : "70",
					hidden : true
				}],
				sortable : false,
				multiselect : true,
				cellEdit : true,// 表示表格可编辑
				cellsubmit : "clientArray", // 表示在本地进行修改
				autowidth : true,
				autoScroll: true, 
				afterEditCell : function(rowid, cellname, value, iRow, iCol) {
					// Modify event handler to save on blur.
					$("#" + iRow + "_" + cellname, "#" + childSystemTableListId)
							.one(
									'blur',
									function() {
										$('#' + childSystemTableListId).saveCell(
												iRow, iCol);
									});
				}
			});
	
			// 设置系统表数据的属性表
			function toChildSystemTableList(bean) {
				var $attributesList = $("#" + childSystemTableListId);
				//清空属性表列表
				$attributesList.jqGrid("clearGridData");
				var attributes = bean["attributes"];
				for ( var index = 0; index < attributes.length; index++) {
					$attributesList.jqGrid("addRowData", attributes[index].uuid,
							attributes[index]);
				}
			}
			
			// 收集改变的属性表信息
			function collectAttributesList(bean){
				var changes = $("#" + childSystemTableListId).getChangedCells('all');
				bean["attributes"] = [];
				bean["changedAttributes"] = changes;
			}
			
			// 设置系统表数据的关系表
			function toChildRelationshipsList(bean) {
				var $relationshipsList = $('#main_secondary_table_relationship_list');
				//清空关系表列表
				$relationshipsList.jqGrid("clearGridData");
				var relationships = bean["relationships"];
				for ( var index = 0; index < relationships.length; index++) {
					$relationshipsList.jqGrid("addRowData", relationships[index].uuid,
							relationships[index]);
				}
			}
			
			// 收集改变的关系表信息
			function collectRelationshipsList(bean){
				var changes =  $('#main_secondary_table_relationship_list').getChangedCells('all');
				bean["relationships"] = [];
				bean["changedRelationships"] = changes;
			}
	
	/*//角色真实值
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "dataDictionaryService",
				"methodName" : "getFromTypeAsTreeAsync",
				"data" : "DATA_PERMISSION"
			}
		}
	};
	
	$("#showRoleValue").comboTree({
		labelField : "showRoleValue",
		valueField : "roleValue",
		treeSetting : setting,
		initServiceParam:["DATA_PERMISSION"]
	}); */

	//模块名
	var setting2 = {
		async : {
			otherParam : {
				"serviceName" : "dataDictionaryService",
				"methodName" : "getFromTypeAsTreeAsync",
				"data" : "MODULE_ID"
			}
		},
		 check:{//设置只能为单选
		    enable:true,
		    chkStyle:"radio",
		    radioType:"all"
		 } 
	};
	 $("#showModuleName").comboTree({
		labelField : "showModuleName",
		valueField : "moduleName", 
		treeSetting : setting2,
		initServiceParam:["MODULE_ID"]
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
					service:"systemTableService.deleteAllById",
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
			var queryShowModuleName = variable1[$("#query_keyWord").val()];
			if(queryValue.indexOf("实")>-1||queryValue.indexOf("体")>-1){
				queryOther = "entity";
			}else if(queryValue.indexOf("动")>-1||queryValue.indexOf("态")>-1||queryValue.indexOf("表")>-1||queryValue.indexOf("单")>-1){
				queryOther = "formdefinition";
			}
			$("#list").jqGrid("setGridParam",{
				postData : {
					"queryPrefix" : "query",
					"queryOr" : true,
					"query_LIKES_code_OR_chineseName_OR_tableName_OR_fullEntityName" : queryValue,
					"query_EQS_moduleName" : queryShowModuleName,
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
			if (grid = $('.ui-jqgrid-btable[id=child_systemtable_list]')) {
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
			if (grid = $('.ui-jqgrid-btable[id=main_secondary_table_relationship_list]')) {
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
			center_onresize : resizeJqGrid
		});
});