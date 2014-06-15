/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */


 var CkPlugin ={
	SUBFORM:"dysubform",
	DATECTL:"control4date",
	LABEL:"control4label",
	FORM:"dyform",
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
	VIEWDISPLAYCTL:"control4viewdisplay"
	
	
 };
 
CKEDITOR.editorConfig = function( config ) {
//	config.fillEmptyBlocks = false; // Prevent filler nodes in all empty blocks.

//	config.pasteFromWordRemoveFontStyles = false;//是否清楚word字体样式
	
	config.extraPlugins=CkPlugin.FORM+ ","+ CkPlugin.SUBFORM+ 
						","+CkPlugin.DATECTL+ 
						","+CkPlugin.TREESELECTCTL+ 
						","+CkPlugin.SERIALNUMBERCTL+ 
						","+CkPlugin.UNITCTL+ 
						","+CkPlugin.VIEWDISPLAYCTL+ 
						","+CkPlugin.NUMBERCTL+ 
						","+CkPlugin.RADIOCTL+ 
						","+CkPlugin.DIALOGCTL+ 
						","+CkPlugin.CHECKBOXCTL+ 
						","+CkPlugin.COMBOBOXCTL+ 
						","+CkPlugin.FILEUPLOADCTL+ 
						","+CkPlugin.FILEUPLOAD4ICONCTL+ 
						","+CkPlugin.TEXTCTL+
						","+CkPlugin.TEXTAREACTL+
						","+CkPlugin.CKEDITORCTL+
						","+CkPlugin.LABEL+ 
						"," + CkPlugin.PREVIEW + "," + "titleClass,titleClass2,titleClass3,table";//添加插件，多个时用","隔开
	
	 
	config.pasteFromWordRemoveStyles = true;
	config.filebrowserImageUploadUrl = ctx+'/cms/cmspage/upLoadImages',
	 config.baseHref = ctx + "/"; 
	config.pasteFromWordPromptCleanup = true;//是否提示保留word样式
	config.pasteFromWordNumberedHeadingToList = true;
	
	config.enterMode = CKEDITOR.ENTER_P;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	
	config.contentsCss = ctx+'/resources/ckeditor4.1/dyformCkeditCss.css';	

	config.tabIndex = 4;
	config.tabSpaces = 8;//制表键走的空格数
	config.ignoreEmptyParagraph = false;//是否忽略段落中的空格
	config.enterMode = CKEDITOR.ENTER_BR;//编辑器中回车产生的标签 
//	config.font_names = 'Arial;Times New Roman;Verdana';
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+ config.font_names;//添加中文字体
	config.defaultLanguage = 'zh-cn';
//	config.font_defaultLabel = '宋体';
	config.toolbarCanCollapse = true;//工具栏是否可伸缩
	config.autoParagraph = false;
	config.format_p = { element : 'p', attributes : { 'class' : 'normalPara'}};
	/*config.format_pre = { element: 'pre', attributes: { 'class': 'code' } };
//	config.fullPage = true;
	config.format_div = { element : 'div', attributes : { 'class' : 'normalDiv' } };
	config.format_address = { element: 'address', attributes: { 'class': 'styledAddress' } };*/
	
//	config.filebrowserUploadUrl="/web/ckfinder/upload/image?command=upload",

//	 config.filebrowserBrowseUrl = '/web/resources/ckfinder/ckfinder.html', 
//	 config.filebrowserImageBrowseUrl = '/web/resources/ckfinder/ckfinder.html?type=Images', 
//	 config.filebrowserFlashBrowseUrl = '/web/resources/ckfinder/ckfinder.html?type=Flash',
//	 config.filebrowserUploadUrl = '/web/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files', 
//	 config.filebrowserImageUploadUrl = '/web/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
//	 config.filebrowserFlashUploadUrl = '/web/resources/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash',  
	 config.width = '100%'; // 宽度
     config.height = '400px'; // 高度
     config.toolbarStartupExpanded = false;
    
//     config.filebrowserWindowWidth = '1000', 
//	 config.filebrowserWindowHeight = '700';
     

};


 

CKEDITOR.getPluginContainerDomElement = function(plugin, focusedDomElement){
	var pluginContainerDomElement = null;
	if(plugin == CkPlugin.SUBFORM){//从表插件 
		if(focusedDomElement.is( 'table' ) ||  focusedDomElement.hasAscendant( 'table' )){
			if(focusedDomElement.is( 'table' )){
				pluginContainerDomElement = focusedDomElement;
			}else{
				pluginContainerDomElement = CKEDITOR.getPluginContainerDomElement(plugin, focusedDomElement.getParent());
			}
			var formUuid = $(pluginContainerDomElement).attr("formUuid"); 
			 if(formUuid != "" && (typeof formUuid ) != "undefined"){//只有从表才会设置formUuid的属性
				 return pluginContainerDomElement;
			 }else{
				 return null;
			 }
		}
	}else if(plugin == CkPlugin.DATECTL//日期控件插件
			||plugin == CkPlugin.TREESELECTCTL
			||plugin == CkPlugin.NUMBERCTL
			||plugin == CkPlugin.RADIOCTL
			||plugin == CkPlugin.CHECKBOXCTL
			||plugin == CkPlugin.COMBOBOXCTL
			||plugin == CkPlugin.TEXTCTL
			||plugin == CkPlugin.UNITCTL
			||plugin == CkPlugin.FILEUPLOADCTL
			||plugin == CkPlugin.FILEUPLOAD4ICONCTL
			||plugin == CkPlugin.DIALOGCTL
			||plugin == CkPlugin.VIEWDISPLAYCTL
			||plugin == CkPlugin.CKEDITORCTL
			||plugin == CkPlugin.SERIALNUMBERCTL
			||plugin == CkPlugin.TEXTAREACTL){
		if(focusedDomElement.is( 'img' )){
			var srcurl=$(focusedDomElement).attr("src");
			if(srcurl.indexOf(plugin) >= 0 && $(focusedDomElement).attr("class") == "value") {
				pluginContainerDomElement = focusedDomElement;
				//var parentDomElement = focusedDomElement.getParent();
				//if(parentDomElement.is("span") && $(parentDomElement).attr("class") == "value"){
				//	pluginContainerDomElement = parentDomElement;
				//}
			}
		} 
	}else if(plugin == CkPlugin.LABEL){//label控件插件 
		if(focusedDomElement.is( 'label' )){
			//var parentDomElement = focusedDomElement.getParent();
			if(  $(focusedDomElement).attr("class") == "label"){
				pluginContainerDomElement = focusedDomElement;
			}
		}
		 
	}else if(plugin == CkPlugin.FORM){//日期控件插件 
		 
		if(focusedDomElement.is( 'td' )  ){
			//var parentDomElement = focusedDomElement.getParent();
			if(  $(focusedDomElement).attr("class") == "title"){
				pluginContainerDomElement = focusedDomElement;
			}
		}
	}
	return pluginContainerDomElement;
};

function loadCommonJsFile(){
	var head=document.getElementsByTagName('head').item(0); 
	var script = document.createElement("script");
	script.src = ctx + "/resources/ckeditor4.1/common.js";
	script.type='text/javascript';  
	 head.appendChild(script);
}
 
function getRelativePath(absolutePath){
	 var projectHostPrefix = window.location.host + ctx; 
	var index = absolutePath.indexOf(projectHostPrefix);
	var relativePath =  absolutePath.substring(index + projectHostPrefix.length + 1);// + 1表示"/"占位 
	return relativePath;
}