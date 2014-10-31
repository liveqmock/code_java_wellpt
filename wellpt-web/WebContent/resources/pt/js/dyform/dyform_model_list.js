//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
// 加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dytable/dytable");

$(function() {
	
	$('#list').jqGrid({
		treeGrid : true,
		treeGridModel : 'adjacency',
		ExpandColumn : 'outerId',
		url : contextPath + '/dyformmodel/list/tree ' ,
		mtype : 'POST',
		datatype : 'json',
		colNames : [ 'uuid', '单据ID', '单据名称', '表单ID', '预览' ],
		colModel : [ {
			name : 'uuid',
			index : 'uuid',
			width : 150,
			hidden : true,
			key : true
		}, {
			name : 'outerId',
			index : 'outerId',
			width : 50
		},
		 {
			name : 'displayName',
			index : 'displayName',
			width : 200
		},
		 {
			name : 'referredFormId',
			index : 'referredFormId',
			//hidden : true,
			width : 200
		} ,
		 {
			name : 'preview',
			index : 'preview',
			//hidden : true,
			width : 200
		} ], 
		rowNum : 500,
		viewrecords : true,
		pager : "#pager",
		height : 'auto',
		scrollOffset : 0, 
		jsonReader : {
			root : "dataList",
			total : "totalPages",
			page : "currentPage",
			records : "totalRows"
		},
		ondblClickRow : function(id) {// 双击视图打开新界面
			var uuid = id;
			if (uuid && uuid.length > 0) {
				openModelDefinition(  uuid);
			}
		},
	});

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();

	// 新增动态表单
	$("#btn_add").click(function() {
		var flag = $('#flag').val();
		openModelDefinition();
	});
	// 编辑动态表单
	$("#btn_edit").click(function() {
		var uuid = $('#list').jqGrid('getGridParam', 'selrow'); 
		if (uuid && uuid.length > 0) {
			 
			openModelDefinition(uuid);
		} else {
			alert(dymsg.selectRecordMod);
		}
	});

	// 删除动态表单
	 $("#btn_del").click(function() {
		var uuid = $('#list').jqGrid('getGridParam', 'selrow');
		if (uuid && uuid.length > 0) {
			if (confirm(dymsg.delConfirm)) {
				$.ajax({
					type : "POST",
					url : ctx + "/dyformmodel/delete",
					data : {uuid:uuid}, 
					dataType : "json",
					success : function(result) {  
						if (result.success) {
							alert(dymsg.delSuccess);
							$('#list').trigger('reloadGrid');
						}
					},
					error:function(result){ 
						var responseText = result.responseText;
						responseText = eval("(" + responseText + ")"); 
						alert(responseText.data);
					}
				});
			}
		} else {
			alert(dymsg.selectRecordDel);
		}
	}); 

	$("#btn_open").click(function() { 
		var uuid = $('#list').jqGrid('getGridParam', 'selrow');// 获取选中行的ID 
		 var referredFormId = $('#list').jqGrid('getCell',uuid ,"referredFormId"); //取得参照表单 
		var formDefinition =  loadFormDefinitionDataByFormId(referredFormId);
		alert( formDefinition.displayFormModelId);
		//console.log(JSON.cStringify(formDefinition));
		formDefinition.displayAsFormModel = true;
		 formPreview(formDefinition);  
	});
	function openModelDefinition( uuid) {
		var url = "";
		if(uuid){
			 url = ctx + "/dyformmodel/openDisplayModel?uuid=" + uuid  ;
		}else{
			 url = ctx + "/dyformmodel/openDisplayModel"  ;
		}
		
		window.open(url);
	}

	// 列表查询
	$("#query_keyWord").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});

	// 视图查询方法
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		$("#list").jqGrid("setGridParam", {
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_displayName_OR_name_OR_version_OR_moduleName_OR_outerId" : queryValue,
			},
			page : 1
		}).trigger("reloadGrid");
	});

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var paneWidth = pane.width();
				var paneHeight = pane.height() - 25;
				if (Browser.isIE()) {// 检测是否是IE浏览器
					$(this).setGridWidth(paneWidth - 22);
					$(this).setGridHeight(paneHeight - 84);
				} else if (Browser.isChrome()) {// 检测是否是chrome浏览器
					$(this).setGridWidth(paneWidth - 30);
					$(this).setGridHeight(paneHeight - 114);
				} else if (Browser.isMozila()) {// 检测是否是Firefox浏览器
					$(this).setGridWidth(paneWidth - 22);
					$(this).setGridHeight(paneHeight - 84);
				} else {
					$(this).setGridWidth(paneWidth);
					$(this).setGridHeight(pane.height());
				}
			});
		}
	}
	// JQuery layout布局
	$('#container').layout({
		center : {
			closable : false,
			resizable : false,
			slidable : false,
			onresize : resizeJqGrid,
			minSize : 500,
			triggerEventsOnLoad : true
		}
	});
});