/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// 界面语言，默认为 'en' 

    config.language = 'zh-cn'; 

// 设置宽高 

    config.width = 775; 

    config.height = 400; 

// 编辑器样式，有三种：'kama'（默认）、'office2003'、'v2' 

    config.skin = 'v2'; 

// 背景颜色 

    config.uiColor = '#FFF'; 

// 工具栏（基础'Basic'、全能'Full'、自定义）plugins/toolbar/plugin.js 

    //config.toolbar = 'Basic'; 

    //config.toolbar = 'Full'; 

//工具按钮.

//工具按钮.

config.toolbar_Full =
[
{ name: 'basic', items : [ 'Bold','Italic','Underline','Font','FontSize','TextColor','BGColor','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','Link','Image','Source'  ] }
];






    //工具栏是否可以被收缩
config.toolbarCanCollapse = true; 

    //工具栏的位置
config.toolbarLocation = 'top';//可选：bottom 

    //工具栏默认是否展开
config.toolbarStartupExpanded = true; 

// 取消 “拖拽以改变尺寸”功能 plugins/resize/plugin.js
config.resize_enabled = false; 

    
};
