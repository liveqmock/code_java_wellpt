var userDetails = SpringSecurityUtils.getUserDetails();
$(function() {
	
	var search = readSearch();
	
	var dytableSelector = "#fileDynamicForm";
	$(dytableSelector).dyform("setFieldValueByFieldName","ldx_notice_person","{'"+userDetails.userId+"':'"+userDetails.userName+"'}");
	$(dytableSelector).dyform("setFieldAsLabelByFieldName","ldx_notice_person");
	$(dytableSelector).dyform("setFieldValueByFieldName","FBRXSZ",userDetails.userName);
	$("#save").html("保存");
	$("#saveNormal").html("发布");
	$("#saveNormal").after("<button type='button' id='isTop'>置顶</button><button type='button' id='isNotTop'>取消置顶</button>");
	
	$("#isTop").live("click",function(){
		oConfirm("确认置顶？", function() {
			$(dytableSelector).dyform("setFieldValueByFieldName","SFZD","1");
		});
		
		
//		var topStatus = $(dytableSelector).dyform("getFieldValue",  "SFZD"));
//		alert(topStatus);
	});
	
	$("#isNotTop").live("click",function(){
		oConfirm("取消置顶？", function() {
			$(dytableSelector).dyform("setFieldValueByFieldName","SFZD","0");
		});
		
		
//		var topStatus = $(dytableSelector).dyform("getFieldValue",  "SFZD"));
//		alert(topStatus);
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