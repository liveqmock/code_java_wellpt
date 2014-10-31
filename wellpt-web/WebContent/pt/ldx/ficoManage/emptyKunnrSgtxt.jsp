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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新摘要信息</title>
</head>
<body>
<div class="div_body" style="">
	<div class="form_header" style="">
		<div class="form_title" style="">
			<h1>摘要信息维护</h1>
		</div>
		<div class="form_toolbar" id="toolbar" style="">
			<div class="form_operate">
				<button id="form_save"  type="button">保存</button>
				<button id="form_close" type="button">关闭</button>
			</div>
		</div>
	</div>
	<form id="" action="sgtxtTitle" class="dyform">
		<table>
			<tr>
				<td class="title" colspan="2">摘要信息维护</td>
			</tr>
			<tr class="field">
				<td width="110px">客户编号</td>
				<td><input type="text" name="kunnr" id="kunnr" value="${kunnr}" readonly="readonly" /></td>				
			</tr>
			<tr class="field">
				<td width="110px">客户简称</td>
				<td><input type="text" name="sortl" id="sortl" value="${sortl}" /></td>				
			</tr>
			<tr class="field">
				<td width="110px">摘要</td>
				<td><input type="text" name="sgtxt" id="sgtxt" value="${sgtxt}" /></td>				
			</tr>
			<tr class="field">
				<td width="110px">国家汇款人</td>
				<td><input type="text" name="zcrem" id="zcrem" value="${zcrem}" /></td>				
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
		var method="${method}";
		if(method == 0){                  // 更改
			$("#sortl").attr("readonly",true);
			
			$("#form_save").click(function(){
				JDS.call({
					service : "emptyKunnrSgtxtService.updateSgtxt",
					data : [ $("#sgtxt").val(),$("#kunnr").val(),$("#zcrem").val()],
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
		}else if( method == 1){           // 创建
			$("#sortl").blur(function(){
				JDS.call({
					service : "emptyKunnrSgtxtService.getKunnr",
					data : [ $("#sortl").val()],
					async : false,
					success : function(result) {
						if (result.data.mess != null){
							oAlert2(result.data.mess);
						}else{
							$("#kunnr").val(result.data.kunnr);					
						}
					}
				});
			});
			$("#form_save").click(function(){
				JDS.call({
					service : "emptyKunnrSgtxtService.saveSgtxt",
					data : [ $("#sgtxt").val(),$("#kunnr").val(),$("#zcrem").val()],
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
		
		
	});
</script>
</html>