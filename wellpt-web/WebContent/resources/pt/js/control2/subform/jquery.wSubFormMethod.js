/**
 * 从表操作的一些方法
 * 增删行、更新行操作等
 */
$(function(){
	
	$.wSubFormMethod={
			/**
			 * 从表行数据对话框
			 * @param subform
			 * @param data
			 * @param parentData
			 */
			editSubformRowDataInDialog:function(subform, data, parentData){
				//点击添加按钮，然后在新窗口中编辑数据，点击确定之后在jqgrid中为从表添加一行
				//同时再将数据填充至该行中
				var _parentthis=this.options.$paranentelement;
				var formUuid = subform.formUuid;
				var formDefinition =this.options.subformDefinition;
				var colModels = _parentthis.$("#" + formUuid).jqGrid('getGridParam','colModel');
				var isAdd = false;
				var uuid = data["uuid"] ;
			 
				if(typeof data == "undefined" || data == null || typeof uuid == "undefined"){
					isAdd = true;
					//添加行
					uuid = this.createUuid();
					data["id"] = uuid;
					data["newRow"]=true;
					var formData = this.loadDefaultFormData(formUuid);
					for(var j = 0 ; j < colModels.length; j ++){
						var model = colModels[j];
						var fieldName = model.name;
						var value = formData[fieldName];
						if(typeof value != "undefined" && value != null){
							data[fieldName] = formData[fieldName];
						}
					}
					if(typeof parentData != "undefined"){
						data["parent_uuid"] = parentData["uuid"];
					}
					
				}else{ 
					data["id"] = uuid;
				}
				
				
				//定义窗口内容 
				$("#dialog" + uuid).remove();
				_parentthis.append("<div id='dialog" + uuid + "' title=" +  subform.displayName+ "("+dybtn.add+")></div>"); 
				
				//this.$("#" + uuid);
				var html ="<div class='dyform'>";
				html += "<table></table>";
				_parentthis.$("#dialog" + uuid).append(html);
				
				var fields = subform.fields; 
				var controlInfo = {};
				for(var j = 0 ; j < colModels.length; j ++){
					var model = colModels[j]; 
					var fieldName = model.name;
					if(!this.isControlFieldInSubform(fieldName)){
						continue;
					}
					var field = fields[fieldName];
					var displayName = field.displayName;
					var controlId = this.getCellId(uuid + "_", fieldName);
				 
					var h = "";
					h += "<tr><td >";
					h += displayName;
					h += "</td>";
					h += "<td>";
					h += "<img class='value' name='" + controlId +  "'/>";
					h += "</td></tr>";
					_parentthis.$("#dialog" + uuid + " .dyform").find("table").append(h);
					
					var fieldDefinition = formDefinition.fields[fieldName];
					
					
					/*var fieldDefinitionCopy = {}; 
					$.extend(fieldDefinitionCopy, fieldDefinition);
					
					//设置显示值真实值字段
					var realDisplay = fieldDefinitionCopy.realDisplay;
					if(realDisplay){
						var real = realDisplay.real;
						var display = realDisplay.display;
						if(typeof real != "undefined" && real.length > 0 ){
							realDisplay.real = this.getCellId(uuid + "_", real);
						}
						if(typeof display != "undefined" && display.length > 0 ){
							realDisplay.display = this.getCellId(uuid + "_", display);
						}
					}
					fieldDefinitionCopy.realDisplay = realDisplay;
					
					 $.ControlManager.createCtl(controlId, fieldDefinitionCopy, formDefinition);*/
				     
					 $.ControlManager.createCtl(_parentthis.$("img[name='" + controlId + "']"), fieldDefinition, formDefinition);
					 var control =  $.ControlManager.getCtl(controlId);
					 controlInfo[fieldName] = control;
					 var value = data[fieldName];
					 control.setPos(dyControlPos.subForm);
					 control.setDataUuid(uuid);
					 if(typeof value == "undefined"){
						 continue;
					 }
				 
					 this.setValue(control, value, uuid); 
					 control.setEditable(true);
				}
				
				var _this = this;
				 var buttons = {};
				buttons[dybtn.save] = function(){
					for(var j = 0 ; j < colModels.length; j ++){
						var model = colModels[j]; 
						var fieldName = model.name;
						if(!_this.isControlFieldInSubform(fieldName)){
							continue;
						}  
						if(controlInfo[fieldName]!=undefined){
						 data[fieldName] =  _this.getValue(controlInfo[fieldName]); 
						}
						 
					}
					if(isAdd){ 
						_this.addRowData( formUuid, data);
					}else{ 
						_this.updateRowData( formUuid, data); 
					}
					$(this).dialog("close"); 
					$("#dialog" + uuid).remove();
				};
				
				buttons[dybtn.cancel] = function(){
					$(this).dialog("close");
					$("#dialog" + uuid).remove();
				};
				
				//显示窗口
				_parentthis.$("#dialog" + uuid ).dialog( {
							buttons:buttons, 
							height: 380,
							width: 700,
							modal: true 
				});
			}, 
			
			/**
			 * 根据fieldDefinition获得gridmodel
			 * @param fieldDefinition
			 */
			getJqGridColModel:function(fieldDefinition){
			 
				var field = fieldDefinition;
				var model = {};
				/*if(fieldDefinition.name == "status"){
					fieldDefinition.showType = dyshowType.showAsLabel; 
				}*/
				model.name = field.name;
				model.index = field.name;
				//model.width =  "50%";
				//model.edittype = "text";
				//model.editrules//检验规则 
				model.edittype = "custom";
				
				var _this = this;
				
				model.formatter = function(cellvalue, options, rowObject){
					var id = _this.getCellId(options.rowId, options.colModel.name);  
					/*var parentElement =  document.createElement("span"); 
					var element = document.createElement("span");
					var id = _this.getCellId(options.rowId, options.colModel.name);  
					element.setAttribute("id", id);  
					parentElement.appendChild(element);
					$(element).append("<input  style='display:none' name='" + id + "'/>");*/
					 
					 
					 
					return "<img class='value' style='display:none' name='" + id + "' pos='" + dyControlPos.subForm + "'/>";
				};
			
				
				return model;
				
			},   //bind函数，桥接
		     bind:function(eventname,event){
	    		this.options[eventname] = event;
	    		return this;
		     },
		     /*在设置之后角发该事件*/
			 invoke:function(method){
				 if(this.options[method]){
					 this.options[method].apply(this,$.makeArray(arguments).slice(1));
				 }
			 },
			
			/**
			 * 添加行控件的url事件.
			 */
			addControlUrl:function(cellControl,rowId){
				var _this=this;
				 
                var urlClickEvent=function(event) {
					var reg=new RegExp(/\$\{([^\}]*)\}/g);
					var url = event.data.a;
					var tempValueArray = url.match(reg);
					for(var k=0;k<tempValueArray.length;k++) {
						var tempvariable = tempValueArray[k].replace("${", "").replace("}", "");
						var cellctl=_this.getCellId(rowId, tempvariable);
						var control=$.ControlManager.getCtl(cellctl);
						if(control!=undefined){
							url = url.replace(tempValueArray[k],control.getValue());
						}else if(tempvariable=='datarowid'){
							url = url.replace(tempValueArray[k],rowId);
						}
					}
					window.open(ctx+url);
				};
				 //添加url点击事件
				cellControl.addUrlClickEvent(urlClickEvent);
		},
			
			
			/**
			 *置于jqgrid中，从表的各cell的id 
			 **/
			getCellId:function(rowId, fieldName){
				var id = rowId + "___" + fieldName; 
				return id;
			},
			
			/**
			 * 根据表单定义Id从表单定义Json中获取从表定义
			 */
			getSubformDefinitionByFormUuid:function(formDefinition, formUuid){
				var subforms = formDefinition.subforms;
				var thisSubform = null;
				for(var i in subforms){
					var subform = subforms[i];
					if(subform.formUuid == formUuid){
						thisSubform = subform;
						break;
					}
				}
				return thisSubform;
			},
			
			
			/**
			 * 在从表中的字段是不是控件字段
			 * @param fieldName
			 */
			isControlFieldInSubform:function(fieldName){
				if(fieldName == "seqNO" || fieldName == "cb" 
					|| fieldName == "id" 
						|| fieldName == "uuid"
							|| fieldName == "parent_uuid"
								|| fieldName == "level"
									|| fieldName == "isLeaf"
										|| fieldName == "expanded"
											|| fieldName == "loaded"
												|| fieldName == "parent"
													|| fieldName == "icon"
				){
					return false;
				}else{
					return true;
				}
			},
			
			
			/**
			 * JqGrid行数据增加
			 * @param formUuid
			 * @param parentData
			 */
			addSubformRowDataInJqGrid:function(formUuid, parentData){ //点击添加从表行数据的按钮事件 
				var colModels = this.options.$paranentelement.$("#" + formUuid).jqGrid('getGridParam','colModel');
				var uuid = this.createUuid();
				var data = {};
				data["id"] = uuid; 
				data["newRow"] = true; //新增的行
				if(typeof parentData != "undefined"){
					data["parent_uuid"] = parentData["uuid"];
				}
				
				var formData = this.loadDefaultFormData(formUuid);
				for(var j = 0 ; j < colModels.length; j ++){
					var model = colModels[j];
					var fieldName = model.name;
					var value = formData[fieldName];
					if(typeof value != "undefined" && value != null){
						data[fieldName] = formData[fieldName];
					}	
				}
				this.addRowData( formUuid, data);
			}, 
			
			
		  	/**
		   	 * 初始化行控件
		   	 * 从行新增挪到了此处.
		   	 * @param rowId
		   	 * @param rowdata
		   	 */
		   	initRowControls:function(formUuid,rowId,rowdata){
		   		 
		   		var _this=this;
				var subFormDefinition=this.options.subformDefinition;
				//var subform = this.options.formDefinition.subforms[formUuid];
				//此处初始化控件
			   	var colModels = this.options.$paranentelement.$("#" + formUuid).jqGrid('getGridParam','colModel'); 
			   
			   	for(var j = 0 ; j < colModels.length; j ++){
			   
					var model = colModels[j];
					
					var fieldName = model.name;
					if(!_this.isControlFieldInSubform(fieldName) ){
						continue;
					}
					var id = _this.getCellId(rowId, fieldName);
				 
					var fieldDefinition = subFormDefinition.fields[fieldName]; 
					 if(typeof fieldDefinition == "undefined"){
						 continue;
					 }
					 
					 
					var cellValue = rowdata[fieldName];
					var control = _this.createControl($(".value[name='" + id + "']"),fieldDefinition , subFormDefinition, cellValue, rowId, model); 
			   	}
			    
		   	},
		   	
		   	/**
		   	 * 设置在光标离开之后的效果
		   	 * @param control
		   	 */
		   	setBlurPartOfSwitch:function(control, model){
		   		var _this = this;
		   		 
		   		if(!_this.options.readOnly && model.editable && model.controlable){//直接显示为控件
		   			control.setDisplayAsCtl();
					return;
				}
				
				//control.setDisplayAsLabel();
				(function(control){
					
					if(control.getAllOptions().commonProperty.inputMode==dyFormInputMode.checkbox||
			   				control.getAllOptions().commonProperty.inputMode==dyFormInputMode.radio){
						var $ctlParentTd = $(control.$placeHolder.parents("td").get(0)); 
						$ctlParentTd.bind("mouseleave", function(){
							control.setDisplayAsLabel();
						});
			   			return ;
			   		}
					var ctlName = control.getCtlName();
					$(".editableClass[name='" + ctlName + "']").live("blur", function(){
						control.setDisplayAsLabel();
					});
					 
					/*if(control.getAllOptions().commonProperty.inputMode==dyFormInputMode.selectMutilFase || control.getAllOptions().commonProperty.inputMode==dyFormInputMode.job){
						var ctlName = control.getCtlName();
						$(".editableClass[name='" + ctlName + "']").live("blur", function(){ 
							control.setDisplayAsLabel();
						}); 
					}else{
						var $ctlParentTd = $(control.$placeHolder.parents("td").get(0)); 
						$ctlParentTd.bind("mouseleave", function(){
							control.setDisplayAsLabel();
						});
					}*/
					
					//} 
				})(control);
				/*
				if(subform.editMode == dySubFormEdittype.newWin){//在新窗口编辑，所以在jqgrid里面直接展示文本即可
					control.setDisplayAsLabel();
				} */
		   	},
			
			
			/**
			 * 为从表添加行数据
			 * @param formUuid
			 * @param data 数据格式：{name1:value1,name2: value2…} name为在colModel中指定的名称
			 * @param controlable 是否对新添加的行控件化
			 * @param seqNOable 是否刷新序号
			 */
			//initializeControlFromRemoteFlag:false, 
			addRowData: function(formUuid, data, controlable, seqNOable){  
				
				
				//return;
				//var time1 = (new Date()).getTime();
				var t = formUuid;
				if(arguments.length == 1){
					formUuid = this.options.formUuid;
					data = t;
				}
				if(typeof controlable == "undefined"){
					controlable = true;
				}
				if(typeof seqNOable == "undefined"){
					seqNOable = true;
				}
				
				data["controlable"] = controlable;
				data["seqNOable"] = seqNOable;
				
				if(typeof data["id"] == "undefined"){//调用方没有生成行id,这里也为dataUuid
					data["id"] = this.createUuid();
					data["newRow"] = true; //添加新增的行，
				}
				
				// level:"0", parent:"", isLeaf:false, expanded:true, loaded:true
				if( typeof data["level"] == "undefined"){
					  data["level"] = "0"; 
				}
				
				if( typeof data["parent_uuid"] == "undefined"){
					  data["parent_uuid"] = ""; 
				}
				 
				if( typeof data["isLeaf"] == "undefined"){
					  data["isLeaf"] = true; 
				}
				if( typeof data["expanded"] == "undefined"){
					  data["expanded"] = true; 
				}
				if( typeof data["loaded"] == "undefined"){
					  data["loaded"] = true; 
				}
				 
				this.options.$paranentelement.$("#" + formUuid).jqGrid('addChildNode',data["id"],data["parent_uuid"], data);//jqgrid的行ID与Id值一样
				 //$("#cde" ).jqGrid ('addChildNode', ret.rows[0].id, ret.rows[0].parent, ret.rows[0]);
				var parentNode = this.options.$paranentelement.$("#" + formUuid).jqGrid('getNodeParent', data);//jqgrid的行ID与Id值一样
				 
				if(seqNOable != false){ 
					this.updateSeqNo(this.getFormUuid(), parentNode);//更新从表的序号
				}
				
				
				
				//this.loadAndAddChildRowData(formUuid, data, parseInt(data["level"]) + 1);//为当前添加的节点加载子节点
				//var time2 = (new Date()).getTime();
				//console.log(formUuid + "从表插入一条占用时间" + (time2-time1)/1000.0 + "s");
				
				
				
				return data["id"];
			},
			
			/*获取某行的所有控件*/
			getControls:function(rowId){
				var controls = [];
				var data = this.options.$paranentelement.$("#" + this.getFormUuid()).jqGrid("getRowData", rowId); 
				 var colModels = this.options.$paranentelement.$("#" +  this.getFormUuid()).jqGrid('getGridParam','colModel');
				 var formDefinition =this.options.subformDefinition;
				 for(var j = 0 ; j < colModels.length; j ++){
					 var model = colModels[j];
					 var fieldName = model.name;
					 if(!this.isControlFieldInSubform(fieldName)){
						 continue;
					 }
					 var cellId = this.getCellId(rowId, fieldName);
					 var control = $.ControlManager.getCtl(cellId);
					 if(typeof control == "undefined" || control == null ){
						 continue;
					 }
					 controls.push(control);
				 }
				 return controls;
			},
			
			
			/**
			 * 获得行数据
			 * @param formUuid
			 * @param rowId
			 * @returns {___anonymous8084_8085}
			 */
			 getRowData:function(formUuid, rowId){
					var t = formUuid;
					if(arguments.length == 1){
						formUuid = this.options.formUuid;
						rowId = t;
					}
				 
				 var subformData = {};
				 subformData["uuid"] = rowId;
				 var data = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData", rowId);
				 for(var i in data){//获取不是从数据加载而来的字段的值
				 if(!this.isControlFieldInSubform(i)){
				 subformData[i]= data[i];
				 }
				 }
				 var colModels = this.options.$paranentelement.$("#" + formUuid).jqGrid('getGridParam','colModel');
				 var formDefinition =this.options.subformDefinition;
				 for(var j = 0 ; j < colModels.length; j ++){
					 var model = colModels[j];
					 var fieldName = model.name;
					 if(!this.isControlFieldInSubform(fieldName)){
						 continue;
					 }
					 var cellId = this.getCellId(rowId, fieldName);
					 var control = $.ControlManager.getCtl(cellId);
					 var field = formDefinition.fields[fieldName];
					 if(typeof field == "undefined"){
						 console.log( " unknown fieldName[" + fieldName + "]");
						 continue;
					 }
					 /*if(control.isValueMap() ){
					 //这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
					 value = JSON.cStringify(control.getValueMap());
					 }else{
					 value = control.getValue();
					 }*/
					 
					 subformData[fieldName] = this.getValue(control);
				 }
				 return subformData;
				 },
				/**
				 * 获取从表所有的控件的值键对象(两种实现方式：1、control，从表不太好实现，因为jqgrid是先设置值再初始化控件的。2、更具表单定义)
				 * getValueKeySet ->- Map
				 */
				getSubformValueKeySetMap:function(){
					var keyMap = {};
					var fields=this.options.subformDefinition.fields;
					for(var field in fields){
						// radio表单元素 valuemap,树形下拉框valuemap,checkbox表单元素valuemap,下拉单选框valuemap
						var inputMode= fields[field].inputMode;
						if(inputMode == dyFormInputMode.treeSelect || inputMode == dyFormInputMode.radio 
								|| inputMode == dyFormInputMode.checkbox || inputMode == dyFormInputMode.selectMutilFase ){
							var optionSet = fields[field].optionSet;
							if (typeof optionSet == "undefined" || optionSet == null || optionSet.length == 0) {
								console.log("field["+field+"] optionSet undefined");
								continue;
							}
							var obj = {};
							for ( var key in optionSet) {
								var value = optionSet[key];// 键值对换
								obj[value] = key;
							}
							keyMap[field] = obj;
						};
					};
					return keyMap;
				},
				
				getSubformValueFormatSetMap:function(){
					var keyMap = {};
					var fields=this.options.subformDefinition.fields;
					for(var field in fields){
						var inputMode= fields[field].inputMode;
						if(inputMode == dyFormInputMode.date){
							var fmt = fields[field].contentFormat;
							if (typeof fmt == "undefined" || fmt == null) {
								console.log("field["+field+"] contentFormat undefined");
								continue;
							}
							var format='';
							if(fmt==dyDateFmt.yearMonthDate){
								format='yyyy-MM-dd';
							}else if(fmt==dyDateFmt.dateTimeHour){
								format='yyyy-MM-dd HH';
							}else if(fmt==dyDateFmt.dateTimeMin){
								format='yyyy-MM-dd HH:mm';
							}else if(fmt==dyDateFmt.dateTimeSec){
								format='yyyy-MM-dd HH:mm:ss';
							}else if(fmt==dyDateFmt.timeHour){
								format='HH';
							}else if(fmt==dyDateFmt.timeMin){
								format='HH:mm';
							}else if(fmt==dyDateFmt.timeSec){
								format='HH:mm:ss';
							}else if(fmt==dyDateFmt.yearMonthDateCn){
								format='yyyy年MM月dd日';
							}else if(fmt==dyDateFmt.yearCn){
								format='yyyy年';
							}else if(fmt==dyDateFmt.yearMonthCn){
								format='yyyy年MM月';
							}else if(fmt==dyDateFmt.monthDateCn){
								format='MM月dd日';
							}else if(fmt==dyDateFmt.year){
								format='yyyy';
							}
							keyMap[field] = format;
						};
					};
					return keyMap;
				},	
				/**
				 * 获得行数据
				 * @param formUuid
				 * @param rowId
				 * @returns {___anonymous8084_8085}
				 */
				 getRowDisplayData:function(formUuid, rowId){
						var t = formUuid;
						if(arguments.length == 1){
							formUuid = this.options.formUuid;
							rowId = t;
						}
						 
						 var subformData = {};
						 subformData["uuid"] = rowId;
						 var data = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData", rowId);
						 for(var i in data){//获取不是从数据加载而来的字段的值
						 if(!this.isControlFieldInSubform(i)){
						 subformData[i]= data[i];
						 }
						 }
						 var colModels = this.options.$paranentelement.$("#" + formUuid).jqGrid('getGridParam','colModel');
						 var formDefinition =this.options.subformDefinition;
						 for(var j = 0 ; j < colModels.length; j ++){
							 var model = colModels[j];
							 var fieldName = model.name;
							 if(!this.isControlFieldInSubform(fieldName)){
								 continue;
							 }
							 var cellId = this.getCellId(rowId, fieldName);
							 var control = $.ControlManager.getCtl(cellId);
							 var field = formDefinition.fields[fieldName];
							 if(typeof field == "undefined"){
								 console.log( " unknown fieldName[" + fieldName + "]");
								 continue;
							 }
							 /*if(control.isValueMap() ){
							 //这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
							 value = JSON.cStringify(control.getValueMap());
							 }else{
							 value = control.getValue();
							 }*/
							 
							 subformData[fieldName] = this.getDisplayValue(control);
						 }
						 return subformData;
					 }, 				 
				
				 /**
				  * 删除行事件
				  * @param formUuid
				  * @param selectedRowId
				  */
				deleteSubformRowDataEvent:function(formUuid, selectedRowId){//点击删除从表行数据的按钮事件 
					var _this = this;
					//var $dg = this.$('#' + formUuid);
					var ids = [];
					ids.push(selectedRowId);
					//var ids = $dg.jqGrid('getGridParam','selarrrow');
					if(ids.length > 0){
						 oConfirm(dymsg.delConfirm, function ( ){ 
								for(var i=(ids.length-1);i>=0;i--){
									//先清控件清空
									var controls = _this.getControls(ids[i]); 
									for(var j = 0; j < controls.length; j++ ){
										//_this.setValue(, "", ids[i]);
										if(controls[j].clear){
											controls[j].clear();
										}
									} 
									
									_this.delRowData( formUuid, ids[i]); 
									
								}
						});
					}else{
						$.jBox.info(dymsg.selectRecordDel,dymsg.tipTitle);
					}
				},
				
				upSubformRowDataEvent:function(formUuid,selectRowId){
					var tr2 = null;
					var tr = $("#" + selectRowId);
					tr2 = $("#" + selectRowId).prev('tr');
					if(!tr2.hasClass('jqgfirstrow')){
						var text1 = tr.children("td:eq(2)").html();
						var text2 = tr2.children("td:eq(2)").html();
						tr.after(tr2);
						tr.children("td:eq(2)").html(text2);
						tr2.children("td:eq(2)").html(text1);
					}
				},
				
				downSubformRowDataEvent:function(formUuid,selectRowId){
					var tr2 = null;
					var tr = $("#" + selectRowId);
					tr2 = $("#" + selectRowId).next('tr');
					if('undefined'!=typeof(tr2.html())){
						var text1 = tr.children("td:eq(2)").html();
						var text2 = tr2.children("td:eq(2)").html();
						tr.before(tr2);
						tr.children("td:eq(2)").html(text2);
						tr2.children("td:eq(2)").html(text1);
					}
				},
				
				
				/**
				 * 更新序号
				 * @param formUuid
				 * @param parentNode
				 */
				updateSeqNo:function(formUuid, parentNode){
					//var data  = this.$("#" + formUuid).jqGrid("getRowData");
					//var count = this.$("#" + formUuid).jqGrid("getGridParam", "reccount");
					if(typeof parentNode == "undefined" || parentNode == null  ){//设置最上层的节点的序号
						var rootNodes =  this.options.$paranentelement.$("#" + formUuid).jqGrid("getRootNodes", "reccount");
						 for(var i =0; i < rootNodes.length; i ++ ){
							 var id = rootNodes[i]["id"]; 
							 this.options.$paranentelement.$("#" + formUuid).jqGrid("setCell", id, "seqNO", i + 1 ); 
						 }
					}else{
						var childNodes =  this.options.$paranentelement.$("#" + formUuid).jqGrid("getNodeChildren", parentNode);
						for(var i = 0; i < childNodes.length; i++){
							var id = childNodes[i]["id"]; 
							this.options.$paranentelement.$("#" + formUuid).jqGrid("setCell", id, "seqNO", parentNode["seqNO"] + "_" +(i + 1) ); 
						}
					}
				},
					
				/**
				 * 更新从表的行数据
				 * @param formUuid
				 * @param data
				 */
				updateRowData: function(formUuid, data){
					var t = formUuid;
					if(arguments.length == 1){
						formUuid = this.options.formUuid;
						data = t;
					}
					var rowId = data["id"] ;
					if(typeof rowId == "undefined"){
						throw new Error("id is not defined");
					}
					 
					for(var i in data){
						var fieldName = i;
						if(!this.isControlFieldInSubform(fieldName)) continue;
						var controlId = this.getCellId(rowId, fieldName);
						
						var control = $.ControlManager.getCtl(controlId); 
						
						this.setValue(control, data[fieldName], rowId);
						
					}
					 
				},
				
				/**
				 * 删除从表的某行数据
				 * @param formUuid
				 * @param data 数据格式：{name1:value1,name2: value2…} name为在colModel中指定的名称
				 */
				delRowData: function(formUuid, rowId){
					var t = formUuid;
					if(arguments.length == 1){
						formUuid = this.options.formUuid;
						rowId = t;
					}
					
					this.options.$paranentelement.$("#" + formUuid).jqGrid('delRowData',rowId);
					this.updateSeqNo(formUuid);//更新从表的序号
					var parentNode = this.options.$paranentelement.$("#" + formUuid).jqGrid('getNodeParent', data);//jqgrid的行ID与Id值一样
					 
					// this.cache.put.call(this, cacheType.deletedFormDataOfSubform, {dataUuid: rowId, formUuid:formUuid}); 
					 this.cacheDeleteRow({dataUuid: rowId, formUuid:formUuid});
				},
			 
				createUuid:function(){
					return  new UUID().id.toLowerCase(); 
				},
					
			    
				/**
				 * 加载form数据
				 * @param formUuid
				 * @returns {___anonymous12548_12549}
				 */
				loadDefaultFormData: function(formUuid){
					var formData = {};
					var url = ctx + "/dyformdata/getDefaultFormData";
					$.ajax({
						url:url,
						cache : false,
						async : false,//同步完成
						type : "POST",
						data : {"formUuid": formUuid} ,
						dataType : "json",
						success:function (result){
							if(result.success == "true" || result.success == true){ 
								  formData =  result.data;  
				  		   }else{
				  			    
				  		   }
						},
						error:function(data){
							 console.log(JSON.cStringify(data));
						}
					});
					return formData;
				},
					
				/**
				 * 获取主表的定义id
				 */
				getFormUuid:function(){
					return this.options.subformDefinition.uuid;
				},
				
				setMainformDataUuid:function(datauuid){ 
					this.options.mainformDataUuid=datauuid;
				},
				
				/**
				 * 获取主表Uuid
				 * @param dataUuid
				 */
				getDataUuid4Mainform:function(){ 
					 return this.options.mainformDataUuid;
				},
					
				/**
				 * 加载子节点
				 * @param formUuid
				 * @param data
				 */
				loadAndAddChildRowData : function(formUuid, data){
					var formUuidOfSubform = formUuid;
					var formUuidOfMainform = this.getFormUuid();
					var dataUuidOfMainform = this.getDataUuid4Mainform();
					if(dataUuidOfMainform == undefined){
						return;
					}
					var dataUuidOfParentNode = data["id"];
					var _this = this;
					var url = ctx + "/dyformdata/getFormDataOfChildNode4ParentNode";
					$.ajax({
						url:url,
						cache : false,
						async : true,//异步完成即可
						type : "POST",
						data : {
								"formUuidOfSubform": formUuidOfSubform, 
								"formUuidOfMainform":formUuidOfMainform,
								"dataUuidOfMainform":dataUuidOfMainform,
								"dataUuidOfParentNode":dataUuidOfParentNode 
							} ,
						dataType : "json",
						success:function (result){
							if(result.success == "true" || result.success == true){ 
									var formData = {};
								  formData =  result.data;  
								  if(formData == null) return;//没有子节点
								  
								  for(var i = 0; i < formData.length ; i ++){
									  var data =  formData[i];
									  data["id"] = data["uuid"];
									  _this.addRowData(formUuidOfSubform,data);
								  }
				  		   }else{
				  			 console.log(JSON.cStringify(result));
				  		   }
						},
						error:function(data){
							 console.log(JSON.cStringify(data));
						}
					});
					//return formData;
				},
				
				/**
				 * 设置控件的值
				 * @param control
				 * @param cellValue
				 * @param dataUuid
				 */
				setValue:function(control, cellValue, dataUuid){
					control.setDataUuid(dataUuid); 
					if(control.isValueMap()){ 
						control.setValueByMap(cellValue); 
					}else{
						 
						control.setValue(cellValue); 
					}
				},
				
				/**
				 * 获得控件的值
				 * @param control
				 * @returns {String}
				 */
				getValue:function(control){
					var value = "";
					if(control.isValueMap() ){
						//这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
						var valueMap = control.getValueMap();
						if(typeof valueMap == "string"){
							value = valueMap;
						}else{
							value =   JSON.cStringify(control.getValueMap());
						}
					}else{
						value = control.getValue();
					}
					return value;
				},
				/**
				 * 获得控件的值
				 * @param control
				 * @returns {String}
				 */
				getDisplayValue:function(control){
					var value = "";
					if(control.isValueMap() ){
						//这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
						value = control.getDisplayValue();
					}else{
						value = control.getValue();
					}
					return value;
				},				
				/**
				 * 获取从表的数据
				 * @param formUuid 从表定义Uuid
				 */
				collectSubformData:function(){
					var subformDatas = [];
					
					var datas  = this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid("getRowData");
					 
					for(var i = 0; i < datas.length; i ++){
						var subformData = {}; 
						var data = datas[i];
						
						var rowId = data["id"]; 
						
						subformData = this.getRowData(this.options.formUuid, rowId);
					
						subformDatas.push(subformData);
					}
					 
					return subformDatas;  
				},
				/**
				 * 获取从表的显示数据
				 * @param formUuid 从表定义Uuid
				 */
				collectSubformDisplayData:function(){
					var subformDatas = [];
					var datas  = this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid("getRowData");
					for(var i = 0; i < datas.length; i ++){
						var subformData = {}; 
						var data = datas[i];
						var rowId = data["id"]; 
						subformData = this.getRowDisplayData(this.options.formUuid, rowId);
						subformDatas.push(subformData);
					}
					return subformDatas;  
				},				
				/**
				 * 获得被删除的行.
				 */
				getDeleteRows:function(){
					 data = this.$element.data(cacheType.deletedFormDataOfSubform);
					 if(typeof data == "undefined" ){
						 data = [];
						}
					 return data;
				},
				
				cacheDeleteRow:function(data){
					var fdata = this.$element.data(cacheType.deletedFormDataOfSubform);
					if(typeof fdata == "undefined" ){
						fdata = [];
					} 
					fdata.push(data.dataUuid);
					this.$element.data(cacheType.deletedFormDataOfSubform, fdata);
				},
				
				/**
				 * 从表控件校验
				 * @returns {Boolean}
				 */
				validate:function(){
					var valid =true;
					var datas  = this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid("getRowData");
					var colModels = this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid('getGridParam','colModel');
					for(var j = 0; j < datas.length; j++){//遍历从表的各行
						var data = datas[j];
						var rowId = data["id"];
						for(var k = 0 ; k < colModels.length; k ++){
							var model = colModels[k];
							var fieldName = model.name;
							if(!this.isControlFieldInSubform(fieldName)){//不获取序号
								continue;
							}
							var cellId = this.getCellId(rowId, fieldName);
							 
							var control = $.ControlManager.getCtl(cellId);
							var v = control.validate(); 
							valid = valid &&  v ; 
						} 
					}  
					return valid;
					
				},
				
				
				/**
				 * 填充从表数据.
				 * @param formUuid
				 * @param formDatas
				 */
				fillFormData: function(formDatas){
					var _this = this;
					if(typeof formDatas != "undefined" && formDatas.length > 0){
						this.clearSubform();//清空从表数据
					}
					
					
					for(var i =0; i < formDatas.length;  i++){
						var formData = formDatas[i];
						formData["id"] = formData.uuid;
						//var time1 =( new Date()).getTime();
						//window.setTimeout(function(){
							_this.addRowData(_this.options.formUuid, formData, true, false);
						//}, 5);
						
						//var time2 =( new Date()).getTime();
						//console.log("插入一条从表记录所用时间:" + (time2 - time1)/1000.0 + "s");
					}
					this.updateSeqNo(_this.options.formUuid);
				},
				/**清空整个从表*/
				clearSubform:function(){
					//this.collectSubformData();
					 var formUuid=this.options.formUuid; 
					 $('#'+formUuid).jqGrid("clearGridData");
				},
				
				setReadOnly: function(){
					 
					//var operateBtn =   "operateBtn" + this.options.formUuid;
					//$("#" + operateBtn).hide();
					  this.hideOperateBtn();
					this.options.readOnly = true; 
					this.invokeCommonMethod("setReadOnly", true); 
				},
				 
				isReadOnly: function(){
					return this.options.readOnly == true;  
				},
				
				hideOperateBtn:function(){  
					var operateBtn =   "operateBtn" + this.options.formUuid;
					$("#" + operateBtn).hide();
				},
				
				showOperateBtn:function(){
					if(this.isReadOnly()){//只读不显示按钮
						return;
					}
					var operateBtn =   "operateBtn" + this.options.formUuid; 
					$("#" + operateBtn).show();
				},
				
				setTextFile2SWF:function(){
					var _parentthis=this.options.$paranentelement;
					var formUuid = this.options.formUuid;
					var subFormDefinition=this.options.subformDefinition;
					var datas  = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData");
					var colModels = _parentthis.$("#" + formUuid).jqGrid('getGridParam','colModel');
					for(var i = 0; i < datas.length; i ++){ 
						var data = datas[i]; 
						var rowId = data["id"];  
						for(var j = 0; j < colModels.length; j ++){
							var fieldName = colModels[j].name;
							if(!subFormDefinition.isInputModeAsAttach(fieldName)){
								continue;
							}
							var ctlId = this.getCellId(rowId, fieldName);
							var control = $.ControlManager.getCtl(ctlId);
							if(typeof paramters == "undefined"){
								control["setTextFile2SWF"]();
							}else{
								
								control["setTextFile2SWF"](paramters);
							}
							
						}
					}
					//this.invokeCommonMethod("setTextFile2SWF");
				},
				enableSignature:function(enable){
					var _parentthis=this.options.$paranentelement;
					var formUuid = this.options.formUuid;
					var subFormDefinition=this.options.subformDefinition;
					var datas  = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData");
					var colModels = _parentthis.$("#" + formUuid).jqGrid('getGridParam','colModel');
					for(var i = 0; i < datas.length; i ++){ 
						var data = datas[i];
						var rowId = data["id"];
						for(var j = 0; j < colModels.length; j ++){
							var fieldName = colModels[j].name;
							if(!subFormDefinition.isInputModeAsAttach(fieldName)){
								continue;
							}
							var ctlId = this.getCellId(rowId, fieldName);
							var control = $.ControlManager.getCtl(ctlId);
							if(typeof paramters == "undefined"){
								control["enableSignature"]();
							}else{
								control["enableSignature"](paramters);
							}
						}
					}
					//this.invokeCommonMethod("setTextFile2SWF");
				
				},
				setEditable: function(){
					this.options.readOnly = false;
					//var operateBtn =   "operateBtn" + this.options.formUuid;
					 
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					if($context.$("#gbox_" + formUuid + " .collapsesubform ").attr("id") == "notOpenDiv"){  //折叠状态时不展示操作按钮
						//$("#" + operateBtn).hide();
						this.hideOperateBtn();
						 
					}else{ 
						this.showOperateBtn();
						 
					}
					
					
				 
					
				},
				setDisplayAsLabel:function(){   
					//this.setReadOnly(); 
					this.options.readOnly = true;
					this.hideOperateBtn();
					this.invokeCommonMethod("setDisplayAsLabel");
					
				},
				invokeCommonMethod:function(methodName, paramters){  
					var _parentthis=this.options.$paranentelement;
					var formUuid = this.options.formUuid;
					var datas  = this.options.$paranentelement.$("#" + formUuid).jqGrid("getRowData");
					var colModels = _parentthis.$("#" + formUuid).jqGrid('getGridParam','colModel');
					for(var i = 0; i < datas.length; i ++){ 
						var data = datas[i]; 
						var rowId = data["id"];  
						for(var j = 0; j < colModels.length; j ++){
							var fieldName = colModels[j].name;
							if(!this.isControlFieldInSubform(fieldName)){
								continue;
							}
							var ctlId = this.getCellId(rowId, fieldName);
							
							var control = $.ControlManager.getCtl(ctlId); 
							if(typeof control == "undefined" || control == null){
								continue;
							}
							if(typeof paramters == "undefined"){
								control[methodName]();
							}else{ 
								 
								control[methodName](paramters);
							}
							
						}
					}
				},
				/**
				 * 隐藏从表
				 */
				hide:function(){
				 
					var formUuid = this.options.formUuid;
				 
					$("#gbox_" + formUuid).hide();
				},
				
				/**
				 * 显示从表
				 */
				show:function(){
					var formUuid = this.options.formUuid;
					
					$("#gbox_" + formUuid).show();
				},
				$thisContext:function(){
					var formUuid = this.options.formUuid;
					return $("#gbox_" + formUuid);
				},
				
				
				group:function(){ 
					if(!this.options.groupField){
						return;
					}
					console.log("分组");
						//分组刷新..
						 var formUuid=this.options.formUuid;
							//获得表单数据
							var formdatas=this.collectSubformData();
							 
							$('#'+formUuid).jqGrid("clearGridData");
							//重新填充
							
							this.fillFormData(formdatas);
							  
				},
				isFieldHidden: function(field){
					
					var hidden =  ((typeof field.hidden) == "undefined" ?  false: (dySubFormFieldShow.notShow == field.hidden));//是否隐藏  
				 
					return hidden;
				},
				isFieldEditable: function(field){
					var editable =  ((typeof field.editable) == "undefined" ?  true: (dySubFormFieldEdit.notEdit != field.editable));//是否隐藏  
					return editable;
				},
				
				/**
				 * 定义从表折叠事件
				 * @param collapse 
				 */
				definiteCollapse:function(collapse){
					
					var $context = this.$thisContext();
					 
				 
					var _this = this;
					$context.$(".ui-jqgrid-titlebar-close").hide();//将jqgrid默认的collapse/expand按钮隐藏掉 
					$context.$(".collapsesubform").unbind("click");
					$context.$(".collapsesubform").bind("click", function(){//设置自定义的collapse/expand按钮 
						 
						var $siblings = $(this).parent().parent().siblings();
						var id = $(this).attr("id");
						if(id == "openDiv"){
							$siblings.hide();
							$(this).attr("id", "notOpenDiv");
							 
							_this.hideOperateBtn();
						}else{
							$siblings.show();    
							
							_this.showOperateBtn();
							$(this).attr("id", "openDiv");
						}
						$(window).trigger("resize");//触发重新设置从表的宽度
					});
					
					
					 
					
					if(collapse == dySubFormTableOpen.notOpen){
						this.collapse();
					 
					}else{
						 
						this.expand();
					}
					
				},
				
				/**
				 *从表折叠接口
				 */
				collapse:function(){
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					if($context.$("#gbox_" + formUuid + " .collapsesubform ").attr("id") == "openDiv"){
						$context.$("#gbox_" + formUuid + " .collapsesubform ").trigger("click");
					}
				},
				
				/**
				 * 从表展开接口
				 */
				expand:function(){
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					if($context.$("#gbox_" + formUuid + " .collapsesubform ").attr("id") == "notOpenDiv"){
						$context.$("#gbox_" + formUuid + " .collapsesubform ").trigger("click");
					}
				},
				
				/**
				 * 隐藏列
				 * @param fieldName
				 */
				hideColumn:function(fieldName){
					this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid('hideCol', fieldName);
					 
				},
				
				/**
				 * 展示列
				 * @param fieldName
				 */
				showColumn:function(fieldName){
					this.options.$paranentelement.$("#" + this.options.formUuid).jqGrid('showCol', fieldName);
				},
				
				/**
				 * 设置整列显示为标签
				 * @param fieldName
				 */
				setColumnAsLabel:function(fieldName){
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					var colModels = $context.$("#" + formUuid).jqGrid('getGridParam','colModel');  
						for(var j = 0; j < colModels.length; j ++){ 
							if( colModels[j].name == fieldName){
								colModels[j].editable = false;
								break;
							}
						}
				},
				
				
				/**
				 * 设置整列可编辑
				 * @param fieldName
				 */
				setColumnEditable:function(fieldName){
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					var colModels = $context.$("#" + formUuid).jqGrid('getGridParam','colModel'); 
						for(var j = 0; j < colModels.length; j ++){
							if( colModels[j].name == fieldName){
								colModels[j].editable = true;
							}
						}
					 
				} ,
				
				
				/**
				 * 在列为可编辑的前提下，调用该方法时,从表指定列的各控件不再有光标移开之前就把控件显示为标签的效果
				 * */
				setColumnCtl:function(fieldName, controlable){
					if(typeof controlable == "undefined" || controlable == null){
						controlable = true;
					}
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
					var colModels = $context.$("#" + formUuid).jqGrid('getGridParam','colModel'); 
						for(var j = 0; j < colModels.length; j ++){
							if( colModels[j].name == fieldName){
								colModels[j].controlable = true;
							}
						}
				},
				
				setGridWidth:function(width){
					var formUuid = this.options.formUuid;
					var $context = this.options.$paranentelement;
				    $context.$("#" + formUuid).jqGrid('setGridWidth', width);  
				},
				
				getLastFormContainerWidth:function(){
					return this.lastFormContainerWidth; 
				},
				
				setParentWidth:function(width){ 
					 this.options.$parent.css("width", width);  
				},
				
				setParentPadding:function(padding){ 
					 this.options.$parent.css("padding", padding);  
				},
				
				setLastParentWidth:function(){
					this.lastParentWidth =  this.options.$parent.width();
				},
				
				getLastParentWidth:function(){
					return this.lastParentWidth  ;
				},
				
				setLastFormContainerWidth:function( ){
					var $context = this.options.$paranentelement;
					this.lastFormContainerWidth = $context.width();
					this.setLastParentWidth();
					//.lastParentWidth =  this.$thisContext().parent().width();
				},
				
				resetGridWidth:function(){
					//debugger;
					var $context = this.options.$paranentelement;
					var formWidth = $context.width();
					//debugger;
					
					var paddingLeft = this.options.$parent.css('padding-left') ;
					paddingLeft = paddingLeft.replace("p", "").replace("x", "");
					paddingLeft = parseInt(paddingLeft, 10);
					
					var paddingRight = this.options.$parent.css('padding-right') ;
					paddingRight = paddingRight.replace("p", "").replace("x", ""); 
					paddingRight = parseInt(paddingRight, 10); 
					if(this.lastFormContainerWidth != formWidth){
						
						//var paddingLeft = 0;
						//var paddingRight = 0;
						
						var proportion = (formWidth * 1.1)/(this.lastFormContainerWidth * 1.1);
						var width = this.getLastParentWidth() * proportion;
						width = width  + paddingLeft + paddingRight;
						//var gridwidth = (this.getLastParentWidth()-(paddingLeft+paddingRight)/2) * proportion;
						if(proportion > 1){
							this.setGridWidth(  width );
						}else{
							this.setGridWidth(  width -2);
						}
						  
						if( this.options.$parent[0].tagName.toLowerCase() != "form"){
							this.setParentPadding("0");
							this.setParentWidth( width );
						}
					    this.setLastFormContainerWidth();
					}else{
						if( this.options.$parent[0].tagName.toLowerCase() != "form"){
							this.setParentPadding("0");
						}
						 
						var width = this.getLastParentWidth(); 
						 
						width = width  + paddingLeft + paddingRight ;
						this.setGridWidth(width - 2);
						this.lastParentWidth = width;
						//this.setParentWidth( width );
					}
					 
					
				},
				//将运算公式及公式依附的列放到缓存中
				addFormula:function( fieldName,formula ){
					//var parentThis = this.options.$paranentelement;
					//parentThis.cache.put.call(parentThis, cacheType.formula, {formId:this.options.subformDefinition.outerId,fieldName:fieldName, formula:formula});
					$.ControlManager.registFormula(formula);
				},
				//创建控件
				createControl:function($placeHolder, fieldDefinition , subFormDefinition, cellValue, rowId, model){
					fieldDefinition.pos= dyControlPos.subForm;
					fieldDefinition.data = cellValue;
					 
					fieldDefinition.dataUuid = rowId; 
					$.ControlManager.createCtl($placeHolder, fieldDefinition, subFormDefinition); 
					//var time000 = (new Date()).getTime();
					//console.log(fieldName + "--" + (time000 - time00)/1000.0);
					var control = $.ControlManager.getCtl($placeHolder.attr("name"));
					if(control==undefined) return;
					
					if(control.isAttachCtl && control.isAttachCtl()){
						control.setDisplayAsLabel();
					}
					//设置分组验证id
					control.setValidateGroup({groupName:model.name, groupUsed:"uniqueGroup"});
					
					this.addControlUrl(control,rowId);
					
					this.setBlurPartOfSwitch(control, model); 
					//console.log("registFormula-->" + control.getCtlName());
					//this.registFormula(control);//将运算公式注册到控件中
					
					return control;
				},
				/*将运算公式注册到控件中*/
				registFormula:function(control){
					var parentThis = this.options.$paranentelement;
					var allformulas = parentThis.cache.get.call(parentThis, cacheType.formula);
					 
					var formId =  this.options.subformDefinition.outerId;
					var dataUuid = control.getDataUuid();
					var fieldName = control.getFieldName();
					
					//console.log(control.getCtlName() + ":" + fieldName);
					var formulaPlaceHolder1 = "${" + formId + ":" + fieldName + "}";//指定到列,该公式对所有列有效
					//console.log(formulaPlaceHolder1);
					var formulaPlaceHolder2 =  control.getCtlName();//指定到行,该公式对行有效
					
					/*for(var i in allformulas){
						console.log(formulaPlaceHolder1 + "=" + i + ":" + (formulaPlaceHolder1 == i));
					}*/
					
					//指定到列
					var fieldFormulas = allformulas[formulaPlaceHolder1];
					if(typeof fieldFormulas != "undefined"){//找到运算公式 
						if(control.addAllFormulas) {
							control.addAllFormulas(formulaPlaceHolder1, fieldFormulas);
							 
						}
						
					}
					
					//指定到行
					var fieldFormulas = allformulas[formulaPlaceHolder2];
					if(typeof fieldFormulas != "undefined"){//找到运算公式  
						if(control.addAllFormulas){
							control.addAllFormulas(formulaPlaceHolder2, fieldFormulas);
							 
						}
						
					}
					 
					
				},
				/*设置列宽度*/
				setModelWidth:function(model, width){
					if((typeof width) != "undefined" && width != null &&  width.length > 0 ){//列宽度
						model.width =  width;
						if( model.width.indexOf("px") == -1){
							model.width += "px";
						}   
					}
				 
					
				},
				/*宽度自适应 */
				getAutoWidth:function(){
					if(typeof this.options.autoWidth == "undefined" || this.options.autoWidth == "true" || this.options.autoWidth == true) {
						return true;
					}else{
						return false;
					}
				}
				 
			
	};
	
	
	
	
});

/**
 * 判断是不是鼠标点击事件
 * @param event
 * @returns {Boolean}
 */
function mouseEvent(evt){
	var _this = evt.currentTarget;
	 var rect = _this.getBoundingClientRect(); 
	 var me = (evt.clientX >= rect.left && evt.clientX <= (rect.left + $(_this).width()*2)); 
	 me = me &&  (evt.clientY >= rect.top && evt.clientY <= (rect.bottom  )); 
	 if(!me){//非鼠标事件
		 return false;
	 }else{
		 return true;
	 }
}

var cellcount = 0;
var rowcount = 0;


