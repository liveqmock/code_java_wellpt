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
<%-- 旧版本的行政审批头部<h1 style="width: 230px;background:url('${ctx}/resources/pt/img/zzffzx-logo4.png') no-repeat 0 1px;height: 60px;" onclick="location.href='${ctx}/cms/cmspage/readPage?uuid=3aead82f-9e34-4540-8a08-0253da4e032e'">&nbsp;</h1> --%>
<h1 style="width: 230px;background:url('${ctx}/resources/pt/img/zzffzx-logo4.png') no-repeat 0 1px;height: 60px;cursor: auto;" >&nbsp;</h1>
<h2  style="width: 267px; border-bottom: 1px solid #FFFFFF;
    border-left: medium none;
    margin-left: -185px;font-size: 18px;margin-top: 17px;">
    <div style="text-align: left;letter-spacing: 15px;">厦门市审批服务云</div>
     <div style="text-align: left;font-size: 14px;">厦门市行政审批（商事登记）信息管理平台</div>
    </h2>

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
	<li><a href="${ctx}/cms/cmspage/readPage?uuid=8aa31ed9-66c8-4749-b118-aa2c323cb16b&treeName=MESSAGE&mid=201312721472258&moduleid=01c72d84-753a-43de-8475-b87527c8a693"><img class="member_message" src="${ctx}/resources/theme/images/v1_icon_message.png" />我的消息<sup class="num"  style="right: 17px;">0</sup></a></li>
</ul>
</div>
<iframe frameborder="0" height="0" name="rtx" src="about:blank" width="0"></iframe>
<div class="user-profile-settings" style="display: none;">
	<div >
		<div class="dialog_form_content">
			<table width="100%">
				<tbody>
				  <tr class="odd">
				    <td class="Label">照片</td>
				    <td class="value">
				    <form method="POST" id="upload_share_form" enctype="multipart/form-data" >
				    <div class="td_class" id="div_photo">
						<img id="user_photo"
								style="width: 80px; height: 100px"></img>
						 <span> <input class="input_photo" type="file" id="upload" name="upload" />
						 <input onclick="btnUpload()" class="input_photo" type="button" id="btn_upload" value="上传" class="btn" style="width: 50px;" />
						 <input class="input_photo" id="photoUuid" name="photoUuid" type="hidden">
						</span>
					</div>
					</form>
				   </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">性别</td>
				    <td class="value">
				    <div class="td_class" id="div_sex" style="float: left">
						<input id="sex_man" name="sex" type="radio" value="1" />
						男
						<input id="sex_girl" name="sex" type="radio" value="2" />
						女
					</div>
				   </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">手机</td>
				    <td class="value">
				    <div class="td_class" id="div_mobilePhone">
						<input class="v1_input_password" id="mobilePhone" name="mobilePhone" type="text" />
					</div>
					<div class="td_class"><span><input id="receiveSmsMessage"
						name="receiveSmsMessage" type="checkbox" style="float: left;" checked="checked">
						<label for="receiveSmsMessage">接收短信消息</label></span>
					</div>
				   </td>
				  </tr>
				  <tr>
				    <td class="Label">办公电话</td>
				    <td class="value">
				    <div class="td_class" id="div_officePhone">
				   		 <input class="v1_input_newpassword" id="officePhone" name="officePhone" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr class="odd">
				  </tr>
				    <tr class="odd">
				    <td class="Label">传真</td>
				    <td class="value">
				    <div class="td_class" id="div_fax">
				    <input class="v1_input_doublepassword" id="fax" name="fax" type="text" />
				    </div>
				    </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">身份证号</td>
				    <td class="value">
				    <div class="td_class" id="div_idNumber">
				    <input class="v1_input_doublepassword" id="idNumber" name="idNumber" type="text" />
				    </div>
				    </td>
				  </tr>
				  <tr class="odd" style="display: none;">
				    <td class="Label">我的去向</td>
				    <td class="value">
				    <div class="td_class" id="div_position_state">
				    </div>
				    </td>
				  </tr>
				</tbody>
			</table>
		</div>
	  </div>	
</div>
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
		var userSetStr = $(".user-profile-settings").html();
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
								$(".user-profile-settings").html("");
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
						$("#dialogModule #div_position_state").html(_trace);
					}
				}else{
					oAlert("获取用户信息失败");
				}
			}
		});
	}
	function findChecked(_trace) {
		$(".user-profile-settings input[name='trace']").each(function(){					
			if (_trace == $(this).val()) {		
				$(this).attr("checked","checked");
			} 
		});			
	}
	function btnUpload() {
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