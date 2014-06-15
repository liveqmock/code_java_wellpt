(function() {
	var pluginName =   CkPlugin.TREESELECTCTL;
    CKEDITOR.dialog.add(pluginName, 
    function(editor) { 
    	 
		var containerID = "container_" + pluginName;
        return {
            title: "树形下拉控件属性设置",
            minHeight:400,
            minWidth:800,
            contents: [{
                id: "setTreeSelect",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
					{	id:"table_html",
                    	type:"html",
                    	style: "width: 100%;",
                    	html:"<div id='" + containerID + "'>树形下拉控件属性设置</div>",
                    	//html:"<iframe  id=container_" + pluginName + " src='" + ctx +"/resources/ckeditor4.1/plugins/dysubform/index.html' />",
                    	onLoad:function(){
					        window.setTimeout(function(){
					        	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4TreeSelect; 
					        	 
					        	//以GET方式(不得以POST方式)加载属性窗口的html
					        	$("#" + containerID).load(focusedDomPropertyUrl, function(){
					        		treeselectctl.initPropertyDialog(editor);//初始化属性窗口
					        	});
					         
					        }, 50); 
                    	}
					} 
                ]
            }],
            onOk: function() {
            	var checkpass=$.ControlConfigUtil.ctlEditDialogOnOk(treeselectctl,containerID);
            	if(!checkpass){
            		return false;
            	}
            },
            onCancel: function(){//退出窗口时清空属性窗口的缓存 
            	$.ControlConfigUtil.ctlEditDialogOnCancel(treeselectctl,containerID);
            } ,
            onShow:function(){
            	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4TreeSelect; 
	        	//以GET方式(不得以POST方式)加载属性窗口的html
	        	$("#" + containerID).load(focusedDomPropertyUrl, function(){
	        		treeselectctl.initPropertyDialog(editor);//初始化属性窗口
	        	});
            	$.ControlConfigUtil.ctlEditDialogonShow(editor,this,"树形下拉");
            }
        };
    });
})();

