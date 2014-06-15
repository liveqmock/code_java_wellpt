var ExData = {};

$(function() {
	

	ExData.ToSendData = function() {
		var json = new Object(); 
		$.ajax({
			type:"post",
			async:false,
			data:{"type":"user_trace"},
			url:ctx+"/exchangedata/client/toTypeList",
			success:function(result){
				 var $toSendPage = $(result);
				 //业务类型只有一个，直接跳转到发件页
				 if($(".toSendPage", $toSendPage).length==1) {
					 	var msg = $(".toSendPage", $toSendPage);
					    var rel = msg.attr("rel");
						var typeUuid = msg.attr("typeUuid");
						if(rel == 20){
							window.open(ctx +"/exchangedata/client/toSendData.aciton?typeUuid="+typeUuid+"&rel=20");
						}
					 
				 } else {
					 json.content = result;
					 json.title = "选择业务类型";
					 json.height= 350;
					 json.width= 650;
					 showDialog(json);
				 }
			}
		});
	 };
	 //批量上传
	 ExData.uploadsZip = function() {
		var json = new Object(); 
		$.ajax({
			type:"post",
			async:false,
			url:ctx+"/exchangedata/client/uploadZip",
			success:function(result){
				 json.content = result;
				 json.title = "批量上传";
				 json.height= 350;
				 json.width= 650;
				 var buttons = new Object();
				 
				// buttons.批量样式说明 = ExData.uploadsMsg;
		         buttons.提交 = plsc;
		         json.buttons = buttons;
				 showDialog(json);
			}
		});
	 };
	//批量上传说明
	 ExData.uploadsMsg = function() {
		 var result = '';
		 result += '<div class="msg">';
		 result += '<div class="leftMsg" style="float: left;width: 45%;margin-left: 50px;margin-top: 10px;"><a href="' + ctx + '/resfacade/help/exchangedata?filename=大交换的文件交换方式接口实现.docx">大交换的文件交换方式接口实现(下载)</a></div>';
		 result += '<div class="leftMsg" style="float: left;width: 45%;margin-left: 50px;margin-top: 10px;">';
		 result += '<span style="font-size: 15px; display: block; margin-bottom: 5px;">EXCEL上传包标准</span>';
		 result += '<span>file1.zip(文件)</span><br/>';
		 result += '&nbsp;&nbsp;typeId_batchId.xls(类型Id_批次Id, excel数据文件)<br/>';
		 result += '&nbsp;&nbsp;dataId_recVer(附件目录，统一查询号_版本号)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;file1(附件1)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;file2(附件2)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;…<br/>';
		 result += '&nbsp;&nbsp;dataId_recVer(附件目录，统一查询号_版本号)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;file1(附件1)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;file2(附件2)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;…<br/>';
		 result += '&nbsp;&nbsp;…<br/>';
		 result += '<span style="font-size: 15px; display: block; margin-bottom: 5px;margin-top: 10px;">说明</span>';
		 result += '&nbsp;&nbsp;一个zip包包含一个excel文件<br/>';
		 result += '&nbsp;&nbsp;一个excel文件表示一批同一类型的数据<br/>';
		 result += '&nbsp;&nbsp;一个zip包里只能有一种数据类型的数据集合<br/>';
		 result += '&nbsp;&nbsp;zip包下可以有多个目录<br/>';
		 result += '&nbsp;&nbsp;一个目录代表excel中的一条数据的附件<br/>';
		 result += '&nbsp;&nbsp; 目录下的附件为对应数据的附件<br/>';
		 result += '&nbsp;&nbsp; <a style="color:blue;" href="' + ctx + '/resfacade/help/exchangedata?filename=1 商事行政许可信息.rar">1 商事行政许可信息(下载)</a><br/>';
		 // result += '&nbsp;&nbsp; <a href="' + ctx + '/resfacade/help/exchangedata?filename=2 商事行政处罚信息.rar">2 商事行政处罚信息(下载)</a><br/>';
		 // result += '&nbsp;&nbsp; <a href="' + ctx + '/resfacade/help/exchangedata?filename=3 商事主体荣誉信息.rar">3 商事主体荣誉信息(下载)</a><br/>';
		 // result += '&nbsp;&nbsp; <a href="' + ctx + '/resfacade/help/exchangedata?filename=4 商事主体资质信息.rar">4 商事主体资质信息(下载)</a><br/>';
		 // result += '&nbsp;&nbsp; <a href="' + ctx + '/resfacade/help/exchangedata?filename=5 商事主体年报备案.rar">5 商事主体年报备案(下载)</a><br/>';
		 result += '</div>';
		 result += '<div class="rightMsg" style="float: left;width: 45%">';
		 result += '<span style="font-size: 15px; display: block; margin-bottom: 5px;margin-top: 10px;">XML上传包标准</span>';
		 result += '<span>file1.zip(文件)</span><br/>';
		 result += '&nbsp;&nbsp;typeId_batchId.data(数据集合zip包的加密文件，类型Id_批准Id)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;dataId_recVer(目录，统一查询号_版本号)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dataId.xml(xml数据文件)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;file1(附件1)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;file2(附件2)<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;…<br/>';
		 result += '&nbsp;&nbsp;&nbsp;&nbsp;…<br/>';
		 result += '&nbsp;&nbsp;CA_SIGN.txt(签名文件，原数据集合加密前签名数据的base64编码) <br/>';
		 result += '<span style="font-size: 15px; display: block; margin-bottom: 5px;margin-top: 10px;">说明</span>';
		 result += '&nbsp;&nbsp;一个zip包表示一批同一类型的数据<br/>';
		 result += '&nbsp;&nbsp;一个zip包里只能有一种已加密的数据集合<br/>';
		 result += '&nbsp;&nbsp;一个数据集合加密前用私钥签名的base64编码签名数据文件<br/>';
		 result += '&nbsp;&nbsp;zip包下可以有多个目录<br/>';
		 result += '&nbsp;&nbsp;一个目录代表一条数据<br/>';
		 result += '&nbsp;&nbsp; <a style="color:blue;" href="' + ctx + '/resfacade/help/exchangedata?filename=1 商事主体登记信息(xml).zip">1 商事主体登记信息(下载)</a><br/>';
		 result += '&nbsp;&nbsp; <a style="color:blue;" href="' + ctx + '/resfacade/help/exchangedata?filename=2 商事行政许可信息(xml).zip">2 商事行政许可信息(下载)</a><br/>';
		 result += '</div>';
		 result += '</div>';
		 var json = new Object(); 
		 json.content = result;
		 json.title = "批量样式说明 ";
		 json.height= 520;
		 json.width= 900;
		 showDialog(json);
	};
	function plsc(){
		JDS.call({
			service : 'exchangeDataClientService.uploadFile',
			data : [WellFileUpload.files["fileupload_ctlid_single_demo"]],
			success : function(result) {
				oAlert("发送成功",refreshPage);
			},
			error : function(jqXHR, statusText, error) {
				var fault = JSON.parse(jqXHR.responseText);
				oAlert(fault.msg);
			}
		});
	}
	 //退回
	 var tempObj="";
	 ExData.refuseData = function(obj,type) {
		if(!checkCAKey()){
			return false;
		}
		tempObj = obj;
		var object = $(tempObj).parents(".viewContent").find("input[class=checkeds]:checked");
		if(object.length==0) {
			oAlert("请至少选择一条数据");
			return false;
		}
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
	 };

	 function refuse(){
		 $("#dialogModule").dialog("close");  //关闭当前窗口
		 $(tempObj).parents(".viewContent").find("input[class=checkeds]:checked").each(function(){
			var uuid = $(this).val();
			var msg = $("#msg_").val();
			JDS.call({
				service : "exchangeDataClientService.refuseData",
				data : [uuid,msg,"receive"],
				success : function(result) {
					oAlert("退回成功",function (){refreshWindow($("button[value='B003006056']"));});
				},
				error : function(jqXHR) {
					oAlert("退回失败");
				}
			});
		 });
	}
	 
	 //签收
	 ExData.signData = function(obj) {
		 if(!checkCAKey()){
			 return false;
		 }
		 var object = $(obj).parents(".viewContent").find("input[class=checkeds]:checked");
		 if(object.length==0) {
			 oAlert("请至少选择一条数据");
			 return false;
		 }
		 $(obj).parents(".viewContent").find("input[class=checkeds]:checked").each(function(){
			 var uuid = $(this).val();
			 JDS.call({
				 service : "exchangeDataClientService.signData",
				 data : [uuid],
				 success : function(result) {
					 oAlert("签收成功",function (){refreshWindow($("button[value='B003006037']"));});
				 },
				 error : function(jqXHR) {
					 oAlert("签收失败");
				 }
			 });
		 });
	 };
	 
	// 下载
	 ExData.download = function(exchangeUuid) {
		if(!checkCAKey()){
			return false;
		}
		var element = $(this);
		var exchangeUuids = [];
		if (isTopBtn(element)) {
			$("input[class=checkeds]:checked").each(function() {
				exchangeUuids.push($(this).val());
			});
			if (exchangeUuids.length == 0) {
				oAlert("请选择记录!");
				return;
			}
		} else {
			exchangeUuids.push(exchangeUuid);
		}
		
		var uuidParam = exchangeUuids[0];
		for(var i = 1; i < exchangeUuids.length; i++){
			uuidParam += "&uuids=" + exchangeUuids[i];
		}
		window.location.href = ctx + '/commercial/business/download?uuids=' + uuidParam;
	 };
	 
});