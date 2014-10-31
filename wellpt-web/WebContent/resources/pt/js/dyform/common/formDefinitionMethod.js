

var formDefinitionMethod = {
	 deleteField: function (fieldName){
		 var field = this.fields[fieldName];
		 if(typeof field == "undefined"){
			 //alert(fieldName + " 字段不存在");
			 console.log(fieldName + " 字段不存在");
			 return;
		 }
		if(typeof field.oldName == "undefined"){//新建的字段，不需要上传到后台进行操作 
			 return;
		 }else{
			
		 }
		if(typeof this.deletedFieldNames == "undefined"){
			this.deletedFieldNames = [];
		}
		this.deletedFieldNames.push(field.name);
		delete this.fields[name];
	},
	
	deleteSubform: function (formUuid){
		 var subform = this.subforms[formUuid];
		 if(typeof subform == "undefined"){
			 console.log(formUuid + " 从表不存在");
			 return;
		 }
		delete this.subforms[formUuid];
	},
	addField:function(fieldName, fieldClzzObj){
		if(typeof this.deletedFieldNames == "undefined"){
			this.deletedFieldNames = [];
		}
		
		for(var i = 0; i < this.deletedFieldNames.length; i ++){
			if(this.deletedFieldNames[i] == fieldName){
				this.deletedFieldNames.splice(0, 1);
			}
		}
		this.fields[fieldName] = fieldClzzObj;
	},
	
	/**
	 * 判断字段是否为附件字段
	 * @param fieldName
	 * @returns {Boolean}
	 */
	isInputModeAsAttach:function(fieldName){
		 
		 var field = this.fields[fieldName];
		 if(typeof field == "undefined"){
			 return false;
		 }
		 var inputMode = field.inputMode;
		 if(inputMode == dyFormInputMode.accessory1 
				 || inputMode == dyFormInputMode.accessoryImg 
				 || inputMode == dyFormInputMode.accessory3){
			 return true;
		 }else{
			 return false;
		 }
	},
	isValueAsMap: function(fieldName){
		if(typeof fieldName == "undefined"){
			return false;
		}
		var inputType =  this.fields[fieldName].inputMode;
		return this.isInputModeAsValueMap(inputType);
	},
	isInputModeAsValueMap:function(inputMode){
		if(typeof inputMode == "undefined"){
			return false;
		}
		
		if (dyFormInputMode.orgSelect == (inputMode) 
				|| dyFormInputMode.orgSelectStaff == (inputMode)
				|| dyFormInputMode.orgSelectDepartment == (inputMode)
				|| dyFormInputMode.orgSelectStaDep == (inputMode)
				|| dyFormInputMode.orgSelectAddress == (inputMode) 
				|| dyFormInputMode.treeSelect == (inputMode)
				|| dyFormInputMode.radio == (inputMode) 
				|| dyFormInputMode.checkbox == (inputMode)
				|| dyFormInputMode.selectMutilFase == (inputMode)
				|| dyFormInputMode.job == (inputMode)
			)   
		{
			return true;
		}
		return false;
	},
	 
	isCustomField: function(fieldName){
		if(typeof fieldName == "undefined"){
			return false;
		}
		var inputType =  this.fields[fieldName].sysType;
		if(inputType !=  dyFieldSysType.custom){//系统性字段不提供给外部使用
			return false;
		}else{
			return true;
		}
	},
	isInputModeAsText: function(inputMode){
		if(typeof inputMode == "undefined"){
			return false;
		}
		if (dyFormInputMode.text == (inputMode)) {
			return true;
		}
		return false;
	},
	/**
	 * 
	 * @param desc true:降序, false:升序
	 */
	sortFieldsOfSubform: function(formUuid, desc) {
		var fields = this.subforms[formUuid].fields;
		var fieldsArray = [];
		for(var i in fields){
			//console.log(fields[i].order);
			fieldsArray.push(fields[i]);
		}
		var ret = sortASCBy(fieldsArray, "order"); 
		var fieldsOfBeSored = {};
		for(var i = 0; i < ret.length; i ++){
			fieldsOfBeSored[ret[i].name] = ret[i];
		}
		return fieldsOfBeSored;
	}
	
	
};

