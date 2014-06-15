$(function() {

	var bean = {
		"uuid" : null,
		"imIp" : null,
		"loginName" : null,
		"loginPassword" : null,
		"apiCode" : null,
		"dbName" : null,
		"isOpen" : null,
		"sendLimit" : null
	};

	// 获取MAS设置信息
	function getMasConfig() {
		JDS.call({
			service : "shortMessageService.getBean",
			success : function(result) {
					bean = result.data;
					$("#mas_form").json2form(bean);
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
	
	//保存mas设置
	$("#btn_save").click(function(){
		
		if($("#sendLimit").val()==''){
			
		}  else if(!($("#sendLimit").val().match("^[0-9]*[1-9][0-9]*$"))){
			alert("重发时限请输入合法的数字");
			return false;
		}
		
		// 清空JSON
		$.common.json.clearJson(bean);
		// 收集表单数据
		$("#mas_form").form2json(bean);
		
		JDS.call({
			service : "shortMessageService.saveMas",
			data : [ bean ],
			validate : true,
			success : function(result) {
				if(result.data=='success') {
					alert("保存成功！");
				} else {
					alert("保存失败");
				}
			}
		});
	});
	

	$(window).load(function () {
		getMasConfig();
	});
	
	
});