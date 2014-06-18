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
		this.init("wunit", element, options);
	};

	Unit.prototype = {
		constructor : Unit,
		init : function(type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = this.getOptions(options);
			//参数初始化
			this.initparams(this.options);
		},
		//默认参数初始化
		initparams:function(options){
			
			 //设置字段属性.根据不同的控件类型区分。
			 $.ControlUtil.setCtrAttr(this.$element,this.options);
			//设置默认值
			this.$element.val(options.columnProperty.defaultvalue);
			 
			 //只显示为label.
			if(options.columnProperty.showType==dyshowType.showAsLabel){
				 $.ControlUtil.setIsDisplayAsLabel(this.$element,options,true);
			}else if(options.columnProperty.showType==dyshowType.disabled){
				 $.ControlUtil.setEnable(this.$element,false);
			 }else if(options.columnProperty.showType==dyshowType.hide){
				 $.ControlUtil.setVisible(this.$element,false);
			 }else if(options.columnProperty.showType==dyshowType.readonly){
				 $.ControlUtil.setReadOnly(this.$element,true);
			 }else {
			 var labelField = this.$element.attr("id");
			 var inputMode=this.options.commonProperty.inputMode;
			 if(dyFormOrgSelectType.orgSelectAddress == inputMode) {//单位通讯录
				 this.$element.mousedown(function(){
						$.unit.open({
							labelField:labelField, 
							type : 'Unit',
							commonType : 2,
							close:function(){
								$("#"+labelField).focus();
								$("#"+labelField).blur();
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
						}
					});
				 });
			 }
			 }
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
				
		//set............................................................//
	     
		//设值
		 setValue:function(value){
	    	  this.$element.attr("hiddenvalue",value);
		 } ,
		 
			
		 //设置显示值。
		 setDisplayValue:function(value){
	    	  this.$element.val(value);
	    	  this.$element.attr("value",value);
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
		 
		 setValueByMap:function(valuemap){
			 var valueobj=eval("("+valuemap+")");
			 var valuearray=[];
			 var displayvaluearray=[];
			 for(attribute in valueobj){
				 valuearray.push(attribute);
				 displayvaluearray.push(valueobj[attribute]);
				}
			 this.setValue((valuearray.toString()).replace(/\,/g, ";"));
			 this.setDisplayValue((displayvaluearray.toString()).replace(/\,/g, ";"));
		     
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
						$this.data('wunit', (data = new Unit(this,
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

	$.fn.wunit.Constructor = Unit;

	$.fn.wunit.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	};
	
})(jQuery);