(function() {
	
	 var titleClass2 = {
	    		exec:function(editor){
	    			var element = editor.getSelection().getStartElement();
	    			var parent = element.getParent();
	    			parent.addClass("white_title");
	    		}
	    };
	 
	
    CKEDITOR.plugins.add("titleClass2", {
        init: function(a) {
            a.addCommand("titleClass2", titleClass2);
            a.ui.addButton("titleClass2", {
                label: "白色标题样式",//调用dialog时显示的名称
                command: "titleClass2",
                icon: this.path + "images/preview.png"//在toolbar中的图标
            });
        }
    });
    
    
})();