<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/pt/common/taglibs.jsp"%>
<!DOCTYPE  html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><spring:message code="fileManager.label.fileManager" /></title>
	<%@ include file="/pt/common/meta.jsp"%>
</head>

<body>
	<div>
	<div id="toolbar">
		<table width="100%">
			<tr>
				<td class="left_td" align="left">
					<button class="go_taday"><spring:message code="schedule.info.today" /></button>
				</td>
				<td class="center_td" align="center">
					<a class="prev_month" href="#" ><</a>
					<span class="today"><fmt:formatDate value="${date }" pattern="yyyy年MM月"/> </span>
					<a class="next_month" href="#">></a>
				</td>
				<td class="right_td" align="right">
					 <button class="month_view"><spring:message code="schedule.info.month" /></button>
					 <button class="day_view"   ldate="${ldate }"><spring:message code="schedule.info.day" /></button>
					 <button class="scheSetting" ><spring:message code="schedule.btn.setting" /></button>
				</td>
			</tr>
		</table>
	</div>
	<table class="schedule_table">
		<input id="queryDate" type="hidden" value="<fmt:formatDate value='${date }' pattern='yyyy-MM-dd'/>"/>
		<input id="prev_month" type="hidden" value="<fmt:formatDate value='${prevMonth }' pattern='yyyy-MM-dd'/>"/>
		<input id="next_month" type="hidden" value="<fmt:formatDate value='${nextMonth }' pattern='yyyy-MM-dd'/>"/>
		<input id="ctype" type="hidden" value="${ctype }"/>
		<input id="viewType" type="hidden" value="${viewType }"/>
		<input id="tagId" type="hidden" value="${nowTag.uuid }"/>
		<tr class="tr_title">
			<td><spring:message code="schedule.info.week1" /></td>
			<td><spring:message code="schedule.info.week2" /></td>
			<td><spring:message code="schedule.info.week3" /></td>
			<td ><spring:message code="schedule.info.week4" /></td>
			<td><spring:message code="schedule.info.week5" /></td>
			<td ><spring:message code="schedule.info.week6" /></td>
			<td ><spring:message code="schedule.info.week7" /></td>
		</tr>
		<tr class="tr_content">
			<c:forEach items="${days }" var="day" varStatus="status">
				<c:if test="${status.count%7==1&&status.count!=1 }">
					</tr>
					<tr class="tr_content">
				</c:if>
				<td class="scheduletd ${day.weekend==1?'weekend':''}" valign="top" >
					<div  class="scheduletd_div"  >
						<div class="head_div ${day.today==1?'today':'' } ">
							<span class="${day.holiday } ${day.inMonth==0?'':'inmonth'}" ldate="${day.sdate }">${day.displayDay }</span>
							<span class="right ${day.holiday }"  ldate="${day.sdate }">${day.chinaDay }</span>
						</div>
						<ul>
							<c:forEach items="${scheMap[day.sdate] }" var="sche">
								<li>
								<c:if test="${sche.isComplete=='1' }"><strike></c:if>
								<p id='${day.sdate }${sche.uuid }' style="background-color:${sche.tag.color }; color: ${sche.color};" 
									title="主题：${sche.scheduleName}；开始时间：${sche.startTime == null?'全天':sche.startTime }；地点：${sche.address }；发起人：${sche.respon }；邀请的人：${sche.inviteeNames}" 
									onclick="openScheduleDialog('${viewType }','${ctype }','${ldate }','${currentUserId }','${nowTag.uuid }',
										'${sche.uuid }','${sche.encodeName }','${sche.encodeAddress }','${sche.dstartDate }','${sche.startTime }','${sche.dendDate }','${sche.endTime }',
										'${sche.isComplete }','${sche.respon}','${sche.responIds }','${sche.pleases }','${sche.pleaseIds }',
										'${sche.color}','${sche.tip }','${sche.tipDate }','${sche.tipTime }','${sche.tipMethod }','${sche.repeat }','${sche.creators }','${sche.creatorIds }','${sche.inviteeNames}','${sche.inviteeIds}','${sche.acceptIds }',
										'${sche.acceptNames }','${sche.refuseIds}','${sche.refuseNames}','${nowTag.name}','${sche.content }');">
									${sche.startTime == null?'全天':sche.startTime }&nbsp;
									${sche.scheduleName}
									</script> 
								</p>
								<c:if test="${sche.isComplete=='1' }"></strike></c:if>
								</li>
							</c:forEach>
							<c:if test="${fn:startsWith(ctype ,'1') || fn:startsWith(ctype,'3')}">
								<li class="addSchedule" style="display: none;">
									<p onclick="openNewSchedule('${viewType }','${ctype }','${day.sdate}','${nowTag.uuid }','${nowTag.name }','${nowTag.userId }','${nowTag.userName }','${currentUserId}','${currentUserName }');" >
										<spring:message code="schedule.info.newSchedule" />
									</p>
								</li>
							</c:if>
						</ul>
					</div>
				</td>
			</c:forEach>
		</tr>
	
	</table>
	</div>
<%-- 	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script> --%>
<%-- 	<script type="text/javascript" src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script> --%>
	<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/sche.js"></script>
	<script type="text/javascript" src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>

	<script type="text/javascript">

		// 转到日历设置页面 
		$(".scheSetting").click(function(){
			$.ajax({
				type : "POST",
				data:"",
				url : ctx + "/schedule/calendar/setting",
				dataType : "text",
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		});
	
		// 查询日期改为今天 
		$(".go_taday").click(function(){
			$.ajax({
				type : "POST",
				data:{"ctype":$("#ctype").val(),"tagId":$("#tagId").val(),"viewType":"1"},
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		});
		// 上一月 
		$(".prev_month").click(function(){
			$.ajax({
				type : "POST",
				data:{"dateStr":$("#prev_month").val(),"ctype":$("#ctype").val(),"tagId":$("#tagId").val(),"viewType":"1"},
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		});
		// 下一月 
		$(".next_month").click(function(){
			$.ajax({
				type : "POST",
				data:{"dateStr":$("#next_month").val(),"ctype":$("#ctype").val(),"tagId":$("#tagId").val(),"viewType":"1"},
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		});
		
		// 日视图 
		$(".day_view").click(function(){
			$.ajax({
				type : "POST",
				data:{"dateStr":$(this).attr("ldate"),"ctype":$("#ctype").val(),"tagId":$("#tagId").val(),"viewType":"2"},
				url : ctx + "/schedule/month/view",
				dataType : "text",
				success : function callback(result) {
					refreshSchedule(result);
				}
			});
		});
		// 新建事件的隐藏、显示 
		$(".scheduletd_div").hover(
			function(){
				$(this).find(".addSchedule").show();
			},
			function(){
				$(this).find(".addSchedule").hide();
			}
		);
	</script>
</body>
</html>