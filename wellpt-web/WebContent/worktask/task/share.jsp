<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
var uid=null;
function check(userid){
	uid=userid;
}
function gourl(url){
	if(uid==null||uid==''){
		url=url+"?userId="+uid;
	} 
	window.parent.share_iframe.src=url;
}

window.onload=function(){
	var zTreeSetting = {
			async : {
			enable : true,
			contentType : "application/json",
			url : ctx + "/json/data/services",
			otherParam : {
			"serviceName" : "unitTreeService",
					"methodName" : "parseUnitTree"
				},
				type : "POST"
			},
			callback : {}
		};

		$.fn.zTree.init($("#selector"), zTreeSetting);
	}
</script>
<body>
	<div id="selector">
 
			 

	</div> 
		<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
</body>
</html>