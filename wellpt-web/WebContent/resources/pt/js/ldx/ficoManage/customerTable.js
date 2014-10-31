I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	//打开添加页面
	File.addCustomer = function() {
		window.open(ctx + "/ficoManage/customerTable/add");
	};
	
	//删除客户对应表
	File.deleteCustomer = function() {
		var element = $(this);
		var datas = readParam();
		if(datas.length<1){
			oAlert2(fileManager.pleaseChooseRecord);
			return false;
		}
		if(!confirm(fileManager.deleteConfirm)){
			return false;
		}
		var arr = [];
		var ind = 0;
		datas.forEach(function (obj) {
			arr[ind++]=obj.bukrs+","+obj.kunnr;
		});
		JDS.call({
			service : "customerTableService.deleteCustomer",
			data : [arr.join(";")],
			async : false,
			success : function(res) {
				oAlert(fileManager.deleteSuccess, function() {
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
		s.bukrs=$(this).parent().parent(".dataTr").children("td[field='bUKRS']").attr("title");
		s.kunnr=$(this).parent().parent(".dataTr").children("td[field='kUNNR']").attr("title");
		arrayObj.push(s);
	});
	return arrayObj;
}