<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>样品课反馈结果维护</title>
</head>
<body>
      <%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>样品课反馈结果维护</h2>
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
				<td class="title" colspan="2">样品课反馈结果维护</td>
			</tr>
			<tr class="field">
				<td>排程开始</td>
				<td><input type="text" id="planStart" name="planStart" value="${planStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"
				></td>
			</tr>
			<tr class="field">
				<td>排程完工</td>
				<td><input type="text" name="planEnd" id="planEnd" value="${planEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"
				></td>
			</tr>
			<tr class="field">
				<td>生产状态</td>
				<td>
				<select name="prodStatus" id="prodStatus">
					<option value=""> </option>
					<option value="1" <c:if test="${prodStatus=='1' }">selected</c:if>>物料调拨</option>
					<option value="2" <c:if test="${prodStatus=='2' }">selected</c:if>>物料采购</option>
					<option value="3" <c:if test="${prodStatus=='3' }">selected</c:if>>生产中</option>
					<option value="4" <c:if test="${prodStatus=='4' }">selected</c:if>>已完成</option>
					
				</select>
				</td>
			</tr>
			<tr class="field">
				<td>生产状态备注说明</td>
				<td>
					<textarea rows="3" cols="30" name="prodStatusMemo" id="prodStatusMemo">${prodStatusMemo }</textarea>
				</td>
			</tr>
			<tr class="field">
				<td>领样说明</td>
				<td>
					<textarea rows="3" cols="30" name="ledSampleMemo" id="ledSampleMemo">${ledSampleMemo }</textarea>
				</td>
			</tr>
			<tr class="field">
				<td>单价</td>
				<td><input type="text" name="unitPrice" id="unitPrice" value="${unitPrice }"
				 onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')">
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
			service : "sampleTrackService.prodResultUpdate",
			data : [ $("#planStart").val(), $("#planEnd").val(), $("#prodStatus").val(),
					$("#prodStatusMemo").val(), $("#ledSampleMemo").val(), $("#unitPrice").val(),$("#lid").val()],
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