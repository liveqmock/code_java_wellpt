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
		<td style="<c:if test="${fn:length(sche[row.sdate]) >0&&row.displayDay!=''}">cursor: pointer;</c:if>" class="scheduletd <c:if test="${row.today==1}">nowday</c:if> <c:if test="${fn:length(sche[row.sdate]) >0&&row.displayDay!=''}"> tank</c:if>" valign="top">
		<div class="<c:if test="${row.displayDay!=null&&row.displayDay!=''}">td_div_content</c:if><c:if test="${row.displayDay==null||row.displayDay==''}">td_div_nullcontent</c:if>">
			<c:if test="${fn:length(sche[row.sdate]) >0&&row.displayDay!=''}">
				<div class="rc"></div>
				
				<div class="sc_item" style="height: auto;">
				<i class="con-icon">  </i>
					<ul>
					<c:forEach items="${sche[row.sdate] }" var="row2">
						<li>
						<a href="#" onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','3','','','${row2.tipMethod }','${row2.isComplete }','${row.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
						<font color="${row2.color }">
						<c:if test="${row2.isComplete=='1' }">
						<strike>
						<c:if test="${row2.startTime=='' or row2.startTime==null}">全天 </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
						<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
						<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
						<c:if test="${mtype=='1'||mtype=='2' }">
						<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
						</c:if>
						</strike>
						</c:if>
						<c:if test="${row2.isComplete!='1' }">
						<c:if test="${row2.startTime=='' or row2.startTime==null}">全天 </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
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
	<button class="date_foot_nd" ldate=${now3 } mtype=${mtype } >今天</button>
<%-- 	<c:choose>
		<c:when test="${empty pageType}">
			<button class="date_foot_more" title="查看详细" onclick="location.href='${ctx}/pt/cms/index.jsp?uuid=c645ba94-a0d0-48f3-9ef4-a04624b548e8&treeName=SCHEDULE_CATE'"></button>
		</c:when>
		<c:when test="${pageType == 'XZSPY_JJGL'}">
			<button class="date_foot_more" title="查看详细" onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=7f2f78e0-024b-4c18-be87-915c337aa445&mid=2014228163647814&moduleid=055acade-425b-453f-8cfb-a6dc71ffa29b&treeName=SCHEDULE_CATE_JJ'"></button>
		</c:when>
		<c:when test="${pageType == 'XZSPY_SPGL'}">
			<button class="date_foot_more" title="查看详细" onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=1a50acec-7682-441a-b117-54fb5dc79bf6&mid=201422816489382&moduleid=055acade-425b-453f-8cfb-a6dc71ffa29b&treeName=SCHEDULE_CATE_SP'"></button>
		</c:when>
		<c:when test="${pageType == 'XZSPY_QJGL'}">
			<button class="date_foot_more" title="查看详细" onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=55302281-0615-4c5b-b3f2-eb306014590b&mid=201422816553135&moduleid=055acade-425b-453f-8cfb-a6dc71ffa29b&treeName=SCHEDULE_CATE_QJ'"></button>
		</c:when>
	</c:choose> --%>
</div>
<div class="view_foot" style=" margin-right: 0;height:3px;"></div>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script src="${ctx}/resources/schedule/schedule.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#prev_date").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":1,"ldate":ldate,"mtype":mtype},
			url:"${ctx}/cms/schedule/person_schedule",
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
			url:"${ctx}/cms/schedule/person_schedule",
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
			async:false,
			data : {"stype":0,"ldate":ldate,"mtype":mtype},
			url:"${ctx}/cms/schedule/person_schedule",
			success:function(result){
				$(".schedule_module").html(result);
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