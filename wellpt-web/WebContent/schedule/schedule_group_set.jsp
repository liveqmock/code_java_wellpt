<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:if test="${addCss!='yes'}">
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />

<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
		<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<c:if test="${addCss!='yes'}">
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
</c:if>
<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
</c:if>
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div class="group_set schedule_css">
	<c:if test="${addCss=='yes'}">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
	</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
	<input type="hidden" id="contextPath" value="${ctx}"></input>
	<div id="toolbar">
		<table width="100%"><tr>
		<td align="left"><span class="fromandto"><spring:message code="schedule.btn.groupset" /></span></td>
		<td align="right">
		<c:if test="${addCss!='yes'}">
		<a class="as_day" href="#" onclick="window.location.href='${ctx}/schedule/group_schedule.action?ldate=${ldate }&ctype=${ctype }&groupid=${groupid }';"><spring:message code="schedule.btn.return" /></a>
		</c:if>
		<a class="as_day" href="#" onclick="openLayerGroupSet();"><spring:message code="schedule.btn.add" /></a>
		</td></tr></table>
	</div> 
	<table width="100%"  border="2" class="content">
		<tr>
		<td width="5%">
		<spring:message code="schedult.info.groupName" />
		<div  style="width:100%;overflow:hidden;"  >
			<div id="group"></div>
		</div>
		</td>
		
		<td width="40%">
		<spring:message code="schedult.info.groupUser" />
		</td>
		
		</tr>
		<c:forEach items="${dataList}" var="row">
		<tr>
		<td>
		<a href="#" onclick="openLayerGroupSet2('${row.uuid }','${row.groupName }','${row.userNames }','${row.userIds }');">
		<c:out value="${row.groupName }"></c:out>
		</a>
		</td>
		<td>
		<a href="#" onclick="openLayerGroupSet2('${row.uuid }','${row.groupName }','${row.userNames }','${row.userIds }');">
		<c:out value="${row.userNames }"></c:out>
		</a>
		</td>
		
		</tr>
		</c:forEach>
	</table></div><div class="view_foot"></div>
	<div id="dialogModule" title="" style="padding:0;margin:0; display:none;">
<div class="dialogcontent"></div>
</div>
	
	

</body>
</html>