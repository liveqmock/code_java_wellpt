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
	 * DIALOG CLASS DEFINITION ======================
	 */
	var Dialog = function(element, options) {
		this.init("wdialog", element, options);
	};

	Dialog.prototype = {
		constructor : Dialog,
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
			//根据show类型展示
			 $.ControlUtil.dispalyByShowType(this.$element,this.options);
			 
			 var _relationDataDefiantion=this.options.relationDataDefiantion;
			 var _relationDataValueTwo=this.options.relationDataValueTwo;
			 var _relationDataTwoSql=this.options.relationDataTwoSql;
			 var tempArray = _relationDataDefiantion.split("|");
			 this.$element.unbind("click");
			    this.$element.click(function(){
					var paramsId = $(this).attr("id");
					//获得要查询的字段
					var str = "";
					for(var j=0;j<tempArray.length;j++){
						if(tempArray[j] != "") {
							var tempObj = JSON.parse(tempArray[j]);
							str += ','+tempObj.sqlField;
						}
					}
					strArray = tempArray;
					var path = "";
					if(_relationDataValueTwo!= undefined) {
						path = "/basicdata/dyview/view_show?viewUuid="+_relationDataValueTwo+"&currentPage=1&openBy=dytable&relationDataDefiantion="+str;
					}
					var parmArray = new Array();
					var relationDataTwoSql2 =_relationDataTwoSql;
					if(relationDataTwoSql2!="" && relationDataTwoSql2!=null && relationDataTwoSql2!=undefined){
						while(relationDataTwoSql2.indexOf("${")>-1){
							var s1=relationDataTwoSql2.match("\\${.*?\\}")+"";
							parmArray.push(s1.replace("${", "").replace("}", ""));
							relationDataTwoSql2 = relationDataTwoSql2.replace(s1,"");
						}
					}
					if(_relationDataTwoSql!=undefined&&_relationDataTwoSql!=""){
						path += "&"+_relationDataTwoSql;
					}
				/*	for(var jt=0;jt<parmArray.length;jt++){
						if(eval('formData.'+parmArray[jt])!=undefined&&eval('formData.'+parmArray[jt])!=""&&eval('formData.'+parmArray[jt])!="undefined"){
							path = path.replace("${"+parmArray[jt]+"}",eval('formData.'+parmArray[jt])) ;
						}
					}*/
					if(path.indexOf("${")>-1){
						var json = new Object(); 
						json.content = "没有相应条件的数据";
				        json.title = "相关数据源";
				        json.height= 600;
				        json.width= 800;
				        showdialog(json);
					}else{
						$.ajax({
							async:false,
							url : ctx + path,
							success : function(data) {
								var json = new Object(); 
								json.content = "<div class='dnrw' style='width:99%;'>" +data+"</div>";
						        json.title = "相关数据源";
						        json.height= 600;
						        json.width= 800;
						        showDialog(json);
						        //var afterDialogSelect = dytable.afterDialogSelect;
						        var afterDialogSelect =null;
						        $(".dataTr").unbind("dblclick");
						        $(".dataTr").live("dblclick",function(){
						        	var paramsObj = new Object();
						        	var valStr = $(this).attr("jsonstr").replace("{","").replace("}","").split(",");
						        	for(var ai1=0;ai1<valStr.length;ai1++){
										for(var j=0;j<tempArray.length;j++){
											var tempObj = JSON.parse(tempArray[j]);
											if(tempObj.sqlField.replace("_","").toUpperCase()==valStr[ai1].split("=")[0].toUpperCase().replace(" ","")){
												if(tempObj.formField == "expand_field"){
													var expandValue = "";
													expandValue = paramsId + ":" + valStr[ai1].split("=")[1]
													if($("#"+tempObj.formField).val() != null && $("#"+tempObj.formField).val() != "") {
														var oldValue = $("#"+tempObj.formField).val();
														$("#"+tempObj.formField).val(oldValue+","+expandValue);
														paramsObj[tempObj.formField] =  oldValue+","+expandValue;
													}else {
														$("#"+tempObj.formField).val(expandValue);
														paramsObj[tempObj.formField] =  expandValue;
													}
												}else{
													$("#"+tempObj.formField).val(valStr[ai1].split("=")[1]);
													paramsObj[tempObj.formField] =  valStr[ai1].split("=")[1];
												}
												
											}
											if(valStr[ai1].split("=")[0].toUpperCase().replace(" ","")=="MATTERID"){
												$("#matterId").val(valStr[ai1].split("=")[1]);
											}
										}
						        	}
						        	$("#dialogModule").dialog( "close" );
						        	if(afterDialogSelect){
						        		afterDialogSelect.call(this, paramsId, paramsObj);
									}
								});
							}
						});
					}
				});
			    
			   $("#fieldName9").val("11111111111");
				
				
		},
		getOptions : function(options) {
			options = $.extend({}, $.fn[this.type].defaults, options,
					this.$element.data());
			return options;
		},
				
		//set............................................................//
	     
		//设值
		 setValue:function(value){
			 $.ControlUtil.setValue(this.$element,this.options,value);
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
		 
		 //显示为控件
		 setDisplayAsCtl:function(){
			 $.ControlUtil.setDisplayAsCtl(this.$element,this.options);
		 },
		 
	       
	    //get..........................................................//
		
		 //返回控件值
		 getValue:function(){
			 return this.$element.val();
		 },

		 isValueMap:function(){
			 return false;
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
	 * DIALOG PLUGIN DEFINITION =========================
	 */
	$.fn.wdialog = function(option) {
		var method = false;
		var args = null;
		if (arguments.length == 2) {
			method = true;
			args = arguments[1];
		}
		
		if (typeof option == 'string') {
			if(option === 'getObject'){ //通过getObject来获取实例
				var $this = $(this);
				data = $this.data('wdialog');
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
					data = $this.data('wdialog'), 
					options = typeof option == 'object'
							&& option;
					if (!data) {
						$this.data('wdialog', (data = new Dialog(this,
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

	$.fn.wdialog.Constructor = Dialog;

	$.fn.wdialog.defaults = {
			columnProperty:columnProperty,//字段属性
			commonProperty:commonProperty,//公共属性
	        readOnly:false,
	        isShowAsLabel:false,
	        disabled:false,
	        isHide:false,//是否隐藏
	    	relationDataTextTwo: "",		 	
			relationDataValueTwo: "", 		 	
			relationDataTwoSql: "", 	
			relationDataDefiantion:"", 	
			relationDataShowMethod: "", 	
			relationDataShowType: ""
	};
	
})(jQuery);