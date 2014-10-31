$(function() {
	//项目单提交前执行方法
	File.setFileData("beforeSubmitService", "projectService.beforeSubmit");
	//项目单提交后执行方法
	File.setFileData("afterSubmitService", "projectService.afterSubmit");
	//设置为只读
	$("#fileDynamicForm").dyform("showAsLabel");
	var search = readSearch();
	
	var fmUuid = search.uuid;
	
	$(":button[id=save]").remove();
	$(":button[id=deleteButton]").remove();
	$(":button[id=FILE_PRINT]").remove();
	$(":button[id=saveNormal]").html("申请成功");
	var XMMC = $("#XMMC").val();
	var btn_view_project_tree = '<button type="button" id="btn_view_project_tree">申请失败</button>';
	$(".form_operate button:first").before(btn_view_project_tree);
	
	$("#btn_view_project_tree").click(function() {
		var fmTitle = "";
		var modifyTime = "";
		$.ajax({
			type : "POST",
			url : ctx + "/xzsp/project/getFmFile",
			data : fmUuid,
			contentType : "application/json",
			dataType : "json",
			success : function(result) {
				 fmTitle = result.title;
				 modifyTime = result.modifyTime;
				 var join_reason = SpringSecurityUtils.getCurrentUserName();
					var joinTime = "";
					var myDate = new Date();
					joinTime = myDate.getFullYear() + "-" + parseInt(myDate.getMonth()+1) + "-"
							+ myDate.getDate() + " " + myDate.getHours() + ":" + myDate.getMinutes()
							+ ":" + myDate.getSeconds();
					var json = new Object();
					var data = "<div style='padding:20px;'><table style='width: 90%;'><tr><td>审核不通过原因</td>"
						+ "<td><textarea style='width:380px;height: 150px;' name='join_reason' id='join_reason'>" 
						+"您于["+modifyTime+"]通过网站申请的项目["+fmTitle+"]未通过预审，特此通知。【厦门市行政服务中心】</textarea>"
						+ "</td></tr>"
						+ "<tr><td>审核人员</td><td><span id='join_person' name='join_person' value='"
						+ join_reason
						+ "'>"
						+ join_reason
						+ "</span></td></tr>"
						+ "<tr><td>审核时间</td><td><span id='join_time' name='join_time' value='"
						+ joinTime + "'>" + joinTime + "</span></td></tr>";
					json.content = data;
					json.title = "项目单审核";
					json.height = 400;
					json.width = 600;
					json.buttons = {
							"确定" : function() {
								if ($("#join_reason").val() == undefined || $("#join_reason").val() == "") {
									oAlert("请填写审核不通过原因!");
								} else {
									$.ajax({
										type : "POST",
										url : ctx + "/xzsp/project/sendFailMessage",
										data : fmUuid,
										contentType : "application/json",
										dataType : "json",
										success : function(result) {
											alert(1);
										},
										error : function(result) {
										}
									});
									$("#dialogModule").dialog("close");
								}
							},
						};
						showDialog(json);
			},
			error : function(result) {
			}
		});
		
		
	});
});

//从url中获取参数值
function readSearch() {
	var search = window.location.search;
	var s = new Object();
	var searchArray = search.replace("?", "").split("&");
	for ( var i = 0; i < searchArray.length; i++) {
		var paraArray = searchArray[i].split("=");
		var key = paraArray[0];
		var value = paraArray[1];
		s[key] = value;
	}
	return s;
}