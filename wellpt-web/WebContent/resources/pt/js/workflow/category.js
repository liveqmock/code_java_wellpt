//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
$(function() {
	var bean = {
		"uuid" : null,
		"name" : null,
		"code" : null,
		"parentName" : null,
		"parentUuid" : null
	};
	$("#list").jqGrid($.extend($.common.jqGrid.settings, {
		url : ctx + '/common/jqgrid/query?queryType=flowCategory',
		colNames : [ "uuid", "名称", "编号" ],
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
			width : "180"
		} ],// 行选择事件
		onSelectRow : function(id) {
			var rowData = $(this).getRowData(id);
			$("#category_form").json2form(rowData);
			$("#category_form").form2json(bean);
			$("#btn_del").show();
		}
	}));

	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();

	// 新增操作
	$("#btn_add").click(function() {
		$("#category_form").clearForm(true);
		$("#btn_del").hide();
	});

	// 保存流程分类
	$("#btn_save").click(function() {
		$("#category_form").form2json(bean);
		JDS.call({
			service : "flowCategoryService.saveBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// toForm(bean, $("#category_form"));
				$("#category_form").json2form(bean);

				$("#list").trigger("reloadGrid");
			}
		});
	});

	// 删除操作
	$("#btn_del").click(function() {
		if (bean.uuid == null || bean.uuid == "") {
			alert("请选择记录!");
			return true;
		}
		var name = bean.name;
		if (confirm("确定要删除流程分类[" + name + "]吗?")) {
			JDS.call({
				service : "flowCategoryService.removeByPk",
				data : [ bean.uuid ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});
	// 批量删除操作
	$("#btn_del_all").click(function() {
		var rowids = $("#list").jqGrid('getGridParam', 'selarrrow');
		if (rowids.length == 0) {
			alert("请选择记录!");
			return true;
		}
		if (confirm("确定要删除所选记录吗?")) {
			JDS.call({
				service : "flowCategoryService.removeAllByPk",
				data : [ rowids ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新列表
					$("#list").trigger("reloadGrid");
				}
			});
		}
	});

	// 列表查询
	$("#query_category").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#btn_query").trigger("click");
		}
	});
	$("#btn_query").click(function(e) {
		var queryValue = $("#query_category").val();
		var postData = {
			"queryPrefix" : "query",
			"queryOr" : true,
			"query_LIKES_name_OR_code" : queryValue
		};
		$("#list").jqGrid("setGridParam", {
			postData : null
		});
		$("#list").jqGrid("setGridParam", {
			postData : postData,
			page : 1
		}).trigger("reloadGrid");
	});

	// 页面布局
	Layout.layout();
});