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
	 * FILEUPLOAD CLASS DEFINITION ======================
	 */
	var FileUpload = function(element, options) {
		this.init("wfileUpload", element, options);
	};

	FileUpload.prototype = {
		constructor : FileUpload,
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
			 
			 var allowDownload=this.options.allowDownload;
			 var allowDelete=this.options.allowDelete;
			 var mutiselect=this.options.mutiselect;
			 
			 var setReadOnly=false;
			 setReadOnly=!this.options.allowUpload;;
			this.$element.hide();
			var id = this.$element.attr('id');//字段名称
			 
			var uploaddivhtml="<div id='_fileupload"+id+"'></div>";
			 
			this.$element.after(uploaddivhtml);
			var $attachContainer = $("#_fileupload"+id);
			 //创建上传控件
			var elementID = WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
			var fileupload = new WellFileUpload(elementID);
			//初始化上传控件
			fileupload.initWithLoadFilesFromFileSystem(setReadOnly,  $attachContainer,  this.options.enableSignature, mutiselect, this.options.dataUuid , id);
			
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
				
		//set............................................................//
	     
		getValue:function(){
			var files = WellFileUpload.files[this.getFielctlID()];
			 if(files){
				return files;
			 }else{
				return  [];
			 }
		},
		
		getFielctlID:function(){
			var id = this.$element.attr('id');//字段名称
			return  WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
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
		 
		 isValueMap:function(){
			 return false;
		 },
		 
		 setValue:function(value){
	    	
		 } ,
		 
	     /**
	      * 获得控件名
	      * @returns
	      */
	     getCtlName:function(){
	    	 var id = this.$element.attr('id');//字段名称
			 return  WellFileUpload.getCtlID4Dytable(this.options.mainTableName, id, 0);
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
	 * FILEUPLOAD PLUGIN DEFINITION =========================
	 */
	$.fn.wfileUpload = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wfileUpload');
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
					data = $this.data('wfileUpload'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wfileUpload', (data = new FileUpload(this,
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

	$.fn.wfileUpload.Constructor = FileUpload;
	
	$.fn.wfileUpload.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        isHide:false,//是否隐藏
	        mainTableName:'',
	        formSign:'',
	        dataUuid:'222',
	        enableSignature:"",
        	allowUpload:true,//允许上传
            allowDownload:true,//允许下载
            allowDelete:true,//允许删除
            mutiselect:true//是否多选
	};
	
})(jQuery);