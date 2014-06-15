$(function() {
	//获得所有的cms页面
	$.ajax({
		type : "POST",
		async:false,
		url : ctx + "/cms/cmspage/getallcmspage",
		success : function(result) {
			rscmspages = result.cmspages;
			window.rsmodule = result.module;
			var option = "";
			for(var key in rscmspages){
				option+= "<option value='"+rscmspages[key].url+"'>"+rscmspages[key].name+"</option>";
			}
			$("#pageUrl").html(option);
		}
	});
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "cmsService",
				"methodName" : "getMdouleAsTreeAsync",
			}
		},
		check : {
			enable : true,
			chkStyle : "radio"
		},
		callback : {
//			onClick: treeNodeOnClick,
		}
	};
	$("#moduleName").comboTree({
		labelField: "moduleName",
		valueField: "moduleId",
		treeSetting : setting,
		width: 250,
		height: 220
	});
	function treeNodeOnClick(event, treeId, treeNode) {
		$("#moduleId").val(treeNode.id);
		$("#moduleName").val(treeNode.name);
		$("#moduleName").comboTree("hide");
	}
	// 最新选择的树结点
	var latestSelectedNode = null;
	// 数据字典表单ID
	var datadict_form_selector = "#datadict_form";
	// 数据字典对ID
	var datadict_tree_selector = "#datadict_tree";
	var bean = {
		"uuid" : null,
		"code" : null,
		"ecode" : null,
		"moduleId" : null,
		"divId" : null,
		"icon" : null,
		"inputUrl" : null,
		"jsContent" : null,
		"newPage" : null,
		"openType" : null,
		"pageUrl" : null,
		"showNum" : null,
		"openSearch" : null,
		"title" : null,
		"remark" : null,
		"fullWindow":null
	};
	// JQuery zTree设置
	var dataDataDictionarySetting = {
		async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
				"serviceName" : "cmsService",
				"methodName" : "getAsTreeAsync"
			},
			type : "POST"
		},
		callback : {
			beforeClick : beforeClick
		}
	};
	// JQyery zTree加载数据字典树结点
	function loadDataDictionaryTree() {
		$.fn.zTree.init($(datadict_tree_selector), dataDataDictionarySetting);
	}
	loadDataDictionaryTree();
	// 树结点点击处理
	function beforeClick(treeId, treeNode) {
		// 最新选择的树结点
		latestSelectedNode = treeNode;
		if (treeNode.id != null && treeNode.id != -1) {
			// 查看详细
			getCmsCatecory(treeNode.id);
		}
		return true;
	}
	// 根据uuid获取详细信息
	function getCmsCatecory(uuid) {
		clear();
		$(".openType").each(function(){
			$("#"+$(this).val()).parent().parent().hide();
		});
		JDS.call({
			service : "cmsService.getCmsCategoryBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				showDelButton();
				$(datadict_form_selector).json2form(bean);
				if(bean.newPage=="windows"){
					$("#divId").parent().parent().show();
				}else{
					$("#divId").parent().parent().hide();
				}
				if(bean.moduleId!=null||bean.moduleId!=undefined||bean.moduleId==""){
					for(var key in window.rsmodule){
						if(bean.moduleId==window.rsmodule[key].uuid){
							$("#moduleName").val(window.rsmodule[key].name);
						}
					}
				}
				if(bean.openType=="dialog"){
				}else{
					$("#"+bean.openType).parent().parent().show();
				}
				if(bean.fullWindow=="yes"){
					$("#fullWindow").attr("checked",true);
				}
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

	
	// 新增操作
	$("#btn_add").click(function() {
		$(".openType").each(function(){
			$("#"+$(this).val()).parent().parent().hide();
		});
		clear();
		hideDelButton();
	});
	$(".newPage").click(function() {
		var id = $(this).val();
		if(id=="windows"){
			$("#divId").parent().parent().show();
		}else{
			$("#divId").parent().parent().hide();
		}
	});
	$(".openType").click(function() {
		$(".openType").each(function(){
			$("#"+$(this).val()).parent().parent().hide();
		});
		var id = $(this).val();
		$("#"+id).parent().parent().show();
	});
	// 保存群组信息
	$("#btn_save").click(function() {
		// 收集表单数据
		// toObject($(datadict_form_selector), bean);
		$(datadict_form_selector).form2json(bean);
		if(bean.fullWindow==true){
			bean.fullWindow="yes";
		}else{
			bean.fullWindow="no";
		}
		JDS.call({
			service : "cmsService.saveCmsCategoryBean",
			data : [ bean ],
			success : function(result) {
				alert("保存成功!");
				// 保存成功刷新树
				loadDataDictionaryTree();
				showDelButton();
			}
		});
		clear();
	});
	// 删除操作
	$("#btn_del").click(function() {
		if (latestSelectedNode == null || latestSelectedNode.id == -1) {
			alert("请选择记录！");
			return;
		}
		var name = latestSelectedNode.name;
		var id = latestSelectedNode.id;
		if (confirm("确定要删除导航[" + name + "]吗？")) {
			JDS.call({
				service : "cmsService.removeCmsCategory",
				data : [ id ],
				success : function(result) {
					alert("删除成功!");
					// 删除成功刷新树
					loadDataDictionaryTree();
					// 清空表单等
					clear();
				}
			});
		}
	});
	function clear() {
		$(datadict_form_selector).clearForm(true);
		bean.uuid = null;
		if (latestSelectedNode) {
			$("#remark", $(datadict_form_selector)).val(
					latestSelectedNode.id);
		}
	}
	
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	
	// JQuery layout布局变化时，更新jqGrid宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable')) {
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
	// 页面布局
	Layout.layout({
		west__size : 300,
		onresize : resizeJqGrid
	});
});