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
<title>选择登录类型</title>
</head>
<body>
	<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>选择登录类型</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button id="form_save" type="button">确定</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
			<table>
				<tr class="field">
					<td width="110px;">登录类型</td>
					<td>
						<select name="userType" id="userType" >
							<c:forEach var="tp" items="${typeList}">
								<option value="${tp[1]}">${tp[0]}</option>
							</c:forEach>
						</select>
					</td>
					<td width="110px;"><p id="sgLable">生管</p></td>
					<td>
						<select name="productionManager" id="productionManager" style="display:none;">
							<c:forEach var="sg" items="${sgList}">
								<option value="${sg}">${sg}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var div_body_width = $(window).width() * 0.76;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
	$("#form_close").click(function() {
		window.close();
	});
	$('#userType').change(function(){
		var type = $('#userType')[0].value;
		if(type == "1"){
			$('#sgLable').hide();
			$('#productionManager').hide();
		}
		if(type == "3"){
			$('#sgLable').show();
			$('#productionManager').show();
		}
	});
	$('#form_save').click(function(){
		var url = ctx + "/productionPlan/${planType}/redirect";
		$('#capacityform').attr("action",url).submit();
	});
});
</script>
</html>