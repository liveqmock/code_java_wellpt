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
	 * Radio CLASS DEFINITION ======================
	 */
	var Radio = function(element, options) {
		this.init("wradio", element, options);
	};

	Radio.prototype = {
		constructor : Radio,
		init : function(type, element, options) {
			this.type = type;
			this.$element = $(element);
			this.options = this.getOptions(options);
			this.initparams(this.options);
		},
		//默认参数初始化
		initparams:function(options){
			 //设置字段属性.根据不同的控件类型区分。
			 $.ControlUtil.setCtrAttr(this.$element,this.options);		
			 var colEnName=this.$element.attr("name");
			//根据数据初始化元素下拉框
			 this.$element.hide();
				var opt = new Array();
				var labelstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight);
				if(options.optionDataSource==dyDataSourceType.dataConstant){
					var selectobj = this.options.optionSet;
					for(attrbute in selectobj){
						var i=0;
						var s = '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + attrbute + '>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>';
						opt.push(s);
						i++;
					}
				}else{
					if(options.dictCode==""&&options.dictCode==undefined){
						return;
					}
					var dictcodearray=options.dictCode.split(":");
					JDS.call({
			       	 service:"dataDictionaryService.getDataDictionariesByType",
			       	 data:[dictcodearray[0]],
			       	 async: false,
						success:function(result){
							var datas = result.data;
							for(var i in datas){
								var data=datas[i];
								var s = '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + data.code + '>' +'<label  '+labelstyle +'for=' +colEnName + '_' + i+labelstyle+'>'+data.name+'</label>';
								opt.push(s);
							}
						},
						error:function(jqXHR){
						}
					});
			  }
			this.$element.after(opt.join(''));
			//设置默认值
			this.setValue(options.columnProperty.defaultValue);
				 
			if(options.columnProperty.showType==dyshowType.showAsLabel){
				 this.setDisplayAsLabel();
			}else if(options.columnProperty.showType==dyshowType.disabled){
				 this.setEnable(false);
			 }else if(options.columnProperty.showType==dyshowType.hide){
				 this.setVisible(false);
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
			 $("input[name='"+this.getCtlName()+"'][value='"+value+"']").attr("checked",true);
			 if(this.options.isShowAsLabel==true){
				 this.setDisplayAsCtl();
				 this.setDisplayAsLabel();
			 }
		 } ,
		 
		 setValueByMap:function(valuemap){
			 $("input[name='"+this.getCtlName()+"']").prop('checked', false);
			 var valueobj=eval("("+valuemap+")");
			 for(attribute in valueobj){  
				 $("input[name='"+this.getCtlName()+"'][value='"+attribute+"']").prop("checked",true);
				}
			 if(this.options.isShowAsLabel==true){
				 this.setDisplayAsCtl();
				 this.setDisplayAsLabel();
			 }
		 },
		 
		 //设置必输
		 setRequired:function(isrequire){
			 $.ControlUtil.setRequired(isrequire,this.options);
		 } ,
		 
		 //设置可编辑
		 setEditable:function(){
			 this.setEnable(true);
			 this.setDisplayAsCtl();
		 } ,
		 
		 
		 //设置disabled属性
		 setEnable:function(isenable){
			 $.ControlUtil.setEnable($("input[name='"+this.getCtlName()+"']"),isenable);
			 this.options.disabled=!isenable;
		 } ,
		 
		 //设置hide属性
		 setVisible:function(isvisible){
			 $.ControlUtil.setVisible($("input[name='"+this.getCtlName()+"']"),isvisible);
			 if(!isvisible){
			  $("input[name='"+this.getCtlName()+"']").each(function(){
		           $(this).next().hide();
		       });
			  } else{
				  $("input[name='"+this.getCtlName()+"']").each(function(){
			           $(this).next().show();
			       });
			 }
			 this.options.isHide=!isvisible;
		 } ,
		 
		 //显示为lablel
		 setDisplayAsLabel:function(){
				$("input[name='"+this.getCtlName()+"']").hide();
				$("input[name='"+this.getCtlName()+"']:not(:checked)").next().hide();
			this.options.isShowAsLabel=true;
		 } ,
		 
		 //显示为控件
		 setDisplayAsCtl:function(){
				$("input[name='"+this.getCtlName()+"']").show();
				$("input[name='"+this.getCtlName()+"']").each(function(){
			           $(this).next().show();
			       });
			this.options.isShowAsLabel=false;
		 } ,
		 
		 
		 /*//设置显示值。
		 setDisplayValue:function(value){
				$("input[name='"+this.getCtlName()+"']").each(function(){
			       if($(this).next().text()==value){
					$(this).attr("checked",true);
				}
			   });
		 } ,*/
	       
	    //get..........................................................//
		
		 getCtlName:function(){
			 var name="_radio"+this.$element.attr("name");
			 return name;
		 },
		 
		 //返回控件值
		 getValue:function(){
			 return $("input[name='"+this.getCtlName()+"']:checked").val();
		 },

		 isValueMap:function(){
			 return true;
		 },
		 getDisplayValue:function(){
			 return $("input[name='"+this.getCtlName()+"']:checked").next().text();
		 },
		 
		 getValueMap:function(){
			 var v={};
			 v[this.getValue()]=this.getDisplayValue();
			 return v;
		 },
		 
		 /**
		  * 返回是否可编辑(由readOnly和disabled判断)
		  * @returns {Boolean}o
		  */
		 isEditable:function(){
			 if(this.options.disabled){
				 return false;
			 }else{
				 return true;
			 }
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
		 
	     //bind函数，桥接
	     bind:function(eventname,event){
	    	this.$element.bind(eventname,event);
	    	return this;
	     },
		 
		 //unbind函数，桥接
	     unbind:function(eventname){
	    	this.$element.unbind(eventname);
	    	return this;
	     },
	     getAllOptions:function(){
	    	 return this.options;
	     } ,   
		      
	     
	};
	
	function privatefunction(obj) {
		return null;
	}
	/*
	 * TEXTINPUT PLUGIN DEFINITION =========================
	 */
	$.fn.wradio = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wradio');
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
					data = $this.data('wradio'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wradio', (data = new Radio(this,
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

	$.fn.wradio.Constructor = Radio;

	$.fn.wradio.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//私有属性
			isHide:false,//是否隐藏
			checked:false,
			disabled:false,
			isShowAsLabel:false,
			optionDataSource:"1", //备选项来源1:常量,2:字段
			optionSet:[],
			dictCode:null
			
	};
})(jQuery);