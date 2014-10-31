;
(function($) {
	
	$.ControlConfigUtil = {
		  	/**
	     	 * 控件初始化设置
	     	 */
			
	     	ctlPropertyComInitSet:function (field){
	     		//初始化应用于 
	     		var _$ = this.$;
	     		this.initApplyToTree(_$("#applyTo"));
	     		 
	     		_$("#applyTo").wcomboTree("getObject").setValue(field.applyTo);
	     		//console.log(JSON.cStringify(field.applyTo));
	     		_$("#applyTo").comboTree("initValue", field.applyTo);
	     		
	     		_$("#name").val(field.name);
	     		_$("#name").attr("oldName", field.oldName);
	     		_$("#displayName").val(field.displayName);
	     		_$("#dbDataType").val(field.dbDataType);
	     		selColor(field.fontColor);
	     		_$("#fontSize").val(field.fontSize);
	     		_$("#length").val(field.length);
	     		_$("#ctlWidth").val(field.ctlWidth);
	     		_$("#ctlHight").val(field.ctlHight);
	     		_$("#textAlign").val(field.textAlign);
	     		_$("#defaultValue").val(field.defaultValue);
	     		_$("#onlyreadUrl").val(field.onlyreadUrl);  
	     		
	     		_$("#realDisplay_display").empty();
	     		_$("#realDisplay_real").empty();
	     		_$("#realDisplay_display").append("<option value=\"\">--请选择--</option>");
	     		_$("#realDisplay_real").append("<option value=\"\">--请选择--</option>");
	     		 
	     		if(  formDefinition.isInputModeAsValueMap(field.inputMode) ){
	     			 //字段有真实值显示值
	     			for(var i in formDefinition.fields){
	     				var fieldt =  formDefinition.fields[i];
	     				 
	     				if(!formDefinition.isCustomField(fieldt.name) || fieldt.name == field.name ||  !formDefinition.isInputModeAsText(fieldt.inputMode)){
	     					continue;
	     				} 
	     				_$("#realDisplay_display").append("<option value=\"" + fieldt.name + "\">" +fieldt.displayName  + "</option>");
	     				_$("#realDisplay_real").append("<option value=\"" + fieldt.name + "\">" + fieldt.displayName + "</option>");
	     				
	     			}
	     		
	     			var realDisplay = field.realDisplay;
	     		 
		     		if(typeof realDisplay != "undefined"){
		     			var realFieldName = realDisplay.real;
	     				var displayFieldName =  realDisplay.display;
	     				_$("#realDisplay_display").val(displayFieldName);
	     				_$("#realDisplay_real").val(realFieldName); 
		     		}
	     		}else{
	     			//_$("#realDisplay_display").hide();
     				//_$("#realDisplay_real").hide();
	     		}
	     		 
	     	
	     		
	     		
	     		
	     		
	     		
	     		//设置约束条件
	     		if((typeof field.fieldCheckRules) != "undefined" 
	     				&& field.fieldCheckRules != null){
	     			var fieldCheckRules =  field.fieldCheckRules;
	     			for(var i = 0; i < fieldCheckRules.length ; i++){
	     				var fieldCheckRule = fieldCheckRules[i];
	     				_$("input[name='fieldCheckRules'][value='" + fieldCheckRule.value + "']").each(function(){
	     					this.checked = true;
	     				});
	     			}
	     		}
	     		_$("input[name='showType'][value='" + field.showType + "']").each(function(){
	     			this.checked = true;
	     		});
	     		//如果field已存在，则name不可编辑
	     		if(field.name!=""&&field.name!=undefined){
	     			//_$("#name").attr("readonly","readonly");
	     		}else{
	     			_$("#name").removeAttr("readonly");
	     		}
	     		 
	     		this.setDbDataType(field.dbDataType);
	     	},
	     	setDbDataType:function(dbDataType){
	     		var _this = this;
	     		
	     		if(this.$("#dbDataType").size() == 0 ){
	     			return;
	     		}
	     		if(this.$("#dbDataType").find("option").length>0){
	     			this.$("#dbDataType").val(dbDataType);
	     			return;
	     		}
	     		for(var type in dyFormInputTypeObj){
	     			var obj =  dyFormInputTypeObj[type]; 
	     			this.$("#dbDataType").append("<option value='" + obj.code + "'>" + obj.name + "</option>");
	     		}
	     		this.$("#dbDataType").change(function(){
	     			if($(this).val() == dyFormInputType._clob){//大字段,不需要设置长度
	     				_this.$("#length").parents("tr").first().hide();
	     			}else{
	     				_this.$("#length").parents("tr").first().show();
	     			}
	     		});
	     		if(typeof dbDataType == "undefined"){
	     			return;
	     		}
	     		this.$("#dbDataType").val(dbDataType);
	     		this.$("#dbDataType").trigger("change");
	     		
	     	}, 
	     	getDbDataType:function(){
	     		if(this.$("#dbDataType").size() == 0 ){
	     			return null;
	     		}
	     		return this.$("#dbDataType").val();
	     		
	     	},
	     	
	     	
	     	/**
			 * 控件属性编辑对话框onshow
			 * @param _editor
			 * @param _this
			 * @param ctlname
			 */
			ctlEditDialogonShow:function(_editor,_this,ctlname){
				if(_editor==undefined){
	            	return ;
	            }
				 
	         	//判断光标焦点位置是不是在从表中，若在其他从表中则不允许插入新的从表
	         	var selection = _editor.getSelection();
	         	 var selected_ranges = selection.getRanges(); // getting ranges
	         	 var node = selected_ranges[0].startContainer; // selecting the starting node
	              
	         	  var parents = node.getParents(true);
	         	  var parentsLength = parents.length;
	         	  if(parentsLength > 3){ 
		    			  var fieldClazzElement = null; 
		        		  for(var i = parentsLength - 1; i > -1 ; i --){
		        			  var parent = parents[i];
		        			  if(parent.type != 3 && parent.getName() == "tr"){
		        				  fieldClazzElement = parent; //获取包括焦点的最内层的tr,该tr的class将被设置成"field"
		        			  }else if(parent.type != 3 ){
		        				  valueClassElement = parent; 
		        			  } 
		        		  }
		        		  editor.fieldClazzElement = fieldClazzElement;
		        		 /* if(fieldClazzElement == null){
		        			  _this.hide();
		        			  alert("请将您的光标移至布局表格中!!");
		        			  return;
		        		  }else{
		        			  editor.fieldClazzElement = fieldClazzElement;
		        		  }*/
	         	  }else{ 
	         		 _this.hide();
	         		  alert("在插件"+ctlname+"控件之前，请先添加布局表格!!");
	         	  }
	         },
	     	
	         
	         /**
	 		 * 控件属性编辑对话框ok事件
	 		 * @param ctl
	 		 * @param containerID
	 		 * @returns {Boolean}
	 		 */
	 		ctlEditDialogOnOk:function(ctl,containerID){
	 			var checkpass=ctl.collectFormAndFillCkeditor();  
	         	if(!checkpass){
	         		return false;
	         	} 
	         	ctl.exitDialog();
	            	window.setTimeout(function(){ 
	         		 $("#"+containerID).empty();//重新初始化属性窗口 
	 			 }, 100);
	            	return true;
	 		},
	 		
	 		/**
	 		 * 控件属性编辑对话框退出事件
	 		 * @param ctl
	 		 * @param containerID
	 		 */
	 		ctlEditDialogOnCancel:function(ctl,containerID){
	 			if( (typeof ctl.exitDialog) != "undefined"){
	 				ctl.exitDialog();
	         		window.setTimeout(function(){
	            		 $("#"+containerID).empty();//重新初始化属性窗口 
	 				 }, 100);
	         	}
	 		},
	 		
	 		 /**
			  * 初始化applytotree
			  */
			initApplyToTree:function(elment){
				 elment.wcomboTree({
			 		 treewidth: 300,
			 		 treeheight: 220,
			 		 serviceName:"dataDictionaryService.getFromTypeAsTreeAsync('DY_FORM_FIELD_MAPPING')",
			 		 initService : "dataDictionaryService.getKeyValuePair",
			 		 initServiceParam : [ "DY_FORM_FIELD_MAPPING" ],
			 		 mutiselect:true,
			 		 isinitTreeById:true
			 	 }); 
				 elment.addClass("input-tier");
				 
				/* 
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
					
					this.$element.comboTree({
						labelField : colEnName,
						valueField : "_"+colEnName,
						width: options.treeWidth,
						height: options.treeHeight,
						treeSetting : setting,
						initService :options.initService,
						initServiceParam : options.initServiceParam
					});
					*/
			 },
			 
			 
			 
			/**
			 * 收集控件一些公共属性
			 * @param field
			 */
			collectFormCtlComProperty:function(field){
				var _$ =  this.$;
	     		 
				var checkpass=true;
				field.name = _$("#name").val();
				field.oldName = _$("#name").attr("oldName"); 
				field.displayName = _$("#displayName").val();
				
				if(field.name==""||field.name==undefined){
					alert(dymsg.fieldNameNotEmpty);
					return false;
				}
				
				if(field.displayName==""||field.displayName==undefined){
					alert(dymsg.displayNameNotEmpty);
					return false;
				}
				if(preservedFields.is($.trim(field.name))){//字段名是否与预留字段重复
					alert(dymsg.preservedField);
					return false;
				}
				
				
				
				
				field.valueCreateMethod = "1";
				field.applyTo=_$("#applyTo").wcomboTree("getObject").getValue();
				field.fontColor = _$("#fontColor").val();
				field.fontSize = _$("#fontSize").val();
				field.ctlWidth = _$("#ctlWidth").val();
				field.ctlHight = _$("#ctlHight").val();
				field.textAlign = _$("#textAlign").val();
				field.showType = _$("input[name='showType']:checked").val();
				field.defaultValue = _$("#defaultValue").val();
				field.onlyreadUrl = _$("#onlyreadUrl").val(); 
				if(_$("#length").size()>0){
					field.length = _$("#length").val(); 
				}
				if(_$("#dbDataType").size()>0){
					field.dbDataType = _$("#dbDataType").val(); 
				}
				_$("input[name='fieldCheckRules']:checked").each(function(){
					if(this.checked){
						field.fieldCheckRules.push({value:$(this).val(), label:$(this).next().text()});
					}
				});
				if(typeof field.oldName != "undefined" && field.name != field.oldName){
					delete formDefinition.fields[field.oldName];
				}
				
				 
				if(  formDefinition.isInputModeAsValueMap(field.inputMode) ){
					 //字段有真实值显示值
					field.realDisplay = {};
				 
					field.realDisplay.real = _$("#realDisplay_real").val();
					field.realDisplay.display = _$("#realDisplay_display").val(); 
	     		}
	     		 
				var dbDataType = this.getDbDataType();
				if(dbDataType != null){
					field.dbDataType = dbDataType;
				}
				
				return checkpass;
			},

	     	
			/**
			 * 初始化数据字典字树
			 */
			initDictCode:function(){
				 var ctrlFieldSetting = {
						 async : {
						 otherParam : {
						 "serviceName" : "dataDictionaryService",
						 "methodName" : "getAsTreeAsyncForControl",
						 }
						 },
						 check: {
								enable: false
							},
						 callback : {
							 onClick:treeNodeOnClickForctrlFieldSetting,
							 beforeClick: zTreeBeforeClick
						 }
						 };
						 $("#dictName").comboTree({
							 labelField : "dictName",
							 valueField : "dictCode",
							 treeSetting : ctrlFieldSetting,
							 width: 220,
							 height: 220
						 });
						 function treeNodeOnClickForctrlFieldSetting(event, treeId, treeNode) {
							 $("#dictName").val(getAbsolutePath(treeNode));
							 $("#dictCode").val(treeNode.data+':'+getAbsolutePath(treeNode));
						 }
						 function zTreeBeforeClick(treeId, treeNode, clickFlag) {
							    return treeNode.isParent;//当是父节点 返回false 不让选取
							}
							// 获取树结点的绝对路径
						function getAbsolutePath(treeNode) {
								var path = treeNode.name;
								var parentNode = treeNode.getParentNode();
								while (parentNode != null) {
									path = parentNode.name + "/" + path;
									parentNode = parentNode.getParentNode();
								}
								return path;
							}
						$("#dictName").addClass("input-tier");
			},
			
			/**
			 * 初始化数据源
			 */
			initDataSource:function(){
				 var ctrlFieldSetting = {
						 async : {
						 otherParam : {
						 "serviceName" : "dyFormDefinitionService",
						 "methodName" : "getAsTreeAsyncForControl",
						 }
						 },
						 check: {
								enable: false
							},
						 callback : {
							 onClick:treeNodeOnClickForctrlFieldSetting,
							 beforeClick: zTreeBeforeClick
						 }
					};
						 $("#dictName").comboTree({
							 labelField : "dictName",
							 valueField : "dictCode",
							 treeSetting : ctrlFieldSetting,
							 width: 220,
							 height: 220
						 });
						 function treeNodeOnClickForctrlFieldSetting(event, treeId, treeNode) {
							 $("#dictName").val(getAbsolutePath(treeNode));
							 $("#dictCode").val(treeNode.data+':'+getAbsolutePath(treeNode));
						 }
						 function zTreeBeforeClick(treeId, treeNode, clickFlag) {
							    return treeNode.isParent;//当是父节点 返回false 不让选取
							}
							// 获取树结点的绝对路径
						function getAbsolutePath(treeNode) {
								var path = treeNode.name;
								var parentNode = treeNode.getParentNode();
								while (parentNode != null) {
									path = parentNode.name + "/" + path;
									parentNode = parentNode.getParentNode();
								}
								return path;
							}
						$("#dictName").addClass("input-tier");
			},
			
			/**
			 * 确定时生成控件占位符
			 * @param _this
			 * @param imgsrc
			 * @param inputMode
			 */
			createControlPlaceHolder:function(_this,imgSrc,field){
				var inputMode=field.inputMode;
				var ctlHtml = "<img  class='value' inputMode='" + inputMode + "' name='" + field.name + "' title='" + field.displayName +"' src='" + imgSrc + "'/>";
				 
				$(_this.editor.fieldClazzElement).attr("class", "field");
				 
				var element = CKEDITOR.dom.element.createFromHtml( ctlHtml );
				if(_this.editor.focusedDom != null){
					element.insertBefore(_this.editor.focusedDom);
					_this.editor.focusedDom.remove(); 
				}else{
					_this.editor.insertElement( element );
				} 
			}
			
};
	

	
})(jQuery);