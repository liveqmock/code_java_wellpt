var XZSP = XZSP || {};
XZSP.workflow = XZSP.workflow || {};

$(function() {
	var isNotBlank = StringUtils.isNotBlank;
	// goinWay 进入页面的方式 'CMS_GOIN'：从CMS进入；
	XZSP.workflow.getMattersByLibId = function(libId, projectUuid, goinWay) {
		$.get(ctx + "/xzsp/matters", function(data) {
			prepareAndShowMattersDialog(data, projectUuid, goinWay);
		});
	};

	// 加载与选择事项弹出框
	function prepareAndShowMattersDialog(data, projectUuid, goinWay) {
		// 放置
		if ($("#dlg_xzsp_matters").length == 0) {
			$("body").append("<div id='dlg_xzsp_matters' />");
		}

		$("#dlg_xzsp_matters").html("");
		$("#dlg_xzsp_matters").html(data);

		$("#matters_accordion", "#dlg_xzsp_matters").accordion({
			collapsible : true,
			heightStyle : "content"
		});

		showMattersDialog({
			projectUuid : projectUuid,
			goinWay : goinWay
		});

		$("#dlg_xzsp_matters a.belonging-unit").click(function() {
			var unitId = $(this).attr("name");
			$("#dlg_xzsp_matters div.current_unit").removeClass("current_unit").hide();
			$("#dlg_xzsp_matters div.unit_" + unitId).addClass("current_unit").show();

			// 标识当前事项，用于并联接件判断事项类型
			$("#dlg_xzsp_matters a.belonging-unit").removeClass("current_matters");
			$(this).addClass("current_matters");
		});

		// 串联事项绑定点击关闭弹出框事件
		$("#dlg_xzsp_matters div.unit-matters ul li a").click(function() {
			var src = $(this).attr("src");
			if (projectUuid != null) {
				src = src + "&projectUuid=" + projectUuid + "&ep_goinWay=" + goinWay;
			}
			var json = $(this).attr("json");
			var matters = JSON.parse(json);

			// 检查与提示项目所在事项已经接件过
			var showText = getJSText(matters.uuid, projectUuid);
			if (isNotBlank(showText)) {
				oConfirm(showText, function() {
					window.open(src, "_blank");
					$("#dlg_xzsp_matters").oDialog("close");
				}, null, "提示", 500, 250);
			} else {
				window.open(src, "_blank");
				$("#dlg_xzsp_matters").oDialog("close");
			}
		});
	}

	function getJSText(mattersUuid, projectUuid) {
		var showText = null;
		if (isNotBlank(projectUuid)) {
			JDS.call({
				service : "projectService.getJSRecords",
				data : [ mattersUuid, projectUuid ],
				async : false,
				success : function(result) {
					var data = result.data;
					if (data["records"] != null && data["records"].length > 0) {
						showText = data["mattersName"] + "(" + data["projectName"] + ")" + "已经申报过" + "<br/>"
								+ "接件日期和状态如下" + "<br/>";
						$.each(data["records"], function(i) {
							showText += data["mattersName"] + "(" + data["projectName"] + ")" + " "
									+ this["receiveDate"] + " " + this["status"] + "<br/>";
						});
						showText += "确认要再次申报吗？";
					}
				}
			});
		}
		return showText;
	}

	// 显示选择事项对话框
	function showMattersDialog(option) {
		// 初始化下一流程选择框
		var options = {
			title : "选择事项",
			autoOpen : true,
			width : 850,
			height : 550,
			resizable : false,
			modal : true,
			buttons : {
				"并联确认" : function(e) {
					var $a = $("#dlg_xzsp_matters a.current_matters");
					if ($a.hasClass("parallel-matters")) {
						var mattersUuid = $a.attr("mattersUuid");
						var projectUuid = option.projectUuid;
						var goinWay = option.goinWay;
						var $checkbox = $("#dlg_xzsp_matters div ul li input[name=parallelMatters]:checked");
						if ($checkbox.length == 0) {
							oAlert("请先选择并联事项");
						} else {
							var src = ctx + "/xzsp/matters/new?mattersUuid=" + mattersUuid
									+ "&ep_mattersUuid=" + mattersUuid;
							src = src + "&projectUuid=" + projectUuid + "&ep_goinWay=" + goinWay;
							var ep = "";
							$checkbox.each(function(i) {
								ep += "&ep_parallelMattersUuid[" + i + "]=" + $(this).val();
							});
							// 检查与提示项目所在事项已经接件过
							var showText = getJSText(mattersUuid, projectUuid);
							if (isNotBlank(showText)) {
								oConfirm(showText, function() {
									window.open(src + ep, "_blank");
									$("#dlg_xzsp_matters").oDialog("close");
								}, null, "提示", 500, 250);
							} else {
								window.open(src + ep, "_blank");
								$("#dlg_xzsp_matters").oDialog("close");
							}
						}
					} else {
						oAlert("请先选择并联事项");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_matters").html("");
			}
		};
		options = $.extend(true, options, option);

		$("#dlg_xzsp_matters").oDialog(options);
	}
});