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
	var ComboTree = function($placeHolder, options) { 
		this.options = $.extend({}, $.fn["wcomboTree"].defaults, options);
		this.value = "";
		this.$editableElem = null;
		this.$labelElem = null;
		this.$placeHolder = $placeHolder;
	};

	
	ComboTree.prototype = {
		constructor : ComboTree  
	};
	
	
	
	$.ComboTree = { 
			createEditableElem:function(){ 
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 
				 var options = this.options;
				 var ctlName =  this.getCtlName();
				 var editableElem = document.createElement("input");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 editableElem.setAttribute("id", ctlName);
				 editableElem.setAttribute("type", "text");
				 
				 $( editableElem).css(this.getTextInputCss());
				 
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass); 
				 this.$editableElem.addClass("input-tier");//css in wellnewoa.css
				 
				  
				 var colEnName= ctlName;
				  
				 
				//树形下拉框
				 var val = "";
//					alert("树形下拉框");
				 	var serviceName=this.options.serviceName;
					 
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
					this.$editableElem.after("<input type='hidden' id='_"+colEnName+"' name='_"+colEnName+"' value=''>");
					 this.$_editableElem = this.$editableElem.next();
					 
					var data1 = data.replace(/\|+/g,"");
					this.$editableElem.after("<input type='hidden' id='"+data1+"'  value='"+data.replace(/\|+/g,",")+"'>");
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
										
										_this.setValue2Object();//将值分别从labelfield和valuefield中取出放到内存中的value对象中
										//_this.setToRealDisplayColumn();
									},
									onCheck:function(event, treeId, treeNode){
										if(!_this.options.mutiSelect){
											return ;
										}
										_this.setValue2Object();//将值分别从labelfield和valuefield中取出放到内存中的value对象中
										//_this.setToRealDisplayColumn();
									}
								}
							};
						//初始化树的值
						 this.$_editableElem.val(options.columnProperty.defaultValue);
						
						this.$editableElem.comboTree({
							labelField : colEnName,
							valueField : "_"+colEnName,
							width: options.treeWidth,
							height: options.treeHeight,
							treeSetting : setting,
							initService :options.initService,
							initServiceParam : options.initServiceParam
						});
						var _this = this;
						
						 
						this.$editableElem.keyup(function(){
							var val = {};
							val[$(this).val()] = $(this).val(); 
							_this.setValueByMap(JSON.stringify(val), false);
						});
						//this.$editableElem.change(evtFun);
						
						this.$editableElem.bind('paste',function(){
							var _dom = this;
							window.setTimeout(function(){
								var val = {};
								val[$(_dom).val()] = $(_dom).val(); 
								_this.setValueByMap(JSON.stringify(val), false); 
							},100);
						});
						/*this.$editableElem.bind('change',function(){
							var val = {};
							val[$(this).val()] = $(this).val(); 
							_this.setValueByMap(JSON.stringify(val), false);
						 });*/
						
			 },
			 
			 /*设值到标签中*/
			 setValue2LabelElem:function(){
				 if(this.$labelElem == null){
					 return;
				 }
				 this.$labelElem.html(this.getDisplayValue());
			 },
			 
			 /*设置到可编辑元素中*/
			 setValue2EditableElem:function(){
				 
				 if(this.$editableElem == null){
					 return;
				 }
				 /*var valueObj = {};
				if(this.value == null || $.trim(this.value).length == 0){  
				}else{
					valueObj = eval("(" +  this.value + ")");
				}*/
				this.$_editableElem.val(this.getValue());
				 
				this.$editableElem.val(this.getDisplayValue());
				 
			 },
			 
			 
			 isValueMap:function(){
				 return true;
			 } ,
			 
			
			  
			   
			//设值,值为真实值
			 setValue:function(value){
				 var options = this.options; 
				if(value.length > 0){
					 var value1= null;
					if(value.indexOf(",") != -1){//值用,隔开 
						value1=value.split(",");
					}else{//值用;隔开
						value1=value.split(";");
					}
					var valueMap = {};
					 
					 for(var i=0;i<value1.length;i++){
						 valueMap[value1[i]] = value1[i];
					 }
					  this.setValueByMap(JSON.stringify(valueMap));
				}
			 } ,
			//将值分别从labelfield和valuefield中取出放到内存中的value对象中
			 setValue2Object:function(){ 
				 var realValue = this.$_editableElem.val(); 
				 if(realValue.length == 0){
					 this.setValueByMap("");
					 return;
				 }
				 var displayValue = this.$editableElem.val();
				 var values = realValue.split(';');
				 var displayvalue=displayValue.split(';');
				 if(values.length!=displayvalue.length){
					 throw new Error('隐藏值和显示值长度不一致!');
				 }
				 var v = {};
				 for ( var i = 0; i < values.length; i++) {
					 v[values[i]]=displayvalue[i];
				}
				 
				 this.setValueByMap( JSON.stringify(v));
			 }
		  
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
						 data = new ComboTree($(this),options);
						 
						 $.extend(data,$.wControlInterface); 
						 $.extend(data,$.wTextCommonMethod);
						 $.extend(data, $.ComboTree);
						 
						
						 data.init();
						 $this.data('wcomboTree',data );
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