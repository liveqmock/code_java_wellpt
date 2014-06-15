<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Inbox Mail List</title>
<%@ include file="/pt/common/meta.jsp"%>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link href="${ctx}/resources/jqgrid/themes/ui.jqgrid.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/resources/jBox/Skins/Blue/jbox.css"/>
<link href="${ctx}/resources/jqgrid/themes/redmond/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
</head>
<body>
<div class="viewContent">
<style type="text/css">
  * {margin:0; padding:0}
 ul,li { list-style:none}
.box {
 	align:center;
	height:auto; 
}
 /* .tagMenu {height:28px; line-height:28px; background:#efefef; position:relative; border-bottom:1px solid #999}
 .tagMenu ul {position:absolute; left:10px; bottom:-1px; height:26px;background:#EFEFEF;border-bottom:1px solid #999}
 ul.menu li{float:left; margin-bottom:1px; line-height:16px; height:14px; margin:5px 0 0 -1px; border-left:1px solid #999; text-align:center; padding:0 12px; cursor:pointer} */
 .tagMenu {
    background: none repeat scroll 0 0 #EFEFEF;
    height: 35px;
    line-height: 35px;
    position: relative;
}

.tagMenu ul {
    background: none repeat scroll 0 0 #EFEFEF;
    left: 10px;
    margin: 9px 0 0;
    padding: 0;
    position: absolute;
}

ul.menu li {
    cursor: pointer;
    float: left;
    height: 14px;
    line-height: 16px;
    margin: 5px 0 0 -1px;
    padding: 0 12px;
    text-align: center;
}
 
 ul.menu li.current {
	 border:1px solid #f7f7f7; 
	 border-bottom:none;
	 background:#f7f7f7;
	 height:25px; 
	 line-height:26px; 
	 margin:0;
	 
 }
 ul.menu li{
 	color: #0F599C;
 }
 ul.menu li:hover{
  	color: #ff7200;
  }
 .content { padding:10px;}
 
 .mail_set {
	padding: 8px;
	text-align: right;
}

.mail_set button {
	cursor: pointer;
	display: inline-block;
	height: 17px;
	margin-left: 5px;
	padding: 0 3px;
	line-height: 17px;
	background: url("${ctx}/resources/theme/images/v1_icon.png") no-repeat 0 -165px;
	color: #0f599c;
	border: 1px solid #dee1e2;
	vertical-align: middle;
}
.content{
	background:#f7f7f7;
}

.common_title td{
    border-bottom: 1px solid #dddddd;
}
.title_next td{
    border-top: 1px solid #FFFFFF;
}
.common_title_second td{
    padding: 5px 0;
}
input[type="file"], input[type="image"], input[type="submit"], input[type="reset"], input[type="button"], input[type="radio"], input[type="checkbox"] {
    margin-bottom: 6px;
}
.common_title strong {
    color: #666666;
    font-weight: normal;
    padding-left: 5px;
}
.content input[type="text"]{
	font-family: "Microsoft YaHei";
    font-size: 12px;
    height: 25px;
    margin: 0;
    padding: 0;
}
.content select {
	font-family: "Microsoft YaHei";
    font-size: 12px;
    height: 25px;
    margin: 0 0 0 15px;
    padding: 3px;
    width: 90px;
}
.content #mailSize2{
	margin-left: 3px;
}
#receiveTime2, #receiveTime3{
	margin: 0;
}
.button_div {
    margin: 10px;
    text-align: center;
}
.box button {
    border: 1px solid #DEE1E2;
    color: #0F599C;
    font-family: "Microsoft YaHei";
    font-size: 12px;
    height: 22px;
    padding: 0 5px;
    background: url("${ctx}/resources/theme/images/v1_icon.png") repeat-x scroll 0 -165px transparent;
}
.button_div button{
	margin: 0 -3px;
	padding: 0 10px;
	width: 95px;
}
.box a{
	color: #0F599C;
}
.box a:hover{
	color: #ff7200;
	text-decoration: none;
}
button:hover{
	color: #ff7200;
}
.folder_title {
	background: none repeat scroll 0 0 #0F599C;
    border: 1px solid #0F599C;
    color: #FFFFFF;
}
.folder_title td {
    padding: 5px 0;
    text-indent: 5px;
    border: 1px solid #0F599C;
}
.folder_content  td {
    background: none repeat scroll 0 0 #FFFFFF;
    border: 1px solid;
    padding: 5px 0;
    text-indent: 5px;
}
#No3 #toolbar {
    margin: 0 0 5px;
}
#showgz{
 	font-size: 12px;
}
#showgz label{
	font-size: 12px;
	float: right;
}
#showgz td {
    padding: 1px 0;
}
#showgz .gz_tr_lefttd {
    padding-right: 7px;
    width: 123px;
}
#showgz .ckeditor {
    width: 385px;
}
/**********文件夹弹出框******/
#test_con3 table {
    display: block;
    margin-top: 15px;
    width: 100%;
}

#test_con3 tbody,#test_con3 td,#test_con3 tr{
    display: block;
    width:100%;
}

/*******个性签名谈出框*******/

.dialogcontent div {
    margin-top: 10px;
}
#test_con33 table {
    display: block;
    margin-left: 25px;
    width: 94%;
}
#test_con33 tbody {
    display: block;
}
.signName_tr {
    background: none repeat scroll 0 0 #F7F7F7;
    display: block;
    width: 100%;
}
.signName_tr td,.signText_tr td,.signText_tr{
    display: block;
    padding: 10px;
}
.signName_tr input {
    margin: 0;
    width: 96%;
}
.ckeditor {
    height: 215px;
    width: 96%;
}
 </style>
<%-- <script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"></script> --%>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript">
// document.write("<OBJECT id=\"dlgHelper\" CLASSID=\"clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b\" width=\"0px\" height=\"0px\"></OBJECT>");
</script>
<script src="${ctx}/pt/mail/js/mail.js"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>

<input  type="hidden" id="ptype" name="ptype" value="${ptype }"/>
<%-- <div style="padding:3px 2px;"><spring:message code="mail.label.mailSetC"/></div>  --%>
 <div class="box">
    <div  class="tagMenu">

        <ul class="menu">
            <li><spring:message code="mail.label.changgui"/></li>
            <li><spring:message code="mail.label.rule"/></li>
            <li><spring:message code="mail.label.myfolder"/></li>
            <li><spring:message code="mail.label.otherMail"/></li>
        </ul>
    </div>
    <div class="content">
        <div id="No0" class="layout" >
        
        <input type="hidden" id="cuuid" name="cuuid" value="${mailDS.uuid }"/>
        	<table width="100%">
        	<tr class="common_title">
        		<td align="left" colspan="2"><strong><spring:message code="mail.label.xianshi"/> </strong></td>
        	</tr>
        	<tr class="common_title_second title_next">
	        	<td align="right">
	        	<spring:message code="mail.label.addToPerson"/>：
	        	</td>
	        	<td align="left">
	        	<c:if test="${mailDS.addTo=='1' }">
	        	<input type="checkbox" id="addTo" name="addTo" checked="checked"/>
	        	</c:if>
	        	<c:if test="${mailDS.addTo!='1' }">
	        	<input type="checkbox" id="addTo" name="addTo"/>
	        	</c:if>
	        	<spring:message code="mail.label.supportTip"/>
	        	</td>
        	</tr>
        	<tr class="common_title_second">
	        	<td rowspan="2" valign="top" align="right">
	        		<spring:message code="mail.label.moveOrDeleteMail"/>：
	        	</td>
	        	<td align="left">
		        	<c:if test="${mailDS.listView=='1' }">
		        	<input id="listView" class="listView" name="listView" value="1" checked="checked" type="radio"/>
		        	</c:if>
		        	<c:if test="${mailDS.listView!='1' }">
		        	<input id="listView" class="listView" name="listView" value="1" type="radio"/>
		        	</c:if>
		        	<spring:message code="mail.label.readNextMail"/>
	        	</td>
        	</tr>
        	<tr class="common_title_second"> 
	        	<td align="left">
		        	<c:if test="${mailDS.listView=='2' }">
		        	<input id="listView" class="listView" name="listView" value="2" checked="checked" type="radio"/>
		        	</c:if>
		        	<c:if test="${mailDS.listView!='2' }">
		        	<input id="listView" class="listView" name="listView" value="2" type="radio"/>
		        	</c:if>
		        	<spring:message code="mail.label.returnNowList"/> 
	        	</td>
        	</tr>
        	<tr class="common_title">
        		<td align="left" colspan="2"><strong><spring:message code="mail.label.gexingSign"/></strong></td>
        	</tr>
        	
        	<tr class="common_title_second title_next">
	        	<td align="right">
	        	<spring:message code="mail.label.useGexingSign"/>：
	        	</td>
	        	<td align="left">
	        	<select onchange="signChange();" id="sign" name="sign">
	        		<c:if test="${''==mailDS.sign }">
	        		<option value="" selected="selected"><spring:message code="mail.label.notUse"/></option>
	        		</c:if>
	        		<c:if test="${''!=mailDS.sign }">
	        		<option value=""><spring:message code="mail.label.notUse"/></option>
	        		</c:if>
	        	<c:forEach items="${signList }" var="srow">
	        		<c:if test="${srow.uuid==mailDS.sign }">
	        		<option value="${srow.uuid }" selected="selected">${srow.signName }</option>
	        		</c:if>
	        		<c:if test="${srow.uuid!=mailDS.sign }">
	        		<option value="${srow.uuid }" >${srow.signName }</option>
	        		</c:if>
	        	</c:forEach>
	        	
	        	</select>
	        	</td>
        	</tr>
        	
        	<tr class="common_title_second">
	        	<td align="right">
	        	</td>
	        	<td align="left">
	        	<a href="#" onclick="addSign();"><spring:message code="mail.label.addGexingSign"/></a>
	        	</td>
        	</tr>
        	
        	
        	<tr class="common_title_second">
	        	<td align="right">
	        	</td>
	        	<td align="left" id="addSignLocation">
	        	<c:forEach items="${signList }" var="srow2">
	        	<c:if test="${mailDS.sign==srow2.uuid }">
	        	<table width="60%" class="showSignTable" id="${srow2.uuid }">
	        	<tr>
	        	<td width="100%">
	        	<div  style="width:100%;height:100px;border:1px solid blue;">${srow2.signText }</div>
	        	</td>
	        	</tr>
	        	<tr>
	        	<td align="left">
	        	<button onclick="singEdit('${srow2.uuid}','${srow2.signName }','${srow2.signText }');"><spring:message code="mail.btn.edit"/></button><button onclick="signDel('${srow2.uuid}');"><spring:message code="mail.btn.delete"/></button>
	        	</td>
	        	</tr>
	        	</table>
	        	</c:if>
	        	<c:if test="${mailDS.sign!=srow2.uuid }">
	        	<table width="60%" class="showSignTable" id="${srow2.uuid }" style="display: none;">
	        	<tr>
	        	<td width="100%">
	        	<div  style="width:100%;height:100px;border:1px solid blue;">${srow2.signText }</div>
	        	</td>
	        	</tr>
	        	<tr>
	        	<td align="left">
	        	<button onclick="singEdit('${srow2.uuid}','${srow2.signName }','${srow2.signText }');"><spring:message code="mail.btn.edit"/></button><button onclick="signDel('${srow2.uuid}');"><spring:message code="mail.btn.delete"/></button>
	        	</td>
	        	</tr>
	        	</table>
	        	</c:if>
	        	
	        	</c:forEach>
	        	
	        	</td>
        	</tr>
        	
        	
        	
        	<tr class="common_title">
        		<td align="left" colspan="2"><strong><spring:message code="mail.label.faxin"/></strong></td>
        	</tr>
        	<tr class="common_title_second title_next">
	        	<td rowspan="3">
	        	</td>
	        	<td align="left">
	        	<c:if test="${mailDS.sendFlag1=='1' }">
	        	<input type="checkbox" id="sendFlag1" name="sendFlag1" checked="checked"/>
	        	</c:if>
	        	<c:if test="${mailDS.sendFlag1!='1' }">
	        	<input type="checkbox" id="sendFlag1" name="sendFlag1"/>
	        	</c:if>
	        	<spring:message code="mail.label.faxin1"/>
	        	</td>
        	</tr>
        	<tr class="common_title_second">
	        	<td align="left">
	        	<c:if test="${mailDS.sendFlag2=='1' }">
	        	<input type="checkbox" id="sendFlag2" name="sendFlag2" checked="checked"/>
	        	</c:if>
	        	<c:if test="${mailDS.sendFlag2!='1' }">
	        	<input type="checkbox" id="sendFlag2" name="sendFlag2"/>
	        	</c:if>
	        	<spring:message code="mail.label.faxin2"/>
	        	</td>
        	</tr>
        	<tr class="common_title_second">
	        	<td align="left">
	        	<c:if test="${mailDS.sendFlag3=='1' }">
	        	<input type="checkbox" id="sendFlag3" name="sendFlag3" checked="checked"/>
	        	</c:if>
	        	<c:if test="${mailDS.sendFlag3!='1' }">
	        	<input type="checkbox" id="sendFlag3" name="sendFlag3"/>
	        	</c:if>
	        	<spring:message code="mail.label.faxin3"/>
	        	</td>
        	</tr>
        	
        	<tr class="common_title">
        		<td align="left" colspan="2"><strong><spring:message code="mail.label.replyOrSendOther"/></strong></td>
        	</tr>
        	
        	<tr class="common_title_second title_next">
	        	<td rowspan="2" valign="top" align="right">
	        	<spring:message code="mail.label.replyOrSendOther1"/>：
	        	</td>
	        	<td align="left">
	        	<c:if test="${mailDS.replyFlag=='1' }">
	        	<input id="replyFlag" class="replyFlag" name="replyFlag" value="1" checked="checked" type="radio"/>
	        	</c:if>
	        	<c:if test="${mailDS.replyFlag!='1' }">
	        	<input id="replyFlag" class="replyFlag" name="replyFlag" value="1" type="radio"/>
	        	</c:if>
	        	<spring:message code="mail.label.replyOrSendOther11"/>
	        	</td>
        	</tr>
        	<tr class="common_title_second">
	        	<td align="left">
	        	<c:if test="${mailDS.replyFlag=='2' }">
	        	<input id="replyFlag" class="replyFlag" name="replyFlag" value="2" checked="checked" type="radio"/>
	        	</c:if>
	        	<c:if test="${mailDS.replyFlag!='2' }">
	        	<input id="replyFlag" class="replyFlag" name="replyFlag" value="2" type="radio"/>
	        	</c:if>
	        	<spring:message code="mail.label.replyOrSendOther12"/>
	        	</td>
        	</tr>
        	
        	<tr class="common_title_second">
	        	<td rowspan="2" valign="top" align="right">
	        	<spring:message code="mail.label.repluOrSendOtherTitle"/>：
	        	</td>
	        	<td align="left">
	        	<c:if test="${mailDS.replyTitleFlag=='1' }">
	        	<input id="replyTitleFlag" class="replyTitleFlag" name="replyTitleFlag" value="1" checked="checked" type="radio"/>
	        	</c:if>
	        	<c:if test="${mailDS.replyTitleFlag!='1' }">
	        	<input id="replyTitleFlag" class="replyTitleFlag" name="replyTitleFlag" value="1" type="radio"/>
	        	</c:if>
	        	<spring:message code="mail.label.repluOrSendOtherTitle1"/> 
	        	</td>
        	</tr>
        	<tr class="common_title_second">
	        	<td align="left">
	        	<c:if test="${mailDS.replyTitleFlag=='2' }">
	        	<input id="replyTitleFlag" class="replyTitleFlag" name="replyTitleFlag" value="2" checked="checked" type="radio"/>
	        	</c:if>
	        	<c:if test="${mailDS.replyTitleFlag!='2' }">
	        	<input id="replyTitleFlag" class="replyTitleFlag" name="replyTitleFlag" value="2" type="radio"/>
	        	</c:if>
	        	<spring:message code="mail.label.repluOrSendOtherTitle2"/>
	        	</td>
        	</tr>
        	
        	
        	<tr class="common_title">
        		<td align="left" colspan="2"><strong><spring:message code="mail.label.mailAutoSendOther"/></strong></td>
        	</tr>
        	
        	<tr class="common_title_second title_next">
        	<td rowspan="4" valign="top">
        	</td>
        	<td align="left">
        	<c:if test="${mailDS.autoSendOtherFlag=='1' }">
        	<input onclick="nosendOther();" class="autoSendOtherFlag" id="autoSendOtherFlag" name="autoSendOtherFlag" value="1" checked="checked" type="radio"/>
        	</c:if>
        	<c:if test="${mailDS.autoSendOtherFlag!='1' }">
        	<input onclick="nosendOther();" class="autoSendOtherFlag" id="autoSendOtherFlag" name="autoSendOtherFlag" value="1" type="radio"/>
        	</c:if>
        	<spring:message code="mail.btn.close"/>
        	</td>
        	</tr>
        	<tr class="common_title_second">
        	<td align="left">
        	<c:if test="${mailDS.autoSendOtherFlag=='2' }">
        	<input onclick="yessendOther();" class="autoSendOtherFlag" id="autoSendOtherFlag" name="autoSendOtherFlag" value="2" checked="checked" type="radio"/>
        	</c:if>
        	<c:if test="${mailDS.autoSendOtherFlag!='2' }">
        	<input onclick="yessendOther();" class="autoSendOtherFlag" id="autoSendOtherFlag" name="autoSendOtherFlag" value="2" type="radio"/>
        	</c:if>
        	<spring:message code="mail.label.qiyong"/>
        	</td>
        	</tr>
        	<tr class="common_title_second">
        	<td align="left">
        	<spring:message code="mail.label.autoSendTo"/>：
        	<c:if test="${mailDS.autoSendOtherFlag=='1' }">
        	<input onclick="autoSendClick();" id="autoSendTo" name="autoSendTo" value="${mailDS.autoSendTo }" disabled="disabled" type="text"/>
        	</c:if>
        	<c:if test="${mailDS.autoSendOtherFlag!='1' }">
        	<input onclick="autoSendClick();" id="autoSendTo" name="autoSendTo" value="${mailDS.autoSendTo }"  type="text"/>
        	</c:if>
        	</td>
        	</tr>
        	<tr class="common_title_second">
        	<td align="left">
        	<spring:message code="mail.label.hasAotuoSendOther"/>，
        	<c:if test="${mailDS.autoSendOtherFlag=='1' }">
			<select disabled="disabled" id="hasAutoSendFlag" name="hasAutoSendFlag">
			<c:if test="${mailDS.hasAutoSendFlag=='1' }">
			<option value="1" selected="selected"><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			<c:if test="${mailDS.hasAutoSendFlag=='2' }">
			<option value="1" ><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2" selected="selected"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			<c:if test="${mailDS.hasAutoSendFlag=='3' }">
			<option value="1" ><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3" selected="selected"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			</select>
			</c:if>
			<c:if test="${mailDS.autoSendOtherFlag!='1' }">
			<select id="hasAutoSendFlag" name="hasAutoSendFlag">
			<c:if test="${mailDS.hasAutoSendFlag=='1' }">
			<option value="1" selected="selected"><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			<c:if test="${mailDS.hasAutoSendFlag=='2' }">
			<option value="1" ><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2" selected="selected"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			<c:if test="${mailDS.hasAutoSendFlag=='3' }">
			<option value="1" ><spring:message code="mail.label.hasAotuoSendOther1"/></option>
			<option value="2"><spring:message code="mail.label.hasAotuoSendOther2"/></option>
			<option value="3" selected="selected"><spring:message code="mail.label.hasAotuoSendOther3"/></option>
			</c:if>
			</select>
			</c:if>
        	</td>
        	</tr>
        	</table>
        	<div id="changgui" style="display: none;"></div>
        	<div class="mail_set"><center><button onclick="addDisplaySet();"><spring:message code="mail.btn.saveModify"/></button><button><spring:message code="mail.btn.cancel"/></button></center></div>
        	
        </div>
        <div id="No1" class="layout">
        <div id="gzdiv">
        <button onclick="createGz();"><spring:message code="mail.label.createRule"/></button>
        <label style="color: #808080;font-size: 12px;margin: 5px 0;"><spring:message code="mail.label.ruleDemo"/></label>
			<table width="100%"  >
			<tbody id="showRuleTable">
			<tr class="folder_title">
				<td width="60%">收信规则</td>
				<td width="15%">状态</td>
				<td width="15%">操作</td>
			</tr>
			<c:forEach items="${gzlist }" var="gzrow">
			<tr id="${gzrow.uuid }tr" class="folder_content">
			<td id="${gzrow.uuid }ruleName">
			<c:out value="${gzrow.ruleName}"/>
			</td>
			<td id="${gzrow.uuid }">
			<c:if test="${gzrow.qiyong=='1' }">
			<spring:message code="mail.label.hasQiyong"/>|<a href='#' onclick="ifQiyong('${gzrow.uuid }',2);"><spring:message code="mail.btn.close"/></a>
			</c:if>
			<c:if test="${gzrow.qiyong=='0' }">
			<spring:message code="mail.label.hasClose"/>|<a href='#' onclick="ifQiyong('${gzrow.uuid }',1);"><spring:message code="mail.label.qiyong"/></a>
			</c:if>
			</td>
			<td>
			<a href="#" onclick="editRule('${gzrow.uuid}','${gzrow.qiyong}','${gzrow.sendPerson1}','${gzrow.sendPerson2}','${gzrow.sendPerson3}','${gzrow.sendDomain1}','${gzrow.sendDomain2}','${gzrow.sendDomain3}','${gzrow.receivePerson1}','${gzrow.receivePerson2}','${gzrow.receivePerson3}','${gzrow.title1}','${gzrow.title2}','${gzrow.title3}','${gzrow.mailSize1}','${gzrow.mailSize2}','${gzrow.mailSize3}','${gzrow.receiveTime1}','${gzrow.receiveTime2}','${gzrow.receiveTime3}','${gzrow.operation}','${gzrow.moveFile1}','${gzrow.moveFile2}','${gzrow.setRead}','${gzrow.setStar}','${gzrow.sendOther1}','${gzrow.sendOther2}','${gzrow.sendOther3}','${gzrow.reply}','${gzrow.replytext}');">[修改]</a><a href="#" onclick="delRule('${gzrow.uuid}');">[删除]</a>
			</td>
			</tr>
			</c:forEach>
			</tbody>
			</table>
		</div>	
		
		<div id="showgz" style="display: none">
		<input type="hidden" id="guuid" name="guuid"/>
			<table >
			<tr>
			<td class="gz_tr_lefttd" align="right">
			<spring:message code="mail.label.ruleQiyong"/>:
			</td>
			<td align="left">
			<c:if test="${guize.qiyong=='1'||guize.qiyong==null }">
			<input  type="radio" id="qiyong" name="qiyong" value="1" checked="checked"/><spring:message code="mail.label.qiyong"/><input value="0" id="qiyong" name="qiyong" type="radio" /><spring:message code="mail.label.noqiyong"/>
			</c:if>
			<c:if test="${guize.qiyong!='1'&&guize.qiyong!=null }">
			<input  type="radio" id="qiyong" name="qiyong" value="1"/><spring:message code="mail.label.qiyong"/><input  type="radio" value="0" id="qiyong" name="qiyong" checked="checked"/><spring:message code="mail.label.noqiyong"/>
			</c:if>
			</td>
			</tr>
			<tr>
			<td class="gz_tr_lefttd" rowspan="6" valign="top" align="right">
			<spring:message code="mail.label.mailDaoda"/>:
			</td>
			<td align="left">
			<c:if test="${guize.sendPerson1=='1' }">
			<input type="checkbox" checked="checked" id="sendPerson1" name="sendPerson1"/>
			</c:if>
			<c:if test="${guize.sendPerson1!='1' }">
			<input type="checkbox" id="sendPerson1" name="sendPerson1"/>
			</c:if>
			<spring:message code="mail.label.ifFrom"/>
			<select id="sendPerson2" name="sendPerson2">
			<c:if test="${guize.sendPerson2=='1'||guize.sendPerson2==null }">
			<option value="1" selected="selected"><spring:message code="mail.label.baohan"/></option><option value="2"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			<c:if test="${guize.sendPerson2=='2' }">
			<option value="1"><spring:message code="mail.label.baohan"/></option><option value="2" selected="selected"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			</select>
			<input type="text" id="sendPerson3" name="sendPerson3" value="${guize.sendPerson3}"/>
			</td>
			</tr>
			<tr>
			<td align="left">
			<c:if test="${guize.sendDomain1=='1' }">
			<input type="checkbox" checked="checked" id="sendDomain1" name="sendDomain1"/>
			</c:if>
			<c:if test="${guize.sendDomain1!='1' }">
			<input type="checkbox" id="sendDomain1" name="sendDomain1"/>
			</c:if>
			<spring:message code="mail.label.ifsendDomain"/>
			<select id="sendDomain2" name="sendDomain2">
			<c:if test="${guize.sendDomain2=='1'||guize.sendDomain2==null }">
			<option value="1" selected="selected"><spring:message code="mail.label.baohan"/></option><option value="2"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			<c:if test="${guize.sendDomain2=='2' }">
			<option value="1"><spring:message code="mail.label.baohan"/></option><option value="2" selected="selected"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			</select>
			<input type="text" id="sendDomain3" name="sendDomain3" value="${guize.sendDomain3 }"/>
			</td>
			</tr>
			<tr>
			<td align="left">
			<c:if test="${guize.receivePerson1=='1' }">
			<input type="checkbox" checked="checked" id="receivePerson1" name="receivePerson1"/>
			</c:if>
			<c:if test="${guize.receivePerson1!='1' }">
			<input type="checkbox" id="receivePerson1" name="receivePerson1"/>
			</c:if>
			<spring:message code="mail.label.ifTo"/>
			<select id="receivePerson2" name="receivePerson2">
			<c:if test="${guize.receivePerson2=='1'||guize.receivePerson2==null }">
			<option value="1" selected="selected"><spring:message code="mail.label.baohan"/></option><option value="2"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			<c:if test="${guize.receivePerson2=='2' }">
			<option value="1"><spring:message code="mail.label.baohan"/></option><option value="2" selected="selected"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			</select>
			<input type="text" id="receivePerson3" name="receivePerson3" value="${guize.receivePerson3 }"/>
			</td>
			</tr>
			<tr>
			<td align="left">
			<c:if test="${guize.title1=='1' }">
			<input type="checkbox" checked="checked" id="title1" name="title1"/>
			</c:if>
			<c:if test="${guize.title1!='1' }">
			<input type="checkbox" id="title1" name="title1"/>
			</c:if>
			<spring:message code="mail.label.ifTitle"/>
			<select id="title2" name="title2">
			<c:if test="${guize.title2=='1'||guize.title2==null }">
			<option value="1" selected="selected"><spring:message code="mail.label.baohan"/></option><option value="2"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			<c:if test="${guize.title2=='2' }">
			<option value="1"><spring:message code="mail.label.baohan"/></option><option value="2" selected="selected"><spring:message code="mail.label.nobaohan"/></option>
			</c:if>
			</select>
			<input type="text" id="title3" name="title3" value="${guize.title3 }"/>
			</td>
			</tr>
			<tr>
			<td align="left">
			<c:if test="${guize.mailSize1=='1' }">
			<input type="checkbox" checked="checked" id="mailSize1" name="mailSize1"/>
			</c:if>
			<c:if test="${guize.mailSize1!='1' }">
			<input type="checkbox" id="mailSize1" name="mailSize1"/>
			</c:if>
			<spring:message code="mail.label.ifMailSize"/> 
			<select id="mailSize2" name="mailSize2">
			<c:if test="${guize.mailSize2=='1'||guize.mailSize2==null }">
			<option value="1" selected="selected"><spring:message code="mail.label.dadengyu"/></option><option value="2"><spring:message code="mail.label.shaoyu"/></option>
			</c:if>
			<c:if test="${guize.mailSize2=='2' }">
			<option value="1"><spring:message code="mail.label.dadengyu"/></option><option value="2" selected="selected"><spring:message code="mail.label.shaoyu"/></option>
			</c:if>
			</select>
			<input onkeypress="inputkeypress(this);" onkeyup="inputkeyup(this);" onblur="inputblur(this);"
			  type="text" id="mailSize3" name="mailSize3" value="${guize.mailSize3 }"/><spring:message code="mail.label.byte"/>
			</td>
			</tr>
			<tr>
			<td align="left">
			<c:if test="${guize.receiveTime1=='1' }">
			<input type="checkbox" checked="checked" id="receiveTime1" name="receiveTime1"/>
			</c:if>
			<c:if test="${guize.receiveTime1!='1' }">
			<input type="checkbox" id="receiveTime1" name="receiveTime1"/>
			</c:if>
			<spring:message code="mail.label.ifTime1"/><select id="receiveTime2" name="receiveTime2"><option value="01:00" selected="selected">01:00</option><option value="02:00">02:00</option><option value="03:00">03:00</option><option value="04:00">04:00</option><option value="05:00">05:00</option><option value="06:00">06:00</option><option value="07:00">07:00</option><option value="08:00">08:00</option><option value="09:00">09:00</option><option value="10:00">10:00</option><option value="11:00">11:00</option><option value="12:00">12:00</option><option value="13:00">13:00</option><option value="14:00">14:00</option><option value="15:00">15:00</option><option value="16:00">16:00</option><option value="17:00">17:00</option><option value="18:00">18:00</option><option value="19:00">19:00</option><option value="20:00">20:00</option><option value="21:00">21:00</option><option value="22:00">22:00</option><option value="23:00">23:00</option><option value="24:00">24:00</option></select><spring:message code="mail.label.ifTime2"/><select id="receiveTime3" name="receiveTime3"><option value="01:00" selected="selected">01:00</option><option value="02:00">02:00</option><option value="03:00">03:00</option><option value="04:00">04:00</option><option value="05:00">05:00</option><option value="06:00">06:00</option><option value="07:00">07:00</option><option value="08:00">08:00</option><option value="09:00">09:00</option><option value="10:00">10:00</option><option value="11:00">11:00</option><option value="12:00">12:00</option><option value="13:00">13:00</option><option value="14:00">14:00</option><option value="15:00">15:00</option><option value="16:00">16:00</option><option value="17:00">17:00</option><option value="18:00">18:00</option><option value="19:00">19:00</option><option value="20:00">20:00</option><option value="21:00">21:00</option><option value="22:00">22:00</option><option value="23:00">23:00</option><option value="24:00">24:00</option></select><label ><spring:message code="mail.label.ifTime3"/> </label>
			</td>
			</tr>
			
			<tr >
			<td class="gz_tr_lefttd" rowspan="8" valign="top" align="right">
			<spring:message code="mail.label.satifyAll"/>:
			</td>
			<td align="left">
			<c:if test="${guize.operation=='1'||guize.operation==null }">
			<input type="radio" onclick="opeClick(1);" class="operation" id="operation" name="operation" value="1" checked="checked"/><spring:message code="mail.label.donext"/><input onclick="opeClick(2);" class="operation" id="operation" name="operation" value="2" type="radio" style="margin-left: 10px;"/><spring:message code="mail.label.directDelete"/> 
			</c:if>
			<c:if test="${guize.operation=='2' }">
			<input type="radio" onclick="opeClick(1);" class="operation" id="operation" name="operation" value="1"/><spring:message code="mail.label.donext"/><input onclick="opeClick(2);" class="operation" id="operation" name="operation" value="2" type="radio" checked="checked" style="margin-left: 10px;"/><spring:message code="mail.label.directDelete"/> 
			</c:if>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<c:if test="${guize.moveFile1=='1' }">
			<input type="checkbox" checked="checked" id="moveFile1" name="moveFile1"/>
			</c:if>
			<c:if test="${guize.moveFile1!='1' }">
			<input type="checkbox" id="moveFile1" name="moveFile1"/>
			</c:if>
			<spring:message code="mail.label.mailMoveFolder"/>:
			<select id="moveFile2" name="moveFile2">
			<c:forEach items="${myfolders }" var="mfolder">
				<c:if test="${guize.moveFile2==mfolder.uuid }">
				<option value="${mfolder.uuid}" selected="selected">${mfolder.fname }</option>
				</c:if>
				<c:if test="${guize.moveFile2!=mfolder.uuid }">
				<option value="${mfolder.uuid}">${mfolder.fname }</option>
				</c:if>
			</c:forEach>
			</select>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<c:if test="${guize.setRead=='1' }">
			<input type="checkbox" checked="checked" id="setRead" name="setRead"/>
			</c:if>
			<c:if test="${guize.setRead!='1' }">
			<input type="checkbox" id="setRead" name="setRead"/>
			</c:if>
			<spring:message code="mail.label.setToRead"/>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<c:if test="${guize.setStar=='1' }">
			<input type="checkbox" checked="checked" id="setStar" name="setStar"/>
			</c:if>
			<c:if test="${guize.setStar!='1' }">
			<input type="checkbox" id="setStar" name="setStar"/>
			</c:if>
			<spring:message code="mail.label.setToStar"/>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<c:if test="${guize.sendOther1=='1' }">
			<input type="checkbox" checked="checked" id="sendOther1" name="sendOther1"/>
			</c:if>
			<c:if test="${guize.sendOther1!='1' }">
			<input type="checkbox" id="sendOther1" name="sendOther1"/>
			</c:if>
			<spring:message code="mail.label.mailSendTo4"/>:<input onblur="sendOther2Click2();" onclick="sendOther2Click();" type="text" id="sendOther2" name="sendOther2" value="${guize.sendOther2 }"/>
			</td>
			</tr>
			<tr>
			<td align="left" >
			<div id="showSendOther" style="border:1px solid blue;display: none;">
			<c:if test="${guize.sendOther3=='1'||guize.sendOther3==null }">
			<input type="radio" class="sendOther3" id="sendOther3" name="sendOther3" value="1" checked="checked"/><spring:message code="mail.label.baoliu"/>  <input type="radio" class="sendOther3" id="sendOther3" name="sendOther3" value="2"/><spring:message code="mail.label.shanchu"/>
			</c:if>
			<c:if test="${guize.sendOther3=='2' }">
			<input type="radio" class="sendOther3" id="sendOther3" name="sendOther3" value="1" /><spring:message code="mail.label.baoliu"/>  <input type="radio" class="sendOther3" id="sendOther3" name="sendOther3" value="2" checked="checked"/><spring:message code="mail.label.shanchu"/>
			</c:if>
			</div>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<c:if test="${guize.reply=='1' }">
			<input type="checkbox" checked="checked" id="reply" name="reply"/>
			</c:if>
			<c:if test="${guize.reply!='1' }">
			<input type="checkbox" id="reply" name="reply"/>
			</c:if>
			<spring:message code="mail.label.autoReply4"/>
			</td>
			</tr>
			
			<tr>
			<td align="left">
			<textarea id="replytext" name="replytext" onclick="replyTextclick();" onblur="replyTextclick2();" rows="10" cols="50">${guize.replytext }</textarea>
<!-- 			class="ckeditor"   -->
			</td>
			</tr>
			</table>
			<center><button onclick="saveRule();"><spring:message code="mail.btn.ncreate"/></button><button onclick="createGz2();"><spring:message code="mail.btn.cancel"/></button></center>
			</div>
		</div>
        <div id="No2" class="layout">
        <div class="button_div">
        <button id="button1" class="active" onclick="changeFolder(1);"><spring:message code="mail.label.sysFolder"/></button><button id="button2" onclick="changeFolder(2);"><spring:message code="mail.label.myfolder"/></button><button id="button3" onclick="changeFolder(3);"><spring:message code="mail.label.label4"/></button>
        </div>
        <div id="folder1">
        <table width="100%">
        <tr class="folder_title">
        <td width="20%" align="left">
        	<spring:message code="mail.label.folder4"/>
        </td>
        <td width="10%" align="center">
        	<spring:message code="mail.label.noRead"/>
        </td>
        <td width="10%" align="center">
        	<spring:message code="mail.label.totalMailN"/>
        </td>
        <td width="15%" align="center">
        	<spring:message code="mail.label.autoToHasRead"/>
        </td>
        <td width="35%" align="left">
        	<spring:message code="mail.label.autoClearTime"/>
        </td>
        <td width="10%" align="center">
        	<spring:message code="mail.label.operation"/>
        </td>
        </tr>
        <c:forEach items="${syslist }" var="sysrow">
        <tr  class="folder_content">
        <td>
        	<c:if test="${sysrow.sort==1 }">
        	<spring:message code="mail.label.receiveMailBox"/>
        	</c:if>
        	<c:if test="${sysrow.sort==2 }">
        	<spring:message code="mail.label.groupBox"/>
        	</c:if>
        	<c:if test="${sysrow.sort==3 }">
        	<spring:message code="mail.label.caogaoBox"/>
        	</c:if>
        	<c:if test="${sysrow.sort==4 }">
        	<spring:message code="mail.label.hasSendBox"/>
        	</c:if>
        	<c:if test="${sysrow.sort==5 }">
        	<spring:message code="mail.label.deleteBox"/>
        	</c:if>
        </td>
        <td id="${sysrow.sort }hassend" align="center">
        	<c:out value="${sysrow.noread }"></c:out>
        </td>
        <td id="${sysrow.sort }hasdel" align="center">
        	<c:out value="${sysrow.total }"></c:out>
        </td>
        <td align="center">
        	-
        </td>
        <td align="left">
        	<c:if test="${sysrow.sort==5 }">
        	<div id="${sysrow.sort }label">${sysrow.cleanDay }天|<a href="#" onclick="alterDay('${sysrow.cleanDay }','${sysrow.sort}');"><spring:message code="mail.btn.modify"/></a></div>
        	</c:if>
        	<c:if test="${sysrow.sort!=5 }">
        	-
        	</c:if>
        </td>
         <td align="center">
         <c:if test="${sysrow.sort==5 }">
        	<a href="#" onclick="clearMailBox2(${sysrow.sort})"><spring:message code="mail.btn.clear"/></a>
        </c:if>
        <c:if test="${sysrow.sort!=5 }">
        	-
        	</c:if>
        	</td>
        </tr>
        </c:forEach>
        </table>
        </div>
        
        
        <div id="folder2" style="display: none;">
        <div id="addFolder"></div>
			<table width="100%" >
			<tr id="ftr" class="folder_title">
			<td width="35%"><spring:message code="mail.label.folder4"/></td>
			<td width="15%"><spring:message code="mail.label.noRead"/></td>
			<td width="15%"><spring:message code="mail.label.totalMailN"/></td>
			<td width="35%"><spring:message code="mail.label.operation"/></td>
			</tr>
			<c:forEach items="${myfolders }" var="mrow">
			<tr class="folder_content" id="${mrow.uuid }tr">
			<td id="${mrow.uuid }name">${mrow.fname }</td>
			<td id="${mrow.uuid }noread">${mrow.noread }</td>
			<td id="${mrow.uuid }total">${mrow.total }</td>
			<td><a href="#" onclick="openFolder('${mrow.fname }','${mrow.uuid }','0');"><spring:message code="mail.label.modifyName"/></a>
			<a href="#" onclick="clearFolder('${mrow.uuid }');"><spring:message code="mail.btn.clear"/></a>
			<a href="#" onclick="deleteFolder('${mrow.uuid }','${mrow.fname }','0');"><spring:message code="mail.btn.delete"/></a></td>
			</tr>
			</c:forEach>
			<tr>
			<td colspan="4" align="left" style="padding-top: 10px;padding-left: 2px;"><button onclick="openNewFileFox('0');"> <spring:message code="mail.label.addFolder"/></button></td>
			</tr>
			</table>
			</div>
			
			
			<div id="folder3" style="display: none;">
			<table width="100%" >
			<tr id="ftr2"  class="folder_title">
			<td width="35%"><spring:message code="mail.label.label4"/></td>
			<td width="15%"><spring:message code="mail.label.noRead"/></td>
			<td width="15%"><spring:message code="mail.label.totalMailN"/></td>
			<td width="35%"><spring:message code="mail.label.operation"/></td>
			</tr>
			<c:forEach items="${mylabels }" var="mrow2">
			<tr id="${mrow2.uuid }tr2" class="folder_content">
			<td id="${mrow2.uuid }name2"><span id="${mrow2.uuid }name22"  style="background-color:${ mrow2.color};display: block;float: left;height: 10px;margin-left: 10px;margin-top: 4px;width: 10px; border-radius: 4px 4px 4px 4px;" onclick="selLabelColor('${mrow2.uuid }');" ></span><font color="${ mrow2.color}" >${mrow2.fname }</font></td> 
<%-- 			 --%>
			<td id="${mrow2.uuid }noread2">${mrow2.noread }</td>
			<td id="${mrow2.uuid }total2">${mrow2.total }</td>
			<td><a href="#" onclick="openFolder('${mrow2.fname }','${mrow2.uuid }','2');"><spring:message code="mail.label.modifyName"/></a>
			<a href="#" onclick="deleteFolder('${mrow2.uuid }','${mrow2.fname }','2');"><spring:message code="mail.btn.delete"/></a></td>
			</tr>
			</c:forEach>
			<tr>
			<td colspan="4" align="left" style="padding-left: 2px;padding-top: 10px;"><button onclick="openNewFileFox('2');"> <spring:message code="mail.label.addLabel"/></button></td>
			</tr>
			</table>
			</div>
		</div>
    
     <div id="No3" class="layout">
     	<input type="hidden" id="contextPath" value="${ctx}"></input>
		<div id="msg" ></div>
		<div id="toolbar">
		
		<div align="right">
<%-- 			<button onclick="window.location.href='${ctx}/mail/mailother_first.action'"><spring:message code="mail.label.addOtherMailN"/></button> --%>
			<button onclick="addOtherMail();"><spring:message code="mail.label.addOtherMailN"/></button>
		</div>
		</div>
		<table width="100%">
		
			<tr  class="folder_title">
				<td  width="30%"><spring:message code="mail.label.otherMailN"/></td>
				<td  width="20%"><spring:message code="mail.label.noReadMail1"/></td>
				<td  width="20%"><spring:message code="mail.label.totalMailN"/></td>
				<td  width="30%"><spring:message code="mail.label.operation"/></td>
			</tr>
	
		<c:forEach items="${dataList }" var="row">
			<tr class="folder_content">
			<td>
			<c:out value="${row.mailName }"></c:out>
			</td>
			<td >
			<span id="${row.uuid }noread" ><c:out value="${row.noRead }"></c:out></span>
			</td>
			<td >
			<span id="${row.uuid}total" ><c:out value="${row.total }"></c:out></span>
			</td>
			<td>
<%-- 			<a href="#" onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=6&mailname=${row.mailName }&pageNo=0&mtype=0'"><spring:message code="mail.label.otherRead"/></a> --%>
			<a href="#" onclick="read_othermail('${row.mailName}');"><spring:message code="mail.label.otherRead"/></a>
			<a href="#" onclick="receive('${row.uuid }','${row.mailName}','${row.password }','${row.pop3 }','${row.pop3Port }');"><spring:message code="mail.label.receiveGetMail"/></a>
			<a href="#" class="config_othermail_set" onclick="configOthermail('${row.uuid }');"><spring:message code="mail.label.otherSet"/></a>
<%-- 			<a href="#" class="config_othermail_set" onclick="window.location.href='${ctx}/mail/mailother_next22.action?uuid=${row.uuid }&delFlag=1'"><spring:message code="mail.label.otherSet"/></a> --%>
			
			</td>
			</tr>
		</c:forEach>
		</table>
     </div>
 </div>
 </div>
 
<div id="alterDayDiv" style="display: none;">
<input type="hidden" id="wstatus"/>
<table>
<tr>
<!-- <td> -->
<!-- "已删除"系统自动清理时间 -->
<!-- </td> -->
</tr>
<tr>
<td>
<input id="ddays"  name="ddays"  class="ddays" type="radio" value="30"/>30天 (默认)
</td>
</tr>
<tr>
<td>
<input id="ddays" name="ddays" class="ddays" type="radio" value="14"/>14天 
</td>
</tr>
<tr>
<td>
<input id="ddays" name="ddays" class="ddays" type="radio" value="7"/>7天
</td>
</tr>
<tr>
<td>
<input id="ddays" name="ddays" class="ddays" type="radio" value="0"/>每次退出邮箱自动清空
</td>
</tr>
</table>
</div>
<!-- <script type="text/javascript" -->
<%-- 	src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script> --%>
<!-- <script type="text/javascript" -->
<%-- 	src="${ctx}/resources/pt/js/common/jquery.ckeditor.js"></script> --%>
<script type="text/javascript">
	 $(document).ready(function(){
	// 		 $('.colorpicker').colorpicker({
	// 				format: 'hex'
	// 			}).on('changeColor', function(ev){  $(this).style.backgroundColor = ev.color.toHex();});
			 
			 var ptype=$("#ptype").val();
			 
			 if(ptype==''||ptype==null){
			    $("ul.menu li:first-child").addClass("current");
				 
			    $("div.content").find("div.layout:not(:first-child)").hide();
			 }else{
				 var c = $("ul.menu li");
				 $("ul.menu li").each(function (){
					 var index = c.index(this);
					if(index==ptype){
						 //var c = $("ul.menu li");
						 var p = idNumber("No");
						 show(c,ptype,p);
					}
				});
			 }
		    //$("div.content div.layout").attr("id", function(){return idNumber("No")+ $("div.content div.layout").index(this)});
		    $("ul.menu li").click(function(){
		        var c = $("ul.menu li");
		        var index = c.index(this);
		        var p = idNumber("No");
		        show(c,index,p);
		    });
		    
		    function show(controlMenu,num,prefix){
		        var content= prefix + num;
		        $('#'+content).siblings().hide();
		        $('#'+content).show();
		        controlMenu.eq(num).addClass("current").siblings().removeClass("current");
		    };
		 
		    function idNumber(prefix){
		        var idNum = prefix;
		        return idNum;
		    };
		 });
	 
	 
	//清理时间修改
	/*
		function daySubmit(v, h, f){
				var h = $("#alterDayDiv");
//			if (v == 1) {
				var day='0';
				var wstatus=h.find("#wstatus").val();
				alert(h.find(".ddays").length);
				h.find(".ddays").each(function(){
					alert($(this).attr("checked"));
					if($(this).attr("checked")=="checked"){
						day=$(this).val();
					}
				});
				alert("day=="+day);
				$.ajax({
					type : "POST",
					url : contextPath+"/mail/set_clean_day.action",
					data : "status="+wstatus+'&days='+day,
					dataType : "text",
					success : function callback(result) {
						var s=day+global.day+"|<a href='#' onclick=alterDay('"+day+"','"+wstatus+"')>"+global.modify+"</a>";
						$("#"+wstatus+"label").html(s);
						oAlert(result);
						closeDialog();
					}
				});
//			}
			return true;
		}
	*/
	
		
</script>
<div id="setMailDiv" style="display: none;"></div>
</div>
</body>
</html>