$(function() {
	var bean = {
		"uuid": null,	
		"moduleId": null,
		"code": null,	
		"title": null,
		"cateUuid": null,
		"cateName": null,
		"name" : null,
		"catalog" : null,
		"type" : null,
		"role" : null,
		"path" : null,
		"tplDir" : null,
		"defaultTplStyle" : null,
		"jsDir" : null,
		"defaultWidth" : 200,
		"defaultHeight" : 100,
		"defaultRows" : null,
		"isEdit" : null,
		"htmlContent" : null,
		"moreUrl" : null,
		"columns" : null	
	};
	$("#isEdit").change(function(){
		var flag = $(this,"option:selected").val();
		if(flag=="true"){
			$(".pathClass").hide();
			$(".urlClass").hide();
			$(".htmlContentClass").show();
		}else if(flag=="false"){
			$(".htmlContentClass").hide();
			$(".urlClass").hide();
			$(".pathClass").show();
		}
		else if(flag=="other"){
			$(".htmlContentClass").hide();
			$(".pathClass").hide();
			$(".urlClass").show();
		}
	});
	
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
	$("#viewName").comboTree({
		labelField: "viewName",
		valueField: "path",
		treeSetting : setting,
		width: 250,
		height: 220
	});
	
//	var options = new Object();
//	options.labelField = "viewName";
//	options.valueField = "path";
//	options.width = 250;
//	options.height = 220;
//	$.dyviewTree.open(options);
	
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url :  ctx +"/common/jqgrid/query?queryType=module",
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "名称", "编号", "标题", "分类" ],
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
				name : "code",
				index : "code",
				width : "30"
			},{
				name : "title",
				index : "title",
				width : "30"
			},{
				name : "cateName",
				index : "cateName",
				width : "30"
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
				getModuleById(id);
	//			var rowData = $(this).getRowData(id);
	//			getModuleById(rowData.uuid);
			},
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据模块ID获取模块信息
	function getModuleById(uuid) {
		$.ajax({
			type : "POST",
			url : ctx +"/cms/module/get",
			data : uuid,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				bean = result.data.bean;
				showDelButton();
				var option = result.data.option;
				$("#cateUuid").html(option);
//				var viewSelect = result.data.viewSelect;
//				$("#path").html(viewSelect);
				$("#module_form").json2form(bean);
				//toForm(bean, $("#module_form"));
				var views = result.data.views;
				if(bean.path != "" && bean.path != null){
					for(var key in views){
						if(bean.path.split("=")[1]==views[key].uuid){
							$("#viewName").val(views[key].name);
						}
					}
				}
				if(bean.path != "" && bean.path != null){
					$("#path").val(bean.path.split("=")[1]);
				
					//自定义视图的处理
					$("#customPath").val(bean.path);
				}
				
				CKEDITOR.instances.htmlContent.setData(bean.htmlContent);
				
				if(bean.isEdit=="true"){
					$(".pathClass").hide();
					$(".urlClass").hide();
					$(".htmlContentClass").show();
				}else if(bean.isEdit=="false"){
					$(".htmlContentClass").hide();
					$(".urlClass").hide();
					$(".pathClass").show();
				}else if(bean.isEdit=="other"){
					$(".htmlContentClass").hide();
					$(".pathClass").hide();
					$(".urlClass").show();
				} 
			},
			error : function(result) {
			}
		});
	}

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();

	// 新增
	$("#btn_add").click(function() {
		$("#module_form").clearForm(true);
		$("#defaultWidth").val(200);
		$("#defaultHeight").val(100);
		$("#isEdit").val("false");
		$(".htmlContentClass").hide();
		$(".pathClass").show();
		hideDelButton();
		
	});
	//隐藏删除按钮
	function hideDelButton(){
		$("#btn_del").hide();
	}
	//显示删除按钮
	function showDelButton(){
		$("#btn_del").show();
	}


	// 保存
	$("#btn_save").click(function() {
		$("#htmlContent").val(CKEDITOR.instances.htmlContent.getData());
		$("#cateName").val($("#cateUuid").find("option:selected").text());
		if(!($("#defaultWidth").val().match(/^\+?[1-9][0-9]*$/)||$("#defaultHeight").val().match(/^\+?[1-9][0-9]*$/))){
			alert("宽高请输入合法的数字");
			return false;
		}
		$("#module_form").form2json(bean);
		// toObject($("#module_form"), bean);
		
		if($("#isEdit").val()=="other"){
			bean.path = $("#customPath").val();
		}else if($("#isEdit").val()=="false"){
			bean.path = "/basicdata/dyview/view_show?viewUuid="+bean.path;
		}
		delete bean["customPath"];
		$.ajax({
			type : "POST",
			url :ctx +"/cms/module/save/",
			data : JSON.stringify(bean),
			contentType : "application/json",
			dataType : "text",
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新列表
				$("#list").trigger("reloadGrid");
				showDelButton();
			},
			error : function(result) {
			}
		});
	});

	// 删除
	$("#btn_del").click(function() {
		if ($(this).attr("id") == "btn_del") {
			var ids = $("#list").jqGrid('getGridParam', 'selrow');
			if (ids.length == 0) {
				alert("请选择记录！");
				return true;
			}
		} else {
			if (bean.id == "" || bean.id == null) {
				alert("请选择记录 ！");
				return true;
			}
		}
		var name = bean.name;
		if (confirm("确定要删除页面元素[" + name + "]吗？")) {
			$.ajax({
				type : "POST",
				url :  ctx + "/cms/module/delete/",
				data : JSON.stringify(bean),
				contentType : "application/json",
				dataType : "text",
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				},
				error : function(result) {
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
		var ids = rowids.join(",");
		if (confirm("确定要删除所选资源？")) {
			$.ajax({
				type : "POST",
				async : false,
				url : ctx + "/cms/module/deleteAll/",
				data : {"ids" : ids},
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				},
				error : function(result) {
				}
			});
		}
	});
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_name_OR_code_OR_title_OR_cateName" : queryValue,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	$("#query_keyWord").keypress(function(event) {
		var code = event.keyCode;
	    if (code == 13) {
	    	var queryValue = $("#query_keyWord").val();
			$("#list").jqGrid("setGridParam",{
				postData : {
					"queryPrefix" : "query",
					"queryOr" : true,
					"query_LIKES_name_OR_code_OR_title_OR_cateName" : queryValue,
				},
				page : 1
			}).trigger("reloadGrid");
	    }
	});
	Layout.layout({
		west__size : 480
	});
});
