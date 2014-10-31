<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	.schedule2_left{
		width: 25%;
	}
	.schedule2_right{
		width: 75%;
	}
	.schedule2_left_table_content2_td{
		height: 600px;
	}
	.color_1{
		color: black;
	}
	.color_2{
		color: gray;
	}
	.as_day{
		color:blue;
	}
</style>
</c:if >
</head>
<body style="width: 100%; height: 100%; padding: 0px; margin: 0px;">
<div class="schedule_person_list2 schedule_css">
<c:if test="${addCss=='yes'}">
	<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
</c:if>
<input type="hidden" value="${addCss}" id="addCss"/>
<a href="#" style="display: none;" id="showHref">test</a>
<%-- <input type="hidden" id="contextPath" value="${ctx}"></input> --%>
<div id="toolbar">
<table width="100%">
	<tr>
		<td align="left"><!--		<button onclick="openLayer('${now2 }' ,'wuzq','test_con3');" >+</button>-->
		<!--		<input type="text" size="7" name="firstday" value="${firstday }"/>-->
		<button class="go_taday" ldate=${now } mtype=${mtype }
			><spring:message code="schedule.info.today" /></button>
		</td>
		<c:forEach items="${days }" var="rrrow">
		<c:if test="${rrrow.inMonth!=0 }">
		<c:if test="${rrrow.sdate==ldate}">
		<td id="${rrrow.sdate }1" align="center" class="visi2">
		</c:if>
		<c:if test="${rrrow.sdate!=ldate}">
		<td id="${rrrow.sdate }1" style="display: none;" align="center">
		</c:if>
		<c:if test="${rrrow.preDayBean!=null}">
		<a class="s_prev_day" href="#" onclick="changeDay2('${rrrow.sdate }','${rrrow.preDayBean.displayDay}','${rrrow.preDayBean.sdate }','${rrrow.preDayBean.display2Day }','${rrrow.preDayBean.fullChinaDay }',this);"><</a> 
		</c:if>
		<c:if test="${rrrow.preDayBean==null }">
		<a class="s_prev_day" href="#"><</a> 
		</c:if>
		<label id="lldate" class="s_taday"> ${rrrow.display2Day }</label> <input type="hidden"
			size="7" id="ldate" name="ldate" value="${rrrow.sdate }" /> 
			
			<c:if test="${rrrow.nextDayBean!=null }">
		<a class="s_next_day" href="#" onclick="changeDay2('${rrrow.sdate }','${rrrow.nextDayBean.displayDay}','${rrrow.nextDayBean.sdate }','${rrrow.nextDayBean.display2Day }','${rrrow.nextDayBean.fullChinaDay }',this);">></a>
		</c:if>
		<c:if test="${rrrow.nextDayBean==null }">
		<a class="s_next_day" href="#">></a> 
		</c:if>	
			
			</td>
			</c:if>
			</c:forEach>
		<td align="right">
		<button class="as_month" ldate=${ldate } mtype=${mtype }
			><spring:message code="schedule.info.month" /></button>
		<button class="as_day"  ldate=${ldate } mtype=${mtype }
			><spring:message code="schedule.info.day" /></button>
		</td>
	</tr>
</table>
</div>
<div class="content">
<table width="100%"  border="2">
	<tr valign="top">
		<td class="schedule2_left" valign="top">
		<table class="schedule2_left_table">
			<tr>
				<td height="150">
				<div class="schedule2_left_table_content1">
				<font size="7"><strong><label
					id="displayDay"><c:out value="${day.displayDay }" /></label></strong></font><br />
				<label id="display2Day"><c:out value="${day.display2Day }" /></label><c:if test="${addCss!='yes'}"><br /></c:if>
				<label id="fullChinaDay"><c:out value="${day.fullChinaDay }" /></label>
				</td>
				</div>
			</tr>
			<tr>
				<td class="schedule2_left_table_content2_td" valign="top">
				<div class="schedule2_left_table_content2">
					<div class="schedule2_left_table_content2_h1">
					<a href="#" class="second_prev_moth" ldate="${ldate }" mtype="${mtype }"></a>
					<label>${lldate }</label>
					<a href="#" class="second_next_moth" ldate="${ldate }" mtype="${mtype }" ></a>
					</div>
					<div class="schedule2_left_table_content2_h2">
					<table class="schedule2_left_table_content2_h2_table">
						<tr class="schedule2_left_table_content2_h2_table_title">
							<td ><spring:message code="schedule.info.week11" /></td>
							<td><spring:message code="schedule.info.week22" /></td>
							<td><spring:message code="schedule.info.week33" /></td>
							<td><spring:message code="schedule.info.week44" /></td>
							<td><spring:message code="schedule.info.week55" /></td>
							<td class="weekend"><spring:message code="schedule.info.week66" /></td>
							<td class="weekend"><spring:message code="schedule.info.week77" /></td>
						</tr>
						<tr class="schedule2_left_table_content2_h2_table_ct">
							<c:forEach items="${days }" var="row">
	
								<c:if test="${row.sdate==now&&row.inMonth!=0 }">
									<td class="today_td" onclick="changeDay('${row.displayDay}','${row.sdate }','${row.display2Day }','${row.fullChinaDay }');">
								</c:if>
								<c:if test="${row.sdate==now&&row.inMonth==0 }">
									<td onclick="changeDay('${row.displayDay}','${row.sdate }','${row.display2Day }','${row.fullChinaDay }');">
								</c:if>
								<c:if test="${row.sdate!=now }">
									<td onclick="changeDay('${row.displayDay}','${row.sdate }','${row.display2Day }','${row.fullChinaDay }');">
								</c:if>
<%-- 								<c:if test="${row.inMonth==0 }"> --%>
<!-- 									<font class="color_1"> -->
<!-- 									<span  -->
<%-- 									onclick="changeDay('${row.displayDay}','${row.sdate }','${row.display2Day }','${row.fullChinaDay }');"><c:out --%>
<%-- 									value="${row.displayDay }"></c:out></span> --%>
<!-- 									</font> -->
<%-- 								</c:if> --%>
								<c:if test="${row.inMonth!=0 }">
									<font class="color_2" >
									<span >
									<c:out value="${row.displayDay }"></c:out>
									</span>
									<c:if test="${fn:length(sche[row.sdate]) >0&&row.displayDay!=''}">
										<div class="rc"></div>
									</c:if>
									</font>
								</c:if>
								</td>
						<c:if test="${row.count%7==0 }">
							</tr>
							<tr class="schedule2_left_table_content2_h2_table_ct">
						</c:if>
							</c:forEach>
						</tr>
					</table>
					</div>
				</div>
				</td>
			</tr>
		</table>

		</td>
		<td class="schedule2_right">
		<table id="mytb" width="100%">
			<c:forEach items="${days }" var="row1" varStatus="var">
			<c:if test="${row1.inMonth!=0 }"> 
			
				<c:if test="${fn:length(sche[row1.sdate])>0}">
					<tr valign="top" id="${row1.sdate }one" class="day_detail_title" style="display: block">
				</c:if>
				<c:if test="${fn:length(sche[row1.sdate])<=0}">
					<tr valign="top" id="${row1.sdate }one" class="day_detail_title" style="display: none">
				</c:if>

				<td><c:out value="${row1.display2Day }"></c:out></td>
				</tr>
				<c:if test="${fn:length(sche[row1.sdate])>0}">
					<tr valign="top" id="${row1.sdate }two" class="day_detail_content" style="display: block">
				</c:if>
				<c:if test="${fn:length(sche[row1.sdate])<=0}">
					<tr valign="top" id="${row1.sdate }two" class="day_detail_content" style="display: none">
				</c:if>
				<td class="day_detail_content_td"><table width="100%" id="${row1.sdate}fourtable"><c:forEach items="${sche[row1.sdate] }" var="row2">
						<tr width="100%">
							<td width="15%"><a href="#"
								onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','4','','','${row2.tipMethod }','${row2.isComplete }','${row1.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:if test="${row2.startTime=='' or row2.startTime==null}"><spring:message code="schedule.info.allDay" /> </c:if><c:if test="${row2.startTime!='' and row2.startTime!=null}"><c:out value="${row2.startTime }"/>:<c:out value="${row2.startTime2 }"/> </c:if>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','4','','','${row2.tipMethod }','${row2.isComplete }','${row1.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.address)>20 }">
									<c:out value="${fn:substring(row2.address,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.address }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.address)>20 }">
									<c:out value="${fn:substring(row2.address,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.address }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="25%"><a href="#"
								onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','4','','','${row2.tipMethod }','${row2.isComplete }','${row1.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.scheduleName)>10 }">
									<c:out value="${fn:substring(row2.scheduleName,0,10) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.scheduleName }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.scheduleName)>10 }">
									<c:out value="${fn:substring(row2.scheduleName,0,10) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.scheduleName }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','4','','','${row2.tipMethod }','${row2.isComplete }','${row1.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.leaderNames)>20 }">
									<c:out value="${fn:substring(row2.leaderNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.leaderNames }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.leaderNames)>20 }">
									<c:out value="${fn:substring(row2.leaderNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.leaderNames }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
							<td width="20%"><a href="#"
								onclick="openScheduleDialog('','','${mtype }','${ldate }','${userno }','2','4','','','${row2.tipMethod }','${row2.isComplete }','${row1.sdate }','${row2.uuid }','${row2.scheduleName }','${row2.address }','${row2.dstartDate }','${row2.startTime }','${row2.dendDate }','${row2.endTime }','${row2.isView }','${row2.status }','${row2.leaderNames }','${row2.leaderIds }','${row2.pleases }','${row2.pleaseIds }','${row2.views }','${row2.viewIds }','${row2.color}','${row2.tip }','${row2.tipDate }','${row2.tipTime }','${row2.repeat }','${row2.startTime2 }','${row2.endTime2 }','${row2.tipTime2 }','${row2.creators }','${row2.creatorIds }');">
							<font color="${row2.color }">
							<c:if test="${row2.isComplete=='1' }">
							<strike>
							<c:choose>
								<c:when test="${fn:length(row2.allNames)>20 }">
									<c:out value="${fn:substring(row2.allNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.allNames }" />
								</c:otherwise>
							</c:choose>
							</strike>
							</c:if>
							<c:if test="${row2.isComplete!='1' }">
							<c:choose>
								<c:when test="${fn:length(row2.allNames)>20 }">
									<c:out value="${fn:substring(row2.allNames,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${row2.allNames }" />
								</c:otherwise>
							</c:choose>
							</c:if>
							</font> </a></td>
						</tr>
					
				</c:forEach>
				</table>
				
				
				
				<div style="width: 100%; overflow: hidden;">
				<c:if test="${fn:length(sche[row1.sdate])>0}">
				<div id="${row1.sdate }four" class="visi">
				</c:if>
				<c:if test="${fn:length(sche[row1.sdate])<=0}">
				<div id="${row1.sdate }four">
				</c:if>
				<font size="1"><a href="#"
					onclick="openScheduleNewDialog('','','','','2','4','','','${creators}','${creatorIds}','${row1.sdate }','${ldate }','${mtype }','${now }');"><spring:message code="schedule.info.newSchedule" /></a></font></div>
				</div>
				</td>
				</tr>
				
				
				
				
</c:if>
			</c:forEach>

		</table>
		</td>
	</tr>
</table>
</div>

<div class="view_foot"></div>

<div id="dialogModule" title="" style="padding:0;margin:0; display:none;">
<div class="dialogcontent"></div>
</div>

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
	
	<script type="text/javascript">
	
	
	
	
	
	//按日显示时，日变更时，更新其他展现元素
	function changeDay2(ydate,displayDay, sdate, display2Day, fullChinaDay,obj) {
		//上下天不是本月的日期时间无效
		if($(obj).attr("class")=="s_prev_day"){
			if($(obj).parent().prev().attr("align")!="center"){
				return false;
			}
		}else if($(obj).attr("class")=="s_next_day"){
			if($(obj).parent().next().attr("align")!="center"){
				return false;
			}
		}
		var showdate=sdate+"1";
		var showdate2=ydate+"1";
		//$("#lldate").html(display2Day);
		$("#"+showdate).show();
		$("#"+showdate).addClass("visi2");
		$("#"+showdate2).hide();
		$("#"+showdate2).removeClass("visi2");
		$("#displayDay").html(displayDay);
		$("#display2Day").html(display2Day);
		$("#fullChinaDay").html(fullChinaDay);
		
		if ($(".visi").length > 0) {
			var i = 1;
			if($(".visi").length == 1) {  //点击事件之前有且仅有一个 '新建事件'在页面上
				var id = $(".visi").attr("id");
				var id2 = "#"+ id + "table";
				if($(id2).children().length<=0) { //把one two隐藏掉
					 var id3 = id.substring(0,10) + "one";
					var id4 = id.substring(0,10) + "two";
					 //$(id3).attr("display","none");
 					//$(id4).attr("display","none"); 
 					$("tr[id='"+id3+"']").css("display","none");
 					$("tr[id='"+id4+"']").css("display","none");
				}
			}
			
			$(".visi").each(function() {
				var trid = $(this).attr("id");
				if (trid != (sdate + 'four')) {
						$(this).hide();
						$(this).removeClass("visi");
				}
			});
		}
		
		$("#mytb tr").each(function() {
			var trid = $(this).attr("id");
			if (trid != "undefined" && trid != null) {
				if (trid == (sdate + 'one') || trid == (sdate + 'two')) {
					$(this).show();
					var s=sdate+"four";
					$("#"+s).show();
					$("#"+s).addClass("visi");
				}
			}
		});
		
		//先去掉所有加上了背景色的之前被选中的div
		$(".day_detail_content").each(function(){
			$(this).css("background","none repeat scroll 0 0 rgba(0, 0, 0, 0)");
		}); 
		$("#"+sdate+"two").css("background-color", $("#"+sdate+"one").css("background-color"));
		
		var mon = "<span id='mon'>gohref</span>";
		$("#showHref").attr("href","#"+sdate+"one");
		$("#showHref").html(mon);
		$("#mon").trigger("click"); 
		
	}
	
	//按日显示时，日变更时，更新其他展现元素
	function changeDay(displayDay, sdate, display2Day, fullChinaDay) {
		if ($(".visi2").length > 0) {
			$(".visi2").each(function() {
					$(this).hide();
					$(this).removeClass("visi2");
			});
		}
		var showdate=sdate+"1";
		$("#"+showdate).show();
		$("#"+showdate).addClass("visi2");
		//$("#lldate").html(display2Day);
		$("#displayDay").html(displayDay);
		$("#display2Day").html(display2Day);
		$("#fullChinaDay").html(fullChinaDay);
		
		if ($(".visi").length > 0) {
			var i = 1;
			if($(".visi").length == 1) {  //点击事件之前有且仅有一个 '新建事件'在页面上
				var id = $(".visi").attr("id");
				var id2 = "#"+ id + "table";
				if($(id2).children().length<=0) { //把one two隐藏掉
					var id3 = id.substring(0,10) + "one";
					var id4 = id.substring(0,10) + "two";
 					$("tr[id='"+id3+"']").css("display","none");
 					$("tr[id='"+id4+"']").css("display","none")
				}
			}
			$(".visi").each(function() {
				var trid = $(this).attr("id");
				if (trid != (sdate + 'four')) {
						$(this).hide();
						$(this).removeClass("visi");

				}
			});
		}
		
		$("#mytb tr").each(function() {
			var trid = $(this).attr("id");
			if (trid != "undefined" && trid != null) {
				if (trid == (sdate + 'one') || trid == (sdate + 'two')) {
					$(this).show();
					var s=sdate+"four";
					$("#"+s).show();
					$("#"+s).addClass("visi");
				}
				//if(trid!=(sdate+'one')&&trid!=(sdate+'two')){
				//$(this).hide();
				//}
			}
		});
		
		
		//先去掉所有加上了背景色的之前被选中的div
		$(".day_detail_content").each(function(){
			$(this).css("background","none");
		});
		
		var count = 0;
		$(".day_detail_title").each(function(){
			if($(this).css("display")=="block"){
				if(count%2==0){
					$(this).css("background","#D9E2EE");
					if($(this).next().find("div .visi").length>0){
						$(this).next().css("background","#D9E2EE"); //被选中的div加上背景色
					}
				}else{
					$(this).css("background","#F3EDDD");
					if($(this).next().find("div .visi").length>0){
						$(this).next().css("background","#F3EDDD"); //被选中的div加上背景色
					}
				}
				count++;
			}
		});
		
	 	var mon = "<span id='mon'>gohref</span>";
		$("#showHref").attr("href","#"+sdate+"one");
		$("#showHref").html(mon);
		$("#mon").trigger("click"); 
		
		//window.location.hash = sdate+"one";
		
	}
	//今天
	$(".go_taday").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		var addCss = $("#addCss").val();
		if(addCss=='yes'){
			$.ajax({
				type:"post",
				async:false,
				data : {"stype":0,"ldate":ldate,"mtype":mtype,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list2").parent().html(result);
					$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				}
			});
		}else{
			window.location.href='${ctx}/schedule/person_schedule2.action?ldate='+ldate+'&stype=0&mtype='+mtype;
		}
	});
	
	//月
	$(".as_month").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		var addCss = $("#addCss").val();
		if(addCss=='yes'){
			$.ajax({
				type:"post",
				async:false,
				data : {"ldate":ldate,"mtype":mtype,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule.action",
				success:function(result){
					$(".schedule_person_list2").parent().html(result);
					$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				}
			});
		}else{
			window.location.href='${ctx}/schedule/person_schedule.action?ldate='+ldate+'&mtype='+mtype;
		}
	});
	//日
	$(".as_day").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		var addCss = $("#addCss").val();
		if(addCss=='yes'){
			$.ajax({
				type:"post",
				async:false,
				data : {"mtype":mtype,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list2").parent().html(result);
					$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				}
			});
		}else{
			window.location.href='${ctx}/schedule/person_schedule2.action?ldate='+ldate+'&mtype='+mtype;
		}
	});
	$(".second_prev_moth").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		var addCss = $("#addCss").val();
		if(addCss=='yes'){
			$.ajax({
				type:"post",
				async:false,
				data : {"mtype":mtype,"ldate":ldate,"stype":1,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list2").parent().html(result);
					$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				}
			});
		}else{
			window.location.href='${ctx}/schedule/person_schedule2.action?ldate='+ldate+'&mtype='+mtype+'&stype=1';
		}
	});
	$(".second_next_moth").click(function(){
		var ldate = $(this).attr("ldate");
		var mtype = $(this).attr("mtype");
		var addCss = $("#addCss").val();
		if(addCss=='yes'){
			$.ajax({
				type:"post",
				async:false,
				data : {"mtype":mtype,"ldate":ldate,"stype":2,"requestType":"cms"},
				url:"${ctx}/schedule/person_schedule2.action",
				success:function(result){
					$(".schedule_person_list2").parent().html(result);
					$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
				}
			});
		}else{
			window.location.href='${ctx}/schedule/person_schedule2.action?ldate='+ldate+'&mtype='+mtype+'&stype=2';
		}
	});
	</script>
</div>
</body>
</html>