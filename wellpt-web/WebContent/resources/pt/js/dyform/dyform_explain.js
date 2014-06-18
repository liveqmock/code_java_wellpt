//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dyform/dyform");


var cacheType={
	formUuid:1,//主表的formuuid
	options:2,//表单的选项参数
	formDefinition:3,//主表、从表的定义
	dataUuid:4//主表的datauuid
};

/**
 * 表单插件
 */
$(function(){
	
	$.dyform = $.dyform || {};
	
	$.extend($.dyform,{
		getAccessor : function(obj, expr) {
			var ret,p,prm = [], i;
			if( typeof expr === 'function') { return expr(obj); }
			ret = obj[expr];
			if(ret===undefined) {
				try {
					if ( typeof expr === 'string' ) {
						prm = expr.split('.');
					}
					i = prm.length;
					if( i ) {
						ret = obj;
					    while (ret && i--) {
							p = prm.shift();
							ret = ret[p];
						}
					}
				} catch (e) {}
			}
			return ret;
		},
		extend : function(options) {
			$.extend($.fn.dyform,options);
			if (!this.no_legacy_api) {
				$.fn.extend(options);
			}
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
		 * 根据
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
			/*model.editoptions={
				custom_element:function(value, options){
					alert(12);
					console.log(JSON.stringify(options));
					console.log("值:" + value);
					var el = document.createElement("input");
		              el.type="text";
		              el.value = value;
		              return el;
				},
				custom_value:function(elem){
					 return $(elem).val();
				}
			};*/
			
			var _this = this;
		 
			model.formatter = function(cellvalue, options, rowObject){
				var parentElement =  document.createElement("span");
				
				var element = document.createElement("span");
				var id = _this.getCellId(options.rowId, options.colModel.name);  
				element.setAttribute("id", id);
				parentElement.appendChild(element);
				 
				var body = document.getElementsByTagName("body").item(0);
				 
				body.appendChild(parentElement);
				$("#" + id).html("<input name='" + id + "'/>");  
				
				var html = $("#" + id).parent().html();
				
				$("#" + id).parent().remove();
				 
				/*alert(3);
				//_inputc62d36ba4cd00001d6fd961320607a50___test1
				var obj = $("input[name='_input" + id + "']");
				alert(obj.size() + "ttttttttt");
				console.log(JSON.stringify(obj));
				return;
				 var data = obj.wdatePicker("getObject");
				 alert(66);
				alert(data);*/
				 //alert(html);
				return html;
			};
		
			
			return model;
			
		},
		/**
		 *置于jqgrid中，从表的各cell的id 
		 **/
		getCellId:function(rowId, fieldName){
			var id = rowId + "___" + fieldName; 
			return id;
		}
		,
		loadFormDefinition: function(formUuid){
			return loadFormDefinition(formUuid);
		},
		setValue:function(control, cellValue){
			if(control.isValueMap()){
				control.setValueByMap(cellValue); 
			}else{
				control.setValue(cellValue); 
			}
		},
		getValue:function(control){
			var value = "";
			if(control.isValueMap() ){
				//这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
				value =   JSON.stringify(control.getValueMap());
			}else{
				value = control.getValue();
			}
			return value;
		},
		/**
		 * 在从表中的字段是不是控件字段
		 * @param fieldName
		 */
		isControlFieldInSubform:function(fieldName){
			if(fieldName == "seqNO" || fieldName == "cb" || fieldName == "id" || fieldName == "uuid"){
				return false;
			}else{
				return true;
			}
		}
		
		
		
		 
	});
	
	
	$.fn.dyform = function(options){ 
		if(this.size() == 0){
			throw new Error("zero elements found by selector["  + this.selector + "]");
		}if(this.size() > 1){
			throw new Error("more than one element found by selector["  + this.selector + "]");
		}else if(this[0].nodeName != "FORM"){
			throw new Error("dataform container must be a form html element");
		}
		
		if(typeof options == "string"){//字段串类型 
			var fn = $.dyform.getAccessor($.fn.dyform,options);
			if (!fn) {
				throw ("jqGrid - No such method: " + options);
			}
			
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this,args);
		}else{//如果只传定义,默认为定义解析接口 
			//this.dyform("parseFormDefinition", arguments);
			
		 
			var fn = $.dyform.getAccessor($.fn.dyform,"parseForm");  
			return fn.apply(this,arguments);
		}
	};
	
	/**
	 * 对外接口
	 */
	$.dyform.extend(/**
	 * @author Administrator
	 *
	 */
	{
		$ : function(selector){ 
			 
			return $(selector, this[0]);
		},
		parseForm : function(formDefinition, options){
			var defaults = {
					isFile2swf:true,
					//enableSignature:true,//是否签名
					readOnly:false,//是否设置所有字段只读，true表示设置,false表示不设置
					supportDown:"2",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
					displayFormModel:false,//是否用单据展示
					success:function(){ },
					error:function(){ }
			};
			
			
			$.extend(defaults, options);
		 
			
			//将当前容器的对应的表单定义相关数据缓存起来,当需要获取缓存中的数据时，就需要通过key去获取
			//key是什么，需要到缓存$.dyform.cache代码去看,方便统一管理缓存 
			this.cache.put.call(this, cacheType.formUuid, formDefinition.uuid);
			this.cache.put.call(this, cacheType.formDefinition, formDefinition);
			this.cache.put.call(this, cacheType.options, defaults);
			
			
			console.log("开始解析表单定义");
			
			$(this).html(formDefinition.html);
			$(".label").removeClass("label");
			
			this.parseMainForm(formDefinition);//解析主表
			
			
			
			
			this.parseSubform(formDefinition);//解析从表
			   
		
			 
			defaults.success.apply(this);
		},
		
		/**
		 * 为从表添加行数据
		 * @param formUuid
		 * @param data 数据格式：{name1:value1,name2: value2…} name为在colModel中指定的名称
		 */
		//initializeControlFromRemoteFlag:false, 
		addRowData: function(formUuid, data){ 
			
			if(typeof data["id"] == "undefined"){//调用方没有生成行id,这里也为dataUuid
				data["id"] = this.createUuid();  
			}
 	
			this.$("#" + formUuid).jqGrid('addRowData',data["id"],data,"first");//jqgrid的行ID与Id值一样
			
			this.updateSeqNoOfSubform(formUuid);//更新从表的序号
			 
		},
		/**
		 * 更新从表的行数据
		 * @param formUuid
		 * @param data
		 */
		updateRowData: function(formUuid, data){
			var rowId = data["id"] ;
			if(typeof rowId == "undefined"){
				throw new Error("id is not defined");
			}
			console.log("更新的目标数据:" + JSON.stringify(data));
			for(var i in data){
				var fieldName = i;
				if(!$.dyform.isControlFieldInSubform(fieldName)) continue;
				var controlId = $.dyform.getCellId(rowId, fieldName);
				
				var control = $.ControlManager.getControl(controlId); 
				
				$.dyform.setValue(control, data[fieldName]);
				
			}
			 
			 
		},
		/**
		 * 删除从表的某行数据
		 * @param formUuid
		 * @param data 数据格式：{name1:value1,name2: value2…} name为在colModel中指定的名称
		 */
		delRowData: function(formUuid, rowId){
			this.$("#" + formUuid).jqGrid('delRowData',rowId);  
			this.updateSeqNoOfSubform(formUuid);//更新从表的序号
		},
		/**
		 * 给主表设置Uuid
		 * @param dataUuid
		 */
		setDataUuid4Mainform:function(dataUuid){ 
			 this.cache.put.call(this, cacheType.dataUuid, dataUuid); 
		},
		/**
		 * 给主表设置Uuid
		 * @param dataUuid
		 */
		getDataUuid4Mainform:function(){ 
			 return this.cache.get.call(this, cacheType.dataUuid);
		},
		
		/**
		 * 收集表单数据
		 * @param formUuid
		 */
		collectFormData:function(){
			var formData = {};
			var mainformData = {};
			var subformData = {};
			var formUuid =  this.cache.get.call(this, cacheType.formUuid);
			console.log("为formUuid[" + formUuid + "]收集数据");
			var formDefinition =  this.cache.get.call(this, cacheType.formDefinition, formUuid);
			
			//收集从表数据
			var subforms = formDefinition.subforms;
			for(var i in subforms){
				var subform = subforms[i];
				subformData[subform.formUuid] = this.collectSubformData(subform.formUuid);
			}
			formData.subformData = subformData;  
			
			//收集主表数据
			mainformData[formUuid] = this.collectMainformData(formUuid);
			 formData.mainformData = mainformData;
			return formData;
		},
		
		
		/**
		 * 验证表单数据
		 * @param formUuid
		 */
		validateForm: function(){
			var ruleObj = {rules:{}, messages:{}};
			var formUuid =  this.cache.get.call(this, cacheType.formUuid);
			 
			//获取主表的验证规则
			var mainformRuleObj = this.validateMainform( formUuid);
			$.extend(ruleObj.rules, mainformRuleObj.rules);
			$.extend(ruleObj.messages, mainformRuleObj.messages); 
			
			//获取从表的验证规则
			var subformRuleObj =  this.validateSubform(formUuid);
		 
			$.extend(ruleObj.rules, subformRuleObj.rules);
			$.extend(ruleObj.messages, subformRuleObj.messages); 
			
			
			
			//设置自定义样式
			//validate初始化方式一
			//$.extend(ruleObj, Theme.validationRules);
			//var validator = this.validate(ruleObj);
			 
			//validate初始化方式二///////////////用方式一会出现一种情况:当验证完一次，然后从表再增加一行，这时新的一行不会被验证
			var validator = this.validate(Theme.validationRules ); 
			validator.settings.rules = ruleObj.rules;
			validator.settings.messages = ruleObj.messages;
			
			var valid = validator.form() ;
			
			return valid;
			 
		},
		
		/**
		 * 填充数据
		 * @param formDatas
		 * @param callback
		 */
		fillFormData:function(formDatas, callback){
			var formUuid =  this.cache.get.call(this, cacheType.formUuid);//获取当前表单的定义uuid 
			for(var i in formDatas){
				var formData = formDatas[i]; 
				if(formUuid == i){//填充主表
					this.fillFormDataOfMainform(formData[0]);
				}else{//填充从表
					this.fillFormDataOfSubform(i, formData);
				}
			}
			
			if(callback){
				callback.apply(this);
			}
		},
		/**
		 * 为从表填数据
		 * @param formData
		 */
		fillFormDataOfSubform: function(formUuid, formDatas){
			for(var i =0; i < formDatas.length;  i++){
				var formData = formDatas[i];
				formData["id"] = formData.uuid;
				this.addRowData( formUuid, formData); 
			}
		},
		
		/**
		 * 为主表填充数据
		 * @param formData
		 */
		fillFormDataOfMainform: function(formData){ 
			 this.setDataUuid4Mainform(formData.uuid);//将dataUuid缓存  
			 var _this = this;
			 this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");  
				var value = formData[fieldName];
				if(typeof value == "undefined"){
					return true;
				}
				_this.setFieldValue(fieldName, value); 
			}); 
		},
		
		/**
		 * 设置主表的某字段的值
		 * @param fieldName 字段名
		 * @param data 各字段是的值
		 * 	格式根据不同的inputMode也会不一样，与getFieldValue一致
		 */
		setFieldValue:function(fieldName, data){
		 
			var control = $.ControlManager.getControl(fieldName); 
			if(typeof control == "undefined" || control == null){
				return;
			}
			$.dyform.setValue(control, data);
			/*if(control.isValueMap()){
				control.setValueByMap(data);
			}else{
				control.setValue(data);
			}*/
			
		},
		/**
		 * 获取主表某字段的值
		 * @param fieldName
		 */
		getFieldValue:function(fieldName){
			
		}
		 
	});
	
	$.dyform.extend({
		parseMainForm : function(formDefinition){ 
			var formData = this.loadDefaultFormData(formDefinition.uuid); 
			this.$(".value[name]").each(function(){
				var name = $(this).attr("name");
				var fieldDefinition = formDefinition.fields[name];
				fieldDefinition.formUuid = formDefinition.uuid;
				if(typeof fieldDefinition == "undefined"){
					return true;//continue;
				}
				$(this).before("<input name='" + name + "'/>");
				$(this).hide();
				//$(this).html("<input name='" + name + "'/>"); 
				
				$.ControlManager.createControl(fieldDefinition);
				var control = $.ControlManager.getControl(fieldDefinition.name);
				var value = formData[name];
				if(typeof value != "undefined" && value != null){
					$.dyform.setValue(control, value);
				/*	if(control.isValueMap()){
						//var control = 	$.ControlManager.getControl(name).setValue;
						control.setValueByMap(value);
					}else{
						control.setValue(value);
					}*/
				}
				
			
				 
				//$.ControlManager.getControl("fieldName").getValue();
				
				$(".label").removeClass("label"); 
			});
		},
		parseSubform: function(formDefinition){
			var _this = this;
			//含有formUuid属性的table即为从表,解析各从表
			this.$("table[formUuid]").each(function(){
				
				var formUuid = $(this).attr("formUuid");
				 
				//获取从表定义
				var subform = formDefinition.subforms[formUuid];
				if(subform == null){//在定义中找不到该表单 
					$(this).remove();//找不到定义就直接删除
					return true;
				}
				
				
				
				$(this).attr("id", formUuid);//从表的table 的id属性值为formuuid
				var subFormDefinition = $.dyform.loadFormDefinition(formUuid);//加载从表对应的那张表的完整定义
				
				//缓存从表的完整定义
				_this.cache.put.call(_this, cacheType.formDefinition, subFormDefinition);
				
				if(subFormDefinition == null){
					$(this).remove();//找不到定义就直接删除
					return true;
				}
				
				/*
				 *从表以jqGrid来展示
				 */
				
				//从表的标题
				var jqGridCaption = subform.displayName;
				
				//收集从表列信息
				var jqGridColNames = [];//从表列标题 
				var jqGridColModels = [];//各列的配置信息
				
				//行ID,dataUuid
				jqGridColNames.push("id");
				jqGridColModels.push({name:'id',index:'id',hidden:true});
				
				//序号
				jqGridColNames.push("序号");
				/*if(subform.hideSeqNO){//将序号隐藏
					jqGridColModels.push({name:'seqNO',index:'seqNO',width:'25px', align:"center", sortable:false, hidden:true});
				}else{//展示序号,默认为不
					jqGridColModels.push({name:'seqNO',index:'seqNO',width:'25px', align:"center", sortable:false});
				}*/
				jqGridColModels.push({name:'seqNO',index:'seqNO',width:'25px', align:"center", sortable:false});
				 
				
				var fields = subform.fields;
				for(var i in fields){
					var field = fields[i];
					jqGridColNames.push(field.displayName);
					var model =  $.dyform.getJqGridColModel(subFormDefinition.fields[field.name]); 
					model.editable = field.editable;//可编辑与否 
					model.sortable = field.order;//是否支持点击排序
					model.hidden = field.hidden;//是否隐藏 
					jqGridColModels.push(model); 
				}
				
				 
				 
				 var options =  _this.cache.get.call(_this,cacheType.options);
				 if(options.readOnly ){//只读
					 //TODO here set every field control to readonly 
				 } else{//非只读
					 if(subform.hideButtons == dySubFormHideButtons.show){//显示操作按钮，及其事件
						 //对从表的数据进行编辑时，有两种展示方式，一种是直接在jqgrid对表单数据进行编辑,一种是弹出到新窗口再对数据进行编辑
						 var subBtnId = "operateBtn" +   formUuid;//操作按钮的id
						 var btnAddId = "btn_add_" + formUuid; 
						 var btnEditId = "btn_edit_" + formUuid; 
						 var btnDelId = "btn_del_" +  formUuid; 
						 var btnAddElem = '<button  id="' + btnAddId +   '"   >' + dybtn.add  +'</input>' ;
						 var btnEditElem =  '<button  id="' + btnEditId +   '"   >' + dybtn.edit  +'</input>' ;
						 var btnDelElem = '<button id="' + btnDelId +   '" >' + dybtn.del  +'</button>' ;
						 var strbtn1 = '<span id="'+subBtnId+'">';
						 var strbtn2 = '</span>';
						 var strSpace =  '&nbsp;&nbsp;&nbsp;&nbsp;';
						 if(subform.editMode == dySubFormEdittype.newWin){//在新窗口编辑
							  //而对于在窗口中编辑表单数据的需要“编辑“的按钮
							 jqGridCaption += strbtn1 + strSpace + btnAddElem + strSpace + btnEditElem  + strSpace +btnDelElem + strbtn2;
							 
							 _this.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
								 try{
									 
									 _this.editSubformRowDataInDialog( subform, {});
								 }catch(e){
									 alert(e);
								 }
								 return false;
							 });
							 _this.$("#" + btnEditId).live("click", function(){//定义操作事件--添加行 
								 try{
										var ids = _this.$("#" + subform.formUuid).jqGrid('getGridParam','selarrrow');
										if(ids.length != 1){  
											oAlert(dymsg.selectRecordModErr);
											return false;
										}
									var rowData = _this.collectSubformDataOfConcreteRow(formUuid, ids[0]);
									 
									 _this.editSubformRowDataInDialog( subform, rowData);
								 }catch(e){
									 alert(e);
								 }
								 return false;
							 });
							 
						 }else{//直接在jqgrid编辑
							//对于直接在jqgrid对表单数据进行编辑的方式，不需要“编辑“的按钮,直接在jqgrid中编辑即可
							 jqGridCaption += strbtn1 + strSpace + btnAddElem + strSpace + btnDelElem + strbtn2; 
							 
							 _this.$("#" + btnAddId).live("click", function(){//定义操作事件--添加行 
								 try{
									 _this.addSubformRowDataInJqGrid(formUuid);
								 }catch(e){
									 alert(e);
								 }
								 return false;
							 });
							  
						 }
						  
						 _this.$("#" + btnDelId).live("click", function(){//定义操作事件--删除行 
							 try{
								 _this.deleteSubformRowDataEvent(formUuid);
							 }catch(e){
								 alert(e);
							 }
							 
							 return false;
						 });
						
					 }
				 }
				 
				//展示jqGrid
				 $(this).jqGrid({
					beforeEditCell:function(){},
					datatype:'local',
					data:[],
			        autowidth: true,  
					colNames:jqGridColNames,
					colModel:jqGridColModels,
					scrollOffset : 0,
					multiselect:true, 
					//cellEdit:true,
					cellsubmit:'clientArray',
					shrinkToFit:true,
					scrollOffset : 0,
					height : 'auto',
					grouping:true,
				   	groupingView : {
				   		//groupField : [],//groupArray,
				   		//groupColumnShow : [false]
				   	},
				   	afterEditCell:function(){},
				   	loadComplete:function(){},
				   	caption:"<span class='collapsesubform' id='openDiv'></span>" +  jqGridCaption,
				   	gridComplete:function(){
				   		console.log("subform load gridComplete  ");
				   	},
				   	afterInsertRow:function( rowId, rowdata, rowelem){
				   		 
					   	var colModels = $(this).jqGrid('getGridParam','colModel'); 
					   	for(var j = 0 ; j < colModels.length; j ++){
							var model = colModels[j];
							var fieldName = model.name;
							if(!$.dyform.isControlFieldInSubform(fieldName) ){
								continue;
							}
							
							var id = $.dyform.getCellId(rowId, fieldName);
						 
							var fieldDefinitionCopy = {};
							var fieldDefinition = subFormDefinition.fields[fieldName]; 
							$.extend(fieldDefinitionCopy, fieldDefinition); 
							fieldDefinitionCopy.name = id;
							
							var cellValue = rowdata[fieldName];
							fieldDefinitionCopy.formUuid = subFormDefinition.uuid;
							$.ControlManager.createControl(fieldDefinitionCopy); 
						 
							var control = $.ControlManager.getControl(id);
							
							$.dyform.setValue(control, cellValue);
							
							control.setDisplayAsLabel();
							(function(control){
								control.bind("blur", function(){  
									//control.setDisplayAsCtl();
									
									control.setDisplayAsLabel(); 
								});
							})(control);
							
							/*if(subform.editMode == dySubFormEdittype.newWin){//在新窗口编辑，所以在jqgrid里面直接展示文本即可
								control.setDisplayAsLabel();
							} */
					   	}
					    
					  	//alert (	_this.$("#" + rowId ).css("background", null));
				   	},
				   	onCellSelect:function(rowId, iCol,  cellcontent, e){ 
				   		if((options.readOnly ) //调用者要求只读
				   				|| subform.editMode == dySubFormEdittype.newWin//编辑直接在窗口中
				   				|| subform.hideButtons != dySubFormHideButtons.show//在定义中配置了不让编辑
				   		){
				   			return;
				   		}
				   		var colModels = $(this).jqGrid('getGridParam','colModel'); 
				   		var colModel = colModels[iCol];
				   		var fieldName = colModel.name;
				   		if(!$.dyform.isControlFieldInSubform(fieldName) ){
							return;
						}
				   		
				   		var id = $.dyform.getCellId(rowId, fieldName);
				   		var control = $.ControlManager.getControl(id);
				   		
				   		if(control == undefined){
				   			return;
				   		}
				   		var name = control.getCtlName();
				   		
				   		control.setEditable();
				   		
				   		
				   		//如果去掉下面的代码，第一次点击后，在光标移开之后没办法再还原为label状态
				   		var dom = _this.$("input[name='" + name + "']")[0];
				  /* 	 _this.$("input[name='" + name + "']").blur(function(){
				   		 alert("blur");
				   	 });*/
				   		dom.focus();
				   		var hadBeenFocused = dom.getAttribute("focus"); 
				   		if(typeof hadBeenFocused == "undefined" || hadBeenFocused == null){
				   			dom.setAttribute("focus", true); 
				   			dom.blur();
				   			dom.focus();
				   		}
				   		
				   		 
				   	}
				 
				});
				
				 
				 _this.$(".ui-jqgrid-titlebar-close").hide();//将jqgrid默认的collapse/expand按钮隐藏掉
				
				 _this.$(".collapsesubform").unbind("click");
				 _this.$(".collapsesubform").bind("click", function(){//设置自定义的collapse/expand按钮
					var $siblings = $(this).parent().parent().siblings();
					var id = $(this).attr("id");
					if(id == "openDiv"){
						$siblings.hide();
						 $(this).attr("id", "notOpenDiv");
						 $(this).next("span").hide();//隐藏操作按钮
					}else{
						$siblings.show(); 
						 $(this).next("span").show();//展示操作按钮
						 $(this).attr("id", "openDiv");
					}
				});
			});
		},
		
		
		
		/**
		 * 缓存在这里集中管理,数据从这里保存和获取,统一入口和出口
		 */
		cache:{
			put:function(type, data){
				switch(type){
					case cacheType.formUuid://表单定义的uuid
						$(this).data("formUuid", data);
						break;
					case cacheType.options://表单的一些设置
					 
						$(this).data("options", data);
						break;
					case cacheType.formDefinition:
						var formDefinitions = $(this).data("formDefinition");
						if(typeof formDefinitions == "undefined" || formDefinitions == null){
							formDefinitions = {};
						}
						formDefinitions[data.uuid] = data;
						$(this).data("formDefinition", formDefinitions);
						break;
					case cacheType.dataUuid:
						$(this).data("dataUuid", data);
						break;
					default:
						throw new Error("unknown cacheType when put data into cache"); 
					
				} 
			},
			get:function(type, key){
				var data = null;
				 
				switch(type){
					case cacheType.formUuid://表单定义的uuid
						data = $(this).data("formUuid");
						break;
					case cacheType.options://表单的一些设置
						data = $(this).data("options");
						 
						break;
					case cacheType.formDefinition:
						
						//这里需要知道欲获取哪个表单的定义，所以需要通过key将表单定义的uuid传进来
						if(typeof key == "undefined" || key == null){
							throw new Error("parameter[key] should be passed");
						}
						
						var formDefinitions = $(this).data("formDefinition");
						if(typeof formDefinitions == "undefined" || formDefinitions == null){
							throw new Error("there is no form definition in the cache");
						}
						 
						data = formDefinitions[key];
						
						break;
					 case cacheType.dataUuid:
						data = $(this).data("dataUuid");
						break;
					default:
						throw new Error("unknown cachetype when get data from cache");
						 
				}
				return data;
			}
		},
		validateMainform:function(formUuid){
			var formDefinition = this.cache.get.call(this, cacheType.formDefinition, formUuid);//从缓存中取得定义
			var rulesObj = {rules:{}, messages:{}};
			var _this = this;
			this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");
				var control = $.ControlManager.getControl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true;
				}
				
				var rule = control.getRule();
				var message =  control.getMessage();
				if(rule == null || message == null){
					return true;
				}
				
				var ruleObj =  eval("(" + rule +")");
				var messageObj = eval("(" + message +")");
				console.log("开始设置验证规则");
				_this.setCustomValidateRule(ruleObj, messageObj, formUuid, _this.getDataUuid4Mainform(), fieldName, formDefinition.name, control);
				console.log("设置验证规则完毕");
				
				var ctlName = control.getCtlName();
				rulesObj.rules[ctlName] = ruleObj;
				rulesObj.messages[ctlName] = messageObj;
				
				
			});
			return rulesObj;
		},
		validateSubform:function(formUuid){ 
			var rulesObj = {rules:{}, messages:{}};
			var formDefinition = this.cache.get.call(this,cacheType.formDefinition, formUuid);
			var subforms = formDefinition.subforms;
			for(var i in subforms){//遍历各从表
				var subform = subforms[i];
				var subformUuid = subform.formUuid;
				var formDefinition = this.cache.get.call(this, cacheType.formDefinition, subformUuid);
				var datas  = this.$("#" + subformUuid).jqGrid("getRowData");
				var colModels = this.$("#" + subformUuid).jqGrid('getGridParam','colModel');
				for(var j = 0; j < datas.length; j++){//遍历从表的各行
					var data = datas[j];
					var rowId = data["id"];
					for(var k = 0 ; k < colModels.length; k ++){
						var model = colModels[k];
						var fieldName = model.name;
						if(!$.dyform.isControlFieldInSubform(fieldName)){//不获取序号
							continue;
						}
						
						
						var cellId = $.dyform.getCellId(rowId, fieldName);
						  
						 
						var control = $.ControlManager.getControl(cellId);
						//control.setEditable();
						var rule = control.getRule();
						var message =  control.getMessage();
						if(rule == null || message == null){
							continue;
						}
						
						var ruleObj =  eval("(" + rule +")");
						var messageObj = eval("(" + message +")");
						
						this.setCustomValidateRule(ruleObj, messageObj,subformUuid, rowId, fieldName, formDefinition.name, control);
						
						var ctlName = control.getCtlName();
						rulesObj.rules[ctlName] =  ruleObj;
						rulesObj.messages[ctlName] = messageObj;
						
						
						
					} 
				}  
			}
			return rulesObj;
		},
		/**
		 * 获取从表的数据
		 * @param formUuid 从表定义Uuid
		 */
		collectSubformData:function(formUuid){
			var subformDatas = [];
			
			//var formDefinition =  this.cache.get.call(this,cacheType.formDefinition, formUuid);
			var datas  = this.$("#" + formUuid).jqGrid("getRowData");
			 
			for(var i = 0; i < datas.length; i ++){
				var subformData = {}; 
				var data = datas[i];
				
				var rowId = data["id"]; 
				subformData = this.collectSubformDataOfConcreteRow(formUuid, rowId);
				
				subformDatas.push(subformData);
			}
			 
			return subformDatas;  
		},
		
		collectSubformDataOfConcreteRow:function(formUuid, rowId){
			var subformData = {};  
			subformData["uuid"] = rowId;
			var colModels = this.$("#" + formUuid).jqGrid('getGridParam','colModel');
			var formDefinition =  this.cache.get.call(this,cacheType.formDefinition, formUuid);
			for(var j = 0 ; j < colModels.length; j ++){
				var model = colModels[j];
				var fieldName = model.name;
				if(!$.dyform.isControlFieldInSubform(fieldName)){ 
					continue;
				}
				 
				var cellId = $.dyform.getCellId(rowId, fieldName);
				 
				var control = $.ControlManager.getControl(cellId);
				
				var field = formDefinition.fields[fieldName];
				if(typeof field == "undefined"){
					console.log( " unknown fieldName[" + fieldName + "]");
					continue;
				}
				
				/*if(control.isValueMap() ){
					//这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
					value =   JSON.stringify(control.getValueMap());
				}else{
					value = control.getValue();
				}*/
				subformData[fieldName] = $.dyform.getValue(control);
			}
			return subformData;
		},
		
		/**
		 * 获取主表数据
		 * @param formUuid
		 */
		collectMainformData: function(formUuid){
			var formData = {};
			this.$(".value[name]").each(function(){//遍历所有的占位符
				var fieldName = $(this).attr("name");
				var control = $.ControlManager.getControl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true;
				}
				 
			 
				/*var value = null;
				
				if(control.isValueMap() ){
					//这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
					value = JSON.stringify(control.getValueMap());
				}else{
					value = control.getValue();
				}
				*/
				formData[fieldName] = $.dyform.getValue(control);; 
			});
			
			var dataUuid = this.getDataUuid4Mainform();//先从缓存中获取
			if(typeof dataUuid == "undefined"){
				dataUuid = this.createUuid();
				this.setDataUuid4Mainform(dataUuid);
			}
			formData["uuid" ] = dataUuid;//主表的uuid
			
			return formData;
		},
		editSubformRowDataInDialog:function(subform, data){
			//点击添加按钮，然后在新窗口中编辑数据，点击确定之后在jqgrid中为从表添加一行
			//同时再将数据填充至该行中
			var formUuid = subform.formUuid;
			var formDefinition = this.cache.get.call(this, cacheType.formDefinition, formUuid);
			var colModels = this.$("#" + formUuid).jqGrid('getGridParam','colModel');
			var isAdd = false;
			var uuid = data["uuid"] ;
			console.log(JSON.stringify(data));
			if(typeof data == "undefined" || data == null || typeof uuid == "undefined"){
				isAdd = true;
				//添加行
				uuid = this.createUuid();
				data["id"] = uuid;
				var formData = this.loadDefaultFormData(formUuid);
				for(var j = 0 ; j < colModels.length; j ++){
					var model = colModels[j];
					var fieldName = model.name;
					var value = formData[fieldName];
					if(typeof value != "undefined" && value != null){
						data[fieldName] = formData[fieldName];
					}
				}
			}else{ 
				data["id"] = uuid;
			}
			
			
			//定义窗口内容 
			this.find("#dialog" + uuid).remove();
			this.append("<div id='dialog" + uuid + "' title=" +  subform.displayName+ "("+dybtn.add+")></div>"); 
			
			//this.$("#" + uuid);
			var html ="<div class='dyform'>";
			html += "<table></table>";
			this.$("#dialog" + uuid).append(html);
			
			var fields = subform.fields; 
			var controlInfo = {};
			for(var j = 0 ; j < colModels.length; j ++){
				var model = colModels[j]; 
				var fieldName = model.name;
				if(!$.dyform.isControlFieldInSubform(fieldName)){
					continue;
				}
				var field = fields[fieldName];
				var displayName = field.displayName;
				var controlId = $.dyform.getCellId(uuid + "_", fieldName);
			 
				var h = "";
				h += "<tr><td >";
				h += displayName;
				h += "</td>";
				h += "<td>";
				h += "<span id='" + controlId +"'><input class='value' name='" + controlId +  "'/></span>";
				h += "</td></tr>";
				this.$("#dialog" + uuid + " .dyform").find("table").append(h);
				
				var fieldDefinition = formDefinition.fields[fieldName];
				var fieldDefinitionCopy = {}; 
				$.extend(fieldDefinitionCopy, fieldDefinition); 
				fieldDefinitionCopy.name = controlId;
			 
				 $.ControlManager.createControl(fieldDefinitionCopy);
				 var control =  $.ControlManager.getControl(controlId);
				 controlInfo[fieldName] = control;
				 var value = data[fieldName];
				  
				 if(typeof value == "undefined"){
					 continue;
				 } 
				 
				 $.dyform.setValue(control, value); 
			}
			
			var _this = this;
			 var buttons = {};
			buttons[dybtn.save] = function(){
				for(var j = 0 ; j < colModels.length; j ++){
					var model = colModels[j]; 
					var fieldName = model.name;
					if(!$.dyform.isControlFieldInSubform(fieldName)){
						continue;
					}  
				 
					 data[fieldName] =  $.dyform.getValue(controlInfo[fieldName]); 
					 console.log(fieldName + "=" + data[fieldName]);
				}
				if(isAdd){
					_this.addRowData( formUuid, data);
				}else{ 
					_this.updateRowData( formUuid, data); 
				}
				$(this).dialog("close"); 
				_this.$("#dialog" + uuid).remove();
			};
			
			buttons[dybtn.cancel] = function(){
				$(this).dialog("close");
				_this.$("#dialog" + uuid).remove();
			};
			
			//显示窗口
			this.$("#dialog" + uuid ).dialog( {
						buttons:buttons, 
						height: 300,
						width: 700,
						modal: true 
			});
			 
			
			
			//this.addRowData( formUuid, data);
			
		}, 
		/*editSubformRowDataInDialog:function(formUuid,subform){
			//点击添加按钮，然后在新窗口中编辑数据，点击确定之后在jqgrid中为从表添加一行
			//同时再将数据填充至该行中
			var colModels = this.$("#" + formUuid).jqGrid('getGridParam','colModel');
			var uuid = this.createUuid();
			var data = {};
			data["id"] = uuid;
			var formData = this.loadDefaultFormData(formUuid);
			for(var j = 0 ; j < colModels.length; j ++){
				var model = colModels[j];
				var fieldName = model.name;
				var value = formData[fieldName];
				if(typeof value != "undefined" && value != null){
					data[fieldName] = formData[fieldName];
				} 
			}
			
			this.find("#" + uuid).remove();
			this.append("<div id='" + uuid + "' title=" +  subform.displayName+ "("+dybtn.edit+")></div>"); 
			
			
			//this.addRowData( formUuid, data);
			
		}, */
		addSubformRowDataInJqGrid:function(formUuid){ //点击添加从表行数据的按钮事件 
			var colModels = this.$("#" + formUuid).jqGrid('getGridParam','colModel');
			var uuid = this.createUuid();
			var data = {};
			data["id"] = uuid;
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
		deleteSubformRowDataEvent:function(formUuid){//点击删除从表行数据的按钮事件 
			var _this = this;
			var $dg = this.$('#' + formUuid);
			var ids = $dg.jqGrid('getGridParam','selarrrow');
			if(ids.length > 0){
				$.jBox.confirm(dymsg.delConfirm,dymsg.tipTitle,function (v,h,f){
					if(v){
						for(var i=(ids.length-1);i>=0;i--){ 
							_this.delRowData( formUuid, ids[i]);
						}
					}
				},{buttons:{'是':true,'否':false}});
			}else{
				$.jBox.info(dymsg.selectRecordDel,dymsg.tipTitle);
			}
		},
		updateSeqNoOfSubform:function(formUuid){
			var data  = this.$("#" + formUuid).jqGrid("getRowData");
			var count = this.$("#" + formUuid).jqGrid("getGridParam", "reccount");
			count ++;
			for(var i = 0; i < data.length; i++){
				count --;
				var rowData = data[i];
				//rowData["seqNO"];
				var id = rowData["id"]; 
				/*if(count%2 != 1 ){
					this.$("#" + id).css("background", "#f7f7f7");
				}*/
				this.$("#" + formUuid).jqGrid("setCell", id, "seqNO", count );
			}
		},
		createUuid:function(){
			return  new UUID().id.toLowerCase(); 
		},
		loadDefaultFormData: function(formUuid){ 
			 
			var formData = {};
			var url = ctx + "/dyformdata/getDefaultFormData";
			$.ajax({
				url:url,
				cache : false,
				async : false,//同步完成
				type : "POST",
				data : {formUuid: formUuid} ,
				dataType : "json",
				success:function (result){
					if(result.success == "true" || result.success == true){ 
						  formData =  result.data;  
		  		   }else{
		  			    
		  		   }
				},
				error:function(data){
					 
				}
			});
			return formData;
		},
		/**
		 * 设定自定义的校验规则
		 * @param ruleObj
		 * @param messageObj
		 * @param rowId
		 * @param fieldName
		 * @param control
		 */
		setCustomValidateRule:function(ruleObj, messageObj, formUuid, dataUuid, fieldName, tblName, control){
			console.log("2"); 
			var newRuleObj = {};
			var newMessageObj = {};
			for(var i in ruleObj){
				//var ruleItem =  ruleObj[i];
				if(i == "unique"){//唯一性验证  
					 
					newRuleObj["isUnique"] = {
							url : ctx + "/dyformdata/validate/exists",
	    					type : "POST",
	    					async: false,
	    					formUuid: formUuid,
	    					$form : this,
	    					data : {
		    					uuid:  dataUuid,
		    					tblName : tblName,//表单名称
		    					fieldName : fieldName, 
		    					fieldValue :  function(){
		    					 /*
		    						if(control.isValueMap()){
		    							value = JSON.stringify(control.getValueMap());
		    						}else{
		    							value = control.getValue();
		    						}*/
		    						return  $.dyform.getValue(control);;
		    					}
	    					}
					};
					newMessageObj["isUnique"] = messageObj[i];
					
					delete ruleObj[i];//删除unique
				}
			}
			$.extend(ruleObj, newRuleObj);
			$.extend(messageObj, newMessageObj);
		}
	});
	
	
	
	
});
 