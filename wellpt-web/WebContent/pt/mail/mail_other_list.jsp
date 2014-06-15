<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	
	<input type="hidden" id="contextPath" value="${ctx}"></input>
	<div id="msg" ></div>
	<div id="toolbar">
		
		<div align="right">
			<button onclick="window.location.href='${ctx}/mail/mailother_first.action'"><spring:message code="mail.label.addOtherMailN"/></button>
		</div>
	</div>
	<table>
		
			<tr>
				<td  width="30%"><spring:message code="mail.label.otherMailN"/></td>
				<td  width="10%"><spring:message code="mail.label.noReadMail1"/></td>
				<td  width="40%"><spring:message code="mail.label.totalMailN"/></td>
				<td  width="20%"><spring:message code="mail.label.operation"/></td>
			</tr>
	
		<c:forEach items="${dataList }" var="row">
			<tr >
			<td>
			<c:out value="${row.mailName }"></c:out>
			</td>
			<td >
			<span id="${row.uuid }noread" ><c:out value="${row.noRead }"></c:out></span>
			</td>
			<td >
			<span id="${row.uuid}total" ><c:out value="${row.total }"></c:out></span>
			</td>
			<td>
			<a href="#" onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=6&mailname=${row.mailName }&pageNo=0&mtype=0'"><spring:message code="mail.label.otherRead"/></a>
			<a href="#" onclick="receive('${row.uuid }','${row.mailName}','${row.password }','${row.pop3 }','${row.pop3Port }','${row.imap }','${row.imapPort }');"><spring:message code="mail.label.receiveGetMail"/></a>
			<a href="#" onclick="toNext('${row.uuid}');"><spring:message code="mail.label.otherSet"/></a>
			
			</td>
			</tr>
		</c:forEach>
	</table>
</body>
<script type="text/javascript">
	function toNext(uuid){
		window.location.href=contextPath+"/mail/mailother_next22.action?uuid="+row.uuid+"&delFlag=1";
	}
	
	</script>
</html>