/*****************************************************************
                  jQuery Validate扩展验证方法  (linjq)       
*****************************************************************/
//加载全局国际化资源
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
I18nLoader.load("/resources/pt/js/dyform/validate/messages");

$(function(){
	  jQuery.validator.addMethod("regex",  //addMethod第1个参数:方法名称
		        function(value, element, params) {     //addMethod第2个参数:验证方法，
		            //验证方法参数（被验证元素的值，被验证元素，参数）
		            var exp = new RegExp(params);     //实例化正则对象，参数为用户传入的正则表达式
		            return exp.test(value);                    //测试是否匹配
		        },
		        "格式错误"); 
		         
     jQuery.validator.addMethod("isUnique",  //addMethod第1个参数:方法名称
    	        function(value, element, params) {     //addMethod第2个参数:验证方法，
    	 	
    	   	
    	 	var data = {};
    	 	$.extend(data, params.data);
     		for(var i in params.data){
     		 	if(typeof params.data[i] == "function"){
     		 		data[i] = params.data[i]();
     		 	}
     		}
     		
     		
     		//从表的列在前台的唯一性验证
     		if(typeof params.$form != "undefined" && typeof params.formUuid != "undefined"){ 
     			var $form = params.$form;
     			
     			 
     			var formUuid =$form.cache.get.call($form, cacheType.formUuid);
     			 console.log(params.formUuid == formUuid);
     			var exist = false;
     			if(params.formUuid != formUuid){//从表
     				var datas  = $form.$("#" + params.formUuid).jqGrid("getRowData");
     				console.log(datas.length);
     				//var colModels = $form.$("#" + formUuid).jqGrid('getGridParam','colModel');
     				for(var i = 0; i < datas.length; i ++){
     					var rowId = datas[i]["id"];
     					if(rowId == data.uuid){
     						continue;
     					}
     					var fieldName = data.fieldName;
     					var cellId = $.dyform.getCellId(rowId, fieldName); 
						var control = $.ControlManager.getControl(cellId);
						var value = "";
						if(control.isValueMap()){
							value = control.getValueMap();
						}else{
							value = control.getValue();
						}
						if(value == data.fieldValue){
							exist = true;
							break;
						}
     				}
     			}
     			if(exist){
     				 
     				return !exist;
     			}
     		}
     		
     		
    	 	var checked = false;
           	$.ajax({
        	   url:params.url,                                                                                                   
        	   type:params.type,
        	   async:params.async,
        	   data:JSON.stringify(data),
        	   dataType:'json',
       			contentType:'application/json',
       			type:"POST",
        	   success:function(result){
        		   
        		   if(result.success == "true" || result.success == true){
        			   if(result.data == "false" || result.data == false){
        				   checked = true;
        			   }else{
        				   checked = false;
        			   } 
        		   }else{
        			   checked = false;
        		   }
        	   },
        	   error:function(result){ 
        		   checked = false; 
        	   }
           });
           
           
           return checked;//测试是否匹配 
    	        },
    	        "须唯一");   
    
});