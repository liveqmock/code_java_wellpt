(function() {
	
	  
	var pluginName =  CkPlugin.PREVIEW; 
	 
	var formpreview = {
			 exec:function(editor){
				 
				
				/*window.setTimeout(function(){
					 
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
					
					
					
					
				}, 10);*/
				 collectFormDatas();
				 if(formDefinition.uuid == ""){
					 formDefinition.uuid = "demouuid";
				 }
				
				 var str = JSON.stringify(formDefinition);
				// console.log(urlencode(str));
				// var url = ctx + "/dyformdata/demo?formDefinition=" + urlencode(urlencode(str));
				// window.open(url);
				 openPostWindow(ctx + "/dyformdata/demo" , urlencode(urlencode(str)), "_blank");
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


 function openPostWindow(url, data, name)  
      {  
          var tempForm = document.createElement("form");  
          tempForm.id="tempForm1";  
          tempForm.method="post";  
          tempForm.action=url;  
          tempForm.target=name;  
          
          var hideInput = document.createElement("input");  
          hideInput.type="hidden";  
          hideInput.name= "formDefinition"
          hideInput.value= data;
          tempForm.appendChild(hideInput);   
          tempForm.onsubmit=function(){ openWindow(name); };
         document.body.appendChild(tempForm);  
          
          //tempForm.fireEvent("onsubmit");
          tempForm.submit();
          document.body.removeChild(tempForm);
     }


 function openWindow(name)  
       {  
           window.open('about:blank',name,'height=400, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');   
       }  

 


