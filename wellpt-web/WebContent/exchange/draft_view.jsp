<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>新建草稿</title>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/exchange/css/exchange.css" rel="stylesheet" />
</head>
<body>
<div id="document_view_dialog" style="display: none">
	<div class="box">
		<div class="box-content">
			<div class="tabbable">
				<ul class="nav nav-tabs" id="document_view_tab">
					<li class="active"><a href="#tab1" data-toggle="tab"><spring:message code="exchange.label.view.tabDocInfo" /></a></li>
					<li><a href="#tab2" data-toggle="tab"><spring:message code="exchange.label.view.tabTrack" /></a></li>
					<li><a href="#tab3" data-toggle="tab"><spring:message code="exchange.label.view.tabLog" /></a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<table class="table table-bordered">
							<tr>
								<td width="10%" style="background: #F8F8F8;"><spring:message code="exchange.label.view.senderName" /></td>
								<td><span id="docView_senderUserName"></span>
								<input type="hidden" id="docView_docID" />
								<input type="hidden" id="docView_senderUUID" />
								<input type="hidden" id="docView_senderUser" />
								<input type="hidden" id="docView_sendeeUUID" />
								<input type="hidden" id="docView_sendeeUser" />
								</td>
							</tr>
							<tr>
								<td width="10%" style="background: #F8F8F8;"><spring:message code="exchange.label.view.sendeeName" /></td>
								<td><span id="docView_sendeeUserName"></span></td>
							</tr>
							<tr>
								<td width="10%" style="background: #F8F8F8;"><spring:message code="exchange.label.view.senderDate" /></td>
								<td><span id="docView_senderTime"></span></td>
							</tr>
							<tr>
								<td width="10%" style="background: #F8F8F8;"><spring:message code="exchange.label.view.docTitle" /></td>
								<td><span id="docView_docTitle"></span></td>
							</tr>
							<tr>
								<td width="10%" style="background: #F8F8F8;"><spring:message code="exchange.label.view.docOption" /></td>
								<td><span id="docView_docOption"></span></td>
							</tr>
							<tr>
								<td colspan="2"><span id="docView_docContent"></span></td>
							</tr>
						</table>
					</div>					
					<div class="tab-pane" id="tab2">
						<table id="track_list"></table>
					</div>
					<div class="tab-pane" id="tab3">
						<table id="log_list"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<%@ include file="/pt/dytable/form_js.jsp"%>
	<%@ include file="/exchange/cm_option.jsp"%>
	<script type="text/javascript" src="${ctx}/resources/exchange/js/common_v1.js"></script>
	<script type="text/javascript" src="${ctx}/resources/exchange/js/draft_v1.js"></script>
</body>
</html>