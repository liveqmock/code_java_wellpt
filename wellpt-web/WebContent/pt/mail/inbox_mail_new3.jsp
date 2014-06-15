<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>写信</title>
<%@ include file="/pt/common/meta.jsp"%>
<style type="text/css">
 * {margin:0; padding:0}
 ul,li { list-style:none}
 .tagMenu {
 	height:28px; 
 	line-height:28px; 
/*  	background:#efefef;  */
 	position:relative; 
/*  	border-bottom:1px solid #999; */
 }
/*  .tagMenu ul { */
/*     margin: 0; */
/*   } */
/*  ul.menu li {float:left; margin-bottom:1px; line-height:16px; height:14px; margin:5px 0 0 -1px; border-left:1px solid #999; text-align:center; padding:0 0px; cursor:pointer;list-style: none outside none;} */
/*  ul.menu li.current { */
/*  	margin: 0 0 0 5px; */
/*  	height: 29px; */
/*     line-height: 26px; */
/*     border: none; */
/* } */
 .content { padding:0px}
 #barImg{
 	padding: 5px;
 	cursor: pointer;
 }
 #rightf {
    border: 1px solid #B6B6B6;
 }
 ._delete_input {
	cursor: pointer;
	border: 1px none;
	width: 42px;
	font-size: 12px;
	margin-left: 0px;
	background: none repeat scroll 0 0 transparent;
	color: #4B9AD2;
	margin-top: 6px;
}
.files_div p {
    color: #4B9AD2;
    cursor: pointer;
    float: left;
    font-size: 12px;
    margin: 4px 0 0 32px;
    text-decoration: none;
}
 </style>
 
 <link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/jqgrid/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/layout/jquery.layout.css" />
<link href="${ctx}/resources/pt/css/mailTree.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/fileupload/jquery.fileupload-ui.css" />
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/fileupload/fileupload.css" />
<link href="${ctx}/pt/mail/js/syronex-colorpicker.css" rel="stylesheet" />
<link href="${ctx}/resources/theme/css/wellnewoa.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${ctx}/pt/mail/js/jquery.coolfieldset.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/pt/mail/js/jquery.coolautosuggest.css" />
<link href="${ctx}/resources/pt/css/write_mail.css" rel="stylesheet" />

	



<script src="${ctx}/resources/jquery/jquery.js"></script>
<script src="${ctx}/resources/jqueryui/js/jquery-ui.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.autocomplete.js"/> --%>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.alerts.js"/>
<script type="text/javascript" src="${ctx}/pt/mail/js/syronex-colorpicker.js"></script>

<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>	
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.fileuploader.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/uuid.js"></script>	

<!--<script type="text/javascript" src="${ctx}/resources/validform/plugin/swfupload/swfuploadv2.2.js"></script>-->

<!--<script type="text/javascript"-->
<!--	src="${ctx}/pt/mail/js/swfupload.swfobject.js"></script>-->
<!--<script type="text/javascript"-->
<!--	src="${ctx}/pt/mail/js/swfupload.queue.js"></script> -->
<!--	<script type="text/javascript" src="${ctx}/pt/mail/js/mail_swfupload.js"></script>-->
<!--<script type="text/javascript" src="${ctx}/pt/mail/js/fileprogress.js"></script>-->
<!--<script type="text/javascript" src="${ctx}/pt/mail/js/handlers.js"></script>-->

<script type="text/javascript" src="${ctx}/pt/mail/js/jquery.colorpicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/mail/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/mail.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/jquery.coolautosuggest.js"></script>
<script type="text/javascript" src="${ctx}/pt/mail/js/jquery.coolfieldset.js"></script>
<script src="${ctx}/resources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/timers/jquery.timers.min.js"></script>
</head>

<body onload="wmailOnLoad();">
<div class="mail_css_bg">
<div class="mail_css">
<!-- <div id="msg" ></div> -->
<input type="hidden" id="contextPath" value="${ctx}"></input>
<!--<form id="form" name="form" action="${ctx}/mail/sendMail.action" method="post"enctype="multipart/form-data">-->
<input type="hidden" id="attachment"
	name="attachment" value="${mail.attachment }" /> <input type="hidden"
	id="fileMid" name="fileMid" value="${mail.fileMid }" /> <input
	type="hidden" value="${mail.isSend }" id="isSend" name="isSend" />
	<input type="hidden" value="${mail.mtype }" id="mtype" name="mtype" />
	<input type="hidden" id="mailType" name="mailType" value="${mail.mailType }" />
<input type="hidden" id="uuid" name="uuid" value="${mail.uuid}"/>
<input type="hidden" id="mailname" name="mailname" value="${mailname}"/>
<input type="hidden" id="rel" name="rel" value="${rel}"/>
<input type="hidden" id="scolor" name="scolor" value="${mail.scolor}" />
<input type="hidden" id="groupName" name="groupName" value="${mail.groupName}"/>
<input type="hidden" id="mid" name="mid" value="${mail.mid}"/>
<input type="hidden" id="sendFlag1" name="sendFlag1" value="${sendFlag1}"/>
<input type="hidden" id="dbFiles" name="dbFiles" value="${dbFiles}"/>

	 
	<input  type="hidden" id="ccuserids" name="ccuserids" />
	<input  type="hidden" id="bccuserids" name="bccuserids" />
	<input  type="hidden" id="touserids" name="touserids" />
	<input  type="hidden" id="bcc_temp" name="bcc_temp" />
	<input  type="hidden" id="cc_temp" name="cc_temp" />
	<input  type="hidden" id="to_temp" name="to_temp" />
	<input  type="hidden" id="mtarget" name="mtarget" />
	<input  type="hidden" id="jto" name="jto" value="${mail.to }" />
	<input  type="hidden" id="jcc" name="jcc" value="${mail.cc }" />
	<input  type="hidden" id="jbcc" name="jbcc" value="${mail.bcc }" />
	<input  type="hidden" id="status" name="status" value="${mail.status }" />
	<input  type="hidden" id="mailconfig" name="mailconfig" value="${mailconfig }" />
<!-- 	<input  type="hidden" id="fileupload" name="fileupload" value="1" /> -->
	<input  type="hidden" id="tcb" name="tdb" />
<!-- 	<center><label  id="msg" style="color: red;"></label></center> -->
<div class="mail_operate2">
	<div class="form_header">
		<!--标题-->
		<div class="form_title">
			<h2><spring:message code="mail.label.writeMail" /></h2>
			<div class="form_close" title="关闭" onclick="window.close();"></div>
		</div>
		<div id="toolbar" class="form_toolbar">
			<div class="form_operate">
					<button id="fasong" type="button" onclick="return check('1');"><spring:message code="mail.btn.send" /></button>
					<button style="display: none"  onclick=" check('0');"><spring:message code="mail.btn.fsend" /></button>
					<button id="cuncaogao" type="button" onclick=" check('2');"><spring:message code="mail.btn.savecaogao" /></button>
					<button style="display: none"  onclick="rSelect();"><spring:message code="mail.btn.reselect" /> </button>
					<button style="display: none" type="button" onclick="openSDLayer('${nyear}','${nmonth }','${nday }','${nhour }','${nsec }','addFolder','test_con3');return false;"><spring:message code="mail.btn.dingshisend" /></button> 
<%-- 				<button id="btn_close" onclick="window.opener=null;window.close();" type="button"><spring:message code="mail.btn.close" /></button> --%>
					<c:if test="${rel!=''&&rel!=null }">
						<button style="display: none" onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${pageNo}&mtype=${mtype}&mailname=${mailname}';return false;"><spring:message code="mail.btn.return" /></button>
						<button onclick="history.go(-1);"><spring:message code="mail.btn.return" /></button>
					</c:if>
			</div>
		</div>
	</div>
	
<%-- <button style="display: none" id="fasong" type="submit" onclick=" check('1');"><spring:message code="mail.btn.send" /></button> --%>
<%-- <button style="display: none"  onclick=" check('0');"><spring:message code="mail.btn.fsend" /></button> --%>
<%-- <button style="display: none" id="cuncaogao" type="submit" onclick=" check('2');"><spring:message code="mail.btn.savecaogao" /></button> --%>
<%-- <button style="display: none"  onclick="rSelect();"><spring:message code="mail.btn.reselect" /> </button>  --%>
<%-- <button style="display: none" type="submit" onclick="openSDLayer('${nyear}','${nmonth }','${nday }','${nhour }','${nsec }','addFolder','test_con3');return false;"><spring:message code="mail.btn.dingshisend" /> </button> --%>

</div>
<!-- <div id="addFolder"></div> -->
<!-- <div id="addFolder3"></div> -->
<div  class="mail_content">
<!-- <div class="form_content"></div> -->
		 <!-- 动态表单 -->
		<div id="dyform"><div class="dytable_form">
			<div class="post-sign">
				<div class="post-detail">
					<table edittype="1" id="001" tabletype="1" width="100%">
						<tbody>
							<tr>
    <th width="80%" valign="top" scope="col" id="frmTitle">
    
		<table cellpadding="2" cellspacing="2" class="tableForm" >
			<tr class="odd">
				<td class="Label"><a id="receiveMail" href="#" onclick="openMailUser('to','touserids','mailconfig');"><spring:message code="mail.label.to" /> </a></td>
				<td><%-- <textarea style="width:730px;" id="to" name="to" onkeyup="addressKey(this,event);"  onclick="setValue('to');"  >${mail.to }</textarea> --%>
 					<input name="to" type="text" style="margin-top:6px;" id="to"     onclick="setValue('to');" 
					size="125" value="${mail.to }"  />
 				</td>
			</tr>
			<tbody id="ffshow">
			<c:if test="${mail.cc==''||mail.cc==null}">
			<tr id="mcc"  style="display: none">
				<td class="Label"><a href="#" id="copyMail" onclick="openMailUser('cc','ccuserids','mailconfig');"><spring:message code="mail.label.cc" /> </a></td>
				<td ><input name="cc" type="text" style="margin-top:6px;" id="cc"  
					size="125" value="${mail.cc }" onclick="setValue('cc');" /></td>
			</tr>
			</c:if>
			<c:if test="${mail.cc!=''&&mail.cc!=null }">
			<tr id="mcc" >
				<td class="Label"><a href="#" id="copyMail" onclick="openMailUser('cc','ccuserids','mailconfig');"><spring:message code="mail.label.cc" /></a></td>
				<td ><input name="cc" type="text" style="margin-top:6px;" id="cc"  
					size="125" value="${mail.cc }" onclick="setValue('cc');" /></td>
			</tr>
			</c:if>
			<c:if test="${mail.bcc==''||mail.bcc==null }">
			<tr id="mbcc"  class="odd" style="display: none">
				<td class="Label"><a href="#" onclick="openMailUser('bcc','bccuserids','mailconfig');"><spring:message code="mail.label.bcc" /> </a></td>
				<td ><input name="bcc" type="text" id="bcc" 
					 size="125" onclick="setValue('bcc');" value="${mail.bcc }" /></td>
			</tr>
			</c:if>
			<c:if test="${mail.bcc!=''&&mail.bcc!=null }">
			<tr id="mbcc"  class="odd" >
				<td class="Label"><a href="#" onclick="openMailUser('bcc','bccuserids','mailconfig');"><spring:message code="mail.label.bcc" /> </a></td>
				<td ><input name="bcc" type="text" id="bcc" 
					 size="125" onclick="setValue('bcc');" value="${mail.bcc }" /></td>
			</tr>
			</c:if>
			<tr>
				<td class="Label"></td>
				<td >
				<table>
					<tr>
					<c:if test="${mail.cc==''||mail.cc==null }">
						<td id="addCc" ><a href="#"  onclick="addCc();" ><spring:message code="mail.label.addcc" /> </a></td>
						<td  id="delCc" style="display: none"><a href="#" 
							onclick="delCc();"><spring:message code="mail.label.delcc" /> </a></td>
					</c:if>
					<c:if test="${mail.cc!=''&&mail.cc!=null }">
						<td id="addCc" style="display: none"><a href="#"  onclick="addCc();" ><spring:message code="mail.label.addcc" /> </a></td>
						<td  id="delCc" ><a href="#" 
							onclick="delCc();"><spring:message code="mail.label.delcc" /> </a></td>
					</c:if> 
							<c:if test="${mail.bcc!=''&&mail.bcc!=null }">
						<td  id="addBcc" style="display: none"><a href="#" onclick="addBcc();"><spring:message code="mail.label.addbcc" /> </a></td>
						<td  id="delBcc">
						<a href="#"
							onclick="delBcc();"><spring:message code="mail.label.delbcc" /></a></td>
							</c:if>
							<c:if test="${mail.bcc==''||mail.bcc==null }">
						<td  id="addBcc"><a href="#" onclick="addBcc();"><spring:message code="mail.label.addbcc" /> </a></td>
						<td style="display: none" id="delBcc">
						<a href="#"
							onclick="delBcc();"><spring:message code="mail.label.delbcc" /></a></td>
							</c:if>
						<td style="display: none"  id="fsendsplid">|</td>
						<td style="display: none" id="fsend"><a href="#" onclick=" check('0');"><spring:message code="mail.btn.fsend" /></a>
						</td>
						
						
						
					</tr>
				</table>
				</td>
			</tr>
			</tbody>
			<tr class="odd">
				<td  class="Label"></td>
				<td><input id="ggmail"  name="ggmail"  type="checkbox" onclick="changMailType();"  size="100" /><spring:message code="mail.label.groupMail" />
				
				<input id="ffmail"  name="ffmail" type="checkbox" onclick="changMailType();"  size="100" /><spring:message code="mail.btn.fsend" />
				<input id="ddmail"  name="ddmail" type="checkbox" onclick="changMailDs();"  size="100" /><spring:message code="mail.btn.dingshisend" />
				<span id="nymdhs_span" style="display: none"><spring:message code="mail.label.inputDate" />:<input type="text" id="nymdhs" name="nymdhs" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:{%m+5}'});" value="${mail.nymdhs }"></input></span>
				</td>
			</tr>
			<tr style="display: none" class="odd">
				<td  class="Label"></td>
				<td >
				<spring:message code="mail.label.inputDate" />:<input type="text" id="nymdhs" name="nymdhs" onclick="WdatePicker({dateFmt:'yyyy-MM-dd H:m'});" value="${mail.nymdhs }"></input>
				</td>
			</tr>
			<tr>
				<td  class="Label"><spring:message code="mail.label.title" /></td>
				<td ><input id="subject" style="color: <c:out value="${mail.scolor }"/>" name="subject" type="text" size="125"
					value="${mail.subject }" />
					<div id="subcolor">
						<div style="display: none;" class="colors">
							<span onclick="selColor('black');" style="background-color:black;" class="selectcolor"></span>
							<span onclick="selColor('red');" style="background-color:red " class="selectcolor"></span>
							<span onclick="selColor('blue');" style="background-color:blue " class="selectcolor"></span>
							<span onclick="selColor('orange');" style="background-color:orange " class="selectcolor"></span>
							<span onclick="selColor('green');" style="background-color:green " class="selectcolor"></span>
							<span onclick="selColor('yellow');" style="background-color:yellow " class="selectcolor"></span>
							<span onclick="selColor('purple');" style="background-color:purple " class="selectcolor"></span>
							<span onclick="selColor('silver');" style="background-color:silver " class="selectcolor"></span>
							<span onclick="selColor('tan');" style="background-color:tan " class="selectcolor"></span>
							<span onclick="selColor('maroon');" style="background-color:maroon;" class="selectcolor"></span>
							<span onclick="selColor('olive');" style="background-color:olive;" class="selectcolor"></span>
							<span onclick="selColor('gray');" style="background-color:gray;" class="selectcolor"></span>
							<span onclick="selColor('lime');" style="background-color:lime;" class="selectcolor"></span>
						</div>
					</div>
					<div style="display:none;" id="color_picker"></div>
				</td>
			</tr>

			<tr class="odd">
				<td class="Label"></td>
				<td id="mail_file_upload"><!-- <input type="hidden" id="text_title" /> -->
<!-- 					<div id="mail_file_upload"></div> -->
				</td>
			</tr>
			
			
			<tr >
				<td   valign="top"><spring:message code="mail.label.text" /></td>
				<td class="ckediter_td"><textarea id="text" name="text"  class="ckeditor" height="400px" type="simple" >${mail.text }</textarea></td>
			</tr>
			<tr class="odd">
				<td class="Label"><spring:message code="mail.label.from" /></td>
				<td>			
				<div id="sendPerson"><select id="from" name="from">
<c:forEach items="${flist }" var="frow">
<c:if test="${mail.from==frow.username }">
<option value="${frow.username }" selected="selected">${frow.username }</option>
</c:if>
<c:if test="${mail.from!=frow.username }">
<option value="${frow.username }">${frow.username }</option>
</c:if>
</c:forEach>
</select></div>
				</td>
			</tr>
		</table>
		
	</th>
    <th width="1%" valign="middle" scope="col" >
		<div id="topLink" ><span  onclick="switchSysBar()"   id=switchPoint title=关闭/打开><img  src="${ctx}/pt/mail/js/left.gif" name="barImg" id="barImg"/></span></div>
	</th>
    <th width="13%"  valign="top" scope="col" id="rightf">
 <div class="box"  style="width:177px;height:535px;OVERFLOW-Y: auto; OVERFLOW-X:hidden;margin-top:3px;" align="left">
    <div  class="tagMenu">
        
        <ul class="menu">
            <li><spring:message code="mail.label.contactor" /></li>
           <!--  <li>通讯录</li> -->
        </ul>
    </div>
    <div class="content">
        <div class="layout searchDiv">
        	<input type="text" id="cname" name="cname" ></input>
        	<img onclick="queryContactor();" class="searchIcon" src="${ctx }/resources/theme/images/v1_search_icon.png" alt="" />
			<img  onclick="openLayer('addFolder3','test_con33');" style="display:none;cursor: pointer;" src="${ctx }/pt/mail/js/add.gif" alt="" />
        	<ul id="treeContactor" class="ztree">
			<c:forEach items="${contactor }" var="cr">
				<li><a href="#" ondblclick="setToMail('${cr.mailName }');"> <c:out value=" ${fn:substringBefore(cr.mailName , '<')} "></c:out> </a></li>
			</c:forEach>
			</ul>
        </div>
        <div class="layout">
        	<input type="text" id="tname" name="tname" ></input><img style="cursor: pointer;" src="${ctx }/pt/mail/js/search.gif" alt="" />&nbsp;&nbsp;<img  onclick="" style="cursor: pointer;" src="${ctx }/pt/mail/js/add.gif" alt="" />
		</div>

    </div>
 </div>
	</th>
  </tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
  
<%-- <div class="mail_operate2">
<c:if test="${rel!=''&&rel!=null }">
	<button onclick="window.location.href='${ctx}/mail/mail_box_list.action?rel=${rel }&pageNo=${pageNo}&mtype=${mtype}&mailname=${mailname}';return false;"><spring:message code="mail.btn.return" /></button>
</c:if>
<button id="fasong" type="submit" onclick="return check('1');"><spring:message code="mail.btn.send" /></button> --%>
<%-- <button style="display: none" ><spring:message code="mail.btn.fsend" /></button> --%>
<%-- <button id="cuncaogao" type="submit" onclick="return check('2');"><spring:message code="mail.btn.savecaogao" /></button> --%>
<%-- <button style="display: none"  onclick="rSelect();"><spring:message code="mail.btn.reselect" /> </button> --%>
<%-- <button style="display: none" type="submit" onclick="openSDLayer('${nyear}','${nmonth }','${nday }','${nhour }','${nsec }','addFolder','test_con3');return false;"><spring:message code="mail.btn.dingshisend" /></button> --%>

<%-- <button style="display: none"  onclick="rSelect();"><spring:message code="mail.btn.close" /></button> --%>
<!--</form>-->




	<script type="text/javascript" src="${ctx}/resources/ckeditor4.1/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>	
 <script type="text/javascript">



$(document).ready(function(){
	var availableTags = new Array();
	$.ajax({
		type : "POST",
		async : false,
		url : contextPath+'/mail/get_all_tips.action',
		data : [],
		dataType : "text",
		success : function callback(result) {
			result = result.replace(/&lt;/g,"<").replace(/&gt;/g,">");
			availableTags=result.split(",");
		}
	});		
	$("#to").Autocomplete(availableTags);
	$("#cc").Autocomplete(availableTags);
	$("#bcc").Autocomplete(availableTags);
			 
	
	$('#subcolor').live("click",function(){
		if($(".colors").css("display")=="block"){
			$(".colors").css("display","none");
		}else if($(".colors").css("display")=="none"){
			$(".colors").css("display","block");
		}
		
	});
	//上传邮件附件
	elementID = "mail_file_upload_ctlID";
	var fileupload = new WellFileUpload(elementID);
	var dbFiles = [];
	<c:if test="${dbFiles != '' and dbFiles != null}">
	 dbFiles = ${dbFiles};
	</c:if>
	for(var i in dbFiles){
		dbFiles[i].fileName = dbFiles[i].filename;
	}
	fileupload.init(false,$("#mail_file_upload"),false, true, dbFiles);
});
function selColor(color){
	 $("#scolor").val(color);
     $("#subject").css("color",color);
}
</script> 

</div>
</div>
<div class="body_foot"></div>
</body>
</html>
