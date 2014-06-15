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
</c:if >
</head>
<body style="width:100%; height:100%;padding:0px; margin:0px;">
	<div id="container" class="schedule_group_list schedule_css">
	<c:if test="${addCss=='yes'}">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
	</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
<%-- 	<input type="hidden" id="contextPath" value="${ctx}"></input> --%>
	<div id="toolbar">
		<table width="100%"><tr><td align="left">
		<a class="s_prev_day" ldate="${firstday}" groupid="${groupid}" href="#" ></a>
		<span class="s_taday" type="text" size="7" name="firstday">${firstday1}</span>
		<a class="s_next_day" ldate="${firstday}" groupid="${groupid}" href="#" ></a>
		<span class="fromandto">(${from})<spring:message code="schedule.info.zhi" />(${to})</span>
		</td>
		<td align="left">
		
		</td>
		<td align="right">
		 <a class="as_day" href="${ctx}/schedule/export_group.action?ldate=${firstday}&ctype=0&groupid=${groupid}"><spring:message code="schedule.btn.export" /></a>
		 <a class="asleader" ldate="${firstday}" groupid="${groupid}" href="#"><spring:message code="schedule.btn.shixiang" /></a>
		</td></tr></table>
	</div>
	<div class="content">
	<div class="content_group_list">
	<c:forEach items="${groupList}" var="rgoup">
		
		<c:if test="${groupid==rgoup.uuid}">
		<div class="content_group_item activity">
		<a class="group_name" ldate="${ldate}" groupid="${rgoup.uuid}" href="#">${rgoup.groupName}</a>
		<a class="editgroup" href="#" onclick="openLayerGroupSet2('${rgoup.uuid}','${rgoup.groupName}','${rgoup.userNames}','${rgoup.userIds}','${firstday}','${groupid}',1);"></a>
		<a class="delgroup" href="#" onclick="deleteGroup2('${rgoup.uuid}','${firstday}','${groupid}',1);"></a>
		</div>
		</c:if>
		<c:if test="${groupid!=rgoup.uuid}">
		<div class="content_group_item">
		<a class="group_name" ldate="${ldate}" groupid="${rgoup.uuid}" href="#">${rgoup.groupName}</a>
		<a class="editgroup" href="#" onclick="openLayerGroupSet2('${rgoup.uuid}','${rgoup.groupName}','${rgoup.userNames}','${rgoup.userIds}','${firstday}','${groupid}',1);"></a>
		<a class="delgroup" href="#" onclick="deleteGroup2('${rgoup.uuid}','${firstday}','${groupid}',1);"></a>
		</div>
		</c:if>
	</c:forEach>
	<a class="addgroup"  onclick="openLayerGroupSet('${firstday}','${groupid}',1);"  href="#"><spring:message code="schedule.btn.add" />群组</a>
	</div>
	<div class="schedule_leader_list_content_div">
	<table class="schedule_leader_list_content" width="100%"  height="100%" border="2" >
		
			<tr class="tr_title">
				<td  width="5%" align="center">成员姓名</td>
				<c:forEach items="${weeks}" var="row">
				<c:if test="${row.sdate==now}">
				<td align="center" width="14%" class="tr_title_taday"><c:out value="${row.today}"/></td>
				</c:if>
				<c:if test="${row.sdate!=now}">
				<td align="center"  width="14%"><c:out value="${row.today}"/></td>
				</c:if>
				
				</c:forEach>
			</tr>
		
		<c:forEach items="${leaders}" var="row2">
		
			<tr  class="tr_content" style="">
			<td class="tr_content_l1 tr_content_td">
			${row2.userName}
			</td>
<!--			<td onmouseover="showorno('${row2.userNo}','one','1');" onmouseout="showorno('${row2.userNo}','one','2');"> -->
			<td class="tr_content_td" >
			<div  style="width:100px;overflow:hidden;display: inline;border: 1pxfont-size: 12px;"  >
			<c:forEach items="${row2.oneList}" var="one">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${one.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${one.tipMethod}','${one.isComplete}','${week1.sdate}','${one.uuid}','${one.scheduleName}','${one.address}','${one.dstartDate}','${one.startTime}','${one.dendDate}','${one.endTime}','${one.isView}','${one.status}','${one.leaderNames}','${one.leaderIds}','${one.pleases}','${one.pleaseIds}','${one.views}','${one.viewIds}','${one.color}','${one.tip}','${one.tipDate}','${one.tipTime}','${one.repeat}','${one.startTime2}','${one.endTime2}','${one.tipTime2}','${one.creators}','${one.creatorIds}');">
			<font color="${one.color}">
			<c:if test="${one.isComplete=='1'}">
			<strike><c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
					<c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${one.isComplete!='1'}">
			<c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			</font></a></font><br/>
			</c:if>
			<c:if test="${one.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${one.tipMethod}','${one.isComplete}','${week1.sdate}','${one.uuid}','${one.scheduleName}','${one.address}','${one.dstartDate}','${one.startTime}','${one.dendDate}','${one.endTime}','${one.isView}','${one.status}','${one.leaderNames}','${one.leaderIds}','${one.pleases}','${one.pleaseIds}','${one.views}','${one.viewIds}','${one.color}','${one.tip}','${one.tipDate}','${one.tipTime}','${one.repeat}','${one.startTime2}','${one.endTime2}','${one.tipTime2}','${one.creators}','${one.creatorIds}');">
			<font color="${one.color}">
			<c:if test="${one.isComplete=='1'}">
			<strike><c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${one.isComplete!='1'}"> 
			<c:if test="${one.startTime=='' or one.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${one.startTime!='' and one.startTime!=null}"><c:out value="${one.startTime}"/>:<c:out value="${one.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(one.address)>3}"><c:out value="${fn:substring(one.address,0,3)}.."/></c:when><c:otherwise><c:out value="${one.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(one.scheduleName)>3}"><c:out value="${fn:substring(one.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${one.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}one" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week1.sdate}','${row2.userNo}one','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>
<!--			<td onmouseover="showorno('${row2.userNo}','two','1');" onmouseout="showorno('${row2.userNo}','two','2');"> -->
			<td class="tr_content_td">
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.twoList}" var="two">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${two.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${two.tipMethod}','${two.isComplete}','${week2.sdate}','${two.uuid}','${two.scheduleName}','${two.address}','${two.dstartDate}','${two.startTime}','${two.dendDate}','${two.endTime}','${two.isView}','${two.status}','${two.leaderNames}','${two.leaderIds}','${two.pleases}','${two.pleaseIds}','${two.views}','${two.viewIds}','${two.color}','${two.tip}','${two.tipDate}','${two.tipTime}','${two.repeat}','${two.startTime2}','${two.endTime2}','${two.tipTime2}','${two.creators}','${two.creatorIds}');">
			<font color="${two.color}">
			<c:if test="${two.isComplete=='1'}">
			<strike><c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${two.isComplete!='1'}">
			<c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a></font><br/>
			</c:if>
			<c:if test="${two.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${two.tipMethod}','${two.isComplete}','${week2.sdate}','${two.uuid}','${two.scheduleName}','${two.address}','${two.dstartDate}','${two.startTime}','${two.dendDate}','${two.endTime}','${two.isView}','${two.status}','${two.leaderNames}','${two.leaderIds}','${two.pleases}','${two.pleaseIds}','${two.views}','${two.viewIds}','${two.color}','${two.tip}','${two.tipDate}','${two.tipTime}','${two.repeat}','${two.startTime2}','${two.endTime2}','${two.tipTime2}','${two.creators}','${two.creatorIds}');">
			<font color="${two.color}">
			<c:if test="${two.isComplete=='1'}">
			<strike><c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${two.isComplete!='1'}"> 
			<c:if test="${two.startTime=='' or two.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${two.startTime!='' and two.startTime!=null}"><c:out value="${two.startTime}"/>:<c:out value="${two.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(two.address)>3}"><c:out value="${fn:substring(two.address,0,3)}.."/></c:when><c:otherwise><c:out value="${two.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(two.scheduleName)>3}"><c:out value="${fn:substring(two.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${two.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}two" style="display: none;">
<!--<font size="1"><a href="#" onclick="openLayer('${week2.sdate}','${row2.userNo}two','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
</div>
			</div>
			</td>
<!--			<td  onmouseover="showorno('${row2.userNo}','three','1');" onmouseout="showorno('${row2.userNo}','three','2');"> -->
			<td class="tr_content_td">
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.threeList}" var="three">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${three.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${three.tipMethod}','${three.isComplete}','${week3.sdate}','${three.uuid}','${three.scheduleName}','${three.address}','${three.dstartDate}','${three.startTime}','${three.dendDate}','${three.endTime}','${three.isView}','${three.status}','${three.leaderNames}','${three.leaderIds}','${three.pleases}','${three.pleaseIds}','${three.views}','${three.viewIds}','${three.color}','${three.tip}','${three.tipDate}','${three.tipTime}','${three.repeat}','${three.startTime2}','${three.endTime2}','${three.tipTime2}','${three.creators}','${three.creatorIds}');">
			<font color="${three.color}">
			<c:if test="${three.isComplete=='1'}">
			<strike><c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${three.isComplete!='1'}">
			<c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a></font><br/>
			</c:if>
			<c:if test="${three.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${three.tipMethod}','${three.isComplete}','${week3.sdate}','${three.uuid}','${three.scheduleName}','${three.address}','${three.dstartDate}','${three.startTime}','${three.dendDate}','${three.endTime}','${three.isView}','${three.status}','${three.leaderNames}','${three.leaderIds}','${three.pleases}','${three.pleaseIds}','${three.views}','${three.viewIds}','${three.color}','${three.tip}','${three.tipDate}','${three.tipTime}','${three.repeat}','${three.startTime2}','${three.endTime2}','${three.tipTime2}','${three.creators}','${three.creatorIds}');">
			<font color="${three.color}">
			<c:if test="${three.isComplete=='1'}">
			<strike><c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${three.isComplete!='1'}"> 
			<c:if test="${three.startTime=='' or three.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${three.startTime!='' and three.startTime!=null}"><c:out value="${three.startTime}"/>:<c:out value="${three.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(three.address)>3}"><c:out value="${fn:substring(three.address,0,3)}.."/></c:when><c:otherwise><c:out value="${three.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(three.scheduleName)>3}"><c:out value="${fn:substring(three.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${three.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}three" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week3.sdate}','${row2.userNo}three','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>
<!--			<td onmouseover="showorno('${row2.userNo}','four','1');" onmouseout="showorno('${row2.userNo}','four','2');"> -->
			<td class="tr_content_td">
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.fourList}" var="four">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${four.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${four.tipMethod}','${four.isComplete}','${week4.sdate}','${four.uuid}','${four.scheduleName}','${four.address}','${four.dstartDate}','${four.startTime}','${four.dendDate}','${four.endTime}','${four.isView}','${four.status}','${four.leaderNames}','${four.leaderIds}','${four.pleases}','${four.pleaseIds}','${four.views}','${four.viewIds}','${four.color}','${four.tip}','${four.tipDate}','${four.tipTime}','${four.repeat}','${four.startTime2}','${four.endTime2}','${four.tipTime2}','${four.creators}','${four.creatorIds}');">
			<font color="${four.color}">
			<c:if test="${four.isComplete=='1'}">
			<strike><c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${four.isComplete!='1'}">
			<c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			</font></a></font><br/>
			</c:if>
			<c:if test="${four.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${four.tipMethod}','${four.isComplete}','${week4.sdate}','${four.uuid}','${four.scheduleName}','${four.address}','${four.dstartDate}','${four.startTime}','${four.dendDate}','${four.endTime}','${four.isView}','${four.status}','${four.leaderNames}','${four.leaderIds}','${four.pleases}','${four.pleaseIds}','${four.views}','${four.viewIds}','${four.color}','${four.tip}','${four.tipDate}','${four.tipTime}','${four.repeat}','${four.startTime2}','${four.endTime2}','${four.tipTime2}','${four.creators}','${four.creatorIds}');">
			<font color="${four.color}">
			<c:if test="${four.isComplete=='1'}">
			<strike><c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${four.isComplete!='1'}"> 
			<c:if test="${four.startTime=='' or four.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${four.startTime!='' and four.startTime!=null}"><c:out value="${four.startTime}"/>:<c:out value="${four.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(four.address)>3}"><c:out value="${fn:substring(four.address,0,3)}.."/></c:when><c:otherwise><c:out value="${four.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(four.scheduleName)>3}"><c:out value="${fn:substring(four.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${four.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}four" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week4.sdate}','${row2.userNo}four','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>
<!--			<td onmouseover="showorno('${row2.userNo}','five','1');" onmouseout="showorno('${row2.userNo}','five','2');"> -->
			<td class="tr_content_td">
			<div style="width:140px;overflow:hidden;display: inline;border: 1px;font-size: 12px;" >
			<c:forEach items="${row2.fiveList}" var="five">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${five.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${five.tipMethod}','${five.isComplete}','${week5.sdate}','${five.uuid}','${five.scheduleName}','${five.address}','${five.dstartDate}','${five.startTime}','${five.dendDate}','${five.endTime}','${five.isView}','${five.status}','${five.leaderNames}','${five.leaderIds}','${five.pleases}','${five.pleaseIds}','${five.views}','${five.viewIds}','${five.color}','${five.tip}','${five.tipDate}','${five.tipTime}','${five.repeat}','${five.startTime2}','${five.endTime2}','${five.tipTime2}','${five.creators}','${five.creatorIds}');">
			<font color="${five.color}">
			<c:if test="${five.isComplete=='1'}">
			<strike><c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${five.isComplete!='1'}">
			<c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			</font></a></font><br/>
			</c:if>
			<c:if test="${five.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${five.tipMethod}','${five.isComplete}','${week5.sdate}','${five.uuid}','${five.scheduleName}','${five.address}','${five.dstartDate}','${five.startTime}','${five.dendDate}','${five.endTime}','${five.isView}','${five.status}','${five.leaderNames}','${five.leaderIds}','${five.pleases}','${five.pleaseIds}','${five.views}','${five.viewIds}','${five.color}','${five.tip}','${five.tipDate}','${five.tipTime}','${five.repeat}','${five.startTime2}','${five.endTime2}','${five.tipTime2}','${five.creators}','${five.creatorIds}');">
			<font color="${five.color}">
			<c:if test="${five.isComplete=='1'}">
			<strike><c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${five.isComplete!='1'}"> 
			<c:if test="${five.startTime=='' or five.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${five.startTime!='' and five.startTime!=null}"><c:out value="${five.startTime}"/>:<c:out value="${five.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(five.address)>3}"><c:out value="${fn:substring(five.address,0,3)}.."/></c:when><c:otherwise><c:out value="${five.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(five.scheduleName)>3}"><c:out value="${fn:substring(five.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${five.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			
			</c:forEach>
			<div id="${row2.userNo}five" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week5.sdate}','${row2.userNo}five','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>
<!--			<td onmouseover="showorno('${row2.userNo}','six','1');" onmouseout="showorno('${row2.userNo}','six','2');" > -->
			<td class="tr_content_td">
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.sixList}" var="six">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${six.displayStartDate==now}">
			<font color="blue"><a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${six.tipMethod}','${six.isComplete}','${week1.sdate}','${six.uuid}','${six.scheduleName}','${six.address}','${six.dstartDate}','${six.startTime}','${six.dendDate}','${six.endTime}','${six.isView}','${six.status}','${six.leaderNames}','${six.leaderIds}','${six.pleases}','${six.pleaseIds}','${six.views}','${six.viewIds}','${six.color}','${six.tip}','${six.tipDate}','${six.tipTime}','${six.repeat}','${six.startTime2}','${six.endTime2}','${six.tipTime2}','${six.creators}','${six.creatorIds}');">
			<font color="${six.color}">
			<c:if test="${six.isComplete=='1'}">
			<strike><c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${six.isComplete!='1'}">
			<c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			</font></a></font><br/>
			</c:if>
			<c:if test="${six.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${six.tipMethod}','${six.isComplete}','${week1.sdate}','${six.uuid}','${six.scheduleName}','${six.address}','${six.dstartDate}','${six.startTime}','${six.dendDate}','${six.endTime}','${six.isView}','${six.status}','${six.leaderNames}','${six.leaderIds}','${six.pleases}','${six.pleaseIds}','${six.views}','${six.viewIds}','${six.color}','${six.tip}','${six.tipDate}','${six.tipTime}','${six.repeat}','${six.startTime2}','${six.endTime2}','${six.tipTime2}','${six.creators}','${six.creatorIds}');">
			<font color="${six.color}">
			<c:if test="${six.isComplete=='1'}">
			<strike><c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${six.isComplete!='1'}"> 
			<c:if test="${six.startTime=='' or six.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${six.startTime!='' and six.startTime!=null}"><c:out value="${six.startTime}"/>:<c:out value="${six.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(six.address)>3}"><c:out value="${fn:substring(six.address,0,3)}.."/></c:when><c:otherwise><c:out value="${six.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(six.scheduleName)>3}"><c:out value="${fn:substring(six.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${six.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}six" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week6.sdate}','${row2.userNo}six','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>
<!--			<td  onmouseover="showorno('${row2.userNo}','seven','1');" onmouseout="showorno('${row2.userNo}','seven','2');"> -->
			<td class="tr_content_td">
			<div style="width:100px;overflow:hidden;display: inline;border: 1px;font-size: 12px;">
			<c:forEach items="${row2.sevenList}" var="seven">
			<c:if test="${addCss=='yes'}"><img src="${ctx}/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx}/resources/schedule/img/star-on.png"  /></c:if>
			<c:if test="${seven.displayStartDate==now}">
			<font color="blue">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${seven.tipMethod}','${seven.isComplete}','${week1.sdate}','${seven.uuid}','${seven.scheduleName}','${seven.address}','${seven.dstartDate}','${seven.startTime}','${seven.dendDate}','${seven.endTime}','${seven.isView}','${seven.status}','${seven.leaderNames}','${seven.leaderIds}','${seven.pleases}','${seven.pleaseIds}','${seven.views}','${seven.viewIds}','${seven.color}','${seven.tip}','${seven.tipDate}','${seven.tipTime}','${seven.repeat}','${seven.startTime2}','${seven.endTime2}','${seven.tipTime2}','${seven.creators}','${seven.creatorIds}');">
			<font color="${seven.color}">
			<c:if test="${seven.isComplete=='1'}">
			<strike><c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${seven.isComplete!='1'}">
			<c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose> <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</c:if>	
			</font></a></font><br/>
			</c:if>
			<c:if test="${seven.displayStartDate!=now}">
			<a href="#" onclick="openScheduleDialog('','','${mtype}','${ldate}','${userno}','2','5','${ctype}','${groupid}','${seven.tipMethod}','${seven.isComplete}','${week1.sdate}','${seven.uuid}','${seven.scheduleName}','${seven.address}','${seven.dstartDate}','${seven.startTime}','${seven.dendDate}','${seven.endTime}','${seven.isView}','${seven.status}','${seven.leaderNames}','${seven.leaderIds}','${seven.pleases}','${seven.pleaseIds}','${seven.views}','${seven.viewIds}','${seven.color}','${seven.tip}','${seven.tipDate}','${seven.tipTime}','${seven.repeat}','${seven.startTime2}','${seven.endTime2}','${seven.tipTime2}','${seven.creators}','${seven.creatorIds}');">
			<font color="${seven.color}">
			<c:if test="${seven.isComplete=='1'}">
			<strike><c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</strike>
			</c:if>
			<c:if test="${seven.isComplete!='1'}"> 
			<c:if test="${seven.startTime=='' or seven.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
			<c:if test="${seven.startTime!='' and seven.startTime!=null}"><c:out value="${seven.startTime}"/>:<c:out value="${seven.startTime2}"/> </c:if>
			<c:choose><c:when test="${fn:length(seven.address)>3}"><c:out value="${fn:substring(seven.address,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.address}"/></c:otherwise></c:choose>  <c:choose><c:when test="${fn:length(seven.scheduleName)>3}"><c:out value="${fn:substring(seven.scheduleName,0,3)}.."/></c:when><c:otherwise><c:out value="${seven.scheduleName}"/></c:otherwise></c:choose>
			</c:if>
			</font></a><br/>
			</c:if>
			</c:forEach>
			<div id="${row2.userNo}seven" style="display: none;">
<!--			<font size="1"><a href="#" onclick="openLayer('${week7.sdate}','${row2.userNo}seven','test_con3');"><spring:message code="schedule.info.newSchedule" /></a></font>-->
			</div>
			</div>
			</td>	
			</tr>
		
		</c:forEach>
	</table>
	</div>
<div class="view_foot"></div>
</div>
</body>
<c:if test="${addCss!='yes'}">
	<script src="${ctx}/resources/jquery/jquery.js"></script>
	<!-- jQuery UI -->
	<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
</c:if>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
<script  type="text/javascript">

$(function(){
		if($(".tr_content").length==0&&$(".group_name").length>0){
			var ldate = $(this).find(".group_name").eq(0).attr("ldate");
			var groupid = $(this).find(".group_name").eq(0).attr("groupid");
			$.ajax({
				type:"post",
				async:false,
				data : {"ctype":0,"groupid":groupid,"ldate":ldate,"requestType":"cms"},
				url:"${ctx}/schedule/group_schedule.action",
				success:function(result){
					$(".schedule_group_list").parent().html(result);
				}
			});
		}
});
//上一月
$(".s_prev_day").click(function(){
	var ldate = $(this).attr("ldate");
	var groupid = $(this).attr("groupid");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":1,"ldate":ldate,"groupid":groupid,"requestType":"cms","ctype":0},
			url:"${ctx}/schedule/group_schedule.action",
			success:function(result){
				$(".schedule_group_list").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/group_schedule.action?ldate='+ldate+'&ctype=0&stype=1&groupid='+groupid;
	}
});
//下一月
$(".s_next_day").click(function(){
	var ldate = $(this).attr("ldate");
	var groupid = $(this).attr("groupid");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":2,"ldate":ldate,"groupid":groupid,"requestType":"cms","ctype":0},
			url:"${ctx}/schedule/group_schedule.action",
			success:function(result){
				$(".schedule_group_list").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/group_schedule.action?ldate='+ldate+'&stype=2&ctype=0&groupid='+groupid;
	}
});
//按事项
$(".asleader").click(function(){
	var ldate = $(this).attr("ldate");
	var groupid = $(this).attr("groupid");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"ctype":1,"groupid":groupid,"ldate":ldate,"requestType":"cms"},
			url:"${ctx}/schedule/group_schedule.action",
			success:function(result){
				$(".schedule_group_list").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/group_schedule.action?ldate='+ldate+'&ctype=1&groupid='+groupid;
	}
});
//群组设置
$(".as_day2").click(function(){
	var ldate = $(this).attr("ldate");
	var groupid = $(this).attr("groupid");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"ctype":1,"groupid":groupid,"ldate":ldate,"requestType":"cms"},
			url:"${ctx}/schedule/schedule_setlist.action",
			success:function(result){
				$(".schedule_group_list").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/schedule_setlist.action?ldate='+ldate+'&ctype=1&groupid='+groupid;
	}
});
//群组
$(".group_name").click(function(){
	var ldate = $(this).attr("ldate");
	var groupid = $(this).attr("groupid");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"ctype":0,"groupid":groupid,"ldate":ldate,"requestType":"cms"},
			url:"${ctx}/schedule/group_schedule.action",
			success:function(result){
				$(".schedule_group_list").parent().html(result);
			}
		});
	}else{
		window.location.href='${ctx}/schedule/group_schedule.action?ldate='+ldate+'&ctype=0&groupid='+groupid;
	}
});
$(".content_group_item").live("mouseover",function(){
	$(this).find("a").not(".group_name").css("display","block");
});
$(".content_group_item").live("mouseout",function(){
	$(this).find("a").not(".group_name").css("display","none");
});

function addHref(str) {
	var body = $("body").html();
	var index = body.indexOf(str);
	while(index != -1) {
		var s = "<a href='#'>"+str+"</a>";
		body = body.replaceAll(str, s);
		$("body").html(body);
	}
}

	</script>
</html>