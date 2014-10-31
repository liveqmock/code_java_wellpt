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
<style>
<c:if test="${addCss!='yes'}">
	#tabtop-L3 strong{
 		background: #fee; 
	}
	</c:if >
.schedule_leader_list2_content{
	width: 100%;
	border:1px solid #000000;
}
.tr_title{
	background: gray;
}
.table_l1_bg{
	background: gray;
}
.table_tr{
	border:1px solid #000000;
}
.table_tr_bg{
	background: gray;
}
.table_tr_tr1_td{
	border:1px solid #000000;
}
</style>		
</c:if>
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div class="schedule_leader_list2 schedule_css">
	<c:if test="${addCss=='yes'}">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
<%-- 	<input type="hidden" id="contextPath" value="${ctx}"></input> --%>
	<div id="toolbar">
		<table width="100%" ><tr><td align="left">
		<a  class="s_prev_day" ldate=${firstday } mtype=${mtype } href="#" ><</a>
		<span class="s_taday" name="firstday" >${firstday1 }</span>
		<a class="s_next_day" ldate=${firstday } mtype=${mtype } href="#" href="#" >></a>
		<span class="fromandto">(${from })<spring:message code="schedule.info.zhi" />(${to })</span>
		</td>
		<td align="right">
		 <a class="as_day" href="${ctx}/schedule/exportExcel.action?ldate=${firstday }&ctype=1&mtype=${mtype }" ><spring:message code="schedule.btn.export" /></a>
		 <a class="as_day asleader"  ldate=${firstday } mtype=${mtype } href="#" ><spring:message code="schedule.btn.lingdao" /></a>
		</td></tr></table>
	</div>
	<c:set var="count" value="1"></c:set>
	<table class="schedule_leader_list2_content">
		<c:forEach items="${weeks}" var="row">
			<c:if test="${row.sdate==now }">
			<tr class="table_tr table_tr_today table_tr_<c:if test="${count%2==1 }">odd</c:if><c:if test="${count%2==0 }">even</c:if>" >
			</c:if>
			<c:if test="${row.sdate!=now }">
			<tr class="table_tr table_tr_<c:if test="${count%2==1 }">odd</c:if><c:if test="${count%2==0 }">even</c:if>">
			</c:if>
			<c:set var="count" value="${count+1}"></c:set>
			<td height="100" width="10%"  class="table_l1_bg">
			<c:out value="${row.displayDay }"/>
			</td>
			<td colspan="4" class="table_l1_bg2" onmouseover="showorno('${row.sdate }','six','1');" onmouseout="showorno('${row.sdate }','six','2');">
			<table width="100%"  style="border-collapse:collapse" cellspacing="0px">
			<tr class="tr_title">
		<td width="10%">
		</td>
		<td width="17%">
		<spring:message code="schedule.info.date" />
		</td>
		<td width="20%">
		<spring:message code="schedule.info.address" />
		</td>
		<td width="33%">
		<spring:message code="schedule.info.huodong" />
		</td>
		<td width="30%">
		<spring:message code="schedule.info.person" />
		</td>
		</tr>
			
			<c:forEach items="${sche[row.sdate] }" var="row2">
			<tr class="table_tr_tr1" valign="top" >
			<td width="17%" class="table_tr_tr1_td" >
			<a href="#" onclick="openScheduleDialog('${leaderNames }','${leaderIds }','${mtype }','${ldate }','${userno }','${row2.status }','2','','','${row2.tipMethod }','${row2.isComplete }','${row.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color }','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }','${row2.isLeaderView}','${row2.inviteeNames}','${row2.inviteeIds}','${row2.acceptIds }','${row2.acceptNames }','${row2.refuseIds}','${row2.refuseNames}','${row2.tag.uuid}');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			</c:if>
			</font>
			</a>
			</td>
			<td width="20%" class="table_tr_tr1_td">
			<a href="#" onclick="openScheduleDialog('${leaderNames }','${leaderIds }','${mtype }','${ldate }','${userno }','${row2.status }','2','','','${row2.tipMethod }','${row2.isComplete }','${userno }','${row.sdate }','${row.sdate }six','test_con3','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color }','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }','${row2.isLeaderView}','${row2.inviteeNames}','${row2.inviteeIds}','${row2.acceptIds }','${row2.acceptNames }','${row2.refuseIds}','${row2.refuseNames}','${row2.tag.uuid}');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:choose><c:when test="${fn:length(row2.address)>20 }"><c:out value="${fn:substring(row2.address,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:choose><c:when test="${fn:length(row2.address)>20 }"><c:out value="${fn:substring(row2.address,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose>
			</c:if>
			
			</font>
			</a>
			</td>
			<td width="33%" class="table_tr_tr1_td">
			<a href="#" onclick="openScheduleDialog('${leaderNames }','${leaderIds }','${mtype }','${ldate }','${userno }','${row2.status }','2','','','${row2.tipMethod }','${row2.isComplete }','${userno }','${row.sdate }','${row.sdate }six','test_con3','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color }','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }','${row2.isLeaderView}','${row2.inviteeNames}','${row2.inviteeIds}','${row2.acceptIds }','${row2.acceptNames }','${row2.refuseIds}','${row2.refuseNames}','${row2.tag.uuid}');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:choose><c:when test="${fn:length(row2.scheduleName)>20 }"><c:out value="${fn:substring(row2.scheduleName,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:choose><c:when test="${fn:length(row2.scheduleName)>20 }"><c:out value="${fn:substring(row2.scheduleName,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			</c:if>
			
			</font>
			</a>
			</td>
			<td width="30%" class="table_tr_tr1_td">
			<a href="#" onclick="openScheduleDialog('${leaderNames }','${leaderIds }','${mtype }','${ldate }','${userno }','${row2.status }','2','','','${row2.tipMethod }','${row2.isComplete }','${userno }','${row.sdate }','${row.sdate }six','test_con3','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color }','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }','${row2.isLeaderView}','${row2.inviteeNames}','${row2.inviteeIds}','${row2.acceptIds }','${row2.acceptNames }','${row2.refuseIds}','${row2.refuseNames}','${row2.tag.uuid}');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:choose><c:when test="${fn:length(row2.allNames)>20 }"><c:out value="${fn:substring(row2.allNames,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.allNames }"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:choose><c:when test="${fn:length(row2.allNames)>20 }"><c:out value="${fn:substring(row2.allNames,0,20) }..."/></c:when><c:otherwise><c:out value="${row2.allNames }"/></c:otherwise></c:choose>
			</c:if>
			
			</font>
			</a>
			</td>
			
			</tr>
			</c:forEach>
			<tr class="table_tr_tr1">
			<td align="left" colspan="4">
			<div id="${row.sdate }six" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('','','${leaderNames }','${leaderIds }','1','2','','','${creators}','${creatorIds}','${row.sdate }','${ldate }','${mtype }','${now }','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</td>
			</tr>
			</table>
			</td>
			</tr>
		
		</c:forEach>
	</table>
	<div class="view_foot"></div>
<div id="dialogModule" title="" style="padding:0;margin:0; display:none;">
<div class="dialogcontent"></div>
</div>

<c:if test="${addCss!='yes'}">
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<!-- jQuery UI -->
	<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
</c:if>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>

<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
		<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
	<script type="text/javascript">
		//上一月
$(".s_prev_day").click(function(){
	var ldate = $(this).attr("ldate");
	var mtype = $(this).attr("mtype");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":1,"ctype":1,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list2").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&stype=1&ctype=1&mtype='+mtype;
	}
});
//下一月
$(".s_next_day").click(function(){
	var ldate = $(this).attr("ldate");
	var mtype = $(this).attr("mtype");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":2,"ctype":1,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list2").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&stype=2&ctype=0&mtype='+mtype;
	}
});
//按事项
$(".asleader").click(function(){
	var mtype = $(this).attr("mtype");
	var ldate = $(this).attr("ldate");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list2").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&mtype='+mtype;
	}
});
</script>
</div>
</body>
</html>