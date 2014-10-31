$(function(){
	
	var bean = {
			"uuid":null,
			"dataSourceProfileName":null,
			"dataSourceProfileId":null,
			"dataSourceProfileNum":null,
			"outDataSourceType":null,
			"databaseType":null,
			"databaseSid": null,
			"host": null,
			"port":null,
			"owner":null,
			"userName":null,
			"passWord":null,
	};
	
	$("#outDataSourceType").change(function(e) {
		var val = $(this).val();
		if(val == 1) {
			$(".outDataSource").css("display","");
		}else {
			$(".outDataSource").css("display","none");
		}
	})
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	//清空表单，选择框
	function clear() {
		//清空表单
		$("#column_form").clearForm(true);
	}
	//收集列定义数据
	function collectColumn(bean) {
	}
	
	//保存操作
	$("#btn_save").click(function() {
		$("#column_form").form2json(bean);
		alert("bean " + JSON.stringify(bean));
		JDS.call({
			service:"dataSourceProfileService.save",
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
	$("#list").jqGrid (
			$.extend($.common.jqGrid.settings, {
				url:ctx + '/common/jqgrid/query?queryType=dataSourceProfile',
				mtype:"POST",
				datatype:"json",
				colNames:["uuid","外部数据源名称","编号","ID"],
				colModel:[{
					name:"uuid",
					index:"uuid",
					hidden:true,
				},{
					name:"dataSourceProfileName",
					index:"dataSourceProfileName",
					width:"50"
				},{
					name:"dataSourceProfileNum",
					index:"dataSourceProfileNum",
					width:"30"
				},{
					name:"dataSourceProfileId",
					index:"dataSourceProfileId",
					width:"30"
				}
				],
				rowNum : 20,
				rownumbers : true,
				rowList : [ 10, 20, 50, 100, 200 ],
				rowId : "uuid",
				pager : "#pager",
				sortname : "dataSourceProfileNum",
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
	
	//测试配置是否能连接成功
	$("#dataSourceLinkTest").click(function() {
		var databaseType = $("#databaseType").val();
		var databaseSid = $("#databaseSid").val();
		var host = $("#host").val();
		var port = $("#port").val();
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		JDS.call({
			service:"dataSourceProfileService.jdbcTest",
			data:[databaseType,databaseSid,host,port,userName,passWord],
			success:function(result) {
				alert("测试连接成功");
			},
			error:function(result) {
				alert("测试连接不成功,请检查配置是否有误!");
			}
		});
	}); 
	//根据数据配置id获取数据配置
	function getDataSourceProfileById(id) {
		JDS.call({
			service:"dataSourceProfileService.getBeanById",
			data:[id],
			success:function(result) {
				bean = result.data;
				if(bean.outDataSourceType == 1) {
					$(".outDataSource").css("display","");
				}
				$("#column_form").json2form(bean);
			}
		});
	}

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
		$(".outDataSource").css("display","none");
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
		var dataSourceProfileName = $("#list").getRowData(bean.id)["dataSourceProfileName"];
		if (confirm("确定要删除配置[" + dataSourceProfileName + "]吗？")) {
			JDS.call({
				service:"dataSourceProfileService.deleteById",
				data:[bean.id],
				success:function(result) {
					alert("删除成功!!");
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
				service:"dataSourceProfileService.deleteAllById",
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
	
	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {}
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