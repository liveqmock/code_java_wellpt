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
	 * CHECKBOX CLASS DEFINITION ======================
	 */
	var CheckBox = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wcheckBox"].defaults, options,
				this.$element.data());
	};

	CheckBox.prototype = {
		constructor : CheckBox,

		initSelf:function(){
			//默认参数初始化
			var options=this.options;
			this.$element.attr("optiondatasource",options.optiondatasource);	//radio,checkbox,select供选项来源 1.常量 2.字典
			this.$element.attr("ctrlfield",options.ctrlfield);	//checkbox 控制隐藏字段
			this.$element.attr("selectMode",options.selectMode);	//选择模式，单选1，多选2
			this.$element.attr("singleCheckContent",options.singleCheckContent);	//单选 选中内容
			this.$element.attr("singleUnCheckContent",options.singleUnCheckContent);	//单选 取消选中内容
			//根据数据初始化元素下拉框
			this.$element.hide();
			var opt = new Array();
			var labelstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight,'');
			var otherinputstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,'120','');
			var colEnName=this.$element.attr("name");
			
			//单选模式
			if(options.selectMode=='1'){
				var selectobj = eval("("+options.singleCheckContent+")");
				for(attrbute in selectobj){
					var i=0;
					var s="";
					 s = '<input type=checkbox name=' + "_checkbox"+colEnName+ ' id=' + colEnName + '_' + i + ' value="' + attrbute + '"/>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>';
					 opt.push(s);
					 i++;
				}
			}
			//多选模式
			else if(options.selectMode=='2'){
					if(options.optionDataSource==dyDataSourceType.dataConstant){
						var selectobj = this.options.optionSet; 
						if(typeof selectobj =="object"){
							selectobj = eval("(" + JSON.cStringify(selectobj) + ")");
						}
						for(attrbute in selectobj){
							var i=0;
							var s="";
							if(selectobj[attrbute]=='其他'){
								 s = '<input type=checkbox name=' + "_checkbox"+colEnName+ ' id=' + colEnName + '_' + i + ' value="' + attrbute + '"/>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>'+'<input  id=other_input'+colEnName+ '_' + i+' '+otherinputstyle+' type=text value="'+selectobj[attrbute]+'"/>';
							}else{
								 s = '<input type=checkbox name=' + "_checkbox"+colEnName+ ' id=' + colEnName + '_' + i + ' value="' + attrbute + '"/>' +'<label '+labelstyle +'for=' +colEnName + '_' + i+'>'+selectobj[attrbute]+'</label>';
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
										 s = '<input type=checkbox name=' + "_checkbox"+colEnName+ ' id=' + colEnName + '_' + i + ' value="' + data.code + '"/>' +'<label  '+labelstyle +'for=' +colEnName + '_' + i+'>'+data.name+'</label>'+'<input  id=other_input'+colEnName+ '_' + i+' '+otherinputstyle+' type=text value="'+data.name+'"/>';
									}else{
										 s = '<input type=checkbox name=' + "_checkbox"+colEnName+ ' id=' + colEnName + '_' + i + ' value="' + data.code + '"/>' +'<label  '+labelstyle +'for=' +colEnName + '_' + i+'>'+data.name+'</label>';
									}
									opt.push(s);
								}
				  }	
			}
		 
			this.$element.after('<div id=_div_checkgroups_'+colEnName+' name=_div_checkgroups_'+colEnName+'></div>');	
			$('#_div_checkgroups_'+colEnName).html(opt.join(''));
		 
		
			
			//其他的特殊处理
			var $otherinput={};
			$("input[name='"+this.getCtlName()+"']").each(function(){
			       if($(this).next().text()=='其他'){
			    	   $(this).next().next().hide();
			    	   $otherinput=$(this).next().next();
			     	   $(this).change(function(){
			    		   if($(this).attr("checked")=='checked'){
			    			   $otherinput.show();
			    		   }else{
			    			   $otherinput.hide();
			    			   //$otherinput.val('其他');
			    		   }
			    	   });
			    	   
			       }
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
		
			if(this.options.ctrlField != "" && this.options.ctrlField != null&& this.options.ctrlField != ",") {
				var temps = this.options.ctrlField.split(",")[0];
				fieldname= this.options.columnProperty.columnName;
					var temp = temps.split(";");
						var id = this.$element.attr("name")+"_0";
						$.each(temp,function (i,obj){
							if($(".value[name='"+obj+"']").parent().parent().attr("class")=='field'){
								$(".value[name='"+obj+"']").parent().parent().hide();
							}
						});
							$("#"+id).die().live("click",function() {
								$.each(temp,function (i,obj){
									if($(".value[name='"+obj+"']").parent().parent().attr("class")=='field'){
									if($(".value[name='"+obj+"']").parent().parent().css("display") == "none") {
										$(".value[name='"+obj+"']").parent().parent().show();
									}else {
										$(".value[name='"+obj+"']").parent().parent().hide();
										}
										}
									});
								
							});
					
		
		 }
			
			this.setDefaultValue(options.columnProperty.defaultValue);
			  this.addMustMark();
		},
		
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			// console.log(value.length);
			// var time1 = (new Date).getTime();
			 //设值之前先取消选中
			 
			$("input[name='"+this.getCtlName()+"']:checked").each(function(){
				$(this).prop("checked",false);
			});
			
			if(value.length > 0){
				 var value1=value.split(",");
				 for(var i=0;i<value1.length;i++){
					 $("input[name='"+this.getCtlName()+"'][value='"+value1[i]+"']").prop("checked",true);
				 }
			}
			
			 if(this.options.isShowAsLabel==true){
				 this.getGroupDivElement().next().html(this.getDisplayValue());
	    	  }
			 
			 this.setToRealDisplayColumn();
			 
		 } ,
		 
		 setValueByMap:function(valuemap){
			 
			 $("input[name='"+this.getCtlName()+"']").prop('checked', false);
			 var valueobj=eval("("+valuemap+")");
			 
			 var str=new Array();
			 for(attribute in valueobj){
				 var $checkobj=$("input[name='"+this.getCtlName()+"'][value='"+attribute+"']");
				 $checkobj.prop("checked",true);
				 str.push(valueobj[attribute]);
				 if($checkobj.next().text()=='其他'){
					 $checkobj.next().next().val(valueobj[attribute]);
					 if(this.options.isShowAsLabel==false){
						 $checkobj.next().next().show();
			    	  }
					}
			 }
			 
			 str.join("'"); 
			 
			 if( this.getGroupDivElement().next().size() > 0 && !this.getGroupDivElement().next().is("img")){
				 this.getGroupDivElement().next().html(str.toString());
			 }
			 
			// this.getGroupDivElement().next().html(str.toString());
			 
		 },
		 
		 
		 //设置disabled属性
		 setEnable:function(isenable){
			 $.ControlUtil.setEnable($("input[name='"+this.getCtlName()+"']"),isenable);
			 this.options.disabled=!isenable;
		 } ,
		 
		 //设置hide属性
		 setVisible:function(isvisible){
			 var elment=this.getGroupDivElement();
			 $.ControlUtil.setVisible(elment,isvisible);
			 this.options.isHide=!isvisible;
		 } ,
		 
		 
		 setReadOnly:function(){
			 this.setEnable(false);
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
		 
		 
		 
		 //设置显示值。
		 setDisplayValue:function(value){
			 $("input[name='"+this.getCtlName()+"'][value='"+value+"']").attr("checked",true);
		 } ,
	       
	    //get..........................................................//
		
		 getCtlName:function(){
			 var name="_checkbox"+this.$element.attr("name");
			 return name;
		 },
		 
		 getCtlElement:function(){
	    	 return "input[name='" + this.getCtlName() + "']";
		 },
		 
		 getGroupDivElement:function(){
			 var colEnName=this.$element.attr("name");
			 return $('#_div_checkgroups_'+colEnName);
		 },
		 
		 //返回控件值
		 getValue:function(){
			 //如果是单选模式的，则直接返回对应的配置项
			 if(this.options.selectMode=='1'){
	        	   if( $("input[name='"+this.getCtlName()+"']").prop('checked')==true){
	        			var selectobj = eval("("+this.options.singleCheckContent+")");
						for(attrbute in selectobj){
							return attrbute;
						} 
	        	   }else{
	        			var selectobj = eval("("+this.options.singleUnCheckContent+")");
						for(attrbute in selectobj){
							return attrbute;
						}
	        	   }
	           }
			 
			 var str=new Array();
	           $("input[name='"+this.getCtlName()+"']:checked").each(function(){
	               var str1=$(this).val();
	               str.push(str1);
	           });
	           str.join("'");
	           
	          
			 return	 str.toString();
		 },
		 

		 getDisplayValue:function(){
			//如果是单选模式的，则直接返回对应的配置项
			 if(this.options.selectMode=='1'){
	        	   if( $("input[name='"+this.getCtlName()+"']").prop('checked')==true){
	        			var selectobj = eval("("+this.options.singleCheckContent+")");
						for(attrbute in selectobj){
							return selectobj[attrbute];
						} 
	        	   }else{
	        			var selectobj = eval("("+this.options.singleUnCheckContent+")");
						for(attrbute in selectobj){
							return selectobj[attrbute];
						}
	        	   }
	           }
			 
			 var str=new Array();
	           $("input[name='"+this.getCtlName()+"']:checked").each(function(){
	               var str1=$(this).next().text();
	               
	               if(str1=='其他'){
	            	   str1=$(this).next().next().val();
	               }
	               
	               str.push(str1);
	           });
	           str.join("'");
			 return	 str.toString();
		 },
		 
		 getValueMap:function(){
			//如果是单选模式的，则直接返回对应的配置项
			 if(this.options.selectMode=='1'){
	        	   if( $("input[name='"+this.getCtlName()+"']").prop('checked')==true){
						return eval("("+this.options.singleCheckContent+")");
	        	   }else{
						return eval("("+this.options.singleUnCheckContent+")");
	        	   }
	           }
			 
			 var v={};
	           $("input[name='"+this.getCtlName()+"']:checked").each(function(){
	        	   var value=$(this).val();
	        	   var displayvalue=$(this).next().text();
	               if(displayvalue=='其他'){
	            	   displayvalue=$(this).next().next().val();
	               }
	               v[value]=displayvalue;
	           });
			 return v;
		 },
		 

		 
		 isValueMap:function(){
			 return true;
		 },
		 
		   //bind函数，桥接
	     bind:function(eventname,event){
	    	 
	    		$('#_div_checkgroups_'+this.$element.attr("name")).bind(eventname,event);
	    		return this;
	     },
		 
		 //unbind函数，桥接
	     unbind:function(eventname){
	    	 
	    	 $('#_div_checkgroups_'+this.$element.attr("name")).bind(eventname);
	    		return this;
	     },
	     
	};
	
	/*
	 * CHECKBOX PLUGIN DEFINITION =========================
	 */
	$.fn.wcheckBox = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wcheckBox');
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
					data = $this.data('wcheckBox'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new CheckBox(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 data2.init();
						 $this.data('wcheckBox',data2 );
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

	$.fn.wcheckBox.Constructor = CheckBox;

	$.fn.wcheckBox.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			isShowAsLabel:false,
			//私有属性
			isHide:false,//是否隐藏
			checked:false,
			disabled:false,
			ctrlField:null,
			optionDataSource:"1", //备选项来源1:常量,2:字段
			optionSet:[],
			dictCode:null,
			dataDictionarys:[],
			selectMode:"2",//选择模式，单选1，多选2
			singleCheckContent :"",//单选 选中内容
			singleUnCheckContent:""//单选 取消选中内容
				
	};
})(jQuery);