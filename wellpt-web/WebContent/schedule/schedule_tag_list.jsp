<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:if test="${addCss!='yes'}">
		<title>Schedule Tag List</title>
		<%@ include file="/pt/common/meta.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" ></link>
		<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js" ></script>
		<script type="text/javascript">
			function scheduleOnload(){
				var addCss = $("#addCss").val();
				if(addCss=="yes"){
				}else{
					$(".scheduletd").each(function (){
						var hei=($(window).height()-89)/5;
						$(this).height(hei+"px");
					});
				}
			}
		</script>
	</c:if>
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;" onload="scheduleOnload();">
	<div class="viewContent">
	<c:if test="${addCss=='yes'}">
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
	</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
	<div class="schedule_person_list schedule_css">
		<div id="toolbar">
			<table width="100%">
				<tr>
					<td align="left">
						<button class="go_taday" ldate=${now3 } ><spring:message code="schedule.info.today" /></button>
					</td>
					<td class="center_td" align="center">
						<a class="s_prev_day"  ldate=${lastMonth }  href="#" ><</a>
						<span class="taday"><c:out value="${showDate }"/></span>
						<a class="s_next_day" href="#" ldate=${nextMonth } >></a>
					</td>
					<td align="right">
						 <button class="as_month" ldate=${date } ><spring:message code="schedule.info.month" /></button>
						 <button class="as_day"  ldate=${date }  tagId="${nowTag.uuid }"><spring:message code="schedule.info.day" /></button>
					</td>
				</tr>
			</table>
		</div>
		<div class="content">
			<div class="content_group_list">
				<c:forEach items="${tagList}" var="tag">
					<div class="content_group_item ${nowTag.uuid==tag.uuid?'activity':''}">
						<a class="group_name" tagId="${tag.uuid}" ldate="${date}"
							href="#">${tag.name}</a> 
						<a class="editgroup" href="#"
							onclick="editTag('${tag.uuid}','${tag.name}','${tag.color}','${tag.sort}');"></a>
						<a class="delgroup" href="#"
							onclick="deleteTag('${tag.uuid}');"></a>
					</div>
				</c:forEach>
				<a class="addgroup"  href="#" onclick="editTag('','','','');">
					<spring:message code="schedule.btn.add" />日历组
				</a>
			</div>
			<c:set var="count" value="1"/> 
			<table class="table_content" width="100%" style="height:100%;border:solid #red 3px;" >
				<tr class="tr_title">
					<td width="14%" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week1" />
					</td>
					<td width="14%" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week2" />
					</td>
					<td width="14%" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week3" />
					</td>
					<td width="14%" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week4" />
					</td>
					<td width="14%" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week5" />
					</td>
					<td width="14%" class="weekend" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};">
						<spring:message code="schedule.info.week6" />
					</td>
					<td width="14%"  class="weekend" style="border:1px solid ${not empty nowTag.color?nowTag.color:black};;">
						<spring:message code="schedule.info.week7" />
					</td>
				</tr>
				<tr class="tr_content">
					<c:forEach items="${days }" var="day" >
						<c:if test="${day.weekend==1}">
							<td class="scheduletd weekend "  valign="top" height="${100/(totalday/7) }"  
								style="border:1px solid ${not empty nowTag.color?nowTag.color:black};"
								onmouseover="showorno('${day.sdate }','one','1');" onmouseout="showorno('${day.sdate }','one','2');">
						</c:if>
						<c:if test="${day.weekend!=1}">
							<td class="scheduletd" valign="top" height="${100/(totalday/7) }" 
								style="border:1px solid ${not empty nowTag.color?nowTag.color:black};"
								onmouseover="showorno('${day.sdate }','one','1');" onmouseout="showorno('${day.sdate }','one','2');">
						</c:if>
						<div  class="scheduletd_div" style="width:100%;overflow:hidden;font-size: 12px;"  >
							<c:if test="${day.today==1}">
								<table width="100%" class="new_taday">
									<tr  valign="top">
										<td align="left" class="td_content_left" width="60%">
											<c:if test="${day.inMonth==0}"><span class="inmonth inmonth_0_css"></c:if>
											<c:if test="${day.inMonth!=0}"><span class="inmonth"></c:if>
											<a href="#" class="as_day"  ldate=${day.sdate }  tagId="${nowTag.uuid }" ><c:out value="${day.displayDay }"/></a></span>
										</td>
										<td align="right" class="td_content_right">
											<a href="#" class="as_day"  ldate=${day.sdate }   tagId="${nowTag.uuid }"><c:out value="${day.chinaDay }"/></a>
										</td>
									</tr>
								</table>
							</c:if>
						
							<c:if test="${day.today!=1}">
								<table width="100%">
									<tr valign="top">
										<td align="left" class="td_content_left" width="60%">
											<c:if test="${day.inMonth==0}">
												<span class="inmonth inmonth_0_css">
											</c:if>
											<c:if test="${day.inMonth!=0}">
												<span class="inmonth">
											</c:if>
											<a href="#" class="as_day"  ldate=${day.sdate }  tagId="${nowTag.uuid }" ><c:out value="${day.displayDay }"/><c:out value="${day.today}"/></a></span>
										</td>
										<td align="right" class="td_content_right">
											<a href="#" class="as_day"  ldate=${day.sdate }   tagId="${nowTag.uuid }"><c:out value="${day.chinaDay }"/></a>
										</td>
									</tr>
								</table>
							</c:if>
						
							<table>
								<c:forEach items="${sche[day.sdate] }" var="schedule">
									<tr>
										<c:if test="${day.today==1}">
											<td class="new_taday_sj" >
												<c:if test="${addCss=='yes'}">
													<img src="${ctx }/resources/theme/images/v1_rc_l_icon.png"  />
												</c:if>
												<c:if test="${addCss!='yes'}">
													<img src="${ctx }/resources/schedule/img/star-on.png"  />
												</c:if>
												<a class="day_list_css" href="#" onclick="openScheduleDialog('','','${mtype }','${date }','${userno }','2','3','','','${schedule.tipMethod }','${schedule.isComplete }','${day.sdate }','${schedule.uuid }','${schedule.scheduleName }','${schedule.address }','${schedule.dstartDate }','${schedule.startTime }','${schedule.dendDate }','${schedule.endTime }','${schedule.isView }','${schedule.status }','${schedule.leaderNames }','${schedule.leaderIds }','${schedule.pleases }','${schedule.pleaseIds }','${schedule.views }','${schedule.viewIds }','${schedule.color}','${schedule.tip }','${schedule.tipDate }','${schedule.tipTime }','${schedule.repeat }','${schedule.startTime2 }','${schedule.endTime2 }','${schedule.tipTime2 }','${schedule.creators }','${schedule.creatorIds }','${schedule.isLeaderView}','${schedule.inviteeNames}','${schedule.inviteeIds}');">
													<font color="${schedule.color }" >
														<c:if test="${schedule.isComplete=='1' }">
															<strike>
																<c:if test="${schedule.startTime=='' or schedule.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
																<c:if test="${schedule.startTime!='' and schedule.startTime!=null}"><c:out value="${schedule.startTime }"/>&nbsp;<c:out value="${schedule.startTime2 }"/> </c:if> 
																<c:choose><c:when test="${fn:length(schedule.address)>3 }"><c:out value="${fn:substring(schedule.address,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.address }"/></c:otherwise></c:choose> 
																<c:choose><c:when test="${fn:length(schedule.scheduleName)>3 }"><c:out value="${fn:substring(schedule.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.scheduleName }"/></c:otherwise></c:choose>
																<c:if test="${mtype=='1'||mtype=='2' }">
																<c:choose><c:when test="${fn:length(schedule.leaderNames)>5 }"><c:out value="${fn:substring(schedule.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${schedule.leaderNames }"/></c:otherwise></c:choose>
																</c:if>
															</strike>
														</c:if>
									
														<c:if test="${schedule.isComplete!='1' }">
															<c:if test="${schedule.startTime=='' or schedule.startTime==null}">
																<spring:message code="schedule.info.allDay" /> 
															</c:if>
															<c:if test="${schedule.startTime!='' and schedule.startTime!=null}">
																<c:out value="${schedule.startTime }"/>:<c:out value="${schedule.startTime2 }"/> 
															</c:if> 
															<c:choose>
																<c:when test="${fn:length(schedule.address)>3 }">
																	<c:out value="${fn:substring(schedule.address,0,3) }.."/>
																</c:when>
																<c:otherwise><c:out value="${schedule.address }"/></c:otherwise>
															</c:choose> 
															<c:choose>
																<c:when test="${fn:length(schedule.scheduleName)>3 }">
																	<c:out value="${fn:substring(schedule.scheduleName,0,3) }.."/>
																</c:when>
																<c:otherwise><c:out value="${schedule.scheduleName }"/></c:otherwise>
															</c:choose>
															<c:if test="${mtype=='1'||mtype=='2' }">
																<c:choose><c:when test="${fn:length(schedule.leaderNames)>5 }"><c:out value="${fn:substring(schedule.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${schedule.leaderNames }"/></c:otherwise></c:choose>
															</c:if>
														</c:if>
													</font>
												</a>
											</td>
										</c:if>
										<c:if test="${day.today!='1'}">
											<td >
												<c:if test="${addCss=='yes'}"><img src="${ctx }/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
												<c:if test="${addCss!='yes'}"><img src="${ctx }/resources/schedule/img/star-on.png"  /></c:if>
												<a class="day_list_css" href="#" onclick="openScheduleDialog('','','${mtype }','${date }','${userno }','2','3','','','${schedule.tipMethod }','${schedule.isComplete }','${day.sdate }','${schedule.uuid }','${schedule.scheduleName }','${schedule.address }','${schedule.dstartDate }','${schedule.startTime }','${schedule.dendDate }','${schedule.endTime }','${schedule.isView }','${schedule.status }','${schedule.leaderNames }','${schedule.leaderIds }','${schedule.pleases }','${schedule.pleaseIds }','${schedule.views }','${schedule.viewIds }','${schedule.color}','${schedule.tip }','${schedule.tipDate }','${schedule.tipTime }','${schedule.repeat }','${schedule.startTime2 }','${schedule.endTime2 }','${schedule.tipTime2 }','${schedule.creators }','${schedule.creatorIds }','${schedule.isLeaderView}','${schedule.inviteeNames}','${schedule.inviteeIds}');">
													<font color="${schedule.color }">
														<c:if test="${schedule.isComplete=='1' }">
															<strike>
																<c:if test="${schedule.startTime=='' or schedule.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
																<c:if test="${schedule.startTime!='' and schedule.startTime!=null}"><c:out value="${schedule.startTime }"/>:<c:out value="${schedule.startTime2 }"/> </c:if>
																<c:choose><c:when test="${fn:length(schedule.address)>3 }"><c:out value="${fn:substring(schedule.address,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.address }"/></c:otherwise></c:choose> 
																<c:choose><c:when test="${fn:length(schedule.scheduleName)>3 }"><c:out value="${fn:substring(schedule.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.scheduleName }"/></c:otherwise></c:choose>
											
															</strike>
														</c:if>
														<c:if test="${schedule.isComplete!='1' }">
															<c:if test="${schedule.startTime=='' or schedule.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if>
															<c:if test="${schedule.startTime!='' and schedule.startTime!=null}"><c:out value="${schedule.startTime }"/>:<c:out value="${schedule.startTime2 }"/> </c:if>
															<c:choose><c:when test="${fn:length(schedule.address)>3 }"><c:out value="${fn:substring(schedule.address,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.address }"/></c:otherwise></c:choose> 
															<c:choose><c:when test="${fn:length(schedule.scheduleName)>3 }"><c:out value="${fn:substring(schedule.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${schedule.scheduleName }"/></c:otherwise></c:choose>
								
														</c:if>
													</font>
												</a>
											</td>
										</c:if>
						
									</tr>
								</c:forEach>
								<tr>
									<td>
										<c:if test="${day.scheSize>2 }">
											<c:if test="${addCss=='yes'}">
												<a class="day_list_more" href="#" ldate="${day.sdate }" mtype="${mtype }" tagId="${nowTag.uuid }"><spring:message code="schedule.info.more" /></a>
											</c:if>
											<c:if test="${addCss!='yes'}">
												<spring:message code="schedule.info.total1" />${day.scheSize }<spring:message code="schedule.info.total2" />,<a href="#" onclick="window.location.href='${ctx}/schedule/person_schedule2.action?ldate=${day.sdate }&mtype=${mtype }';"><spring:message code="schedule.btn.view" /></a>
											</c:if>
										</c:if>
									</td>
								</tr>
							</table>
							<div id="${day.sdate }one" class="<c:if test="${day.scheSize>2 }">add_day_sj</c:if>" style="display: none;">
								<font size="1">
									<a class="addschedule_css" href="#" onclick="openScheduleNewDialog('','','','','2','3','','','${creators}','${creatorIds}','${day.sdate }','${date }','${mtype }','${now }','','','');">
										<spring:message code="schedule.info.newSchedule" />
									</a>
								</font>
							</div>
						</div>
					</td>
						<c:if test="${day.count%7==0 }">
							</tr>
							<c:if test="${count<6}">
								<tr class="tr_content">
							</c:if>
							<c:set var="count" value="${count+1}"></c:set>
						</c:if>
					</c:forEach>
				</tr>
			</table>
		</div>
	</div>
	<c:if test="${addCss!='yes'}">
		<script src="${ctx}/resources/jquery/jquery.js"></script>
		<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
		<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	</c:if>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/schedule.js"></script>
	<script type="text/javascript">
	
		//今天 、上一月、下一月
		$(".go_taday,.as_month,.s_prev_day,.s_next_day").click(function(){
			var date = $(this).attr("ldate");
			var tagUuid = $(".activity .group_name").attr("tagId");
			$.ajax({
				type:"post",
				async:false,
				data : {"date":date,"tagUuid":tagUuid},
				url:"${ctx}/schedule/tag/list",
				success:function(result){
					$(".schedule_person_list").parent().html(result);
				}
			});
		});
		
		// 日视图 ，更多 
		$(".as_day,.day_list_more").click(function(){
			var mtype = "3";
			var ldate = $(this).attr("ldate");
			var tagId = $(this).attr("tagId");
			$.ajax({
				type:"post",
				async:false,
				data : {"ldate":ldate,"mtype":mtype,"tagId":tagId,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list").parent().html(result);
				}
			});
		});
		
		// 日历组选择 
		$(".group_name").click(function() {
			var tagId = $(this).attr("tagId");
			var date=$(this).attr("ldate");
			$.ajax({
				type : "post",
				async : false,
				data : {
					"tagUuid":tagId,
					"date":date
				},
				url : "${ctx}/schedule/tag/list",
				success : function(result) {
					$(".schedule_person_list").parent().html(result);
				}
			});
		});
		
		
		// 鼠标滑过日历组名称时显示修改、删除按钮 
		$(".content_group_item").live("mouseover", function() {
			$(this).find("a").not(".group_name").css("display", "block");
		});
		$(".content_group_item").live("mouseout", function() {
			$(this).find("a").not(".group_name").css("display", "none");
		});
		
	</script>
	</div>
</body>
</html>