//加载全局国际化资源
//I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
//I18nLoader.load("/resources/pt/js/dyform/dyform");
 
var cacheType={
	formUuid:1,//主表的formuuid
	options:2,//表单的选项参数
	formDefinition:3,//主表、从表的定义
	dataUuid:4,//主表的datauuid,
	deletedFormDataOfSubform:"deletedFormDataOfSubform",//被删除的从表的数据uuid
	formula:"formula"//运算公式
	 
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
		 *置于jqgrid中，从表的各cell的id 
		 **/
		getCellId:function(rowId, fieldName){
			return $.wSubFormMethod.getCellId(rowId, fieldName);
		}
		,
		setValue:function(control, cellValue, dataUuid){
			if(!control){
				return;
			}
			control.setDataUuid(dataUuid);
			 
			if(control.isValueMap()){
				if(cellValue != null && cellValue.indexOf("{") == -1){
				/*	var v = cellValue.split(";");
					var v1 = {};
					for(var i = 0; i < v.length; i++){
						v1[v[i]] = null;
					}*/
					//control.setValueByMap(JSON.cStringify(v1)); 
					control.setValue(cellValue); 
				}else{
					control.setValueByMap(cellValue); 
				}
			
			}else{
				control.setValue(cellValue); 
			}
		},
		getValue:function(control){
			if(control == null){
				console.log("control is null");
				return;
			}
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
		getDisplayValue : function(control) {
			if (control == null) {
				console.log("control is null");
				return;
			}
			var value = "";
			if (control.isValueMap()) {
				// 这些控件类型有显示值，需要将显示值及真实值一起保存至数据库中
				value = control.getDisplayValue();
			} else {
				value = control.getValue();
			}
			return value;
		},
	    modelReg : /\${([^}]*)}/i,  //显示单据占位符
	    formulaReg:/\${([^}]*)}/g //运算符中字段占位符
	});
	
	
	$.fn.dyform = function(options){ 
		if(this.size() == 0){
			throw new Error("zero elements found by selector["  + this.selector + "]");
		}if(this.size() > 1){
			throw new Error("more than one element found by selector["  + this.selector + "]");
		}else if(this[0].nodeName != "FORM"){
			throw new Error("dataform container must be a form html element");
		}
		
		this.attr("class", "dyform");
		
		
		if(typeof options == "string"){//字段串类型 
			var fn = $.dyform.getAccessor($.fn.dyform,options);
			if (!fn) {
				throw ("jqGrid - No such method: " + options);
			}
			
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this,args);
		}else{//如果只传定义,默认为定义解析接口 
			//this.dyform("parseFormDefinition", arguments);
			
		 
			var fn = $.dyform.getAccessor($.fn.dyform, "parseForm");  
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
		addCAControlObject:function(){
			if(this.isEnableSignature() && $.browser && $.browser.msie){
				/*alert(1);
				 var fjcaWs = "<object id=\"fjcaWs\" name=\"SBFjCAEnAndSign\" classid=\"CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709\" codebase=\"" + ctx + "/resources/pt/js/security/SBFjCAEnAndSign.ocx\"></object>";
				 var fjcaControl = "<object id=\"fjcaControl\" classid=\"clsid:414C56EC-7370-48F1-9FB4-AF4A40526463\" codebase=\"" + ctx + "/resources/pt/js/security/fjcaControl.ocx\" ></object>";
				 this.append(fjcaWs);
				 this.append(fjcaControl);*/
			}
		}
		,
		parseForm : function(options){ 
			console.log("开始解析表单定义");
			var time1 =( new Date()).getTime();
			var formDefinition = options.definition;
			
			if(typeof formDefinition == "string"){//字段串类型 
				formDefinition =  (eval("(" + formDefinition +  ")"));
			}
			 
			var data = options.data;
			var defaults = {
					displayAsLabel:false,//是否以标签的形式展示
					displayAsFormModel:true,//是否用单据展示,在displayAsLabel为true的前提下，
											//该参数才有效,true时表示使用显示单据,false表示不使用显示单据，直接使用表单的模板
					success:function(){ },
					error:function(){ }
			};
			
			
			$.extend(defaults, options);
			
			//将当前容器的对应的表单定义相关数据缓存起来,当需要获取缓存中的数据时，就需要通过key去获取
			//key是什么，需要到缓存$.dyform.cache代码去看,方便统一管理缓存 
			this.cache.put.call(this, cacheType.formUuid, formDefinition.uuid);
			$.extend(formDefinition, formDefinitionMethod);
			this.cache.put.call(this, cacheType.formDefinition, formDefinition);
			this.cache.put.call(this, cacheType.options, defaults);
			
			
			
			this.addCAControlObject();
			
			this.validSignatureUSB();
			
			var  displayFormModelId = formDefinition.displayFormModelId;
			 
			 if(this.isDisplayAsModel()){//判断是否用显示单据  
				var model = loadDisplayModelDefintionByModelId(displayFormModelId);
				if(model == null || model == undefined){//无法找到对应的单据
					defaults.error.call(this, dymsg.modelIdNotFound);
					return;
				}
				$(this).html(model.html);
				this.formatModel(formDefinition);//将单据信息格式化成表单解析的格式 
			}else{ 
				
				//去掉占位符中的src属性
				 var html = "<span id='_htmldyform_'>" + formDefinition.html + "</span>";
				 var srcpattern = /src="\/resources[^\"]+ckeditor[^\"]+plugins[^\"]+images[^\"]+jpg\"/;
				 var srcpattern2 = /src="resources[^\"]+ckeditor[^\"]+plugins[^\"]+images[^\"]+jpg\"/;
				 html = html.replace(srcpattern, ""); 
				 html = html.replace(srcpattern2, "");
				 while(srcpattern.test(html) || srcpattern2.test(html) ){
					 html = html.replace(srcpattern, ""); 
					 html = html.replace(srcpattern2, "");
				 }
				 
				 
					
					// console.log(html);
					 /*	var $html = $(html);
				$html.find(".value[src]").each(function(){
					 
					$(this).attr("src", "");
					 
				});*/
				// alert($(html).html())
				$(this).html($(html).html()); 
				this.parseBlock(formDefinition);
				//$(this).html(formDefinition.html);
			}  
			
			$(".label").removeClass("label");
			
			
			
			
			var time1 =( new Date()).getTime();
			
			
			//含有formUuid属性的table即为从表,解析各从表
			var formUuids = [];
			this.$("table[formUuid]").each(function(){
				var formUuid=$(this).attr('formUuid');
				formUuids.push(formUuid);
			});
			
			//var datas = loadFormDefinitionsAndDefaultFormData(formUuids, formDefinition.uuid);
			
			var formData = formDefinition["defaultFormData"]; 
			 
			var definitionObjs =  formDefinition["subformDefinitions"];
			var definitionMap = {};
			 
			if(typeof definitionObjs != "undefined" && definitionObjs != null && definitionObjs.length > 0){
				for(var i = 0; i < definitionObjs.length; i++){ 
					var definitionObj = eval("(" + definitionObjs[i] + ")");
					$.extend(definitionObj, formDefinitionMethod);
					definitionMap[definitionObj.uuid] = definitionObj;
				}
			}
			 
			var time2 =( new Date()).getTime();
			console.log("加载默认值及从表定义所用时间:" + (time2 - time1)/1000.0 + "s");
			
			
			this.parseMainForm(formDefinition, formData);//解析主表
			
			this.parseSubform(formDefinition, definitionMap);//解析从表
			
			var time2 = ( new Date()).getTime();
			console.log("表单解析所花时间:" + (time2 - time1)/1000.0 + "s"); 
		 
			this.fillFormData(data, function(){ });
			var time3 = ( new Date()).getTime();
			console.log("表单填充数据所花时间:" + (time3 - time2)/1000.0 + "s");
			var timex =( new Date()).getTime();
		 	if(this.isDisplayAsLabel()  ){ //显示为标签 
				this.showAsLabel();
			} 
		 	var timey =( new Date()).getTime();
		 	console.log("显示为标签所用时间:" + (timey - timex)/1000.0 + "s");
			 
			var _this = this;
			 
			$(window).resize(function(){
				var formUuid =  _this.getFormUuid(); 
				var formDefinition =  _this.cache.get.call(_this, cacheType.formDefinition, formUuid);
				 
				var subforms = formDefinition.subforms;
				for(var i in subforms){
					var subform = subforms[i];
						var subformctl=$.ControlManager.getSubFormControl(subform.formUuid);
						//subformctl.setGridWidth(formWidth);
						if(subformctl)
						subformctl.resetGridWidth(); 
				}
			});
			var timex = new Date();
			defaults.success.apply(this);
			var timey = new Date();
			console.log("回调所花时间:" + (timey - timex)/1000.0 + "s");
			try{
				if(typeof this[formDefinition.outerId] == "function"){
					 this[formDefinition.outerId]();//初始化扩展函数,在dyform_custom.js文件中
				}
			}catch(e){
				console.log(e);
			}
			var timez = new Date();
			
			console.log("自定义js所花时间:" + (timez - timey)/1000.0 + "s");
			
			 
			console.log("表单共花时间:" + (timez - time1)/1000.0 + "s");
			
			if(this.isCreate()){
				this.addRowEmptyDatasByDefaultRowCount();//给从表添加默认行
			}
			if(this.formParseComplete){
				this.formParseComplete();
			}
		},
		//获取可选参数
		getOptional:function(){
			var  options =  this.cache.get.call(this, cacheType.options);
			return options.optional;
		},
		
		//是否新建
		isCreate:function(){
			if(this.getOptional() && this.getOptional().isFirst){
				return true;
			}else{
				return false;
			}
		},
		
		formatModel:function(formDefinition){
			$(".model_field[name]").each(function(){
					var name = $(this).attr("name");
					if(name == undefined && $.trim(name).length == 0){
						return true;
					}
					
					var fieldDefinition = formDefinition.fields[name];
					if(typeof fieldDefinition == "undefined"){
						return true;//continue;
					}
					
					fieldDefinition.showType = dyshowType.showAsLabel;//显示为标签
					//将占位符转换成表单解析的格式
					var titleHtml = $(this).html();
					titleHtml = titleHtml.replace($.dyform.modelReg, "<img class='value' name='" + name + "'/>");
					$(this).html(titleHtml);
			});
			
			var _this = this;
			//含有formUuid属性的table即为从表,解析各从表
			this.$("table[formUuid]").each(function(){
				$.ControlManager.createSubFormControl($(this),_this,formDefinition);
			});
			
			$(".model_subform[name]").each(function(){
				var name = $(this).attr("name");
				if(name == undefined && $.trim(name).length == 0){
					return true;
				}
				var subDefinitions  = formDefinition.subforms;
				if(typeof subDefinitions == "undefined" || subDefinitions.length == 0){
					return true;//continue;
				}
				var formUuid = undefined; 
				for(var i in subDefinitions){
					var subDefinition = subDefinitions[i];
					if(subDefinition.outerId == name){
						formUuid = subDefinition.formUuid;
						break;
					}
				}
				if(!formUuid){
					return true;
				}
				
				
				//fieldDefinition.showType = dyshowType.showAsLabel;//显示为标签
				//将占位符转换成表单解析的格式
				var titleHtml = $(this).html();
				titleHtml = titleHtml.replace($.dyform.modelReg, "<table formUuid='" + formUuid + "'></table>");
				$(this).html(titleHtml);
		});
		},
		
		
		isDisplayAsModel:function(){
			var  displayFormModelId = this.getFormDefinition(this.getFormUuid()).displayFormModelId; 
			var  options =  this.cache.get.call(this, cacheType.options);
			if(options.displayAsFormModel == true && options.displayAsLabel == true 
					&& displayFormModelId != undefined && $.trim(displayFormModelId).length > 0){
				return true;
			}else{
				return false;
			}
			// return options.displayAsFormModel == true;
		},
		
		isDisplayAsLabel:function(){
			var  options =  this.cache.get.call(this, cacheType.options);
			return  options.displayAsLabel ;
			// return options.displayAsFormModel == true;
		},
		
		/**
		 * 给主表设置Uuid
		 * @param dataUuid
		 */
		setDataUuid:function(dataUuid){ 
			 this.cache.put.call(this, cacheType.dataUuid, dataUuid); 
		},
		/**
		 * 获取主表Uuid
		 * @param dataUuid
		 */
		getDataUuid:function(){ 
			 return this.cache.get.call(this, cacheType.dataUuid);
		},
		/**
		 * 获取主表的定义id
		 */
		getFormUuid:function(formId){
			if(formId){//从表
				var formDefinitions = this.getFormDefinition();//所有的表单定义
				for(var i in formDefinitions){
					if(formDefinitions[i].outerId == formId){
						return i;
					}
				}
			}else{//主表
				return this.cache.get.call(this, cacheType.formUuid);
			}
			
		},
		/**
		 * 获取主表的定义id 
		 */
		getFormId : function(formUuid) {
			if (formUuid) {// 从表
				var formDefinitions = this.getFormDefinition();// 所有的表单定义
				for ( var i in formDefinitions) {
					if (formDefinitions[i].uuid == formUuid) {
						return formDefinitions[i].outerId;
					}
				}
			} else {// 主表
				return this.getFormId(this.getFormUuid());// 迭代获取FormId
			}
		},		
		getFormDefinition:function(formUuid){
			var formDefinition = this.cache.get.call(this,cacheType.formDefinition, formUuid);
			return formDefinition;
		},
		
		getMainformValueKeySetMap : function() {
			var keyMap = {};
			var fields = this.getFormDefinition(this.getFormUuid()).fields;
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
		
		getMainformValueFormatSetMap : function() {
			var keyMap = {};
			var fields = this.getFormDefinition(this.getFormUuid()).fields;
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
		 * 收集表单数据,包括控件数据、签名、被删除的行数据、正文上传
		 * @param formUuid
		 */
		collectFormData:function(){
			console.log("开始收集数据-----" + new Date());
			var time1 = ( new Date()).getTime(); 
			
			var formData = {}; 
			var deletedFormDatas = this.getDeletedRowIds();
			var formDatas =  this.getFormDatas();
			
			if(this.isEnableSignature()){//创建签名
				var signature = this.createSignature(formData);
				formData.signature = signature; 
			}
			
			formData["formDatas"] = formDatas;
			formData["deletedFormDatas"] = deletedFormDatas;
			formData["formUuid"] = this.getFormUuid();
			var time2 = ( new Date()).getTime();
			console.log("表单收集所花时间:" + (time2 - time1)/1000.0 + "s");		
			console.log("收集结束-----" + new Date());
			return formData;
		},
		/**
		 * 收集表单显示数据,包括控件数据、签名、被删除的行数据、正文上传
		 * 
		 * @param formUuid
		 */
		collectFormDisplayData : function() {
			console.log("开始收集数据-----" + new Date());
			var time1 = (new Date()).getTime();

			var formData = {};
			var deletedFormDatas = this.getDeletedRowIds();
			var formDatas = this.getFormDisplayDatas();

			if (this.isEnableSignature()) {// 创建签名
				var signature = this.createSignature(formData);
				formData.signature = signature;
			}

			formData["formDatas"] = formDatas;
			formData["deletedFormDatas"] = deletedFormDatas;
			formData["formUuid"] = this.getFormUuid();
			var time2 = (new Date()).getTime();
			console.log("表单收集所花时间:" + (time2 - time1) / 1000.0 + "s");
			console.log("收集结束-----" + new Date());
			return formData;
		},		
		
		/**
		 * 需要签名返回true
		 */
		isEnableSignature: function(){
			var formUuidOfMainform = this.getFormUuid();
			var formDefinitionOfMainform = this.getFormDefinition(formUuidOfMainform);
			var enableSignature = formDefinitionOfMainform.enableSignature;
			 
			if( enableSignature == signature.enable){
				return true;
			}else{
				return false;
			}
		},
		
		createSignature:function(signedData){ 
			var jsonString = JSON.cStringify(signedData); 
			var signature = {};
			$.ajax({
				url : ctx + "/dyformdata/getDigestValue",
				cache : false,
				async : false,//同步完成
				type : "POST",
				data :  jsonString,
				dataType : "json",
				contentType:'application/json',
				success : function(result) { 
					var dataSignature = result.data;  
					var b = fjcaWs.OpenFJCAUSBKey();
					if(!b){ 
						signature.status = -1;
						signature.remark = "lose to open FJCAUSBKey";  
					}else{
						fjcaWs.ReadCertFromKey();
						var cert = fjcaWs.GetCertData();
						fjcaWs.SignDataWithKey(dataSignature.digestValue);
						var signData = fjcaWs.GetSignData();
						fjcaWs.CloseUSBKey();
						signature.signedData = jsonString;
						signature.digestValue = dataSignature.digestValue;
						signature.certificate = cert;
						signature.signatureValue = signData;
						signature.status = 1;
						signature.digestAlgorithm = dataSignature.digestAlgorithm; 
					} 
				}, 
				 error:function(jqXHR){
						// 数字签名失败
					 
					 console.log("lose to get digestValue");
						var faultData = JSON.parse(jqXHR.responseText);
						signature.status = -1;
						signature.remark = faultData.msg; 
				 } 
			});
			return signature;
		},
		
		/**
		 * 验证表单数据
		 * @param formUuid
		 */
		validateForm: function(){ 
			if(!this.validSignatureUSB()){
				return false;
			}
			var formUuid =  this.getFormUuid();
			var dataUuid = this.getDataUuid();//先从缓存中获取
			var valid1 = this.validateMainform( formUuid);
			var valid2 =  this.validateSubform(formUuid);
			return valid1 && valid2;
			 
		},
		/**
		 * 校验签名key
		 */
		validSignatureUSB:function(){  
			if(this.isEnableSignature()){
				if(Browser.isIE()){ 
				}else{
					oAlert("当前浏览器无法对表单数据进行签名，请使用IE浏览器编辑表单!");
				}
				return checkCAKey();
			}
			return true;
		},
		
		
		
		
		/**
		 * 填充数据,数据key为formUuid
		 * @param formDatas
		 * @param callback
		 */
		fillFormData:function(formDatas, callback){
			 
			var formUuid =  this.getFormUuid();//获取当前表单的定义uuid 
			var time1 =( new Date()).getTime();
			
			for(var i in formDatas){
				var formData = formDatas[i]; 
				if(formUuid == i){//填充主表
					this.fillFormDataOfMainform(formData[0]);
				}
			}
			
			var time2 =( new Date()).getTime();
			console.log("填充主表数据所用时间:" + (time2 - time1)/1000.0 + "s");
			var time3 =( new Date()).getTime();
			for(var i in formDatas){
				var formData = formDatas[i]; 
				if(formUuid == i){
				}else{//填充从表
					var subformctl=$.ControlManager.getSubFormControl(i);
					if(!subformctl ){ 
						continue;
					}
					
					subformctl.setMainformDataUuid(this.getDataUuid());
					console.log(JSON.cStringify(formData));
					subformctl.fillFormData(formData);
				}
			}
			
			var time4 =( new Date()).getTime();
			console.log("填充从表数据所用时间:" + (time4 - time3)/1000.0 + "s");
			if(callback){
				callback.apply(this);
			}
		},

		/**
		 * 填充数据,数据key为formUuid,值为显示值
		 * @param formDatas
		 * @param formUuid 数据的定义uuid,没有的话就主表id(应为excel表空间不能有formId)
		 * @param callback
		 */
		fillFormDisplayData : function(formDatas,formUuid, callback) {
			//var formUuid = mainFormUuid;
			if(typeof formUuid == "undefined" || formUuid == null ){
				formUuid = this.getFormUuid();// 获取当前表单的定义uuid
			};
			var time1 = (new Date()).getTime();
			for ( var fi in formDatas) {
				if (formUuid != fi) {continue;}
				var cacheVK = this.getMainformValueKeySetMap();
				var cacheVF = this.getMainformValueFormatSetMap();
				// 主表值键转换
				var formData = formDatas[fi];
				var mainFormData = formData[0];
				for(var key in mainFormData){
					if(typeof cacheVF[key] != "undefined" && cacheVF[key] != ""){//日期格式处理
						var disValue = mainFormData[key];
						var fmt = cacheVF[key];
						try{
							var dateVar = new Date(Date.parse(disValue.replace(/-/g, "/")));
							if(!isNaN(dateVar.getTime())){// 有效日期
								mainFormData[key] = dateVar.format(fmt);
							}
						}catch(e){
							console.log(e);
						}
						continue;//日期控件
					}
					var optionSet = cacheVK[key];
					if(typeof optionSet == "undefined" || optionSet == null){// 不再需要isValueMap判断了
						continue;
					}
					var disValue = mainFormData[key];
					var value = optionSet[disValue];
					if(typeof value == "undefined" || value == null){
						continue;
					}
					mainFormData[key] = value;
				}
				cacheVK = null;
			}

			var time2 = (new Date()).getTime();
			console.log("主表(值->键)解析所用时间:" + (time2 - time1) / 1000.0 + "s");
			var time3 = (new Date()).getTime();
			for ( var di in formDatas) {
				if (formUuid == di) {
				} else {// 填充从表
					var formData = formDatas[di];//从表数据
					if(formData.length <= 0){
						continue;
					}
					var subformctl = $.ControlManager.getSubFormControl(di);
					if (typeof subformctl == "undefined" || subformctl == null) {
						continue;
					}
					var cacheVK = undefined;// 从表级缓存
					var cacheVF = undefined;// 从表级缓存
					for(var fi =0; fi < formData.length;  fi++){
						var subFormData = formData[fi];
						if(typeof cacheVK == "undefined" || cacheVK == null){// 缓存从表VK定义
							cacheVK = subformctl.getSubformValueKeySetMap();
						}
						if(typeof cacheVF == "undefined" || cacheVF == null){// 缓存从表VF定义
							cacheVF = subformctl.getSubformValueFormatSetMap();
						}
						for(var key in subFormData){
							if(typeof cacheVF[key] != "undefined" && cacheVF[key] != ""){//日期格式处理
								var disValue = subFormData[key];
								var fmt = cacheVF[key];
								try{
									var dateVar = new Date(Date.parse(disValue.replace(/-/g, "/")));
									if(!isNaN(dateVar.getTime())){
										subFormData[key] = dateVar.format(fmt);
									}
								}catch(e){
									console.log(e);
								}
								continue;//日期控件
							}
							var optionSet = cacheVK[key];
							if(typeof optionSet == "undefined" || optionSet == null){//没有控件缓存则isValueMap为false
								continue;
							}
							var disValue = subFormData[key];
							var value = optionSet[disValue];
							if(typeof value == "undefined" || value == null){
								continue;
							}
							subFormData[key] = value;
						}
					}
					cacheVK = null;
					cacheVF = null;
				}
			}
			
			var time4 = (new Date()).getTime();
			console.log("从表主表(值->键)解析所用时间:" + (time4 - time3) / 1000.0 + "s");
			this.fillFormData(formDatas,callback);
		},			
		/**
		 * 填充数据,数据key为formID
		 * @param formDatas
		 * @param callback
		 */
		fillFormDatas:function(formDatas, callback){ 
			var formDatasKeyUuid = {}; 
			for(var i in formDatas){ 
				var formData = formDatas[i];
				var uuid = this.getFormUuid(i);  
				formDatasKeyUuid[uuid] = formData;
			}
			 this.fillFormData(formDatasKeyUuid, callback);
		},
		/**
		 * 填充数据,数据key为formID,value为显示值
		 * 
		 * @param formDatas
		 * @param mainFormId为主表数据ID
		 * @param callback
		 */
		fillFormDisplayDatas : function(formDatas, mainFormId, callback) {
			if(typeof mainFormId == "undefined" || mainFormId == null){
				mainFormId = this.getFormId();// 获取主表ID,不是UUID
			}
			var formDatasKeyUuid = {};
			for ( var i in formDatas) {
				if(typeof i == "undefined" || i == null){
					continue;
				}
				var formData = formDatas[i];
				var uuid = "";
				if(mainFormId == i){
					uuid = this.getFormUuid();//主表formUuid为当前表单Uuid
				}else{
					uuid = this.getFormUuid(i);
				}
				formDatasKeyUuid[uuid] = formData;
			}
			this.fillFormDisplayData(formDatasKeyUuid, callback);
		},		
		/**
		 * 为主表填充数据
		 * @param formData
		 */
		fillFormDataOfMainform: function(formData){  
			 this.setDataUuid(formData.uuid);//将dataUuid缓存   
			 var _this = this;
			 this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");  
				var value = formData[fieldName];
				if(typeof value == "undefined"){
					return true;
				}
				_this.setFieldValueByFieldName(fieldName, value); 
			}); 
		}, 
		
	
		createUuid:function(){
			return  new UUID().id.toLowerCase(); 
		},
		/**
		 * 整个表单设置为只读
		 */
		setReadOnly:function(){
			 this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");
				var control = $.ControlManager.getCtl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true; //continue;
				}
				control.setReadOnly(true);
			}); 
			this.$("table[formUuid]").each(function(){
				var subformctl=$.ControlManager.getSubFormControl($(this).attr("formUuid")); 
				subformctl.setReadOnly();
			});
			this.invoke("afterSetReadOnly");
		}, 
		/**
		 * 设置上传文本文件时同时生成swf副本
		 */
		setTextFile2SWF:function(enable){
			var formUuidOfMainform = this.getFormUuid();
			var formDefinitionOfMainform = this.getFormDefinition(formUuidOfMainform);
			this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");
				var control = $.ControlManager.getCtl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true; //continue;
				}
				 
				if(formDefinitionOfMainform.isInputModeAsAttach(fieldName)){
					control.setTextFile2SWF(enable);
				}
			});
			
			this.$("table[formUuid]").each(function(){
				var subformctl=$.ControlManager.getSubFormControl($(this).attr("formUuid")); 
				subformctl.setTextFile2SWF();
			});
		},
		setEditable:function(){ 
			 this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");  
				var control = $.ControlManager.getCtl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true; //continue;
				}
				control.setEditable();
			}); 
			
			this.$("table[formUuid]").each(function(){
				var subformctl=$.ControlManager.getSubFormControl($(this).attr("formUuid"));
				subformctl.setEditable();
			});
			this.invoke("afterSetEditable");
		},
		
		showAsLabel:function(){
			var _this = this;
			 this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");  
				var control = _this.getControl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true; //continue;
				}
				control.setDisplayAsLabel();
			}); 
			this.$("table[formUuid]").each(function(){ 
				var subformctl=_this.getSubformControl($(this).attr("formUuid"));
				subformctl.setDisplayAsLabel();
			});
			
			this.invoke("afterShowAsLabel");
		},
		/**
		 * 处理异常
		 */
		handleException:function(exceptionData){
			
		},
		enableSignature:function(enable){
			var _this = this;
			var formUuidOfMainform = this.getFormUuid();
			var formDefinitionOfMainform = this.getFormDefinition(formUuidOfMainform);
			
			if(enable){
				formDefinitionOfMainform.enableSignature  = signature.enable
			}else{
				formDefinitionOfMainform.enableSignature  = signature.disable
			}
			 
			this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");
				var control = _this.getControl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true; //continue;
				}
				 
				if(formDefinitionOfMainform.isInputModeAsAttach(fieldName)){
					control.enableSignature(enable);
				}
			});
			
			this.$("table[formUuid]").each(function(){  
				var subformctl=_this.getSubformControl($(this).attr("formUuid"));
				subformctl.enableSignature(enable);
			});
		}
		
		 
	});
	
	/**
	 * 设置字段属性值接口
	 */
	$.dyform.extend({
		getFieldNameByApplyTo:function(applyTo, formUuid){
			if((typeof applyTo == "object") && (typeof applyTo["fieldMappingName"]) != "undefined"){
				applyTo = applyTo["fieldMappingName"];//这里为了兼容旧系统
			}
			
			if(typeof formUuid == "undefined" || formUuid == null){
				formUuid == this.getFormUuid();
			}
		 
			var formDefinition = this.getFormDefinition(formUuid);
			 
			var applyTo2FieldName = formDefinition["applyTo2FieldName"];
			if (typeof applyTo2FieldName == "undefined") {
			 
				applyTo2FieldName = {};
				var fields = formDefinition["fields"];
			 
				for (var fieldName in fields) {
					var field = fields[fieldName];
					var applyTotmp = field.applyTo; 
					 
					applyTo2FieldName[fieldName] =  fieldName;
					if (typeof applyTotmp != "undefined" && applyTotmp.length > 0) {
						var tos = applyTotmp.split(";");
						for (var i = 0; i < tos.length;  i++) {
							var to = tos[i];
							applyTo2FieldName[to] = fieldName;
						}
					}
				}
				formDefinition["applyTo2FieldName"] = applyTo2FieldName;
			}
			var fieldName = applyTo2FieldName[applyTo];
		    if(typeof fieldName == "undefined" ){
		    	console.log("cann't get the fieldName for mappingName[" + JSON.cStringify(applyTo) + "][formID:" + formDefinition.outerId + "]");
		    	//throw new Error("cann't get the fieldName for mappingName[" + applyTo + "][formID:" + formDefinition.outerId + "]");
		    }
			return fieldName ;
		},
		
		setFieldValue:function(mappingName, data, dataUuid){
			
			var formUuid = this.getFormUuidByRowId(dataUuid);
		 
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			if(!fieldName){
				return ;
			}
			if(data == null || !data.value ){
				this.setFieldValueByFieldName(fieldName, data, dataUuid); 
			}else{
				this.setFieldValueByFieldName(fieldName, data.value, dataUuid); 
			}
			
		},
		
		/**
		 * 设置主表的某字段的值
		 * @param fieldName 字段名
		 * @param data 各字段是的值
		 * 	格式根据不同的inputMode也会不一样，与getFieldValue一致
		 */
		 
		setFieldValueByFieldName:function(fieldName, data, dataUuid){
			
			//var time1 = (new Date()).getTime();
			 
			var control = this.getControl(fieldName, dataUuid);
			if(!control){
				//throw new Error("cann't find field[" + fieldName + "]");
				console.log("cann't find field[" + fieldName + "]");
				return;
			}
			$.dyform.setValue(control, data, this.getDataUuid());
			//var time2 = (new Date()).getTime();
			//console.log("set value for control [" + fieldName + "]"+ (time2 - time1 )/1000.0 + "s" );
		},
		
		/**
		 * 获取主表某字段的值
		 * @param fieldName
		 */ 
		getFieldValueByFieldName:function(fieldName, dataUuid){
			var control = this.getControl(fieldName, dataUuid);
			if(control == null){
				console.log("control cann't find ,fieldName=" + fieldName + "dataUuid=" + dataUuid);
				return;
			}
			return $.dyform.getValue(control);
		},
		
		
		 /**
		 * 获取主表某字段的值
		 * @param fieldName
		 */ 
		getFieldValue:function(mappingName, dataUuid){
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			if(typeof fieldName == "undefined"){
				return null;
			}
			return this.getFieldValueByFieldName(fieldName, dataUuid);
		},
		
		setFieldReadOnlyByFieldName:function(fieldName, dataUuid){
		 
			var control = this.getControl(fieldName, dataUuid);
			
			control.setReadOnly(true);
		},
		
		setFieldReadOnly:function(mappingName, dataUuid){
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			this.setFieldReadOnlyByFieldName(fieldName, dataUuid);
		},
		
		
		setFieldEditableByFieldName:function(fieldName, dataUuid){
			var control = this.getControl(fieldName, dataUuid); 
			control.setEditable(true);
		},
		
		setFieldEditable:function(mappingName, dataUuid){
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			this.setFieldEditableByFieldName(fieldName, dataUuid);
		},
		
		
		setFieldAsLabelByFieldName:function(fieldName, dataUuid){
			var control = this.getControl(fieldName, dataUuid); 
			control.setDisplayAsLabel(true);
		},
		
		setFieldAsLabel:function(mappingName, dataUuid){
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			this.setFieldAsLabelByFieldName(fieldName, dataUuid);
		},
		
		setFieldAsHiddenByFieldName:function(fieldName, dataUuid){
			var control = this.getControl(fieldName, dataUuid); 
			control.setVisible(false); 
		},
		
		setFieldAsHide:function(mappingName, dataUuid){
		 
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			this.setFieldAsHiddenByFieldName(fieldName, dataUuid);
		},
		
		setFieldAsShowByFieldName:function(fieldName, dataUuid){
			var control = this.getControl(fieldName, dataUuid); 
			control.setVisible(true);
		},
		
		setFieldAsShow:function(mappingName, dataUuid){
			var formUuid = this.getFormUuidByRowId(dataUuid);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			this.setFieldAsShowByFieldName(fieldName, dataUuid);
		},
		getControl:function(fieldName, dataUuidOfSubform){
			 
			var controlId = fieldName;
			if(typeof dataUuidOfSubform != "undefined" && dataUuidOfSubform != this.getDataUuid()){
				controlId = $.dyform.getCellId(dataUuidOfSubform, fieldName);
			}
			return  $.ControlManager.getCtl(controlId);
		} ,
		//控件不存在 返回false,否则 true
		isControl:function(fieldName, dataUuidOfSubform){
			 
			return this.getControl(fieldName, dataUuidOfSubform) == null? false: true;
		} ,
		autoWidth:function(){
			$(window).trigger("resize")
		},
		
		/**
		 * 绑定事件至控件中
		 * @param option
		*/
		bind2Control: function(option){ 
			var defaults = {
				type:"click",
				mappingName:null,
				dataUuid: this.getDataUuid(),
				callback:function(){}
			};
			
			$.extend(defaults, option);
			
			var formUuid = this.getFormUuidByRowId(defaults.dataUuid); 
			 
		 
			var fieldName = this.getFieldNameByApplyTo(defaults.mappingName, formUuid);
			 
			var control = this.getControl(fieldName, defaults.dataUuid);
			if(!control){
				console.log("bind2Control cann't find control :" + JSON.cStringify(option) );
				return;
			}
			control.bind(defaults.type, defaults.callback);
		}, 
		   //bind函数，桥接
	    bind2Dyform:function(eventname,event){ 
	    	var options = this.cache.get.call(this,cacheType.options);
	    	var events = options.events;
	    	if(typeof events == "undefined"){
	    		options.events = {};
	    		events = options.events ;
	    	}
	    	events[eventname] = event;
	    },
		 invoke:function(method){
			 var options = this.cache.get.call(this,cacheType.options);
		    	var events = options.events;
			 if(events && events[method]){
				 events[method].apply(this,$.makeArray(arguments).slice(1));
			 }
		 },
	   
	});
	/**
	 * 从表对外接口
	 * */
	$.dyform.extend({
		getSubformControl: function(formUuidOfSubform){
			var control = $.ControlManager.getSubFormControl(formUuidOfSubform); 
			return control;
		},
		/**
		 * 隐藏从表
		 * @param formUuid
		 */
		hideSubFormByFormUuid:function(formUuid){
			 
			var subformctl= this.getSubformControl(formUuid);
			 
			subformctl.hide();
			 
		},
		
		/**
		 * 隐藏从表
		 * @param formId
		 */
		hideSubForm:function(formId){ 
			 
			var formUuid = this.getFormUuid(formId);
			 
			this.hideSubFormByFormUuid(formUuid);
		},
		/**
		 * 显示从表
		 * @param formUuid
		 */
		showSubFormByFormUuid:function(formUuid){ 
			var subformctl= this.getSubformControl(formUuid);
			subformctl.show();
		},
		
		/**
		 * 显示从表
		 * @param formId
		 */
		showSubForm:function(formId){ 
			var formUuid = this.getFormUuid(formId);
			this.showSubFormByFormUuid(formUuid);
		},
		addRowDataByFormUuid:function(formUuid, data){
			var subformCtl = this.getSubformControl (formUuid); 
			subformCtl.addRowData(data);
		},
		
		addRowData:function(formId, data){
			var formUuid = this.getFormUuid(formId);
			return this.addRowDataByFormUuid(formUuid, data);
		},
		
		updateRowDataByFormUuid:function(formUuid, data){
			var subformCtl = this.getSubformControl (formUuid);
			subformCtl.updateRowData(data);
		},
		
		updateRowData:function(formId, data){
			var formUuid = this.getFormUuid(formId); 
			this.updateRowDataByFormUuid(formUuid, data);
		},
		
		
		deleteRowDataByFormUuid:function(formUuid, rowId){
			var subformCtl = this.getSubformControl (formUuid);
			subformCtl.delRowData(rowId);
		},
		
		deleteRowData:function(formId, rowId){
			var formUuid = this.getFormUuid(formId);
			this.deleteRowDataByFormUuid(formUuid, rowId);
		},
		
		getRowDataByFormUuid:function(formUuid, rowId){
			var subformCtl = this.getSubformControl (formUuid);
			return subformCtl.getRowData(rowId);
		},
		
		getRowData:function(formId, rowId){
			var formUuid = this.getFormUuid(formId);
			return this.getRowDataByFormUuid(formUuid, rowId);
		},
		/*获取选中的行*/
		getSelectedRowDataByFormUuid:function(formUuid){
			var subformCtl = this.getSubformControl (formUuid);
			if(typeof subformCtl.selectedRowId == "undefined"){
				return [];
			}
			return subformCtl.getRowData(subformCtl.selectedRowId);
		},
		/*获取选中的行*/
		getSelectedRowData:function(formId){
			var formUuid = this.getFormUuid(formId);
			return this.getSelectedRowDataByFormUuid(formUuid);
		},
		
		collectSubformDataByFormUuid:function(formUuid){
			var subformCtl = this.getSubformControl (formUuid);
			return subformCtl.collectSubformData();
		},
		collectSubformDisplayDataByFormUuid: function(formUuid) {
			var subformCtl = this.getSubformControl(formUuid);
			return subformCtl.collectSubformDisplayData();
		},		 
		group:function(formId){
			var formUuid = this.getFormUuid(formId);
			var subformCtl = this.getSubformControl (formUuid);
			subformCtl.group();
		},
		
		/**
		 * 获取从表的所有行数据
		 * @param formInfo {id:""}
		 */
		getAllRowData:function(formInfo){
			return this.collectSubformData(formInfo.id);
		},
		
		/**
		 * 设置从表整列只读
		 * @param formId
		 * @param mappingName
		 */
		setColumnReadOnly: function(formId, mappingName){ 
			this.setColumnAsLabel(formId, mappingName);
			
		},
		/**
		 *  
		 * 设置整个从表只读 
		 * @param formUuid
		 */
		setSubformReadOnlyByFormUuid:function(formUuid){
			var subformCtl = this.getSubformControl (formUuid); 
			subformCtl.setReadOnly();
		},
		
		/**
		 * 设置整个从表只读
		 * @param formId
		 */
		setSubformReadOnly:function(formId){ 
			var formUuid = this.getFormUuid(formId);
			this.setSubformReadOnlyByFormUuid(formUuid); 
		},
		
		/**
		 *  
		 * 设置整个从表只读 
		 * @param formUuid
		 */
		setSubformLabelByFormUuid:function(formUuid){
			var subformCtl = this.getSubformControl (formUuid); 
			subformCtl.setDisplayAsLabel();
		},
		
		
		/**
		 * 设置整个从表只读
		 * @param formId
		 */
		setSubformLabel:function(formId){ 
			var formUuid = this.getFormUuid(formId);
			this.setSubformLabelByFormUuid(formUuid); 
		},
		
		/**
		 * 设置从表整列为标签
		 * @param formId
		 * @param mappingName
		 */
		setColumnAsLabel: function(formId, mappingName){
			var formUuid = this.getFormUuid(formId);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			var subformCtl = this.getSubformControl (formUuid); 
			 
			subformCtl.setColumnAsLabel(fieldName);
		},
		
		/**
		 * 设置从表整列可编辑
		 * @param formId
		 * @param mappingName
		 */
		setColumnEditable:function(formId, mappingName){
			var formUuid = this.getFormUuid(formId);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			var subformCtl = this.getSubformControl (formUuid);
			subformCtl.setColumnEditable(fieldName);
		},
		
		/**
		 * 
		 * @param formId
		 * @param mappingName
		 * @param controlable 不传该参数或者为null默认为true,true:在从表中光标移出移出时没控件的标签/控件的切换效果,false:反之，有切换效果
		 */
		setColumnCtl:function(formId, mappingName, controlable){
			var formUuid = this.getFormUuid(formId);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			var subformCtl = this.getSubformControl (formUuid);
			subformCtl.setColumnCtl(fieldName, controlable);
		},
		
		/**
		 * 隐藏列(根据字段名)
		 * @param formId
		 * @param mappingName
		 */
		hideColumnByFieldName:function(formId, fieldName){
			var formUuid = this.getFormUuid(formId); 
			var subformctl = this.getSubformControl(formUuid);
			subformctl.hideColumn(fieldName);
			subformctl.resetGridWidth();
		},
		
		/**
		 * 隐藏列
		 * @param formId
		 * @param mappingName
		 */
		hideColumn:function(formId, mappingName){
			//var formUuid = this.getFormUuid(formId);
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			//this.getSubformControl(formUuid).hideColumn(fieldName);
			this.hideColumnByFieldName(formId, fieldName);
			
		},
		
		/**
		 * 显示列
		 * @param formId
		 * @param mappingName
		 */
		showColumn:function(formId, mappingName){
			var formUuid = this.getFormUuid(formId);
		 
			var fieldName = this.getFieldNameByApplyTo(mappingName, formUuid);
			 
			var subformctl = this.getSubformControl(formUuid);
			subformctl.showColumn(fieldName); 
			subformctl.resetGridWidth();
		},
		

		/**
		 * 通过从表的行ID获取对应的从表formUuid
		 * @param rowId
		 * @returns
		 */
		getFormUuidByRowId:function(rowId){ 
			if(typeof rowId == "undefined" || rowId == null){
				return this.getFormUuid();
			}
			var formDatas = this.getFormDatas();  
			for(var i in formDatas){
				var records = formDatas[i];
				for(var index = 0; index < records.length; index ++){ 
					if(records[index]["uuid"] == rowId){
						return i;
					}
				}
			}
			return null;
		},
		getRowIds:function(formUuid){
			var rowIds = [];
			var formDatas = this.collectSubformDataByFormUuid(formUuid);
			for(var index = 0; index < formDatas.length; index ++){ 
				var record = formDatas[index];
				rowIds.push(record["uuid"]);
			}
			 return rowIds;
		},
		/*addCustomBtn:function(options){
			var formUuid = this.getFormUuid(options.formId);
			var subformCtl = this.getSubformControl(formUuid);
			subformCtl.addCustomBtn(options); 
			///this.addCustomBtnByFormUuid(formUuid, options);
		}*/
		
		/*addCustomBtnByFormUuid:function(options){
			var subformCtl = this.getSubformControl(options.formUuid);
			subformCtl.addCustomBtn(options); 
		}*/
	
		/**
		 * 隐藏从表的操作按钮
		 */
		hideOperateBtn:function(formId){
			var formUuid = this.getFormUuid(formId); 
			this.hideOperateBtnByFormUuid(formUuid);
		},
		hideOperateBtnByFormUuid:function(formUuid){
			var subformctl = this.getSubformControl(formUuid); 
			subformctl.hideOperateBtn();
		},
		
		/**
		 * 显示从表的操作按钮
		 */
		showOperateBtn:function(formId){
			var formUuid = this.getFormUuid(formId); 
			this.showOperateBtnByFormUuid(formUuid);
		},
		
		/**
		 * 显示从表的操作按钮
		 */
		showOperateBtnByFormUuid:function(formUuid){
			var subformctl = this.getSubformControl(formUuid); 
			subformctl.showOperateBtn();
		},
		/***
		 * 根据默认行数添加从表空行
		 */
		addRowEmptyDataByDefaultRowCount:function(formId){
			var formUuid = this.getFormUuid(formId);
			var subformctl = this.getSubformControl(formUuid);
			if(typeof subformctl== "undefined"){
				return;
			}
			var subformconfig = subformctl.options.formDefinition.subforms[formUuid];
			if(typeof subformconfig.defaultRowCount == "undefined" || subformconfig.defaultRowCount == 0){
				return;
			}else{
				var count = parseInt(subformconfig.defaultRowCount, 10);
				for(var index = 0; index < count; index ++){
					var id = this.createUuid();
					this.addRowDataByFormUuid(formUuid, {id:id, uuid:id});
				}
			}
			
		},
		/**
		 * 给所有的从表添加默认空行
		 */
		addRowEmptyDatasByDefaultRowCount:function(){
			var formUuid =  this.getFormUuid(); 
			var formDefinition =  this.cache.get.call(this, cacheType.formDefinition, formUuid);
			var subforms = formDefinition.subforms;
			for(var i in subforms){
				var subform = subforms[i];
				this.addRowEmptyDataByDefaultRowCount(subform.outerId);
			} 
		}
		 
		
	});
	
	
	
	
	$.dyform.extend({
		/**
		 * 解析区块
		 */
		parseBlock:function(formDefinition){
			var blocks = formDefinition.blocks;
			if(typeof blocks == "undefined"){
				return;
			}
			
			for(var i in blocks){
				var block = blocks[i];
				var hide = block["hide"];
				if(typeof hide == "undefined"){//没有hide属性，不处理
					continue;
				}
				if(hide == true){
					this.$("td[blockCode='" + i + "']").parent().parent().hide();
				}else{
					this.$("td[blockCode='" + i + "']").parent().parent().show();
				}
				
			}
			
		},
		/**
		 * 主表解析
		 * @param formDefinition
		 */
		parseMainForm : function(formDefinition, formData){
			var _this = this;
			this.$(".value[name]").each(function(){
				 
				var name = $(this).attr("name");
				 
				var fieldDefinition = formDefinition.fields[name];  
				
			
				if(typeof fieldDefinition == "undefined"){
					return true;//continue;
				}
			
				
				
				
				$.ControlManager.createCtl($(this), fieldDefinition, formDefinition);
				
				
				//var control = $.ControlManager.getControl(fieldDefinition.name);
				var control = $.ControlManager.getCtl(fieldDefinition.name); 
				//return true;
				if(typeof formData != "undefined"){
					var value = formData[name];
					if(typeof value != "undefined" && value != null){
						$.dyform.setValue(control, value, null);
					}
				}
				
				control.setPos(dyControlPos.mainForm);
				
				
			 $(".label").removeClass("label"); 
			});
			
			  //自定义JS
		    if(formDefinition.customJs && $.trim(formDefinition.customJs).length > 0){
		    	try{
		    		eval( formDefinition.customJs );
		    	}catch(e){
		    		console.error(e);
		    	}
		    }
			 
		},
		
		/**
		 * 从表解析
		 * @param formDefinition
		 */
		parseSubform: function(formDefinition, definitions){
			var _this = this;
			
			this.$("table[formUuid]").each(function(){
				var formUuid=$(this).attr('formUuid');
				var subformDefinition =  null;
				 if(typeof definitions == "undefined" ||  typeof definitions[formUuid] == "undefined"){
					 subformDefinition = loadFormDefinition(formUuid);
				 }else{
					 subformDefinition =  definitions[formUuid];
				 }
				 
				if(typeof subformDefinition == "undefined" || subformDefinition == null){
					return true;
				}
				$.ControlManager.createSubFormControl($(this),_this,formDefinition, subformDefinition);
				
				//自定义JS
			    if(subformDefinition.customJs && $.trim(subformDefinition.customJs).length > 0){
			    	try{
			    		eval( subformDefinition.customJs );
			    	}catch(e){
			    		console.error(e);
			    	}
			    }
			});
			 
			
			
			
			//$.ControlManager.createSubFormControl($(this),_this,formDefinition);
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
						//运算公式缓存处理
					case cacheType.formula:
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
							//throw new Error("parameter[key] should be passed");
						 
							data = $(this).data("formDefinition");
						}else{
							var formDefinitions = $(this).data("formDefinition");
							if(typeof formDefinitions == "undefined" || formDefinitions == null){
								throw new Error("there is no form definition in the cache");
							}
							
							data = formDefinitions[key];
						}
						
						break;
					 case cacheType.dataUuid:
						data = $(this).data("dataUuid");
						break;
					 case cacheType.formula:
							data = $(this).data(cacheType.formula);
							if(typeof data == "undefined" || data == null){
								data = {};
							}
							break;
					default:
						throw new Error("unknown cachetype when get data from cache");
						 
				}
				return data;
			}
		},
		validateMainform:function(formUuid){
			 var valid = true;
			this.$(".value[name]").each(function(){
				var fieldName = $(this).attr("name");
				var control = $.ControlManager.getCtl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true;
				}
				var v = control.validate();
				valid = valid &&  v;
			});
			return valid;
		},
		
		validateSubform:function(formUuid){
			//var rulesObj = {rules:{}, messages:{}};
			var formDefinition = this.cache.get.call(this,cacheType.formDefinition, formUuid);
			var valid = true;
			var subforms = formDefinition.subforms;
			for(var i in subforms){//遍历各从表
				 try{
					var subformcontrol=$.ControlManager.getSubFormControl(subforms[i].formUuid);
					if(!subformcontrol){
						continue;
					}
					var v = subformcontrol.validate(); 
					valid = valid &&  v ; 
				 }catch(e){
						console.log(e);
				 }
			}
			return valid;
		},
		
		 
		/**
		 * 获取主表数据
		 * @param formUuid
		 */
		collectMainformData: function(formUuid){
			var formData = {};
			this.$(".value[name][pos!='" + dyControlPos.subForm + "']").each(function(){//遍历主表的占位符
				var fieldName = $(this).attr("name"); 
				var control = $.ControlManager.getCtl(fieldName);
				if(typeof control == "undefined" || control == null){
					return true;
				}
				formData[fieldName] = $.dyform.getValue(control);
			});
			
			var dataUuid = this.getDataUuid();//先从缓存中获取
			if(typeof dataUuid == "undefined"){
				dataUuid = this.createUuid();
				this.setDataUuid(dataUuid);
			}
			formData["uuid" ] = dataUuid;//主表的uuid
			
			return formData;
		},

		/**
		 * 获取主表显示数据
		 * 
		 * @param formUuid
		 */
		collectMainformDisplayData : function(formUuid) {
			var formData = {};
			this.$(".value[name][pos!='" + dyControlPos.subForm + "']")
					.each(
							function() {// 遍历主表的占位符
								var fieldName = $(this).attr("name");
								var control = $.ControlManager
										.getCtl(fieldName);
								if (typeof control == "undefined"
										|| control == null) {
									return true;
								}
								formData[fieldName] = $.dyform
										.getDisplayValue(control);
							});

			var dataUuid = this.getDataUuid();// 先从缓存中获取
			if (typeof dataUuid == "undefined") {
				dataUuid = this.createUuid();
				this.setDataUuid(dataUuid);
			}
			formData["uuid"] = dataUuid;// 主表的uuid

			return formData;
		},		
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
		 * 收集表单各控件的数据
		 * */
		getFormDatas:function(){
			var formDatas = {};
			var formUuid =  this.getFormUuid();
			//console.log("为formUuid[" + formUuid + "]收集数据");
			var formDefinition =  this.cache.get.call(this, cacheType.formDefinition, formUuid);
			
			//收集从表数据
		 
			var subforms = formDefinition.subforms;
			for(var i in subforms){
				var subform = subforms[i];
				try{
					var subformctl=$.ControlManager.getSubFormControl(subform.formUuid);
					if(!subformctl)continue;
					formDatas[subform.formUuid] = subformctl.collectSubformData(); 
				}catch(e){
					console.log(e);
				}
			}
			//formData.subformData = subformData;
			//收集主表数据
			 
			formDatas[formUuid] = [];
			formDatas[formUuid].push(this.collectMainformData(formUuid));
			return formDatas;
		},
		
		/**
		 * 收集表单各控件的显示数据
		 */
		getFormDisplayDatas : function() {
			var formDatas = {};
			var formUuid = this.getFormUuid();
			// console.log("为formUuid[" + formUuid + "]收集数据");
			var formDefinition = this.cache.get.call(this,
					cacheType.formDefinition, formUuid);

			// 收集从表数据

			var subforms = formDefinition.subforms;
			for ( var i in subforms) {
				var subform = subforms[i];
				try {
					var subformctl = $.ControlManager
							.getSubFormControl(subform.formUuid);
					if (!subformctl)
						continue;
					formDatas[subform.formUuid] = subformctl
							.collectSubformDisplayData();
				} catch (e) {
					console.log(e);
				}
			}
			// formData.subformData = subformData;
			// 收集主表数据

			formDatas[formUuid] = [];
			formDatas[formUuid]
					.push(this.collectMainformDisplayData(formUuid));
			return formDatas;
		},		
		/**
		 * 收集被删除的行数据
		 */
		getDeletedRowIds:function(){
			var formUuid =  this.getFormUuid(); 
			var formDefinition =  this.cache.get.call(this, cacheType.formDefinition, formUuid);
			var deletedFormDatas = {};
			 
			var subforms = formDefinition.subforms;
			for(var i in subforms){
				var subform = subforms[i];
				try{
					var subformctl =  this.getSubformControl(subform.formUuid);
					if(!subformctl){
						continue;
					}
					deletedFormDatas[subform.formUuid]=subformctl.getDeleteRows();
				}catch(e){
					console.log(e);
				}
			}
			return deletedFormDatas;
		},
		
		/**
		 * 获取从表的所有行数据
		 * @param formId
		 * @returns
		 */
		collectSubformData:function(formId){
			var formUuid = this.getFormUuid(formId);
			return this.collectSubformDataByFormUuid(formUuid);
		},
		/**
		 * 获取从表的所有行显示数据
		 * 
		 * @param formId
		 * @returns
		 */
		collectSubformDisplayData : function(formId) {
			var formUuid = this.getFormUuid(formId);
			return this.collectSubformDisplayDataByFormUuid(formUuid);
		}
	}); 
}); 


 
