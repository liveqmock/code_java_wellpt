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
	var user_isShowContact = $("#user_isShowContact").val();
	if(user_isShowContact == 'true') {  //当前用户是业务管理员
		var popup = false; 
		var popupMsg = '';
		if($("#user_contact_is_null").val() == "true"){
			popup = true;
			popupMsg += '、联系人';
		} 
		if($("#user_mobilePhone").val() == '') {
			popup = true;
			popupMsg += '、手机';
		} 
		if($("#user_officePhone").val() == '') {
			popup = true;
			popupMsg += '、办公电话';
		}
		if(popup) {
			oAlert('请先设置' +popupMsg.substring(1, popupMsg.length)+"！");
			getUserMsg();
		}
	}
	
	
	$("#op_userSet").click(function(){
		getUserMsg();
	});
	function getUserMsg() {
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
					        json.height= 525;
					        json.width= 585;
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
							//$("#dialogModule #photoUuid").val(result.data.photoUuid);	
							$("#dialogModule #contactName").val(result.data.contactName);
							$("#dialogModule #email").val(result.data.email);
							
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
	}
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
	
