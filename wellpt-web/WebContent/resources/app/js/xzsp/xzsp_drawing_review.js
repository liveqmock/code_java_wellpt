$(function() {
	var btn = "<button id='btn_review' name='btn_review'>发起审阅</button>";
	
	//判断用户是否有操作发起审阅的权限
	var resultData = "";
	JDS.call({
		async: false,
		service: "XZSPService.isGrant",
		data: ["B013001081"],
		success: function(result){
			resultData = result.data;
		}
	});
	if(resultData) {
		WorkFlow.addButton(btn);
		var unitList = "";
		for(var i=0; i<1; i++) {
			unitList = unitList + "<tr><td>"+
			"<input type='checkbox' id='review_unitName' value='" + 426605496 + "'>"+
			"<span id='unitName_value'>单位名</span></td>"+
			"<td>手机号</td></tr>";
		}
		
		$("#btn_review").live("click", function(){
			var json = new Object();
			var data = "<style>#review_unitName{display: block; float: left;} #unitName_value{display: block; float: left; margin: 2px 0 0 4px;} .col_2{font-weight: normal; color: #2468A9; font-size: 14px;}</style>"+
			"<div style='padding:20px;' class='review col_2'>" +
			"<div style='margin-left: 30%; font-weight: bolder'>单位列表</div>" +
			"<table style='width: 99%;'><tr><td style='font-weight: bolder'>单位名称</td>" +
			"<td style='font-weight: bolder'>接收手机号码</td></tr>" + unitList +"</table></div>";
	        json.content = data;
	        json.title = "发起审阅情况";
	        json.height= 460;
	        json.width= 600;
	        json.buttons = {
	        		"发送":function() {
	        			if($("input[id=review_unitName]:checked").length == 0) {
	        				oAlert("请至少选择一个单位！");
	        			}else {
	        				var unitIdArr = "";
	        				$("input[id=review_unitName]:checked").each(function(){
	        					unitIdArr = unitIdArr + "," + $(this).val() + ":" + $(this).next("#unitName_value").text();
	        				});
	        				var banjian_uuid = $("#ep_banjian_uuid").val();
	        				JDS.call({
	        					async: false,
	        					service: "banJianService.drawingReview",
	        					data: [unitIdArr.substr(1),banjian_uuid],
	        					success: function(result) {
	        						oAlert("发送审阅成功！");
	        					}
	        				});
	        				
		        			$("#dialogModule").dialog( "close" );
	        			}
	        		},
	        }
	        showDialog(json);
		});
	}
		
	
});