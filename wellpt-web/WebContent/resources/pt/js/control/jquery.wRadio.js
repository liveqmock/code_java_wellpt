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
			onlyreadUrl:null//只读状态下设置跳转的url
	};
	
	//控件公共属性
	var commonProperty={
			inputMode:null,//输入样式 控件类型 inputDataType
			fieldCheckRules:null,
			fontSize:null,//字段的大小
			fontColor:null,//字段的颜色
			ctlWidth:null,//宽度
			ctlHight:null,//高度
			textAlign:null//对齐方式
	}; 
	
	/*
	 * RADIO CLASS DEFINITION ======================
	 */
	var Radio = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wradio"].defaults, options,
				this.$element.data());
	};

	Radio.prototype = {
		constructor : Radio,
		
		initSelf:function(){
		
			//默认参数初始化
			var options=this.options;
			this.$element.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
		
			 var colEnName=this.$element.attr("name");
			//根据数据初始化元素下拉框
			 this.$element.hide();
				var opt = new Array();
				var labelstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight);
				var otherinputstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,'120','');
				if(options.optionDataSource==dyDataSourceType.dataConstant){
					var selectobj = this.options.optionSet;
					if(typeof selectobj =="object"){
						selectobj = eval("(" + JSON.cStringify(selectobj) + ")");
					}
					var i=0;
					for(attrbute in selectobj){
						
						var s="";
						if(selectobj[attrbute]=='其他'){
							 s= '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + attrbute + '>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>'+'<input  id=other_input'+colEnName+ '_' + i+' '+otherinputstyle+' type=text value='+selectobj[attrbute]+'></input>';
						}else{
							 s = '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + attrbute + '>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>';
						}
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
						var s="";
						if(data.name=='其他'){
							 s = '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + data.code + '>' +'<label  '+labelstyle+' for=' +colEnName + '_' + i+'>'+data.name+'</label>'+'<input  id=other_input'+colEnName+ '_' + i+' '+otherinputstyle+' type=text value='+data.name+'></input>';
						}else{
							 s = '<input type=radio name=' + "_radio"+colEnName+ ' id=' + colEnName + '_' + i + ' value=' + data.code + '>' +'<label  '+labelstyle+' for=' +colEnName + '_' + i+'>'+data.name+'</label>';
						}
						opt.push(s);
					}
				
			  }
			this.$element.after('<div id=_div_radiogroups_'+colEnName+' name=_div_radiogroups_'+colEnName+'></div>');	
			this.getGroupDivElement().html(opt.join(''));
			
			//其他的特殊处理
			var $otherinput=undefined;
			$("input[name='"+this.getCtlName()+"']").each(function(){
			       if($(this).next().text()=='其他'){
			    	   $(this).next().next().hide();
			    	   $otherinput=$(this).next().next();
			       }
			   });
			//其他的特殊处理
			$("input[name='"+this.getCtlName()+"']").each(function(){
			  	   //按钮事件绑定
		    	   $(this).change(function(){
		    		   if($(this).next().text()=='其他'){
		    			   $otherinput.show();
		    		   }else{
		    			   if($otherinput!=undefined){
		    				   $otherinput.hide();
		    			   }
		    		   }
		    	   });
			   });
			
			
			
			var _this = this;
			$("input[name='"+this.getCtlName()+"']").each(function(){
				$(this).click(function(){
					 
					 
					_this.setToRealDisplayColumn();
				});
			});
			
		
				 
			if(options.columnProperty.showType==dyshowType.showAsLabel){
				 this.setDisplayAsLabel();
			}else if(options.columnProperty.showType==dyshowType.disabled){
				 this.setEnable(false);
			 }else if(options.columnProperty.showType==dyshowType.hide){
				 this.setVisible(false);
			 }
			
			//设置默认值
			this.setDefaultValue(options.columnProperty.defaultValue);
			  this.addMustMark();
		},
		
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			 if(!value) return;
			 $("input[name='"+this.getCtlName()+"'][value='"+value+"']").attr("checked",true);
			 if(this.options.isShowAsLabel==true){
				 this.getGroupDivElement().next().html(this.getDisplayValue());
	    	  }
			 this.setToRealDisplayColumn();
		 } ,
		 
		 setValueByMap:function(valuemap){ 
			 var valueobj=eval("("+valuemap+")");
			 for(attribute in valueobj){  
					this.setValue(attribute);
					var $checkobj=$("input[name='"+this.getCtlName()+"']:checked");
					if($checkobj.next().text()=='其他'){
						$checkobj.next().next().val(valueobj[attribute]);
						 if(this.options.isShowAsLabel==false){
							 $checkobj.next().next().show();
				    	  }
					}
					// this.getGroupDivElement().next().html(valueobj[attribute]);
			}
		 },
		 
		 
		 //设置disabled属性
		 setEnable:function(isenable){
			 $.ControlUtil.setEnable($("input[name='"+this.getCtlName()+"']"),isenable);
			 this.options.disabled=!isenable;
		 } ,
		 setReadOnly:function(){
			 this.setEnable(false);
		 } ,
		 
		 
		 //设置hide属性
		 setVisible:function(isvisible){
			 var elment=this.getGroupDivElement();
			 $.ControlUtil.setVisible(elment,isvisible);
			 this.options.isHide=!isvisible;
		 } ,
		 
		 //显示为lablel
		 setDisplayAsLabel:function(){
			 if(this.options.isShowAsLabel==true){
				 return;
			 }
			 
			var options=this.options;
			var elment=this.getGroupDivElement();
			var name = elment.attr("name");
			$("#span" + name ).remove();
		     //只显示为label.
			 var val=this.getDisplayValue();
			 elment.hide();
		     $.ControlUtil.setSpanStyle(elment,options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight,val);
			 options.isShowAsLabel=true;
			
		 } ,
		 
		 //显示为控件
		 setDisplayAsCtl:function(){
			 if(this.options.isShowAsLabel==false){
				 return;
			 }
			 var elment=this.getGroupDivElement();
			 elment.show();
			 if( elment.next().is('span')){
				 elment.next().remove();
			 }
			 this.options.isShowAsLabel=false;
		 } ,
		 
		 
		 setDisplayValue:function(value){
				$("input[name='"+this.getCtlName()+"']").each(function(){
			       if($(this).next().text()==value){
					$(this).attr("checked",true);
				}
			   });
		 } ,
	       
	    //get..........................................................//
		
		 getCtlName:function(){
			 var name="_radio"+this.$element.attr("name");
			 return name;
		 },
		 
		 getCtlElement:function(){
		    	 return "input[name='" + this.getCtlName() + "']";
		 },
		 
		 getGroupDivElement:function(){
			 var colEnName=this.$element.attr("name");
			 return $('#_div_radiogroups_'+colEnName);
		 },
		 
		 //返回控件值
		 getValue:function(){
			 return $("input[name='"+this.getCtlName()+"']:checked").val();
		 },

		 isValueMap:function(){
			 return true;
		 },
		 getDisplayValue:function(){
			 if($("input[name='"+this.getCtlName()+"']:checked").next().text()=='其他'){
				 return $("input[name='"+this.getCtlName()+"']:checked").next().next().val();
			 }
			 return $("input[name='"+this.getCtlName()+"']:checked").next().text();
		 },
		 
		 getValueMap:function(){
			 var v={};
			 v[this.getValue()]=this.getDisplayValue();
			 return v;
		 },
		 
		   //bind函数，桥接
	     bind:function(eventname,event){
	    		$('#_div_radiogroups_'+this.$element.attr("name")).bind(eventname,event);
	    		return this;
	     },
		 
		 //unbind函数，桥接
	     unbind:function(eventname){
	    	 $('#_div_radiogroups_'+this.$element.attr("name")).bind(eventname);
	    		return this;
	     }
	     
	};
	
	 
	/*
	 * RADIO PLUGIN DEFINITION =========================
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
						 data = new Radio(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 data2.init();
						 $this.data('wradio',data2 );
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