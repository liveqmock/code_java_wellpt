I18nLoader.load("/resources/app/js/meeting/meetingManager");
/**
 * 描述：显示会议对话框
 */
function openMeetingDialog(element,meetingName,startTime,endTime,employLeader,employPhoneNumber){
	
	var meetingJson = {"meetingName":meetingName,"startTime":startTime,"endTime":endTime,"employLeader":employLeader,"employPhoneNumber":employPhoneNumber};
	
	var content = getMeetingHtml(meetingJson);
	
	var json = {
	
		title:meetingName,  //*****标题******//*
		autoOpen: true,  //*****初始化之后，是否立即显示对话框******//*
		modal: true,     //*****是否模式对话框******//*
		closeOnEscape: true, //***当按 Esc之后，是否应该关闭对话框****//*
		draggable: true, //*****是否允许拖动******//*  
		resizable: true, //*****是否可以调整对话框的大小******//*  
		stack : false,   //*****对话框是否叠在其他对话框之上******//*
		height: 300, //*****弹出框高度******//*
		width: 400,   //*****弹出框宽度******//*
		content: content,//*****内容******//*
		
	};
	
	showDialog(json);
}

function getMeetingHtml(meetingJson){
	var content="<div id='test_con3'>"
		+ "<table class=''>"
		+ 	"<tbody>"
		
		+ "<tr class='dialog_tr tr_odd'>"
		+ "<td align='left' width='20%' class='left_td'>" + meetingManager.title + ":</td>"
		+ "<td>"+ "<input type='text' size='50' id='meetingName' name='meetingName' value='" + meetingJson.meetingName +"' readonly='readonly'>" + "</td>"
		+ "</tr>"
		
		+ "<tr class='dialog_tr tr_even'>"
		+ "<td align='left' width='20%' class='left_td'>" + meetingManager.startTime + ":</td>"
		+ "<td>"+ "<input type='text' size='50' id='startTime' name='startTime' value='" + meetingJson.startTime +"' readonly='readonly'>" + "</td>"
		+ "</tr>"
		
		+ "<tr class='dialog_tr tr_odd'>"
		+ "<td align='left' width='20%' class='left_td'>" + meetingManager.endTime + ":</td>"
		+ "<td>"+ "<input type='text' size='50' id='endTime' name='endTime' value='" + meetingJson.endTime +"' readonly='readonly'>" + "</td>"
		+ "</tr>"
		
		+ "<tr class='dialog_tr tr_even'>"
		+ "<td align='left' width='20%' class='left_td'>" + meetingManager.employLeader + ":</td>"
		+ "<td>"+ "<input type='text' size='50' id='employLeader' name='employLeader' value='" + meetingJson.employLeader +"' readonly='readonly'>" + "</td>"
		+ "</tr>"
		
		+ "<tr class='dialog_tr tr_odd'>"
		+ "<td align='left' width='20%' class='left_td'>" + meetingManager.employPhoneNumber + ":</td>"
		+ "<td>"+ "<input type='text' size='50' id='employPhoneNumber' name='employPhoneNumber' value='" + meetingJson.employPhoneNumber +"' readonly='readonly'>" + "</td>"
		+ "</tr>"
		
		+ 	"</tbody>"
		+ "</table>";
	
	return content;
}

	
/**
 * 新建会议安排
 */
$(".new_meeting").bind('click',function(){
	var url = ctx +"/workflow/work/new?flowDefUuid=7d2b519c-2a78-41fb-9efd-d764b9095709";
	window.open(url);
});



//上一周
$(".s_prev_day").bind('click',function(){
	
	var ldate=$(this).attr("ldate");
	//alert(ldate);
	$.ajax({
		type:"post",
		async:false,
		data
		: {"stype":1,"ldate":ldate},
		url:ctx + "/app/meeting/meeting_board_show.action",
		success:function(result){
			$(".dialogcontent").html(result);
		}
	});
	
});

//下一周
$(".s_next_day").bind('click',function(){
	var ldate=$(this).attr("ldate");
	$.ajax({
		type:"post",
		async:false,
		data: {"stype":2,"ldate":ldate},
		url: ctx + "/app/meeting/meeting_board_show.action",
		success:function(result){
			$(".dialogcontent").html(result);
		}
	});
});