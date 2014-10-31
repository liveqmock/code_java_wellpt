<%@page import="com.wellsoft.pt.basicdata.ca.service.FJCAAppsService"%>
<%@page import="com.wellsoft.pt.core.resource.Config"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="/wellpt/tags/cms" prefix="cm"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="/pt/common/taglibs.jsp" %>
<%@ include file="/pt/common/meta.jsp"%>
<title>${pageTitle}</title>
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
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" href="${ctx}/resources/pt/css/jquery.jScrollPane.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/resources/fileupload/fileupload.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/resources/theme/css/scheMonth.css" />
<%-- <script type="text/javascript" src="${ctx}/resources/jquery/jqueryToJscrollPane.js"></script> --%>
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
<script src="${ctx}/resources/pt/js/exchangedata/cmsbtns.js"></script>
<script src="${ctx}/resources/pt/js/global.js"></script>

<script type="text/javascript" src="${ctx}/resources/schedule/global_zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/resources/schedule/sche.js "></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.constant.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/pt/js/fileupload/well.fileupload4Icon.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload.js"></script>	
<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.fileuploader.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.iframe-transport.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-process.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-fp.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/jquery.fileupload-ui.js"></script>	
<script type="text/javascript" src="${ctx}/resources/fileupload/js/uuid.js"></script>	
<!-- 		/resources/jquery/jquery.js -->
<!-- 		/resources/bootstrap/js/bootstrap.js -->
<!-- 		/resources/jqueryui/js/jquery-ui.js -->
<!-- 		/resources/pt/js/common/jquery.cmsWindown.js -->
<!-- 		/resources/pt/js/common/jquery.alerts.js -->
<!-- 		/resources/pt/js/basicdata/dyview/dyview_demo.js -->
<script>
	window.ctx = '${ctx}';
$(function() {
	//推送消息
	dwr.engine._errorHandler = function(message, ex) {
		dwr.engine._debug("error: " + ex.name + ", " + ex.message, true);
	};
	directController.onPageLoad(function(data) {  
	    $(".num").html(data);
	});
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true);
	//添加已阅未阅的图标
	$(".page_index .dataTr").each(function(){
		if($(this).attr("class").indexOf("readed")>-1){
			$(this).find("td").eq(0).html("<div class='icon_readed'></div>"+$(this).find("td").eq(0).html());
		}else if($(this).attr("class").indexOf("noread")>-1){
			$(this).find("td").eq(0).html("<div class='icon_noread'></div>"+$(this).find("td").eq(0).html());
		}
	});
	//格式化时间
	formDate();
	//判断是否全屏
	if($.cookie("cookie.isfull")=="yes"){
		 var windowWidth =   $(window).width();
         var containerClass = $(".container").attr("class");
		 proportion = parseInt(windowWidth) / 1200;
	     $(".container").css("width",windowWidth);
	     $(".container").css("background","#fff"); 
	     $("body").css("background","#fff");
	     $(".footer").next().remove();
	     $(".dnrw").each(function(){
	         var width = $(this).css("width");
	         var left = $(this).css("left");
	         var width_ = parseInt(width)*proportion;
	         var left_ = parseInt(left)*proportion;
	         $(this).css("width",width_);
	         $(this).css("left",left_);
	     });
	     $(".container").attr("class","container fulled");
	}
	
});
	//通过该方法与后台交互，确保推送时能找到指定用户  
	function onPageLoad(){  
	directController.onPageLoad(function(data) {  
	    $(".num").html(data);
	});  
	}  
	//推送信息  
		function showMessage(noReadMessageCount,js,uuid,sender,senderName,recipient,recipientName,receivedtime,subject,body,note,viewpoint,relatedurl){ 
		   $(".num").html(noReadMessageCount);
		   $("#messageContent").val(js);
		   $("#uuid").val(uuid);
		
		   
		   var message_js =eval('(' + js + ')');
		   var onlinePopup=message_js.isOnlinePopup;
		   var online_js_event=message_js.foregroundEvent;
		   var viewpointY=message_js.viewpointY;
		   var viewpointN=message_js.viewpointN;
		   var viewpointNone=message_js.viewpointNone;
		   $("#userid_h").val(recipient);
		   $("#username_h").val(recipientName);
		   $("#viewpointY_h").val(viewpointY);
		   $("#viewpointN_h").val(viewpointN);
		   $("#viewpointNone_h").val(viewpointNone);
		   $("#online_js_event").val(online_js_event);
		   setMessageInfo(message_js);
		   if("Y"==onlinePopup){//设定弹窗
			   var json =new Object(); 
			   var  showMessage=getInboxMessage(sender,senderName,subject,body,js,note,viewpoint,json,relatedurl);
			   json.content=showMessage;
				json.title = "收件消息   "+receivedtime.substr(0,19);
				json.height= dialog_height;
				json.width= 830;
			    showDialog(json);
		   }
		    
		} 
	    function setMessageInfo(messagejson){
	    	$("#scheduleTitle_h").val(messagejson.scheduleTitle);
			$("#scheduleDates_h").val(messagejson.scheduleDates);
			$("#scheduleDatee_h").val(messagejson.scheduleDatee);
			$("#scheduleAddress_h").val(messagejson.scheduleAddress);
			$("#reminderTime_h").val(messagejson.reminderTime);
			$("#scheduleBody").val(messagejson.scheduleBody);
			$("#srcTitle_h").val(messagejson.srcTitle);
			$("#srcAddress_h").val(messagejson.srcAddress);
	    }
		function messageConfirm(viewpoint,viewpointText){
			var messageContent = $("#messageContent").val();
			var uuid = $("#uuid").val();
			var online_js_event= $("#online_js_event").val();
			var msgnote = $("#note_show").val();
			//前台事件处理
			onlineJsEvent(online_js_event,messageContent);
			//后台事件处理
				$.ajax({
					type:"post",
					async:false,
					data : "message="+messageContent+"&viewpoint="+viewpoint+"&msgnote="+msgnote+"&uuid="+uuid+"&viewpointText="+viewpointText,
					url:ctx+"/message/content/submitViewPoint",
					success:function(result){
						oAlert2("保存成功！");
						$("#dialogModule").dialog("close");
					}, error: function(result) {  
						oAlert2("保存失败！");
			            $("#dialogModule").dialog("close");
			        }  
				});
			
		}
		function onlineJsEvent(js_event_id,messageContent){
			//messagecontent为参数值，以json格式提供
			//js_event_id为前台处理方法id
		}
	
		function callScheduleDialog(){
			var scheduleJson={
					"scheduleTitle" : null,
					"scheduleDates" : null,
					"scheduleDatee" : null,
					"scheduleAddress" : null,
					"reminderTime" : null,
					"scheduleBody" : null,
					"srcTitle" : null,
					"srcAddress" : null,
					"userid" : null,
					"userName" : null
			};
			scheduleJson["scheduleTitle"]=$("#scheduleTitle_h").val();
			scheduleJson["scheduleDates"]= $("#scheduleDates_h").val();
			scheduleJson["scheduleDatee"]= $("#scheduleDatee_h").val();
			scheduleJson["scheduleAddress"]= $("#scheduleAddress_h").val();
			scheduleJson["reminderTime"]= $("#reminderTime_h").val();
			scheduleJson["scheduleBody"]= $("#scheduleBody").val();
			scheduleJson["srcTitle"]= $("#srcTitle_h").val();
			scheduleJson["srcAddress"]=$("#srcAddress_h").val();
			scheduleJson["userid"]=$("#userid_h").val();
			scheduleJson["userName"]= $("#username_h").val();
			
			msgAddSchedule(scheduleJson);
		}
		
		function refeshInbox(uuid,obj){
			$.ajax({
				type : "POST",
				url : ctx+"/message/content/read.action",
				data : "uuids="+uuid,
				dataType : "text",
				success : function() {
					refreshWindow($(obj));
				}
			});
		}
		function showHomeInboxMessage(){
			var line_info = new Object(); 
			line_info=eval("("+urldecode($(this).attr("jsonstr")+ ")"));
			var uuid=processInboxMessage(line_info);
			//refeshInbox(uuid,"#messageHiddenId");
			$.ajax({
				type : "POST",
				url : ctx+"/message/content/read.action",
				data : "uuids="+uuid,
				dataType : "text",
				success : function() {
				}
			});
		}
		var dialog_height=450;
		function showInboxMessage(){
			var line_info = new Object(); 
			line_info=eval("("+urldecode($(this).attr("jsonstr")+ ")"));
			var uuid=processInboxMessage(line_info);
			
			refeshInbox(uuid,this);
		}
		function showOutboxMessage(){
			var line_info = new Object(); 
			var showMessage="";
			line_info=eval("("+urldecode($(this).attr("jsonstr")+ ")"));
		   var userid= line_info["recipient"];
		   var userName= line_info["recipientName"];
		   var sendTime= line_info["sentTime"];
		   var subject= line_info["subject"];
		   var body= line_info["body"];
		   var relatedUrl= line_info["relatedUrl"];
		   var uuid= line_info["uuid"];
		   $("#userid_h").val(line_info["sender"]);
		   $("#username_h").val(line_info["senderName"]);
		   showMessage=getOutboxMessage(userid, userName, subject, body,relatedUrl);
		   var json =new Object(); 
		   json.content=showMessage;
			json.title = "发件消息   "+sendTime.substr(0,19);
			json.height= 500;
			json.width= 830;
		    showDialog(json);
		    var markflag= line_info["markFlag"];
		    if(markflag=="1"){
		    	 $("#messageStatus_1").attr("checked", "checked");	
		    }
		    initFileupload2(uuid,true);
		    
		}
		function processInboxMessage(line_info){
			 var userid= line_info["sender"];
			   var userName= line_info["senderName"];
			   var receivedTime= line_info["receivedTime"];
			   var subject= line_info["subject"];
			   var body= line_info["body"];
			   var relatedUrl= line_info["relatedUrl"];
			   var showMessage="";
			   var uuid=line_info["uuid"];
			   var note=line_info["note"];//
			   var viewpoint=line_info["viewpoint"];//
			   var message=line_info["messageParm"];//待处理
			   var json =new Object(); 
			   $("#messageContent").val(message);
			   $("#uuid").val(uuid);
			   $("#userid_h").val(line_info["recipient"]);
			   $("#username_h").val(line_info["recipientName"]);
			   showMessage=getInboxMessage(userid, userName, subject, body,message,note,viewpoint,json,relatedUrl);
			   json.content=showMessage;
				json.title = "收件消息   "+receivedTime.substr(0,19);
				json.height= dialog_height+20;
				json.width= 830;
			    showDialog(json);
			    var markflag= line_info["markFlag"];
			    if(markflag=="1"){
			    	 $("#messageStatus_1").attr("checked", "checked");	
			    }
			    initFileupload2(uuid,true);//改为收件箱的发件箱id
			    return uuid;
		}
		function getInboxMessage(userid,userName,subject,body,message,note,viewpoint,json,relatedUrl){
		    var message_js=eval("("+message+ ")");
		    setMessageInfo(message_js);
			var content_head="<div id='dialog_form_content'>"
					+"<table width='100%'><tbody><br></tr><tr class='odd'> <td class='Label' width='100' align='center'>发件人</td><td class='value'><div class='td_class'>"
						+"<input type='hidden' id='userId' name='userId' value='"+userid+"'/><input type='text' id='showUser' name='showUser' style='width:95%;height:20px' value='" 
						+userName+"'/>"
						+" </div> </td></tr><tr ><td class='Label' width='100' align='center'>主题</td> <td><table><tr> <td class='value'><div class='td_class'>"
						+"<input type='text' id='subject' name='subject' style='width:600px;height:20px' value='"+subject+"'/>"
						+"</div></td><td>&nbsp;&nbsp;</td><td style='padding-bottom: 6px;'><input id='messageStatus_1' name='messageStatus' type='checkbox' value='1'/></td><td>重要</td></td></tr></table>"+
						"</td></tr><tr class='odd'><td class='Label' width='100' align='center'>内容</td><td><table><tr><td class='value'>"
						+"<div class='td_class'><textarea  id='datetime_sec_col' style='width:650px; height:215px;' name='body'>"+body+"</textarea>"
						+" </div></td></tr><tr><td><div id='message_fileupload'></div></td></tr></table></td></tr> <tr ></tr> <input type='checkbox' class='messageType' name='type' value='ON_LINE' checked='checked' style='display: none;'/>";
			if(""!=relatedUrl){
				content_head=content_head+"<tr class='odd'> <td class='Label' width='100' align='center'>相关链接</td><td class='value'><div class='td_class'>"
				  +"<a target='_blank' style='color:blue' href='"+relatedUrl+"'>"+relatedUrl+"</a></div></td></tr>";
			  }
			var content_body="";
			var content_tail="</tbody></table></div>";
			if(true){//如果有保存消息模板信息
				content_body=processBody(message_js,note,viewpoint,json);
				
			}
			
		return content_head+content_body+content_tail;		
		}
		function processBody(message_js,note,viewpoint,json){
			   var content_body="";
			   var showViewpoint=message_js.showViewpoint;
			   var viewpointY=message_js.viewpointY;
			   var viewpointN=message_js.viewpointN;
			   var viewpointNone=message_js.viewpointNone;
			   var askForSchedule=message_js.askForSchedule;
			   var online_js_event=message_js.foregroundEvent;
			   $("#viewpointY_h").val(viewpointY);
			   $("#viewpointN_h").val(viewpointN);
			   $("#viewpointNone_h").val(viewpointNone);
			   $("#online_js_event").val(online_js_event);
			   var buttons = new  Object(); 
			   if(""==viewpoint){
				   if("Y"==showViewpoint){
					   content_body=content_body
				        +"<tr ><td class='Label' width='100' align='center'>说明</td> <td class='value'><div class='td_class'>"
		                +"<textarea  id='note_show' style='width:95%; height:45px;' name='note_show'>"+note+"</textarea></div></td>";
					   dialog_height=520;
						buttons[viewpointY]=messageConfirmY;
						buttons[viewpointN]=messageConfirmN;
						buttons[viewpointNone]=messageConfirmNone;
				   }else{
					   dialog_height=450;
				   }
				   
			   }else{
				   content_body=content_body+"<tr ><td class='Label' width='100' align='center'>意见立场</td> <td class='value'><div class='td_class'>"
		            +"<input type='text' id='viewpoint_show' name='viewpoint_show' style='width:95%;height:20px' value='"+viewpoint+"'/></div></td>"
			        +"<tr ><td class='Label' width='100' align='center'>说明</td> <td class='value'><div class='td_class'>"
	                +"<textarea  id='note_show' style='width:95%; height:45px;' name='note_show'>"+note+"</textarea></div></td>";
			   dialog_height=550;
			   }
			   if("Y"==askForSchedule){
				   buttons.列入日程=callScheduleDialog;
			   }
			   json.buttons = buttons;
			   return content_body;
		}
		function messageConfirmY(){
			var viewpoint=$("#viewpointY_h").val();
			messageConfirm("PASS", viewpoint);
		}
        function messageConfirmN(){
			if($("#note_show").val()==""){
				oAlert2("请填写说明！");
				return;
			}
			var viewpoint=$("#viewpointN_h").val();
			messageConfirm("REFUSE", viewpoint);
		}
       function messageConfirmNone(){
    	   var viewpoint=$("#viewpointNone_h").val();
			messageConfirm("NONE", viewpoint);
		}
		function getOutboxMessage(userid,userName,subject,body,relatedUrl){
			   var content="<div id='dialog_form_content'>"
						+"<table width='100%'><tbody><br></tr><tr class='odd'> <td class='Label' width='100' align='center'>收件人</td><td class='value'><div class='td_class'>"
							+"<input type='hidden' id='userId' name='userId' value='"+userid+"'/><input type='text' id='showUser' name='showUser' style='width:95%;height:20px' value='" 
							+userName+"'/>"
							+" </div> </td></tr><tr ><td class='Label' width='100' align='center'>主题</td><td><table><tr> <td class='value'><div class='td_class'>"
							+"<input type='text' id='subject' name='subject' style='width:600px;height:20px' value='"+subject+"'/>"
							+"</div></td><td>&nbsp;&nbsp;</td><td  style='padding-bottom: 6px;'><input id='messageStatus_1' name='messageStatus' type='checkbox'  value='1'/></td><td>重要</td></tr></table>"+
							"</td></tr><tr class='odd'><td class='Label' width='100' align='center'>内容</td><td><table><tr><td class='value'>"
							+"<div class='td_class'><textarea  id='datetime_sec_col' style='width:650px; height:215px;' name='body'>"+body+"</textarea>"
							+" </div></td></tr><tr><td><div id='message_fileupload'></div></td></tr></table></td></tr> <tr ></tr> <input type='checkbox' class='messageType' name='type' value='ON_LINE' checked='checked' style='display: none;'/>";
			  if(""!=relatedUrl){
				  content=content+"<tr class='odd'> <td class='Label' width='100' align='center'>相关链接</td><td class='value'><div class='td_class'>"
				  +"<a target='_blank' style='color:blue' href='"+relatedUrl+"'>"+relatedUrl+"</a></div></td></tr>";
			  }
							+"</tbody></table></div>";		
			return content;		
			
		   }
	//格式化时间
	function formDate(){
		$(".dataTr td").each(function(){
			var _reTimeReg = /^(?:19|20)[0-9][0-9]-(?:(?:[1-9])|(?:1[0-2]))-(?:(?:[1-9])|(?:[1-2][1-9])|(?:[1-3][0-1])) (?:(?:2[0-3])|(?:1[0-9])|(?:[1-9])|0):[0-5][0-9]:[0-5][0-9]$/;
			var tempText = $(this).text();
			if(_reTimeReg.test(tempText)){
				
				var dataStr = tempText.split(" ")[0];
				var timeStr = tempText.split(" ")[1];
				var dataStrArray = dataStr.split("-");
				var timeStrArray = timeStr.split(":");
				var strArray = new Array();
				strArray[0] = dataStrArray[0];
				strArray[1] = dataStrArray[1];
				strArray[2] = dataStrArray[2];
				strArray[3] = timeStrArray[0];
				strArray[4] = timeStrArray[1];
				strArray[5] = timeStrArray[2];
				for(var i=1;i<6;i++){
					if(strArray[i].length==1){
						strArray[i] = 0+""+strArray[i];
					}
				}
/**		var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4]+":"+strArray[5];**/
				var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4];
				$(this).text(str);
				$(this).attr("title",str);
			}
		});
	}
	function sendMessageForInviteOthers(){
		var userId = $("#dialogModule #userId_online").val();
		
		var body = $("#dialogModule #msgbody").val(); 
		var subject = $("#dialogModule #subject").val();
		var showUser = $("#dialogModule #showUser_online").val();
		if(showUser == null || showUser ==""){
			oAlert2("邀请人不能为空！");
		} else if(body == null || body ==""){
			oAlert2("消息内容不能为空！");
		} else {
			$.ajax({
				type:"post",
				async:false,
				data : "userId="+userId+"&body="+body+"&type=ON_LINE"+"&subject="+subject,
				url:ctx+"/message/content/submitmessage",
				success:function(result){
					oAlert2("发送成功！");
					$("#dialogModule").dialog("close");
				}, error: function(result) {  
					oAlert2("发送失败！");
		            $("#dialogModule").dialog("close");
		        }  
			});
		}
	}
	function sendMessage(){
		var userId = $("#dialogModule #userId").val();
		var subject = $("#dialogModule #subject").val();
		var body = $("#dialogModule #datetime_sec_col").val(); 
		//var messageStatus = $("#dialogModule #messageStatus").val(); 
		var messageStatus = $("#dialogModule input[name='messageStatus']:checked").val(); 
		var messageAttach=WellFileUpload.files["fileupload_online_message"];
		var messageAttachId=[];
		for(i in messageAttach){
			messageAttachId.push(messageAttach[i].fileID);
		}
// 		var type = $("#dialogModule .messageType").val();
		var type = new Array();
		$("#dialogModule .messageType").each(function(){
			if($(this).attr("checked")){
				type.push($(this).val());
			}
		});
		var showUser = $("#dialogModule #showUser").val();
		if(showUser == null || showUser ==""){
			oAlert2("收件人不能为空！");
		} else if(body == null || body ==""){
			oAlert2("消息内容不能为空！");
		} else {
			$.ajax({
				type:"post",
				async:false,
// 				data:{"userId":userId,"body":body,"type":type},
				data : "userId="+userId+"&body="+body+"&type="+type+"&subject="+subject+"&markflag="+messageStatus+"&messageAttach="+messageAttachId,
				url:ctx+"/message/content/submitmessage",
				success:function(result){
					oAlert2("发送成功！");
					$("#dialogModule").dialog("close");
				}, error: function(result) {  
					oAlert2("发送失败！");
		            $("#dialogModule").dialog("close");
		        }  
			});
		}
	}
</script>
</head>
<body>
   
	<div class="cmspagecontent">
		<cm:cmspage></cm:cmspage>
	</div>
	<div class="send_message" style="display: none;">
		<div id="dialog_form">
			<div class="dialog_form_content">
				<table width="100%">
					<tbody>
					  <tr >
					    <td class="Label"  width="100px">收件人</td>
					    <td >
					    <div >
							<input type="hidden" id="userId" name="userId" />
							<input type="text" id="showUser" name="showUser" style="width:95%;" value=""/>
					   </div>
					   </td>
					  </tr>
					   <tr >
					    <td class="Label">主题</td>
					    <td >
					    <div >
							<input type="text" id="subject" name="subject" style="width:95%;" value=""/>
					   </div>
					   </td>
					  </tr>
					  <tr >
					    <td class="Label">消息内容</td>
					    <td >
					    <div >
					   		<textarea  id="datetime_sec_col" style="width:95%; height:115px;" name="body"></textarea>
					    </div>
					    </td>
					  </tr>
					  <tr >
					  </tr>
	<!--  				    <tr class="odd">
					    <td class="Label">消息类型</td>
					    <td class="value">
					    	<div class="td_class">-->
					    	<input type="checkbox" class="messageType" name="type" value="ON_LINE" checked="checked" style="display: none;"/>
					    	<!--发在线消息&nbsp;  -->
<!-- 					    	<input type="checkbox" class="messageType" name="type" value="EMAIL" />发邮件&nbsp; -->
<!-- 					    	<input type="checkbox" class="messageType" name="type" value="SMS" />发短信 -->
	<!--  						</div>
					    </td>
					  </tr>-->
					</tbody>
				</table>
			</div>
		  </div>	
	</div>
	<div class="online_message" style="display: none;">
		<div id="dialog_form">
		<input type="hidden" id="messageContent"/>
		<input type="hidden" id="viewpointY_h" name="viewpointY_h" />
			<input type="hidden" id="viewpointN_h" name="viewpointN_h"/>
			<input type="hidden" id="viewpointNone_h" name="viewpointNone_h" />
			<input type="hidden" id="userid_h" name="userid_h" />
			<input type="hidden" id="username_h" name="username_h" />
			<input type="hidden" id="uuid" />
			<input type="hidden" id="scheduleTitle_h" />
			<input type="hidden" id="scheduleDates_h" />
			<input type="hidden" id="scheduleDatee_h" />
			<input type="hidden" id="scheduleAddress_h" />
			<input type="hidden" id="reminderTime_h" />
			<input type="hidden" id="scheduleBody_h" />
			<input type="hidden" id="srcTitle_h" />
			<input type="hidden" id="srcAddress_h" />
			<input type="hidden" id="dbFiles" name="dbFiles" value=""/>
		  </div>	
	</div>
	<div class="op_password" style="display: none;">
		<div id="dialog_form">
			<div class="dialog_form_content">
				<table width="100%">
					<tbody>
					  <tr class="odd">
					    <td class="Label">旧密码</td>
					    <td class="value">
					    <div class="td_class">
							<input class="v1_input_password" type="password" />
						</div>
					   </td>
					  </tr>
					  <tr>
					    <td class="Label">新密码</td>
					    <td class="value">
					    <div class="td_class">
					   		 <input class="v1_input_newpassword" type="password" />
				   		</div>
					    </td>
					  </tr>
					  <tr class="odd">
					  </tr>
					    <tr class="odd">
					    <td class="Label">确认密码</td>
					    <td class="value">
					    <div class="td_class">
					    <input class="v1_input_doublepassword" type="password" />
					    </div>
					    </td>
					  </tr>
					</tbody>
				</table>
			</div>
		  </div>	
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		
			$("#showUser").die().live("click",function(){
				$.unit.open({
				 	labelField : "showUser",
					valueField : "userId",
					selectType : 1,
					afterSelect : function(retVal){
						$("input[name=showUser]").val(retVal.name);
						$("input[name=userId]").val(retVal.id);
					}
				});
			});
			
			 
			/***********二级页刷新的操作******************/
			if($(".page_category").length>0){
				
				/***********二级搜索******************/
				var search = readSearch();
				var mid = "";
				var moduleid = "";
				$.each(search,function(n,value) {   
				    if(n == "uuid" || n == "treeName" || n =="viewName") {
				 	   
				    }else {
				    	mid += ","+n;
				    	moduleid += ","+value;
				    }
				  });  
				mid = mid.substring(1);
				moduleid = moduleid.substring(1);
// 				if(search.indexOf("keyword")>-1||search.indexOf("moduleid")>-1){
// 					var searchArray = search.replace("?", "").split("&");
// 					for ( var i = 0; i < searchArray.length; i++) {
// 						var paraArray = searchArray[i].split("=");
// 						var key = paraArray[0];
// 						var value = paraArray[1];
// 						if(key=='keyword'){
// 							$("#abc").dyView("keySelectClick", value);
// 							$("#keyWord").val(value);
// 						}else if(key=='moduleid'){
// 							moduleid = value;
// 						}else if(key=='mid'){
// 							mid = value;
// 						}
// 					}
// 				}
				/**************二级页指定模块名称与导航一致*********************/
				if(moduleid!=""){
					$(".newoa_cate .list-item").each(function(){
						var moduleid_ = $(this).find(".openchild").attr("moduleid");
						if(moduleid_ == moduleid){
							var moduleName = $(this).find(".openchild").text();
							$("#"+mid+" ul .active a" ).text(moduleName);
						}
					});
				}
			}
			
		});
	/***********更改密码*****************/
	function password_submit(){
		var oldpassword = $("#dialogModule .v1_input_password").val();
		var newpassword = $("#dialogModule .v1_input_newpassword").val();
		var doublepassword = $("#dialogModule .v1_input_doublepassword").val();
		if(newpassword == null || newpassword =="" || newpassword == null || newpassword =="" || doublepassword == null || doublepassword ==""){
			oAlert("请将表单填写完整");
		}else if(newpassword!=doublepassword){
			oAlert("两次密码不一致");
		}else{
			var uuid = $("#user_uuid").val();
			$.ajax({
					type:"post",
					async:false,
					data:{"uuid":uuid,"oldPassword":oldpassword,"newPassword":newpassword},
					url:ctx+"/cms/cmspage/modifPassword",
					success:function(result){
						if(result.data=="success"){
							oAlert("修改成功");
							$("#dialogModule").dialog("close");
						}else{
							oAlert("修改失败");
						}
					}
			});
		}
	}
	
	 function indexTrim(obj) {
		var m = obj.match(/^\s*(\S+(\s+\S+)*)\s*$/);
		return (m == null) ? "" : m[1];
	} 
	
 	function isMobile(mobile) {
		//return (/^(?:13\d|15[89])-?\d{5}(\d{3}|\*{3})$/.test(indexTrim()));
		return (/^1\d{10}$/.test(indexTrim(mobile)));
	}
	
 	function isTel(tel) {
	    //"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"
	    return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-?)(\d{7,8})(-(\d{3,}))?$/.test(indexTrim(tel)));
	}
	
	function isChinese(chinese) {
		return (/^[\u4e00-\u9fa5]+$/.test(indexTrim(chinese)));
	} 
	
	/***********个人设置*****************/
	function userSet(){
// 		var user_isShowContact = $("#user_isShowContact").val();
		var mobilePhone = $("#dialogModule #mobilePhone").val();
		var officePhone = $("#dialogModule #officePhone").val();
//		var contactName = $("#dialogModule #contactName").val();
// 		if(user_isShowContact == 'true'||user_isShowContact == '') {
//			if(contactName=='' || contactName==null) {
//				oAlert('请输入联系人的真实姓名！');
//				return false;
//			} else if(!isChinese(contactName)){
//				oAlert('联系人请输入中文！');
//				return false;
//			}
			if(mobilePhone=='' ||mobilePhone==null) {
				oAlert('请输入手机号！');
				return false;
			}
			if(officePhone == '' || officePhone==null) {
				oAlert('请输入办公电话！');
				return false;
			}
// 		}
//        if(mobilePhone!='' && !isMobile(mobilePhone)){
//   	 	oAlert('请输入正确的手机号！');
//    	 	return false;
//        } 
//        if(officePhone!='' && !isTel(officePhone)) {
//        	oAlert('请输入正确的办公电话！');
//        	return false;
//        } 
		
		var photoUuid = $("#dialogModule #photoUuid").val();
		var sex = $("#dialogModule input[name='sex']:checked").val();
		var receiveSmsMessage = $("#dialogModule #receiveSmsMessage:checked").val();
		if(receiveSmsMessage == null){
			receiveSmsMessage = false;
		}
		var fax = $("#dialogModule #fax").val();
		var idNumber = $("#dialogModule #idNumber").val();
		var trace = $("#dialogModule input[name='trace']:checked").val();
		var uuid = $("#user_uuid").val();
		var mainEmail = $("#dialogModule #mainEmail").val();
		var otherEmail = $("#dialogModule #otherEmail").val();
		var otherMobilePhone = $("#dialogModule #otherMobilePhone").val();
		var boPwd = $("#dialogModule #boPwd").val();
		var boUser = $("#dialogModule #boUser").val();
		$.ajax({
				type:"post",
				async:false,
				data:{"uuid":uuid,"photoUuid":photoUuid,"sex":sex,"mobilePhone":mobilePhone,"boUser":boUser,"boPwd":boPwd,"receiveSmsMessage": receiveSmsMessage,"officePhone":officePhone,"fax":fax,"idNumber":idNumber,"trace":trace,"mainEmail":mainEmail,"otherEmail":otherEmail,"otherMobilePhone":otherMobilePhone},
				url:ctx+"/org/user/saveUserSet",
				success:function(result){
					if(result.success){
						oAlert("保存成功");
						$("#dialogModule").dialog("close");
					}else{
						oAlert("保存失败");
					}
				}
		});
	}
	
	/*****获得所有的流程串********/
	function getAllFlows(){
		var str = "";
		$.ajax({
			type:"get",
			async:false,
			url:ctx+"/cms/cmspage/allflow",
			success:function(result){
				str = result.substring(result.indexOf("<body>") + 6, result.indexOf("</body>"));
			}
		});
		return str;
	}
	function jsmod(obj,isfirst){
		$(obj).each(function(){
			if(isfirst){
					$(this).jScrollPane();
			}else{
				if($(this).height()<$(this).children().eq(0).height()){
					$(this).jScrollPane();
				}
			}
		});
	}
	jsmod(".dnrw .tab-content");
</script>
<%
	// 加入附件签名控件
// 	String fjcaControlContextPath = request.getContextPath();
// 	if("/".equals(fjcaControlContextPath)) {
// 		fjcaControlContextPath = "";
// 	}
// 	String fjcaWs = "<object id=\"fjcaWs\" name=\"SBFjCAEnAndSign\" classid=\"CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/SBFjCAEnAndSign.ocx\"></object>";
// 	String fjcaControl = "<object id=\"fjcaControl\" classid=\"clsid:414C56EC-7370-48F1-9FB4-AF4A40526463\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/fjcaControl.ocx\" ></object>";
// 	out.print(fjcaWs);
// 	out.print(fjcaControl);
%>
</body>
</html>