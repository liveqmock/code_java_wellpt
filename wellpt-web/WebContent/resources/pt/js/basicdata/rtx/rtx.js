$(function() {

	var bean = {
		"uuid" : null,
		"isEnable" : null,
		"rtxServerIp" : null,
		"rtxServerPort" : null,
		"sdkServerIp" : null,
		"sdkServerPort" : null,
		"rtxApplicationServerIp" : null,
		"rtxApplicationServerPort" : null,
		"messageSendWay" : null,
		"isEnableAbbreviation" : null,
		"synchronizationOperation" : null,
		"rtxClientDownloadAddress" : null
	};

	// 根据用户uuid获取RTX设置信息
	function getRtxConfig(uuid) {
		var rtx = {};
		rtx.uuid = uuid;
		JDS.call({
			service : "rtxService.getBeanByUuid",
			data : [ uuid ],
			success : function(result) {
					bean = result.data;
					$("#rtx_form").json2form(bean);
			}
		});
	}

	// JQuery layout布局变化时，更新jqGrid高度与宽度
	function resizeJqGrid(position, pane, paneState) {
		if (grid = $('.ui-jqgrid-btable:visible')) {
			grid.each(function(index) {
				var gridId = $(this).attr('id');
				$('#' + gridId).setGridWidth(pane.width() - 2);
				$('#' + gridId).setGridHeight(pane.height() - 44);
				if (Browser.isIE()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 44);
				} else if (Browser.isChrome()) {
					$('#' + gridId).setGridWidth(pane.width() - 10);
					$('#' + gridId).setGridHeight(pane.height() - 54);
				} else if (Browser.isMozila()) {
					$('#' + gridId).setGridWidth(pane.width() - 2);
					$('#' + gridId).setGridHeight(pane.height() - 44);
				} else {
					$('#' + gridId).setGridWidth(pane.width());
					$('#' + gridId).setGridHeight(pane.height());
				}
			});
		}
	}
	// JQuery UI按钮
	$("input[type=submit], a, button", $(".btn-group")).button();
	// JQuery UI页签
	$(".tabs").tabs();
	// JQuery layout布局
	$('#container').layout({
		center : {
			closable : false,
			resizable : false,
			slidable : false,
			onresize : resizeJqGrid,
			minSize : 500,
			triggerEventsOnLoad : true
		},
		east : {
			fxName : "slide",
			minSize : 600,
			maxSize : 600
		}
	});
	
	//保存rtx设置
	$("#btn_save").click(function(){
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#rtx_form").form2json(bean);
		
		JDS.call({
			service : "rtxService.saveBean",
			data : [ bean ],
			validate : true,
			success : function(result) {
				alert("保存成功！");
			}
		});
	});
	

	//同步组织树
	$("#btn_synchronized_to_rtx").click(function(){
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#rtx_form").form2json(bean);
		JDS.call({
			service : "rtxService.synchronizedOrganization",
			data : [ bean ],
			validate : true,
			success : function(result) {
				alert("同步组织成功！");
			}
		});
	});
	var rtxuuid="";
	$(window).load(function () {
		if(rtxuuid !="" && rtxuuid !=null){
			getRtxConfig(rtxuuid);
		}
	});
	
	
});