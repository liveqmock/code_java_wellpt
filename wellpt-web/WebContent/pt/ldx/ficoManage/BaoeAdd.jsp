<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>新增客户保额</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>保额维护</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_save" type="butoon">保存</button>
          <button id="form_close" type="button">关闭</button>
        </div>
      </div>
    </div>
    <form action="" id="baoeTitle" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="2">客户保额</td>   
        </tr>
        <tr class="field">
          <td width="110px">客户编号</td>          
          <td><input type="text" name="kunnr" id="kunnr" value="${kunnr}" /></td>
        </tr>
        <tr class="field">
          <td width="110px">客户简称</td>          
          <td><input type="text" name="sortl" id="sortl" value="${sortl}" readonly /></td>
        </tr>
        <tr class="field">
          <td width="110px">保额</td>          
          <td><input type="text" name="zbemt" id="zbemt" value="${zbemt}" /></td>
        </tr>
        <tr class="field">
          <td width="110px">币种</td>          
          <td><input type="text" name="waers" id="waers" value="${waers}" /></td>
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
	var method = <%=request.getAttribute("method")%>;
		
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	$("#form_close").click(function(){
		window.close();
	});
	
	/**
	  method = 0: 更改
	  method = 1: 创建
	*/
	
	if (method === 0){
		$("#kunnr").attr("readonly",true);
		
		$("#form_save").click(function(){
			JDS.call({
				service : "defaultBaoeDataService.updateBaoe",
				data : [ $("#kunnr").val(),$("#zbemt").val(),$("#waers").val()],
				async : false,
				success : function(result) {
					if(result.data.res=="success"){
						window.opener.reloadFileParentWindow();
						oAlert2(result.data.msg,closeDialog);
					}else{
						oAlert2(result.data.msg);
					}
				}
			});
		});
	}else{
		$("#kunnr").blur(function(){
			JDS.call({
				service : "defaultBaoeDataService.getSortl",
				data : [ $("#kunnr").val()],
				async : false,
				success : function(result) {
					if (result.data.mess != null){
						oAlert2(result.data.mess);
					}else{
						$("#sortl").val(result.data.sortl);					
					}
				}
			});
		});
		$("#form_save").click(function(){
			JDS.call({
				service : "defaultBaoeDataService.saveBaoe",
				data : [ $("#kunnr").val(),$("#zbemt").val(),$("#waers").val()],
				async : false,
				success : function(result) {
					if(result.data.res=="success"){
						window.opener.reloadFileParentWindow();
						window.close();
					}else{
						oAlert2(result.data.msg);
					}
				}
			});
		});
	}	
})

</script>
</html>