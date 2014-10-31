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
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wcomboBox"].defaults, options,
				this.$element.data());
	};

	ComboBox.prototype = {
		constructor : ComboBox,
		initSelf:function(){
			var options=this.options;
			 this.$element.css("display", "none");
			
			this.$element.attr("id",this.$element.attr("name"));
			this.$element.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
			//设置文本的css样式
			this.setTextInputCss();
			var opt = new Array();
			//根据数据初始化元素下拉框
			if(options.optionDataSource==dyDataSourceType.dataConstant){
				var selectobj = this.options.optionSet;
				if(typeof selectobj =="object"){
					selectobj = eval("(" + JSON.cStringify(selectobj) + ")");
				}
				for(attrbute in selectobj){
					var i=0;
					var s = '<option value=' + attrbute + '>'+selectobj[attrbute] + '</option>';
					opt.push(s);
					i++;
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
		 
		 this.$element.html('<option value="" ></option>'+opt.join(''));
		 this.setValue(options.columnProperty.defaultValue); 
		 this.$element.css("display", "");
		 
		 if(options.columnProperty.showType==dyshowType.showAsLabel){
			 this.setDisplayAsLabel();
		 }else if(options.columnProperty.showType==dyshowType.disabled){ 
			 this.setEnable(false);
		 }else if(options.columnProperty.showType==dyshowType.hide){
			 this.setVisible(false);
		 }else{
			
		 }
		 
		 var _this = this;
		 this.$element.change(function(){
			 _this.setToRealDisplayColumn(); 
		 });
		 
		 
		 this.$element.css("padding-left", "10px");
		  this.addMustMark();
		 
		},
		 
		 getDisplayValue:function(){
			 return this.$element.find("option:selected").text();
		 },
		 
		 
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			 this.$element.attr('value',value);
			 if(this.options.isShowAsLabel==true){
				 this.$element.next().html(this.getDisplayValue());
			 }
			 this.setToRealDisplayColumn();
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
		 
		 
		 //显示为lablel
		 setDisplayAsLabel:function(){
			 //只显示为label.
			var val=this.getDisplayValue();
			this.$element.hide();
			$.ControlUtil.setSpanStyle(this.$element,this.options.commonProperty.textAlign,this.options.commonProperty.fontSize,this.options.commonProperty.fontColor,this.options.commonProperty.fontWidth,this.options.commonProperty.fontHight,val);
			this.options.isShowAsLabel=true;
		 } ,
		 
		 
		 setValueByMap:function(valuemap){
			 var valueobj=eval("("+valuemap+")");
			 for(attribute in valueobj){  
					this.setValue(attribute);
					//this.setDisplayValue(valueobj[attribute]);
				}
			 if(this.options.isShowAsLabel==true){
				 this.$element.next().html(this.getDisplayValue());
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
						 data = new ComboBox(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 data2.init();
						 $this.data('wcomboBox',data2 );
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