<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<title>messageDetail</title>
<%-- <link href="${ctx}/resources/pt/css/message.css" rel="stylesheet"
	type="text/css" />  --%>
</head>
<body>
<style type="text/css" rel="stylesheet">
.message_form {font: 12px/1.5 arial;}
.message_form .post-sign .filter{width:880px;margin:15px auto 0;}
.message_form .post-sign .filter-form{height:40px;}
.message_form .post-sign .filter-form li{float:left;display:inline;margin:0 37px 0 18px;padding:4px 
12px;border:1px solid #d7d7d7;line-height:25px;color:#0f599c;}
.post-sign .post-sign .filter-form li input{margin-left:13px;padding:3px 5px 4px;line-
height:18px;border:0 none;border-left:1px solid #d7d7d7;}

.message_form .post-sign .post-detail{width:840px;margin:15px auto 0;}
.message_form .post-sign .post-detail table{width:100%;margin-top:10px;}
.message_form .post-sign .post-detail td{height:29px;padding:0 15px;border-right:1px solid #fff;}
.message_form .post-sign .post-detail .Label{width:110px;}
.message_form .post-sign .post-detail .odd td{background:#f7f7f7;white-space: nowrap;}
.message_form .post-sign .post-detail .odd td select {padding: 0;margin: 0;width:133px;}

.message_form .post-sign .post-info{margin-top:2px;}
.message_form .post-sign .post-info .post-hd{height:30px;padding:0 10px;background:#0f599c;}
.message_form .post-sign .post-info .post-hd h3{float:left;font-weight:normal;color:#fff;margin: 0;padding: 0;}
.message_form .post-sign .post-info .post-hd .extral{float:right;margin-top:5px;color:#fff;cursor: pointer;margin-left:12px;margin-top:2px;}
.message_form .post-sign .post-info .post-hd a:link,.post-sign .post-info .post-hd a:visited{margin:0 
8px;color:#fff;}
.message_form .post-sign .post-info .post-bd{padding:15px 0;}
.message_form .post-sign .post-info .attach-list{height:100px;}
.message_form .post-sign .post-info .attach-list li{float:left;width:120px;text-align:center;list-style: none outside none;}
.message_form .post-sign .post-info .attach-list .bar{border: 1px solid;}
.message_form .post-sign .post-info .attach-list a img{width:64px;height:64px;padding:10px;border:1px solid #d4d0c8;}
.message_form .post-sign .post-info .attach-list a{text-decoration:none;color:#434343;}
.message_form .post-sign .post-info .attach-list span{display:block;line-height:25px;text-align:center;}
.message_form .post-sign .post-info .attach-list2{height:100px;}
.message_form .post-sign .post-info .attach-list2 li{float:left;width:120px;text-align:center;list-style: none outside none;}
.message_form .post-sign .post-info .attach-list2 .bar{border: 1px solid;}
.message_form .post-sign .post-info .attach-list2 a img{width:64px;height:64px;padding:10px;border:1px solid #d4d0c8;}
.message_form .post-sign .post-info .attach-list2 a{text-decoration:none;color:#434343;}
.message_form .post-sign .post-info .attach-list2 span{display:block;line-height:25px;text-align:center;}

.message_form .post-sign .post-detail .title {background: none repeat scroll 0 0 #0F599C;color:#fff;}

.form_header {
	top: 0;
	left: 0;
	position: relative;
	right: 0;
	z-index: 1;
	/* 	overflow: visible; */
	background: url(../images/icon.png) repeat-x scroll 0 -53px transparent;
	color: #fff;
	font-size: 100%;
	text-align: left;
	padding: 0px;
}

.form_header .form_title {
    height: 43px;
    width:880px;
    margin:0 auto;
}
.form_header .form_title h2 {
	color: #FFFFFF;
    float: left;
    font-family: "Microsoft YaHei";
    font-size: 18px;
    font-weight: normal;
    margin: 0px;
    padding: 8px 0 0 15px;
}
.form_content:before,.form_content:after {
	content: "";
	display: table;
	line-height: 0;
}

.form_content:after {
	clear: both;
}

.form_content {
	display: block;
	margin-left: auto;
	margin-right: auto;
	width: 1170px;
	margin-top: 90px;
	overflow: auto;
}

.form_toolbar {
	background: none repeat scroll 0 0 #F4F8FA;
    padding: 8px;
    text-align: right;
}
.form_operate {
	width:830px;
    margin:0 auto;
}
.form_operate button {
	cursor: pointer;
	display: inline-block;
	height: 17px;
	margin-left: 5px;
	padding: 0 3px;
	line-height: 17px;
	background: url(../images/icon.png) no-repeat scroll 0 -165px transparent;
	color: #0F599C;
	border: 1px solid #DEE1E2;
	vertical-align: middle;
}
.form_operate button:hover {
	color: #FF7200;
}
.value > input{
	 float: left;
}
.value > label {width: 100px;float: left;} 
</style>
<div>
<form id="hidForm" method="post" action="${ctx }/message/content/submitmessage" enctype="multipart/form-data">
	<div class="form_header">
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
				
				<button id="sendButton" type="button" value="发送">发送</button>
				
			</div>
		</div>
	</div>
	<div id="dyform">
    <div class="message_form">
    <div class="post-sign">	
	<div class="post-detail">
	<table tabletype="1" id="002" edittype="1" width="100%">
	 <tbody>
	  <tr class="odd">
	    <td class="Label">收件人</td>
	    <td class="value">
	    	<input type="hidden" id="userId" name="userId"/>
			<input id="showUser" style="width:700px;height:20px" value="" disabled"/>
	   </td>
	  </tr>
	  <tr>
	    <td class="Label">消息内容</td>
	    <td class="value" colspan="3">
	   		 <textarea ignore="ignore"  id="datetime_sec_col" style="width:690px; height:150px;" name="body"></textarea>
	    </td>
	  </tr>
	  <tr class="odd">
	  </tr>
	    <tr class="odd">
	    <td class="Label">消息类型</td>
	    <td class="value"><input type="checkbox" name="type" value="ON_LINE" checked="checked"/><label>发在线消息</label><input type="checkbox" name="type" value="EMAIL" /><label>发邮件</label><input type="checkbox" name="type" value="SMS" /><label>发短信</label></td>
	  </tr>	
	</tbody></table>
	</div>
  </div>
  </div>
</div>
</form>
</div>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js "></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#showUser").focus(function(){
				$.unit.open({
				 	labelField : "showUser",
					valueField : "userId",
					selectType : 4
				});
			});
			$("#sendButton").click(function(){
				alert($("#hidForm").attr("action"));
				$("#hidForm").submit();
			});
		});
	</script>
</body>
</html>