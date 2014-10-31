I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//添加
	File.addObject = function() {
		window.open(ctx + "/mps/enteradd");
	};
	//编辑
	File.editObject = function(parentUuid) {
		var arrayObj = readParamUuid();
		var question_ids = new Array();
		for ( var i = 0; i < arrayObj.length; i++) {
			var question_id = arrayObj[i]["question_id"];
			question_ids.push(question_id);
		}
		if (question_ids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
			if (question_ids.length > 1) {
				oAlert(global.selectARecord);
			} else {
				//alert(question_ids[0]);
				window.open(ctx + "/mps/enterupdate?qid=" + question_ids[0]);
			}
		}
	};
	//删除
	File.deleteObjects = function() {
		
		var qids="";
		var element = $(this);
		var arrayObj = readParamUuid();
		var question_ids = new Array();
		for ( var i = 0; i <= arrayObj.length - 1; i++) {
			var question_id = arrayObj[i]["question_id"];
			question_ids.push(question_id);
			qids+=question_id+"@@";
		}
		if (question_ids.length == 0) {
			oAlert(fileManager.pleaseChooseRecord);
		} else {
		    
		
		  //  alert(qids);
			
			if(window.confirm('你确定要删除这些数据？')){
			window.open(ctx + "/mps/deletedqb?qids=" + qids);
			}
		
			
	
		}
	};
});
function reloadFileParentWindow() {
	window.location.reload();
}
// 获取jsonstr参数
function readParamUuid() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var jsonstr = $(this).parent().parent(".dataTr").attr("jsonstr");
				var jsonObj = eval("(" + urldecode(jsonstr) + ")");
				s['question_id'] = jsonObj.questionid;
				arrayObj.push(s);
			});
	return arrayObj;
}
function readParam() {
	var arrayObj = new Array();
	$(".checkeds:checked").each(
			function(i) {
				var s = new Object();
				var index = $(this).parent().parent(".dataTr").attr("src")
						.indexOf("?");
				var search = $(this).parent().parent(".dataTr").attr("src")
						.substr(index);
				var searchArray = search.replace("?", "").split("&");
				for ( var i = 0; i < searchArray.length; i++) {
					var paraArray = searchArray[i].split("=");
					var key = paraArray[0];
					var value = paraArray[1];
					alert(key+"----"+value);
					s[key] = value;
				}
				arrayObj.push(s);
			});
	return arrayObj;
}
