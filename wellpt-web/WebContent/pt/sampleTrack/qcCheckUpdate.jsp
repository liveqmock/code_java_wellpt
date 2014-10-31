<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>品保检验结果</title>
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
				<h2>品保检验结果</h2>
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
				<td class="title" colspan="2">品保检验结果</td>
			</tr>
			<tr class="field">
				<td>检验开始时间</td>
				<td><input type="text" name="qcCheckDate" id="qcCheckDate" value="${qcCheckDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"
				></td>
			</tr>
			<tr class="field">
				<td>检验完成时间</td>
				<td><input type="text" name="qcFinishDate" id="qcFinishDate" value="${sampleDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
					class="Wdate required"
				></td>
			</tr>
			<tr class="field">
				<td>品保判定结果</td>
				<td>
					<select name="qcCheckResult" id="qcCheckResult">
					    <option value=""></option>
					    <option value="1" <c:if test="${qcCheckResult=='1' }">selected</c:if>>OK</option>
					    <option value="2" <c:if test="${qcCheckResult=='2' }">selected</c:if>>NG</option>
					    <option value="3" <c:if test="${qcCheckResult=='3' }">selected</c:if>>放行</option>
					    <option value="4" <c:if test="${qcCheckResult=='4' }">selected</c:if>>特采出货</option>
					</select>
				</td>
			</tr>
			<tr class="field">
				<td>二次判定结果</td>
				<td>
				<select name="qcSecondResult" id="qcSecondResult">
				    <option value=""></option>
				    <option value="1" <c:if test="${qcSecondResult=='1' }">selected</c:if>>OK</option>
				    <option value="2" <c:if test="${qcSecondResult=='2' }">selected</c:if>>NG</option>
				    <option value="3" <c:if test="${qcSecondResult=='3' }">selected</c:if>>放行</option>
				    <option value="4" <c:if test="${qcSecondResult=='4' }">selected</c:if>>特采出货</option>
				</select>
				</td>
			</tr>
			<tr class="field">
				<td>品保检验项目</td>
				<td>
					<textarea rows="3" cols="30" name="qcCheckItem" id="qcCheckItem">${qcCheckItem }</textarea>
				</td>
			</tr>
			<tr class="field">
				<td>品保检验描述</td>
				<td><textarea rows="3" cols="30" name="" id="qcCheckMemo">${qcCheckMemo }</textarea>
			</tr>
			<tr class="field">
				<td>品质异常原因</td>
				<td><textarea rows="3" cols="30" name="qcExceptCause" id="qcExceptCause">${qcExceptCause }</textarea>
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
			service : "sampleTrackService.qcCheckUpdate",
			data : [ $("#qcCheckDate").val(), $("#qcFinishDate").val(), $("#qcSecondResult").val(),$("#qcCheckResult").val(),
					$("#qcCheckItem").val(), $("#qcCheckMemo").val(), $("#qcExceptCause").val(),$("#lid").val()],
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