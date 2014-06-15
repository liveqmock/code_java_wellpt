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
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/jqueryui/css/base/jquery-ui.css" />
<link rel="stylesheet" href="${ctx}/resources/pt/css/jquery.jScrollPane.css" />
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
		function showMessage(noReadMessageCount){  
		   $(".num").html(noReadMessageCount);
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
	function sendMessage(){
		var userId = $("#dialogModule #userId").val();
		var body = $("#dialogModule #datetime_sec_col").val(); 
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
				data : "userId="+userId+"&body="+body+"&type="+type,
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
					  <tr class="odd">
					    <td class="Label">收件人</td>
					    <td class="value">
					    <div class="td_class">
							<input type="hidden" id="userId" name="userId"/>
							<input type="text" id="showUser" name="showUser" style="width:95%;height:20px" value=""/>
					   </div>
					   </td>
					  </tr>
					  <tr>
					    <td class="Label">消息内容</td>
					    <td class="value">
					    <div class="td_class">
					   		<textarea  id="datetime_sec_col" style="width:95%; height:150px;" name="body"></textarea>
					    </div>
					    </td>
					  </tr>
					  <tr class="odd">
					  </tr>
					    <tr class="odd">
					    <td class="Label">消息类型</td>
					    <td class="value">
					    	<div class="td_class">
					    	<input type="checkbox" class="messageType" name="type" value="ON_LINE" checked="checked"/>发在线消息&nbsp;
<!-- 					    	<input type="checkbox" class="messageType" name="type" value="EMAIL" />发邮件&nbsp; -->
<!-- 					    	<input type="checkbox" class="messageType" name="type" value="SMS" />发短信 -->
							</div>
					    </td>
					  </tr>
					</tbody>
				</table>
			</div>
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
				var search = decodeURIComponent(window.location.search);
				var moduleid = "";
				var mid = "";
				if(search.indexOf("keyword")>-1||search.indexOf("moduleid")>-1){
					var searchArray = search.replace("?", "").split("&");
					for ( var i = 0; i < searchArray.length; i++) {
						var paraArray = searchArray[i].split("=");
						var key = paraArray[0];
						var value = paraArray[1];
						if(key=='keyword'){
							$("#abc").dyView("keySelectClick", value);
							$("#keyWord").val(value);
						}else if(key=='moduleid'){
							moduleid = value;
						}else if(key=='mid'){
							mid = value;
						}
					}
				}
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
		var user_isShowContact = $("#user_isShowContact").val();
		var mobilePhone = $("#dialogModule #mobilePhone").val();
		var officePhone = $("#dialogModule #officePhone").val();
		var contactName = $("#contactName").val();
		if(user_isShowContact == 'true') {
			if(contactName=='' || contactName==null) {
				oAlert('请输入联系人的真实姓名！');
				return false;
			} else if(!isChinese(contactName)){
				oAlert('联系人请输入中文！');
				return false;
			}
			if(mobilePhone=='' ||mobilePhone==null) {
				oAlert('请输入手机号！');
				return false;
			}
			if(officePhone == '' || officePhone==null) {
				oAlert('请输入办公电话！');
				return false;
			}
		}
        if(mobilePhone!='' && !isMobile(mobilePhone)){
    	 	oAlert('请输入正确的手机号！');
    	 	return false;
        } 
        if(officePhone!='' && !isTel(officePhone)) {
        	oAlert('请输入正确的办公电话！');
        	return false;
        } 
		
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
		var email = $("#email").val();
		$.ajax({
				type:"post",
				async:false,
				data:{"uuid":uuid,"photoUuid":photoUuid,"sex":sex,"mobilePhone":mobilePhone,"receiveSmsMessage": receiveSmsMessage,"officePhone":officePhone,"fax":fax,"idNumber":idNumber,"trace":trace,"contactName":contactName,"email":email},
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
	function jsmod(obj){
		$(obj).each(function(){
			if($(this).height()<$(this).children().eq(0).height()){
				$(this).jScrollPane();
			}
		});
	}
	jsmod(".dnrw .tab-content");
</script>
<%
	// 加入附件签名控件
	String fjcaControlContextPath = request.getContextPath();
	if("/".equals(fjcaControlContextPath)) {
		fjcaControlContextPath = "";
	}
	String fjcaWs = "<object id=\"fjcaWs\" name=\"SBFjCAEnAndSign\" classid=\"CLSID:506038C2-52A5-4EA5-8F7D-F39B10265709\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/SBFjCAEnAndSign.ocx\"></object>";
	String fjcaControl = "<object id=\"fjcaControl\" classid=\"clsid:414C56EC-7370-48F1-9FB4-AF4A40526463\" codebase=\"" + fjcaControlContextPath + "/resources/pt/js/security/fjcaControl.ocx\" ></object>";
	out.print(fjcaWs);
	out.print(fjcaControl);
%>
</body>
</html>