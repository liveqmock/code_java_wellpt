I18nLoader.load("/resources/pt/js/fileManager");
var File = {};
$(function() {
	
});

function viewAcct(zbelnr){
	var url = ctx +"/ficoManage/belnrSearch?belnr="+belnr+"&bukrs="+bukrs+"&gjahr="+data.substring(0,4);
	window.open(url,"总帐查询");
}

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