(function($) {
		var DyView = function(element,options) {
			this.init("dyView", element, options);
		};
		
		DyView.prototype = {
				constructor : DyView,
				init: function(type,element,options) {
					
					this.$element = $(element);
					var $element = this.$element;
					this.options = this.getOptions(options);
					var options = this.options;
					var $this = this;
					var data = this.options.data;
					var pageTotalCount = this.options.data.pageTotalCount;
					var pageSize = this.options.data.pageSize;
					var fieldSelects = this.options.data.fieldSelects;
					var totalpageNum = pageTotalCount % pageSize == 0 ? pageTotalCount/pageSize : parseInt(pageTotalCount/pageSize) + 1;
					var $this = this;
					
						//关键字查询
						$("#keyWord").die().live("keyup",(function(event){
							$("#keySelect").attr("flag","flag");
							$("#fieldSelect").removeAttr("flag");
							ele = $(this);
							var code = event.keyCode;
				    	    if (code == 13) {
				    	    	var keyWordsArray = new Array();
								var keyWordObj = new Object();
								//全文搜索
								var keyWord = $("#keyWord").val();
								keyWord = keyWord.replace("关键字搜索","");
								keyWord = keyWord.replace(" ","");
								keyWordObj.all=keyWord;
								keyWordsArray.push(keyWordObj);
								$this.keySelectClick(keyWordsArray,ele);
							}
				    	    else{
								var keyWord = $("#keyWord").val();
								if(keyWord=="关键字搜索"||keyWord==""){
									if($("#keyWord").next().attr("class")=="clearText"){
										$(".clearText").remove();
									}
								}else{
									if($("#keyWord").next().attr("class")!="clearText"){
//										$("#keyWord").after("<div class='clearText'></div>");
									}
								}
							}
						}));
						
						//全文搜索查询按钮
						$("#keySelect").die().live("click",(function(){
							$(this).attr("flag","flag");
							$("#fieldSelect").removeAttr("flag");
								ele = $(this);
								var keyWordsArray = new Array();
								var keyWordObj = new Object();
								//全文搜索
								var keyWord = $("#keyWord").val();
								keyWord = keyWord.replace("关键字搜索","");
								keyWord = keyWord.replace(" ","");
								keyWordObj.all=keyWord;
								keyWordsArray.push(keyWordObj);
								$this.keySelectClick(keyWordsArray,ele);
//								$(".selectKeyText").each(function() {
//									var value = $(this).val();
//									if(value != "") {
//										$("#selectKeyTableId").css("display","");
//										$("#keyWord").css("display","none");
//										$("#showButton").text("↓");
//									}
//								});
							
						}));
						
						/**删除关键字**/
						$(".view_tool2 .clearText").die().live("click",(function(){
							ele = $(this);
							$(".view_tool2 .clearText").remove();
							$("#keyWord").val("");
							$this.keySelectClick("","","","",ele);
						}));
						
						//按字段查询点击按钮
						$(".Wdate").die().live("keyup",(function(event){
							var code = event.keyCode;
				    	    if (code == 13) {
				    	    	//给查询按钮添加一个标记
								$(this).attr("flag","flag");
								$("#keyWord").removeAttr("flag");
								//按字段查询
								if($(".fieldSelectTr").size() != 0 ) {
									var paramsArray = new Array();
									$(".view_search").find(".fieldSelectTd").each(function(index){
										var params = {};
										var $thisView = $(this);
										var fieldName = $thisView.find(".inputClass").attr("id");
										var selectTypeId = $thisView.find(".inputClass").attr("selectTypeId");
										if(selectTypeId == "TEXT") {
											var searchField = fieldName;
											params.searchField = searchField;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = "";
											params.endTime = "";
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = "";
											var fieldNameNew = "";
											fieldNameNew = fieldName.replace(".","\\.");
											var searchValue = $("#"+fieldNameNew).val();
											params.searchValue = searchValue;
											var isArea =  $thisView.find(".inputClass").attr("isArea"); 
											var isExact = $thisView.find(".inputClass").attr("isExact");
											var isLike  = $thisView.find(".inputClass").attr("isLike");
											if(isArea == 'true') {
												searchField = $thisView.find(".inputClass").attr("fieldName");
												params.isArea = "true";
												var searchValueFirst = $("#"+searchField+"_first").val();
												var searchValueLast = $("#"+searchField+"_last").val();
												params.searchValue = searchValueFirst + "|" + searchValueLast;
											}
											if(isExact == 'true') {
												params.isExact = "true";
												var exactValue = $thisView.find(".inputClass").attr("exactValue");
											}
											if(isLike == "true") {
												params.isLike = "true";
											}
											paramsArray.push(params);
										}else if(selectTypeId == "DATE") {
											var searchField = $thisView.find(".inputClass").attr("searchField");
											var ctl_begin = $("#"+searchField+"_begin").wdatePicker("getObject");
											 var ctlBeginValue = ctl_begin.getValue();
											 var ctl_end = $("#"+searchField+"_end").wdatePicker("getObject");
											 var ctlEndValue = ctl_end.getValue();
												params.searchField = searchField;
												var searchFieldTypeId = selectTypeId;
												params.searchFieldTypeId = searchFieldTypeId;
												params.beginTime = ctlBeginValue;
												params.endTime = ctlEndValue;
												params.isArea = "";
												params.isExact = "";
												params.isLike = "";
												params.searchValue = "";
												paramsArray.push(params);
										}else if(selectTypeId == "ORG") {
											var ctl = $("#"+fieldName).wunit("getObject");
											params.searchField = fieldName;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = "";
											params.endTime = "";
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = ctl.getValue();
											paramsArray.push(params);
										}else if(selectTypeId == "SELECT") {
											 var ctl = $("#"+fieldName).wcomboBox("getObject");
											 var checkBoxValue = ctl.getValue();
											 var searchField = fieldName;
												params.searchField = searchField;
												var searchFieldTypeId = selectTypeId;
												params.searchFieldTypeId = searchFieldTypeId;
												params.beginTime = "";
												params.endTime = "";
												params.isArea = "";
												params.isExact = "";
												params.isLike = "";
												params.searchValue = "";
												var searchValue = checkBoxValue;
												params.searchValue = searchValue;
												var isArea =  $thisView.find(".inputClass").attr("isArea"); 
												var isExact = $thisView.find(".inputClass").attr("isExact");
												var isLike  = $thisView.find(".inputClass").attr("isLike");
												if(isArea == true) {
													params.isArea = "true";
												}
												if(isExact == true) {
													params.isExact = "true";
													var exactValue = $thisView.find(".inputClass").attr("exactValue");
												}
												if(isLike == "true") {
													params.isLike = "true";
												}
												paramsArray.push(params);
										}else if(selectTypeId == "RADIO") {
											 var ctl = $("#"+fieldName).wradio("getObject");
											 var checkBoxValue = ctl.getValue();
											 var searchField = fieldName;
												params.searchField = searchField;
												var searchFieldTypeId = selectTypeId;
												params.searchFieldTypeId = searchFieldTypeId;
												params.beginTime = "";
												params.endTime = "";
												params.isArea = "";
												params.isExact = "";
												params.isLike = "";
												params.searchValue = "";
												var searchValue = checkBoxValue;
												params.searchValue = searchValue;
												var isArea =  $thisView.find(".inputClass").attr("isArea"); 
												var isExact = $thisView.find(".inputClass").attr("isExact");
												var isLike  = $thisView.find(".inputClass").attr("isLike");
												if(isArea == true) {
													params.isArea = "true";
												}
												if(isExact == true) {
													params.isExact = "true";
													var exactValue = $thisView.find(".inputClass").attr("exactValue");
												}
												if(isLike == "true") {
													params.isLike = "true";
												}
												paramsArray.push(params);
										}else if(selectTypeId == "CHECKBOX") {
											 var ctl = $("#"+fieldName).wcheckBox("getObject");
											 var checkBoxValue = ctl.getValue();
											 var searchField = fieldName;
												params.searchField = searchField;
												var searchFieldTypeId = selectTypeId;
												params.searchFieldTypeId = searchFieldTypeId;
												params.beginTime = "";
												params.endTime = "";
												params.isArea = "";
												params.isExact = "";
												params.isLike = "";
												params.searchValue = "";
												var searchValue = checkBoxValue;
												params.searchValue = searchValue;
												var isArea =  $thisView.find(".inputClass").attr("isArea"); 
												var isExact = $thisView.find(".inputClass").attr("isExact");
												var isLike  = $thisView.find(".inputClass").attr("isLike");
												if(isArea == true) {
													params.isArea = "true";
												}
												if(isExact == true) {
													params.isExact = "true";
													var exactValue = $thisView.find(".inputClass").attr("exactValue");
												}
												if(isLike == "true") {
													params.isLike = "true";
												}
												paramsArray.push(params);
										}else if(selectTypeId == "DIAlOG") {
											
										}
									});
									$this.fieldSelectClick(paramsArray);
								}
				    	    }
						}));
						
						//按字段查询点击按钮
						$("#fieldSelect").die().live("click",(function(){
							//给查询按钮添加一个标记
							$(this).attr("flag","flag");
							$("#keyWord").removeAttr("flag");
							//按字段查询
							if($(".fieldSelectTr").size() != 0 ) {
								var paramsArray = new Array();
								$(".view_search").find(".fieldSelectTd").each(function(index){
									var params = {};
									var $thisView = $(this);
									var fieldName = $thisView.find(".inputClass").attr("id");
									var selectTypeId = $thisView.find(".inputClass").attr("selectTypeId");
									if(selectTypeId == "TEXT") {
										var searchField = fieldName;
										params.searchField = searchField;
										var searchFieldTypeId = selectTypeId;
										params.searchFieldTypeId = searchFieldTypeId;
										params.beginTime = "";
										params.endTime = "";
										params.isArea = "";
										params.isExact = "";
										params.isLike = "";
										params.searchValue = "";
										var fieldNameNew = "";
										fieldNameNew = fieldName.replace(".","\\.");
										var searchValue = $("#"+fieldNameNew).val();
										params.searchValue = searchValue;
										var isArea =  $thisView.find(".inputClass").attr("isArea"); 
										var isExact = $thisView.find(".inputClass").attr("isExact");
										var isLike  = $thisView.find(".inputClass").attr("isLike");
										if(isArea == 'true') {
											searchField = $thisView.find(".inputClass").attr("fieldName");
											params.isArea = "true";
											var searchValueFirst = $("#"+searchField+"_first").val();
											var searchValueLast = $("#"+searchField+"_last").val();
											params.searchValue = searchValueFirst + "|" + searchValueLast;
										}
										if(isExact == 'true') {
											params.isExact = "true";
											var exactValue = $thisView.find(".inputClass").attr("exactValue");
										}
										if(isLike == "true") {
											params.isLike = "true";
										}
										paramsArray.push(params);
									}else if(selectTypeId == "DATE") {
										var searchField = $thisView.find(".inputClass").attr("searchField");
										var ctl_begin = $("#"+searchField+"_begin").wdatePicker("getObject");
										 var ctlBeginValue = ctl_begin.getValue();
										 var ctl_end = $("#"+searchField+"_end").wdatePicker("getObject");
										 var ctlEndValue = ctl_end.getValue();
											params.searchField = searchField;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = ctlBeginValue;
											params.endTime = ctlEndValue;
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = "";
											paramsArray.push(params);
									}else if(selectTypeId == "ORG") {
										var ctl = $("#"+fieldName).wunit("getObject");
										params.searchField = fieldName;
										var searchFieldTypeId = selectTypeId;
										params.searchFieldTypeId = searchFieldTypeId;
										params.beginTime = "";
										params.endTime = "";
										params.isArea = "";
										params.isExact = "";
										params.isLike = "";
										params.searchValue = ctl.getValue();
										paramsArray.push(params);
									}else if(selectTypeId == "SELECT") {
										 var ctl = $("#"+fieldName).wcomboBox("getObject");
										 var checkBoxValue = ctl.getValue();
										 var searchField = fieldName;
											params.searchField = searchField;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = "";
											params.endTime = "";
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = "";
											var searchValue = checkBoxValue;
											params.searchValue = searchValue;
											var isArea =  $thisView.find(".inputClass").attr("isArea"); 
											var isExact = $thisView.find(".inputClass").attr("isExact");
											var isLike  = $thisView.find(".inputClass").attr("isLike");
											if(isArea == true) {
												params.isArea = "true";
											}
											if(isExact == true) {
												params.isExact = "true";
												var exactValue = $thisView.find(".inputClass").attr("exactValue");
											}
											if(isLike == "true") {
												params.isLike = "true";
											}
											paramsArray.push(params);
									}else if(selectTypeId == "RADIO") {
										 var ctl = $("#"+fieldName).wradio("getObject");
										 var checkBoxValue = ctl.getValue();
										 var searchField = fieldName;
											params.searchField = searchField;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = "";
											params.endTime = "";
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = "";
											var searchValue = checkBoxValue;
											params.searchValue = searchValue;
											var isArea =  $thisView.find(".inputClass").attr("isArea"); 
											var isExact = $thisView.find(".inputClass").attr("isExact");
											var isLike  = $thisView.find(".inputClass").attr("isLike");
											if(isArea == true) {
												params.isArea = "true";
											}
											if(isExact == true) {
												params.isExact = "true";
												var exactValue = $thisView.find(".inputClass").attr("exactValue");
											}
											if(isLike == "true") {
												params.isLike = "true";
											}
											paramsArray.push(params);
									}else if(selectTypeId == "CHECKBOX") {
										 var ctl = $("#"+fieldName).wcheckBox("getObject");
										 var checkBoxValue = ctl.getValue();
										 var searchField = fieldName;
											params.searchField = searchField;
											var searchFieldTypeId = selectTypeId;
											params.searchFieldTypeId = searchFieldTypeId;
											params.beginTime = "";
											params.endTime = "";
											params.isArea = "";
											params.isExact = "";
											params.isLike = "";
											params.searchValue = "";
											var searchValue = checkBoxValue;
											params.searchValue = searchValue;
											var isArea =  $thisView.find(".inputClass").attr("isArea"); 
											var isExact = $thisView.find(".inputClass").attr("isExact");
											var isLike  = $thisView.find(".inputClass").attr("isLike");
											if(isArea == true) {
												params.isArea = "true";
											}
											if(isExact == true) {
												params.isExact = "true";
												var exactValue = $thisView.find(".inputClass").attr("exactValue");
											}
											if(isLike == "true") {
												params.isLike = "true";
											}
											paramsArray.push(params);
									}else if(selectTypeId == "DIAlOG") {
										
									}
								});
								$this.fieldSelectClick(paramsArray);
							}
						}));
						
						//根据前台页面展示的箭头显示或隐藏精确关键字搜索的界面
						$("#showButton").die().live("click",function() {
							var $this = $(this);
							if($this.text() == "↑") {
								$(".clearText").css("display","none");
								$(".view_search").css("display","");
								$("#keyWord").css("display","none");
								$("#keySelect").css("display","none");
								$("#fieldSelect").css("display","");
								$this.text("↓");
								jsmod(".dnrw .tab-content",true);
							}else if($this.text() == "↓") {
								$("#fieldSelect").css("display","none");
								$(".view_search").css("display","none");
								$("#keyWord").css("display","");
								$("#keySelect").css("display","");
								$this.text("↑");
								jsmod(".dnrw .tab-content",true);
							}
							
						});
						
						//条件查询
						$(".cond_class").die().live("click",(function(){
							var class_ = $(this).find("a").attr("class");
							if(class_!=undefined && class_.indexOf("cond_class_bg")>-1){
								$(".cond_class a").attr("class","cond_class_a");
								refreshWindow($(this));
							}else{
								$(".cond_class a").attr("class","cond_class_a");
								$(this).find("a").attr("class","cond_class_a cond_class_bg");
								var value = $(this).attr("value");
								var appointColumn = $(this).attr("appointColumn");
								var viewUuid = $(this).parents("#abc").find("#viewUuid").val();
								$this.condSelectClick({value:value,appointColumn:appointColumn});
							}
						}));
					
						//点击排序
						$(".sortAble").die().live('click',function(){
							var this_ = $(this);
							var index = $(".sortAble").index(this_);
							var dyViewQueryInfo = new Object();
							var orderbyArr = this_.attr("orderby");
							var viewUuid = $(this).parents("#abc").find("#viewUuid").val();
							var currentPage = $(this).parents("#abc").find("#pageCurrentPage").val();
							var pageSize = $(this).parents("#abc").find("#pageSize").val();
							var title = this_.text().replace("↓","").replace("↑","");
							dyViewQueryInfo.viewUuid = viewUuid;
							var condSelect = new Object();
							condSelect.orderTitle = title;
							condSelect.orderbyArr = orderbyArr;
							var pageInfo = new Object();
							pageInfo.currentPage = currentPage;
							pageInfo.pageSize = pageSize;
							dyViewQueryInfo.condSelect = condSelect;
							dyViewQueryInfo.pageInfo = pageInfo;
							
							var parmStr = this_.parents("#abc").find("#parmStr").val(); 
							var parmArray = parmStr.split("&");
							var expandParams = new Object();
							for(var o=0;o<parmArray.length;o++){
								var key_ = parmArray[o].split("=")[0];
								var val_ = parmArray[o].split("=")[1];
								if(key_!=""){
									if(key_=='openBy'){
										dyViewQueryInfo.openBy = val_;
									}else{
										expandParams[key_] = val_;
									}
								}
								
							}
							dyViewQueryInfo.expandParams = expandParams;
							
//							alert(JSON.stringify(dyViewQueryInfo))
							var url = ctx + '/basicdata/view/view_show_param.action';
							$(".sortAble").each(function(){
								$(this).text($(this).text().replace("↓","").replace("↑",""));
							});
							$.ajax({
								url:url,
								type:"POST",
								data:JSON.stringify(dyViewQueryInfo),
								dataType:'text',
								contentType:'application/json',
								success:function(data) {
									$("#update_"+viewUuid).html(data);
									formDate();
									var title_ = $(".sortAble").eq(index).html();
									if(orderbyArr == "asc") {
										$(".sortAble").eq(index).attr("orderby","desc");
										$(".sortAble").eq(index).html(title_+"↓");
									}else {
										$(".sortAble").eq(index).attr("orderby","asc");
										$(".sortAble").eq(index).html(title_+"↑");
									}
								}
							});
						});
				},
				getOptions : function(options) {
					options = $.extend({}, $.fn.dyView.defaults, options,
							this.$element.data());
					return options;
				},
				//点击排序
				clickSort: function(data) {
					var dyViewQueryInfo = this.collectData(this);
					var viewUuid = data.viewUuid;
					var currentPage = data.pageCurrentPage;
					var pageSize = data.pageSize;
					var orderTitle = $(this).text();
					dyViewQueryInfo.viewUuid = viewUuid;
					dyViewQueryInfo.condSelect.orderTitle = orderTitle;
					
					dyViewQueryInfo.pageInfo.currentPage = currentPage;
					dyViewQueryInfo.pageInfo.pageSize = pageSize;
					
						var url = ctx + '/basicdata/view/view_show_param.action';
						$.ajax({
							url:url,
							type:"POST",
							data:JSON.stringify(dyViewQueryInfo),
							dataType:'text',
							contentType:'application/json',
							success:function(data) {
								$("#update_"+viewUuid).html(data);
								formDate();
							}
						});
				},
				
				
				//按条件查询
				condSelectClick:function(params) {
					var dyViewQueryInfo = this.collectData(this);
					var pageInfo = new Object();
					var condSelect = new Object();
					var currentPage = this.options.data.pageCurrentPage;
					var pageSize = this.options.data.pageSize;
					dyViewQueryInfo.viewUuid = viewUuid;
					pageInfo.currentPage = currentPage;
					pageInfo.pageSize = pageSize;
					condSelect.optionValue = params.value;
					condSelect.appointColumn = params.appointColumn;
					dyViewQueryInfo.pageInfo = pageInfo;
					dyViewQueryInfo.condSelect = condSelect;
					var url = ctx + '/basicdata/view/view_show_param.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(dyViewQueryInfo),
						dataType:"text",
						contentType:'application/json',
						success:function(data) {
							if(data == "") {
								alert("抱歉,没有符合条件的记录!");
							}
							$("#update_"+viewUuid).html(data);
							formDate();
						}
					});
				},
				
				//按字段查询
				fieldSelectClick:function(paramsArray) {
					var dyViewQueryInfo = this.collectData(this);
					dyViewQueryInfo.condSelectList = paramsArray;
					var url = ctx + '/basicdata/view/view_show_param.action';
					$.ajax({
						async:false,
						url:url,
						type:"POST",
						data:JSON.stringify(dyViewQueryInfo),
						dataType:'text',
						contentType:'application/json',
						success:function(data) {
							if(data == "") {
								alert("抱歉,没有符合条件的记录!");
							}
							$("#update_"+dyViewQueryInfo.viewUuid).html(data);
							$(".view_search").show();
//							$(".view_keyword_div").hide();
							formDate();
						}
					});
				},
				
				//关键字点击查询
				keySelectClick:function(keyWords,ele) {
						var parmStr = ele.parents(".templateDiv").next(".viewContent").find("#parmStr").val();
						var dyViewQueryInfo = this.collectData(this);
						var condSelectList = new Array();
						condSelectList.push({"keyWords":keyWords});
						dyViewQueryInfo.condSelectList = condSelectList;
						var parmArray = parmStr.split("&");
						var expandParams = new Object();
						for(var o=0;o<parmArray.length;o++){
							var key_ = parmArray[o].split("=")[0];
							var val_ = parmArray[o].split("=")[1];
							if(key_!=""){
								if(key_=='openBy'){
									dyViewQueryInfo.openBy = val_;
								}else{
									expandParams[key_] = val_;
								}
							}
							
						}
						dyViewQueryInfo.expandParams = expandParams;
						var url = ctx + '/basicdata/view/view_show_param.action';
						$.ajax({
							async:false,
							url:url,
							type:"POST",
							data:JSON.stringify(dyViewQueryInfo),
							dataType:'text',
							contentType:'application/json',
							success:function(data) {
								if(data == "") {
									alert("抱歉,没有符合条件的记录!");
								}
								$("#update_"+dyViewQueryInfo.viewUuid).html(data);
								formDate();
							}
						});
				},
				//收集点击查询的数据
				collectData:function(element) {
					var dyViewQueryInfo = new Object();
					var condSelectList = new Array();
					var pageInfo = new Object();
					var viewUuid = this.options.data.viewUuid;
					var pageSize = this.options.data.pageSize;
//					var pageCurrentPage = this.options.data.pageCurrentPage;
					var pageCurrentPage = 1;
					dyViewQueryInfo.viewUuid = viewUuid;
					dyViewQueryInfo.viewName = "";
					pageInfo.currentPage = pageCurrentPage;
					pageInfo.pageSize = pageSize;
					pageInfo.totalCount = 0;
					pageInfo.first = 0;
					pageInfo.autoCount = true;
					dyViewQueryInfo.condSelectList = condSelectList;
					dyViewQueryInfo.pageInfo = pageInfo;
					return dyViewQueryInfo;
				}
		};
		
		$.fn.dyView = function(option) {
			var args = null;
			if(arguments.length == 2) {
				args = arguments[1];
			}
			return this
			.each(function() {
				var $this = $(this), data = $this.data("dyView"), options = $
						.extend({}, $this.data(), typeof option == 'object'
								&& option);
				if (!data) {
					$this.data('dyView',
							(data = new DyView(this, options)));
				}
				
				if (typeof option == 'string') {
					if(typeof args != null){
						return data[option](args);
					}
					return data[option]();
				}
			});
		};
		
		$.fn.dyView.Constructor = DyView;
		
		$.fn.dyView.defaults = {
		};
})(jQuery);

//视图解析的导出功能
function exportData() {
	var dataArray = new Array();
	$(".checkeds").each(function(){
		var $this = $(this);
		if($this.attr("checked")){
			var dataUuid = $this.val();
			dataArray.push(dataUuid);
		}
	});
	
	 var url = ctx+"/basicdata/excelexportrule/export?viewUuid="+viewUuid+"&dataArray="+dataArray;
	 window.location.href=url;
}

//格式化时间
function formDate(){
	$(".dataTr td").each(function(){
		var _reTimeReg = /^(?:19|20)[0-9][0-9]-(?:(?:[1-9])|(?:1[0-2]))-(?:(?:[1-9])|(?:[1-2][1-9])|(?:[1-3][0-1])) (?:(?:2[0-3])|(?:1[0-9])|(?:[1-9])|0):[0-5][0-9]:[0-5][0-9]$/;
		var tempText = $(this).text();
		if(_reTimeReg.test(tempText)){
			
			var dataStr = tempText.split(" ")[0];
			var timeStr = tempText.split(" ")[1];
			var dataStrArray = dataStr.split("-");
			var timeStrArray = timeStr.split(":");
			var strArray = new Array();
			strArray[0] = dataStrArray[0];
			strArray[1] = dataStrArray[1];
			strArray[2] = dataStrArray[2];
			strArray[3] = timeStrArray[0];
			strArray[4] = timeStrArray[1];
			strArray[5] = timeStrArray[2];
			for(var i=1;i<6;i++){
				if(strArray[i].length==1){
					strArray[i] = 0+""+strArray[i];
				}
			}
		var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4]+":"+strArray[5];
//			var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4];
			$(this).text(str);
			$(this).attr("title",str);
		}
	});
}