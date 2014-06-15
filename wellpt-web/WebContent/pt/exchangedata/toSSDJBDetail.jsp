<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商事登记簿-${ztName}</title>
<%@ include file="/pt/dytable/form_css.jsp"%>
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/pt/js/common/jquery.commercialRegister2.css" />
<style type="text/css">
.div_body {
    width: 1030px;
    height: 100%
}
.body_foot {
	width: 1025px;
	margin: 0;
}
#container {
	color: #000;
	margin: 0 auto;
	background-color: #fff;
	width: 1000px;
}
</style>
</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
			<div class="form_header">
				<div class="form_title">
					<h2>商事登记簿-${ztName}</h2>
					<div class="form_close" title="关闭"></div>
				</div>
				<div id="toolbar" class="form_toolbar" style="padding: 8px 0 10px 0px;"></div>
			</div>
			<div id="dyform">
				<div class="dytable_form">
					<div class="post-sign">
						<div class="post-detail">
							<div id="container">
								<input type="hidden" id="zch" vlaue="${zch}">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="body_foot" style="height:3px;"></div>
	</div>
</body>
<script src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.commercialRegister2.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
		var zch = '${zch}';
		JDS.call({
			service : 'exchangeDataClientService.getSSDJBDetailDate',
			async : false,
			data : [zch],
			success : function(result) {
				var list = result.data;
// 				alert(JSON.stringify(list));
// 				var list = '[{"maxDept":6,"type":"商事主体登记信息","data":[[{"index":1,"isFirst":true,"childNum":3,"time":"2014-02-12","task":"主体登记","code":1}],[{"index":1,"isFirst":false,"childNum":3,"time":"2014-02-12 10:24","task":"工商上传","limitNum":null,"code":2,"recVer":"版本:1","url":"/wellpt-web/exchangedata/client/toExchangeDetailPage?UUID=2c2a612e-d14a-49f1-849b-d321034d314a"}],[{"index":2,"isFirst":true,"childNum":3,"time":"2014-02-12 10:24","task":"送达厦门市卫生局","code":3},{"index":2,"isFirst":true,"childNum":1,"time":"2014-02-12 10:24","task":"送达厦门市公安局","code":3},{"index":2,"isFirst":true,"childNum":1,"time":"2014-02-12 10:24","task":"送达厦门市教育局","code":3},{"index":2,"isFirst":true,"childNum":1,"time":"2014-02-12 10:24","task":"送达厦门市卫生局","code":3}],[{"index":2,"isFirst":false,"childNum":3,"time":"2014-02-12 11:12","task":"签收","limitNum":null,"code":4},{"index":2,"isFirst":false,"childNum":1,"task":"待签收","code":4},{"index":2,"isFirst":false,"childNum":1,"task":"待签收","code":4},{"index":2,"isFirst":false,"childNum":1,"time":"2014-02-12 11:12","task":"签收","limitNum":null,"code":4}],[{"index":2,"isFirst":false,"childNum":3,"data":[{"isFirst":true,"time":"2014-02-12 10:24","task":"转发到集美卫生局","code":5},{"isFirst":true,"time":"2014-02-12 10:24","task":"转发到思明卫生局","code":5},{"isFirst":true,"time":"2014-02-12 10:24","task":"转发到同安卫生局","code":5}]},{"index":2,"isFirst":false,"childNum":1,"task":"出证","code":5},{"index":2,"isFirst":false,"childNum":1,"task":"出证","code":5},{"index":2,"isFirst":false,"childNum":1,"time":"2014-02-11","task":"出证","code":5}],[{"index":2,"isFirst":false,"childNum":3,"data":[{"time":"2014-02-12 10:24","task":"签收","code":6},{"task":"待签收","code":6},{"task":"待签收","code":6}]},{"index":2,"isFirst":false,"childNum":1,"task":"上传","code":6},{"index":2,"isFirst":false,"childNum":1,"task":"上传","code":6},{"index":2,"isFirst":false,"childNum":1,"time":"2014-02-12 11:18","task":"上传","limitNum":null,"code":6,"recVer":"版本:1","url":"/wellpt-web/exchangedata/client/toExchangeDetailPage?UUID=2c5f4f80-7ce1-4b6f-ba55-2403112431a6&rel=7"}],[{"index":2,"isFirst":false,"childNum":3,"data":[{"time":"2014-02-12 10:24","task":"出证","code":7},{"task":"出证","code":7},{"task":"出证","code":7}]},{"index":2,"isFirst":false,"childNum":1},{"index":2,"isFirst":false,"childNum":1},{"index":2,"isFirst":false,"childNum":1}],[{"index":2,"isFirst":false,"childNum":3,"data":[{"time":"2014-02-12 10:24","task":"出证","code":8},{"task":"上传","code":8},{"task":"上传","code":8}]},{"index":2,"isFirst":false,"childNum":1},{"index":2,"isFirst":false,"childNum":1},{"index":2,"isFirst":false,"childNum":1}]]}]';
// 				list = JSON.parse(list);
				for(var i=0;i<list.length;i++){
					var str = "<div class='commercial_register"+i+"'></div>";
					$("#container").append(str);
					$(".commercial_register"+i).commercialRegister({
						data : list[i]
					});
				}
			},
			error : function(xhr, textStatus, errorThrown) {
			}
		});
// 		var height = 70;
// 		$(".process").each(function(){
// 			height += parseInt($(this).css("height"));
// 		});
// 		var wh = parseInt($(window).height())-100
// 		var dh = height;
// 		if(wh>dh){
// 			$("#container").css("height",wh);
// 		}else{
// 			$("#container").css("height",dh);
// 		}
		$(".form_close").click(function(){
			window.close();
		});
	});
	</script>
</html>