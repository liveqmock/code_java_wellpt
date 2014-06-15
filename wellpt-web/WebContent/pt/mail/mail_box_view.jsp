<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>

<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>


<script language="javascript" type="text/javascript" src="${ctx}/pt/mail/js/jquery.coolautosuggest.js"></script>
	<script language="javascript" type="text/javascript" src="${ctx}/pt/mail/js/jquery.coolfieldset.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/pt/mail/js/jquery.coolfieldset.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/pt/mail/js/jquery.coolautosuggest.css" />

<script type="text/javascript"
	src="${ctx}/resources/ckeditor/ckeditor.js"></script>


<link href="${ctx}/pt/mail/js/default.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
		src="${ctx}/pt/mail/js/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/resources/validform/plugin/swfupload/swfuploadv2.2.js"></script>

<script type="text/javascript"
	src="${ctx}/pt/mail/js/swfupload.swfobject.js"></script>
<script type="text/javascript"
	src="${ctx}/pt/mail/js/swfupload.queue.js"></script>
	
<script type="text/javascript" src="${ctx}/pt/mail/js/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/handlers.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/mail_swfupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/mail.js"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;">
 <input type="hidden" id="contextPath"
	value="${ctx}"></input>
<form name="form" action="${ctx}/sendmail/sendMail.action" method="post" enctype="multipart/form-data">
<input type="hidden" id="attachment"  name="attachment" value="${mail.attachment}"/>
<input type="hidden" id="uuid" name="uuid" value="${mail.uuid }"/>
<input type="hidden" id="fileMid" name="fileMid" value="${mail.fileMid }"/>
<input type="hidden"  value="1" name="isSend"/>
<input  type="hidden" id="mailconfig" name="mailconfig" value="${mailconfig }"/>
<input  type="hidden" id="ids" name="ids" />
<div>

<button onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${pageNo}&mtype=${mtype}&mailname=${mailname}';return false;"><spring:message code="mail.btn.return"/></button>

<button id="fasong" type="submit" onclick="return check('1');"><spring:message code="mail.btn.send"/></button> <button id="cuncaogao" type="submit" onclick="return check('2');"><spring:message code="mail.btn.savecaogao"/></button></div>
<table cellpadding="0" cellspacing="0" class="tableForm">
	<tr>
		<td align="right" width="6%"><a href="#" onclick="openMailUser('to','ids');"><spring:message code="mail.label.to"/></a></td>
		<td width="94%"><textarea id="to" name="to"
					cols="99" rows="2"    >${mail.to }</textarea></td>
	</tr>


		<tr id="mcc" align="right">
			<td><label><a href="#" onclick="openMailUser('cc','ids');"><spring:message code="mail.label.cc"/></a></label></td>
			<td><input name="cc" type="text" id="cc" value="${mail.cc }"
				size="100" /></td>
		</tr>
<c:if test="${mail.bcc!='' }">
	<tr id="mbcc"  style="display: none">
		<td ><spring:message code="mail.label.bcc"/></td>
		<td><input name="bcc" type="text" id="bcc"  size="100" value="${mail.cc }" /></td>
	</tr>
</c:if>
<c:if test="${mail.bcc=='' }">
	<tr id="mbcc"  style="display: none">
		<td ><spring:message code="mail.label.bcc"/></td>
		<td><input name="bcc" type="text" id="bcc"  size="100"  /></td>
	</tr>
</c:if>
	<tr style="display: none">
		<td></td>
		<td align="left">
		<table>
			<tr>
				<td id="addCc"><a href="#" onclick="addCc();"><spring:message code="mail.label.addcc"/></a>-</td>
				<td style="display: none" id="delCc"><a href="#"
					onclick="delCc();"><spring:message code="mail.label.delcc"/></a>-</td>
				<td id="addBcc"><a href="#" onclick="addBcc();"><spring:message code="mail.label.addbcc"/></a></td>
				<td style="display: none" id="delBcc"><a href="#"
					onclick="delBcc();"> <spring:message code="mail.label.delbcc"/></a></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td ><spring:message code="mail.label.title"/></td>
		<td><input id="subject" name="subject" type="text" size="100" value="${mail.subject }" /></td>
	</tr>

	<tr>
				<td align="right"></td>
				<td><span id="spanButtonPlaceholder"></span></td>
			</tr>
			<tr>
				<td></td>
				<td>

				<table id="idFileList">
				<c:forEach items="${mail.attachmentParts }" var="rf">
				<tr style="color:green">
				<td class="uploadTD">
				<c:out value="${rf.filename }"></c:out>(<c:out value="${rf.length }"></c:out>)
				</td>
				<td align="left" width="auto">
				<span   class=uploadBar></span>
				</td>
				<td align="left" width="auto" class="uploadTD">
				<span  onclick="javascript:deleteFile(this);"  class=uploadCancel><spring:message code="mail.btn.delete"/></span>
				</td>
				</tr>
				</c:forEach>
				</table>

				</td>
			</tr>
	<tr>
		<td ><spring:message code="mail.label.text"/></td>
		<td><textarea id="text" name="text" rows="10" cols="100">${mail.text }</textarea></td>
	</tr>
</table>
<div><spring:message code="mail.label.from"/>：<input name="from" type="text" readonly="readonly" value="${mail.from }"/></div>
<div>

<button onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${pageNo}&mtype=${mtype}&mailname=${mailname}';return false;"><spring:message code="mail.btn.return"/></button>

<button id="fasong" type="submit" onclick="return check('1');"><spring:message code="mail.btn.send"/></button> <button id="cuncaogao" type="submit" onclick="return check('2');"><spring:message code="mail.btn.savecaogao"/></button></div>
</form>


</body>
<script type="text/javascript">
CKEDITOR.replace('text',addUploadButton(this));
</script>
</html>