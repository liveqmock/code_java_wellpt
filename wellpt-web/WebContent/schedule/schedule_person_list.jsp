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
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css"></link>
		<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
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
		<input type="hidden" value="${ldate}" id="ldate"/>
		<input type="hidden" value="${stype}" id="stype"/>
		<input type="hidden" value="${mtype}" id="mtype"/>
		<input type="hidden" value="${nowTag.uuid}" id="tagId"/>
		<input type="hidden" value="${addCss}" id="addCss"/>
		<div id="toolbar">
			<table width="100%"><tr><td align="left">
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
		<div style="width:100px;float: left;">
			<%-- <c:if test="${mtype=='0' }">
				<div class="second_menu_list">
					<div class="second_menu_item ${not empty nowTag.uuid?'':'activity'}">
						<a class="second_menu_label"  tagId="" href="#">全部</a> 
					</div>
					<c:forEach items="${tagList}" var="tag">
						<div class="second_menu_item ${nowTag.uuid==tag.uuid?'activity':''}">
							<a class="second_menu_label" tagId="${tag.uuid}" href="#">${tag.name}</a> 
							<a class="second_menu_edit" href="#" onclick="editTag('${tag.uuid}','${tag.name}','${tag.color}','${tag.sort}','${tag.isView}','${tag.viewIds}','${tag.viewNames}');"></a>
							<a class="second_menu_del" href="#" onclick="deleteTag('${tag.uuid}');"></a>
						</div>
					</c:forEach>
					<a class="addTag"  href="#" onclick="editTag('','','','','','','','');">
						<spring:message code="schedule.btn.add" />日历组
					</a>
				</div>
			</c:if> --%>
		</div> 
		<div class="content" style="float:left;width: 100%;">
			
		<table class="table_content" width="100%“ style="height:100%"   border="2" >
			<tr class="tr_title">
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
			<c:forEach items="${days }" var="day" >
				<c:if test="${day.weekend==1}">
					<td class="scheduletd weekend "  valign="top" height="${100/(totalday/7) }"  onmouseover="showorno('${day.sdate }','one','1');" onmouseout="showorno('${day.sdate }','one','2');">
				</c:if>
				<c:if test="${day.weekend!=1}">
					<td class="scheduletd" valign="top" height="${100/(totalday/7) }" onmouseover="showorno('${day.sdate }','one','1');" onmouseout="showorno('${day.sdate }','one','2');">
				</c:if>
				<div  class="scheduletd_div"  >
					<c:if test="${day.today==1}">
						<table width="100%" class="new_taday">
							<tr  valign="top">
								<td align="left" class="td_content_left" width="60%">
									<c:if test="${day.inMonth==0}"><span class="inmonth inmonth_0_css"></c:if>
									<c:if test="${day.inMonth!=0}"><span class="inmonth"></c:if>
									<a href="#" class="as_day"  ldate=${day.sdate }  mtype=${mtype }><c:out value="${day.displayDay }"/></a></span>
								</td>
								<td align="right" class="td_content_right">
									<a href="#" class="as_day"  ldate=${day.sdate }  mtype=${mtype }><c:out value="${day.chinaDay }"/></a>
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
								<a href="#" class="as_day"  ldate=${day.sdate }  mtype=${mtype }><c:out value="${day.displayDay }"/><c:out value="${day.today}"/></a></span>
							</td>
							<td align="right" class="td_content_right">
								<a href="#" class="as_day"  ldate=${day.sdate }  mtype=${mtype }><c:out value="${day.chinaDay }"/></a>
							</td>
						</tr>
					</table>
				</c:if>
				
				<table>
					<c:forEach items="${sche[day.sdate] }" var="schedule">
						<tr>
							<td class="${day.today=='1' ?'new_taday_sj':''}" style="background-color: ${schedule.tag.color};">
								<c:if test="${addCss=='yes'}">
									<img src="${ctx }/resources/theme/images/v1_rc_l_icon.png"  />
								</c:if>
								<c:if test="${addCss!='yes'}">
									<img src="${ctx }/resources/schedule/img/star-on.png"  />
								</c:if>
								<a class="day_list_css" href="#" onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','3','','','${schedule.tipMethod }','${schedule.isComplete }','${day.sdate }','${schedule.uuid }','${schedule.scheduleName }','${schedule.address }','${schedule.dstartDate }','${schedule.startTime }','${schedule.dendDate }','${schedule.endTime }','${schedule.isView }','${schedule.status }','${schedule.leaderNames }','${schedule.leaderIds }','${schedule.pleases }','${schedule.pleaseIds }','${schedule.views }','${schedule.viewIds }','${schedule.color}','${schedule.tip }','${schedule.tipDate }','${schedule.tipTime }','${schedule.repeat }','${schedule.startTime2 }','${schedule.endTime2 }','${schedule.tipTime2 }','${schedule.creators }','${schedule.creatorIds }','${schedule.isLeaderView}','${schedule.inviteeNames}','${schedule.inviteeIds}','${schedule.acceptIds }','${schedule.acceptNames }','${schedule.refuseIds}','${schedule.refuseNames}','${schedule.tag.uuid}');">
									<font color="${schedule.color }" >
										<c:if test="${schedule.isComplete=='1' }">
											<strike>
												<c:choose>
													<c:when test="${fn:length(schedule.scheduleName)>9 }">
														<c:out value="${fn:substring(schedule.scheduleName,0,9) }.."/>
													</c:when>
													<c:otherwise>
														<c:out value="${schedule.scheduleName }"/>
													</c:otherwise>
												</c:choose>
											</strike>
										</c:if>
										<c:if test="${schedule.isComplete!='1' }">
											<c:choose>
												<c:when test="${fn:length(schedule.scheduleName)>9 }">
													<c:out value="${fn:substring(schedule.scheduleName,0,9) }.."/>
												</c:when>
												<c:otherwise>
													<c:out value="${schedule.scheduleName }"/>
												</c:otherwise>
											</c:choose>
										</c:if>
									</font>
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="${day.sdate }one" class="${day.scheSize>2 ?'add_day_sj':''}" style="display: none;"><font size="1"><a class="addschedule_css" href="#" onclick="openScheduleNewDialog('','','','','2','3','','','${creators}','${creatorIds}','${day.sdate }','${ldate }','${mtype }','${now }','','','');"><spring:message code="schedule.info.newSchedule" /></a></font></div></div>
			</td>
			<c:if test="${day.count%7==0 }">
				</tr>
				<tr class="tr_content">
			</c:if>
		</c:forEach>
		</tr>
			
		</table>	
		<div class="view_foot"></div>
	</div>
	<c:if test="${addCss!='yes'}">
		<script src="${ctx}/resources/jquery/jquery.js"></script>
		<!-- jQuery UI -->
		<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
		<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script>
	</c:if>
	<script type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/schedule/schedule.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.constant.js"></script>
	
	<script type="text/javascript">
		
		//今天
		$(".go_taday,.as_month").click(function(){
			var ldate = $(this).attr("ldate");
			var mtype = $(this).attr("mtype");
			var tagId = $("#tagId").val();
			var addCss = $("#addCss").val();
			if(addCss=='yes'){
				$.ajax({
					type:"post",
					async:false,
					data : {"stype":0,"ldate":ldate,"mtype":mtype,"tagId":tagId,"requestType":"cms"},
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
			var tagId = $("#tagId").val();
			var addCss = $("#addCss").val();
			if(addCss=='yes'){
				$.ajax({
					type:"post",
					async:false,
					data : {"stype":1,"ldate":ldate,"mtype":mtype,"tagId":tagId,"requestType":"cms"},
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
			var tagId = $("#tagId").val();
			var addCss = $("#addCss").val();
			if(addCss=='yes'){
				$.ajax({
					type:"post",
					async:false,
					data : {"stype":2,"ldate":ldate,"mtype":mtype,"tagId":tagId,"requestType":"cms"},
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

		// 日历组选择 
		$(".group_name").click(function() {
			var tagId = $(this).attr("tagId");
			var ldate=$("#ldate").val();
			var mtype = $("#mtype").val();
			$.ajax({
				type : "post",
				async : false,
				data : {
					"tagId":tagId,
					"ldate":ldate,
					"mtype":mtype,
					"requestType":"cms"
				},
				url : "${ctx}/schedule/person_schedule",
				success : function(result) {
					$(".schedule_person_list").parent().html(result);
				}
			});
		});
		
		//日
		$(".as_day").click(function(){
			var mtype = $(this).attr("mtype");
			var ldate = $(this).attr("ldate");
			var tagId = $("#tagId").val();
			var addCss = $("#addCss").val();
			if(addCss=='yes'){
				$.ajax({
					type:"post",
					async:false,
					data : {"ldate":ldate,"mtype":mtype,"tagId":tagId,"requestType":"cms"},
					url:"${ctx}/schedule/person_schedule2.action",
					success:function(result){
						$(".schedule_person_list").parent().html(result);
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
			var tagId = $("#tagId").val();
			$.ajax({
				type:"post",
				async:false,
				data : {"mtype":mtype,"ldate":ldate,"tagId":tagId,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list").parent().html(result);
		// 			$(".schedule_person_list2").parent().children().not(".schedule_person_list2").remove();
				}
			});
		});

		// 日历组选择 
		$(".second_menu_label").click(function() {
			var tagId = $(this).attr("tagId");
			var ldate=$("#ldate").val();
			var mtype = $("#mtype").val();
			$.ajax({
				type : "post",
				async : false,
				data : {
					"tagId":tagId,
					"ldate":ldate,
					"mtype":mtype,
					"requestType":"cms"
				},
				url : "${ctx}/schedule/person_schedule",
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
		$(".second_menu_item").live("mouseover", function() {
			$(this).find("a").not(".second_menu_label").css("display", "block");
		});
		$(".second_menu_item").live("mouseout", function() {
			$(this).find("a").not(".second_menu_label").css("display", "none");
		});
	</script>

	</div>
</body>
</html>