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
	 * TEXTINPUT CLASS DEFINITION ======================
	 */
	var TextInput = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wtextInput"].defaults, options,
				this.$element.data());
	};

	TextInput.prototype = {
		constructor : TextInput,
		initSelf:function(){
			this.$element.attr("id",this.$element.attr("name"));
			 //根据show类型展示
			 this.dispalyByShowType();
			 //设置文本的css样式
			 this.setTextInputCss();
			 //设置默认值
			 this.setDefaultValue(this.options.columnProperty.defaultValue);
			  this.addMustMark();
		},
	};
	
	/*
	 * TEXTINPUT PLUGIN DEFINITION =========================
	 */
	$.fn.wtextInput = function(option) {
	 
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		 
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				
				 
				var $this = $(this);
				 
				var data = $this.data('wtextInput');
				 
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
					data = $this.data('wtextInput'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new TextInput(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 var data3=$.extend(data2,$.wTextCommonMethod);
						 data3.init();
						 $this.data('wtextInput',data3 );
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

	$.fn.wtextInput.Constructor = TextInput;

	$.fn.wtextInput.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	        isShowAsLabel:false
	};
	
})(jQuery);