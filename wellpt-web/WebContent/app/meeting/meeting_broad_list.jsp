<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE  html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<%@ include file="/pt/common/meta.jsp"%>
</head>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;" >
<div class="viewContent">
	<div class="dialogcontent">

<link href="${ctx}/resources/app/js/meeting/meeting_broad.css" rel="stylesheet" type="text/css">
	
		<div id="container" class="schedule_group_list schedule_css">
			
				<!-- 工具条 -->
				<div id="toolbar">
					<table width="100%">
						<tbody>
						<tr>
							<td align="center">
									<a class="s_prev_day" href='#' ldate="${curDateOfMonday}"></a>
									<span class="s_today" name='firstday' size='7' type='text'>${curDateOfMonday}</span>
									<a class="s_next_day" href='#' ldate="${curDateOfSunday}"></a>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			<!-- 会议按钮 -->	
			<div class="customButton_top_my">
				<input class="new_meeting" name="new_meeting" type="button" value="新建会议">
			</div>	
			<div class="content">
					
				<table class="schedule_table">
					<tbody>
						<!-- tr start -->						
							<tr class="tr_title">
								<td>会议室</td>
								<c:choose>
									<c:when test="${dateToWeek[0][1] == currentDate}">
										<td ><font color="red">${dateToWeek[0][0]}(${fn:substring(dateToWeek[0][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td>${dateToWeek[0][0]}(${fn:substring(dateToWeek[0][1],8,10)})</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${dateToWeek[1][1] == currentDate}">
										<td><font color="red">${dateToWeek[1][0]}(${fn:substring(dateToWeek[1][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td >${dateToWeek[1][0]}(${fn:substring(dateToWeek[1][1],8,10)})</td>
									</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dateToWeek[2][1] == currentDate}">
										<td><font color="red">${dateToWeek[2][0]}(${fn:substring(dateToWeek[2][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td >${dateToWeek[2][0]}(${fn:substring(dateToWeek[2][1],8,10)})</div></td>
									</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${dateToWeek[3][1] == currentDate}">
										<td ><font color="red">${dateToWeek[3][0]}(${fn:substring(dateToWeek[3][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td>${dateToWeek[3][0]}(${fn:substring(dateToWeek[3][1],8,10)})</div></td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${dateToWeek[4][1] == currentDate}">
										<td><font color="red"><strong>${dateToWeek[4][0]}(${fn:substring(dateToWeek[4][1],8,10)})</strong></font></td>
									</c:when>
									<c:otherwise>
										<td>${dateToWeek[4][0]}(${fn:substring(dateToWeek[4][1],8,10)})</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${dateToWeek[5][1] == currentDate}">
										<td><font color="red">${dateToWeek[5][0]}(${fn:substring(dateToWeek[5][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td >${dateToWeek[5][0]}(${fn:substring(dateToWeek[5][1],8,10)})</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${dateToWeek[6][1] == currentDate}">
										<td ><font color="red">${dateToWeek[6][0]}(${fn:substring(dateToWeek[6][1],8,10)})</font></td>
									</c:when>
									<c:otherwise>
										<td>${dateToWeek[6][0]}(${fn:substring(dateToWeek[6][1],8,10)})</td>
									</c:otherwise>
								</c:choose>
								
							</tr>
					<!-- tr end -->			
					
					<!-- c：forEach开始 -->					
					<c:forEach items="${meetingBeanList}" var="item">
						<tr class="tr_content">
							
							<td class="scheduletd " valign="center" style="text-align: center">
								${item.meetName}
							</td>
							
							<td class="scheduletd" id="Mondy" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 1}">
													<li class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p>
													</li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							
							<td class="scheduletd"  id="Tuesday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 2}">
													<li  class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="scheduletd" id="Wednesday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 3}">
													<li  class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="scheduletd" id="Thursday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 4}">
													<li  class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="scheduletd" id="Friday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 5}">
													<li  class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="scheduletd" id="Saturday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 6}">
													<li  class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
							<td class="scheduletd" id="Sunday" valign="top">
								<div class="scheduletd_div">
									<ul>
										<c:forEach items="${item.meetingEmployList}" var="bean">
												<c:if test="${bean.employDate-1 == 7}">
													<li class="addSchedule" >
														<p onclick="openMeetingDialog('this','${item.meetName}','${bean.startTime}','${bean.endTime}','${bean.employLeader}','${bean.employPhoneNumber}')">${fn:substring(bean.startTime,11,16)}至${fn:substring(bean.endTime,11,16)}-${item.meetName}</p></li>
												</c:if>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
					</c:forEach>
					<!-- c：forEach结束 -->			
				</tbody>			
			</table>
		</div>
	</div>
				
		</div>
</div>
</div>
</div>

<!-- jQuery UI -->
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>

<!-- 自己的js -->
<script src="${ctx}/resources/app/js/meeting/meeting_broad_list.js" type="text/javascript"></script>


</body>
</html>