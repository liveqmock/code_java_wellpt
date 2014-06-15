$(function() {
	var isNotBlank = StringUtils.isNotBlank;
	// 加载与选择事项弹出框
	// 绑定点击关闭弹出框事件
	$(".matters_list ul li a").unbind();
	$(".matters_list ul li a").bind("click",function() {
		var src = $(this).attr("src");
		var json = $(this).attr("json");
		var matter = JSON.parse(json);
		
        //联办件
		if (matter.isParallel === true) {
			// 显示选择子事项
			prepareAndShowParallelMattersDialog2(matter, src);
		} else {
			window.open(src, "_blank");
		}
	});

	// 加载与选择串联事项弹出框
	function prepareAndShowParallelMattersDialog2(matter, src) {
		// 放置
		if ($("#xzsp_parallel_matters").length == 0) {
			$("body").append("<div id='xzsp_parallel_matters' />");
		}

		$("#xzsp_parallel_matters").html("");

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
					$("#xzsp_parallel_matters").html(content);
				} else {
					$("#xzsp_parallel_matters").html("事项[" + matter.name + "]没有设置包含的并联事项");
				}
				showParallelMattersDialog2(matter, src);
			}
		});
	}

	// 加载与选择并联事项弹出框
	function showParallelMattersDialog2(matter, src) {
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
					var $checkbox = $("input:checked", "#xzsp_parallel_matters");
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
				$("#xzsp_parallel_matters").html("");
			}
		};

		$("#xzsp_parallel_matters").oDialog(options);
	}
});