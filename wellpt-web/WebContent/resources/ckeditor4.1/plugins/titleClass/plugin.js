(function() {
	
	 var titleClass = {
	    		exec:function(editor){
	    			var element = editor.getSelection().getStartElement();
	    			var parent = element.getParent();
	    			parent.addClass("blue_title");
	    		}
	    };
	 
	
    CKEDITOR.plugins.add("titleClass", {
        init: function(a) {
            a.addCommand("titleClass", titleClass);
            a.ui.addButton("titleClass", {
                label: "蓝色标题样式",//调用dialog时显示的名称
                command: "titleClass",
                icon: this.path + "images/preview.png"//在toolbar中的图标
            });
        }
    });
    
    
})();