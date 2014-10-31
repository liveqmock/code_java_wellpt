<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<title>${typeName}</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/chosen/chosen.min.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/exchange/sendData.css" />
	
<style type="text/css">
/*  #addCc {
	 width:150px; height:40px; top:20%; left:30%;
}  */
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<!--标题-->
				<div class="form_title">
					<h2>${typeName}</h2>
				</div>
				<div id="toolbar" class="form_toolbar">
					<div id="mini_wf_opinion" style="margin-bottom: 2px;">
					</div>
					<div class="form_operate">
						<c:forEach var="btn" items="${btns}">
							<button id="${btn.code}" name="${btn.code}" style="display: none;">${btn.name}</button>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="form_content" style="display: none;" id="showMsg">
				<table cellpadding="2" cellspacing="2" class="tableForm" >
					<tr class="odd">
						<td class="Label">收件 </td>
						<td>
		 					<input name="toNames" type="text" id="toNames"/>
		 					<input type="hidden" id="to" name="to"> 
		 				</td>
					</tr>
					
					<tbody id="ffshow">
						<tr id="mcc" style="display: none">
							<td class="Label">抄送</td>
							<td><input id="ccNames" name="ccNames" type="text"/>
								<input type="hidden" id="cc" name="cc">
							</td>
						</tr>
						
						<tr id="mbcc" class="odd" style="display: none">
							<td class="Label">密送</td>
							<td><input name="bccNames" type="text" id="bccNames"/>
								<input type="hidden" name="bcc" id="bcc">
							</td>
						</tr>
						
						<tr>
							<td class="Label"></td>
							<td>
							<table>
								<tr>
									<td id="addCctd" width="50px"><div><a href="#" id="addCc">添加抄送</a></div></td>
									<td id="delCctd" width="50px" style="display: none">
									<a href="#"	id="delCc">删除抄送</a>
									</td>
								
									<td id="addBcctd"><div><a href="#" id="addBcc">添加密送</a></div></td>
									<td id="delBcctd" style="display: none; ">
									<a href="#" id="delBcc">删除密送</a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<input type="hidden" id="rel" name="rel" value="${rel}">
			<input type="hidden" id="formId" name="formId" value="${formId}">
			<input type="hidden" id="typeName" name="typeName" value="${typeName}">
			<input type="hidden" id="typeId" name="typeId" value="${typeId}">
			<input type="hidden" id="showToUnit" name="showToUnit" value="${showToUnit}">
			<input type="hidden" id="from" name="from" value="${from}">
			<input type="hidden" id="toId1" name="toId1" value="${toId1}">
			<input type="hidden" id="cc1" name="cc1" value="${cc1}">
			<input type="hidden" id="bcc1" name="bcc1" value="${bcc1}">
			<input type="hidden" id="toNames1" name="toNames1" value="${toNames1}">
			<input type="hidden" id="ccNames1" name="ccNames1" value="${ccNames1}">
			<input type="hidden" id="bccNames1" name="bccNames1" value="${bccNames1}">
			<input type="hidden" id="fromName" name="fromName" value="${fromName}">
			<input type="hidden" id="dataUuid" name="dataUuid" value="${dataUuid}">
			<input type="hidden" id="sendUuid" name="sendUuid" value="${sendUuid}">
			<input type="hidden" id="correlationDataId" name="correlationDataId" value="${correlationDataId}">
			<input type="hidden" id="correlationRecver" name="correlationRecver" value="${correlationRecver}">
			
			<input type="hidden" id="ZCHval" name="ZCHval" value="${ZCH}">
			<input type="hidden" id="ZTMCval" name="ZTMCval" value="${ZTMC}">
			<input type="hidden" id="FDDBRval" name="FDDBRval" value="${FDDBR}">
			<input type="hidden" id="JYCSval" name="JYCSval" value="${JYCS}">
			<input type="hidden" id="ZTLXval" name="ZTLXval" value="${ZTLX}">
			
			<input type="hidden" id="matterId" name="matterId">
			<input type="hidden" id="unitId" name="unitId" value="${unitId}">
			<input type="hidden" id="unitName" name="unitName" value="${unitName}">
			
			<!-- 动态表单 -->
			<form   id="dyform" ></form>
			<!-- Project -->
			<%@ include file="/pt/dyform/dyform_js.jsp"%>
			<script type="text/javascript"
				src="${ctx}/resources/chosen/chosen.jquery.min.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/exchangedata/senddata.js"></script>
			<script type="text/javascript"
				src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
		</div>
	</div>
	<div class="body_foot"></div>
</body>
</html>