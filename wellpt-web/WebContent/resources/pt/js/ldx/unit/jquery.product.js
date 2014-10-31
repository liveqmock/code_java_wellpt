;
var unitDialogCount = 0;
(function($) {
	$.product = {
		open : function(options) {
			options = $.extend({
				labelField : null,
				valueField : null,
				descField : null,
				initNames : null,// 仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。?
				initIDs : null,// 仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。?
				title : "产品选择",
				multiple : false,// 是否多选，默认为true
				showType : true,// 是否显示类型选择下拉框。默认显示。
				type : "LED",// 备选值：产品|Product;
				descFlag : false,//是否查询长描述
				isSetChildWin : false
			// 是否设置显示在父窗口
			// 不允许选择节点值，多值以;分割。
			}, options);
			var laArg = new Array();
			if (options.labelField != null && options.initNames == null) {
				laArg["Name"] = $("#" + options.labelField).val();
			} else {
				laArg["Name"] = options.initNames;
			}
			if (options.valueField != null && options.initIDs == null) {
				laArg["ID"] = $("#" + options.valueField).val();
			} else {
				laArg["ID"] = options.initIDs;
			}
			if ((laArg["ID"] == null || laArg["ID"] == "") && typeof ($("#" + options.labelField).attr("hiddenValue")) != "undefined") {
				laArg["ID"] = $("#" + options.labelField).attr("hiddenValue");
			}
			if (laArg["Name"] == "" || laArg["ID"] == "") {
				laArg["Name"] = null;
				laArg["ID"] = null;
			}
			laArg["Title"] = null;
			laArg["Type"] = options.type;
			laArg["ShowType"] = options.showType;
			laArg["multiple"] = options.multiple;
			laArg["descFlag"] = options.descFlag;
			// 获取cookie
			function getCookie(name) {
				var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
				if (arr) {
					return decodeURI(arr[2]);
				} else {
					return null;
				}
			}
			// 确保contextPath有值
			function getContextPath() {
				if (window.ctx == null || window.contextPath == null) {
					window.contextPath = getCookie("ctx");
					window.contextPath = window.contextPath == "\"\"" ? "" : window.contextPath;
					window.ctx = window.contextPath;
				}
				return window.ctx;
			}
			getContextPath();
			unitDialogCount++;
			var index = unitDialogCount;
			laArg["index"] = index;
			laArg["ctx"] = window.ctx;
			// 后台管理组织选择框滚动问题
			var layoutSelector = ".ui-layout-container";
			var layoutOverflowX = $(layoutSelector).css("overflow-x");
			var layoutOverflowY = $(layoutSelector).css("overflow-y");
			var unitDialog = $(
					'<iframe id="unit_dialog_' + index + '" src="' + ctx
							+ '/resources/pt/js/ldx/unit/unit.htm" frameborder="0"></iframe>').dialog({
				title : options.title,
				bgiframe : true,
				autoOpen : true,
				resizable : true,
				stack : true,
				zIndex : 9999,
				width : 850,
				height : 560,
				modal : true,
				overlay : {
					background : '#000',
					opacity : 0.5
				},
				close : function(e, ui) {
					if (this.contentWindow.goUnitTree) {
						var returnValue = this.contentWindow.goUnitTree.returnValue;
						if (returnValue) {
							if (options.labelField != null) {
								$("#" + options.labelField).val(returnValue.name);
								$("#" + options.labelField).attr("hiddenValue", returnValue.id);
								$("input[name='" + options.labelField+"']").val(returnValue.name);
								$("input[name='" + options.labelField+"']").attr("hiddenValue", returnValue.id);
							}
							if (options.valueField != null) {
								$("#" + options.valueField).val(returnValue.id);
								$("input[name='" + options.valueField+"']").val(returnValue.id);
							}
							if (options.descFlag && options.descField != null) {
								$.get(ctx+"/ldx/unit/product/search/getLondDesc",{id:returnValue.id}, function(data) {
									$("#" + options.descField).val(data);
									$("input[name='" + options.descField+"']").val(data);
								});
							}
							if (options.isSetChildWin) {
								setChildWin();
							}
						}
						if (returnValue && options.afterSelect) {
							options.afterSelect.call(this, this.contentWindow.goUnitTree.returnValue);
						}
					}
					if (options.close) {
						options.close.call(this, e);
					}
					$(layoutSelector).css("overflow-x", layoutOverflowX);
					$(layoutSelector).css("overflow-y", layoutOverflowY);
				},
				open : function(e) {
					$(".ui-widget-overlay").css("background", "#000");
					$(".ui-widget-overlay").css("opacity", "0.5");
					$(this).css("width", "100%");
					$(this).css("margin", "0");
					$(this).css("padding", "0");
					this.contentWindow.dialogArguments = laArg;
					$(layoutSelector).css("overflow-x", "scroll");
					$(layoutSelector).css("overflow-y", "scroll");
				}
			});
			if (!window.unitDialogs) {
				window.unitDialogs = [];
			}
			window.unitDialogs[index] = unitDialog;
		}
	};
})(jQuery);