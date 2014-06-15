<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
<!--
	var ctx = "${ctx}";
//-->
</script>
	<script src="${ctx}/resources/My97DatePicker/WdatePicker.js"
		type="text/javascript"></script>
<body>
	<div style="text-align: center;">
		<h1>我的周期性计划</h1>
	</div>
	<div class="row-fluid sortable">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-edit"></i>基本设计
				</h2>
				<div class="box-icon"></div>
			</div>
			<div class="box-content">
				<form class="form-horizontal" method="post"
					action="${ctx }/worktask/taskcycle/save.action">
					<input type="hidden" value="${pojo.uuid }" name="uuid" />
					<c:if test="${not empty pojo.createTime }">
						<input type="hidden" value="${pojo.creator }" name="creator" />
						<input type="hidden"
							value='<fmt:formatDate value="${pojo.createTime }" pattern="MM/dd/yyyy"/>'
							name="createTime" />
						<input type="hidden" value="${pojo.recVer }" name="recVer" />
					</c:if>
						<input type="hidden" value="${pojo.typeName }" name="typeName" id="typeName"/>
						<input type="hidden" value="${pojo.stateUuid }" name="stateUuid" />
						<input type="hidden" value="${pojo.stateName}" name="stateName" />
						<input type="hidden" value="1" name="state" />
					<fieldset>
						<div class="control-group">
							<label class="control-label" for="focusedInput">任务名称</label>
							<div class="controls">
								<input id="name" name="name" type="text" value="${pojo.name }">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="disabledInput">任务类别</label>
							<div class="controls">
								<select name="typeUuid" id="typeUuid">
									<c:forEach var="item" items="${dicts }">
										<option value="${item.uuid }"
											<c:if test="${pojo.typeUuid==item.uuid }">selected</c:if>>${item.name
											}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="optionsCheckbox2">计划时间</label>
							<div class="controls">
								<label class="checkbox"> 从<input type="text" size="12" maxlength="20" readonly="readonly"
									
									value="${pojo.timeBegin }" name="timeBegin" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
								</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="optionsCheckbox2">计划时间</label>
							<div class="controls">
								<label class="checkbox">  到<input id="timeAfter"
									type="text" name="timeAfter" value="${pojo.timeAfter }" maxlength="20"
									style="width: 50px" /> <span
												class="help-inline" id="timeAftererr"style="display:none;"> 请填入数字</span>
								</label> 
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="optionsCheckbox2">计划时间</label>
							<div class="controls">
								<label class="checkbox">   天后的<input type="text" name="timeEnd" maxlength="20"  
									size="12" value="${pojo.timeEnd }"  onFocus="WdatePicker({ dateFmt:' HH:mm ' })" />
								</label>
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label" for="inputWarning">重复方式</label>
							<div class="controls">
								<label class="checkbox inline"> <input name="reptyType"
									<c:if test="${pojo.reptyType!=2 }">checked</c:if> type="radio"
									id="reptyType1" value="1"> 按周重复
								</label> <label class="checkbox inline"> <input type="radio"
									<c:if test="${pojo.reptyType==2 }">checked</c:if>
									id="reptyType2" name="reptyType" value="2"> 按月重复
								</label>

							</div>
						</div>
						<div class="control-group error">
							<label class="control-label" for="inputError">重复时间</label> <input
								type="hidden" value="${pojo.reptyTime }" name="reptyTime"
								id="reptyTime" />
							<div class="controls">


								<div id="rpty1"
									<c:if test="${pojo.reptyType==2 }"> style="display:none;"</c:if>>
									<c:forEach var="item" begin="1" end="7">
										<c:set var="configkey" value="${item };" />
										<input name="reptyTime1" type="checkbox" value="${item }"
											<c:if test="${fn:contains(pojo.reptyTime,configkey) and pojo.reptyType!=2}">checked </c:if> />
												周<c:if test="${item==7 }">天</c:if>
										<c:if test="${item==1 }">一</c:if>
										<c:if test="${item==2 }">二</c:if>
										<c:if test="${item==3 }">三</c:if>
										<c:if test="${item==4 }">四</c:if>
										<c:if test="${item==5 }">五</c:if>
										<c:if test="${item==6 }">六</c:if>
									</c:forEach>
								</div>
								<div id="rpty2"
									<c:if test="${pojo.reptyType!=2 }"> style="display:none;"</c:if>>
									<c:forEach var="item" begin="1" end="32">
										<c:set var="configkey" value="${item };" />
										<input name="reptyTime2" type="checkbox" value="${item }"
											<c:if test="${fn:contains(pojo.reptyTime,configkey) and pojo.reptyType==2}">checked </c:if> />
										<c:if test="${item==32 }">每月最后一天</c:if>
										<c:if test="${item<32 }">${item }</c:if>

									</c:forEach>
								</div>
							</div>
						</div>
						<div class="control-group success">
							<label class="control-label" for="inputSuccess">如在周末</label>
							<div class="controls">

								<label class="checkbox inline"> <input
									<c:if test="${pojo.weekEndState==1 or empty pojo}">checked</c:if>
									name="weekEndState" type="radio" id="weekEndState" value="1">
									禁止移动
								</label> <label class="checkbox inline"> <input type="radio"
									<c:if test="${pojo.weekEndState==2 }">checked</c:if>
									id="weekEndState" name="weekEndState" value="2"> 删除
								</label> <label class="checkbox inline"> <input
									<c:if test="${pojo.weekEndState==3 }">checked</c:if>
									name="weekEndState" type="radio" id="weekEndState" value="3">
									转至本周五
								</label> <label class="checkbox inline"> <input type="radio"
									<c:if test="${pojo.weekEndState==4 }">checked</c:if>
									id="weekEndState" name="weekEndState" value="4"> 转至下周一
								</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="beginDate">开始日期</label>
							<div class="controls">
								<input type="text" class="input-xlarge datepicker" maxlength="20"
									id="beginDate" name="beginDate" onFocus="WdatePicker({dateFmt:'MM/dd/yyyy'})" 
									value="<fmt:formatDate value="${pojo.beginDate }" pattern="MM/dd/yyyy"/>"> <span
												class="help-inline" id="beginDateerr"style="display:none;"> 开始日期不能为空</span>
									
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="endDate">结束日期</label>
							<div class="controls">
								<input type="text" class="input-xlarge datepicker" id="endDate"
									name="endDate" maxlength="20"onFocus="WdatePicker({dateFmt:'MM/dd/yyyy'})" 
									value="<fmt:formatDate value="${pojo.endDate }" pattern="MM/dd/yyyy"/>"><span
												class="help-inline" id="endDateerr" style="display:none;"> 结束日期不能为空</span>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="sharernames">共享者</label>
							<div class="controls">
								<textarea id="sharernames" name="sharernames" rows="2" readonly="readonly"
									cols="180">${pojo.sharernames }</textarea>
								<input id="shareruuids" name="shareruuids" type="hidden"
									value="${pojo.shareruuids }">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="selectError2"> 备注</label>
							<div class="controls">
								<textarea class="cleditor" name="memo" id="memo" rows="3">${pojo.memo }</textarea>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" id="submitbtn" class="btn btn-primary">保存</button>
							<button class="btn" type="reset">取消</button>
						</div>
					</fieldset>
				</form>

			</div>
		</div>
</body>

<script type="text/javascript"
	src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>

<script type="text/javascript"
	src="${ctx}/resources/worktask/js/taskcycle/taskcycleedit.js"></script>
</html>