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
<body>
	<div class="ui-layout-west">
		<div>
			<div class="btn-group btn-group-top">
				<!-- 				<label>登录名</label><input id="query_loginName" name="query_loginName" /> -->
				<div class="query-fields">
					<input id="query_job_details" name="query_job_details" />
					<button id="btn_query" type="button" class="btn">查询</button>
				</div>
				<button id="btn_add" type="button" class="btn">新 增</button>
				<button id="btn_start" type="button" class="btn">启动</button>
				<button id="btn_pause" type="button" class="btn">暂停</button>
				<button id="btn_resume" type="button" class="btn">恢复</button>
				<button id="btn_stop" type="button" class="btn">停止</button>
				<button id="btn_del_all" type="button" class="btn">删除</button>
			</div>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div>
			<form action="" id="job_details_form" name="job_details_form">
				<div class="tabs">
					<ul>
						<li><a href="#tabs-1">基本信息</a></li>
					</ul>
					<input type="hidden" id="uuid" name="uuid" />
					<div id="tabs-1">
						<table>
							<tr>
								<td style="width: 75px;"><label for="name">名称</label></td>
								<td><input id="name" name="name" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="id">ID</label></td>
								<td><input id="id" name="id" type="text" class="full-width"></td>
								<td></td>
							</tr>
							<tr>
								<td><label for="code">编号</label></td>
								<td><input id="code" name="code" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<td><label>类名</label></td>
								<td><input id="jobClassName" name="jobClassName"
									type="text" class="full-width" /></td>
								<td></td>
							</tr>
							<!-- 							<tr> -->
							<!-- 								类型(定时、临时) -->
							<!-- 								<td><label>类型</label></td> -->
							<!-- 								<td><input id="type_timing" name="type" type="radio" -->
							<!-- 									value="timing" checked="checked" /><label for="type_timing">定时任务</label> -->
							<!-- 									<input id="type_temporary" name="type" type="radio" -->
							<!-- 									value="temporary" /><label for="type_temporary">临时任务</label></td> -->
							<!-- 								<td></td> -->
							<!-- 							</tr> -->
							<tr class="type-timing" style="display: none;">
								<td><label for="expression">CRON表达式</label></td>
								<td><input id="expression" name="expression" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr>
								<!-- 类型(定时、临时) -->
								<td><label>定时方式</label></td>
								<td><input id="timingMode_day" name="timingMode"
									type="radio" value="1" checked="checked" /><label
									for="timingMode_day">每天</label><input id="timingMode_week"
									name="timingMode" type="radio" value="2" checked="checked" /><label
									for="timingMode_week">每周</label><input id="timingMode_month"
									name="timingMode" type="radio" value="3" checked="checked" /><label
									for="timingMode_month">每月</label><input id="timingMode_season"
									name="timingMode" type="radio" value="4" checked="checked" /><label
									for="timingMode_season">每季</label><input id="timingMode_year"
									name="timingMode" type="radio" value="5" checked="checked" /><label
									for="timingMode_year">每年</label><input id="timingMode_interval"
									name="timingMode" type="radio" value="6" checked="checked" /><label
									for="timingMode_interval">间隔时间</label></td>
								<td></td>
							</tr>
							<tr class="timingMode-week">
								<td><label>重复日期</label></td>
								<td><input id="repeatDay_SUN" name="repeatDayOfWeek"
									type="checkbox" value="SUN" /><label for="repeatDay_SUN">星期日</label>
									<input id="repeatDay_MON" name="repeatDayOfWeek"
									type="checkbox" value="MON" /><label for="repeatDay_MON">星期一</label>
									<input id="repeatDay_TUE" name="repeatDayOfWeek"
									type="checkbox" value="TUE" /><label for="repeatDay_TUE">星期二</label>
									<input id="repeatDay_WED" name="repeatDayOfWeek"
									type="checkbox" value="WED" /><label for="repeatDay_WED">星期三</label>
									<input id="repeatDay_THU" name="repeatDayOfWeek"
									type="checkbox" value="THU" /><label for="repeatDay_THU">星期四</label>
									<input id="repeatDay_FRI" name="repeatDayOfWeek"
									type="checkbox" value="FRI" /><label for="repeatDay_FRI">星期五</label>
									<input id="repeatDay_SAT" name="repeatDayOfWeek"
									type="checkbox" value="SAT" /><label for="repeatDay_SAT">星期六</label></td>
								<td></td>
							</tr>
							<tr class="timingMode-month">
								<td><label>重复日期</label></td>
								<td><c:forEach var="dayOfMonth" begin="1" end="31">
										<input id="repeatDay_${dayOfMonth}" name="repeatDayOfMonth"
											type="checkbox" value="${dayOfMonth}" />
										<label for="repeatDay_${dayOfMonth}"><c:if
												test="${dayOfMonth < 10}">0</c:if>${dayOfMonth}</label>
									</c:forEach> <input id="repeatDay_LW" name="repeatDayOfMonth"
									type="checkbox" value="LW" /> <label for="repeatDay_LW">每月最后一天</label></td>
								<td></td>
							</tr>
							<tr class="timingMode-season">
								<td class="align-top"><label>重复日期</label></td>
								<td>添加日期：<select name="repeatDayOfSeason"
									style="width: 50px;"><option>第 1</option>
										<option>第 2</option>
										<option>第 3</option></select>个月， <select style="width: 50px;"><option>第
											1</option>
										<option>第 2</option>
										<option>第 3</option></select>日&nbsp;&nbsp;
									<button id="btn_add_item" type="button" class="btn">添加</button>
									<button id="btn_del_item" type="button" class="btn">删除</button>
									<br /> <select multiple="multiple"
									style="height: 200px; width: 100%;">
								</select>
								</td>
								<td></td>
							</tr>
							<tr class="timingMode-year">
								<td class="align-top"><label>重复日期</label></td>
								<td>添加日期：<select name="repeatDayOfYear"
									style="width: 50px;"><option>01</option>
										<option>02</option>
										<option>03</option></select>个月， <select style="width: 50px;"><option>01</option>
										<option>02</option>
										<option>03</option></select>日&nbsp;&nbsp;
									<button id="btn_add_item" type="button" class="btn">添加</button>
									<button id="btn_del_item" type="button" class="btn">删除</button>
									<br /> <select multiple="multiple"
									style="height: 200px; width: 100%;">
								</select>
								</td>
								<td></td>
							</tr>
							<tr class="timingMode-day">
								<td><label for="timePoint">时间点</label></td>
								<td><input id="timePoint" name="timePoint" type="text"
									class="full-width" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
								<td></td>
							</tr>
							<tr class="timingMode-interval">
								<td><label for="repeatIntervalTime">间隔时间</label></td>
								<td><input id="repeatIntervalTime" name="repeatIntervalTime"
									type="text" class="full-width"
									onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" /></td>
								<td></td>
							</tr>
							<tr class="timingMode-interval">
								<td><label for="repeatCount">重复次数</label></td>
								<td><input id="repeatCount" name="repeatCount" type="text"
									class="full-width" /></td>
								<td></td>
							</tr>
							<tr class="type-temporary">
								<td><label for="startTime">开始时间</label></td>
								<td><input id="startTime" name="startTime" type="text"
									class="full-width"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								<td></td>
							</tr>
							<tr class="type-temporary">
								<td><label for="endTime">结束时间</label></td>
								<td><input id="endTime" name="endTime" type="text"
									class="full-width"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								<td></td>
							</tr>
							<tr>
								<td class="align-top"><label for="remark">备注</label></td>
								<td><textarea id="remark" name="remark" class="full-width"></textarea></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="btn-group btn-group-bottom">
					<button id="btn_save" type="button" class="btn">保存</button>
					<button id="btn_del" type="button" class="btn">删除</button>
				</div>
			</form>
		</div>
	</div>

	<!-- Project -->
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/utils/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/system_admin.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/task/job_details.js"></script>
</body>
</html>