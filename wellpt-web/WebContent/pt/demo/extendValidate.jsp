<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>jquery validate 扩展</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <!-- 校验框架 -->
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validate/js/jquery.validate.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validate/js/additional-methods.js"></script>
    <script src="jquery-1.5.js" type="text/javascript"></script>
    <script src="jquery.validate.min.js" type="text/javascript"></script>
    <script type="text/javascript">
       jQuery.validator.addMethod("regex",  //addMethod第1个参数:方法名称
        function(value, element, params) {     //addMethod第2个参数:验证方法，
            //验证方法参数（被验证元素的值，被验证元素，参数）
            var exp = new RegExp(params);     //实例化正则对象，参数为用户传入的正则表达式
            return exp.test(value);                    //测试是否匹配
        },
        "格式错误"); 
         
         jQuery.validator.addMethod("unique",  //addMethod第1个参数:方法名称
        	        function(value, element, params) {     //addMethod第2个参数:验证方法，
        	 	var data = {};
        	 	$.extend(data, params.data);
         		for(var i in params.data){
         		 	if(typeof params.data[i] == "function"){
         		 		data[i] = params.data[i]();
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
	        		   console.log(JSON.stringify(result));
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
	        		   console.log("error:" + JSON.stringify(result));
	        		   checked = false; 
	        	   }
	           });
	           
	           
	           return checked;//测试是否匹配 
        	        },
        	        "须唯一");   
        
        $(function() {
            $("#signupForm").validate(
            //在上例中新增的部分
            {
            rules: { 
            	apply_to: {    //密码2的描述多于1项使用对象类型
                    required: true,  //必填，这里可以是一个匿名方法 
                    rangelength: [3, 20],    //长度5-10之间 
                    unique:{
                    	url :"${ctx}/dyformdata/validate/exists",
    					type : "POST",
    					async: false,
    					data : {
	    					uuid: function(){
	    						return $("#uuid").val();
	    					},
	    					tblName : "dyform_form_definition",//表单名称
	    					fieldName : "apply_to",
	    					fieldValue : function(){
	    						return $('#apply_to').val();
	    					}
    					}
                    }
                },
                
               
            },
            messages: {  //对应上面的错误信息
                 
            },
            errorPlacement: function(error, element) { //指定错误信息位置
                if (element.is(':radio') || element.is(':checkbox')) {  //如果是radio或checkbox
                   // var eid = element.attr('name');  //获取元素的name属性
                    error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面
                } else {
                    error.insertAfter(element);
                }
            },
            debug: false,  //如果修改为true则表单不会提交
            submitHandler: function() {
                alert("开始提交了");
            }
        });
         
          var i = 0;
         $("#datacheck1").click(function(){
        	$("#signupForm").data("greeting", "hello, world" +  i);
        	$("#apply_to").data("greeting", "hi, world" +  i);
        	 i++;
         });
         $("#datacheck2").click(function(){ 
         	alert($("#signupForm").data("greeting"));
        	alert($("#apply_to").data("greeting"));
          });
    });
    </script>
    <style type="text/css">
        *
        {
            font-size: 14px;
        }
        #signupForm label.error
        {
            color: Red;
            font-size: 13px;
            margin-left: 5px;
            padding-left: 16px;
            background: url("error.png") left no-repeat;
        }
    </style>
</head>
<body>
    <form id="signupForm" method="get" action="">
    <fieldset>
        <legend>表单定义表</legend>
          <p>
            <label for="uuid">
              	  ID</label>
            <input id="uuid" name="uuid" type="text" />
        </p>
          <p>
            <label for="apply_to">
              	  apply_to</label>
            <input id="apply_to" name="apply_to" type="text" />
        </p>
        
    </fieldset>
    <input type="submit" value="提交 ">
      <input type="button" value="datacheck1" id="datacheck1" >
        <input type="button" value="datacheck2" id="datacheck2">
    </form>
</body>
</html>