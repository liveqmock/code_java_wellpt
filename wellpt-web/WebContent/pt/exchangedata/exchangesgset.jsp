<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>User List</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: auto; /* when page gets too small */
}

#container {
	background: #999;
	height: 100%;
	position: absolute;
	margin: 0 auto;
	width: 100%;
}

.pane {
	display: none; /* will appear when layout inits */
}
.tabs-1_td{
	width: 170px;
}
.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
.ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
.ui-timepicker-div td { font-size: 90%; }
.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
</style>

</head>
<body>
	<div id="container">
		<div class="pane ui-layout-center">
		
			
					<div class="tabs">
						<ul>
							<li><a href="#tabs-1"><spring:message code="mail.label.mailSetC"/></a></li>
						</ul>
						<div id="tabs-1">
							<table>
						    	 <tr>
									<td class="tabs-1_td"><label>接收时限(天)</label></td>
						        	<td><input id="receiveLimit" name="receiveLimit" value="${result.receiveLimit}" type="text" style="width:100%;"/></td>
						        </tr>  
						        
						        <tr>  
						        	<td class="tabs-1_td"><label>上报时限(天)</label></td>
						        	<td><input id="reportLimit" name="reportLimit" value="${result.reportLimit}" type="text" style="width:100%;"/></td>
						        </tr>
						    </table>  
								    
						</div>
					</div>
					<div class="btn-group btn-group-bottom">
						<input type="button" id="btn_save"  value="保存"></input>
					</div>
		</div>
	</div>


	<!-- Project -->
	
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/layout/jquery.layout.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/ztree/js/jquery.ztree.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
	<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/pt/js/mail/mail_config.js"></script>
	<script type="text/javascript">
		$(function(){
			//保存操作
			$("#btn_save").click(function() {
				var bean = new Object();
				bean.receiveLimit = $("#receiveLimit").val();
				bean.reportLimit= $("#reportLimit").val();
				JDS.call({
					service:"exchangeDataConfigService.saveSGSet",
					data:[bean],
					success:function(result) {
						alert("保存成功!");
					},
					error:function(result) {
					}
				});
			});
		});
	</script>
</body>
</html>