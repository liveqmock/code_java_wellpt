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
	 * NUMBERINPUT CLASS DEFINITION ======================
	 */
	var NumberInput = function(element, options) {
		this.init("wnumberInput", element, options);
	};

	var MAX_INT_VALUE = new Number(2147483647);
	var MIN_INT_VALUE = new Number(-2147483648);
	var MAX_LONG_VALUE = new Number(9223372036854775807);
	var MIN_LONG_VALUE = new Number(-9223372036854775808);
	var MAX_DOUBLE_VALUE = new Number(1.7976931348623157E308);
	var MIN_DOUBLE_VALUE = new Number(4.9E-324);
	var KEY_CODE_MAP = {
		48 : "0",
		49 : "1",
		50 : "2",
		51 : "3",
		52 : "4",
		53 : "5",
		54 : "6",
		55 : "7",
		56 : "8",
		57 : "9"
	};

	NumberInput.prototype = {
		constructor : NumberInput,
		init : function(type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.htmlelement=element;
			this.options = this.getOptions(options);
			this.$element.keydown($.proxy(this._preCheckInput, this));
			this.$element.keyup($.proxy(this._postCheckInput, this));
	
			 //设置字段属性.根据不同的控件类型区分。
			 $.ControlUtil.setCtrAttr(this.$element,this.options);
			//根据show类型展示
			 $.ControlUtil.dispalyByShowType(this.$element,this.options);
			 //设置默认值
			this.setValue(options.defaultValue);
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
		_preCheckInput : function(event) {
			this.oldValue = this.$element.val();
//			alert(JSON.stringify(event.keyCode));
			// Allow: backspace, delete, tab and escape
			if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9
					|| event.keyCode == 27 ||
					// Allow: Ctrl+A
					(event.keyCode == 65 && event.ctrlKey === true) ||
					// Allow: home, end, left, right
					(event.keyCode >= 35 && event.keyCode <= 39)) {
				// let it happen, don't do anything
				return true;
			}
			// "-" 189
			//if ((event.keyCode == 189||event.keyCode == 173) && this.options.negative == true) {
			//	return true;
			//}
			// "." 190
			if (event.keyCode == 190 && this.options.columnProperty.dbDataType == "15") {
				return true;
			}
			//火狐的"-"号是173的code?
			if (!(event.keyCode >= 48 && event.keyCode <= 57) && !(event.keyCode >= 95 && event.keyCode <= 105)&&!((event.keyCode == 189||event.keyCode == 173)&& this.options.negative == true)) {
				return false;
			}
			//如果是小数，则根据decimal设置输入小数位
			if (this.options.columnProperty.dbDataType == "15") {
				var value=this.oldValue;
				var decimal=this.options.decimal;
					 if (event.keyCode >= 48 && event.keyCode <= 57) {
				            var cursorpos = getCurPosition(this.htmlelement);
				            var selText = getSelectedText(this.htmlelement);
				            var dotPos = value.indexOf(".");
				            if (dotPos > 0 && cursorpos > dotPos) {
				                if (cursorpos > dotPos + decimal) return false;
				                if (selText.length > 0 || value.substr(dotPos + 1).length < decimal)
				                    return true;
				                else
				                    return false;
				            }
					return true;
				 }
			return true;
			}else if(this.options.columnProperty.dbDataType == "13"){
				var newValue = Number(this.$element.val());
			    var selText = getSelectedText(this.htmlelement);
				if (selText.length == 0&&newValue > MAX_INT_VALUE || newValue < MIN_INT_VALUE) {
					this.$element.val(this.oldValue);
					return false;
				}
			}else if(this.options.columnProperty.dbDataType == "14"){
				var newValue = Number(this.$element.val());
				var selText = getSelectedText(this.htmlelement);
				if (selText.length == 0&&newValue > MAX_LONG_VALUE || newValue < MIN_LONG_VALUE) {
					this.$element.val(this.oldValue);
					return false;
				}
				}else{
				return true;
			}
		},
		_postCheckInput : function(event) {
			var value = this.$element.val();
			if (this.options.negative == true && value == "-") {
				return true;
			}
			
			if(this.$element.val() == ""){
				return true;
			}

			var newValue = Number(this.$element.val());
			if (!isNumeric(newValue)) {
				this.$element.val(this.oldValue);
				return false;
			}
		},
		
		//set............................................................//
	     
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
		
		
	
	//获取当前光标在文本框的位置
	function getCurPosition(domObj) {
	    var position = 0;
	    if (domObj.selectionStart || domObj.selectionStart == '0') {
	        position = domObj.selectionStart;
	    }
	    return position;
	}
	//获取当前文本框选中的文本
	function getSelectedText(domObj) {
	    if (domObj.selectionStart || domObj.selectionStart == '0') {
	        return domObj.value.substring(domObj.selectionStart, domObj.selectionEnd);
	    }
	 return '';
	}
	
	function isNumeric(obj) {
		return !isNaN(parseFloat(obj)) && isFinite(obj);
	}
	/*
	 * NUMBERINPUT PLUGIN DEFINITION =========================
	 */
	$.fn.wnumberInput = function(option) {
		
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wnumberInput');
                if(data){
                    return data; //返回实例对象
                }else{
                    throw new Error('This object is not available');
                }
            }
		}
		
		return this
				.each(function() {
					var $this = $(this), data = $this.data('wnumberInput'), options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wnumberInput', (data = new NumberInput(this,
								options)));
					}
					if (typeof option == 'string') {
						data[option]();
					}
				});
	};

	$.fn.wnumberInput.Constructor = NumberInput;

	$.fn.wnumberInput.defaults = {
		columnProperty:columnProperty,//字段属性
		commonProperty:commonProperty,//公共属性
		
		//控件私有属性
		decimal : 2,//默认两位小数
		negative:true,
		disabled:false,
        readOnly:false,
        isShowAsLabel:false,
        isHide:false,//是否隐藏
	};
})(jQuery);