<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ include file="/pt/common/taglibs.jsp"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>日程</title>
	<%@ include file="/pt/common/meta.jsp"%>
</head>
<body  style="width:100%; height:100%;padding:0px; margin:0px;">

	<div class="schedule_index_list">
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/schedule_person.css" />
		<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/urlPage.css">

		<ul class = "nav nav-tabs">
			<li class="active" >
				<a href="${ctx}/cms/cmspage/readPage?uuid=ce557faa-c55b-4cde-96c3-c9cc26ada8d5&ContentArea=3757900f-b32e-404b-a613-6f0975f33c34">日程</a>
			</li>
			<div class="right" style="margin-top: 8px;margin-right: 5px;"> ${ldate } </div>
		</ul>

		<div class="date_content">
			<table   width="100%"  class="table">
				
				<c:forEach items="${scheMap[ldate] }" var="sche">
					<tr class="tr_content odd dataTr "  style="color: ${sche.color}" 
						onclick="openScheduleDialog('${viewType }','${ctype }','${ldate }','${currentUserId }','${nowTag.uuid }',
						'${sche.uuid }','${sche.encodeName }','${sche.encodeAddress }','${sche.dstartDate }','${sche.startTime }','${sche.dendDate }','${sche.endTime }',
						'${sche.isComplete }','${sche.respon}','${sche.responIds }','${sche.pleases }','${sche.pleaseIds }',
						'${sche.color}','${sche.tip }','${sche.tipDate }','${sche.tipTime }','${sche.tipMethod }','${sche.repeat }','${sche.creators }','${sche.creatorIds }','${sche.inviteeNames}','${sche.inviteeIds}','${sche.acceptIds }',
						'${sche.acceptNames }','${sche.refuseIds}','${sche.refuseNames}','${nowTag.name}','${sche.content }');">
			
						<td width="65%">
						<c:choose>
							<c:when test="${sche.isComplete=='1' }">
								<strike>${sche.scheduleName }</strike>
							</c:when>
							<c:otherwise>${sche.scheduleName }</c:otherwise>
						</c:choose>
						</td>
						<td width="20%">
						<c:choose>
						<c:when test="${sche.isComplete=='1' }">
								<strike>${sche.startTime }</strike>
							</c:when>
							<c:otherwise>${sche.startTime }</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>	
			<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
			<!-- jQuery UI -->
			<script type="text/javascript" src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
			<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
			<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
			<script type="text/javascript" src="${ctx}/resources/schedule/sche.js"></script>
		</div>
	</div>
</body>
</html>