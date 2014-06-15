<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />

<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
<script src="${ctx}/resources/schedule/setschedule.js" type="text/javascript"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<style type="text/css">
table {
    border: medium none;
    width: 90%;
    margin: 0 auto;
    font-family: "Microsoft YaHei";
}
.tr_title td {
	background: url("${ctx}/resources/jqueryui/css/base/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
   	border: 1px solid #C5DBEC;
   	color: #2E6E9E;
   	font-weight: bold;
   	font-size: 12px;
}
.tr_content td {
    border: 1px solid #C5DBEC;
    color: #000;
}
a {
    color: #000000;
    font-size: 12px;
}
button {
	background: url("/wellpt-web/resources/jqueryui/css/base/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    border: 1px solid #C5DBEC;
    border-radius: 3px;
    color: #2E6E9E;
    font-size: 14px;
    font-family: "Microsoft YaHei";
    padding: 5px 10px;
    margin: 5px 0;
}
</style>		
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	
	<input type="hidden" id="contextPath" value="${ctx}"></input>
	<div id="toolbar">
		<table width="100%"><tr>
		<td align="right">
		<button onclick="openSecNew();"><spring:message code="schedule.btn.add" /></button>
		</td></tr></table>
	</div> 
	
	
	<table width="100%"  border="2" >
		<tr class="tr_title">
		<td width="5%">
		<spring:message code="schedule.info.sec" />
		<div  style="width:100%;overflow:hidden;"  >
			<div id="group2"></div>
		</div>
		</td>
		
		<td width="45%">
		<spring:message code="schedule.info.leader" />
		</td>
		</tr>
		<c:forEach items="${dataList2}" var="row2">
		<tr class="tr_content">
		
		<td>
		<a href="#" onclick="openSec('${row2.secUuid }','${row2.secUserNo }','${row2.secUserName }','${row2.leaderUserNos }','${row2.leaderUserNames }');">
		<c:out value="${row2.secUserName }"></c:out>
		</a>
		</td>
		
		<td>
		<a href="#" onclick="openSec('${row2.secUuid }','${row2.secUserNo }','${row2.secUserName }','${row2.leaderUserNos }','${row2.leaderUserNames }');">
		<c:out value="${row2.leaderUserNames }"></c:out>
		</a>
		</td>
		</tr>
		</c:forEach>
			
	</table>
	

<div id="dialogModule" title="" style="padding:0;margin:0; display:none;">
<div class="dialogcontent"></div>
</div>


<script  type="text/javascript">

</script>

</body>
</html>