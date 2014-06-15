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
</head>
<body>
	<div style="padding:3px 2px;border-bottom:1px solid #ccc"><spring:message code="mail.label.writeMailAddress"/></div>  
	<div>${msg }</div>
	<form action="${ctx }/mail/mailother_next.action">
	<table>
	<tr>
	<td>
	<input type="text" name="address" size="15" value="${address }"/>
	</td>
	<td>
	<button type="submit"><spring:message code="mail.label.next"/></button><button type="button" onclick="OtherAddCancel();"><spring:message code="mail.btn.cancel"/></button>
	</td>
	</tr>
	</table>
	</form>
</body>
</html>