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
	 * Ckeditor CLASS DEFINITION ======================
	 */
	var Ckeditor = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wckeditor"].defaults, options,
				this.$element.data());
	};

	
	Ckeditor.prototype = {
		constructor : Ckeditor,
		//默认参数初始化
		initSelf:function(){
		    var name=this.$element.attr("id");
			//清除编辑器
			var instance = CKEDITOR.instances[name];
			if (instance) { 
				CKEDITOR.remove(instance);
			}
			options=this.options;
			var width = this.options.commonProperty.ctlWidth;
			if($.trim(width).length > 0){
				if(width.indexOf("px") == -1 && width.indexOf("%") == -1 ){
					width = width + "px";
				}
			}else{
				width = "100%";
			}
			
			var height = this.options.commonProperty.ctlHight;
			if($.trim(height).length > 0){
				if(height.indexOf("px") == -1 && height.indexOf("%") == -1 ){
					height = width + "px";
				}
			}else{
				height = "100%";
			}
			
			
			 //初始化编辑器
				var editor = CKEDITOR.replace( name, {  
					allowedContent:true,
					enterMode: CKEDITOR.ENTER_P,
					height: height,
					width: width,
					//工具栏
					toolbar: [ 
					          ['Bold','Italic','Underline'], ['Cut','Copy','Paste'], 
					          ['NumberedList','BulletedList','-'], 
					          ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
					          ['Link','Unlink'],['Format','Font','FontSize'],['TextColor','BGColor'],
					          ['Image','Table','Smiley'],['Source','Maximize'],['formfile']
					          ],
				
					 on: {
					        instanceReady: function( ev ) {
					            this.dataProcessor.writer.setRules( 'p', {
					                indent: options.indent,
					                breakBeforeOpen: options.breakBeforeOpen,
					                breakAfterOpen: options.breakAfterOpen,
					                breakBeforeClose: options.breakBeforeClose,
					                breakAfterClose: options.breakAfterClose
					            });
					        }
					    }
				 });
			//	alert(this.$element.css());
			//editor.applyStyle();
			//设置文本的css样式
			this.setTextInputCss();
			//设置默认值
            this.setDefaultValue(this.options.columnProperty.defaultValue);
            this.addMustMark();
		},
		
		getCkText : function() {
			var name=this.$element.attr("id"); 
			var oEditor=CKEDITOR.instances[name]; 
			return oEditor.getData();
		},
		getCkHtml : function() {
			var name=this.$element.attr("id");
			var oEditor=CKEDITOR.instances[name];
			return oEditor.document.getBody().getHtml();
		},
		
		//set方法............................................................//
	        
		//设值
		 setValue:function(value){
	    		var name=this.$element.attr("id");
				var oEditor=CKEDITOR.instances[name];
				oEditor.setData(value);
				  if(this.options.isShowAsLabel==true){
					  this.$element.next().html(value);
		    	  }
		 } ,
		 
		 //设置必输
		 setRequired:function(isrequire){
			 $.ControlUtil.setRequired(isrequire,this.options);
		 } ,
		 
		 //显示为lablel
		 setDisplayAsLabel:function(){
			 $.ControlUtil.setDisplayAsLabel(this.$element,this.options,false,this);
			   
			 this.hideDiv();
			  
		 } ,
		 
		 hideDiv:function(){
			 var _this = this;
			 if(_this.$element.siblings("div[role='application']").size() == 0 //ie外的浏览器
					 && 
					 _this.$element.siblings("span[role='application']").size() == 0//ie
					 ){//由于ckeditor是异步的，所在这里需要等待
				  
				 window.setTimeout(function(){
					 _this.hideDiv();
				 }, 10);
			 }else{
				 _this.$element.siblings("span[role='application']").hide();
				 _this.$element.siblings("div[role='application']").hide(); 
			 }
			 
		 },
		 
		 //显示为控件
		 setDisplayAsCtl:function(){
			 $.ControlUtil.setDisplayAsCtl(this.$element,this.options);
			 this.$element.hide();
			 this.$element.next().show();
		 },
	       
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 return this.getCkText();
		 },
		 

	    //一些其他method ---------------------
	     getwckeditorInstance:function(){
	    	 return CKEDITOR.instances[options.colenname];
	     }
	     
	     
	};
	
	/*
	 * wckeditor PLUGIN DEFINITION =========================
	 */
	$.fn.wckeditor = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wckeditor');
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
					data = $this.data('wckeditor'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new Ckeditor(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 var data3=$.extend(data2,$.wTextCommonMethod);
						 data3.init();
						 $this.data('wckeditor',data3 );
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

	$.fn.wckeditor.Constructor = Ckeditor;

	$.fn.wckeditor.defaults = {
			
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			isShowAsLabel:false,
			//控件私有属性
			disabled:false,
	        readOnly:false,
	        isHide:false,//是否隐藏
			indent: true,
            breakBeforeOpen: false,
            breakAfterOpen: false,
            breakBeforeClose: false,
            breakAfterClose: false,
            allowedContent:true,
	};
})(jQuery);