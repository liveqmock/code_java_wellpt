/**
 * 控件公共接口方法
 */
;
(function($){
	
	$.wControlInterface={
		   labelClass:"labelclass",
		   editableClass:"editableClass",
		   init : function() {  
				 //初始化私有参数
				 this.initSelf(); 
			},
			initSelf:function(){
				var options=this.options; 
				if(this.isInSubform()){//在从表中
					 this.setDisplayAsLabel();
				}else{
					this.dispalyByShowType();
				}
				if(options.columnProperty.data){//在初始化时也有数据
					this.setDefaultValue ( this.options.columnProperty.data);
				}else{//没数据则用默认值
					this.setDefaultValue(options.columnProperty.defaultValue);
				}
				this.addMustMark(); 
				this.options.initComplete = true;
			},
			
			/**
			 * 清空控件
			 */
			clear:function(){
				if(this.isValueMap()){
					this.setValueByMap("", true);
				}else{
					this.setValue("");
				}
			},
			
			 /**
		     * 设置文本控件的css样式.
		     * @param elment
		     * @param options
		     */
	     	 setTextInputCss:function(){
	    		 //宽、高
	     		this.$element.css(this.getTextInputCss()); 
	    	},
	    	
	    	getTextInputCss:function(){
	    		var width = this.options.commonProperty.ctlWidth;
	    		if(width && width.indexOf("%") == -1){
	    			width = width + "px";
	    		}
	    		return {
        			"width": width,
        			"height": this.options.commonProperty.ctlHight+"px",
        			"text-align":this.options.commonProperty.textAlign,
        			"font-size":this.options.commonProperty.fontSize+"px",
        			"color":this.options.commonProperty.fontColor,
        		   };
	    	},
	    	/**
	    	 * 
	    	 * @param isRender 是否对控件再进行界面渲染,false表示不渲染,true或者没定义表示要渲染   
	    	 */
	    	 setValueByMap:function(valuemap, isRender){ 
				 this.value = valuemap;
				 if(!this.options.isHide){//该控件被隐藏时则不进行渲染
					 this.render(isRender);//将值渲染到页面元素上
				 }
				 if(this.options.initComplete ){//在初始化完成后,才会进行这个操作 
					 this.setToRealDisplayColumn();//设置到真实值，显示值字段上
				 }
				  
				 this.invoke("afterSetValue",  this.value);
			 },

			/**
			 * 设置默认值
			 * @param defaultValue
			 */
			setDefaultValue:function(defaultValue){
				//加空判断
				if(defaultValue==null){
					return;
				}
				 
				if(this.isValueAsJsonCtl()){  
					if(defaultValue.indexOf("{") == -1){
						this.setValue(defaultValue); 
					}else{
						this.setValueByMap(defaultValue);
					} 
				}else{ 
					 this.setValue(defaultValue);
				}
				
			},
			
			/**
			 * 根据showtype类型展示
			 */
			dispalyByShowType:function(){ 
				var showType=this.options.columnProperty.showType; 
				 if(showType==dyshowType.showAsLabel){
					 this.setDisplayAsLabel();
				 }else if(showType==dyshowType.readonly){ 
					 //this.setDisplayAsCtl();
					 this.setReadOnly(true);
				 }else if(showType==dyshowType.disabled){
				 
					 this.setEnable(false);
				 }else if(showType==dyshowType.hide){
					// this.setDisplayAsCtl();
					 this.setVisible(false);
				 }else{
					 //this.setDisplayAsCtl();
					 
					 this.setEnable(true);
				 }
				
			},
			
			 
			
			//设值
			 setValue:function(value){
				
				this.get$InputElem().val(value);
				this.value = value;
				this.invoke("afterSetValue",  this.value);
			 } ,
			 
			 //设置必输
			 setRequired:function(isrequire){
				 $.ControlUtil.setRequired(isrequire,this.options);
			 } ,
			 
			 //设置可编辑
			 setEditable:function(isEnable){ 
				 this.options.isShowAsLabel=false;
				 this.render();
				 if(isEnable){
					 
					 this.setEnable(true);
				 }
			 } ,
			 
			 
			 
			 //设置hide属性
			 setVisible:function(isvisible){ 
				this.options.isHide = !isvisible;
				if(isvisible){//设置为显示出来
					this.render();
					this.$placeHolder.parents(".field").show();
				}else{//设置为隐藏起来
					this.hideLabelElem();
					this.hideEditableElem();
					this.$placeHolder.parents(".field").hide(); 
				}
			 } ,
			 
			
			 
			 //显示为lablel
			 setDisplayAsLabel:function(){
				var options = this.options;
				 if(this.$labelElem == null){//创建标签元素
					 var labelElem = document.createElement("span");
					 labelElem.setAttribute("class", this.labelClass);
					 labelElem.setAttribute("name", this.getCtlName());
					
					 $( labelElem).css(this.getTextInputCss());
					 this.$placeHolder.after($( labelElem));
					 this.$labelElem =  this.$placeHolder.next("." + this.labelClass); 
					//添加url点击事件
					
				 }
				 
				 this.$labelElem.show(); 
				 options.isShowAsLabel=true; 
				 this.hideEditableElem();
				 this.setValue2LabelElem(); 
				 if(this.setCtlField){
					 this.setCtlField();
				 }
				 this.addUrlClickEvent(urlClickEvent); 
			 } ,

			 //显示为可编辑框
			 setDisplayAsCtl:function(){
				 var options = this.options;
				 options.isShowAsLabel=false;
				 if(this.$editableElem == null){
					 this.createEditableElem();
				 }
				 
				this.$editableElem.show();
				this.hideLabelElem(); 
				this.setValue2EditableElem();
				this.invoke("show");
			 } ,
		 

			 hideEditableElem:function(){
				 if(this.$editableElem == null){
					 return;
				 }
				 this.$editableElem.hide();
				 this.$editableElem.siblings(".Validform_checktip").hide();
			 },
			 
			 hideLabelElem:function(){
				 if(this.$labelElem == null){
					 return;
				 }
				 this.$labelElem.hide();
			 },
			 
			 
			 
			 setDataUuid:function(datauuid){
				 this.options.columnProperty.dataUuid=datauuid;
			 },
			 
			 getDataUuid:function(){
				 return this.options.columnProperty.dataUuid;
			 },
		       
			 getFormDefinition:function(){
				 return this.options.columnProperty.formDefinition;
			 },
			 
			 
			 setPos:function(pos){
				 this.options.columnProperty.pos=pos;
			 },
			 
			 getPos:function(pos){
				 return this.options.columnProperty.pos;
			 },
			 
			 
		    //get..........................................................//
			
			 //返回控件真实值
			 getValue:function(){
				 var value = this.value;
				 
				 if(value == null || $.trim(value).length == 0){
					 return "";
				 }
				 
				 if(this.isValueMap()){ 
					 var valueObj = eval("(" + value+ ")");
					 var realValue = [];
					 for(var i in valueObj){
						 realValue.push(i);
					 }
					 return	realValue.join(";");
				 }else{
					 return this.value;
				 }
				
			 },
			 
			 getValueMap:function(){
				 
				 	if(typeof this.value == "object"){
				 		return JSON.cStringify( this.value)
				 	}else{
				 		if(this.value == undefined || this.value == null || $.trim(this.value).length == 0 ){
				 			return JSON.cStringify( {})
				 		}
				 		return this.value;
				 	}
				 	
					
			 },
			 
			 getDisplayValue:function(){
				 
				 var value = this.value;
				
				 if(value == null || $.trim(value).length == 0){ 
					 return "";
					
				 }
				 var valueObj = eval("(" + value+ ")");
				 var displayValue = [];
				 for(var i in valueObj){
					 displayValue.push(valueObj[i]);
				 }
				 
				 return	displayValue.join(";");
			 },

			 isValueMap:function(){
				 return false;
			 },
			 /**
			  * 返回是否可编辑(由readOnly和disabled判断)
			  * @returns {Boolean}
			  */
			 isEditable:function(){
				 if(this.options.showType ==  dyshowType.edit){
					 return true;
				 }else{
					 return false;
				 }
			 },
			 
			
			 
			 isEnable:function(){
				// return !this.options.disabled;
				 if(this.options.showType ==  dyshowType.disabled){
					  return false;
				 }
				 return true;
			 },
			 
		
			 
			 clean: function( selector ) {
					return $(selector)[0];
			 },
			 
			 
			 
			 isVisible:function(){
				 return !this.options.isHide ;
			 },
			 
			 
			 //设置disabled属性
			 setEnable:function(isenable){
				if(this.isShowAsLabel()){
					return;
				}
				this.render();
				 if(isenable){
					 this.options.columnProperty.showType = dyshowType.edit;
					 this.get$InputElem().removeAttr("disabled");
				 }else{
					 this.options.columnProperty.showType = dyshowType.disabled;
					 this.get$InputElem().attr("disabled","disabled");  
				 } 
			 } ,
			 
			 
			 
			 setReadOnly:function(isReadOnly){
				 if(this.isShowAsLabel()){
					 return;
				 }
				 this.render(true);
				// this.setDisplayAsCtl(); 
				 if(isReadOnly){ 
					 this. options.columnProperty.showType = dyshowType.readonly;
					 this.get$InputElem().attr("readonly","readonly");  
					 
				 }else{
					 this. options.columnProperty.showType = dyshowType.edit;
					 this.get$InputElem().removeAttr("readonly");
				 }
				 this.render(); 
			 } ,
			 
			 isReadOnly:function(){
				 if(this.options.columnProperty.showType ==  dyshowType.readonly){
					  return true;
				 }
				 return false;
			 },
			 

		
			 
			 
			 isRequired:function(){
				 return $.ControlUtil.isRequired(this.options);
			 },
			 
			 
			 
			 isShowAsLabel:function(){
				 return this.options.isShowAsLabel;
			 },
			 
			 getAllOptions:function(){
			    	 return this.options;
			     } ,  
			     
		     getRule:function(){
				 //add by heshi 20141029 数字控件需增加校验,无法重新保存全部表单配置,此处暂时写死
				 if($.ControlUtil.getCheckRuleAndMsg(this.options)["rule"]==undefined
						 &&(this.options.columnProperty.dbDataType=="12"||
								 this.options.columnProperty.dbDataType=="13"||
								 this.options.columnProperty.dbDataType=="14"||
								 this.options.columnProperty.dbDataType=="15")){
					 var rule;
					 if(this.options.columnProperty.dbDataType=="12"){//双精度浮点数
						 rule = {
							isFloat: true,
							maxlength: '18'
						 };
					 }else if(this.options.columnProperty.dbDataType=="15"){//浮点数
						 rule = {
							isFloat: true,
							maxlength: '12'
						 };
					 }else if(this.options.columnProperty.dbDataType=="13"){//整数
						 rule = {
						    isInteger: true,
							maxlength: '9'
						 };
					 }else if(this.options.columnProperty.dbDataType=="14"){//长整数
						 rule = {
							isInteger: true,
							maxlength: '16'
						 };
					 }
					 return JSON.cStringify(rule);
				 }
				 return JSON.cStringify($.ControlUtil.getCheckRuleAndMsg(this.options)["rule"]);
			 } ,
			 
			 getMessage:function(){
				 if($.ControlUtil.getCheckRuleAndMsg(this.options)["msg"]==undefined
						 &&(this.options.columnProperty.dbDataType=="12"||
								 this.options.columnProperty.dbDataType=="13"||
								 this.options.columnProperty.dbDataType=="14"||
								 this.options.columnProperty.dbDataType=="15")){
					 var msg;
					 if(this.options.columnProperty.dbDataType=="12"){//双精度浮点数
						 msg = {
							isFloat: "  双精度浮点数",
							maxlength: "不得超过  {0} 个字符"
						 };
					 }else if(this.options.columnProperty.dbDataType=="15"){//浮点数
						 msg = {
							isFloat: "  浮点数",
							maxlength: "不得超过  {0} 个字符"
						 };
					 }else if(this.options.columnProperty.dbDataType=="13"){//整数
						 msg = {
						    isInteger: "  整数",
							maxlength: "不得超过  {0} 个字符"
						 };
					 }else if(this.options.columnProperty.dbDataType=="14"){//长整数
						 msg = {
							isInteger: "  长整数",
							maxlength: "不得超过  {0} 个字符"
						 };
					 }
					 return JSON.cStringify(msg);
				 }
				 return JSON.cStringify($.ControlUtil.getCheckRuleAndMsg(this.options)["msg"]);
			 } ,
			 
		     /**
		      * 获得控件名
		      * @returns
		      */
		     getCtlName:function(){
		    	 return this.options.columnProperty.controlName;
		     },
		     getFieldName:function(){
				 return this.options.columnProperty.columnName;
			 },

			   //bind函数，桥接
		     bind:function(eventname,event, custom){
		    	 	//if(this.$editableElem != null) 
		    	 	//this.$editableElem.bind(eventname,event); 
		    	 if(custom){
		    		 this.options[eventname] = event;
		    	 }else{
		    		 $("." + this.editableClass + "[name='" + this.getCtlName() + "']").live(eventname,event); 
		    	 }
		    	    
		    		return this;
		     },
			 
			 //unbind函数，桥接
		     unbind:function(eventname){
		    			 $("." + this.editableClass + "[name='" + this.getCtlName() + "']").die(eventname); 
			    	 	 
		    		return this;
		     },
		     
		     
		     /**
		      * 获得控件元素 指的是动态表单中生成的(_input\_textarea\_select+fieldname)的控件元素
		      * 如checkbox，radio元素组则是在此元素下面附加的元素.属性值仍然是放在(_input\_textarea\_select+fieldname)里面.
		      * @returns
		      */
			 getCtlElement:function(){
		    	 return this.get$InputElem();
			 }, 
			 
			 /**
			  * 将值渲染到页面元素上
			  */
			 render:function(isRender){
				 if(this.isShowAsLabel()){
					 this.setDisplayAsLabel();
				 }else{
					 if(typeof isRender == "undefined" || isRender == null || isRender === true){
						 this.setDisplayAsCtl();
					 }
					 
				 }
			 },
			 
			 
			 /**
				 * 获取输入框元素
				 */
				get$InputElem:function(){ 
					if( this.$editableElem == null){
						return $([]);//还没生成输入框时，先返回一个jquery对象
					}else{
						return this.$editableElem;
					}
				},
		     
		     /**
		      * 添加url超连接事件
		      */
		     addUrlClickEvent:function(urlClickEvent){
		    	 var onlyreadUrl=this.options.columnProperty.onlyreadUrl;
		    	 if(!(onlyreadUrl==""||onlyreadUrl==undefined)){
						if(this.isShowAsLabel()){
							var elment=this.$labelElem;
							elment.css("cursor","pointer");
							elment.css("color","#0000FF");
							elment.unbind("click", urlClickEvent);
							elment.bind("click",{a:onlyreadUrl},urlClickEvent); 
						}
					}
		     },

		     /**
	    	 * 设置需要分组验证的标识
	    	 */
	    	setValidateGroup:function(group){
	    		var element =this.$placeHolder;
	    		element.attr(group.groupUsed, group.groupName);
	    		
	    	},
	    	
	    	getValidator:function(element){
	    		var $form = element.parents("form"); 
	    		
	    		if($form.size() == 0){
	    			throw new Error("element must be nested in form html element");
	    		}
	    		
	    		var validator = $form.data("validator");
	    		if(typeof validator == "undefined" || validator == null){
	    			validator = $form.validate(Theme.validationRules ); 
	    			
	    			
	    			$form.data("validator", validator);
	    		 
	    		}else{
	    			 
	    		}
	    		 
	    		 return validator;
	    	},
	    	
	    	
	    	 /*参与校验的元素 */
			// getElementInvolvedValidator:function(){
				// if(this.$editableElem == null){
				//	 this.createEditableElem();
				// }
				// this.setValue2EditableElem();//将最新的值设置到被验证的元素中参与验证
			//	 return this.getInputElem();
			// },
	    	
	    	/*判断控件是不是在class为field的tr里面*/
	    	isControlInFieldTr:function(){
	    		if(this.$placeHolder.parents("tr[class='field']").size() == 0){
	    			return false;
	    		}else{
	    			return true;
	    		}
	    	},
	    	isFieldTrHide:function(){
	    		 
	    		if(this.$placeHolder.parents("tr[class='field']:hidden").size() == 0){
	    			return false;
	    		}else{
	    			return true;
	    		}
	    	},
	    	
	    	/**
	    	 * 验证控件的值
	    	 */
	    	validate:function(){
	    		 this.invoke("beforeValidate");
	    		
	    		if(!this.isVisible())return true;//隐藏不参与验证 ,直接验证通过
	    		
	    		//判断控件是否在class为field的tr中。如果控件在该类tr中，且tr为隐藏状态，这时不参与验证，直接验证通过
	    		if(this.isControlInFieldTr()){
	    			 if(this.isFieldTrHide()){
	    				 return true;
	    			 }
	    		}
	    		
	    		
	    		
	    		//如果显示为标签,则先显示为可编辑状态，然后进行验证
	    		
	    		//if(isLabel){
	    		//	this.setEditable();
	    		//}
	    		
	    		//var $inputElem = this.get$InputElem();
	    		var validator = this.getValidator(this.$placeHolder);
	    		
	    		var rule = this.getRule();
				var message =  this.getMessage();
				if(typeof rule == "undefined"){
					return true;
				}
				var ctlName = this.getCtlName();
				var ruleObj = {};
				var messageObj = {};
				///console.log(ctlName + ":" + rule);
				 
				 ruleObj=  eval("(" + rule +")");
				 messageObj = eval("(" + message +")");
				 var isLabel = false
				 if(this.objectLength(ruleObj)){//验证规则长度大于0的,则显示为可编辑
					 isLabel = this.isShowAsLabel();
					// console.log(ctlName + "...label:" + isLabel);
					 if(isLabel){
						// console.log(ctlName + "...label--->" );
						 this.setEditable(true);
						 
					 }
					 for(var i in groupUsed){//设置分组校验,例如从表中某列的唯一性
						 var groupVal = this.$placeHolder.attr(i);
						 if(typeof groupVal != "undefined" && $.trim(groupVal).length > 0){
							 this.get$InputElem().attr(i, groupVal);
						 }
					 }
					 if((this.isCheckBoxCtl() || this.isRadioCtl() ) &&  this.get$InputElem().not(":hidden").size() == 0){
						 //对于checkbox如果还是被隐藏了，则不进行验证
						 return true;
					 }
					 
					
				 }else{
					 return true;
				 }
				 
				 var fieldName = this.getFieldName();
				  
				// this.setCustomValidateRule.apply.call(this, ruleObj, messageObj, this.getDataUuid(), this., this.getFormDefinition().getName());
				this.setCustomValidateRule( ruleObj, messageObj, this.getDataUuid(), fieldName, this.getFormDefinition().name);
				
				validator.settings.rules[ctlName]  = ruleObj;
				
				
				
				validator.settings.messages[ctlName] = messageObj;
				//console.log(ctlName + "===...begin" );
				validator.findByName= function( name ) {
					var element = $(this.currentForm).find(".editableClass>[name='" + name + "']");
					if(element.size == 0){ 
						 element = $(this.currentForm).find(".editableClass[name='" + name + "']");
					} 
					return element;
				};
				var valid = validator.element(this.get$InputElem());
				//console.log(ctlName + "===" + valid);
				if(valid && isLabel){//验证完成之后,如果通过，还原为标签
					this.setDisplayAsLabel();
				}
				 this.invoke("afterValidate");
				
				return valid; 
	    	},
	    	objectLength: function( obj ) {
				var count = 0;
				for ( var i in obj ) {
					count++;
				}
				return count;
			},
	    	
	    	/**
			 * 设定自定义的校验规则
			 * @param ruleObj
			 * @param messageObj
			 * @param rowId
			 * @param fieldName
			 * @param control
			 */
			setCustomValidateRule:function(ruleObj, messageObj, dataUuid, fieldName, tblName){ 
				var _this = this;
				var newRuleObj = {};
				var newMessageObj = {};
				for(var i in ruleObj){
					//var ruleItem =  ruleObj[i];
					if(i == "unique"){//唯一性验证  
						newRuleObj["isUnique"] = {
								url : ctx + "/dyformdata/validate/exists",
		    					type : "POST",
		    					async: false,  
		    					data : {
			    					uuid:  dataUuid,
			    					tblName : tblName,//表单名称
			    					fieldName : fieldName, 
			    					fieldValue :  function(){
			    						if(_this.isValueMap()){
			    							value = JSON.cStringify(_this.getValueMap());
			    						}else{
			    							value = _this.getValue();
			    						}
			    						return value;
			    					}
		    					}
						};
						newMessageObj["isUnique"] = messageObj[i]; 
						delete ruleObj[i];//删除unique
					}
				}
				$.extend(ruleObj, newRuleObj);
				$.extend(messageObj, newMessageObj);
			},
			
			getControl: function(fieldName){
				
			},
			//设值到真实值显示值字段
			setToRealDisplayColumn:function(){ 
				
				if(!this.options.columnProperty.realDisplay)return;
				var value = this.getValue();
				var displayValue = this.getDisplayValue();
				var real = this.options.columnProperty.realDisplay.real;
				var display = this.options.columnProperty.realDisplay.display;
				 
				if(typeof real != "undefined" && real.length > 0 ){
					var control = $.ControlManager.getCtl(this.getContronId(real));
					if(typeof control == "undefined" || control == null  ){
						return;
					}
					control.setValue(value);
				}
				
				if(typeof display != "undefined" && display.length > 0 ){
					var control = $.ControlManager.getCtl(this.getContronId(display));
					if(typeof control == "undefined" || control == null  ){
						return;
					}
					control.setValue(displayValue);
				}
			},
			
			isInSubform:function(){
				if(this.getPos()==dyControlPos.subForm){//在从表中
					 return true;
				}else{
					return false;
				}
			},
			
			getContronId:function(fieldName){
				var dataUuid = this.options.columnProperty.dataUuid;
				if(this.getPos()==dyControlPos.subForm){//在从表中
					 return this.getCellId(dataUuid, fieldName);
				}
				return fieldName;
			},
			getCellId:function(rowId, fieldName){
				var id = rowId + "___" + fieldName; 
				return id;
			},
			
			/**
			 * 必输字段的备注前加*
			 */
			addMustMark:function(){
				if(this.getPos()==dyControlPos.subForm){//从表不用加
					return;
				}
				if(!this.isRequired()){//非必输
					return;
				}
				var $parentTd = this.$placeHolder.parent("td");
			   (function($parentTd){
				   window.setTimeout(function(){  
						if($parentTd.size() == 0){
							return ;
						}
						
						var $prevTd = $parentTd.prev("td");
						if($prevTd.size() == 0){
							return;
						}
						var requiredAttr = $prevTd.attr("required") ;
						if(typeof requiredAttr != "undefined"  ){
							return;
						}
						var mustRemark = "<font color='red' size='2'>*</font>";
					    if(	  ($prevTd.is(".Label") && $prevTd.html().length > 0) || $prevTd.find("label").size() > 0){
					    	
					       if($prevTd.is(".Label")){
					    	   var html = $prevTd.html() + mustRemark; 
						    	$prevTd.html(html);
					       }else{
					    	  var label = $prevTd.find("label").get($prevTd.find("label").size() -1);
					    	  var $label = $(label); 
					    	   $label.html($label.html() + mustRemark);
					       }
					       
					       $prevTd.attr("required", "required");
					    	
					    }else if($.trim($prevTd.html()).length > 0 && !($.trim($prevTd.html()).indexOf("<") != -1  && $.trim($prevTd.html()).indexOf("/>") != -1)){
					    	var html = $prevTd.html() + mustRemark; 
					    	$prevTd.html(html);
					    }
					  
				   }, 100);
			   })($parentTd);
			   
			},
			/**
			 * 判断是不是radio控件
			 */
			isRadioCtl:function(){ 
				if(this.getInputMode() == dyFormInputMode.radio){
					return true;
				}else{
					return false;
				}
			},
			getInputMode:function(){
				if(typeof this.options.commonProperty.inputMode == "undefined"){
					throw new Error(this.getCtlName() + " inputMode is undefined");
				}
				return this.options.commonProperty.inputMode;
			},
			/**
			 * 判断是不是checkbox控件
			 */
			isCheckBoxCtl:function(){
				if(this.getInputMode() == dyFormInputMode.checkbox){
					return true;
				}else{
					return false;
				}
			},
			/**
			 * 树形下拉框
			 * @returns {Boolean}
			 */
			isTreeSelectCtl:function(){
				if(this.options.commonProperty.inputMode == dyFormInputMode.treeSelect){
					return true;
				}else{
					return false;
				}
			},
			 
			isSelectCtl:function(){
				if(this.options.commonProperty.inputMode == dyFormInputMode.selectMutilFase){
					return true;
				}else{
					return false;
				}
			},
			isNumberCtl:function(){
				if(this.options.commonProperty.inputMode == dyFormInputMode.number){
					return true;
				}else{
					return false;
				}
			},
			isValueAsJsonCtl:function(){
				return this.isValueMap();
			} ,
			culateByFormula:function(){
				/*if(typeof this.options.ctlFormulas == "undefined"){
					return;
				}
				
				var formulas = this.options.ctlFormulas;
				for(var i = 0; i < formulas.length; i ++){
					formulas[i].call(this.dyform$Context(), this);
				}*/
				//alert(this.dyform$Context().cache);
				//var allformulas = this.cache.get.call(this, cacheType.formula);
				 
				$.ControlManager.culateByFormula(this);
				
			},
			/*在设置之后角发该事件*/
			 invoke:function(method){
				 if(this.options[method]){
					 this.options[method].apply(this,$.makeArray(arguments).slice(1));
				 }
			 },
			 dyform$Context:function(){
				// console.log("---" + this.$placeHolder.size());
				  return this.$placeHolder.parents(".dyform") ;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			 },
			 
			 /**
			  * 获取被选中的文本
			  */
			 getSelection:function(){ 
				 if(!this.$editableElem[0]){//编辑框还没展示出来
					 return null;
				 }
				 
				 if (document.selection) //IE
				    {
				       return ( document.selection.createRange().text);
				    }  else { 
				    	 return (this.$editableElem[0].value.substring(this.$editableElem[0].selectionStart, 
				    			 this.$editableElem[0].selectionEnd));
				    }  
			 },
			 /**
			  * 是否是进行选中操作
			  */
			 isSelection:function(){
				 var selectionVal = this.getSelection();
				 if(selectionVal == null || $.trim(selectionVal).length == 0){
					 return false;
				 }else{
					 return true;
				 }
			 }
			
			
			 
			
	};
	
	
})(jQuery);