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
	var JobSelect = function($placeHolder, options) {
		this.options = $.extend({}, $.fn["wjobSelect"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};
	
	JobSelect.prototype = {
		constructor : JobSelect,
	};
	
	$.JobSelect = {
			createEditableElem:function(){
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("select");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 $( editableElem).css(this.getTextInputCss());
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass); 
				  
				 this.drawOptions();
				 var _this = this;
				 
				 this.$editableElem.change(function(){
					var $selectedOpt =  $(this).find("option:selected");
					var realValue = $selectedOpt.val();
					_this.setValue(realValue);
				 });
			 },
			 
			 /**
			  * 设置控件的optionset
			  * @param opitions 这是一个数组,成员的是一个{'code':'xxxx', name:'yyyyy'}的对象
			  * */
			 setOptionSet:function(optionSet){
				 this.options.optionSet = optionSet;
			 },
			 
			 /*为可编辑框重画option*/
			 drawOptions:function(){
				var optionSet = this.options.optionSet ;
				 if(this.$editableElem == null){//创建可编辑框
					 this.createEditableElem();
				 }
				 this.$editableElem.empty();//清空可编辑框 
				 
				 //填充可编辑框
				 for(var i = 0; i < optionSet.length; i++){
					 this.$editableElem.append("<option value='" + optionSet[i].code + "'>" +  optionSet[i].name + "</option>");
				 }
				// alert(this.$editableElem.find("option").first().val()); 
				 this.setValue( this.$editableElem.find("option").first().val());
				 
			 },
			  
			 
			 /**
			 * 设置默认值
			 * @param defaultValue
			 */
			setDefaultValue:function(defaultValue){
				//加空判断
				if(typeof defaultValue == "undefined" || defaultValue==null || defaultValue.length == 0){ 
					//return;
					defaultValue =  {}; 
					defaultValue[this.options.optionSet[0].code] = this.options.optionSet[0].name; 
				}
				
				this.setValueByMap(JSON.cStringify(defaultValue));
			},
				
			
			
			/*设值到标签中*/
			 setValue2LabelElem:function(){
				 if(this.$labelElem == null){
					 return;
				 }
				 this.$labelElem.html(this.getDisplayValue());
			 },
			 
			 /*设置到可编辑元素中*/
			 setValue2EditableElem:function(){
				 if(this.$editableElem == null){
					 return;
				 }
				 var valueObj = {};
				if(this.value == null || $.trim(this.value).length == 0){  
					 this.$editableElem.val("");
					 return;
				}else{
					valueObj = eval("(" +  this.value + ")");
				}
				 for(var i in valueObj){
					 this.$editableElem.val(i); 
				 }
				 //this.addOptions(valueObj);
			 },
			 
			//set............................................................//
		     
			/*该方法,在这个控件中已过时*/
			 setValue:function(value){ 
				 if(typeof value == "undefined" || value == null 
						 || $.trim(value).length == 0//由于默认为主职位，所以没有空的选项
						 ){
					 return;
				 }
				 var valueMap = this.getValueMapInOptionSet(value);
				 if(typeof valueMap == "string"){
					 this.setValueByMap( valueMap );
				 }else{
					 this.setValueByMap(JSON.cStringify(valueMap));
				 }
			 } ,
			 
			 isValueMap:function(){
				 return true;
			 } ,
			 
			 getValueMapInOptionSet:function(value){
				var optionSet = this.options.optionSet ;
				for(var i in optionSet){
					var option = optionSet[i];
					if(option.code == value){
						var res = {};
						res[value] = option.name;
						return res;
					}
				} 
			 },
			 setValueByMap:function(valuemap, isRender){
				 
				 var internalCall = false;
				 for(var i in $.JobSelect){ 
	    			 if($.JobSelect[i] == arguments.callee.caller){//内部调用  
	    				 internalCall = true;
	    				 break;
	    			 }
	    		 }
				 
				 
				 
    			 if(internalCall){
    				// this.setDisplayAsCtl();
	    		 }else{//外部调用显示为文本
	    			 this.setDisplayAsLabel();
	    		 }
    			 
    			 $.wControlInterface.setValueByMap.call(this, valuemap, isRender);
	    		 
			 },
			 
	
	};
	
	/*
	 * TEXTINPUT PLUGIN DEFINITION =========================
	 */
	$.fn.wjobSelect = function(option) {
	 
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		 
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				
				var $this = $(this);
				 
				var data = $this.data('wjobSelect');
				 
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
					data = $this.data('wjobSelect'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new JobSelect($(this), options);
						 $.extend(data,$.wControlInterface);
						//$.extend(data,$.wTextCommonMethod);
						//$.extend(data,$.TextInput); 
						$.extend(data,$.JobSelect); 
						data.init();
						 $this.data('wjobSelect',data );
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

	$.fn.wjobSelect.Constructor = JobSelect;

	$.fn.wjobSelect.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	        isShowAsLabel:false
	};
	
})(jQuery);