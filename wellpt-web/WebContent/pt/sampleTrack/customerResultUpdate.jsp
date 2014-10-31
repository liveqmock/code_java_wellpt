<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户反馈结果维护</title>
</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>客户反馈结果维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
		<table>
			<tr>
				<td class="title" colspan="2">客户反馈结果维护</td>
			</tr>
			<tr class="field">
				<td>实际送样目的地</td>
				<td><input type="text" id="sampleDestination" name="sampleDestination" value="${sampleDestination }"
				></td>
			</tr>
			<tr class="field">
				<td>送样时间</td>
				<td><input type="text" name="sampleDate" id="sampleDate" value="${sampleDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"
				></td>
			</tr>
			<tr class="field">
				<td>快递单号</td>
				<td>
				<input type="text" name="expressageNum" id="expressageNum" value="${expressageNum }">
				</td>
			</tr>
			<tr class="field">
				<td>客户反馈结果</td>
				<td>
					<textarea rows="4" cols="30" name="customerResult" id="customerResult">${customerResult }</textarea>
				</td>
			</tr>
			<tr class="field">
				<td>提醒品质异常责任部门</td>
				<td>
					<input type="checkbox" name="qcExceptReply" id="qcExceptReply">
				</td>
			</tr>
			<tr class="field">
				<td>提醒交期异常责任部门</td>
				<td><input type="checkbox" name="prodExceptReply" id="prodExceptReply">
				<input type="hidden" name="id" id="lid" value="${lineItemId }"></td>
			</tr>
		</table>
		</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
	
	$("#form_close").click(function(){
		window.close();
	});
	$("#form_save").click(function() {
		JDS.call({
			service : "sampleTrackService.customerUpdate",
			data : [ $("#sampleDestination").val(), $("#sampleDate").val(), $("#expressageNum").val(),
					$("#customerResult").val(), $("#qcExceptReply").val(), $("#prodExceptReply").val(),$("#lid").val()],
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
});
</script>
</body>
</html>