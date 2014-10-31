$(function() {
	
	var search = readSearch();
	var keyword = urldecode(search.keyword);
	var keyword2 = urldecode(search.keyword2);
	var keyword3 = urldecode(search.keyword3);
	var keyword4 = urldecode(search.keyword4);
	var keywordTime1 = urldecode(search.keywordTime1);
	var keywordTime2 = urldecode(search.keywordTime2);
	var doSearch = search.doSearch;
	if(doSearch == "true") {
		var keyWordsArray = new Array();
		var keyWordObj = new Object();
		if(notNull(keyword)) {
			keyWordObj.all=keyword;
		}else if(notNull(keyword2) || notNull(keyword3) || notNull(keyword4)) {
			var keyWordKey2 = "流程.标题";
			var keyWordValue2 = keyword2;
			keyWordObj[keyWordKey2] = keyWordValue2;
			var keyWordKey3 =  "流程.预留文本5";
			var keyWordValue3 = keyword3;
			keyWordObj[keyWordKey3] = keyWordValue3;
			var keyWordKey4 =  "流程.预留文本10";
			var keyWordValue4 = keyword4;
			keyWordObj[keyWordKey4] = keyWordValue4;
		}
		keyWordsArray.push(keyWordObj);
		
		
		var dyViewQueryInfo = new Object();
		var condSelect = new Object();
		condSelect.optionTitle = "";
		condSelect.optionValue = "";
		condSelect.appointColumn = "";
		condSelect.beginTime = keywordTime1;
		condSelect.endTime = keywordTime2;
		//流程.创建时间，createTime
		condSelect.searchField = "createTime";
		condSelect.keyWords = keyWordsArray;
		condSelect.orderbyArr = "";
		condSelect.orderTitle = "";
		
		var pageInfo = new Object();
		var viewUuid = "c954f085-2b89-4f09-af56-f68f2d874415";
		var pageSize = 10;
		var pageCurrentPage = 1;
		dyViewQueryInfo.viewUuid = viewUuid;
		dyViewQueryInfo.viewName = "";
		pageInfo.currentPage = pageCurrentPage;
		pageInfo.pageSize = pageSize;
		pageInfo.totalCount = 0;
		pageInfo.first = 0;
		pageInfo.autoCount = true;
		dyViewQueryInfo.condSelect = condSelect;
		dyViewQueryInfo.pageInfo = pageInfo;
		
		var expandParams = new Object();
		dyViewQueryInfo.expandParams = expandParams;
		var requestUrl = ctx + '/basicdata/dyview/view_show_param.action';
		$.ajax({
			async:false,
			url:requestUrl,
			type:"POST",
			data:JSON.stringify(dyViewQueryInfo),
			dataType:'text',
			contentType:'application/json',
			success:function(data) {
				if(data == "") {
					alert("抱歉,没有符合条件的记录!");
				}
				$("#update_"+dyViewQueryInfo.viewUuid).html(data);
				$("#selectKeyTableId").css("display","");
				$("#keyWord").css("display","none");
				$("#showButton").text("↓");
				$(".selectKeyText").each(function() {
					if($(this).attr("field") == keyWordKey2) {
						$(this).val(keyWordValue2);
					}
					if($(this).attr("field") == keyWordKey3) {
						$(this).val(keyWordValue3);
					}
					if($(this).attr("field") == keyWordKey4) {
						$(this).val(keyWordValue4);
					}
				});
				formDate();
			}
		});
	}
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

//判断某字段是否为空
function notNull(str) {
	if (str != "undefined" && str != "")
	{
	    return true;
	}else {
		return false;
	}　
}

//格式化时间
function formDate(){
	$(".dataTr td").each(function(){
		var _reTimeReg = /^(?:19|20)[0-9][0-9]-(?:(?:[1-9])|(?:1[0-2]))-(?:(?:[1-9])|(?:[1-2][1-9])|(?:[1-3][0-1])) (?:(?:2[0-3])|(?:1[0-9])|(?:[1-9])|0):[0-5][0-9]:[0-5][0-9]$/;
		var tempText = $(this).text();
		if(_reTimeReg.test(tempText)){
			
			var dataStr = tempText.split(" ")[0];
			var timeStr = tempText.split(" ")[1];
			var dataStrArray = dataStr.split("-");
			var timeStrArray = timeStr.split(":");
			var strArray = new Array();
			strArray[0] = dataStrArray[0];
			strArray[1] = dataStrArray[1];
			strArray[2] = dataStrArray[2];
			strArray[3] = timeStrArray[0];
			strArray[4] = timeStrArray[1];
			strArray[5] = timeStrArray[2];
			for(var i=1;i<6;i++){
				if(strArray[i].length==1){
					strArray[i] = 0+""+strArray[i];
				}
			}
/**		var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4]+":"+strArray[5];**/
			var str = strArray[0]+"-"+strArray[1]+"-"+strArray[2]+" "+strArray[3]+":"+strArray[4];
			$(this).text(str);
			$(this).attr("title",str);
		}
	});
}