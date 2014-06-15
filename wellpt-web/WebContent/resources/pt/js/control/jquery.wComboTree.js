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
	 * COMBOTREE CLASS DEFINITION ======================
	 */
	var ComboTree = function(element, options) {
		this.init("wcomboTree", element, options);
	};

	
	ComboTree.prototype = {
		constructor : ComboTree,
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
				var data3 = options.mutiselect;
				$(this.$element).after("<input type='hidden' id='_"+colEnName+"' name='_"+colEnName+"' value=''>");
				var data1 = data.replace("|","");
				if($('#data').val() == undefined) {
					$(this.$element).after("<input type='hidden' id='"+data1+"'  value='"+data.replace("|",",")+"'>");
				}else {
					$(this.$element).after("<input type='hidden' id='"+data1+"'  value='"+data.replace("|",",")+"'>");
				}
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
				
				if(isMod){
					if(val != undefined && methodName != "" && methodName != null) {
						var method = methodName.split(".");
						var servicename = method[0];
						var Method = method[1].split("(")[0]; //方法名
						var datas = method[1].split("(")[1].replace(")",""); //数据
						var data = datas.split(",")[0];
						data = data.replace("\'","").replace("\'","");
						var initService = servicename+ "."+Method;
						var VAL = val;
						JDS.call({
							async:false,
							service : initService,
							data : [data,val],
							success : function(result) {
								var data = result.data;
								$("#"+showId).attr('value',data.label);
							}
						});
					}else {
						if(val != undefined && val.indexOf(";")>0) {
							var _val = val.split(";");
							$(this.$element).attr('value',_val[1]);
						}else {
							$(this.$element).val(val);
							if(methodName == "" || methodName == null) {
								JDS.call({
									async:false,
									service : servicename+"."+method,
									data : ["-1",$("#"+data1).val()],
									success : function(result) {
										var data = result.data;
										for(var l=0;l<data.length;l++) {
											if(val == data[l].data) {
												$(this.$element).val(data[l].name);
											}
										}
									}
								});
							}
						}
						
					}
				}
				if(options.columnProperty.showType==dyshowType.showAsLabel){
					 $.ControlUtil.setIsDisplayAsLabel(this.$element,options,true);
				}else if(options.columnProperty.showType==dyshowType.disabled){
					 $.ControlUtil.setEnable(this.$element,false);
				 }else if(options.columnProperty.showType==dyshowType.hide){
					 $.ControlUtil.setVisible(this.$element,false);
				 }else if(options.columnProperty.showType==dyshowType.readonly){
					 $.ControlUtil.setReadOnly(this.$element,true);
				 }else {
					var setting = {
							async : {
								otherParam : {
									"serviceName" : servicename,
									"methodName" : method,
									"data" : dataArrays
								}
							},
							check : {//复选框的选择做成可配置项
								enable:data3
							},
							callback : {
								onClick:function (event, treeId, treeNode) {
									var inputId = treeId.replace("_ztree","");
									if(typeof(treeNode.data) == "object") {
										$("#"+inputId).val(treeNode.name);
										$("#_"+inputId).val(treeNode.data.reservedText1+";"+treeNode.name);
									}else {
										$("#"+inputId).val(treeNode.name);
										$("#_"+inputId).val(treeNode.data);
									}
								}
							}
						};
					//初始化树的值
					$("#_"+colEnName).val(options.columnProperty.defaultValue);
					this.$element.comboTree({
						labelField : colEnName,
						valueField : "_"+colEnName,
						width: options.treeWidth,
						height: options.height,
						treeSetting : setting,
						initService :options.initService,
						initServiceParam : options.initServiceParam
					});
					//$("#_"+colEnName).val(val);
				}
			//the end
			//设置默认值
			//this.setValue(options.columnProperty..defaultValue);	
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
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
			 var valuename=this.$element.attr("name");
			 if(this.options.isinitTreeById){
				 valuename=this.$element.attr("id");
			 }
			 return $("#_"+valuename).val();
		 } ,
		 
		 //设置必输
		 setRequired:function(isrequire){
			 $.ControlUtil.setRequired(isrequire,this.options);
		 } ,
		 
		 //设置可编辑
		 setEditable:function(iseditable){
			 this.setReadOnly(!iseditable);
			 this.setEnable(iseditable);
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
			 $.ControlUtil.setVisible(isvisible);
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
						$this.data('wcomboTree', (data = new ComboTree(this,
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

	$.fn.wcomboTree.Constructor = ComboTree;

	$.fn.wcomboTree.defaults = {
			
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
			
			//控件私有属性
			disabled:false,
	        readOnly:false,
	        isHide:false,//是否隐藏
			serviceName:null,
			treeWidth: 220,
			treeHeight: 220,
			
			
			//获得根据真实值获得初始值。新版需要解析value，将真实值解析为真实值和显示值，分别填充到对应元素上.
			initService : "dataDictionaryService.getKeyValuePair",
			initServiceParam : [ "DY_FORM_FIELD_MAPPING" ],
			//是否允许多选
			mutiselect:true,
			isinitTreeById:false
	};
	
})(jQuery);