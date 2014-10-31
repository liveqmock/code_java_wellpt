I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	// parentUuid:父文件夹的uuid;
	// fileShowTitle:文件显示的名字;
	// methodName:其他模块保存文档方法之后需要执行的方法的名字,如果没有，则默认传''即可
	// 所传入的需要执行的scriptUrl
	// 用户自定义JSON格式参数,如var json =
	// {"fmFile_type":"新景舜弘大厦"},表示用户传入参数为'fmFile_type',值为'新景舜弘大厦',自定义的参数名必须以fmFile_为开头
	File.addFileWithTitleAndMethodNameAndScriptUrl = function(parentUuid,
			fileShowTitle, methodName, scriptUrl, paramJson) {
		// if(!checkCAKey()) {
		// return false;
		// }
		var folderUuid = parentUuid;
		var title = encodeURIComponent(fileShowTitle);
		var bean = null;
		var urlParams = '';
		JDS.call({
			service : "fileManagerService.loadTemplateByFolderId",
			data : [ folderUuid ],
			async : false,
			success : function(result) {
				bean = result.data;
			}
		});
		if (bean != null) {
			// 判断是否有传入自定义JSON格式参数
			if (paramJson != undefined) {
				for ( var key in paramJson) {
					urlParams += "&" + key + "=" + paramJson[key];
				}
			}
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0] + "&fileShowTitle="
					+ title + "&methodName=" + methodName + "&scriptUrl="
					+ scriptUrl + urlParams);
		}
	};

	// parentUuid:父文件夹的uuid;
	// fileShowTitle:文件显示的名字;
	// methodName:其他模块保存文档方法之后需要执行的方法的名字,如果没有，则默认传''即可
	// 用户自定义JSON格式参数,如var json =
	// {"fmFile_type":"新景舜弘大厦"},表示用户传入参数为'fmFile_type',值为'新景舜弘大厦',自定义的参数名必须以fmFile_为开头
	File.addFileWithTitleAndMethodName = function(parentUuid, fileShowTitle,
			methodName, paramJson) {
		// if(!checkCAKey()) {
		// return false;
		// }
		var folderUuid = parentUuid;
		var title = encodeURIComponent(fileShowTitle);
		var bean = null;
		var urlParams = '';
		JDS.call({
			service : "fileManagerService.loadTemplateByFolderId",
			data : [ folderUuid ],
			async : false,
			success : function(result) {
				bean = result.data;
			}
		});
		if (bean != null) {
			// 判断是否有传入自定义JSON格式参数
			if (paramJson != undefined) {
				for ( var key in paramJson) {
					urlParams += "&" + key + "=" + paramJson[key];
				}
			}
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0] + "&fileShowTitle="
					+ title + "&methodName=" + methodName + urlParams);
		}
	};

	File.addFileWithTitle = function(parentUuid, fileShowTitle) {
		// if(!checkCAKey()) {
		// return false;
		// }
		var folderUuid = parentUuid;
		var title = encodeURIComponent(fileShowTitle);
		var bean = null;
		JDS.call({
			service : "fileManagerService.loadTemplateByFolderId",
			data : [ folderUuid ],
			async : false,
			success : function(result) {
				bean = result.data;
			}
		});
		if (bean != null) {
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0] + "&fileShowTitle="
					+ title);
		}
	};

	File.addFile = function(parentUuid) {
		var folderUuid = parentUuid;
		var bean = null;
		JDS.call({
			service : "fileManagerService.loadTemplateByFolderId",
			data : [ folderUuid ],
			async : false,
			success : function(result) {
				bean = result.data;
			}
		});
		if (bean != null) {
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0]);
		}
	};

	// 删除文档（假删除）
	File.deleteFile = function() {
		// if(!checkCAKey()) {
		// return false;
		// }
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid + ",file");
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm(fileManager.deleteConfirm, function() {
				JDS.call({
					service : "folderManagerService.deleteObjects",
					data : [ uuids.join(";") ],
					success : function(result) {
						oAlert(fileManager.deleteSuccess, function() {
							refreshWindow(element);
						});
					}
				});
			});
		}
	};

	// 删除文档（真删除）
	File.deleteFileTotally = function() {
		// if(!checkCAKey()) {
		// return false;
		// }
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid + ",file");
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm(fileManager.deleteConfirm, function() {
				JDS.call({
					service : "folderManagerService.deleteFilesTotally",
					data : [ uuids.join(";") ],
					success : function(result) {
						oAlert(fileManager.deleteSuccess, function() {
							refreshWindow(element);
						});
					}
				});
			});
		}
	};

	// 还原文档
	File.restoreFile = function() {
		// if(!checkCAKey()) {
		// return false;
		// }
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid + ",file");
		}
		if (uuids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			oConfirm(fileManager.restoreConfirm, function() {
				JDS.call({
					service : "folderManagerService.restoreObjects",
					data : [ uuids.join(";") ],
					success : function(result) {
						oAlert(fileManager.restoreSuccess, function() {
							refreshWindow(element);
						});
					}
				});
			});
		}
	};

	File.uploadFile = function(module) {
		var element = $(this);
		$.ajax({
			async : false,
			url : ctx + "/maindata/preUploadFile?module=" + module,
			success : function(data) {
				var json = new Object();
				json.content = data;
				json.title = "导入数据";
				json.height = 300;
				json.width = 400;
				json.buttons = {
					"上传" : function sure() {
						var file = $("#uploadWgFile").val();
						var module = $("#module").val();
						if (file == '') {
							oAlert("请选择Excel文件");
							return;
						}
						if (file.indexOf(".") < 0) {
							return;
						}
						var fileType = file.substring(file.lastIndexOf("."), file.length);
						if (fileType == ".xls" || fileType == ".xlsx") {
							$.ajaxFileUpload({
								url : ctx + "/maindata/uploadWg?module=" + module,// 链接到服务器的地址
								secureuri : false,
								fileElementId : 'uploadWgFile',// 文件选择框的ID属性
								dataType : 'text', // 服务器返回的数据格式
								success : function(data, status) {
									if (data == "success") {
										$("#dialogModule").dialog("close");
										refreshWindow(element);
									} else {
										oAlert(data);
									}
								},
								error : function(data, status, e) {
									oAlert("导入失败");
								}
							});
						} else {
							oAlert("请选择Excel文件");
							return;
						}
					}
				};
				showDialog(json);
			}
		});
	};

});

// 刷新父窗口
function reloadFileParentWindow() {
	window.location.reload();
}

function refreshWindow() {
	window.location.reload();
}

// 刷新打开本页面的父窗口
function reloadFileParentOpenerWindow() {
	window.opener.location.reload();
}

// 获取url参数
function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var index = $(this).parent().parent(".dataTr").attr("src")
						.indexOf("?");
				var search = $(this).parent().parent(".dataTr").attr("src")
						.substr(index);
				var searchArray = search.replace("?", "").split("&");
				for ( var i = 0; i < searchArray.length; i++) {
					var paraArray = searchArray[i].split("=");
					var key = paraArray[0];
					var value = paraArray[1];
					s[key] = value;
				}
				arrayObj.push(s);
			});
	return arrayObj;
}