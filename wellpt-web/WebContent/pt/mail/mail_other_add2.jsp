<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
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
button:hover{
	color: #ff7200;
}


		
/*******添加其他邮箱*******/
.other_div_td {
    padding-bottom: 10px;
    padding-right: 10px;
    text-align: right;
}
.other_div td {
    margin-top: 18px;
}
.other_div input[type="checkbox"] {
    margin: 0;
}
.other_div {
    margin-left: 40px;
    margin-top: 20px;
}

.mail_set {
    bottom: 0;
    left: 40%;
    padding: 15px;
    position: absolute;
}
.other_mail_input {
    width: 77%;
}
.other_mail_top {
    text-align: center;
}

.othermail_set_del {
    bottom: 220px;
    left: 270px;
    position: absolute;
}
</style>
</head>
<body>
	 <div class="box">
	<div style="padding:3px 2px;border-bottom:1px solid #ccc" class="other_mail_top"><spring:message code="mail.label.otherConfirm"/></div>  
	<center><label  id="msg" style="color: red;">${msg }</label></center>
<%-- 	<form action="${ctx }/mail/mailother_next2.action"> --%>
	<input type="hidden" name="uuid" id="uuid" value="${bean.uuid }" />
	<div class="other_div">
	<table>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.yourOtherMail"/>：
	</td>
	<td class="other_mail_input">
	<input type="text" size="15" name="username"  id="username" value="${bean.username }"  class="other_mail_input" readonly="readonly"/>
	</td>
	</tr>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.pass"/>：
	</td>
	<td>
	<input type="password"" size="15" id="password" name="password" value="${bean.password }"  class="other_mail_input"/>
	</td>
	</tr>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.alies"/>：
	</td>
	<td>
	<input type="text" size="15" name="aliesName" id="aliesName" value="${bean.aliesName }"  class="other_mail_input"/>
	</td>
	</tr>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.otherPop"/>：
	</td>
	<td>
	<input type="text" size="15" name="pop3" id="pop3" value="${bean.pop3 }"  class="other_mail_input"/>
	</td>
	</tr>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.otherPopPort"/>：
	</td>
	<td>
	<input type="text" size="15" name="pop3Port" id="pop3Port" value="${bean.pop3Port }"  class="other_mail_input" readonly="readonly"/>
	</td>
	</tr>
	<tr>
	<td  class="other_div_td">
	<spring:message code="mail.label.receiveGetSet"/>：
	</td>
	<td>
	<input type="checkbox"" name="method" id="method"   checked="${bean.method }"><spring:message code="mail.label.autoGet"/></input><br/>
	<input type="checkbox"" name="keepOnServer" id="keepOnServer" checked="${bean.keepOnServer }"><spring:message code="mail.label.keepOnServer2"/></input>
	</td>
	</tr>
	<c:if test="${delFlag=='1' }">
		<tr class="othermail_set_del"><td colspan="2"><button type="button" onclick="delOtherMail('${bean.uuid }','${bean.username }');"><spring:message code="mail.btn.deleteOtherMail"/></button></td></tr>
	</c:if>
	</table>
<!-- 	<div> -->
<!-- 	<div> -->
<%-- 	<button type="submit" onclick="return addOtherMail();"><spring:message code="mail.btn.confirm"/></button> --%>
<%-- 	<button onclick="OtherAddCancel();"><spring:message code="mail.btn.cancel"/></button> --%>
<!-- 	</div> -->
		<div id="changgui" style="display: none;"></div>
        	<div class="mail_set">
	        	<center>
<%-- 	        		<c:if test="${delFlag=='1' }"> --%>
<%-- 						<button onclick="delOtherMail('${bean.uuid }','${bean.username }');"><spring:message code="mail.btn.deleteOtherMail"/></button> --%>
<%-- 					</c:if> --%>
<%-- 		        	<button onclick="return addOtherMail();"><spring:message code="mail.btn.confirm"/></button> --%>
		        	<button onclick="confirmAddOtherMail();"><spring:message code="mail.btn.confirm"/></button>
		        	<button onclick="OtherAddCancel();"><spring:message code="mail.btn.cancel"/></button>
	        	</center>
        	</div>
<!-- 	</div> -->
<!-- 	</form> -->
	<script type="text/javascript">
	//删除其他邮件
	function delOtherMail(uuid,groupName){
		var obj = new Object(); 
		obj.uuid = uuid;
		obj.groupName = groupName;
		oConfirm (global.deleteConfirm,delOtherMail1,obj,"");
	}
	function delOtherMail1(obj){
		var uuid = obj.uuid;
		var groupName = obj.groupName;
		$.ajax({
			type : "POST",
			url : contextPath+"/mail/del_otherMain.action",
			data : "uuid="+uuid+"&groupName="+groupName,
			dataType : "text",
			success : function callback(result) {
				oAlert("删除成功！");
				refreshWindow($(".box"));
			}
		});
	}
	
// 	function delOtherMail(uuid,groupName){
// 		if(confirm(global.deleteConfirm)) {
// 		$.ajax({
// 			type : "POST",
// 			url : contextPath+"/mail/del_otherMain.action",
// 			data : "uuid="+uuid+"&groupName="+groupName,
// 			dataType : "text",
// 			success : function callback(result) {
// 		/* 		window.location.href=contextPath+"/mail/mail_pconfig.action?ptype=3"; */
// 			}
// 		});
// 		}
// 	}


	//确认添加其他邮件
	function confirmAddOtherMail(){
		var bean = new Object();
		bean.uuid = "${bean.uuid}";
		bean.username = "${bean.username}";
		bean.password = $("#password").val();
		bean.aliesName = $("#aliesName").val();
		bean.pop3 = $("#pop3").val();
		bean.pop3Port = "${bean.pop3Port}";
		bean.method = ${bean.method};
		bean.keepOnServer = ${bean.keepOnServer};
		$.ajax({
			type : "POST",
			url : contextPath+"/mail/mailother_next2.action",
			data : bean,
			dataType : "text",
			success : function callback(result) {
				$("#20136315910846 div div").html(result);
			}
		});
	}
// 	function addOtherMail(){
// 		var password=$("#password").val();
// 		if(password==''){
// 			oAlert2(global.passIsNeed);
// 			$("#password").focus();
// 			return false;
// 		}
// 		return true;
// 	}
	</script>
	</div>
</body>
</html>