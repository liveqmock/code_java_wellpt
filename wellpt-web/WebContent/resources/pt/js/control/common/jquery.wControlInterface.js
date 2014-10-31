/**
 * 控件公共接口方法
 */
$(function(){
	
	$.wControlInterface={
			
		   init : function() {
			    //公共属性设置
				 $.ControlUtil.setCommonCtrAttr(this.$element,this.options);
				 
				 //初始化私有参数
				 this.initSelf();
				 
				/* var  formDefinition = this.options.formDefinition ;
				 var fieldDefinition = this.options.fieldDefinition ;*/
					 
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
	    		return {
        			"width": this.options.commonProperty.ctlWidth+"px",
        			"height": this.options.commonProperty.ctlHight+"px",
        			"text-align":this.options.commonProperty.textAlign,
        			"font-size":this.options.commonProperty.fontSize+"px",
        			"color":this.options.commonProperty.fontColor,
        		   };
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
				elment=this.$element;
				var showType=this.options.columnProperty.showType;
				 if(showType==dyshowType.showAsLabel){
					 this.setDisplayAsLabel();
				 }else if(showType==dyshowType.readonly){
					 this.setReadOnly(true);
				 }else if(showType==dyshowType.disabled){
					 this.setEnable(false);
				 }else if(showType==dyshowType.hide){
					 this.setVisible(false);
				 }
			},
			
			/**
			 * 初始化私有参数(各控件内部实现)
			 */
			initSelf:function(){
				
			},
			
			//设值
			 setValue:function(value){
				 
				 //(如果是显示为lable，则label也得更新)
				 this.$element.val(value);
				
				 //_$("#applyTo").comboTree("initValue", field.applyTo);
				// this.$element.comboTree("initValue", value);
		    	  if(this.options.isShowAsLabel==true){
		    		  this.$element.next().html(value);
		    	  }
		    		 
		    	 // this.$element.comboTree("initValue", value);
			 } ,
			 
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
				 $.ControlUtil.setDisplayAsLabel(this.$element,this.options);
			 } ,
			 
			 //显示为控件
			 setDisplayAsCtl:function(){
				 $.ControlUtil.setDisplayAsCtl(this.$element,this.options);
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
				 if(this.options.disabled){
					 return false;
				 }else{
					 return true;
				 }
			 },
			 
			
			 
			 isEnable:function(){
				 return !this.options.disabled;
			 },
			 clean: function( selector ) {
					return $(selector)[0];
			 },
			 
			 
			 isVisible:function(){
				 
				 var element = this.clean(this.getCtlElement());
                 if(!element){//找不到该元素，默认为隐藏
                	 return false;
                 }
				 if((/radio|checkbox/i).test(element.type)){
					 
					// if radio/checkbox, validate first element in group instead
					var size =  this.findByName( element.name ).not(":hidden").size();
					 if(size == 0){
						 return false;
					 }
				 };
				 
				 return  !this.options.isHide;
			 }, 
			 
			 findByName: function( name ) {
					return 	  this.$element.parents("form").find("[name='" + name + "']");
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
				 return JSON.cStringify($.ControlUtil.getCheckRuleAndMsg(this.options)["rule"]);
			 } ,
			 
			 getMessage:function(){
				 return JSON.cStringify($.ControlUtil.getCheckRuleAndMsg(this.options)["msg"]);
			 } ,
			 
		     /**
		      * 获得控件名
		      * @returns
		      */
		     getCtlName:function(){
		    	 return this.$element.attr("name");
		     },
		     getFieldName:function(){
				 return this.options.columnProperty.columnName;
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
		     },
		     
		     /**
		      * 获得控件元素 指的是动态表单中生成的(_input\_textarea\_select+fieldname)的控件元素
		      * 如checkbox，radio元素组则是在此元素下面附加的元素.属性值仍然是放在(_input\_textarea\_select+fieldname)里面.
		      * @returns
		      */
		     getCtlElement:function(){
		    	 return this.$element;
		     },
		     
		     
		     /**
		      * 添加url超连接事件
		      */
		     addUrlClickEvent:function(urlClickEvent){
		    	 var onlyreadUrl=this.options.columnProperty.onlyreadUrl;
					if(!(onlyreadUrl==""||onlyreadUrl==undefined)){
						if(this.isShowAsLabel()){
							var elment=this.getCtlElement();
							elment.next("span").css("cursor","pointer");
							elment.next("span").css("color","#0000FF");
							elment.next("span").bind("click",{a:onlyreadUrl,},urlClickEvent);
						}
					}
		     },

		     /**
	    	 * 设置需要分组验证的标识
	    	 */
	    	setValidateGroup:function(group){
	    		var element = this.$element;
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
	    	
	    	/**
	    	 * 验证控件的值
	    	 */
	    	validate:function(){ 
	    		if(!this.isVisible())return true;//隐藏不验证
	    		var element = this.$element;  
	    		var validator = this.getValidator(element);
	    		
	    		var rule = this.getRule();
				var message =  this.getMessage();
				var ctlName = this.getCtlName();
				var ruleObj = {};
				var messageObj = {};
				
				 ruleObj=  eval("(" + rule +")");
				 messageObj = eval("(" + message +")");
				 
				 var fieldName = this.getFieldName();
				  
				 
				//$.ControlUtil.setCustomValidateRule.apply.call(this, ruleObj, messageObj, this.getDataUuid(), this., this.getFormDefinition().getName());
				this.setCustomValidateRule( ruleObj, messageObj, this.getDataUuid(), fieldName, this.getFormDefinition().name);
				
				validator.settings.rules[ctlName]  = ruleObj;
				
				validator.settings.messages[ctlName] = messageObj;
				
				var isLabel = this.isShowAsLabel();
				
				if(isLabel){
					this.setEditable();
				}
				//var selector = "input[name='" + ctlName + "']";
				
				
				var valid = validator.element(this.getCtlElement()) ;
				
				if(valid && isLabel){//验证完成之后,如果通过，还原为标签
					this.setDisplayAsLabel();
				}
				return valid;
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
					var control = $.ControlManager.getControl(this.getContronId(real));
					if(typeof control == "undefined" || control == null  ){
						return;
					}
					control.setValue(value);
				}
				
				if(typeof display != "undefined" && display.length > 0 ){
					var control = $.ControlManager.getControl(this.getContronId(display));
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
				if(!this.isRequired()){//非必输
					return;
				}
			 
				var $parentTd = this.$element.parent("td[class='value']");
				if($parentTd.size() == 0){
					return ;
				}
				 
				var $prevTd = $parentTd.prev("td");
				if($prevTd.size() == 0){
					return;
				}
				 
			    if(	  $prevTd.is(".Label") && $prevTd.html().length > 0){ 
			    	$prevTd.html($prevTd.html() + "<font color='red' size='2'>*</font>");
			    }
			  
			},
			/**
			 * 判断是不是radio控件
			 */
			isRadioCtl:function(){ 
				if(this.options.commonProperty.inputMode == dyFormInputMode.radio){
					return true;
				}else{
					return false;
				}
			},
			/**
			 * 判断是不是checkbox控件
			 */
			isCheckBoxCtl:function(){
				if(this.options.commonProperty.inputMode == dyFormInputMode.checkbox){
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
			/**
			 * 判断是不是下拉框控件
			 */
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
				if(this.isRadioCtl() || this.isCheckBoxCtl() || this.isTreeSelectCtl() || this.isSelectCtl()){
					return true;
				}else{
					return false;
				}
			}
			
			
			 
			
	};
	
	
});