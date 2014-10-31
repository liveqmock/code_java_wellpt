<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Inbox Mail List</title>
		<%@ include file="/pt/common/meta.jsp"%>
	</head>
	
	<body  style="width:100%; height:100%;padding:0px; margin:0px;">
		<div class="schedule_module">
			<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/scheMonth.css" />
			<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
			<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
			<div class="toolbar">
				<div class="toolbar_title prev" id="prev_date" title="上个月" style="cursor: pointer;" ldate=${ldate } mtype=${mtype }></div>
				<div class="toolbar_title title-month" >${lldate }</div>
				<div class="toolbar_title next" id="prev_next" title="下个月" style="cursor: pointer;" ldate=${ldate } mtype=${mtype }></div>
			</div>
			<div class="date_content">
				<table   width="100%"  >
					<tr class="title_head">
					<td width="14%">一</td>
					<td width="14%">二</td>
					<td width="14%">三</td>
					<td width="14%">四</td>
					<td width="14%">五</td>
					<td width="14%" class="weekend">六</td>
					<td width="14%" class="weekend">日</td>
					</tr>
					<tr class="tr_content">
					<c:forEach items="${days }" var="row" >
						<td style="${row.displayDay!=''?'cursor: pointer':''}" class="scheduletd ${row.today==1?'nowday':''} ${fn:length(sche[row.sdate]) >0&&row.displayDay!='' ?'tank':''}" valign="top">
						<div ldate="${row.sdate }" class="${row.displayDay!=null&&row.displayDay!=''?'td_div_content':'td_div_nullcontent'} ${row.holiday}">
							<c:if test="${fn:length(sche[row.sdate]) >0&&row.displayDay!=''}">
								<div class="rc"></div>
								
								<div class="sc_item" style="height: auto;">
								<i class="con-icon">  </i>
									<ul>
									<c:forEach items="${sche[row.sdate] }" var="row2">
										<li>
										<a href="#" onclick="openScheduleDialog('${viewType }','${ctype }','${ldate }','${currentUserId }','${row2.tag.uuid }',
														'${row2.uuid }','${row2.encodeName }','${row2.encodeAddress }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }',
														'${row2.isComplete }','${row2.respon}','${row2.responIds }','${row2.pleases }','${row2.pleaseIds }',
														'${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.tipMethod }','${row2.repeat }','${row2.creators }','${row2.creatorIds }','${row2.inviteeNames}','${row2.inviteeIds}','${row2.acceptIds }',
														'${row2.acceptNames }','${row2.refuseIds}','${row2.refuseNames}','${nowTag.name}','${row2.content }');">
										<font color="${row2.color }">
										<c:if test="${row2.isComplete=='1' }">
										<strike>
										<c:if test="${row2.startTime=='' or row2.startTime==null}">全天 </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>&nbsp;<c:out value="${row2.startTime2 }"/> </c:if>
										<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
										<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
										<c:if test="${mtype=='1'||mtype=='2' }">
										<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
										</c:if>
										</strike>
										</c:if>
										<c:if test="${row2.isComplete!='1' }">
										<c:if test="${row2.startTime=='' or row2.startTime==null}">全天 </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>&nbsp;<c:out value="${row2.startTime2 }"/> </c:if>
										<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
										<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
										<c:if test="${mtype=='1'||mtype=='2' }">
										<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
										</c:if>
										</c:if>
										</font></a>
										</li>
										
									</c:forEach>
									</ul>
								</div>
								
							</c:if>
							<span><c:out value="${row.displayDay }"/></span>
						</div>
						</td>
					<c:if test="${row.count%7==0 }">
					</tr>
					<tr class="tr_content">
					</c:if>
					</c:forEach>
					</tr>
				</table>	
				<div id="scheduleone" style="display: none;"></div>
			</div>
			<div class="date_foot">
				<a class="date_foot_nd" ldate=${now3 } mtype=${mtype } >
					<i class="icon_schedule_today"></i>
					今天
				</a>
			</div>
			<div class="view_foot" style=" margin-right: 0;height:3px;"></div>
			<script src="${ctx}/resources/jquery/jquery.js"></script>
			<!-- jQuery UI -->
			<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
			<script type="text/javascript"
					src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
			<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
				<script type="text/javascript" src="${ctx}/resources/schedule/sche.js"></script>
			<script type="text/javascript">
			$(function(){
				$(".td_div_content").click(function(){
					$.ajax({
						type:"post",
						async:false,
						data : {"ldate":$(this).attr("ldate")},
						url:"${ctx}/schedule/index/list",
						success:function(result){
							$(".schedule_index_list").html(result);
						}
					});
				});
				
				$("#prev_date").click(function(){
					var ldate = $(this).attr("ldate");
					var mtype = $(this).attr("mtype");
					$.ajax({
						type:"post",
						async:false,
						data : {"stype":1,"ldate":ldate,"mtype":mtype},
						url:"${ctx}/schedule/index/month",
						success:function(result){
							$(".schedule_module").html(result);
						}
					});
				});
				$("#prev_next").click(function(){
					var ldate = $(this).attr("ldate");
					var mtype = $(this).attr("mtype");
					$.ajax({
						type:"post",
						async:false,
						data : {"stype":2,"ldate":ldate,"mtype":mtype},
						url:"${ctx}/schedule/index/month",
						success:function(result){
							$(".schedule_module").html(result);
						}
					});
				});
				$(".date_foot_nd").click(function(){
					var ldate = $(this).attr("ldate");
					var mtype = $(this).attr("mtype");
					$.ajax({
						type:"post",
						data : {"stype":0,"ldate":ldate,"mtype":mtype},
						url:"${ctx}/schedule/index/month",
						success:function(result){
							$(".schedule_module").html(result);
						}
					});
					$.ajax({
						type:"post",
						data : {},
						url:"${ctx}/schedule/index/list",
						success:function(result){
							$(".schedule_index_list").html(result);
						}
					});
					
				});
				$(".tank").unbind("mouseover");
				$(".tank").mouseover(function(){
					$(this).find(".sc_item").show();
				});
				$(".tank").unbind("mouseout");
				$(".tank").mouseout(function(){
					$(this).find(".sc_item").hide();
				});
				$(".sc_item").unbind("mouseout");
				$(".sc_item").mouseout(function(){
					$(this).hide();
				});
			});
			</script>
			<input type="hidden" id="contextPath" value="${ctx}"></input>
		</div>
	</body>
</html>