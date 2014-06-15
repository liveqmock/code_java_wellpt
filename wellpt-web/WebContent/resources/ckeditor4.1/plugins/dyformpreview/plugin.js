(function() {
	
	  
	var pluginName =  CkPlugin.PREVIEW; 
	 
	var formpreview = {
			 exec:function(editor){
				 
				
				window.setTimeout(function(){
					 
					$("#moduleDiv" ).html(editor.getData());
					
					window.setTimeout(function(){
						$(".value").each(function(){ 
							var name = $(this).attr("name");
							
							var fieldDefinition = formDefinition.fields[name];
							if(typeof fieldDefinition == "undefined"){
								return true;//continue;
							}
							
							switch(fieldDefinition.inputMode){ 
								case dyFormInputMode.date:
									console.log("日期");
									
								default:
									$(this).before("<input name='" + name + "'/>");
									$(this).hide();
								window.setTimeout(function(){ 
									$.ControlManager.createControl(fieldDefinition);
								}, 10); 	
							}
							
							$(".label").removeClass("label");
							 
							 
						});
						$("#moduleDiv" ).dialog({width:800});
					}, 10);
					
					
					
					
				}, 10);
				 
			 }};
	
	
	
	 CKEDITOR.plugins.add(pluginName, {
	        init: function(a) {
	            a.addCommand(pluginName, formpreview);
	            a.ui.addButton(pluginName, {
	                label: "预览",//调用dialog时显示的名称
	                icon: this.path + "images/preview.png",//在toolbar中的图标
	                command: pluginName
	            });
	        }
	    });
	 
	 
	 
})();