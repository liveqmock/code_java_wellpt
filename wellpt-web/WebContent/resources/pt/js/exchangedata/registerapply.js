$(function() {
	
	//审核按钮
	var btn_pass = "C001010";
	var btn_notpass = "C001011";
	
	var object = {
			"uuid" : null,
			"zch" : null,
			"mc" : null,
			"frxm" : null,
			"frid" : null,
			"mp" : null,
			"email" : null,
			"submitTime" : null,
			"verifyTime" : null,
			"isVerify" : null,
			"verifyId" : null,
			"replyMsg" : null
	};
	if($("#isVerify").val() != "0") {
		$("#btns").hide();
		$("#msg").show();
		$("#verifyMsg").show();
		$("#reMsg").show();
	} else {
		$("#btns").show();
	}
	
	/** ******************************** 审核开始 ***************************** */
		
	//审核通过
	$("#" + btn_pass).click(function() {
		object.isVerify = 1;
		object.uuid = $("#uuid").val();
		JDS.call({
			service : "commercialBusinessService.doRegisterReply",
			async : false,
			data : [object],
			success: function(result) {
				if(result.data == "success") {
					oAlert("审核成功",function (){
						window.opener.location.href=window.opener.location.href;
						window.close();
					});
				} else {
					oAlert("审核失败");
				}
			},
			error : function(jqXHR) {
				oAlert("审核失败");
			}
		});
	});
	
	//审核不通过，填写原因
	function form_submit() {
		var fname = $("#fname").val();
		if(fname=="") {
			oAlert("请输入内容");
			return false;
		}
		object.replyMsg = fname;
		object.isVerify = -1;
		object.uuid = $("#uuid").val();
		JDS.call({
			service : "commercialBusinessService.doRegisterReply",
			async : false,
			data : [object],
			success: function(result) {
				if(result.data == "success") {
					oAlert("提交成功",function (){
						window.opener.location.href=window.opener.location.href;
						window.close();
					});
				} else {
					oAlert("提交失败");
				}
			},
			error : function(jxg) {
				oAlert("审核失败");
			}
		});
	}
	
	$("#"+ btn_notpass).click(function() {
		var json = new Object(); 
      		json.content = getHtml;
	        json.title = "主体注册审核";
	        json.height= 300;
	        json.width= 450;
	        var buttons = new Object(); 
	        buttons.提交 = form_submit;
	        json.buttons = buttons;
	        showDialog(json);
	});
	
	function getHtml(){
		var content1="<div id='test_con3' align='center'>"
			+"<table>"
			+"<tr>";
			content1+="<td align='left'><span style='font-size: 16px'>审核内容</span></td>";
			content1+="</tr>"
			+"<tr>"
			+"<td><textarea id='fname' rows='4' cols='80' name='fname' style='width: 335px; height: 110px;'></textarea></td>"
			+"</tr>"
			+"</table>"
			+"</div>";
		return content1;
	}
		
	/** ******************************** 审核结束 ***************************** */



});