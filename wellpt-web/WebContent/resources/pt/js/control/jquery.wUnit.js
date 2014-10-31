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
	 * UNIT CLASS DEFINITION ======================
	 */
	var Unit = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wunit"].defaults, options,
				this.$element.data());
	};

	Unit.prototype = {
		constructor : Unit,
	
		//默认参数初始化
		initSelf:function(){
			 //设置字段属性.根据不同的控件类型区分。
			this.$element.attr("id",this.$element.attr("name"));
			 if(this.options.commonProperty.inputMode==dyFormOrgSelectType.orgSelectStaff){
				 this.$element.addClass("input-people");//css in wellnewoa.css
			 }else{
				 this.$element.addClass("input-search");//css in wellnewoa.css
			 }
			 //设置文本的css样式
			 this.setTextInputCss();
			//设置默认值
			 this.setDefaultValue(this.options.columnProperty.defaultValue); 
			
			 //只显示为label.
			 var showType=this.options.columnProperty.showType;
			if(showType==dyshowType.showAsLabel){
				 this.setDisplayAsLabel();
			}else if(showType==dyshowType.disabled){
				 this.setEnable(false);
			 }else if(showType==dyshowType.hide){
				 this.setVisible(false);
			 }else if(showType==dyshowType.readonly){
				 this.setReadOnly(true);
			 }else {
			 var labelField = this.$element.attr("id");
			 var inputMode=this.options.commonProperty.inputMode;
			 var _this = this;
			 if(dyFormOrgSelectType.orgSelectAddress == inputMode) {//单位通讯录
				
				 this.$element.mousedown(function(){
						$.unit.open({
							labelField:labelField, 
							type : 'Unit',
							commonType : 2,
							close:function(){
								$("#"+labelField).focus();
								$("#"+labelField).blur();
								
								_this.setToRealDisplayColumn( );
							}
						});
					});
			 }else if(dyFormOrgSelectType.orgSelectStaDep == inputMode){//组织选择框(人员+部门)
				 this.$element.mousedown(function(){
					$.unit.open({
						labelField:labelField, 
						close:function(){
							$("#"+labelField).focus();
							$("#"+labelField).blur();
							_this.setToRealDisplayColumn( );
						}
					});
					});
			 }else if(dyFormOrgSelectType.orgSelectDepartment == inputMode){//组织选择框(仅选择组织部门)
				 this.$element.mousedown(function(){
					$.unit.open({
						labelField:labelField, 
						type : "Dept",
						selectType : 2,
						close:function(){
							$("#"+labelField).focus();
							$("#"+labelField).blur();
							_this.setToRealDisplayColumn( );
						}
					});
				 });
			 }else if(dyFormOrgSelectType.orgSelectStaff == inputMode){//组织选择框(仅选择组织人员)
				 this.$element.mousedown(function(){
					$.unit.open({
						labelField:labelField, 
						selectType : 4,
						close:function(){
							$("#"+labelField).focus();
							$("#"+labelField).blur(); 
							_this.setToRealDisplayColumn( );
						}
					});
				 });
			 }
			 }
			  this.addMustMark();
		},
		
		
				
		//set............................................................//
	     
		//设值
		 setValue:function(value){
	    	  this.$element.attr("hiddenvalue",value);
	    	  this.setToRealDisplayColumn(); 
		 } ,
		 
			
		 //设置显示值。
		 setDisplayValue:function(value){
	    	  this.$element.val(value);
		 } ,
		 
		 setValueByMap:function(valuemap){
			 var valueobj=eval("("+valuemap+")");
			 var valuearray=[];
			 var displayvaluearray=[];
			 for(attribute in valueobj){
				 valuearray.push(attribute);
				 displayvaluearray.push(valueobj[attribute]);
				}
			 
			 this.setDisplayValue((displayvaluearray.toString()).replace(/\,/g, ";"));
			 this.setValue((valuearray.toString()).replace(/\,/g, ";"));
		     
			 if(this.options.isShowAsLabel==true){
				 this.$element.next().html(this.getDisplayValue());
	    	  }
		 }, 
		 
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 return this.$element.attr("hiddenvalue");
		 },

		 getDisplayValue:function(){
			 return this.$element.val();
		 },
		 
		 isValueMap:function(){
			 return true;
		 },
		 
		 getValueMap:function(){
			 var v={};
			 if(this.getValue()==""||this.getValue()==undefined){
				 return v;
			 }
			 var values=this.getValue().split(';');
			 var displayvalue=this.getDisplayValue().split(';');
			 if(values.length!=displayvalue.length){
				 throw new Error('隐藏值和显示值长度不一致!');
			 }
			 for ( var i = 0; i < values.length; i++) {
				 v[values[i]]=displayvalue[i];
			}
			 return v;
		 },
	     
	};
	
	/*
	 * UNIT PLUGIN DEFINITION =========================
	 */
	$.fn.wunit = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wunit');
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
					data = $this.data('wunit'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Unit(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 var data3=$.extend(data2,$.wTextCommonMethod);
						 data3.init();
						 $this.data('wunit',data3 );
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

	$.fn.wunit.Constructor = Unit;

	$.fn.wunit.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	        
	};
	
})(jQuery);