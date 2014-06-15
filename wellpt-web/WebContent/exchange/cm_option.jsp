<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var exconfig = {};
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