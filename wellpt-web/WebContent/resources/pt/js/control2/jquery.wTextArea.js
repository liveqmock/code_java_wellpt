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
			inputMode:dyFormInputMode.textArea,//输入样式 控件类型 inputDataType
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
	var TextArea = function($placeHolder, options) { 
		this.options = $.extend({}, $.fn["wtextArea"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};

	TextArea.prototype = {
		constructor : TextArea
		 
	};
	
	$.TextArea = {

			createEditableElem:function(){ 
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("textarea");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("type", "text");
				 editableElem.setAttribute("maxlength", options.columnProperty.length);
				// <textarea type="text" id="'+formfieldcode+'" name="'+formfieldcode+'"></textarea>
				 var cssObj = this.getTextInputCss();
				 var width = cssObj.width; 
				 if(width.replace("p", "").replace("x", "") == ""){//如果没有设置宽度，则默认宽度为100%
					 cssObj.width = "100%"; 
				 }
				 $( editableElem).css(cssObj);
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass);
				 var _this = this;
				 this.$editableElem.change(function(){
					 //_this.value =; 
					 _this.setValue( _this.$editableElem.val(), false);
				 });
			 },
			 /*设置到可编辑元素中*/
			 setValue2EditableElem:function(){
				 if(this.$editableElem == null){
					 return;
				 } 
				 console.log(this.value);
				 this.$editableElem.val(this.value); 
			 },

			
	};
	
	/*
	 * TEXTINPUT PLUGIN DEFINITION =========================
	 */
	$.fn.wtextArea = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wtextArea');
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
					data = $this.data('wtextArea'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new TextArea($(this),options); 
					     $.extend(data,$.wControlInterface); 
						 $.extend(data,$.wTextCommonMethod);
						 $.extend(data, $.TextArea);
						 data.init();
						 $this.data('wtextArea',data );
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
	

	$.fn.wtextArea.Constructor = TextArea;

	$.fn.wtextArea.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			//控件私有属性
			disabled:false,
	        readOnly:false,
			isHide:false,//是否隐藏
			isShowAsLabel:false
	};
})(jQuery);