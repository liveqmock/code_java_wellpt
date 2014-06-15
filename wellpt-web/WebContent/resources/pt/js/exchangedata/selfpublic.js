$(function() {
	
	//审核按钮
	var btn_pass = "C001010";
	var btn_notpass = "C001011";
	var bean = {
			"uuid" : null,
			"type" : null,
			"zch" : null,
			"mc" : null,
			"sydw" : null,
			"qdrq" : null,
			"submitTime" : null,
			"verifyTime" : null,
			"verifyId" : null,
			"isVerify" : null,
			"replyMsg" : null,
			"reMc" : null,
			"reFrxm" : null,
			"yxrq" : null
	};
	if($("#isVerify").val() != "0") {
		$("#btns").hide();
		$("#msg").show();
		$("#verifyMsg").show();
		$("#reMsg").show();
	} else {
		$("#btns").show();
	}
	if($("#type").val() == "1") {
		$("#tab").html("荣誉信息");
		$("#typeshow").html("荣誉");
	} else {
		$("#tab").html("资质信息");
		$("#typeshow").html("资质");
	}
	
	/** ******************************** 审核开始 ***************************** */
	
	//审核通过
	$("#" + btn_pass).click(function() {
		bean.isVerify = 1;
		bean.uuid = $("#uuid").val();
		JDS.call({
			service : "commercialBusinessService.doSelfPublicVerify",
			async : false,
			data : [bean],
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
		bean.replyMsg = fname;
		bean.isVerify = -1;
		bean.uuid = $("#uuid").val();
		JDS.call({
			service : "commercialBusinessService.doSelfPublicVerify",
			async : false,
			data : [bean],
			success: function(result) {
				if(result.data == "success") {
					oAlert("提交成功",function (){
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
	}
	
	$("#"+ btn_notpass).click(function() {
		var json = new Object(); 
      		json.content = getHtml;
	        json.title = "公示审核";
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