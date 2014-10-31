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
<title>工单计划量维护</title>
</head>
<body>
  <div class="div_body" style="">
    <div class="form_header" style="">
      <div class="form_title" style="">
        <h2>工单计划量维护</h2>
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
          <td class="title" colspan="2">工单计划量</td>   
        </tr>
        <tr class="field">
          <td width="110px">类型:</td>          
          <td>
          	    <select id="typeSel" name="type" style='width: 100%;border: 0;padding: 0;margin: 0;' value='${type}'>
					<option value='生管' <c:if test="${'生管' eq type}">selected</c:if>>生管</option>
					<option value='生产订单' <c:if test="${'生产订单' eq type}">selected</c:if>>生产订单</option>
				</select>
			</td>
        </tr>
        <tr class="field">
          <td width="110px" id="typeLable">生管</td>          
          <td><input type="text" name="key" id="key" value="${key}" /></td>
        </tr>
        <tr class="field">
          <td width="110px">工单计划量</td>          
          <td><input type="text" name="zjhl" id="zjhl" value="${zjhl}" /></td>
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
		$("#typeSel").attr("disabled",true);
		$("#key").attr("readonly",true);
		$("#form_save").click(function(){
			JDS.call({
				service : "productionWorkOrderService.updateWoConfig",
				data : [ $("#typeSel").val(),$("#key").val(),$("#zjhl").val()],
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
		$("#typeSel").change(function(){
			$("#typeLable").text($("#typeSel").val());
		});
		$("#form_save").click(function(){
			JDS.call({
				service : "productionWorkOrderService.saveWoConfig",
				data : [ $("#typeSel").val(),$("#key").val(),$("#zjhl").val()],
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