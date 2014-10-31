$(function() {
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
			"dyFormData" : {}
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
	
	// 初使化
	var dataUuid = $("#dataUuid").val();
	var formDatas = loadFormDefinitionData($("#formId").val(), dataUuid);
	if(typeof formDatas == "string"){
		formDatas = (eval("(" + formDatas +  ")"));
	}
	$("#dyform").dyform(
		{
			definition:  formDatas.formDefinition ,
			data:formDatas.formDatas,
			displayAsFormModel:false,
			success:function(){
				console.log("表单解析完毕");
			},
			error:function(){
				console.log("表单解析失败");
			}
		} 
	); 
	$("#dyform").dyform("setEditable");
	
	if($("#rel").val()!=27){
		$("#ZCH").val($("#ZCHval").val());
		$("#ZTMC").val($("#ZTMCval").val());
		$("#FDDBR").val($("#FDDBRval").val());
		$("#JYCS").val($("#JYCSval").val());
		$("#ZTLX").val($("#ZTLXval").val());
	}
	if($("#typeId").val() == "000000000XK") {
		$("#dyform").dyform("setFieldValue", "BZDWDM", $("#unitId").val());
		$("#dyform").dyform("setFieldValue", "BZDWMC", $("#unitName").val());
	}
	
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
	// 发送处理
	$("#"+btn_send).click(function(){
		validateForm = $("#dyform").dyform("validateForm");
		if(validateForm ==  undefined){
			return false;
		}else if(validateForm == false){
			return false;
		}
		var formData = $("#dyform").dyform("collectFormData");
		if(!checkCAKey()){
			return false;
		}
		bean.dyFormData = formData;
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
	});
	$("#"+btn_aginSend).click(function(){
		$("#"+btn_Send).trigger("click");
	});
	
	$("#openHisModule").live("click",function(){
		var json = new Object(); 
		json.content = "<div class='queryDiv' style='height:50px;'>" +
				"<div style='float: left;margin: 5px 0 0 5px;'><div style='width:45px;float: left;'>注册号:</div><div style='float: left;'><input class='zch'/></div></div>" +
				"<div style='float: left;margin: 5px 0 0 5px;'><div style='width:60px;float: left;'>主体名称:</div><div style='float: left;'><input class='ztmc'/></div></div>" +
				"<div style='float: left;margin: 5px 0 0 5px;'><button id='queryBtn' class='blurBtn' type='button'>查询历史数据</button>" +
				"<button id='sendBtn' class='blurBtn' style='display:none;' type='button'>提取历史数据</button></div>" +
				"</div>"+
				"<div class='dnrw' style='width:99%;background:none;'></div>";
		json.title = "查询历史数据";
        json.height= 600;
        json.width= 800;
        showDialog(json);
        
	});
	$("#relationDate").live("click",function(){
		 var ctl = 	$("#dyform").dyform("getControl", "ZCH");
		 $("input[name='" + ctl.getCtlName() + "']").click();
	});
	
	$("#queryBtn").live("click",function(){
		$("#sendBtn").hide();
		var unitId = $("#unitId").val();
		var zch = $(".zch").val();
		var ztmc = $(".ztmc").val();
		if(zch==""&&ztmc==""){
			oAlert("注册号或主体名称必须输入一项!");
			return false;
		}
		pageLock("show");
		$.ajax({
			type:"post",
			async:false,
			data:{"unitId":unitId,"zch":zch,"ztmc":ztmc},
			url:ctx+"/exchangedata/client/queryHistoryDate",
			success:function(result){
				var htmlStr = "";
				var code = result.code;
				var msg = result.msg;
				var totalRow = result.totalRow;
				var datalist = result.datalist;
				if(code == 1){
					htmlStr = "<table class='table'>"+
						"<thead>"+
							"<tr class='thead_tr'>"+
								"<td width='15px' class='checks_td'><input type='checkbox' class='checkall'></td>"+
								"<td width='30%'>企业名称</td>"+
								"<td width='20%'>注册号</td>"+
								"<td width='15%'>企业类型</td>"+
								"<td width='20%' class='last'>法定代表人</td>"+
							"</tr>"+
							"</thead>"+
							"<tbody id='template' style='clear: both;'>";
					for(var i=0;i<datalist.length;i++){
						var map = datalist[i];
						htmlStr += "<tr class='dataTr'>";
						htmlStr += "<td width='15px'><input type='checkbox' class='checkeds' value='"+map['ZCH']+"'></td>"+
						"<td width='30%'>"+map['ZCH']+"</td>"+
						"<td width='20%'>"+map['ZTMC']+"</td>"+
						"<td width='15%'>"+map['ZTLX']+"</td>"+
						"<td width='20%'>"+map['FDDBR']+"</td>";
						htmlStr += "</tr>";
					}		
					htmlStr += "</tbody></table>";
					htmlStr += "共返回"+totalRow+"条数据";
//					$(".queryDiv").append("<button id='sendBtn' class='blurBtn' type='button'>请求历史数据</button>");
					$("#sendBtn").show();
				}else{
					htmlStr = msg;
				}
				$(".dnrw").html(htmlStr);
				pageLock("hide");
			}
		});
	});
	
	$("#sendBtn").live("click",function(){
		if($(".checkeds:checked").length==0){
			oAlert("请选择一条数据");
		}else if($(".checkeds:checked").length>1){
			oAlert("每次只能选择一条数据");
		}else{
			var zch = $(".checkeds:checked").val();
			var unitId = $("#unitId").val();
			pageLock("show");
			JDS.call({
				service : "exchangeDataFlowService.historyDataRequest",
				data : [ zch,unitId],
				success : function(result) {
					pageLock("hide");
					oAlert("请求成功");
				},
				error : function(jqXHR) {
					pageLock("hide");
					oAlert("请求失败");
				}
			});
		}
	});
});