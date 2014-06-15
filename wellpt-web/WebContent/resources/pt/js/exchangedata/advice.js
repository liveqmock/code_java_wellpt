$(function() {
	
	//回复
	var btn_reply = "C001003";
	
	var object = {
			"uuid" : null,
			"body" : null,
			"title" : null,
			"type" : null,
			"submitTime" : null,
			"submitUserName" : null,
			"unit" : null,
			"isReply" : null,
			"replyTime" : null,
			"replyId" : null,
			"replyMsg" : null
	};
	if($("#isReply").val() == "true") {
		$("#btns").hide();
		$("#msg").show();
		$("#reMsg").show();
		$("#replyUnitName").val();
	} else {
		$("#btns").show();
	}
	if($("#type").val() == "1") {
		$("#tab").html("咨询信息");
		$("#typeshow").html("咨询");
	} else {
		$("#tab").html("投诉信息");
		$("#typeshow").html("投诉");
	}
	
	/** ******************************** 回复开始 ***************************** */
		
	function form_submit() {
		var fname = $("#fname").val();
		if(fname=="") {
			oAlert("请输入内容");
			return false;
		}
		object.replyMsg = fname;
		object.uuid = $("#uuid").val();
		JDS.call({
			service : "commercialBusinessService.doReply",
			async : false,
			data : [object],
			success: function(result) {
				if(result.data == "success") {
					oAlert("回复成功",function (){
						//returnWindow();
						window.opener.location.href=window.opener.location.href;
						window.close();
					});
				} else {
					oAlert("回复失败");
				}
				
			},error :function(htg) {
				oAlert("回复失败");
			}
		});
	}
	
	$("#"+ btn_reply).click(function() {
		var json = new Object(); 
      		json.content = getHtml;
	        json.title = "回复";
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
			content1+="<td align='left'><span style='font-size: 16px'>回复内容</span></td>";
			content1+="</tr>"
			+"<tr>"
			+"<td><textarea id='fname' rows='4' cols='80' name='fname' style='width: 335px; height: 110px;'></textarea></td>"
			+"</tr>"
			+"</table>"
			+"</div>";
		return content1;
	}
		
	/** ******************************** 回复结束 ***************************** */



});