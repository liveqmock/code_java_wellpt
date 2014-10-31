(function() {
	
	var pluginName =  CkPlugin.CKEDITORCTL;
    CKEDITOR.plugins.add(pluginName, {
        requires: ["dialog"],
        init: function(a) { 
        	 a.focusedDomPropertyUrl4Ckeditor =  this.path + "dialogs/" + pluginName + ".html";//属性窗口中的html对应的地址,dysubform.js中要使用到该变量
        	 a.imageOfCkeditorctl = getRelativePath(this.path) + "images/ckeditorctl.jpg" ;
        	 //定义"设置从表"对话框
            CKEDITOR.dialog.add(pluginName, this.path + "dialogs/" + pluginName +".js");
            
        	//定义命令，用于打开"设置从表"对话框
            a.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
            
            //定义一个按钮,用于触发打开"设置从表"对话框的命令
            a.ui.addButton(pluginName, {
                label: "富文本控件",//调用dialog时显示的名称
                command: pluginName,
                icon: this.path + "images/anchor.png"//在toolbar中的图标
            });
            
            //定义双击事件
            a.on( 'doubleclick', function( evt ) {
        		var element = evt.data.element;  //element是CKEDITOR.dom.node类的对象 
				var pluginContainerDomElement = CKEDITOR.getPluginContainerDomElement(pluginName, element); 
				if(pluginContainerDomElement != null){
						 a.focusedDom = pluginContainerDomElement;
						 evt.data.dialog = pluginName;
				}
        	  }
           );
            
            
        }
    });
})();