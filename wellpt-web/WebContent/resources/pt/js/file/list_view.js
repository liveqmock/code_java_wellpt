I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//parentUuid:父文件夹的uuid;      
	//fileShowTitle:文件显示的名字;      
	//methodName:其他模块保存文档方法之后需要执行的方法的名字,如果没有，则默认传''即可
	//所传入的需要执行的scriptUrl
	//用户自定义JSON格式参数,如var json = {"fmFile_type":"新景舜弘大厦"},表示用户传入参数为'fmFile_type',值为'新景舜弘大厦',自定义的参数名必须以fmFile_为开头
	File.addFileWithTitleAndMethodNameAndScriptUrl = function(parentUuid,fileShowTitle,methodName,scriptUrl,paramJson) {
//		if(!checkCAKey()) {
//			return false;
//		}
		var folderUuid = parentUuid;
		var title = fileShowTitle;
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
			//判断是否有传入自定义JSON格式参数
			if(paramJson!=undefined){
				for(var key in paramJson){
					urlParams +="&"+key +"="+ paramJson[key];
				}
			}
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0]+"&fileShowTitle="+title+"&methodName="+methodName+"&scriptUrl="+scriptUrl+urlParams);
		}
	};
	
	
	//parentUuid:父文件夹的uuid;      
	//fileShowTitle:文件显示的名字;      
	//methodName:其他模块保存文档方法之后需要执行的方法的名字,如果没有，则默认传''即可
	//用户自定义JSON格式参数,如var json = {"fmFile_type":"新景舜弘大厦"},表示用户传入参数为'fmFile_type',值为'新景舜弘大厦',自定义的参数名必须以fmFile_为开头
	File.addFileWithTitleAndMethodName = function(parentUuid,fileShowTitle,methodName,paramJson) {
//		if(!checkCAKey()) {
//			return false;
//		}
		var folderUuid = parentUuid;
		var title = fileShowTitle;
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
			//判断是否有传入自定义JSON格式参数
			if(paramJson!=undefined){
				for(var key in paramJson){
					urlParams +="&"+key +"="+ paramJson[key];
				}
			}
			window.open(ctx + "/fileManager/file/add?folderId=" + folderUuid
					+ "&dynamicFormId=" + bean["ids"][0]+"&fileShowTitle="+title+"&methodName="+methodName+urlParams);
		}
	};
	
	
	File.addFileWithTitle = function(parentUuid,fileShowTitle) {
//		if(!checkCAKey()) {
//			return false;
//		}
		var folderUuid = parentUuid;
		var title = fileShowTitle;
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
					+ "&dynamicFormId=" + bean["ids"][0]+"&fileShowTitle="+title);
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

	File.deleteFile = function() {
//		if(!checkCAKey()) {
//			return false;
//		}
		var element = $(this);
		var arrayObj = readParam();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid+ ",file");
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
});

function reloadFileParentWindow(){
	window.location.reload();
}

//获取url参数
function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		var index = $(this).parent().parent(".dataTr").attr("src").indexOf("?");
		var search = $(this).parent().parent(".dataTr").attr("src").substr(index);
		var searchArray = search.replace("?", "").split("&");
		for(var i=0;i<searchArray.length;i++){
			var paraArray = searchArray[i].split("=");
			var key = paraArray[0];
			var value = paraArray[1];
			s[key] = value;
		}
		arrayObj.push(s);
	});
	return arrayObj;
}