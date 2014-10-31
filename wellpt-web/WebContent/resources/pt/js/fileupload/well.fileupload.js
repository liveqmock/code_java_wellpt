	
/**
 * 文件信息类
 */
var FileInfo = function(){
	this.fileID = null;
	this.fileName = null;
	this.contentType = null;
	this.digestAlgorithm = null;
	this.digestValue = null;
	this.certificate = null;
	this.signatureValue = null;
	this.isNew = true;//表示如果值为false表示该文件已隶属于某个文件夹，true表示该文件还没和任何文件夹产生关系
	
};
/**
 * 列表式附件(附件在上传之后以列表的形式展示出来)
 */
$(function(){
	/***
	 * 该上传控件使用的样式为下拉列表样式.
	 * 如果没有其他个性化需求。其他需要文件上传的地方统一使用该控件
	 */
	/**
	 * @author Administrator
	 *
	 */
	WellFileUpload = function( ctlID/*控件唯一ID*/ ) {
		this.ctlID = ctlID;//文件上传input元素的元素ID 
		this.$containerElement = null;
		this.signature = false;//是否签名 
		this.readOnly = false;
		this.multiple = false; 
		this.$fileContainer = null;//用户保存文件列表的html元素 
		this.files = null;//用于存在当前控件的文件 
		 
	};
	
	//该变量用于存储最终要存放到后台的文件ID, 以ctlID为key.value为类FileInfo对象的数组
	WellFileUpload.files = [];
	
	
	//暂时没用
	WellFileUpload.bodyFiles=[];
	
	
	//用于保存所有的控件实例
	WellFileUpload.fileUploadObj = [];
	 
	WellFileUpload.file2swf = false;//是否生成swf副本,这个要页面初始化时会被初始化
	
	
	/**
	 * 通过控件ID获取指定的控件实例,若该控件ID所指定的控件实例不存在则返回undefined
	 * @param ctlID 控件实例的控件ID
	 * @returns
	 */
	WellFileUpload.get = function(ctlID){ 
		return WellFileUpload.fileUploadObj[ctlID];
	};
	
	/**
	 * 根据folderID从数据库里面加载文件-
	 */
	WellFileUpload.loadFilesFormDb = function(folderID, purpose){
		var dbFiles = [];
		JDS.call({
       	 service:"mongoFileService.getNonioFilesFromFolder",
       	 data:[folderID, purpose],
       	 async: false,
			success:function(result){
				var files = result.data;
				if(files != null){
					for(var i in files){
						var file = files[i]; 
						
						var file2 = new FileInfo();
						file2.fileID = file.fileID;
						file2.fileName =  file.fileName;
						file2.contentType = file.contentType;
						file2.digestAlgorithm = file.digestAlgorithm ;
						file2.digestValue = file.digestValue ;
						file2.certificate = file.certificate;
						file2.isNew = false;
						dbFiles.push(file2);
					}
				} 
			},
			error:function(jqXHR){
				 
			}
		});
		return dbFiles;
		
	};
	
	
	/**
	 * 签名
	 * @param fileID
	 * @param digestValue
	 * @param digestAlgorithm
	 * @param certificate
	 */
	WellFileUpload.sign =  function(fileID,  digestValue,  digestAlgorithm,    certificate){
		var b = fjcaWs.OpenFJCAUSBKey();
		fjcaWs.ReadCertFromKey();
		var cert = fjcaWs.GetCertData();
		fjcaWs.SignDataWithKey(digestValue);
		var signData = fjcaWs.GetSignData();
		var signaturex = {};
		
		fjcaWs.CloseUSBKey();
		signaturex.digestValue = digestValue;
		signaturex.certificate = cert;
		signaturex.signatureValue = signData;
		signaturex.digestAlgorithm = digestAlgorithm;
		
		 var url = ctx + fileServiceURL.updateSignature;
		 //console.log("签名地址:" + url);
		 //console.log("签名信息 ::" + JSON.stringify(signaturex));
		 var updateSignatureOK = false;
			$.ajax({
				type : "post",
				url : url, 
				data:{signatureData: JSON.stringify(signaturex), fileID: fileID},
				timeout:1000,
				async: false,
				success : function(data) { updateSignatureOK = true;},
				error : function(data) {
					  //$('<span/>').text("文件签名更新失败");
					updateSignatureOK = false;
					 //console.log("签名错误 :" + JSON.stringify(data));
				}
			});
			return updateSignatureOK;
		
	};
	
	
	
	 
	 
	
	
	/**
	 *初始化控件实例,在控件预先有文件信息，但文件信息存在于数据库中时使用该初始化方法，调用方需要传入文件信息对应的文件夹及文件用途，再由控件将文件信息加载出来。
	 */
	WellFileUpload.prototype.initWithLoadFilesFromFileSystem = function(
			readOnly/*是否只读,例如只给一些用户只读的权限*/,  
			$containerElement/*存放该附件的容器*/, 
			signature/*是否签名*/ ,
			multiple/*多文件为true, 单文件为false*/,
			folderID,/*文件夹ID*/ 
			purpose/*文件用途*/
	){ 
		if((folderID == undefined || folderID == null || folderID == "")){
			throw new Error("请设置正确的文件夹ID");
		}
		
		if((purpose == undefined || purpose == null || purpose == "")){
			throw new Error("请设置正确的文件用途");
		}
		
		var dbFiles = WellFileUpload.loadFilesFormDb(folderID, purpose);
		
		//console.log("[" + folderID + "][" + purpose + "]上传控件从文件系统 中获取到的文件数为:" + dbFiles.length );
		
		 
		
		this.init(readOnly, $containerElement, signature, multiple, dbFiles);
		  
	};
	
	
	
	 
	/**
	 * 初始化控件实例,在控件预先没有文件信息，或者有文件信息但已知的情况下使用该初始化方法
	 * @param readOnly 是否只读
	 * @param $containerElement, 附件容器:jquery对象
	 * @param signature 是否签名
	 * @param multiple 多文件或者单文件
	 * @param dbFiles 文件信息数组，其成员为FileInfo类的对象,若没有文件信息，则dbFiles=[]
	 */
	WellFileUpload.prototype.init = function(
			readOnly/*是否只读,例如只给一些用户只读的权限*/,  
			$containerElement/*存放该附件的容器*/, 
			signature/*是否签名*/ ,
			multiple,// 多文件为true, 单文件为false 
			dbFiles
	){
		
		if(($containerElement == undefined || $containerElement == null || $containerElement == "")){
			throw new Error("请设置正确的控件容器");
		}
		
		this.$containerElement = $containerElement;
		this.signature = ((signature == undefined || signature == null || signature == "")? false: true) ;
		this.readOnly =  ((readOnly == undefined || readOnly == null || readOnly == "")? false: true) ;
		 
		this.multiple = ((multiple == undefined || multiple == null || multiple == "")? false: true) ;
		WellFileUpload.fileUploadObj[this.ctlID] = this;
		
		this.$fileContainer = null;//用户保存文件列表的html元素
		
		this.files = null;
		
		
		 //获取保存文件信息的容器
		if(WellFileUpload.files[this.ctlID] == undefined){//容器不存在，新建
			WellFileUpload.files[this.ctlID] = [];
		}
		this.files = WellFileUpload.files[this.ctlID];
		
		var fileHtml = "";
		var fileListId = "filelist"+ this.ctlID;
		
		
		
		if(!this.readOnly){
			//如果有上传的权限,则将添加附件的功能添加进去
			fileHtml +=  '<div class="upload_div" id="upload_div">'+
				'<span class="btn btn-success fileinput-button2">'+
				'<span class=\"file_icon\" style=\"display: block;float: left;\"></span>'+
				'<span class="add_icon">添加附件</span>'+
				'<input id='+this.ctlID+' type="file" name="files" multiple class="fileupload_css">'+
				'</span></div>';
		}
		
		fileHtml += '<div id="'+fileListId+'"></div>';//文件列表容器
		
		
		this.$containerElement.html(fileHtml); 
		
		if(!this.multiple){//单文件,默认为多文件
			$("#" + this.ctlID).removeAttr("multiple");
		}
		 
		var _this = this;
		//window.setTimeout(function(){//延迟否则下面的第一条语句无效
			_this.$fileContainer = $("#" + fileListId);
		 
			
			var attach_list = "attach-list"+ _this.ctlID;
			_this.$fileContainer.append('<ul class="'+attach_list+'" style="list-style-type:none;padding: 0;" />'); 
			
			//将从数据库中获取到的文件列表组装成html
			if(dbFiles != null && dbFiles.length > 0 ){
				var size = dbFiles.length;
				for(var index=0; index < size; index++) {
					var dbFile = dbFiles[index];
					var fileID = dbFile.fileID;
					var fileName = dbFile.fileName;
					var digestValue = dbFile.digestValue;
					var digestAlgorithm = dbFile.digestAlgorithm;
					var certificate = dbFile.certificate; 
					var contentType = dbFile.contentType;
					var isNew = dbFile.isNew  ;
					isNew = isNew == undefined ? true : isNew;
					_this.addFile(fileName, fileID, contentType, digestValue, digestAlgorithm, certificate,  isNew/*非新增的文件*/); 
				}
			 
			}
			 
			if(!_this.readOnly){
				_this.defineUploadEvent();//定义上传事件 
			}
			
		//}, 10);
		 
		
		 
	};
	
	 
	
	WellFileUpload.prototype.defineUploadEvent = function(){
		 var _this = this;
		 
		var url = ctx	+ fileServiceURL.saveFiles;//上传文件的地址
		 
		 var iframe = false; 
			if($.browser.msie  && $.browser.version < 10){  
				iframe = true; 
			}
	 
		$('#' + _this.ctlID ).fileupload({
			url: url,
			//forceIframeTransport: forceIframeTransport,
			 iframe: iframe,
			dataType: 'json',
			//datatype: dataType,
			autoUpload: true,
			//sequentialUploads : true,
			formData: {signUploadFile: _this.signature},
			maxFileSize: 5000000, // 5 MB
			previewMaxWidth: 100,
			previewMaxHeight: 100,
			previewCrop: true
		}).on('fileuploadadd', function (e, data) {
			 
			pageLock("show");
		}).on("fileuploadsubmit", function(e, data){
			if(_this.signature && !_this.validSignatureUSB()){
				pageLock("hide");
				return false;
			}
			return true;
		}).on('fileuploaddone', function (e, data) {
			pageLock("hide"); 
			if((typeof data.result) == "undefined"){
				oAlert("不支持上传该格式的文件");
			}else{
				$.each(data.result.data, function(index){
					//console.log("new file " + this.fileID);
					_this.addFile(this.filename, this.fileID,  this.contentType, this.digestValue,  this.digestAlgorithm,  this.certificate, true);
				});
			}
			 
		}).on('fileuploadfail', function (e, data) {
			pageLock("hide");
			if((typeof data.result) == "undefined"){
				oAlert("可能您上传的文件格式不被支持!!!"); 
			}else{
				$.each(data.result.files, function (index, file) {
					var error = $('<span/>').text(file.error);
					$(data.context.children()[index])
						.append('<br>')
						.append(error);
				});
			}
			
		}); 
	};
	
	WellFileUpload.createReplicaOfSWF = function(fileID){
		var okFlag = true;//标识是否成功生成副本
		if(this.file2swf ){ 
			JDS.call({//该接口不支持重载，如果一个方法有多个重载一定要注意，要被调用的方法必须放在最前面 
		       	 service:"mongoFileService.createReplicaOfSWF",
		       	 data:fileID,
		       	 async: false,
				success:function(result){ },
				error:function(jqXHR){
					okFlag = false;					//throw new Error("文件生成副本的过程中出现错误,请重新提交，若多次重试请联系技术人员");
				}
			});
		}
		return okFlag;
	};
	
	
	
	 /**
	  *  为控件添加单个附件 .
	 * @param filename
	 * @param fileID
	 * @param digestValue
	 * @param digestAlgorithm
	 * @param certificate
	 * @param isNew 如果为true,表示该文件还没与文件夹产生关系,false则反之
	 */
	WellFileUpload.prototype.addFile = function( filename,fileID , contentType,  digestValue , digestAlgorithm,certificate, isNew/*新增的文件为true, 否则为false*/){
		if(typeof isNew == "undefined"){
			isNew = false;
		}
		
		var _this = this;
		 
		 if(this.signature && isNew){ //更新文件签名 
				var updateSignatureOK = WellFileUpload.sign(fileID, digestValue, digestAlgorithm, certificate);
				
				if(!updateSignatureOK){
					oAlert("文件签名上传失败,请重新上传");
					return;
				}
		 }
		 
		 
	 
		 if(isNew && WellFileUpload.file2swf){
			  
			var ok =  WellFileUpload.createReplicaOfSWF(fileID);//
			if(!ok){
				oAlert("副本生成失败,请重试");
				return;
			}
		 }
		 
		 
		
		var $ul =  this.$fileContainer.find("ul");
		 
		 if(!this.multiple ){//单文件上传
			 
			 this.files.length = 0;
			// this.files.push({fileID:fileID, ctlID: this.ctlID,   fileName: filename, isNew: isNew});
			 
			 $ul.find("._delete_input").last().trigger("click");//删除最后一个文件
		 } 
		 
		
		 this.$fileContainer.find("ul").append('<li filename="'+filename+'"></li>');
		// $ul.append('<li filename="'+filename+'"');
		 var _html = '<div class="files_div" style="background:none;"><span class="file_icon" style="display:block;float:left;"></span><p class="filename"  style="display:block;float:left;">'+filename+'</p>';
		 if(this.readOnly) {//如果只读，则不提供删除 的功能
			 _html += "</div>"; 
		 }else {
			 _html += "<div class='delete_Div' style='float:left;'><input type='button' id='delete' class='_delete_input' filename='"+filename+"' value='删除' /></div>" +
			 "</div>";
		 }
		  
		 $ul.find("li").last().html(_html);
		 
		 
		
		 var isExist = false;
		for(var index = 0; index < this.files.length; index ++ ){
   			if(_this.files[index].fileID == fileID){
   				isExist = true; 
   			}
   		}
		
		 
		if(!isExist){
			var file = new FileInfo();
			file.fileID = fileID;
			file.fileName = filename;
			file.digestAlgorithm = digestAlgorithm == undefined? "": digestAlgorithm;
			file.digestValue = digestValue == undefined? "": digestValue;;
			file.certificate = certificate== undefined? "": certificate;;
			file.signatureValue = "";
			file.contentType =  contentType== undefined? "": contentType;;
			file.isNew = isNew;
			this.files.push(file); 
		}
		
		
		
	 
		
		 
		 //下载文件
		 (function( fileidtmp){
			 $ul.find(".filename").last().last().click(function(){ 
					location.href = ctx + fileServiceURL.downFile + fileidtmp;
				});
		 })(  fileID);
		 
		
		 //删除文件 
		// (function(){})()
		
			 $ul.find("._delete_input").last().click(function(){
				 
				 $(this).parent().parent().remove();
				 
				 
		   		for(var index = 0; index < _this.files.length; index ++ ){
		   		 
		   			if(_this.files[index].fileID == fileID){
		   			 
		   				_this.files.splice(index, 1);
		   			}
		   		}
		   	 
		   		
			   	 if(isNew ){//用户新增的文件  
						$.ajax({
							type : "post",
							url : ctx + fileServiceURL.deleteFile + fileID, 
							success : function(data) { },
							error : function(data) { }
						});
		   	    } 
			   	  
		   	     
			});
			 
		 
	};
	
	
	
	/**
	 * 为控件添加一组文件
	 * @param files 文件信息数组，数组中的成员的成员为类FileInfo对象
	 * @param isNew 如果为true，同时初始化时的signature参数也为true,那么将会为该附件生成签名信息。
	 * @param isAppend 如果为true, 表示不清空原来夹下的文件，在原来的夹下面继续append参数files里面的文件到文件夹下，如果为false则表示将原来夹下面的文件清空，再把参数files里面的文件添加到文件夹下
	 */
	WellFileUpload.prototype.addFiles = function(files, isAppend){
		//console.log(JSON.stringify(files));
		var _this=this;
		//window.setTimeout(function(){//跟初始化时保持秒数一致.
			if(files == undefined || !(files instanceof Array) || files.length == 0){//files中没有元素，无需再进一步处理
				return;
			}
			
			
			if(isAppend == false){//清空文件夹即该控件下面的文件
				_this.files.length = 0;
			}
			
			for(var i in files){
				var file = files[i];
				_this.addFile(file.fileName, 
							file.fileID, 
							file.contentType,
							file.digestValue , 
							file.digestAlgorithm, 
							file.certificate,  
							file.isNew);
			}
	//	}, 10);
		
	};
	
	
	
	
	/**
	*验证签名USB
	*/
	WellFileUpload.prototype.validSignatureUSB = function(){
		//var enableSignUploadFile = false;
		/*try {
			enableSignUploadFile = fjcaWs.OpenFJCAUSBKey();
		} catch (e) {
			try {
				fjcaWs.CloseUSBKey();
			} catch (e) {}
		}
		
		if(!enableSignUploadFile) {
			//this.$containerElement.html(""); //附件容器中的内容清空 
			if(Browser.isIE()){
				//oAlert("无法对表单数据进行签名，请插入USBKey数字证书!");
				//this.$containerElement.html("无法对表单数据进行签名，请插入USBKey数字证书,再重新进入该页面!"); 
			}else{
				oAlert("当前浏览器无法对表单数据进行签名，请使用IE浏览器编辑表单!");
				//this.$containerElement.html("当前浏览器无法对表单数据进行签名，请使用IE浏览器编辑表单!");
			}
			
		} else{
			fjcaWs.CloseUSBKey();
		}	*/
		
		
		return checkCAKey();
	};
	
	 
	
	 /**
	  * 获取指定的控件ID的文件列表信息数组,数组成员为FileInfo类对象
	  * 若指定的ctlID不存在则返回undefined
	 * @param ctlID
	 * @returns 返回的文件信息列表，是一个由FileInfo类对象组成的数组
	 */
	WellFileUpload.getFiles =function( ctlID){
		var files = WellFileUpload.files[ctlID]; 
		return files;
	};
	
	
	
	
	
	
	
	/**
	 * 产生文件夹ID
	 */
	WellFileUpload.createFolderID = function(){
		var folderID = null;
		JDS.call({
	       	 service:"mongoFileService.createFolderID",
	       	 data:[],
	       	 async: false,
			success:function(result){
				folderID = result.data;
			},
			error:function(jqXHR){
				folderID = new UUID().id.toLowerCase();
			}
		});
		return folderID;
	};
	
	
	 /**
	 * 该方法仅提供给动态表单使用
	 * 通过表名，字段名，行索引组合成每个控件的id。
	 * @param tblName
	 * @param fieldName
	 * @param rowIndex 行索引, 主表就直接使用0即可,从表则可以每行的行ID,该参数也可以是直接用dataUUID
	 * @returns 返回控件ID
	 */
	WellFileUpload.getCtlID4Dytable = function(tblName, fieldName, rowIndex){
		
		var controlID = tblName + "___" + fieldName + "____" + rowIndex;
		 
		return controlID;
	};

});
