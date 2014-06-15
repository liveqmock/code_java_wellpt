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
						
						//关键字查询
						$("#keyWord").die().live("keyup",(function(event){
							var code = event.keyCode;
				    	    if (code == 13) {
				    	    	var keyWord = $("#keyWord").val();
				    	    	keyWord = keyWord.replace(" ","");
								$this.keySelectClick(keyWord,"","","");
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
							$(".view_tool2 .clearText").remove();
							$("#keyWord").val("");
							$this.keySelectClick("","","","");
						}));
						
						$("#keySelect").die().live("click",(function(){
							var keyWord = $("#keyWord").val();
							keyWord = keyWord.replace(" ","");
							var beginTime = $("#beginTime").val();
							if(beginTime=="开始时间"){
								beginTime = "";
							}
							var endTime = $("#endTime").val();
							if(endTime=="结束时间"){
								endTime = "";
							}
							var searchField = $(".searchField").val();
							$this.keySelectClick(keyWord,beginTime,endTime,searchField);
						}));
							
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
							var params = {};
							var orderbyArr = this_.attr("orderby");
							var viewUuid = $(this).parents("#abc").find("#viewUuid").val();
							var currentPage = $(this).parents("#abc").find("#pageCurrentPage").val();
							var pageSize = $(this).parents("#abc").find("#pageSize").val();
							var title = this_.text().replace("↓","").replace("↑","");
							params.viewUuid = viewUuid;
							params.title = title;
							params.currentPage = currentPage;
							params.pageSize = pageSize;
							params.orderbyArr = orderbyArr;
							var url = ctx + '/basicdata/dyview/view_data_sort.action';
							$(".sortAble").each(function(){
								$(this).text($(this).text().replace("↓","").replace("↑",""));
							});
							$.ajax({
								url:url,
								type:"POST",
								data:JSON.stringify(params),
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
						//日期不限
						$("#allDay").click(function() {
							var beginTime = "";
							var endTime = "";
							var appointColumn = $(this).attr("appointColumn");
							$this.dateSelectClick({beginTime:beginTime,endTime:endTime,appointColumn:appointColumn});
							});
						
						//获取日期条件中的今天备选项
						$("#today").click(function() {
							var today = $(this).attr("today");
							var appointColumn = $(this).attr("appointColumn");
							$this.condSelectClick({value:today,appointColumn:appointColumn});
						});
						
						//获取日期条件中的昨天备选项
						$("#yesterday").click(function() {
							var yesterday = $(this).attr("yesterday");
							var appointColumn = $(this).attr("appointColumn");
							$this.condSelectClick({value:yesterday,appointColumn:appointColumn});
						});
						
						//获取日期条件中的上一周备选项
						$("#lastWeek").click(function() {
							var lastWeekFirstDay = $(this).attr("lastWeekFirstDay");
							var lastWeekSunday = $(this).attr("lastWeekSunday");
							var appointColumn = $(this).attr("appointColumn");
							$this.dateSelectClick({beginTime:lastWeekFirstDay,endTime:lastWeekSunday,appointColumn:appointColumn});
						});
						
						//获取日期条件中的上一月备选项
						$("#lastMonth").click(function() {
							var lastMonthFirstDay = $(this).attr("lastMonthFirstDay");
							var lastMonthLastDay = $(this).attr("lastMonthLastDay");
							var appointColumn = $(this).attr("appointColumn");
							$this.dateSelectClick({beginTime:lastMonthFirstDay,endTime:lastMonthLastDay,appointColumn:appointColumn});
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
					var params = {};
					var viewUuid = data.viewUuid;
					var currentPage = data.pageCurrentPage;
					var pageSize = data.pageSize;
					var title = $(this).text();
					params.viewUuid = viewUuid;
					params.title = title;
					params.currentPage = currentPage;
					params.pageSize = pageSize;
					
						var url = ctx + '/basicdata/dyview/view_data_sort.action';
						$.ajax({
							url:url,
							type:"POST",
							data:JSON.stringify(params),
							dataType:'text',
							contentType:'application/json',
							success:function(data) {
								$("#update_"+viewUuid).html(data);
								formDate();
							}
						});
				},
				
				
				//按条件查询
				condSelectClick:function(param) {
					var params = {};
					var viewUuid = window.viewId_.replace("update_","");
					var currentPage = this.options.data.pageCurrentPage;
					var pageSize = this.options.data.pageSize;
					params.viewUuid = viewUuid;
					params.currentPage = currentPage;
					params.value = param.value;
					params.appointColumn = param.appointColumn;
					params.pageSize = pageSize;
					var url = ctx + '/basicdata/dyview/view_data_condSelect.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(params),
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
				
				//按日期查询
				dateSelectClick:function(param) {
					var Data = this.options.data;
					var params = {};
					var viewUuid = this.options.data.viewUuid;
					var currentPage = this.options.data.pageCurrentPage;
					params.viewUuid = viewUuid;
					params.currentPage = currentPage;
					params.beginTime = param.beginTime;
					params.endTime = param.endTime;
					params.appointColumn = param.appointColumn;
					var url = ctx + '/basicdata/dyview/view_data_dateSelect.action';
					$.ajax({
						url:url,
						type:"POST",
						data:JSON.stringify(params),
						dataType:"text",
						contentType:'application/json',
						success:function(data) {
							if(data == "") {
								alert("抱歉,没有符合条件的记录!");
							}
							Data.clickType = "date";
							$("#template").html(data);
							formDate();
						}
					});
				},
				
				//关键字点击查询
				keySelectClick:function(keyWord,beginTime,endTime,searchField) {
						var params = this.collectData(this);
						var keyWords = new Array();
						var Data = this.options.data;
						keyWord = keyWord.replace("关键字搜索","");
						keyWords = keyWord.split(",");
						params.keyWords = keyWords;
						params.beginTime = beginTime;
						params.endTime = endTime;
						params.searchField = searchField;
						var url = ctx + '/basicdata/dyview/view_data_keySelect.action';
						$.ajax({
							url:url,
							type:"POST",
							data:JSON.stringify(params),
							dataType:'text',
							contentType:'application/json',
							success:function(data) {
								if(data == "") {
									alert("抱歉,没有符合条件的记录!");
								}
								$("#update_"+params.viewUuid).html(data);
								formDate();
								
							}
						});
				},
				//收集点击查询的数据
				collectData:function(element) {
					var viewUuid = this.options.data.viewUuid;
					var pageSize = this.options.data.pageSize;
					var pageCurrentPage = this.options.data.pageCurrentPage;
					var params = {};
					params.viewUuid = viewUuid;
					params.currentPage = pageCurrentPage;
					params.pageSize = pageSize;
					return params;
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