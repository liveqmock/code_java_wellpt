I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//开票确认
	File.doneConfirm = function() {
		var element = $(this);
		var datas = readParam();
		if(datas.length<1){
			oAlert2(fileManager.pleaseChooseRecord);
			return false;
		}
		if(!confirm("确定更新为已开票状态?")){
			return false;
		}
		var arr = [];
		var ind = 0;
		datas.forEach(function (obj) {
			arr[ind++]=obj.zbelnr+","+obj.vbeln;
		});
		JDS.call({
			service : "billTrackService.billConfirm",
			data : [arr.join(";"),"Y"],
			async : false,
			success : function(res) {
				oAlert(fileManager.saveSuccess, function() {
					refreshWindow(element);
				});
			}
		});
	};
	File.failConfirm = function() {
		var element = $(this);
		var datas = readParam();
		if(datas.length<1){
			oAlert2(fileManager.pleaseChooseRecord);
			return false;
		}
		if(!confirm("确定更新为未开票状态?")){
			return false;
		}
		var arr = [];
		var ind = 0;
		datas.forEach(function (obj) {
			arr[ind++]=obj.zbelnr+","+obj.vbeln;
		});
		JDS.call({
			service : "billTrackService.billConfirm",
			data : [arr.join(";"),"N"],
			async : false,
			success : function(res) {
				oAlert(fileManager.saveSuccess, function() {
					refreshWindow(element);
				});
			}
		});
	};
});

function reloadFileParentWindow() {
	window.location.reload();
}

function readParam(){
	var arrayObj = new Array();
	$(".checkeds:checked").each(function(i){
		var s = new Object(); 
		s.zbelnr=$(this).parent().parent(".dataTr").children("td[field='zBELNR']").attr("title");
		s.vbeln=$(this).parent().parent(".dataTr").children("td[field='vBELN']").attr("title");
		arrayObj.push(s);
	});
	return arrayObj;
}