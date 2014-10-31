$(function() {
	initPageInfo();
});

/**
 * 初始化分页信息
 */
function initPageInfo(){
	if(currentPage==1||totalPage==1){
		$("#B_First").attr("disabled",true);
		$("#B_Previous").attr("disabled",true);
	}else{
		$("#B_First").attr("disabled",false);
		$("#B_Previous").attr("disabled",false);
	}
	if(totalPage==1||currentPage==totalPage){
		$("#B_Next").attr("disabled",true);
		$("#B_Last").attr("disabled",true);
	}else{
		$("#B_Next").attr("disabled",false);
		$("#B_Last").attr("disabled",false);
	}
}

/**
 * 翻页
 * @param num
 */
function addPage(num) {
	var currentPage = $("#currentPage").val();
	var totalPage = $("#totalPage").val();
    var targetPage = 1;
    num = parseInt(num);
    if (num != 0) {
        var targetPage = parseInt(currentPage) + num;
        if (targetPage < 1) {
            targetPage = 1;
            return;
        } else if (totalPage > 0 && totalPage < targetPage) {
            targetPage = totalPage;
            return;
        }
    }
    goToPage(targetPage);
}

function goToPage(num){
	var currentPage = $("#currentPage").val();
	if(num==parseInt(currentPage)){
		return;
	}
	$("#currentPage").val(num);
	$("#currentPage").parents("form").submit();
}