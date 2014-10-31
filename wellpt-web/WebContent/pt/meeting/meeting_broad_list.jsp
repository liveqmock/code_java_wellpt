<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/pt/common/meta.jsp"%>
<title>Insert title here</title>
	
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;" >
<div class="viewContent">
<div class="dialogcontent">

<link href="${ctx}/resources/theme/css/meeting_broad.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/pt/css/calendar.css" />
	
		<div id="container" class="schedule_group_list schedule_css">
			
				<div id="toolbar">
					<table width="100%">
						<tbody>
						<tr>
							<td align="left">
									<a class="s_prev_day" href='#' ldate="${curDateOfMonday}"></a>
									<span class="s_taday1" name='firstday' size='7' type='text'>${curDateOfMonday}</span>
									<a class="s_next_day" href='#' ldate="${curDateOfSunday}"></a>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			<div class="content">
					<div class="schedule_leader_list_content_div">
									<table class="schedule_leader_list_content" width='100%' height='100%' border='2'>
												
										<tr class="tr_title">
											<td   class="tr_content_l1" align="center" width="16%" >会议室</td>
											<c:choose>
												<c:when test="${dateToWeek[0][1] == currentDate}">
													<td align="center"  width="12%"><font color="red">${dateToWeek[0][0]}(${fn:substring(dateToWeek[0][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[0][0]}(${fn:substring(dateToWeek[0][1],8,10)})</td>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${dateToWeek[1][1] == currentDate}">
													<td align="center" width="12%"><font color="red">${dateToWeek[1][0]}(${fn:substring(dateToWeek[1][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[1][0]}(${fn:substring(dateToWeek[1][1],8,10)})</td>
												</c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${dateToWeek[2][1] == currentDate}">
													<td align="center" width="12%"><font color="red">${dateToWeek[2][0]}(${fn:substring(dateToWeek[2][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[2][0]}(${fn:substring(dateToWeek[2][1],8,10)})</div></td>
												</c:otherwise>
											</c:choose>
											
											<c:choose>
												<c:when test="${dateToWeek[3][1] == currentDate}">
													<td align="center" width="12%"><font color="red">${dateToWeek[3][0]}(${fn:substring(dateToWeek[3][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[3][0]}(${fn:substring(dateToWeek[3][1],8,10)})</div></td>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${dateToWeek[4][1] == currentDate}">
													<td align="center" width="12%"><font color="red"><strong>${dateToWeek[4][0]}(${fn:substring(dateToWeek[4][1],8,10)})</strong></font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[4][0]}(${fn:substring(dateToWeek[4][1],8,10)})</td>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${dateToWeek[5][1] == currentDate}">
													<td align="center" width="12%"><font color="red">${dateToWeek[5][0]}(${fn:substring(dateToWeek[5][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center"  width="12%">${dateToWeek[5][0]}(${fn:substring(dateToWeek[5][1],8,10)})</td>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${dateToWeek[6][1] == currentDate}">
													<td align="center" width="12%"><font color="red">${dateToWeek[6][0]}(${fn:substring(dateToWeek[6][1],8,10)})</font></td>
												</c:when>
												<c:otherwise>
													<td align="center" width="12%">${dateToWeek[6][0]}(${fn:substring(dateToWeek[6][1],8,10)})</td>
												</c:otherwise>
											</c:choose>
											
										</tr>
									
										
										<c:forEach items="${meetingBeanList}" var="item">
											<tr class='tr_content' style='height: 100px;overflow: hidden;'>
												
												<td height="120" class="tr_content_l1" width="16%"  align="center">${item.meetName}</td>
												
												
												<td class="tr_content_id" width="12%"  id="Mondy"><div style='width: 100px;height:100px; overflow: hidden; border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 1}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Tuesday"><div style='width: 100px;height:100px;  overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 2}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Wednesday"><div style='width: 100px; height:100px; overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 3}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Thursday"><div style='width: 100px; height:100px; overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 4}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Friday"><div style='width: 100px; height:100px; overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 5}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Saturday"><div style='width: 100px; height:100px; overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 6}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
												<td class="tr_content_id" height="100" width="12%"  id="Sunday"><div style='width: 100px; height:100px; overflow: hidden;  border: 1px; font-size: 12px;'>
													<c:forEach items="${item.meetingEmployList}" var="bean">
															<c:if test="${bean.employDate-1 == 7}">
																<div  style="width:100%;overflow:hidden;font-size: 12px;"><a>${fn:substring(bean.startTime,11,16)}:${fn:substring(bean.endTime,11,16)}-${item.meetName}</a></div>
															</c:if>
													</c:forEach>
												</div></td>
											</tr>
										</c:forEach>
									</table>
							</div>
					</div>
				</div>
				<div class="view_foot"></div>
		</div>
</div>
</div>
</div>
<script type="text/javascript">
	
//上一周
$(".s_prev_day").click(function(){
	
	var ldate=$(this).attr(
		"ldate");
	//alert(ldate);
	$.ajax({
		type:"post",
		async:false,
		data
		: {"stype":1,"ldate":ldate},
		url:"${ctx}/pt/meeting/meeting_board_show.action",
		success:function(result){
			$(".dialogcontent").html(result);
		}
	});
	
});

//下一周
$(".s_next_day").click(function(){
	var
		ldate=$(this).attr("ldate");
	$.ajax({
		type:"post",
		async:false,
		data: {"stype":2,"ldate":ldate},
		url:"${ctx}/pt/meeting/meeting_board_show.action",
		success:function(result){
			$(".dialogcontent").html(result);
		}
	});
});
				
</script>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
<script src="${ctx}/resources/meeting/meeting.js" type="text/javascript"></script>
</body>
</html>