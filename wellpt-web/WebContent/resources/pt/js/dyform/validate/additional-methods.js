/*****************************************************************
                  jQuery Validate扩展验证方法  (linjq)       
*****************************************************************/
//加载全局国际化资源
if(typeof I18nLoader != "undefined")
I18nLoader.load("/resources/pt/js/global");
//加载动态表单定义模块国际化资源
if(typeof I18nLoader != "undefined")
I18nLoader.load("/resources/pt/js/dyform/validate/messages");

$(function(){
	  jQuery.validator.addMethod("regexCaseNotSensitive",  //addMethod第1个参数:方法名称
		        function(value, element, params) {     //addMethod第2个参数:验证方法，
		            //验证方法参数（被验证元素的值，被验证元素，参数）
		            var exp = new RegExp(params, "i");     //实例化正则对象，参数为用户传入的正则表达式
		            return exp.test(value);                    //测试是否匹配
		        },
		        "格式错误"); 
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
     		
     		//先验证页面上的唯一分组里面的元素有没有重复
     		var valid = true;
     		var uniqueGroup = $(element).attr("uniqueGroup");
     		$("input[uniqueGroup='" + uniqueGroup + "']").each(function(){
     			var controlname =  $(this).attr("name");
     			if( $(element).attr("name")  == controlname ){
     				return true;
     			}
     			
     			var control = $.ControlManager.getCtl(controlname);
     			var value = "";
				if(control.isValueMap()){
					value = control.getValueMap();
				}else{
					value = control.getValue();
				}
				if(value == data.fieldValue){
					valid = valid && false;
					 
				}
     		});
     		
     		if(!valid){
     			return valid;
     		}
     		
    	 	var checked = false;
           	$.ajax({
        	   url:params.url,                                                                                                   
        	   type:params.type,
        	   async:params.async,
        	   data:JSON.cStringify(data),
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
        		   checked = true; 
        	   }
           });
           
           
           return checked;//测试是否匹配 
    	        },
    	        "须唯一");   
    
});