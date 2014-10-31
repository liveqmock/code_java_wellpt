(function($) {
	var ComboTree = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn.comboTree.defaults, options);
		this.init();
	};
	ComboTree.prototype = {
		constructor : ComboTree,
		init : function() {
			var $element = this.$element;
			var treeId = $element.attr("id") + "_" + "ztree";
			var treeDivId = "content" + "_" + treeId;
			var divWidth = this.options.width || $element.width();
			var divHeight = this.options.height || 200;
			var treeDiv = "<div id='"
					+ treeDivId
					+ "' style='display: none; position: absolute; z-index: 9999; background-color: #fff; overflow-x: hidden;overflow-y: auto; border: 1px solid #c5dbec;'>"
					+ "<ul id='" + treeId
					+ "' class='ztree' style='margin-top: 0; width: "
					+ divWidth + "px; height: " + divHeight + "px;'></ul>"
					+ "</div>";
			this.treeId = treeId;
			this.treeDivId = treeDivId;
			$(treeDiv).insertAfter($element);

			this.$element.click($.proxy(this._showComboTree, this));

			// 初始化值
			if ($("#" + this.options.valueField).val()
					&& this.options.autoInitValue === true) {
				this.initValue($("#" + this.options.valueField).val());
			};
			//输入搜索事件的绑定
			$element.keyup(function(event){
				var inputValue = $element.val();
				if(inputValue==""){
					$.fn.zTree.init($("#" + treeId), window.settingParam);
				}else{
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var nodes = zTree.getNodes();
					zTree.showNodes(nodes);
					ergodicTree(zTree,nodes,inputValue);
					zTree.expandAll(true);
				}
			});
			//遍历节点并隐藏name不匹配的节点
			function ergodicTree(ztree_,nodes_,name_){
				for ( var i = 0; i < nodes_.length; i++) {
					var node = nodes_[i];
					if(node.isParent==true){
						if(JSON.stringify(node).indexOf(name_)<0){
							ztree_.hideNode(node);
						}else{
							var nodeschild = node.children;
							if(nodeschild.length>0){
								ergodicTree(ztree_,nodeschild,name_);
							}
						}
					}else{
						if(node.name.indexOf(name_)<0){
							ztree_.hideNode(node);
						}
					}
				}
			}
		},
		setParams : function(option) {
			this.options = $.extend(true, this.options, option);
		},
		_showComboTree : function() {
			if (this._enable === false) {
				return;
			}
			var $element = this.$element;
			$element.treeInitialized = false;
			var $this = this;
			var options = this.options;
			var treeId = this.treeId;
			var treeDivId = this.treeDivId;
//			if (!window.ctx) {
//				window.ctx = "/" + window.location.pathname.split("/")[1];
//			}
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
			var ztreeSetting = {
				view : {
					showLine : false
				},
				async : {
					enable : true,
					contentType : "application/json",
					url : ctx + "/json/data/services",
					otherParam : {
						"serviceName" : options.serviceName,
						"methodName" : options.methodName
					},
					type : "POST"
				},
				check : {
					enable : true
				},
				callback : {
					onClick : onClick,
					onCheck : zTreeOnCheck,
					onExpand : zTreeOnExpand,
					onAsyncSuccess : onAsyncSuccess
				}
			};
			var ztreeSettingCopy = {};
			$.extend(true,ztreeSettingCopy,  ztreeSetting);
			var setting = $.extend(true, ztreeSetting, options.treeSetting
					|| {});
			
			if(typeof setting.src != "undefined" && setting.src == "control"){
				/*setting.callback.onClick = function(event, treeId, treeNode){ 
			 		if(setting.check.enable){//多选
			 			ztreeSettingCopy.callback.onClick(event,treeId, treeNode);
			 		}
			 		 
			 		if(typeof options.treeSetting.callback != "undefined" && typeof options.treeSetting.callback.onClick != "undefined"){ 
			 			options.treeSetting.callback.onClick(event,treeId, treeNode);
			 		} 
				}; */
				
				 setting.callback.onCheck = function(event, treeId, treeNode){
				 	 
				 		if(setting.check.enable){//多选
				 			ztreeSettingCopy.callback.onCheck(event,treeId, treeNode);
				 		}
				 		 
				 		if(typeof options.treeSetting.callback != "undefined" && typeof options.treeSetting.callback.onCheck != "undefined"){ 
				 			options.treeSetting.callback.onCheck(event,treeId, treeNode);
				 		} 
					}; 
			}
			
		 	 
			 
			window.settingParam = setting;
			 
			
			
			if (setting.check.chkStyle === 'radio') {
				$this.enableRadio = true;
			}
			// 展开树
			function zTreeOnExpand(event, treeId, treeNode) {
				if (options.valueField != null && options.valueField != "") {
					// 设置值
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var nodes = zTree.getNodes();
					var value = $("#" + options.valueField).val();
					if (value != null && value != "") {
						var values = value.split(";");
						for ( var i = 0; i < nodes.length; i++) {
							var node = nodes[i];
							for ( var j = 0; j < values.length; j++) {
								checkNodeByData(zTree, node, values[j]);
							}
						}
					}
				}
			}
			function checkNodeByData(zTree, node, value) {
				if (value == node.data) {
					zTree.checkNode(node, true);
				}
				var children = node.children;
				for ( var i = 0; i < children.length; i++) {
					checkNodeByData(zTree, children[i], value);
				}
			}
			
			//检查节点是否未选中 true:未选中, false
			function isNodeUnchecked(zTree, nodeValue){
				var uncheckNodes =  zTree.getCheckedNodes(false);   
				for(var index = 0; index < uncheckNodes.length; index ++){
					var unCheckNode = uncheckNodes[index];
					var unCheckNodeValue = unCheckNode.data;
					if(unCheckNodeValue == nodeValue){
						return true;
					}
				}
				return false;
			}
			// 选中树
			function zTreeOnCheck(event, treeId, treeNode) {
				if (treeNode.isParent) {
					if (options.labelField != null) {
						$("#" + options.labelField).val("");
					}
					if (options.valueField != null) {
						$("#" + options.valueField).val("");
					}
					return;
				}
				// 设置值
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				
				var path = "";
				var value = "";
				  
				var oldValue = $("#" + options.valueField).val( );
				var oldNodePath = $("#" + options.labelField).val( );
				
			 
				var oldValues = oldValue.split(";");
				var oldNodePaths = oldNodePath.split(";");
				for(var index = 0; index < oldValues.length; index ++){
					if(oldNodePaths.length != oldValues.length){
						break;
					}
					var nodeValue = oldValues[index];
					if(nodeValue.length == 0){
						continue;
					}
					//if(typeof $this.$element.data["comboTreeValueLabelMap"] == "undefined"){continue;}
					//var nodePath = $this.$element.data["comboTreeValueLabelMap"][nodeValue]; 
					//if(typeof nodePath == "undefined"){continue;} 
					var unchecked = isNodeUnchecked(zTree, nodeValue);
					 
					if(!unchecked){//非unchecked状态
						if (value == "") {
							value = nodeValue;
							path = oldNodePaths[index];
						} else {
							value = value + ";" + nodeValue;
							path = path + ";" +  oldNodePaths[index];
						}  
					}
				}
				
				oldValue = ";"+ oldValue+";";
				var checkNodes = zTree.getCheckedNodes(true);
				for ( var index = 0; index < checkNodes.length; index++) {
					var checkNode = checkNodes[index];
					 
					var nodePath = "";
					var nodeValue = "";
					if ($this.enableRadio && $this.enableRadio == true) {
						if (checkNode !== treeNode) {
							zTree.checkNode(checkNode, false);
							continue;
						}
					}
					
					if(oldValue.indexOf(';'+checkNode.data+';')> -1){
						continue;
					}
				 
					if (options.labelField != null) {
						nodePath =  getAbsolutePath(checkNode);
						if (path == "") {
							path = nodePath;
						} else {
							path = path + ";" + nodePath;
						}
						
					}
					if (options.valueField != null) {
						nodeValue = checkNode.data;
						if (value == "") {
							value = nodeValue;
						} else {
							value = value + ";" + nodeValue;
						}
						if(!$this.$element.data["comboTreeValueLabelMap"]){$this.$element.data["comboTreeValueLabelMap"] = {};};
						$this.$element.data["comboTreeValueLabelMap"][nodeValue] = nodePath;
					}
				}
				
				$("#" + options.labelField).val(path);
				$("#" + options.valueField).val(value);
			}
			// 点击树
			function onClick(event, treeId, treeNode) {
				if (treeNode.isParent) {
					if (options.labelField != null) {
						$("#" + options.labelField).val("");
					}
					if (options.valueField != null) {
						$("#" + options.valueField).val("");
					}
					var zTree = $.fn.zTree.getZTreeObj(treeId);
					var checkNodes = zTree.getCheckedNodes(true);
					$.each(checkNodes, function() {
						zTree.checkNode(this, false);
					});
					return;
				}
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.checkNode(treeNode, !treeNode.checked);

				zTreeOnCheck(null, treeId, treeNode);
			}
			function onAsyncSuccess(event, treeId, treeNode, msg) {
				// 选中结点
				zTreeOnExpand(null, treeId, null);
			}
			// 获取树结点的绝对路径
			function getAbsolutePath(treeNode) {
				var path = treeNode.name;
				var parentNode = treeNode.getParentNode();
				while (parentNode != null) {
					path = parentNode.name + "/" + path;
					parentNode = parentNode.getParentNode();
				}
				return path;
			}

			if (this.treeInitialized !== true) {
				$.fn.zTree.init($("#" + treeId), setting);
				this.treeInitialized = true;
			} else {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.checkAllNodes(false);
				// 选中结点
				zTreeOnExpand(null, treeId, null);
			}
			var comboTree = this;
			comboTree.show();

			$(document).mousedown(
					function(event) {
						var id = event.target.id;
						if ((id != null) && (typeof id == "string")
								&& (id.lastIndexOf(treeId) == -1)
								&& (id != treeDivId)
								&& (id != $element.attr("id"))) {
							comboTree.hide();
						}
					});
		},
		show : function() {
			var treeDivId = this.treeDivId;
			var $element = this.$element;
			var outerHeight = $element.outerHeight();
			var offset = $element.position();
			$("#" + treeDivId).css({
				left : offset.left + "px",
				top : offset.top + outerHeight + "px"
			}).slideDown("fast");
		},
		hide : function() {
			var treeDivId = this.treeDivId;
			$("#" + treeDivId).fadeOut("fast");
		},
		_getCacheValueKey : function(value){
			var cacheValueKey = "DY_FORM_FIELD_MAPPING" + "_" + value;
			return cacheValueKey;
		},
		_getCacheLabelKey : function(value){
			var cacheLabelKey = this._getCacheValueKey(value) + "_label";
			return cacheLabelKey;
		},
		
		initValue : function(value) {   
			var initService = this.options.initService;
			var initServiceParam = this.options.initServiceParam;
			var param = initServiceParam.concat([ value ]);
			var options = this.options;
			var $element = this.$element;
			 
			var cacheValueKey = this._getCacheValueKey(value);
			var cacheLabelKey =  this._getCacheLabelKey(value);
			if ($element.data[cacheValueKey]) {
				if (options.labelField != null) {
					$("#" + options.labelField).val(
							$element.data[cacheLabelKey]);
				}
				if (options.valueField != null) {
					$("#" + options.valueField).val(
							$element.data[cacheValueKey]);
				}
				return;
			}
			if (value != null && value != "") {
				JDS
						.call({
							service : initService,
							data : param,
							success : function(result) {
								if (result.data) {
									if (options.labelField != null) {
										$("#" + options.labelField).val(
												result.data.label);
									}
									if (options.valueField != null) {
										$("#" + options.valueField).val(
												result.data.value);
									}
									$element.data[cacheLabelKey] = result.data.label;
									$element.data[cacheValueKey] = result.data.value;
									if( result.data.value){
										var values = result.data.value.split(";");
										var labels = result.data.label.split(";");
										$element.data["comboTreeValueLabelMap"] = {};
										for(var index = 0; values.length == labels.length && index < values.length ; index ++){ 
											var nodeValue = values[index];
											var nodePath = labels[index];
											$element.data["comboTreeValueLabelMap"][nodeValue] = nodePath;
										}
									}
									
								}
							}
						});
			} else {
				if (options.labelField != null) {
					$("#" + options.labelField).val("");
				}
				if (options.valueField != null) {
					$("#" + options.valueField).val("");
				}
			}
			
			
		},
		clear : function() {
			if (this.options.labelField != null) {
				$("#" + this.options.labelField).val("");
			}
			if (this.options.valueField != null) {
				$("#" + this.options.valueField).val("");
			}
		},
		disable : function() {
			this._enable = false;
			this.treeInitialized = false;
			$.fn.zTree.destroy(this.treeId);
		},
		enable : function() {
			this._enable = true;
		}
	};
	$.fn.comboTree = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		 
		return this.each(function() {
			 
			var $this = $(this), data = $this.data("comboTree"), options = $
					.extend({}, $this.data(), typeof option == 'object'
							&& option);
			if (!data) {
				$this.data('comboTree', (data = new ComboTree(this, options)));
			}
			if (typeof option == 'string') {
				if (method == true && args != null) {
					data[option](args);
				} else {
					data[option]();
				}
			} else if (options.show) {
				data.show();
			}
		});
	};

	$.fn.comboTree.defaults = {
		autoInitValue : true,
		serviceName : "dataDictionaryService",
		methodName : "getAsTreeAsync",
		initService : "dataDictionaryService.getKeyValuePair",
		initServiceParam : [ "DY_FORM_FIELD_MAPPING" ]
	};
})(jQuery);