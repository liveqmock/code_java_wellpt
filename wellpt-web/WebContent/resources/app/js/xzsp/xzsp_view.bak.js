var XZSP = XZSP || {};
XZSP.workflow = XZSP.workflow || {};

$(function() {
	var isNotBlank = StringUtils.isNotBlank;
	//goinWay 进入页面的方式  'CMS_GOIN'：从CMS进入；
	XZSP.workflow.getMattersByLibId = function(libId, projectUuid, goinWay) {
		$.get(ctx + "/xzsp/matters", function(data) {
			prepareAndShowMattersDialog(data, projectUuid, goinWay);
		});

		// JDS.call({
		// service : "XZSPService.getMattersByLibId",
		// data : [ libId ],
		// success : function(result) {
		// showMattersDialog(result.data);
		// }
		// });
	};

	// 加载与选择事项弹出框
	function prepareAndShowMattersDialog(data, projectUuid, goinWay) {
		// 放置
		if ($("#dlg_xzsp_matters").length == 0) {
			$("body").append("<div id='dlg_xzsp_matters' />");
		}

		$("#dlg_xzsp_matters").html("");
		$("#dlg_xzsp_matters").html(data);

		showMattersDialog({});

		// 绑定点击关闭弹出框事件
		$("#dlg_xzsp_matters ul li a").click(
				function() {
					var src = $(this).attr("src");
					if (projectUuid != null) {
						src = src + "&projectUuid=" + projectUuid + "&ep_goinWay="+ goinWay;
					}
					var json = $(this).attr("json");
					var matter = JSON.parse(json);

					// 检查与提示项目所在事项已经接件过
					var showText = null;
					if (isNotBlank(projectUuid)) {
						JDS.call({
							service : "projectService.getJSRecords",
							data : [ matter.uuid, projectUuid ],
							async : false,
							success : function(result) {
								var data = result.data;
								if (data["records"] != null && data["records"].length > 0) {
									showText = data["mattersName"] + "(" + data["projectName"] + ")"
											+ "已经申报过" + "<br/>" + "接件日期和状态如下" + "<br/>";
									$.each(data["records"], function(i) {
										showText += data["mattersName"] + "(" + data["projectName"] + ")"
												+ " " + this["receiveDate"] + " " + this["status"] + "<br/>";
									});
									showText += "确认要再次申报吗";
								}
							}
						});
					}

					if (isNotBlank(showText)) {
						oConfirm(showText, function() {
							if (matter.isParallel === true) {
								// 显示选择子事项
								$("#dlg_xzsp_matters").oDialog("close");
								prepareAndShowParallelMattersDialog(matter, src);
							} else {
								window.open(src, "_blank");
								$("#dlg_xzsp_matters").oDialog("close");
							}
						}, null, "提示", 500, 250);
					} else {
						if (matter.isParallel === true) {
							// 显示选择子事项
							$("#dlg_xzsp_matters").oDialog("close");
							prepareAndShowParallelMattersDialog(matter, src);
						} else {
							window.open(src, "_blank");
							$("#dlg_xzsp_matters").oDialog("close");
						}
					}
				});
	}

	// 显示选择事项对话框
	function showMattersDialog(option) {
		// 初始化下一流程选择框
		var options = {
			title : "选择事项",
			autoOpen : true,
			width : 650,
			height : 350,
			resizable : false,
			modal : true,
			buttons : {
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

	// 加载与选择串联事项弹出框
	function prepareAndShowParallelMattersDialog(matter, src) {
		// 放置
		if ($("#dlg_xzsp_parallel_matters").length == 0) {
			$("body").append("<div id='dlg_xzsp_parallel_matters' />");
		}

		$("#dlg_xzsp_parallel_matters").html("");

		var materUuids = matter.parallelMatterUuids;
		materUuids = (materUuids == null || materUuids == "null") ? "" : materUuids;
		JDS.call({
			service : "XZSPService.getMattersByUuids",
			data : [ materUuids ],
			async : false,
			success : function(result) {
				var data = result.data;
				var content = "";

				if (data.length != 0) {
					$.each(data, function(i) {
						var id = this.uuid;
						var name = this.name;
						var value = this.uuid;
						content += '<div><label class="checkbox inline"><input id="' + id + '" name="' + id
								+ '" type="checkbox" value="' + value + '" >' + name + '</label></div>';
					});
					$("#dlg_xzsp_parallel_matters").html(content);
				} else {
					$("#dlg_xzsp_parallel_matters").html("事项[" + matter.name + "]没有设置包含的并联事项");
				}
				showParallelMattersDialog(matter, src);
			}
		});
	}

	// 加载与选择串联事项弹出框
	function showParallelMattersDialog(matter, src) {
		// 初始化下一流程选择框
		var options = {
			title : "选择并联事项",
			autoOpen : true,
			width : 400,
			height : 350,
			resizable : false,
			modal : true,
			buttons : {
				"确定" : function(e) {
					var $checkbox = $("input:checked", "#dlg_xzsp_parallel_matters");
					if ($checkbox.length == 0) {
						oAlert("请选择并联事项!");
					} else {
						var ep = "";
						$checkbox.each(function(i) {
							ep += "&ep_parallelMattersUuid[" + i + "]=" + $(this).val();
						});
						window.open(src + ep, "_blank");
						$(this).oDialog("close");
					}
				},
				"取消" : function(e) {
					$(this).oDialog("close");
				}
			},
			close : function() {
				$("#dlg_xzsp_parallel_matters").html("");
			}
		};

		$("#dlg_xzsp_parallel_matters").oDialog(options);
	}
});