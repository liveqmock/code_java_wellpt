(function() {
	
	 var titleClass3 = {
	    		exec:function(editor){
	    			var element = editor.getSelection().getStartElement();
	    			var parent = element.getParent();
	    			parent.addClass("grey_title");
	    		}
	    };
	 
	
    CKEDITOR.plugins.add("titleClass3", {
        init: function(a) {
            a.addCommand("titleClass3", titleClass3);
            a.ui.addButton("titleClass3", {
                label: "灰色标题样式",//调用dialog时显示的名称
                command: "titleClass3",
                icon: this.path + "images/preview.png"//在toolbar中的图标
            });
        }
    });
    
    
})();