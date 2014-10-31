<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pt/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<html>
<head>
<title>我的资料</title>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<script src="${ctx}/resources/jquery/jquery.js"></script>
</head>
<body>
<div class="viewContent">
<script src="${ctx}/resources/app/js/xzsp/xzsp_cmsbtn.js"></script>
<link href="${ctx}/resources/theme/css/urlPage.css" rel="stylesheet">
<div class="user-profile-settings">
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
					  <tr>
					    <td class="Label">联系人*</td>
					    <td class="value">
					    <div class="td_class" id="div_contactName">
					   		 <input class="v1_input_newpassword" id="contactName" name="contactName" type="text" />
				   		</div>
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
				   <tr>
				    <td class="Label">手机*</td>
				    <td class="value">
				    <div class="td_class" id="div_mobilePhone">
						<input class="v1_input_password" id="mobilePhone" name="mobilePhone" type="text" />
					<div class="div_input_phone"><input id="receiveSmsMessage"
						name="receiveSmsMessage" type="checkbox" style="float: left;" checked="checked">
						<label for="receiveSmsMessage">接收短信消息</label>
					</div>
					</div>
				   </td>
				  </tr>
				  <tr class="odd">
				    <td class="Label">邮件</td>
				    <td class="value">
				    <div class="td_class" id="div_email">
				   		 <input class="v1_input_newpassword" id="email" name="email" type="text" />
			   		</div>
				    </td>
				  </tr>
				  <tr>
				    <td class="Label">办公电话*</td>
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
				  <tr>
				  	<td class="Label"></td>
				  	<td class="value">
				  		<div  class="customButton customButton_top">
				  			<button id="saveData" >保存</button>
				  		</div>
				  	</td>
				  </tr>
				</tbody>
			</table>
		</div>
</div>
<script type="text/javascript">
/***********个人设置*****************/

$("#saveData").click(function() {
	alert(1);
	userSet();
});

function userSet(){
//		var user_isShowContact = $("#user_isShowContact").val();
	alert($("#dialogModule #mobilePhone").length);
	var mobilePhone = $("#dialogModule #mobilePhone").val();
	var officePhone = $("#dialogModule #officePhone").val();
	var contactName = $("#contactName").val();
//		if(user_isShowContact == 'true'||user_isShowContact == '') {
	alert(contactName);
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
//		}
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
</script>
</div>
</body>
</html>