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
// 格式化消息参数工具类
MsgUtils = {};
// 格式化消息，填充参数
MsgUtils.format = function(msg) {
	for ( var i = 1; i < arguments.length; i++) {
		var param = "\{" + (i - 1) + "\}";
		msg = msg.replace(param, arguments[i]);
	}
	return msg;
};

// 国际化资源加载器
I18nLoader = {};
// 根据当前cookie的语言环境加载相应的js国际化信息文件
I18nLoader.load = function(prefixPath) {
	var language = getCookie("language");
	try {
		$.ajaxSettings.async = false;
		if (language == "zh_CN") {
			$.getScript(ctx + prefixPath + "_zh_CN.js");
		} else {
			$.getScript(ctx + prefixPath + "_en.js");
		}
	} catch (e) {
	} finally {
		$.ajaxSettings.async = true;
	}
};

// Tab Utils
TabUtils = {};
TabUtils.openTab = function(tabid, text, url, icon) {
	if (top.addTab) {
		top.addTab(tabid, text, url, icon);
	} else {
		window.open(url);
	}
};
TabUtils.removeTab = function(tabid) {
	if (returnWindow) {
		returnWindow();
	}
	if (top.navtab) {
		top.navtab.removeTabItem(tabid);
	} else {
		window.opener = null;
		top.close();
	}
};
TabUtils.reloadAndRemoveTab = function(tabid1, tabid2) {
	if (returnWindow) {
		returnWindow();
	}
	if (top.navtab) {
		top.navtab.selectTabItem(tabid1);
		top.navtab.reload(tabid1);
		top.navtab.removeTabItem(tabid2);
	} else {
		top.close();
	}
};

var StringUtils = StringUtils || {};
// 判断字符串不为undefined、null、空串、空格串
StringUtils.isNotBlank = function(string) {
	return string != null && $.trim(string) != "";
};
// 判断字符串为undefined、null、空串、空格串
StringUtils.isBlank = function(string) {
	return string == null || $.trim(string) == "";
};

// JSON Data Services
var JDS = {};
JDS.call = function(options) {
	options = $.extend({
		data : [],
		validate : false,
		async : true,
		mask : true
	}, options);
	var jsonData = {};
	var service = options.service;
	var splitIndex = service.indexOf(".");
	jsonData.serviceName = service.substring(0, splitIndex);
	jsonData.methodName = service.substring(splitIndex + 1, service.length);
	jsonData.validate = options.validate;
	var data = options.data;
	if ($.isArray(data) === true) {
		jsonData.args = JSON.stringify(data);
	} else {
		jsonData.args = JSON.stringify([ data ]);
	}
	if ($.alerts && options.mask === true) {
		pageLock("show");
	}
	$.ajax({
		type : "POST",
		url : ctx + "/json/data/services",
		data : JSON.stringify(jsonData),
		contentType : "application/json",
		dataType : "json",
		async : options.async,
		success : function(success, statusText, jqXHR) {
			if ($.alerts && options.mask === true) {
				pageLock("hide");
			}
			if (options.success) {
				options.success.apply(this, [ success, statusText, jqXHR ]);
			}
		},
		error : function(jqXHR, statusText, error) {
			if ($.alerts && options.mask === true) {
				pageLock("hide");
			}
			var faultData = JSON.parse(jqXHR.responseText);
			// 登录超时
			if (faultData != null && "SessionExpired" == faultData.errorCode) {
				var logout = function() {
					if (top) {
						top.location.href = ctx + faultData.data;
					} else {
						location.href = ctx + faultData.data;
					}
				};
				if ($.alerts) {
					oAlert(faultData.msg, logout);
				} else {
					alert(faultData.msg);
					logout();
				}
			} else if (faultData != null && "StaleObjectState" == faultData.errorCode) {
				// Hibernate乐观锁
				if ($.alerts) {
					oAlert(faultData.msg);
				} else {
					alert(faultData.msg);
				}
			} else if (options.error) {// 原生错误处理
				options.error.apply(this, [ jqXHR, statusText, error ]);
			} else {
				// 默认错误处理
				if (faultData != null && faultData.msg != null && $.trim(faultData.msg) != "") {
					alert(faultData.msg);
				} else {
					alert(jqXHR.responseText);
				}
			}
		}
	});
};

var Browser = {};
// 检测是否是IE浏览器
Browser.isIE = function() {
	var _uaMatch = $.uaMatch(navigator.userAgent);
	var _browser = _uaMatch.browser;
	if (_browser == 'msie') {
		return true;
	} else {
		return false;
	}
};
// 检测是否是chrome浏览器
Browser.isChrome = function() {
	var _uaMatch = $.uaMatch(navigator.userAgent);
	var _browser = _uaMatch.browser;
	if (_browser == 'chrome' || _browser == 'webkit') {
		return true;
	} else {
		return false;
	}
};
// 检测是否是Firefox浏览器
Browser.isMozila = function() {
	var _uaMatch = $.uaMatch(navigator.userAgent);
	var _browser = _uaMatch.browser;
	if (_browser == 'mozilla') {
		return true;
	} else {
		return false;
	}
};
// HashMap
var HashMap = function() {
	this.data = {};
};
HashMap.prototype = {
	constructor : HashMap,
	get : function(key) {
		return this.data[key];
	},
	put : function(key, value) {
		this.data[key] = value;
		return this.data[key];
	},
	remove : function(key) {
		var v = this.data[key];
		delete this.data[key];
		return v;
	},
	values : function() {
		var a = [];
		for ( var key in this.data) {
			a.push(this.data[key]);
		}
		return a;
	},
	containsKey : function(key) {
		return this.data.hasOwnProperty(key);
	}
};
(function($) {
	// 将JSON对象的数据设置到表单中
	$.fn.json2form = function(object, checkboxesAsString) {
		// 如果对象不存在属性直接忽略
		for ( var property in object) {
			$(':input[name=' + property + ']', $(this)).each(function() {
				var type = this.type;
				var name = this.name;
				var data = object[name];
				switch (type) {
				case undefined:
				case "text":
				case "password":
				case "hidden":
				case "button":
				case "reset":
				case "textarea":
				case "submit": {
					$(this).val(data);
					break;
				}
				case "checkbox":
				case "radio": {
					this.checked = false;
					if ($.isArray(data) === true) {// checkbox
						// multiple value is Array
						for ( var el in data) {
							if (data[el] == $(this).val()) {
								this.checked = true;
							}
						}
					} else {// radio is a string single value
						if (type == "radio") {
							if (data == $(this).val()) {
								this.checked = true;
							}
						} else {
							if (checkboxesAsString && checkboxesAsString === true) {
								data = (data == null) ? "" : data;
								var a = data.split(",");
								for ( var el in a) {
									if (a[el] == $(this).val()) {
										this.checked = true;
									}
								}
							} else {
								// 当个checkbox只有true、false
								this.checked = (data == true || data == "true");
							}
						}
					}
					break;
				}
				case "select":
				case "select-one":
				case "select-multiple": {
					$(this).find("option:selected").attr("selected", false);
					if ($.isArray(data) === true) {
						for ( var el in data) {
							$(this).find("option[value='" + data[el] + "']").attr("selected", true);
						}
					} else {
						$(this).find("option[value='" + data + "']").attr("selected", true);
					}
					break;
				}
				}
			});
		}
	};

	// 将表单中的数据收集到指定的JSON对象中
	$.fn.form2json = function(object, checkboxesAsString) {
		// 如果表单元素不存在直接忽略
		for ( var property in object) {
			var elements = $(':input[name=' + property + ']', $(this));
			if (elements.length == 0) {
				continue;
			}
			// 单个元素
			if (elements.length == 1) {
				var element = elements[0];
				var v = fieldValue(element);
				if (v && v.constructor == Array) {
					var a = [];
					for ( var i = 0, max = v.length; i < max; i++) {
						a.push(v[i]);
					}
					object[property] = a;
				} else {
					if (v !== null && typeof v != 'undefined') {
						object[property] = v;
					}
					// checkbox只有一个值(true、false)
					if (element.type == 'checkbox') {
						if (element.checked === true) {
							object[property] = true;
						} else {
							object[property] = false;
						}
					}
				}
			}
			// 多个元素
			if (elements.length > 1) {
				var a = [];
				for ( var i = 0; i < elements.length; i++) {
					var element = elements[i];
					var v = fieldValue(element);
					if (v && v.constructor == Array) {
						for ( var i = 0, max = v.length; i < max; i++) {
							a.push(v[i]);
						}
					} else {
						if (v !== null && typeof v != 'undefined') {
							a.push(v);
						}
					}
				}
				// 单选框只有一个值
				if (elements[0].type == 'radio') {
					object[property] = a.join();
				} else {
					if (elements[0].type == 'checkbox' && checkboxesAsString && checkboxesAsString === true) {
						object[property] = a.join();
					} else {
						object[property] = a;
					}
				}
			}
		}
	};
	/**
	 * Returns the value of the field element.
	 */
	function fieldValue(el, successful) {
		var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
		if (successful === undefined) {
			successful = true;
		}

		if (successful
				&& (!n || el.disabled || t == 'reset' || t == 'button' || (t == 'checkbox' || t == 'radio')
						&& !el.checked || (t == 'submit' || t == 'image') && el.form && el.form.clk != el || tag == 'select'
						&& el.selectedIndex == -1)) {
			return null;
		}

		if (tag == 'select') {
			var index = el.selectedIndex;
			if (index < 0) {
				return null;
			}
			var a = [], ops = el.options;
			var one = (t == 'select-one');
			var max = (one ? index + 1 : ops.length);
			for ( var i = (one ? index : 0); i < max; i++) {
				var op = ops[i];
				if (op.selected) {
					var v = op.value;
					if (!v) { // extra pain for IE...
						v = (op.attributes && op.attributes['value'] && !(op.attributes['value'].specified)) ? op.text
								: op.value;
					}
					if (one) {
						return v;
					}
					a.push(v);
				}
			}
			return a;
		}
		return $(el).val();
	}

	// 公共区
	$.common = $.common || {};
	$.common.json = $.common.json || {};
	// 清空JSON对象的属性
	$.common.json.clearJson = function(json, emptyToNull) {
		for ( var p in json) {
			var v = json[p];
			if (typeof (p) == 'object') {
				$.common.json.clearJson(json[p], emptyToNull);
			} else if (v && v.constructor == Array) {
				json[p] = [];
			} else {
				if (emptyToNull && emptyToNull === true) {
					json[p] = null;
				} else {
					json[p] = '';
				}
			}
		}
	};
})(jQuery);
// 解析url 返回json数组
function readSearch() {
	var search = window.location.search;
	var s = new Object();
	var searchArray = search.replace("?", "").split("&");
	for ( var i = 0; i < searchArray.length; i++) {
		var paraArray = searchArray[i].split("=");
		var key = paraArray[0];
		var value = paraArray[1];
		s[key] = value;
	}
	return s;
}
function refreshPage(){
	location.href = location.href;
}
var SpringSecurityUtils = {};
SpringSecurityUtils.getCurrentUserName = function() {
	if (window.opener == null) {
		window.opener = top;
	}
	if (window.opener.userDetails == null) {
		$.ajax({
			type : "GET",
			url : ctx + "/security/user/details/",
			contentType : "application/json",
			dataType : "json",
			async : false,
			success : function(success, statusText, jqXHR) {
				window.opener.userDetails = success;
			}
		});
	}
	return window.opener.userDetails == null ? "" : window.opener.userDetails["userName"];
};
SpringSecurityUtils.getUserDetails = function() {
	SpringSecurityUtils.getCurrentUserName();
	return window.opener.userDetails;
};
/** ******************************** 验证CAKey开始 ***************************** */
function checkCAKey() {
	var caStatus = false;// 判断客户端是否插key
	if (caStatus) {
		// 这里检测是否安装客户端版本
		var b = false;
		try {
			b = fjcaWs.OpenFJCAUSBKey();
		} catch (e) {
			oAlert("打开证书失败");
			try {
				fjcaWs.CloseUSBKey();
			} catch (e) {
			}
			return false;
		}
		if (!b) {
			oAlert("打开证书失败");
			try {
				fjcaWs.CloseUSBKey();
			} catch (e) {
			}
			return false;
		}
		// 读取证书
		if (!fjcaWs.ReadCertFromKey()) {
			oAlert("读取证书失败");
			try {
				fjcaWs.CloseUSBKey();
			} catch (e) {
			}
			return false;
		}
		var testCert = fjcaWs.GetCertData();
		var flag = false;
		JDS.call({
			service : "FJCAAppsService.checkCurrentCertificate",
			data : [ testCert ],
			async : false,
			success : function(result) {
				flag = true;
			},
			error : function(jqXHR) {
				var faultData = JSON.parse(jqXHR.responseText);
				oAlert(faultData.msg);
				flag = false;
			}
		});
		fjcaWs.CloseUSBKey();
		return flag;
	} else {
		return true;
	}
}
/** ******************************** 验证CAKey结束 ***************************** */
