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
			$("#pageUrl").html("<option value=''>-请选择-</option>"+option);
			$("#appointPage").html("<option value=''>-请选择-</option>"+option);
		}
	});
	
	var setting = {
		async : {
			otherParam : {
				"serviceName" : "cmsService",
				"methodName" : "getMdouleAsTreeAsync"
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
	
	$("#appointPageElement").comboTree({
		labelField: "appointPageElement",
		valueField: "appointPageElementId",
		treeSetting : setting,
		width: 250,
		height: 220
	});
	
	//导航的树形下拉展示
	var setting2 = {
			async : {
				otherParam : {
					"serviceName" : "cmsService",
					"methodName" : "getCmsCategoryAsTreeAsync"
				}
			},
			check : {
				enable : true,
				chkStyle : "radio"
			}
	};
	
	$("#appointCategoryName").comboTree({
		labelField: "appointCategoryName",
		valueField: "appointCategoryId",
		treeSetting : setting2,
		width: 250,
		height: 220
	});
	
	function treeNodeOnClick(event, treeId, treeNode) {
		$("#moduleId").val(treeNode.id);
		$("#moduleName").val(treeNode.name);
		$("#moduleName").comboTree("hide");
	}
	//添加按钮
	$(".addAppointWindow").live("click",function(){
		var appointWindowText = $("#appointWindow option:selected").text();
		var appointWindowValue = $("#appointWindow option:selected").val();
		var appointPageElementText = $("#appointPageElement").val();
		var appointPageElementValue = $("#appointPageElementId").val();
		
		var temp = '<tr class="definitioncontentiteam">';
		temp += '<td>'+appointWindowText+'</td>';
		temp += '<td>'+appointPageElementText+'</td>';
		temp += '<td appointWindowValue='+appointWindowValue+' style="display: none;">'+appointWindowValue+'</td>';
		temp += '<td appointPageElementValue='+appointPageElementValue+' style="display: none;">'+appointPageElementValue+'</td>';
		temp += '<td><button class="delAppointWindow">删除</button></td>';
		temp += '</tr>';
		$(".definitiontrtable").append(temp);
	});
	
	$(".delAppointWindow").live("click",function(){
		$(this).parent().parent().remove();
	});
	
	/**
	 * add by HeShi 20141016
	 * 名称输入完毕后自动回填显示标题字段
	 */
	$("#showTitle").live("blur",function(){
		if($("#title").val()==""){
			$("#title").val(this.value);
		}
	});
	
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
		"showTitle":null,
		"resources":null,
		"remark" : null,
		"fullWindow":null,
		"cateUuid": null,
		"cateName": null,
		"appointPage":null,
		"appointPageId":null,
		"appointWindow":null,
		"appointWindowId":null,
		"appointPageElement":null,
		"appointPageElementId":null,
		"appointCategoryName":null,
		"appointCategoryId":null
	};
	$.ajax({
		type : "POST",
		url : ctx +"/cms/module/getNavTypeData",
		contentType : "application/json",
		dataType : "json",
		success : function(result) {
			$("#cateUuid").html(result.data);
		}
	});
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
			$("#appointWindow").parent().parent().parent().hide();
			$("#appointCategoryName").parent().parent().hide();
		});
		
		$("#resources_btncode").live("click",function(){
			var this_ = $(this);
			$("#dlg_choose_button").popupTreeWindow({
				title : "导航资源选择",
				initValues : this_.next().val(),
				treeSetting : selectSubFlowSetting,
				afterSelect : function(retVal) {
					this_.next().val(retVal["value"]);
					this_.val(retVal["name"]);
				},
				afterCancel : function() {
				},
				close : function(e) {
				}
			});
			$("#dlg_choose_button").popupTreeWindow("open");
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
					"methodName" : "getResourceMenuTree",
					"data" : "-1"
				},
				type : "POST"
			}
		};
		
		JDS.call({
			service : "cmsService.getCmsCategoryBean",
			data : [ uuid ],
			success : function(result) {
				bean = result.data;
				if(bean.resources != null) {
					JDS.call({
						service : "cmsService.getResourceName",
						data : [ bean.resources ],
						success : function(result) {
							$("#resources_btncode").val(result.data);
						}
					});
				}
				showDelButton();
				$(datadict_form_selector).json2form(bean);
					$("#appointPageElement").val("");
				if(bean.newPage=="windows"){
					$("#divId").parent().parent().show();
				}else{
					$("#divId").parent().parent().hide();
				}
				if(bean.moduleId!=null||bean.moduleId!=undefined||bean.moduleId==""){
					for(var key in window.rsmodule){
						if(bean.moduleId==window.rsmodule[key].uuid){
							$("#moduleName").val(window.rsmodule[key].name);
						}else {
							$("#moduleId").val("");
						}
					}
				}
				if(bean.openType=="dialog"){
				}else if(bean.openType == "pageUrl") {
//					$("#pageUrl").empty();
					$("#appointWindow").empty();
					$("#appointCategoryName").empty();
					$("#appointCategoryName").empty();
					$("#pageUrl").parent().parent().show();
					$("#appointWindow").parent().parent().parent().show();
					$("#appointCategoryName").parent().parent().show();
					$("#pageUrl").find("option[value='"+bean.appointPageId+"']").attr("selected",true);
					change(bean.appointPageId);
					var pageUrl = bean.pageUrl;
					var appointWindows = bean.appointWindow.split(";");
					var appointWindowId = bean.appointWindowId.split(";");
					var appointPageElement = bean.appointPageElement;
					var appointPageElementId = bean.appointPageElementId;
					var appointPageElements = appointPageElement.split(";");
					var appointPageElementIds = appointPageElementId.split(";");
					if($(".definitioncontentiteam").size() != 0) {
						$(".definitioncontentiteam").remove();
					}
					for(var index=0;index<appointPageElements.length;index++) {
						if(appointPageElements[index] != '') {
							var temp = '<tr class="definitioncontentiteam">';
							temp += '<td>'+appointWindows[index]+'</td>';
							temp += '<td>'+appointPageElements[index]+'</td>';
							temp += '<td appointWindowValue='+appointWindowId[index]+' style="display: none;">'+appointWindowId[index]+'</td>';
							temp += '<td appointPageElementValue='+appointPageElementIds[index]+' style="display: none;">'+appointPageElementIds[index]+'</td>';
							temp += '<td style="display: none;"></td>';
							temp += '<td><button class="delAppointWindow">删除</button></td>';
							temp += '</tr>';
							$(".definitiontrtable").append(temp);
						}
					}
				}else if(bean.openType == "inputUrl") {
					$("#"+bean.openType).parent().parent().show();
					$(".openType").each(function(){
						$(this).show();
						$(this).next().show();
					});
				}
				else{
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
		var id=(latestSelectedNode==null||latestSelectedNode==undefined)?'':latestSelectedNode.id;
		JDS.call({
			service : "cmsService.findNewCodeForAddNavigator",
			data : [id,'10'],
			success : function(result) {
				$("#ecode").val(result.data);
			},
			error : function(){
				$("#ecode").val("");
			}
		});
	});
	$(".newPage").die().live("click",function() {
		var id = $(this).val();
		if(id=="windows"){
			$("#divId").parent().parent().show();
			$(".openType").each(function(){
				$(this).show();
				$(this).next().show();
		});
		}else if(id == "new") {
			$(".openType").each(function(){
				if($(this).val() == "moduleId") {
					$(this).hide();
					$(this).next().hide();
				}else {
					$(this).show();
					$(this).next().show();
				}
			});
		}
		else if(id == "none") {
			$("#divId").parent().parent().hide();
			$(".openType").each(function(){
				if($(this).val() != "jsContent") {
					$(this).hide();
					$(this).next().hide();
				}
			});
		}else{
			$("#divId").parent().parent().hide();
			$(".openType").each(function(){
					$(this).show();
					$(this).next().show();
			});
		}
	});
	$(".openType").click(function() {
		$(".openType").each(function(){
			$("#"+$(this).val()).parent().parent().hide();
		});
		var id = $(this).val();
		if(id == "pageUrl") {
			$("#"+id).parent().parent().show();
			$("#appointPage").parent().parent().show();
			$("#appointCategoryName").parent().parent().show();
			$("#appointWindow").parent().parent().parent().show();
		}else {
			$("#"+id).parent().parent().show();
			$("#appointPage").parent().parent().hide();
			$("#appointCategoryName").parent().parent().hide();
			$("#appointWindow").parent().parent().parent().hide();
		}
	});
	
	//搜集
	function collectInfo(bean) {
		if($("#pageUrl option:selected").val() == undefined) {
			alert("请先选择一个页面！");
		}else {
			var pageUuid = $("#pageUrl option:selected").val().split("=")[1];
			var pageName = $("#pageUrl option:selected").text();
			var pageValue = $("#pageUrl option:selected").val();
			bean.appointPage = pageName;
			bean.appointPageId = pageValue;
			var treeName = $("#appointCategoryId").val();
			var widAndMoudleArray = new Array(); 
			var widAndMoudleAll = "";
			bean.appointPageElement = "";
			bean.appointPageElementId = "";
			$(".definitioncontentiteam").each(function() {
				var eq0 = $(this).find("td").eq(0).text();
				var eq1	= $(this).find("td").eq(1).text();
				var eq2	= $(this).find("td").eq(2).text();
				var eq3	= $(this).find("td").eq(3).text();
				bean.appointWindow = ";"+eq0;
				bean.appointWindowId = ";"+eq2;
				bean.appointPageElement += ";"+eq1;
				bean.appointPageElementId += ";"+eq3;
				var widAndMoulde = eq2 + "=" + eq3;
				widAndMoudleAll += "&"+widAndMoulde;
			});
			bean.pageUrl = "/cms/cmspage/readPage?uuid="+pageUuid+"&treeName="+treeName+widAndMoudleAll;
		}
	}
	
	// 保存导航信息
	$("#btn_save").click(function() {
		$("#cateName").val($("#cateUuid").find("option:selected").text());
		// 收集表单数据
		// toObject($(datadict_form_selector), bean);
		$(datadict_form_selector).form2json(bean);
		if($('input[type=radio][value=pageUrl]').attr("checked")) {
			collectInfo(bean);
		};
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
				//loadDataDictionaryTree();
				showDelButton();
			}
		});
		//clear();
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


function change(value) {
	var pageUuid = value.split("=")[1];
	JDS.call({
		service:"cmsService.getWidgetsByPage",
		data:[pageUuid],
		success:function(result) { 
			var data = result.data;
			var optionStr = "";
			for(var i=0;i<data.length;i++) {
				var title = data[i].title;
				var wid = data[i].wid;
				optionStr += "<option value='"+wid+"'>"+title+"</option>";
			}
			$("#appointWindow").html(optionStr);
		}
	});
}

