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
function readParamByJsonstr(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		s=eval("("+urldecode($(this).parent().parent(".dataTr").attr("jsonstr"))+ ")");
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
	function markUnreadMessage(obj){
		var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要标记的信息！");
			return ;
		}
		if(confirm('确认标记为未读吗?')) {
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
	}
	
	
	//全部置已读（我的消息）
	function readMessage(obj){
		oConfirm ("确认全部标记为已读吗？",markAllRead,obj,"");
		
	}
	//删除发件箱信息
	function deleteoutboxMessage(obj){
		var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要删除的信息！");
			return ;
		}
		if(confirm('确认删除么?')) {
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/deleteOutboxMessage.action",
			data : "uuids="+uuids,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));//刷新页面
			}
		});
		}
	}
	function deleteInboxMessage(obj){
		var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要删除的信息！");
			return ;
		}
		if(confirm('确认删除么?')) {
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/deleteInboxMessage.action",
			data : "uuids="+uuids,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));//刷新页面
			}
		});
		}
	}
	
	function transmitMessage(messagetype){
		var arrayObj = readParamByJsonstr();
		var userid="";
		var userName="";
		var subject="";
		var body="";
		var sendtime="";
		if(arrayObj.length==0){
			oAlert("请选择要转发的信息！");
			return ;
		}
		if(arrayObj.length>1){
			oAlert("不支持批量转发！");
			return ;
		}
		 sendtime=arrayObj[0]["sentTime"];
		 subject=arrayObj[0]["subject"];
		 userName=arrayObj[0]["senderName"];
		 body=arrayObj[0]["body"];
		 
		 uuid="";
		 if(messagetype=="outbox"){
			 uuid=arrayObj[0]["uuid"]; 
		 }else{
			 uuid=arrayObj[0]["uuid"]; 
		 }
			
		 body=getReplybody(userName, subject, body, sendtime);//取得回复的消息内容
		var json = new Object(); 
		 json.content =getMessage(userid,"","Fwd:"+subject,body);
	        json.title = "转发消息";
	        json.height= 490;
	        json.width= 830;
	        var buttons = new Object(); 
	        buttons.发送 = sendMessage;
	        json.buttons = buttons;
	       showDialog(json);
	       var markflag= arrayObj[0]["markFlag"];
		    if(markflag=="1"){
		    	 $("#messageStatus_1").attr("checked", "checked");	
		    }
	       initFileupload2(uuid,false);
	}
	
	function replyMessage(messagetype){
		var arrayObj = readParamByJsonstr();
		var userid="";
		var userName="";
		var subject="";
		var body="";
		var sendtime="";
		if(arrayObj.length==0){
			oAlert("请选择要答复的信息！");
			return ;
		}
		if(arrayObj.length>1){
			oAlert("不支持批量答复！");
			return ;
		}
		//取参数
		 userid=arrayObj[0]["sender"];
		 userName=arrayObj[0]["senderName"];
		 sendtime=arrayObj[0]["sentTime"];
		 subject=arrayObj[0]["subject"];
		 body=arrayObj[0]["body"];
		
		 uuid="";
		 if(messagetype=="outbox"){
			 uuid=arrayObj[0]["uuid"]; 
		 }else{
			 uuid=arrayObj[0]["uuid"]; //改为收件箱的字段
		 }
		 body=getReplybody(userName, subject, body, sendtime);//取得回复的消息内容
		 var json = new Object(); 
		 json.content = getMessage(userid,userName,"Re:"+subject,body);
	        json.title = "答复消息";
	        json.height= 490;
	        json.width= 830;
	        var buttons = new Object(); 
	        buttons.发送 = sendMessage;
	        json.buttons = buttons;
	       showDialog(json);
	       var markflag= arrayObj[0]["markFlag"];
		    if(markflag=="1"){
		    	 $("#messageStatus_1").attr("checked", "checked");	
		    }
	      initFileupload2(uuid,false);
	}
	function newMessage(obj){
		var json = new Object(); 
		 json.content = getNewMessage("","","","");
	        json.title = "新建消息";
	        json.height= 520;
	        json.width= 830;
	        var buttons = new Object(); 
	        buttons.发送 = sendMessage;
	        json.buttons = buttons;
	       showDialog(json);
	       initFileupload();
	}
	function initFileupload(){
		  var ctlID = "fileupload_online_message";
		   var fileupload = new WellFileUpload(ctlID);
		   var dbFiles = [];
			fileupload.init(false,$("#message_fileupload"),false, true, dbFiles);
	}
	function initFileupload2(folderID,readonly){
		var ctlID = "fileupload_online_message";
		   var fileupload = new WellFileUpload(ctlID);
		   fileupload.initWithLoadFilesFromFileSystem(readonly, $("#message_fileupload"),false,true,folderID,"messageAttach" );
	}
	function getReplybody(senderName,subject,body,sendtime){
		var replybody="\r\n\r\n--------------"+sendtime+"  "+senderName+" 在源消息中写道------\r\n主题:"+subject+"\r\n内容:"+body;
		return replybody;
	}
   function getMessage(userid,userName,subject,body){
	   var content="<div id='dialog_form_content'>"
				+"<table width='100%'><tbody><br></tr><tr class='odd'> <td class='Label' width='100' align='center'>收件人</td><td class='value'><div class='td_class'>"
					+"<input type='hidden' id='userId' name='userId' value='"+userid+"'/><input type='text' id='showUser' name='showUser' style='width:95%;height:20px' value='" 
					+userName+"'/>"
					+" </div> </td></tr><tr ><td class='Label' width='100' align='center'>主题</td> <td><table><tr> <td class='value'><div class='td_class'>"
					+"<input type='text' id='subject' name='subject' style='width:600px;height:20px' value='"+subject+"'/>"
					+"</div></td><td>&nbsp;&nbsp;</td><td style='padding-bottom: 6px;'><input id='messageStatus_1' name='messageStatus' type='checkbox' value='1'/></td><td>重要</td></tr></table>" +
							"</td></tr><tr class='odd'><td class='Label' width='100' align='center'>内容</td><td><table><tr><td class='value'>"
					+"<div class='td_class'><textarea  id='datetime_sec_col' style='width:650px; height:215px;' name='body'>"+body+"</textarea>"
					+" </div></td></tr><tr><td><div id='message_fileupload'></div></td></tr></table></td></tr> <tr ></tr> <input type='checkbox' class='messageType' name='type' value='ON_LINE' checked='checked' style='display: none;'/>"
					+"</tbody></table></div>";		
				
	return content;		
	
   }
   function getNewMessage(userid,userName,subject,body){
	   var content="<div id='dialog_form_content'>"
				+"<table width='100%'><tbody><br></tr><tr class='odd'> <td class='Label' width='100' align='center'>收件人</td><td class='value'><div class='td_class'>"
					+"<input type='hidden' id='userId' name='userId' value='"+userid+"'/><input type='text' id='showUser' name='showUser' style='width:95%;height:20px' value='" 
					+userName+"'/>"
					+" </div> </td></tr><tr ><td class='Label' width='100' align='center'>主题</td> <td>" +
							"<table><tr><td  class='value'><div class='td_class'>"
					+"<input type='text' id='subject' name='subject' style='width:600px;height:20px' value='"+subject+"'/>"
					+"</div></td><td>&nbsp;&nbsp;</td><td style='padding-bottom: 6px;'><input id='messageStatus_1' name='messageStatus' type='checkbox' value='1'/></td><td>重要</td></tr></table></td></tr>" +
							"<tr class='odd'><td class='Label' width='100' align='center'>内容</td><td><table><tr><td class='value'>"
					+"<div class='td_class'><textarea  id='datetime_sec_col' style='width:650px; height:215px;' name='body'>"+body+"</textarea>"
					+" </div></td></tr><tr><td><div id='message_fileupload'></div></td></tr></table></td></tr> <tr ></tr> <input type='checkbox' class='messageType' name='type' value='ON_LINE' checked='checked' style='display: none;'/>"
						
					+"</tbody></table></div>";		
				
	return content;		
	
   }

   function markReadMessage(obj){
	   var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要标记的信息！");
			return ;
		}
		if(confirm('确认标记为已读吗?')) {
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
		
	}
   function markOutboxflag(flag,obj){
	   var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要标记的信息！");
			return ;
		}
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/markoutboxflag.action",
			data : "uuids="+uuids+"&markflag="+flag,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));
			}
		});
	}
   function markInboxflag(flag,obj){
	   var arrayObj = readParamByJsonstr();
		var uuids = new Array();
		for (var i=0;i<=arrayObj.length-1;i++) { 
			var uuid = arrayObj[i]["uuid"];
			uuids.push(uuid);
		}
		if(arrayObj.length==0){
			oAlert("请选择要标记的信息！");
			return ;
		}
		$.ajax({
			type : "POST",
			url : ctx+"/message/content/markInboxflag.action",
			data : "uuids="+uuids+"&markflag="+flag,
			dataType : "text",
			success : function() {
				refreshWindow($(obj));
			}
		});
		
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





