$(function() {
		//判断该用户是否具有删除权限、编辑权限
		var xiangmuId = $("#_inputXMBH").val();
		var isProject = "";
		JDS.call({
			async:false,
			service : "projectService.isProjectBidById",
			data : [xiangmuId],
			success : function(result) {
				isProject = result.data;
			}
		});
		
		var fileUuid = $("#uuid").val();
		JDS.call({
			async:false,
			service : "fileManagerService.getFmFileByUuid",
			data : [fileUuid],
			success : function(result) {
				var fieldNameArray = new Array();
				var selectionArgs = new Array();
				var formUuid = result.data.dynamicFormId;
			    var dynamicDataId = result.data.dynamicDataId;	
			    JDS.call({
					async:false,
					service : "dyFormDataService.query2",
					data : [formUuid,fieldNameArray," uuid='" + dynamicDataId + "' ",selectionArgs,"","","",0,0],
					success : function(result) {
						var createTime = "";
							var data = result.data;
							if(data.length !=0) {
								for(var index=0;index<data.length;index++) {
									var uuid = data[index].uUID;
									if(uuid == dynamicDataId) {
										creatTime = data[index].createTime;
										var creatDate = creatTime.split(" ")[0];
										var myDate = new Date();
										 var year=myDate.getFullYear(); 
										 var month=myDate.getMonth()+1;
										 month =(month<10 ? "0"+month:month); 
										 var nowDate = year.toString()+"-"+month.toString()+"-"+myDate.getDate().toString();
										if(creatDate == nowDate && isProject == false ) {
											var editStatus = $("#editStatus").val();
											 if(editStatus == "0") {
												 File.addFunctionsButton("delete,editor");
											 }else if(editStatus == "1") {
												 File.addFunctionsButton("save");
											 }
										}
									}
								}
							}
					}
				});
			}
		});
	
		//判断该用户是否具有操作黑名单的权限
		var resultData = "";
		var btn = "B013002003";//加入黑名单按钮
		JDS.call({
			async:false,
			service : "XZSPService.isGrant",
			data : [btn],
			success : function(result) {
				resultData = result.data;
			}
		});
		
		if(resultData) {
			$('input[type=radio][name="SHJRHMD"]').live("click",function(){
				var join_reason = SpringSecurityUtils.getCurrentUserName();
				var joinTime = "";
				var myDate = new Date();
				joinTime = myDate.getFullYear() + "-" + parseInt(myDate.getMonth()+1) + "-" + myDate.getDate() + " " + myDate.getHours() + ":" + myDate.getMinutes() + ":" +myDate.getSeconds() ;
				var json = new Object();
				var data = "<div style='padding:20px;'><table style='width: 90%;'><tr><td>加入或解除黑名单原因</td>" +
						"<td><textarea style='width:380px;height: 150px;' name='join_reason' id='join_reason'></textarea>" +
						"</td></tr>" +
						"<tr><td>操作人员</td><td><span id='join_person' name='join_person' value='"+join_reason+"'>"+join_reason+"</span></td></tr>"+
						"<tr><td>加入时间</td><td><span id='join_time' name='join_time' value='"+joinTime+"'>"+joinTime+"</span></td></tr>"
						"</table></div>"
		        json.content = data;
		        json.title = "黑名单";
		        json.height= 400;
		        json.width= 600;
		        json.buttons = {
		        		"确定":function() {
		        			if($("#join_reason").val() == undefined || $("#join_reason").val() == "") {
		        				oAlert("请填写加入或解除黑名单原因!");
		        			}else {
		        				var blackData = new Object();
			        			blackData['joinblack_reason'] = $("#join_reason").val();
			        			blackData['CZRY'] = $("#join_person").attr("value");
			        			blackData['CZSJ'] = $("#join_time").attr("value");
			        			var dytableSelector = "#fileDynamicForm"; 
			        			$(dytableSelector).dyform("addRowData", 
									 "uf_xzsp_black",
									  blackData
								);
			        			 $("#dialogModule").dialog( "close" );
		        			}
		        		},
		        }
		        showDialog(json);
		});
		}else {
			$('input[type=radio][name="_radio_inputSHJRHMD"]').attr("disabled","disabled");
		}
	
});