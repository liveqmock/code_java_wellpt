I18nLoader.load("/resources/schedule/global");
////确保contextPath有值
//if (window.contextPath == undefined || window.contextPath == null) {
//	window.contextPath = "/" + window.location.pathname.split("/")[1];
//	window.ctx = window.contextPath;
//}
//获取cookie
function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr) {
		return decodeURI(arr[2]);
	} else {
		return null;
	}
}
// 确保contextPath有值
function getContextPath() {
	if (window.ctx == null || window.contextPath == null) {
		window.contextPath = getCookie("ctx");
		window.contextPath = window.contextPath == "\"\"" ? "" : window.contextPath;
		window.ctx = window.contextPath;
	}
	return window.ctx;
}
getContextPath();
	
	
	//new 
	
	//查看个人日程
	function openScheduleDialog(lleaderNames ,lleaderIds,mtype,ldate,userno,lstatus,rel,ctype,groupid,tipMethod,isComplete,sdate,uuid ,scheduleName ,address ,startDate ,startTime ,endDate,endTime   ,isView ,status ,leaderNames ,leaderIds ,pleases ,pleaseIds ,views ,viewIds,color,tip,tipDate,tipTime,repeat,startTime2,endTime2,tipTime2,creators,creatorIds){
		var title=global.viewSchedule;
		var delsche=true,editsche=true;
		var ress=leaderIds.split(";");
		var compsche=false;
		for(var i=0;i<ress.length;i++){
			if(ress[i]==userno){
				compsche=true;
				break;
			}
		}
		if(compsche==false){
			var ress=creatorIds.split(";");
			for(var i=0;i<ress.length;i++){
				if(ress[i]==userno){
					compsche=true;
					break;
				}
			}
		}
		var content=getScheduleHtml(lleaderNames ,lleaderIds,lstatus,rel,ctype,groupid,isComplete,uuid,scheduleName,address,startDate,startTime,endDate,endTime,color,leaderNames,leaderIds,pleases,pleaseIds,creators,creatorIds,isView,views,viewIds,tip,tipDate,tipTime,tipMethod,repeat,ldate,mtype,delsche,compsche,editsche,startTime2);
		var json = 
		{
			title:title,  /*****标题******/
			autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
			modal: true,     /*****是否模式对话框******/
			closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
			draggable: true, /*****是否允许拖动******/  
			resizable: true, /*****是否可以调整对话框的大小******/  
			stack : false,   /*****对话框是否叠在其他对话框之上******/
			height: 610, /*****标题******/
			width: 860,   /*****标题******/
			content: content,/*****内容******/
			//open：事件,
			buttons: {
				"编辑": invalid,
				"确定":addSchedule,
				"删除":deleteSchedule,
				"完成":completeSaveSchedule
				//"取消": 事件2
			}
		};
		showDialog(json);
		$('#flag').val(1);
		//showScheduleDialog (title,content,300,600);
		unvalid(compsche,editsche,delsche,isComplete);
		
	}

	
	//完成领导日程
	function completeSaveSchedule(){
		var status=$('#status').val();
		var ldate=$('#ldate').val();
		var rel=$('#rel').val();
		var mtype=$('#mtype').val();
		var ctype=$('#ctype').val();
		var groupid=$('#groupid').val();
		var scheduleName=$('#scheduleName').val();
		if(scheduleName==''){
			alert(global.subjectNotNull);
			$('#scheduleName').focus();
			return false;
		}
		var address=$('#address').val();
		if(address==''){
			alert(global.addressNotNull);
			$('#address').focus();
			return false;
		}
		var startDate=$('#startDate').val();
		if(startDate==''){
			alert(global.startDateNotNull);
			$('#startDate').focus();
			return false;
		}
		var quan=$("#quan").attr("checked")=='checked'?"1":"0";
		var jieshu=$("#jieshu").attr("checked")=='checked'?"1":"0";
		var startTime,startTime2,endDate,endTime,endTime2;
		if(quan==0){
		startTime=$('#startTime').val();
		startTime2=$('#startTime2').val();
		if(startTime==''||startTime2==''){
			alert(global.startTimeNotNull);
			$('#startTime').focus();
			return false;
		}
		}
		if(jieshu==1){
		endDate=$('#endDate').val();
		if(endDate==''){
			alert(global.endDateNotNull);
			$('#endDate').focus();
			return false;
		}
		}
		if(quan==0&&jieshu==1){
		endTime=$('#endTime').val();
		endTime2=$('#endTime2').val();
		if(endTime==''||endTime2==''){
			alert(global.endTimeNotNull);
			$('#endTime').focus();
			return false;
		}
		}
		
		var color="";
		
		color=$("#mycolor").val();
		
		
		
		
		
		var leaderNames=$('#leaderNames').val();
		if(leaderNames==''){
			if(status=='1'){
				alert(global.leaderNotNull);
				}else{
					alert(global.zerenNotNull);
				}
			$('#leaderNames').focus();
			return false;
		}
		var leaderIds=$('#leaderIds').val();
		var isView="";
		$(".isView").each(function (){
				if($(this).attr("checked")=="checked"){
					isView=$(this).val();
				}
			});
		var pleases=$('#pleases').val();
		var views=$('#views').val();
		var pleaseIds=$('#pleaseIds').val();
		var viewIds=$('#viewIds').val();
		var creatorIds=$('#creatorIds').val();
		var creators=$('#creators').val();
		var status=$('#status').val();
		var uuid=$('#uuid').val();
		var tip=$('#tip').val();
		var tipDate=$('#tipDate').val();
		var tipTime=$('#tipTime').val();
		var tipTime2=$('#tipTime2').val();
		var repeat=$("#repeat").val();
		$(".tipMethod").each(function (){
			if($(this).attr("checked")=="checked"){
				tipMethod=$(this).val();
			}
		});
//		var tipMethod=$("#tipMethod").val();
		//alert("color="+color+"&uuid="+uuid+"&status="+status+"&scheduleName="+scheduleName+"&address="+address+"&dstartDate="+startDate+"&startTime="+startTime+"&dendDate="+endDate+"&endTime="+endTime+"&scheduleType="+scheduleType+"&isHint="+isHint+"&hintCreator="+hintCreator+"&hintPlease="+hintPlease+"&hintView="+hintView+"&onlineMessage="+onlineMessage+"&mail="+mail+"&mobil="+mobil+"&hintDay="+hintDay+"&hintHour="+hintHour+"&hintMinute="+hintMinute+"&isView="+isView+"&leaderNames="+leaderNames+"&leaderIds="+leaderIds+"&pleases="+pleases+"&views="+views+"&pleaseIds="+pleaseIds+"&viewIds="+viewIds);
				$.ajax({
					type : "POST",
					url : contextPath+"/schedule/schedule_add.action",
					data : "tipMethod="+tipMethod+"&creators="+creators+"&creatorIds="+creatorIds+"&tip="+tip+"&tipTime="+tipTime+"&tipDate="+tipDate+"&repeat="+repeat+"&color="+color+"&uuid="+uuid+"&status="+status+"&scheduleName="+scheduleName+"&address="+address+"&dstartDate="+startDate+"&startTime="+startTime+"&dendDate="+endDate+"&endTime="+endTime+"&isView="+isView+"&leaderNames="+leaderNames+"&leaderIds="+leaderIds+"&pleases="+pleases+"&views="+views+"&pleaseIds="+pleaseIds+"&viewIds="+viewIds,
					dataType : "text",
					success : function callback(result) {
						completeSchedule(status,ldate,rel,mtype,ctype,groupid);
					},
					error: function (data, status, e)
					{
						alert(e);
					}
				});
		}
		
	//完成日程
	function completeSchedule(status,ldate,rel,mtype,ctype,groupid){
		var uuid=$('#uuid').val();
			if(confirm(global.completeConfirm)) {
					$.ajax({
						type : "POST",
						url : contextPath+"/schedule/schedule_complete.action",
						data : "uuid="+uuid,
						dataType : "text",
						success : function callback(result) {
							closeDialog();
							var uu;
							if(rel==1){
								//window.location.href=contextPath+"/schedule/leader_schedule.action?ldate="+ldate+"&mtype="+mtype;
								uu = contextPath+"/schedule/leader_schedule.action";
							}
							
								if(rel==2){
									//window.location.href=contextPath+"/schedule/leader_schedule.action?ldate="+ldate+"&ctype=1&mtype="+mtype;
									uu =contextPath+"/schedule/leader_schedule.action";
									ctype = 1;
									
									}
								if(rel==3){
									//window.location.href=contextPath+"/schedule/person_schedule.action?ldate="+ldate+"&mtype="+mtype;
									uu = contextPath+"/schedule/person_schedule.action";
									
									}
								if(rel==4){
									//window.location.href=contextPath+"/schedule/person_schedule2.action?ldate="+ldate+"&mtype="+mtype;
									uu=contextPath+"/schedule/person_schedule2.action";
									
								}
								if(rel==5){
									//window.location.href=contextPath+"/schedule/group_schedule.action?ldate="+ldate+"&ctype="+ctype+"&groupid="+groupid;
									uu=contextPath+"/schedule/group_schedule.action";
									
								}
								
								$.ajax({
									type:"post",
									async:false,
									data : {"stype":0,"ldate":ldate,"mtype":mtype,"requestType":"cms","&ctype=":ctype,"&groupid=":groupid},
									url: uu,
									success:function(result){
										$(".schedule_person_list").parent().html(result);
//										$(".schedule_person_list").parent().hide();
//										$(".schedule_person_list").parent().html(result);
////						 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
//										$(".schedule_person_list").parent().show();
									}
								});
						}
					});
			}
		
	}
	//删除领导日程
	function deleteSchedule(){
		var status=$('#status').val();
		var ldate=$('#ldate').val();
		var rel=$('#rel').val();
		var mtype=$('#mtype').val();
		var ctype=$('#ctype').val();
		var groupid=$('#groupid').val();
		var uuid=$('#uuid').val();
			if(confirm(global.deleteConfirm)) {
					$.ajax({
						type : "POST",
						url : contextPath+"/schedule/schedule_delete.action",
						data : "uuid="+uuid,
						dataType : "text",
						success : function callback(result) {
							closeDialog();
							var uu;
							if(rel==1){
								uu = contextPath+"/schedule/leader_schedule.action";
								}
								if(rel==2){
									uu =contextPath+"/schedule/leader_schedule.action";
									ctype = 1;
									}
								if(rel==3){
									//window.location.href=contextPath+"/schedule/person_schedule.action?ldate="+ldate+"&mtype="+mtype;
									  uu = contextPath+"/schedule/person_schedule.action";
									}
								if(rel==4){
									uu=contextPath+"/schedule/person_schedule2.action";
								}
								if(rel==5){
									uu=contextPath+"/schedule/group_schedule.action";
								}
								
								$.ajax({
									type:"post",
									async:false,
									data : {"stype":0,"ldate":ldate,"mtype":mtype,"requestType":"cms","&ctype=":ctype,"&groupid=":groupid},
									url: uu,
									success:function(result){
										$(".schedule_person_list").parent().html(result);
//										$(".schedule_person_list").parent().hide();
//										$(".schedule_person_list").parent().html(result);
////						 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
//										$(".schedule_person_list").parent().show();
									}
								});
								
						},
						error: function (data, status, e)
					{
						alert(e);
					}
					});
			}
		
	}
	//添加领导日程
	//ctype=0 群组日程1 ctype=1 群组日程2
	function addSchedule(){
		var status=$('#status').val();
		var ldate=$('#ldate').val();
		var rel=$('#rel').val();
		var mtype=$('#mtype').val();
		var ctype=$('#ctype').val();
		var groupid=$('#groupid').val();
		var scheduleName=$('#scheduleName').val();
		if(scheduleName==''){
			alert(global.subjectNotNull);
			$('#scheduleName').focus();
			return false;
		}
		var address=$('#address').val();
		if(address==''){
			alert(global.addressNotNull);
			$('#address').focus();
			return false;
		}
		var startDate=$('#startDate').val();
		if(startDate==''){
			alert(global.startDateNotNull);
			$('#startDate').focus();
			return false;
		}
		var quan=$("#quan").attr("checked")=='checked'?"1":"0";
		var jieshu=$("#jieshu").attr("checked")=='checked'?"1":"0";
		var startTime,startTime2,endDate,endTime,endTime2;
		if(quan==0){
		startTime=$('#startTime').val();
		//startTime2=$('#startTime2').val();
		if(startTime==''||startTime2==''){
			alert(global.startTimeNotNull);
			$('#startTime').focus();
			return false;
		}
		}
		if(jieshu==1){
		endDate=$('#endDate').val();
		if(endDate==''){
			alert(global.endDateNotNull);
			$('#endDate').focus();
			return false;
		}
		}
		if(quan==0&&jieshu==1){
			endTime=$('#endTime').val();
			//endTime2=$('#endTime2').val();
			if(endTime==''||endTime2==''){
				alert(global.endTimeNotNull);
				$('#endTime').focus();
				return false;
			}
		}
		var color="";
		color=$("#mycolor").val();
		var leaderNames=$('#leaderNames').val();
		if(leaderNames==''){
			if(status=='1'){
			alert(global.leaderNotNull);
			}else{
				alert(global.zerenNotNull);
			}
			$('#leaderNames').focus();
			return false;
		}
		var leaderIds=$('#leaderIds').val();
		var isView="";
		$(".isView").each(function (){
			if($(this).attr("checked")=="checked"){
				isView=$(this).val();
			}
		});
		var pleases=$('#pleases').val();
		var views=$('#views').val();
		var pleaseIds=$('#pleaseIds').val();
		var viewIds=$('#viewIds').val();
		var creatorIds=$('#creatorIds').val();
		var creators=$('#creators').val();
		var status=$('#status').val();
		var uuid=$('#uuid').val();
		var tip=$('#tip').val();
		var tipTime=$('#tipTime').val();
		var tipDate=$('#tipDate').val();
		//var tipTime2=$('#tipTime2').val();
		var repeat=$("#repeat").val();
		var tipMethod;
		$(".tipMethod").each(function (){
			if($(this).attr("checked")=="checked"){
				tipMethod=$(this).val();
			}
		});
		//alert("ldate="+ldate+",mtype="+mtype+"rel="+rel);
		//alert("tipMethod="+tipMethod+"&creators="+creators+"&creatorIds="+creatorIds+"&tip="+tip+"&tipTime="+tipTime+"&repeat="+repeat+"&color="+color+"&uuid="+uuid+"&status="+status+"&scheduleName="+scheduleName+"&address="+address+"&dstartDate="+startDate+"&startTime="+startTime+"&dendDate="+endDate+"&endTime="+endTime+"&isView="+isView+"&leaderNames="+leaderNames+"&leaderIds="+leaderIds+"&pleases="+pleases+"&views="+views+"&pleaseIds="+pleaseIds+"&viewIds="+viewIds);
		$.ajax({
			type : "POST",
			url : contextPath+"/schedule/schedule_add.action",
			data : "tipMethod="+tipMethod+"&creators="+creators+"&creatorIds="+creatorIds+"&tip="+tip+"&tipTime="+tipTime+"&tipDate="+tipDate+"&repeat="+repeat+"&color="+color+"&uuid="+uuid+"&status="+status+"&scheduleName="+scheduleName+"&address="+address+"&dstartDate="+startDate+"&startTime="+startTime+"&dendDate="+endDate+"&endTime="+endTime+"&isView="+isView+"&leaderNames="+leaderNames+"&leaderIds="+leaderIds+"&pleases="+pleases+"&views="+views+"&pleaseIds="+pleaseIds+"&viewIds="+viewIds,
			dataType : "text",
			success : function(result) {
				alert("保存成功！");
				closeDialog();
				var uu;
				if(rel==1){
				//window.location.href=contextPath+"/schedule/leader_schedule.action?ldate="+ldate+"&mtype="+mtype;
					uu = contextPath+"/schedule/leader_schedule.action";
				}
				if(rel==2){
					//window.location.href=contextPath+"/schedule/leader_schedule.action?ldate="+ldate+"&ctype=1&mtype="+mtype;
					uu =contextPath+"/schedule/leader_schedule.action";
					ctype = 1;
					}
				if(rel==3){ 
					//alert("/schedule/person_schedule.action?ldate="+ldate+"&mtype="+mtype);
					//window.location.href=contextPath+"/schedule/person_schedule.action?ldate="+ldate+"&mtype="+mtype;
					uu = contextPath+"/schedule/person_schedule.action";
					}
				if(rel==4){
					//window.location.href=contextPath+"/schedule/person_schedule2.action?ldate="+ldate+"&mtype="+mtype;
					uu=contextPath+"/schedule/person_schedule2.action";
				}
				if(rel==5){
					//window.location.href=contextPath+"/schedule/group_schedule.action?ldate="+ldate+"&ctype="+ctype+"&groupid="+groupid;
					uu=contextPath+"/schedule/group_schedule.action";
				}
				$.ajax({
					type:"post",
					async:false,
					data : {"stype":0,"ldate":ldate,"mtype":mtype,"requestType":"cms","&ctype=":ctype,"&groupid=":groupid},
					url: uu,
					success:function(result){
						$(".schedule_person_list").parent().html(result);
//										$(".schedule_person_list").parent().hide();
//										$(".schedule_person_list").parent().html(result);
////								 				$(".schedule_person_list").parent().children().not(".schedule_person_list").remove();
//										$(".schedule_person_list").parent().show();
					}
				});
			},
			error: function (data, status, e)
			{
				alert(e);
			}
		});
		
		//刷新窗口
		setTimeout(function(){searchWindow();},300);
	}
	
////弹出DIALOG窗口
//	function showScheduleDialog (title,content,height,width){
//		$("#dialogModule").dialog({
//			title:title,
//			autoOpen: true,
//			height: height,
//			width: width,
//			modal: true,
//			open:function() {
//				$(".ui-widget-overlay").css("background", "#000");
//				$(".ui-widget-overlay").css("opacity", "0.5");
//				if(content!=""){
//					$(".dialogcontent").html(content);
//				}
//			}
//		});	
//	}
//	//关闭DIALOG窗口
//	function closeDialog(){
//		$("#dialogModule").dialog("close");
//	}	
	
	
	//新建日程
	function openScheduleNewDialog(relLeader,relLeaderId,lleaderNames ,lleaderIds,status,rel,ctype,groupid,creators,creatorIds,sdate,ldate,mtype,now){
		var title=global.addSchedule;
		var endDate='',startDate=sdate,editsche=false,delsche=false,compsche=false,isComplete='0',uuid='',scheduleName='',address='',startTime='',startTime2=sdate,endTime='',mycolor='black',leaderNames='',leaderIds='',pleases='',pleaseIds='',isView='3',views='',viewIds='',tip='1',tipDate='',tipTime='',tipMethod='1',repeat='1';
		
		if(tipDate==''){
			tipDate=sdate;
		}
		if(status=='1'){
			leaderNames=relLeader+";"+relLeaderId;
			leaderIds=relLeaderId;
		}
		var content=getScheduleHtml(lleaderNames ,lleaderIds,status,rel,ctype,groupid,isComplete,uuid,scheduleName,address,startDate,startTime,endDate,endTime,mycolor,leaderNames,leaderIds,pleases,pleaseIds,creators,creatorIds,isView,views,viewIds,tip,tipDate,tipTime,tipMethod,repeat,ldate,mtype,delsche,compsche,editsche,startTime2);
		var json = 
		{
			title:title,  /*****标题******/
			autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
			modal: true,     /*****是否模式对话框******/
			closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
			draggable: true, /*****是否允许拖动******/  
			resizable: true, /*****是否可以调整对话框的大小******/  
			stack : false,   /*****对话框是否叠在其他对话框之上******/
			height: 600, /*****标题******/
			width: 850,   /*****标题******/
			content: content,/*****内容******/
			//open：事件,
			buttons: {
				"确定":addSchedule
				//"取消": 事件2
			}
		};

		showDialog(json);
		openNewSche();
		$('#flag').val(0);//代表新增
		
		/**使用日程的样例，请使用dialog的按钮（统一规范），按钮不能写在content里**/
//		showScheduleDialog (title,content,300,600);
	}
	function test(){
		alert("测试弹出口按钮");
	}
	//获得当前小时分钟
	function getTimeHHMM()
	{
		var ERP_TIME = new Date();
		var intHours, intMinutes,intSeconds;
		var timestr="";
		
		intHours = ERP_TIME.getHours();
		intMinutes = ERP_TIME.getMinutes();
		intSeconds = ERP_TIME.getSeconds();
		if (intHours < 10) {
			timestr =timestr+ "0"+intHours+":";
		} else {
			timestr =timestr+ intHours + ":";
		}
		if (intMinutes < 10) {
			timestr =timestr+ "0"+intMinutes+":";
		} else {
			timestr =timestr+ intMinutes+":";
		}
		if (intSeconds < 10) {
			timestr =timestr+ "0"+intSeconds+" ";
		} else {
			timestr =timestr+ intSeconds+" ";
		}
		return timestr;
	}
	
	//获得当前小时分钟
	function getTimeHHMM2()
	{
		var ERP_TIME = new Date();
		var intHours, intMinutes;
		var timestr="";
		
		intHours = ERP_TIME.getHours();
		intMinutes = ERP_TIME.getMinutes();
		if (intHours < 10) {
			timestr =timestr+ "0"+intHours+":";
		} else {
			timestr =timestr+ intHours + ":";
		}
		if (intMinutes < 10) {
			timestr =timestr+ "0"+intMinutes;
		} else {
			timestr =timestr+ intMinutes;
		}
		
		return timestr;
	}
	
	//获得日程HTML
	function getScheduleHtml(lleaderNames ,lleaderIds,status,rel,ctype,groupid,isComplete,uuid,scheduleName,address,startDate,startTime,endDate,endTime,mycolor,leaderNames,leaderIds,pleases,pleaseIds,creators,creatorIds,isView,views,viewIds,tip,tipDate,tipTime,tipMethod,repeat,ldate,mtype,delsche,compsche,editsche,startTime2){
		var content="<div class='addschedulediv'>"
			+"<input type='hidden' id='status' name='status' value='"+status+"' />"
			+"<input type='hidden' id='uuid' name='uuid' value='"+uuid+"' />"
			
			+"<input type='hidden' id='rel' name='rel' value='"+rel+"'/>"
			+"<input type='hidden' id='ctype' name='ctype' value='"+ctype+"'/>"
			+"<input type='hidden' id='mtype' name='mtype' value='"+mtype+"'/>"
			+"<input type='hidden' id='ldate' name='ldate' value='"+ldate+"'/>"
			+"<input type='hidden' id='groupid' name='groupid' value='"+groupid+"'/>"
			//设置隐藏域 便于区别查看和编辑
			+"<input type='hidden' id='flag' name='flag' />"
			
			+"<table class='add_schedule_table'>"
			
			+"<tr class='add_schedule_table_tr add_schedule_table_odd' >"
			+"<td align='left' width='20%'  class='td_align_right'>"+global.title+"</td>"
			+"<td class='td_align_left'><input  type='text' size='50' id='scheduleName' name='scheduleName' value='"+scheduleName+"' size='70'/>" 
			+"<div class='chosecolor'>" 
			+"<input type='hidden' id='mycolor' name='mycolor' value='"+mycolor+"'/>"
			+"<span id='showcolor' style='background-color:"+mycolor+";'></span>"
			+"</div>"
			+"<div class='colorbutton' onclick='choseColor1(this)'>" 
			+"<div class='colors' style='display:none;'>" 
			+"<span class='selectcolor' style='background-color:black;' onclick=\"selColor('black');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:red ' onclick=\"selColor('red');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:blue ' onclick=\"selColor('blue');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:orange ' onclick=\"selColor('orange');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:green ' onclick=\"selColor('green');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:yellow ' onclick=\"selColor('yellow');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:purple ' onclick=\"selColor('purple');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:silver ' onclick=\"selColor('silver');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:tan ' onclick=\"selColor('tan');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:maroon;' onclick=\"selColor('maroon');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:olive;' onclick=\"selColor('olive');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:gray;' onclick=\"selColor('gray');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"<span class='selectcolor' style='background-color:lime;' onclick=\"selColor('lime');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+"</div>"
			+"</div>"
			+"</td></tr>"
			
			+"<tr class='add_schedule_table_tr add_schedule_table_even'>"
			+"<td align='left' width='20%' class='td_align_right'>时间</td>"
			+"<td class='td_align_left'>"
			+"<div class='trkaishi'>"
			+"<div class='trkaishi_data'>"
			+"<input type='text' size='10' id='startDate' name='startDate' value='"+startDate+"'  onclick='WdatePicker();' />" 
			+"</div>";
			if(startTime!=''){
			content+="<div class='trkaishi_time' id='dstartTime' style='display:block;'>";
			content+="<input  type='text' size='10' id='startTime' name='startTime' value='"+startTime+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
			}else{
			content+="<div class='trkaishi_time' id='dstartTime' style='display:none;'>";
			content+="<input  type='text' size='10' id='startTime' name='startTime' value='"+getTimeHHMM()+"'  onclick=\"WdatePicker({dateFmt:'HH:mm:ss'});\" />";
			}
			content+="</div>";
			content+="</div>";
			if(endDate!=''){
			content+="<div style='display:block' id='trjieshu' class='trjieshu'>";
			}else{
			content+="<div style='display:none' id='trjieshu' class='trjieshu'>";
			}
			content+="<div class='datetimeto'>-</div><div  class='trjieshu_data' >"
			+"<input  type='text' size='10' id='endDate' name='endDate' value='"+endDate+"'  onclick='WdatePicker();' />"
			+"</div>";
			if(endTime!=''){
			content+="<div class='trjieshu_time' id='dendTime'  style='display:block;'>";
			content+="<input  type='text' size='10' id='endTime' name='endTime' value='"+endTime+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
			}else{
			content+="<div class='trjieshu_time' id='dendTime'  style='display:none;'>";
			content+="<input  type='text' size='10' id='endTime' name='endTime' value='"+getTimeHHMM()+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
			}
			content+=" </div>"
			+"</div>"
			+"<div class='datatimeset'>";
			if(startDate!='' &&startTime==''&&endDate==''){
			content+="<input type='checkbox' id='quan' name='quan' onclick='quantian();' checked='checked' />"+global.quantian;
			}else{
			content+="<input type='checkbox' id='quan' name='quan' onclick='quantian();' />"+global.quantian;
			}
			if(endDate!=''){
			content+="<input type='checkbox' id='jieshu' name='jieshu' onclick='jieshu();' checked='checked'  />"+global.jieshu;
			}else{
			content+="<input type='checkbox' id='jieshu' name='jieshu' onclick='jieshu();'  />"+global.jieshu;
			}
			content+="</div>"
			+"</td>"
			+"</tr>"
			
			
			
//			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
//			+"<td align='left' width='20%' class='td_align_right'>"+global.start+"</td>"
//			+"<td class='td_align_left'>"
//			+"<div style='float: left;width:100%;'>"
//			+"<input type='text' size='10' id='startDate' name='startDate' value='"+startDate+"'  onclick='WdatePicker();' />" 
//			+"</div>";
//			if(startTime!=''){
//			content+="<div id='dstartTime' style='width:48%;display:inline;float:left; position:relative; margin:0 0 0 0;'>";
//			content+="<input  type='text' size='10' id='startTime' name='startTime' value='"+startTime+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
//			}else{
//			content+="<div id='dstartTime' style='width:48%;display:none;float:left; position:relative; margin:0 0 0 0;'>";
//			content+="<input  type='text' size='10' id='startTime' name='startTime' value='"+getTimeHHMM()+"'  onclick=\"WdatePicker({dateFmt:'HH:mm:ss'});\" />";
//			}
//			content+="</div>"
//			+"</td>"
//			+"</tr>";
//			if(endDate!=''){
//			content+="<tr style='display:inline' id='trjieshu' class='add_schedule_table_tr add_schedule_table_even'>";
//			}else{
//			content+="<tr style='display:none' id='trjieshu' class='add_schedule_table_tr add_schedule_table_even'>";
//			}
//			content+="<td align='left' width='20%' class='td_align_right'>"+global.end+"</td>"
//			+"<td class='td_align_left'>"
//			+"<div  style='width:100%;float:left; position:relative; margin:0 0 0 0; '>"
//			+"<input  type='text' size='10' id='endDate' name='endDate' value='"+endDate+"'  onclick='WdatePicker();' />"
//			+"</div>";
//			if(endTime!=''){
//			content+="<div id='dendTime'  style='width:48%;display:inline;float:left; position:relative; margin:0 0 0 0;'>";
//			content+="<input  type='text' size='10' id='endTime' name='endTime' value='"+endTime+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
//			}else{
//			content+="<div id='dendTime'  style='width:48%;display:none;float:left; position:relative; margin:0 0 0 0;'>";
//			content+="<input  type='text' size='10' id='endTime' name='endTime' value='"+getTimeHHMM()+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
//			}
//			content+=" </div>"
//			+"</td>"
//			+"</tr>"
//			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
//			+"<td class='td_align_right' align='left' width='20%'></td>"
//			+"<td class='td_align_left'><table><tr><td>";
//			if(startDate!='' &&startTime==''&&endDate==''){
//			content+="<input type='checkbox' id='quan' name='quan' onclick='quantian();' checked='checked' />"+global.quantian;
//			}else{
//			content+="<input type='checkbox' id='quan' name='quan' onclick='quantian();' />"+global.quantian;
//			}
//			if(endDate!=''){
//			content+="</td><td><input type='checkbox' id='jieshu' name='jieshu' onclick='jieshu();' checked='checked'  />"+global.jieshu;
//			}else{
//			content+="</td><td><input type='checkbox' id='jieshu' name='jieshu' onclick='jieshu();'  />"+global.jieshu;
//			}
//			content+="</td></tr></table></td>"
//			+"</tr>"
			
			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
			+"<td align='left' width='20%' class='td_align_right'>"+global.address+"</td>"
			+"<td class='td_align_left'><input type='text' id='address' name='address' value='"+address+"' size='50'/></td>"
			+"</tr>"
			
//			+"<tr class='add_schedule_table_tr add_schedule_table_even'>"
//			+"<td align='left' width='20%' class='td_align_right'>"+global.color+"</td>"
//			+"<td class='td_align_left'>"
//			+"<input type='hidden' id='mycolor' name='mycolor' value='"+mycolor+"'/>"
//			+"<span id='showcolor' style='background-color:"+mycolor+";'>&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span >&nbsp;|&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:black;' onclick=\"selColor('black');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:red ' onclick=\"selColor('red');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:blue ' onclick=\"selColor('blue');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:orange ' onclick=\"selColor('orange');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:green ' onclick=\"selColor('green');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:yellow ' onclick=\"selColor('yellow');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:purple ' onclick=\"selColor('purple');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:silver ' onclick=\"selColor('silver');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:tan ' onclick=\"selColor('tan');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:maroon;' onclick=\"selColor('maroon');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:olive;' onclick=\"selColor('olive');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:gray;' onclick=\"selColor('gray');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"<span class='selectcolor' style='background-color:lime;' onclick=\"selColor('lime');\">&nbsp;&nbsp;&nbsp;&nbsp;</span>"
//			+"</td>"
//			+"</tr>"
			
			+"<tr class='add_schedule_table_tr add_schedule_table_even'>";
			//leaderNames ,leaderIds,
			if(status=='1'){
				content+="<td align='left' width='20%' class='td_align_right'>"+global.leader+"</td>"
					+"<td class='td_align_left'><select id='leaderNames' name='leaderNames' value='"+leaderNames+"'>";
				var lnames=lleaderNames.split(";");
				var lids=lleaderIds.split(";");
				for(var i=0;i<lnames.length;i++){
					if(lnames[i]!=''){
						var lvalue=lnames[i]+";"+lids[i];
						if(lids[i]==leaderIds){
							content+="<option value='"+lvalue+"' selected='selected'>"+lnames[i]+"</option>";
						}else{
							content+="<option value='"+lvalue+"'>"+lnames[i]+"</option>";
						}
					}
				}
				content+="</select><input  type='hidden' name='leaderIds' value='"+leaderIds+"' id='leaderIds'/>";
			}else{
				content+="<td align='left' width='20%' class='td_align_right'>"+global.zeren+"</td>"
				+"<td class='td_align_left'><input type='text' name='leaderNames' value='"+leaderNames+"' readonly='readonly' id='leaderNames' size='50' onclick=\"openUser('leaderNames','leaderIds');\" /><input  type='hidden' name='leaderIds' value='"+leaderIds+"' id='leaderIds'/></td>";
			}
			content+="</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
			+"<td align='left' width='20%' class='td_align_right'>"+global.pleases+"</td>"
			+"<td class='td_align_left'><input  type='text' name='pleases'  id='pleases' value='"+pleases+"' readonly='readonly'  size='50' onclick=\"openUser('pleases','pleaseIds');\"/><input  type='hidden' name='pleaseIds' value='"+pleaseIds+"'  id='pleaseIds'  /></td>"
			+"</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_even'>"
			+"<td align='left' width='20%' class='td_align_right'>"+global.creator+"</td>"
			+"<td class='td_align_left'><input  type='text' name='creators'  id='creators' value='"+creators+"' readonly='readonly'  size='50' onclick=\"openUser('creators','creatorIds');\"/><input  type='hidden' value='"+creatorIds+"' name='creatorIds'  id='creatorIds'  /></td>"
			+"</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
			+"<td align='left' width='20%' class='td_align_right'>"+global.isView+"</td>"
			+"<td class='td_align_left'><table><tr><td>";
			if(isView=='3'){
			content+="<input  type='radio' size='5' id='isView' name='isView' class='isView' value='3' checked='checked' onclick='showView(this);'/>"+global.isView1;
			content+="</td><td><input type='radio' size='5' id='isView' name='isView' class='isView' value='2'  onclick='showView(this);'/>"+global.isView2;
			content+="</td><td><input type='radio' size='5' id='isView' class='isView' name='isView' value='1' onclick='showView(this);' />"+global.isView3;
			}
			if(isView=='2'){
				content+="<input  type='radio' size='5' id='isView' name='isView' class='isView' value='3'  onclick='showView(this);'/>"+global.isView1;
				content+="</td><td><input type='radio' size='5' id='isView' name='isView' class='isView' value='2' checked='checked' onclick='showView(this);'/>"+global.isView2;
				content+="</td><td><input type='radio' size='5' id='isView' class='isView' name='isView' value='1' onclick='showView(this);' />"+global.isView3;
			}
			if(isView=='1'){
				content+="<input  type='radio' size='5' id='isView' name='isView' class='isView' value='3'  onclick='showView(this);'/>"+global.isView1;
				content+="</td><td><input type='radio' size='5' id='isView' name='isView' class='isView' value='2'  onclick='showView(this);'/>"+global.isView2;
				content+="</td><td><input type='radio' size='5' id='isView' class='isView' name='isView' value='1' checked='checked' onclick='showView(this);' />"+global.isView3;
			}
			content+="</td></tr></table></td>"
			+"</tr>";
			if(views==''){
				content+="<tr id='showview' style='display: none' class='add_schedule_table_tr add_schedule_table_odd'>";
			}else{
				content+="<tr id='showview' class='add_schedule_table_tr add_schedule_table_odd'>";
			}
			content+="<td align='left' width='20%' class='td_align_right'>"+global.views+"</td>"
			+"<td class='td_align_left'><input  type='text' id='views' name='views' value='"+views+"'   readonly='readonly' size='50' onclick=\"openUser('views','viewIds');\"/><input  type='hidden' value='"+viewIds+"' id='viewIds' name='viewIds'  /></td>"
			+"</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_even'>"
			+"<td align='left' width='20%' class='td_align_right'>"+global.tip+"</td>"
			+"<td class='td_align_left'>"
			+"<table width='93%'><tr class='tixing'><td>"
			+"<select id='tip' name='tip' value='"+tip+"' onchange='tipChange();'>";
			if(tip=='1'){
				content+="<option value='1' selected='selected'  class='td_align_right'>"+global.tip1+"</option>";
			}else{
				content+="<option value='1'>"+global.tip1+"</option>";
			}
			if(tip=='2'){
				content+="<option value='2' selected='selected'  class='td_align_right'>"+global.tip2+"</option>";
			}else{
				content+="<option value='2'>"+global.tip2+"</option>";
			}
			if(tip=='3'){
				content+="<option value='3' selected='selected'  class='td_align_right'>"+global.tip3+"</option>";
			}else{
				content+="<option value='3'>"+global.tip3+"</option>";
			}
			if(tip=='4'){
				content+="<option value='4' selected='selected'  class='td_align_right'>"+global.tip4+"</option>";
			}else{
				content+="<option value='4'>"+global.tip4+"</option>";
			}
			content+="</select>"
			+"</td>";
			if(tip=='4'){
				content+="<td id='dtipDate'>";
			}else{
				content+="<td id='dtipDate' style='display:none;'>";
			}
			if(tipDate==''){
				tipDate=startTime2;
			}
			content+="<input type='text' size='10'  id='tipDate' name='tipDate' value='"+tipDate+"' onclick='WdatePicker();'/>"
			+"</td>";
			if(tip!='1'){
				content+="<td id='dtipTime'  >";
					content+="<input  type='text' size='10' id='tipTime' name='tipTime' value='"+tipTime+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
			}else{
				content+="<td id='dtipTime'  style='display:none;'>";
				content+="<input  type='text' size='10' id='tipTime' name='tipTime' value='"+getTimeHHMM2()+"'  onclick=\"WdatePicker({dateFmt:'HH:mm'});\" />";
			}
			content+="</td></tr></table>"
			+"</td>"
			+"</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_odd'>"
			+"<td align='left' width='20%'  class='td_align_right'>"+global.tipMethod+"</td>"
			+"<td class='td_align_left'>";
			
//			+"<select id='tipMethod' name='tipMethod' value='"+tipMethod+"'>";
			if(tipMethod=='1'){
//				content+="<option value='1' selected='selected'>"+global.tipMethod1+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="1" checked="checked"/>'+global.tipMethod1;
			}else{
//				content+="<option value='1'>"+global.tipMethod1+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="1"/>'+global.tipMethod1;
			}
			if(tipMethod=='2'){
//				content+="<option value='2' selected='selected'>"+global.tipMethod2+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="2" checked="checked"/>'+global.tipMethod2;
			}else{
//				content+="<option value='2'>"+global.tipMethod2+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="2"/>'+global.tipMethod2;
			}
			if(tipMethod=='3'){
//				content+="<option value='3' selected='selected'>"+global.tipMethod3+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="3" checked="checked"/>'+global.tipMethod3;
			}else{
//				content+="<option value='3'>"+global.tipMethod3+"</option>";
				content+='<input id="tipMethod" class="tipMethod" name="tipMethod" type="radio" value="3"/>'+global.tipMethod3;
			}
//			content+="</select>"
				
			content+="</td>"
			+"</tr>"
			+"<tr class='add_schedule_table_tr add_schedule_table_even'>"
			+"<td align='left' width='20%'  class='td_align_right'>"+global.repeat+"</td>"
			+"<td class='td_align_left'>"
			+"<select id='repeat' name='repeat' value='"+repeat+"' >";
			if(repeat=='1'){
				content+="<option value='1' selected='selected'>"+global.repeat1+"</option>";
			}else{
				content+="<option value='1'>"+global.repeat1+"</option>";
			}
			if(repeat=='2'){
				content+="<option value='2' selected='selected'>"+global.repeat2+"</option>";
			}else{
				content+="<option value='2'>"+global.repeat2+"</option>";
			}
			if(repeat=='3'){
				content+="<option value='3' selected='selected'>"+global.repeat3+"</option>";
			}else{
				content+="<option value='3'>"+global.repeat3+"</option>";
			}
			if(repeat=='4'){
				content+="<option value='4' selected='selected'>"+global.repeat4+"</option>";
			}else{
				content+="<option value='4'>"+global.repeat4+"</option>";
			}
			if(repeat=='5'){
				content+="<option value='5' selected='selected'>"+global.repeat5+"</option>";
			}else{
				content+="<option value='5'>"+global.repeat5+"</option>";
			}
			if(repeat=='6'){
				content+="<option value='6' selected='selected'>"+global.repeat6+"</option>";
			}else{
				content+="<option value='6'>"+global.repeat6+"</option>";
			}
			content+="</select>"
			+"</td>"
			+"</tr>";
//			+"<tr>"
//			+"<td  colspan='2' align='center' style='text-align: center;'>";
//			if(editsche==true&&isComplete!='1'){
//				content+="<button  id='editsche'   onclick='invalid();'>"+global.editBtn+"</button>";
//			}
//			if(isComplete!='1'){
//				content+="<button id='addsche'  onclick=\"addSchedule('"+status+"','"+ldate+"','"+rel+"','"+mtype +"','"+ctype+"','"+groupid+"');\">"+global.confirm+"</button>";
//			}
//			if(delsche==true&&isComplete!='1'){
//				content+="<button id='delsche'  onclick=\"deleteSchedule('"+status+"','"+ldate+"','"+rel+"','"+mtype +"','"+ctype+"','"+groupid+"');\">"+global.deleteBtn+"</button>";
//			}
//			if(compsche==true&&isComplete!='1'){
//			content+="<button id='compsche'  onclick=\"completeSaveSchedule('"+status+"','"+ldate+"','"+rel+"','"+mtype +"','"+ctype+"','"+groupid+"');\">"+global.completeBtn+"</button>";
//			}
//			content+="<button onclick='closeDialog();' class='btn_cancel'>"+global.cancel+"</button></td></tr>" ;
			content+="</table>"
			+"</div>";
			return content;
	}
	
	
	//日程无法修改
	function unvalid(compsche,editsche,delsche,isComplete){
		$('#scheduleName').attr("disabled",true);
		$('#address').attr("disabled",true);
		$('#startDate').attr("disabled",true);
		$("#quan").attr("disabled",true);
		$("#jieshu").attr("disabled",true);
		$('#startTime').attr("disabled",true);
		//$('#startTime2').attr("disabled",true);
		$('#endDate').attr("disabled",true);
		$('#endTime').attr("disabled",true);
		//$('#endTime2').attr("disabled",true);
		$('#creators').attr("disabled",true);
		//$('#creatorIds').val(creatorIds);

		$(".isView").attr("disabled",true);
		$(".tipMethod").attr("disabled",true);
		
		$(".selectcolor").attr("disabled",true);
		
		
		$('#status').attr("disabled",true);
		
		$('#leaderNames').attr("disabled",true);
		//$('#leaderIds').val(leaderIds);
		
		$('#pleases').attr("disabled",true);
		//$('#pleaseIds').val(pleaseIds);
		$('#views').attr("disabled",true);
		//$('#viewIds').val(viewIds);
		$("#tip").attr("disabled",true);
		
		$("#tipDate").attr("disabled",true);
		$("#tipTime").attr("disabled",true);
		//$("#tipTime2").attr("disabled",true);
		if(editsche==false||isComplete=='1'){
			$(".ui-dialog-buttonset button").each(function(){
				if($(this).text()=="编辑"){
					$(this).attr("display",'none');
				}
			});
		}
		//if(isComplete=='1'){
			$(".ui-dialog-buttonset button").each(function(){
				if($(this).text()=="确定"){
					$(this).attr("display",'none');
				}
			});
		//}
		if(delsche==false||isComplete=='1'){
			$(".ui-dialog-buttonset button").each(function(){
				if($(this).text()=="删除"){
					$(this).attr("display",'none');
				}
			});
		}
		if(compsche==false||isComplete=='1'){
			$(".ui-dialog-buttonset button").each(function(){
				if($(this).text()=="完成"){
					$(this).attr("display",'none');
				}
			});
		}
		//$("#addsche").attr("disabled",true);
		$("#repeat").attr("disabled",true);
		
	}
	//日程无法修改
	function invalid(){
		var isComplete=$("#isComplete").val();
		$('#scheduleName').attr("disabled",false);
		$('#address').attr("disabled",false);
		$('#startDate').attr("disabled",false);
		$("#quan").attr("disabled",false);
		$("#jieshu").attr("disabled",false);
		$('#startTime').attr("disabled",false);
		$('#startTime2').attr("disabled",false);
		$('#endDate').attr("disabled",false);
		$('#endTime').attr("disabled",false);
		$('#endTime2').attr("disabled",false);
		$('#creators').attr("disabled",false);
		$('#creators').removeAttr("readonly");
		$('#creators').css("cursor","pointer");
		//$('#creatorIds').val(creatorIds);
		
		$(".isView").attr("disabled",false);
		$(".tipMethod").attr("disabled",false);
		
		$(".selectcolor").attr("disabled",false);
		
		$("#flag").val(2);
		
		//$(".colors").show();
		
		$('#status').attr("disabled",false);
		
		$('#leaderNames').attr("disabled",false);
		$('#leaderNames').removeAttr("readonly");
		$('#leaderNames').css("cursor","pointer");
		//$('#leaderIds').val(leaderIds);
		
		$('#pleases').attr("disabled",false);
		$('#pleases').removeAttr("readonly");
		$('#pleases').css("cursor","pointer");
		//$('#pleaseIds').val(pleaseIds);
		$('#views').attr("disabled",false);
		$('#views').removeAttr("readonly");
		$('#views').css("cursor","pointer");
		//$('#viewIds').val(viewIds);
		$("#tip").attr("disabled",false);
		
		$("#tipDate").attr("disabled",false);
		$("#tipTime").attr("disabled",false);
		$("#tipTime2").attr("disabled",false);
		//$("#addsche").attr("disabled",false);
		$("#repeat").attr("disabled",false);
		if(isComplete!='1'){
		$(".ui-dialog-buttonset button").each(function(){
			if($(this).text()=="确定"){
				$(this).attr("display",'inline');
			}
		});
		}
	}
	
	function openNewSche() {
		$('#creators').removeAttr("readonly");
		$('#creators').css("cursor","pointer");
		
		$('#leaderNames').removeAttr("readonly");
		$('#leaderNames').css("cursor","pointer");
		
		$('#pleases').removeAttr("readonly");
		$('#pleases').css("cursor","pointer");
		
		$('#views').removeAttr("readonly");
		$('#views').css("cursor","pointer");
	}
	
	//跳转至按日日程显示 
	function to(stype,mtype) {
		var addCss = $("#addCss").val();
		var ldate = $("#ldate").val();
		alert(contextPath+'/schedule/person_schedule2.action?ldate='
				+ ldate + '&stype=' + stype+"&mtype="+mtype+"&requestType=cms");
		if(addCss='yes'){
			window.location.href = contextPath+'/schedule/person_schedule2.action?ldate='
			+ ldate + '&stype=' + stype+"&mtype="+mtype+"&requestType=cms";
		}else{
			window.location.href = contextPath+'/schedule/person_schedule2.action?ldate='
			+ ldate + '&stype=' + stype+"&mtype="+mtype;
		}
		
	}
	//
	function to3(stype) {
		var tr = $("tr");
		$("#mytb tr").each(function() {

		});
	}

	
	
	
	
	
	
	
	
	
	
	
	

//鼠标经过显示新建
	function showorno(userno,cday,ctype){
		if(ctype==1){
			$("#"+userno+cday).show();
		}
		if(ctype==2){
			$("#"+userno+cday).hide();
		}
	}
	
	
	
	
	
	
	//日程时间范围变更
	function changeTime(ctype){
		if(ctype==1){
			$('#startTime').val("08:00");
			$('#endTime').val("17:30");
		}
		if(ctype==2){
			$('#startTime').val("08:00");
			$('#endTime').val("11:30");
		}
		if(ctype==3){
			$('#startTime').val("13:30");
			$('#endTime').val("17:30");
		}
		if(ctype==4){
			$('#startTime').val("18:00");
			$('#endTime').val("21:00");
		}
		
	}

	//弹出秘书领导窗口
	function openLayer33(objId,conId,secUuid ,secUserNo ,secUserName ,leaderUserNos ,leaderUserNames){
		openLayer3(objId,conId);
		$("#secUuid").val(secUuid);
		$("#secUserNo").val(secUserNo);
		$("#secUserName").val(secUserName);
		$("#leaderUserNames").val(leaderUserNames);
		$("#leaderUserNos").val(leaderUserNos);
		$("#delssec").show();
		}
	
		
		
	
	
	
	
	
	
	//选择用户
	function openUser(lname,lid){

		$.unit.open({
			 	labelField : lname,
				valueField : lid,
				selectType : 4
		});

		}
	
	//获得当前小时分钟
	function getTime()
	{
		var ERP_TIME = new Date();
		var intHours, intMinutes;
		var timestr="";
		
		intHours = ERP_TIME.getHours();
		intMinutes = ERP_TIME.getMinutes();
		
		if (intHours < 12) {
			timestr =timestr+ intHours+":";
		} else {
			intHours = intHours - 12;
			timestr =timestr+ intHours + ":";
		}
		if (intMinutes < 10) {
			timestr =timestr+ "0"+intMinutes;
		} else {
			timestr =timestr+ intMinutes;
		}
		return timestr;
	}
	
	
	
	
	
			//获得当前小时分钟
			function getTimeH()
			{
				var ERP_TIME = new Date();
				var intHours;
				
				intHours = ERP_TIME.getHours();
				
				
				return intHours;
			}
			
			
			//获得当前小时分钟
			function getTimeM()
			{
				var ERP_TIME = new Date();
				var  intMinutes;
				var timestr="";
				

				intMinutes = ERP_TIME.getMinutes();
				if(intMinutes<15&&intMinutes>=5){
					intMinutes=10;
				}
				if(intMinutes<25&&intMinutes>=15){
					intMinutes=20;
				}
				if(intMinutes<35&&intMinutes>=25){
					intMinutes=30;
				}
				if(intMinutes<45&&intMinutes>=35){
					intMinutes=40;
				}
				if(intMinutes<55&&intMinutes>=45){
					intMinutes=50;
				}
				if((intMinutes<=60&&intMinutes>=55)||(intMinutes<5&&intMinutes>=0)){
					intMinutes=0;
				}
				if (intMinutes < 10) {
					timestr =timestr+ "0"+intMinutes;
				} else {
					timestr =timestr+ intMinutes;
				}
				return timestr;
			}
			
			
			
			function tipChange(){
				var tip=$("#tip").val();
				if(tip=='1'){
					$("#dtipDate").hide();
					$("#dtipTime").hide();
				}
				if(tip=='2'){
					$("#dtipDate").hide();
					$("#dtipTime").show();
					if($("#tipTime").val()==''){
						$("#tipTime").val(getTimeH());
						$("#tipTime2").val(getTimeM());
						}
				}
				if(tip=='3'){
					$("#dtipDate").hide();
					$("#dtipTime").show();
					if($("#tipTime").val()==''){
						$("#tipTime").val(getTimeH());
						$("#tipTime2").val(getTimeM());
						}
				}
				if(tip=='4'){
					$("#dtipDate").show();
					$("#dtipTime").show();
					if($("#tipTime").val()==''){
					$("#tipTime").val(getTimeH());
					$("#tipTime2").val(getTimeM());
					}
				}
			}


			function quantian(){
				if($("#quan").attr("checked")=='checked'){
					$("#dstartTime").hide();
					$("#dendTime").hide();
				}else{
					$("#dstartTime").show();
					$("#dendTime").show();
					if($("#startTime").val()==''){
					$("#startTime").val(getTimeH());
					$("#startTime2").val(getTimeM());
					}
					if($("#endTime").val()==''){
						$("#endTime").val(getTimeH());
						$("#endTime2").val(getTimeM());
						}
				}
			}
			function jieshu(){
				if($("#jieshu").attr("checked")=='checked'){
					$("#trjieshu").show();
					if($("#endDate").val()==''){
						$("#endDate").val($("#startDate").val());
					}
					quantian();
				}else{
					$("#trjieshu").hide();
					quantian();
				}
			}
			
			function selColor(c){
				$("#mycolor").val(c);
				$("#showcolor").css("background-color",c);
			}
			
			
			function showView(obj){
				if($(obj).val()=='1'){
					$("#showview").show();
				}else{
					$("#showview").hide();
				}
			}
		
//群组
			function openLayerGroupSet(ldate,groupid,stype){
				var content=getGroupHtml('','','','',ldate,groupid,stype,false);
//				var title=global.addGroup;
				var title="群组-名称";
				var json = 
				{
					title:title,  /*****标题******/
					autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
					modal: true,     /*****是否模式对话框******/
					closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
					draggable: true, /*****是否允许拖动******/  
					resizable: true, /*****是否可以调整对话框的大小******/  
					stack : false,   /*****对话框是否叠在其他对话框之上******/
					height: 250, /*****标题******/
					width: 600,   /*****标题******/
					content: content,/*****内容******/
					//open：事件,
					buttons: {
						"确定":addGroup
						//"取消": 事件2
					}
				};

				showDialog(json);
				//showScheduleDialog(global.addGroup,content,300,500);
			}
			function openLayerGroupSet2(uuid,groupName,userNames,userIds,ldate,groupid,stype){
				var content=getGroupHtml(uuid,groupName,userNames,userIds,ldate,groupid,stype,true);
//				var title=global.addGroup;
				var title="群组-名称";
				var json = 
				{
					title:title,  /*****标题******/
					autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
					modal: true,     /*****是否模式对话框******/
					closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
					draggable: true, /*****是否允许拖动******/  
					resizable: true, /*****是否可以调整对话框的大小******/  
					stack : false,   /*****对话框是否叠在其他对话框之上******/
					height: 250, /*****标题******/
					width: 600,   /*****标题******/
					content: content,/*****内容******/
					//open：事件,
					buttons: {
						"确定":addGroup
						//"删除":deleteGroup
						//"取消": 事件2
					}
				};

				showDialog(json);
				//showScheduleDialog(global.viewGroup,content,300,500);
			}
			function getGroupHtml(uuid,groupName,userNames,userIds,ldate,groupid,stype,delsche){
				var content="<div id='test_con3'>"
					+"<input type='hidden' id='uuid' name='uuid' value='"+uuid+"'/>"
					+"<input type='hidden' id='stype' name='stype' value='"+stype+"'/>"
					+"<input type='hidden' id='ldate' name='ldate' value='"+ldate+"'/>"
					+"<input type='hidden' id='groupid' name='groupid' value='"+groupid+"'/>"
					+"<table>"
					+"<tr class='tr_odd'>"
					+"<td align='left' width='20%' class='left' style='text-indent: 15px;'>"+global.gourpName+"</td>"
					+"<td><input type='text' name='groupName' value='"+groupName+"' id='groupName' size='50' /></td>"
					+"</tr>"
					+"<tr class='tr_even'>"
					+"<td align='left' width='20%'  style='text-indent: 15px;'>"+global.chengyuan+"</td>"
					+"<td>"
					+"<input  type='hidden' value='"+userIds+"' name='userIds' id='userIds'/>"
					+"<textarea style='cursor: pointer;background:#FFFFFF;margin: 5px; width: 90%;'  readonly='readonly' id='userNames' name='userNames' onclick=\"openUser('userNames','userIds');\">"+userNames+"</textarea>";
//					+"<input type='text' style='cursor: pointer;' value='"+userNames+"' readonly='readonly' name='userNames' id='userNames' size='50'  onclick=\"openUser('userNames','userIds');\"/>"
					+"</td>"
					+"</tr>"
					+"</table>"
//					+"<div align='center'>"
//					+"<table><tr><td class='group_set_btn'>"
//					+"<button onclick='addGroup();'>"+global.confirm+"</button>";
//					if(delsche==true){
//					content+="<button id='delsche' onclick='deleteGroup();'>"+global.deleteBtn+"</button>";
//					}else{
//					content+="<button id='delsche' style='display:none' onclick='deleteGroup();'>"+global.deleteBtn+"</button>";
//					}
//					content+="<button onclick='closeDialog();'>"+global.cancel+"</button></td><td>"
//					+"</td></tr></table>"
//					+"</div>"
					+"</div>";
					return content;
			}
			
			//删除群组
			function deleteGroup(){
				var uuid=$("#uuid").val();
					if(confirm(global.deleteConfirm)) {
							$.ajax({
								type : "POST",
								url : contextPath+"/schedule/group_delete.action",
								data : "uuid="+uuid,
								dataType : "text",
								success : function callback(result) {
									closeDialog();
									window.location.href=contextPath+"/schedule/schedule_setlist.action";
								}
							});
					}
				
			}
			//删除群组
			function deleteGroup2(uuid,ldate,groupid,type){
					if(confirm(global.deleteConfirm)) {
							$.ajax({
								type : "POST",
								url : contextPath+"/schedule/group_delete.action",
								data : "uuid="+uuid,
								dataType : "text",
								success : function callback(result) {
									var addCss = $("#addCss").val();
									if(addCss=="yes"){
										setTimeout(function(){searchWindow();},300);
									}else{
										if(type==1){
											window.location.href=contextPath+'/schedule/group_schedule.action?ldate='+ldate+'&ctype=0&groupid='+groupid;
										}else{
											window.location.href=contextPath+'/schedule/group_schedule.action?ldate='+ldate+'&ctype=1&groupid='+groupid;
										}
									}
									
								}
							});
					}
				
			}

			
			//添加群组
			function addGroup(){
				var uuid=$("#uuid").val();
				var stype=$("#stype").val();
				var ldate=$("#ldate").val();
				var groupid=$("#groupid").val();
				var groupName=$("#groupName").val();
				var userNames=$("#userNames").val();
				var userIds=$("#userIds").val();
				var addCss = $("#addCss").val();
				if(groupName==''){
					alert(global.groupNameNotNull);
					$("#groupName").focus();
					return;
				}
				if(userNames==''){
					alert(global.chengyuanNotNull);
					$("#userNames").focus();
					return;
				}
				//alert("userNo="+userNo+"&userName="+userName+"&groupUserNames="+groupUserNames+"&groupUserNos="+groupUserNos);
							$.ajax({
								type : "POST",
								url : contextPath+"/schedule/group_add.action",
								data : "uuid="+uuid+"&groupName="+groupName+"&userNames="+userNames+"&userIds="+userIds,
								dataType : "text",
								success : function callback(result) {
									closeDialog();
									//刷新窗口
									
									if(addCss=="yes"){
										setTimeout(function(){searchWindow();},300);
//										window.location.href=contextPath+"/pt/cms/index.jsp?uuid=c645ba94-a0d0-48f3-9ef4-a04624b548e8&treeName=SCHEDULE_CATE&mid=20136199117335&moduleid=fc5d811f-8bd1-40cc-a3eb-641498ac320a";
									}else{
										if(stype==1){
											window.location.href=contextPath+'/schedule/group_schedule.action?ldate='+ldate+'&ctype=0&groupid='+groupid;
										}else{
											window.location.href=contextPath+'/schedule/group_schedule.action?ldate='+ldate+'&ctype=1&groupid='+groupid;
										}
										//window.location.href=contextPath+"/schedule/schedule_setlist.action";
									}
								}
							});
				
			}
			
			//秘书
			function openSecNew(){
				var content=getSecHtml('','','','','',false);
				var title=global.addSec;
				var json = 
				{
					title:title,  /*****标题******/
					autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
					modal: true,     /*****是否模式对话框******/
					closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
					draggable: true, /*****是否允许拖动******/  
					resizable: true, /*****是否可以调整对话框的大小******/  
					stack : false,   /*****对话框是否叠在其他对话框之上******/
					height: 600, /*****标题******/
					width: 850,   /*****标题******/
					content: content,/*****内容******/
					//open：事件,
					buttons: {
						"确定":addSec
						//"取消": 事件2
					}
				};

				showDialog(json);
			}
			function openSec(secUuid ,secUserNo ,secUserName,leaderUserNos ,leaderUserNames){
				var content=getSecHtml(secUuid ,secUserName,secUserNo ,leaderUserNames,leaderUserNos ,true);
				var title=global.addSec;
				var json = 
				{
					title:title,  /*****标题******/
					autoOpen: true,  /*****初始化之后，是否立即显示对话框******/
					modal: true,     /*****是否模式对话框******/
					closeOnEscape: true, /*当按 Esc之后，是否应该关闭对话框******/
					draggable: true, /*****是否允许拖动******/  
					resizable: true, /*****是否可以调整对话框的大小******/  
					stack : false,   /*****对话框是否叠在其他对话框之上******/
					height: 600, /*****标题******/
					width: 850,   /*****标题******/
					content: content,/*****内容******/
					//open：事件,
					buttons: {
						"确定":addSec,
						"删除":deleteSec
						//"取消": 事件2
					}
				};

				showDialog(json);
			}
				function getSecHtml(uuid,secUserName,secUserNo,leaderUserNames,leaderUserNos,delssec){
					var content="<div id='test_con33' >"
						+"<input type='hidden' id='secUuid' value='"+uuid+"' />"
						+"<table>"
						+"<tr>"
						+"<td align='left' width='20%'>"+global.sec+":</td>"
						+"<td><input type='text' style='cursor: pointer;' name='secUserName' value='"+secUserName+"' id='secUserName' readonly='readonly' size='50' onclick=\"openUser('secUserName','secUserNo');\" /><input  type='hidden' value='"+secUserNo+"' name='secUserNo' id='secUserNo'/></td>"
						+"</tr>"
						+"<tr>"
						+"<td align='left' width='20%'>"+global.leader+":</td>"
						+"<td><input type='text' style='cursor: pointer;' name='leaderUserNames' value='"+leaderUserNames+"' id='leaderUserNames' readonly='readonly' size='50' onclick=\"openUser('leaderUserNames','leaderUserNos');\" /><input  type='hidden' value='"+leaderUserNos+"' name='leaderUserNos' id='leaderUserNos'/></td>"
						+"</tr>"
						+"</table>"
//						+"<div align='center'>"
//						+"<table><tr><td>"
//						+"<button onclick='addSec();'>"+global.confirm+"</button></td><td>";
//						if(delssec==true){
//						content+="<button id='delssec' onclick='deleteSec();'>"+global.deleteBtn+"</button></td>";
//						}else{
//						content+="<button id='delssec' style='display:none' onclick='deleteSec();'>"+global.deleteBtn+"</button></td>";
//						}
//						content+="<td>"
//						+"<button onclick='closeDialog();'>"+global.cancel+"</button></td><td>"
//						+"</td></tr></table>"
//						+"</div>"
						+"</div>";
						return content;
				}
				
				//删除秘书
				function deleteSec(){
					var secUuid=$("#secUuid").val();
					var secUserNo=$("#secUserNo").val();
					var secUserName=$("#secUserName").val();
					var groupUserNames=$("#leaderUserNames").val();
					var groupUserNos=$("#leaderUserNos").val();
						if(confirm(global.deleteConfirm)) {
								$.ajax({
									type : "POST",
									url : contextPath+"/schedule/leader_delete.action",
									data : "secUuid="+secUuid+"&secUserNo="+secUserNo+"&secUserName="+secUserName+"&leaderUserNames="+groupUserNames+"&leaderUserNos="+groupUserNos,
									dataType : "text",
									success : function callback(result) {
										closeDialog();
										window.location.href=contextPath+"/schedule/schedule_secsetlist.action";
									}
								});
						}
					
				}
				//添加秘书
				function addSec(){
					var secUuid=$("#secUuid").val();
					var secUserNo=$("#secUserNo").val();
					var secUserName=$("#secUserName").val();
					var groupUserNames=$("#leaderUserNames").val();
					var groupUserNos=$("#leaderUserNos").val();
					if(secUserName==''){
						alert(global.secNotNull);
						return false;
					}
					if(groupUserNames==''){
						alert(global.secNotNull);
						return false;
					}
								$.ajax({
									type : "POST",
									url : contextPath+"/schedule/leader_add.action",
									data : "secUuid="+secUuid+"&secUserNo="+secUserNo+"&secUserName="+secUserName+"&leaderUserNames="+groupUserNames+"&leaderUserNos="+groupUserNos,
									dataType : "text",
									success : function callback(result) {
										closeDialog();
										window.location.href=contextPath+"/schedule/schedule_secsetlist.action";
									}
								});
					
				}

		function choseColor(obj){
			if($(obj).find(".colors").css("display")=='none'){
				$(obj).find(".colors").css("display","block");
			}else{
				$(obj).find(".colors").css("display","none");
			}
		}
		//新增方法
		function choseColor1(obj){
			var value= $('#flag').val();
			if(value ==2 || value ==0) {   //当前为编辑(2)或新增(0)日程，需要展示颜色栏
				if($(obj).find(".colors").css("display")=='none'){
					$(obj).find(".colors").css("display","block");
				}else{
					$(obj).find(".colors").css("display","none");
				}
			}
			else if(value ==1)  //当前为查看日程，不要展示 颜色栏
				$(".colors").hide();
			
		}
		//编辑后刷新窗口
		function searchWindow(){
			var moduleid = "";
			if($(".page_index").length>0){
				moduleid = $(".rc").parents(".dnrw").attr("moduleid");
				alert(moduleid);
			}else{
				$(".openchild").each(function(){
					if($(this).attr("class").indexOf("activite")>-1){
						moduleid = $(this).attr("moduleid");
					}
				});
				if(moduleid==""){
					var json_ = readSearch();
					moduleid = json_.moduleid;
				}
			}
			pageLock("show");
			$.ajax({
				type:"post",
				async:false,
				data : {"uuid":moduleid},
				url:ctx+"/cms/cmspage/viewcontent",
				success:function(result){
					$(".schedule_css").parent().html(result);
					pageLock("hide");
				}
			});
		}
