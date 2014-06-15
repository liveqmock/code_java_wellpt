$(function() {
	var bean = {
		"uuid" : null,
		"id" : null,
		"name" : null,
		"title" : null,
		"type" : null,
		"users" : null,
		"cssContent" : null,
		"pageType" : null,
		"code" : null
	};
	$("#userNames").focus(function() {
		$.unit.open({
			labelField : "userNames",
			valueField : "users",
			selectType : 1
		});
	});
	$("#list").jqGrid(
		$.extend($.common.jqGrid.settings, {
			url : ctx + '/common/jqgrid/query?queryType=cmsPage',
			datatype : 'json',
			mtype : "POST",
			colNames : [ "uuid", "名称", "编号", "类型", "所有者" ],
			colModel : [ {
				name : "uuid",
				index : "uuid",
				width : "180",
				hidden : true,
				key : true
			}, {
				name : "name",
				index : "name",
				width : "50"
			}, {
				name : "code",
				index : "code",
				width : "30"
			}, {
				name : "type",
				index : "type",
				width : "40",
				formatter : function(cellvalue, options, rowObject) {
					if (cellvalue == "default") {
						return '默认页面';
					} else if (cellvalue == "common") {
						return '';
					}
					return cellvalue;
				}
			}, {
				name : "userNames",
				index : "userNames",
				width : "50"
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
			height : 400,
			scrollOffset : 0,
			jsonReader : {
				root : "dataList",
				total : "totalPages",
				page : "currentPage",
				records : "totalRows",
				repeatitems : false
			},// 行选择事件
			onSelectRow : function(id) {
				getPgeById(id);
				// var rowData = $(this).getRowData(id);
				// getPgeById(rowData.uuid);
			},
	
			loadComplete : function(data) {
				$("#list").setSelection($("#list").getDataIDs()[0]);
			}
		}));

	// 根据模块ID获取模块信息
	function getPgeById(uuid) {

		$.ajax({
			type : "POST",
			url : ctx + "/cms/cmspage/get",
			data : uuid,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				var option = result.data.option;
				$("#cssContent").html(option);
				bean = result.data.bean;
				showDelButton();
				$("#page_form").json2form(bean);
				//给值非true的复选框勾选
				if(bean.type=="default"){
					$("#type").attr("checked",true);
				}
				if(bean.pageType=="index"){
					$("#pageType").attr("checked",true);
				}
				
				
			},
			error : function(result) {
			}
		});
	}

	// 新增页面信息
	$("#btn_add").click(function() {
		$("#page_form").clearForm(true);
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

	// 保存页面信息
	$("#btn_save").click(function() {
		$("#page_form").form2json(bean);
		if(bean.type==true){
			bean.type = "default";
		}else{
			bean.type = "common";
		}
		if(bean.pageType==true){
			bean.pageType = "index";
		}else{
			bean.pageType = "category";
		}
		$.ajax({
			type : "POST",
			url : ctx + "/cms/cmspage/save/",
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

	// 配置内容页面信息
	$("#btn_config").click(function() {
		$("#page_form").form2json(bean);
		// toObject($("#page_form"), bean);

		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录 ！");
			return true;
		}
		window.open(ctx + "/pt/cms/cmsconfig.jsp?uuid=" + bean.uuid);
	});
	// 预览
	$("#btn_preview").click(function() {
		$("#page_form").form2json(bean);
		// toObject($("#page_form"), bean);

		if (bean.uuid == "" || bean.uuid == null) {
			alert("请选择记录 ！");
			return true;
		}
		window.open(ctx + "/pt/cms/index.jsp?uuid=" + bean.uuid);
	});

	// 删除页面信息
	$("#btn_del").click(function() {
		if ($(this).attr("id") == "btn_del") {
			var ids = $("#list").jqGrid('getGridParam', 'selrow');
			alert(ids);
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
		if (confirm("确定要删除页面[" + name + "]吗？")) {
			$.ajax({
				type : "POST",
				url : ctx + "/cms/cmspage/delete/",
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
	// 批量删除操作
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
				url : ctx + "/cms/cmspage/deleteAll/",
				data : {
					"ids" : ids
				},
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
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$("#tabs").tabs();
	
	$("#btn_query").click(function() {
		var queryValue = $("#query_keyWord").val();
		var queryOther = "";
		if(queryValue.indexOf("默")>-1||queryValue.indexOf("认")>-1){
			queryOther = "default";
		}else if(queryValue.indexOf("普")>-1||queryValue.indexOf("通")>-1){
			queryOther = "common";
		}
		$("#list").jqGrid("setGridParam",{
			postData : {
				"queryPrefix" : "query",
				"queryOr" : true,
				"query_LIKES_name_OR_code_OR_userNames" : queryValue,
				"query_EQS_type" : queryOther,
			},
			page : 1
		}).trigger("reloadGrid");
	});
	$("#query_keyWord").keypress(function(event) {
		var code = event.keyCode;
	    if (code == 13) {
	    	var queryValue = $("#query_keyWord").val();
	    	var queryOther = "";
	    	if(queryValue.indexOf("默")>-1||queryValue.indexOf("认")>-1){
	    		queryOther = "default";
	    	}else if(queryValue.indexOf("普")>-1||queryValue.indexOf("通")>-1){
	    		queryOther = "common";
	    	}
	    	$("#list").jqGrid("setGridParam",{
	    		postData : {
	    			"queryPrefix" : "query",
	    			"queryOr" : true,
	    			"query_LIKES_name_OR_code_OR_userNames" : queryValue,
	    			"query_EQS_type" : queryOther,
	    		},			page : 1
	    	}).trigger("reloadGrid");
	    }
	});
	
	Layout.layout({
		west__size : 480
	});

});
