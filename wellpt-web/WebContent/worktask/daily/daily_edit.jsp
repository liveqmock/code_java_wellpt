<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
<script>
	var done_state = '${done_state}';
	var tasks = new Array();
	<c:forEach var="item" items="${details }" varStatus="i">
	tasks[${i.index}]='${item.taskUuid}';
	</c:forEach>
	$(function() {<c:forEach var="item" items="${details }" varStatus="i">
		appendTbody('${item.taskUuid}', '${item.taskName}', '${item.workcount}', '${item.selfEvaluate}', '${item.stateUuid}', '${item.stateName}','${item.memo}','${item.uuid}') ;
		</c:forEach>
	});
</script>
<body>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i> 工作日志
				</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" action="${ctx }/worktask/daily/save"
					method="post">
					<fieldset>
						<input type="hidden" value="${pojo.uuid }" name="uuid" />
						<c:if test="${not empty pojo.createTime }">
							<input type="hidden" value="${pojo.creator }" name="creator" />
							<input type="hidden"
								value='<fmt:formatDate value="${pojo.createTime }" pattern="MM/dd/yyyy HH:mm:ss"/>'
								name="createTime" />
							<input type="hidden" value="${pojo.recVer }" name="recVer" />
						</c:if>
						<div class="control-group">
							<label class="control-label" for="typeahead">任务名称 </label>
							<div class="controls">
								<select name="taskUuid" id="task">
									<c:forEach var="item" items="${tasks }">
										<option value="${item.uuid }">${item.taskName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="date01">状态</label>
							<div class="controls">
								<select name="stateUuid" id="stateUuid">
									<c:forEach var="item" items="${states }">
										<option value="${item.code }">${item.name }</option>
									</c:forEach>
								</select>
							</div>

						</div>

						<div class="control-group">
							<label class="control-label" for="fileInput">日期</label>
							<div class="controls">
								<input class="date" id="date" type="text" value="${today }"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="textarea2">责任人 </label>
							<div class="controls">

								<input class="dutyManName" id="dutyManName" type="text"
									value="${ud.username }" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fileInput">执行工作量</label>
							<div class="controls">
								<input class="workcount_main" id="workcount" type="text">个小时
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="textarea2">自评 </label>
							<div class="controls">
								<select name="selfEvaluate_main" id="selfEvaluate">

									<option value=""></option>
									<option value="优秀">优秀</option>
									<option value="达标">达标</option>
									<option value="未达标">未达标</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="textarea2">执行情况说明</label>
							<div class="controls">
								<input id="memo"></input>
							</div>
						</div>
						<div class="control-group">
							<div class="box span6">
								<div class="box-header well">
									<button type="button" id="addBtn" class="btn">添加</button>
									<button type="button" id="delBtn" class="btn">删除</button>
									<div class="box-icon"></div>
								</div>
								<div class="box-content">
									<table  class="table table-bordered">
										<thead>
											<tr>
												<th>任务名称</th>
												<th>任务状态</th>
											</tr>
										</thead>
										<tbody id="tbdy">
										</tbody>
									</table>

								</div>
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
		I18nLoader.load("/resources/worktask/js/daily/editpage");
	</script>
	<script src="${ctx }/resources/worktask/js/daily/workdaily.js"></script>
</body>
</html>