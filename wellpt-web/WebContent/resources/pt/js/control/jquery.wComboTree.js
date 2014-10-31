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
	 * COMBOTREE CLASS DEFINITION ======================
	 */
	var ComboTree = function(element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn["wcomboTree"].defaults, options,
				this.$element.data());
	};

	
	ComboTree.prototype = {
		constructor : ComboTree,
	
		initSelf:function(){
			var options=this.options;
			this.$element.attr("id",this.$element.attr("name"));
			// this.$element.attr("data",options.data);	//树形下拉框调用的服务层的方法的请求参数
			this.$element.attr("servicename",options.servicename);	//树形下拉框调用的服务层
			// this.$element.attr("methodname",options.methodname);	 //树形下拉框调用的服务层的方法
			this.$element.addClass("input-tier");//css in wellnewoa.css
			 //设置文本的css样式
			 this.setTextInputCss();
			 
			 //设置默认值
			// this.setDefaultValue(this.options.columnProperty.defaultValue);
			 var isMod = false;//(dataUid != '' ? true : false);
			 var colEnName="";
			 if(options.isinitTreeById){
				 colEnName=this.$element.attr("id");
			 }else{
				 colEnName=this.$element.attr("name");
			 }
			 
			//树形下拉框
			 var val = "";
//				alert("树形下拉框");
			 	var serviceName=this.options.serviceName;
				var showId = this.$element.attr('id');
				var servicename = serviceName.split(".")[0]; //服务名
				var method = serviceName.split(".")[1].split("(")[0]; //方法名
				var datas = serviceName.split(".")[1].split("(")[1].replace(")",""); //数据
				var data = datas.split(",")[0];
				data = data.replace("\'","").replace("\'","");
				/*var data2 = datas.split(",")[1];
				var data3 = "";
				if(data2 != "" && data2 != null) {
					data3 = data2;
				}else {
					data3 = false;
				}*/
				var data3 = options.mutiSelect;
				$(this.$element).after("<input type='hidden' id='_"+colEnName+"' name='_"+colEnName+"' value=''>");
				var data1 = data.replace(/\|+/g,"");
				$(this.$element).after("<input type='hidden' id='"+data1+"'  value='"+data.replace(/\|+/g,",")+"'>");
				var flag = true;
				if(method[0] == 'getFormDataAsTree') {
					 flag = false;
				}else {
					 flag = true;
				}
				var dataArrays = new Array();
				if($("#"+data1).val().split(",").length >1) {
					var dataArray = $("#"+data1).val().split(",");
					for(var da = 0; da<dataArray.length;da++) {
						dataArrays.push(dataArray[da]);
					}
				}else if($("#"+data1).val().indexOf("${data}") > 0){
					dataArrays.push($("#data").val());
				}else {
					dataArrays.push($("#"+data1).val());
				}

				if(options.columnProperty.showType==dyshowType.showAsLabel){
					this.setDisplayAsLabel();
				}else if(options.columnProperty.showType==dyshowType.disabled){
					this.setEnable(false);
				 }else if(options.columnProperty.showType==dyshowType.hide){
					 this.setVisible(false);
				 }else if(options.columnProperty.showType==dyshowType.readonly){
					 this.setReadOnly(true);
				 }else {
					 var _this = this;
					var setting = {
							async : {
								otherParam : {
									"serviceName" : servicename,
									"methodName" : method,
									"data" : dataArrays
								}
							},
							view : {
								showLine : true
							},
							check : {//复选框的选择做成可配置项
								enable:data3
							},
							src:"control",
							callback : {
								onClick:function (event, treeId, treeNode) {
									if(_this.options.mutiSelect){//多选只能通过点击checkbox
										return ;
									}
									var inputId = treeId.replace("_ztree","");
									if(typeof(treeNode.data) == "object") {
										$("#"+inputId).val(treeNode.name);
										$("#_"+inputId).val(treeNode.data.reservedText1+";"+treeNode.name);
									}else {
										$("#"+inputId).val(treeNode.name);
										$("#_"+inputId).val(treeNode.data);
									}
									
									
									_this.setToRealDisplayColumn();
								 
									
								},
								onCheck:function(event, treeId, treeNode){
									if(!_this.options.mutiSelect){
										return ;
									}
									_this.setToRealDisplayColumn();
								}
							}
						};
					//初始化树的值
					$("#_"+colEnName).val(options.columnProperty.defaultValue);
				 
					this.$element.comboTree({
						labelField : colEnName,
						valueField : "_"+colEnName,
						width: options.treeWidth,
						height: options.treeHeight,
						treeSetting : setting,
						initService :options.initService,
						initServiceParam : options.initServiceParam
					});
					
				 
					//$("#_"+colEnName).val(val);
				}
			//the end
			//设置默认值
			//this.setValue(options.columnProperty..defaultValue);	
				  this.addMustMark();
				
		},
		
		 //设置显示值。
		 setDisplayValue:function(value){
				this.$element.val(value);	
		 } ,
		 
		 getDisplayValue:function(){
			 return this.$element.val();
		 },
		 
		 
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			
			 var valuename=this.getCtlName();
			 
			 
			 if(this.options.isinitTreeById){
				 valuename=this.$element.attr("id");
			 }
		 
			 $("#_"+valuename).val(value);
			 
			 this.setToRealDisplayColumn();
			 
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
			 //this.$element.comboTree("initValue", value);
		 },
	       
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 var valuename=this.$element.attr("name");
			 if(this.options.isinitTreeById){
				 valuename=this.$element.attr("id");
			 }
			 
			 return $("#_"+valuename).val();
		 },
		 
		 isValueMap:function(){
			 return true;
		 },

		 getValueMap:function(){
			  
			 var v={};
			 
			 if(this.getValue()==""||this.getValue()==undefined){
				 if(!this.options.mutiSelect  ){//单选,
					var displayV = this.getDisplayValue();
					if(typeof displayV != "undefined" && displayV.length > 0){
						v[this.getDisplayValue()] = this.getDisplayValue();
					} 
				 }
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
	 * COMBOTREE PLUGIN DEFINITION =========================
	 */
	$.fn.wcomboTree = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wcomboTree');
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
					data = $this.data('wcomboTree'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						 data = new ComboTree(this,options);
						 var datacopy={};
						 var data1=$.extend(datacopy,data);
						 var extenddata=$.extend(data,$.wControlInterface);
						 var data2=$.extend(extenddata,data1);
						 var data3=$.extend(data2,$.wTextCommonMethod);
						 data3.init();
						 $this.data('wcomboTree',data3 );
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

	$.fn.wcomboTree.Constructor = ComboTree;

	$.fn.wcomboTree.defaults = {
			
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//控件私有属性
			disabled:false,
	        readOnly:false,
	        isShowAsLabel:false,
	        isHide:false,//是否隐藏
			serviceName:null,
			treeWidth: 220,
			treeHeight: 220,
			mutiSelect:true,
			realDisplay:{},
			
			//获得根据真实值获得初始值。新版需要解析value，将真实值解析为真实值和显示值，分别填充到对应元素上.
			initService : "dataDictionaryService.getKeyValuePair",
			initServiceParam : [ "DY_FORM_FIELD_MAPPING" ],
			//是否允许多选
			
			isinitTreeById:false
	};
	
})(jQuery);