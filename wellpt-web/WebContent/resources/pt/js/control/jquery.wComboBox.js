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
	 * COMBOBOX CLASS DEFINITION ======================
	 */
	var ComboBox = function(element, options) {
		this.init("wcomboBox", element, options);
	};

	ComboBox.prototype = {
		constructor : ComboBox,
		init : function(type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = this.getOptions(options);
			this.initparams(this.options);
		},
		//默认参数初始化
		initparams:function(options){
			
			 //设置字段属性.根据不同的控件类型区分。
			 $.ControlUtil.setCtrAttr(this.$element,this.options);
			 
			 var opt = new Array();
			//根据数据初始化元素下拉框
			if(options.optionDataSource==dyDataSourceType.dataConstant){
				var selectobj = this.options.optionSet;
				for(attrbute in selectobj){
					var i=0;
					var s = '<option value=' + attrbute + '>'+selectobj[attrbute] + '</option>';
					opt.push(s);
					i++;
				}
			}else{
				if(options.dictCode==""&&options.dictCode==undefined){
					return;
				}
				var dictcodearray=options.dictCode.split(":");
				JDS.call({
		       	 service:"dataDictionaryService.getDataDictionariesByType",
		       	 data:[dictcodearray[0]],
		       	 async: false,
					success:function(result){
						var datas = result.data;
						for(var i in datas){
							var data=datas[i];
							var s =  '<option value=' + data.code + '>' +data.name + '</option>';
							opt.push(s);
						}
					},
					error:function(jqXHR){
					}
				});
		  }	
			
		 this.$element.html('<option value="" ></option>'+opt.join(''));
		 this.setValue(options.columnProperty.defaultValue);
		 if(options.columnProperty.showType==dyshowType.showAsLabel){
			 this.setDisplayAsLabel();
		 }else if(options.columnProperty.showType==dyshowType.disabled){
			 this.setEnable(false);
		 }else if(options.columnProperty.showType==dyshowType.hide){
			 this.setVisible(false);
		 }
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
		
		 
		 getDisplayValue:function(){
			 return this.$element.find("option:selected").text();
		 },
		 
		 
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			 this.$element.attr('value',value);
		 } ,
		 
		 //设置必输
		 setRequired:function(isrequire){
			 $.ControlUtil.setRequired(isrequire,this.options);
		 } ,
		 
		 //设置可编辑
		 setEditable:function(iseditable){
			 this.setReadOnly(!iseditable);
			 this.setEnable(iseditable);
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
			 //只显示为label.
			var val=this.getDisplayValue();
			this.$element.hide();
			if(!val) {
				elment.after("<span>"+val+"</span>");
			}else {
				if(val == "" || val == null) {
					elment.after("<span></span>");
				}else {
					$.ControlUtil.setSpanStyle(this.$element,this.options.textAlign,this.options.fontSize,this.options.fontColor,this.options.fontWidth,this.options.fontHight,val);
				}
			}
		 } ,
		 
		 setValueByMap:function(valuemap){
			 var valueobj=eval("("+valuemap+")");
			 for(attribute in valueobj){  
					this.setValue(attribute);
					this.setDisplayValue(valueobj[attribute]);
				}
		 },
		 
		 //设置显示值。
		 setDisplayValue:function(value){
			 this.$element.find("option[value='"+value+"']").attr("selected",true);
		 } ,
	       
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 return this.$element.find("option:selected").val();
		 },

		 isValueMap:function(){
			 return true;
		 },
		 
		 getValueMap:function(){
			 var v={};
			 v[this.getValue()]=this.getDisplayValue();
			 return v;
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
	 * COMBOBOX PLUGIN DEFINITION =========================
	 */
	$.fn.wcomboBox = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wcomboBox');
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
					data = $this.data('wcomboBox'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wcomboBox', (data = new ComboBox(this,
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

	$.fn.wcomboBox.Constructor = ComboBox;

	$.fn.wcomboBox.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			isHide:false,//是否隐藏
			disabled:false,
			optionDataSource:"1", //备选项来源1:常量,2:字段
			optionSet:[],
			dictCode:null

	};
})(jQuery);