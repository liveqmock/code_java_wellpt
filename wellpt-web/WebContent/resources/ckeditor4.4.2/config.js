/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
//	config.fillEmptyBlocks = false; // Prevent filler nodes in all empty blocks.

//	config.pasteFromWordRemoveFontStyles = false;//是否清楚word字体样式
	
	//config.extraPlugins="formfile,formpreview,fileupload,fileupload2,titleClass,titleClass2,titleClass3";//添加插件，多个时用","隔开
	
	//图片处理  
	config.pasteFromWordRemoveStyles = true;
	config.filebrowserImageUploadUrl = ctx+'/cms/cmspage/upLoadImages',
	
	config.pasteFromWordPromptCleanup = true;//是否提示保留word样式
	config.pasteFromWordNumberedHeadingToList = true;
	
	config.enterMode = CKEDITOR.ENTER_P;
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	
	config.contentsCss = ctx+'/resources/ckeditor4.4.2/ckeditCss.css';	

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