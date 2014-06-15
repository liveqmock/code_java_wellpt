<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/pt/css/form_head.css" />
<!-- <style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
</style> -->

</head>
<body>
	<div id="container">
	
		
		
		<form action="" id="work_hour_form" class="work_hour_form" enctype='multipart/form-data'>
			
			<div id="dyform">
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-1">工作日设置</a></li>
					</ul> -->
					
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">工作日设置</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<div id="tabs-1">
						<input type="hidden" id="uuid" name="uuid" />
						<table class="stab">
						<c:forEach items="${workDayList}" var="workday"  varStatus="status" begin="0" end="0" >
							<tr>
								<td><label>工作日起止时间</label></td>
								<td>&nbsp;上午：<input id="fromTime1"  name="fromTime1" type="text" value="${workday.fromTime1 }" style="width:100px;"  onfocus="WdatePicker({dateFmt:'H:mm'})" class="Wdate" />&nbsp;至<input onfocus="WdatePicker({dateFmt:'H:mm'})" class="Wdate" id="toTime1" name="toTime1" type="text" value="${workday.toTime1 }"  style="width:100px;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下午：<input onfocus="WdatePicker({dateFmt:'H:mm'})" class="Wdate" id="fromTime2" name="fromTime2" type="text"  value="${workday.fromTime2 }" style="width:100px;" />&nbsp;至<input onfocus="WdatePicker({dateFmt:'H:mm'})" class="Wdate" id="toTime2" name="toTime2" type="text" value="${workday.toTime2 }"  style="width:100px;" /></td>
								<td></td>
							</tr>
						</c:forEach>
							
							
							<tr>
								<td><label>每周工作日</label></td>
								<td>
								<c:forEach items="${workDayList}" var="workday">
								
									 <c:choose>
									  	<c:when test="${workday.code == 'MON' }">
									  		<input id="workday_name_MON"  type="checkbox" value="MON" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_MON">星期一</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'TUE' }">
									  		<input id="workday_name_TUE"  type="checkbox" value="TUE" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_TUE">星期二</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'WED' }">
									  		<input id="workday_name_WED"  type="checkbox" value="WED" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_WED">星期三</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'THU' }">
									  		<input id="workday_name_THU"  type="checkbox" value="THU" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_THU">星期四</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'FRI' }">
									  		<input id="workday_name_FRI"  type="checkbox" value="FRI"  <c:if test="${workday.isWorkday}">checked="checked"</c:if>/><label for="workday_name_FRI">星期五</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'SAT' }">
									  		<input id="workday_name_SAT"  type="checkbox" value="SAT" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_SAT">星期六</label>
									  	</c:when>
									  </c:choose>
									 <c:choose>
									  	<c:when test="${workday.code == 'SUN' }">
									  		<input id="workday_name_SUN"  type="checkbox" value="SUN" <c:if test="${workday.isWorkday}">checked="checked"</c:if> /><label for="workday_name_SUN">星期日</label> 
									  	</c:when>
									  </c:choose>
								</c:forEach>
								</td>
								<td></td>
							</tr>
							
							
							
						</table>
					</div>
				</div>
				
				
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-2">每年度固定节假日</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">每年度固定节假日</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-2">
						<div class="btn-group">
							<button id="fixed_holidays_btn_add" type="button" class="btn">新 增</button>
							<button id="fixed_holidays_btn_del" type="button" class="btn">删除</button>
						</div>
						<table id="fixed_holidays_list"></table>
						<br />
					</div>
				</div>
				
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-2">其他特殊节假日</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">其他特殊节假日</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
					<div id="tabs-2">
						<div class="btn-group">
							<button id="special_holidays_btn_add" type="button" class="btn">新 增</button>
							<button id="special_holidays_btn_del" type="button" class="btn">删除</button>
						</div>
						<table id="special_holidays_list"></table>
						<br />
					</div>
				</div>
				
				<div class="tabs">
					<!-- <ul>
						<li><a href="#tabs-2">补班日期</a></li>
					</ul> -->
					<div class="dytable_form">
						<div class="post-sign">
							<div class="post-detail">
								<table>
									<tbody>
										<tr class="title">
											<td class="Label" colspan="4">补班日期</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div id="tabs-2">
						<div class="btn-group">
							<button id="make_up_btn_add" type="button" class="btn">新 增</button>
							<button id="make_up_btn_del" type="button" class="btn">删除</button>
						</div>
						<table id="make_up_list"></table>
						<br />
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
				<br />
				
				</div>
			</form>
		</div>


	<!-- Project -->
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
<%-- 	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui-timepicker-addon.js"></script> --%>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/basicdata/workhour/workhour.js"></script>
</body>
</html>