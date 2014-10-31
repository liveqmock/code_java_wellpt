;
(function($) {
	var columnProperty={
			//控件字段属性
			applyTo:null,//应用于
			columnName:null,//字段定义  fieldname
			displayName:null,//描述名称  descname
			dbDataType:'',//字段类型  datatype type
			indexed:null,//是否索引
			showed:null,//是否界面表格显示
			sorted:null,//是否排序
			sysType:null,//系统定义类型，包括三种（0：系统默认，1：管理员常量定义，2：表单添加后自定义）
			length:null,//长度
			showType:'1',//显示类型 1,2,3,4 datashow
			defaultValue:null,//默认值
			valueCreateMethod:'1',//默认值创建方式 1用户输入
			onlyreadUrl:null,//只读状态下设置跳转的url
	};
	
	//控件公共属性
	var commonProperty={
			inputMode:null,//输入样式 控件类型 inputDataType
			fieldCheckRules:null,
			fontSize:null,//字段的大小
			fontColor:null,//字段的颜色
			ctlWidth:null,//宽度
			ctlHight:null,//高度
			textAlign:null,//对齐方式
			
	};
	
	/*
	 * DATEPICKER CLASS DEFINITION ======================
	 */
	var DatePicker = function($placeHolder, options) {
		this.options = $.extend({}, $.fn["wdatePicker"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};

	DatePicker.prototype = {
		constructor : DatePicker 
	};
	
	
	$.DatePicker = {

			createEditableElem:function(){
				 
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("input");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("type", "text");
				 
				 $( editableElem).css(this.getTextInputCss());
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass);
				 /* var _this = this;
				 this.$editableElem.change(function(){
					 alert(1);
					 _this.value = _this.$editableElem.val(); 
				 });*/
				 
				 
				 var format='';
					var fmt=options.contentFormat;
					if(fmt==dyDateFmt.yearMonthDate){
						format='yyyy-MM-dd';
					}else if(fmt==dyDateFmt.dateTimeHour){
						format='yyyy-MM-dd HH';
					}else if(fmt==dyDateFmt.dateTimeMin){
						format='yyyy-MM-dd HH:mm';
					}else if(fmt==dyDateFmt.dateTimeSec){
						format='yyyy-MM-dd HH:mm:ss';
					}else if(fmt==dyDateFmt.timeHour){
						format='HH';
					}else if(fmt==dyDateFmt.timeMin){
						format='HH:mm';
					}else if(fmt==dyDateFmt.timeSec){
						format='HH:mm:ss';
					}else if(fmt==dyDateFmt.yearMonthDateCn){
						format='yyyy年MM月dd日';
					}else if(fmt==dyDateFmt.yearCn){
						format='yyyy年';
					}else if(fmt==dyDateFmt.yearMonthCn){
						format='yyyy年MM月';
					}else if(fmt==dyDateFmt.monthDateCn){
						format='MM月dd日';
					}else if(fmt==dyDateFmt.year){
						format='yyyy';
					}
					if(this.options.showIcon){
						this.$editableElem.attr("class","Wdate");
					}
					 var _this_=this.$editableElem;
					 var _this = this;
					 var _option=this.options;
					  
					 _this_.bind('click', function() {
					 	if(!_this.options.readOnly){
							WdatePicker({
								dateFmt:format,
								alwaysUseStartDate:options.alwaysUseStartDate,
								onpicked:function(){
									_this_.focus() ;  _this.setValue( _this_.val(), false);;
								},
								startDate:options.startDate
							});
					 	}
					});
					 
					 
					 _this_.bind('paste',function(){
						 window.setTimeout(function(){
							 _this.setValue( _this_.val(), false);
						 },100);
						
					 });
					 _this_.keyup(function(){ 
						 _this.setValue( _this_.val(), false);
					 });
					 
					 /*_this_.bind('change',function(){ 
						 _this.setValue( _this_.val());
					 });*/
			 }
		  
	};
	/*
	 * DATEPICKER PLUGIN DEFINITION =========================
	 */
	$.fn.wdatePicker = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wdatePicker');
                if(data){
                    return data; //返回实例对象
                }else{
                    throw new Error('This object is not available');
                }
            }
		}
		
		return this
				.each(function() {
					var $this = $(this),
					data = $this.data('wdatePicker'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						data = new DatePicker($(this),options); 
						 $.extend(data,$.wControlInterface); 
						$.extend(data,$.wTextCommonMethod);
						$.extend(data,$.DatePicker);
						data.init();
						 $this.data('wdatePicker',data );
					}
					if (typeof option == 'string') {
						if (method == true && args != null) {
							return data[option](args);
						} else {
							return data[option]();
						}
						
						
					}
					
				});
	};

	$.fn.wdatePicker.Constructor = DatePicker;

	$.fn.wdatePicker.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//控件私有属性
			disabled:false,
	        readOnly:false,
	        isShowAsLabel:false,
	        isHide:false,//是否隐藏
			alwaysUseStartDate:false,
			startDate:'%y-%M-%d %H:%m:%s',
			showIcon:true,
			contentFormat:''
			
	};
	
})(jQuery);