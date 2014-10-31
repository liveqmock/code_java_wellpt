$(function() {
	WellFileUpload4Image = function(ctlID// 控件唯一ID
		) {
			this.ctlID = ctlID;// 文件上传input元素的元素ID
		};

		WellFileUpload4Image.prototype = new WellFileUpload(
				"formWellFileUpload4Image", "image");
		
		/**
		 *初始化控件实例,在控件预先有文件信息，但文件信息存在于数据库中时使用该初始化方法，调用方需要传入文件信息对应的文件夹及文件用途，再由控件将文件信息加载出来。
		 */
		WellFileUpload4Image.prototype.initWithLoadFilesFromFileSystem = function(
				readOnly/*是否只读,例如只给一些用户只读的权限*/,  
				$containerElement/*存放该附件的容器*/, 
				signature/*是否签名*/ ,
				multiple/*多文件为true, 单文件为false*/,
				folderID,/*文件夹ID*/ 
				purpose,/*文件用途*/
				imgWidth,/*图片宽度*/
				imgHeight/*图片高度*/
				
		){ 
			if((folderID == undefined || folderID == null || folderID == "")){
				throw new Error("请设置正确的文件夹ID");
			}
			
			if((purpose == undefined || purpose == null || purpose == "")){
				throw new Error("请设置正确的文件用途");
			}
			var dbFiles = WellFileUpload.loadFilesFormDb(folderID, purpose);
			
			this.init(readOnly, $containerElement, signature, multiple, dbFiles,imgWidth,imgHeight);
		};
		
		/**
		 * 初始化控件实例,在控件预先没有文件信息，或者有文件信息但已知的情况下使用该初始化方法
		 * @param readOnly 是否只读
		 * @param $containerElement, 附件容器:jquery对象
		 * @param signature 是否签名
		 * @param multiple 多文件或者单文件
		 * @param dbFiles 文件信息数组，其成员为FileInfo类的对象,若没有文件信息，则dbFiles=[]
		 */
		WellFileUpload4Image.prototype.init = function(
				readOnly/*是否只读,例如只给一些用户只读的权限*/,  
				$containerElement/*存放该附件的容器*/, 
				signature/*是否签名*/ ,
				multiple,// 多文件为true, 单文件为false 
				dbFiles,
				imgWidth,
				imgHeight
				
				
				
		){
			if(($containerElement == undefined || $containerElement == null || $containerElement == "")){
				throw new Error("请设置正确的控件容器");
			}
			
			this.$containerElement = $containerElement;
			this.signature = ((signature == undefined || signature == null || signature == "")? false: true) ;
			this.readOnly =  ((readOnly == undefined || readOnly == null || readOnly == "")? false: true) ;
			 
			this.multiple = ((multiple == undefined || multiple == null || multiple == "")? false: true) ;
			
			this.imgHeight = ((imgHeight == undefined || imgHeight == null || imgHeight == "")? "200": imgHeight) ;
			this.imgWidth = ((imgWidth == undefined || imgWidth == null || imgWidth == "")? "200": imgWidth) ;
		
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
					'<span class="add_icon">请选择图片</span>'+
					'<input id='+this.ctlID+' type="file" name="files" multiple class="fileupload_css" accept="image/png,image/jpeg,image/gif">'+
					'</span></div>';
			}
			
			fileHtml += '<div id=img_container_'+this.ctlID+' ></div>';//文件图片容器
			//fileHtml += '<div id="'+fileListId+'" style="display:none"></div>';//文件列表容器
			fileHtml += '<div id="'+fileListId+'" ></div>';//文件列表容器
			
			 $('#img_container_'+this.ctlID).html('<img src="" alt="显示图片"></img>');
			this.$containerElement.html(fileHtml); 
			
			if(!this.multiple){//单文件,默认为多文件
				$("#" + this.ctlID).removeAttr("multiple");
			}
			 
			var _this = this;
		 
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
				
			 
			 
		};
		
		WellFileUpload4Image.prototype.defineUploadEvent = function(){
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
					var checkpass=true;
				    $.each(data.result.data, function(index){
						if(this.filename.indexOf('.jpg')==-1&&this.filename.indexOf('.jpeg')==-1&&this.filename.indexOf('.gif')==-1&&this.filename.indexOf('.png')==-1){
							oAlert("不支持上传该格式的文件");
							checkpass=false;
						}
					});
					
					if(!checkpass){
						return;
					}
					
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
		
		 /**
		  *  为控件添加单个附件 .
		 * @param filename
		 * @param fileID
		 * @param digestValue
		 * @param digestAlgorithm
		 * @param certificate
		 * @param isNew 如果为true,表示该文件还没与文件夹产生关系,false则反之
		 */
		
		WellFileUpload4Image.prototype.addFile = function( filename,fileID , contentType,  digestValue , digestAlgorithm,certificate, isNew/*新增的文件为true, 否则为false*/){
		 
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
			 
			 $('#img_container_'+this.ctlID).html('<a id="imgherf_'+this.ctlID+'" name="imgherf_'+this.ctlID+'" href="'+ctx + fileServiceURL.downFile + fileID+'"></a>');
			 var divimg = document.getElementById("imgherf_"+this.ctlID);
				var imgelement = document.createElement("img");
				//alert(divimg.id);
				imgelement.src =  ctx + fileServiceURL.downFile + fileID;
				imgelement.setAttribute("id", "img_"+fileID);
				imgelement.setAttribute("name", "img_"+fileID);
				imgelement.setAttribute("style", "width:"+this.imgWidth+"px;height:"+this.imgHeight+"px;");
				divimg.appendChild(imgelement);
			 
			
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
	
					 if($(this).parent().parent().parent().parent().parent().prev().attr('id').indexOf('img_container')==0){
						 $(this).parent().parent().parent().parent().parent().prev().empty();
					 }
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
	
});