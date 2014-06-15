<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${spsxName}-${sqrhsqdw}</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/js/common/jquery.commercialRegister.css" />
<style type="text/css">
.div_body {
    width: 1030px;
    height: 100%
}
.body_foot {
	width: 1025px;
}
#container {
	color: #000;
	margin: 0 auto;
	background-color: #fff;
	width: 1650px;
}

.dytable_form .post-sign .post-detail {
	overflow: auto;
}

.process .proce.first {
    width: 95px;
}
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<div class="form_title">
					<h2>${spsxName}-${sqrhsqdw}</h2>
					<div class="form_close" title="关闭"></div>
				</div>
				<div id="toolbar" class="form_toolbar" style="padding: 8px 0 10px 0px;"></div>
			</div>
			<div id="dyform">
				<div class="dytable_form">
					<div class="post-sign">
						<div class="post-detail">
							<div id="container">
								<input type="hidden" id="ywlsh" value="${ywlsh}">
								<input type="hidden" id="uuid" value="${uuid}">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.administrativeLicense.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
		var ywlsh = '${ywlsh}';
		var uuid = '${uuid}';
		JDS.call({
			service : 'exchangeDataClientService.getXZXKGCDetail',
			async : false,
			data : [ywlsh,uuid],
			success : function(result) {
				var list = result.data;
				for(var i=0;i<list.length;i++){
					var str = "<div class='administrative_license"+i+"'></div>";
					$("#container").append(str);
					$(".administrative_license"+i).administrativeLicense({
						data : list[i]
					});
				}
			},
			error : function(xhr, textStatus, errorThrown) {
			}
		});
		var height = 70;
		$(".process").each(function(){
			height += parseInt($(this).css("height"));
		});
		var wh = parseInt($(window).height())-100
		var dh = height;
		if(wh>dh){
			$("#container").css("height",wh);
		}else{
			$("#container").css("height",dh);
		}
		$(".form_close").click(function(){
			window.close();
		});
	});
	</script>
</html>