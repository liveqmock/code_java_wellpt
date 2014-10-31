	function getLdxContact(uuid,mbmc) {
		//alert(mbmc);
		$.ajax({
				type:"post",
				async:false,
				data:{"uuid":uuid},
				url:ctx+"/org/user/getUser?",
				success:function(result){
					if(result.success){	
						if (result.data != null) {
							$("#contact_boUser").html(result.data.userName);
							$("#contact_sex").html(result.data.sex == "1" ? "男":result.data.sex == "2" ? "女" : "未知");
							$("#contact_dept").html(mbmc);
							$("#contact_mobilePhone").html(result.data.mobilePhone);
							$("#contact_otherMobilePhone").html(result.data.otherMobilePhone == null ? "" : result.data.otherMobilePhone);
							$("#contact_mainEmail").html(result.data.mainEmail);
							$("#contact_otherEmail").html(result.data.otherEmail);
							$("#contact_officePhone").html(result.data.officePhone);
							$("#contact_homePhone").html(result.data.homePhone);
							$("#contact_fax").html(result.data.fax);
							var json = new Object(); 
							var userSetStr = $(".contact-detail-show").html();
					        json.content = userSetStr;
					        json.title = "通讯录详情";
					        json.height= 480;
					        json.width= 720;
					        var buttons = new Object(); 
					        json.buttons = buttons;
					        showDialog(json);
						}
					}else{
						oAlert("获取用户信息失败");
					}
				}
		});	        
	}
