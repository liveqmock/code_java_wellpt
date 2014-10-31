var CkPlugin ={
	SUBFORM:"dysubform",
	DATECTL:"control4date",
	LABEL:"control4label",
	//FORM:"dyform",
	PREVIEW:"dyformpreview",
	TREESELECTCTL:"control4treeselect",
	NUMBERCTL:"control4number",
	RADIOCTL:"control4radio",
	COMBOBOXCTL:"control4combobox",
	CHECKBOXCTL:"control4checkbox",
	CKEDITORCTL:"control4ckeditor",
	TEXTCTL:"control4text",
	TEXTAREACTL:"control4textarea",
	CKEDITORCTL:"control4ckeditor",
	CHECKBOXCTL:"control4checkbox",
	SERIALNUMBERCTL:"control4serialnumber",
	DIALOGCTL:"control4dialog",
	UNITCTL:"control4unit",
	FILEUPLOADCTL:"control4fileupload",
	FILEUPLOAD4ICONCTL:"control4fileupload4icon",
	FILEUPLOAD4IMAGECTL:"control4fileupload4image",
	VIEWDISPLAYCTL:"control4viewdisplay",
	PROPERTIESDIALOGS:"propertiesDialog",
	BLOCK:"block"
};


var ckUtils = {
	removeHintInDesigner:function(){
		 var _hintelement = this.document.find("#user__hint");
		 if(_hintelement.count() > 0){
			 _hintelement.getItem(0).remove();
		 }
	},
	doubleClick:function(evt){
		var element = evt.data.element;  //element是CKEDITOR.dom.node类的对象 
		var focusedDomElement = element; 
		
		//从表控件
		var tableContainer = this.getTableContainer(focusedDomElement); 
		var formUuid = $(tableContainer).attr("formUuid"); 
		 if(formUuid != "" && (typeof formUuid ) != "undefined"){//只有从表才会设置formUuid的属性 
			 return this.jump2PropertyDialog(tableContainer, CkPlugin.SUBFORM, evt);//进入从表属性窗口
			   
		 }
		 
		 
		 //普通控件
		if(focusedDomElement.is( 'img' )){ 
			var inputmode=$(focusedDomElement).attr("inputmode");
			var clazz = $(focusedDomElement).attr("class");
			var fieldName = $(focusedDomElement).attr("name");
			if(typeof fieldName == "undefined" || typeof clazz == "undefined" || clazz  != "value"){ 
				return false;
			}
			
			var field = formDefinition.fields[fieldName];
			if(typeof inputmode == "undefined" && typeof field != "undefined" ){ 
				inputmode = field.inputMode;
			}else{
				if(typeof field == "undefined" ){
					field = new MainFormFieldClass();
					field.name = fieldName;
					formDefinition.fields[fieldName] = field;
				}
			}
			if(typeof inputmode == "undefined"){
				return false;
			}
			
			var pluginName = this.getPluginName(inputmode);
			if(pluginName == null){
				return false;
			}
			return this.jump2PropertyDialog(focusedDomElement, pluginName, evt);//进入控件属性窗口
			   
			 
		}  
		if(focusedDomElement.is( 'label' )){
			//var parentDomElement = focusedDomElement.getParent();
			if(  $(focusedDomElement).attr("class") == "label"){
				this.jump2PropertyDialog(focusedDomElement, CkPlugin.LABEL, evt);//进入标签属性窗口
				 return; 
			}
		}
		 
		if(focusedDomElement.is( 'td' )  ){ 
			//var parentDomElement = focusedDomElement.getParent();
			if(  $(focusedDomElement).attr("class") == "title"){
				this.jump2PropertyDialog(focusedDomElement, CkPlugin.BLOCK, evt);//进入标题属性窗口
				 return;
			} 
		}
		return pluginContainerDomElement;

		 
	},
	/**
	 * 获取指定元素对应的table容器
	 * @param focusedDomElement
	 * @returns
	 */
	getTableContainer : function(focusedDomElement){
		var pluginContainerDomElement = null;
		if(focusedDomElement.is( 'table' ) ||  focusedDomElement.hasAscendant( 'table' )){
			if(focusedDomElement.is( 'table' )){
				pluginContainerDomElement = focusedDomElement;
				return pluginContainerDomElement;
			}else{
				pluginContainerDomElement = this.getTableContainer(focusedDomElement.getParent());
			} 
		}
		return pluginContainerDomElement;
	},
	jump2PropertyDialog :function(focusedDom, pluginName, evt){
		//console.log(pluginName + "===>" + JSON.cStringify(evt)); 
		 this.focusedDom = focusedDom; 
		 evt.data.dialog = pluginName; 
	}, 
	
	getPluginName:function(inputMode){
		var pluginName = "";
		switch(inputMode){
		case  dyFormInputMode.date:
			pluginName = CkPlugin.DATECTL;
			break;
		case  dyFormInputMode.treeSelect:
			 
			pluginName = CkPlugin.TREESELECTCTL;
			break;
		case  dyFormInputMode.number:
			 
			pluginName = CkPlugin.NUMBERCTL;
			break;
		case  dyFormInputMode.radio:
		 
			pluginName = CkPlugin.RADIOCTL;
			break;
		case  dyFormInputMode.checkbox:
		 
			pluginName = CkPlugin.CHECKBOXCTL;
			break;
		case  dyFormInputMode.selectMutilFase:
			 
			pluginName = CkPlugin.COMBOBOXCTL;
			break;
		case  dyFormInputMode.text:
			 
			pluginName = CkPlugin.TEXTCTL;
			break;
		case  dyFormInputMode.orgSelectAddress:
		case  dyFormInputMode.orgSelectStaDep:
		case  dyFormInputMode.orgSelectDepartment:
		case  dyFormInputMode.orgSelectStaff: 
			 pluginName = CkPlugin.UNITCTL;
			break;
		case  dyFormInputMode.accessory3:
			 
			pluginName = CkPlugin.FILEUPLOADCTL;
			break;
		case  dyFormInputMode.accessory1:
			 
			pluginName = CkPlugin.FILEUPLOAD4ICONCTL;
			break;
		case  dyFormInputMode.accessoryImg:
			 
			pluginName = CkPlugin.FILEUPLOAD4IMAGECTL;
			break;
		case  dyFormInputMode.dialog:
			 
			pluginName = CkPlugin.DIALOGCTL;
			break;
		case  dyFormInputMode.viewdisplay:
			 
			pluginName = CkPlugin.VIEWDISPLAYCTL;
			break;
		case  dyFormInputMode.ckedit:
			 
			pluginName = CkPlugin.CKEDITORCTL;
			break;
		case  dyFormInputMode.serialNumber:
		case  dyFormInputMode.unEditSerialNumber: 
			 pluginName = CkPlugin.SERIALNUMBERCTL;
			break;
		case  dyFormInputMode.textArea:
			 pluginName = CkPlugin.TEXTAREACTL;
			break;
		default:
			return null;
		}
		
		return pluginName;
	},
	 
	createPlugin:function(pluginName, pluginTitle, needDialog){//创建一个插件对象  
		var currentPath = this.plugins[pluginName].path;
	   this.ui.addButton(pluginName, {
           label: pluginTitle,//调用dialog时显示的名称
           icon: currentPath + "/images/anchor.jpg",//在toolbar中的图标
           command: pluginName
       });
	   
	   if(needDialog){
		   this.plugins[pluginName].htmlUrl = currentPath + "dialogs/" + pluginName + ".html";
		   CKEDITOR.dialog.add(pluginName, currentPath + "dialogs/" + pluginName +".js");
		   this.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
	   } 
	}
	 
		
};



var dialogMethod = { 
	onOk: function() {
		
    	//this.fillCkeditor();
    	//this.exitDialog();
		var _editor = this.getParentEditor( );
    	alert(_editor);
    },
    onCancel: function(){//退出窗口时清空属性窗口的缓存  
    	//if( (typeof custombtn.exitDialog) != "undefined"){
    	//	this.exitDialog(); 
    	///}
    	var _editor = this.getParentEditor( );
    	alert(_editor);
    } ,
    
    onShow:function(){
    	var _editor = this.getParentEditor( );
    	
    	 //var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4Btn;
    	//$("#" + containerID).load(focusedDomPropertyUrl, function(){
    	//	custombtn.initPropertyDialog(editor);//初始化属性窗口
    	//});
    	
     
    }
};



