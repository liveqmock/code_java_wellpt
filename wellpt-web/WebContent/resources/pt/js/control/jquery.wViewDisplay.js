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
	 * VIEWDISPLAY CLASS DEFINITION ======================
	 */
	var ViewDisplay = function(element, options) {
		this.init("wviewDisplay", element, options);
	};

	ViewDisplay.prototype = {
		constructor : ViewDisplay,
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
			 
				var path = "/basicdata/dyview/view_show?viewUuid="+this.options.relationDataValue+"&currentPage=1&openBy=dytable";
				var parmArray = new Array();
				this.options.relationdatasql=" & o.status = '1' ";	
				var relationDataSql2 = this.options.relationdatasql;
				if(relationDataSql2!=undefined){
				while(relationDataSql2.indexOf("${")>-1){
					var s1=relationDataSql2.match("\\${.*?\\}")+"";
					parmArray.push(s1.replace("${", "").replace("}", ""));
					relationDataSql2 = relationDataSql2.replace(s1,"");
				}}
				path += this.options.relationdatasql;
			/*	for(var jt=0;jt<parmArray.length;jt++){
					if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
						path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
					}
				}*/
				if(options.columnProperty.showType==dyshowType.hide){
					 $.ControlUtil.setVisible(this.$element,false);
				 }else{
					var _element=this.$element;
					if(path.indexOf("${")>-1){
						_element.after("<span>没有相应记录</span>");
						_element.hide();
					}else{
						$.ajax({
							async:false,
							url : ctx + path,
							success : function(data) {
								_element.after(data);
								_element.hide();
							}
						});
					}
				 }
			 
		},
		
		 setValue:function(value){
	    	
		 },
		
		 //设置hide属性
		 setVisible:function(isvisible){
			 $.ControlUtil.setVisible(this.$element,isvisible);
			 this.options.isHide=!isvisible;
		 } ,
		 
		 
		 isVisible:function(){
			 return  this.options.isHide;
		 }, 
		
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
		
		 isValueMap:function(){
			 return false;
		 },
				

		 getValue:function(){
			 return '';
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
	 * VIEWDISPLAY PLUGIN DEFINITION =========================
	 */
	$.fn.wviewDisplay = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wviewDisplay');
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
					data = $this.data('wviewDisplay'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wviewDisplay', (data = new ViewDisplay(this,
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

	$.fn.wviewDisplay.Constructor = ViewDisplay;

	$.fn.wviewDisplay.defaults = {
			isHide:false,//是否隐藏
			
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			relationDataText : "", 		 	
			relationDataValue : "",		 	
			relationDataSql : ""		 	
	};
	
})(jQuery);