<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dyform/dyform_css.jsp"%>
<%@ include file="/pt/dyform/dyform_js.jsp"%>
<%@ include file="/pt/dyform/dyform_ctljs.jsp"%>
<title>订单行项目明细维护</title>

<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/validform/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/resources/jqgrid/themes/ui.datepicker.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/pt/css/custom-admin-style.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet"
	type="text/css" />
	
<script src="${ctx}/resources/jquery/jquery.js" type='text/javascript'></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js" type='text/javascript'></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js "></script>
<script src='${ctx}/dwr/engine.js' type='text/javascript' type='text/javascript'></script> 
<script src='${ctx}/dwr/util.js' type='text/javascript' type='text/javascript'></script>
<script src='${ctx}/dwr/interface/directController.js' ></script>  
<script src="${ctx}/resources/cookie/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.jscrollpane.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/ajaxfileupload.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/form/jquery.form.js"></script>
<script src="/wellpt-web/resources/pt/js/global.js" type="text/javascript"></script>
<script src="${ctx}/resources/pt/js/exchangedata/cmsbtns.js"></script>
<script src="${ctx}/resources/pt/js/ldx/orderManage/list_order_view.js"></script>
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
</style>

</head>
<body>
<div class="div_body" style="">
		<div class="form_header" style="">
			<div class="form_title">
				<h2>行项目明细维护</h2>
			</div>
			<div id="toolbar" class="form_toolbar">
				<div class="form_operate">
					<button type="button" id="form_save" onClick="File.sava();">保存</button>
					<button id="form_close" type="button">关闭</button>
				</div>
			</div>
		</div>
		<form action="" id="capacityform" class="dyform">
<div class="div_body">
	<table>
		<tr class="field">
			<td align="right">&nbsp;SAP订单号:</td>
			<td>&nbsp;<input type="text" name="vbeln" id="vbeln" value="${vbeln}" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;行项目号:</td>
			<td>&nbsp;<input type="text" name="posnr" id="posnr" value="${posnr}" readonly="readonly" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;回复客人交期:</td>
			<td>&nbsp;<input type="text" name="zhfkr" id="zhfkr" value="${zhfkr}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;验货日期:</td>
			<td>&nbsp;<input type="text" name="zyhrq" id="zyhrq" value="${zyhrq}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;验货结果:</td>
			<td>&nbsp;<input type="text" name="zyhjg" id="zyhjg" value="${zyhjg}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;验货备注:</td>
			<td>&nbsp;<input type="text" name="zyhbz" id="zyhbz" value="${zyhbz}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;尾数预计出货数量:</td>
			<td>&nbsp;<input type="text" name="zchsl" id="zchsl" value="${zchsl}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;尾数预计出货日期:</td>
			<td>&nbsp;<input type="text" name="zchrq" id="zchrq" value="${zchrq}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td width="110px;">&nbsp;逾期责任部门:</td>
			<td>&nbsp;<input type="text" name="zyqbm" id="zyqbm" value="${zyqbm}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;逾期异常大类:</td>
			<td>&nbsp;<input type="text" name="zycdl" id="zycdl" value="${zycdl}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;逾期备注:</td>
			<td>&nbsp;<input type="text" name="zyqbz" id="zyqbz" value="${zyqbz}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
		<tr class="field">
			<td align="right">&nbsp;OM提供出货计划日期:</td>
			<td>&nbsp;<input type="text" name="omchjh" id="omchjh" value="${omchjh}" style="text-align: left; font-size: 12px; color: rgb(0, 0, 0);"></td>
		</tr>
	</table>
</div>
</form>
</div>
<script type="text/javascript">
$(function(){
	$("#sae").click(function(){
		var vbeln = $("#vbeln").attr("value"); 
		var posnr = $("#posnr").attr("value"); 
		var orderJson = {ag0:vbeln,ag1:posnr};
		alert("11");
		$.ajax({
			url:"${ctx}/orderManage/orderLineItemDetail/save",
			async : false,
			type:"POST",
			data:{
				"saps":"",
				"functionName" : "ZFM_TEMPLATE",
				"jsonParams" : orderJson
			},
			success: function(data){ 
		       alert("data");
		     }
		})
	})
	$("#form_close").click(function(){
		window.close();
	})
})
</script>
</body>
</html>