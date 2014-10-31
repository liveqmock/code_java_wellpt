;
(function($) {
	
	/**   
	 * newJsonData:首次调用时传入的参数：
	 * '{"beginTime":"","endTime":"","employName":"","uuid":"",
	 * 	"resCode":"MEET_RESOURCE"}';
	 * 	resCode 必须传入数值,例如:CAR_RESOURCE,MEETING_RESOURCE,DRIVER_RESOURCE
	 * ----------------------------------------------------------------------
	 * 第二次入参时参数：  
	 * 例如：var newJsonData =
	 *  '{"beginTime":"2014-09-02 04:00","endTime":"2014-09-02 06:00",
	 * 	"employName":"大会议室","uuid":"b4e1b21f-1003-45e8-9f41-d8879d6a2127",
	 * 	"resCode":"MEET_RESOURCE"}';
	 * 	resCode 必须传入数值,例如:CAR_RESOURCE,MEETING_RESOURCE,DRIVER_RESOURCE  
	 */ 
	$.fn.myTimeEmploy = function(newJsonData, callback){
		//保存资源类型
		var resourceType = newJsonData.resCode;
		var element = $(this);
		var week = '';
		var timeType = 'today';
		var appointDate = '';
		var type = 'RES_MANAGE';
		var field = 'datetime_begin,datetime_end';
		//var callback = function(jsonData){};
		//var aaa = '{"beginTime":"2014-09-02 04:00","endTime":"2014-09-02 06:00","employName":"大会议室","uuid":"b4e1b21f-1003-45e8-9f41-d8879d6a2127","resCode":"MEET_RESOURCE"}';
		//上次点击的时间占用记录
		var newJsonData = newJsonData;
		//时间资源数据
		var tmList;
		/**
		 * $(this).myTimeEmploy($(this),'','today','','RES_MANAGE_DETAIL','datetime_begin,datetime_end','CAR_RESOURCE',callback);
		 * 参数说明： element:调用方法的元素
		 * week: 第几周,默认传入 ''
		 * timeType：指定日期（默认为今天），例如： 'today'
		 * appointDate：默认传入 ' ' 即可
		 * type: 动态表单名称，默认传入  'RES_MANAGE_DETAIL'
		 * field:点击时，把起止时间回填到相应字段，例如字段：'datetime_begin,datetime_end'
		 * resourceType:资源类别  例如：CAR_RESOURCE
		 * callback: 回调函数(function) 
		 */
		getRecords(element,week,timeType,appointDate,type,field,resourceType,callback,newJsonData);
		//*
		//计算出占用的时间块(具体到半个小时)
		//*
		function getTimes(begintime,endtime) {
			var begintimes =  begintime.split(" ");
			var bts = begintimes[1].split(":");
			var time1 = bts[1];
			var endtimes =  endtime.split(" ");
			var eds = endtimes[1].split(":");
			var time2 = eds[1];
			
			var a=0,b=0;
			if(parseInt(time1) >=30) {
				a = parseInt(bts[0]) * 2 + parseInt(2);
			}else {
				a = parseInt(bts[0]) * 2 + parseInt(1);
			}
			
			if(parseInt(time2) >=30) {
				b = parseInt(eds[0]) * 2 + parseInt(2);
			}else {
				b = parseInt(eds[0]) * 2 + parseInt(1);
			}
			
			var strs = "";
			var c = parseInt(b) - parseInt(a);
			for(var index = 0;index<c;index++) {
				var x = parseInt(a) + parseInt(index);
				strs += ","+ x;
			}
			return strs.substring(1);
			}
			
		//**
		 // 计算出占用的时间块(具体到分钟)
		 //*
		function getTime2s(begintime,endtime) {
			var begintimes =  begintime.split(" ");
			var bts = begintimes[1].split(":");
			var time1 = bts[1];
			var endtimes =  endtime.split(" ");
			var eds = endtimes[1].split(":");
			var time2 = eds[1];
			
			var a=0,b=0,A=0,B=0;
			if(parseInt(time1) == 00) {
				a = parseInt(bts[0]) * 2 + parseInt(1);
				A = parseInt(30);
			}else if(parseInt(time1) == 30){
				a = parseInt(bts[0]) * 2 + parseInt(2);
				A = parseInt(30);
			}else {
				a = parseInt(bts[0]) * 2 + parseInt(1);
				A = parseInt(30) - parseInt(time1);
			}
			
			if(parseInt(time2) == 00) {
				b = parseInt(eds[0]) * 2 + parseInt(1);
				B = parseInt(30);
			}else if(parseInt(time2) == 30) {
				b = parseInt(eds[0]) * 2 + 2;
				B = parseInt(30);
			}else {
				b = parseInt(eds[0]) * 2 + parseInt(1);
				B = parseInt(30) - parseInt(time2);
			}
			
			var strs = "";
			var c = parseInt(b) - parseInt(a);
			for(var index = 0;index<c;index++) {
				var x = parseInt(a) + parseInt(index);
				if(index == 0) {
					strs += ","+ x+"_"+A;
				}else if(parseInt(index) < (parseInt(c)-parseInt(1)) && index>0 ) {
					strs += ","+ x + "_" + "30";
				}else if(parseInt(index) == (parseInt(c)-parseInt(1))) {
					strs += ","+x + "_" + B;
				}
				
			}
			return strs.substring(1);
		}
		
		//日期输入框调用日期控件
		$("#s_today").live("focus",function(){
			var resourceName = $(this).attr("resourceName");
			var field = $(this).attr("field");
			WdatePicker({startDate:'%y-%M-01 00:00:00',
				dateFmt:"yyyy-MM-dd",alwaysUseStartDate:false,
				onpicking:function(dp){
					getRecords($("#s_today"),"","notoday",dp.cal.getNewDateStr(),resourceName,field,resourceType,callback,newJsonData);
				}
			});
		});
		
		
		
		$("#prevWeek").live("click",function(){
			var week = $(this).attr("week");
			var resourceName = $(this).attr("resourceName");
			var field = $(this).attr("field");
			getRecords($(this),week,"notoday","",resourceName,field,resourceType,callback,newJsonData);
		});
		
		$("#nextWeek").live("click",function(){
			var week = $(this).attr("week");
			var resourceName = $(this).attr("resourceName");
			var field = $(this).attr("field");
			getRecords($(this),week,"notoday","",resourceName,field,resourceType,callback,newJsonData);
		});
		//$(this),'','today','','RES_MANAGE_DETAIL','datetime_begin,datetime_end','MEET_RESOURCE',callback	
		
		
		function getRecords(element,week,timeType,appointDate,type,field,resourceType,callback,newJsonData){
			var url = "";
			if(resourceType == "MEET_RESOURCE") {
				url = ctx + '/calendar/calendar_show_new.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type + '&resourceType='+ resourceType;
			}else if(resourceType == "CAR_RESOURCE") {
				url = ctx + '/calendar/calendar_show_new.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type + '&resourceType='+ resourceType;
			}else if(resourceType == "DRIVER_RESOURCE") {
				url = ctx + '/calendar/calendar_show_new.action?weekCount='+week+'&appointDate='+appointDate+'&resourceName='+type + '&resourceType='+ resourceType;
			}
			var x = new Array;
			x = ["changeDiv","changeDiv1","changeDiv2","changeDiv3","changeDiv4","changeDiv5","changeDiv6","changeDiv7","changeDiv8","changeDiv9"];
			$.ajax({
				type : "post",
				url : url,
				success : function(data) {
					var html = "";
					
					//var dataDictionaryAttr = data["dataDictionaryAttr"];
					//var begin_time_and_name = dataDictionaryAttr[0].split("-");
					//var end_time_and_name = dataDictionaryAttr[1].split("-");
					//数据字典配置的属性
					//var begin_time =  end_time_and_name[1];
					//var end_time =  begin_time_and_name[1];
					//var begin_time_part = begin_time.split(":")[0];
					//var end_time_part = end_time.split(":")[0];
					
					var weekCount = data["weekCount"];
					var today = data["today"];//今天的日期
					var weekList = data["weekList"];//	["星期一", "2014-07-21"] ["星期二", "2014-07-22"] ["星期三", "2014-07-23"]
					var weekSplitBg = weekList[0][1].split("-");
					var weekBg = weekSplitBg[1] + "月" + weekSplitBg[2] + "日";//一周的周一的日期
					var weekSplitEd = weekList[6][1].split("-");
					var weekEd = weekSplitEd[1] + "月" + weekSplitEd[2] + "日";//一周的周日的日期
					//显示资源的日期等待
					html += '<div class="toolbar">';
					html += '<table width="100%">';
					html += '<tr>';
					html += '<td align="left" width="300">';
					html += '<a class="s_prev_day" href="#" mtype="0" id="prevWeek" resourceName ="'+type+'"  field="'+field+'"  week="'+(weekCount-parseInt(1))+'"><</a>';
					html += '<input  type="text"  resourceName ="'+type+'"  field="'+field+'" id="s_today" class="s_today" value="'+today+'" size="7">';
					html += '<a class="s_next_day" href="#" mtype="0"  resourceName ="'+type+'" field="'+field+'" id="nextWeek" week="'+(parseInt(weekCount)+parseInt(1))+'">></a>';
					html += '<span class="fromandto">'+weekBg+'至'+weekEd+'</span>';
					html += '</td >';
					html += '<td align="right">';
					html += '<input id="clear" type="button" style="float:right;font-size: 14px;color:#0f599c; height:22px; margin-right:24px; padding:0 5px;border:1px solid #dee1e2; font-family:Microsoft YaHei" value="清空"/>';
					html += '</td>';
					html += '</tr>';
					html += '</table>';
					html +=	'</div>';
					
					/*
					html += '<div class="toolbar"><table><tr><td align="left"><a class="s_prev_day" href="#" mtype="0" id="prevWeek" resourceName ="'+type+'"  field="'+'"  week="'+(weekCount-parseInt(1))+'"><</a><input  type="text"  resourceName ="'+type+'"  field="'+'" id="s_today" class="s_today" value="'+today+'" size="7"><a class="s_next_day" href="#" mtype="0"  resourceName ="'+type+'" field="'+'" id="nextWeek" week="'+(parseInt(weekCount)+parseInt(1))+'">></a><span class="fromandto">'+weekBg+'至'+weekEd+'</span></td><td></td></tr></table></div>';
					*/
					var queryItem = data["queryItem"];//相关资源Item 例如：汽车资源：宝马和劳斯莱斯
					tmList = data["tmList"];	  //时间占用资源Item
					if(newJsonData.beginTime != ""){
						tmList.push(newJsonData);
					}
					for(var i=0;i<weekList.length;i++) {
						var date = weekList[i][1]; //值：2014-07-21
						var dates = date.split("-"); //分割后：["2014", "07", "21"]
						var dateNew = dates[1]+"月"+dates[2]+"日"; //07月21日
						html += "<table class='weekList_table'>";
						if(i%2 != 0) {
							html += "<tr><td class='calendar_weekList_2'><div class='weekDay'>"+weekList[i][0]+"</div><div class='weedDay2'>"+dateNew+"</div></td>";
						}else {
							html += "<tr><td class='calendar_weekList'><div class='weekDay'>"+weekList[i][0]+"</div><div class='weedDay2'>"+dateNew+"</div></td>";
						}
						if(resourceType == "MEET_RESOURCE") {
							html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>会议室</span></td>";
						}else if(resourceType == "CAR_RESOURCE") {
							html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>车辆类型</span></td>";
						}else if(resourceType == "DRIVER_RESOURCE") {
							html += "<td class='calendar_rightTd'><table class='title_table'><tr class='calendar_table'><td style='width:80px' class='title_td' ><span class='title_text'>司机</span></td>";
						}
						//循环出时间条：0:00  2:00  4:00  6:00
						var htmlArr = new Array();
						for(var index=1;index<49;index++) {
							var str = "";
							if(parseInt(index)<17) {
								if(index%4 == 0 ){
										str = index/2-2+":00";
										
										if(str == "")
										var starstr = (index/2)*2-3;
										var endstr = (index/2)*2;
										html += "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
										/*var tempHtml = "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>"
										htmlArr.push(tempHtml);*/
								}
							}else if(parseInt(index)>=17 && parseInt(index)<=37) {
								if(index%2 == 0 ){
									str = index/2-1 +":00";
									var starstr = index-1;
									var endstr = index;
									html += "<td colspan=2 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
									/*var tempHtml = "<td colspan=2 timestamp='"+starstr+","+endstr+"'>"+str+"</td>"
									htmlArr.push(tempHtml);*/
								}
							}else if(parseInt(index)>37 && parseInt(index)<=49) {
								if((index-parseInt(2))%4 == 0 ){
										var starstr = index-1;
										var endstr = index+2;
										str = (index-parseInt(2))/2-2+parseInt(2)+":00";
											html += "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>";
											/*var tempHtml = "<td colspan=4 timestamp='"+starstr+","+endstr+"'>"+str+"</td>"
											htmlArr.push(tempHtml);*/
								}
							}
						}
						/*var htmlArrFilter = new Array();
						for(var i=parseInt(begin_time_part)/2;i<=parseInt(end_time_part)/2;i++){
							alert(i);
							htmlArrFilter.push(htmlArr[i]);
						}
						html += htmlArrFilter.toString();
						alert(htmlArrFilter.toString());*/
						html += "</tr>";
						for(var j=0;j<queryItem.length;j++) {
								x.sort(function(){return Math.random()-0.5});
								if(resourceType == "MEET_RESOURCE") {
									html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
								}else if(resourceType == "CAR_RESOURCE") {
									html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
								}else if(resourceType == "DRIVER_RESOURCE") {
									html += "<tr><td class='basic' width='80px' title='"+queryItem[j].title+"'><div class='autocut'>"+queryItem[j].title+"</div></td>";
								}
								window.flag2 = "default";
								//开始循环时间占用的记录
								for(var k=1;k<49;k++) {
									var flag = 0;
									var k2 = ","+k + ",";
									for(var l=0;l<tmList.length;l++) {
										var xNew = x[l%(tmList.length)];
										var borderColor = "";
										if(xNew == "changeDiv") {
											borderColor = "#FD6F00";
										}else if(xNew == "changeDiv1") {
											borderColor = "#0B78AD";
										}else if(xNew == "changeDiv2") {
											borderColor = "#D2CB80";
										}else if(xNew == "changeDiv3") {
											borderColor = "#AE9EA6";
										}else if(xNew == "changeDiv4") {
											borderColor = "#3E9448";
										}else if(xNew == "changeDiv5") {
											borderColor = "#89A7B6";
										}else if(xNew == "changeDiv6") {
											borderColor = "#946DD5";
										}else if(xNew == "changeDiv7") {
											borderColor = "#D56D79";
										}else if(xNew == "changeDiv8") {
											borderColor = "#4D3235";
										}else if(xNew == "changeDiv9") {
											borderColor = "#E1C8B9";
										}
										var dateBg = date+" 00:00"; //日期的开始时间：2014-07-21 00:00
										var dateEnd = date+" 23:59"; //日期的结束时间：2014-07-21 23:59
										var dateBgDate = new Date(dates[0],dates[1],dates[2],00,00); //实例化日期
										var dateEndDate = new Date(dates[0],dates[1],dates[2],23,59);//实例化日期
										
//										alert(dateBgDate+"$$$"+dateEndDate);
										var flowUuid = tmList[l].flowUuid;
										var taskUuid = tmList[l].taskUuid
										var tmBg = tmList[l].beginTime;  //时间占用Item的开始时间： 2014-07-22 02:00:00
										var tmBgs = tmBg.split(" ");     //分割时间占用 ：["2014-07-22", "02:00:00"]
										var tmBgs2 = tmBgs[0].split("-"); // 继续分割 ：["2014", "07", "22"]
										var tmBgs3 = tmBgs[1].split(":"); //继续分割：["02", "00", "00"]
										var tmBgDate = new Date(tmBgs2[0],tmBgs2[1],tmBgs2[2],tmBgs3[0],tmBgs3[1]);//实例化
										
										var tmEnd = tmList[l].endTime;      //"2014-07-22 04:00:00"
										var tmEnds = tmEnd.split(" ");      //["2014-07-22", "04:00:00"]
										var tmEnds2 = tmEnds[0].split("-"); //["2014", "07", "22"]
										var tmEnds3 = tmEnds[1].split(":"); //["04", "00", "00"]
										var tmEndDate = new Date(tmEnds2[0],tmEnds2[1],tmEnds2[2],tmEnds3[0],tmEnds3[1]);
//										alert(tmBgDate+"###"+tmEndDate);
										//对tmBg..做时间格式化处理？
										var time = "";
										var time2 = "";
//										alert("dateBgDate " + dateBgDate + ";" + " dateEndDate " + dateEndDate);
//										alert("tmBgDate " + tmBgDate + ";" + " tmEndDate " +tmEndDate);
										if(dateBgDate<tmBgDate && dateEndDate< tmBgDate)  {//当天时间在占用时间记录段的左侧
											//无处理
										}else if(dateBgDate>tmEndDate && dateEndDate>tmEndDate) {//当天时间在占用时间记录段的右侧
											//无处理
											//2014-07-21 00:00<=2014-07-22 02:00:00
										}else if(dateBgDate<=tmBgDate && dateEndDate>=tmBgDate && dateEndDate<=tmEndDate) {
											//tmBg-dateEnd;
											time = getTimes(tmBg,dateEnd);
											time2 = getTime2s(tmBg,dateEnd);
										}else if(dateBgDate<tmBgDate && dateEndDate>tmEndDate) {
//											var time = tmBg-tmEnd;
											time = getTimes(tmBg,tmEnd);
											time2 = getTime2s(tmBg,tmEnd);
										}else if(dateBgDate>=tmBgDate && dateEndDate<=tmEndDate) {
//											var time = dateBg-dateEnd
											time = getTimes(dateBg,dateEnd);
											time2 = getTime2s(dateBg,dateEnd);
										}else if(dateBgDate>=tmBgDate && dateEndDate>tmEndDate) {
//											var time = dateBg-tmEnd;
											time = getTimes(dateBg,tmEnd);
											time2 = getTime2s(dateBg,tmEnd);
										}
										if(time != "") {
										
										time = ","+time+",";
//										alert("time " +time);
//										var time2 = tmList[l].time2;
										var times2 = time2.split(",");
										var beginTime = tmList[l].beginTime;
										var begintime = beginTime.split(" ");
										var endTime = tmList[l].endTime;
										var endtime = endTime.split(" ");
										var employWork = tmList[l].employWork;
										 var employName = tmList[l].employName;
//										 alert("employWork " + employWork + " employName " + employName);
										var title = employWork + " " + begintime[1] + "-" + endtime[1];
										for(var m=0;m<times2.length;m++) {
											var kAdd = parseInt(k)+parseInt(1);
											var kAdd2 = parseInt(k)+parseInt(3);
											var time2_0 = times2[m].split("_")[0];
//											alert(tmList[l].employWork+"$$$"+queryItem[j].title);
											if(time2_0 == k && tmList[l].employName == queryItem[j].title) {
												if(k>17 && k<=36) {
													if(k%2 == 1) {
														var time2_1 =  times2[m].split("_")[1];//30
														if(m == 0) {
															if(time.indexOf(k2)>=0) {
																	html +="<td colspan='2' class='basic change1 left'"+k+"' right'"+kAdd+"'' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																	flag = 1;
																	window.flag2 = "odd";
																}
														}else if(m == (times2.length-1)) {
															if(time.indexOf(k2)>=0) {
																	html += "<td colspan='2' class='basic change1 left' style='border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																	flag = 1;
																	window.flag2 = "odd";
																}
														}
														else {
															if(time.indexOf(k2)>=0) {
																	html += "<td colspan='2' class='basic change1' style='border-right:1px solid "+borderColor+";border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd+"'><div class='basicDiv' style='width:50%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																	flag = 1;
																}
														}
													}else {
														var time2_1 =  times2[m].split("_")[1];//30
														if(m == 0) {
															if(time.indexOf(k2)>=0) {
																html = html.substring(0,html.length-7);
																html += " change1 right' style='border-right:1px solid "+borderColor+"' title='"+title+"'><div class='basicDiv' style='width:50%;float: right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																	flag = 1;
																}
														}else if(m == (times2.length-1)) {
															if(time.indexOf(k2)>=0) {
																html.replace("left"+k,"left");
																html.replace("right"+kAdd,"right");
																html = html.substring(0,html.length-5);
																html += "<div class='basicDiv' style='width:50%;float: right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																flag = 1;
																window.flag2 = "default";
															}
														}else {
															if(time.indexOf(k2)>=0) {
																html = html.substring(0,html.length-5);
																html += "<div class='basicDiv' style='width:50%;float:right;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																flag = 1;
																window.flag2 = "default";
															}
														}
													}
												}else{
													if(k%4 == 1) {
														var time2_1 =  times2[m].split("_")[1];//30
														if(m == 0) {
															if(time.indexOf(k2)>=0) {
																	html +="<td colspan='4' class='basic change1 left'"+k+"' right'"+kAdd2+"''' title='"+title+"' timestamp='"+k+","+kAdd2+"'><div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																	flag = 1;
																	window.flag2 = "odd";
																}
														}else if(m == (times2.length-1)) {
															if(time.indexOf(k2)>=0) {
																	html += "<td colspan='4' class='basic change1 left' style='border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd2+"'><div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																	flag = 1;
																	window.flag2 = "odd";
																}
														}
														else {
															if(time.indexOf(k2)>=0) {
																	html += "<td colspan='4' class='basic change1 middle' style='border-right:1px solid "+borderColor+";border-left:1px solid "+borderColor+"' title='"+title+"' timestamp='"+k+","+kAdd2+"'>" +
																			"<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																	flag = 1;
																}
														}
													}
													else{
														
														var time2_1 =  times2[m].split("_")[1];//30
														if(m == 0) {
															if(time.indexOf(k2)>=0) {
																html = html.substring(0,html.length-5);
																html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:right;'></div></div></td>";
																	flag = 1;
																}
														}else if(m == (times2.length-1)) {
															if(time.indexOf(k2)>=0) {
																html.replace("left"+k,"left");
																html.replace("right"+kAdd,"right");
																html = html.replace("middle","left");
																html = html.substring(0,html.length-5);
																html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float: left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:"+time2_1/30*100+"%;float:left;'></div></div></td>";
																flag = 1;
																window.flag2 = "default";
															}
														}else {
															if(time.indexOf(k2)>=0) {
																html.replace("left"+k,"left");
																html.replace("right"+kAdd2,"right");
																html = html.substring(0,html.length-5);
																html += "<div class='basicDiv ol"+k%4+"' style='width:25%;float:left;'><div flowUuid='"+flowUuid+"' taskUuid='"+taskUuid+"' class='"+xNew+"' title='"+title+"' style='width:100%;'></div></div></td>";
																flag = 1;
																window.flag2 = "default";
															}
														}
													
													}
												}
												
											}
//											
											}	
										}
									}
									if(flag==0){
										if(k<17) {
											if(k%4==1){
												var starstr = (k/2)*2;
												var endstr = (k/2)*2+3;
												html +="<td colspan=4  timestamp='"+starstr+","+endstr+"' date='"+date+ "' uuid='" + queryItem[j].uUID +"' resCode='" + queryItem[j].reservedText4 + "' meetroomName='"+queryItem[j].title+"' class='basic' ><div class='basicDiv o1' style='width:25%;float:left;'></div></td>";
												window.flag2 = "default";
											}else if(k%4 ==2){
												html = html.substring(0,html.length-5);
												html += "<div class='basicDiv o2' style='width:25%;float:left;'></div></td>";
											}else if(k%4 ==3) {
												html = html.substring(0,html.length-5);
												html += "<div class='basicDiv o3' style='width:25%;float:left;'></div></td>";
											}
											else if(k%4 ==0){
												html = html.substring(0,html.length-5);
												html += "<div class='basicDiv o4' style='width:25%;float:left;'></div></td>";
											}
										}else if(k>36) {
											if(k%4==1){
												var starstr = k;
												var endstr = k+3;
												html +="<td colspan=4 timestamp='"+starstr+","+endstr+"' class='basic' date='"+date+ "' uuid='" + queryItem[j].uUID +"' resCode='" + queryItem[j].reservedText4 +"' meetroomName='"+queryItem[j].title+"'></td>";
												window.flag2 = "default";
											}
										}else {
											if(k%2==1){
												var starstr = k;
												var endstr = k+1;
												html +="<td colspan=2 timestamp='"+starstr+","+endstr+"' class='basic' date='"+date+ "' uuid='" + queryItem[j].uUID +"' resCode='" + queryItem[j].reservedText4 +"'  meetroomName='"+queryItem[j].title+"'></td>";
												window.flag2 = "default";
											}
										}
									}
								}
								html += "</tr>";
						}
						html +="</table></td></tr>";
						html +="</table>";
					}
					if(timeType=="today"){
						var json = "";
						if(resourceType == "MEET_RESOURCE") {
							json = {title:"会议室占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
						}else if(resourceType == "CAR_RESOURCE") {
							json = {title:"车辆占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
						}else if(resourceType == "DRIVER_RESOURCE") {
							json = {title:"司机占用情况",closeOnEscape: true,draggable: true,resizable: true,height: 950,width: 1200,content: html,defaultBtnName:"关闭"};
						}
						showDialog(json);
					}else{
						element.parents(".dialogcontent").html(html);
					}
				},
				Error : function(data) {
					oAlert(data);
				}
				
				
			});
			

			/**
			 * 描述：清空功能
			 */
			$("#clear").die().live("click",function(){
				var confirmReturn = confirm("你确认要清空吗?");
				if(confirmReturn){
					var resCode = newJsonData.resCode;
					var begin = '';
					var end = '';
					var meetroomname = '';
					var resourceUUID = '';
					var returnValue = '{"beginTime": "'+ begin  + '","endTime": "'  +end +  '","employName": "' +meetroomname + '","uuid": "'  + resourceUUID  + '","resCode":"' +resCode + '"}';
					callback($.parseJSON(returnValue));
					closeDialog();
				}
			});
			
			
			$(".basic").die().live("click",function(e) {
				var class_ = $(e.target).attr("class");
				var begin = "";
				var end = "";
				var timestamp = $(this).attr("timestamp");
				var date = $(this).attr("date");
				if(timestamp == "1,4") {
					begin = date+" 00:00";
					end = date+" 02:00";
				}
				else if(timestamp == "5,8") {
					begin = date+" 02:00";
					end = date+" 04:00";
				}
				else if(timestamp == "9,12") {
					begin = date+" 04:00";
					end = date+" 06:00";
				}
				else if(timestamp == "13,16") {
					begin = date+" 06:00";
					end = date+" 08:00";
				}
				else if(timestamp == "17,18") {
					begin = date+" 08:00";
					end = date+" 09:00";
				}
				else if(timestamp == "19,20") {
					begin = date+" 09:00";
					end = date+" 10:00";
				}
				else if(timestamp == "21,22") {
					begin = date+" 10:00";
					end = date+" 11:00";
				}
				else if(timestamp == "23,24") {
					begin = date+" 11:00";
					end = date+" 12:00";
				}
				else if(timestamp == "25,26") {
					begin = date+" 12:00";
					end = date+" 13:00";
				}
				else if(timestamp == "27,28") {
					begin = date+" 13:00";
					end = date+" 14:00";
				}
				else if(timestamp == "29,30") {
					begin = date+" 14:00";
					end = date+" 15:00";
				}
				else if(timestamp == "31,32") {
					begin = date+" 15:00";
					end = date+" 16:00";
				}
				else if(timestamp == "33,34") {
					begin = date+" 16:00";
					end = date+" 17:00";
				}
				else if(timestamp == "35,36") {
					begin = date+" 17:00";
					end = date+" 18:00";
				}
				else if(timestamp == "37,40") {
					begin = date+" 18:00";
					end = date+" 20:00";
				}
				else if(timestamp == "41,44") {
					begin = date+" 20:00";
					end = date+" 22:00";
				}
				else if(timestamp == "45,48") {
					begin = date+" 22:00";
					end = date+" 23:59";
				}
				if(class_.indexOf("changeDiv")>=0) {
					
				}else if(resourceType == "MEET_RESOURCE"){
					var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>';
					 $(this).children().remove();
					$(this).append(html);
					
					var meetroomname = $(this).attr("meetroomname");
					var resourceUUID = $(this).attr("uuid");
					var resCode = $(this).attr("resCode");
					$(".newAppend").click(function() {
						if(confirm("确定增加一笔记录?")){
//							$("#meeting_begintime").val(begin);
//							$("#meeting_endtime").val(end);
							var timeMeetroomname = element.val(meetroomname);
							element.next().val(type+";"+meetroomname);
							$("#"+field.split(",")[0]).val(begin);
							var timeEnd  = $("#"+field.split(",")[1]).val(end);

							if (callback){
								var returnValue = '{"beginTime": "'+ begin  + '","endTime": "'  +end +  '","employName": "' +meetroomname + '","uuid": "'  + resourceUUID  + '","resCode":"' +resCode + '"}';
								callback($.parseJSON(returnValue));
							}
							
							closeDialog();
						}
					});
				}else if(resourceType == "CAR_RESOURCE") {
					var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>';
					 $(this).children().remove();
						$(this).append(html);
						var meetroomname = $(this).attr("meetroomname");
						var resourceUUID = $(this).attr("uuid");
						var resCode = $(this).attr("resCode");
						$(".newAppend").click(function() {
							if(confirm("确定增加一笔记录?")){
//								$("#datetime_begin").val(begin);
//								$("#datetime_end").val(end);
								$("#"+field.split(",")[0]).val(begin);
								$("#"+field.split(",")[1]).val(end);
								element.val(meetroomname);
								element.next().val(type+";"+meetroomname);
								
								if (callback){
									var returnValue = '{"beginTime": "'+ begin  + '","endTime": "'  +end +  '","employName": "' +meetroomname + '","uuid": "'  + resourceUUID  + '","resCode":"' +resCode + '"}';
									callback($.parseJSON(returnValue));
								}
								closeDialog();
							}
						});
				}else if(resourceType == "DRIVER_RESOURCE") {
					var html = '<div class="newAppend" style="width:100%;"><div class="changeDiv4"  style="width:100%;float:left;"></div></div>'
						 $(this).children().remove();
						$(this).append(html);
						var meetroomname = $(this).attr("meetroomname");
						var resourceUUID = $(this).attr("uuid");
						var resCode = $(this).attr("resCode");
						$(".newAppend").click(function() {
							if(confirm("确定增加一笔记录?")){
//								$("#datetime_begin").val(begin);
//								$("#datetime_end").val(end);
								$("#"+field.split(",")[0]).val(begin);
								$("#"+field.split(",")[1]).val(end);
								element.val(meetroomname);
								element.next().val(type+";"+meetroomname);
								if (callback){
									
									var returnValue = '{"beginTime": "'+ begin  + '","endTime": "'  +end +  '","employName": "' +meetroomname + '","uuid": "'  + resourceUUID  + '","resCode":"' +resCode + '"}';
									callback($.parseJSON(returnValue));
								}
								closeDialog();
							}
						});
				}
			});
		   }
			
		}		
				
			
		//点击占用的时间块打开一笔时间占用的记录
		$(".basic .basicDiv div").live("click",function() {
			var flowUuid = $(this).attr("flowUuid");
			var taskUuid = $(this).attr("taskUuid");
			var url = ctx + "/workflow/work/view/todo?taskUuid="+taskUuid+"&flowInstUuid="+flowUuid+"";
			window.open(url);
		});

})(jQuery);