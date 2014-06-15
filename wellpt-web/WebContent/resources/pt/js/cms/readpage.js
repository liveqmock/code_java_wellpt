$(function() {
	$(".more").click(function(){
		var moreUrl = $(this).parent().parent().find(".active").attr("moreurl");
		window.location.href = ctx+moreUrl;
	});
	$(".dnrw ul .active").click(function(){
		var moreUrl = $(this).attr("moreurl");
		if(moreUrl!="" && moreUrl != "null"){
			window.location.href = ctx+moreUrl;
		}else{
			window.location.href = location.href;
		}
	});
});
