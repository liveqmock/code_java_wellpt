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
	var DatePicker = function(element, options) {
		this.init("wdatePicker", element, options);
	};

	DatePicker.prototype = {
		constructor : DatePicker,
		init : function(type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = this.getOptions(options);
			//参数初始化
			this.initparams(this.options);
		},
		//默认参数初始化
		initparams:function(options){
			 //设置字段属性.根据不同的控件类型区分get。
			 $.ControlUtil.setCtrAttr(this.$element,this.options);
			 //根据show类型展示
			 $.ControlUtil.dispalyByShowType(this.$element,this.options);
			 
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
				this.$element.attr("class","Wdate");
			}
			 var _this=this.$element;
			 var _option=this.options;
			$("input[name='"+this.$element.attr("name")+"']").bind('click', function() {
				WdatePicker({
					dateFmt:format,
					alwaysUseStartDate:options.alwaysUseStartDate,
					onpicked:function(){ $.ControlUtil.setDisplayAsCtl(_this,_option);},
					startDate:options.startDate,
					
				});
			});

		
			//设置默认值
			this.setValue(options.columnProperty.defaultValue);
			
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
				
		//set方法............................................................//
	     
		//设值
		 setValue:function(value){
			 $.ControlUtil.setValue(this.$element,this.options,value);
		 } ,
		 
		 //设置必输
		 setRequired:function(isrequire){
			$.ControlUtil.setRequired(isrequire,this.options);
		 } ,
		 
		 //设置可编辑
		 setEditable:function(){
			 this.setReadOnly(false);
			 this.setEnable(true);
			 this.setDisplayAsCtl();
		 } ,
		 
		 //只读，文本框不置灰，不可编辑
		 setReadOnly:function(isreadonly){
			 $.ControlUtil.setReadOnly(this.$element,isreadonly);
			 this.options.readOnly=isreadonly;
		 } ,
		 
		 //设置disabled属性
		 setEnable:function(isenable){
			 $.ControlUtil.setEnable(this.$element,isenable);
			 this.options.disabled=!isenable;
		 } ,
		 
		 //设置hide属性
		 setVisible:function(isvisible){
			 $.ControlUtil.setVisible(this.$element,isvisible);
			 this.options.isHide=!isvisible;
		 } ,
		 
		 //显示为lablel
		 setDisplayAsLabel:function(){
			 $.ControlUtil.setIsDisplayAsLabel(this.$element,this.options,true);
		 } ,
		 
		 //显示为控件
		 setDisplayAsCtl:function(){
			 $.ControlUtil.setDisplayAsCtl(this.$element,this.options);
		 },
	       
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 return this.$element.val();
		 },

		 isValueMap:function(){
			 return false;
		 },
		 
		 /**
		  * 返回是否可编辑(由readOnly和disabled判断)
		  * @returns {Boolean}
		  */
		 isEditable:function(){
			 if(this.options.readOnly&&this.options.disabled){
				 return false;
			 }else{
				 return true;
			 }
		 },
		 
		 isReadOnly:function(){
			 return this.options.readOnly;
		 },
		 
		 isEnable:function(){
			 return !this.options.disabled;
		 },
		 
		 isVisible:function(){
			 return  this.options.isHide;
		 }, 
		 
		 isRequired:function(){
			 return $.ControlUtil.isRequired(this.options);
		 },
		 
		 isShowAsLabel:function(){
			 return this.options.isShowAsLabel;
		 },
		 
		 getAllOptions:function(){
		    	 return this.options;
		     } ,  
		     
		 getRule:function(){
			 return $.ControlUtil.getCheckRules(this.options);
		 } ,
		 
		 getMessage:function(){
			 return $.ControlUtil.getCheckMsg(this.options);
		 } ,
		 
	     /**
	      * 获得控件名
	      * @returns
	      */
	     getCtlName:function(){
	    	 return this.$element.attr("name");
	     },
		 
	     //bind函数，桥接
	     bind:function(eventname,event){
	    	this.$element.bind(eventname,event);
	    	return this;
	     },
		 
		 //unbind函数，桥接
	     unbind:function(eventname){
	    	this.$element.unbind(eventname);
	    	return this;
	     }
	    //一些其他method ---------------------
	     
	     
	     
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
						$this.data('wdatePicker', (data = new DatePicker(this,
								options)));
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