<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加其他邮件成功</title>
<%@ include file="/pt/common/meta.jsp"%>

<script src="${ctx}/resources/jquery/jquery.js"></script>
	<!-- jQuery UI -->
	<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/pt/mail/js/mail.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
	<script type="text/javascript">
	</script>
	
	<style type="text/css">
.box {
 	align:center;
	height:auto; 
}
.box button {
    border: 1px solid #DEE1E2;
    color: #0F599C;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    height: 22px;
    padding: 0 5px;
    background: url("${ctx}/resources/theme/images/v1_icon.png") repeat-x scroll 0 -165px transparent;
}
.box_tab {
    display: block;
    margin: 20px 100px;
}
button:hover{
	color: #ff7200;
}
.mail_set {
    bottom: 0;
    left: 40%;
    padding: 15px;
    position: absolute;
}
.add_success_title{
 	 border-bottom: 1px solid #CCCCCC;
    padding: 3px 95px;
}
.box_tab .daigui{
	margin-right: 5px;
    margin-top: -4px;
}

#daigui2 {
    margin-left: 8px;
    margin-top: 6px;
}
</style>
</head>
<body>
	 <div class="box">
		<div class="add_success_title"><spring:message code="mail.label.seccessAdd"/>${address }</div>  
	<%-- 	<form action="${ctx }/mail/mailother_next3.action"> --%>
		<input type="hidden" name="uuid" id="uuid" value="${uuid }" />
		<div class="box_tab">
			<table>
			<tr>
				<td>
					<input type="radio" id="daigui" name="daigui" class="daigui"  value="1" checked="checked"><spring:message code="mail.label.get"/><select id="daigui2"  name="daigui2" ><option selected="selected" value="1"><spring:message code="mail.label.get7"/></option><option value="2"><spring:message code="mail.label.all"/></option></select></input>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" id="daigui" name="daigui" class="daigui"  value="2"><spring:message code="mail.label.get0"/></input>
				</td>
			</tr>
			</table>
		</div>
		<div class="mail_set">
        	<center>
				<button type="button" onclick="comOtherMail();"><spring:message code="mail.btn.complete"/></button>
			</center>
       	</div>
	</div>
<!-- 	</form> -->
</body>
<script type="text/javascript">
	function comOtherMail(){
		var uuid=$("#uuid").val();
		var daigui="";
		$(".daigui").each(function (){
			if($(this).attr("checked")=="checked"){
				daigui=$(this).val();
			}
		});
		var daigui2=$("#daigui2").val();
		$.ajax({
			type : "POST",
			url : contextPath+"/mail/mailother_next3.action",
			data : "uuid="+uuid+"&daigui="+daigui+"&daigui2="+daigui2,
			dataType : "text",
			success : function callback(result) {
				refreshWindow($(".box"));
// 				window.location.href=contextPath+"/mail/mail_pconfig.action?ptype=3";
			}
		});
	}
// 	function comOtherMail(){
// 		var uuid=$("#uuid").val();
// 		var daigui="";
// 		$(".daigui").each(function (){
// 			if($(this).attr("checked")=="checked"){
// 				daigui=$(this).val();
// 			}
// 		});
// 		var daigui2=$("#daigui2").val();
// 		$.ajax({
// 			type : "POST",
// 			url : contextPath+"/mail/mailother_next3.action",
// 			data : "uuid="+uuid+"&daigui="+daigui+"&daigui2="+daigui2,
// 			dataType : "text",
// 			success : function callback(result) {
// 				window.location.href=contextPath+"/mail/mail_pconfig.action?ptype=3";
// 			}
// 		});
// 	}
	
	</script>
</html>