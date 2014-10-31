$(function() {
	
	adjustWidthToForm();
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		adjustWidthToForm();
	});
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.95;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	
	$(".form_header .form_close").click(function(e) {
		returnWindow();
		window.close();
	});
	
    var uuid = $("#uuid").val();
	$.ajax({
			type:"post",
			async:false,
			data:{"uuid":uuid},
			url:ctx+"/org/user/getUser?",
			success:function(result){
				if(result.success){	
					if (result.data != null) {
						$("#userName").html(result.data.userName);
						$("#mobilePhone").html(result.data.mobilePhone);
						$("#officePhone").html(result.data.officePhone);
						$("#fax").html(result.data.fax);
						$("#idNumber").html(result.data.idNumber);
						$("#contactName").html(result.data.contactName);
						$("#email").html(result.data.email);
						$("#departmentName").html(result.data.departmentName);
						$("#jobName").html(result.data.jobName);
						$("#remark").html(result.data.remark);
						if(result.data.sex == 1){
							$("#sex").html("男");
						} else {
							$("#sex").html("女");
						}
						$("#code").html(result.data.code);
					}
				}else{
					oAlert("获取用户信息失败");
				}
			}
	});	        
});
