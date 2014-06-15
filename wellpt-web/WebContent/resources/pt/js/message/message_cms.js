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

//删除消息（我的消息）
function moveMail(){
	var arrayObj = readParam();
	if(confirm('确认删除么?')) {
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var rel = arrayObj[i]["rel"];
			var mtype = arrayObj[i]["mtype"];
			var uuid = arrayObj[i]["uuid"]+"#";
			var pageNo = arrayObj[i]["pageNo"];
			var mailname = arrayObj[i]["mailname"];
			if(mtype=='') mtype=0;
			$.ajax({
				type : "POST",
				url : ctx+"/mail/mailbox_move.action",
				data : "rel="+rel+"&pageNo="+pageNo+"&mtype="+mtype+"&selected="+uuid+"&ctype="+'3'+"&mailname="+mailname,
				dataType : "text",
				success : function callback(result) {
					$(".checkeds:checked").each(function(i){
						refreshWindow($(this));
					});
				}
			});
		}
	} 
}

//彻底删除消息（我的消息）
function delMail(){
	var arrayObj = readParam();
	if(confirm('确认彻底删除么?')) {
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var mtype = arrayObj[i]["mtype"];
			var uuid = arrayObj[i]["uuid"];
			var pageNo = arrayObj[i]["pageNo"];
			if(mtype=="") mtype="0";
				$.ajax({
					type : "POST",
					url : ctx+"/mail/mail_box_cremove.action",
					data : "mids="+uuid+"&pageNo="+pageNo+"&mtype="+mtype,
					dataType : "text",
					success : function callback(result) {
						$(".checkeds:checked").each(function(i){
							refreshWindow($(this));
						});
					}
				});
		}
	} 
}



	//全部置未读（我的消息）
	function unreadMessage(obj){
		oConfirm ("确认全部标记为未读吗？",markAllUnread,obj,"");
		
	}
	function markAllUnread(obj){
		var arrayObj = readParam();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/unread.action",
			data : "uuids="+uuids,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));
			}
		});
	}
	
	
	//全部置已读（我的消息）
	function readMessage(obj){
		oConfirm ("确认全部标记为已读吗？",markAllRead,obj,"");
		
	}
	function markAllRead(obj){
		var arrayObj = readParam();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/read.action",
			data : "uuids="+uuids,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));
			}
		});
	}





