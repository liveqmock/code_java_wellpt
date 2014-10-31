(function() {
	var pluginName =   CkPlugin.FILEUPLOAD4IMAGECTL;
    CKEDITOR.dialog.add(pluginName, 
    function(editor) { 
    	
		var containerID = "container_" + pluginName;
        return {
            title: "图片上传控件属性设置",
            minHeight:400,
            minWidth:800,
            contents: [{
                id: "setFileUpLoad4Icon",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
					{	id:"table_html",
                    	type:"html",
                    	style: "width: 100%;",
                    	html:"<div id='" + containerID + "'>图标式附件上传控件属性设置</div>",
                    	//html:"<iframe  id=container_" + pluginName + " src='" + ctx +"/resources/ckeditor4.1/plugins/dysubform/index.html' />",
                    	onLoad:function(){
					        window.setTimeout(function(){
					        	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4FileUpload4Icon4Icon; 
					        	 
					        	//以GET方式(不得以POST方式)加载属性窗口的html
					        	$("#" + containerID).load(focusedDomPropertyUrl, function(){
					        		fileupload4imagectl.initPropertyDialog(editor);//初始化属性窗口
					        	});
					         
					        }, 50); 
                    	}
					} 
                ]
            }],
            onOk: function() {
            	var checkpass=$.ControlConfigUtil.ctlEditDialogOnOk(fileupload4imagectl,containerID);
            	if(!checkpass){
            		return false;
            	}
            },
            onCancel: function(){//退出窗口时清空属性窗口的缓存 
            	$.ControlConfigUtil.ctlEditDialogOnCancel(fileupload4imagectl,containerID);
            } ,
            onShow:function(){
              	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4FileUpload4Image; 
	        	//以GET方式(不得以POST方式)加载属性窗口的html
	        	$("#" + containerID).load(focusedDomPropertyUrl, function(){
	        		fileupload4imagectl.initPropertyDialog(editor);//初始化属性窗口
	        	});
            	$.ControlConfigUtil.ctlEditDialogonShow(editor,this,"图标式附件上传");
            }
        };
        
    });
     
})();

