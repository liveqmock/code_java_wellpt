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
<title>未知客户补充资料</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>未知客户补充资料</h2>
      </div>
      <div id="toolbar" class="form_toolbar">
        <div class="form_operate">
          <button id="form_save" type="butoon">保存</button>
          <button id="form_close" type="button">关闭</button>
        </div>
      </div>
    </div>
    <form action="" id="customerForm" class="dyform">
      <table>
        <tr>
          <td class="title" colspan="2">未知客户补充资料</td>   
        </tr>
        <tr class="field">
          <td width="110px">流水号</td>          
          <td><input type="text" name="zbelnr" id="zbelnr" value="${zbelnr}" class="required" readonly="readonly"/></td>
        </tr>
        <tr class="field">
          <td width="110px">备注</td>          
          <td><input type="text" name="ztext" id="ztext" value="${ztext}" class="required"/></td>
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
		
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width",div_body_width - 5);
	$(".body").css("width",div_body_width);
	$("#form_close").click(function(){
		window.close();
	});
	$("#form_save").click(function(){
		if ($("#customerForm").validate(Theme.validationRules).form()) {
			JDS.call({
				service : "emptyKunnrEmailService.saveText",
				data : [ $("#zbelnr").val(),$("#ztext").val() ],
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
		}
	});
})

</script>
</html>