<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<link href="${ctx}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
	<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="header">
<h1 onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=3aead82f-9e34-4540-8a08-0253da4e032e'">&nbsp;</h1>
<h2>统一业务应用与开发云平台</h2>
<ul class="user-info">
	<li class="slide"><a class="handle member-info-show" href="#">
	<img class="member_icon" src="${ctx}/resources/theme/images/v1_icon_user.png" />
	<span class="member-info-show" id="username">${user.userName}</span>
	<i class="member-info-show"> </i></a>
	<div class="slide-con"><i class="con-icon">&nbsp; </i>
	<div class="member-info member-info-show">
	<div class="clearfix info  member-info-show">
	<span class="photo  member-info-show">
	<div style="height: 120px;width: 120px;"><img alt="" class="member-info-show user_img" src="${ctx}${user.photoUrl}" /></div>
<%-- 	${ctx}/${user.photoUrl} --%>
	</span> 
	<input class="member-info-show" id="user_uuid" type="hidden" value="${user.uuid}"/>
	<div class="summary member-info-show">
		<p class="member-info-show">
			<span class="label member-info-show">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
			<span class="v1_head_username member-info-show">${user.userName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">部&nbsp;&nbsp;&nbsp;&nbsp;门：</span>
			<span class="v1_head_dpname member-info-show">${user.departmentName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">岗&nbsp;&nbsp;&nbsp;&nbsp;位：</span>
			<span class="v1_head_jobname member-info-show">${user.jobName}</span>
		</p>
		<p class="member-info-show">
			<span class="label member-info-show">上次登录：</span>
			<span class="v1_head_bftime member-info-show"><fmt:formatDate value="${user.lastLoginTime}" pattern="yyyy-MM-dd HH:mm"/></span>
		</p>
	</div>
	</div>

	<div class="operate member-info-show">
	<a class="password_modif member-info-show" href="#" style="display:none;">修改密码</a>
	<a class="member-info-show" id="op_userSet" href="#">用户设置</a>
	<c:if test="${rtxOpen == true}">
	<a class="member-info-show" href="${ctx}/basicdata/rtx/singlesignon" id="rtx" target="rtx">RTX</a>
	</c:if>
	
	<c:if test="${user.isAdmin == true}">
	<a class="member-info-show" href="${ctx}/passport/admin/main" id="admin_manage" target="_blank">管理后台</a>
	</c:if>
	<a class="member-info-show" href="${ctx}/security_logout">注销</a></div>
	</div>
	</div>
	</li>
	<li><a href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;treeName=MESSAGE&amp;mid=20136315910846&amp;moduleid=01c72d84-753a-43de-8475-b87527c8a693"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />我的消息<sup class="num">0</sup></a></li>
	<li><a href="${ctx}/cms/cmspage/readPage?uuid=666d0f04-7200-47f7-82d3-f85c495d8ce1&amp;mid=20136315910846,20136315812575&amp;moduleid=3fc451f3-bfc8-4a7e-ba21-0fa7c53f2efe,613b369e-687a-4491-beb4-ed361b0f301d&amp;treeName=MAIL_CATE"><img class="member_mail" src="${ctx}/resources/theme/images/v1_icon_mail.png" />我的邮件<sup class="num2">${emailCount}</sup></a></li>
</ul>

<ul class="top-nav">
	<c:forEach var="cmsCategory" items="${cmsCategorys}">
		<li><a href="${ctx}${cmsCategory.cc.inputUrl}">${cmsCategory.cc.title}</a></li>
	</c:forEach>
</ul>
</div>
<iframe frameborder="0" height="0" name="rtx" src="about:blank" width="0"></iframe>
<script>
	$(function() { 
		$(".handle").unbind("click");
		$(".handle").click(function(){
			if($(this).next().css("display")=='none'){
				$(this).next().show();
			}else{
				$(this).next().hide();
			}
		});
	    $(".slide-con").unbind("mouseover");
		$(".slide-con").mouseover(function(){
			$(this).show();
		}); 
		$(".slide-con").unbind("mouseout");
		$(".slide-con").mouseout(function(){
			$(this).hide();
		}); 
		$(".password_modif").unbind("click");
		$(".password_modif").click(function(){
	        /**$(".dialogcontent").html();**/
	        var json = new Object(); 
	        json.content = $(".op_password").html();
	        json.title = "修改密码";
	        json.height= 300;
	        json.width= 550;
	        var buttons = new Object(); 
	        buttons.提交 = password_submit;
	        json.buttons = buttons;
	        showDialog(json);
	    });
		var userSetStr = $(".op_userSetHtm").html();
		$("#op_userSet").click(function(){
	        /**$(".dialogcontent").html();**/
	        var uuid = $("#user_uuid").val();
			$.ajax({
					type:"post",
					async:false,
					data:{"uuid":uuid},
					url:ctx+"/org/user/getUser?",
					success:function(result){
						if(result.success){	
							if (result.data != null) {
								var json = new Object(); 
						        json.content = userSetStr;
						        json.title = "用户设置";
						        json.height= 480;
						        json.width= 550;
						        var buttons = new Object(); 
						        buttons.保存 = userSet;
						        json.buttons = buttons;
						        showDialog(json);		
						        $("#dialogModule #photoUuid").val(result.data.photoUuid);
								$("#dialogModule #user_photo").attr("src", ctx + "/org/user/view/photo/" + result.data.photoUuid);
								$("#dialogModule #mobilePhone").val(result.data.mobilePhone);
								if(result.data.receiveSmsMessage == "true" || result.data.receiveSmsMessage == true){
									$("#dialogModule #receiveSmsMessage").attr("checked", "checked");
								} else {
									$("#dialogModule #receiveSmsMessage")[0].checked= false;
								}
								$("#dialogModule #officePhone").val(result.data.officePhone);
								$("#dialogModule #fax").val(result.data.fax);
								$("#dialogModule #idNumber").val(result.data.idNumber);
								$("#dialogModule #photoUuid").val(result.data.photoUuid);	
								$("#dialogModule input[name='sex']").each(function(){
									if (result.data.sex == $(this).val()) {
										$(this).attr("checked","checked");
									} 
								});	
								$(".op_userSetHtm").html("");
								getDataDictionariesByType(result.data.trace);
							}
						}else{
							oAlert("获取用户信息失败");
						}
					}
			});	        
	    });			
	});
	function getDataDictionariesByType(trace) {
		/**
		if ($(".op_userSetHtm #div_position_state").html().trim() != "") {
			$("#dialogModule #div_position_state").html("");
			findChecked(trace);
			$("#dialogModule #div_position_state").html($(".op_userSetHtm #div_position_state").html());
			return ;
		}*/
		$.ajax({
			type:"post",
			async:false,
			data:{"type":"user_trace"},
			url:ctx+"/org/user/getDataDictionariesByType",
			success:function(result){
				if(result.success){	
					if (result.data != null && result.data.length > 0) {						
						$("#dialogModule #div_position_state").html("");	
						var _trace = "";
						for (var i = 0;i < result.data.length; i++) {
							var _checked = "";
							if (result.data[i].code==trace) {
								_checked = "checked='checked'";
							}
							_trace += "<input name='trace' type='radio' value='"+result.data[i].code+"' " + _checked + "/>"+result.data[i].name;
							
						}
						//$(".op_userSetHtm #div_position_state").html(_trace);
						$("#dialogModule #div_position_state").html(_trace);
					}
				}else{
					oAlert("获取用户信息失败");
				}
			}
		});
	}
	function findChecked(_trace) {
		$(".op_userSetHtm input[name='trace']").each(function(){					
			if (_trace == $(this).val()) {		
				$(this).attr("checked","checked");
			} 
		});			
	}
	function btnUpload() {
		// 上传文件
		/**$.ajaxFileUpload({
			url : ctx + "/org/user/upload/photo",// 链接到服务器的地址
			secureuri : false,
			fileElementId : 'upload',// 文件选择框的ID属性
			dataType : 'text', // 服务器返回的数据格式
			success : function(data, status) {
				$("#dialogModule #photoUuid").val(data);
				$("#dialogModule #user_photo").attr("src",
					ctx + "/org/user/view/photo/" + data);
				$("#dialogModule #user_photo").show();
			},
			error : function(data, status, e) {
				alert("上传失败");
			}
		});*/
	$("#upload_share_form").ajaxSubmit({
        success: function (data, status) {
        	$("#dialogModule #photoUuid").val(data);
			$("#dialogModule #user_photo").attr("src",
				ctx + "/org/user/view/photo/" + data);
			$("#dialogModule #user_photo").show();
        },
        url : ctx + "/org/user/upload/photo",
        data: $('#upload_share_form').formSerialize(),
        type: 'POST',
        dataType: 'text',
        beforeSubmit: function () {
        }
    });
	}
</script>
</body>
</html>