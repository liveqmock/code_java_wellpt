;
(function($){
	$.wRadioCheckBoxCommonMethod={
			 
			
			 
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
			 } ,
			 
			 
			 getValueMapInOptionSet:function(value){ 
				 if(typeof value == "undefined" || value.length == 0){
					 return "";
				 }
				 var options = this.options;
					 var value1= null;
					if(value.indexOf(",") != -1){//值用,隔开 
						value1=value.split(",");
					}else{//值用;隔开
						value1=value.split(";");
					}
					var valueMap = {};
					 for(var i=0;i<value1.length;i++){
						if(this.isSingleCheck()){
							var selectobj = eval("("+options.singleCheckContent+")");
							var checked = false;
							for(attrbute in selectobj){
								if(attrbute == value1[i]){
									valueMap[attrbute] = selectobj[attrbute];
									checked = true;
									break;
								}
							}
							if(!checked){
								var selectobj = eval("("+options.singleUnCheckContent+")");
								for(attrbute in selectobj){
									if(attrbute == value1[i]){
										valueMap[attrbute] = selectobj[attrbute];
										break;
									}
								}
							}
						}else{
						
							 var optionSet = {};
								var selectobj = this.options.optionSet; 
									if(typeof selectobj == "undefined" || selectobj == null || (typeof selectobj == "string" && $.trim(selectobj).length == 0)){
										console.error( "a json parameter is null , used to initialize checkbox options ");
										return;
									}
									if(options.optionDataSource!=dyDataSourceType.dataConstant){//来自字典,这时optionSet为数组
										if(selectobj.length == 0){
											return;
										}else{
											for(var j = 0; j < selectobj.length; j ++ ){
												//console.log(selectobj[i].code);
												optionSet[selectobj[j].code] = selectobj[j].name;
											}
										}
									}else{
										optionSet = selectobj;
									}
									
									if(typeof optionSet =="object"){//为了兼容IE8,先通过JSON.cStringify进行排序后，再转换成对象 
										optionSet = eval("(" + JSON.cStringify(optionSet) + ")");
									}else if(typeof optionSet == "string"){
										try{
											optionSet = eval("(" +  (optionSet) + ")");
										}catch(e){
											console.error(optionSet + " -->not json format ");
											return;
										}
									}
								  
									for(attrbute in optionSet){ 
										if(attrbute == value1[i]){
											valueMap[attrbute] = optionSet[attrbute];
										}
									} 
									 
						}
					 }
					 if(typeof valueMap == "string"){
						return   valueMap  ;
					 }else{
						 return JSON.cStringify(valueMap) ;
					 }
					 
				 
			 },
			
			 
			 createEditableElem:function(){
				 if(this.$editableElem != null){//创建可编辑框
					 return ;
				 }
				 
				 var options = this.options;
				 var inputType = "";
				 if(this.isCheckBoxCtl()){
					 inputType = "checkbox";
				 }else if(this.isRadioCtl()){
					 inputType = "radio";
				 }else{
					 return;
				 }
				 
				 var ctlName =  this.getCtlName();
				 
				 
				 var editableElem = document.createElement("span");
				 editableElem.setAttribute("class", this.editableClass);
				 editableElem.setAttribute("name", ctlName);
				 $( editableElem).css(this.getTextInputCss());
				 this.$placeHolder.after($(editableElem));
				 this.$editableElem = this.$placeHolder.next("." + this.editableClass); 
			 
				 var opt = new Array(); 
				  	var labelstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,options.commonProperty.ctlWidth,options.commonProperty.ctlHight,'');
					var otherinputstyle=$.ControlUtil.getStyleHtmlByParams(options.commonProperty.textAlign,options.commonProperty.fontSize,options.commonProperty.fontColor,'120','');
					//单选模式
					if(this.isSingleCheck()){
						var selectobj = eval("("+options.singleCheckContent+")");
						for(attrbute in selectobj){
							var s="";
							 s = "<input type='" + inputType + "' name='"+ctlName+ "' id='" + ctlName + "_" + attrbute + "' value='" + attrbute + "'/>" +"<label "+labelstyle +"for='" +ctlName + "_" + attrbute+"'>"+selectobj[attrbute]+"</label>";
							 opt.push(s);
						}
						this.setCtlField();
					}
					//多选模式
					else  {
						 var optionSet = {};
						var selectobj = this.options.optionSet; 
					 
							if(typeof selectobj == "undefined" || selectobj == null || (typeof selectobj == "string" && $.trim(selectobj).length == 0)){
								console.error( "a json parameter is null , used to initialize checkbox options ");
								return;
							}
							if(options.optionDataSource!=dyDataSourceType.dataConstant){//来自字典,这时optionSet为数组
								if(selectobj.length == 0){
									return;
								}else{
									for(var i = 0; i < selectobj.length; i ++ ){
										//console.log(selectobj[i].code);
										optionSet[selectobj[i].code] = selectobj[i].name;
										//console.log(selectobj[i].code);
									}
								}
							}else{
								optionSet = selectobj;
							}
						 
							if(typeof optionSet =="object"){//为了兼容IE8,先通过JSON.cStringify进行排序后，再转换成对象 
								optionSet = eval("(" + JSON.cStringify(optionSet) + ")");
							}else if(typeof optionSet == "string"){
								try{
									optionSet = eval("(" +  (optionSet) + ")");
								}catch(e){
									console.error(optionSet + " -->not json format ");
									return;
								}
							}
							
							
							for(attrbute in optionSet){
								var s="";
								if(optionSet[attrbute]=='其他'){
									 s = "<input class='other_input' type='" + inputType + "' name='" +ctlName+ "' id='" + ctlName + "_" + attrbute + "' value='" + attrbute + "'/>" +"<label "+labelstyle +"for='" +ctlName + "_" + attrbute+"'>"+optionSet[attrbute]+"</label>"+"<input  id='other_input"+ctlName+ "_" + attrbute+"' "+otherinputstyle+" type='text' value='"+optionSet[attrbute]+"'  />";
								}else{
									 s = "<input type='" + inputType + "' name='" + ctlName+ "' id='" + ctlName + "_" + attrbute + "' value='" + attrbute + "'/>" +"<label "+labelstyle +"for='" +ctlName + "_" + attrbute+"'>"+optionSet[attrbute]+"</label>";
								}
								opt.push(s);
							}
					}
				 
					this.$editableElem.html(opt.join(''));
					var _this = this;
					$("input[id^=other_input]",  this.$editableElem[0] ).hide();
					$("input[id^=other_input]",  this.$editableElem[0] ).change(function(){ 
						_this.collectValue();
					}); 
					
					
					this.get$InputElem().click(function(){ 
						if($(this).is(".other_input")){//其他
							var $otherinput=$(this).next().next();
					    	   if($(this).prop("checked")== true){
				    			   $otherinput.show();
				    		   }else{
				    			   $otherinput.hide();
				    		   }
						}else{
							if(_this.isRadioCtl()){
									$("input[id^=other_input]",  _this.$editableElem[0] ).hide();
							} 
						}
						_this.collectValue();
						if(_this.isSingleCheck()){
							_this.setCtlField($(this));
						}
					});
			 },
			 /**
			  * 单选时,被选中时，将ctlField中的字段展示出来
			  */
			 setCtlField:function($this){
				 if(this.options.ctrlField != "" && this.options.ctrlField != null&& this.options.ctrlField != ",") { 
						var ctlField = this.options.ctrlField.split(",")[0]; 
						var fields = ctlField.split(";"); 
						for(var i = 0; i < fields.length; i ++){
							var field = fields[i];
							if(typeof $this == "undefined"){//不是通过click调用进来的,初始化时调用进来的 
								$(".value[name='"+field+"']").parents(".field").hide(); 
							 }else{
								 if($.ControlManager){
										var ctl = $.ControlManager.getCtl(field); 
										if(typeof ctl == "undefined" ||  ctl == null){
											console.log("无法找到控件" + field);
											continue;
										}
										  
										if($this.prop("checked")== true){
											ctl.setVisible(true);
										}else{ 
											ctl.setVisible(false);
										}
									}
							 }
							
						}
				} 
				 
			 },
			 /*从页面元素上收集map值 */
			 collectValue:function(){
			 
				 var value = {};
				 var _this = this;
				 var empty = true;
				 $("input[name='" + this.getCtlName() + "']:checked", this.$editableElem[0] ).each(function(){
					 if($(this).is(".other_input")){
						 value[$(this).val()] = $(this).next().next().val();
					 }else{
						 value[$(this).val()] = $(this).next().html(); 
					 }
					 empty = false;
				 });
				 if(!empty){
					 value = JSON.cStringify(value);
				 }else{
					 value = "";
					 if(this.isSingleCheck()){
						 value = this.options.singleUnCheckContent;
					 }
				 }
				 
				 this.setValueByMap(value);
				// console.log( this.value);
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
				 
				 var valueObj = {};
				if(this.value == null || $.trim(this.value).length == 0){  
				}else{
					valueObj = eval("(" +  this.value + ")");
				}
				// console.log("zzzzzzzzzz" + valueObj);
				var _this = this;
				this.get$InputElem().each(function(){
					var checked = false;
					 for(var i in valueObj){ 
						// console.log(i);
						 if($(this).val() == i){
							 if($(this).prop("checked") != true){
								 $(this).prop("checked", true);
							 }
							 checked = true;
							 break;
						 }
					 }
					 if(!checked){
						 $(this).prop("checked", false);
					 } 	 
					 
					 if($(this).is(".other_input") &&  $(this).prop("checked") == true){ 
						$(this).next().next().show();
						$(this).next().next().val(valueObj[$(this).val()]);
					 }
					 
					 //$("input[id^=other_input]",  this.$editableElem[0] )
				});
				 
				 
			 },
			 
			  
			isSingleCheck:function(){
				return this.options.selectMode=='1';
			},
			
			//set............................................................//
		     
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
					 if(this.get$InputElem().size() == 0){//还没生成可编辑框
						 valueMap = this.getValueMapInOptionSet(value);
						 this.setValueByMap(valueMap);
						 return;
					 }
					 for(var i=0;i<value1.length;i++){
						 //$("input[name='"+this.getCtlName()+"'][value='"+value1[i]+"']").prop("checked",true);
						 //1、从inputElem中取得对应的displayValue
						 //2、如果找不到inputElement时,从optionSet中取得对应的displayValue 
						 if(this.get$InputElem().size() == 0){//还没生成可编辑框
							 
								
						 }else{
							 if(this.isSingleCheck()){//如果是单选模式的 
					        	   if( this.get$InputElem().val() == value1[i]){
					        		   this.setValueByMap(this.options.singleCheckContent);
					        		  return;
					        	   }else{
					        		   this.setValueByMap(this.options.singleUnCheckContent);
					        		   return;
					        	   }
					           }else{
						           $("input[name='"+this.getCtlName()+"'][value='" + $.trim(value1[i]) + "']").each(function(){
						        	   var val=$(this).val();
						        	   var displayvalue=$(this).next().text();
						               if(displayvalue=='其他'){
						            	   displayvalue=$(this).next().next().val();
						               }
						               valueMap[val]=displayvalue;
						           });
					           }
						 }
					 }
					  this.setValueByMap(JSON.cStringify(valueMap));
				} 
			 } ,
			 
			 
			
			
		 
			 
			
			 
			 //返回控件真实值
			 getValue:function(){
				 var value = this.value;
				 if(value == null || $.trim(value).length == 0){
					 return "";
				 }
				 var valueObj = eval("(" + value+ ")");
				 var realValue = [];
				 for(var i in valueObj){
					 realValue.push(i);
				 }
				 
				 return	realValue.join(";");
			 },
			 

			 getDisplayValue:function(){ 
				 var value = this.value;
				 if(value == null || $.trim(value).length == 0){
					 if(this.isSingleCheck()){
						var selectobj = eval("("+this.options.singleUnCheckContent+")");
						for(var i in selectobj){
							return  selectobj[i];
						}
					 }
					 return "";
					
				 }
				 var valueObj = eval("(" + value+ ")");
				 var displayValue = [];
				 for(var i in valueObj){
					 displayValue.push(valueObj[i]);
				 }
				 
				 return	displayValue.join(";");
			 },
			 
			
			 /**
				 * 获取输入框元素
				 */
				get$InputElem:function(){ 
					if( this.$editableElem == null){
						return $([]);//还没生成输入框时，先返回一个jquery对象
					}else{
						return $("input[name='" + this.getCtlName() + "']", this.$editableElem[0] );
					}
				},

			 
			 isValueMap:function(){ 
				 return true;
			 } ,
			 setReadOnly:function(isReadOnly){ 
				 if(this.options.isShowAsLabel){
					 return;
				 }
				 this.render();
				
				 if(isReadOnly){
					 this. options.columnProperty.showType = dyshowType.readonly;
					 this.get$InputElem().attr("disabled","disabled");  
					 $("#other_input" + this.getCtlName() , this.$editableElem[0]).attr("readonly", "readonly");
				 }else{
					 this. options.columnProperty.showType = dyshowType.edit;
					 this.get$InputElem().removeAttr("disabled");
					 $("#other_input" + this.getCtlName() , this.$editableElem[0]).removeAttr("readonly"); 
				 }
				 
			 } ,
		     
	}
	
	
})(jQuery);