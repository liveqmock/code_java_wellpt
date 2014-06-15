;
var unitDialogCount = 0;
(function($) {
	$.unit = {
		open : function(options) {
			options = $.extend({
				labelField : null,
				valueField : null,
				initNames : null,// 仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。?
				initIDs : null,// 仅一个目标域值时，以;分割的文本值，当为多个目标域值时则为与psTargetNames一一对应的数组成员值。?
				title : "人员选择",
				multiple : true,// 不支持
				selectType : 1,// 限制用户只能选择的节点类型(默认"1")；0-都不能选择，1-都可以选择，2-仅允许选择部门，4-仅允许选择人员，8-表示仅允许选择公共群组，其他值为0/1/2/4/8相加组合。
				nameType : "21",// 返回的组织名称格式，两个字符(默认“21”)：分别表示部门节点/人员节点；每一个字符表示，“1”-名称，“2”-不带根全路径（集团版自动带根），“3”-带根全路径。
				showType : true,// 是否显示类型选择下拉框。默认显示。
				type : "MyUnit",// 备选值：集团|All;我的单位|MyUnit;我的部门|MyDept;我的领导|MyLeader;我的下属|MyUnderling;公共群组|PublicGroup;个人群组|PrivateGroup;上级部门|MyParentDept;在线人员|LoginUser;部门树|Dept也可以是节点值（可以多值，且要求支持路径值或者id），用来选择此节点树下的值。默认为MyUnit.，当值为Dept，不显示姓氏下拉框。
				loginStatus : false,// 是否显示在线/离线状态，默认不显示
				excludeValues : null,
				sexField : null,
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
			laArg["Sex"] = options.sexField;
			laArg["Multiple"] = options.multiple;
			laArg["SelectType"] = options.selectType;
			laArg["NameType"] = options.nameType;
			laArg["ShowType"] = options.showType;
			laArg["Type"] = options.type;
			laArg["LoginStatus"] = options.loginStatus;
			laArg["Exclude"] = options.excludeValues;
			// if (!window.ctx) {
			// window.ctx = getContextPath();
			// }
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
							+ '/resources/pt/js/org/unit/unit.htm" frameborder="0"></iframe>').dialog({
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
							}
							if (options.valueField != null) {
								$("#" + options.valueField).val(returnValue.id);
							}
							if (options.sexField != null) {
								$("#" + options.sexField).val(returnValue.sex);
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