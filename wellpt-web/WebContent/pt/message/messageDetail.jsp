<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/pt/common/taglibs.jsp"%>
<%@ include file="/pt/common/meta.jsp"%>
<%@ include file="/pt/dytable/form_css.jsp"%>	
<%@ include file="/pt/dytable/form_js.jsp"%>	
<title>消息内容</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/theme/css/scheMonth.css" />
<script type="text/javascript" src="${ctx}/resources/pt/js/org/unit/jquery.unit.js "></script>
<script type="text/javascript" src="${ctx}/resources/schedule/sche.js "></script>

<script type="text/javascript">
function messageConfirm(){
	var messageParm = $("#messageParm").val();
	var uuid = $("#uuid").val();
	var viewpoint =$("input[name='viewpoint']:checked").val();
	   if("PASS"==viewpoint){
		   viewpoint=$("#viewpointY_h").val();
	   }else if("REFUSE"==viewpoint){
		   viewpoint=$("#viewpointN_h").val();
	   }else if("NONE"==viewpoint){
		   viewpoint=$("#viewpointNone_h").val();
	   }
	var msgnote = $("#msgnote").val();
	if(viewpoint==undefined||viewpoint==null||viewpoint==""){
		oAlert2("选择意见立场！");
	}else{
		$.ajax({
			type:"post",
			async:false,
			data : "message="+messageParm+"&viewpoint="+viewpoint+"&msgnote="+msgnote+"&uuid="+uuid,
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
}
function callScheduleDialog(){
	var userid=$("#userId_cur").val(); 
	var username=$("#userName_cur").val();
	openNewSchedule("", "", "", "", "", "", "", userid, username);
}
function sendMessageForInviteOthers(){
	var userId = $("#userId_online").val();
	var body = $("#msgbody").val(); 
	var subject = $("#subject").val();
	var showUser = $("#showUser_online").val();
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

</script>

</head>
<body>
	<div class="div_body">
		<div class="div_body_content">
		<div class="form_header">
			<!--标题-->
			<div class="form_title">
				<h2>消息内容</h2><div class="form_close" title="关闭"></div>
			</div>
			<!-- <div id="toolbar" class="form_toolbar" style="padding: 8px 0 10px 0px;"> -->
				<div id="mini_wf_opinion" style="margin-bottom: 2px;">

				</div>
				<div class="form_operate">
					<button id="btn_dyform" name="btn_dyform" style="display: none">保存</button></div>
				</div>
		</div>
		<div class="form_content" style="width: 875px; margin: 0 15px 0 25px;"></div>
		
		<!-- 动态表单 -->
		<div id="dyform">
			<div class="dytable_form">
			<textarea type="text" id="messageParm" name="messageParm" style="display:none;" >${messageParm}</textarea>
			<input type="hidden" id="subject" name="subject"  value=${mc.subject} />
			<input type="hidden" id="viewpointY_h" name="viewpointY_h" />
			<input type="hidden" id="viewpointN_h" name="viewpointN_h" />
			<input type="hidden" id="viewpointNone_h" name="viewpointNone_h" />
			<input type="hidden" id="uuid" value=${mc.uuid} />
			<input type="hidden" id="userId_cur" value=${userId_cur} />
			<input type="hidden" id="userName_cur" value=${userName_cur} />
			<input type="hidden" id="scheduleTitle"  />
			<input type="hidden" id="schedule" />
				<div class="post-sign">
					<div class="post-detail"><!-- 表类型(tableType):1主表,2从表 -->
						<table edittype="1" id="002" tabletype="1" width="100%">
							<tbody>
								<c:choose>
									<c:when test="${type=='send'}">
								        <tr class="odd">
											<td class="Label">收件人</td>
											<td class="value">${mc.recipientName}</td>
										</tr>
									</c:when>
									<c:when test="${type=='receive'}">
								         <tr class="odd">
											<td class="Label">发件人</td>
											<td class="value">${mc.senderName}</td>
										</tr>
									</c:when>
								</c:choose>
								<tr>
									<td class="Label">时间</td>
									<td class="value" colspan="3"><fmt:formatDate value="${mc.receivedTime}"
											type="both" /></td>
								</tr>
							 <tr class="odd" >
							        <td class="Label">主题</td>
						        	<td class="value" >${mc.subject}
					        </div>
						     </td>
						     </tr>  
						        <tr >
							        <td class="Label">内容</td>
						        	<td>
						        	<div >
					   		<textarea  id="msgbody" style="width:95%; height:150px;" name="msgbody">${mc.body }</textarea>
					        </div>
						     </td>
						     </tr>  
				   <c:if test="${type=='receive'}">    
					<tr id="viewpoint_row" class="odd">
					<input type="hidden" id="viewpoint_h" value=${mc.viewpoint}>
					  <td class="Label">意见立场</td>
					  <td class="value" align="left">
					  <div  id="show_viewpoint" style="text-align: left"></div>
					   </td>
					  </tr>
					   <tr id="note_row">
					    <td class="Label">备注</td>
					    <td class="value" align="left">
					  <div >
					   		<textarea  id="msgnote" style="width:95%; height:50px;" name="msgnote" >${mc.note}</textarea>
					    </div>
					   </td>
					  </tr>
					   <tr id="invite_row">
					    <td class="Label">邀请其他人</div></td>
					    <td class="value"  align="left">
					    <div >
			                <input id="showUser_online" name="showUser_online" style="width:75%;height:20px"  value=""  />
			                 <input type="hidden" id="userId_online" name="userId_online"/>
			                <button id='invite_btn' type='button' class='btn' onclick="sendMessageForInviteOthers()">发送</button>
			            </div>
					   </td>
					  </tr>
					    <tr>
							 <td class="Label"></td>
						      <td class="value"></td>
						</tr>
					   <tr id="btn_row">
					    <td class="Label"></div></td>
					    <td class="value" align="center">
					    <div class="td_class" >
			                <button id='confirm_btn' type='button' class='btn' onclick="messageConfirm()">确定</button>
			                 <button id='schedule_btn' type='button' class='btn' onclick="callScheduleDialog()">列入日程</button>
			            </div>
					   </td>
					  </tr>
					  </c:if>
					   <tr>
								    <td class="Label"></td>
								    <td class="value"></td>
								</tr>
						</tbody>						
						</table>
					</div>
					
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class="body_foot">
	</div>
		
	
	<script type="text/javascript" src="${ctx}/resources/pt/js/global.js"></script>
	<script type="text/javascript" src="${ctx}/resources/pt/js/common/jquery.cmsWindown.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$(window).resize(function(e) {
				// 调整自适应表单宽度
				adjustWidthToForm();
			});
			// 调整自适应表单宽度
			function adjustWidthToForm() {
				var div_body_width = $(window).width() * 0.76;
				$(".form_header").css("width", div_body_width - 5);
				$(".div_body").css("width", div_body_width);
			}
			
			$(".form_header .form_close").click(function(e) {
				returnWindow();
				window.close();
			});
			$("#showUser_online").die().live("click",function(){
				$.unit.open({
				 	labelField : "showUser_online",
					valueField : "userId_online",
					selectType : 1,
					afterSelect : function(retVal){
						$("input[name=showUser_online]").val(retVal.name);
						$("input[name=userId_online]").val(retVal.id);
					}
				});
			});
		});
		
		  var message_js =eval('(' + $("#messageParm").val() + ')');
		   var showViewpoint=message_js.showViewpoint;
		   var viewpointY=message_js.viewpointY;
		   var viewpointN=message_js.viewpointN;
		   var viewpointNone=message_js.viewpointNone;
		   var askForSchedule=message_js.askForSchedule;
		   var online_js_event=message_js.foregroundEvent;
		////   var scheduleTitle=message_js.scheduleTitle;
		//   var scheduleBody=message_js.scheduleBody;
		   $("#viewpointY_h").val(viewpointY);
		   $("#viewpointN_h").val(viewpointN);
		   $("#viewpointNone_h").val(viewpointNone);
		//   $("#scheduleTitle").val(scheduleTitle);
		//   $("#scheduleBody").val(scheduleBody);
		   
		   var viewpoint_h=$("#viewpoint_h").val();
		       if(viewpoint_h==""){
		    	   if("Y"==showViewpoint){
					   $("#show_viewpoint").html("");
					   $("#show_viewpoint").append(" <input id='viewpointY' name='viewpoint' type='radio' value='PASS' >"+viewpointY+"</input> "); 
					   $("#show_viewpoint").append(" <input id='viewpointN' name='viewpoint' type='radio' value='REFUSE' >"+viewpointN+"</input> ");
					   $("#show_viewpoint").append(" <input id='viewpointNone' name='viewpoint' type='radio'  value='NONE' >"+viewpointNone+"</input>  ");
					   if("1"!=online_js_event){//邀请其他人
						   $("#invite_row").html("");
					   }
				   }else{//不需要显示立场
					   $("#show_viewpoint").html("");
					   $("#btn_row").html("");
					   $("#invite_row").html("");
				   } 
		       }else{
		    	   $("#show_viewpoint").append(viewpoint_h);  
		    	   $("#invite_row").html("");
		    	   $("#btn_row").html("");
		       }
			   
		
		
		
		
		$("#accept_invite").click(function (){
			$.ajax({
				type : "POST",
				url : contextPath+"/schedule/accept_invite",
				data : {"scheduleId":$("#scheduleId").val()},
				dataType : "text",
				success : function callback(result) {
					if(result=="true"){
						alert("操作完成！");
					}else{
						alert("请勿重复操作！");
					}
				},
				error: function (e)
				{
					alert("请求错误，请重试！");
				}
			});
		});
		
		$("#refuse_invite").click(function (){
			$.ajax({
				type : "POST",
				url : contextPath+"/schedule/refuse_invite",
				data : {"scheduleId":$("#scheduleId").val()},
				dataType : "text",
				success : function callback(result) {
					if(result=="true"){
						alert("操作完成！");
					}else{
						alert("请勿重复操作！");
					}
				},
				error: function (e)
				{
					alert("请求错误，请重试！");
				}
			});
		});
	</script>
</body>
</html>