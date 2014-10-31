(function() {
	var pluginName =   CkPlugin.DIALOGCTL;
    CKEDITOR.dialog.add(pluginName, 
    function(editor) { 
    	 
		var containerID = "container_" + pluginName;
        return {
            title: "弹出框控件属性设置",
            minHeight:400,
            minWidth:950,
            contents: [{
                id: "setDialogctl",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
					{	id:"table_html",
                    	type:"html",
                    	style: "width: 100%;",
                    	html:"<div id='" + containerID + "'>弹出框控件属性设置</div>",
                    	//html:"<iframe  id=container_" + pluginName + " src='" + ctx +"/resources/ckeditor4.1/plugins/dysubform/index.html' />",
                    	onLoad:function(){
					       
                    	}
					} 
                ]
            }],
            onOk: function() {
            	var checkpass=$.ControlConfigUtil.ctlEditDialogOnOk(dialogctl,containerID);
            	if(!checkpass){
            		return false;
            	}
            },
            onCancel: function(){//退出窗口时清空属性窗口的缓存 
            	$.ControlConfigUtil.ctlEditDialogOnCancel(dialogctl,containerID);
            } ,
            onShow:function(){ 
              	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4Dialog; 
	        	//以GET方式(不得以POST方式)加载属性窗口的html
	        	$("#" + containerID).load(focusedDomPropertyUrl, function(){
	        		dialogctl.initPropertyDialog(editor);//初始化属性窗口
	        	});
            	$.ControlConfigUtil.ctlEditDialogonShow(editor,this,"弹出框");
            }
        };
        
    });
     
})();

