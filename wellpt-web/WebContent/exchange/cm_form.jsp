<%@ page language="java" pageEncoding="UTF-8"%>

<!-- 拟稿表单 -->
<div id="draft_form_dialog" style="display: none" class="row-fluid sortable">
	<div class="box span12">
		<div class="box-content">
			<form class="form-horizontal" id="draft_form">
				<input type="hidden" id="documentUUID" name="documentUUID" /> 
				<input type="hidden" id="formUUID" name="formUUID" value="${form_uuid}" /> 
				<input type="hidden" id="dataUUID" name="dataUUID" value="${data_uuid}" />				
				<div class="row-fluid" id="draft_dytable"></div>
			</form>
		</div>
	</div>
</div>
<script>
$(function() {
	exconfig = {
		showTraceUser: ${config_bean.showTraceUser},		
		showLogs: ${config_bean.showLogs},
		attachmentForSign: ${config_bean.attachmentForSign}, //签收之前允许查阅附件
		attachmentForOpinion: ${config_bean.attachmentForOpinion}, //意见框允许增加附件
		allowLimitTime: ${config_bean.allowLimitTime}, //启用办理时限
		forceSignature: ${config_bean.forceSignature}, //签收强制签名
		userID: "${cuuser_bean.userId}",
		userUUID: "${cuuser_bean.userUuid}",
		userNmae: "${cuuser_bean.username}",
		formUUID: "${form_uuid}"
	};
});
</script>

<!-- 信息查看表单 -->
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