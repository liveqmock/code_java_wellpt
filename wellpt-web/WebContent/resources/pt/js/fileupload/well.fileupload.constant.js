 

var fileServiceURL={
		saveFiles: "/repository/file/mongo/savefiles",//上传的文件地址
		downFile: "/repository/file/mongo/download?fileID=",//下载文件地址
		downloadBody:"/repository/file/mongo/downloadBody/",//正文下载地址
		deleteFile: "/repository/file/mongo/deleteFile?fileID=",//删除文件地址
		updateSignature:"/repository/file/mongo/updateSignature",//更新签名
			viewFile:"/repository/file/mongo/downloadSWF?fileID="//查看	
};

 
//图标上传控件风格
iconFileControlStyle={
	OnlyIcon:0,//只有图标
	OnlyBody:1,//只有正文
	IconAndBody:2//有图标也有正文
};



BODY_FILEID_PREFIX="BODY_FILEID_";