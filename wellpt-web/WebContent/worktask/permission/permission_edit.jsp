<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i> 权限设置
				</h2>
				<div class="box-icon">
					 
				</div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" action="${ctx }/worktask/permission/save" method="post">
				<input type="hidden" name="uuid" value="${pojo.uuid }"/>
					<fieldset>
						<div class="control-group">
							<label class="control-label" for="prependedInput">模块管理者</label>
							<div class="controls">
								<div class="input-prepend">
									<input name="adminNames" id="adminNames"
										value="${pojo.adminNames }" size="16" type="text"> <input
										type="hidden" name="adminIds" id="adminIds"
										value="${pojo.adminIds }" />
								</div>

							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInput">模块阅读者</label>
							<div class="controls">
								<div class="input-append">
									<div class="input-prepend">
										<input name="readerNames" id="readerNames"
											value="${pojo.readerNames }" size="16" type="text"> <input
											type="hidden" name="readerIds" id="readerIds"
											value="${pojo.readerIds }" />
									</div>

								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedPrependedInput">启用</label>
							<div class="controls">
								<div class="input-prepend input-append">
									<label class="checkbox inline"> <input type="checkbox"
										<c:if test="${pojo.planYear==1 }">checked</c:if> id="planYear"
										name="planYear" value="1"> 年度计划
									</label> <label class="checkbox inline"> <input type="checkbox"
										<c:if test="${pojo.planSeason==1 }">checked</c:if>
										id="planSeason" name="planSeason" value="1"> 季度计划
									</label> <label class="checkbox inline"> <input type="checkbox"
										<c:if test="${pojo.planMonth==1 }">checked</c:if>
										id="planMonth" name="planMonth" value="1"> 月计划
									</label><label class="checkbox inline"> <input type="checkbox"
										<c:if test="${pojo.planWeek==1 }">checked</c:if> id="planWeek"
										name="planWeek" value="1"> 周计划
									</label><label class="checkbox inline"> <input type="checkbox"
										<c:if test="${pojo.recordState==1 }">checked</c:if>
										id="recordState" name="recordState" value="1"> 日志
									</label>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInputButton">年度计划使用人</label>
							<div class="controls">
								<div class="input-append">
									<textarea id="yearUsemanNames" name="yearUsemanNames"
										style="width: 95%">${pojo.yearUsemanNames }</textarea>
									<input type="hidden" name="yearUsemanIds" id="yearUsemanIds"
										value="${pojo.yearUsemanIds }" />
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInputButton">季度计划使用人</label>
							<div class="controls">
								<div class="input-append">
									<textarea id="seasonUsemanNames" name="seasonUsemanNames"
										style="width: 95%">${pojo.seasonUsemanNames }</textarea>
									<input type="hidden" name="seasonUsemanIds"
										value="${pojo.seasonUsemanIds }" id="seasonUsemanIds" />
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInputButton">月度计划使用人</label>
							<div class="controls">
								<div class="input-append">
									<textarea id="monthUsemanNames" name="monthUsemanNames"
										style="width: 95%">${pojo.monthUsemanNames }</textarea>
									<input type="hidden" name="monthUsemanIds" id="monthUsemanIds"
										value="${pojo.monthUsemanIds }" />
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInputButton">周计划使用人</label>
							<div class="controls">
								<div class="input-append">
									<textarea id="weekUsemanNames" name="weekUsemanNames"
										style="width: 95%">${pojo.weekUsemanNames }</textarea>
									<input type="hidden" name="weekUsemanIds" id="weekUsemanIds"
										value="${pojo.weekUsemanIds }" />
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="appendedInputButton">日志使用人</label>
							<div class="controls">
								<div class="input-append">
									<textarea id="recordUsemanNames" name="recordUsemanNames"
										style="width: 95%">${pojo.recordUsemanNames }</textarea>
									<input type="hidden" name="recordUsemanIds"
										value="${pojo.recordUsemanIds }" id="recordUsemanIds" />
								</div>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="appendedInputButtons">任务提醒</label>
							<div class="controls">
								任务到前
								<div class="input-append"> 
									<input type="text" name="taskAlarmDate"  style="width:30px" maxlength="20"
										value="${pojo.taskAlarmDate }" id="recordUsemanIds" />
								</div>
								天开始提醒，提醒
								<div class="input-append">
									<input type="text" name="taskAlarmTimes" size="5" style="width:30px" maxlength="20"
										value="${pojo.taskAlarmTimes }" id="recordUsemanIds" />次<br />
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">提醒对象</label>
							<div class="controls">
								<label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.taskDutyState==1 }">checked</c:if>
									id="taskDutyState" name="taskDutyState" value="1"> 责任人
								</label> <label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.taskSupervisiorState==1 }">checked</c:if>
									id="taskSupervisiorState" name="taskSupervisiorState" value="1">
									督办人
								</label> <label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.taskSharerState==1 }">checked</c:if>
									id="taskSharerState" name="taskSharerState" value="1">
									共享者
								</label>&nbsp;&nbsp;注:如果责任人，督办人，共享者都未选中表示不提醒。

							</div>
						</div>
						<div class="control-group">
							<label class="control-label">提醒方式</label>
							<div class="controls">
								<label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.alarmOnlineState==1 }">checked</c:if>
									id="alarmOnlineState" name="alarmOnlineState" value="1">
									在线消息
								</label> <label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.alarmEmailState==1 }">checked</c:if>
									id="alarmEmailState" name="alarmEmailState" value="1">
									邮件
								</label> <label class="checkbox inline"> <input type="checkbox"
									<c:if test="${pojo.alarmSmsState==1 }">checked</c:if>
									id="alarmSmsState" name="alarmSmsState" value="1"> 手机短信
								</label>&nbsp;&nbsp;注:如果在线消息，邮件，手机短信都未选中表示不提醒
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">日志提醒</label>
							<div class="controls">
								<select name="recordAlarmState">
									<option value="1" <c:if test="${pojo.recordAlarmState==1 }">selected</c:if>>提醒</option>
									<option value="2" <c:if test="${pojo.recordAlarmState==2 }">selected</c:if>>不提醒</option>
								</select>

							</div>
						</div>
						<div class="control-group">
							<label class="control-label">已完成数据保留时间</label>
							<div class="controls">
								<input type="text" maxlength="2" name="keepMonth" style="width:30px" id="keepMonth" value="${pojo.keepMonth }"/>个月注:保留时间不能超过12个月，保留时间为零表示不归档
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" id="submitBtn" class="btn btn-primary">保存</button>
							<button type="reset" class="btn">取消</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
		<!--/span-->

	</div>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script src="${ctx}/resources/jBox/jquery.jBox.src.js"
		type="text/javascript"></script>
	<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$.jgrid.no_legacy_api = true;
		$.jgrid.useJSON = true;
		// 加载全局国际化资源
		I18nLoader.load("/resources/pt/js/global");
		// 加载动态表单定义模块国际化资源
		I18nLoader.load("/resources/worktask/js/permission/editpage");
	</script>
	
<script type="text/javascript"
	src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script src="${ctx }/resources/worktask/js/permission/workplanpermission.js"></script>
	<div id="iframe_fix" class="clearfix block lines"
		style="height: 0px; width: 0px; visibility: hidden; clear: both;"></div>

</body>
</html>