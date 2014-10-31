<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>数据同步</title>
</head>
<body>
	<div class="div_body" style="">
		<form action="" id="dataSynchronizeForm" class="dyform">
			<div class="form_header noprint" style="">
				<div class="form_title">
					<h2>数据同步</h2>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div class="form_operate" style="float:left;">
						<table>
							<tbody>
								<tr class="field">
									<td>
										<button id="synMailAddrBtn" type="button" onclick="return false;">同步邮件配置</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<table></table>
		</form>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	var div_body_width = 1200;
	$(".form_header").css("width", div_body_width - 5);
	$(".div_body").css("width", div_body_width);
	$("#synMailAddrBtn").click(function() {
		JDS.call({
			service : "ficoDataSynchronizeService.saveMailAddr",
			data : [],
			async : false,
			success : function(result) {
				alert("done");
			}
		});
	});
});
</script>
</html>