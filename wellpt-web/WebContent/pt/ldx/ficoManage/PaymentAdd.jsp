<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title id="titl">新增付款条件</title>
</head>
<body>
 <div class="div_body" style="">
   <div class="form_header" style="">
    <div class="form_title" style="">
      <h1>付款条件维护</h1>
    </div>
    <div id="toolbar" class="form_toolbar" style="">
      <div class="form_operate">
        <button id="form_save" type="button">保存</button>
        <button id="form_close" type="button">关闭</button>
      </div>
    </div>
   </div>
   <form action="" id="paymentTitle" class="dyform">
     <table>
       <tr>
         <td class="title" colspan="2">付款条件维护</td>         
       </tr>
       <tr class="field">
         <td width="110px">付款条件</td>
         <td><input type="text" name="zterm" id="zterm" value="${zterm}"></td>
       </tr>
       <tr class="field">
         <td width="110px">公司代码</td>
         <td><input type="text" name="bukrs" id="bukrs" value="${bukrs}"></td>
       </tr>
       <tr class="field">
         <td width="110px">客户编码</td>
         <td><input type="text" name="kunnr" id="kunnr" value="${kunnr}"></td>
       </tr>
       <tr class="field">
         <td width="110px">付款说明</td>
         <td><input type="text" name="zvtext" id="zvtext" value="${zvtext}"></td>
       </tr>
       <tr class="field">
         <td width="110px">期限</td>
         <td><input type="text" name="zdeadline" id="zdeadline" value="${zdeadline}"></td>
       </tr>
       <tr class="field">
         <td width="110px">货币码</td>
         <td><input type="text" name="waers" id="waers" value="${waers}"></td>
       </tr>
       <tr class="field">
         <td width="110px">出口区域</td>
         <td><input type="text" name="zearea" id="zearea" value="${zearea}"></td>
       </tr>
       <tr class="field">
         <td width="110px">客户组</td>
         <td><input type="text" name="zkdgrp" id="zkdgrp" value="${zkdgrp}"></td>
       </tr>       
     </table>
   </form>
 </div>
</body>

<script type="text/javascript">
function closeDialog(){
	window.close();
}
$(document).ready(function(){
	var method = <%=request.getAttribute("method")%>;  //获取输入的参数
	
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	$("#form_close").click(function(){     //执行关闭窗口操作
		window.close();
	});
	
	/**
	  method = 0: 更改
	  method = 1: 创建
	*/
	
	if(method == 0){
		document.title = "更改付款条件";  //当执行更改付款条件时，同时更改title描述
		
		$("#form_save").click(function(){
			JDS.call({
				service : "defaultPaymentDataService.updatePayment",
				data : [ $("#zterm").val(),$("#bukrs").val(),$("#kunnr").val(),$("#zvtext").val(),$("#zdeadline").val(),$("#waers").val(),$("#zearea").val(),$("#zkdgrp").val()],
				async : false,
				success : function(result) {
					if(result.data.res=="success"){
						window.opener.reloadFileParentWindow();
						oAlert2(result.data.msg,closeDialog);  //弹出消息窗口，然后再自动关闭当前页
					}else{
						oAlert2(result.data.msg);
					}
				}
			});
		});
	}else{
		$("#form_save").click(function(){
			JDS.call({
				service : "defaultPaymentDataService.savePayment",
				data : [ $("#zterm").val(),$("#bukrs").val(),$("#kunnr").val(),$("#zvtext").val(),$("#zdeadline").val(),$("#waers").val(),$("#zearea").val(),$("#zkdgrp").val()],
				async : false,
				success : function(result) {
					if(result.data.res=="success"){
						window.opener.reloadFileParentWindow();
						oAlert2(result.data.msg,closeDialog);  //弹出消息窗口，然后再自动关闭当前页
					}else{
						oAlert2(result.data.msg);
					}
				}
			});
		});
	}
});
</script>
</html>