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
			onlyreadUrl:null//只读状态下设置跳转的url
	};
	
	//控件公共属性
	var commonProperty={
			inputMode:null,//输入样式 控件类型 inputDataType
			fieldCheckRules:null,
			fontSize:null,//字段的大小
			fontColor:null,//字段的颜色
			ctlWidth:null,//宽度
			ctlHight:null,//高度
			textAlign:null//对齐方式
	}; 
	
	/*
	 * RADIO CLASS DEFINITION ======================
	 */
	var Radio = function($placeHolder, options) { 
		this.options = $.extend({}, $.fn["wradio"].defaults, options );
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};

	Radio.prototype = {
		constructor : Radio
	};
		 
 
		
	 
	/*
	 * RADIO PLUGIN DEFINITION =========================
	 */
	$.fn.wradio = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wradio');
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
					data = $this.data('wradio'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Radio($(this),options); 
						/* var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);*/
						 $.extend(data, $.wControlInterface);
						 $.extend(data, $.wRadioCheckBoxCommonMethod);
					 
						 data.init();
						 $this.data('wradio',data );
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

	$.fn.wradio.Constructor = Radio;

	$.fn.wradio.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//私有属性
			isHide:false,//是否隐藏
			checked:false,
			disabled:false,
			isShowAsLabel:false,
			optionDataSource:"1", //备选项来源1:常量,2:字段
			optionSet:[],
			dictCode:null,
			value:""
			
	};
})(jQuery);