$(function() {
	var flag = 0;
	//发送
	var btn_send = "B012008";
	var btn_aginSend = "B012014";
	if($(".form_operate").find("button[id=B012008]").length==0&&$(".form_operate").find("button[id=B012010]").length>0){
		if($("#rel").val()==27){
				//重新提交审核
				btn_send = "B012014";
				$("#"+btn_send).text("重新发送");
			}else{
				//提交审核
				btn_send = "B012010";
				$("#"+btn_send).text("发送");
			}
	}
	$(".form_operate").find("button").each(function(){
		if($(this).attr("id")!="B012008"&&$(this).attr("id")!="B012010"&&$(this).attr("id")!="B012014"){
			$(this).remove();
		}
	});
	$(":button[name='" + btn_send + "']").show();
	
	var bean = {
			"cc" : null,
			"bcc" : null,
			"toId" : null,
			"rel" : null,
			"typeId" : null,
			"rootFormDataBean" : {}
	};
	if($("#rel").val()==23){
		$("#to").val($("#from").val());
		$("#toNames").val($("#fromName").val());
		$("#showMsg").show();
	} else if($("#rel").val()==24){
		$("#showMsg").hide();
	} else if($("#rel").val()==22||$("#rel").val()==27||$("#rel").val()==26){
		$("#showMsg").show();
		$("#to").val($("#toId1").val());
		$("#cc").val($("#cc1").val());
		$("#bcc").val($("#bcc1").val());
		$("#toNames").val($("#toNames1").val());
		$("#ccNames").val($("#ccNames1").val());
		$("#bccNames").val($("#bccNames1").val());
	}
	else{
		if($("#showToUnit").val() == "true"){
			$("#showMsg").show();
		} else {
			$("#showMsg").hide();
		}
	}
	
	$("#addCc").click(function() {
		$("#mcc").show();
		$("#addCctd").hide();
		$("#delCctd").show();
	});
	
	$("#delCc").click(function() {
		$("#mcc").hide();
		$("#addCctd").show();
		$("#delCctd").hide();
		$("#cc").val("");
		$("#ccNames").val("");
	});

	$("#addBcc").click(function() {
		$("#mbcc").show();
		$("#addBcctd").hide();
		$("#delBcctd").show();
	});

	$("#delBcc").click(function() {
		$("#mbcc").hide();
		$("#addBcctd").show();
		$("#delBcctd").hide();
		$("#bcc").val("");
		$("#bccNames").val("");
	});
	
	//$(".form_title h2").html($("#typeName").val());
	
	var dataUuid = $("#dataUuid").val();
	
	// 初使化
	JDS.call({
		service : 'formDataService.getFormData',
		data : [$("#formId").val(), dataUuid, null],
		success : function(result) {
			$("#dyform").dytable({
				data : result.data,
				isFile2swf:false,
				enableSignature:true,
				setReadOnly:null,//是否设置所有字段只读，true表示设置,false表示不设置
				supportDown:"1",//2表示防止下载 1表示支持下载 不设置表示默认支持下载
				btnSubmit : btn_send,
				beforeSubmit : submit,
				buttons:[{"text": "选择完成任务","method":function(){alert(1);},"subtableMapping":"dy_form_id_report_evaluate"},
				         {"text": "选择未完成任务","method":function(){alert(2);},"subtableMapping":"dy_form_id_report_evaluate"},
				         {"text": "选择未成任务","method":function(){alert(3);},"subtableMapping":"dy_form_id_report_plan"},
				         {"text": "选择未完务","method":function(){alert(4);},"subtableMapping":"dy_form_id_report_plan"},
				         ],
				open: function(){
				}
			});
			if($("#rel").val()!=27){
				$("#ZCH").val($("#ZCHval").val());
				$("#ZTMC").val($("#ZTMCval").val());
				$("#FDDBR").val($("#FDDBRval").val());
				$("#JYCS").val($("#JYCSval").val());
				$("#ZTLX").val($("#ZTLXval").val());
			}
			if($("#typeId").val() == "000000000XK") {
				$("#BZDWDM").val($("#unitId").val());
				$("#BZDWMC").val($("#unitName").val());
			}
		},
		error : function(xhr, textStatus, errorThrown) {
		}
	});
	adjustWidthToForm();
	$(window).resize(function(e) {
		// 调整自适应表单宽度
		adjustWidthToForm();
	});
	// 调整自适应表单宽度
	function adjustWidthToForm() {
		var div_body_width = $(window).width() * 0.76;
		$(".form_header").css("width", div_body_width - 5);
		$(".div_body").css("width", div_body_width);
	}
	
	$("#toNames").click(function(){
		$.unit.open({
			title : "选择单位",
			labelField : "toNames",
			valueField : "to",
			type : "SSGL",
			selectType : 2,
			showType : false,
			close : function(e){
			}
		});
	});
	
	$("#ccNames").click(function(){
		$.unit.open({
			title : "选择单位",
			labelField : "ccNames",
			valueField : "cc",
			type : "SSGL",
			selectType : 2,
			commonType : 1,
			showType : false,
			close : function(e){
			}
		});
	});
	
	$("#bccNames").click(function(){
		$.unit.open({
			title : "选择单位",
			labelField : "bccNames",
			valueField : "bcc",
			type : "SSGL",
			selectType : 2,
			commonType : 1,
			showType : false,
			close : function(e){
			}
		});
	});
	
	/** ******************************** 发送开始 ***************************** */
	$("#"+btn_aginSend).live("click",function(){
		if(flag==0){
			submit();
		}
	});
	// 发送处理
	function submit() {
		if(flag==0){
			flag = 1;
			if(!checkCAKey()){
				return false;
			}
			var rootFormData = $("#dyform").dytable("formData");
			//console.log(JSON.stringify(rootFormData));
			bean.rootFormDataBean = rootFormData;
			bean.cc = $("#cc").val();
			bean.bcc = $("#bcc").val();
			bean.toId = $("#to").val();
			bean.typeId = $("#typeId").val();
			bean.rel = $("#rel").val();
			bean.matterId = $("#matterId").val();
			bean.sendMonitorUuid = $("#sendUuid").val();
			if(btn_send == "B012010"){
				bean.rel = 26;//提交审核
			}else if(btn_send == "B012014"){
				bean.rel = 27;//重新提交审核
			}
			bean.correlationDataId = $("#correlationDataId").val();
			bean.correlationRecver = $("#correlationRecver").val();
			JDS.call({
				service : "exchangeDataClientService.saveAndSendData",
				data : [bean],
				success : function(result) {
					if(result.data="Success"){
						oAlert("发送成功",function (){returnWindow();window.close();});
					}else{
						oAlert("发送失败");
					}
				},
				error : function(xhr, textStatus, errorThrown) {
					oAlert("发送失败");
				}
			});
		}
	}
	
	/** ******************************** 发送结束 ***************************** */
	
});