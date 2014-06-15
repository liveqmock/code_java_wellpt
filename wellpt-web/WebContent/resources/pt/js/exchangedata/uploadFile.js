var delfile = new Array();//删除的文件
var files  = new Array();
$(function() {
//	if($("#attach").val().length == 0) {
//		$("#attach").val(new UUID().id);
//	}
		// 判断是否启用上传附件签名
		var enableSignUploadFile = false;
		try {
			enableSignUploadFile = fjcaWs.OpenFJCAUSBKey();
		} catch (e) {
			try {
				fjcaWs.CloseUSBKey();
			} catch (e) {}
		}
		if(!enableSignUploadFile) {
			$('#fileId').remove();
			$('.fileinput-button2>span.add_icon').click(function(e){
				oAlert("当前浏览器无法对上传的附件进行签名，请使用IE浏览器进行上传!");
				return false;
			});
			return;
		} else{
			fjcaWs.CloseUSBKey();
		}
	
		var url = $("#ctx").val()	+ '/repository/file/uploadfile';
		$('#fileId').fileupload({
	        url: url,
	        dataType: 'json',
	        formData: {attach:"", moduleName:"DY_TABLE_FORM", signUploadFile: true},
	        autoUpload: true,
	        maxFileSize: 5000000, // 50 MB
	        previewMaxWidth: 100,
	        previewMaxHeight: 100,
	        previewCrop: true
	    }).on('fileuploadadd', function (e, data) {
	    	pageLock("show");
	    }).on('fileuploaddone', function (e, data) {
	    	pageLock("hide");
	    	$.each(data.result.data, function(index){
	    		if(this.signUploadFile === true) {
	    			var fileUpload = this;
	    			var digestValue = this.digestValue;
	    			var b = fjcaWs.OpenFJCAUSBKey();
	    			fjcaWs.ReadCertFromKey();
	    			var cert = fjcaWs.GetCertData();
	    			fjcaWs.SignDataWithKey(digestValue);
	    			var signData = fjcaWs.GetSignData();
	    			fjcaWs.CloseUSBKey();
	    			fileUpload.digestValue = digestValue;
	    			fileUpload.certificate = cert;
	    			fileUpload.signatureValue = signData;
	    			files.push(fileUpload);
//	    			JDS.call({service:"jcrFileService.setFileUploadSign",data:[fileUpload],
//	    				success:function(result){},
//	    				error:function(jqXHR){
//	    					// 数字签名失败
//	    					var faultData = JSON.parse(jqXHR.responseText);
//	    					oAlert(faultData.msg);
//	    				}
//	    			});
	    		} else{
	    			files.push(this);
	    		}
	    	});
	    	$.each(data.files, function (index, file) {
	    		var filenames = new Array();
	    		filenames.push(file.name);
	    		var html = '<div class="files_div"><span class="file_icon"></span><p class="filename">'+file.name+'</p>'+
	    			' <div id="progress" class="progress progress-success progress-striped progress-div"><div class="bar bar-div" ></div></div>'+
	    			"<div class='delete_Div'><input type='button' id='delete' class='delete_input' filename='"+file.name+"' value='删除' /></div></div>";
	    			$(".attach-list").append(html);
			});
	    	$("#progress").remove();
	    }).on('fileuploadfail', function (e, data) {
	    	$(".bar.bar-div").hide();
	    });
		
		//删除
   	 $("#delete").die().live("click",(function(){
   		var $this = $(this);
   		var file =  $('#delete').attr("filename");
			delfile.push(file);
			$this.parent().parent().remove();
   	 })); 
	 $("#uploadBtn").click(function(){
		JDS.call({
			service : 'exchangeDataClientService.uploadFile',
			data : [files],
			success : function(result) {
				if(result.data=="success"){
					oAlert("发送成功");
				}else{
					oAlert("发送失败");
				}
			},
			error : function(xhr, textStatus, errorThrown) {
				oAlert("数据标准异常");
			}
		});
	 });
});