$(function() {
	WellFileUpload4Icon = function(ctlID,// 控件唯一ID
	style// 风格,参考对象iconFileControlStyle
	) {
		this.ctlID = ctlID;// 文件上传input元素的元素ID
		this.bodyFiles = [];
		this.style = (style == undefined ? iconFileControlStyle.IconAndBody
				: style);// 该控件只显示图标附件，还是只显示正文，还是图标正文都要,默认都显示

	};

	WellFileUpload4Icon.prototype = new WellFileUpload(
			"formWellFileUpload4Icon", "icon");

	/**
	 * 组装html, 将从文件系统中获取到的文件列表组装成html。
	 */
	WellFileUpload4Icon.prototype.initWithLoadFilesFromFileSystem = function(
			uploadable/* 是否则有上传的权限 */, downable/* 是否具有下载的权限 */,
			$containerElement/* 存放该附件的容器 */, signature/* 是否签名 */, folderID,/* 文件夹ID */
			purpose/* 文件用途 */
	) {

		if ((folderID == undefined || folderID == null || folderID == "")) {
			throw new Error("请设置正确的文件夹ID");
		}

		if ((purpose == undefined || purpose == null || purpose == "")) {
			throw new Error("请设置正确的文件用途");
		}

		var dbFiles = WellFileUpload.loadFilesFormDb(folderID, purpose);

		//console.log("WellFileUpload4Icon[" + folderID + "][" + purpose
			//	+ "]上传控件从文件系统 中获取到的文件数为:" + dbFiles.length);

		this.dbFiles = dbFiles;

		this.init(uploadable/* 是否则有上传的权限 */, downable/* 是否具有下载的权限 */,
				$containerElement/* 存放该附件的容器 */, signature/* 是否签名 */,
				this.dbFiles);
	};

	/**
	 * 组装html, 将从数据库中获取到的文件列表组装成html。
	 */
	WellFileUpload4Icon.prototype.init = function(uploadable/* 是否则有上传的权限 */,
			downable/* 是否具有下载的权限 */, $containerElement/* 存放该附件的容器 */,
			signature/* 是否签名 */, dbFiles/* 是不是需要从数据库获取属于该ctlID及purpose的文件,true为需要，false为不需要 */
	) {
		this.uploadable = uploadable;
		this.downable = downable;
		this.$containerElement = $containerElement;
		this.signature = signature;// 是否签名

		// 获取保存文件信息的容器
		if (WellFileUpload.bodyFiles[this.ctlID] == undefined) {// 容器不存在，新建
			WellFileUpload.bodyFiles[this.ctlID] = [];
		}
		this.bodyFiles = WellFileUpload.bodyFiles[this.ctlID];

		// 获取保存文件信息的容器
		if (WellFileUpload.files[this.ctlID] == undefined) {// 容器不存在，新建
			WellFileUpload.files[this.ctlID] = [];
		}
		this.files = WellFileUpload.files[this.ctlID];

		// 组装html,这里将上传文件页面元素分成两部分，上半部分称之为标题部分(主要是标题，和其他的按钮(上传、清空、删除等));
		// 下半部分称之为文件部分，主要用来存放文件图标列表。
		var containerId = "titleContainer_Id_" + this.ctlID;
		var titleContainer = "<div class='title' style='padding-top:10px;padding-left:14px;height:29px;margin-right: -16px;margin-left: -13px;' id='"
				+ containerId + "'></div>";

		var containerId2 = "files_" + this.ctlID;
		var fileContainer = '<div class="post-info">'
				+ '<div id="aaaa" class="post-bd aaaa"></div>' + '<div id="'
				+ containerId2 + '" class="post-bd"></div></div>';

		this.$containerElement.html(titleContainer);
		this.$containerElement.append(fileContainer);
		this.$fileContainer = $("#" + containerId2);
		this.$fileContainer4Body = this.$fileContainer.prev();

		this.$titleContainer = $("#" + containerId);
		if (this.uploadable) {// 有上传权限
			var text = '正文 / 附件';
			if (this.style == iconFileControlStyle.OnlyIcon) {
				text = '附件';
			} else if (this.style == iconFileControlStyle.OnlyBody) {
				text = '正文';
			}
			var fileHtml = '<div class="post-info">' + '<div class="post-hd">'
					+ text + '<div class="extralDiv">'
					+ '<span class="btn btn-success fileinput-button">'
					+ '<i></i>';
			if (this.style != iconFileControlStyle.OnlyBody) {
				fileHtml = fileHtml + '<span>上传附件</span>' + "<input id='"
						+ this.ctlID + "' type='file' name='files' multiple>";
			}

			fileHtml = fileHtml + '</span></div></div></div>';

			this.$titleContainer.html(fileHtml);
			if (this.style != iconFileControlStyle.OnlyIcon) {
				this.$titleContainer
						.find(".extralDiv")
						.prepend(
								'<div id="'
										+ this.ctlID
										+ '_body" isNew="true" class="extral z"><i></i> 新建正文</div>');// isNew这个属性主要是用来判断是新建正文还是,编辑正文
			}
		} else {// 没有上传权限
			var fileHtml = '<div class="post-info">' + '<div class="post-hd">'
					+ '附件' + '</div></div>';
			this.$titleContainer.html(fileHtml);
		}

		this.$fileContainer.append('<div class="filelist"/>');
		this.$fileContainer.find(".filelist").append(
				'<ul class="attach-list"/>');

		this.$fileContainer4Body.append('<div class="filelist"/>');
		this.$fileContainer4Body.find(".filelist").append(
				'<ul class="attach-list2" >');

		if (this.style != iconFileControlStyle.OnlyBody) {// 正文没有删除功能
			this.defineDeleteEvent();
		}

		// 将从数据库中获取到的文件列表组装成html
		if (dbFiles != null && dbFiles.length > 0) {
			var fileList = dbFiles;
			for ( var index = 0; index < fileList.length; index++) {
				var dbFile = fileList[index];
				var filename = dbFile.fileName;
				var fileID = dbFile.fileID;
				var digestValue = dbFile.digestValue;
				var digestAlgorithm = dbFile.digestAlgorithm;
				var certificate = dbFile.certificate;
				var contentType = dbFile.contentType;
				if (fileID.indexOf(BODY_FILEID_PREFIX) == 0) {// 以正文前缀开头，说明是正文
					this.addBodyFile(filename, fileID, false);
				} else {
					this.addFile(filename, fileID, contentType, digestValue,
							digestAlgorithm, certificate, false/* 非新增的文件 */,
							false);
				}
			}
		}

		if (this.uploadable) {
			this.defineUploadEvent();
			if (this.style != iconFileControlStyle.OnlyIcon) {
				this.defineUploadBodyEvent();
			}

		}

	};

	WellFileUpload4Icon.prototype.addFile = function(filename, fileID,contentType, 
			digestValue, digestAlgorithm, certificate, isNew/*
															 * 新增的文件为true,
															 * 否则为false
															 */) {

		if (this.signature && isNew) { // 更新文件签名
			var updateSignatureOK = WellFileUpload.sign(fileID, digestValue,
					digestAlgorithm, certificate);
			if (!updateSignatureOK) {
				oAlert("文件签名上传失败,请重新上传");
				return;
			}
		}

		if (isNew && WellFileUpload.file2swf) {
			var ok = WellFileUpload.createReplicaOfSWF(fileID);//
			if (!ok) {
				oAlert("副本生成失败,请重试");
				return;
			}
		}

		var $ul = this.$fileContainer.find("ul");
		$ul.append('<li filename="' + filename + '" fileID="' + fileID
				+ '" isNew="' + isNew + '"></li>');

		var $li = $ul.find("li").last();

		var filename2 = filename.toLowerCase();

		if (filename2.indexOf("txt") > -1) {
			$li.html('<img src="' + ctx
					+ '/resources/form/file_txt.png" alt="" /><span>'
					+ filename + '</span>');
		} else if (filename2.indexOf("doc") > -1
				|| filename2.indexOf("docx") > -1) {
			$li.html('<img src="' + ctx
					+ '/resources/form/file_doc.png" alt="" /><span>'
					+ filename + '</span>');
		} else if (filename2.indexOf("rar") > -1
				|| filename2.indexOf("zip") > -1) {
			$li.html('<img src="' + ctx
					+ '/resources/form/rar.png" alt="" /><span>' + filename
					+ '</span>');
		} else if (filename2.indexOf("png") > -1
				|| filename2.indexOf("jpg") > -1) {
			$li.html('<img src="' + ctx
					+ '/resources/form/jpg.png" alt="" /><span>' + filename
					+ '</span>');
		} else {
			$li.html('<img src="' + ctx
					+ '/resources/form/file_doc.png" alt="" /><span>'
					+ filename + '</span>');
		}

		var file = new FileInfo();
		file.fileID = fileID;
		file.fileName = filename;
		file.digestAlgorithm = digestAlgorithm == undefined ? ""
				: digestAlgorithm;
		file.digestValue = digestValue == undefined ? "" : digestValue;
		;
		file.certificate = certificate == undefined ? "" : certificate;
		;
		
		file.contentType =  contentType== undefined? "": contentType;
		file.signatureValue = "";
		file.isNew = isNew;
		this.files.push(file);

		// 下载文件
		var downURL = ctx + fileServiceURL.downFile;
		var viewURL = ctx + fileServiceURL.viewFile;

		// 点中文件
		$li.bind("click", function() {
			if ($li.attr("class") != null && $li.attr("class") != "") {
				$li.removeClass("bar");
			} else {
				$li.addClass("bar");
			}
		});

		if (isNew || this.downable) {// 有下载权限
			// 双击下载
			$li.dblclick(function() {
				location.href = downURL + fileID;
			});
		} else {
			if (!this.downable) {// 没有下载权限,可以查看
				$li
						.bind(
								"dblclick",
								function() {
									if ((filename.toLowerCase().indexOf(".doc") > -1
											|| filename.toLowerCase().indexOf(
													".docx") > -1
											|| filename.toLowerCase().indexOf(
													".ppt") > -1
											|| filename.toLowerCase().indexOf(
													".pptx") > -1
											|| filename.toLowerCase().indexOf(
													".xls") > -1
											|| filename.toLowerCase().indexOf(
													".xlsx") > -1 || filename
											.toLowerCase().indexOf(".txt") > -1)
											&& WellFileUpload.file2swf) {
										var url = viewURL + fileID;
										window.open(url);
									} else {
										location.href = downURL + fileID;
									}
								});
			}
		}

	};

	/**
	 * 定义删除和清空按钮,该方法需在控件初始化完成后调用
	 */
	WellFileUpload4Icon.prototype.defineDeleteEvent = function() {
		// 添加删除和清空的按钮
		var deleteHtml = '<div id="' + this.ctlID
				+ 'file_clear" class="extral c">' + '<i></i> 清空' + '</div>'
				+ '<div id="' + this.ctlID + 'file_delete" class="extral d">'
				+ '<i></i> 删除' + '</div>';
		this.$titleContainer.find(".extralDiv").append(deleteHtml);

		var _this = this;

		// 最好在看完该控件的其他代码后再来看下面定义的这两个事件
		// 定义删除事件
		$("#" + this.ctlID + "file_delete")
				.bind(
						"click",
						function() {
							if (_this.$fileContainer.find(".attach-list").find(
									".bar").length == 0) {
								oAlert("未选中文件！");
							} else {
								_this.$fileContainer
										.find("ul")
										.find("li")
										.each(
												function() {
													var $this = $(this);
													var clazz = $this
															.attr("class");
													var filename = $this
															.attr("filename");
													var fileID = $this
															.attr("fileID");
													var isNew = $this
															.attr("isNew");
													if (clazz != undefined) {
														if (clazz == "bar") {
															if (confirm("确定要删除选中的文件["
																	+ filename
																	+ "]吗?")) {
																for ( var index = 0; index < _this.files.length; index++) {
																	if (_this.files[index].fileID == fileID) {
																		_this.files
																				.splice(
																						index,
																						1);
																	}
																}

																if (isNew == "true") {// 用户新增的文件
																	 
																	$
																			.ajax({
																				type : "post",
																				url : ctx
																						+ fileServiceURL.deleteFile
																						+ fileID,
																				success : function(
																						data) {
																				},
																				Error : function(
																						data) {
																				}
																			});
																}

																$this.remove();
															}
															;

														}
													}

												});
							}

						});
		// 定义清空事件
		$('#' + this.ctlID + 'file_clear').live("click", function() {
			if (confirm("确定要清空文件吗?")) {
				_this.$fileContainer.find("ul").find("li").each(function() {
					var $this = $(this);
					var fileID = $this.attr("fileID");
					var isNew = $this.attr("isNew");

					for ( var index = 0; index < _this.files.length; index++) {
						if (_this.files[index].fileID == fileID) {
							_this.files.splice(index, 1);
						}
					}

					if (isNew == "true") {// 用户新增的文件
						$.ajax({
							type : "post",
							url : ctx + fileServiceURL.deleteFile + fileID,
							success : function(data) {
							},
							Error : function(data) {
							}
						});
					}

					$this.remove();
				});
			} else {
				return false;
			}
		});
	};

	/**
	 * 定义正文点击事件
	 */
	WellFileUpload4Icon.prototype.defineUploadBodyEvent = function() {
		var _this = this;
		var $body = $("#" + this.ctlID + "_body");
		isNew = $body.attr("isNew");
		$body.click(function() {

			var bodyInfo = _this.createBodyFileInfo();
			var caller = {
				fileID : bodyInfo.fileID,
				$fileContainer : _this.$fileContainer.prev(),
				fileName : bodyInfo.fileName
			};

			MSOffice.caller = caller;
			NewOfficeDocument(MSOffice, '1');
			_this.addBodyFile(caller.fileName, caller.fileID, true);

		});
	};

	/**
	 * 创建文件信息
	 */
	WellFileUpload4Icon.prototype.createBodyFileInfo = function() {
		var fileID = WellFileUpload.createFolderID();
		fileID = BODY_FILEID_PREFIX + fileID;// 正文则在id前面
		var fileName = fileID + ".doc";

		return {
			fileID : fileID,
			fileName : fileName
		};
	};

	/**
	 * 添加正文文件
	 */
	WellFileUpload4Icon.prototype.addBodyFile = function(fileName, fileID, isNew) {
		$("#" + this.ctlID + "_body").remove();
		_this = this;
		var $ul = this.$fileContainer4Body.find("ul");
		$ul.append('<li filename="' + fileName + '" fileID="' + fileID
				+ '"   style="margin-left: 0px;"></li>');

		var $li = $ul.find("li").last();

		$li.html('<img src="' + ctx + '/resources/form/word.png" alt="'
				+ fileName + '"   title="' + fileName
				+ '"/><span>正文.doc</span>');

		var file = new FileInfo();
		file.fileID = fileID;
		file.fileName = fileName;
		file.digestAlgorithm = "";
		file.digestValue = "";
		file.certificate = "";
		file.signatureValue = "";
		file.isNew = isNew;
		this.files.push(file);

		// 双击文件
		$li.bind("dblclick", function() {
			_this.$fileContainer4Body.find("ul").find("li").removeClass("bar");
			/*
			 * if($li.attr("class") != null && $li.attr("class") != "") {
			 * $li.removeClass("bar"); }else { $li.addClass("bar"); }
			 * //console.log(fileID);
			 */
			 
			_this.files.pop(file);
			var bodyInfo = _this.createBodyFileInfo();
			var caller = {
				oldFileID : fileID,
				fileID : bodyInfo.fileID,
				fileName : bodyInfo.fileName
			};
			_this.files.push({
				fileID : bodyInfo.fileID,
				ctlID : _this.ctlID,
				fileName : bodyInfo.fileName
			});
			MSOffice.caller = caller;
			EditWordBody(MSOffice, '1', '0', '0', '0', '1');

		});

	};

});
