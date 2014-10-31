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
.schedule_leader_list_content{
	height: 100%;
}
.tr_content_l1{
	background: gray;
}
.tr_title{
	background: gray;
}
.tr_title_taday{
	background:blue;
}
</style>		
</c:if>
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div id="container" class="schedule_leader_list schedule_css">
	<c:if test="${addCss=='yes'}">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
<%-- 	<input type="hidden" id="contextPath" value="${ctx}"></input> --%>
	<div id="toolbar">
		<table width="100%"><tr><td align="left">
		<a  class="s_prev_day" ldate=${firstday} mtype=${mtype} href="#" ><</a>
		<span class="s_taday"  name="firstday">${firstday1}</span>
		<a class="s_next_day" ldate=${firstday} mtype=${mtype} href="#" >></a>
		<span class="fromandto">(${from})<spring:message code="schedule.info.zhi" />(${to})</span>
		</td>
		<td align="right">
		 <a class="as_day" href="${ctx}/schedule/exportExcel.action?ldate=${firstday}&stype=0&mtype=${mtype}" ><spring:message code="schedule.btn.export" /></a>
		 <a class="asleader"  ldate=${firstday} mtype=${mtype} href="#" ><spring:message code="schedule.btn.shixiang" /></a>
		</td></tr></table>
	</div>
	<div class="content">
	<table class="schedule_leader_list_content" width="100%"  border="2" >
		
			<tr class="tr_title" >
				<td  width="5%"></td>
				<c:forEach items="${weeks}" var="row">
				<c:if test="${row.sdate==now}">
				<td  width="14%" class="tr_title_taday"><c:out value="${row.displayDay}"/></td>
				</c:if>
				<c:if test="${row.sdate!=now}">
				<td  width="14%"><c:out value="${row.displayDay}"/></td>
				</c:if>
				
				</c:forEach>
			</tr>
		
		<c:forEach items="${leaders}" var="row2">
		
			<tr  class="tr_content" style="">
			<td class="tr_content_l1 tr_content_td">
			${row2.userName}
			</td>
			<td class="tr_content_td" onmouseover="showorno('${row2.userNo}','one','1');" onmouseout="showorno('${row2.userNo}','one','2');"> 
			<div  style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.oneList}" var="one">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${one.displayStartDate==now}">
			
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${one.status}','1','','','${one.tipMethod}','${one.isComplete}','${week1.sdate}','${one.uuid}','${one.scheduleName}','${one.address}','${one.dstartDate}','${one.startTime}','${one.dendDate}','${one.endTime}','${one.isView}','${one.status}','${one.leaderNames}','${one.leaderIds}','${one.pleases}','${one.pleaseIds}','${one.views}','${one.viewIds}','${one.color}','${one.tip}','${one.tipDate}','${one.tipTime}','${one.repeat}','${one.startTime2}','${one.endTime2}','${one.tipTime2}','${one.creators}','${one.creatorIds}','${one.isLeaderView}','${one.inviteeNames}','${one.inviteeIds}','${one.acceptIds }','${one.acceptNames }','${one.refuseIds}','${one.refuseNames}','${one.tag.uuid}');">
			<font color="${one.color}">
			
			<c:if test="${one.isComplete=='1'}">
			<strike><c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${one.isComplete!='1'}">
			<c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${one.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${one.status}','1','','','${one.tipMethod}','${one.isComplete}','${week1.sdate}','${one.uuid}','${one.scheduleName}','${one.address}','${one.dstartDate}','${one.startTime}','${one.dendDate}','${one.endTime}','${one.isView}','${one.status}','${one.leaderNames}','${one.leaderIds}','${one.pleases}','${one.pleaseIds}','${one.views}','${one.viewIds}','${one.color}','${one.tip}','${one.tipDate}','${one.tipTime}','${one.repeat}','${one.startTime2}','${one.endTime2}','${one.tipTime2}','${one.creators}','${one.creatorIds}','${one.creatorIds}','${one.isLeaderView}','${one.inviteeNames}','${one.inviteeIds}','${one.acceptIds }','${one.acceptNames }','${one.refuseIds}','${one.refuseNames}','${one.tag.uuid}');">
			<font color="${one.color}">
			<c:if test="${one.isComplete=='1'}">
			<strike><c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${one.isComplete!='1'}"> 
			<c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}one" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week1.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td class="tr_content_td" onmouseover="showorno('${row2.userNo}','two','1');" onmouseout="showorno('${row2.userNo}','two','2');"> 
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.twoList}" var="two">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${two.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${two.status}','1','','','${two.tipMethod}','${two.isComplete}','${week2.sdate}','${two.uuid}','${two.scheduleName}','${two.address}','${two.dstartDate}','${two.startTime}','${two.dendDate}','${two.endTime}','${two.isView}','${two.status}','${two.leaderNames}','${two.leaderIds}','${two.pleases}','${two.pleaseIds}','${two.views}','${two.viewIds}','${two.color}','${two.tip}','${two.tipDate}','${two.tipTime}','${two.repeat}','${two.startTime2}','${two.endTime2}','${two.tipTime2}','${two.creators}','${two.creatorIds}','${two.isLeaderView}','${two.inviteeNames}','${two.inviteeIds}','${two.acceptIds }','${two.acceptNames }','${two.refuseIds}','${two.refuseNames}','${two.tag.uuid}');">
			<font color="${two.color}">
			<c:if test="${two.isComplete=='1'}">
			<strike><c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /></c:if><c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${two.isComplete!='1'}">
			<c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${two.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${two.status}','1','','','${two.tipMethod}','${two.isComplete}','${week2.sdate}','${two.uuid}','${two.scheduleName}','${two.address}','${two.dstartDate}','${two.startTime}','${two.dendDate}','${two.endTime}','${two.isView}','${two.status}','${two.leaderNames}','${two.leaderIds}','${two.pleases}','${two.pleaseIds}','${two.views}','${two.viewIds}','${two.color}','${two.tip}','${two.tipDate}','${two.tipTime}','${two.repeat}','${two.startTime2}','${two.endTime2}','${two.tipTime2}','${two.creators}','${two.creatorIds}','${two.isLeaderView}','${two.inviteeNames}','${two.inviteeIds}','${two.acceptIds }','${two.acceptNames }','${two.refuseIds}','${two.refuseNames}','${two.tag.uuid}');">
			<font color="${two.color}">
			<c:if test="${two.isComplete=='1'}">
			<strike><c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${two.isComplete!='1'}"> 
			<c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /></c:if><c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			</c:forEach>
			<div id="${row2.userNo}two" style="display: none;"><font size="1"><a class="addschedule_css"  href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week2.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td  class="tr_content_td" onmouseover="showorno('${row2.userNo}','three','1');" onmouseout="showorno('${row2.userNo}','three','2');"> 
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.threeList}" var="three">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${three.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${three.status}','1','','','${three.tipMethod}','${three.isComplete}','${week3.sdate}','${three.uuid}','${three.scheduleName}','${three.address}','${three.dstartDate}','${three.startTime}','${three.dendDate}','${three.endTime}','${three.isView}','${three.status}','${three.leaderNames}','${three.leaderIds}','${three.pleases}','${three.pleaseIds}','${three.views}','${three.viewIds}','${three.color}','${three.tip}','${three.tipDate}','${three.tipTime}','${three.repeat}','${three.startTime2}','${three.endTime2}','${three.tipTime2}','${three.creators}','${three.creatorIds}','${three.isLeaderView}','${three.inviteeNames}','${three.inviteeIds}','${three.acceptIds }','${three.acceptNames }','${three.refuseIds}','${three.refuseNames}','${three.tag.uuid}');">
			<font color="${three.color}">
			<c:if test="${three.isComplete=='1'}">
			<strike><c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${three.isComplete!='1'}">
			<c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${three.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${three.status}','1','','','${three.tipMethod}','${three.isComplete}','${week3.sdate}','${three.uuid}','${three.scheduleName}','${three.address}','${three.dstartDate}','${three.startTime}','${three.dendDate}','${three.endTime}','${three.isView}','${three.status}','${three.leaderNames}','${three.leaderIds}','${three.pleases}','${three.pleaseIds}','${three.views}','${three.viewIds}','${three.color}','${three.tip}','${three.tipDate}','${three.tipTime}','${three.repeat}','${three.startTime2}','${three.endTime2}','${three.tipTime2}','${three.creators}','${three.creatorIds}','${three.isLeaderView}','${three.inviteeNames}','${three.inviteeIds}','${three.acceptIds }','${three.acceptNames }','${three.refuseIds}','${three.refuseNames}','${three.tag.uuid}');">
			<font color="${three.color}">
			<c:if test="${three.isComplete=='1'}">
			<strike><c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${three.isComplete!='1'}"> 
			<c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			</c:forEach>
			<div id="${row2.userNo}three" style="display: none;"><font size="1"><a class="addschedule_css"  href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week3.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td class="tr_content_td" onmouseover="showorno('${row2.userNo}','four','1');" onmouseout="showorno('${row2.userNo}','four','2');"> 
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.fourList}" var="four">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${four.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${four.status}','1','','','${four.tipMethod}','${four.isComplete}','${week4.sdate}','${four.uuid}','${four.scheduleName}','${four.address}','${four.dstartDate}','${four.startTime}','${four.dendDate}','${four.endTime}','${four.isView}','${four.status}','${four.leaderNames}','${four.leaderIds}','${four.pleases}','${four.pleaseIds}','${four.views}','${four.viewIds}','${four.color}','${four.tip}','${four.tipDate}','${four.tipTime}','${four.repeat}','${four.startTime2}','${four.endTime2}','${four.tipTime2}','${four.creators}','${four.creatorIds}','${four.isLeaderView}','${four.inviteeNames}','${four.inviteeIds}','${four.acceptIds }','${four.acceptNames }','${four.refuseIds}','${four.refuseNames}','${four.tag.uuid}');">
			<font color="${four.color}">
			<c:if test="${four.isComplete=='1'}">
			<strike><c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${four.isComplete!='1'}">
			<c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${four.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${four.status}','1','','','${four.tipMethod}','${four.isComplete}','${week4.sdate}','${four.uuid}','${four.scheduleName}','${four.address}','${four.dstartDate}','${four.startTime}','${four.dendDate}','${four.endTime}','${four.isView}','${four.status}','${four.leaderNames}','${four.leaderIds}','${four.pleases}','${four.pleaseIds}','${four.views}','${four.viewIds}','${four.color}','${four.tip}','${four.tipDate}','${four.tipTime}','${four.repeat}','${four.startTime2}','${four.endTime2}','${four.tipTime2}','${four.creators}','${four.creatorIds}','${four.isLeaderView}','${four.inviteeNames}','${four.inviteeIds}','${four.acceptIds }','${four.acceptNames }','${four.refuseIds}','${four.refuseNames}','${four.tag.uuid}');">
			<font color="${four.color}">
			<c:if test="${four.isComplete=='1'}">
			<strike><c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${four.isComplete!='1'}"> 
			<c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			</c:forEach>
			<div id="${row2.userNo}four" style="display: none;"><font size="1"><a class="addschedule_css"  href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week4.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td class="tr_content_td" onmouseover="showorno('${row2.userNo}','five','1');" onmouseout="showorno('${row2.userNo}','five','2');"> 
			<div style="width:140px;overflow:hidden;display: inline;border: 1px;font-size: 12px;" >
			<c:forEach items="${row2.fiveList}" var="five">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${five.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${five.status}','1','','','${five.tipMethod}','${five.isComplete}','${week5.sdate}','${five.uuid}','${five.scheduleName}','${five.address}','${five.dstartDate}','${five.startTime}','${five.dendDate}','${five.endTime}','${five.isView}','${five.status}','${five.leaderNames}','${five.leaderIds}','${five.pleases}','${five.pleaseIds}','${five.views}','${five.viewIds}','${five.color}','${five.tip}','${five.tipDate}','${five.tipTime}','${five.repeat}','${five.startTime2}','${five.endTime2}','${five.tipTime2}','${five.creators}','${five.creatorIds}','${five.isLeaderView}','${five.inviteeNames}','${five.inviteeIds}','${five.acceptIds }','${five.acceptNames }','${five.refuseIds}','${five.refuseNames}','${five.tag.uuid}');">
			<font color="${five.color}">
			<c:if test="${five.isComplete=='1'}">
			<strike><c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${five.isComplete!='1'}">
			<c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${five.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${five.status}','1','','','${five.tipMethod}','${five.isComplete}','${week5.sdate}','${five.uuid}','${five.scheduleName}','${five.address}','${five.dstartDate}','${five.startTime}','${five.dendDate}','${five.endTime}','${five.isView}','${five.status}','${five.leaderNames}','${five.leaderIds}','${five.pleases}','${five.pleaseIds}','${five.views}','${five.viewIds}','${five.color}','${five.tip}','${five.tipDate}','${five.tipTime}','${five.repeat}','${five.startTime2}','${five.endTime2}','${five.tipTime2}','${five.creators}','${five.creatorIds}','${five.isLeaderView}','${five.inviteeNames}','${five.inviteeIds}','${five.acceptIds }','${five.acceptNames }','${five.refuseIds}','${five.refuseNames}','${five.tag.uuid}');">
			<font color="${five.color}">
			<c:if test="${five.isComplete=='1'}">
			<strike><c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${five.isComplete!='1'}"> 
			<c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			
			</c:forEach>
			<div id="${row2.userNo}five" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week5.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td class="tr_content_td" onmouseover="showorno('${row2.userNo}','six','1');" onmouseout="showorno('${row2.userNo}','six','2');" > 
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.sixList}" var="six">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${six.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${six.status}','1','','','${six.tipMethod}','${six.isComplete}','${week6.sdate}','${six.uuid}','${six.scheduleName}','${six.address}','${six.dstartDate}','${six.startTime}','${six.dendDate}','${six.endTime}','${six.isView}','${six.status}','${six.leaderNames}','${six.leaderIds}','${six.pleases}','${six.pleaseIds}','${six.views}','${six.viewIds}','${six.color}','${six.tip}','${six.tipDate}','${six.tipTime}','${six.repeat}','${six.startTime2}','${six.endTime2}','${six.tipTime2}','${six.creators}','${six.creatorIds}','${six.isLeaderView}','${six.inviteeNames}','${six.inviteeIds}','${six.acceptIds }','${six.acceptNames }','${six.refuseIds}','${six.refuseNames}','${six.tag.uuid}');">
			<font color="${six.color}">
			<c:if test="${six.isComplete=='1'}">
			<strike><c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${six.isComplete!='1'}">
			<c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${six.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${six.status}','1','','','${six.tipMethod}','${six.isComplete}','${week6.sdate}','${six.uuid}','${six.scheduleName}','${six.address}','${six.dstartDate}','${six.startTime}','${six.dendDate}','${six.endTime}','${six.isView}','${six.status}','${six.leaderNames}','${six.leaderIds}','${six.pleases}','${six.pleaseIds}','${six.views}','${six.viewIds}','${six.color}','${six.tip}','${six.tipDate}','${six.tipTime}','${six.repeat}','${six.startTime2}','${six.endTime2}','${six.tipTime2}','${six.creators}','${six.creatorIds}','${six.isLeaderView}','${six.inviteeNames}','${six.inviteeIds}','${six.acceptIds }','${six.acceptNames }','${six.refuseIds}','${six.refuseNames}','${six.tag.uuid}');">
			<font color="${six.color}">
			<c:if test="${six.isComplete=='1'}">
			<strike><c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${six.isComplete!='1'}"> 
			<c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			</c:forEach>
			<div id="${row2.userNo}six" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week6.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<td  class="tr_content_td" onmouseover="showorno('${row2.userNo}','seven','1');" onmouseout="showorno('${row2.userNo}','seven','2');"> 
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.sevenList}" var="seven">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${seven.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${seven.status}','1','','','${seven.tipMethod}','${seven.isComplete}','${week7.sdate}','${seven.uuid}','${seven.scheduleName}','${seven.address}','${seven.dstartDate}','${seven.startTime}','${seven.dendDate}','${seven.endTime}','${seven.isView}','${seven.status}','${seven.leaderNames}','${seven.leaderIds}','${seven.pleases}','${seven.pleaseIds}','${seven.views}','${seven.viewIds}','${seven.color}','${seven.tip}','${seven.tipDate}','${seven.tipTime}','${seven.repeat}','${seven.startTime2}','${seven.endTime2}','${seven.tipTime2}','${seven.creators}','${seven.creatorIds}','${seven.isLeaderView}','${seven.inviteeNames}','${seven.inviteeIds}','${seven.acceptIds }','${seven.acceptNames }','${seven.refuseIds}','${seven.refuseNames}','${seven.tag.uuid}');">
			<font color="${seven.color}">
			<c:if test="${seven.isComplete=='1'}">
			<strike><c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${seven.isComplete!='1'}">
			<c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			
			</font></a></font><br/>
			</c:if>
			<c:if test="${seven.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('${leaderNames}','${leaderIds}','${mtype}','${ldate}','${userno}','${seven.status}','1','','','${seven.tipMethod}','${seven.isComplete}','${week7.sdate}','${seven.uuid}','${seven.scheduleName}','${seven.address}','${seven.dstartDate}','${seven.startTime}','${seven.dendDate}','${seven.endTime}','${seven.isView}','${seven.status}','${seven.leaderNames}','${seven.leaderIds}','${seven.pleases}','${seven.pleaseIds}','${seven.views}','${seven.viewIds}','${seven.color}','${seven.tip}','${seven.tipDate}','${seven.tipTime}','${seven.repeat}','${seven.startTime2}','${seven.endTime2}','${seven.tipTime2}','${seven.creators}','${seven.creatorIds}','${seven.isLeaderView}','${seven.inviteeNames}','${seven.inviteeIds}','${seven.acceptIds }','${seven.acceptNames }','${seven.refuseIds}','${seven.refuseNames}','${seven.tag.uuid}');">
			<font color="${seven.color}">
			<c:if test="${seven.isComplete=='1'}">
			<strike><c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${seven.isComplete!='1'}"> 
			<c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			 </font></a><br/>
			 </c:if>
			</c:forEach>
<!--			<div id="${row2.userNo}seven" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openww('${creators}','${creatorIds}','${week7.sdate}','${ldate}','${mtype}','${now}');"><spring:message code="schedule.info.newSchedule" /></a></font></div>-->
			<div id="${row2.userNo}seven" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('${row2.userName}','${row2.userNo}','${leaderNames}','${leaderIds}','1','1','','','${creators}','${creatorIds}','${week7.sdate}','${ldate}','${mtype}','${now}','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>	
			</tr>
		
		</c:forEach>
	</table>
	</div>
	<div class="view_foot"></div>
	<script type="text/javascript">
	
</script>
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
<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
<script  type="text/javascript">

	
//上一月
$(".s_prev_day").click(function(){
	var ldate = $(this).attr("ldate");
	var mtype = $(this).attr("mtype");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":1,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list").parent().html(result);
				$(".schedule_leader_list2").parent().children().not(".schedule_leader_list2").remove();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&stype=1&mtype='+mtype;
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
			data : {"stype":2,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list").parent().html(result);
				$(".schedule_leader_list2").parent().children().not(".schedule_leader_list2").remove();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&stype=2&mtype='+mtype;
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
			data : {"ctype":1,"mtype":mtype,"ldate":ldate,"requestType":"cms"},
			url:"${ctx}/schedule/leader_schedule.action",
			success:function(result){
				$(".schedule_leader_list").parent().html(result);
				$(".schedule_leader_list2").parent().children().not(".schedule_leader_list2").remove();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/leader_schedule.action?ldate='+ldate+'&ctype=1&mtype='+mtype;
	}
});


</script>
</div>
</body>
</html>