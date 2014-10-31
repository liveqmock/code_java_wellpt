<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>


<!DOCTYPE html>
<html lang="en">
<head>
<style>
/* Component containers
----------------------------------*/
.ui-widget { font-family: "Microsoft YaHei"; font-size: 12px; }
.ui-widget .ui-widget { font-size: 11px; }
.ui-widget input, .ui-widget select, .ui-widget textarea, .ui-widget button { font-family:"Microsoft YaHei"; font-size: 12px; }
<!--
/* 组织选择框 */
.ui-dialog {
padding:0;
}
.ui-widget-header{
background: url("/wellpt-web/resources/theme/images/v1_icon.png") repeat-x scroll 0 -53px transparent;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
border-bottom-right-radius: 0;
}
.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl {
border-bottom-left-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr {
border-top-right-radius: 0;
}
.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl {
border-top-left-radius: 0;
}
.ui-resizable-handle.ui-resizable-s {
background: url("/wellpt-web/resources/theme/images/v1_panel.png") repeat-x scroll 0 -10px transparent;
}
.ui-resizable-handle.ui-resizable-e {
background: url("/wellpt-web/resources/theme/images/v1_bd.png") repeat-y scroll 100% 0 transparent;
right: -2px;
}
-->
</style>

<%-- <link rel="stylesheet" href="${ctx}/resources/theme/css/wellnewoa.css  " type="text/css" /> --%>
 <c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/resources/pt/css/jquery-ui-1.8.21.custom.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" /> 

<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" /> 
<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>



<script type="text/javascript">
$(document).ready(function(){

		$("#btn_jds").click(function(){
			console.log("11111");
			var dbFiles = [];
			JDS.call({
	       	 service:"dataDictionaryService.getDataDictionariesByType",
	       	 data:["XZSP_BJ_TYPE"],
	       	 async: false,
				success:function(result){
					var datas = result.data;
					for(var i in datas){
						var data=datas[i]
					   console.log(data.name);
					   console.log(data.code);
					}
				},
				error:function(jqXHR){
					 
				}
			});
			return dbFiles;
		});
		
		var params='{"PI_MATNR":"abc","PI_WERKS":"efg","PI_TAB":{"MATNR":"1"},"PT_TAB":{"row":[{"MATNR":"1","WERKS":"2"},{"MATNR":"3","WERKS":"4"}]}}';
		
		$("#btn_jds_rfc").click(function(){
			console.log("11111");
			$.ajax({
				type : "post",
				data : {
					"saps":"",
					"functionName" : "ZFM_TEMPLATE",
					"jsonParams" : params
				},
				async : false,
				url : ctx + "/basicdata/sap/executeRFC",
				success : function(result) {
					if (result.success) {
						alert("保存成功!");
						// 保存成功刷新列表
						alert(result.data);
					} else {
						alert(result.msg);
					}
				}
			});
		});
		
		$("#btn_jds_save").click(function(){
			$.ajax({
				type : "post",
				data : {
					"uuid":"test",
				},
				async : false,
				url : ctx + "/org/job/saveJobSet",
				success : function(result) {
					if (result.success) {
						alert("保存成功!");
						// 保存成功刷新列表
						alert(result.data);
					} else {
						alert(result.msg);
					}
				}
			});
		});
});

</script>
<body>

<input type="button" id="btn_jds" value="JDS" />
<input type="button" id="btn_jds_rfc" value="JDS_RFC" />
<input type="button" id="btn_jds_save" value="JDS_savejob" />

</body>
</html>
