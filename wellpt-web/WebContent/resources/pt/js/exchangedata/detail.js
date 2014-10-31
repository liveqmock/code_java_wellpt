$(function() {
	//签收
	var btn_sign = "B012001";
	
	//退回
	var btn_refuse = "B012002";
	
	//回复
	var btn_reply = "B012003";
	
	//转发
	var btn_repeat = "B012005";
	
	//注销
	var btn_cancel = "B012006";
	
	//补发
	var btn_reapply = "B012007";
	
	// 下载
	var btn_download = "B012009";
	
	//出证
	var btn_cz = "B012004";
	
	//不同意上传
	var btn_bsc = "B012011";
	
	//同意上传
	var btn_sc = "B012012";
	
	//编辑（审核被退回）
	var btn_edit = "B012013";
	
	//删除（审核被退回）
	var btn_del = "B012015";
	
	//标记出证
	var btn_flag_cz = "B012016";
	
	var type_ID_SSXX_XZXK = "000000000XK";
	var type_ID_SSXX_XZCF = "000000000CF";
	var type_ID_SSXX_QDRY = "000000000RY";
	var type_ID_SSXX_QDZZ = "000000000ZZ";
	var type_ID_SSXX_NBBA = "004140203NB";
	
	var signService = "exchangeDataClientService.signData";
	var refuseService = "exchangeDataClientService.refuseData";
	var cancelService = "exchangeDataClientService.cancel";
	var reApplyService = "exchangeDataClientService.reapply";
	var examineService  = "exchangeDataFlowService.examineExchange";
	var repeatService = "exchangeDataFlowService.reapeat";
	var delExamineService = "exchangeDataFlowService.delExamineExchange";
	var markCertificateService = "exchangeDataFlowService.markCertificateStatus";
	
	function hideButtons(name) {
		$(":button[name='" + name + "']").each(function() {
			$(this).hide();
		});
	}
	function showButtons(name) {
		$(":button[name='" + name + "']").each(function() {
			$(this).show();
		});
	}
	
	var sendNode = $("#sendNode").val();
	var receiveNode =  $("#receiveNode").val();
//	var showRefuse = $("#showRefuse").val();
	if(sendNode != "") { //发件显示 注销 补发
		$("#sendMode").show();
		$("#receiveMode").remove();
		$("#routeMsg").show();
		
		if(sendNode=="examineIng"){
			showButtons(btn_bsc);
			showButtons(btn_sc);
		}else if(sendNode=="examineFail"){
			showButtons(btn_edit);
			showButtons(btn_del);
		}else{
			if($("#sendType").val()=="distribution") {
				showButtons(btn_refuse);
			}
			if($("#cancelUnits").val()!=""&&$("#cancelUnits").val()!=undefined&&$("#cancelUnits").val()!=null){
				showButtons(btn_cancel);
			}
			//没有接收单位的时候不显示补发
			if($("#routeMsg").find(".tableForm .odd").length>1){
				showButtons(btn_reapply);
			}
			if($("#sendLimitNum").val() != "" && $("#sendLimitNum").val() != 0 && $("#sendLimitNum").val()!= undefined) {
				$("#sendLimit").show();
			}
		}
	} else if(receiveNode != "") {
		$("#receiveMode").show();
		$("#sendMode").remove();
		//$("#routeMsg").remove();
		$("#routeMsg").show();
		if(receiveNode == 'wait'){  //待收 显示签收 退回
			showButtons(btn_sign);
			showButtons(btn_refuse);
		} else if(receiveNode == 'sign') { //已签收完 显示退回 回复 出证 转发
			showButtons(btn_refuse);
			showButtons(btn_cz);
			showButtons(btn_reply);
			showButtons(btn_repeat);
			showButtons(btn_download);
			//标记出证状态
			showButtons(btn_flag_cz);
			if($("#sendNode").val()=="reply"){
				$("#"+btn_flag_cz).text("取消标记出证");
			}
		} else if(receiveNode == 'transfer'){
			showButtons(btn_refuse);
		} else if(receiveNode == 'back'){ //退回件 显示签收
			showButtons(btn_sign);
		}
		if($("#status").val()!=""&& $("#status").val()=="replyLimit") { //接收逾期
			$("#showLimitUnit").show();
		}
		if($("#replyLimitNum").val() != "" && $("#replyLimitNum").val()!= undefined) {
			$("#replyLimit").show();
		}
		//如果当前用户所在单位没有下属区单位时，转发按钮隐藏
		if($("#hasUnderling").val() == '0') {
			hideButtons(btn_repeat);
		} 
	}
	
	//移除其他不显示按钮
	$(":button[style='display: none']").each(function(i) {
		$(this).remove();
	});
	
	var formDatas = loadFormDefinitionData($("#formId").val(), $("#dataUuid").val());
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
	$("#dyform").dyform("showAsLabel");
//	var typeid = $("#typeId").val();
//	if(typeid != "004140203SZ") {//非商事登记
//		$("span[name='ZTMC']").css("color","blue");
//		$("span[name='ZTMC']").css("cursor","pointer");
//		$("span[name='ZTMC']").click(function(){
//			var zch = $("input[name='ZCH']").val();
//			if(zch!=undefined && zch!=""){
//				window.open(ctx+"/exchangedata/client/toZTDJXX.aciton?zch="+zch);
//			}
//		});
//	}
	
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
	
	
	
	/** ******************************** 签收开始 ***************************** */
	
	$(":button[name='"+ btn_sign +"']").each(function(){
		$(this).click($.proxy(onSign, this));
	});
	
	function onSign(e) {
	  if(checkCAKey()){
		var uuid = $("#uuid").val();
		JDS.call({
			service : signService,
			data : [uuid],
			success : function(result) {
				oAlert("签收成功",function (){returnWindow();window.close();});
			},
			error : function(jqXHR) {
				oAlert("签收失败");
			}
		});
	  }
	}
	
	/** ******************************** 签收结束 ***************************** */
	
	
	
	/** ******************************** 退回开始 ***************************** */
	
	$(":button[name='"+ btn_refuse +"']").each(function(){
		$(this).click($.proxy(onRefuse, this));
	});
	
	function onRefuse(e) {
	  if(checkCAKey()) {
		var str= "<div style='margin:10px;'><div>退回原因</div><div><textarea id='msg_' style='height:195px;width:465px;'></textarea></div></div>";
		var json = new Object(); 
        json.content = str;
        json.title = "退回发件";
        json.height= 350;
        json.width= 500;
        var buttons = new Object(); 
        buttons.退回 = refuse;
        json.buttons = buttons;
        showDialog(json);
	  }
		
	}
	function refuse(){
		var sendType = $("#sendType").val();
		if(sendType == "distribution"){
			var uuid = $("#uuid").val();
			var msg = $("#msg_").val();
			msg = msg.replace(/\s+/g,"");
			if(msg==""){
				oAlert("请填写退回原因！");
				return false;
			}
			JDS.call({
				service : refuseService,
				data : [uuid,msg,"send"],
				success : function(result) {
					$("#"+ btn_refuse).attr("disabled", true);
					oAlert("退回成功",function (){returnWindow();window.close();});
				},
				error : function(jqXHR) {
					oAlert("退回失败");
				}
			});
		}else{
			var uuid = $("#uuid").val();
			var msg = $("#msg_").val();
			msg = msg.replace(/\s+/g,"");
			if(msg==""){
				oAlert("请填写退回原因！");
				return false;
			}
			JDS.call({
				service : refuseService,
				data : [uuid,msg,"receive"],
				success : function(result) {
					$("#"+ btn_refuse).attr("disabled", true);
					oAlert("退回成功",function (){returnWindow();window.close();});
				},
				error : function(jqXHR) {
					oAlert("退回失败");
				}
			});
		}
	}
	/** ******************************** 退回结束 ***************************** */

	
	
	
	/** ******************************** 回复开始 ***************************** */
	
	$("#"+ btn_reply).click(function() {
		if(checkCAKey()){
			var json = new Object(); 
			$.ajax({
				type:"post",
				async:false,
				data:{"type":"user_trace"},
				url:ctx+"/exchangedata/client/toTypeList?dataId="+$("#dataId").val()+"&recVer="+$("#recVer").val()+"&rel=23",
				success:function(result){
					 var $toSendPage = $(result);
					 //业务类型只有一个，直接跳转到发件页
					 if($(".toSendPage", $toSendPage).length==1) {
						 	var msg = $(".toSendPage", $toSendPage);
						    var rel = msg.attr("rel");
							var typeUuid = msg.attr("typeUuid");
							var dataId = msg.attr("dataId");
							var recVer = msg.attr("recVer");
							if(rel == 23){
								window.open(ctx +"/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=23&dataId="+dataId+"&recVer="+recVer);
							}
							
					 } else {
						 json.content = result;
						 json.title = "选择业务类型";
						 json.height= 300;
						 json.width= 650;
						 showDialog(json);
					 }
				}
			});
		}
	});

	/** ******************************** 回复结束 ***************************** */

	/** ******************************** 出证开始 ***************************** */
	
	$("#"+ btn_cz).click(function() {
		if(checkCAKey()){
			var json = new Object(); 
			$.ajax({
				type:"post",
				async:false,
				data:{"type":"user_trace"},
				url:ctx+"/exchangedata/client/toTypeList?dataId="+$("#dataId").val()+"&recVer="+$("#recVer").val()+"&rel=23",
				success:function(result){
					var $toSendPage = $(result);
					 //业务类型只有一个，直接跳转到发件页
					 if($(".toSendPage", $toSendPage).length==1) {
						 	var msg = $(".toSendPage", $toSendPage);
						    var rel = msg.attr("rel");
							var typeUuid = msg.attr("typeUuid");
							var dataId = msg.attr("dataId");
							var recVer = msg.attr("recVer");
							 if(rel == 23){
									window.open(ctx +"/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=23&dataId="+dataId+"&recVer="+recVer);
								}
							
					 } else {
						 json.content = result;
						 json.title = "选择业务类型";
						 json.height= 300;
						 json.width= 645;
						 showDialog(json);
					 }
				}
			});
		}
	});

	/** ******************************** 出证结束 ***************************** */
	
	
	/** ******************************** 转发开始 ***************************** */
	
	$(":button[name='"+ btn_repeat +"']").each(function(){
		$(this).click($.proxy(onReapeat, this));
	});
	
	function onReapeat(e) {
		if(checkCAKey()){
			var str = "<div style='margin:20px;'>";
		
			$(".includeUnits").each(function() {
				str += "<div style='margin:5px;'><input class='iUnitId' style='margin-right:5px;' name='iUnitId' type='checkbox' value='" + $(this).attr("id") + "'";
				str += "/>" + $(this).val() + "</div>";
			});	
			
			str += "</div>";
			var json = new Object(); 
	        json.content = str;
	        json.title = "转发发件";
	        json.height= 350;
	        json.width= 500;
	        var buttons = new Object(); 
	        buttons.转发 = reapeat;
	        json.buttons = buttons;
	        showDialog(json);
		}
	
	}
	
	function reapeat(){
		var unitIds = "";
		$("input[name='iUnitId']:checkbox").each(function(){ 
            if($(this).attr("checked")){
            	unitIds += $(this).val()+";";
            }
        });
		unitIds = unitIds.substring(0, unitIds.lastIndexOf(";"));
		unitIds = unitIds.replace(/\s+/g,"");
		if(unitIds==""){
			oAlert("请选择区级单位！");
			return false;
		}
		JDS.call({
			service : repeatService,
			data : [ 22, $("#uuid").val(), unitIds],
			success : function(result) {
				oAlert("转发成功",function (){returnWindow();window.close();});
			},
			error : function(jqXHR) {
				oAlert("转发失败");
			}
		});
	}
	
	/** ******************************** 转发结束 ***************************** */
	
	
	

	/** ******************************** 注销开始 ***************************** */
	
	$(":button[name='"+ btn_cancel +"']").each(function(){
		$(this).click($.proxy(onCancel, this));
	});
	
	function onCancel(e) {
	  if(checkCAKey()){
		var str = "";
		var cancelUnits = $("#cancelUnits").val();
		var unitArray = cancelUnits.split(";");
		for(var i=0;i<unitArray.length;i++){
			str += "<div style='margin:5px;'><input style='margin: 0 5px 0 0;' class='iUnitId' name='iUnitId' type='checkbox' matterId='"+unitArray[i].split(":")[2]+"' value='"+unitArray[i].split(":")[0]+"'";
			if(unitArray[i].split(":")[2]=="null"){
				str += "/>"+unitArray[i].split(":")[1]+"</div>";
			}else{
				str += "/>"+unitArray[i].split(":")[1]+"("+unitArray[i].split(":")[2]+")"+"</div>";
			}
		}
		str += "<div style='margin:5px;height:100px;width:200px;'><textarea id='iMsg' style='height: 80px;width:473px;'></textarea></div></div>";
		var json = new Object(); 
        json.content = str;
        json.title = "选择单位";
        json.height= 350;
        json.width= 500;
        var buttons = new Object(); 
        buttons.注销 = cancel;
        json.buttons = buttons;
        showDialog(json);
	  }
	}
	
	function cancel(){
		var unitIds = "";
		var matterIds = "";
		$("input[name='iUnitId']:checkbox").each(function(){ 
            if($(this).attr("checked")){
            	unitIds += $(this).val()+";";
            	matterIds += $(this).attr("matterId")+";";
            }
        });
		var msg = $("#iMsg").val();
		if(unitIds==""){
			oAlert("请选择需要注销的单位");
			return false;
		}
		JDS.call({
			service : cancelService,
			data : [ 25,$("#dataId").val(), $("#recVer").val(),unitIds,msg,$("#fromId").val(),matterIds],
			success : function(result) {
				oAlert(result.data,function (){returnWindow();window.close();});
			},
			error : function(jqXHR) {
				oAlert("注销失败");
			}
		});
	}
	/** ******************************** 注销结束 ***************************** */
	
	/** ******************************** 补发开始 ***************************** */
	
	$("#" + btn_reapply).click(function() {
		if(checkCAKey()){
			$.unit.open({
				title : "选择单位",
				type : "Unit",
				showType : false,
				commonType : 1,
				afterSelect : function(retVal){
					var unitId = retVal["id"];
					unitId = unitId.replace(/\s+/g,"");
					if(unitId==""){
						oAlert("请选择需要补发的单位！");
						return false;
					}
					JDS.call({
						service: reApplyService, 
						data: [24,$("#uuid").val(), unitId], 
						success:function(result){
							oAlert("补发成功",function (){returnWindow();window.close();});
						},
						error : function(jqXHR) {
							oAlert("补发失败");
						}
					});
				}
			});
		}
	});
	
	/** ******************************** 补发结束 ***************************** */
	
	/** ******************************** 下载开始 ***************************** */
	$(":button[name='"+ btn_download +"']").each(function(){
		$(this).click($.proxy(onDownload, this));
	});
	function onDownload(e) {
	  if(checkCAKey()){
		var uuid = $("#uuid").val();
		window.location.href = ctx + '/commercial/business/download?uuids=' + uuid;
	  }
//		$.ajax({
//			      type:'get',
//			      url :ctx + '/commercial/business/download?uuid',
//			      data:{
//			    	  uuids : [uuid, "123"]
//			      }
//			  });
	}
	/** ******************************** 下载结束 ***************************** */
	
	/** ******************************** 同意审核通过开始 ***************************** */
	$("#" + btn_sc).click(function() {
		if(checkCAKey()){
			JDS.call({
				service: examineService, 
				data: [$("#uuid").val(), 1,""], 
				success:function(result){
					oAlert("处理成功",function (){returnWindow();window.close();});
				},
				error : function(jqXHR) {
					oAlert("处理失败");
				}
			});
		}
	});
	/** ******************************** 同意审核通过结束 ***************************** */
	
	/** ******************************** 不同意审核通过开始 ***************************** */
	$("#" + btn_bsc).click(function() {
		if(checkCAKey()){
			var str= "<div style='margin:10px;'><div>不同意通过原因</div><div><textarea id='examineMsg' style='height:195px;width:465px;'></textarea></div></div>";
			var json = new Object(); 
	        json.content = str;
	        json.title = "不同意通过";
	        json.height= 350;
	        json.width= 500;
	        var buttons = new Object(); 
	        buttons.确认 = backExamine;
	        json.buttons = buttons;
	        showDialog(json);
		}
        
	});
	function backExamine() {
		var msg = $("#examineMsg").val();
		msg = msg.replace(/\s+/g,"");
		if(msg==""){
			oAlert("请填写不同意通过的原因！");
			return false;
		}
		JDS.call({
			service: examineService, 
			data: [$("#uuid").val(), -1,msg], 
			success:function(result){
				oAlert("处理成功",function (){returnWindow();window.close();});
			},
			error : function(jqXHR) {
				oAlert("处理失败");
			}
		});
	}
	/** ******************************** 同意审核通过结束 ***************************** */
	
	/** ******************************** 编辑按钮开始 ***************************** */
	$("#" + btn_edit).click(function() {
		if(checkCAKey()){
			location.href = ctx + "/exchangedata/client/toSendData?rel=27&sendUuid="+$("#uuid").val();
		}
	});
	/** ******************************** 编辑按钮结束 ***************************** */

	/** ******************************** 删除按钮开始 ***************************** */
	$("#" + btn_del).click(function() {
		if(checkCAKey()){
			JDS.call({
				service: delExamineService, 
				data: [$("#uuid").val()], 
				success:function(result){
					oAlert("删除成功",function (){returnWindow();window.close();});
				},
				error : function(jqXHR) {
					oAlert("删除失败");
				}
			});
		}
	});
	/** ******************************** 删除按钮结束 ***************************** */
	
	/** ******************************** 标记出证状态开始 ***************************** */
	$("#" + btn_flag_cz).click(function() {
		if(checkCAKey()){
			JDS.call({
				service: markCertificateService, 
				data: [[$("#uuid").val()]], 
				success:function(result){
					oAlert("操作成功",function (){returnWindow();window.close();});
				},
				error : function(jqXHR) {
					oAlert("操作失败");
				}
			});
		}
	});
	/** ******************************** 标记出证状态结束 ***************************** */
	
	//隐藏单据上的按钮
	$("#relationDate").hide();
	$("#openHisModule").hide();
});