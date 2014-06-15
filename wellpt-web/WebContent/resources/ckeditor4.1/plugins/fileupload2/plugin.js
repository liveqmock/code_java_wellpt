(function() {
	
	 var fileupload2 = {
	    		exec:function(editor){
	    			var a = new CKEDITOR.dom.element('div');
	    			var html = '<div class="upload_div" id="upload_div">'+
					'<span class="btn btn-success fileinput-button2">'+
					'<span class="file_icon"></span>'+
					'<span class="add_icon">添加附件</span>'+
					'<input id="fileupload" type="file" name="files[]" multiple class="fileupload_css">'+
					'</span></div>'+
					'<div id="files">'+
					'</div>'
//					"<input type='hidden' id='attach' value='"+new UUID().id+"'>"
					;
	    			a.appendHtml(html);
	    			editor.insertElement(a);
	    		}
	    };
	 
	
    CKEDITOR.plugins.add("fileupload2", {
        init: function(a) {
            a.addCommand("fileupload2", fileupload2);
            a.ui.addButton("fileupload2", {
                label: "简要附件",//调用dialog时显示的名称
                command: "fileupload2",
                icon: this.path + "images/anchor.png"//在toolbar中的图标
            });
        }
    });
    
    
})();