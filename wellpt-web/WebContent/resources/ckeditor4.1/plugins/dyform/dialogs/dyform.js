(function() {
	if(typeof CkPlugin == "undefined"){
		return;
	}
	var pluginName = CkPlugin.FORM; 
	
    CKEDITOR.dialog.add(pluginName, 
    function(editor) { 
    	 
		var containerID = "container_" + pluginName;
        return {
            title: "设置表单",
            minHeight:100,
            minWidth:300,
            contents: [{
                id: "setTitle",
                label: "label",
                title: "title",
                expand: true,
                padding: 0,
                elements: [
					{	id:"setForm",
                    	type:"html",
                    	style: "width: 100%;",
                    	html:"<div id='" + containerID + "'>设置表单</div>",
                    	//html:"<iframe  id=container_" + pluginName + " src='" + ctx +"/resources/ckeditor4.1/plugins/dysubform/index.html' />",
                    	onLoad:function(){ 
					        window.setTimeout(function(){
					        	var focusedDomPropertyUrl = editor.focusedDomPropertyUrl4Form;  
					        	//以GET方式(不得以POST方式)加载属性窗口的html
					        	$("#" + containerID).load(focusedDomPropertyUrl, function(){  
					        		dyform.initPropertyDialog(editor);//初始化属性窗口
					        	});
					         
					        }, 100); 
                    	}
					} 
                ]
            }],
            onOk: function() {
            	dyform.fillCkeditor();  
            	 
            	dyform.exitDialog();
            },
            onCancel: function(){//退出窗口时清空属性窗口的缓存 
            	if( (typeof dyform.exitDialog) != "undefined"){
            		dyform.exitDialog(); 
            	}
            } ,
            onShow:function(){
           	 
            	if(editor.focusedDom != null && (typeof editor.focusedDom != "undefined")){
            		//通过双击ckeditor中的从表元素,表示修改从表属性,则不需要做焦点位置判断
            		return;
            	}
            	
            	var editorHtml = editor.getData();
            	if(editorHtml.indexOf("dyform") != -1 
            		&& 	editorHtml.indexOf("post-sign") != -1 
            		&& editorHtml.indexOf("post-detail") != -1
            	){
            		this.hide();
            		alert("表单已经存在于设计器中!!");
            		return;
            	}
            	 
            }
        };
        
    });
     
})();

 



