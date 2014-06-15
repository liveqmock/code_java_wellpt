<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:if test="${addCss!='yes'}">
<title>Inbox Mail List</title>

<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<script type="text/javascript">

function scheduleOnload(){
	var addCss = $("#addCss").val();
	if(addCss=="yes"){
	}else{
		var hei=0;
		$(".scheduletd").each(function (){
			var hei=($(window).height()-89)/5;
			$(this).height(hei+"px");
		});
	}
}
</script>
<style>
	<c:if test="${addCss!='yes'}">
	#tabtop-L3 strong{
 		background: #fee; 
	}
	</c:if >
	.weekend{
		background: gray;
	}
	.new_taday{
		background: blue;
	}
	.new_taday_sj{
		background: blue;
	}
</style>
</c:if>
</head>
<body  style="width:100%; height:100%;padding:0px; margin:0px;" onload="scheduleOnload();">
	<div class="schedule_person_list schedule_css">
	<c:if test="${addCss=='yes'}">
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
	</c:if>
	<input type="hidden" value="${addCss}" id="addCss"/>
	<div id="toolbar">
		<table width="100%"><tr><td align="left">
<!--		<button onclick="openLayer('${now2 }' ,'wuzq','test_con3');" >+</button>-->
<!--		<input type="text" size="7" name="firstday" value="${firstday }"/>-->
		<button class="go_taday" ldate=${now3 } mtype=${mtype }><spring:message code="schedule.info.today" /></button>
		</td>
		<td class="center_td" align="center">
		<a class="s_prev_day"  ldate=${ldate } mtype=${mtype } href="#" ><</a>
		<span class="taday"><c:out value="${lldate }"/></span>
		<a class="s_next_day" href="#" ldate=${ldate } mtype=${mtype }>></a>
		</td>
		<td align="right">
		 <button class="as_month" ldate=${ldate } mtype=${mtype }><spring:message code="schedule.info.month" /></button>
		 <button class="as_day"  ldate=${ldate }  mtype=${mtype }><spring:message code="schedule.info.day" /></button>
		</td></tr></table>
	</div>
	<c:set var="count" value="1"/> 
	<table class="table_content" width="100%" style="height:100%"   border="2" >
		<tr class="tr_title">
		<div style="border: 2;border-width: 2"></div>
		<td width="14%">
		<spring:message code="schedule.info.week1" />
		</td>
		<td width="14%">
		<spring:message code="schedule.info.week2" />
		</td>
		<td width="14%">
		<spring:message code="schedule.info.week3" />
		</td>
		<td width="14%">
		<spring:message code="schedule.info.week4" />
		</td>
		<td width="14%">
		<spring:message code="schedule.info.week5" />
		</td>
		<td width="14%" class="weekend" >
		<spring:message code="schedule.info.week6" />
		</td>
		<td width="14%"  class="weekend" >
		<spring:message code="schedule.info.week7" />
		</td>
		</tr>
		<tr class="tr_content">
		<c:forEach items="${days }" var="row" >
			<c:if test="${row.weekend==1}">
			<td class="scheduletd weekend "  valign="top" height="${100/(totalday/7) }"  onmouseover="showorno('${row.sdate }','one','1');" onmouseout="showorno('${row.sdate }','one','2');">
			</c:if>
			<c:if test="${row.weekend!=1}">
			<td class="scheduletd" valign="top" height="${100/(totalday/7) }" onmouseover="showorno('${row.sdate }','one','1');" onmouseout="showorno('${row.sdate }','one','2');">
			</c:if>
			<div  class="scheduletd_div" style="width:100%;overflow:hidden;font-size: 12px;"  >
			<c:if test="${row.today==1}">
			<table width="100%" class="new_taday"><tr  valign="top"><td align="left" class="td_content_left" width="60%">
			<c:if test="${row.inMonth==0}"><span class="inmonth inmonth_0_css"></c:if>
			<c:if test="${row.inMonth!=0}"><span class="inmonth"></c:if>
			<a href="#" class="as_day"  ldate=${row.sdate }  mtype=${mtype }><c:out value="${row.displayDay }"/></a></span>
			</td>
			<td align="right" class="td_content_right">
			<a href="#" class="as_day"  ldate=${row.sdate }  mtype=${mtype }><c:out value="${row.chinaDay }"/></a>
			</td>
			</tr></table>
			</c:if>
			
			<c:if test="${row.today!=1}">
			<table width="100%"><tr valign="top"><td align="left" class="td_content_left" width="60%">
			<c:if test="${row.inMonth==0}"><span class="inmonth inmonth_0_css"></c:if>
			<c:if test="${row.inMonth!=0}"><span class="inmonth"></c:if>
			<a href="#" class="as_day"  ldate=${row.sdate }  mtype=${mtype }><c:out value="${row.displayDay }"/><c:out value="${row.today}"/></a></span>
			</td>
			<td align="right" class="td_content_right">
			<a href="#" class="as_day"  ldate=${row.sdate }  mtype=${mtype }><c:out value="${row.chinaDay }"/></a>
			</td>
			</tr></table>
			</c:if>
			<table>
			
			<c:forEach items="${sche[row.sdate] }" var="row2">
			<tr>
			<c:if test="${row.today==1}">
			<td class="new_taday_sj" >
			<c:if test="${addCss=='yes'}"><img src="${ctx }/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx }/resources/schedule/img/star-on.png"  /></c:if>
			<a class="day_list_css" href="#" onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','3','','','${row2.tipMethod }','${row2.isComplete }','${row.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
			<font color="${row2.color }" >
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if> 
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>5 }"><c:out value="${fn:substring(row2.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</strike>
			</c:if>
			
			<c:if test="${row2.isComplete!='1' }">
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if> 
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			<c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>5 }"><c:out value="${fn:substring(row2.leaderNames,0,5) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if>
			</c:if>
			</font></a>
			</td>
			</c:if>
			<c:if test="${row.today!='1'}">
			<td >
			<c:if test="${addCss=='yes'}"><img src="${ctx }/resources/theme/images/v1_rc_l_icon.png"  /></c:if>
			<c:if test="${addCss!='yes'}"><img src="${ctx }/resources/schedule/img/star-on.png"  /></c:if>
			<a class="day_list_css" href="#" onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','3','','','${row2.tipMethod }','${row2.isComplete }','${row.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
			<font color="${row2.color }">
			<c:if test="${row2.isComplete=='1' }">
			<strike>
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			
			<%-- <c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if> --%>
			
			</strike>
			</c:if>
			<c:if test="${row2.isComplete!='1' }">
			<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
			<c:choose><c:when test="${fn:length(row2.address)>3 }"><c:out value="${fn:substring(row2.address,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.address }"/></c:otherwise></c:choose> 
			<c:choose><c:when test="${fn:length(row2.scheduleName)>3 }"><c:out value="${fn:substring(row2.scheduleName,0,3) }.."/></c:when><c:otherwise><c:out value="${row2.scheduleName }"/></c:otherwise></c:choose>
			
			<%-- <c:if test="${mtype=='1'||mtype=='2' }">
			<c:choose><c:when test="${fn:length(row2.leaderNames)>6 }"><c:out value="${fn:substring(row2.leaderNames,0,6) }.."/></c:when><c:otherwise><c:out value="${row2.leaderNames }"/></c:otherwise></c:choose>
			</c:if> --%>
			
			</c:if>
			</font></a>
			</td>
			</c:if>
			
			</tr>
			</c:forEach>
			<tr>
			<td>
			<c:if test="${row.scheSize>2 }">
			<c:if test="${addCss=='yes'}">
				<a class="day_list_more" href="#" ldate="${row.sdate }" mtype="${mtype }"><spring:message code="schedule.info.more" /></a>
			</c:if>
			<c:if test="${addCss!='yes'}">
				<spring:message code="schedule.info.total1" />${row.scheSize }<spring:message code="schedule.info.total2" />,<a href="#" onclick="window.location.href='${ctx}/schedule/person_schedule2.action?ldate=${row.sdate }&mtype=${mtype }';"><spring:message code="schedule.btn.view" /></a>
			</c:if>
			</c:if>
			</td>
			</tr>
			</table>
			<div id="${row.sdate }one" class="<c:if test="${row.scheSize>2 }">add_day_sj</c:if>" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('','','','','2','3','','','${creators}','${creatorIds}','${row.sdate }','${ldate }','${mtype }','${now }');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
			</div>
			</td>
			<c:if test="${row.count%7==0 }">
				</tr>
				<c:if test="${count<6}">
					<tr class="tr_content">
				</c:if>
				<c:set var="count" value="${count+1}"></c:set>
			</c:if>
		</c:forEach>
		</tr>
		
	</table>	
	<div class="view_foot"></div>
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
	

//今天
$(".go_taday,.as_month").click(function(){
	var ldate = $(this).attr("ldate");
	var mtype = $(this).attr("mtype");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"stype":0,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/person_schedule.action",
			success:function(result){
				$(".schedule_person_list").parent().html(result);
				/* $(".schedule_person_list").parent().hide();
				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				$(".schedule_person_list").parent().show(); */
			}
		});
	}else{
		window.location.href='${ctx}/schedule/person_schedule.action?ldate='+ldate+'&stype=0&mtype='+mtype;
	}
});
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
			url:"${ctx}/schedule/person_schedule.action",
			success:function(result){
				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list").parent().hide();
// 				$(".schedule_person_list").parent().html(result);
// // 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
// 				$(".schedule_person_list").parent().show();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/person_schedule.action?ldate='+ldate+'&stype=1&mtype='+mtype;
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
			url:"${ctx}/schedule/person_schedule.action",
			success:function(result){
				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list").parent().hide();
// 				$(".schedule_person_list").parent().html(result);
// // 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
// 				$(".schedule_person_list").parent().show();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/person_schedule.action?ldate='+ldate+'&stype=2&mtype='+mtype;
	}
});
//月
// $(".as_month").click(function(){
// 	var ldate = $(this).attr("ldate");
// 	var mtype = $(this).attr("mtype");
// 	var addCss = $("#addCss").val();
// 	if(addCss=='yes'){
// 		$.ajax({
// 			type:"post",
// 			async:false,
// 			data : {"ldate":ldate,"mtype":mtype,"requestType":"cms"},
// 			url:"${ctx}/schedule/person_schedule.action",
// 			success:function(result){
// 				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list").parent().children().not(".schedule_person_list2").remove();
// 			}
// 		});
// 	}else{
// 		window.location.href='${ctx}/schedule/person_schedule.action?ldate='+ldate+'&mtype='+mtype;
// 	}
// });
//日
$(".as_day").click(function(){
	var mtype = $(this).attr("mtype");
	var ldate = $(this).attr("ldate");
	var addCss = $("#addCss").val();
	if(addCss=='yes'){
		$.ajax({
			type:"post",
			async:false,
			data : {"ldate":ldate,"mtype":mtype,"requestType":"cms"},
			url:"${ctx}/schedule/person_schedule2.action",
			success:function(result){
				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list2").parent().children().not(".schedule_person_list").remove();
				
// 				$(".schedule_person_list").parent().html(result);
// 				$(".schedule_person_list2").parent().show();
			}
		});
	}else{
		window.location.href='${ctx}/schedule/person_schedule2.action?mtype='+mtype+'&ldate='+ldate;
	}
});
//更多
$(".day_list_more").click(function(){
	var mtype = $(this).attr("mtype");
	var ldate = $(this).attr("ldate");
	$.ajax({
		type:"post",
		async:false,
		data : {"mtype":mtype,"ldate":ldate,"requestType":"cms"},
		url:"${ctx}/schedule/person_schedule2.action",
		success:function(result){
			$(".schedule_person_list").parent().html(result);
// 			$(".schedule_person_list2").parent().children().not(".schedule_person_list2").remove();
		}
	});
});
</script>
<%-- 	<input type="hidden" id="contextPath" value="${ctx}"></input> --%>
	</div>
</body>
</html>