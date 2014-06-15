(function() {
	
	 var fileupload = {
	    		exec:function(editor){
	    			var a = new CKEDITOR.dom.element('tr');
	    			a.addClass("blue_title");
	    			a.addClass("title");
	    			a.appendHtml('<td class="Label" colspan="3">附件</td><td><input id="fileupload" name="fileupload" type="text"></td>');
	    			var element = editor.getSelection().getStartElement();
	    			var parent = element.getParent().getNext();
	    			if(parent.getName() =="html") {
	    				editor.insertElement(a);
	    			}else {
	    				parent.insertBeforeMe(a);
	    			}
	    		}
	    };
	 
	
    CKEDITOR.plugins.add("fileupload", {
        init: function(a) {
            a.addCommand("fileupload", fileupload);
            a.ui.addButton("fileupload", {
                label: "附件",//调用dialog时显示的名称
                command: "fileupload",
                icon: this.path + "images/anchor.png"//在toolbar中的图标
            });
        }
    });
    
    
})();