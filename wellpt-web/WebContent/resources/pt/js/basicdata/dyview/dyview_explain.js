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
					
					var totalpageNum = pageTotalCount % pageSize == 0 ? pageTotalCount/pageSize : parseInt(pageTotalCount/pageSize) + 1;
						
					
						var $this = this;
						
						//敲击回车的搜索事件
						$(".selectKeyText").die().live("keyup",(function(event){
							ele = $(this);
							var code = event.keyCode;
							if (code == 13) {
								var keyWordsArray = new Array();
								var keyWordObj = new Object();
								if($("#selectKeyTableId").attr("id") != undefined) {
									$(".selectKeyText").each(function(){
										var keyWordKey = $(this).attr("field");
										var keyWordValue = $(this).val().replace(" ","");
										keyWordObj[keyWordKey] = keyWordValue;
									});
									keyWordsArray.push(keyWordObj);
								}
								var beginTime = $("#beginTime").val();
								if(beginTime=="开始时间"){
									beginTime = "";
								}
								var endTime = $("#endTime").val();
								if(endTime=="结束时间"){
									endTime = "";
								}
								var searchField = $(".searchField").val();
//				    	    	alert("keyWordsArray " + JSON.stringify(keyWordsArray));
								$this.keySelectClick(keyWordsArray,beginTime,endTime,searchField,ele);
								
								$(".selectKeyText").each(function() {
									var value = $(this).val();
									if(value != "") {
										$("#selectKeyTableId").css("display","");
										$("#keyWord").css("display","none");
										$("#showButton").text("↓");
									}
								});
							}
						}));
						
						//关键字查询
						$("#keyWord").die().live("keyup",(function(event){
							ele = $(this);
							var code = event.keyCode;
				    	    if (code == 13) {
				    	    	var keyWordsArray = new Array();
								var keyWordObj = new Object();
								//精确关键字查询
								if($("#selectKeyTableId").attr("id") != undefined && $("#selectKeyTableId").css("display") != "none") {
									$(".selectKeyText").each(function(){
										var keyWordKey = $(this).attr("field");
										var keyWordValue = $(this).val().replace(" ","");
										keyWordObj[keyWordKey] = keyWordValue;
									});
									keyWordsArray.push(keyWordObj);
								}else {
									//全文搜索
									var keyWord = $("#keyWord").val();
									keyWord = keyWord.replace("关键字搜索","");
									keyWord = keyWord.replace(" ","");
									keyWordObj.all=keyWord;
									keyWordsArray.push(keyWordObj);
								}
								var beginTime = $("#beginTime").val();
								if(beginTime=="开始时间"){
									beginTime = "";
								}
								var endTime = $("#endTime").val();
								if(endTime=="结束时间"){
									endTime = "";
								}
								var searchField = $(".searchField").val();
//								alert("keyWordsArray " + JSON.stringify(keyWordsArray));
								$this.keySelectClick(keyWordsArray,beginTime,endTime,searchField,ele);
							}
				    	    else{
								var keyWord = $("#keyWord").val();
								if(keyWord=="关键字搜索"||keyWord==""){
									if($("#keyWord").next().attr("class")=="clearText"){
										$(".clearText").remove();
									}
								}else{
									if($("#keyWord").next().attr("class")!="clearText"){
										$("#keyWord").after("<div class='clearText'></div>");
									}
								}
							}
						}));
						/**删除关键字**/
						$(".view_tool2 .clearText").die().live("click",(function(){
							ele = $(this);
							$(".view_tool2 .clearText").remove();
							$("#keyWord").val("");
							$this.keySelectClick("","","","",ele);
						}));
						
						$("#keySelect").die().live("click",(function(){
							ele = $(this);
							var keyWordsArray = new Array();
							var keyWordObj = new Object();
							//精确关键字查询
							if($("#selectKeyTableId").attr("id") != undefined && $("#selectKeyTableId").css("display") != "none") {
								$(".selectKeyText").each(function(){
									var keyWordKey = $(this).attr("field");
									var keyWordValue = $(this).val().replace(" ","");
									keyWordObj[keyWordKey] = keyWordValue;
								});
								keyWordsArray.push(keyWordObj);
							}else {
								//全文搜索
								var keyWord = $("#keyWord").val();
								keyWord = keyWord.replace("关键字搜索","");
								keyWord = keyWord.replace(" ","");
								keyWordObj.all=keyWord;
								keyWordsArray.push(keyWordObj);
							}
							var beginTime = $("#beginTime").val();
							if(beginTime=="开始时间"){
								beginTime = "";
							}
							var endTime = $("#endTime").val();
							if(endTime=="结束时间"){
								endTime = "";
							}
							var searchField = $(".searchField").val();
//							alert("keyWordsArray " + JSON.stringify(keyWordsArray));
							$this.keySelectClick(keyWordsArray,beginTime,endTime,searchField,ele);
							
								$(".selectKeyText").each(function() {
									var value = $(this).val();
									if(value != "") {
										$("#selectKeyTableId").css("display","");
										$("#keyWord").css("display","none");
										$("#showButton").text("↓");
									}
								});
							
						}));
						//根据前台页面展示的箭头显示或隐藏精确关键字搜索的界面
						$("#showButton").die().live("click",function() {
							var $this = $(this);
							if($this.text() == "↑") {
								$("#selectKeyTableId").css("display","");
								$("#keyWord").css("display","none");
								$this.text("↓");
								jsmod(".dnrw .tab-content");
							}else if($this.text() == "↓") {
								$("#selectKeyTableId").css("display","none");
								$("#keyWord").css("display","");
								$this.text("↑");
//								jsmod(".dnrw .tab-content");
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
								window.viewId_ = $(this).parents("#abc").find(".view_content_list").attr("id");
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
							var url = ctx + '/basicdata/dyview/view_show_param.action';
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
						
						
						//选择日期
						$("#chooseDate").click(function() {
							$("#dateInput").css("display","");
							var appointColumn = $("#datepicker").attr("appointColumn");
							$("#datepicker").datepicker(
								{
									onClose:function(selectedDate ) {
										$this.condSelectClick({value:selectedDate ,appointColumn:appointColumn});
									$("#dateInput").css("display","none");
								}
							});
							$( "#datepicker" ).datepicker( "option", "dateFormat","yy-mm-dd" );
							
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
					
						var url = ctx + '/basicdata/dyview/view_show_param.action';
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
					var dyViewQueryInfo = new Object();
					var pageInfo = new Object();
					var condSelect = new Object();
					var viewUuid = window.viewId_.replace("update_","");
					var currentPage = this.options.data.pageCurrentPage;
					var pageSize = this.options.data.pageSize;
					dyViewQueryInfo.viewUuid = viewUuid;
					pageInfo.currentPage = currentPage;
					pageInfo.pageSize = pageSize;
					condSelect.optionValue = param.value;
					condSelect.appointColumn = param.appointColumn;
					dyViewQueryInfo.pageInfo = pageInfo;
					dyViewQueryInfo.condSelect = condSelect;
					var url = ctx + '/basicdata/dyview/view_data_condSelect.action';
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
							$("#"+window.viewId_).html(data);
							formDate();
						}
					});
				},
				
				//关键字点击查询
				keySelectClick:function(keyWords,beginTime,endTime,searchField,ele) {
						
						var parmStr = ele.parents("#abc").find("#parmStr").val(); 
						var dyViewQueryInfo = this.collectData(this);
						var condSelect = dyViewQueryInfo.condSelect;
						condSelect.keyWords = keyWords;
						condSelect.beginTime = beginTime;
						condSelect.endTime = endTime;
						condSelect.searchField = searchField;
						dyViewQueryInfo.condSelect = condSelect;
						
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
						
						
						var url = ctx + '/basicdata/dyview/view_show_param.action';
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
					var condSelect = new Object();
					condSelect.optionTitle = "";
					condSelect.optionValue = "";
					condSelect.appointColumn = "";
					condSelect.beginTime = "";
					condSelect.endTime = "";
					condSelect.searchField = "";
					condSelect.keyWords = [];
					condSelect.orderbyArr = "";
					condSelect.orderTitle = "";
					
					var pageInfo = new Object();
					var viewUuid = this.options.data.viewUuid;
					var pageSize = this.options.data.pageSize;
					var pageCurrentPage = this.options.data.pageCurrentPage;
					dyViewQueryInfo.viewUuid = viewUuid;
					dyViewQueryInfo.viewName = "";
					pageInfo.currentPage = pageCurrentPage;
					pageInfo.pageSize = pageSize;
					pageInfo.totalCount = 0;
					pageInfo.first = 0;
					pageInfo.autoCount = true;
					dyViewQueryInfo.condSelect = condSelect;
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
/**		var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4]+":"+strArray[5];**/
			var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4];
			$(this).text(str);
			$(this).attr("title",str);
		}
	});
}