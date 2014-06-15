var swfu;
var settings;
////确保contextPath有值
//if (!window.contextPath) {
//	window.contextPath = "/" + window.location.pathname.split("/")[1];
//	window.ctx = window.contextPath;
//}
//获取cookie
function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr) {
		return decodeURI(arr[2]);
	} else {
		return null;
	}
}
// 确保contextPath有值
function getContextPath() {
	if (window.ctx == null || window.contextPath == null) {
		window.contextPath = getCookie("ctx");
		window.contextPath = window.contextPath == "\"\"" ? "" : window.contextPath;
		window.ctx = window.contextPath;
	}
	return window.ctx;
}
getContextPath();
//SWFUpload上传
SWFUpload.onload = function () {
	
	 settings = {
		flash_url : contextPath+"/pt/mail/js/swfupload.swf",
		upload_url: contextPath+'/pt/mail/fileSubmit.jsp',
		post_params: {
			"attachment" : $("#attachment").val(),
			"fileMid" : $("#fileMid").val()
		},
		file_post_name:'fileToUpload',
		file_size_limit : 30720,//30M
		file_types : "*.*",
		file_types_description :"All files",
		file_upload_limit : 100,
		file_queue_limit : 10,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			//cancelButtonId : "btnCancel",
			//uploadButtonId : "btnUpload",
			myFileListTarget : "idFileList"
		},
		debug: false,
		auto_upload:true,

		// Button Settings
		button_image_url : contextPath+"/pt/mail/js/XPButtonUploadText_61x22.png",	// Relative to the SWF file
		button_placeholder_id : "spanButtonPlaceholder",
		button_width: 61,
		button_height: 22,
		button_window_mode:SWFUpload.WINDOW_MODE.TRANSPARENT,

		// The event handler functions are defined in handlers.js
		swfupload_loaded_handler : swfUploadLoaded,
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete,	// Queue plugin event
		
		// SWFObject settings
		minimum_flash_version : "9.0.28",
		swfupload_pre_load_handler : swfUploadPreLoad,
		swfupload_load_failed_handler : swfUploadLoadFailed
	};

	swfu = new SWFUpload(settings);
};
//删除附件
function Delete(fid) {       
	//var tab=$("#idFileList");
	//$("#"+fid).parent().remove();
	
	var s=$("#idFileList").children().find("#"+fid).index();
	//var s=$("#"+fid +" td:first").html();
	//$(input).parent().remove();
	var files= $("#attachment").val();
	var filess=files.split("#");
	var f='';
	for(var i=0;i<filess.length;i++){
		if(i!=s&&filess[i]!='undefined'){
			f+=filess[i]+"#";
		}
	}
	f=f.substring(0,f.length-1);
	$("#attachment").val(f);
	
	$("#idFileList").children().find("#"+fid).remove();

	//$(mydiv).parent().parent().remove();
	//$(mydiv).parent().remove();//swfu参见iframe页面中的
//swfu = new SWFUpload(settings);
//var stats = swfu.getStats();    
//stats.successful_uploads--;       
//swfu.setStats(stats);       
//var status = $("#idFileListSuccessUploadCount");      
//status.html(stats.successful_uploads);  
} 
//上传成功后处理
function uploadSuccess(file, serverData) {
	//alert(serverData);
	var isSuccess = (serverData.indexOf("successed")>=0?true:false);
	var isExist = (serverData.indexOf("exist")>=0?true:false);
	try {
		if(isExist){
			Delete(file.id);
		}else{
		if(isSuccess){
			//alert('11');
			var fnss=serverData.split("#|#");
			var fns=fnss[0].split("|");
			var att=$("#attachment").val();
			//alert(fns[0]);
			//alert(att);
			if(endWith(att,"#")||att==''){
			$("#attachment").val(att+fns[0]+"#");
			}else{
				$("#attachment").val(att+"#"+fns[0]+"#");
			}
			//alert(fns[1]);
			var fmid=$("#fileMid").val();
			if(endWith(fmid,"#")||fmid==''){
				$("#fileMid").val(fmid+fns[1]+"#");
				}else{
					$("#fileMid").val(fmid+"#"+fns[1]+"#");
				}
			
			//alert($("#fileMid").val());
			swfu.setPostParams({"fileMid":$("#fileMid").val(),"attachment":$("#attachment").val()});
			//alert($("#attachment").val());
			
			var tr = document.getElementById(file.id);
			tr.style.color="green";
			var bar = document.getElementById(file.id+"_bar");
			bar.innerHTML="";
			bar.className="";
			
			$("#fileupload").val("1");
			//var delObject = document.getElementById(file.id+"_del");
			//delObject.parentNode.innerHTML="<a href=\"javascript:Delete('"+file.id+"');\">删除</a>";
			//document.getElementById(this.customSettings.myFileListTarget+"Count").innerHTML=this.getStats().files_queued;
		}else{
			var tr = document.getElementById(file.id);
			tr.style.color="red";
			var bar = document.getElementById(file.id+"_bar");
			bar.className="";
			bar.innerHTML="上传失败:"+serverData;
			//var delObject = document.getElementById(file.id+"_del");
			//delObject.parentNode.innerHTML="<a href=\"javascript:Delete('"+file.id+"');\">删除</a>";
		}
		}
	} catch (ex) {
		this.debug(ex);
	}
	
	
	
	/*
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("Complete.");
		progress.toggleCancel(false);

	} catch (ex) {
		this.debug(ex);
	}
	*/
}   
//上传操作
function FileProgress(file,fileListID,swfUploadInstance) {
	if(!document.getElementById(file.id)){
		//document.getElementById(fileListID+"Count").innerHTML=swfUploadInstance.getStats().files_queued;
		var tb = document.getElementById(fileListID);
		var tr = tb.insertRow(-1);
		tr.setAttribute("id",file.id);
		
		//tr.className='uploadTR';
		var td = tr.insertCell(-1);//td.className='uploadTD';
		td.style.width = "auto";
		//td.style="";
		//td.style.position = "absolute";
		if(file.size){
			td.innerHTML=file.name+'('+getNiceFileSize(file.size)+')';
		}else{
			td.innerHTML=file.name+'(0B)';
		}
		var td = tr.insertCell(-1);//td.className='uploadTD';
		td.innerHTML="<span  id="+file.id+"_bar class=uploadBar></span>";
		td.style.width = "auto";
		td.align="left";
		var td = tr.insertCell(-1);//td.className='uploadTD';
		td.innerHTML="<span  onclick=\"javascript:Delete('"+file.id+"');\" id="+file.id+"_del class=uploadCancel>删除</span>";
		td.style.width = "auto";
		td.align="left";
		//alert('2');
		//td.setAttribute("align","left");
		//td.innerHTML=file.name;
		//td = tr.insertCell(-1);td.className='uploadTD';
		//if(file.size){
			//td.innerHTML=getNiceFileSize(file.size);
		//}else{
			//td.innerHTML="0B";
		//}
		//td = tr.insertCell(-1);td.className='uploadTD';
		//td.innerHTML="等待上传<span id="+file.id+"_bar class=uploadBar></span>";
		//td = tr.insertCell(-1);td.className='uploadTD';
		//td.innerHTML="<span id="+file.id+"_del class=uploadCancel>删除</span>";
		//var delObject = document.getElementById(file.id+"_del");
		//delObject.onclick = function () {
			//Delete('"+file.id+"');
			//swfUploadInstance.cancelUpload(file.id);
			//var tr = document.getElementById(file.id);
			//tr.style.color="red";
			//var bar = document.getElementById(file.id+"_bar");
			//var errInfo = "已取消";
			//bar.parentNode.innerHTML=errInfo;
			//$("#idFileList").children().find("#"+fid).remove();
			//var delObject = document.getElementById(file.id+"_del");
			//delObject.parentNode.innerHTML="&nbsp;";
			//document.getElementById(fileListID+"Count").innerHTML=swfUploadInstance.getStats().files_queued;
		//};
		//alert(tb.innerHTML);
	}
	
	 /*comment by stephen
	this.fileProgressID = file.id;

	this.opacity = 100;
	this.height = 0;

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode(file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);

		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
	}

	this.height = this.fileProgressWrapper.offsetHeight;
	*/
	

}




