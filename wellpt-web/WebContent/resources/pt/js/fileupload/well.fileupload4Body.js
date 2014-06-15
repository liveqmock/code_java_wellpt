
$(function(){ 
	WellFileUpload4Icon = function(
			ctlID/*控件唯一ID*/ 
			)
	{
		this.ctlID = ctlID;//文件上传input元素的元素ID  
	};
	
	WellFileUpload4Icon.prototype = new WellFileUpload("formWellFileUpload4Icon", "icon");
	
	
	//WellFileUpload4Icon.fileIDs = new HashMap();//该变量用于存储最终要存放到后台的文件ID, 以ctlID为key.以fileid列表为value,当用户点击submit时，通过该map即获取每个组件的文件列表
	
	
	/**
	 *组装html, 将从数据库中获取到的文件列表组装成html。 
	 */
	
	WellFileUpload4Icon.prototype.init = function(
			uploadable/*是否则有上传的权限*/, 
			downable/*是否具有下载的权限*/, 
			$containerElement/*存放该附件的容器*/, 
			signature/*是否签名*/,  
			multiple,/*多文件为true, 单文件为false*/
			dbFiles/*是不是需要从数据库获取属于该ctlID及purpose的文件,true为需要，false为不需要*/
	){ 
		this.uploadable = uploadable;
		this.downable = downable; 
		this.$containerElement = $containerElement;
		this.signature = signature;//是否签名  
		this.multiple = multiple;
		 
		
		 
		 //获取保存文件信息的容器
		if(WellFileUpload.files[this.ctlID] == undefined){//容器不存在，新建
			WellFileUpload.files[this.ctlID] = [];
		}
		this.files = WellFileUpload.files[this.ctlID];
		 
		
		
		//组装html,这里将上传文件页面元素分成两部分，上半部分称之为标题部分(主要是标题，和其他的按钮(上传、清空、删除等));
		//下半部分称之为文件部分，主要用来存放文件图标列表。
		var containerId = "titleContainer_Id_" + this.ctlID;
		var titleContainer = "<div class='title' style='padding-top:10px;padding-left:10px;height:29px;margin-top:0px;' id='" + containerId + "'></div>";
		
		var containerId2 = "files_" + this.ctlID;
		var fileContainer = '<div class="post-info">'
			+   '<div id="aaaa" class="post-bd aaaa"></div>'+
			'<div id="' + containerId2 +'" class="post-bd"></div></div>';
		
		this.$containerElement.html(titleContainer);
		this.$containerElement.append(fileContainer);
		this.$fileContainer = $("#" + containerId2); 
		this.$fileContainer4Body =  this.$fileContainer.prev();
		
		
		this.$titleContainer = $("#" + containerId); 
		
		 
			if(this.uploadable){//有上传权限
				var text =  '正文 / 附件';
				if(dyFormInputMode.accessory1 ==  this.inputMode){
					text = '附件';
				}
				var fileHtml = '<div class="post-info">'+
				'<div class="post-hd">'+
				text +
			    '<div class="extralDiv">'+
			    '<span class="btn btn-success fileinput-button">'+
			    '<i></i>'+
			    '<span>上传附件</span>'+
				"<input id='"+this.ctlID+"' type='file' name='files' multiple>"+
				'</span></div></div></div>'; 
				this.$titleContainer.html(fileHtml); 
				if (dyFormInputMode.accessory2 ==  this.inputMode){
					this.$titleContainer.find(".extralDiv")
					.prepend( '<div id="' + this.ctlID + '_body" isNew="true" class="extral z"><i></i> 新建正文</div>');//isNew这个属性主要是用来判断是新建正文还是,编辑正文 
				}
			}else{//没有上传权限
				var fileHtml =  '<div class="post-info">'+
				'<div class="post-hd">'+
			    '附件'+
			    '</div></div>';
				this.$titleContainer.html(fileHtml); 
			}
	
		
		
		if(!this.multiple){//单文件,默认为多文件
			$("#" + this.ctlID).removeAttr("multiple");
		}
		
		this.$fileContainer.append('<div class="filelist"/>');
		this.$fileContainer.find(".filelist").append('<ul class="attach-list"/>');
		
		this.$fileContainer4Body.append('<div class="filelist"/>');
		this.$fileContainer4Body.find(".filelist").append('<ul class="attach-list2" >');
		
		
		
		this.defineDeleteEvent();
		
		var dbFiles = [];
		if(this.dbLoadable){//要从数据库加载已有数据
			dbFiles = WellFileUpload.loadFilesFormDb(this.ctlID, this.purpose);
		}
		
		//将从数据库中获取到的文件列表组装成html  
		if(dbFiles != null && dbFiles.length > 0 ){
			 var fileList = dbFiles;
			for(var index=0;index<fileList.length;index++) {
				var filename = fileList[index].fileName;
				var fileID = fileList[index].fileID;
				this.addFile(filename, fileID,null , null, null, false/*非新增的文件*/, false); 
			}
		}
		
		
		
		 
		if(this.uploadable){
			 this.defineUploadEvent(); 
			 this.defineUploadBodyEvent();
		}
		 
	};
	
	WellFileUpload4Icon.prototype.addFile = function(filename, fileID,  digestValue,
			 digestAlgorithm,    certificate ,isNew/*新增的文件为true, 否则为false*/){
		
		 if(this.signature && isNew){ //更新文件签名
			 var updateSignatureOK = WellFileUpload.sign(fileID, digestValue, digestAlgorithm, certificate); 
				if(!updateSignatureOK){
					oAlert("文件签名上传失败,请重新上传");
					return;
				}
		 }
		 
		 
		 
		 
		
		var $ul =  this.$fileContainer.find("ul");
		$ul.find("ul").append('<li filename="'+filename+'" fileID="' + fileID +'" isNew="' + isNew + '" style="margin-left: 0px;"></li>');
		 
		 
		 var $li = $ul.find("li").last();
		 
		 var filename2 = filename.toLowerCase();
		 
		 if(filename2.indexOf("txt") > -1) {
			 $li.html('<img src="'+ctx+'/resources/form/file_txt.png" alt="" /><span>'+filename+'</span>' );
		 }else if(filename2.indexOf("doc") > -1 || filename2.indexOf("docx") > -1 ) {
			 $li.html('<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span>'+filename+'</span>' );
		 }else if(filename2.indexOf("rar") > -1 || filename2.indexOf("zip") > -1) {
			 $li .html('<img src="'+ctx+'/resources/form/rar.png" alt="" /><span>'+filename+'</span>' );
		 }else if(filename2.indexOf("png") > -1 || filename2.indexOf("jpg") > -1) {
			 $li .html('<img src="'+ctx+'/resources/form/jpg.png" alt="" /><span>'+filename+'</span>' );
		 }else{
			 $li.html('<img src="'+ctx+'/resources/form/file_doc.png" alt="" /><span>'+filename+'</span>' );
		 }
		  
		 
		 this.files.push({fileID:fileID, ctlID: this.ctlID, purpose: this.purpose, fileName: filename});
		 
		 
		
		 //下载文件 
		 var downURL = ctx + fileServiceURL.downFile ;
		 var viewURL = ctx + fileServiceURL.viewFile;		 
		 
		 
		 //点中文件
		  $li.bind("click", function(){
			  if($li.attr("class") != null && $li.attr("class") != "") {
					 $li.removeClass("bar");
					}else {
						$li.addClass("bar");
					}
		  });
		
		 
		 if(isNew || this.downable){//有下载权限 
			//双击下载 
			 $li.dblclick(function(){
					location.href = downURL + fileID;
			});
		 }else{
			 if(!this.downable){//没有下载权限,可以查看
		 		$li.bind("dblclick",function(){
					if((filename.toLowerCase().indexOf(".doc") > -1 
							|| filename.toLowerCase().indexOf(".docx") > -1
							|| filename.toLowerCase().indexOf(".ppt") > -1 
							|| filename.toLowerCase().indexOf(".pptx") > -1
							|| filename.toLowerCase().indexOf(".xls") > -1 
							|| filename.toLowerCase().indexOf(".xlsx") > -1 
							|| filename.toLowerCase().indexOf(".txt") > -1) && WellFileUpload.file2swf){
						var url =  viewURL + fileID; 
						window.open(url);
					}else {
						location.href = downURL + fileID;
					}
		 		});
			 } 
		 }
		 
		 
		 
	 
		 
		 
	 
		 
	};
	
 
	/**
	 * 定义删除和清空按钮,该方法需在控件初始化完成后调用
	 */
	WellFileUpload4Icon.prototype.defineDeleteEvent = function(){
		//添加删除和清空的按钮
		var deleteHtml = '<div id="' + this.ctlID + 'file_clear" class="extral c">'
				+'<i></i> 清空'
				+'</div>'
				+'<div id="' + this.ctlID + 'file_delete" class="extral d">'
				+'<i></i> 删除'
				+'</div>';
		this.$titleContainer.find(".extralDiv").append(deleteHtml);
		 
		var _this = this;
		
		//最好在看完该控件的其他代码后再来看下面定义的这两个事件
		//定义删除事件 
		$("#" + this.ctlID +"file_delete").bind("click",function() {  
			if(_this.$fileContainer.find(".attach-list").find(".bar").length==0 ){
				oAlert("未选中文件！");
			}else{ 
				_this.$fileContainer.find("ul").find("li").each(function(){
					var $this = $(this);
					var clazz = $this.attr("class");
					var filename = $this.attr("filename");
					var fileID = $this.attr("fileID");
					var isNew = $this.attr("isNew");
					if(clazz != undefined) {
						if(clazz == "bar") {
							if(confirm("确定要删除选中的文件[" + filename + "]吗?")){  
							   		for(var index = 0; index < _this.files.length; index ++ ){
							   			if(_this.files[index].fileID == fileID){
							   				_this.files.splice(index, 1);
							   			}
							   		}
							   		  
								   	if(isNew == "true" ){//用户新增的文件 
								   		//console.log("删除文件11s " +fileID);
											$.ajax({
												type : "post",
												url : ctx + fileServiceURL.deleteFile + fileID, 
												success : function(data) { },
												Error : function(data) { }
											});
							   	    }
								   	 
									$this.remove();
								};
									  
							
								
							}
						}
				 
				});
			}
			
			
			
			});
			//定义清空事件 
			$('#' + this.ctlID + 'file_clear').live("click",function() {
				if(confirm("确定要清空文件吗?")){
					_this.$fileContainer.find("ul").find("li").each(function(){
						var $this = $(this); 
						var fileID = $this.attr("fileID");
						var isNew = $this.attr("isNew");
						
						for(var index = 0; index < _this.files.length; index ++ ){
				   			if(_this.files[index].fileID == fileID){
				   				_this.files.splice(index, 1);
				   			}
				   		}
				   		  
					   	if(isNew == "true" ){//用户新增的文件  
								$.ajax({
									type : "post",
									url : ctx + fileServiceURL.deleteFile + fileID, 
									success : function(data) { },
									Error : function(data) { }
								});
				   	    }
					   	
						$this.remove(); 
					});
				}else {
					return false;
				}
			}); 
	};
	
	/**
	 * 定义正文点击事件
	 */
	WellFileUpload4Icon.prototype.defineUploadBodyEvent = function(){
		var _this = this;
		var $body = $("#" + this.ctlID + "_body");
		var isNew = false;
		isNew = $body.attr("isNew");
		 
		$body.click(function(){
			if(isNew == "true"){//新建 
				var fileID = WellFileUpload.createctlID();
				var caller = {fileID: fileID, $fileContainer:  _this.$fileContainer.prev(), fileName: fileID + ".doc"}; 
				MSOffice.caller =  caller;
				NewOfficeDocument(MSOffice,'1'); 
				this.addBodyFile(caller.fileName, caller.fileID, false/*非新增的文件*/); 
				 
			}else{//编辑
				EditWordBody(MSOffice,'1','0','0','0','1'); 
			}
		});
	};
	
	
	/**
	 * 添加正文文件
	 */
	WellFileUpload4Icon.prototype.addBodyFile = function(fileName, fileID, isNew){

		var $ul =  this.$fileContainer4Body.find("ul");
		$ul.append('<li filename="'+filename+'" fileID="' + fileID +'" isNew="' + isNew + '" style="margin-left: 0px;"></li>');
		  
		 var $li = $ul.find("li").last();
		 
		 '<img src="'+ctx+'/resources/form/word.png" alt="' + fileName +'" /><span>正文.doc</span>';
		  
		 
		 this.files.push({fileID:fileID, ctlID: this.ctlID, purpose: this.purpose, fileName: fileName});
		 
		 
		
		 //下载文件 
		 var downURL = ctx + fileServiceURL.downFile ;
		 var viewURL = ctx + fileServiceURL.viewFile;		 
		 
		 
		 //点中文件
		  $li.bind("click", function(){
			  if($li.attr("class") != null && $li.attr("class") != "") {
					 $li.removeClass("bar");
					}else {
						$li.addClass("bar");
					}
		  });
		
		 
		 if(isNew || this.downable){//有下载权限 
			//双击下载 
			 $li.dblclick(function(){
					location.href = downURL + fileID;
			});
		 }
	};
 

});
