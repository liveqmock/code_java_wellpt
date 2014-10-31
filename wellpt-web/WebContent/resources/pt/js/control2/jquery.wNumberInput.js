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
	var NumberInput = function($placeHolder, options) { 
		this.options = $.extend({}, $.fn["wnumberInput"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	
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

	 
	
	$.NumberInput = {
			
			createEditableElem:function(){
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("input");
				 if(options.columnProperty.dbDataType=='13'&&options.columnProperty.length>9){
					 options.columnProperty.length='9';
				 }else if(options.columnProperty.dbDataType=='14'&&options.columnProperty.length>16){
					 options.columnProperty.length='16';
				 }else if(options.columnProperty.dbDataType=='15'&&options.columnProperty.length>12){
					 options.columnProperty.length='12';
				 }else if(options.columnProperty.dbDataType=='12'&&options.columnProperty.length>18){
					 options.columnProperty.length='18';
				 }
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("type", "text");
				 editableElem.setAttribute("maxlength", options.columnProperty.length);
				 
				 $( editableElem).css(this.getTextInputCss());
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass);
				 this.htmlelement =  this.$editableElem[0];
				 var _this = this;
				 
				 var evtFun = function(){
					 _this.setValue(_this.$editableElem.val(), false);
				 }
				 this.$editableElem.keyup(evtFun); 
				 this.$editableElem.bind("paste", function(){
					 window.setTimeout(function(){
						 _this.setValue(_this.$editableElem.val(), false);
					 },100);
				 });
				 this.$editableElem.bind("change", evtFun);
				/* this.$editableElem.bind('change',function(){
					 _this.setValue(_this.$editableElem.val(), false);
					 });*/
				 
//				 this.$editableElem.keydown($.proxy(this._preCheckInput, this));
//				 this.$editableElem.keyup($.proxy(this._postCheckInput, this));
				 
				 var operator = this.options.operator;
				 var inputMode = this.options.commonProperty.inputMode;
					if(operator){
						var _this = this;
						var dbDataType = this.options.dbDataType;
						if(operator.plus){ 
							this.$editableElem.before("<span class='plus' style='cursor:pointer' inputMode='" + inputMode+ "'>+&nbsp;</span>");
							this.$editableElem.prev().click(function(){
								var val = _this.value;
								if($.trim(val).length == 0){
									val = 0;
									//return;
								}
								if(dbDataType != dyFormDataType["float"]){ 
									_this.setValue(parseInt(val) + operator.plusUnit);
								}else{
									_this.setValue(parseFloat(val) + operator.plusUnit);
									 
								}
								
							});
						}
						if(operator.minus){
							this.$editableElem.after("<span class='minus' style='cursor:pointer' inputMode='" + inputMode + "'>&nbsp;-</span>");
							this.$editableElem.next().click(function(){
								var val = _this.value;
								if($.trim(val).length == 0){
									//return;
									val = 0;
								}
								if(dbDataType != dyFormDataType["float"]){
									_this.setValue(parseInt(val) - operator.minusUnit);
								}else{
									_this.setValue(parseFloat(val) - operator.minusUnit);
								} 
							});
						}
					}
			 },
		 
			 hideEditableElem:function(){
				 if(this.$editableElem == null){
					 return;
				 }
				 this.$editableElem.hide();
				 this.hideOperator();
				 this.$editableElem.siblings(".Validform_checktip").hide();
			 },
			 

			 //显示为可编辑框
			 setDisplayAsCtl:function(){
				 var options = this.options;
				 options.isShowAsLabel=false;
				 if(this.$editableElem == null){
					 this.createEditableElem();
				 }
				 
				this.$editableElem.show();
				this.showOperator();
				this.hideLabelElem();
				this.setValue2EditableElem();
			 } ,
			 
			 hideOperator:function(){
					//alert(1);
					 var operator = this.options.operator;
					 if(operator){
							if(operator.plus){
								this.$editableElem.prev(".plus").hide();
								 
							}
							if(operator.minus){
								//console.log(this.$editableElem.siblings(".minus").size());
								this.$editableElem.siblings(".minus").hide();
							}
						}
				},
				showOperator:function(){
					 var operator = this.options.operator;
					 if(operator){ 
							if(operator.plus){ 
								this.$editableElem.prev(".plus").show();
							}
							if(operator.minus){
								//this.$editableElem.next(".minus").show();
								this.$editableElem.siblings(".minus").show();
							}
						}
				},
				_preCheckInput : function(event) {
					this.oldValue = this.$editableElem.val();
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
					if (this.oldValue.indexOf(".")>0&&(event.keyCode == 190||event.keyCode == 110) && this.options.columnProperty.dbDataType == "15") {
						return false;
					}
					//火狐的"-"号是173的code?
					if (!(event.keyCode >= 48 && event.keyCode <= 57) && !(event.keyCode >= 95 && event.keyCode <= 105)&&!((event.keyCode == 190||event.keyCode == 173||event.keyCode == 110)&& this.options.negative == true)) {
						return false;
					}
					//如果是小数，则根据decimal设置输入小数位
					if (this.options.columnProperty.dbDataType == "15") {
						var value=this.oldValue;
						var decimal=this.options.decimal;
							 if ((event.keyCode >= 48 && event.keyCode <= 57)||(event.keyCode >= 95 && event.keyCode <= 105)||(event.keyCode == 190||event.keyCode == 110)) {
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
						var newValue = Number(this.$editableElem.val());
					    var selText = getSelectedText(this.htmlelement);
						if (selText.length == 0&&newValue > MAX_INT_VALUE || newValue < MIN_INT_VALUE) {
							this.$editableElem.val(this.oldValue);
							return false;
						}
					}else if(this.options.columnProperty.dbDataType == "14"){
						var newValue = Number(this.$editableElem.val());
						var selText = getSelectedText(this.htmlelement);
						if (selText.length == 0&&newValue > MAX_LONG_VALUE || newValue < MIN_LONG_VALUE) {
							this.$editableElem.val(this.oldValue);
							return false;
						}
						}else{
						return true;
					}
				},
				_postCheckInput : function(event) {
					var value = this.$editableElem.val();
					if (this.options.negative == true && value == "-") {
						return true;
					}
					
					if(this.$editableElem.val() == ""){
						return true;
					}

					var newValue = Number(this.$editableElem.val());
					if (!isNumeric(newValue)) {
						this.$editableElem.val(this.oldValue);
						return false;
					}
				},
				 
				 setReadOnly:function(isReadOnly){
					 this.render();  
					 if(isReadOnly){
						 this. options.columnProperty.showType = dyshowType.readonly;
						 this.get$InputElem().attr("readonly","readonly");  
						 this.hideOperator();
					 }else{
						 this. options.columnProperty.showType = dyshowType.edit;
						 this.get$InputElem().removeAttr("readonly","");
						 this.showOperator(); 
					 }
					 
				 } ,
				 
			
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
						 data = new NumberInput($(this),options); 
						 $.extend(data,$.wControlInterface); 
					     $.extend(data,$.wTextCommonMethod);
					     $.extend(data,$.NumberInput);
					     data.init();
						 $this.data('wnumberInput',data );
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
        formulas:{}
	};
})(jQuery);