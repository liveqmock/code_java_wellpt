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
	var ComboBox = function($placeHolder/*占位符*/, options) { 
		this.options = $.extend({}, $.fn["wcomboBox"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder; 
	};

	ComboBox.prototype = {
		constructor : ComboBox
	};
	
	$.ComboBox = {
			
			
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
				 
					var opt = new Array();
					//根据数据初始化元素下拉框
					if(options.optionDataSource==dyDataSourceType.dataConstant){
						var selectobj = this.options.optionSet;
						if(typeof selectobj =="object"){
							selectobj = eval("(" + JSON.cStringify(selectobj) + ")");
						}
						for(attrbute in selectobj){
							var s = '<option value=' + attrbute + '>'+selectobj[attrbute] + '</option>';
							opt.push(s); 
						}
					}else{
						var selectobj = this.options.optionSet; 
						if(typeof selectobj == "undefined"){
							return;
						}
						if(typeof selectobj == "string"){
							if(   selectobj.length == 0){
								return;
							}else{
								selectobj = eval("(" + selectobj + ")");
							} 
						}
						
						var datas= selectobj; 
							for(var i in datas){
								var data=datas[i];
								var s =  '<option value=' + data.code + '>' +data.name + '</option>';
								opt.push(s);
							}
				  }
				 var _this = this;
				 this.$editableElem.html('<option value="" ></option>'+opt.join(''));
				 this.$editableElem.change(function(){
					 
					var $selectedOpt =  $(this).find("option:selected");
					var realValue = $selectedOpt.val();
					if($.trim(realValue) == ""){
						_this.value = "";
						return;
					}else{
						
						//_this.value = {}
						//var displayValue = $selectedOpt.text();
						//_this.value[realValue] = displayValue;
						 
						_this.setValue(realValue);
					}
					
				 });
			 },
		 
			 
			 
			 getValueMapInOptionSet:function(value){ 
				 if(typeof value == "undefined" || value.length == 0){
					 return "";
				 }
				 
				 var options = this.options;
					 
					var valueMap = {};
					 
					var optionSet = {};
				  var selectobj = this.options.optionSet; 
					if(typeof selectobj == "undefined" || selectobj == null || (typeof selectobj == "string" && $.trim(selectobj).length == 0)){
						console.error( "a json parameter is null , used to initialize select options ");
						return;
					}
					if(options.optionDataSource!=dyDataSourceType.dataConstant){//来自字典,这时optionSet为数组
						if(selectobj.length == 0){
							return;
						}else{
							for(var j = 0; j < selectobj.length; j ++ ){
								//console.log(selectobj[i].code);
								optionSet[selectobj[j].code] = selectobj[j].name;
							}
						}
					}else{
						optionSet = selectobj;
					}
					
					if(typeof optionSet =="object"){ 
						 
					}else if(typeof optionSet == "string"){
						try{
							optionSet = eval("(" +  (optionSet) + ")");
						}catch(e){
							console.error(optionSet + " -->not json format ");
							return;
						}
					}
				  
					for(attrbute in optionSet){ 
						if(attrbute == value){
							valueMap[attrbute] = optionSet[attrbute];
						}
					} 
									 
						 
					 
					return JSON.cStringify(valueMap);
				 
			 },
			 
			 
			 /**
				 * 设置默认值
				 * @param defaultValue
				 */
				setDefaultValue:function(defaultValue){
					//加空判断
					if(defaultValue==null || defaultValue.length == 0){
						return;
					}
					  
					if(this.isValueAsJsonCtl()){  
						if(defaultValue.indexOf("{") == -1){
							defaultValue = this.getValueMapInOptionSet(defaultValue);
						}
						
						this.setValueByMap(defaultValue);
						 
					}else{ 
						 this.setValue(defaultValue);
					}
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
				}else{
					valueObj = eval("(" +  this.value + ")");
				}
				 for(var i in valueObj){ 
					 this.$editableElem.val(i);
				 } 
			 },
			 
			 
			 

			 
			//set............................................................//
		     
			//设值,值为真实值
			 setValue:function(value){
				 if(typeof value == "undefined" || value == null){
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
						 data = new ComboBox($(this),options);
						 $.extend(data,$.wControlInterface);
						 $.extend(data,$.ComboBox);
						 data.init();
						 $this.data('wcomboBox',data );
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
			isShowAsLabel:false,
			isHide:false,//是否隐藏
			disabled:false,
			optionDataSource:"1", //备选项来源1:常量,2:字段
			optionSet:[],
			dictCode:null

	};
})(jQuery);