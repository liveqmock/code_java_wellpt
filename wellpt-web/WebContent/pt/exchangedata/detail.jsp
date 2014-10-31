<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>${bean.title}</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/chosen/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/exchange/sendData.css" />
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>${bean.title}</h2>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div id="mini_wf_opinion" style="margin-bottom: 2px;">
					</div>
					<div class="form_operate">
						<c:forEach var="btn" items="${bean.btns}">
							<button id="${btn.code}" name="${btn.code}" style="display: none">${btn.name}</button>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="form_content" style="display: none;" id="sendMode">
				<table cellpadding="2" cellspacing="2" class="tableForm" >
					<c:if test="${bean.sendNode=='examineFail'}"><!-- 发件 -->
						<tr class="odd">
							<td class="Label">不通过原因</td>
							<td>
			 					<span>${bean.examineFailMsg}</span>
			 				</td>
						</tr>
					</c:if>
					<tr class="odd">
						<td class="Label">发件单位</td>
						<td>
		 					<span id="sendUnitNames">${bean.fromUnitName}${bean.fromUserName}</span>
		 				</td>
					</tr>
					<c:if test="${not empty bean.allUnit}">
						<tr id="munit" class="odd">
							<td class="Label">收件单位</td>
							<td class="value" colspan="5">
			 					<span id="unitNames">${bean.allUnit}</span>
			 				</td>
						</tr>
					</c:if>
					
					<tbody>
						<c:if test="${not empty bean.cc}">
						<tr class="odd" id="mcc">
							<td class="Label">抄送</td>
							<td><span id="ccNames">${bean.cc}</span>
							</td>
						</tr>
						</c:if>
						<c:if test="${not empty bean.bcc}">
						<tr id="mbcc" class="odd">
							<td class="Label">密送</td>
							<td><span id="bccNames">${bean.bcc}</span>
							</td>
						</tr>
						</c:if>
					</tbody>
					<!-- 创建时间,发布时间 -->
						<c:if test="${empty bean.releaseTime}">
							<tr class="odd">
								<td class="Label">起草时间</td>
								<td>
				 					<span><fmt:formatDate value="${bean.draftTime}" pattern="yyyy-MM-dd HH:mm"/>(${bean.drafter})</span>
				 				</td>
							</tr>
						</c:if>
						<c:if test="${not empty bean.releaseTime}">
							<c:if test="${bean.releaseTime==bean.draftTime}">
								<tr class="odd">
									<td class="Label">发布时间</td>
									<td>
					 					<span><fmt:formatDate value="${bean.releaseTime}" pattern="yyyy-MM-dd HH:mm"/>(${bean.releaser})</span>
					 				</td>
								</tr>
							</c:if>
							<c:if test="${bean.releaseTime!=bean.draftTime}">
								<tr class="odd">
									<td class="Label">起草时间</td>
									<td>
					 					<span><fmt:formatDate value="${bean.draftTime}" pattern="yyyy-MM-dd HH:mm"/>(${bean.drafter})</span>
					 				</td>
								</tr>
								<tr class="odd">
									<td class="Label">发布时间</td>
									<td>
					 					<span><fmt:formatDate value="${bean.releaseTime}" pattern="yyyy-MM-dd HH:mm"/>(${bean.releaser})</span>
					 				</td>
								</tr>
							</c:if>
						</c:if>						
					<tr class="odd" style="display: none;" id="sendLimit">
						<td class="Label">上报逾期</td>
						<td>
		 					<span>${bean.sendLimitNum}工作日</span>
		 				</td>
					</tr>	
					
				</table>
			</div>
			
			<div class="form_content" style="display: none;" id="receiveMode"><!-- 收件 -->
				<table cellpadding="2" cellspacing="2" class="tableForm" >
					<tr class="odd">
						<td class="Label">发件单位</td>
						<td>
		 					<span id="sendUnitNames">${bean.fromUnitName}${bean.fromUserName}</span>
		 				</td>
					</tr>
					<tr class="odd" style="display: none;" id="showLimitUnit"><!-- 收件  （接收逾期）-->
						<td class="Label">收件单位</td>
						<td>
		 					<span>${bean.to}</span>
		 				</td>
					</tr>
					<tr class="odd" style="display: none;" id="replyLimit">
						<td class="Label">接收逾期</td>
						<td>
		 					<span>${bean.replyLimitNum}工作日</span>
		 				</td>
					</tr>
				</table>
			</div>
			
			<input type="hidden" id="uuid" name="uuid" value="${bean.uuid}">
			<input type="hidden" id="formId" name="formId" value="${bean.formId}">
			<input type="hidden" id="dataUuid" name="dataUuid" value="${bean.dataUuid}">
			<input type="hidden" id="typeId" name="typeId" value="${bean.typeId}">
			<input type="hidden" id="typeName" name="typeName" value="${bean.typeName}">
			<input type="hidden" id="showToUnit" name="showToUnit" value="${bean.showToUnit}">
			<input type="hidden" id="to" name="to" value="${bean.to}">
			<input type="hidden" id="cc" name="cc" value="${bean.cc}">
			<input type="hidden" id="bcc" name="bcc" value="${bean.bcc}">
			<input type="hidden" id="dataId" name="dataId" value="${bean.dataId}">
			<input type="hidden" id="recVer" name="recVer" value="${bean.recVer}">
			<input type="hidden" id="allUnit" name="allUnit" value="${bean.allUnit}">
			<input type="hidden" id="title" name="title" value="${bean.title}">
			<input type="hidden" id="hasUnderling" name="hasUnderling" value="${bean.hasUnderling}">
			<input type="hidden" id="sendNode" name="sendNode" value="${bean.sendNode}">
			<input type="hidden" id="receiveNode" name="receiveNode" value="${bean.receiveNode}">
			<input type="hidden" id="sendLimitNum" name="sendLimitNum" value="${bean.sendLimitNum}">
			<input type="hidden" id="replyLimitNum" name="replyLimitNum" value="${bean.replyLimitNum}">
			<input type="hidden" id="cancelUnits" name="cancelUnits" value="${bean.cancelUnits}">
			<input type="hidden" id="showRefuse" name="showRefuse" value="${bean.showRefuse}">
			<input type="hidden" id="status" name="status" value="${status}">
			<input type="hidden" id="fromId" name="fromId" value="${bean.fromId}">
			<input type="hidden" id="sendType" name="sendType" value="${bean.sendType}">
			<!-- 动态表单 -->
			<form   id="dyform" ></form>
			
			<!-- 收发情况 -->
			<%-- <c:if test="${not empty bean.allUnit}"> --%>
			<div class="form_content" style="padding-bottom: 25px; display: none;" id="routeMsg">
				<table cellpadding="2" cellspacing="2" class="tableForm">
					<tr class="title">
		  				<td class="Label" colspan="5">收发情况</td>
					</tr>
					<tr class="odd">
						<td class="Label">发件单位</td>
						<td class="Label">发件时间</td>
						<td class="Label">收件单位</td>
						<td class="Label">送达时间</td>
						<td class="Label">收件情况</td>
					</tr>
					<c:forEach items="${bean.exchangeDataMonitors}" var="monitor" varStatus="ver">
						<c:if test="${fn:indexOf(bean.bcc,monitor.unitId)<0||monitor.unitId==bean.userUnitId}">
						<tr class="odd">
							<td class="Label">${monitor.fromUnitName}</td>	
							<td class="Label">
								<fmt:formatDate value="${monitor.createTime}" pattern="yyyy-MM-dd HH:mm"/>
								<c:if test="${monitor.cancelStatus eq 'success'}">
									(于
									<fmt:formatDate value="${monitor.cancelTime}" pattern="yyyy-MM-dd HH:mm"/>
									注销
									<c:if test="${not empty monitor.cancelMsg&&monitor.cancelMsg!=''}">
									：${monitor.cancelMsg}
									</c:if>)
								</c:if>
							</td>
							<td class="Label">${monitor.unitName}</td>
							<td class="Label">
								<c:if test="${monitor.receiveStatus eq 'success'}">
								<fmt:formatDate value="${monitor.receiveTime}" pattern="yyyy-MM-dd HH:mm"/>(成功)
								</c:if> 
								<c:if test="${monitor.receiveStatus eq 'fail'}">
								<fmt:formatDate value="${monitor.receiveTime}" pattern="yyyy-MM-dd HH:mm"/>(失败)
								</c:if>
							</td>
							<td class="Label">
							
								<c:if test="${monitor.replyStatus eq 'success'}">
								<fmt:formatDate value="${monitor.replyTime}" pattern="yyyy-MM-dd HH:mm"/>(${monitor.replyUser}签收)
								</c:if> 
								<c:if test="${monitor.replyStatus eq 'fail'}">
								<fmt:formatDate value="${monitor.replyTime}" pattern="yyyy-MM-dd HH:mm"/>(${monitor.replyUser}退回<c:if test="${not empty monitor.replyMsg&&monitor.replyMsg!=''}">
									：${monitor.replyMsg}
								</c:if>)
								</c:if>
							</td>
						</tr>
						</c:if>
					</c:forEach>
					
				</table>
			</div>
			
			<c:forEach items="${bean.unitList}" var="unit">
				<input type="hidden" class="includeUnits" id="${unit.id}" value="${unit.name}">
			</c:forEach>
	
			<!-- Project -->
			<%@ include file="/pt/dyform/dyform_js.jsp"%>
			 
			<script type="text/javascript"
				src="${ctx}/resources/chosen/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/exchangedata/detail.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
	<script type="text/javascript">
		$(function() {

		});
	</script>
</body>
</html>