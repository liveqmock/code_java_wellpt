<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>动态表单解释解释</title>
<script type="text/javascript" src="${ctx}/resources/jqgrid/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/resources/utils/json2.js"></script>

<script type="text/javascript" src="${ctx}/resources/validform/js/Validform_v5.2.1.js"></script>
<script type="text/javascript" src="${ctx}/resources/jqueryValidate/js/jquery-validate.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/resources/validform/css/style.css"/>

<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/resources/jqgrid/themes/ui.multiselect.css" />

<script src="${ctx}/resources/jqgrid/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/jqgrid/js/src/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script type="text/javascript">  
    $.jgrid.no_legacy_api = true;
    $.jgrid.useJSON = true;
</script> 
<script src="${ctx}/resources/jqgrid/js/jquery.jqGrid.js" type="text/javascript"></script>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<script src="${ctx}/resources/jBox/jquery.jBox.src.js" type="text/javascript"></script>
<script src="${ctx}/resources/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
<link href="${ctx}/resources/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.numberInput.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/pt/js/common/jquery.comboTree.js"></script>
<script type="text/javascript" src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/dytable/dytable_constant.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/dytable/dytable_explain.js"></script>

<style type="text/css">
	body,table,input,select{font-size:12px}
	TABLE{font-size:12px;border-collapse:collapse;border:1px solid #888888}
	TD{border:1px solid #888888}
	TD.label{background-color:#EEEEEE;color:#0066CC;text-align:right;padding-right:8px}
	
.dytable_form .post-sign .filter{width:880px;margin:15px auto 0;}
.dytable_form .post-sign .filter-form{height:40px;}
.dytable_form .post-sign .filter-form li{float:left;display:inline;margin:0 37px 0 18px;padding:4px 
12px;border:1px solid #d7d7d7;line-height:25px;color:#0f599c;}
.post-sign .post-sign .filter-form li input{margin-left:13px;padding:3px 5px 4px;line-
height:18px;border:0 none;border-left:1px solid #d7d7d7;}

.dytable_form .post-sign .post-detail{width:880px;margin:15px auto 0;}
.dytable_form .post-sign .post-detail table{width:100%;margin-top:10px;}
.dytable_form .post-sign .post-detail td{height:29px;padding:0 15px;border-right:2px solid #fff;}
.dytable_form .post-sign .post-detail .Label{width:110px;}
.dytable_form .post-sign .post-detail .odd td{background:#f7f7f7;}

.dytable_form .post-sign .post-info{margin-top:2px;}
.dytable_form .post-sign .post-info .post-hd{height:30px;padding:0 10px;background:#0f599c;}
.dytable_form .post-sign .post-info .post-hd h3{float:left;margin-top:5px;font-weight:normal;color:#fff;}
.dytable_form .post-sign .post-info .post-hd .extral{float:right;margin-top:5px;color:#fff;}
.dytable_form .post-sign .post-info .post-hd a:link,.post-sign .post-info .post-hd a:visited{margin:0 
8px;color:#fff;}
.dytable_form .post-sign .post-info .post-bd{padding:15px 0;}
.dytable_form .post-sign .post-info .attach-list{height:100px;}
.dytable_form .post-sign .post-info .attach-list li{float:left;width:120px;text-align:center;}
.dytable_form .post-sign .post-info .attach-list a img{width:64px;height:64px;padding:10px;border:1px solid 
#d4d0c8;}
.dytable_form .post-sign .post-info .attach-list span{display:block;line-height:25px;text-align:center;}
.dytable_form .post-sign .post-detail .title {background: none repeat scroll 0 0 #0F599C;color:#fff;}
</style>

<script type="text/javascript">
	//全局变量
	var formUid = '${formUid}';
	var dataUid = '${dataUid}';
</script>

</head>
<body id="explainBody">
	<div><input id="save" type="button" value="保存"/></div>
	<div id="explain">
	</div>
	
	<!-- <textarea rows="10" cols="60" id="showJSON"></textarea> -->
</body>
</html>