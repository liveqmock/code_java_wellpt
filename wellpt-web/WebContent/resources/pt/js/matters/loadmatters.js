$(function(){
	//首次进入事项管理的时候默认选中第一个事项
	$(".liveData_matters").each(function(i){
		$(this).addClass('active');
		var $this = $(this);
		var url = $(this).attr("url");
		var title = $(this).attr("name_");
//		var id_ = this.id;
		pageLock("show");
		$.ajax({
			type:"post",
			async:false,
			url:ctx+url,
			success:function(result){
				$("#"+window.dnrwId+" ul li a" ).text(title);
//				$("#"+window.dnrwId+" div div").attr("moduleid",id_);
				$("#"+window.dnrwId+" div div").html(result);
				//事项管理新增按钮事件处理
				if($this.attr("folderid")!=""&&$("button[value='B014001001']").length>0){
					var onclickStr = $("button[value='B014001001']").attr("onclick");
					onclickStr = onclickStr.replace("folderid",$this.attr("folderid"));
					$("button[value='B014001001']").attr("onclick",onclickStr);
				}
				//格式化时间
				formDate();
				pageLock("hide");
			}
		});
		return false;
	}); 
});